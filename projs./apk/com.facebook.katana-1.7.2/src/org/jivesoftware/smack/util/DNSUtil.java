package org.jivesoftware.smack.util;

import java.util.Map;
import org.jivesoftware.smack.util.Cache;

public class DNSUtil {

   private static Map<String, DNSUtil.HostAddress> ccache = new Cache(100, 600000L);
   private static Map<String, DNSUtil.HostAddress> scache = new Cache(100, 600000L);


   public DNSUtil() {}

   private static DNSUtil.HostAddress resolveSRV(String param0) {
      // $FF: Couldn't be decompiled
   }

   public static DNSUtil.HostAddress resolveXMPPDomain(String param0) {
      // $FF: Couldn't be decompiled
   }

   public static DNSUtil.HostAddress resolveXMPPServerDomain(String param0) {
      // $FF: Couldn't be decompiled
   }

   public static class HostAddress {

      private String host;
      private int port;


      private HostAddress(String var1, int var2) {
         this.host = var1;
         this.port = var2;
      }

      // $FF: synthetic method
      HostAddress(String var1, int var2, DNSUtil.1 var3) {
         this(var1, var2);
      }

      public boolean equals(Object var1) {
         boolean var2;
         if(this == var1) {
            var2 = true;
         } else if(!(var1 instanceof DNSUtil.HostAddress)) {
            var2 = false;
         } else {
            DNSUtil.HostAddress var7 = (DNSUtil.HostAddress)var1;
            String var3 = this.host;
            String var4 = var7.host;
            if(!var3.equals(var4)) {
               var2 = false;
            } else {
               int var5 = this.port;
               int var6 = var7.port;
               if(var5 == var6) {
                  var2 = true;
               } else {
                  var2 = false;
               }
            }
         }

         return var2;
      }

      public String getHost() {
         return this.host;
      }

      public int getPort() {
         return this.port;
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         String var2 = this.host;
         StringBuilder var3 = var1.append(var2).append(":");
         int var4 = this.port;
         return var3.append(var4).toString();
      }
   }

   // $FF: synthetic class
   static class 1 {
   }
}
