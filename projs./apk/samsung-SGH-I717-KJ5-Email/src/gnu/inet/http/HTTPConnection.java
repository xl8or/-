package gnu.inet.http;

import gnu.inet.http.Cookie;
import gnu.inet.http.CookieManager;
import gnu.inet.http.Request;
import gnu.inet.http.event.ConnectionListener;
import gnu.inet.http.event.RequestListener;
import gnu.inet.util.EmptyX509TrustManager;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

public class HTTPConnection {

   public static final int HTTPS_PORT = 443;
   public static final int HTTP_PORT = 80;
   private static final String userAgent = initUserAgent();
   private final List connectionListeners;
   protected final int connectionTimeout;
   protected CookieManager cookieManager;
   protected final String hostname;
   protected InputStream in;
   protected int majorVersion;
   protected int minorVersion;
   private Map nonceCounts;
   protected OutputStream out;
   protected final int port;
   protected String proxyHostname;
   protected int proxyPort;
   private final List requestListeners;
   protected final boolean secure;
   protected Socket socket;
   protected final int timeout;


   public HTTPConnection(String var1) {
      byte var4 = 0;
      byte var5 = 0;
      this(var1, 80, (boolean)0, var4, var5);
   }

   public HTTPConnection(String var1, int var2) {
      byte var6 = 0;
      byte var7 = 0;
      this(var1, var2, (boolean)0, var6, var7);
   }

   public HTTPConnection(String var1, int var2, boolean var3) {
      byte var8 = 0;
      this(var1, var2, var3, 0, var8);
   }

   public HTTPConnection(String var1, int var2, boolean var3, int var4, int var5) {
      this.hostname = var1;
      this.port = var2;
      this.secure = var3;
      this.connectionTimeout = var4;
      this.timeout = var5;
      this.minorVersion = 1;
      this.majorVersion = 1;
      List var6 = Collections.synchronizedList(new ArrayList(4));
      this.connectionListeners = var6;
      List var7 = Collections.synchronizedList(new ArrayList(4));
      this.requestListeners = var7;
   }

   public HTTPConnection(String var1, boolean var2) {
      short var3;
      if(var2) {
         var3 = 443;
      } else {
         var3 = 80;
      }

      byte var7 = 0;
      this(var1, var3, var2, 0, var7);
   }

   public HTTPConnection(String var1, boolean var2, int var3, int var4) {
      short var5;
      if(var2) {
         var5 = 443;
      } else {
         var5 = 80;
      }

      this(var1, var5, var2, var3, var4);
   }

   private static String initUserAgent() {
      String var10;
      String var11;
      try {
         StringBuffer var0 = new StringBuffer("inetlib/1.1 (");
         String var1 = System.getProperty("os.name");
         var0.append(var1);
         StringBuffer var3 = var0.append("; ");
         String var4 = System.getProperty("os.arch");
         var0.append(var4);
         StringBuffer var6 = var0.append("; ");
         String var7 = System.getProperty("user.language");
         var0.append(var7);
         StringBuffer var9 = var0.append(")");
         var10 = var0.toString();
      } catch (SecurityException var13) {
         var11 = "inetlib/1.1";
         return var11;
      }

      var11 = var10;
      return var11;
   }

   public void addConnectionListener(ConnectionListener var1) {
      List var2 = this.connectionListeners;
      synchronized(var2) {
         this.connectionListeners.add(var1);
      }
   }

   public void addRequestListener(RequestListener var1) {
      List var2 = this.requestListeners;
      synchronized(var2) {
         this.requestListeners.add(var1);
      }
   }

   public void close() throws IOException {
      try {
         this.closeConnection();
      } finally {
         this.fireConnectionEvent(0);
      }

   }

   protected void closeConnection() throws IOException {
      if(this.socket != null) {
         try {
            this.socket.close();
         } finally {
            this.socket = null;
         }

      }
   }

   protected void fireConnectionEvent(int param1) {
      // $FF: Couldn't be decompiled
   }

   protected void fireRequestEvent(int param1, Request param2) {
      // $FF: Couldn't be decompiled
   }

   public CookieManager getCookieManager() {
      return this.cookieManager;
   }

   public String getHostName() {
      return this.hostname;
   }

   protected InputStream getInputStream() throws IOException {
      if(this.socket == null) {
         Socket var1 = this.getSocket();
      }

      return this.in;
   }

   int getNonceCount(String var1) {
      int var2;
      if(this.nonceCounts == null) {
         var2 = 0;
      } else {
         var2 = ((Integer)this.nonceCounts.get(var1)).intValue();
      }

      return var2;
   }

   protected OutputStream getOutputStream() throws IOException {
      if(this.socket == null) {
         Socket var1 = this.getSocket();
      }

      return this.out;
   }

   public int getPort() {
      return this.port;
   }

