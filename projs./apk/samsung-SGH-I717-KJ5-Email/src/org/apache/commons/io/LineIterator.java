package org.apache.commons.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.apache.commons.io.IOUtils;

public class LineIterator implements Iterator {

   private final BufferedReader bufferedReader;
   private String cachedLine;
   private boolean finished = 0;


   public LineIterator(Reader var1) throws IllegalArgumentException {
      if(var1 == null) {
         throw new IllegalArgumentException("Reader must not be null");
      } else if(var1 instanceof BufferedReader) {
         BufferedReader var2 = (BufferedReader)var1;
         this.bufferedReader = var2;
      } else {
         BufferedReader var3 = new BufferedReader(var1);
         this.bufferedReader = var3;
      }
   }

   public static void closeQuietly(LineIterator var0) {
      if(var0 != null) {
         var0.close();
      }
   }

   public void close() {
      this.finished = (boolean)1;
      IOUtils.closeQuietly((Reader)this.bufferedReader);
      this.cachedLine = null;
   }

   public boolean hasNext() {
      boolean var1;
      if(this.cachedLine != null) {
         var1 = true;
      } else if(this.finished) {
         var1 = false;
      } else {
         while(true) {
            try {
               String var2 = this.bufferedReader.readLine();
               if(var2 == null) {
                  this.finished = (boolean)1;
                  var1 = false;
                  break;
               }

               if(!this.isValidLine(var2)) {
                  continue;
               }

               this.cachedLine = var2;
            } catch (IOException var5) {
               this.close();
               String var4 = var5.toString();
               throw new IllegalStateException(var4);
            }

            var1 = true;
            break;
         }
      }

      return var1;
   }

   protected boolean isValidLine(String var1) {
      return true;
   }

   public Object next() {
      return this.nextLine();
   }

   public String nextLine() {
      if(!this.hasNext()) {
         throw new NoSuchElementException("No more lines");
      } else {
         String var1 = this.cachedLine;
         this.cachedLine = null;
         return var1;
      }
   }

   public void remove() {
      throw new UnsupportedOperationException("Remove unsupported on LineIterator");
   }
}
