package org.xbill.DNS.spi;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.StringTokenizer;
import org.xbill.DNS.AAAARecord;
import org.xbill.DNS.ARecord;
import org.xbill.DNS.ExtendedResolver;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.Name;
import org.xbill.DNS.PTRRecord;
import org.xbill.DNS.Record;
import org.xbill.DNS.ReverseMap;
import org.xbill.DNS.TextParseException;

public class DNSJavaNameService implements InvocationHandler {

   private static final String domainProperty = "sun.net.spi.nameservice.domain";
   private static final String nsProperty = "sun.net.spi.nameservice.nameservers";
   private static final String v6Property = "java.net.preferIPv6Addresses";
   private boolean preferV6 = 0;


   protected DNSJavaNameService() {
      String var1 = System.getProperty("sun.net.spi.nameservice.nameservers");
      String var2 = System.getProperty("sun.net.spi.nameservice.domain");
      String var3 = System.getProperty("java.net.preferIPv6Addresses");
      if(var1 != null) {
         StringTokenizer var4 = new StringTokenizer(var1, ",");
         String[] var14 = new String[var4.countTokens()];

         int var6;
         for(int var5 = 0; var4.hasMoreTokens(); var5 = var6) {
            var6 = var5 + 1;
            String var7 = var4.nextToken();
            var14[var5] = var7;
         }

         try {
            Lookup.setDefaultResolver(new ExtendedResolver(var14));
         } catch (UnknownHostException var13) {
            System.err.println("DNSJavaNameService: invalid sun.net.spi.nameservice.nameservers");
         }
      }

      if(var2 != null) {
         byte var8 = 1;

         try {
            String[] var9 = new String[var8];
            var9[0] = var2;
            Lookup.setDefaultSearchPath(var9);
         } catch (TextParseException var12) {
            System.err.println("DNSJavaNameService: invalid sun.net.spi.nameservice.domain");
         }
      }

      if(var3 != null) {
         if(var3.equalsIgnoreCase("true")) {
            this.preferV6 = (boolean)1;
         }
      }
   }

   public String getHostByAddr(byte[] var1) throws UnknownHostException {
      Name var2 = ReverseMap.fromAddress(InetAddress.getByAddress(var1));
      Record[] var3 = (new Lookup(var2, 12)).run();
      if(var3 == null) {
         throw new UnknownHostException();
      } else {
         return ((PTRRecord)var3[0]).getTarget().toString();
      }
   }

   public Object invoke(Object param1, Method param2, Object[] param3) throws Throwable {
      // $FF: Couldn't be decompiled
   }

   public InetAddress[] lookupAllHostAddr(String var1) throws UnknownHostException {
      Name var2;
      try {
         var2 = new Name(var1);
      } catch (TextParseException var14) {
         throw new UnknownHostException(var1);
      }

      Object var3 = null;
      if(this.preferV6) {
         Record[] var4 = (new Lookup(var2, 28)).run();
      }

      if(var3 == null) {
         Record[] var5 = (new Lookup(var2, 1)).run();
      }

      Record[] var15;
      if(var3 == null && !this.preferV6) {
         var15 = (new Lookup(var2, 28)).run();
      } else {
         var15 = (Record[])var3;
      }

      if(var15 == null) {
         throw new UnknownHostException(var1);
      } else {
         InetAddress[] var7 = new InetAddress[var15.length];
         byte var8 = 0;

         while(true) {
            int var9 = var15.length;
            if(var8 >= var9) {
               return var7;
            }

            Record var10000 = var15[var8];
            if(var15[var8] instanceof ARecord) {
               InetAddress var11 = ((ARecord)var15[var8]).getAddress();
               var7[var8] = var11;
            } else {
               InetAddress var13 = ((AAAARecord)var15[var8]).getAddress();
               var7[var8] = var13;
            }

            int var12 = var8 + 1;
         }
      }
   }
}