   protected Socket getSocket() throws IOException {
      if(this.socket == null) {
         String var1 = this.hostname;
         int var2 = this.port;
         String var5;
         int var6;
         if(this.isUsingProxy()) {
            String var3 = this.proxyHostname;
            int var4 = this.proxyPort;
            var5 = var3;
            var6 = var4;
         } else {
            var5 = var1;
            var6 = var2;
         }

         Socket var7 = new Socket();
         this.socket = var7;
         InetAddress var8 = InetAddress.getByName(var5);
         InetSocketAddress var9 = new InetSocketAddress(var8, var6);
         if(this.connectionTimeout > 0) {
            Socket var10 = this.socket;
            int var11 = this.connectionTimeout;
            var10.connect(var9, var11);
         } else {
            this.socket.connect(var9);
         }

         if(this.timeout > 0) {
            Socket var12 = this.socket;
            int var13 = this.timeout;
            var12.setSoTimeout(var13);
         }

         if(this.secure) {
            try {
               EmptyX509TrustManager var14 = new EmptyX509TrustManager();
               SSLContext var15 = SSLContext.getInstance("SSL");
               TrustManager[] var16 = new TrustManager[]{var14};
               var15.init((KeyManager[])null, var16, (SecureRandom)null);
               SSLSocketFactory var17 = var15.getSocketFactory();
               Socket var18 = this.socket;
               SSLSocket var19 = (SSLSocket)var17.createSocket(var18, var5, var6, (boolean)1);
               String[] var20 = new String[]{"TLSv1", "SSLv3"};
               var19.setEnabledProtocols(var20);
               var19.setUseClientMode((boolean)1);
               var19.startHandshake();
               this.socket = var19;
            } catch (GeneralSecurityException var29) {
               String var27 = var29.getMessage();
               throw new IOException(var27);
            }
         }

         InputStream var21 = this.socket.getInputStream();
         this.in = var21;
         InputStream var22 = this.in;
         BufferedInputStream var23 = new BufferedInputStream(var22);
         this.in = var23;
         OutputStream var24 = this.socket.getOutputStream();
         this.out = var24;
         OutputStream var25 = this.out;
         BufferedOutputStream var26 = new BufferedOutputStream(var25);
         this.out = var26;
      }

      return this.socket;
   }

   protected String getURI() {
      StringBuffer var1 = new StringBuffer();
      String var2;
      if(this.secure) {
         var2 = "https://";
      } else {
         var2 = "http://";
      }

      var1.append(var2);
      String var4 = this.hostname;
      var1.append(var4);
      if(this.secure) {
         if(this.port != 443) {
            StringBuffer var6 = var1.append(':');
            int var7 = this.port;
            var1.append(var7);
         }
      } else if(this.port != 80) {
         StringBuffer var9 = var1.append(':');
         int var10 = this.port;
         var1.append(var10);
      }

      return var1.toString();
   }

   public String getVersion() {
      StringBuilder var1 = (new StringBuilder()).append("HTTP/");
      int var2 = this.majorVersion;
      StringBuilder var3 = var1.append(var2).append('.');
      int var4 = this.minorVersion;
      return var3.append(var4).toString();
   }

   void incrementNonce(String var1) {
      int var2 = this.getNonceCount(var1);
      if(this.nonceCounts == null) {
         HashMap var3 = new HashMap();
         this.nonceCounts = var3;
      }

      Map var4 = this.nonceCounts;
      int var5 = var2 + 1;
      Integer var6 = new Integer(var5);
      var4.put(var1, var6);
   }

   public boolean isSecure() {
      return this.secure;
   }

   public boolean isUsingProxy() {
      boolean var1;
      if(this.proxyHostname != null && this.proxyPort > 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public Request newRequest(String var1, String var2) {
      if(var1 != null && var1.length() != 0) {
         String var3;
         if(var2 != null && var2.length() != 0) {
            var3 = var2;
         } else {
            var3 = "/";
         }

         Request var4 = new Request(this, var1, var3);
         if((!this.secure || this.port == 443) && (this.secure || this.port == 80)) {
            String var24 = this.hostname;
            var4.setHeader("Host", var24);
         } else {
            StringBuilder var5 = new StringBuilder();
            String var6 = this.hostname;
            StringBuilder var7 = var5.append(var6).append(":");
            int var8 = this.port;
            String var9 = var7.append(var8).toString();
            var4.setHeader("Host", var9);
         }

         String var10 = userAgent;
         var4.setHeader("User-Agent", var10);
         var4.setHeader("Connection", "keep-alive");
         var4.setHeader("Accept-Encoding", "chunked;q=1.0, gzip;q=0.9, deflate;q=0.8, identity;q=0.6, *;q=0");
         if(this.cookieManager != null) {
            CookieManager var11 = this.cookieManager;
            String var12 = this.hostname;
            boolean var13 = this.secure;
            Cookie[] var14 = var11.getCookies(var12, var13, var3);
            if(var14 != null && var14.length > 0) {
               StringBuffer var15 = new StringBuffer();
               StringBuffer var16 = var15.append("$Version=1");
               byte var17 = 0;

               while(true) {
                  int var18 = var14.length;
                  if(var17 >= var18) {
                     String var25 = var15.toString();
                     var4.setHeader("Cookie", var25);
                     break;
                  }

                  StringBuffer var19 = var15.append(',');
                  StringBuffer var20 = var15.append(' ');
                  String var21 = var14[var17].toString();
                  var15.append(var21);
                  int var23 = var17 + 1;
               }
            }
         }

         this.fireRequestEvent(0, var4);
         return var4;
      } else {
         throw new IllegalArgumentException("method must have non-zero length");
      }
   }

   public void removeConnectionListener(ConnectionListener var1) {
      List var2 = this.connectionListeners;
      synchronized(var2) {
         this.connectionListeners.remove(var1);
      }
   }

   public void removeRequestListener(RequestListener var1) {
      List var2 = this.requestListeners;
      synchronized(var2) {
         this.requestListeners.remove(var1);
      }
   }

   public void setCookieManager(CookieManager var1) {
      this.cookieManager = var1;
   }

   public void setProxy(String var1, int var2) {
      this.proxyHostname = var1;
      this.proxyPort = var2;
   }

   public void setVersion(int var1, int var2) {
      if(var1 != 1) {
         String var3 = "major version not supported: " + var1;
         throw new IllegalArgumentException(var3);
      } else if(var2 >= 0 && var2 <= 1) {
         this.majorVersion = var1;
         this.minorVersion = var2;
      } else {
         String var4 = "minor version not supported: " + var2;
         throw new IllegalArgumentException(var4);
      }
   }
}
