package com.htc.android.mail.util;

import android.content.Context;
import android.os.Bundle;
import com.htc.android.mail.AbsRequestController;
import com.htc.android.mail.Account;
import com.htc.android.mail.AccountPool;
import com.htc.android.mail.MailMessage;
import com.htc.android.mail.Mailbox;
import com.htc.android.mail.Mailboxs;
import com.htc.android.mail.Request;
import com.htc.android.mail.util.SparseArray;
import com.htc.android.mail.util.SparseLongBooleanArray;
import java.util.ArrayList;

public class SelectedMailMessages {

   public int mCount;
   private SparseLongBooleanArray mSelected;
   private SparseArray<SparseArray<SparseArray<MailMessage>>> mSelectedMessages;


   public SelectedMailMessages() {
      SparseLongBooleanArray var1 = new SparseLongBooleanArray();
      this.mSelected = var1;
      SparseArray var2 = new SparseArray();
      this.mSelectedMessages = var2;
   }

   private ArrayList<MailMessage> getList(SparseArray<MailMessage> var1) {
      ArrayList var2 = new ArrayList();
      int var3 = var1.size();

      for(int var4 = 0; var4 < var3; ++var4) {
         if(var1.valueAt(var4) != null) {
            Object var5 = var1.valueAt(var4);
            var2.add(var5);
         }
      }

      return var2;
   }

   public boolean checkboxChecked(long var1) {
      return this.mSelected.get(var1);
   }

   public void clear() {
      this.mCount = 0;
      this.mSelected.clear();
      this.mSelectedMessages.clear();
   }

   public void deleteMail(Context var1, AbsRequestController var2) {
      int var3 = this.mSelectedMessages.size();
      AccountPool var4 = AccountPool.getInstance(var1);

      for(int var5 = 0; var5 < var3; ++var5) {
         SparseArray var6 = this.mSelectedMessages;
         long var8 = var6.keyAt(var5);
         Account var14 = var4.getAccount(var1, var8);
         SparseArray var15 = this.mSelectedMessages;
         SparseArray var17 = (SparseArray)var15.valueAt(var5);
         int var18 = var17.size();

         for(int var19 = 0; var19 < var18; ++var19) {
            long var22 = var17.keyAt(var19);
            Mailboxs var24 = var14.getMailboxs();
            Mailbox var27 = var24.getMailboxById(var22);
            if(var27 != null) {
               SparseArray var30 = (SparseArray)var17.valueAt(var19);
               ArrayList var33 = this.getList(var30);
               if(var33 != null && var33.size() != 0) {
                  Request var34 = new Request();
                  byte var35 = 4;
                  var34.command = var35;
                  Bundle var36 = new Bundle();
                  String var38 = "Mailbox";
                  var36.putParcelable(var38, var27);
                  String var41 = "MailMessageList";
                  var36.putParcelableArrayList(var41, var33);
                  var34.parameter = var36;
                  var34.setAccountId(var8);
                  if(var2 != null) {
                     var2.deleteMail(var34);
                  }
               }
            }
         }
      }

      this.clear();
   }

   public void deselect(long var1, long var3, long var5) {
      if(this.mSelected.get(var5)) {
         this.mSelected.put(var5, (boolean)0);
         SparseArray var7 = (SparseArray)this.mSelectedMessages.get(var1);
         if(var7 != null) {
            SparseArray var8 = (SparseArray)var7.get(var3);
            if(var8 != null) {
               var8.put(var5, (Object)null);
               int var9 = this.mCount - 1;
               this.mCount = var9;
            }
         }
      }
   }

   public void moveMail(Context var1, AbsRequestController var2, long var3) {
      this.moveMail(var1, var2, var3, (boolean)1);
   }

