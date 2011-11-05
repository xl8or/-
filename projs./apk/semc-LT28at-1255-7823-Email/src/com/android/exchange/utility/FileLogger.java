package com.android.exchange.utility;

import android.content.Context;
import android.os.Environment;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileLogger {

   private static FileLogger LOGGER = null;
   public static String LOG_FILE_NAME;
   private static FileWriter sLogWriter = null;


   static {
      StringBuilder var0 = new StringBuilder();
      File var1 = Environment.getExternalStorageDirectory();
      LOG_FILE_NAME = var0.append(var1).append("/emaillog.txt").toString();
   }

   private FileLogger() {
      try {
         String var1 = LOG_FILE_NAME;
         sLogWriter = new FileWriter(var1, (boolean)1);
      } catch (IOException var3) {
         ;
      }
   }

   public static void close() {
      // $FF: Couldn't be decompiled
   }

   public static FileLogger getLogger(Context var0) {
      synchronized(FileLogger.class){}

      FileLogger var1;
      try {
         LOGGER = new FileLogger();
         var1 = LOGGER;
      } finally {
         ;
      }

      return var1;
   }

   public static void log(Exception var0) {
      synchronized(FileLogger.class){}

      try {
         if(sLogWriter != null) {
            log("Exception", "Stack trace follows...");
            FileWriter var1 = sLogWriter;
            PrintWriter var2 = new PrintWriter(var1);
            var0.printStackTrace(var2);
            var2.flush();
         }
      } finally {
         ;
      }

   }

   public static void log(String param0, String param1) {
      // $FF: Couldn't be decompiled
   }
}
