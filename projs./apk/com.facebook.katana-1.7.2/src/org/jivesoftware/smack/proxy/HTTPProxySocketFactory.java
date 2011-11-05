package org.jivesoftware.smack.proxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.SocketFactory;
import org.jivesoftware.smack.proxy.ProxyException;
import org.jivesoftware.smack.proxy.ProxyInfo;
import org.jivesoftware.smack.util.Base64;

class HTTPProxySocketFactory extends SocketFactory {

   private static final Pattern RESPONSE_PATTERN = Pattern.compile("HTTP/\\S+\\s(\\d+)\\s(.*)\\s*");
   private ProxyInfo proxy;


   public HTTPProxySocketFactory(ProxyInfo var1) {
      this.proxy = var1;
   }

   private Socket httpProxifiedSocket(String var1, int var2) throws IOException {
      String var3 = this.proxy.getProxyAddress();
      int var4 = this.proxy.getProxyPort();
      Socket var5 = new Socket(var3, var4);
      String var6 = "CONNECT " + var1 + ":" + var2;
      String var7 = this.proxy.getProxyUsername();
      String var8;
      if(var7 == null) {
         var8 = "";
      } else {
         String var18 = this.proxy.getProxyPassword();
         StringBuilder var19 = (new StringBuilder()).append("\r\nProxy-Authorization: Basic ");
         String var20 = Base64.encodeBytes((var7 + ":" + var18).getBytes("UTF-8"));
         String var21 = new String(var20);
         var8 = var19.append(var21).toString();
      }

      OutputStream var9 = var5.getOutputStream();
      byte[] var10 = (var6 + " HTTP/1.1\r\nHost: " + var6 + var8 + "\r\n\r\n").getBytes("UTF-8");
      var9.write(var10);
      InputStream var11 = var5.getInputStream();
      StringBuilder var12 = new StringBuilder(100);
      int var13 = 0;

      do {
         char var14 = (char)var11.read();
         var12.append(var14);
         if(var12.length() > 1024) {
            ProxyInfo.ProxyType var16 = ProxyInfo.ProxyType.HTTP;
            String var17 = "Recieved header of >1024 characters from " + var3 + ", cancelling connection";
            throw new ProxyException(var16, var17);
         }

         if(var14 == -1) {
            ProxyInfo.ProxyType var22 = ProxyInfo.ProxyType.HTTP;
            throw new ProxyException(var22);
         }

         if((var13 == 0 || var13 == 2) && var14 == 13) {
            ++var13;
         } else if((var13 == 1 || var13 == 3) && var14 == 10) {
            ++var13;
         } else {
            var13 = 0;
         }
      } while(var13 != 4);

      if(var13 != 4) {
         ProxyInfo.ProxyType var23 = ProxyInfo.ProxyType.HTTP;
         String var24 = "Never received blank line from " + var3 + ", cancelling connection";
         throw new ProxyException(var23, var24);
      } else {
         String var25 = var12.toString();
         StringReader var26 = new StringReader(var25);
         String var27 = (new BufferedReader(var26)).readLine();
         if(var27 == null) {
            ProxyInfo.ProxyType var28 = ProxyInfo.ProxyType.HTTP;
            String var29 = "Empty proxy response from " + var3 + ", cancelling";
            throw new ProxyException(var28, var29);
         } else {
            Matcher var30 = RESPONSE_PATTERN.matcher(var27);
            if(!var30.matches()) {
               ProxyInfo.ProxyType var31 = ProxyInfo.ProxyType.HTTP;
               String var32 = "Unexpected proxy response from " + var3 + ": " + var27;
               throw new ProxyException(var31, var32);
            } else if(Integer.parseInt(var30.group(1)) != 200) {
               ProxyInfo.ProxyType var33 = ProxyInfo.ProxyType.HTTP;
               throw new ProxyException(var33);
            } else {
               return var5;
            }
         }
      }
   }

   public Socket createSocket(String var1, int var2) throws IOException, UnknownHostException {
      return this.httpProxifiedSocket(var1, var2);
   }

   public Socket createSocket(String var1, int var2, InetAddress var3, int var4) throws IOException, UnknownHostException {
      return this.httpProxifiedSocket(var1, var2);
   }

   public Socket createSocket(InetAddress var1, int var2) throws IOException {
      String var3 = var1.getHostAddress();
      return this.httpProxifiedSocket(var3, var2);
   }

   public Socket createSocket(InetAddress var1, int var2, InetAddress var3, int var4) throws IOException {
      String var5 = var1.getHostAddress();
      return this.httpProxifiedSocket(var5, var2);
   }
}
