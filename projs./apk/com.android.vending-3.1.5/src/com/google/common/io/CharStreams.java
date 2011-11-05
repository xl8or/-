package com.google.common.io;

import com.google.common.base.Preconditions;
import com.google.common.io.AppendableWriter;
import com.google.common.io.Closeables;
import com.google.common.io.InputSupplier;
import com.google.common.io.LineProcessor;
import com.google.common.io.LineReader;
import com.google.common.io.MultiReader;
import com.google.common.io.OutputSupplier;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.nio.Buffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public final class CharStreams {

   private static final int BUF_SIZE = 2048;


   private CharStreams() {}

   public static Writer asWriter(Appendable var0) {
      Object var1;
      if(var0 instanceof Writer) {
         var1 = (Writer)var0;
      } else {
         var1 = new AppendableWriter(var0);
      }

      return (Writer)var1;
   }

   public static <R extends Object & Readable & Closeable, W extends Object & Appendable & Closeable> long copy(InputSupplier<R> param0, OutputSupplier<W> param1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public static <R extends Object & Readable & Closeable> long copy(InputSupplier<R> var0, Appendable var1) throws IOException {
      Readable var2 = (Readable)var0.getInput();
      boolean var9 = false;

      long var3;
      try {
         var9 = true;
         var3 = copy(var2, var1);
         var9 = false;
      } finally {
         if(var9) {
            Closeables.close((Closeable)var2, (boolean)1);
         }
      }

      Closeables.close((Closeable)var2, (boolean)0);
      return var3;
   }

   public static long copy(Readable var0, Appendable var1) throws IOException {
      CharBuffer var2 = CharBuffer.allocate(2048);
      long var3 = 0L;

      while(true) {
         int var5 = var0.read(var2);
         if(var5 == -1) {
            return var3;
         }

         Buffer var6 = var2.flip();
         var1.append(var2, 0, var5);
         long var8 = (long)var5;
         var3 += var8;
      }
   }

   public static InputSupplier<Reader> join(Iterable<? extends InputSupplier<? extends Reader>> var0) {
      return new CharStreams.4(var0);
   }

   public static InputSupplier<Reader> join(InputSupplier<? extends Reader> ... var0) {
      return join((Iterable)Arrays.asList(var0));
   }

   public static InputSupplier<InputStreamReader> newReaderSupplier(InputSupplier<? extends InputStream> var0, Charset var1) {
      Object var2 = Preconditions.checkNotNull(var0);
      Object var3 = Preconditions.checkNotNull(var1);
      return new CharStreams.2(var0, var1);
   }

   public static InputSupplier<StringReader> newReaderSupplier(String var0) {
      Object var1 = Preconditions.checkNotNull(var0);
      return new CharStreams.1(var0);
   }

   public static OutputSupplier<OutputStreamWriter> newWriterSupplier(OutputSupplier<? extends OutputStream> var0, Charset var1) {
      Object var2 = Preconditions.checkNotNull(var0);
      Object var3 = Preconditions.checkNotNull(var1);
      return new CharStreams.3(var0, var1);
   }

   public static <R extends Object & Readable & Closeable> String readFirstLine(InputSupplier<R> var0) throws IOException {
      Readable var1 = (Readable)var0.getInput();
      boolean var6 = false;

      String var2;
      try {
         var6 = true;
         var2 = (new LineReader(var1)).readLine();
         var6 = false;
      } finally {
         if(var6) {
            Closeables.close((Closeable)var1, (boolean)1);
         }
      }

      Closeables.close((Closeable)var1, (boolean)0);
      return var2;
   }

   public static <R extends Object & Readable & Closeable, T extends Object> T readLines(InputSupplier<R> param0, LineProcessor<T> param1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public static <R extends Object & Readable & Closeable> List<String> readLines(InputSupplier<R> var0) throws IOException {
      Readable var1 = (Readable)var0.getInput();
      boolean var6 = false;

      List var2;
      try {
         var6 = true;
         var2 = readLines(var1);
         var6 = false;
      } finally {
         if(var6) {
            Closeables.close((Closeable)var1, (boolean)1);
         }
      }

      Closeables.close((Closeable)var1, (boolean)0);
      return var2;
   }

   public static List<String> readLines(Readable var0) throws IOException {
      ArrayList var1 = new ArrayList();
      LineReader var2 = new LineReader(var0);

      while(true) {
         String var3 = var2.readLine();
         if(var3 == null) {
            return var1;
         }

         var1.add(var3);
      }
   }

   public static void skipFully(Reader var0, long var1) throws IOException {
      while(var1 > 0L) {
         long var3 = var0.skip(var1);
         if(var3 == 0L) {
            if(var0.read() == -1) {
               throw new EOFException();
            }

            --var1;
         } else {
            var1 -= var3;
         }
      }

   }

   public static <R extends Object & Readable & Closeable> String toString(InputSupplier<R> var0) throws IOException {
      return toStringBuilder(var0).toString();
   }

   public static String toString(Readable var0) throws IOException {
      return toStringBuilder(var0).toString();
   }

   private static <R extends Object & Readable & Closeable> StringBuilder toStringBuilder(InputSupplier<R> var0) throws IOException {
      Readable var1 = (Readable)var0.getInput();
      boolean var6 = false;

      StringBuilder var2;
      try {
         var6 = true;
         var2 = toStringBuilder(var1);
         var6 = false;
      } finally {
         if(var6) {
            Closeables.close((Closeable)var1, (boolean)1);
         }
      }

      Closeables.close((Closeable)var1, (boolean)0);
      return var2;
   }

   private static StringBuilder toStringBuilder(Readable var0) throws IOException {
      StringBuilder var1 = new StringBuilder();
      copy(var0, (Appendable)var1);
      return var1;
   }

   public static <W extends Object & Appendable & Closeable> void write(CharSequence var0, OutputSupplier<W> var1) throws IOException {
      Object var2 = Preconditions.checkNotNull(var0);
      Appendable var3 = (Appendable)var1.getOutput();
      boolean var7 = false;

      try {
         var7 = true;
         var3.append(var0);
         var7 = false;
      } finally {
         if(var7) {
            Closeables.close((Closeable)var3, (boolean)1);
         }
      }

      Closeables.close((Closeable)var3, (boolean)0);
   }

   static class 4 implements InputSupplier<Reader> {

      // $FF: synthetic field
      final Iterable val$suppliers;


      4(Iterable var1) {
         this.val$suppliers = var1;
      }

      public Reader getInput() throws IOException {
         Iterator var1 = this.val$suppliers.iterator();
         return new MultiReader(var1);
      }
   }

   static class 1 implements InputSupplier<StringReader> {

      // $FF: synthetic field
      final String val$value;


      1(String var1) {
         this.val$value = var1;
      }

      public StringReader getInput() {
         String var1 = this.val$value;
         return new StringReader(var1);
      }
   }

   static class 3 implements OutputSupplier<OutputStreamWriter> {

      // $FF: synthetic field
      final Charset val$charset;
      // $FF: synthetic field
      final OutputSupplier val$out;


      3(OutputSupplier var1, Charset var2) {
         this.val$out = var1;
         this.val$charset = var2;
      }

      public OutputStreamWriter getOutput() throws IOException {
         OutputStream var1 = (OutputStream)this.val$out.getOutput();
         Charset var2 = this.val$charset;
         return new OutputStreamWriter(var1, var2);
      }
   }

   static class 2 implements InputSupplier<InputStreamReader> {

      // $FF: synthetic field
      final Charset val$charset;
      // $FF: synthetic field
      final InputSupplier val$in;


      2(InputSupplier var1, Charset var2) {
         this.val$in = var1;
         this.val$charset = var2;
      }

      public InputStreamReader getInput() throws IOException {
         InputStream var1 = (InputStream)this.val$in.getInput();
         Charset var2 = this.val$charset;
         return new InputStreamReader(var1, var2);
      }
   }
}
