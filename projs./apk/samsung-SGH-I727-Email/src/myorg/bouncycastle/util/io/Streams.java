package myorg.bouncycastle.util.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import myorg.bouncycastle.util.io.StreamOverflowException;

public final class Streams {

   private static int BUFFER_SIZE = 512;


   public Streams() {}

   public static void drain(InputStream var0) throws IOException {
      byte[] var1 = new byte[BUFFER_SIZE];

      int var2;
      do {
         var2 = var1.length;
      } while(var0.read(var1, 0, var2) >= 0);

   }

   public static void pipeAll(InputStream var0, OutputStream var1) throws IOException {
      byte[] var2 = new byte[BUFFER_SIZE];

      while(true) {
         int var3 = var2.length;
         int var4 = var0.read(var2, 0, var3);
         if(var4 < 0) {
            return;
         }

         var1.write(var2, 0, var4);
      }
   }

   public static long pipeAllLimited(InputStream var0, long var1, OutputStream var3) throws IOException {
      long var4 = 0L;
      byte[] var6 = new byte[BUFFER_SIZE];

      while(true) {
         int var7 = var6.length;
         int var8 = var0.read(var6, 0, var7);
         if(var8 < 0) {
            return var4;
         }

         long var9 = (long)var8;
         var4 += var9;
         if(var4 > var1) {
            throw new StreamOverflowException("Data Overflow");
         }

         var3.write(var6, 0, var8);
      }
   }

   public static byte[] readAll(InputStream var0) throws IOException {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();
      pipeAll(var0, var1);
      return var1.toByteArray();
   }

   public static byte[] readAllLimited(InputStream var0, int var1) throws IOException {
      ByteArrayOutputStream var2 = new ByteArrayOutputStream();
      long var3 = (long)var1;
      pipeAllLimited(var0, var3, var2);
      return var2.toByteArray();
   }

   public static int readFully(InputStream var0, byte[] var1) throws IOException {
      int var2 = var1.length;
      return readFully(var0, var1, 0, var2);
   }

   public static int readFully(InputStream var0, byte[] var1, int var2, int var3) throws IOException {
      int var4;
      int var7;
      for(var4 = 0; var4 < var3; var4 += var7) {
         int var5 = var2 + var4;
         int var6 = var3 - var4;
         var7 = var0.read(var1, var5, var6);
         if(var7 < 0) {
            break;
         }
      }

      return var4;
   }
}
