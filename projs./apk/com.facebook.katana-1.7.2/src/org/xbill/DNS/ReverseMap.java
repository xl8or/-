package org.xbill.DNS;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.xbill.DNS.Address;
import org.xbill.DNS.Name;

public final class ReverseMap {

   private static Name inaddr4 = Name.fromConstantString("in-addr.arpa.");
   private static Name inaddr6 = Name.fromConstantString("ip6.arpa.");


   private ReverseMap() {}

   public static Name fromAddress(String var0) throws UnknownHostException {
      byte[] var1 = Address.toByteArray(var0, 1);
      if(var1 == null) {
         var1 = Address.toByteArray(var0, 2);
      }

      if(var1 == null) {
         throw new UnknownHostException("Invalid IP address");
      } else {
         return fromAddress(var1);
      }
   }

   public static Name fromAddress(String var0, int var1) throws UnknownHostException {
      byte[] var2 = Address.toByteArray(var0, var1);
      if(var2 == null) {
         throw new UnknownHostException("Invalid IP address");
      } else {
         return fromAddress(var2);
      }
   }

   public static Name fromAddress(InetAddress var0) {
      return fromAddress(var0.getAddress());
   }

   public static Name fromAddress(byte[] param0) {
      // $FF: Couldn't be decompiled
   }

   public static Name fromAddress(int[] var0) {
      byte[] var1 = new byte[var0.length];
      int var2 = 0;

      while(true) {
         int var3 = var0.length;
         if(var2 >= var3) {
            return fromAddress(var1);
         }

         if(var0[var2] < 0 || var0[var2] > 255) {
            throw new IllegalArgumentException("array must contain values between 0 and 255");
         }

         byte var4 = (byte)var0[var2];
         var1[var2] = var4;
         ++var2;
      }
   }
}
