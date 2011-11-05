package org.apache.commons.httpclient.util;

import java.io.UnsupportedEncodingException;
import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.httpclient.HttpClientError;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EncodingUtil {

   private static final String DEFAULT_CHARSET = "ISO-8859-1";
   private static final Log LOG = LogFactory.getLog(EncodingUtil.class);


   private EncodingUtil() {}

   private static String doFormUrlEncode(NameValuePair[] var0, String var1) throws UnsupportedEncodingException {
      StringBuffer var2 = new StringBuffer();
      int var3 = 0;

      while(true) {
         int var4 = var0.length;
         if(var3 >= var4) {
            return var2.toString();
         }

         URLCodec var5 = new URLCodec();
         NameValuePair var6 = var0[var3];
         if(var6.getName() != null) {
            if(var3 > 0) {
               StringBuffer var7 = var2.append("&");
            }

            String var8 = var6.getName();
            String var9 = var5.encode(var8, var1);
            var2.append(var9);
            StringBuffer var11 = var2.append("=");
            if(var6.getValue() != null) {
               String var12 = var6.getValue();
               String var13 = var5.encode(var12, var1);
               var2.append(var13);
            }
         }

         ++var3;
      }
   }

   public static String formUrlEncode(NameValuePair[] var0, String var1) {
      String var2;
      String var3;
      try {
         var2 = doFormUrlEncode(var0, var1);
      } catch (UnsupportedEncodingException var9) {
         Log var5 = LOG;
         String var6 = "Encoding not supported: " + var1;
         var5.error(var6);

         try {
            var2 = doFormUrlEncode(var0, "ISO-8859-1");
         } catch (UnsupportedEncodingException var8) {
            throw new HttpClientError("Encoding not supported: ISO-8859-1");
         }

         var3 = var2;
         return var3;
      }

      var3 = var2;
      return var3;
   }

   public static byte[] getAsciiBytes(String var0) {
      if(var0 == null) {
         throw new IllegalArgumentException("Parameter may not be null");
      } else {
         try {
            byte[] var1 = var0.getBytes("US-ASCII");
            return var1;
         } catch (UnsupportedEncodingException var3) {
            throw new HttpClientError("HttpClient requires ASCII support");
         }
      }
   }

   public static String getAsciiString(byte[] var0) {
      int var1 = var0.length;
      return getAsciiString(var0, 0, var1);
   }

   public static String getAsciiString(byte[] var0, int var1, int var2) {
      if(var0 == null) {
         throw new IllegalArgumentException("Parameter may not be null");
      } else {
         try {
            String var3 = new String(var0, var1, var2, "US-ASCII");
            return var3;
         } catch (UnsupportedEncodingException var5) {
            throw new HttpClientError("HttpClient requires ASCII support");
         }
      }
   }

   public static byte[] getBytes(String var0, String var1) {
      if(var0 == null) {
         throw new IllegalArgumentException("data may not be null");
      } else if(var1 != null && var1.length() != 0) {
         byte[] var2;
         byte[] var3;
         try {
            var2 = var0.getBytes(var1);
         } catch (UnsupportedEncodingException var7) {
            if(LOG.isWarnEnabled()) {
               Log var5 = LOG;
               String var6 = "Unsupported encoding: " + var1 + ". System encoding used.";
               var5.warn(var6);
            }

            var3 = var0.getBytes();
            return var3;
         }

         var3 = var2;
         return var3;
      } else {
         throw new IllegalArgumentException("charset may not be null or empty");
      }
   }

   public static String getString(byte[] var0, int var1, int var2, String var3) {
      if(var0 == null) {
         throw new IllegalArgumentException("Parameter may not be null");
      } else if(var3 != null && var3.length() != 0) {
         String var4;
         try {
            var4 = new String(var0, var1, var2, var3);
         } catch (UnsupportedEncodingException var8) {
            if(LOG.isWarnEnabled()) {
               Log var6 = LOG;
               String var7 = "Unsupported encoding: " + var3 + ". System encoding used";
               var6.warn(var7);
            }

            var4 = new String(var0, var1, var2);
         }

         return var4;
      } else {
         throw new IllegalArgumentException("charset may not be null or empty");
      }
   }

   public static String getString(byte[] var0, String var1) {
      int var2 = var0.length;
      return getString(var0, 0, var2, var1);
   }
}
