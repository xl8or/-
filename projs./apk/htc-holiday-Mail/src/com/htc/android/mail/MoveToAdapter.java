package com.htc.android.mail;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.htc.android.mail.Account;
import com.htc.android.mail.Mailbox;
import com.htc.android.mail.Mailboxs;
import com.htc.android.mail.Util;
import java.util.ArrayList;

public class MoveToAdapter extends BaseAdapter {

   private static final int ITEM = 101;
   public static final String REGEX = "_";
   private static final int SEPARATOR = 102;
   private static final String TAG = "MoveToAdapter";
   public static final int numMove2Mailbox = 4;
   private ArrayList<MoveToAdapter.ViewTag> listViewTag;
   private LayoutInflater myInflater;
   public int nCount;


   public MoveToAdapter(Context var1, Account var2, Mailbox var3, Context var4) {
      byte var5 = 0;
      this.nCount = var5;
      Mailboxs var6 = var2.getMailboxs();
      Mailboxs var8 = var6.getMailboxsForMove(var3);
      String[] var10 = var8.getDecodeNames(var4);
      long var11 = var2.id;
      String var16 = Util.getAccountLastMove2MailboxId(var4, var11);
      CharSequence[] var17 = null;
      ArrayList var18 = new ArrayList();
      int var22;
      if(var16 != null) {
         byte var19 = 0;
         ArrayList var20 = new ArrayList();
         String[] var21 = var16.split("_");

         for(var22 = var21.length - 1; var22 > -1; var22 += -1) {
            if(var21[var22] != false && var21[var22].length() > 0) {
               long var23 = Long.parseLong(var21[var22]);
               long var25 = var3.id;
               if(var23 != var25) {
                  Mailboxs var27 = var2.getMailboxs();
                  String var30 = var27.getMailboxById(var23).decodeName;
                  boolean var33 = var20.add(var30);
                  Long var34 = Long.valueOf(var23);
                  var18.add(var34);
                  if(var19 + 1 >= 3) {
                     break;
                  }
               }
            }
         }

         CharSequence[] var36 = new CharSequence[var20.size()];
         var17 = (CharSequence[])var20.toArray(var36);
      }

      LayoutInflater var39 = LayoutInflater.from(var1);
      this.myInflater = var39;
      ArrayList var40 = new ArrayList();
      this.listViewTag = var40;
      if(var17 != null && var17.length > 0) {
         ArrayList var41 = this.listViewTag;
         int var43 = 2131362561;
         String var44 = var1.getString(var43);
         MoveToAdapter.ViewTag var46 = new MoveToAdapter.ViewTag(var44, 102, 0L);
         var41.add(var46);
         byte var71 = 0;

         while(true) {
            int var48 = var17.length;
            if(var71 >= var48) {
               ArrayList var57 = this.listViewTag;
               int var59 = 2131362562;
               String var60 = var1.getString(var59);
               MoveToAdapter.ViewTag var62 = new MoveToAdapter.ViewTag(var60, 102, 0L);
               var57.add(var62);
               int var64 = var17.length;
               this.nCount = var64;
               break;
            }

            ArrayList var49 = this.listViewTag;
            CharSequence var50 = var17[var71];
            long var51 = ((Long)var18.get(var71)).longValue();
            MoveToAdapter.ViewTag var54 = new MoveToAdapter.ViewTag(var50, 101, var51);
            var49.add(var54);
            int var56 = var71 + 1;
         }
      }

      if(var10 != null) {
         if(var10.length > 0) {
            var22 = 0;

            while(true) {
               int var65 = var10.length;
               if(var22 >= var65) {
                  return;
               }

               ArrayList var66 = this.listViewTag;
               String var67 = var10[var22];
               MoveToAdapter.ViewTag var69 = new MoveToAdapter.ViewTag(var67, 101, 0L);
               var66.add(var69);
               ++var22;
            }
         }
      }
   }

   public int getCount() {
      return this.listViewTag.size();
   }

   public Object getItem(int var1) {
      int var2 = this.getCount();
      Object var3;
      if(var1 >= var2) {
         this.notifyDataSetChanged();
         var3 = this.listViewTag.get(0);
      } else {
         var3 = this.listViewTag.get(var1);
      }

      return var3;
   }

   public long getItemId(int var1) {
      return (long)var1;
   }

   public int getItemViewType(int var1) {
      byte var2;
      if(((MoveToAdapter.ViewTag)this.getItem(var1)).tag == 102) {
         var2 = 0;
      } else {
         var2 = 1;
      }

      return var2;
   }

   public long getMailboxID(int var1) {
      return ((MoveToAdapter.ViewTag)this.getItem(var1)).mailboxID;
   }

