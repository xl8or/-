package myorg.bouncycastle.crypto.tls;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import myorg.bouncycastle.crypto.Digest;
import myorg.bouncycastle.crypto.digests.MD5Digest;
import myorg.bouncycastle.crypto.digests.SHA1Digest;
import myorg.bouncycastle.crypto.macs.HMac;
import myorg.bouncycastle.crypto.params.KeyParameter;
import myorg.bouncycastle.crypto.tls.TlsProtocolHandler;

public class TlsUtils {

   public TlsUtils() {}

   protected static void PRF(byte[] var0, byte[] var1, byte[] var2, byte[] var3) {
      int var4 = (var0.length + 1) / 2;
      byte[] var5 = new byte[var4];
      byte[] var6 = new byte[var4];
      System.arraycopy(var0, 0, var5, 0, var4);
      int var7 = var0.length - var4;
      System.arraycopy(var0, var7, var6, 0, var4);
      int var8 = var1.length;
      int var9 = var2.length;
      byte[] var10 = new byte[var8 + var9];
      int var11 = var1.length;
      System.arraycopy(var1, 0, var10, 0, var11);
      int var12 = var1.length;
      int var13 = var2.length;
      System.arraycopy(var2, 0, var10, var12, var13);
      byte[] var14 = new byte[var3.length];
      hmac_hash(new MD5Digest(), var5, var10, var14);
      hmac_hash(new SHA1Digest(), var6, var10, var3);
      int var15 = 0;

      while(true) {
         int var16 = var3.length;
         if(var15 >= var16) {
            return;
         }

         byte var17 = var3[var15];
         byte var18 = var14[var15];
         byte var19 = (byte)(var17 ^ var18);
         var3[var15] = var19;
         ++var15;
      }
   }

   protected static void checkVersion(InputStream var0, TlsProtocolHandler var1) throws IOException {
      int var2 = var0.read();
      int var3 = var0.read();
      if(var2 != 3 || var3 != 1) {
         var1.failWithError((short)2, (short)70);
      }
   }

   protected static void checkVersion(byte[] var0, TlsProtocolHandler var1) throws IOException {
      if(var0[0] != 3 || var0[1] != 1) {
         var1.failWithError((short)2, (short)70);
      }
   }

   private static void hmac_hash(Digest var0, byte[] var1, byte[] var2, byte[] var3) {
      HMac var4 = new HMac(var0);
      KeyParameter var5 = new KeyParameter(var1);
      byte[] var6 = var2;
      int var7 = var0.getDigestSize();
      int var8 = (var3.length + var7 - 1) / var7;
      byte[] var9 = new byte[var4.getMacSize()];
      byte[] var10 = new byte[var4.getMacSize()];

      for(int var11 = 0; var11 < var8; ++var11) {
         var4.init(var5);
         int var12 = var6.length;
         var4.update(var6, 0, var12);
         var4.doFinal(var9, 0);
         var6 = var9;
         var4.init(var5);
         int var14 = var9.length;
         var4.update(var9, 0, var14);
         int var15 = var2.length;
         var4.update(var2, 0, var15);
         var4.doFinal(var10, 0);
         int var17 = var7 * var11;
         int var18 = var3.length;
         int var19 = var7 * var11;
         int var20 = var18 - var19;
         int var21 = Math.min(var7, var20);
         System.arraycopy(var10, 0, var3, var17, var21);
      }

   }

   protected static void readFully(byte[] var0, InputStream var1) throws IOException {
      int var2 = 0;

      while(true) {
         int var3 = var0.length;
         if(var2 == var3) {
            return;
         }

         int var4 = var0.length - var2;
         int var5 = var1.read(var0, var2, var4);
         if(var5 == -1) {
            throw new EOFException();
         }

         var2 += var5;
      }
   }

   protected static byte[] readOpaque16(InputStream var0) throws IOException {
      byte[] var1 = new byte[readUint16(var0)];
      readFully(var1, var0);
      return var1;
   }

   protected static byte[] readOpaque8(InputStream var0) throws IOException {
      byte[] var1 = new byte[readUint8(var0)];
      readFully(var1, var0);
      return var1;
   }

   protected static int readUint16(InputStream var0) throws IOException {
      int var1 = var0.read();
      int var2 = var0.read();
      if((var1 | var2) < 0) {
         throw new EOFException();
      } else {
         return var1 << 8 | var2;
      }
   }

   protected static int readUint24(InputStream var0) throws IOException {
      int var1 = var0.read();
      int var2 = var0.read();
      int var3 = var0.read();
      if((var1 | var2 | var3) < 0) {
         throw new EOFException();
      } else {
         int var4 = var1 << 16;
         int var5 = var2 << 8;
         return var4 | var5 | var3;
      }
   }

   protected static long readUint32(InputStream var0) throws IOException {
      int var1 = var0.read();
      int var2 = var0.read();
      int var3 = var0.read();
      int var4 = var0.read();
      if((var1 | var2 | var3 | var4) < 0) {
         throw new EOFException();
      } else {
         long var5 = (long)var1 << 24;
         long var7 = (long)var2 << 16;
         long var9 = var5 | var7;
         long var11 = (long)var3 << 8;
         long var13 = var9 | var11;
         long var15 = (long)var4;
         return var13 | var15;
      }
   }

