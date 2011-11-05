package com.htc.android.mail;

import android.content.Context;
import com.htc.android.mail.IconListAdapter;
import java.util.ArrayList;
import java.util.List;

public class MailItemSelectorAdapter extends IconListAdapter {

   public static final int ITEM_FOWARD = 3;
   public static final int ITEM_REPLY = 1;
   public static final int ITEM_REPLYALL = 2;
   public static final int MARK_READ;


   public MailItemSelectorAdapter(Context var1, int var2) {
      List var3 = getData(var2, var1);
      super(var1, var3);
   }

   protected static void addItem(List<IconListAdapter.IconListItem> var0, String var1, int var2) {
      IconListAdapter.IconListItem var3 = new IconListAdapter.IconListItem(var1, var2);
      var0.add(var3);
   }

   protected static List<IconListAdapter.IconListItem> getData(int var0, Context var1) {
      ArrayList var2 = new ArrayList(4);
      addItem(var2, "Mark read/unread", 2130837647);
      String var3 = var1.getString(2131362021);
      addItem(var2, var3, 2130837647);
      String var4 = var1.getString(2131362020);
      addItem(var2, var4, 2130837647);
      String var5 = var1.getString(2131362022);
      addItem(var2, var5, 2130837647);
      return var2;
   }
}