   public Mailbox getToMailbox(Account var1, Mailbox var2, int var3, Context var4) {
      long var5 = var1.id;
      String var10 = Util.getAccountLastMove2MailboxId(var4, var5);
      String var11 = new String();
      Mailbox var25;
      Mailbox var26;
      if(var10 != null) {
         String var13 = "_";
         String[] var14 = var10.split(var13);
         int var15 = this.nCount;
         if(var15 > 0) {
            var15 += 2;
         }

         if(var3 < var15) {
            long var20 = this.getMailboxID(var3);
            Mailboxs var22 = var1.getMailboxs();
            var25 = var22.getMailboxById(var20);
            if(var25 == null) {
               var26 = null;
               return var26;
            }
         } else {
            Mailboxs var27 = var1.getMailboxs();
            Mailboxs var29 = var27.getMailboxsForMove(var2);
            int var30 = var3 - var15;
            var25 = var29.getMailbox(var30);
            if(var25 == null) {
               var26 = null;
               return var26;
            }
         }

         boolean var31 = false;
         String[] var32 = var14;
         int var33 = var14.length;

         for(int var34 = 0; var34 < var33; ++var34) {
            String var35 = var32[var34];
            if(var35 != null && var35.length() > 0) {
               long var36 = Long.parseLong(var35);
               long var38 = var25.id;
               if(var36 == var38) {
                  var31 = true;
               } else {
                  StringBuilder var40 = new StringBuilder();
                  StringBuilder var42 = var40.append(var11);
                  var11 = var42.append(var35).append("_").toString();
               }
            } else {
               StringBuilder var44 = new StringBuilder();
               var11 = var44.append(var11).append("_").toString();
            }
         }

         if(var31) {
            StringBuilder var46 = new StringBuilder();
            StringBuilder var48 = var46.append(var11);
            long var49 = var25.id;
            var11 = var48.append(var49).toString();
         } else {
            String var67 = "_";
            int var68 = var10.indexOf(var67) + 1;
            String var71 = var10.substring(var68);
            StringBuilder var72 = new StringBuilder();
            StringBuilder var74 = var72.append(var71).append("_");
            long var75 = var25.id;
            var11 = var74.append(var75).toString();
         }
      } else {
         Mailboxs var77 = var1.getMailboxs();
         Mailboxs var79 = var77.getMailboxsForMove(var2);
         var25 = var79.getMailbox(var3);
         int var81 = 0;

         while(true) {
            byte var83 = 3;
            if(var81 >= var83) {
               StringBuilder var86 = new StringBuilder();
               StringBuilder var88 = var86.append(var11);
               long var89 = var25.id;
               var11 = var88.append(var89).toString();
               break;
            }

            StringBuilder var84 = new StringBuilder();
            var11 = var84.append(var11).append("_").toString();
            ++var81;
         }
      }

      StringBuilder var51 = (new StringBuilder()).append("mailbox.id:");
      long var52 = var2.id;
      String var54 = var51.append(var52).toString();
      int var55 = Log.i("MoveToAdapter", var54);
      StringBuilder var56 = (new StringBuilder()).append("lastStr:");
      String var58 = var56.append(var11).toString();
      int var59 = Log.i("MoveToAdapter", var58);
      long var60 = var1.id;
      Util.writeAccountLastMove2MailboxIdToPref(var4, var60, var11);
      var26 = var25;
      return var26;
   }

   public View getView(int var1, View var2, ViewGroup var3) {
      MoveToAdapter.ViewTag var4 = (MoveToAdapter.ViewTag)this.getItem(var1);
      if(var2 == null) {
         if(var4.tag == 102) {
            var2 = this.myInflater.inflate(2130903091, (ViewGroup)null);
         } else {
            var2 = this.myInflater.inflate(2130903044, (ViewGroup)null);
         }
      }

      TextView var5;
      if(var4.tag == 102) {
         var5 = (TextView)var2.findViewById(2131296508);
      } else {
         var5 = (TextView)var2.findViewById(33685520);
      }

      if(var5 != null) {
         CharSequence var6 = var4.text;
         var5.setText(var6);
      }

      return var2;
   }

   public int getViewTypeCount() {
      return 2;
   }

   public boolean isEnabled(int var1) {
      byte var2;
      if(((MoveToAdapter.ViewTag)this.getItem(var1)).tag == 102) {
         var2 = 0;
      } else {
         var2 = super.isEnabled(var1);
      }

      return (boolean)var2;
   }

   class ViewTag {

      long mailboxID;
      int tag;
      CharSequence text;


      public ViewTag(CharSequence var2, int var3, long var4) {
         this.text = var2;
         this.tag = var3;
         this.mailboxID = var4;
      }
   }
}
