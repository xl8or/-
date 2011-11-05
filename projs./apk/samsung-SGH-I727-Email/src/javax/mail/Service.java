package javax.mail;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;
import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.URLName;
import javax.mail.event.ConnectionEvent;
import javax.mail.event.ConnectionListener;
import javax.mail.event.MailEvent;

public abstract class Service {

   private boolean connected = 0;
   private ArrayList connectionListeners = null;
   protected boolean debug;
   protected Session session;
   protected URLName url;


   protected Service(Session var1, URLName var2) {
      this.session = var1;
      this.url = var2;
      boolean var3 = var1.getDebug();
      this.debug = var3;
   }

   public void addConnectionListener(ConnectionListener var1) {
      if(this.connectionListeners == null) {
         ArrayList var2 = new ArrayList();
         this.connectionListeners = var2;
      }

      ArrayList var3 = this.connectionListeners;
      synchronized(var3) {
         this.connectionListeners.add(var1);
      }
   }

   public void close() throws MessagingException {
      synchronized(this){}

      try {
         this.setConnected((boolean)0);
         this.notifyConnectionListeners(3);
      } finally {
         ;
      }

   }

   public void connect() throws MessagingException {
      this.connect((String)null, (String)null, (String)null);
   }

   public void connect(String var1, int var2, String var3, String var4) throws MessagingException {
      if(this.isConnected()) {
         throw new MessagingException("already connected");
      } else {
         byte var5 = 0;
         boolean var6 = false;
         Object var7 = null;
         Object var8 = null;
         String var19;
         String var21;
         int var20;
         String var23;
         String var22;
         String var24;
         if(this.url != null) {
            String var9 = this.url.getProtocol();
            String var10;
            if(var1 == null) {
               var10 = this.url.getHost();
            } else {
               var10 = var1;
            }

            byte var12 = -1;
            int var13;
            if(var2 == var12) {
               var13 = this.url.getPort();
            } else {
               var13 = var2;
            }

            String var17;
            String var16;
            if(var3 == null) {
               String var14 = this.url.getUsername();
               if(var4 == null) {
                  String var15 = this.url.getPassword();
                  var16 = var14;
                  var17 = var15;
               } else {
                  var16 = var14;
                  var17 = var4;
               }
            } else {
               label128: {
                  if(var4 == null) {
                     String var67 = this.url.getUsername();
                     if(var3.equals(var67)) {
                        var17 = this.url.getPassword();
                        var16 = var3;
                        break label128;
                     }
                  }

                  var17 = var4;
                  var16 = var3;
               }
            }

            String var18 = this.url.getFile();
            var19 = var17;
            var20 = var13;
            var21 = var10;
            var22 = var9;
            var23 = var16;
            var24 = var18;
         } else {
            var24 = (String)var8;
            var19 = var4;
            var20 = var2;
            var21 = var1;
            var22 = (String)var7;
            var23 = var3;
         }

         if(var22 != null) {
            if(var21 == null) {
               Session var25 = this.session;
               String var26 = "mail." + var22 + ".host";
               var21 = var25.getProperty(var26);
            }

            if(var23 == null) {
               Session var27 = this.session;
               String var28 = "mail." + var22 + ".user";
               var27.getProperty(var28);
            }
         }

         if(var21 == null) {
            var21 = this.session.getProperty("mail.host");
         }

         if(var23 == null) {
            String var30 = this.session.getProperty("mail.user");
         }

         String var32;
         if(var23 == null) {
            label117: {
               String var31;
               try {
                  var31 = System.getProperty("user.name");
               } catch (SecurityException var86) {
                  if(this.debug) {
                     var86.printStackTrace();
                  }
                  break label117;
               }

               var32 = var31;
            }
         }

         String var39;
         boolean var40;
         String var41;
         label109: {
            if(var19 == null && this.url != null) {
               URLName var33 = new URLName(var22, var21, var20, var24, var32, var19);
               this.setURLName(var33);
               Session var36 = this.session;
               URLName var37 = this.getURLName();
               PasswordAuthentication var88 = var36.getPasswordAuthentication(var37);
               if(var88 == null) {
                  var40 = true;
                  var41 = var32;
                  var39 = var19;
                  break label109;
               }

               if(var32 == null) {
                  String var38 = var88.getUserName();
                  var39 = var88.getPassword();
                  var40 = var6;
                  var41 = var38;
                  break label109;
               }

               String var72 = var88.getUserName();
               if(var32.equals(var72)) {
                  var39 = var88.getPassword();
                  var40 = var6;
                  var41 = var32;
                  break label109;
               }
            }

            var40 = var6;
            var39 = var19;
            var41 = var32;
         }

         PasswordAuthentication var42 = null;

         byte var51;
         AuthenticationFailedException var50;
         label98: {
            byte var48;
            try {
               var48 = this.protocolConnect(var21, var20, var41, var39);
            } catch (AuthenticationFailedException var85) {
               var50 = var85;
               var51 = var5;
               break label98;
            }

            var50 = var42;
            var51 = var48;
         }

         String var64;
         String var65;
         byte var66;
         label94: {
            if(var51 == 0) {
               Object var52 = null;

               InetAddress var87;
               label88: {
                  InetAddress var89;
                  try {
                     var89 = InetAddress.getByName(var21);
                  } catch (UnknownHostException var84) {
                     var87 = (InetAddress)var52;
                     break label88;
                  }

                  var87 = var89;
               }

               Session var53 = this.session;
               var42 = var53.requestPasswordAuthentication(var87, var20, var22, (String)null, var41);
               if(var42 != null) {
                  String var56 = var42.getUserName();
                  String var57 = var42.getPassword();
                  byte var63 = this.protocolConnect(var21, var20, var56, var57);
                  var64 = var57;
                  var65 = var56;
                  var66 = var63;
                  break label94;
               }
            }

            var64 = var39;
            var65 = var41;
            var66 = var51;
         }

         if(var66 == 0) {
            if(var50 != null) {
               throw var50;
            } else {
               throw new AuthenticationFailedException();
            }
         } else {
            URLName var74 = new URLName(var22, var21, var20, var24, var65, var64);
            this.setURLName(var74);
            if(var40) {
               PasswordAuthentication var77 = new PasswordAuthentication(var65, var64);
               Session var78 = this.session;
               URLName var79 = this.getURLName();
               var78.setPasswordAuthentication(var79, var77);
            }

            byte var81 = 1;
            this.setConnected((boolean)var81);
            byte var83 = 1;
            this.notifyConnectionListeners(var83);
         }
      }
   }

