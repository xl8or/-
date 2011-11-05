package org.apache.commons.httpclient.util;

import java.util.BitSet;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.util.EncodingUtil;

public class URIUtil {

   protected static final BitSet empty = new BitSet(1);


   public URIUtil() {}

   public static String decode(String var0) throws URIException {
      try {
         byte[] var1 = URLCodec.decodeUrl(EncodingUtil.getAsciiBytes(var0));
         String var2 = URI.getDefaultProtocolCharset();
         String var3 = EncodingUtil.getString(var1, var2);
         return var3;
      } catch (DecoderException var5) {
         String var4 = var5.getMessage();
         throw new URIException(var4);
      }
   }

   public static String decode(String var0, String var1) throws URIException {
      return URI.decode(var0.toCharArray(), var1);
   }

   public static String encode(String var0, BitSet var1) throws URIException {
      String var2 = URI.getDefaultProtocolCharset();
      return encode(var0, var1, var2);
   }

   public static String encode(String var0, BitSet var1, String var2) throws URIException {
      byte[] var3 = EncodingUtil.getBytes(var0, var2);
      return EncodingUtil.getAsciiString(URLCodec.encodeUrl(var1, var3));
   }

   public static String encodeAll(String var0) throws URIException {
      String var1 = URI.getDefaultProtocolCharset();
      return encodeAll(var0, var1);
   }

   public static String encodeAll(String var0, String var1) throws URIException {
      BitSet var2 = empty;
      return encode(var0, var2, var1);
   }

   public static String encodePath(String var0) throws URIException {
      String var1 = URI.getDefaultProtocolCharset();
      return encodePath(var0, var1);
   }

   public static String encodePath(String var0, String var1) throws URIException {
      BitSet var2 = URI.allowed_abs_path;
      return encode(var0, var2, var1);
   }

   public static String encodePathQuery(String var0) throws URIException {
      String var1 = URI.getDefaultProtocolCharset();
      return encodePathQuery(var0, var1);
   }

   public static String encodePathQuery(String var0, String var1) throws URIException {
      int var2 = var0.indexOf(63);
      String var4;
      if(var2 < 0) {
         BitSet var3 = URI.allowed_abs_path;
         var4 = encode(var0, var3, var1);
      } else {
         StringBuilder var5 = new StringBuilder();
         String var6 = var0.substring(0, var2);
         BitSet var7 = URI.allowed_abs_path;
         String var8 = encode(var6, var7, var1);
         StringBuilder var9 = var5.append(var8).append('?');
         int var10 = var2 + 1;
         String var11 = var0.substring(var10);
         BitSet var12 = URI.allowed_query;
         String var13 = encode(var11, var12, var1);
         var4 = var9.append(var13).toString();
      }

      return var4;
   }

   public static String encodeQuery(String var0) throws URIException {
      String var1 = URI.getDefaultProtocolCharset();
      return encodeQuery(var0, var1);
   }

   public static String encodeQuery(String var0, String var1) throws URIException {
      BitSet var2 = URI.allowed_query;
      return encode(var0, var2, var1);
   }

   public static String encodeWithinAuthority(String var0) throws URIException {
      String var1 = URI.getDefaultProtocolCharset();
      return encodeWithinAuthority(var0, var1);
   }

   public static String encodeWithinAuthority(String var0, String var1) throws URIException {
      BitSet var2 = URI.allowed_within_authority;
      return encode(var0, var2, var1);
   }

   public static String encodeWithinPath(String var0) throws URIException {
      String var1 = URI.getDefaultProtocolCharset();
      return encodeWithinPath(var0, var1);
   }

   public static String encodeWithinPath(String var0, String var1) throws URIException {
      BitSet var2 = URI.allowed_within_path;
      return encode(var0, var2, var1);
   }

   public static String encodeWithinQuery(String var0) throws URIException {
      String var1 = URI.getDefaultProtocolCharset();
      return encodeWithinQuery(var0, var1);
   }

   public static String encodeWithinQuery(String var0, String var1) throws URIException {
      BitSet var2 = URI.allowed_within_query;
      return encode(var0, var2, var1);
   }

   public static String getFromPath(String var0) {
      String var1;
      if(var0 == null) {
         var1 = null;
      } else {
         int var2 = var0.indexOf("//");
         int var4;
         if(var2 >= 0) {
            int var3 = var2 - 1;
            if(var0.lastIndexOf("/", var3) >= 0) {
               var4 = 0;
            } else {
               var4 = var2 + 2;
            }
         } else {
            var4 = 0;
         }

         int var5 = var0.indexOf("/", var4);
         if(var5 < 0) {
            if(var2 >= 0) {
               var1 = "/";
            } else {
               var1 = var0;
            }
         } else {
            var1 = var0.substring(var5);
         }
      }

      return var1;
   }

