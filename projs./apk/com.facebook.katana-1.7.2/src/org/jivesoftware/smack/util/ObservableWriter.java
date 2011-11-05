package org.jivesoftware.smack.util;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import org.jivesoftware.smack.util.WriterListener;

public class ObservableWriter extends Writer {

   List listeners;
   Writer wrappedWriter = null;


   public ObservableWriter(Writer var1) {
      ArrayList var2 = new ArrayList();
      this.listeners = var2;
      this.wrappedWriter = var1;
   }

   private void notifyListeners(String param1) {
      // $FF: Couldn't be decompiled
   }

   public void addWriterListener(WriterListener var1) {
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
      this.wrappedWriter.close();
   }

   public void flush() throws IOException {
      this.wrappedWriter.flush();
   }

   public void removeWriterListener(WriterListener var1) {
      List var2 = this.listeners;
      synchronized(var2) {
         this.listeners.remove(var1);
      }
   }

   public void write(int var1) throws IOException {
      this.wrappedWriter.write(var1);
   }

   public void write(String var1) throws IOException {
      this.wrappedWriter.write(var1);
      this.notifyListeners(var1);
   }

   public void write(String var1, int var2, int var3) throws IOException {
      this.wrappedWriter.write(var1, var2, var3);
      int var4 = var2 + var3;
      String var5 = var1.substring(var2, var4);
      this.notifyListeners(var5);
   }

   public void write(char[] var1) throws IOException {
      this.wrappedWriter.write(var1);
      String var2 = new String(var1);
      this.notifyListeners(var2);
   }

   public void write(char[] var1, int var2, int var3) throws IOException {
      this.wrappedWriter.write(var1, var2, var3);
      String var4 = new String(var1, var2, var3);
      this.notifyListeners(var4);
   }
}
