package org.apache.james.mime4j.message;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.james.mime4j.message.AbstractBody;
import org.apache.james.mime4j.message.BinaryBody;
import org.apache.james.mime4j.message.Entity;
import org.apache.james.mime4j.util.TempPath;
import org.apache.james.mime4j.util.TempStorage;

class MemoryBinaryBody extends AbstractBody implements BinaryBody {

   private static Log log = LogFactory.getLog(MemoryBinaryBody.class);
   private Entity parent = null;
   private byte[] tempFile = null;


   public MemoryBinaryBody(InputStream var1) throws IOException {
      TempPath var2 = TempStorage.getInstance().getRootTempPath();
      ByteArrayOutputStream var3 = new ByteArrayOutputStream();
      IOUtils.copy(var1, (OutputStream)var3);
      var3.close();
      byte[] var5 = var3.toByteArray();
      this.tempFile = var5;
   }

   public InputStream getInputStream() throws IOException {
      byte[] var1 = this.tempFile;
      return new ByteArrayInputStream(var1);
   }

   public Entity getParent() {
      return this.parent;
   }

   public void setParent(Entity var1) {
      this.parent = var1;
   }

   public void writeTo(OutputStream var1) throws IOException {
      int var2 = IOUtils.copy(this.getInputStream(), var1);
   }
}
