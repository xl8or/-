package com.htc.android.mail.eassvc.common;

import com.htc.android.mail.eassvc.common.EASSyncCommon;
import com.htc.android.mail.eassvc.common.ExchangeSyncSources;
import com.htc.android.mail.eassvc.pim.ExchangeAccount;
import com.htc.android.mail.eassvc.provision.EASProvisionDoc;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

public class ExchangeAccountList {

   EASProvisionDoc mAggregateProvisionDoc;
   Vector<ExchangeSyncSources> mList;
   Vector<ExchangeAccountList.AccountChangeListener> mListenerList;


   public ExchangeAccountList() {
      Vector var1 = new Vector();
      this.mList = var1;
      Vector var2 = new Vector();
      this.mListenerList = var2;
      this.mAggregateProvisionDoc = null;
   }

   public void addExchangeSyncSource(ExchangeSyncSources param1) {
      // $FF: Couldn't be decompiled
   }

   public void addListener(ExchangeAccountList.AccountChangeListener var1) {
      this.mListenerList.add(var1);
   }

   public ExchangeSyncSources findExchangeSyncSources(ExchangeAccount var1) {
      Vector var2 = this.mList;
      synchronized(var2) {
         Iterator var3 = this.mList.iterator();

         ExchangeSyncSources var13;
         while(true) {
            if(var3.hasNext()) {
               ExchangeSyncSources var4 = (ExchangeSyncSources)var3.next();
               String var5 = var4.account.accountName;
               String var6 = var1.accountName;
               if(!EASSyncCommon.isStringEqualssIgnoreCase(var5, var6)) {
                  String var7 = var4.account.serverName;
                  String var8 = var1.serverName;
                  if(!EASSyncCommon.isStringEqualssIgnoreCase(var7, var8)) {
                     continue;
                  }

                  String var9 = var4.account.userName;
                  String var10 = var1.userName;
                  if(!EASSyncCommon.isStringEqualssIgnoreCase(var9, var10)) {
                     continue;
                  }
               }

               var13 = var4;
               break;
            }

            var13 = null;
            break;
         }

         return var13;
      }
   }

   public int getAccountCount() {
      int var1 = 0;
      Iterator var2 = this.mList.iterator();

      while(var2.hasNext()) {
         if(((ExchangeSyncSources)var2.next()).account.deleted != 1) {
            ++var1;
         }
      }

      return var1;
   }

   public Vector<ExchangeSyncSources> getAccountList() {
      return this.mList;
   }

   public Vector<Long> getAccountsId() {
      Vector var1 = new Vector();
      Vector var2 = this.mList;
      synchronized(var2) {
         Iterator var3 = this.mList.iterator();

         while(var3.hasNext()) {
            Long var4 = Long.valueOf(((ExchangeSyncSources)var3.next()).account.accountId);
            var1.add(var4);
         }

         return var1;
      }
   }

   public EASProvisionDoc getAggregateProvisionDoc() {
      return this.mAggregateProvisionDoc;
   }

   public Vector<ExchangeSyncSources> getAvailableAccountList() {
      Vector var1 = new Vector();
      Iterator var2 = this.mList.iterator();

      while(var2.hasNext()) {
         ExchangeSyncSources var3 = (ExchangeSyncSources)var2.next();
         if(var3.account.deleted != 1) {
            var1.add(var3);
         }
      }

      return var1;
   }

   public ExchangeSyncSources getAvailableExchangeSyncSources(String var1) {
      Vector var2 = this.mList;
      synchronized(var2) {
         Iterator var3 = this.mList.iterator();

         ExchangeSyncSources var7;
         while(true) {
            if(var3.hasNext()) {
               ExchangeSyncSources var4 = (ExchangeSyncSources)var3.next();
               if(var4.account.deleted == 1 || !EASSyncCommon.isStringEqualssIgnoreCase(var4.account.accountName, var1)) {
                  continue;
               }

               var7 = var4;
               break;
            }

            var7 = null;
            break;
         }

         return var7;
      }
   }

   public ExchangeSyncSources getExchangeSyncSources(long var1) {
      Vector var3 = this.mList;
      synchronized(var3) {
         Iterator var4 = this.mList.iterator();

         ExchangeSyncSources var8;
         while(true) {
            if(var4.hasNext()) {
               ExchangeSyncSources var5 = (ExchangeSyncSources)var4.next();
               if(var5.account.accountId != var1) {
                  continue;
               }

               var8 = var5;
               break;
            }

            var8 = null;
            break;
         }

         return var8;
      }
   }

   public ExchangeSyncSources getExchangeSyncSources(String var1) {
      Vector var2 = this.mList;
      synchronized(var2) {
         Iterator var3 = this.mList.iterator();

         ExchangeSyncSources var7;
         while(true) {
            if(var3.hasNext()) {
               ExchangeSyncSources var4 = (ExchangeSyncSources)var3.next();
               if(!EASSyncCommon.isStringEqualssIgnoreCase(var4.account.accountName, var1)) {
                  continue;
               }

               var7 = var4;
               break;
            }

            var7 = null;
            break;
         }

         return var7;
      }
   }

   public ArrayList<EASProvisionDoc> getProvisionDocList() {
      ArrayList var1 = new ArrayList();
      Iterator var2 = this.mList.iterator();

      while(var2.hasNext()) {
         ExchangeSyncSources var3 = (ExchangeSyncSources)var2.next();
         if(var3.policySet != null && var3.policySet.provisionDoc != null) {
            EASProvisionDoc var4 = var3.policySet.provisionDoc;
            var1.add(var4);
         }
      }

      return var1;
   }

   public boolean isDuplicateDisplayName(String var1) {
      Vector var2 = this.mList;
      synchronized(var2) {
         Iterator var3 = this.mList.iterator();

         while(true) {
            if(var3.hasNext()) {
               ExchangeSyncSources var4 = (ExchangeSyncSources)var3.next();
               if(var4.account.deleted == 1 || !EASSyncCommon.isStringEqualssIgnoreCase(var4.account.displayName, var1)) {
                  continue;
               }

               var2 = null;
               break;
            }

            var2 = null;
            break;
         }

         return (boolean)var2;
      }
   }

   public void release() {
      synchronized(this){}

      try {
         Iterator var1 = this.mList.iterator();

         while(var1.hasNext()) {
            ((ExchangeSyncSources)var1.next()).release();
         }

         this.mList.clear();
         this.mListenerList.clear();
      } finally {
         ;
      }
   }

   public boolean removeExchangeSyncSource(long param1) {
      // $FF: Couldn't be decompiled
   }

   public boolean removeExchangeSyncSource(String param1) {
      // $FF: Couldn't be decompiled
   }

   public void removeListener(ExchangeAccountList.AccountChangeListener var1) {
      this.mListenerList.remove(var1);
   }

   public void setAggregateProvisionDoc(EASProvisionDoc var1) {
      this.mAggregateProvisionDoc = var1;
      Vector var2 = this.mList;
      synchronized(var2) {
         ExchangeSyncSources var4;
         EASProvisionDoc var5;
         for(Iterator var3 = this.mList.iterator(); var3.hasNext(); var4.aggregateProvisionDoc = var5) {
            var4 = (ExchangeSyncSources)var3.next();
            var5 = this.mAggregateProvisionDoc;
         }

      }
   }

   public interface AccountChangeListener {

      void accountAdd(ExchangeSyncSources var1);

      void accountRemove(ExchangeSyncSources var1);
   }
}
