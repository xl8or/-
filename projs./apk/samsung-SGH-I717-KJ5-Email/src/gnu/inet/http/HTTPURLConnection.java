package gnu.inet.http;

import gnu.inet.http.Authenticator;
import gnu.inet.http.ByteArrayRequestBodyWriter;
import gnu.inet.http.ByteArrayResponseBodyReader;
import gnu.inet.http.Credentials;
import gnu.inet.http.HTTPConnection;
import gnu.inet.http.Headers;
import gnu.inet.http.Request;
import gnu.inet.http.Response;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class HTTPURLConnection extends HttpURLConnection {

   private static final Map connectionPool = new LinkedHashMap();
   private String agent;
   private HTTPConnection connection;
   private ByteArrayInputStream errorSink;
   private boolean keepAlive;
   private int maxConnections;
   private String proxyHostname;
   private int proxyPort;
   private Request request;
   private Headers requestHeaders;
   private boolean requestMethodSetExplicitly;
   private ByteArrayOutputStream requestSink;
   private Response response;
   private ByteArrayInputStream responseSink;


   public HTTPURLConnection(URL var1) {
      super(var1);
      Headers var2 = new Headers();
      this.requestHeaders = var2;
      Object var3 = AccessController.doPrivileged(new HTTPURLConnection.GetHTTPPropertiesAction());
   }

   public void addRequestProperty(String var1, String var2) {
      String var3 = this.requestHeaders.getValue(var1);
      if(var3 == null) {
         this.requestHeaders.put(var1, var2);
      } else {
         Headers var5 = this.requestHeaders;
         String var6 = var3 + "," + var2;
         var5.put(var1, var6);
      }
   }

   public void connect() throws IOException {
      if(!this.connected) {
         String var1 = this.url.getProtocol();
         Object var2 = "https".equals(var1);
         String var3 = this.url.getHost();
         int var4 = this.url.getPort();
         if(var4 < 0) {
            if(var2 != false) {
               var4 = 443;
            } else {
               var4 = 80;
            }
         }

         String var5;
         String var10;
         String var11;
         label118: {
            var5 = this.url.getFile();
            String var6 = this.url.getUserInfo();
            if(var6 != null) {
               int var7 = var6.indexOf(58);
               if(var7 != -1) {
                  int var8 = var7 + 1;
                  String var9 = var6.substring(var8);
                  var10 = var6.substring(0, var7);
                  var11 = var9;
                  break label118;
               }
            }

            var10 = var6;
            var11 = null;
         }

         Credentials var12;
         if(var10 == null) {
            var12 = null;
         } else {
            var12 = new Credentials(var10, var11);
         }

         String var14 = (String)var2;
         String var15 = var5;
         int var17 = var4;
         String var18 = var3;

         while(true) {
            if(this.connection == null) {
               HTTPConnection var19 = this.getConnection(var18, var17, (boolean)var14);
               this.connection = var19;
            }

            if(this.proxyHostname != null) {
               if(this.proxyPort < 0) {
                  short var20;
                  if(var14 != false) {
                     var20 = 443;
                  } else {
                     var20 = 80;
                  }

                  this.proxyPort = var20;
               }

               HTTPConnection var21 = this.connection;
               String var22 = this.proxyHostname;
               int var23 = this.proxyPort;
               var21.setProxy(var22, var23);
            }

            HTTPConnection var24 = this.connection;
            String var25 = this.method;
            Request var26 = var24.newRequest(var25, var15);
            this.request = var26;
            if(!this.keepAlive) {
               this.request.setHeader("Connection", "close");
            }

            if(this.agent != null) {
               Request var27 = this.request;
               String var28 = this.agent;
               var27.setHeader("User-Agent", var28);
            }

            Headers var29 = this.request.getHeaders();
            Headers var30 = this.requestHeaders;
            var29.putAll(var30);
            if(this.requestSink != null) {
               byte[] var31 = this.requestSink.toByteArray();
               ByteArrayRequestBodyWriter var32 = new ByteArrayRequestBodyWriter(var31);
               this.request.setRequestBodyWriter(var32);
            }

            ByteArrayResponseBodyReader var33 = new ByteArrayResponseBodyReader();
            this.request.setResponseBodyReader(var33);
            if(var12 != null) {
               Request var34 = this.request;
               HTTPURLConnection.1 var35 = new HTTPURLConnection.1(var12);
               var34.setAuthenticator(var35);
            }

            int var42;
            String var43;
            String var41;
            boolean var44;
            label127: {
               Response var36 = this.request.dispatch();
               this.response = var36;
               if(this.response.getCodeClass() == 3 && this.getInstanceFollowRedirects()) {
                  String var37 = this.response.getHeader("Location");
                  if(var37 != null) {
                     String var38 = this.connection.getURI();
                     int var39 = var38.length();
                     if(var37.startsWith(var38) && var37.charAt(var39) == 47) {
                        String var40 = var37.substring(var39);
                        var10 = var14;
                        var41 = var18;
                        var42 = var17;
                        var43 = var40;
                        var44 = true;
                        break label127;
                     }

                     if(var37.startsWith("http:")) {
                        this.connection.close();
                        this.connection = null;
                        int var45 = var37.indexOf(47, 7);
                        String var46 = var37.substring(7, var45);
                        int var47 = var46.lastIndexOf(58);
                        int var51;
                        String var50;
                        if(var47 != -1) {
                           int var48 = var47 + 1;
                           int var49 = Integer.parseInt(var46.substring(var48));
                           var50 = var46.substring(0, var47);
                           var51 = var49;
                        } else {
                           byte var53 = 80;
                           var50 = var46;
                           var51 = var53;
                        }

                        var43 = var37.substring(var45);
                        var41 = var50;
                        boolean var52 = false;
                        var42 = var51;
                        var44 = true;
                        break label127;
                     }

                     if(var37.startsWith("https:")) {
                        this.connection.close();
                        this.connection = null;
                        int var54 = var37.indexOf(47, 8);
                        String var55 = var37.substring(8, var54);
                        int var56 = var55.lastIndexOf(58);
                        String var59;
                        int var60;
                        if(var56 != -1) {
                           int var57 = var56 + 1;
                           int var58 = Integer.parseInt(var55.substring(var57));
                           var59 = var55.substring(0, var56);
                           var60 = var58;
                        } else {
                           short var62 = 443;
                           var59 = var55;
                           var60 = var62;
                        }

                        var43 = var37.substring(var54);
                        var41 = var59;
                        boolean var61 = true;
                        var42 = var60;
                        var44 = true;
                        break label127;
                     }

                     if(var37.length() > 0) {
                        String var63;
                        if(var37.charAt(0) == 47) {
                           var63 = var37;
                        } else {
                           int var65 = var15.lastIndexOf(47);
                           String var66;
                           if(var65 == -1) {
                              var66 = "/";
                           } else {
                              int var67 = var65 + 1;
                              var66 = var15.substring(0, var67);
                           }

                           var63 = var66 + var37;
                        }

                        var41 = var18;
                        var42 = var17;
                        var43 = var63;
                        var44 = true;
                        break label127;
                     }
                  }
               } else {
                  byte[] var68 = var33.toByteArray();
                  ByteArrayInputStream var69 = new ByteArrayInputStream(var68);
                  this.responseSink = var69;
                  if(this.response.getCode() == 404) {
                     ByteArrayInputStream var70 = this.responseSink;
                     this.errorSink = var70;
                     String var71 = this.url.toString();
                     throw new FileNotFoundException(var71);
                  }
               }

               var10 = var14;
               var41 = var18;
               var42 = var17;
               var43 = var15;
               var44 = false;
            }

            if(!var44) {
               this.connected = (boolean)1;
               return;
            }

            var15 = var43;
            var17 = var42;
            var18 = var41;
            var14 = var10;
         }
      }
   }

   public void disconnect() {
      if(this.connection != null) {
         try {
            this.connection.close();
         } catch (IOException var2) {
            ;
         }
      }
   }

   HTTPConnection getConnection(String param1, int param2, boolean param3) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public String getContentType() {
      return this.getHeaderField("Content-Type");
   }

   public InputStream getErrorStream() {
      return this.errorSink;
   }

   public String getHeaderField(int var1) {
      String var3;
      if(!this.connected) {
         try {
            this.connect();
         } catch (IOException var7) {
            var3 = null;
            return var3;
         }
      }

      if(var1 == 0) {
         Response var2 = this.response;
         var3 = this.getStatusLine(var2);
      } else {
         Iterator var5 = this.response.getHeaders().entrySet().iterator();
         int var6 = 1;

         while(true) {
            if(!var5.hasNext()) {
               var3 = null;
               break;
            }

            Entry var8 = (Entry)var5.next();
            ++var6;
            if(var6 > var1) {
               var3 = (String)var8.getValue();
               break;
            }
         }
      }

      return var3;
   }

   public String getHeaderField(String var1) {
      String var2;
      if(!this.connected) {
         try {
            this.connect();
         } catch (IOException var4) {
            var2 = null;
            return var2;
         }
      }

      var2 = this.response.getHeader(var1);
      return var2;
   }

   public long getHeaderFieldDate(String var1, long var2) {
      long var5;
      if(!this.connected) {
         try {
            this.connect();
         } catch (IOException var8) {
            var5 = var2;
            return var5;
         }
      }

      Date var4 = this.response.getDateHeader(var1);
      if(var4 == null) {
         var5 = var2;
      } else {
         var5 = var4.getTime();
      }

      return var5;
   }

   public String getHeaderFieldKey(int var1) {
      String var2;
      if(!this.connected) {
         try {
            this.connect();
         } catch (IOException var6) {
            var2 = null;
            return var2;
         }
      }

      if(var1 == 0) {
         var2 = null;
      } else {
         Iterator var4 = this.response.getHeaders().entrySet().iterator();
         int var5 = 1;

         while(true) {
            if(!var4.hasNext()) {
               var2 = null;
               break;
            }

            Entry var7 = (Entry)var4.next();
            ++var5;
            if(var5 > var1) {
               var2 = (String)var7.getKey();
               break;
            }
         }
      }

      return var2;
   }

   public Map getHeaderFields() {
      LinkedHashMap var12;
      if(!this.connected) {
         try {
            this.connect();
         } catch (IOException var13) {
            var12 = null;
            return var12;
         }
      }

      Headers var1 = this.response.getHeaders();
      LinkedHashMap var2 = new LinkedHashMap();
      Response var3 = this.response;
      List var4 = Collections.singletonList(this.getStatusLine(var3));
      var2.put("", var4);
      Iterator var6 = var1.entrySet().iterator();

      while(var6.hasNext()) {
         Entry var7 = (Entry)var6.next();
         String var8 = (String)var7.getKey();
         List var9 = Collections.singletonList((String)var7.getValue());
         var2.put(var8, var9);
      }

      var12 = var2;
      return var12;
   }

   public InputStream getInputStream() throws IOException {
      if(!this.connected) {
         this.connect();
      }

      if(!this.doInput) {
         throw new ProtocolException("doInput is false");
      } else {
         return this.responseSink;
      }
   }

   public OutputStream getOutputStream() throws IOException {
      if(this.connected) {
         throw new ProtocolException("Already connected");
      } else if(!this.doOutput) {
         throw new ProtocolException("doOutput is false");
      } else {
         if(!this.requestMethodSetExplicitly) {
            this.method = "POST";
         }

         if(this.requestSink == null) {
            ByteArrayOutputStream var1 = new ByteArrayOutputStream();
            this.requestSink = var1;
         }

         return this.requestSink;
      }
   }

   public Map getRequestProperties() {
      return this.requestHeaders;
   }

   public String getRequestProperty(String var1) {
      return this.requestHeaders.getValue(var1);
   }

   public int getResponseCode() throws IOException {
      if(!this.connected) {
         this.connect();
      }

      return this.response.getCode();
   }

   public String getResponseMessage() throws IOException {
      if(!this.connected) {
         this.connect();
      }

      return this.response.getMessage();
   }

   String getStatusLine(Response var1) {
      StringBuilder var2 = (new StringBuilder()).append("HTTP/");
      int var3 = var1.getMajorVersion();
      StringBuilder var4 = var2.append(var3).append(".");
      int var5 = var1.getMinorVersion();
      StringBuilder var6 = var4.append(var5).append(" ");
      int var7 = var1.getCode();
      StringBuilder var8 = var6.append(var7).append(" ");
      String var9 = var1.getMessage();
      return var8.append(var9).toString();
   }

   public void setRequestMethod(String var1) throws ProtocolException {
      if(this.connected) {
         throw new ProtocolException("Already connected");
      } else {
         String var2 = var1.toUpperCase();
         int var3 = var2.length();
         if(var3 == 0) {
            throw new ProtocolException("Empty method name");
         } else {
            for(int var4 = 0; var4 < var3; ++var4) {
               char var5 = var2.charAt(var4);
               if(var5 < 65 || var5 > 90) {
                  String var6 = "Illegal character \'" + var5 + "\' at index " + var4;
                  throw new ProtocolException(var6);
               }
            }

            this.method = var2;
            this.requestMethodSetExplicitly = (boolean)1;
         }
      }
   }

   public void setRequestProperty(String var1, String var2) {
      this.requestHeaders.put(var1, var2);
   }

   public boolean usingProxy() {
      boolean var1;
      if(this.proxyHostname != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   class GetHTTPPropertiesAction implements PrivilegedAction {

      GetHTTPPropertiesAction() {}

      public Object run() {
         HTTPURLConnection var1 = HTTPURLConnection.this;
         String var2 = System.getProperty("http.proxyHost");
         var1.proxyHostname = var2;
         if(HTTPURLConnection.this.proxyHostname != null && HTTPURLConnection.this.proxyHostname.length() > 0) {
            String var4 = System.getProperty("http.proxyPort");
            if(var4 != null && var4.length() > 0) {
               HTTPURLConnection var5 = HTTPURLConnection.this;
               int var6 = Integer.parseInt(var4);
               var5.proxyPort = var6;
            } else {
               String var18 = HTTPURLConnection.this.proxyHostname = null;
               int var19 = HTTPURLConnection.this.proxyPort = -1;
            }
         }

         HTTPURLConnection var8 = HTTPURLConnection.this;
         String var9 = System.getProperty("http.agent");
         var8.agent = var9;
         String var11 = System.getProperty("http.keepAlive");
         HTTPURLConnection var12 = HTTPURLConnection.this;
         byte var13;
         if(var11 != null && "false".equals(var11)) {
            var13 = 0;
         } else {
            var13 = 1;
         }

         var12.keepAlive = (boolean)var13;
         String var15 = System.getProperty("http.maxConnections");
         HTTPURLConnection var16 = HTTPURLConnection.this;
         int var20;
         if(var15 != null && var15.length() > 0) {
            var20 = Math.max(Integer.parseInt(var15), 1);
         } else {
            var20 = 5;
         }

         var16.maxConnections = var20;
         return null;
      }
   }

   class 1 implements Authenticator {

      // $FF: synthetic field
      final Credentials val$creds;


      1(Credentials var2) {
         this.val$creds = var2;
      }

      public Credentials getCredentials(String var1, int var2) {
         Credentials var3;
         if(var2 < 2) {
            var3 = this.val$creds;
         } else {
            var3 = null;
         }

         return var3;
      }
   }
}
