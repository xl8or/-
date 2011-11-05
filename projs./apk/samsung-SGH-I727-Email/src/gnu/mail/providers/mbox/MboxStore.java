package gnu.mail.providers.mbox;

import gnu.inet.util.GetSystemPropertyAction;
import gnu.inet.util.TraceLevel;
import gnu.mail.providers.mbox.MboxFolder;
import gnu.mail.treeutil.StatusEvent;
import gnu.mail.treeutil.StatusListener;
import gnu.mail.treeutil.StatusSource;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;

public final class MboxStore extends Store implements StatusSource {

   static final Level MBOX_TRACE = new TraceLevel("mbox");
   static boolean attemptFallback = 1;
   static final Logger logger = Logger.getLogger("gnu.mail.util.providers.mbox");
   private static final char separatorChar = '/';
   File root;
   private List statusListeners;


   public MboxStore(Session var1, URLName var2) {
      super(var1, var2);
      ArrayList var3 = new ArrayList();
      this.statusListeners = var3;
      String var4 = var1.getProperty("mail.mbox.attemptfallback");
      if(var4 != null) {
         attemptFallback = Boolean.valueOf(var4).booleanValue();
      }

      if(var1.getDebug()) {
         Logger var5 = logger;
         Level var6 = MBOX_TRACE;
         var5.setLevel(var6);
      }
   }

   static String decodeUrlPath(String var0) {
      int var1 = var0.length();
      StringBuffer var2 = null;
      boolean var3 = false;

      int var13;
      for(int var4 = 0; var4 < var1; var13 = var4 + 1) {
         char var5 = var0.charAt(var4);
         if(var5 == 37) {
            int var6 = var1 - 2;
            if(var4 < var6) {
               if(var2 == null) {
                  String var7 = var0.substring(0, var4);
                  var2 = new StringBuffer(var7);
               }

               int var8 = var4 + 1;
               int var9 = var4 + 3;
               int var10 = Integer.parseInt(var0.substring(var8, var9), 16);
               if(var10 > 127) {
                  var3 = true;
               }

               char var11 = (char)var10;
               var2.append(var11);
               var4 += 2;
               continue;
            }
         }

         if(var2 != null) {
            var2.append(var5);
         }
      }

      String var24;
      if(var2 != null) {
         if(!var3) {
            var24 = var2.toString();
         } else {
            int var15 = var2.length();
            byte[] var22 = new byte[var15];

            int var17;
            for(byte var23 = 0; var23 < var15; var17 = var23 + 1) {
               byte var16 = (byte)var2.charAt(var23);
               var22[var23] = var16;
            }

            try {
               var24 = new String(var22, "UTF-8");
            } catch (UnsupportedEncodingException var21) {
               RuntimeException var19 = new RuntimeException();
               Throwable var20 = var19.initCause(var21);
               throw var19;
            }
         }
      } else {
         var24 = var0;
      }

      return var24;
   }

   static String encodeUrlPath(String var0) {
      int var1 = var0.length();
      int var2 = 0;

      String var19;
      while(true) {
         if(var2 >= var1) {
            var19 = var0;
            break;
         }

         char var3 = var0.charAt(var2);
         if(!isUnreservedPathChar(var3)) {
            StringBuffer var5;
            if(true) {
               String var4 = var0.substring(0, var2);
               var5 = new StringBuffer(var4);
            } else {
               var5 = null;
            }

            int var6 = var2 + 1;

            String var11;
            try {
               byte[] var18 = var0.substring(var2, var6).getBytes("UTF-8");
               var2 = 0;

               while(true) {
                  int var7 = var18.length;
                  if(var2 >= var7) {
                     var11 = var5.toString();
                     break;
                  }

                  String var17 = Integer.toHexString(var18[var2]).toUpperCase();
                  StringBuffer var8 = var5.append('%');
                  if(var17.length() < 2) {
                     StringBuffer var9 = var5.append('0');
                  }

                  var5.append(var17);
                  ++var2;
               }
            } catch (UnsupportedEncodingException var16) {
               RuntimeException var13 = new RuntimeException();
               Throwable var14 = var13.initCause(var16);
               throw var13;
            }

            var19 = var11;
            break;
         }

         if(false) {
            StringBuffer var15 = null.append(var3);
         }

         ++var2;
      }

      return var19;
   }

