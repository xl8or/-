package myorg.bouncycastle.asn1;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.BERConstructedOctetString;
import myorg.bouncycastle.asn1.BERConstructedSequence;
import myorg.bouncycastle.asn1.BERSet;
import myorg.bouncycastle.asn1.BERTaggedObject;
import myorg.bouncycastle.asn1.DERInputStream;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.DEROutputStream;
import myorg.bouncycastle.asn1.DERTaggedObject;

public class BERInputStream extends DERInputStream {

   private static final DERObject END_OF_STREAM = new BERInputStream.1();


   public BERInputStream(InputStream var1) {
      super(var1);
   }

   private BERConstructedOctetString buildConstructedOctetString() throws IOException {
      Vector var1 = new Vector();

      while(true) {
         DERObject var2 = this.readObject();
         DERObject var3 = END_OF_STREAM;
         if(var2 == var3) {
            return new BERConstructedOctetString(var1);
         }

         var1.addElement(var2);
      }
   }

   private byte[] readIndefiniteLengthFully() throws IOException {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();
      int var2 = this.read();

      while(true) {
         int var3 = this.read();
         if(var3 < 0 || var2 == 0 && var3 == 0) {
            return var1.toByteArray();
         }

         var1.write(var2);
         var2 = var3;
      }
   }

   public DERObject readObject() throws IOException {
      int var1 = this.read();
      if(var1 == -1) {
         throw new EOFException();
      } else {
         int var2 = this.readLength();
         Object var3;
         if(var2 < 0) {
            BERConstructedSequence var4;
            DERObject var5;
            switch(var1) {
            case 5:
               var3 = null;
               break;
            case 36:
               var3 = this.buildConstructedOctetString();
               break;
            case 48:
               var4 = new BERConstructedSequence();

               while(true) {
                  var5 = this.readObject();
                  DERObject var6 = END_OF_STREAM;
                  if(var5 == var6) {
                     var3 = var4;
                     return (DERObject)var3;
                  }

                  var4.addObject(var5);
               }
            case 49:
               ASN1EncodableVector var7 = new ASN1EncodableVector();

               while(true) {
                  var5 = this.readObject();
                  DERObject var8 = END_OF_STREAM;
                  if(var5 == var8) {
                     var3 = new BERSet(var7);
                     return (DERObject)var3;
                  }

                  var7.add(var5);
               }
            default:
               if((var1 & 128) == 0) {
                  throw new IOException("unknown BER object encountered");
               }

               if((var1 & 31) == 31) {
                  throw new IOException("unsupported high tag encountered");
               }

               if((var1 & 32) == 0) {
                  byte[] var9 = this.readIndefiniteLengthFully();
                  int var10 = var1 & 31;
                  DEROctetString var11 = new DEROctetString(var9);
                  var3 = new BERTaggedObject((boolean)0, var10, var11);
               } else {
                  DERObject var12 = this.readObject();
                  DERObject var13 = END_OF_STREAM;
                  if(var12 == var13) {
                     int var14 = var1 & 31;
                     var3 = new DERTaggedObject(var14);
                  } else {
                     DERObject var15 = this.readObject();
                     DERObject var16 = END_OF_STREAM;
                     if(var15 == var16) {
                        int var17 = var1 & 31;
                        var3 = new BERTaggedObject(var17, var12);
                     } else {
                        var4 = new BERConstructedSequence();
                        var4.addObject(var12);

                        DERObject var18;
                        do {
                           var4.addObject(var15);
                           var15 = this.readObject();
                           var18 = END_OF_STREAM;
                        } while(var15 != var18);

                        int var19 = var1 & 31;
                        var3 = new BERTaggedObject((boolean)0, var19, var4);
                     }
                  }
               }
            }
         } else if(var1 == 0 && var2 == 0) {
            var3 = END_OF_STREAM;
         } else {
            byte[] var20 = new byte[var2];
            this.readFully(var20);
            var3 = this.buildObject(var1, var20);
         }

         return (DERObject)var3;
      }
   }

   static class 1 extends DERObject {

      1() {}

      void encode(DEROutputStream var1) throws IOException {
         throw new IOException("Eeek!");
      }

      public boolean equals(Object var1) {
         boolean var2;
         if(var1 == this) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      public int hashCode() {
         return 0;
      }
   }
}
