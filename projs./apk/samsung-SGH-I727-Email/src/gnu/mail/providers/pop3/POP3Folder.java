package gnu.mail.providers.pop3;

import gnu.inet.pop3.POP3Connection;
import gnu.mail.providers.pop3.POP3Message;
import gnu.mail.providers.pop3.POP3Store;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.IllegalWriteException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Store;
import javax.mail.UIDFolder;

public final class POP3Folder extends Folder {

   List deleted;
   Folder inbox;
   boolean open = 0;
   boolean readonly = 0;
   int type;


   protected POP3Folder(Store var1, int var2) {
      super(var1);
      this.type = var2;
   }

   public void appendMessages(Message[] var1) throws MessagingException {
      throw new IllegalWriteException();
   }

   public void close(boolean var1) throws MessagingException {
      if(!this.open) {
         throw new MessagingException("Folder is not open");
      } else {
         if(var1) {
            Message[] var2 = this.expunge();
         }

         this.deleted = null;
         this.open = (boolean)0;
         this.notifyConnectionListeners(3);
      }
   }

   public boolean create(int var1) throws MessagingException {
      throw new IllegalWriteException();
   }

   public boolean delete(boolean var1) throws MessagingException {
      throw new IllegalWriteException("Folder can\'t be deleted");
   }

   public boolean exists() throws MessagingException {
      boolean var1;
      if(this.type == 1) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public Message[] expunge() throws MessagingException {
      if(!this.open) {
         throw new MessagingException("Folder is not open");
      } else if(this.readonly) {
         throw new MessagingException("Folder was opened read-only");
      } else {
         POP3Connection var1 = ((POP3Store)this.store).connection;
         synchronized(var1) {
            try {
               Iterator var2 = this.deleted.iterator();

               while(var2.hasNext()) {
                  int var3 = ((Message)var2.next()).getMessageNumber();
                  var1.dele(var3);
               }
            } catch (IOException var9) {
               String var5 = var9.getMessage();
               throw new MessagingException(var5, var9);
            }
         }

         Message[] var7 = new Message[this.deleted.size()];
         this.deleted.toArray(var7);
         this.deleted.clear();
         return var7;
      }
   }

   public void fetch(Message[] var1, FetchProfile var2) throws MessagingException {
      FetchProfile.Item[] var3 = var2.getItems();
      int var4 = 0;
      boolean var5 = false;
      boolean var6 = false;
      boolean var7 = false;

      while(true) {
         int var8 = var3.length;
         if(var4 >= var8) {
            boolean var13;
            if(var2.getHeaderNames().length > 0) {
               var13 = true;
            } else {
               var13 = var7;
            }

            if(!var13 && !var6 && !var5) {
               return;
            }

            int var16 = 0;

            while(true) {
               int var14 = var1.length;
               if(var16 >= var14) {
                  return;
               }

               if(var1[var16] instanceof POP3Message) {
                  POP3Message var15 = (POP3Message)var1[var16];
                  if(var5) {
                     var15.fetchUid();
                  }

                  if(var6) {
                     var15.fetchContent();
                  } else {
                     var15.fetchHeaders();
                  }
               }

               ++var16;
            }
         }

         FetchProfile.Item var9 = var3[var4];
         UIDFolder.FetchProfileItem var10 = UIDFolder.FetchProfileItem.UID;
         if(var9 == var10) {
            var5 = true;
         } else {
            FetchProfile.Item var11 = var3[var4];
            FetchProfile.Item var12 = FetchProfile.Item.CONTENT_INFO;
            if(var11 == var12) {
               var6 = true;
            } else {
               var7 = true;
            }
         }

         ++var4;
      }
   }

   public Folder getFolder(String var1) throws MessagingException {
      switch(this.type) {
      case 2:
         if(this.inbox == null) {
            Store var2 = this.store;
            POP3Folder var3 = new POP3Folder(var2, 1);
            this.inbox = var3;
         }

         return this.inbox;
      default:
         throw new MessagingException("This folder can\'t contain subfolders");
      }
   }

   public String getFullName() {
      return this.getName();
   }

   public Message getMessage(int var1) throws MessagingException {
      if(!this.open) {
         throw new MessagingException("Folder is not open");
      } else {
         POP3Connection var2 = ((POP3Store)this.store).connection;
         synchronized(var2) {
            POP3Message var4;
            try {
               int var3 = var2.list(var1);
               var4 = new POP3Message(this, var1, var3);
            } catch (IOException var8) {
               String var6 = var8.getMessage();
               throw new MessagingException(var6, var8);
            }

            return var4;
         }
      }
   }

   public int getMessageCount() throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public String getName() {
      String var1;
      switch(this.type) {
      case 1:
         var1 = "INBOX";
         break;
      case 2:
         var1 = "/";
         break;
      default:
         var1 = "(Unknown)";
      }

      return var1;
   }

   public Folder getParent() throws MessagingException {
      POP3Folder var1;
      switch(this.type) {
      case 1:
         var1 = ((POP3Store)this.store).root;
         break;
      default:
         var1 = null;
      }

      return var1;
   }

   public Flags getPermanentFlags() {
      return new Flags();
   }

   public char getSeparator() throws MessagingException {
      return '\u0000';
   }

   public int getType() throws MessagingException {
      return this.type;
   }

   public String getUID(Message var1) throws MessagingException {
      String var2;
      if(var1 instanceof POP3Message) {
         var2 = ((POP3Message)var1).getUID();
      } else {
         var2 = null;
      }

      return var2;
   }

   public boolean hasNewMessages() throws MessagingException {
      boolean var1;
      if(this.getNewMessageCount() > 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isOpen() {
      return this.open;
   }

   public Folder[] list() throws MessagingException {
      switch(this.type) {
      case 2:
         if(this.inbox == null) {
            Store var1 = this.store;
            POP3Folder var2 = new POP3Folder(var1, 1);
            this.inbox = var2;
         }

         Folder[] var3 = new Folder[1];
         Folder var4 = this.inbox;
         var3[0] = var4;
         return var3;
      default:
         throw new MessagingException("This folder can\'t contain subfolders");
      }
   }

   public Folder[] list(String var1) throws MessagingException {
      return this.list();
   }

   public void open(int var1) throws MessagingException {
      switch(var1) {
      case 1:
         this.readonly = (boolean)1;
         break;
      case 2:
         this.readonly = (boolean)0;
         ArrayList var2 = new ArrayList();
         this.deleted = var2;
      }

      this.mode = var1;
      this.open = (boolean)1;
      this.notifyConnectionListeners(1);
   }

   public boolean renameTo(Folder var1) throws MessagingException {
      throw new IllegalWriteException("Folder can\'t be renamed");
   }
}
