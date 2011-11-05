package org.apache.james.mime4j.message;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import org.apache.commons.io.IOUtils;
import org.apache.james.mime4j.Log;
import org.apache.james.mime4j.LogFactory;
import org.apache.james.mime4j.message.AbstractBody;
import org.apache.james.mime4j.message.TextBody;
import org.apache.james.mime4j.util.CharsetUtil;
import org.apache.james.mime4j.util.TempPath;
import org.apache.james.mime4j.util.TempStorage;

class MemoryTextBody extends AbstractBody implements TextBody {

   private static Log log = LogFactory.getLog(MemoryTextBody.class);
   private String mimeCharset;
   private byte[] tempFile;


   public MemoryTextBody(InputStream var1) throws IOException {
      this(var1, (String)null);
   }

   public MemoryTextBody(InputStream var1, String var2) throws IOException {
      this.mimeCharset = null;
      this.tempFile = null;
      this.mimeCharset = var2;
      TempPath var3 = TempStorage.getInstance().getRootTempPath();
      ByteArrayOutputStream var4 = new ByteArrayOutputStream();
      IOUtils.copy(var1, (OutputStream)var4);
      var4.close();
      byte[] var6 = var4.toByteArray();
      this.tempFile = var6;
   }

   public Reader getReader() throws UnsupportedEncodingException, IOException {
      String var1 = null;
      if(this.mimeCharset != null) {
         var1 = CharsetUtil.toJavaCharset(this.mimeCharset);
      }

      if(var1 == null) {
         var1 = "ISO-8859-1";
         if(log.isWarnEnabled()) {
            if(this.mimeCharset == null) {
               Log var2 = log;
               String var3 = "No MIME charset specified. Using " + var1 + " instead.";
               var2.warn(var3);
            } else {
               Log var6 = log;
               StringBuilder var7 = (new StringBuilder()).append("MIME charset \'");
               String var8 = this.mimeCharset;
               String var9 = var7.append(var8).append("\' has no ").append("corresponding Java charset. Using ").append(var1).append(" instead.").toString();
               var6.warn(var9);
            }
         }
      }

      byte[] var4 = this.tempFile;
      ByteArrayInputStream var5 = new ByteArrayInputStream(var4);
      return new InputStreamReader(var5, var1);
   }

   public void writeTo(OutputStream var1) throws IOException {
      byte[] var2 = this.tempFile;
      int var3 = IOUtils.copy((InputStream)(new ByteArrayInputStream(var2)), var1);
   }
}
