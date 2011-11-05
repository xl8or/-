package com.android.exchange;

import java.io.IOException;
import java.io.InputStream;

public class MockParserStream extends InputStream {

   int[] array;
   int pos = 0;
   Object value;


   MockParserStream(int[] var1) {
      this.array = var1;
   }

   public Object getResult() {
      return this.value;
   }

   public int read() throws IOException {
      try {
         int[] var1 = this.array;
         int var2 = this.pos;
         int var3 = var2 + 1;
         this.pos = var3;
         int var4 = var1[var2];
         return var4;
      } catch (IndexOutOfBoundsException var6) {
         throw new IOException("End of stream");
      }
   }

   public void setResult(Object var1) {
      this.value = var1;
   }
}
