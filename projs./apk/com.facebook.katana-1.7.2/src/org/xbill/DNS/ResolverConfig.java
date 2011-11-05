package org.xbill.DNS;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import org.xbill.DNS.Name;
import org.xbill.DNS.Options;
import org.xbill.DNS.TextParseException;

public class ResolverConfig {

   private static ResolverConfig currentConfig;
   private Name[] searchlist = null;
   private String[] servers = null;


   static {
      refresh();
   }

   public ResolverConfig() {
      if(!this.findProperty()) {
         if(!this.findSunJVM()) {
            if(this.servers == null || this.searchlist == null) {
               String var1 = System.getProperty("os.name");
               String var2 = System.getProperty("java.vendor");
               if(var1.indexOf("Windows") != -1) {
                  if(var1.indexOf("95") == -1 && var1.indexOf("98") == -1 && var1.indexOf("ME") == -1) {
                     this.findNT();
                  } else {
                     this.find95();
                  }
               } else if(var1.indexOf("NetWare") != -1) {
                  this.findNetware();
               } else if(var2.indexOf("Android") != -1) {
                  this.findAndroid();
               } else {
                  this.findUnix();
               }
            }
         }
      }
   }

   private void addSearch(String var1, List var2) {
      if(Options.check("verbose")) {
         PrintStream var3 = System.out;
         String var4 = "adding search " + var1;
         var3.println(var4);
      }

      Name var6;
      try {
         Name var5 = Name.root;
         var6 = Name.fromString(var1, var5);
      } catch (TextParseException var10) {
         return;
      }

      if(!var2.contains(var6)) {
         var2.add(var6);
      }
   }

   private void addServer(String var1, List var2) {
      if(!var2.contains(var1)) {
         if(Options.check("verbose")) {
            PrintStream var3 = System.out;
            String var4 = "adding server " + var1;
            var3.println(var4);
         }

         var2.add(var1);
      }
   }

   private void configureFromLists(List var1, List var2) {
      if(this.servers == null && var1.size() > 0) {
         String[] var3 = new String[0];
         String[] var4 = (String[])((String[])var1.toArray(var3));
         this.servers = var4;
      }

      if(this.searchlist == null) {
         if(var2.size() > 0) {
            Name[] var5 = new Name[0];
            Name[] var6 = (Name[])((Name[])var2.toArray(var5));
            this.searchlist = var6;
         }
      }
   }

   private void find95() {
      try {
         Runtime var1 = Runtime.getRuntime();
         String var2 = "winipcfg /all /batch " + "winipcfg.out";
         int var3 = var1.exec(var2).waitFor();
         File var4 = new File("winipcfg.out");
         FileInputStream var5 = new FileInputStream(var4);
         this.findWin(var5);
         boolean var6 = (new File("winipcfg.out")).delete();
      } catch (Exception var8) {
         ;
      }
   }

   private void findAndroid() {
      ArrayList var1;
      try {
         var1 = new ArrayList();
         InputStream var2 = Runtime.getRuntime().exec("getprop").getInputStream();
         InputStreamReader var3 = new InputStreamReader(var2);
         BufferedReader var4 = new BufferedReader(var3);

         while(true) {
            String var5 = var4.readLine();
            if(var5 == null) {
               break;
            }

            StringTokenizer var6 = new StringTokenizer(var5, ":");
            if(var6.nextToken().indexOf(".dns") > -1) {
               var5 = var6.nextToken().replaceAll("[ \\[\\]]", "");
               if((var5.matches("^\\d+(\\.\\d+){3}$") || var5.matches("^[0-9a-f]+(:[0-9a-f]*)+:[0-9a-f]+$")) && !var1.contains(var5)) {
                  var1.add(var5);
               }
            }
         }
      } catch (Exception var9) {
         return;
      }

      this.configureFromLists(var1, (List)null);
   }

   private void findNT() {
      try {
         Process var1 = Runtime.getRuntime().exec("ipconfig /all");
         InputStream var2 = var1.getInputStream();
         this.findWin(var2);
         var1.destroy();
      } catch (Exception var4) {
         ;
      }
   }

   private void findNetware() {
      this.findResolvConf("sys:/etc/resolv.cfg");
   }

