package javax.mail;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;

public class URLName {

   private String file;
   protected String fullURL;
   private boolean gotHostAddress;
   private int hashCode;
   private String host;
   private InetAddress hostAddress;
   private String password;
   private int port;
   private String protocol;
   private String ref;
   private String username;


   public URLName(String var1) {
      this.port = -1;
      this.gotHostAddress = (boolean)0;
      this.parseString(var1);
   }

   public URLName(String var1, String var2, int var3, String var4, String var5, String var6) {
      this.port = -1;
      this.gotHostAddress = (boolean)0;
      this.protocol = var1;
      this.host = var2;
      this.port = var3;
      this.file = var4;
      if(var4 != null) {
         int var7 = var4.indexOf(35);
         if(var7 != -1) {
            String var8 = var4.substring(0, var7);
            this.file = var8;
            int var9 = var7 + 1;
            String var10 = var4.substring(var9);
            this.ref = var10;
         }
      }

      this.username = var5;
      this.password = var6;
   }

   public URLName(URL var1) {
      String var2 = var1.toString();
      this(var2);
   }

   private InetAddress getHostAddress() {
      // $FF: Couldn't be decompiled
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(var1 == this) {
         var2 = true;
      } else if(!(var1 instanceof URLName)) {
         var2 = false;
      } else {
         URLName var16 = (URLName)var1;
         if(var16.protocol != null) {
            String var3 = var16.protocol;
            String var4 = this.protocol;
            if(var3.equals(var4)) {
               InetAddress var5 = this.getHostAddress();
               InetAddress var6 = var16.getHostAddress();
               if(var5 != null && var6 != null) {
                  if(!var5.equals(var6)) {
                     var2 = false;
                     return var2;
                  }
               } else if(this.host != null) {
                  String var7 = this.host;
                  String var8 = var16.host;
                  if(!var7.equalsIgnoreCase(var8)) {
                     var2 = false;
                     return var2;
                  }
               }

               String var9 = this.username;
               String var10 = var16.username;
               if(var9 != var10) {
                  label78: {
                     if(this.username != null) {
                        String var11 = this.username;
                        String var12 = var16.username;
                        if(var11.equals(var12)) {
                           break label78;
                        }
                     }

                     var2 = false;
                     return var2;
                  }
               }

               String var13;
               if(this.file != null) {
                  var13 = this.file;
               } else {
                  var13 = "";
               }

               String var17;
               if(var16.file != null) {
                  var17 = var16.file;
               } else {
                  var17 = "";
               }

               if(!var13.equals(var17)) {
                  var2 = false;
                  return var2;
               } else {
                  int var14 = this.port;
                  int var15 = var16.port;
                  if(var14 == var15) {
                     var2 = true;
                  } else {
                     var2 = false;
                  }

                  return var2;
               }
            }
         }

         var2 = false;
      }

      return var2;
   }

   public String getFile() {
      return this.file;
   }

   public String getHost() {
      return this.host;
   }

   public String getPassword() {
      return this.password;
   }

   public int getPort() {
      return this.port;
   }

   public String getProtocol() {
      return this.protocol;
   }

   public String getRef() {
      return this.ref;
   }

   public URL getURL() throws MalformedURLException {
      String var1 = this.getProtocol();
      String var2 = this.getHost();
      int var3 = this.getPort();
      String var4 = this.getFile();
      return new URL(var1, var2, var3, var4);
   }

   public String getUsername() {
      return this.username;
   }

   public int hashCode() {
      int var1;
      if(this.hashCode != 0) {
         var1 = this.hashCode;
      } else {
         if(this.protocol != null) {
            int var2 = this.hashCode;
            int var3 = this.protocol.hashCode();
            int var4 = var2 + var3;
            this.hashCode = var4;
         }

         InetAddress var5 = this.getHostAddress();
         if(var5 != null) {
            int var6 = this.hashCode;
            int var7 = var5.hashCode() + var6;
            this.hashCode = var7;
         } else if(this.host != null) {
            int var17 = this.hashCode;
            int var18 = this.host.toLowerCase().hashCode();
            int var19 = var17 + var18;
            this.hashCode = var19;
         }

         if(this.username != null) {
            int var8 = this.hashCode;
            int var9 = this.username.hashCode();
            int var10 = var8 + var9;
            this.hashCode = var10;
         }

         if(this.file != null) {
            int var11 = this.hashCode;
            int var12 = this.file.hashCode();
            int var13 = var11 + var12;
            this.hashCode = var13;
         }

         int var14 = this.hashCode;
         int var15 = this.port;
         int var16 = var14 + var15;
         this.hashCode = var16;
         var1 = this.hashCode;
      }

      return var1;
   }

