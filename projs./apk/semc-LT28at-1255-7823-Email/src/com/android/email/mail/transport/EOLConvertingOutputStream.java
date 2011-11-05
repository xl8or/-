package com.android.email.mail.transport;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class EOLConvertingOutputStream extends FilterOutputStream {

   int lastChar;


   public EOLConvertingOutputStream(OutputStream var1) {
      super(var1);
   }

   public void flush() throws IOException {
      if(this.lastChar == 13) {
         super.write(10);
         this.lastChar = 10;
      }

      super.flush();
   }

   public void write(int var1) throws IOException {
      if(var1 == 10 && this.lastChar != 13) {
         super.write(13);
      }

      super.write(var1);
      this.lastChar = var1;
   }
}
