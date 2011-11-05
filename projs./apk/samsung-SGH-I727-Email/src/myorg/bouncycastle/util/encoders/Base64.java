package myorg.bouncycastle.util.encoders;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import myorg.bouncycastle.util.encoders.Base64Encoder;
import myorg.bouncycastle.util.encoders.Encoder;

public class Base64 {

   private static final Encoder encoder = new Base64Encoder();


   public Base64() {}

   public static int decode(String var0, OutputStream var1) throws IOException {
      return encoder.decode(var0, var1);
   }

   public static byte[] decode(String var0) {
      int var1 = var0.length() / 4 * 3;
      ByteArrayOutputStream var2 = new ByteArrayOutputStream(var1);

      try {
         encoder.decode(var0, var2);
      } catch (IOException var6) {
         String var5 = "exception decoding base64 string: " + var6;
         throw new RuntimeException(var5);
      }

      return var2.toByteArray();
   }

   public static byte[] decode(byte[] var0) {
      int var1 = var0.length / 4 * 3;
      ByteArrayOutputStream var2 = new ByteArrayOutputStream(var1);

      try {
         Encoder var3 = encoder;
         int var4 = var0.length;
         var3.decode(var0, 0, var4, var2);
      } catch (IOException var8) {
         String var7 = "exception decoding base64 string: " + var8;
         throw new RuntimeException(var7);
      }

      return var2.toByteArray();
   }

   public static int encode(byte[] var0, int var1, int var2, OutputStream var3) throws IOException {
      return encoder.encode(var0, var1, var2, var3);
   }

   public static int encode(byte[] var0, OutputStream var1) throws IOException {
      Encoder var2 = encoder;
      int var3 = var0.length;
      return var2.encode(var0, 0, var3, var1);
   }

   public static byte[] encode(byte[] var0) {
      int var1 = (var0.length + 2) / 3 * 4;
      ByteArrayOutputStream var2 = new ByteArrayOutputStream(var1);

      try {
         Encoder var3 = encoder;
         int var4 = var0.length;
         var3.encode(var0, 0, var4, var2);
      } catch (IOException var8) {
         String var7 = "exception encoding base64 string: " + var8;
         throw new RuntimeException(var7);
      }

      return var2.toByteArray();
   }
}
