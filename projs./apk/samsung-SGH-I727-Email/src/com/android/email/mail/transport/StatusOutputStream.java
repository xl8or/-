package com.android.email.mail.transport;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class StatusOutputStream extends FilterOutputStream {

   private long mCount = 0L;


   public StatusOutputStream(OutputStream var1) {
      super(var1);
   }

   public void write(int var1) throws IOException {
      super.write(var1);
      long var2 = this.mCount + 1L;
      this.mCount = var2;
   }
}
