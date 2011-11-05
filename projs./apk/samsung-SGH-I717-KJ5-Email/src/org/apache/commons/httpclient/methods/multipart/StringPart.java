package org.apache.commons.httpclient.methods.multipart;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.commons.httpclient.methods.multipart.PartBase;
import org.apache.commons.httpclient.util.EncodingUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StringPart extends PartBase {

   public static final String DEFAULT_CHARSET = "US-ASCII";
   public static final String DEFAULT_CONTENT_TYPE = "text/plain";
   public static final String DEFAULT_TRANSFER_ENCODING = "8bit";
   private static final Log LOG = LogFactory.getLog(StringPart.class);
   private byte[] content;
   private String value;


   public StringPart(String var1, String var2) {
      this(var1, var2, (String)null);
   }

   public StringPart(String var1, String var2, String var3) {
      String var4;
      if(var3 == null) {
         var4 = "US-ASCII";
      } else {
         var4 = var3;
      }

      super(var1, "text/plain", var4, "8bit");
      if(var2 == null) {
         throw new IllegalArgumentException("Value may not be null");
      } else if(var2.indexOf(0) != -1) {
         throw new IllegalArgumentException("NULs may not be present in string parts");
      } else {
         this.value = var2;
      }
   }

   private byte[] getContent() {
      if(this.content == null) {
         String var1 = this.value;
         String var2 = this.getCharSet();
         byte[] var3 = EncodingUtil.getBytes(var1, var2);
         this.content = var3;
      }

      return this.content;
   }

   protected long lengthOfData() throws IOException {
      LOG.trace("enter lengthOfData()");
      return (long)this.getContent().length;
   }

   protected void sendData(OutputStream var1) throws IOException {
      LOG.trace("enter sendData(OutputStream)");
      byte[] var2 = this.getContent();
      var1.write(var2);
   }

   public void setCharSet(String var1) {
      super.setCharSet(var1);
      this.content = null;
   }
}