   protected void parseString(String var1) {
      this.password = null;
      this.username = null;
      this.host = null;
      this.ref = null;
      this.file = null;
      this.protocol = null;
      this.port = -1;
      String var2 = var1.length();
      int var3 = var1.indexOf(58);
      if(var3 != -1) {
         String var4 = var1.substring(0, var3);
         this.protocol = var4;
      }

      int var5 = var3 + 1;
      if(var1.regionMatches(var5, "//", 0, 2)) {
         int var6 = var3 + 3;
         int var7 = var1.indexOf(47, var6);
         if(var7 != -1) {
            int var8 = var3 + 3;
            String var9 = var1.substring(var8, var7);
            if(var7 + 1 < var2) {
               int var10 = var7 + 1;
               String var11 = var1.substring(var10);
               this.file = var11;
               var2 = var9;
            } else {
               this.file = "";
            }
         } else {
            int var30 = var3 + 3;
            var1.substring(var30);
         }

         int var12 = var2.lastIndexOf(64);
         if(var12 != -1) {
            String var37 = var2.substring(0, var12);
            int var13 = var12 + 1;
            var2.substring(var13);
            var12 = var37.indexOf(58);
            if(var12 != -1) {
               String var15 = var37.substring(0, var12);
               this.username = var15;
               int var16 = var12 + 1;
               String var17 = var37.substring(var16);
               this.password = var17;
            } else {
               this.username = var37;
            }
         }

         int var19;
         if(var2.length() > 0 && var2.charAt(0) == 91) {
            int var18 = var2.indexOf(93);
            var19 = var2.indexOf(58, var18);
         } else {
            int var32 = var2.indexOf(58);
         }

         if(var19 != -1) {
            int var20 = var19 + 1;
            String var21 = var2.substring(var20);
            if(var21.length() > 0) {
               try {
                  int var22 = Integer.parseInt(var21);
                  this.port = var22;
               } catch (NumberFormatException var36) {
                  this.port = -1;
               }
            }

            String var23 = var2.substring(0, var19);
            this.host = var23;
         } else {
            this.host = var2;
         }
      } else if(var3 + 1 < var2) {
         int var34 = var3 + 1;
         String var35 = var1.substring(var34);
         this.file = var35;
      }

      int var24;
      if(this.file != null) {
         var24 = this.file.indexOf(35);
      } else {
         var24 = -1;
      }

      if(var24 != -1) {
         String var25 = this.file;
         int var26 = var24 + 1;
         String var27 = var25.substring(var26);
         this.ref = var27;
         String var28 = this.file.substring(0, var24);
         this.file = var28;
      }
   }

   public String toString() {
      if(this.fullURL == null) {
         StringBuffer var1 = new StringBuffer();
         if(this.protocol != null) {
            String var2 = this.protocol;
            var1.append(var2);
            StringBuffer var4 = var1.append(":");
         }

         if(this.username != null || this.host != null) {
            StringBuffer var5 = var1.append("//");
            if(this.username != null) {
               String var6 = this.username;
               var1.append(var6);
               if(this.password != null) {
                  StringBuffer var8 = var1.append(":");
                  String var9 = this.password;
                  var1.append(var9);
               }

               StringBuffer var11 = var1.append("@");
            }

            if(this.host != null) {
               String var12 = this.host;
               var1.append(var12);
            }

            if(this.port != -1) {
               StringBuffer var14 = var1.append(":");
               String var15 = Integer.toString(this.port);
               var1.append(var15);
            }

            if(this.file != null) {
               StringBuffer var17 = var1.append("/");
            }
         }

         if(this.file != null) {
            String var18 = this.file;
            var1.append(var18);
         }

         if(this.ref != null) {
            StringBuffer var20 = var1.append("#");
            String var21 = this.ref;
            var1.append(var21);
         }

         String var23 = var1.toString();
         this.fullURL = var23;
      }

      return this.fullURL;
   }
}
