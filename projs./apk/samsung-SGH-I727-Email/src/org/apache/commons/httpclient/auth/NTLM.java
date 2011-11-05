package org.apache.commons.httpclient.auth;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.auth.AuthenticationException;
import org.apache.commons.httpclient.util.EncodingUtil;

final class NTLM {

   public static final String DEFAULT_CHARSET = "ASCII";
   private String credentialCharset = "ASCII";
   private int currentPosition = 0;
   private byte[] currentResponse;


   NTLM() {}

   private void addByte(byte var1) {
      byte[] var2 = this.currentResponse;
      int var3 = this.currentPosition;
      var2[var3] = var1;
      int var4 = this.currentPosition + 1;
      this.currentPosition = var4;
   }

   private void addBytes(byte[] var1) {
      int var2 = 0;

      while(true) {
         int var3 = var1.length;
         if(var2 >= var3) {
            return;
         }

         byte[] var4 = this.currentResponse;
         int var5 = this.currentPosition;
         byte var6 = var1[var2];
         var4[var5] = var6;
         int var7 = this.currentPosition + 1;
         this.currentPosition = var7;
         ++var2;
      }
   }

   private void calcResp(byte[] var1, byte[] var2, byte[] var3) throws AuthenticationException {
      byte[] var4 = new byte[7];
      byte[] var5 = new byte[7];
      byte[] var6 = new byte[7];

      for(int var7 = 0; var7 < 7; ++var7) {
         byte var8 = var1[var7];
         var4[var7] = var8;
      }

      for(int var9 = 0; var9 < 7; ++var9) {
         int var10 = var9 + 7;
         byte var11 = var1[var10];
         var5[var9] = var11;
      }

      for(int var12 = 0; var12 < 7; ++var12) {
         int var13 = var12 + 14;
         byte var14 = var1[var13];
         var6[var12] = var14;
      }

      byte[] var15 = this.encrypt(var4, var2);
      byte[] var16 = this.encrypt(var5, var2);
      byte[] var17 = this.encrypt(var6, var2);

      for(int var18 = 0; var18 < 8; ++var18) {
         byte var19 = var15[var18];
         var3[var18] = var19;
      }

      for(int var20 = 0; var20 < 8; ++var20) {
         int var21 = var20 + 8;
         byte var22 = var16[var20];
         var3[var21] = var22;
      }

      for(int var23 = 0; var23 < 8; ++var23) {
         int var24 = var23 + 16;
         byte var25 = var17[var23];
         var3[var24] = var25;
      }

   }

   private byte[] convertShort(int var1) {
      byte[] var2 = new byte[2];
      String var3 = Integer.toString(var1, 16);

      StringBuffer var4;
      StringBuffer var5;
      for(var4 = new StringBuffer(var3); var4.length() < 4; var5 = var4.insert(0, "0")) {
         ;
      }

      String var6 = var4.substring(2, 4);
      String var7 = var4.substring(0, 2);
      byte var8 = (byte)Integer.parseInt(var6, 16);
      var2[0] = var8;
      byte var9 = (byte)Integer.parseInt(var7, 16);
      var2[1] = var9;
      return var2;
   }

   private byte[] encrypt(byte[] var1, byte[] var2) throws AuthenticationException {
      Cipher var3 = this.getCipher(var1);

      try {
         byte[] var4 = var3.doFinal(var2);
         return var4;
      } catch (IllegalBlockSizeException var7) {
         throw new AuthenticationException("Invalid block size for DES encryption.", var7);
      } catch (BadPaddingException var8) {
         throw new AuthenticationException("Data not padded correctly for DES encryption.", var8);
      }
   }

