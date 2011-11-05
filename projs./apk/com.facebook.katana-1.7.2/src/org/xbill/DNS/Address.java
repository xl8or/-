package org.xbill.DNS;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.xbill.DNS.ARecord;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.Name;
import org.xbill.DNS.PTRRecord;
import org.xbill.DNS.Record;
import org.xbill.DNS.ReverseMap;
import org.xbill.DNS.TextParseException;

public final class Address {

   public static final int IPv4 = 1;
   public static final int IPv6 = 2;


   private Address() {}

   private static InetAddress addrFromRecord(String var0, Record var1) throws UnknownHostException {
      byte[] var2 = ((ARecord)var1).getAddress().getAddress();
      return InetAddress.getByAddress(var0, var2);
   }

   public static int addressLength(int var0) {
      byte var1;
      if(var0 == 1) {
         var1 = 4;
      } else {
         if(var0 != 2) {
            throw new IllegalArgumentException("unknown address family");
         }

         var1 = 16;
      }

      return var1;
   }

   public static int familyOf(InetAddress var0) {
      byte var1;
      if(var0 instanceof Inet4Address) {
         var1 = 1;
      } else {
         if(!(var0 instanceof Inet6Address)) {
            throw new IllegalArgumentException("unknown address family");
         }

         var1 = 2;
      }

      return var1;
   }

   public static InetAddress[] getAllByName(String var0) throws UnknownHostException {
      InetAddress[] var2;
      InetAddress[] var3;
      try {
         InetAddress var1 = getByAddress(var0);
         var2 = new InetAddress[]{var1};
      } catch (UnknownHostException var11) {
         Record[] var5 = lookupHostName(var0);
         var2 = new InetAddress[var5.length];
         byte var6 = 0;

         while(true) {
            int var7 = var5.length;
            if(var6 >= var7) {
               var3 = var2;
               return var3;
            }

            Record var8 = var5[var6];
            InetAddress var9 = addrFromRecord(var0, var8);
            var2[var6] = var9;
            int var10 = var6 + 1;
         }
      }

      var3 = var2;
      return var3;
   }

   public static InetAddress getByAddress(String var0) throws UnknownHostException {
      byte[] var1 = toByteArray(var0, 1);
      InetAddress var4;
      if(var1 != null) {
         var4 = InetAddress.getByAddress(var1);
      } else {
         byte[] var2 = toByteArray(var0, 2);
         if(var2 == null) {
            String var3 = "Invalid address: " + var0;
            throw new UnknownHostException(var3);
         }

         var4 = InetAddress.getByAddress(var2);
      }

      return var4;
   }

   public static InetAddress getByAddress(String var0, int var1) throws UnknownHostException {
      if(var1 != 1 && var1 != 2) {
         throw new IllegalArgumentException("unknown address family");
      } else {
         byte[] var2 = toByteArray(var0, var1);
         if(var2 != null) {
            return InetAddress.getByAddress(var2);
         } else {
            String var3 = "Invalid address: " + var0;
            throw new UnknownHostException(var3);
         }
      }
   }

   public static InetAddress getByName(String var0) throws UnknownHostException {
      InetAddress var1;
      InetAddress var2;
      try {
         var1 = getByAddress(var0);
      } catch (UnknownHostException var5) {
         Record var4 = lookupHostName(var0)[0];
         var2 = addrFromRecord(var0, var4);
         return var2;
      }

      var2 = var1;
      return var2;
   }

   public static String getHostName(InetAddress var0) throws UnknownHostException {
      Name var1 = ReverseMap.fromAddress(var0);
      Record[] var2 = (new Lookup(var1, 12)).run();
      if(var2 == null) {
         throw new UnknownHostException("unknown address");
      } else {
         return ((PTRRecord)var2[0]).getTarget().toString();
      }
   }

