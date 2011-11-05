package myorg.bouncycastle.sasn1;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import myorg.bouncycastle.sasn1.Asn1Integer;
import myorg.bouncycastle.sasn1.Asn1Null;
import myorg.bouncycastle.sasn1.Asn1Object;
import myorg.bouncycastle.sasn1.Asn1ObjectIdentifier;
import myorg.bouncycastle.sasn1.Asn1TaggedObject;
import myorg.bouncycastle.sasn1.BerOctetString;
import myorg.bouncycastle.sasn1.BerSequence;
import myorg.bouncycastle.sasn1.BerSet;
import myorg.bouncycastle.sasn1.DefiniteLengthInputStream;
import myorg.bouncycastle.sasn1.DerOctetString;
import myorg.bouncycastle.sasn1.DerSequence;
import myorg.bouncycastle.sasn1.DerSet;
import myorg.bouncycastle.sasn1.IndefiniteLengthInputStream;

public class Asn1InputStream {

   private boolean _eofFound;
   InputStream _in;
   private int _limit;


   public Asn1InputStream(InputStream var1) {
      this._in = var1;
      this._limit = Integer.MAX_VALUE;
   }

   public Asn1InputStream(InputStream var1, int var2) {
      this._in = var1;
      this._limit = var2;
   }

   public Asn1InputStream(byte[] var1) {
      ByteArrayInputStream var2 = new ByteArrayInputStream(var1);
      this._in = var2;
      int var3 = var1.length;
      this._limit = var3;
   }

   private int readLength() throws IOException {
      int var1 = this._in.read();
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
                  int var5 = this._in.read();
                  if(var5 < 0) {
                     throw new IOException("EOF found reading length");
                  }

                  int var6 = (var1 << 8) + var5;
               }

               if(var1 < 0) {
                  throw new IOException("corrupted stream - negative length found");
               }

               int var7 = this._limit;
               if(var1 >= var7) {
                  throw new IOException("corrupted stream - out of bounds length found");
               }
            }

            var2 = var1;
         }

         return var2;
      }
   }

   InputStream getParentStream() {
      return this._in;
   }

   public Asn1Object readObject() throws IOException {
      int var1 = this._in.read();
      Object var2;
      if(var1 == -1) {
         if(this._eofFound) {
            throw new EOFException("attempt to read past end of file.");
         }

         this._eofFound = (boolean)1;
         var2 = false;
      } else {
         if(this._in instanceof IndefiniteLengthInputStream) {
            ((IndefiniteLengthInputStream)this._in).setEofOn00((boolean)0);
         }

         int var3 = var1 & -33;
         int var4 = var3;
         if((var1 & 128) != 0) {
            var4 = var1 & 31;
            if(var4 == 31) {
               int var5 = 0;

               int var6;
               for(var6 = this._in.read(); var6 >= 0 && (var6 & 128) != 0; var6 = this._in.read()) {
                  int var7 = var6 & 127;
                  var5 = (var5 | var7) << 7;
               }

               if(var6 < 0) {
                  this._eofFound = (boolean)1;
                  throw new EOFException("EOF encountered inside tag value.");
               }

               int var8 = var6 & 127;
               var4 = var5 | var8;
            }
         }

         int var9 = this.readLength();
         if(var9 < 0) {
            InputStream var10 = this._in;
            IndefiniteLengthInputStream var11 = new IndefiniteLengthInputStream(var10);
            switch(var3) {
            case 4:
               var2 = new BerOctetString(var1, var11);
               break;
            case 5:
               var2 = new Asn1Null(var1);
               break;
            case 16:
               var2 = new BerSequence(var1, var11);
               break;
            case 17:
               var2 = new BerSet(var1, var11);
               break;
            default:
               var2 = new Asn1TaggedObject(var1, var4, var11);
            }
         } else {
            InputStream var12 = this._in;
            DefiniteLengthInputStream var13 = new DefiniteLengthInputStream(var12, var9);
            switch(var3) {
            case 2:
               byte[] var14 = var13.toByteArray();
               var2 = new Asn1Integer(var1, var14);
               break;
            case 4:
               byte[] var16 = var13.toByteArray();
               var2 = new DerOctetString(var1, var16);
               break;
            case 5:
               var2 = new Asn1Null(var1);
               break;
            case 6:
               byte[] var15 = var13.toByteArray();
               var2 = new Asn1ObjectIdentifier(var1, var15);
               break;
            case 16:
               byte[] var17 = var13.toByteArray();
               var2 = new DerSequence(var1, var17);
               break;
            case 17:
               byte[] var18 = var13.toByteArray();
               var2 = new DerSet(var1, var18);
               break;
            default:
               var2 = new Asn1TaggedObject(var1, var4, var13);
            }
         }
      }

      return (Asn1Object)var2;
   }
}
