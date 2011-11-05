package com.android.settings;

import android.app.LauncherActivity;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

public class CreateShortcut extends LauncherActivity {

   private static final String TAG = "CreateShortcut";


   public CreateShortcut() {}

   protected Intent getTargetIntent() {
      Intent var1 = new Intent("android.intent.action.MAIN", (Uri)null);
      Intent var2 = var1.addCategory("com.android.settings.SHORTCUT");
      Intent var3 = var1.addFlags(268435456);
      return var1;
   }

   protected void onListItemClick(ListView var1, View var2, int var3, long var4) {
      Intent var6 = this.intentForPosition(var3);
      Intent var7 = var6.setFlags(67108864);
      Intent var8 = new Intent();
      int var9 = this.itemForPosition(var3).resolveInfo.getIconResource();
      ShortcutIconResource var10 = ShortcutIconResource.fromContext(this, var9);
      var8.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", var10);
      String var12 = "onListItemClick : position : " + var3;
      int var13 = Log.d("CreateShortcut", var12);
      StringBuilder var14 = (new StringBuilder()).append("onListItemClick : Resource ID : ");
      int var15 = this.itemForPosition(var3).resolveInfo.getIconResource();
      String var16 = var14.append(var15).toString();
      int var17 = Log.d("CreateShortcut", var16);
      var8.putExtra("android.intent.extra.shortcut.INTENT", var6);
      CharSequence var19 = this.itemForPosition(var3).label;
      var8.putExtra("android.intent.extra.shortcut.NAME", var19);
      this.setResult(-1, var8);
      this.finish();
   }
}
