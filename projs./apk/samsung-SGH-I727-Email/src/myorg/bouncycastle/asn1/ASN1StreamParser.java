package myorg.bouncycastle.asn1;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1InputStream;
import myorg.bouncycastle.asn1.BERApplicationSpecificParser;
import myorg.bouncycastle.asn1.BEROctetStringParser;
import myorg.bouncycastle.asn1.BERSequenceParser;
import myorg.bouncycastle.asn1.BERSetParser;
import myorg.bouncycastle.asn1.BERTaggedObjectParser;
import myorg.bouncycastle.asn1.DERApplicationSpecific;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERExternalParser;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DEROctetStringParser;
import myorg.bouncycastle.asn1.DERSequenceParser;
import myorg.bouncycastle.asn1.DERSetParser;
import myorg.bouncycastle.asn1.DERUnknownTag;
import myorg.bouncycastle.asn1.DefiniteLengthInputStream;
import myorg.bouncycastle.asn1.IndefiniteLengthInputStream;

public class ASN1StreamParser {

   private final InputStream _in;
   private final int _limit;


   public ASN1StreamParser(InputStream var1) {
      int var2 = findLimit(var1);
      this(var1, var2);
   }

   public ASN1StreamParser(InputStream var1, int var2) {
      this._in = var1;
      this._limit = var2;
   }

   public ASN1StreamParser(byte[] var1) {
      ByteArrayInputStream var2 = new ByteArrayInputStream(var1);
      int var3 = var1.length;
      this(var2, var3);
   }

   private static int findLimit(InputStream var0) {
      int var1;
      if(var0 instanceof DefiniteLengthInputStream) {
         var1 = ((DefiniteLengthInputStream)var0).getRemaining();
      } else {
         var1 = Integer.MAX_VALUE;
      }

      return var1;
   }

   private void set00Check(boolean var1) {
      if(this._in instanceof IndefiniteLengthInputStream) {
         ((IndefiniteLengthInputStream)this._in).setEofOn00(var1);
      }
   }

   public DEREncodable readObject() throws IOException {
      int var1 = this._in.read();
      Object var2;
      if(var1 == -1) {
         var2 = null;
      } else {
         this.set00Check((boolean)0);
         int var3 = ASN1InputStream.readTagNumber(this._in, var1);
         byte var4;
         if((var1 & 32) != 0) {
            var4 = 1;
         } else {
            var4 = 0;
         }

         InputStream var5 = this._in;
         int var6 = this._limit;
         int var7 = ASN1InputStream.readLength(var5, var6);
         if(var7 < 0) {
            if(var4 == 0) {
               throw new IOException("indefinite length primitive encoding encountered");
            }

            InputStream var8 = this._in;
            IndefiniteLengthInputStream var9 = new IndefiniteLengthInputStream(var8);
            if((var1 & 64) != 0) {
               int var10 = this._limit;
               ASN1StreamParser var11 = new ASN1StreamParser(var9, var10);
               var2 = new BERApplicationSpecificParser(var3, var11);
            } else if((var1 & 128) != 0) {
               var2 = new BERTaggedObjectParser(var1, var3, var9);
            } else {
               int var12 = this._limit;
               ASN1StreamParser var13 = new ASN1StreamParser(var9, var12);
               switch(var3) {
               case 4:
                  var2 = new BEROctetStringParser(var13);
                  break;
               case 8:
                  var2 = new DERExternalParser(var13);
                  break;
               case 16:
                  var2 = new BERSequenceParser(var13);
                  break;
               case 17:
                  var2 = new BERSetParser(var13);
                  break;
               default:
                  StringBuilder var14 = (new StringBuilder()).append("unknown BER object encountered: 0x");
                  String var15 = Integer.toHexString(var3);
                  String var16 = var14.append(var15).toString();
                  throw new IOException(var16);
               }
            }
         } else {
            InputStream var17 = this._in;
            DefiniteLengthInputStream var18 = new DefiniteLengthInputStream(var17, var7);
            if((var1 & 64) != 0) {
               byte[] var19 = var18.toByteArray();
               var2 = new DERApplicationSpecific((boolean)var4, var3, var19);
            } else if((var1 & 128) != 0) {
               var2 = new BERTaggedObjectParser(var1, var3, var18);
            } else if(var4 != 0) {
               switch(var3) {
               case 4:
                  ASN1StreamParser var21 = new ASN1StreamParser(var18);
                  var2 = new BEROctetStringParser(var21);
                  break;
               case 8:
                  ASN1StreamParser var24 = new ASN1StreamParser(var18);
                  var2 = new DERExternalParser(var24);
                  break;
               case 16:
                  ASN1StreamParser var22 = new ASN1StreamParser(var18);
                  var2 = new DERSequenceParser(var22);
                  break;
               case 17:
                  ASN1StreamParser var23 = new ASN1StreamParser(var18);
                  var2 = new DERSetParser(var23);
                  break;
               default:
                  byte[] var20 = var18.toByteArray();
                  var2 = new DERUnknownTag((boolean)1, var3, var20);
               }
            } else {
               switch(var3) {
               case 4:
                  var2 = new DEROctetStringParser(var18);
                  break;
               default:
                  byte[] var25 = var18.toByteArray();
                  var2 = ASN1InputStream.createPrimitiveDERObject(var3, var25);
               }
            }
         }
      }

      return (DEREncodable)var2;
   }

   ASN1EncodableVector readVector() throws IOException {
      ASN1EncodableVector var1 = new ASN1EncodableVector();

      while(true) {
         DEREncodable var2 = this.readObject();
         if(var2 == null) {
            return var1;
         }

         DERObject var3 = var2.getDERObject();
         var1.add(var3);
      }
   }
}
