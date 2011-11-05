package org.apache.commons.httpclient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.ProtocolException;
import org.apache.commons.httpclient.Wire;
import org.apache.commons.httpclient.util.EncodingUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HttpParser {

   private static final Log LOG = LogFactory.getLog(HttpParser.class);


   private HttpParser() {}

   public static Header[] parseHeaders(InputStream var0) throws IOException, HttpException {
      LOG.trace("enter HeaderParser.parseHeaders(InputStream, String)");
      return parseHeaders(var0, "US-ASCII");
   }

   public static Header[] parseHeaders(InputStream var0, String var1) throws IOException, HttpException {
      LOG.trace("enter HeaderParser.parseHeaders(InputStream, String)");
      ArrayList var2 = new ArrayList();
      String var3 = null;
      StringBuffer var4 = null;
      StringBuffer var5 = new StringBuffer();

      while(true) {
         String var6 = readLine(var0, var1);
         if(var6 == null || var6.trim().length() < 1) {
            if(var3 != null) {
               String var7 = var4.toString();
               Header var8 = new Header(var3, var7);
               var2.add(var8);
            }

            Header[] var10 = new Header[var2.size()];
            return (Header[])((Header[])var2.toArray(var10));
         }

         if(var6.charAt(0) != 32 && var6.charAt(0) != 9) {
            if(var3 != null) {
               String var14 = var4.toString();
               Header var15 = new Header(var3, var14);
               var2.add(var15);
            }

            int var17 = var6.indexOf(":");
            if(var17 < 0) {
               int var18 = var5.length();
               var5.delete(0, var18);
               String var20 = var5.append("Unable to parse header: ").append(var6).toString();
               throw new ProtocolException(var20);
            }

            var3 = var6.substring(0, var17).trim();
            int var21 = var17 + 1;
            String var22 = var6.substring(var21).trim();
            var4 = new StringBuffer(var22);
         } else if(var4 != null) {
            StringBuffer var11 = var4.append(' ');
            String var12 = var6.trim();
            var4.append(var12);
         }
      }
   }

   public static String readLine(InputStream var0) throws IOException {
      LOG.trace("enter HttpParser.readLine(InputStream)");
      return readLine(var0, "US-ASCII");
   }

   public static String readLine(InputStream var0, String var1) throws IOException {
      LOG.trace("enter HttpParser.readLine(InputStream, String)");
      byte[] var2 = readRawLine(var0);
      String var3;
      if(var2 == null) {
         var3 = null;
      } else {
         int var4 = var2.length;
         int var5 = 0;
         if(var4 > 0) {
            int var6 = var4 - 1;
            if(var2[var6] == 10) {
               ++var5;
               if(var4 > 1) {
                  int var7 = var4 - 2;
                  if(var2[var7] == 13) {
                     ++var5;
                  }
               }
            }
         }

         int var8 = var4 - var5;
         String var9 = EncodingUtil.getString(var2, 0, var8, var1);
         if(Wire.HEADER_WIRE.enabled()) {
            String var10 = var9;
            if(var5 == 2) {
               var10 = var9 + "\r\n";
            } else if(var5 == 1) {
               var10 = var9 + "\n";
            }

            Wire.HEADER_WIRE.input(var10);
         }

         var3 = var9;
      }

      return var3;
   }

   public static byte[] readRawLine(InputStream var0) throws IOException {
      LOG.trace("enter HttpParser.readRawLine()");
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();

      int var2;
      do {
         var2 = var0.read();
         if(var2 < 0) {
            break;
         }

         var1.write(var2);
      } while(var2 != 10);

      byte[] var3;
      if(var1.size() == 0) {
         var3 = null;
      } else {
         var3 = var1.toByteArray();
      }

      return var3;
   }
}
