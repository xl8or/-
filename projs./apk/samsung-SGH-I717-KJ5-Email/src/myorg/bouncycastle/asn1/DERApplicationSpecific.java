package myorg.bouncycastle.asn1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1InputStream;
import myorg.bouncycastle.asn1.ASN1Object;
import myorg.bouncycastle.asn1.ASN1ParsingException;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DEROutputStream;
import myorg.bouncycastle.util.Arrays;

public class DERApplicationSpecific extends ASN1Object {

   private final boolean isConstructed;
   private final byte[] octets;
   private final int tag;


   public DERApplicationSpecific(int var1, ASN1EncodableVector var2) {
      this.tag = var1;
      this.isConstructed = (boolean)1;
      ByteArrayOutputStream var3 = new ByteArrayOutputStream();
      int var4 = 0;

      while(true) {
         int var5 = var2.size();
         if(var4 == var5) {
            byte[] var9 = var3.toByteArray();
            this.octets = var9;
            return;
         }

         try {
            byte[] var6 = ((ASN1Encodable)var2.get(var4)).getEncoded();
            var3.write(var6);
         } catch (IOException var10) {
            String var8 = "malformed object: " + var10;
            throw new ASN1ParsingException(var8, var10);
         }

         ++var4;
      }
   }

   public DERApplicationSpecific(int var1, DEREncodable var2) throws IOException {
      this((boolean)1, var1, var2);
   }

   public DERApplicationSpecific(int var1, byte[] var2) {
      this((boolean)0, var1, var2);
   }

   public DERApplicationSpecific(boolean var1, int var2, DEREncodable var3) throws IOException {
      byte[] var4 = var3.getDERObject().getDEREncoded();
      this.isConstructed = var1;
      this.tag = var2;
      if(var1) {
         this.octets = var4;
      } else {
         int var5 = this.getLengthOfLength(var4);
         byte[] var6 = new byte[var4.length - var5];
         int var7 = var6.length;
         System.arraycopy(var4, var5, var6, 0, var7);
         this.octets = var6;
      }
   }

   DERApplicationSpecific(boolean var1, int var2, byte[] var3) {
      this.isConstructed = var1;
      this.tag = var2;
      this.octets = var3;
   }

   private int getLengthOfLength(byte[] var1) {
      int var2 = 2;

      while(true) {
         int var3 = var2 - 1;
         if((var1[var3] & 128) == 0) {
            return var2;
         }

         ++var2;
      }
   }

   private byte[] replaceTagNumber(int var1, byte[] var2) throws IOException {
      int var3 = var2[0] & 31;
      int var4 = 1;
      if(var3 == 31) {
         int var5 = 0;
         int var6 = 1 + 1;
         int var7 = var2[1] & 255;
         if((var7 & 127) == 0) {
            throw new ASN1ParsingException("corrupted stream - invalid high tag number found");
         }

         while(var7 >= 0 && (var7 & 128) != 0) {
            int var8 = var7 & 127;
            var5 = (var5 | var8) << 7;
            int var9 = var6 + 1;
            var7 = var2[var6] & 255;
            var6 = var9;
         }

         int var10 = var7 & 127;
         int var10000 = var5 | var10;
         var4 = var6;
      }

      byte[] var12 = new byte[var2.length - var4 + 1];
      int var13 = var12.length - 1;
      System.arraycopy(var2, var4, var12, 1, var13);
      byte var14 = (byte)var1;
      var12[0] = var14;
      return var12;
   }

   boolean asn1Equals(DERObject var1) {
      boolean var2;
      if(!(var1 instanceof DERApplicationSpecific)) {
         var2 = false;
      } else {
         DERApplicationSpecific var3 = (DERApplicationSpecific)var1;
         boolean var4 = this.isConstructed;
         boolean var5 = var3.isConstructed;
         if(var4 == var5) {
            int var6 = this.tag;
            int var7 = var3.tag;
            if(var6 == var7) {
               byte[] var8 = this.octets;
               byte[] var9 = var3.octets;
               if(Arrays.areEqual(var8, var9)) {
                  var2 = true;
                  return var2;
               }
            }
         }

         var2 = false;
      }

      return var2;
   }

   void encode(DEROutputStream var1) throws IOException {
      int var2 = 64;
      if(this.isConstructed) {
         var2 = 64 | 32;
      }

      int var3 = this.tag;
      byte[] var4 = this.octets;
      var1.writeEncoded(var2, var3, var4);
   }

   public int getApplicationTag() {
      return this.tag;
   }

   public byte[] getContents() {
      return this.octets;
   }

   public DERObject getObject() throws IOException {
      byte[] var1 = this.getContents();
      return (new ASN1InputStream(var1)).readObject();
   }

   public DERObject getObject(int var1) throws IOException {
      if(var1 >= 31) {
         throw new IOException("unsupported tag number");
      } else {
         byte[] var2 = this.getEncoded();
         byte[] var3 = this.replaceTagNumber(var1, var2);
         if((var2[0] & 32) != 0) {
            byte var4 = (byte)(var3[0] | 32);
            var3[0] = var4;
         }

         return (new ASN1InputStream(var3)).readObject();
      }
   }

   public int hashCode() {
      byte var1;
      if(this.isConstructed) {
         var1 = 1;
      } else {
         var1 = 0;
      }

      int var2 = this.tag;
      int var3 = var1 ^ var2;
      int var4 = Arrays.hashCode(this.octets);
      return var3 ^ var4;
   }

   public boolean isConstructed() {
      return this.isConstructed;
   }
}