   public void connect(String var1, String var2) throws MessagingException {
      this.connect((String)null, var1, var2);
   }

   public void connect(String var1, String var2, String var3) throws MessagingException {
      this.connect(var1, -1, var2, var3);
   }

   void fireClosed(ConnectionEvent param1) {
      // $FF: Couldn't be decompiled
   }

   void fireDisconnected(ConnectionEvent param1) {
      // $FF: Couldn't be decompiled
   }

   void fireOpened(ConnectionEvent param1) {
      // $FF: Couldn't be decompiled
   }

   public URLName getURLName() {
      URLName var6;
      if(this.url != null && (this.url.getPassword() != null || this.url.getFile() != null)) {
         String var1 = this.url.getProtocol();
         String var2 = this.url.getHost();
         int var3 = this.url.getPort();
         String var4 = this.url.getUsername();
         Object var5 = null;
         var6 = new URLName(var1, var2, var3, (String)null, var4, (String)var5);
      } else {
         var6 = this.url;
      }

      return var6;
   }

   public boolean isConnected() {
      return this.connected;
   }

   protected void notifyConnectionListeners(int var1) {
      ConnectionEvent var2 = new ConnectionEvent(this, var1);
      switch(var1) {
      case 1:
         this.fireOpened(var2);
         return;
      case 2:
         this.fireDisconnected(var2);
         return;
      case 3:
         this.fireClosed(var2);
         return;
      default:
      }
   }

   protected boolean protocolConnect(String var1, int var2, String var3, String var4) throws MessagingException {
      return false;
   }

   protected void queueEvent(MailEvent var1, Vector var2) {
      Iterator var3 = var2.iterator();

      while(var3.hasNext()) {
         Object var4 = var3.next();
         var1.dispatch(var4);
      }

   }

   public void removeConnectionListener(ConnectionListener var1) {
      if(this.connectionListeners != null) {
         ArrayList var2 = this.connectionListeners;
         synchronized(var2) {
            this.connectionListeners.remove(var1);
         }
      }
   }

   protected void setConnected(boolean var1) {
      this.connected = var1;
   }

   protected void setURLName(URLName var1) {
      this.url = var1;
   }

   public String toString() {
      URLName var1 = this.getURLName();
      String var2;
      if(var1 != null) {
         var2 = var1.toString();
      } else {
         var2 = super.toString();
      }

      return var2;
   }
}