   private Cipher getCipher(byte[] var1) throws AuthenticationException {
      try {
         Cipher var2 = Cipher.getInstance("DES/ECB/NoPadding");
         byte[] var3 = this.setupKey(var1);
         SecretKeySpec var4 = new SecretKeySpec(var3, "DES");
         var2.init(1, var4);
         return var2;
      } catch (NoSuchAlgorithmException var8) {
         throw new AuthenticationException("DES encryption is not available.", var8);
      } catch (InvalidKeyException var9) {
         throw new AuthenticationException("Invalid key for DES encryption.", var9);
      } catch (NoSuchPaddingException var10) {
         throw new AuthenticationException("NoPadding option for DES is not available.", var10);
      }
   }

   private String getResponse() {
      int var1 = this.currentResponse.length;
      int var2 = this.currentPosition;
      byte[] var7;
      if(var1 > var2) {
         byte[] var3 = new byte[this.currentPosition];
         int var4 = 0;

         while(true) {
            int var5 = this.currentPosition;
            if(var4 >= var5) {
               var7 = var3;
               break;
            }

            byte var6 = this.currentResponse[var4];
            var3[var4] = var6;
            ++var4;
         }
      } else {
         var7 = this.currentResponse;
      }

      return EncodingUtil.getAsciiString(Base64.encodeBase64(var7));
   }

   private byte[] hashPassword(String var1, byte[] var2) throws AuthenticationException {
      String var3 = var1.toUpperCase();
      String var4 = this.credentialCharset;
      byte[] var5 = EncodingUtil.getBytes(var3, var4);
      byte[] var6 = new byte[7];
      byte[] var7 = new byte[7];
      int var8 = var5.length;
      if(var8 > 7) {
         var8 = 7;
      }

      int var9;
      for(var9 = 0; var9 < var8; ++var9) {
         byte var10 = var5[var9];
         var6[var9] = var10;
      }

      while(var9 < 7) {
         var6[var9] = 0;
         ++var9;
      }

      int var11 = var5.length;
      if(var11 > 14) {
         var11 = 14;
      }

      int var12;
      for(var12 = 7; var12 < var11; ++var12) {
         int var13 = var12 - 7;
         byte var14 = var5[var12];
         var7[var13] = var14;
      }

      while(var12 < 14) {
         int var15 = var12 - 7;
         var7[var15] = 0;
         ++var12;
      }

      byte[] var16 = new byte[]{(byte)75, (byte)71, (byte)83, (byte)33, (byte)64, (byte)35, (byte)36, (byte)37};
      byte[] var17 = this.encrypt(var6, var16);
      byte[] var18 = this.encrypt(var7, var16);
      byte[] var19 = new byte[21];
      int var20 = 0;

      while(true) {
         int var21 = var17.length;
         if(var20 >= var21) {
            int var23 = 0;

            while(true) {
               int var24 = var18.length;
               if(var23 >= var24) {
                  for(int var27 = 0; var27 < 5; ++var27) {
                     int var28 = var27 + 16;
                     var19[var28] = 0;
                  }

                  byte[] var29 = new byte[24];
                  this.calcResp(var19, var2, var29);
                  return var29;
               }

               int var25 = var23 + 8;
               byte var26 = var18[var23];
               var19[var25] = var26;
               ++var23;
            }
         }

         byte var22 = var17[var20];
         var19[var20] = var22;
         ++var20;
      }
   }

   private void prepareResponse(int var1) {
      byte[] var2 = new byte[var1];
      this.currentResponse = var2;
      this.currentPosition = 0;
   }

