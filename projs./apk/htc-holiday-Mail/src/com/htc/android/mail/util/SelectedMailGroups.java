package com.htc.android.mail.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.htc.android.mail.AbsRequestController;
import com.htc.android.mail.Account;
import com.htc.android.mail.AccountPool;
import com.htc.android.mail.Mail;
import com.htc.android.mail.MailCommon;
import com.htc.android.mail.MailProvider;
import com.htc.android.mail.Mailbox;
import com.htc.android.mail.Mailboxs;
import com.htc.android.mail.ll;
import com.htc.android.mail.util.SelectedMailMessages;
import com.htc.android.mail.util.SparseArray;
import com.htc.android.mail.util.SparseLongBooleanArray;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class SelectedMailGroups {

   private static boolean DEBUG = Mail.MAIL_DEBUG;
   private static final String TAG = "SelectedMailGroups";
   public int mCount;
   public int mGroupCount;
   private SelectedMailGroups.MyMailMessages mMailMessagesForDeselectedGroup;
   private SelectedMailGroups.MyMailMessages mMailMessagesForSelectedGroup;
   private SparseArray<Set<String>> mSelectedGroups;
   private SelectedMailMessages mSelectedMailMessages;


   public SelectedMailGroups() {
      SparseArray var1 = new SparseArray();
      this.mSelectedGroups = var1;
      SelectedMailMessages var2 = new SelectedMailMessages();
      this.mSelectedMailMessages = var2;
      SelectedMailGroups.MyMailMessages var3 = new SelectedMailGroups.MyMailMessages((SelectedMailGroups.1)null);
      this.mMailMessagesForSelectedGroup = var3;
      SelectedMailGroups.MyMailMessages var4 = new SelectedMailGroups.MyMailMessages((SelectedMailGroups.1)null);
      this.mMailMessagesForDeselectedGroup = var4;
   }

   private boolean containsGroup(long var1, String var3) {
      Set var4 = (Set)this.mSelectedGroups.get(var1);
      byte var5;
      if(var4 == null) {
         var5 = 0;
      } else {
         var5 = var4.contains(var3);
      }

      return (boolean)var5;
   }

   private Cursor getConversationMailCursor(Context var1) {
      ContentResolver var2 = var1.getApplicationContext().getContentResolver();
      int var3 = this.mSelectedGroups.size();
      StringBuffer var4 = new StringBuffer();

      for(int var5 = 0; var5 < var3; ++var5) {
         long var8 = this.mSelectedGroups.keyAt(var5);
         Set var10 = (Set)this.mSelectedGroups.valueAt(var5);
         if(var10 != null && var10.size() != 0) {
            String[] var11 = new String[0];
            String[] var12 = (String[])var10.toArray(var11);
            if(false) {
               StringBuffer var13 = var4.append(" OR ");
            }

            StringBuffer var14 = var4.append(" ( _mailboxId = ").append(var8);
            StringBuffer var15 = var4.append(" AND _group IN ( ");
            String var16 = MailCommon.getSequence(var12, (boolean)1);
            var15.append(var16);
            StringBuffer var18 = var4.append(" ) ) ");
         }
      }

      String var19 = this.mMailMessagesForSelectedGroup.getMessageIdSequence();
      String var20 = this.mMailMessagesForDeselectedGroup.getMessageIdSequence();
      StringBuffer var21 = new StringBuffer();
      if(var4.length() > 0) {
         if(var19.length() > 0) {
            StringBuffer var22 = new StringBuffer();
            String var24 = " ( ";
            StringBuffer var25 = var22.append(var24).append(var4).append(" ) ").append(" AND ( _id NOT IN ( ");
            StringBuffer var27 = var25.append(var19).append(") ) ");
            var4 = var22;
         }

         if(var20.length() > 0) {
            StringBuffer var28 = var21.append(" ( ").append(var4).append(" ) ").append(" OR ( _id IN (");
            StringBuffer var30 = var28.append(var20).append(") ) ");
         } else {
            var21 = var4;
         }
      } else if(var20.length() == 0) {
         StringBuffer var37 = var21.append("1 != 1");
      } else {
         StringBuffer var38 = var21.append("_id IN ( ");
         StringBuffer var40 = var38.append(var20).append(" ) ");
      }

      String[] var31 = new String[]{"_id", "_account", "_mailboxId", "_group"};
      if(DEBUG) {
         StringBuilder var32 = (new StringBuilder()).append("sql: ");
         String var33 = var21.toString();
         String var34 = var32.append(var33).toString();
         ll.i("SelectedMailGroups", var34);
      }

      Uri var35 = MailProvider.sMessagesURI;
      String var36 = var21.toString();
      return var2.query(var35, var31, var36, (String[])null, (String)null);
   }

   public boolean childCheckboxChecked(long param1, String param3, long param4) {
      // $FF: Couldn't be decompiled
   }

   public void clear() {
      this.mCount = 0;
      this.mGroupCount = 0;
      this.mSelectedGroups.clear();
      this.mSelectedMailMessages.clear();
      this.mMailMessagesForSelectedGroup.clear();
      this.mMailMessagesForDeselectedGroup.clear();
   }

   public void deleteConversationMail(Context var1, AbsRequestController var2) {
      Cursor var3 = this.getConversationMailCursor(var1);
      if(var3 != null) {
         while(var3.moveToNext()) {
            int var4 = var3.getColumnIndexOrThrow("_id");
            long var5 = var3.getLong(var4);
            int var7 = var3.getColumnIndexOrThrow("_account");
            long var8 = var3.getLong(var7);
            int var10 = var3.getColumnIndexOrThrow("_mailboxId");
            long var11 = var3.getLong(var10);
            this.mSelectedMailMessages.select(var8, var11, var5, (String)null);
         }

         var3.close();
      }

      this.mSelectedMailMessages.deleteMail(var1, var2);
      this.clear();
   }

   public void deselectGroup(Context var1, long var2, Mailbox var4, String var5, int var6) {
      if(var4 != null) {
         if(var4.getAccountId() == Long.MAX_VALUE) {
            Account var7 = AccountPool.getInstance(var1).getAccount(var1, var2);
            if(var7 == null) {
               return;
            }

            if(var4.kind == 2147483642) {
               Mailboxs var8 = var7.getMailboxs();
               long var9 = var4.id;
               var4 = var8.getMailboxById(var9);
            } else {
               Mailboxs var28 = var7.getMailboxs();
               int var29 = var4.kind;
               var4 = var28.getMailboxByKind(var29);
            }

            if(var4 == null) {
               return;
            }
         }

         long[] var11 = var4.getMailboxIds();
         if(var11.length != 0) {
            long var12 = var11[0];
            int var20;
            if(this.containsGroup(var12, var5)) {
               int var30 = this.mGroupCount - 1;
               this.mGroupCount = var30;
               int var31 = this.mCount - var6;
               this.mCount = var31;
               int var32 = this.mCount;
               SelectedMailGroups.MyMailMessages var33 = this.mMailMessagesForSelectedGroup;
               long var34 = var11[0];
               int var36 = var33.size(var34, var5);
               int var37 = var32 + var36;
               this.mCount = var37;
               var20 = 0;

               while(true) {
                  int var38 = var11.length;
                  if(var20 >= var38) {
                     return;
                  }

                  if(var11[var20] != 65535L) {
                     SparseArray var39 = this.mSelectedGroups;
                     long var40 = var11[var20];
                     Set var42 = (Set)var39.get(var40);
                     if(var42 != null && var42.contains(var5)) {
                        var42.remove(var5);
                        SelectedMailGroups.MyMailMessages var44 = this.mMailMessagesForDeselectedGroup;
                        long var45 = var11[var20];
                        var44.clear(var45, var5);
                        SelectedMailGroups.MyMailMessages var47 = this.mMailMessagesForSelectedGroup;
                        long var48 = var11[var20];
                        var47.clear(var48, var5);
                     }
                  }

                  ++var20;
               }
            } else {
               int var14 = this.mCount;
               SelectedMailGroups.MyMailMessages var15 = this.mMailMessagesForDeselectedGroup;
               long var16 = var11[0];
               int var18 = var15.size(var16, var5);
               int var19 = var14 - var18;
               this.mCount = var19;
               var20 = 0;

               while(true) {
                  int var21 = var11.length;
                  if(var20 >= var21) {
                     return;
                  }

                  SelectedMailGroups.MyMailMessages var22 = this.mMailMessagesForSelectedGroup;
                  long var23 = var11[var20];
                  var22.clear(var23, var5);
                  SelectedMailGroups.MyMailMessages var25 = this.mMailMessagesForDeselectedGroup;
                  long var26 = var11[var20];
                  var25.clear(var26, var5);
                  ++var20;
               }
            }
         }
      }
   }

   public void deselectMessage(Context param1, long param2, Mailbox param4, String param5, long param6, boolean param8) {
      // $FF: Couldn't be decompiled
   }

   public boolean groupCheckboxChecked(long var1, String var3, int var4) {
      boolean var5;
      if(this.containsGroup(var1, var3)) {
         if(this.mMailMessagesForSelectedGroup.size(var1, var3) == 0) {
            var5 = true;
         } else {
            var5 = false;
         }
      } else if(this.mMailMessagesForDeselectedGroup.size(var1, var3) == var4) {
         var5 = true;
      } else {
         var5 = false;
      }

      return var5;
   }

   public void moveConversationMail(Context var1, AbsRequestController var2, long var3) {
      Cursor var5 = this.getConversationMailCursor(var1);
      if(var5 != null) {
         while(var5.moveToNext()) {
            int var6 = var5.getColumnIndexOrThrow("_id");
            long var7 = var5.getLong(var6);
            int var9 = var5.getColumnIndexOrThrow("_account");
            long var10 = var5.getLong(var9);
            int var12 = var5.getColumnIndexOrThrow("_mailboxId");
            long var13 = var5.getLong(var12);
            this.mSelectedMailMessages.select(var10, var13, var7, (String)null);
         }

         var5.close();
      }

      this.mSelectedMailMessages.moveMail(var1, var2, var3);
      this.clear();
   }

   public void selectGroup(Context var1, long var2, Mailbox var4, String var5, int var6) {
      if(var4 != null) {
         if(var4.getAccountId() == Long.MAX_VALUE) {
            Account var7 = AccountPool.getInstance(var1).getAccount(var1, var2);
            if(var7 == null) {
               return;
            }

            if(var4.kind == 2147483642) {
               Mailboxs var8 = var7.getMailboxs();
               long var9 = var4.id;
               var4 = var8.getMailboxById(var9);
            } else {
               Mailboxs var28 = var7.getMailboxs();
               int var29 = var4.kind;
               var4 = var28.getMailboxByKind(var29);
            }

            if(var4 == null) {
               return;
            }
         }

         long[] var11 = var4.getMailboxIds();
         if(var11.length != 0) {
            long var12 = var11[0];
            int var20;
            if(!this.containsGroup(var12, var5)) {
               int var30 = this.mGroupCount + 1;
               this.mGroupCount = var30;
               int var31 = this.mCount + var6;
               this.mCount = var31;
               int var32 = this.mCount;
               SelectedMailGroups.MyMailMessages var33 = this.mMailMessagesForDeselectedGroup;
               long var34 = var11[0];
               int var36 = var33.size(var34, var5);
               int var37 = var32 - var36;
               this.mCount = var37;
               var20 = 0;

               while(true) {
                  int var38 = var11.length;
                  if(var20 >= var38) {
                     return;
                  }

                  if(var11[var20] != 65535L) {
                     SparseArray var39 = this.mSelectedGroups;
                     long var40 = var11[var20];
                     Object var42 = (Set)var39.get(var40);
                     if(var42 == null) {
                        var42 = new HashSet();
                        SparseArray var43 = this.mSelectedGroups;
                        long var44 = var11[var20];
                        var43.put(var44, var42);
                     }

                     if(!((Set)var42).contains(var5)) {
                        ((Set)var42).add(var5);
                     }

                     SelectedMailGroups.MyMailMessages var47 = this.mMailMessagesForSelectedGroup;
                     long var48 = var11[var20];
                     var47.clear(var48, var5);
                     SelectedMailGroups.MyMailMessages var50 = this.mMailMessagesForDeselectedGroup;
                     long var51 = var11[var20];
                     var50.clear(var51, var5);
                  }

                  ++var20;
               }
            } else {
               int var14 = this.mCount;
               SelectedMailGroups.MyMailMessages var15 = this.mMailMessagesForSelectedGroup;
               long var16 = var11[0];
               int var18 = var15.size(var16, var5);
               int var19 = var14 + var18;
               this.mCount = var19;
               var20 = 0;

               while(true) {
                  int var21 = var11.length;
                  if(var20 >= var21) {
                     return;
                  }

                  SelectedMailGroups.MyMailMessages var22 = this.mMailMessagesForSelectedGroup;
                  long var23 = var11[var20];
                  var22.clear(var23, var5);
                  SelectedMailGroups.MyMailMessages var25 = this.mMailMessagesForDeselectedGroup;
                  long var26 = var11[var20];
                  var25.clear(var26, var5);
                  ++var20;
               }
            }
         }
      }
   }

   public void selecteMessage(Context param1, long param2, Mailbox param4, String param5, long param6) {
      // $FF: Couldn't be decompiled
   }

   public void setReadStatus(Context var1, AbsRequestController var2, boolean var3) {
      Cursor var4 = this.getConversationMailCursor(var1);
      if(var4 != null) {
         while(var4.moveToNext()) {
            int var5 = var4.getColumnIndexOrThrow("_id");
            long var6 = var4.getLong(var5);
            int var8 = var4.getColumnIndexOrThrow("_account");
            long var9 = var4.getLong(var8);
            int var11 = var4.getColumnIndexOrThrow("_mailboxId");
            long var12 = var4.getLong(var11);
            int var14 = var4.getColumnIndexOrThrow("_group");
            String var15 = var4.getString(var14);
            this.mSelectedMailMessages.select(var9, var12, var6, var15);
         }

         var4.close();
      }

      this.mSelectedMailMessages.setReadStatus(var1, var2, var3);
      this.clear();
   }

   private static class MyMailMessages {

      private SparseArray<Map<String, SparseLongBooleanArray>> mMessages;


      private MyMailMessages() {
         SparseArray var1 = new SparseArray();
         this.mMessages = var1;
      }

      // $FF: synthetic method
      MyMailMessages(SelectedMailGroups.1 var1) {
         this();
      }

      public void add(long param1, String param3, long param4) {
         // $FF: Couldn't be decompiled
      }

      public void clear() {
         this.mMessages.clear();
      }

      public void clear(long var1, String var3) {
         Map var4 = (Map)this.mMessages.get(var1);
         if(var4 != null) {
            SparseLongBooleanArray var5 = (SparseLongBooleanArray)var4.get(var3);
            if(var5 != null) {
               var5.clear();
            }
         }
      }

      public boolean contains(long param1, String param3, long param4) {
         // $FF: Couldn't be decompiled
      }

      public String getMessageIdSequence() {
         StringBuffer var1 = new StringBuffer();
         int var2 = this.mMessages.size();

         for(int var3 = 0; var3 < var2; ++var3) {
            Map var4 = (Map)this.mMessages.valueAt(var3);
            if(var4 != null) {
               Iterator var5 = var4.keySet().iterator();

               while(var5.hasNext()) {
                  String var6 = (String)var5.next();
                  SparseLongBooleanArray var7 = (SparseLongBooleanArray)var4.get(var6);
                  if(var7 != null) {
                     int var8 = var7.size();

                     for(int var9 = 0; var9 < var8; ++var9) {
                        long var10 = var7.keyAt(var9);
                        if(var7.get(var10)) {
                           if(false) {
                              StringBuffer var12 = var1.append(", ");
                           }

                           var1.append(var10);
                        }
                     }
                  }
               }
            }
         }

         return var1.toString();
      }

      public void remove(long param1, String param3, long param4) {
         // $FF: Couldn't be decompiled
      }

      public int size(long var1, String var3) {
         Map var4 = (Map)this.mMessages.get(var1);
         int var5;
         if(var4 == null) {
            var5 = 0;
         } else {
            SparseLongBooleanArray var6 = (SparseLongBooleanArray)var4.get(var3);
            if(var6 == null) {
               var5 = 0;
            } else {
               var5 = var6.size();
            }
         }

         return var5;
      }
   }

   // $FF: synthetic class
   static class 1 {
   }
}
