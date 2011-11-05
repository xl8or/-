package myorg.bouncycastle.crypto.tls;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import myorg.bouncycastle.crypto.tls.CombinedHash;
import myorg.bouncycastle.crypto.tls.TlsCipherSuite;
import myorg.bouncycastle.crypto.tls.TlsNullCipherSuite;
import myorg.bouncycastle.crypto.tls.TlsProtocolHandler;
import myorg.bouncycastle.crypto.tls.TlsUtils;

public class RecordStream {

   private TlsProtocolHandler handler;
   protected CombinedHash hash1;
   protected CombinedHash hash2;
   private InputStream is;
   private OutputStream os;
   protected TlsCipherSuite readSuite = null;
   protected TlsCipherSuite writeSuite = null;


   protected RecordStream(TlsProtocolHandler var1, InputStream var2, OutputStream var3) {
      this.handler = var1;
      this.is = var2;
      this.os = var3;
      CombinedHash var4 = new CombinedHash();
      this.hash1 = var4;
      CombinedHash var5 = new CombinedHash();
      this.hash2 = var5;
      TlsNullCipherSuite var6 = new TlsNullCipherSuite();
      this.readSuite = var6;
      TlsCipherSuite var7 = this.readSuite;
      this.writeSuite = var7;
   }

   protected void close() throws IOException {
      IOException var1 = null;

      try {
         this.is.close();
      } catch (IOException var3) {
         var1 = var3;
      }

      try {
         this.os.close();
      } catch (IOException var2) {
         var1 = var2;
      }

      if(var1 != null) {
         throw var1;
      }
   }

   protected byte[] decodeAndVerify(short var1, InputStream var2, int var3) throws IOException {
      byte[] var4 = new byte[var3];
      TlsUtils.readFully(var4, var2);
      TlsCipherSuite var5 = this.readSuite;
      int var6 = var4.length;
      TlsProtocolHandler var7 = this.handler;
      return var5.decodeCiphertext(var1, var4, 0, var6, var7);
   }

   protected void flush() throws IOException {
      this.os.flush();
   }

   public void readData() throws IOException {
      short var1 = TlsUtils.readUint8(this.is);
      InputStream var2 = this.is;
      TlsProtocolHandler var3 = this.handler;
      TlsUtils.checkVersion(var2, var3);
      int var4 = TlsUtils.readUint16(this.is);
      InputStream var5 = this.is;
      byte[] var6 = this.decodeAndVerify(var1, var5, var4);
      TlsProtocolHandler var7 = this.handler;
      int var8 = var6.length;
      var7.processData(var1, var6, 0, var8);
   }

   protected void writeMessage(short var1, byte[] var2, int var3, int var4) throws IOException {
      if(var1 == 22) {
         this.hash1.update(var2, var3, var4);
         this.hash2.update(var2, var3, var4);
      }

      byte[] var5 = this.writeSuite.encodePlaintext(var1, var2, var3, var4);
      byte[] var6 = new byte[var5.length + 5];
      TlsUtils.writeUint8(var1, var6, 0);
      TlsUtils.writeUint8((short)3, var6, 1);
      TlsUtils.writeUint8((short)1, var6, 2);
      TlsUtils.writeUint16(var5.length, var6, 3);
      int var7 = var5.length;
      System.arraycopy(var5, 0, var6, 5, var7);
      this.os.write(var6);
      this.os.flush();
   }
}
