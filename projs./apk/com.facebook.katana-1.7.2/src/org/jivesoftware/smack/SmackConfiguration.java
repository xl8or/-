package org.jivesoftware.smack;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import org.xmlpull.v1.XmlPullParser;

public final class SmackConfiguration {

   private static final String SMACK_VERSION = "3.1.0";
   private static Vector<String> defaultMechs;
   private static int keepAliveInterval;
   private static int packetReplyTimeout;


   static {
      // $FF: Couldn't be decompiled
   }

   private SmackConfiguration() {}

   public static void addSaslMech(String var0) {
      if(!defaultMechs.contains(var0)) {
         boolean var1 = defaultMechs.add(var0);
      }
   }

   public static void addSaslMechs(Collection<String> var0) {
      Iterator var1 = var0.iterator();

      while(var1.hasNext()) {
         addSaslMech((String)var1.next());
      }

   }

   private static ClassLoader[] getClassLoaders() {
      int var0 = 0;
      ClassLoader[] var1 = new ClassLoader[2];
      ClassLoader var2 = SmackConfiguration.class.getClassLoader();
      var1[var0] = var2;
      ClassLoader var3 = Thread.currentThread().getContextClassLoader();
      var1[1] = var3;
      ArrayList var4 = new ArrayList();

      for(int var5 = var1.length; var0 < var5; ++var0) {
         ClassLoader var6 = var1[var0];
         if(var6 != null) {
            var4.add(var6);
         }
      }

      ClassLoader[] var8 = new ClassLoader[var4.size()];
      return (ClassLoader[])var4.toArray(var8);
   }

   public static int getKeepAliveInterval() {
      return keepAliveInterval;
   }

   public static int getPacketReplyTimeout() {
      if(packetReplyTimeout <= 0) {
         packetReplyTimeout = 5000;
      }

      return packetReplyTimeout;
   }

   public static List<String> getSaslMechs() {
      return defaultMechs;
   }

   public static String getVersion() {
      return "3.1.0";
   }

   private static void parseClassToLoad(XmlPullParser var0) throws Exception {
      String var1 = var0.nextText();

      try {
         Class var2 = Class.forName(var1);
      } catch (ClassNotFoundException var6) {
         PrintStream var4 = System.err;
         String var5 = "Error! A startup class specified in smack-config.xml could not be loaded: " + var1;
         var4.println(var5);
      }
   }

   private static int parseIntProperty(XmlPullParser var0, int var1) throws Exception {
      int var2;
      int var3;
      try {
         var2 = Integer.parseInt(var0.nextText());
      } catch (NumberFormatException var4) {
         var4.printStackTrace();
         var3 = var1;
         return var3;
      }

      var3 = var2;
      return var3;
   }

   public static void removeSaslMech(String var0) {
      if(defaultMechs.contains(var0)) {
         boolean var1 = defaultMechs.remove(var0);
      }
   }

   public static void removeSaslMechs(Collection<String> var0) {
      Iterator var1 = var0.iterator();

      while(var1.hasNext()) {
         removeSaslMech((String)var1.next());
      }

   }

   public static void setKeepAliveInterval(int var0) {
      keepAliveInterval = var0;
   }

   public static void setPacketReplyTimeout(int var0) {
      if(var0 <= 0) {
         throw new IllegalArgumentException();
      } else {
         packetReplyTimeout = var0;
      }
   }
}