   public static String getName(String var0) {
      String var1;
      if(var0 != null && var0.length() != 0) {
         String var2 = getPath(var0);
         int var3 = var2.lastIndexOf("/");
         int var4 = var2.length();
         if(var3 >= 0) {
            int var5 = var3 + 1;
            var1 = var2.substring(var5, var4);
         } else {
            var1 = var2;
         }
      } else {
         var1 = var0;
      }

      return var1;
   }

   public static String getPath(String var0) {
      String var1;
      if(var0 == null) {
         var1 = null;
      } else {
         int var2 = var0.indexOf("//");
         int var4;
         if(var2 >= 0) {
            int var3 = var2 - 1;
            if(var0.lastIndexOf("/", var3) >= 0) {
               var4 = 0;
            } else {
               var4 = var2 + 2;
            }
         } else {
            var4 = 0;
         }

         int var5 = var0.indexOf("/", var4);
         int var6 = var0.length();
         if(var0.indexOf(63, var5) != -1) {
            var6 = var0.indexOf(63, var5);
         }

         if(var0.lastIndexOf("#") > var5 && var0.lastIndexOf("#") < var6) {
            var6 = var0.lastIndexOf("#");
         }

         if(var5 < 0) {
            if(var2 >= 0) {
               var1 = "/";
            } else {
               var1 = var0;
            }
         } else {
            var1 = var0.substring(var5, var6);
         }
      }

      return var1;
   }

   public static String getPathQuery(String var0) {
      String var1;
      if(var0 == null) {
         var1 = null;
      } else {
         int var2 = var0.indexOf("//");
         int var4;
         if(var2 >= 0) {
            int var3 = var2 - 1;
            if(var0.lastIndexOf("/", var3) >= 0) {
               var4 = 0;
            } else {
               var4 = var2 + 2;
            }
         } else {
            var4 = 0;
         }

         int var5 = var0.indexOf("/", var4);
         int var6 = var0.length();
         if(var0.lastIndexOf("#") > var5) {
            var6 = var0.lastIndexOf("#");
         }

         if(var5 < 0) {
            if(var2 >= 0) {
               var1 = "/";
            } else {
               var1 = var0;
            }
         } else {
            var1 = var0.substring(var5, var6);
         }
      }

      return var1;
   }

   public static String getQuery(String var0) {
      String var1;
      if(var0 != null && var0.length() != 0) {
         int var2 = var0.indexOf("//");
         int var4;
         if(var2 >= 0) {
            int var3 = var2 - 1;
            if(var0.lastIndexOf("/", var3) >= 0) {
               var4 = 0;
            } else {
               var4 = var2 + 2;
            }
         } else {
            var4 = 0;
         }

         int var5 = var0.indexOf("/", var4);
         int var6 = var0.length();
         int var7 = var0.indexOf("?", var5);
         if(var7 >= 0) {
            int var8 = var7 + 1;
            if(var0.lastIndexOf("#") > var8) {
               var6 = var0.lastIndexOf("#");
            }

            if(var8 >= 0 && var8 != var6) {
               var1 = var0.substring(var8, var6);
            } else {
               var1 = null;
            }
         } else {
            var1 = null;
         }
      } else {
         var1 = null;
      }

      return var1;
   }

   protected static class Coder extends URI {

      protected Coder() {}

      public static String decode(char[] var0, String var1) throws URIException {
         return URI.decode(var0, var1);
      }

      public static char[] encode(String var0, BitSet var1, String var2) throws URIException {
         return URI.encode(var0, var1, var2);
      }

      public static String replace(String var0, char var1, char var2) {
         int var3 = var0.length();
         StringBuffer var4 = new StringBuffer(var3);
         int var5 = 0;

         int var6;
         do {
            var6 = var0.indexOf(var1);
            if(var6 >= 0) {
               String var7 = var0.substring(0, var6);
               var4.append(var7);
               var4.append(var2);
            } else {
               String var10 = var0.substring(var5);
               var4.append(var10);
            }

            var5 = var6;
         } while(var6 >= 0);

         return var4.toString();
      }

      public static String replace(String var0, char[] var1, char[] var2) {
         for(int var3 = var1.length; var3 > 0; var3 += -1) {
            char var4 = var1[var3];
            char var5 = var2[var3];
            var0 = replace(var0, var4, var5);
         }

         return var0;
      }

      public static boolean verifyEscaped(char[] var0) {
         int var1 = 0;

         boolean var4;
         while(true) {
            int var2 = var0.length;
            if(var1 >= var2) {
               var4 = true;
               break;
            }

            char var3 = var0[var1];
            if(var3 > 128) {
               var4 = false;
               break;
            }

            if(var3 == 37) {
               label21: {
                  ++var1;
                  if(Character.digit(var0[var1], 16) != -1) {
                     ++var1;
                     if(Character.digit(var0[var1], 16) != -1) {
                        break label21;
                     }
                  }

                  var4 = false;
                  break;
               }
            }

            ++var1;
         }

         return var4;
      }
   }
}
