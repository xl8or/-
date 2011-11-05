package org.apache.james.mime4j.message;

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
import org.apache.james.mime4j.util.TempFile;
import org.apache.james.mime4j.util.TempStorage;

class TempFileTextBody extends AbstractBody implements TextBody {

   private static Log log = LogFactory.getLog(TempFileTextBody.class);
   private String mimeCharset;
   private TempFile tempFile;


   public TempFileTextBody(InputStream var1) throws IOException {
      this(var1, (String)null);
   }

   public TempFileTextBody(InputStream var1, String var2) throws IOException {
      this.mimeCharset = null;
      this.tempFile = null;
      this.mimeCharset = var2;
      TempFile var3 = TempStorage.getInstance().getRootTempPath().createTempFile("attachment", ".txt");
      this.tempFile = var3;
      OutputStream var4 = this.tempFile.getOutputStream();
      IOUtils.copy(var1, var4);
      var4.close();
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
               Log var5 = log;
               StringBuilder var6 = (new StringBuilder()).append("MIME charset \'");
               String var7 = this.mimeCharset;
               String var8 = var6.append(var7).append("\' has no ").append("corresponding Java charset. Using ").append(var1).append(" instead.").toString();
               var5.warn(var8);
            }
         }
      }

      InputStream var4 = this.tempFile.getInputStream();
      return new InputStreamReader(var4, var1);
   }

   public void writeTo(OutputStream var1) throws IOException {
      int var2 = IOUtils.copy(this.tempFile.getInputStream(), var1);
   }
}
