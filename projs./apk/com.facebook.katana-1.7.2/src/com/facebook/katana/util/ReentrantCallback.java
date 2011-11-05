package com.facebook.katana.util;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ReentrantCallback<CallbackClass extends Object> {

   // $FF: synthetic field
   static final boolean $assertionsDisabled;
   private final Set<CallbackClass> mListenerPendingAdditions;
   private final Set<CallbackClass> mListenerPendingDeletions;
   private final Set<CallbackClass> mListeners;


   static {
      byte var0;
      if(!ReentrantCallback.class.desiredAssertionStatus()) {
         var0 = 1;
      } else {
         var0 = 0;
      }

      $assertionsDisabled = (boolean)var0;
   }

   public ReentrantCallback() {
      HashSet var1 = new HashSet();
      this.mListeners = var1;
      HashSet var2 = new HashSet();
      this.mListenerPendingAdditions = var2;
      HashSet var3 = new HashSet();
      this.mListenerPendingDeletions = var3;
   }

   public void addListener(CallbackClass var1) {
      this.mListenerPendingDeletions.remove(var1);
      this.mListenerPendingAdditions.add(var1);
   }

   public void clear() {
      this.mListeners.clear();
      this.mListenerPendingAdditions.clear();
      this.mListenerPendingDeletions.clear();
   }

   public int count() {
      int var1 = 0;
      Iterator var2 = this.mListeners.iterator();

      while(var2.hasNext()) {
         Object var3 = var2.next();
         if(!this.mListenerPendingDeletions.contains(var3)) {
            ++var1;
         }
      }

      Iterator var4 = this.mListenerPendingAdditions.iterator();

      while(var4.hasNext()) {
         Object var5 = var4.next();
         if(!this.mListeners.contains(var5)) {
            ++var1;
            if(!$assertionsDisabled && this.mListenerPendingDeletions.contains(var5)) {
               throw new AssertionError();
            }
         }
      }

      return var1;
   }

   public Set<CallbackClass> getListeners() {
      Set var1 = this.mListeners;
      Set var2 = this.mListenerPendingDeletions;
      var1.removeAll(var2);
      Set var4 = this.mListeners;
      Set var5 = this.mListenerPendingAdditions;
      var4.addAll(var5);
      this.mListenerPendingAdditions.clear();
      this.mListenerPendingDeletions.clear();
      return this.mListeners;
   }

   public void removeListener(CallbackClass var1) {
      this.mListenerPendingAdditions.remove(var1);
      this.mListenerPendingDeletions.add(var1);
   }
}
