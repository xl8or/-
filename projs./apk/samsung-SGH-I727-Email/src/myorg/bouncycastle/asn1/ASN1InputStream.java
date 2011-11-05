package myorg.bouncycastle.asn1;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1StreamParser;
import myorg.bouncycastle.asn1.BERApplicationSpecificParser;
import myorg.bouncycastle.asn1.BERConstructedOctetString;
import myorg.bouncycastle.asn1.BEROctetStringParser;
import myorg.bouncycastle.asn1.BERSequenceParser;
import myorg.bouncycastle.asn1.BERSetParser;
import myorg.bouncycastle.asn1.BERTaggedObjectParser;
import myorg.bouncycastle.asn1.DERApplicationSpecific;
import myorg.bouncycastle.asn1.DERBMPString;
import myorg.bouncycastle.asn1.DERBitString;
import myorg.bouncycastle.asn1.DERBoolean;
import myorg.bouncycastle.asn1.DEREnumerated;
import myorg.bouncycastle.asn1.DERExternal;
import myorg.bouncycastle.asn1.DERExternalParser;
import myorg.bouncycastle.asn1.DERFactory;
import myorg.bouncycastle.asn1.DERGeneralString;
import myorg.bouncycastle.asn1.DERGeneralizedTime;
import myorg.bouncycastle.asn1.DERIA5String;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERNull;
import myorg.bouncycastle.asn1.DERNumericString;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.DERPrintableString;
import myorg.bouncycastle.asn1.DERT61String;
import myorg.bouncycastle.asn1.DERTags;
import myorg.bouncycastle.asn1.DERUTCTime;
import myorg.bouncycastle.asn1.DERUTF8String;
import myorg.bouncycastle.asn1.DERUniversalString;
import myorg.bouncycastle.asn1.DERUnknownTag;
import myorg.bouncycastle.asn1.DERVisibleString;
import myorg.bouncycastle.asn1.DefiniteLengthInputStream;
import myorg.bouncycastle.asn1.IndefiniteLengthInputStream;
import myorg.bouncycastle.asn1.LazyDERSequence;
import myorg.bouncycastle.util.io.Streams;

public class ASN1InputStream extends FilterInputStream implements DERTags {

   private final boolean lazyEvaluate;
   private final int limit;


   public ASN1InputStream(InputStream var1) {
      this(var1, Integer.MAX_VALUE);
   }

   public ASN1InputStream(InputStream var1, int var2) {
      this(var1, var2, (boolean)0);
   }

   public ASN1InputStream(InputStream var1, int var2, boolean var3) {
      super(var1);
      this.limit = var2;
      this.lazyEvaluate = var3;
   }

   public ASN1InputStream(byte[] var1) {
      ByteArrayInputStream var2 = new ByteArrayInputStream(var1);
      int var3 = var1.length;
      this(var2, var3);
   }

   public ASN1InputStream(byte[] var1, boolean var2) {
      ByteArrayInputStream var3 = new ByteArrayInputStream(var1);
      int var4 = var1.length;
      this(var3, var4, var2);
   }

   static DERObject createPrimitiveDERObject(int var0, byte[] var1) {
      Object var2;
      switch(var0) {
      case 1:
         var2 = new DERBoolean(var1);
         break;
      case 2:
         var2 = new DERInteger(var1);
         break;
      case 3:
         byte var3 = var1[0];
         byte[] var4 = new byte[var1.length - 1];
         int var5 = var1.length - 1;
         System.arraycopy(var1, 1, var4, 0, var5);
         var2 = new DERBitString(var4, var3);
         break;
      case 4:
         var2 = new DEROctetString(var1);
         break;
      case 5:
         var2 = DERNull.INSTANCE;
         break;
      case 6:
         var2 = new DERObjectIdentifier(var1);
         break;
      case 7:
      case 8:
      case 9:
      case 11:
      case 13:
      case 14:
      case 15:
      case 16:
      case 17:
      case 21:
      case 25:
      case 29:
      default:
         var2 = new DERUnknownTag((boolean)0, var0, var1);
         break;
      case 10:
         var2 = new DEREnumerated(var1);
         break;
      case 12:
         var2 = new DERUTF8String(var1);
         break;
      case 18:
         var2 = new DERNumericString(var1);
         break;
      case 19:
         var2 = new DERPrintableString(var1);
         break;
      case 20:
         var2 = new DERT61String(var1);
         break;
      case 22:
         var2 = new DERIA5String(var1);
         break;
      case 23:
         var2 = new DERUTCTime(var1);
         break;
      case 24:
         var2 = new DERGeneralizedTime(var1);
         break;
      case 26:
         var2 = new DERVisibleString(var1);
         break;
      case 27:
         var2 = new DERGeneralString(var1);
         break;
      case 28:
         var2 = new DERUniversalString(var1);
         break;
      case 30:
         var2 = new DERBMPString(var1);
      }

      return (DERObject)var2;
   }

