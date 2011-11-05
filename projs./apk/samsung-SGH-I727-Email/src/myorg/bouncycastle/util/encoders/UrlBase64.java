package myorg.bouncycastle.util.encoders;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import myorg.bouncycastle.util.encoders.Encoder;
import myorg.bouncycastle.util.encoders.UrlBase64Encoder;

public class UrlBase64 {

   private static final Encoder encoder = new UrlBase64Encoder();


   public UrlBase64() {}

   public static int decode(String var0, OutputStream var1) throws IOException {
      return encoder.decode(var0, var1);
   }

   public static int decode(byte[] var0, OutputStream var1) throws IOException {
      Encoder var2 = encoder;
      int var3 = var0.length;
      return var2.decode(var0, 0, var3, var1);
   }

   public static byte[] decode(String var0) {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();

      try {
         encoder.decode(var0, var1);
      } catch (IOException var5) {
         String var4 = "exception decoding URL safe base64 string: " + var5;
         throw new RuntimeException(var4);
      }

      return var1.toByteArray();
   }

   public static byte[] decode(byte[] var0) {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();

      try {
         Encoder var2 = encoder;
         int var3 = var0.length;
         var2.decode(var0, 0, var3, var1);
      } catch (IOException var7) {
         String var6 = "exception decoding URL safe base64 string: " + var7;
         throw new RuntimeException(var6);
      }

      return var1.toByteArray();
   }

   public static int encode(byte[] var0, OutputStream var1) throws IOException {
      Encoder var2 = encoder;
      int var3 = var0.length;
      return var2.encode(var0, 0, var3, var1);
   }

   public static byte[] encode(byte[] var0) {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();

      try {
         Encoder var2 = encoder;
         int var3 = var0.length;
         var2.encode(var0, 0, var3, var1);
      } catch (IOException var7) {
         String var6 = "exception encoding URL safe base64 string: " + var7;
         throw new RuntimeException(var6);
      }

      return var1.toByteArray();
   }
}
