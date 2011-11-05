package myorg.bouncycastle.cms;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import myorg.bouncycastle.cms.CMSException;
import myorg.bouncycastle.cms.CMSProcessable;

public class CMSProcessableFile implements CMSProcessable {

   private static final int DEFAULT_BUF_SIZE = 32768;
   private final byte[] _buf;
   private final File _file;


   public CMSProcessableFile(File var1) {
      this(var1, '\u8000');
   }

   public CMSProcessableFile(File var1, int var2) {
      this._file = var1;
      byte[] var3 = new byte[var2];
      this._buf = var3;
   }

   public Object getContent() {
      return this._file;
   }

   public void write(OutputStream var1) throws IOException, CMSException {
      File var2 = this._file;
      FileInputStream var3 = new FileInputStream(var2);

      while(true) {
         byte[] var4 = this._buf;
         int var5 = this._buf.length;
         int var6 = var3.read(var4, 0, var5);
         if(var6 <= 0) {
            var3.close();
            return;
         }

         byte[] var7 = this._buf;
         var1.write(var7, 0, var6);
      }
   }
}
