package myorg.bouncycastle.cms;

import java.io.IOException;
import java.io.OutputStream;
import myorg.bouncycastle.cms.CMSException;
import myorg.bouncycastle.cms.CMSProcessable;

public class CMSProcessableByteArray implements CMSProcessable {

   private byte[] bytes;


   public CMSProcessableByteArray(byte[] var1) {
      this.bytes = var1;
   }

   public Object getContent() {
      return this.bytes.clone();
   }

   public void write(OutputStream var1) throws IOException, CMSException {
      byte[] var2 = this.bytes;
      var1.write(var2);
   }
}
