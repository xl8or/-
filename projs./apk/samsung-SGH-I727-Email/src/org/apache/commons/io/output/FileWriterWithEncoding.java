package org.apache.commons.io.output;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

public class FileWriterWithEncoding extends Writer {

   private final Writer out;


   public FileWriterWithEncoding(File var1, String var2) throws IOException {
      this(var1, var2, (boolean)0);
   }

   public FileWriterWithEncoding(File var1, String var2, boolean var3) throws IOException {
      Writer var4 = initWriter(var1, var2, var3);
      this.out = var4;
   }

   public FileWriterWithEncoding(File var1, Charset var2) throws IOException {
      this(var1, var2, (boolean)0);
   }

   public FileWriterWithEncoding(File var1, Charset var2, boolean var3) throws IOException {
      Writer var4 = initWriter(var1, var2, var3);
      this.out = var4;
   }

   public FileWriterWithEncoding(File var1, CharsetEncoder var2) throws IOException {
      this(var1, var2, (boolean)0);
   }

   public FileWriterWithEncoding(File var1, CharsetEncoder var2, boolean var3) throws IOException {
      Writer var4 = initWriter(var1, var2, var3);
      this.out = var4;
   }

   public FileWriterWithEncoding(String var1, String var2) throws IOException {
      File var3 = new File(var1);
      this(var3, var2, (boolean)0);
   }

   public FileWriterWithEncoding(String var1, String var2, boolean var3) throws IOException {
      File var4 = new File(var1);
      this(var4, var2, var3);
   }

   public FileWriterWithEncoding(String var1, Charset var2) throws IOException {
      File var3 = new File(var1);
      this(var3, var2, (boolean)0);
   }

   public FileWriterWithEncoding(String var1, Charset var2, boolean var3) throws IOException {
      File var4 = new File(var1);
      this(var4, var2, var3);
   }

   public FileWriterWithEncoding(String var1, CharsetEncoder var2) throws IOException {
      File var3 = new File(var1);
      this(var3, var2, (boolean)0);
   }

   public FileWriterWithEncoding(String var1, CharsetEncoder var2, boolean var3) throws IOException {
      File var4 = new File(var1);
      this(var4, var2, var3);
   }

   private static Writer initWriter(File param0, Object param1, boolean param2) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public void close() throws IOException {
      this.out.close();
   }

   public void flush() throws IOException {
      this.out.flush();
   }

   public void write(int var1) throws IOException {
      this.out.write(var1);
   }

   public void write(String var1) throws IOException {
      this.out.write(var1);
   }

   public void write(String var1, int var2, int var3) throws IOException {
      this.out.write(var1, var2, var3);
   }

   public void write(char[] var1) throws IOException {
      this.out.write(var1);
   }

   public void write(char[] var1, int var2, int var3) throws IOException {
      this.out.write(var1, var2, var3);
   }
}