   private Folder getFolder(String var1, boolean var2) throws MessagingException {
      String var3;
      if(File.separatorChar == 92 && var1 != null && var1.startsWith("\\\\\\")) {
         var3 = var1.substring(3);
      } else {
         var3 = var1;
      }

      MboxFolder var5;
      if(var3 != null && !"".equals(var3)) {
         if(File.separatorChar != 47) {
            char var6 = File.separatorChar;
            var3 = var3.replace('/', var6);
         }

         File var8;
         if(this.root != null && this.root.isDirectory()) {
            File var7 = this.root;
            var8 = new File(var7, var3);
         } else {
            var8 = null;
         }

         if((var8 == null || !var8.exists()) && !(new File(var3)).exists() && var2) {
            StringBuilder var9 = new StringBuilder();
            String var10 = File.separator;
            String var11 = var9.append(var10).append(var3).toString();
            new File(var11);
         }

         if("INBOX".equalsIgnoreCase(var3) && !var8.exists()) {
            File var13;
            if(this.root != null && this.root.isFile()) {
               var13 = this.root;
            } else {
               var13 = var8;
            }

            if(!var13.exists()) {
               String var14 = this.session.getProperty("mail.mbox.inbox");
               if(var14 != null) {
                  var13 = new File(var14);
               }
            }

            if(!var13.exists() && attemptFallback && File.separatorChar == 47) {
               if(File.separatorChar == 47) {
                  String var15 = (String)AccessController.doPrivileged(new GetSystemPropertyAction("user.name"));
                  String var16 = "/var/mail/" + var15;
                  File var17 = new File(var16);
                  if(!var17.exists()) {
                     String var18 = "/var/spool/mail/" + var15;
                     var13 = new File(var18);
                  } else {
                     var13 = var17;
                  }
               }

               if(!var13.exists()) {
                  String var19 = (String)AccessController.doPrivileged(new GetSystemPropertyAction("user.home"));
                  var13 = new File(var19, "Mailbox");
               }
            }

            var5 = new MboxFolder(this, var13, (boolean)1);
         } else {
            var5 = new MboxFolder(this, var8, (boolean)0);
         }
      } else if(this.root != null) {
         File var4 = this.root;
         var5 = new MboxFolder(this, var4, (boolean)0);
      } else {
         var5 = null;
      }

      return var5;
   }

   static boolean isUnreservedPathChar(char var0) {
      boolean var1;
      if((var0 < 65 || var0 > 90) && (var0 < 97 || var0 > 122) && (var0 < 48 || var0 > 57) && var0 != 45 && var0 != 46 && var0 != 95 && var0 != 126 && var0 != 47) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public void addStatusListener(StatusListener var1) {
      List var2 = this.statusListeners;
      synchronized(var2) {
         this.statusListeners.add(var1);
      }
   }

   public Folder getDefaultFolder() throws MessagingException {
      return this.getFolder("");
   }

   public Folder getFolder(String var1) throws MessagingException {
      return this.getFolder(var1, (boolean)0);
   }

   public Folder getFolder(URLName var1) throws MessagingException {
      String var2 = var1.getFile();
      if(var2 != null) {
         var2 = decodeUrlPath(var2);
      }

      return this.getFolder(var2, (boolean)1);
   }

   Session getSession() {
      return this.session;
   }

   void log(String var1) {
      Logger var2 = logger;
      Level var3 = MBOX_TRACE;
      var2.log(var3, var1);
   }

   protected void processStatusEvent(StatusEvent param1) {
      // $FF: Couldn't be decompiled
   }

   protected boolean protocolConnect(String var1, int var2, String var3, String var4) throws MessagingException {
      if(this.url != null) {
         String var5 = this.url.getFile();
         if(var5 != null && !"".equals(var5)) {
            var5 = decodeUrlPath(var5);
            if(File.separatorChar != 47) {
               char var6 = File.separatorChar;
               var5.replace('/', var6);
            }

            File var8 = new File(var5);
            this.root = var8;
            if(!this.root.exists() && File.separatorChar == 47) {
               String var9 = "/" + var5;
               File var10 = new File(var9);
               this.root = var10;
            }
         }
      }

      if(this.root == null) {
         String var11 = this.session.getProperty("mail.mbox.mailhome");
         if(var11 != null) {
            File var12 = new File(var11);
            this.root = var12;
         }
      }

      if(this.root == null) {
         String var13 = (String)AccessController.doPrivileged(new GetSystemPropertyAction("user.name"));
         File var14 = new File(var13, "Mail");
         this.root = var14;
         if(!this.root.exists()) {
            File var15 = new File(var13, "mail");
            this.root = var15;
            if(!this.root.exists()) {
               this.root = null;
            }
         }
      }

      return true;
   }

   public void removeStatusListener(StatusListener var1) {
      List var2 = this.statusListeners;
      synchronized(var2) {
         this.statusListeners.remove(var1);
      }
   }

   protected void setURLName(URLName var1) {
      String var2 = var1.getProtocol();
      String var3 = var1.getFile();
      Object var4 = null;
      Object var5 = null;
      URLName var6 = new URLName(var2, (String)null, -1, var3, (String)var4, (String)var5);
      super.setURLName(var6);
   }
}
