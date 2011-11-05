package org.jivesoftware.smack.util;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import org.jivesoftware.smack.util.ReaderListener;

public class ObservableReader extends Reader {

   List listeners;
   Reader wrappedReader = null;


   public ObservableReader(Reader var1) {
      ArrayList var2 = new ArrayList();
      this.listeners = var2;
      this.wrappedReader = var1;
   }

   public void addReaderListener(ReaderListener var1) {
      if(var1 != null) {
         List var2 = this.listeners;
         synchronized(var2) {
            if(!this.listeners.contains(var1)) {
               this.listeners.add(var1);
            }

         }
      }
   }

   public void close() throws IOException {
      this.wrappedReader.close();
   }

   public void mark(int var1) throws IOException {
      this.wrappedReader.mark(var1);
   }

   public boolean markSupported() {
      return this.wrappedReader.markSupported();
   }

   public int read() throws IOException {
      return this.wrappedReader.read();
   }

   public int read(char[] var1) throws IOException {
      return this.wrappedReader.read(var1);
   }

   public int read(char[] param1, int param2, int param3) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public boolean ready() throws IOException {
      return this.wrappedReader.ready();
   }

   public void removeReaderListener(ReaderListener var1) {
      List var2 = this.listeners;
      synchronized(var2) {
         this.listeners.remove(var1);
      }
   }

   public void reset() throws IOException {
      this.wrappedReader.reset();
   }

   public long skip(long var1) throws IOException {
      return this.wrappedReader.skip(var1);
   }
}
