package com.android.email.mail.internet;

import android.util.Base64;
import com.android.email.mail.Body;
import com.android.email.mail.MessagingException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public class TextBody implements Body {

   String mBody;


   public TextBody(String var1) {
      this.mBody = var1;
   }

   public InputStream getInputStream() throws MessagingException {
      ByteArrayInputStream var2;
      try {
         byte[] var1 = this.mBody.getBytes("UTF-8");
         var2 = new ByteArrayInputStream(var1);
      } catch (UnsupportedEncodingException var4) {
         var2 = null;
      }

      return var2;
   }

   public String getText() {
      return this.mBody;
   }

   public void writeTo(OutputStream var1) throws IOException, MessagingException {
      byte[] var2 = Base64.encode(this.mBody.getBytes("UTF-8"), 4);
      var1.write(var2);
   }
}
