package com.htc.android.mail;

import android.database.DatabaseUtils;
import android.os.Handler;
import android.os.Message;
import com.htc.android.mail.Account;
import com.htc.android.mail.MailMessage;
import com.htc.android.mail.Request;
import com.htc.android.mail.util.SparseArray;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public abstract class AbsRequestController {

   public static AbsRequestController.GroupStatusMap mGroupStatusMap = new AbsRequestController.GroupStatusMap();
   public static AbsRequestController.MessageStatusMap mMessageStatusMap = new AbsRequestController.MessageStatusMap();


   public AbsRequestController() {}

   public abstract void addRequest(Request var1);

   public abstract void addWeakHandler(WeakReference<Handler> var1);

   public void broadcastAttachmentDownloadChange(Account var1, Message var2, WeakReference<Handler> var3) {}

   public void broadcastAttachmentDownloadComplete(Account var1, Message var2, WeakReference<Handler> var3) {}

   public void broadcastAttachmentDownloadStart(Account var1, Message var2, WeakReference<Handler> var3) {}

   public void broadcastDoComposeView() {}

   public void broadcastSetSortComplete(Account var1, WeakReference<Handler> var2) {}

   public abstract boolean checkIncomingAccount(Account var1, WeakReference<Handler> var2) throws Exception;

   public abstract boolean checkOutgoingAccount(Account var1, WeakReference<Handler> var2) throws Exception;

   public abstract void deleteMail(Request var1);

   public void doInvokeMailSearch() {}

   public void doLaunchAccountList() {}

   public abstract void emptyMailbox(long var1, long var3);

   public abstract int getRefreshCheckMoreNum(long var1);

   public abstract boolean isSending(Account var1);

   public abstract boolean isServerRefreshing(Account var1);

   public abstract void markStar(Request var1);

   public abstract void moveMail(Request var1);

   public abstract int refreshOrCheckMoreMail(Request var1, boolean var2);

   public abstract void registerWeakMailRequestHandler(Account var1, WeakReference<Handler> var2);

   public abstract void removeRequest(long var1);

   public abstract void removeRequest(long var1, int var3);

   public abstract void removeRequest(long var1, WeakReference<Handler> var3);

   public abstract void removeRequest(Request var1);

   public abstract void removeRequest(Request var1, boolean var2);

   public abstract void removeWeakHandler(WeakReference<Handler> var1);

   public abstract void sendMail(Account var1, long var2, int var4);

   public abstract void setReadStatus(Request var1);

   public abstract void stopCheckAccount();

   public abstract void stopRequest(WeakReference<Handler> var1);

   public abstract void unregisterWeakMailRequestHandler(Account var1, WeakReference<Handler> var2);

   public static class MessageStatusMap {

      private Map<String, AbsRequestController.MessageStatus> mMap;


      public MessageStatusMap() {
         HashMap var1 = new HashMap();
         this.mMap = var1;
         Map var2 = this.mMap;
         AbsRequestController.MessageStatus var3 = new AbsRequestController.MessageStatus();
         var2.put("READ", var3);
         Map var5 = this.mMap;
         AbsRequestController.MessageStatus var6 = new AbsRequestController.MessageStatus();
         var5.put("FLAG", var6);
         Map var8 = this.mMap;
         AbsRequestController.MessageStatus var9 = new AbsRequestController.MessageStatus();
         var8.put("DELETE", var9);
      }

      public MessageStatusMap(Map<String, AbsRequestController.MessageStatus> var1) {
         this.mMap = var1;
      }

      public void clear() {
         synchronized(this){}

         try {
            Iterator var1 = this.mMap.keySet().iterator();

            while(var1.hasNext()) {
               String var2 = (String)var1.next();
               AbsRequestController.MessageStatus var3 = (AbsRequestController.MessageStatus)this.mMap.get(var2);
               if(var3 != null) {
                  var3.clear();
               }
            }
         } finally {
            ;
         }

      }

      public AbsRequestController.MessageStatusMap clone() {
         synchronized(this){}

         try {
            HashMap var1 = new HashMap();
            Iterator var2 = this.mMap.entrySet().iterator();

            while(var2.hasNext()) {
               Entry var3 = (Entry)var2.next();
               Object var4 = var3.getKey();
               AbsRequestController.MessageStatus var5 = ((AbsRequestController.MessageStatus)var3.getValue()).clone1();
               var1.put(var4, var5);
            }

            AbsRequestController.MessageStatusMap var8 = new AbsRequestController.MessageStatusMap(var1);
            return var8;
         } finally {
            ;
         }
      }

      public AbsRequestController.MessageStatus get(String var1) {
         synchronized(this){}

         AbsRequestController.MessageStatus var2;
         try {
            var2 = (AbsRequestController.MessageStatus)this.mMap.get(var1);
         } finally {
            ;
         }

         return var2;
      }

      public int size() {
         // $FF: Couldn't be decompiled
      }
   }

   public static class GroupStatus {

      public static final String READ_UNCOMMIT = "READ_UNCOMMIT";
      private SparseArray<Map<String, Integer>> mMapLocalNotUpdate;


      public GroupStatus() {
         SparseArray var1 = new SparseArray();
         this.mMapLocalNotUpdate = var1;
      }

      public void decrement(long param1, String param3) {
         // $FF: Couldn't be decompiled
      }

      public int get(long param1, String param3) {
         // $FF: Couldn't be decompiled
      }

      public void increment(long var1, String var3) {
         synchronized(this){}

         try {
            Object var4 = (Map)this.mMapLocalNotUpdate.get(var1);
            if(var4 == null) {
               var4 = new HashMap();
               this.mMapLocalNotUpdate.put(var1, var4);
            }

            Integer var5 = (Integer)((Map)var4).get(var3);
            int var6 = 0;
            if(var5 != null) {
               var6 = var5.intValue();
            }

            int var7 = var6 + 1;
            Integer var8 = new Integer(var7);
            ((Map)var4).put(var3, var8);
         } finally {
            ;
         }

      }

      public int size() {
         synchronized(this){}
         boolean var5 = false;

         int var1;
         try {
            var5 = true;
            var1 = this.mMapLocalNotUpdate.size();
            var5 = false;
         } finally {
            if(var5) {
               ;
            }
         }

         return var1;
      }
   }

   public static class GroupStatusMap {

      private Map<String, AbsRequestController.GroupStatus> mMap;


      public GroupStatusMap() {
         HashMap var1 = new HashMap();
         this.mMap = var1;
         Map var2 = this.mMap;
         AbsRequestController.GroupStatus var3 = new AbsRequestController.GroupStatus();
         var2.put("READ_UNCOMMIT", var3);
      }

      public AbsRequestController.GroupStatus get(String var1) {
         synchronized(this){}

         AbsRequestController.GroupStatus var2;
         try {
            var2 = (AbsRequestController.GroupStatus)this.mMap.get(var1);
         } finally {
            ;
         }

         return var2;
      }
   }

   public static class MessageStatus {

      public static final String DELETE = "DELETE";
      public static final String FLAG = "FLAG";
      public static final String READ = "READ";
      private Map<MailMessage, Integer> mMapLocalNotUpdate;
      private Set<Long> mSetRemoteNotUpdate;


      public MessageStatus() {
         HashMap var1 = new HashMap();
         this.mMapLocalNotUpdate = var1;
         HashSet var2 = new HashSet();
         this.mSetRemoteNotUpdate = var2;
      }

      public MessageStatus(Map<MailMessage, Integer> var1, Set<Long> var2) {
         this.mMapLocalNotUpdate = var1;
         this.mSetRemoteNotUpdate = var2;
      }

      public void clear() {
         synchronized(this){}

         try {
            this.mMapLocalNotUpdate.clear();
            this.mSetRemoteNotUpdate.clear();
         } finally {
            ;
         }

      }

      public AbsRequestController.MessageStatus clone1() {
         Map var1 = (Map)((HashMap)this.mMapLocalNotUpdate).clone();
         Set var2 = (Set)((HashSet)this.mSetRemoteNotUpdate).clone();
         return new AbsRequestController.MessageStatus(var1, var2);
      }

      public int get(long var1) {
         synchronized(this){}
         boolean var8 = false;

         int var4;
         try {
            var8 = true;
            MailMessage var3 = new MailMessage(var1);
            var4 = this.get(var3);
            var8 = false;
         } finally {
            if(var8) {
               ;
            }
         }

         return var4;
      }

      public int get(MailMessage var1) {
         synchronized(this){}
         boolean var7 = false;

         int var3;
         int var4;
         label50: {
            try {
               var7 = true;
               Integer var2 = (Integer)this.mMapLocalNotUpdate.get(var1);
               if(var2 != null) {
                  var3 = var2.intValue();
                  var7 = false;
                  break label50;
               }

               var7 = false;
            } finally {
               if(var7) {
                  ;
               }
            }

            var4 = -1;
            return var4;
         }

         var4 = var3;
         return var4;
      }

      public MailMessage[] getIdList() {
         // $FF: Couldn't be decompiled
      }

      public String getInvalidateIdList(String var1, boolean var2) {
         synchronized(this){}
         boolean var19 = false;

         String var3;
         String var17;
         try {
            var19 = true;
            if(this.mSetRemoteNotUpdate == null || this.mSetRemoteNotUpdate.size() == 0 || this.isAllUpdateRemoteCompleted()) {
               var3 = "";
               var19 = false;
               return var3;
            }

            StringBuilder var4 = new StringBuilder();
            Iterator var5 = this.mSetRemoteNotUpdate.iterator();
            boolean var6 = true;

            while(var5.hasNext()) {
               Object var7 = var5.next();
               if(var7 != null) {
                  String var8 = var7.toString();
                  if(var6) {
                     var6 = false;
                     if(var2) {
                        String var9 = DatabaseUtils.sqlEscapeString(var8);
                        var4.append(var9);
                     } else {
                        var4.append(var8);
                     }
                  } else {
                     var4.append(var1);
                     if(var2) {
                        String var14 = DatabaseUtils.sqlEscapeString(var8);
                        var4.append(var14);
                     } else {
                        var4.append(var8);
                     }
                  }
               }
            }

            var17 = var4.toString();
            var19 = false;
         } finally {
            if(var19) {
               ;
            }
         }

         var3 = var17;
         return var3;
      }

      public boolean isAllUpdateRemoteCompleted() {
         synchronized(this){}
         boolean var5 = false;

         int var1;
         try {
            var5 = true;
            var1 = this.mSetRemoteNotUpdate.size();
            var5 = false;
         } finally {
            if(var5) {
               ;
            }
         }

         boolean var2;
         if(var1 == 0) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      public void putLocal(MailMessage var1, int var2) {
         synchronized(this){}

         try {
            Map var3 = this.mMapLocalNotUpdate;
            Integer var4 = new Integer(var2);
            var3.put(var1, var4);
            Set var6 = this.mSetRemoteNotUpdate;
            Long var7 = Long.valueOf(var1.id);
            var6.add(var7);
         } finally {
            ;
         }

      }

      public void putRemote(long var1, int var3) {
         synchronized(this){}

         try {
            Set var4 = this.mSetRemoteNotUpdate;
            Long var5 = new Long(var1);
            var4.add(var5);
         } finally {
            ;
         }

      }

      public int size() {
         synchronized(this){}
         boolean var5 = false;

         int var1;
         try {
            var5 = true;
            var1 = this.mMapLocalNotUpdate.size();
            var5 = false;
         } finally {
            if(var5) {
               ;
            }
         }

         return var1;
      }

      public void updateLocalCompleted(MailMessage var1) {
         synchronized(this){}

         try {
            Integer var2 = (Integer)this.mMapLocalNotUpdate.remove(var1);
         } finally {
            ;
         }

      }

      public void updateLocalCompleted(MailMessage var1, int var2) {
         synchronized(this){}

         try {
            Integer var3 = (Integer)this.mMapLocalNotUpdate.get(var1);
            if(var3 != null && var3.intValue() == var2) {
               this.mMapLocalNotUpdate.remove(var1);
            }
         } finally {
            ;
         }

      }

      public void updateRemoteCompleted(long var1) {
         synchronized(this){}

         try {
            Set var3 = this.mSetRemoteNotUpdate;
            Long var4 = new Long(var1);
            var3.remove(var4);
         } finally {
            ;
         }

      }
   }
}
