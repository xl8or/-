package com.android.email.mail.store.imap;

import com.android.email.Utility;
import com.android.email.mail.store.imap.ImapString;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class ImapSimpleString extends ImapString {

   private String mString;


   ImapSimpleString(String var1) {
      String var2;
      if(var1 != null) {
         var2 = var1;
      } else {
         var2 = "";
      }

      this.mString = var2;
   }

   public void destroy() {
      this.mString = null;
      super.destroy();
   }

   public InputStream getAsStream() {
      byte[] var1 = Utility.toAscii(this.mString);
      return new ByteArrayInputStream(var1);
   }

   public String getString() {
      return this.mString;
   }

   public String toString() {
      StringBuilder var1 = (new StringBuilder()).append("\"");
      String var2 = this.mString;
      return var1.append(var2).append("\"").toString();
   }
}