   private byte[] setupKey(byte[] var1) {
      byte[] var2 = new byte[8];
      byte var3 = (byte)(var1[0] >> 1 & 255);
      var2[0] = var3;
      int var4 = (var1[0] & 1) << 6;
      int var5 = (var1[1] & 255) >> 2 & 255;
      byte var6 = (byte)((var4 | var5) & 255);
      var2[1] = var6;
      int var7 = (var1[1] & 3) << 5;
      int var8 = (var1[2] & 255) >> 3 & 255;
      byte var9 = (byte)((var7 | var8) & 255);
      var2[2] = var9;
      int var10 = (var1[2] & 7) << 4;
      int var11 = (var1[3] & 255) >> 4 & 255;
      byte var12 = (byte)((var10 | var11) & 255);
      var2[3] = var12;
      int var13 = (var1[3] & 15) << 3;
      int var14 = (var1[4] & 255) >> 5 & 255;
      byte var15 = (byte)((var13 | var14) & 255);
      var2[4] = var15;
      int var16 = (var1[4] & 31) << 2;
      int var17 = (var1[5] & 255) >> 6 & 255;
      byte var18 = (byte)((var16 | var17) & 255);
      var2[5] = var18;
      int var19 = (var1[5] & 63) << 1;
      int var20 = (var1[6] & 255) >> 7 & 255;
      byte var21 = (byte)((var19 | var20) & 255);
      var2[6] = var21;
      byte var22 = (byte)(var1[6] & 127);
      var2[7] = var22;
      int var23 = 0;

      while(true) {
         int var24 = var2.length;
         if(var23 >= var24) {
            return var2;
         }

         byte var25 = (byte)(var2[var23] << 1);
         var2[var23] = var25;
         ++var23;
      }
   }

   public String getCredentialCharset() {
      return this.credentialCharset;
   }

   public final String getResponseFor(String var1, String var2, String var3, String var4, String var5) throws AuthenticationException {
      String var6;
      if(var1 != null && var1.trim().length() != 0) {
         byte[] var7 = this.parseType2Message(var1);
         var6 = this.getType3Message(var2, var3, var4, var5, var7);
      } else {
         var6 = this.getType1Message(var4, var5);
      }

      return var6;
   }

   public String getType1Message(String var1, String var2) {
      String var3 = var1.toUpperCase();
      String var4 = var2.toUpperCase();
      byte[] var5 = EncodingUtil.getBytes(var3, "ASCII");
      byte[] var6 = EncodingUtil.getBytes(var4, "ASCII");
      int var7 = var5.length + 32;
      int var8 = var6.length;
      int var9 = var7 + var8;
      this.prepareResponse(var9);
      byte[] var10 = EncodingUtil.getBytes("NTLMSSP", "ASCII");
      this.addBytes(var10);
      this.addByte((byte)0);
      this.addByte((byte)1);
      this.addByte((byte)0);
      this.addByte((byte)0);
      this.addByte((byte)0);
      this.addByte((byte)6);
      this.addByte((byte)82);
      this.addByte((byte)0);
      this.addByte((byte)0);
      int var11 = var6.length;
      byte[] var12 = this.convertShort(var11);
      byte var13 = var12[0];
      this.addByte(var13);
      byte var14 = var12[1];
      this.addByte(var14);
      byte var15 = var12[0];
      this.addByte(var15);
      byte var16 = var12[1];
      this.addByte(var16);
      int var17 = var5.length + 32;
      byte[] var18 = this.convertShort(var17);
      byte var19 = var18[0];
      this.addByte(var19);
      byte var20 = var18[1];
      this.addByte(var20);
      this.addByte((byte)0);
      this.addByte((byte)0);
      int var21 = var5.length;
      byte[] var22 = this.convertShort(var21);
      byte var23 = var22[0];
      this.addByte(var23);
      byte var24 = var22[1];
      this.addByte(var24);
      byte var25 = var22[0];
      this.addByte(var25);
      byte var26 = var22[1];
      this.addByte(var26);
      byte[] var27 = this.convertShort(32);
      byte var28 = var27[0];
      this.addByte(var28);
      byte var29 = var27[1];
      this.addByte(var29);
      this.addByte((byte)0);
      this.addByte((byte)0);
      this.addBytes(var5);
      this.addBytes(var6);
      return this.getResponse();
   }