   static int readLength(InputStream var0, int var1) throws IOException {
      int var2 = var0.read();
      if(var2 < 0) {
         throw new EOFException("EOF found when length expected");
      } else {
         int var3;
         if(var2 == 128) {
            var3 = -1;
         } else {
            if(var2 > 127) {
               int var4 = var2 & 127;
               if(var4 > 4) {
                  String var5 = "DER length more than 4 bytes: " + var4;
                  throw new IOException(var5);
               }

               var2 = 0;

               for(int var6 = 0; var6 < var4; ++var6) {
                  int var7 = var0.read();
                  if(var7 < 0) {
                     throw new EOFException("EOF found reading length");
                  }

                  int var8 = (var2 << 8) + var7;
               }

               if(var2 < 0) {
                  throw new IOException("corrupted stream - negative length found");
               }

               if(var2 >= var1) {
                  throw new IOException("corrupted stream - out of bounds length found");
               }
            }

            var3 = var2;
         }

         return var3;
      }
   }

   static int readTagNumber(InputStream var0, int var1) throws IOException {
      int var2 = var1 & 31;
      if(var2 == 31) {
         int var3 = 0;
         int var4 = var0.read();
         if((var4 & 127) == 0) {
            throw new IOException("corrupted stream - invalid high tag number found");
         }

         while(var4 >= 0 && (var4 & 128) != 0) {
            int var5 = var4 & 127;
            var3 = (var3 | var5) << 7;
            var4 = var0.read();
         }

         if(var4 < 0) {
            throw new EOFException("EOF found inside tag value.");
         }

         int var6 = var4 & 127;
         var2 = var3 | var6;
      }

      return var2;
   }

   ASN1EncodableVector buildDEREncodableVector(DefiniteLengthInputStream var1) throws IOException {
      return (new ASN1InputStream(var1)).buildEncodableVector();
   }

   ASN1EncodableVector buildEncodableVector() throws IOException {
      ASN1EncodableVector var1 = new ASN1EncodableVector();

      while(true) {
         DERObject var2 = this.readObject();
         if(var2 == null) {
            return var1;
         }

         var1.add(var2);
      }
   }

   protected DERObject buildObject(int var1, int var2, int var3) throws IOException {
      byte var4;
      if((var1 & 32) != 0) {
         var4 = 1;
      } else {
         var4 = 0;
      }

      DefiniteLengthInputStream var5 = new DefiniteLengthInputStream(this, var3);
      Object var7;
      if((var1 & 64) != 0) {
         byte[] var6 = var5.toByteArray();
         var7 = new DERApplicationSpecific((boolean)var4, var2, var6);
      } else if((var1 & 128) != 0) {
         var7 = (new BERTaggedObjectParser(var1, var2, var5)).getDERObject();
      } else if(var4 != 0) {
         switch(var2) {
         case 4:
            Vector var9 = this.buildDEREncodableVector(var5).v;
            var7 = new BERConstructedOctetString(var9);
            break;
         case 8:
            ASN1EncodableVector var11 = this.buildDEREncodableVector(var5);
            var7 = new DERExternal(var11);
            break;
         case 16:
            if(this.lazyEvaluate) {
               byte[] var10 = var5.toByteArray();
               var7 = new LazyDERSequence(var10);
            } else {
               var7 = DERFactory.createSequence(this.buildDEREncodableVector(var5));
            }
            break;
         case 17:
            var7 = DERFactory.createSet(this.buildDEREncodableVector(var5), (boolean)0);
            break;
         default:
            byte[] var8 = var5.toByteArray();
            var7 = new DERUnknownTag((boolean)1, var2, var8);
         }
      } else {
         byte[] var12 = var5.toByteArray();
         var7 = createPrimitiveDERObject(var2, var12);
      }

      return (DERObject)var7;
   }

   protected void readFully(byte[] var1) throws IOException {
      int var2 = Streams.readFully(this, var1);
      int var3 = var1.length;
      if(var2 != var3) {
         throw new EOFException("EOF encountered in middle of object");
      }
   }

   protected int readLength() throws IOException {
      int var1 = this.limit;
      return readLength(this, var1);
   }

   public DERObject readObject() throws IOException {
      int var1 = this.read();
      DERObject var2;
      if(var1 <= 0) {
         if(var1 == 0) {
            throw new IOException("unexpected end-of-contents marker");
         }

         var2 = null;
      } else {
         int var3 = readTagNumber(this, var1);
         boolean var4;
         if((var1 & 32) != 0) {
            var4 = true;
         } else {
            var4 = false;
         }

         int var5 = this.readLength();
         if(var5 < 0) {
            if(!var4) {
               throw new IOException("indefinite length primitive encoding encountered");
            }

            IndefiniteLengthInputStream var6 = new IndefiniteLengthInputStream(this);
            if((var1 & 64) != 0) {
               int var7 = this.limit;
               ASN1StreamParser var8 = new ASN1StreamParser(var6, var7);
               var2 = (new BERApplicationSpecificParser(var3, var8)).getDERObject();
            } else if((var1 & 128) != 0) {
               var2 = (new BERTaggedObjectParser(var1, var3, var6)).getDERObject();
            } else {
               int var9 = this.limit;
               ASN1StreamParser var10 = new ASN1StreamParser(var6, var9);
               switch(var3) {
               case 4:
                  var2 = (new BEROctetStringParser(var10)).getDERObject();
                  break;
               case 8:
                  var2 = (new DERExternalParser(var10)).getDERObject();
                  break;
               case 16:
                  var2 = (new BERSequenceParser(var10)).getDERObject();
                  break;
               case 17:
                  var2 = (new BERSetParser(var10)).getDERObject();
                  break;
               default:
                  throw new IOException("unknown BER object encountered");
               }
            }
         } else {
            var2 = this.buildObject(var1, var3, var5);
         }
      }

      return var2;
   }
}
