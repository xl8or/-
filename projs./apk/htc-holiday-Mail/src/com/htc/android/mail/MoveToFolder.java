package com.htc.android.mail;

import android.content.Context;
import com.htc.android.mail.IconListAdapter;
import com.htc.android.mail.Mailboxs;
import java.util.ArrayList;
import java.util.List;

public class MoveToFolder {

   public MoveToFolder() {}

   protected static void addItem(List<IconListAdapter.IconListItem> var0, String var1, int var2) {
      IconListAdapter.IconListItem var3 = new IconListAdapter.IconListItem(var1, var2);
      var0.add(var3);
   }

   public static IconListAdapter getAdapter(Context var0, Mailboxs var1, int var2) {
      List var3 = getData(var2, var0, var1);
      return new IconListAdapter(var0, var3);
   }

   protected static List<IconListAdapter.IconListItem> getData(int var0, Context var1, Mailboxs var2) {
      String[] var3 = var2.getDecodeNames(var1);
      int var4 = var3.length;
      ArrayList var5 = new ArrayList(var4);
      int var6 = 0;

      while(true) {
         int var7 = var3.length;
         if(var6 >= var7) {
            return var5;
         }

         String var8 = var3[var6];
         addItem(var5, var8, 2130837647);
         ++var6;
      }
   }
}