   public String getType3Message(String var1, String var2, String var3, String var4, byte[] var5) throws AuthenticationException {
      String var6 = var4.toUpperCase();
      String var7 = var3.toUpperCase();
      String var8 = var1.toUpperCase();
      String var10 = "ASCII";
      byte[] var11 = EncodingUtil.getBytes(var6, var10);
      String var13 = "ASCII";
      byte[] var14 = EncodingUtil.getBytes(var7, var13);
      String var15 = this.credentialCharset;
      byte[] var18 = EncodingUtil.getBytes(var8, var15);
      int var19 = var11.length;
      int var20 = var14.length;
      int var21 = var18.length;
      int var22 = 0 + 64;
      int var23 = 24 + 64;
      int var24 = var19 + 88 + var21 + var20;
      this.prepareResponse(var24);
      byte[] var27 = EncodingUtil.getBytes("NTLMSSP", "ASCII");
      this.addBytes(var27);
      byte var31 = 0;
      this.addByte(var31);
      byte var33 = 3;
      this.addByte(var33);
      byte var35 = 0;
      this.addByte(var35);
      byte var37 = 0;
      this.addByte(var37);
      byte var39 = 0;
      this.addByte(var39);
      byte var41 = 24;
      byte[] var42 = this.convertShort(var41);
      this.addBytes(var42);
      byte var46 = 24;
      byte[] var47 = this.convertShort(var46);
      this.addBytes(var47);
      int var50 = var24 - 24;
      byte[] var53 = this.convertShort(var50);
      this.addBytes(var53);
      byte var57 = 0;
      this.addByte(var57);
      byte var59 = 0;
      this.addByte(var59);
      byte var61 = 0;
      byte[] var62 = this.convertShort(var61);
      this.addBytes(var62);
      byte var66 = 0;
      byte[] var67 = this.convertShort(var66);
      this.addBytes(var67);
      byte[] var72 = this.convertShort(var24);
      this.addBytes(var72);
      byte var76 = 0;
      this.addByte(var76);
      byte var78 = 0;
      this.addByte(var78);
      byte[] var81 = this.convertShort(var19);
      this.addBytes(var81);
      byte[] var86 = this.convertShort(var19);
      this.addBytes(var86);
      byte var90 = 64;
      byte[] var91 = this.convertShort(var90);
      this.addBytes(var91);
      byte var95 = 0;
      this.addByte(var95);
      byte var97 = 0;
      this.addByte(var97);
      byte[] var100 = this.convertShort(var21);
      this.addBytes(var100);
      byte[] var105 = this.convertShort(var21);
      this.addBytes(var105);
      int var108 = var19 + 64;
      byte[] var111 = this.convertShort(var108);
      this.addBytes(var111);
      byte var115 = 0;
      this.addByte(var115);
      byte var117 = 0;
      this.addByte(var117);
      byte[] var120 = this.convertShort(var20);
      this.addBytes(var120);
      byte[] var125 = this.convertShort(var20);
      this.addBytes(var125);
      int var128 = var19 + 64 + var21;
      byte[] var131 = this.convertShort(var128);
      this.addBytes(var131);

      for(int var134 = 0; var134 < 6; ++var134) {
         byte var136 = 0;
         this.addByte(var136);
      }

      byte[] var139 = this.convertShort(var24);
      this.addBytes(var139);
      byte var143 = 0;
      this.addByte(var143);
      byte var145 = 0;
      this.addByte(var145);
      byte var147 = 6;
      this.addByte(var147);
      byte var149 = 82;
      this.addByte(var149);
      byte var151 = 0;
      this.addByte(var151);
      byte var153 = 0;
      this.addByte(var153);
      this.addBytes(var11);
      this.addBytes(var18);
      this.addBytes(var14);
      byte[] var163 = this.hashPassword(var2, var5);
      this.addBytes(var163);
      return this.getResponse();
   }

   public byte[] parseType2Message(String var1) {
      byte[] var2 = Base64.decodeBase64(EncodingUtil.getBytes(var1, "ASCII"));
      byte[] var3 = new byte[8];

      for(int var4 = 0; var4 < 8; ++var4) {
         int var5 = var4 + 24;
         byte var6 = var2[var5];
         var3[var4] = var6;
      }

      return var3;
   }

   public void setCredentialCharset(String var1) {
      this.credentialCharset = var1;
   }
}
