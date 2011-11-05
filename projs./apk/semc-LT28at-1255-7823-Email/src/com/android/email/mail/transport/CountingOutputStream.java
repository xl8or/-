package com.android.email.mail.transport;

import java.io.IOException;
import java.io.OutputStream;

public class CountingOutputStream extends OutputStream {

   private long mCount;


   public CountingOutputStream() {}

   public long getCount() {
      return this.mCount;
   }

   public void write(int var1) throws IOException {
      long var2 = this.mCount + 1L;
      this.mCount = var2;
   }
}
