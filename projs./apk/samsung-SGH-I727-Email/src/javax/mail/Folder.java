package javax.mail;

import java.util.ArrayList;
import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.FolderNotFoundException;
import javax.mail.Message;
import javax.mail.MessageRemovedException;
import javax.mail.MessagingException;
import javax.mail.MethodNotSupportedException;
import javax.mail.Store;
import javax.mail.URLName;
import javax.mail.event.ConnectionEvent;
import javax.mail.event.ConnectionListener;
import javax.mail.event.FolderEvent;
import javax.mail.event.FolderListener;
import javax.mail.event.MessageChangedEvent;
import javax.mail.event.MessageChangedListener;
import javax.mail.event.MessageCountEvent;
import javax.mail.event.MessageCountListener;
import javax.mail.search.SearchTerm;

public abstract class Folder {

   public static final int HOLDS_FOLDERS = 2;
   public static final int HOLDS_MESSAGES = 1;
   public static final int READ_ONLY = 1;
   public static final int READ_WRITE = 2;
   private volatile ArrayList connectionListeners = null;
   private volatile ArrayList folderListeners = null;
   private volatile ArrayList messageChangedListeners = null;
   private volatile ArrayList messageCountListeners = null;
   protected int mode = -1;
   protected Store store;


   protected Folder(Store var1) {
      this.store = var1;
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

   public void addFolderListener(FolderListener var1) {
      if(this.folderListeners == null) {
         ArrayList var2 = new ArrayList();
         this.folderListeners = var2;
      }

      ArrayList var3 = this.folderListeners;
      synchronized(var3) {
         this.folderListeners.add(var1);
      }
   }

   public void addMessageChangedListener(MessageChangedListener var1) {
      if(this.messageChangedListeners == null) {
         ArrayList var2 = new ArrayList();
         this.messageChangedListeners = var2;
      }

      ArrayList var3 = this.messageChangedListeners;
      synchronized(var3) {
         this.messageChangedListeners.add(var1);
      }
   }

   public void addMessageCountListener(MessageCountListener var1) {
      if(this.messageCountListeners == null) {
         ArrayList var2 = new ArrayList();
         this.messageCountListeners = var2;
      }

      ArrayList var3 = this.messageCountListeners;
      synchronized(var3) {
         this.messageCountListeners.add(var1);
      }
   }

   public abstract void appendMessages(Message[] var1) throws MessagingException;

   public abstract void close(boolean var1) throws MessagingException;

   public void copyMessages(Message[] var1, Folder var2) throws MessagingException {
      if(!var2.exists()) {
         throw new FolderNotFoundException("Folder does not exist", var2);
      } else {
         boolean var3 = var2.isOpen();
         if(!var3) {
            var2.open(2);
         }

         var2.appendMessages(var1);
         if(!var3) {
            var2.close((boolean)0);
         }
      }
   }

   public abstract boolean create(int var1) throws MessagingException;

   public abstract boolean delete(boolean var1) throws MessagingException;

   public abstract boolean exists() throws MessagingException;

   public abstract Message[] expunge() throws MessagingException;

   public void fetch(Message[] var1, FetchProfile var2) throws MessagingException {}

   void fireClosed(ConnectionEvent param1) {
      // $FF: Couldn't be decompiled
   }

   void fireDisconnected(ConnectionEvent param1) {
      // $FF: Couldn't be decompiled
   }

   void fireFolderCreated(FolderEvent param1) {
      // $FF: Couldn't be decompiled
   }

   void fireFolderDeleted(FolderEvent param1) {
      // $FF: Couldn't be decompiled
   }

   void fireFolderRenamed(FolderEvent param1) {
      // $FF: Couldn't be decompiled
   }

   void fireMessageChanged(MessageChangedEvent param1) {
      // $FF: Couldn't be decompiled
   }

   void fireMessagesAdded(MessageCountEvent param1) {
      // $FF: Couldn't be decompiled
   }

   void fireMessagesRemoved(MessageCountEvent param1) {
      // $FF: Couldn't be decompiled
   }

   void fireOpened(ConnectionEvent param1) {
      // $FF: Couldn't be decompiled
   }

   public int getDeletedMessageCount() throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public abstract Folder getFolder(String var1) throws MessagingException;

   public abstract String getFullName();

   public abstract Message getMessage(int var1) throws MessagingException;

   public abstract int getMessageCount() throws MessagingException;

   public Message[] getMessages() throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public Message[] getMessages(int param1, int param2) throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public Message[] getMessages(int[] param1) throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public int getMode() {
      if(!this.isOpen()) {
         throw new IllegalStateException("Folder not open");
      } else {
         return this.mode;
      }
   }

   public abstract String getName();

   public int getNewMessageCount() throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public abstract Folder getParent() throws MessagingException;

   public abstract Flags getPermanentFlags();

   public abstract char getSeparator() throws MessagingException;

   public Store getStore() {
      return this.store;
   }

   public abstract int getType() throws MessagingException;

   public URLName getURLName() throws MessagingException {
      URLName var1 = this.getStore().getURLName();
      String var2 = this.getFullName();
      String var3 = var1.getProtocol();
      String var4 = var1.getHost();
      int var5 = var1.getPort();
      String var6 = var1.getUsername();
      return new URLName(var3, var4, var5, var2, var6, (String)null);
   }

   public int getUnreadMessageCount() throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public abstract boolean hasNewMessages() throws MessagingException;

   public abstract boolean isOpen();

   public boolean isSubscribed() {
      return true;
   }

   public Folder[] list() throws MessagingException {
      return this.list("%");
   }

   public abstract Folder[] list(String var1) throws MessagingException;

   public Folder[] listSubscribed() throws MessagingException {
      return this.listSubscribed("%");
   }

   public Folder[] listSubscribed(String var1) throws MessagingException {
      return this.list(var1);
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

   protected void notifyFolderListeners(int var1) {
      FolderEvent var2 = new FolderEvent(this, this, var1);
      switch(var1) {
      case 1:
         this.fireFolderCreated(var2);
         break;
      case 2:
         this.fireFolderDeleted(var2);
      }

      this.store.notifyFolderListeners(var1, this);
   }

   protected void notifyFolderRenamedListeners(Folder var1) {
      FolderEvent var2 = new FolderEvent(this, this, var1, 3);
      this.fireFolderRenamed(var2);
      this.store.notifyFolderRenamedListeners(this, var1);
   }

   protected void notifyMessageAddedListeners(Message[] var1) {
      MessageCountEvent var2 = new MessageCountEvent(this, 1, (boolean)0, var1);
      this.fireMessagesAdded(var2);
   }

   protected void notifyMessageChangedListeners(int var1, Message var2) {
      MessageChangedEvent var3 = new MessageChangedEvent(this, var1, var2);
      this.fireMessageChanged(var3);
   }

   protected void notifyMessageRemovedListeners(boolean var1, Message[] var2) {
      MessageCountEvent var3 = new MessageCountEvent(this, 2, var1, var2);
      this.fireMessagesRemoved(var3);
   }

   public abstract void open(int var1) throws MessagingException;

   public void removeConnectionListener(ConnectionListener var1) {
      if(this.connectionListeners != null) {
         ArrayList var2 = this.connectionListeners;
         synchronized(var2) {
            this.connectionListeners.remove(var1);
         }
      }
   }

   public void removeFolderListener(FolderListener var1) {
      if(this.folderListeners != null) {
         ArrayList var2 = this.folderListeners;
         synchronized(var2) {
            this.folderListeners.remove(var1);
         }
      }
   }

   public void removeMessageChangedListener(MessageChangedListener var1) {
      if(this.messageChangedListeners != null) {
         ArrayList var2 = this.messageChangedListeners;
         synchronized(var2) {
            this.messageChangedListeners.remove(var1);
         }
      }
   }

   public void removeMessageCountListener(MessageCountListener var1) {
      if(this.messageCountListeners != null) {
         ArrayList var2 = this.messageCountListeners;
         synchronized(var2) {
            this.messageCountListeners.remove(var1);
         }
      }
   }

   public abstract boolean renameTo(Folder var1) throws MessagingException;

   public Message[] search(SearchTerm var1) throws MessagingException {
      Message[] var2 = this.getMessages();
      return this.search(var1, var2);
   }

   public Message[] search(SearchTerm var1, Message[] var2) throws MessagingException {
      ArrayList var3 = new ArrayList();
      int var4 = 0;

      while(true) {
         int var5 = var2.length;
         if(var4 >= var5) {
            Message[] var8 = new Message[var3.size()];
            var3.toArray(var8);
            return var8;
         }

         try {
            if(var2[var4].match(var1)) {
               Message var6 = var2[var4];
               var3.add(var6);
            }
         } catch (MessageRemovedException var11) {
            ;
         }

         ++var4;
      }
   }

   public void setFlags(int var1, int var2, Flags var3, boolean var4) throws MessagingException {
      synchronized(this){}

      for(int var5 = var1; var5 <= var2; ++var5) {
         boolean var10 = false;

         try {
            var10 = true;
            this.getMessage(var5).setFlags(var3, var4);
            var10 = false;
         } catch (MessageRemovedException var11) {
            var10 = false;
         } finally {
            if(var10) {
               ;
            }
         }
      }

   }

   public void setFlags(int[] param1, Flags param2, boolean param3) throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public void setFlags(Message[] param1, Flags param2, boolean param3) throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public void setSubscribed(boolean var1) throws MessagingException {
      throw new MethodNotSupportedException();
   }

   public String toString() {
      String var1 = this.getFullName();
      if(var1 == null) {
         var1 = super.toString();
      }

      return var1;
   }
}
