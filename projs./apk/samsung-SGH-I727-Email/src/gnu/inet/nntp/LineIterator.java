package gnu.inet.nntp;

import gnu.inet.nntp.NNTPConnection;
import gnu.inet.nntp.PendingData;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LineIterator implements Iterator, PendingData {

   static final String DOT = ".";
   NNTPConnection connection;
   String current;
   boolean doneRead = 0;


   LineIterator(NNTPConnection var1) {
      this.connection = var1;
   }

   void doRead() throws IOException {
      if(!this.doneRead) {
         String var1 = this.connection.read();
         if(".".equals(var1)) {
            this.current = null;
         } else {
            this.current = var1;
         }

         this.doneRead = (boolean)1;
      }
   }

   public boolean hasNext() {
      boolean var1;
      try {
         this.doRead();
      } catch (IOException var3) {
         var1 = false;
         return var1;
      }

      if(this.current != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public Object next() {
      try {
         String var1 = this.nextLine();
         return var1;
      } catch (IOException var6) {
         StringBuilder var3 = (new StringBuilder()).append("I/O error: ");
         String var4 = var6.getMessage();
         String var5 = var3.append(var4).toString();
         throw new NoSuchElementException(var5);
      }
   }

   public String nextLine() throws IOException {
      this.doRead();
      if(this.current == null) {
         throw new NoSuchElementException();
      } else {
         this.doneRead = (boolean)0;
         return this.current;
      }
   }

   public void readToEOF() throws IOException {
      do {
         this.doRead();
      } while(this.current != null);

   }

   public void remove() {
      throw new UnsupportedOperationException();
   }
}
