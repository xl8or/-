package myorg.bouncycastle.asn1;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.BERInputStream;
import myorg.bouncycastle.asn1.DERBMPString;
import myorg.bouncycastle.asn1.DERBitString;
import myorg.bouncycastle.asn1.DERBoolean;
import myorg.bouncycastle.asn1.DERConstructedSequence;
import myorg.bouncycastle.asn1.DERConstructedSet;
import myorg.bouncycastle.asn1.DEREnumerated;
import myorg.bouncycastle.asn1.DERGeneralString;
import myorg.bouncycastle.asn1.DERGeneralizedTime;
import myorg.bouncycastle.asn1.DERIA5String;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERNull;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.DERPrintableString;
import myorg.bouncycastle.asn1.DERT61String;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.DERTags;
import myorg.bouncycastle.asn1.DERUTCTime;
import myorg.bouncycastle.asn1.DERUTF8String;
import myorg.bouncycastle.asn1.DERUniversalString;
import myorg.bouncycastle.asn1.DERUnknownTag;
import myorg.bouncycastle.asn1.DERVisibleString;

public class DERInputStream extends FilterInputStream implements DERTags {

   public DERInputStream(InputStream var1) {
      super(var1);
   }

   protected DERObject buildObject(int var1, byte[] var2) throws IOException {
      Object var3;
      BERInputStream var5;
      DERConstructedSequence var6;
      switch(var1) {
      case 1:
         var3 = new DERBoolean(var2);
         break;
      case 2:
         var3 = new DERInteger(var2);
         break;
      case 3:
         byte var13 = var2[0];
         byte[] var14 = new byte[var2.length - 1];
         int var15 = var2.length - 1;
         System.arraycopy(var2, 1, var14, 0, var15);
         var3 = new DERBitString(var14, var13);
         break;
      case 4:
         var3 = new DEROctetString(var2);
         break;
      case 5:
         var3 = false;
         break;
      case 6:
         var3 = new DERObjectIdentifier(var2);
         break;
      case 10:
         var3 = new DEREnumerated(var2);
         break;
      case 12:
         var3 = new DERUTF8String(var2);
         break;
      case 19:
         var3 = new DERPrintableString(var2);
         break;
      case 20:
         var3 = new DERT61String(var2);
         break;
      case 22:
         var3 = new DERIA5String(var2);
         break;
      case 23:
         var3 = new DERUTCTime(var2);
         break;
      case 24:
         var3 = new DERGeneralizedTime(var2);
         break;
      case 26:
         var3 = new DERVisibleString(var2);
         break;
      case 27:
         var3 = new DERGeneralString(var2);
         break;
      case 28:
         var3 = new DERUniversalString(var2);
         break;
      case 30:
         var3 = new DERBMPString(var2);
         break;
      case 48:
         ByteArrayInputStream var4 = new ByteArrayInputStream(var2);
         var5 = new BERInputStream(var4);
         var6 = new DERConstructedSequence();

         try {
            while(true) {
               DERObject var7 = var5.readObject();
               var6.addObject(var7);
            }
         } catch (EOFException var30) {
            var3 = var6;
            break;
         }
      case 49:
         ByteArrayInputStream var9 = new ByteArrayInputStream(var2);
         var5 = new BERInputStream(var9);
         ASN1EncodableVector var10 = new ASN1EncodableVector();

         try {
            while(true) {
               DERObject var11 = var5.readObject();
               var10.add(var11);
            }
         } catch (EOFException var29) {
            var3 = new DERConstructedSet(var10);
            break;
         }
      default:
         if((var1 & 128) != 0) {
            if((var1 & 31) == 31) {
               throw new IOException("unsupported high tag encountered");
            }

            if(var2.length == 0) {
               if((var1 & 32) == 0) {
                  int var16 = var1 & 31;
                  DERNull var17 = new DERNull();
                  var3 = new DERTaggedObject((boolean)0, var16, var17);
               } else {
                  int var18 = var1 & 31;
                  DERConstructedSequence var19 = new DERConstructedSequence();
                  var3 = new DERTaggedObject((boolean)0, var18, var19);
               }
            } else if((var1 & 32) == 0) {
               int var20 = var1 & 31;
               DEROctetString var21 = new DEROctetString(var2);
               var3 = new DERTaggedObject((boolean)0, var20, var21);
            } else {
               ByteArrayInputStream var22 = new ByteArrayInputStream(var2);
               var5 = new BERInputStream(var22);
               DERObject var23 = var5.readObject();
               if(var5.available() == 0) {
                  int var24 = var1 & 31;
                  var3 = new DERTaggedObject(var24, var23);
               } else {
                  var6 = new DERConstructedSequence();
                  var6.addObject(var23);

                  try {
                     while(true) {
                        DERObject var25 = var5.readObject();
                        var6.addObject(var25);
                     }
                  } catch (EOFException var28) {
                     int var27 = var1 & 31;
                     var3 = new DERTaggedObject((boolean)0, var27, var6);
                  }
               }
            }
         } else {
            var3 = new DERUnknownTag(var1, var2);
         }
      }

      return (DERObject)var3;
   }

   protected void readFully(byte[] param1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   protected int readLength() throws IOException {
      int var1 = this.read();
      if(var1 < 0) {
         throw new IOException("EOF found when length expected");
      } else {
         int var2;
         if(var1 == 128) {
            var2 = -1;
         } else {
            if(var1 > 127) {
               int var3 = var1 & 127;
               if(var3 > 4) {
                  throw new IOException("DER length more than 4 bytes");
               }

               var1 = 0;

               for(int var4 = 0; var4 < var3; ++var4) {
                  int var5 = this.read();
                  if(var5 < 0) {
                     throw new IOException("EOF found reading length");
                  }

                  int var6 = (var1 << 8) + var5;
               }

               if(var1 < 0) {
                  throw new IOException("corrupted stream - negative length found");
               }
            }

            var2 = var1;
         }

         return var2;
      }
   }

   public DERObject readObject() throws IOException {
      int var1 = this.read();
      if(var1 == -1) {
         throw new EOFException();
      } else {
         byte[] var2 = new byte[this.readLength()];
         this.readFully(var2);
         return this.buildObject(var1, var2);
      }
   }
}
