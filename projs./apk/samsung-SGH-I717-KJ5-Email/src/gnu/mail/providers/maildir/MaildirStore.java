package gnu.mail.providers.maildir;

import gnu.inet.util.TraceLevel;
import gnu.mail.providers.maildir.MaildirFolder;
import gnu.mail.treeutil.StatusEvent;
import gnu.mail.treeutil.StatusListener;
import gnu.mail.treeutil.StatusSource;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;

public final class MaildirStore extends Store implements StatusSource {

   static final Level MAILDIR_TRACE = new TraceLevel("maildir");
   static final Logger logger = Logger.getLogger("gnu.mail.util.providers.maildir");
   private static final char separatorChar = '/';
   private List statusListeners;


   public MaildirStore(Session var1, URLName var2) {
      super(var1, var2);
      ArrayList var3 = new ArrayList();
      this.statusListeners = var3;
      if(var1.getDebug()) {
         Logger var4 = logger;
         Level var5 = MAILDIR_TRACE;
         var4.setLevel(var5);
      }
   }

   private boolean exists(String var1) {
      boolean var6;
      if(var1 != null) {
         File var2 = new File(var1);
         char var3 = File.separatorChar;
         if(47 != var3) {
            char var4 = File.separatorChar;
            String var5 = var1.replace('/', var4);
            var2 = new File(var5);
         }

         var6 = var2.exists();
      } else {
         var6 = false;
      }

      return var6;
   }

   private boolean isMaildir(String var1) {
      boolean var2;
      if(var1 == null) {
         var2 = false;
      } else {
         File var3 = new File(var1);
         char var4 = File.separatorChar;
         if(47 != var4) {
            char var5 = File.separatorChar;
            String var6 = var1.replace('/', var5);
            var3 = new File(var6);
         }

         if(var3.exists() && var3.isDirectory()) {
            var2 = true;
         } else {
            var2 = false;
         }
      }

      return var2;
   }

   public void addStatusListener(StatusListener var1) {
      List var2 = this.statusListeners;
      synchronized(var2) {
         this.statusListeners.add(var1);
      }
   }

   public Folder getDefaultFolder() throws MessagingException {
      Object var7;
      if(this.url != null) {
         String var1 = this.url.getFile();
         if(var1 != null && var1.length() > 0) {
            var7 = this.getFolder(var1);
            return (Folder)var7;
         }
      }

      String var2 = this.session.getProperty("mail.maildir.home");
      if(var2 == null) {
         label20: {
            boolean var3;
            try {
               var2 = System.getProperty("user.home");
               var3 = this.exists(var2);
            } catch (SecurityException var6) {
               this.log("access denied reading system properties");
               break label20;
            }

            if(!var3) {
               var2 = null;
            }
         }
      }

      String var4 = this.toFilename(var2);
      var7 = new MaildirFolder(this, var4, (boolean)1, (boolean)0);
      return (Folder)var7;
   }

   public Folder getFolder(String var1) throws MessagingException {
      String var7;
      byte var11;
      label24: {
         if("inbox".equalsIgnoreCase(var1)) {
            String var2 = this.session.getProperty("mail.maildir.maildir");
            if(!this.isMaildir(var2)) {
               label18: {
                  boolean var5;
                  try {
                     String var3 = System.getProperty("user.home");
                     String var4 = var3 + "/Maildir";
                     var5 = this.isMaildir(var4);
                  } catch (SecurityException var10) {
                     this.log("unable to access system properties");
                     break label18;
                  }

                  if(!var5) {
                     ;
                  }
               }
            }

            if(var2 != null) {
               byte var6 = 1;
               var7 = var2;
               var11 = var6;
               break label24;
            }
         }

         var11 = 0;
         var7 = var1;
      }

      String var8 = this.toFilename(var7);
      return new MaildirFolder(this, var8, (boolean)0, (boolean)var11);
   }

   public Folder getFolder(URLName var1) throws MessagingException {
      try {
         String var2 = URLDecoder.decode(var1.getFile(), "UTF-8");
         Folder var3 = this.getFolder(var2);
         return var3;
      } catch (UnsupportedEncodingException var6) {
         String var5 = var6.getMessage();
         throw new MessagingException(var5, var6);
      }
   }

   Session getSession() {
      return this.session;
   }

   void log(String var1) {
      Logger var2 = logger;
      Level var3 = MAILDIR_TRACE;
      var2.log(var3, var1);
   }

   protected void processStatusEvent(StatusEvent param1) {
      // $FF: Couldn't be decompiled
   }

   protected boolean protocolConnect(String var1, int var2, String var3, String var4) throws MessagingException {
      return true;
   }

   public void removeStatusListener(StatusListener var1) {
      List var2 = this.statusListeners;
      synchronized(var2) {
         this.statusListeners.remove(var1);
      }
   }

   String toFilename(String var1) {
      StringBuffer var2 = new StringBuffer();
      if(var1.length() < 1 || var1.charAt(0) != 47) {
         String var3 = File.separator;
         var2.append(var3);
      }

      char var5 = File.separatorChar;
      if(47 != var5) {
         char var6 = File.separatorChar;
         String var7 = var1.replace('/', var6);
         var2.append(var7);
      } else {
         var2.append(var1);
      }

      return var2.toString();
   }
}
