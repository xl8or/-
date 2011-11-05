package com.google.common.io;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.Writer;

class AppendableWriter extends Writer {

   private boolean closed;
   private final Appendable target;


   AppendableWriter(Appendable var1) {
      this.target = var1;
   }

   private void checkNotClosed() throws IOException {
      if(this.closed) {
         throw new IOException("Cannot write to a closed writer.");
      }
   }

   public Writer append(char var1) throws IOException {
      this.checkNotClosed();
      this.target.append(var1);
      return this;
   }

   public Writer append(CharSequence var1) throws IOException {
      this.checkNotClosed();
      this.target.append(var1);
      return this;
   }

   public Writer append(CharSequence var1, int var2, int var3) throws IOException {
      this.checkNotClosed();
      this.target.append(var1, var2, var3);
      return this;
   }

   public void close() throws IOException {
      this.closed = (boolean)1;
      if(this.target instanceof Closeable) {
         ((Closeable)this.target).close();
      }
   }

   public void flush() throws IOException {
      this.checkNotClosed();
      if(this.target instanceof Flushable) {
         ((Flushable)this.target).flush();
      }
   }

   public void write(int var1) throws IOException {
      this.checkNotClosed();
      Appendable var2 = this.target;
      char var3 = (char)var1;
      var2.append(var3);
   }

   public void write(String var1) throws IOException {
      this.checkNotClosed();
      this.target.append(var1);
   }

   public void write(String var1, int var2, int var3) throws IOException {
      this.checkNotClosed();
      Appendable var4 = this.target;
      int var5 = var2 + var3;
      var4.append(var1, var2, var5);
   }

   public void write(char[] var1, int var2, int var3) throws IOException {
      this.checkNotClosed();
      Appendable var4 = this.target;
      String var5 = new String(var1, var2, var3);
      var4.append(var5);
   }
}
