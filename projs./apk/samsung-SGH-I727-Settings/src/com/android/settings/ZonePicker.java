package com.android.settings;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.android.settings.ZoneList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZonePicker extends ListActivity {

   private ArrayAdapter<CharSequence> mFilterAdapter;


   public ZonePicker() {}

   protected void addItem(List<Map> var1, String var2, String var3) {
      HashMap var4 = new HashMap();
      var4.put("title", var2);
      var4.put("zone", var3);
      var1.add(var4);
   }

   protected void onActivityResult(int var1, int var2, Intent var3) {
      if(var2 == -1) {
         this.finish();
      }
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      ArrayAdapter var2 = ArrayAdapter.createFromResource(this, 2131034112, 17367043);
      this.mFilterAdapter = var2;
      ArrayAdapter var3 = this.mFilterAdapter;
      this.setListAdapter(var3);
   }

   protected void onListItemClick(ListView var1, View var2, int var3, long var4) {
      String var6 = (String)this.mFilterAdapter.getItem(var3);
      if("All".equals(var6)) {
         var6 = null;
      }

      Intent var7 = new Intent();
      var7.setClass(this, ZoneList.class);
      var7.putExtra("filter", var6);
      this.startActivityForResult(var7, 0);
   }
}
