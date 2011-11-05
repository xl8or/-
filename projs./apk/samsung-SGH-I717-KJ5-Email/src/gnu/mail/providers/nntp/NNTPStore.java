package gnu.mail.providers.nntp;

import gnu.inet.nntp.FileNewsrc;
import gnu.inet.nntp.NNTPConnection;
import gnu.inet.nntp.Newsrc;
import gnu.mail.providers.nntp.NNTPRootFolder;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;

public class NNTPStore extends Store {

   static final Level NNTP_TRACE = NNTPConnection.NNTP_TRACE;
   static final Logger logger = Logger.getLogger("gnu.mail.providers.nntp");
   NNTPConnection connection;
   Newsrc newsrc;
   Flags permanentFlags;
   Folder root;


   public NNTPStore(Session var1, URLName var2) {
      super(var1, var2);
      Flags var3 = new Flags();
      this.permanentFlags = var3;
      Flags var4 = this.permanentFlags;
      Flags.Flag var5 = Flags.Flag.RECENT;
      var4.add(var5);
      Flags var6 = this.permanentFlags;
      Flags.Flag var7 = Flags.Flag.SEEN;
      var6.add(var7);
      String var8 = this.getProperty("newsrc");
      if(var8 != null) {
         try {
            Newsrc var9 = (Newsrc)Thread.currentThread().getContextClassLoader().loadClass(var8).newInstance();
            this.newsrc = var9;
         } catch (Exception var24) {
            Logger var11 = logger;
            Level var12 = NNTP_TRACE;
            var11.log(var12, "ERROR: unable to instantiate newsrc", var24);
         }
      } else {
         String var13 = this.getProperty("newsrc.file");
         File var25;
         if(var13 == null) {
            String var14 = System.getProperty("user.home");
            String var15 = ".newsrc";
            StringBuffer var16 = new StringBuffer(var15);
            if(var2 != null) {
               StringBuffer var17 = var16.append('-');
               String var18 = var2.getHost();
               var16.append(var18);
            }

            String var20 = var16.toString();
            File var21 = new File(var14, var20);
            if(!var21.exists()) {
               var25 = new File(var14, var15);
            } else {
               var25 = var21;
            }
         } else {
            var25 = new File(var13);
         }

         boolean var22 = var1.getDebug();
         FileNewsrc var23 = new FileNewsrc(var25, var22);
         this.newsrc = var23;
      }
   }

   private int getIntProperty(String var1) {
      String var2 = this.getProperty(var1);
      int var4;
      if(var2 != null) {
         label23: {
            int var3;
            try {
               var3 = Integer.parseInt(var2);
            } catch (RuntimeException var6) {
               break label23;
            }

            var4 = var3;
            return var4;
         }
      }

      var4 = -1;
      return var4;
   }

   private String getProperty(String var1) {
      Session var2 = this.session;
      String var3 = "mail.nntp." + var1;
      String var4 = var2.getProperty(var3);
      if(var4 == null) {
         Session var5 = this.session;
         String var6 = "mail." + var1;
         var4 = var5.getProperty(var6);
      }

      return var4;
   }

   private boolean propertyIsFalse(String var1) {
      String var2 = this.getProperty(var1);
      return "false".equals(var2);
   }

   private boolean propertyIsTrue(String var1) {
      String var2 = this.getProperty(var1);
      return "true".equals(var2);
   }

   public void close() throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public Folder getDefaultFolder() throws MessagingException {
      if(this.root == null) {
         NNTPRootFolder var1 = new NNTPRootFolder(this);
         this.root = var1;
      }

      return this.root;
   }

   public Folder getFolder(String var1) throws MessagingException {
      return this.getDefaultFolder().getFolder(var1);
   }

   public Folder getFolder(URLName var1) throws MessagingException {
      Folder var2 = this.getDefaultFolder();
      String var3 = var1.getFile();
      return var2.getFolder(var3);
   }

   boolean isListAll() {
      return this.propertyIsTrue("listall");
   }

   protected boolean protocolConnect(String var1, int var2, String var3, String var4) throws MessagingException {
      boolean var5;
      if(this.connection != null) {
         var5 = true;
      } else {
         String var6;
         if(var1 == null) {
            var6 = this.getProperty("host");
         } else {
            var6 = var1;
         }

         String var7;
         if(var3 == null) {
            var7 = this.getProperty("user");
         } else {
            var7 = var3;
         }

         int var8;
         if(var2 < 0) {
            var8 = this.getIntProperty("port");
         } else {
            var8 = var2;
         }

         if(var6 == null) {
            var5 = false;
         } else {
            boolean var14;
            label60: {
               try {
                  int var9 = this.getIntProperty("connectiontimeout");
                  int var10 = this.getIntProperty("timeout");
                  if(var8 < 0) {
                     var8 = 119;
                  }

                  if(this.session.getDebug()) {
                     Logger var11 = NNTPConnection.logger;
                     Level var12 = NNTPConnection.NNTP_TRACE;
                     var11.setLevel(var12);
                  }

                  NNTPConnection var13 = new NNTPConnection(var6, var8, var9, var10);
                  this.connection = var13;
                  if(var7 != null && var4 != null) {
                     var14 = this.connection.authinfo(var7, var4);
                     break label60;
                  }
               } catch (IOException var17) {
                  String var16 = var17.getMessage();
                  throw new MessagingException(var16, var17);
               }

               var5 = true;
               return var5;
            }

            var5 = var14;
         }
      }

      return var5;
   }
}
