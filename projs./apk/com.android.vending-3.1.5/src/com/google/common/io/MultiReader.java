package com.google.common.io;

import com.google.common.base.Preconditions;
import com.google.common.io.InputSupplier;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

class MultiReader extends Reader {

   private Reader current;
   private final Iterator<? extends InputSupplier<? extends Reader>> it;


   MultiReader(Iterator<? extends InputSupplier<? extends Reader>> var1) throws IOException {
      this.it = var1;
      this.advance();
   }

   private void advance() throws IOException {
      this.close();
      if(this.it.hasNext()) {
         Reader var1 = (Reader)((InputSupplier)this.it.next()).getInput();
         this.current = var1;
      }
   }

   public void close() throws IOException {
      if(this.current != null) {
         try {
            this.current.close();
         } finally {
            this.current = null;
         }

      }
   }

   public int read(char[] var1, int var2, int var3) throws IOException {
      int var4;
      if(this.current == null) {
         var4 = -1;
      } else {
         var4 = this.current.read(var1, var2, var3);
         if(var4 == -1) {
            this.advance();
            var4 = this.read(var1, var2, var3);
         }
      }

      return var4;
   }

   public boolean ready() throws IOException {
      boolean var1;
      if(this.current != null && this.current.ready()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public long skip(long var1) throws IOException {
      byte var3;
      if(var1 >= 0L) {
         var3 = 1;
      } else {
         var3 = 0;
      }

      Preconditions.checkArgument((boolean)var3, "n is negative");
      long var4;
      if(var1 > 0L) {
         while(this.current != null) {
            var4 = this.current.skip(var1);
            if(var4 > 0L) {
               return var4;
            }

            this.advance();
         }
      }

      var4 = 0L;
      return var4;
   }
}