   public void moveMail(Context var1, AbsRequestController var2, long var3, boolean var5) {
      int var6 = this.mSelectedMessages.size();
      AccountPool var7 = AccountPool.getInstance(var1);

      for(int var8 = 0; var8 < var6; ++var8) {
         SparseArray var9 = this.mSelectedMessages;
         long var11 = var9.keyAt(var8);
         Account var17 = var7.getAccount(var1, var11);
         SparseArray var18 = this.mSelectedMessages;
         SparseArray var20 = (SparseArray)var18.valueAt(var8);
         int var21 = var20.size();

         for(int var22 = 0; var22 < var21; ++var22) {
            long var25 = var20.keyAt(var22);
            Mailboxs var27 = var17.getMailboxs();
            Mailbox var30 = var27.getMailboxById(var25);
            if(var30 != null) {
               Mailboxs var31 = var17.getMailboxs();
               Mailbox var34 = var31.getMailboxById(var3);
               if(var34 != null) {
                  SparseArray var37 = (SparseArray)var20.valueAt(var22);
                  ArrayList var40 = this.getList(var37);
                  if(var40 != null && var40.size() != 0) {
                     Request var41 = new Request();
                     byte var42 = 2;
                     var41.command = var42;
                     Bundle var43 = new Bundle();
                     String var45 = "FromMailbox";
                     var43.putParcelable(var45, var30);
                     String var48 = "ToMailbox";
                     var43.putParcelable(var48, var34);
                     String var51 = "MailMessageList";
                     var43.putParcelableArrayList(var51, var40);
                     String var54 = "syncToRemote";
                     var43.putBoolean(var54, var5);
                     var41.parameter = var43;
                     var41.setAccountId(var11);
                     if(var2 != null) {
                        var2.moveMail(var41);
                     }
                  }
               }
            }
         }
      }

      this.clear();
   }

   public void select(long var1, long var3, long var5, String var7) {
      if(!this.mSelected.get(var5)) {
         SparseArray var8 = (SparseArray)this.mSelectedMessages.get(var1);
         if(var8 == null) {
            var8 = new SparseArray();
            this.mSelectedMessages.put(var1, var8);
         }

         SparseArray var9 = (SparseArray)var8.get(var3);
         if(var9 == null) {
            var9 = new SparseArray();
            var8.put(var3, var9);
         }

         if(!this.mSelected.get(var5)) {
            this.mSelected.put(var5, (boolean)1);
            MailMessage var10 = new MailMessage(var5);
            var10.accountId = var1;
            var10.mailBoxId = var3;
            var10.group = var7;
            var9.put(var5, var10);
            int var11 = this.mCount + 1;
            this.mCount = var11;
         }
      }
   }

   public void setReadStatus(Context var1, AbsRequestController var2, boolean var3) {
      int var4 = this.mSelectedMessages.size();
      AccountPool var5 = AccountPool.getInstance(var1);

      for(int var6 = 0; var6 < var4; ++var6) {
         SparseArray var7 = this.mSelectedMessages;
         long var9 = var7.keyAt(var6);
         Account var15 = var5.getAccount(var1, var9);
         SparseArray var16 = this.mSelectedMessages;
         SparseArray var18 = (SparseArray)var16.valueAt(var6);
         int var19 = var18.size();

         for(int var20 = 0; var20 < var19; ++var20) {
            long var23 = var18.keyAt(var20);
            Mailboxs var25 = var15.getMailboxs();
            Mailbox var28 = var25.getMailboxById(var23);
            if(var28 != null) {
               SparseArray var31 = (SparseArray)var18.valueAt(var20);
               ArrayList var34 = this.getList(var31);
               if(var34 != null && var34.size() != 0) {
                  Request var35 = new Request();
                  if(var3) {
                     byte var36 = 6;
                     var35.command = var36;
                  } else {
                     byte var50 = 5;
                     var35.command = var50;
                  }

                  Bundle var37 = new Bundle();
                  String var39 = "Mailbox";
                  var37.putParcelable(var39, var28);
                  String var42 = "MailMessageList";
                  var37.putParcelableArrayList(var42, var34);
                  var35.parameter = var37;
                  var35.setAccountId(var9);
                  if(var2 != null) {
                     var2.setReadStatus(var35);
                  }
               }
            }
         }
      }

      this.clear();
   }
}