   protected static short readUint8(InputStream var0) throws IOException {
      int var1 = var0.read();
      if(var1 == -1) {
         throw new EOFException();
      } else {
         return (short)var1;
      }
   }

   static byte[] toByteArray(String var0) {
      char[] var1 = var0.toCharArray();
      byte[] var2 = new byte[var1.length];
      int var3 = 0;

      while(true) {
         int var4 = var2.length;
         if(var3 == var4) {
            return var2;
         }

         byte var5 = (byte)var1[var3];
         var2[var3] = var5;
         ++var3;
      }
   }

   protected static void writeOpaque16(byte[] var0, OutputStream var1) throws IOException {
      writeUint16(var0.length, var1);
      var1.write(var0);
   }

   protected static void writeOpaque8(byte[] var0, OutputStream var1) throws IOException {
      writeUint8((short)var0.length, var1);
      var1.write(var0);
   }

   protected static void writeUint16(int var0, OutputStream var1) throws IOException {
      int var2 = var0 >> 8;
      var1.write(var2);
      var1.write(var0);
   }

   protected static void writeUint16(int var0, byte[] var1, int var2) {
      byte var3 = (byte)(var0 >> 8);
      var1[var2] = var3;
      int var4 = var2 + 1;
      byte var5 = (byte)var0;
      var1[var4] = var5;
   }

   protected static void writeUint24(int var0, OutputStream var1) throws IOException {
      int var2 = var0 >> 16;
      var1.write(var2);
      int var3 = var0 >> 8;
      var1.write(var3);
      var1.write(var0);
   }

   protected static void writeUint24(int var0, byte[] var1, int var2) {
      byte var3 = (byte)(var0 >> 16);
      var1[var2] = var3;
      int var4 = var2 + 1;
      byte var5 = (byte)(var0 >> 8);
      var1[var4] = var5;
      int var6 = var2 + 2;
      byte var7 = (byte)var0;
      var1[var6] = var7;
   }

   protected static void writeUint32(long var0, OutputStream var2) throws IOException {
      int var3 = (int)(var0 >> 24);
      var2.write(var3);
      int var4 = (int)(var0 >> 16);
      var2.write(var4);
      int var5 = (int)(var0 >> 8);
      var2.write(var5);
      int var6 = (int)var0;
      var2.write(var6);
   }

   protected static void writeUint32(long var0, byte[] var2, int var3) {
      byte var4 = (byte)((int)(var0 >> 24));
      var2[var3] = var4;
      int var5 = var3 + 1;
      byte var6 = (byte)((int)(var0 >> 16));
      var2[var5] = var6;
      int var7 = var3 + 2;
      byte var8 = (byte)((int)(var0 >> 8));
      var2[var7] = var8;
      int var9 = var3 + 3;
      byte var10 = (byte)((int)var0);
      var2[var9] = var10;
   }

   protected static void writeUint64(long var0, OutputStream var2) throws IOException {
      int var3 = (int)(var0 >> 56);
      var2.write(var3);
      int var4 = (int)(var0 >> 48);
      var2.write(var4);
      int var5 = (int)(var0 >> 40);
      var2.write(var5);
      int var6 = (int)(var0 >> 32);
      var2.write(var6);
      int var7 = (int)(var0 >> 24);
      var2.write(var7);
      int var8 = (int)(var0 >> 16);
      var2.write(var8);
      int var9 = (int)(var0 >> 8);
      var2.write(var9);
      int var10 = (int)var0;
      var2.write(var10);
   }

   protected static void writeUint64(long var0, byte[] var2, int var3) {
      byte var4 = (byte)((int)(var0 >> 56));
      var2[var3] = var4;
      int var5 = var3 + 1;
      byte var6 = (byte)((int)(var0 >> 48));
      var2[var5] = var6;
      int var7 = var3 + 2;
      byte var8 = (byte)((int)(var0 >> 40));
      var2[var7] = var8;
      int var9 = var3 + 3;
      byte var10 = (byte)((int)(var0 >> 32));
      var2[var9] = var10;
      int var11 = var3 + 4;
      byte var12 = (byte)((int)(var0 >> 24));
      var2[var11] = var12;
      int var13 = var3 + 5;
      byte var14 = (byte)((int)(var0 >> 16));
      var2[var13] = var14;
      int var15 = var3 + 6;
      byte var16 = (byte)((int)(var0 >> 8));
      var2[var15] = var16;
      int var17 = var3 + 7;
      byte var18 = (byte)((int)var0);
      var2[var17] = var18;
   }

   protected static void writeUint8(short var0, OutputStream var1) throws IOException {
      var1.write(var0);
   }

   protected static void writeUint8(short var0, byte[] var1, int var2) {
      byte var3 = (byte)var0;
      var1[var2] = var3;
   }

   protected static void writeVersion(OutputStream var0) throws IOException {
      var0.write(3);
      var0.write(1);
   }
}
