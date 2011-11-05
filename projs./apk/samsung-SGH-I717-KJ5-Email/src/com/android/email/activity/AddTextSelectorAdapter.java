package com.android.email.activity;

import android.content.Context;
import com.android.email.activity.IconListAdapter;
import java.util.ArrayList;
import java.util.List;

public class AddTextSelectorAdapter extends IconListAdapter {

   public static final int ADD_BOOKMARK = 1;
   public static final int ADD_CALENDAR = 2;
   public static final int ADD_DRAWINGPAD = 7;
   public static final int ADD_IMAGE = 5;
   public static final int ADD_LOCATION = 4;
   public static final int ADD_MEMO = 3;
   public static final int ADD_NAMECARD = 0;
   public static final int ADD_PENMEMO = 6;


   public AddTextSelectorAdapter(Context var1) {
      List var2 = getData(var1);
      super(var1, var2);
   }

   protected static void addItem(List<IconListAdapter.IconListItem> var0, String var1, int var2, int var3) {
      AddTextSelectorAdapter.AddTextListItem var4 = new AddTextSelectorAdapter.AddTextListItem(var1, var2, var3);
      var0.add(var4);
   }

   protected static List<IconListAdapter.IconListItem> getData(Context var0) {
      ArrayList var1 = new ArrayList(7);
      String var2 = var0.getString(2131166822);
      addItem(var1, var2, 2130837550, 5);
      String var3 = var0.getString(2131166588);
      addItem(var1, var3, 2130837539, 2);
      String var4 = var0.getString(2131166577);
      addItem(var1, var4, 2130837547, 0);
      String var5 = var0.getString(2131166589);
      addItem(var1, var5, 2130837544, 4);
      return var1;
   }

   public int buttonToCommand(int var1) {
      return ((AddTextSelectorAdapter.AddTextListItem)this.getItem(var1)).getCommand();
   }

   public static class AddTextListItem extends IconListAdapter.IconListItem {

      private int mCommand;


      public AddTextListItem(String var1, int var2, int var3) {
         super(var1, var2);
         this.mCommand = var3;
      }

      public int getCommand() {
         return this.mCommand;
      }
   }
}