   public static boolean isDottedQuad(String var0) {
      boolean var1;
      if(toByteArray(var0, 1) != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private static Record[] lookupHostName(String var0) throws UnknownHostException {
      try {
         Record[] var1 = (new Lookup(var0)).run();
         if(var1 == null) {
            throw new UnknownHostException("unknown host");
         } else {
            return var1;
         }
      } catch (TextParseException var3) {
         throw new UnknownHostException("invalid name");
      }
   }

   private static byte[] parseV4(String var0) {
      byte[] var1 = new byte[4];
      int var2 = var0.length();
      int var3 = 0;
      int var4 = 0;
      int var5 = 0;
      int var6 = 0;

      while(true) {
         if(var3 >= var2) {
            if(var5 != 3) {
               var1 = null;
            } else if(var6 == 0) {
               var1 = null;
            } else {
               byte var12 = (byte)var4;
               var1[var5] = var12;
            }
            break;
         }

         char var7 = var0.charAt(var3);
         if(var7 >= 48 && var7 <= 57) {
            if(var6 == 3) {
               var1 = null;
               break;
            }

            if(var6 > 0 && var4 == 0) {
               var1 = null;
               break;
            }

            ++var6;
            int var8 = var4 * 10;
            int var9 = var7 - 48;
            var4 = var8 + var9;
            if(var4 > 255) {
               var1 = null;
               break;
            }
         } else {
            if(var7 != 46) {
               var1 = null;
               break;
            }

            if(var5 == 3) {
               var1 = null;
               break;
            }

            if(var6 == 0) {
               var1 = null;
               break;
            }

            int var10 = var5 + 1;
            byte var11 = (byte)var4;
            var1[var5] = var11;
            var4 = 0;
            var5 = var10;
            var6 = 0;
         }

         ++var3;
      }

      return var1;
   }

   private static byte[] parseV6(String var0) {
      byte var1 = -1;
      byte[] var2 = new byte[16];
      String[] var3 = var0.split(":", var1);
      int var4 = var3.length - 1;
      int var5;
      if(var3[0].length() == 0) {
         if(var4 - 0 <= 0 || var3[1].length() != 0) {
            var2 = null;
            return var2;
         }

         var5 = 0 + 1;
      } else {
         var5 = 0;
      }

      if(var3[var4].length() == 0) {
         label147: {
            if(var4 - var5 > 0) {
               int var6 = var4 - 1;
               if(var3[var6].length() == 0) {
                  var4 += -1;
                  break label147;
               }
            }

            var2 = null;
            return var2;
         }
      }

      if(var4 - var5 + 1 > 8) {
         var2 = null;
      } else {
         int var7 = var5;
         int var8 = -1;
         var5 = 0;

         while(true) {
            int var23;
            if(var7 <= var4) {
               label99: {
                  if(var3[var7].length() == 0) {
                     if(var8 >= 0) {
                        var2 = null;
                        break;
                     }

                     var8 = var5;
                  } else {
                     if(var3[var7].indexOf(46) >= 0) {
                        if(var7 < var4) {
                           var2 = null;
                           break;
                        }

                        if(var7 > 6) {
                           var2 = null;
                           break;
                        }

                        byte[] var22 = toByteArray(var3[var7], 1);
                        if(var22 == null) {
                           var2 = null;
                           break;
                        }

                        int var9;
                        for(byte var24 = 0; var24 < 4; var5 = var9) {
                           var9 = var5 + 1;
                           byte var10 = var22[var24];
                           var2[var5] = var10;
                           int var11 = var24 + 1;
                        }

                        var23 = var5;
                        break label99;
                     }

                     int var21 = 0;

                     try {
                        while(true) {
                           int var12 = var3[var7].length();
                           if(var21 >= var12) {
                              var21 = Integer.parseInt(var3[var7], 16);
                              if(var21 > '\uffff' || var21 < 0) {
                                 var2 = null;
                                 return var2;
                              }

                              int var13 = var5 + 1;
                              byte var14 = (byte)(var21 >>> 8);
                              var2[var5] = var14;
                              var5 = var13 + 1;
                              byte var15 = (byte)(var21 & 255);
                              var2[var13] = var15;
                              break;
                           }

                           if(Character.digit(var3[var7].charAt(var21), 16) < 0) {
                              var2 = null;
                              return var2;
                           }

                           ++var21;
                        }
                     } catch (NumberFormatException var20) {
                        var2 = null;
                        break;
                     }
                  }

                  ++var7;
                  continue;
               }
            } else {
               var23 = var5;
            }

            if(var23 < 16 && var8 < 0) {
               var2 = null;
               break;
            }

            if(var8 >= 0) {
               var4 = 16 - var23;
               int var17 = var8 + var4;
               int var18 = var23 - var8;
               System.arraycopy(var2, var8, var2, var17, var18);
               var23 = var8;

               while(true) {
                  int var19 = var8 + var4;
                  if(var23 >= var19) {
                     return var2;
                  }

                  var2[var23] = 0;
                  ++var23;
               }
            }
            break;
         }
      }

      return var2;
   }

   public static int[] toArray(String var0) {
      return toArray(var0, 1);
   }

   public static int[] toArray(String var0, int var1) {
      byte[] var2 = toByteArray(var0, var1);
      int[] var7;
      if(var2 == null) {
         var7 = null;
      } else {
         int[] var3 = new int[var2.length];
         int var4 = 0;

         while(true) {
            int var5 = var2.length;
            if(var4 >= var5) {
               var7 = var3;
               break;
            }

            int var6 = var2[var4] & 255;
            var3[var4] = var6;
            ++var4;
         }
      }

      return var7;
   }

   public static byte[] toByteArray(String var0, int var1) {
      byte[] var2;
      if(var1 == 1) {
         var2 = parseV4(var0);
      } else {
         if(var1 != 2) {
            throw new IllegalArgumentException("unknown address family");
         }

         var2 = parseV6(var0);
      }

      return var2;
   }

   public static String toDottedQuad(byte[] var0) {
      StringBuilder var1 = new StringBuilder();
      int var2 = var0[0] & 255;
      StringBuilder var3 = var1.append(var2).append(".");
      int var4 = var0[1] & 255;
      StringBuilder var5 = var3.append(var4).append(".");
      int var6 = var0[2] & 255;
      StringBuilder var7 = var5.append(var6).append(".");
      int var8 = var0[3] & 255;
      return var7.append(var8).toString();
   }

   public static String toDottedQuad(int[] var0) {
      StringBuilder var1 = new StringBuilder();
      int var2 = var0[0];
      StringBuilder var3 = var1.append(var2).append(".");
      int var4 = var0[1];
      StringBuilder var5 = var3.append(var4).append(".");
      int var6 = var0[2];
      StringBuilder var7 = var5.append(var6).append(".");
      int var8 = var0[3];
      return var7.append(var8).toString();
   }
}
