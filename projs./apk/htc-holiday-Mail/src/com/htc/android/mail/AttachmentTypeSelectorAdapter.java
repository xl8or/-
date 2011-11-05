package com.htc.android.mail;

import android.content.Context;
import com.htc.android.mail.IconListAdapter;
import java.util.ArrayList;
import java.util.List;

public class AttachmentTypeSelectorAdapter extends IconListAdapter {

   public static final int ADD_IMAGE = 2;
   public static final int ADD_OFFICE = 1;
   public static final int ADD_PDF = 0;
   public static final int ADD_SOUND = 4;
   public static final int ADD_VIDEO = 6;
   public static final int ATTACT_TYPE_NUM = 8;
   public static final int MODE_WITHOUT_SLIDESHOW = 1;
   public static final int MODE_WITH_SLIDESHOW = 0;
   public static final int RECORD_SOUND = 5;
   public static final int RECORD_VIDEO = 7;
   public static final int TAKE_PICTURE = 3;


   public AttachmentTypeSelectorAdapter(Context var1, int var2) {
      List var3 = getData(var2, var1);
      super(var1, var3);
   }

   protected static void addItem(List<IconListAdapter.IconListItem> var0, String var1, int var2) {
      IconListAdapter.IconListItem var3 = new IconListAdapter.IconListItem(var1, var2);
      var0.add(var3);
   }

   protected static List<IconListAdapter.IconListItem> getData(int var0, Context var1) {
      ArrayList var2 = new ArrayList(8);
      String var3 = var1.getString(2131361866);
      addItem(var2, var3, 2130837647);
      String var4 = var1.getString(2131361867);
      addItem(var2, var4, 2130837647);
      String var5 = var1.getString(2131362124);
      addItem(var2, var5, 2130837647);
      String var6 = var1.getString(2131362125);
      addItem(var2, var6, 2130837647);
      String var7 = var1.getString(2131362128);
      addItem(var2, var7, 2130837647);
      String var8 = var1.getString(2131362129);
      addItem(var2, var8, 2130837647);
      String var9 = var1.getString(2131362126);
      addItem(var2, var9, 2130837647);
      String var10 = var1.getString(2131362127);
      addItem(var2, var10, 2130837647);
      return var2;
   }
}