   private boolean findProperty() {
      ArrayList var1 = new ArrayList(0);
      ArrayList var2 = new ArrayList(0);
      String var3 = System.getProperty("dns.server");
      StringTokenizer var4;
      if(var3 != null) {
         var4 = new StringTokenizer(var3, ",");

         while(var4.hasMoreTokens()) {
            String var5 = var4.nextToken();
            this.addServer(var5, var1);
         }
      }

      String var6 = System.getProperty("dns.search");
      if(var6 != null) {
         var4 = new StringTokenizer(var6, ",");

         while(var4.hasMoreTokens()) {
            String var7 = var4.nextToken();
            this.addSearch(var7, var2);
         }
      }

      this.configureFromLists(var1, var2);
      boolean var8;
      if(this.servers != null && this.searchlist != null) {
         var8 = true;
      } else {
         var8 = false;
      }

      return var8;
   }

   private void findResolvConf(String var1) {
      String var2 = null;

      FileInputStream var3;
      try {
         var3 = new FileInputStream(var1);
      } catch (FileNotFoundException var18) {
         return;
      }

      InputStreamReader var4 = new InputStreamReader(var3);
      BufferedReader var5 = new BufferedReader(var4);
      ArrayList var6 = new ArrayList(0);
      ArrayList var7 = new ArrayList(0);

      try {
         while(true) {
            var2 = var5.readLine();
            if(var2 == null) {
               var5.close();
               break;
            }

            if(var2.startsWith("nameserver")) {
               StringTokenizer var8 = new StringTokenizer(var2);
               String var9 = var8.nextToken();
               String var10 = var8.nextToken();
               this.addServer(var10, var6);
            } else {
               StringTokenizer var12;
               if(var2.startsWith("domain")) {
                  var12 = new StringTokenizer(var2);
                  String var13 = var12.nextToken();
                  if(var12.hasMoreTokens() && var7.isEmpty()) {
                     String var14 = var12.nextToken();
                     this.addSearch(var14, var7);
                  }
               } else if(var2.startsWith("search")) {
                  if(!var7.isEmpty()) {
                     var7.clear();
                  }

                  var12 = new StringTokenizer(var2);
                  String var15 = var12.nextToken();

                  while(var12.hasMoreTokens()) {
                     String var16 = var12.nextToken();
                     this.addSearch(var16, var7);
                  }
               }
            }
         }
      } catch (IOException var19) {
         ;
      }

      this.configureFromLists(var6, var7);
   }

   private boolean findSunJVM() {
      ArrayList var1 = new ArrayList(0);
      ArrayList var2 = new ArrayList(0);

      List var7;
      List var8;
      boolean var9;
      try {
         Class[] var3 = new Class[0];
         Object[] var4 = new Object[0];
         Class var5 = Class.forName("sun.net.dns.ResolverConfiguration");
         Object var6 = var5.getDeclaredMethod("open", var3).invoke((Object)null, var4);
         var7 = (List)var5.getMethod("nameservers", var3).invoke(var6, var4);
         var8 = (List)var5.getMethod("searchlist", var3).invoke(var6, var4);
      } catch (Exception var14) {
         var9 = false;
         return var9;
      }

      if(var7.size() == 0) {
         var9 = false;
      } else {
         if(var7.size() > 0) {
            Iterator var11 = var7.iterator();

            while(var11.hasNext()) {
               String var12 = (String)var11.next();
               this.addServer(var12, var1);
            }
         }

         if(var8.size() > 0) {
            Iterator var15 = var8.iterator();

            while(var15.hasNext()) {
               String var13 = (String)var15.next();
               this.addSearch(var13, var2);
            }
         }

         this.configureFromLists(var1, var2);
         var9 = true;
      }

      return var9;
   }

   private void findUnix() {
      this.findResolvConf("/etc/resolv.conf");
   }

   private void findWin(InputStream param1) {
      // $FF: Couldn't be decompiled
   }

   public static ResolverConfig getCurrentConfig() {
      synchronized(ResolverConfig.class){}

      ResolverConfig var0;
      try {
         var0 = currentConfig;
      } finally {
         ;
      }

      return var0;
   }

   public static void refresh() {
      ResolverConfig var0 = new ResolverConfig();
      synchronized(ResolverConfig.class) {
         currentConfig = var0;
      }
   }

   public Name[] searchPath() {
      return this.searchlist;
   }

   public String server() {
      String var1;
      if(this.servers == null) {
         var1 = null;
      } else {
         var1 = this.servers[0];
      }

      return var1;
   }

   public String[] servers() {
      return this.servers;
   }
}
