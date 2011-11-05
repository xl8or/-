package gnu.mail.providers.nntp;

import gnu.mail.providers.nntp.ListFolderListener;
import gnu.mail.providers.nntp.NNTPFolder;
import gnu.mail.providers.nntp.NNTPStore;
import java.util.Iterator;
import java.util.LinkedList;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.IllegalWriteException;
import javax.mail.Message;
import javax.mail.MessagingException;

public final class NNTPRootFolder extends Folder {

   NNTPRootFolder(NNTPStore var1) {
      super(var1);
   }

   public void appendMessages(Message[] var1) throws MessagingException {
      throw new IllegalWriteException("Folder is read-only");
   }

   public void close(boolean var1) throws MessagingException {}

   public boolean create(int var1) throws MessagingException {
      throw new MessagingException("Folder already exists");
   }

   public boolean delete(boolean var1) throws MessagingException {
      throw new IllegalWriteException("Folder is read-only");
   }

   public boolean exists() throws MessagingException {
      return true;
   }

   public Message[] expunge() throws MessagingException {
      throw new IllegalWriteException("Folder is read-only");
   }

   public Folder getFolder(String var1) throws MessagingException {
      NNTPStore var2 = (NNTPStore)this.store;
      return new NNTPFolder(var2, var1);
   }

   public String getFullName() {
      return ((NNTPStore)this.store).connection.getWelcome();
   }

   public Message getMessage(int var1) throws MessagingException {
      throw new IllegalStateException("Folder not open");
   }

   public int getMessageCount() throws MessagingException {
      return -1;
   }

   public String getName() {
      return ((NNTPStore)this.store).getURLName().getHost();
   }

   public Folder getParent() throws MessagingException {
      return null;
   }

   public Flags getPermanentFlags() {
      return new Flags();
   }

   public char getSeparator() throws MessagingException {
      return '.';
   }

   public int getType() {
      return 2;
   }

   public boolean hasNewMessages() throws MessagingException {
      return false;
   }

   public boolean isOpen() {
      return false;
   }

   public Folder[] list(ListFolderListener var1) throws MessagingException {
      return this.list("%", var1);
   }

   public Folder[] list(String var1) throws MessagingException {
      return this.list(var1, (ListFolderListener)null);
   }

   public Folder[] list(String param1, ListFolderListener param2) throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public Folder[] listSubscribed(String var1) throws MessagingException {
      boolean var2 = false;
      String var3 = var1.replace('%', '*');
      boolean var4;
      if(var3.indexOf(42) > -1) {
         var4 = true;
      } else {
         var4 = false;
      }

      if(var4 && var3.length() == 0) {
         var2 = true;
      }

      NNTPStore var5 = (NNTPStore)this.store;
      LinkedList var6 = new LinkedList();
      Iterator var7 = var5.newsrc.list();

      while(var7.hasNext()) {
         String var8 = (String)var7.next();
         if(!var2) {
            if(var4 && this.matches(var8, var3)) {
               NNTPFolder var9 = new NNTPFolder(var5, var8);
               var6.add(var9);
            } else if(!var4 && var3.equals(var8)) {
               NNTPFolder var11 = new NNTPFolder(var5, var8);
               var6.add(var11);
            }
         }
      }

      Folder[] var13 = new Folder[var6.size()];
      var6.toArray(var13);
      return var13;
   }

   boolean matches(String var1, String var2) {
      int var3 = var2.indexOf(42);
      int var4 = 0;
      int var5 = var3;
      int var6 = 0;

      boolean var10;
      while(true) {
         if(var5 > -1) {
            if(var5 > 0) {
               String var7 = var2.substring(var6, var5);
               int var13 = var7.length();
               String var9 = var1.substring(var4, var13);
               if(!var7.equals(var9)) {
                  var10 = false;
                  break;
               }

               var6 = var5 + 1;
               var4 += var13;
               var5 = 0;
               continue;
            }

            var6 = var5 + 1;
            var5 = var2.indexOf(42, var6);
            String var8;
            if(var5 == -1) {
               var8 = var2.substring(var6);
            } else {
               var8 = var2.substring(var6, var5);
            }

            int var11 = var8.length();
            if(var11 <= 0) {
               continue;
            }

            String var12 = var1.substring(var4, var11);
            if(var8.equals(var12)) {
               continue;
            }

            var10 = false;
            break;
         }

         var10 = true;
         break;
      }

      return var10;
   }

   public void open(int var1) throws MessagingException {
      if(var1 != 1) {
         throw new IllegalWriteException("Folder is read-only");
      }
   }

   public boolean renameTo(Folder var1) throws MessagingException {
      throw new IllegalWriteException("Folder is read-only");
   }

   public void setSubscribed(boolean var1) throws MessagingException {
      if(!var1) {
         throw new IllegalWriteException("Can\'t unsubscribe root folder");
      }
   }
}
