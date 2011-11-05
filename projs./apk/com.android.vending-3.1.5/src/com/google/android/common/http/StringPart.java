package com.google.android.common.http;

import com.google.android.common.http.PartBase;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.http.util.EncodingUtils;

public class StringPart extends PartBase {

   public static final String DEFAULT_CHARSET = "US-ASCII";
   public static final String DEFAULT_CONTENT_TYPE = "text/plain";
   public static final String DEFAULT_TRANSFER_ENCODING = "8bit";
   private byte[] content;
   private String value;


   public StringPart(String var1, String var2) {
      this(var1, var2, (String)null);
   }

   public StringPart(String var1, String var2, String var3) {
      if(var3 == null) {
         var3 = "US-ASCII";
      }

      super(var1, "text/plain", var3, "8bit");
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
         byte[] var3 = EncodingUtils.getBytes(var1, var2);
         this.content = var3;
      }

      return this.content;
   }

   protected long lengthOfData() {
      return (long)this.getContent().length;
   }

   protected void sendData(OutputStream var1) throws IOException {
      byte[] var2 = this.getContent();
      var1.write(var2);
   }

   public void setCharSet(String var1) {
      super.setCharSet(var1);
      this.content = null;
   }
}
