package com.android.email;

import android.content.Context;
import com.android.email.MessagingListener;
import com.android.email.mail.MessagingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class GroupMessagingListener extends MessagingListener {

   private Set<MessagingListener> mListeners;
   private ConcurrentHashMap<MessagingListener, Object> mListenersMap;


   public GroupMessagingListener() {
      ConcurrentHashMap var1 = new ConcurrentHashMap();
      this.mListenersMap = var1;
      Set var2 = this.mListenersMap.keySet();
      this.mListeners = var2;
   }

   public void Attachment_StatusStart(long var1, long var3, long var5, long var7) {
      synchronized(this){}

      try {
         Iterator var9 = this.mListeners.iterator();

         while(var9.hasNext()) {
            MessagingListener var10 = (MessagingListener)var9.next();
            var10.Attachment_StatusStart(var1, var3, var5, var7);
         }
      } finally {
         ;
      }

   }

   public void addListener(MessagingListener var1) {
      synchronized(this){}

      try {
         this.mListenersMap.put(var1, this);
      } finally {
         ;
      }

   }

   public void checkMailFinished(Context var1, long var2, long var4, long var6) {
      synchronized(this){}

      try {
         Iterator var8 = this.mListeners.iterator();

         while(var8.hasNext()) {
            MessagingListener var9 = (MessagingListener)var8.next();
            var9.checkMailFinished(var1, var2, var4, var6);
         }
      } finally {
         ;
      }

   }

   public void checkMailStarted(Context var1, long var2, long var4) {
      synchronized(this){}

      try {
         Iterator var6 = this.mListeners.iterator();

         while(var6.hasNext()) {
            MessagingListener var7 = (MessagingListener)var6.next();
            var7.checkMailStarted(var1, var2, var4);
         }
      } finally {
         ;
      }

   }

   public void controllerCommandCompleted(boolean var1) {
      synchronized(this){}

      try {
         Iterator var2 = this.mListeners.iterator();

         while(var2.hasNext()) {
            ((MessagingListener)var2.next()).controllerCommandCompleted(var1);
         }
      } finally {
         ;
      }

   }

   public void foldersCommandFinished(long var1, int var3, String var4, MessagingException var5) {
      synchronized(this){}

      try {
         Iterator var6 = this.mListeners.iterator();

         while(var6.hasNext()) {
            MessagingListener var7 = (MessagingListener)var6.next();
            var7.foldersCommandFinished(var1, var3, var4, var5);
         }
      } finally {
         ;
      }

   }

   public void foldersCommandStarted(long var1, int var3, String var4) {
      synchronized(this){}

      try {
         Iterator var5 = this.mListeners.iterator();

         while(var5.hasNext()) {
            ((MessagingListener)var5.next()).foldersCommandStarted(var1, var3, var4);
         }
      } finally {
         ;
      }

   }

   public boolean isActiveListener(MessagingListener var1) {
      synchronized(this){}
      boolean var6 = false;

      boolean var2;
      try {
         var6 = true;
         var2 = this.mListenersMap.containsKey(var1);
         var6 = false;
      } finally {
         if(var6) {
            ;
         }
      }

      return var2;
   }

   public void loadAttachmentFailed(long var1, long var3, long var5, String var7) {
      synchronized(this){}

      try {
         Iterator var8 = this.mListeners.iterator();

         while(var8.hasNext()) {
            MessagingListener var9 = (MessagingListener)var8.next();
            var9.loadAttachmentFailed(var1, var3, var5, var7);
         }
      } finally {
         ;
      }

   }

   public void loadAttachmentFinished(long var1, long var3, long var5, boolean var7) {
      synchronized(this){}

      try {
         Iterator var8 = this.mListeners.iterator();

         while(var8.hasNext()) {
            MessagingListener var9 = (MessagingListener)var8.next();
            var9.loadAttachmentFinished(var1, var3, var5, var7);
         }
      } finally {
         ;
      }

   }

   public void loadAttachmentStarted(long var1, long var3, long var5, boolean var7) {
      synchronized(this){}

      try {
         Iterator var8 = this.mListeners.iterator();

         while(var8.hasNext()) {
            MessagingListener var9 = (MessagingListener)var8.next();
            var9.loadAttachmentStarted(var1, var3, var5, var7);
         }
      } finally {
         ;
      }

   }

   public void loadMessageForViewFailed(long var1, String var3) {
      synchronized(this){}

      try {
         Iterator var4 = this.mListeners.iterator();

         while(var4.hasNext()) {
            ((MessagingListener)var4.next()).loadMessageForViewFailed(var1, var3);
         }
      } finally {
         ;
      }

   }

   public void loadMessageForViewFinished(long var1) {
      synchronized(this){}

      try {
         Iterator var3 = this.mListeners.iterator();

         while(var3.hasNext()) {
            ((MessagingListener)var3.next()).loadMessageForViewFinished(var1);
         }
      } finally {
         ;
      }

   }

   public void loadMessageForViewStarted(long var1) {
      synchronized(this){}

      try {
         Iterator var3 = this.mListeners.iterator();

         while(var3.hasNext()) {
            ((MessagingListener)var3.next()).loadMessageForViewStarted(var1);
         }
      } finally {
         ;
      }

   }

   public void messageUidChanged(long var1, long var3, String var5, String var6) {
      synchronized(this){}

      try {
         Iterator var7 = this.mListeners.iterator();

         while(var7.hasNext()) {
            MessagingListener var8 = (MessagingListener)var7.next();
            var8.messageUidChanged(var1, var3, var5, var6);
         }
      } finally {
         ;
      }

   }

   public void movemessageToOtherAccountCallback(boolean var1, long var2, long var4, long var6, long var8, long var10, long var12, int var14, int var15) {
      synchronized(this){}

      try {
         Iterator var16 = this.mListeners.iterator();

         while(var16.hasNext()) {
            MessagingListener var17 = (MessagingListener)var16.next();
            var17.movemessageToOtherAccountCallback(var1, var2, var4, var6, var8, var10, var12, var14, var15);
         }
      } finally {
         ;
      }

   }

   public void removeListener(MessagingListener var1) {
      synchronized(this){}

      try {
         this.mListenersMap.remove(var1);
      } finally {
         ;
      }

   }

   public void sendPendingMessagesCompleted(long var1) {
      synchronized(this){}

      try {
         Iterator var3 = this.mListeners.iterator();

         while(var3.hasNext()) {
            ((MessagingListener)var3.next()).sendPendingMessagesCompleted(var1);
         }
      } finally {
         ;
      }

   }

   public void sendPendingMessagesFailed(long var1, long var3, Exception var5) {
      synchronized(this){}

      try {
         Iterator var6 = this.mListeners.iterator();

         while(var6.hasNext()) {
            MessagingListener var7 = (MessagingListener)var6.next();
            var7.sendPendingMessagesFailed(var1, var3, var5);
         }
      } finally {
         ;
      }

   }

   public void sendPendingMessagesStarted(long var1, long var3) {
      synchronized(this){}

      try {
         Iterator var5 = this.mListeners.iterator();

         while(var5.hasNext()) {
            ((MessagingListener)var5.next()).sendPendingMessagesStarted(var1, var3);
         }
      } finally {
         ;
      }

   }

   public void synchronizeMailboxFailed(long var1, long var3, Exception var5) {
      synchronized(this){}

      try {
         Iterator var6 = this.mListeners.iterator();

         while(var6.hasNext()) {
            MessagingListener var7 = (MessagingListener)var6.next();
            var7.synchronizeMailboxFailed(var1, var3, var5);
         }
      } finally {
         ;
      }

   }

   public void synchronizeMailboxFinished(long var1, long var3, int var5, int var6) {
      synchronized(this){}

      try {
         Iterator var7 = this.mListeners.iterator();

         while(var7.hasNext()) {
            MessagingListener var8 = (MessagingListener)var7.next();
            var8.synchronizeMailboxFinished(var1, var3, var5, var6);
         }
      } finally {
         ;
      }

   }

   public void synchronizeMailboxFinishedEx(long var1, long var3, int var5, int var6, ArrayList<Long> var7) {
      synchronized(this){}

      try {
         Iterator var8 = this.mListeners.iterator();

         while(var8.hasNext()) {
            MessagingListener var9 = (MessagingListener)var8.next();
            var9.synchronizeMailboxFinishedEx(var1, var3, var5, var6, var7);
         }
      } finally {
         ;
      }

   }

   public void synchronizeMailboxStarted(long var1, long var3) {
      synchronized(this){}

      try {
         Iterator var5 = this.mListeners.iterator();

         while(var5.hasNext()) {
            ((MessagingListener)var5.next()).synchronizeMailboxStarted(var1, var3);
         }
      } finally {
         ;
      }

   }
}
