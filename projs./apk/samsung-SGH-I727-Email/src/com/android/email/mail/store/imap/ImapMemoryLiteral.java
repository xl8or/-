package com.android.email.mail.store.imap;

import android.util.Log;
import com.android.email.FixedLengthInputStream;
import com.android.email.Utility;
import com.android.email.mail.store.imap.ImapString;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImapMemoryLiteral extends ImapString {

   private byte[] mData;


   ImapMemoryLiteral(FixedLengthInputStream var1) throws IOException {
      byte[] var2 = new byte[var1.getLength()];
      this.mData = var2;
      int var3 = 0;

      while(true) {
         int var4 = this.mData.length;
         if(var3 >= var4) {
            break;
         }

         byte[] var5 = this.mData;
         int var6 = this.mData.length - var3;
         int var7 = var1.read(var5, var3, var6);
         if(var7 < 0) {
            break;
         }

         var3 += var7;
      }

      int var8 = this.mData.length;
      if(var3 != var8) {
         int var9 = Log.w("Email", "");
      }
   }

   public void destroy() {
      this.mData = null;
      super.destroy();
   }

   public InputStream getAsStream() {
      byte[] var1 = this.mData;
      return new ByteArrayInputStream(var1);
   }

   public String getString() {
      return Utility.fromAscii(this.mData);
   }

   public String toString() {
      Object[] var1 = new Object[1];
      Integer var2 = Integer.valueOf(this.mData.length);
      var1[0] = var2;
      return String.format("{%d byte literal(memory)}", var1);
   }
}
