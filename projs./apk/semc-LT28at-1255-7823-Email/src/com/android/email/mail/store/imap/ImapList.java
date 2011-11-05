package com.android.email.mail.store.imap;

import com.android.email.mail.store.imap.ImapElement;
import com.android.email.mail.store.imap.ImapString;
import java.util.ArrayList;
import java.util.Iterator;

public class ImapList extends ImapElement {

   public static final ImapList EMPTY = new ImapList.1();
   private ArrayList<ImapElement> mList;


   public ImapList() {
      ArrayList var1 = new ArrayList();
      this.mList = var1;
   }

   private final StringBuilder flatten(StringBuilder var1) {
      StringBuilder var2 = var1.append('[');
      int var3 = 0;

      while(true) {
         int var4 = this.mList.size();
         if(var3 >= var4) {
            StringBuilder var10 = var1.append(']');
            return var1;
         }

         if(var3 > 0) {
            StringBuilder var5 = var1.append(',');
         }

         ImapElement var6 = this.getElementOrNone(var3);
         if(var6.isList()) {
            StringBuilder var7 = this.getListOrEmpty(var3).flatten(var1);
         } else if(var6.isString()) {
            String var8 = this.getStringOrEmpty(var3).getString();
            var1.append(var8);
         }

         ++var3;
      }
   }

   void add(ImapElement var1) {
      if(var1 == null) {
         throw new RuntimeException("Can\'t add null");
      } else {
         this.mList.add(var1);
      }
   }

   public final boolean contains(String var1) {
      int var2 = 0;

      boolean var4;
      while(true) {
         int var3 = this.size();
         if(var2 >= var3) {
            var4 = false;
            break;
         }

         if(this.getStringOrEmpty(var2).is(var1)) {
            var4 = true;
            break;
         }

         ++var2;
      }

      return var4;
   }

   public void destroy() {
      if(this.mList != null) {
         Iterator var1 = this.mList.iterator();

         while(var1.hasNext()) {
            ((ImapElement)var1.next()).destroy();
         }

         ArrayList var2 = new ArrayList();
         this.mList = var2;
      }

      super.destroy();
   }

   public boolean equalsForTest(ImapElement var1) {
      boolean var2;
      if(!super.equalsForTest(var1)) {
         var2 = false;
      } else {
         ImapList var3 = (ImapList)var1;
         int var4 = this.size();
         int var5 = var3.size();
         if(var4 != var5) {
            var2 = false;
         } else {
            int var6 = 0;

            while(true) {
               int var7 = this.size();
               if(var6 >= var7) {
                  var2 = true;
                  break;
               }

               ImapElement var8 = (ImapElement)this.mList.get(var6);
               ImapElement var9 = var3.getElementOrNone(var6);
               if(!var8.equalsForTest(var9)) {
                  var2 = false;
                  break;
               }

               ++var6;
            }
         }
      }

      return var2;
   }

   public final String flatten() {
      StringBuilder var1 = new StringBuilder();
      return this.flatten(var1).toString();
   }

   public final ImapElement getElementOrNone(int var1) {
      int var2 = this.mList.size();
      ImapElement var3;
      if(var1 >= var2) {
         var3 = ImapElement.NONE;
      } else {
         var3 = (ImapElement)this.mList.get(var1);
      }

      return var3;
   }

   final ImapElement getKeyedElementOrNull(String var1, boolean var2) {
      int var3 = 1;

      ImapElement var6;
      while(true) {
         int var4 = this.size();
         if(var3 >= var4) {
            var6 = null;
            break;
         }

         int var5 = var3 - 1;
         if(this.is(var5, var1, var2)) {
            var6 = (ImapElement)this.mList.get(var3);
            break;
         }

         var3 += 2;
      }

      return var6;
   }

   public final ImapList getKeyedListOrEmpty(String var1) {
      return this.getKeyedListOrEmpty(var1, (boolean)0);
   }

   public final ImapList getKeyedListOrEmpty(String var1, boolean var2) {
      ImapElement var3 = this.getKeyedElementOrNull(var1, var2);
      ImapList var4;
      if(var3 != null) {
         var4 = (ImapList)var3;
      } else {
         var4 = EMPTY;
      }

      return var4;
   }

   public final ImapString getKeyedStringOrEmpty(String var1) {
      return this.getKeyedStringOrEmpty(var1, (boolean)0);
   }

   public final ImapString getKeyedStringOrEmpty(String var1, boolean var2) {
      ImapElement var3 = this.getKeyedElementOrNull(var1, var2);
      ImapString var4;
      if(var3 != null) {
         var4 = (ImapString)var3;
      } else {
         var4 = ImapString.EMPTY;
      }

      return var4;
   }

   public final ImapList getListOrEmpty(int var1) {
      ImapElement var2 = this.getElementOrNone(var1);
      ImapList var3;
      if(var2.isList()) {
         var3 = (ImapList)var2;
      } else {
         var3 = EMPTY;
      }

      return var3;
   }

   public final ImapString getStringOrEmpty(int var1) {
      ImapElement var2 = this.getElementOrNone(var1);
      ImapString var3;
      if(var2.isString()) {
         var3 = (ImapString)var2;
      } else {
         var3 = ImapString.EMPTY;
      }

      return var3;
   }

   public final boolean is(int var1, String var2) {
      return this.is(var1, var2, (boolean)0);
   }

   public final boolean is(int var1, String var2, boolean var3) {
      boolean var4;
      if(!var3) {
         var4 = this.getStringOrEmpty(var1).is(var2);
      } else {
         var4 = this.getStringOrEmpty(var1).startsWith(var2);
      }

      return var4;
   }

   public final boolean isEmpty() {
      boolean var1;
      if(this.size() == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public final boolean isList() {
      return true;
   }

   public final boolean isString() {
      return false;
   }

   public final int size() {
      return this.mList.size();
   }

   public String toString() {
      return this.mList.toString();
   }

   static class 1 extends ImapList {

      1() {}

      void add(ImapElement var1) {
         throw new RuntimeException();
      }

      public void destroy() {}
   }
}
