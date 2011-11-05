package myorg.bouncycastle.crypto.tls;

import java.io.IOException;
import java.io.OutputStream;
import myorg.bouncycastle.crypto.digests.SHA1Digest;
import myorg.bouncycastle.crypto.engines.AESFastEngine;
import myorg.bouncycastle.crypto.engines.DESedeEngine;
import myorg.bouncycastle.crypto.modes.CBCBlockCipher;
import myorg.bouncycastle.crypto.tls.TlsBlockCipherCipherSuite;
import myorg.bouncycastle.crypto.tls.TlsCipherSuite;
import myorg.bouncycastle.crypto.tls.TlsProtocolHandler;
import myorg.bouncycastle.crypto.tls.TlsUtils;

public class TlsCipherSuiteManager {

   private static final int TLS_DHE_DSS_WITH_3DES_EDE_CBC_SHA = 19;
   private static final int TLS_DHE_DSS_WITH_AES_128_CBC_SHA = 50;
   private static final int TLS_DHE_DSS_WITH_AES_256_CBC_SHA = 56;
   private static final int TLS_DHE_RSA_WITH_3DES_EDE_CBC_SHA = 22;
   private static final int TLS_DHE_RSA_WITH_AES_128_CBC_SHA = 51;
   private static final int TLS_DHE_RSA_WITH_AES_256_CBC_SHA = 57;
   private static final int TLS_RSA_WITH_3DES_EDE_CBC_SHA = 10;
   private static final int TLS_RSA_WITH_AES_128_CBC_SHA = 47;
   private static final int TLS_RSA_WITH_AES_256_CBC_SHA = 53;


   public TlsCipherSuiteManager() {}

   private static CBCBlockCipher createAESCipher() {
      AESFastEngine var0 = new AESFastEngine();
      return new CBCBlockCipher(var0);
   }

   private static TlsCipherSuite createAESCipherSuite(int var0, short var1) {
      CBCBlockCipher var2 = createAESCipher();
      CBCBlockCipher var3 = createAESCipher();
      SHA1Digest var4 = new SHA1Digest();
      SHA1Digest var5 = new SHA1Digest();
      return new TlsBlockCipherCipherSuite(var2, var3, var4, var5, var0, var1);
   }

   private static CBCBlockCipher createDESedeCipher() {
      DESedeEngine var0 = new DESedeEngine();
      return new CBCBlockCipher(var0);
   }

   private static TlsCipherSuite createDESedeCipherSuite(int var0, short var1) {
      CBCBlockCipher var2 = createDESedeCipher();
      CBCBlockCipher var3 = createDESedeCipher();
      SHA1Digest var4 = new SHA1Digest();
      SHA1Digest var5 = new SHA1Digest();
      return new TlsBlockCipherCipherSuite(var2, var3, var4, var5, var0, var1);
   }

   protected static TlsCipherSuite getCipherSuite(int var0, TlsProtocolHandler var1) throws IOException {
      TlsCipherSuite var2;
      switch(var0) {
      case 10:
         var2 = createDESedeCipherSuite(24, (short)1);
         break;
      case 19:
         var2 = createDESedeCipherSuite(24, (short)3);
         break;
      case 22:
         var2 = createDESedeCipherSuite(24, (short)5);
         break;
      case 47:
         var2 = createAESCipherSuite(16, (short)1);
         break;
      case 50:
         var2 = createAESCipherSuite(16, (short)3);
         break;
      case 51:
         var2 = createAESCipherSuite(16, (short)5);
         break;
      case 53:
         var2 = createAESCipherSuite(32, (short)1);
         break;
      case 56:
         var2 = createAESCipherSuite(32, (short)3);
         break;
      case 57:
         var2 = createAESCipherSuite(32, (short)5);
         break;
      default:
         var1.failWithError((short)2, (short)40);
         var2 = null;
      }

      return var2;
   }

   protected static void writeCipherSuites(OutputStream var0) throws IOException {
      int[] var1 = new int[]{57, 56, 51, 50, 22, 19, 53, 47, 10};
      TlsUtils.writeUint16(var1.length * 2, var0);
      int var2 = 0;

      while(true) {
         int var3 = var1.length;
         if(var2 >= var3) {
            return;
         }

         TlsUtils.writeUint16(var1[var2], var0);
         ++var2;
      }
   }
}
