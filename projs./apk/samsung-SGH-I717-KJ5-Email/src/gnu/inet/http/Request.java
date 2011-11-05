package gnu.inet.http;

import gnu.inet.http.Authenticator;
import gnu.inet.http.ByteArrayRequestBodyWriter;
import gnu.inet.http.ChunkedInputStream;
import gnu.inet.http.Cookie;
import gnu.inet.http.CookieManager;
import gnu.inet.http.HTTPConnection;
import gnu.inet.http.HTTPDateFormat;
import gnu.inet.http.Headers;
import gnu.inet.http.RequestBodyWriter;
import gnu.inet.http.Response;
import gnu.inet.http.ResponseBodyReader;
import gnu.inet.http.ResponseHeaderHandler;
import gnu.inet.util.LineInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ProtocolException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

public class Request {

   protected Authenticator authenticator;
   protected final HTTPConnection connection;
   private boolean dispatched;
   protected final String method;
   byte[] nonce;
   protected final String path;
   protected int requestBodyNegotiationThreshold;
   protected RequestBodyWriter requestBodyWriter;
   protected final Headers requestHeaders;
   protected ResponseBodyReader responseBodyReader;
   protected Map responseHeaderHandlers;


   protected Request(HTTPConnection var1, String var2, String var3) {
      this.connection = var1;
      this.method = var2;
      this.path = var3;
      Headers var4 = new Headers();
      this.requestHeaders = var4;
      HashMap var5 = new HashMap();
      this.responseHeaderHandlers = var5;
      this.requestBodyNegotiationThreshold = 4096;
   }

   boolean authenticate(Response param1, int param2) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public Response dispatch() throws IOException {
      // $FF: Couldn't be decompiled
   }

   byte[] generateNonce() throws IOException, NoSuchAlgorithmException {
      if(this.nonce == null) {
         long var1 = System.currentTimeMillis();
         MessageDigest var3 = MessageDigest.getInstance("MD5");
         byte[] var4 = Long.toString(var1).getBytes("US-ASCII");
         var3.update(var4);
         byte[] var5 = var3.digest();
         this.nonce = var5;
      }

      return this.nonce;
   }

   public HTTPConnection getConnection() {
      return this.connection;
   }

   public Date getDateHeader(String var1) {
      return this.requestHeaders.getDateValue(var1);
   }

   public String getHeader(String var1) {
      return this.requestHeaders.getValue(var1);
   }

   public Headers getHeaders() {
      return this.requestHeaders;
   }

   public int getIntHeader(String var1) {
      return this.requestHeaders.getIntValue(var1);
   }

   public String getMethod() {
      return this.method;
   }

   String getNonceCount(String var1) {
      String var2 = Integer.toHexString(this.connection.getNonceCount(var1));
      StringBuffer var3 = new StringBuffer();
      int var4 = var2.length();

      for(int var5 = 8 - var4; var5 > 0; var5 += -1) {
         StringBuffer var6 = var3.append('0');
      }

      var3.append(var2);
      return var3.toString();
   }

   public String getPath() {
      return this.path;
   }

   public String getRequestURI() {
      StringBuilder var1 = new StringBuilder();
      String var2 = this.connection.getURI();
      StringBuilder var3 = var1.append(var2);
      String var4 = this.path;
      return var3.append(var4).toString();
   }

