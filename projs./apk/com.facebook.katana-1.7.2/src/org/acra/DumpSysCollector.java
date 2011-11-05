package org.acra;

import android.os.Process;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.acra.ACRA;

class DumpSysCollector {

   DumpSysCollector() {}

   protected static String collectMemInfo() {
      StringBuilder var0 = new StringBuilder();

      try {
         ArrayList var1 = new ArrayList();
         boolean var2 = var1.add("dumpsys");
         boolean var3 = var1.add("meminfo");
         String var4 = Integer.toString(Process.myPid());
         var1.add(var4);
         Runtime var6 = Runtime.getRuntime();
         String[] var7 = new String[var1.size()];
         String[] var8 = (String[])var1.toArray(var7);
         InputStream var9 = var6.exec(var8).getInputStream();
         InputStreamReader var10 = new InputStreamReader(var9);
         BufferedReader var11 = new BufferedReader(var10);

         while(true) {
            String var12 = var11.readLine();
            if(var12 == null) {
               break;
            }

            var0.append(var12);
            StringBuilder var14 = var0.append("\n");
         }
      } catch (IOException var17) {
         int var16 = Log.e(ACRA.LOG_TAG, "DumpSysCollector.meminfo could not retrieve data", var17);
      }

      return var0.toString();
   }
}
