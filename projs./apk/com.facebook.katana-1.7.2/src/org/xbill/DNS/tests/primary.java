package org.xbill.DNS.tests;

import java.io.PrintStream;
import java.util.Iterator;
import org.xbill.DNS.Name;
import org.xbill.DNS.Zone;

public class primary {

   public primary() {}

   public static void main(String[] var0) throws Exception {
      int var1;
      boolean var2;
      boolean var3;
      boolean var4;
      if(var0.length < 2) {
         var1 = 0;
         var2 = false;
         var3 = false;
         var4 = false;
         usage();
      } else {
         var1 = 0;
         var2 = false;
         var3 = false;
         var4 = false;
      }

      for(; var0.length - var1 > 2; ++var1) {
         if(var0[0].equals("-t")) {
            var4 = true;
         } else if(var0[0].equals("-a")) {
            var3 = true;
         } else if(var0[0].equals("-i")) {
            boolean var5 = true;
         }
      }

      int var6 = var1 + 1;
      String var7 = var0[var1];
      Name var8 = Name.root;
      Name var9 = Name.fromString(var7, var8);
      int var10 = var6 + 1;
      String var11 = var0[var6];
      long var12 = System.currentTimeMillis();
      Zone var14 = new Zone(var9, var11);
      long var15 = System.currentTimeMillis();
      Iterator var17;
      if(var3) {
         var17 = var14.AXFR();

         while(var17.hasNext()) {
            PrintStream var18 = System.out;
            Object var19 = var17.next();
            var18.println(var19);
         }
      } else if(var2) {
         var17 = var14.iterator();

         while(var17.hasNext()) {
            PrintStream var20 = System.out;
            Object var21 = var17.next();
            var20.println(var21);
         }
      } else {
         System.out.println(var14);
      }

      if(var4) {
         PrintStream var22 = System.out;
         StringBuilder var23 = (new StringBuilder()).append("; Load time: ");
         long var24 = var15 - var12;
         String var26 = var23.append(var24).append(" ms").toString();
         var22.println(var26);
      }
   }

   private static void usage() {
      System.out.println("usage: primary [-t] [-a | -i] origin file");
      System.exit(1);
   }
}
