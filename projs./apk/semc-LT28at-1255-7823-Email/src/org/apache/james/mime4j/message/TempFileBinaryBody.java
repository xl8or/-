package org.apache.james.mime4j.message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.io.IOUtils;
import org.apache.james.mime4j.Log;
import org.apache.james.mime4j.LogFactory;
import org.apache.james.mime4j.message.AbstractBody;
import org.apache.james.mime4j.message.BinaryBody;
import org.apache.james.mime4j.message.Entity;
import org.apache.james.mime4j.util.TempFile;
import org.apache.james.mime4j.util.TempStorage;

class TempFileBinaryBody extends AbstractBody implements BinaryBody {

   private static Log log = LogFactory.getLog(TempFileBinaryBody.class);
   private Entity parent = null;
   private TempFile tempFile = null;


   public TempFileBinaryBody(InputStream var1) throws IOException {
      TempFile var2 = TempStorage.getInstance().getRootTempPath().createTempFile("attachment", ".bin");
      this.tempFile = var2;
      OutputStream var3 = this.tempFile.getOutputStream();
      IOUtils.copy(var1, var3);
      var3.close();
   }

   public InputStream getInputStream() throws IOException {
      return this.tempFile.getInputStream();
   }

   public Entity getParent() {
      return this.parent;
   }

   public void setParent(Entity var1) {
      this.parent = var1;
   }

   public void writeTo(OutputStream param1) throws IOException {
      // $FF: Couldn't be decompiled
   }
}
