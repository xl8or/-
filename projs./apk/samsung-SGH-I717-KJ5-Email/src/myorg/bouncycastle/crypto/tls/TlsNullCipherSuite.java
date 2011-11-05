package myorg.bouncycastle.crypto.tls;

import myorg.bouncycastle.crypto.tls.TlsCipherSuite;
import myorg.bouncycastle.crypto.tls.TlsProtocolHandler;
import myorg.bouncycastle.crypto.tls.TlsRuntimeException;

public class TlsNullCipherSuite extends TlsCipherSuite {

   public TlsNullCipherSuite() {}

   protected byte[] decodeCiphertext(short var1, byte[] var2, int var3, int var4, TlsProtocolHandler var5) {
      byte[] var6 = new byte[var4];
      System.arraycopy(var2, var3, var6, 0, var4);
      return var6;
   }

   protected byte[] encodePlaintext(short var1, byte[] var2, int var3, int var4) {
      byte[] var5 = new byte[var4];
      System.arraycopy(var2, var3, var5, 0, var4);
      return var5;
   }

   protected short getKeyExchangeAlgorithm() {
      return (short)0;
   }

   protected void init(byte[] var1, byte[] var2, byte[] var3) {
      throw new TlsRuntimeException("Sorry, init of TLS_NULL_WITH_NULL_NULL is forbidden");
   }
}
