package myorg.bouncycastle.crypto.tls;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import myorg.bouncycastle.crypto.Digest;
import myorg.bouncycastle.crypto.macs.HMac;
import myorg.bouncycastle.crypto.params.KeyParameter;
import myorg.bouncycastle.crypto.tls.TlsUtils;

public class TlsMac {

   private HMac mac;
   private long seqNo;


   protected TlsMac(Digest var1, byte[] var2, int var3, int var4) {
      HMac var5 = new HMac(var1);
      this.mac = var5;
      KeyParameter var6 = new KeyParameter(var2, var3, var4);
      this.mac.init(var6);
      this.seqNo = 0L;
   }

   protected byte[] calculateMac(short var1, byte[] var2, int var3, int var4) {
      try {
         ByteArrayOutputStream var5 = new ByteArrayOutputStream();
         long var6 = this.seqNo;
         long var8 = 1L + var6;
         this.seqNo = var8;
         TlsUtils.writeUint64(var6, var5);
         TlsUtils.writeUint8(var1, var5);
         TlsUtils.writeVersion(var5);
         TlsUtils.writeUint16(var4, var5);
         var5.write(var2, var3, var4);
         byte[] var10 = var5.toByteArray();
         HMac var11 = this.mac;
         int var12 = var10.length;
         var11.update(var10, 0, var12);
         byte[] var13 = new byte[this.mac.getMacSize()];
         this.mac.doFinal(var13, 0);
         this.mac.reset();
         return var13;
      } catch (IOException var16) {
         throw new IllegalStateException("Internal error during mac calculation");
      }
   }

   protected int getSize() {
      return this.mac.getMacSize();
   }
}
