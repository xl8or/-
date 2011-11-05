package com.htc.android.mail;

import android.os.Bundle;
import android.os.Handler;
import com.htc.android.mail.Account;
import com.htc.android.mail.MailProvider;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Request {

   public int command;
   public String filereference;
   public boolean isStopOnLeave;
   public boolean isStopped;
   public boolean isWifiActiveMode;
   private long mAccountId = 65535L;
   private Runnable mCallbackIfNotAdded;
   private ArrayList<Collection> mCollections;
   public int messageWhat;
   public int messageWhatError = -1;
   public int messageWhatStart = -1;
   public Bundle parameter;
   public boolean removeable;
   public Object returnObject;
   public int serviceStartId = -1;
   public WeakReference<Handler> weakHandler;
   public WeakReference<Handler> weakProgressHandler;


   public Request() {
      ArrayList var1 = new ArrayList();
      this.mCollections = var1;
      this.removeable = (boolean)1;
      this.filereference = null;
   }

   public void callbackIfNotAdded() {
      if(this.mCallbackIfNotAdded != null) {
         this.mCallbackIfNotAdded.run();
      }
   }

   public Account getAccount() {
      Account var1 = MailProvider.getAccount(this.mAccountId);
      if(var1 == null) {
         throw new Error("account is null");
      } else {
         return var1;
      }
   }

   public long getAccountId() {
      return this.mAccountId;
   }

   public Request newInstance() {
      Request var1 = new Request();
      int var2 = this.command;
      var1.command = var2;
      Bundle var3 = (Bundle)this.parameter.clone();
      var1.parameter = var3;
      int var4 = this.messageWhatStart;
      var1.messageWhatStart = var4;
      int var5 = this.messageWhatError;
      var1.messageWhatError = var5;
      int var6 = this.messageWhat;
      var1.messageWhat = var6;
      boolean var7 = this.isStopOnLeave;
      var1.isStopOnLeave = var7;
      boolean var8 = this.isStopped;
      var1.isStopped = var8;
      Object var9 = this.returnObject;
      var1.returnObject = var9;
      boolean var10 = this.isWifiActiveMode;
      var1.isWifiActiveMode = var10;
      long var11 = this.mAccountId;
      var1.mAccountId = var11;
      int var13 = this.serviceStartId;
      var1.serviceStartId = var13;
      WeakReference var14 = this.weakHandler;
      var1.weakHandler = var14;
      WeakReference var15 = this.weakProgressHandler;
      var1.weakProgressHandler = var15;
      ArrayList var16 = this.mCollections;
      var1.mCollections = var16;
      var1.filereference = null;
      return var1;
   }

   public void registerCollection(Collection var1) {
      synchronized(this){}

      try {
         this.mCollections.add(var1);
      } finally {
         ;
      }

   }

   public void setAccountId(long var1) {
      this.mAccountId = var1;
   }

   public void setCallbackIfNotAdded(Runnable var1) {
      this.mCallbackIfNotAdded = var1;
   }

   public void unregisterCollection() {
      synchronized(this){}

      boolean var2;
      try {
         for(Iterator var1 = this.mCollections.iterator(); var1.hasNext(); var2 = ((Collection)var1.next()).remove(this)) {
            ;
         }
      } finally {
         ;
      }

   }
}