   void handleSetCookie(String var1) {
      CookieManager var2 = this.connection.getCookieManager();
      if(var2 != null) {
         Object var3 = null;
         Object var4 = null;
         Object var5 = null;
         String var6 = this.connection.getHostName();
         String var7 = this.path;
         int var8 = var7.lastIndexOf(47);
         if(var8 != -1) {
            var7 = var7.substring(0, var8);
         }

         int var9 = var1.length();
         StringBuffer var10 = new StringBuffer();
         byte var11 = 0;
         boolean var12 = false;
         int var13 = var11;
         Object var14 = null;
         String var15 = (String)var3;
         String var16 = (String)var14;
         byte var17 = 0;
         String var18 = (String)var5;
         byte var19 = var17;
         String var21 = var7;
         String var22 = var6;
         Date var24 = null;

         boolean var39;
         for(String var25 = (String)var4; var13 <= var9; var12 = var39) {
            char var26;
            if(var13 == var9) {
               var26 = 0;
            } else {
               var26 = var1.charAt(var13);
            }

            byte var28 = 34;
            String var31;
            String var34;
            String var35;
            String var32;
            String var33;
            String var38;
            byte var36;
            Date var37;
            if(var26 == var28) {
               boolean var29;
               if(!var12) {
                  var29 = true;
               } else {
                  var29 = false;
               }

               var31 = var15;
               var32 = var25;
               var33 = var18;
               var34 = var22;
               var35 = var21;
               var36 = var19;
               var37 = var24;
               var38 = var16;
               var39 = var29;
            } else if(!var12) {
               byte var44 = 61;
               if(var26 == var44 && var16 == null) {
                  String var45 = var10.toString().trim();
                  byte var47 = 0;
                  var10.setLength(var47);
                  var31 = var15;
                  var32 = var25;
                  var33 = var18;
                  var34 = var22;
                  var35 = var21;
                  var36 = var19;
                  var37 = var24;
                  var38 = var45;
                  var39 = var12;
               } else {
                  label109: {
                     byte var50 = 59;
                     if(var26 != var50 && var13 != var9) {
                        byte var52 = 44;
                        if(var26 != var52) {
                           StringBuffer var133 = var10.append(var26);
                           var31 = var15;
                           var32 = var25;
                           var33 = var18;
                           var34 = var22;
                           var35 = var21;
                           var36 = var19;
                           var37 = var24;
                           var38 = var16;
                           var39 = var12;
                           break label109;
                        }
                     }

                     String var53 = var10.toString().trim();
                     String var56 = this.unquote(var53);
                     String var59;
                     byte var58;
                     Date var57;
                     String var63;
                     String var62;
                     String var60;
                     if(var15 == null) {
                        var57 = var24;
                        var58 = var19;
                        var59 = var56;
                        var60 = var16;
                        var62 = var18;
                        var63 = var21;
                     } else {
                        String var85 = "Comment";
                        if(var85.equalsIgnoreCase(var16)) {
                           var63 = var21;
                           var62 = var56;
                           var60 = var15;
                           var57 = var24;
                           var58 = var19;
                           var59 = var25;
                        } else {
                           String var89 = "Domain";
                           if(var89.equalsIgnoreCase(var16)) {
                              var22 = var56;
                              var59 = var25;
                              var58 = var19;
                              var60 = var15;
                              var57 = var24;
                              var62 = var18;
                              var63 = var21;
                           } else {
                              String var94 = "Path";
                              if(var94.equalsIgnoreCase(var16)) {
                                 var62 = var18;
                                 var63 = var56;
                                 var59 = var25;
                                 var58 = var19;
                                 var60 = var15;
                                 var57 = var24;
                              } else {
                                 String var98 = "Secure";
                                 if(var98.equalsIgnoreCase(var56)) {
                                    var59 = var25;
                                    var58 = 1;
                                    var62 = var18;
                                    var63 = var21;
                                    var60 = var15;
                                    var57 = var24;
                                 } else {
                                    String var102 = "Max-Age";
                                    if(var102.equalsIgnoreCase(var16)) {
                                       int var104 = Integer.parseInt(var56);
                                       Calendar var105 = Calendar.getInstance();
                                       long var106 = System.currentTimeMillis();
                                       var105.setTimeInMillis(var106);
                                       byte var112 = 13;
                                       var105.add(var112, var104);
                                       Date var114 = var105.getTime();
                                       var60 = var15;
                                       var57 = var114;
                                       var62 = var18;
                                       var63 = var21;
                                       var58 = var19;
                                       var59 = var25;
                                    } else {
                                       String var117 = "Expires";
                                       if(var117.equalsIgnoreCase(var16)) {
                                          HTTPDateFormat var119 = new HTTPDateFormat();

                                          Date var122;
                                          try {
                                             var122 = var119.parse(var56);
                                          } catch (ParseException var145) {
                                             StringBuffer var129 = var10.append(var26);
                                             var31 = var15;
                                             var32 = var25;
                                             var33 = var18;
                                             var34 = var22;
                                             var35 = var21;
                                             var36 = var19;
                                             var37 = var24;
                                             var38 = var16;
                                             var39 = var12;
                                             break label109;
                                          }

                                          var60 = var15;
                                          var57 = var122;
                                          var62 = var18;
                                          var63 = var21;
                                          var58 = var19;
                                          var59 = var25;
                                       } else {
                                          var60 = var15;
                                          var57 = var24;
                                          var62 = var18;
                                          var63 = var21;
                                          var58 = var19;
                                          var59 = var25;
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }

                     Object var64;
                     label75: {
                        var64 = null;
                        var10.setLength(0);
                        if(var13 != var9) {
                           byte var66 = 44;
                           if(var26 != var66) {
                              break label75;
                           }
                        }

                        Cookie var67 = new Cookie(var60, var59, var62, var22, var63, (boolean)var58, var57);
                        var2.setCookie(var67);
                     }

                     byte var69 = 44;
                     String var76;
                     Date var77;
                     String var79;
                     String var73;
                     String var80;
                     String var83;
                     byte var82;
                     if(var26 == var69) {
                        Object var70 = null;
                        Object var71 = null;
                        Object var72 = null;
                        var73 = this.connection.getHostName();
                        String var74 = this.path;
                        if(var8 != -1) {
                           var74 = var74.substring(0, var8);
                        }

                        Object var75 = null;
                        var76 = (String)var70;
                        var77 = (Date)var75;
                        var79 = (String)var72;
                        var80 = var74;
                        var82 = 0;
                        var83 = (String)var71;
                     } else {
                        var77 = var57;
                        var76 = var60;
                        var82 = var58;
                        var79 = var62;
                        var73 = var22;
                        var80 = var63;
                        var83 = var59;
                     }

                     var32 = var83;
                     var34 = var73;
                     var36 = var82;
                     var38 = (String)var64;
                     var37 = var77;
                     var39 = var12;
                     var31 = var76;
                     var33 = var79;
                     var35 = var80;
                  }
               }
            } else {
               StringBuffer var137 = var10.append(var26);
               var31 = var15;
               var32 = var25;
               var33 = var18;
               var34 = var22;
               var35 = var21;
               var36 = var19;
               var37 = var24;
               var38 = var16;
               var39 = var12;
            }

            ++var13;
            var16 = var38;
            var24 = var37;
            var19 = var36;
            var21 = var35;
            var22 = var34;
            var18 = var33;
            var25 = var32;
            var15 = var31;
         }

      }
   }

   void notifyHeaderHandlers(Headers var1) {
      Iterator var2 = var1.entrySet().iterator();

      while(var2.hasNext()) {
         Entry var3 = (Entry)var2.next();
         String var4 = (String)var3.getKey();
         if("Set-Cookie".equalsIgnoreCase(var4)) {
            String var5 = (String)var3.getValue();
            this.handleSetCookie(var5);
         }

         ResponseHeaderHandler var6 = (ResponseHeaderHandler)this.responseHeaderHandlers.get(var4);
         if(var6 != null) {
            String var7 = (String)var3.getValue();
            var6.setValue(var7);
         }
      }

   }

   Properties parseAuthParams(String var1) {
      int var2 = var1.length();
      StringBuffer var3 = new StringBuffer();
      Properties var4 = new Properties();
      int var5 = 0;
      boolean var6 = false;

      String var7;
      for(var7 = null; var5 < var2; ++var5) {
         char var8 = var1.charAt(var5);
         if(var8 == 34) {
            if(!var6) {
               boolean var9 = true;
            } else {
               boolean var10 = false;
            }
         } else if(var8 == 61 && var7 == null) {
            var7 = var3.toString().trim();
            var3.setLength(0);
         } else if(var8 == 32 && !var6) {
            String var11 = var3.toString().trim();
            String var12 = this.unquote(var11);
            var4.put(var7, var12);
            var3.setLength(0);
            var7 = null;
         } else {
            if(var8 == 44) {
               int var14 = var2 - 1;
               if(var5 >= var14) {
                  continue;
               }

               int var15 = var5 + 1;
               if(var1.charAt(var15) == 32) {
                  continue;
               }
            }

            var3.append(var8);
         }
      }

      if(var7 != null) {
         String var17 = var3.toString().trim();
         String var18 = this.unquote(var17);
         var4.put(var7, var18);
      }

      return var4;
   }

   Response readResponse(LineInputStream var1) throws IOException {
      String var2 = var1.readLine();
      if(var2 == null) {
         throw new ProtocolException("Peer closed connection");
      } else if(!var2.startsWith("HTTP/")) {
         throw new ProtocolException(var2);
      } else {
         int var3 = var2.length();
         byte var4 = 5;

         int var5;
         for(var5 = 6; var2.charAt(var5) != 46; ++var5) {
            ;
         }

         int var6 = Integer.parseInt(var2.substring(var4, var5));
         int var7 = var5 + 1;

         int var8;
         for(var8 = var7 + 1; var2.charAt(var8) != 32; ++var8) {
            ;
         }

         int var9 = Integer.parseInt(var2.substring(var7, var8));
         int var10 = var8 + 1;
         int var11 = var10 + 3;
         int var12 = Integer.parseInt(var2.substring(var10, var11));
         int var13 = var11 + 1;
         int var14 = var3 - 1;
         String var15 = var2.substring(var13, var14);
         Headers var16 = new Headers();
         var16.parse(var1);
         this.notifyHeaderHandlers(var16);
         int var17 = var12 / 100;
         Response var18 = new Response(var6, var9, var12, var17, var15, var16);
         String var19 = this.method;
         if(!"HEAD".equals(var19)) {
            String var20 = this.method;
            if(!"OPTIONS".equals(var20)) {
               switch(var12) {
               case 204:
               case 205:
                  break;
               default:
                  byte var21;
                  if(this.responseBodyReader != null) {
                     var21 = 1;
                  } else {
                     var21 = 0;
                  }

                  if(var21 != 0 && !this.responseBodyReader.accept(this, var18)) {
                     boolean var22 = false;
                  }

                  this.readResponseBody(var18, var1, (boolean)var21);
               }
            }
         }

         return var18;
      }
   }

   void readResponseBody(Response var1, InputStream var2, boolean var3) throws IOException {
      byte[] var4 = new byte[4096];
      Headers var5 = null;
      String var6 = var1.getHeader("Transfer-Encoding");
      Object var7;
      int var8;
      if("chunked".equalsIgnoreCase(var6)) {
         var5 = new Headers();
         var7 = new ChunkedInputStream(var2, var5);
         var8 = -1;
      } else {
         var8 = var1.getIntHeader("Content-Length");
         var7 = var2;
      }

      String var9 = var1.getHeader("Content-Encoding");
      if(var9 != null && !"identity".equals(var9)) {
         if("gzip".equals(var9)) {
            var7 = new GZIPInputStream((InputStream)var7);
         } else {
            if(!"deflate".equals(var9)) {
               String var13 = "Unsupported Content-Encoding: " + var9;
               throw new ProtocolException(var13);
            }

            var7 = new InflaterInputStream((InputStream)var7);
         }
      }

      boolean var12;
      label90: {
         String var10 = this.getHeader("Connection");
         if(!"close".equalsIgnoreCase(var10)) {
            String var11 = var1.getHeader("Connection");
            if(!"close".equalsIgnoreCase(var11) && (this.connection.majorVersion != 1 || this.connection.minorVersion != 0) && (var1.majorVersion != 1 || var1.minorVersion != 0) && var8 != -1) {
               var12 = false;
               break label90;
            }
         }

         var12 = true;
      }

      if(var8 == 0) {
         if(var12) {
            this.connection.closeConnection();
         }
      } else {
         int var14;
         if(var8 > -1) {
            var14 = var8;
         } else {
            var14 = var4.length;
         }

         int var15 = var4.length;
         if(var14 > var15) {
            var14 = var4.length;
         }

         int var17 = var8;
         var8 = var14;

         while(var8 > -1) {
            var8 = ((InputStream)var7).read(var4, 0, var8);
            if(var8 < 0) {
               this.connection.closeConnection();
               break;
            }

            if(var3) {
               this.responseBodyReader.read(var4, 0, var8);
            }

            if(var17 > -1) {
               var17 -= var8;
               if(var17 < 1) {
                  if(var12) {
                     this.connection.closeConnection();
                  }
                  break;
               }
            }
         }
      }

      if(var3) {
         this.responseBodyReader.close();
      }

      if(var5 != null) {
         var1.getHeaders().putAll(var5);
         this.notifyHeaderHandlers(var5);
      }
   }

   public void setAuthenticator(Authenticator var1) {
      this.authenticator = var1;
   }

   public void setHeader(String var1, String var2) {
      this.requestHeaders.put(var1, var2);
   }

   public void setRequestBody(byte[] var1) {
      ByteArrayRequestBodyWriter var2 = new ByteArrayRequestBodyWriter(var1);
      this.setRequestBodyWriter(var2);
   }

   public void setRequestBodyNegotiationThreshold(int var1) {
      this.requestBodyNegotiationThreshold = var1;
   }

   public void setRequestBodyWriter(RequestBodyWriter var1) {
      this.requestBodyWriter = var1;
   }

   public void setResponseBodyReader(ResponseBodyReader var1) {
      this.responseBodyReader = var1;
   }

   public void setResponseHeaderHandler(String var1, ResponseHeaderHandler var2) {
      this.responseHeaderHandlers.put(var1, var2);
   }

   String toHexString(byte[] var1) {
      int var2 = 0;
      char[] var3 = new char[var1.length * 2];
      int var4 = var2;

      while(true) {
         int var5 = var1.length;
         if(var4 >= var5) {
            return new String(var3);
         }

         int var6 = var1[var4];
         if(var6 < 0) {
            var6 += 256;
         }

         int var7 = var2 + 1;
         char var8 = Character.forDigit(var6 / 16, 16);
         var3[var2] = var8;
         var2 = var7 + 1;
         char var9 = Character.forDigit(var6 % 16, 16);
         var3[var7] = var9;
         ++var4;
      }
   }

   String unquote(String var1) {
      int var2 = var1.length();
      String var5;
      if(var2 > 0 && var1.charAt(0) == 34) {
         int var3 = var2 - 1;
         if(var1.charAt(var3) == 34) {
            int var4 = var2 - 1;
            var5 = var1.substring(1, var4);
            return var5;
         }
      }

      var5 = var1;
      return var5;
   }
}
