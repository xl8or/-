package gnu.mail.providers.imap;

import gnu.inet.imap.IMAPConnection;
import gnu.inet.imap.Quota;
import gnu.mail.providers.imap.IMAPFolder;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.StoreClosedException;
import javax.mail.URLName;
import javax.net.ssl.TrustManager;

public class IMAPStore extends Store {

   protected IMAPConnection connection = null;
   protected IMAPFolder root = null;
   protected IMAPFolder selected = null;


   public IMAPStore(Session var1, URLName var2) {
      super(var1, var2);
   }

   private int getIntProperty(String var1) {
      String var2 = this.getProperty(var1);
      int var4;
      if(var2 != null) {
         label23: {
            int var3;
            try {
               var3 = Integer.parseInt(var2);
            } catch (Exception var6) {
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
      String var3 = "mail.imap." + var1;
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

   protected IMAPConnection getConnection() throws StoreClosedException {
      if(!super.isConnected()) {
         throw new StoreClosedException(this);
      } else {
         return this.connection;
      }
   }

   public Folder getDefaultFolder() throws MessagingException {
      if(this.root == null) {
         IMAPFolder var1 = new IMAPFolder(this, "");
         this.root = var1;
      }

      return this.root;
   }

   public Folder getFolder(String var1) throws MessagingException {
      return new IMAPFolder(this, var1);
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

   public Folder[] getPersonalNamespaces() throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public Quota getQuota(String param1) throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public Folder[] getSharedNamespaces() throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   protected TrustManager getTrustManager() throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public Folder[] getUserNamespaces() throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public boolean isConnected() {
      // $FF: Couldn't be decompiled
   }

   protected boolean isSelected(IMAPFolder var1) {
      IMAPFolder var2 = this.selected;
      return var1.equals(var2);
   }

   protected void processAlerts() {
      String[] var1 = this.connection.getAlerts();
      int var2 = 0;

      while(true) {
         int var3 = var1.length;
         if(var2 >= var3) {
            return;
         }

         String var4 = var1[var2];
         this.notifyStoreListeners(1, var4);
         ++var2;
      }
   }

   protected boolean protocolConnect(String param1, int param2, String param3, String param4) throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public void setQuota(String var1, Quota.Resource[] var2) throws MessagingException {
      if(!super.isConnected()) {
         throw new StoreClosedException(this);
      } else {
         synchronized(this) {
            try {
               this.connection.setquota(var1, var2);
            } catch (IOException var7) {
               String var5 = var7.getMessage();
               throw new MessagingException(var5, var7);
            }

         }
      }
   }

   protected void setSelected(IMAPFolder var1) {
      this.selected = var1;
   }
}
