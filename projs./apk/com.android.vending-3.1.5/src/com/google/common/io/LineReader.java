package com.google.common.io;

import com.google.common.base.Preconditions;
import com.google.common.io.LineBuffer;
import java.io.IOException;
import java.io.Reader;
import java.nio.Buffer;
import java.nio.CharBuffer;
import java.util.LinkedList;
import java.util.Queue;

public final class LineReader {

   private final char[] buf;
   private final CharBuffer cbuf;
   private final LineBuffer lineBuf;
   private final Queue<String> lines;
   private final Readable readable;
   private final Reader reader;


   public LineReader(Readable var1) {
      char[] var2 = new char[4096];
      this.buf = var2;
      CharBuffer var3 = CharBuffer.wrap(this.buf);
      this.cbuf = var3;
      LinkedList var4 = new LinkedList();
      this.lines = var4;
      LineReader.1 var5 = new LineReader.1();
      this.lineBuf = var5;
      Object var6 = Preconditions.checkNotNull(var1);
      this.readable = var1;
      Reader var7;
      if(var1 instanceof Reader) {
         var7 = (Reader)var1;
      } else {
         var7 = null;
      }

      this.reader = var7;
   }

   public String readLine() throws IOException {
      while(true) {
         if(this.lines.peek() == null) {
            Buffer var1 = this.cbuf.clear();
            int var5;
            if(this.reader != null) {
               Reader var2 = this.reader;
               char[] var3 = this.buf;
               int var4 = this.buf.length;
               var5 = var2.read(var3, 0, var4);
            } else {
               Readable var6 = this.readable;
               CharBuffer var7 = this.cbuf;
               var5 = var6.read(var7);
            }

            if(var5 != -1) {
               LineBuffer var8 = this.lineBuf;
               char[] var9 = this.buf;
               var8.add(var9, 0, var5);
               continue;
            }

            this.lineBuf.finish();
         }

         return (String)this.lines.poll();
      }
   }

   class 1 extends LineBuffer {

      1() {}

      protected void handleLine(String var1, String var2) {
         boolean var3 = LineReader.this.lines.add(var1);
      }
   }
}
