package myorg.bouncycastle.util.encoders;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import myorg.bouncycastle.util.encoders.Encoder;
import myorg.bouncycastle.util.encoders.HexEncoder;

public class Hex {

   private static final Encoder encoder = new HexEncoder();


   public Hex() {}

   public static int decode(String var0, OutputStream var1) throws IOException {
      return encoder.decode(var0, var1);
   }

   public static byte[] decode(String var0) {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();

      try {
         encoder.decode(var0, var1);
      } catch (IOException var5) {
         String var4 = "exception decoding Hex string: " + var5;
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
         String var6 = "exception decoding Hex string: " + var7;
         throw new RuntimeException(var6);
      }

      return var1.toByteArray();
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
      int var1 = var0.length;
      return encode(var0, 0, var1);
   }

   public static byte[] encode(byte[] var0, int var1, int var2) {
      ByteArrayOutputStream var3 = new ByteArrayOutputStream();

      try {
         encoder.encode(var0, var1, var2, var3);
      } catch (IOException var7) {
         String var6 = "exception encoding Hex string: " + var7;
         throw new RuntimeException(var6);
      }

      return var3.toByteArray();
   }
}
