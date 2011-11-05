package com.google.common.io;

import com.google.common.base.Preconditions;
import com.google.common.io.ByteStreams;
import com.google.common.io.CharStreams;
import com.google.common.io.InputSupplier;
import com.google.common.io.LineProcessor;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

public final class Resources {

   public Resources() {}

   public static void copy(URL var0, OutputStream var1) throws IOException {
      long var2 = ByteStreams.copy(newInputStreamSupplier(var0), var1);
   }

   public static URL getResource(Class<?> var0, String var1) {
      URL var2 = var0.getResource(var1);
      byte var3;
      if(var2 != null) {
         var3 = 1;
      } else {
         var3 = 0;
      }

      Object[] var4 = new Object[]{var1, null};
      String var5 = var0.getName();
      var4[1] = var5;
      Preconditions.checkArgument((boolean)var3, "resource %s relative to %s not found.", var4);
      return var2;
   }

   public static URL getResource(String var0) {
      URL var1 = Resources.class.getClassLoader().getResource(var0);
      byte var2;
      if(var1 != null) {
         var2 = 1;
      } else {
         var2 = 0;
      }

      Object[] var3 = new Object[]{var0};
      Preconditions.checkArgument((boolean)var2, "resource %s not found.", var3);
      return var1;
   }

   public static InputSupplier<InputStream> newInputStreamSupplier(URL var0) {
      Object var1 = Preconditions.checkNotNull(var0);
      return new Resources.1(var0);
   }

   public static InputSupplier<InputStreamReader> newReaderSupplier(URL var0, Charset var1) {
      return CharStreams.newReaderSupplier(newInputStreamSupplier(var0), var1);
   }

   public static <T extends Object> T readLines(URL var0, Charset var1, LineProcessor<T> var2) throws IOException {
      return CharStreams.readLines(newReaderSupplier(var0, var1), var2);
   }

   public static List<String> readLines(URL var0, Charset var1) throws IOException {
      return CharStreams.readLines(newReaderSupplier(var0, var1));
   }

   public static byte[] toByteArray(URL var0) throws IOException {
      return ByteStreams.toByteArray(newInputStreamSupplier(var0));
   }

   public static String toString(URL var0, Charset var1) throws IOException {
      return CharStreams.toString(newReaderSupplier(var0, var1));
   }

   static class 1 implements InputSupplier<InputStream> {

      // $FF: synthetic field
      final URL val$url;


      1(URL var1) {
         this.val$url = var1;
      }

      public InputStream getInput() throws IOException {
         return this.val$url.openStream();
      }
   }
}
