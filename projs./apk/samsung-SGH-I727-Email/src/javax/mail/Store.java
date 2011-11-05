package javax.mail;

import java.util.ArrayList;
import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Service;
import javax.mail.Session;
import javax.mail.URLName;
import javax.mail.event.FolderEvent;
import javax.mail.event.FolderListener;
import javax.mail.event.StoreEvent;
import javax.mail.event.StoreListener;

public abstract class Store extends Service {

   private ArrayList folderListeners = null;
   private ArrayList storeListeners = null;


   protected Store(Session var1, URLName var2) {
      super(var1, var2);
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

   public void addStoreListener(StoreListener var1) {
      if(this.storeListeners == null) {
         ArrayList var2 = new ArrayList();
         this.storeListeners = var2;
      }

      ArrayList var3 = this.storeListeners;
      synchronized(var3) {
         this.storeListeners.add(var1);
      }
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

   void fireNotification(StoreEvent param1) {
      // $FF: Couldn't be decompiled
   }

   public abstract Folder getDefaultFolder() throws MessagingException;

   public abstract Folder getFolder(String var1) throws MessagingException;

   public abstract Folder getFolder(URLName var1) throws MessagingException;

   public Folder[] getPersonalNamespaces() throws MessagingException {
      Folder[] var1 = new Folder[1];
      Folder var2 = this.getDefaultFolder();
      var1[0] = var2;
      return var1;
   }

   public Folder[] getSharedNamespaces() throws MessagingException {
      return new Folder[0];
   }

   public Folder[] getUserNamespaces(String var1) throws MessagingException {
      return new Folder[0];
   }

   protected void notifyFolderListeners(int var1, Folder var2) {
      FolderEvent var3 = new FolderEvent(this, var2, var1);
      switch(var1) {
      case 1:
         this.fireFolderCreated(var3);
         return;
      case 2:
         this.fireFolderDeleted(var3);
         return;
      default:
      }
   }

   protected void notifyFolderRenamedListeners(Folder var1, Folder var2) {
      FolderEvent var3 = new FolderEvent(this, var1, var2, 3);
      this.fireFolderRenamed(var3);
   }

   protected void notifyStoreListeners(int var1, String var2) {
      StoreEvent var3 = new StoreEvent(this, var1, var2);
      this.fireNotification(var3);
   }

   public void removeFolderListener(FolderListener var1) {
      if(this.folderListeners != null) {
         ArrayList var2 = this.folderListeners;
         synchronized(var2) {
            this.folderListeners.remove(var1);
         }
      }
   }

   public void removeStoreListener(StoreListener var1) {
      if(this.storeListeners != null) {
         ArrayList var2 = this.storeListeners;
         synchronized(var2) {
            this.storeListeners.remove(var1);
         }
      }
   }
}
