package org.apache.commons.httpclient;

import java.io.UnsupportedEncodingException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HttpConstants {

   public static final String DEFAULT_CONTENT_CHARSET = "ISO-8859-1";
   public static final String HTTP_ELEMENT_CHARSET = "US-ASCII";
   private static final Log LOG = LogFactory.getLog(HttpConstants.class);


   public HttpConstants() {}

   public static byte[] getAsciiBytes(String var0) {
      if(var0 == null) {
         throw new IllegalArgumentException("Parameter may not be null");
      } else {
         try {
            byte[] var1 = var0.getBytes("US-ASCII");
            return var1;
         } catch (UnsupportedEncodingException var3) {
            throw new RuntimeException("HttpClient requires ASCII support");
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
            throw new RuntimeException("HttpClient requires ASCII support");
         }
      }
   }

   public static byte[] getBytes(String var0) {
      if(var0 == null) {
         throw new IllegalArgumentException("Parameter may not be null");
      } else {
         byte[] var1;
         byte[] var2;
         try {
            var1 = var0.getBytes("US-ASCII");
         } catch (UnsupportedEncodingException var4) {
            if(LOG.isWarnEnabled()) {
               LOG.warn("Unsupported encoding: US-ASCII. System default encoding used");
            }

            var2 = var0.getBytes();
            return var2;
         }

         var2 = var1;
         return var2;
      }
   }

   public static byte[] getContentBytes(String var0) {
      return getContentBytes(var0, (String)null);
   }

   public static byte[] getContentBytes(String var0, String var1) {
      if(var0 == null) {
         throw new IllegalArgumentException("Parameter may not be null");
      } else {
         if(var1 == null || var1.length() == 0) {
            var1 = "ISO-8859-1";
         }

         byte[] var2;
         byte[] var3;
         try {
            var2 = var0.getBytes(var1);
         } catch (UnsupportedEncodingException var9) {
            if(LOG.isWarnEnabled()) {
               Log var5 = LOG;
               String var6 = "Unsupported encoding: " + var1 + ". HTTP default encoding used";
               var5.warn(var6);
            }

            try {
               var2 = var0.getBytes("ISO-8859-1");
            } catch (UnsupportedEncodingException var8) {
               if(LOG.isWarnEnabled()) {
                  LOG.warn("Unsupported encoding: ISO-8859-1. System encoding used");
               }

               var3 = var0.getBytes();
               return var3;
            }

            var3 = var2;
            return var3;
         }

         var3 = var2;
         return var3;
      }
   }

   public static String getContentString(byte[] var0) {
      return getContentString(var0, (String)null);
   }

   public static String getContentString(byte[] var0, int var1, int var2) {
      return getContentString(var0, var1, var2, (String)null);
   }

   public static String getContentString(byte[] var0, int var1, int var2, String var3) {
      if(var0 == null) {
         throw new IllegalArgumentException("Parameter may not be null");
      } else {
         if(var3 == null || var3.length() == 0) {
            var3 = "ISO-8859-1";
         }

         String var4;
         try {
            var4 = new String(var0, var1, var2, var3);
         } catch (UnsupportedEncodingException var10) {
            if(LOG.isWarnEnabled()) {
               Log var6 = LOG;
               String var7 = "Unsupported encoding: " + var3 + ". Default HTTP encoding used";
               var6.warn(var7);
            }

            try {
               var4 = new String(var0, var1, var2, "ISO-8859-1");
            } catch (UnsupportedEncodingException var9) {
               if(LOG.isWarnEnabled()) {
                  LOG.warn("Unsupported encoding: ISO-8859-1. System encoding used");
               }

               var4 = new String(var0, var1, var2);
            }
         }

         return var4;
      }
   }

   public static String getContentString(byte[] var0, String var1) {
      int var2 = var0.length;
      return getContentString(var0, 0, var2, var1);
   }

   public static String getString(byte[] var0) {
      int var1 = var0.length;
      return getString(var0, 0, var1);
   }

   public static String getString(byte[] var0, int var1, int var2) {
      if(var0 == null) {
         throw new IllegalArgumentException("Parameter may not be null");
      } else {
         String var3;
         try {
            var3 = new String(var0, var1, var2, "US-ASCII");
         } catch (UnsupportedEncodingException var5) {
            if(LOG.isWarnEnabled()) {
               LOG.warn("Unsupported encoding: US-ASCII. System default encoding used");
            }

            var3 = new String(var0, var1, var2);
         }

         return var3;
      }
   }
}
