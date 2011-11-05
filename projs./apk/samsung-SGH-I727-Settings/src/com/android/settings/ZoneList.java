package com.android.settings;

import android.app.AlarmManager;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class ZoneList extends ListActivity {

   private static final int HOURS_1 = 3600000;
   private static final int HOURS_24 = 86400000;
   private static final int HOURS_HALF = 1800000;
   private static final String KEY_DISPLAYNAME = "name";
   private static final String KEY_GMT = "gmt";
   private static final String KEY_ID = "id";
   private static final String KEY_OFFSET = "offset";
   private static final int MENU_ALPHABETICAL = 1;
   private static final int MENU_TIMEZONE = 2;
   private static final String TAG = "ZoneList";
   private static final String XMLTAG_TIMEZONE = "timezone";
   private SimpleAdapter mAlphabeticalAdapter;
   private int mDefault;
   private boolean mSortedByTimezone;
   private SimpleAdapter mTimezoneSortedAdapter;


   public ZoneList() {}

   private List<HashMap> getZones() {
      // $FF: Couldn't be decompiled
   }

   private void setSorting(boolean var1) {
      SimpleAdapter var2;
      if(var1) {
         var2 = this.mTimezoneSortedAdapter;
      } else {
         var2 = this.mAlphabeticalAdapter;
      }

      this.setListAdapter(var2);
      this.mSortedByTimezone = var1;
   }

   protected void addItem(List<HashMap> var1, String var2, String var3, long var4) {
      HashMap var6 = new HashMap();
      var6.put("id", var2);
      var6.put("name", var3);
      int var9 = TimeZone.getTimeZone(var2).getOffset(var4);
      int var10 = Math.abs(var9);
      StringBuilder var11 = new StringBuilder();
      StringBuilder var12 = var11.append("GMT");
      if(var9 < 0) {
         StringBuilder var13 = var11.append('-');
      } else {
         StringBuilder var31 = var11.append('+');
      }

      int var14 = var10 / 3600000;
      var11.append(var14);
      StringBuilder var16 = var11.append(':');
      int var17 = var10 / '\uea60' % 60;
      if(var17 < 10) {
         StringBuilder var18 = var11.append('0');
      }

      var11.append(var17);
      String var20 = var11.toString();
      var6.put("gmt", var20);
      Integer var22 = Integer.valueOf(var9);
      var6.put("offset", var22);
      String var24 = TimeZone.getDefault().getID();
      if(var2.equals(var24)) {
         int var25 = var1.size();
         this.mDefault = var25;
         StringBuilder var26 = (new StringBuilder()).append("addItem(), mDefault: ");
         int var27 = this.mDefault;
         String var28 = var26.append(var27).append(", id? ").append(var2).toString();
         int var29 = Log.i("ZoneList", var28);
      }

      var1.add(var6);
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      String[] var2 = new String[]{"name", "gmt"};
      int[] var3 = new int[]{2131427462, 2131427463};
      ZoneList.MyComparator var4 = new ZoneList.MyComparator("offset");
      List var5 = this.getZones();
      Collections.sort(var5, var4);
      int var6 = 0;

      while(true) {
         int var7 = var5.size();
         if(var6 >= var7) {
            break;
         }

         StringBuilder var8 = (new StringBuilder()).append("i(").append(var6).append("):");
         String var9 = ((HashMap)var5.get(var6)).toString();
         String var10 = var8.append(var9).toString();
         int var11 = Log.i("ZoneList", var10);
         String var12 = ((HashMap)var5.get(var6)).toString();
         String var13 = TimeZone.getDefault().getID();
         if(var12.contains(var13)) {
            String var14 = "Break loop / i(" + var6 + ")";
            int var15 = Log.i("ZoneList", var14);
            this.mDefault = var6;
            break;
         }

         ++var6;
      }

      SimpleAdapter var17 = new SimpleAdapter(this, var5, 2130903083, var2, var3);
      this.mTimezoneSortedAdapter = var17;
      ArrayList var18 = new ArrayList(var5);
      var4.setSortingKey("name");
      Collections.sort(var18, var4);
      SimpleAdapter var22 = new SimpleAdapter(this, var18, 2130903083, var2, var3);
      this.mAlphabeticalAdapter = var22;
      this.setSorting((boolean)1);
      StringBuilder var23 = (new StringBuilder()).append("onCreate(), mDefault: ");
      int var24 = this.mDefault;
      String var25 = var23.append(var24).toString();
      int var26 = Log.i("ZoneList", var25);
      int var27 = this.mDefault;
      this.setSelection(var27);
      this.setResult(0);
   }

   public boolean onCreateOptionsMenu(Menu var1) {
      MenuItem var2 = var1.add(0, 1, 0, 2131230952).setIcon(33685560);
      MenuItem var3 = var1.add(0, 2, 0, 2131230953).setIcon(2130837554);
      return true;
   }

   protected void onListItemClick(ListView var1, View var2, int var3, long var4) {
      Map var6 = (Map)var1.getItemAtPosition(var3);
      AlarmManager var7 = (AlarmManager)this.getSystemService("alarm");
      String var8 = (String)var6.get("id");
      var7.setTimeZone(var8);
      this.setResult(-1);
      this.finish();
   }

   public boolean onOptionsItemSelected(MenuItem var1) {
      boolean var2;
      switch(var1.getItemId()) {
      case 1:
         this.setSorting((boolean)0);
         var2 = true;
         break;
      case 2:
         this.setSorting((boolean)1);
         var2 = true;
         break;
      default:
         var2 = false;
      }

      return var2;
   }

   public boolean onPrepareOptionsMenu(Menu var1) {
      if(this.mSortedByTimezone) {
         MenuItem var2 = var1.findItem(2).setVisible((boolean)0);
         MenuItem var3 = var1.findItem(1).setVisible((boolean)1);
      } else {
         MenuItem var4 = var1.findItem(2).setVisible((boolean)1);
         MenuItem var5 = var1.findItem(1).setVisible((boolean)0);
      }

      return true;
   }

   private static class MyComparator implements Comparator<HashMap> {

      private String mSortingKey;


      public MyComparator(String var1) {
         this.mSortingKey = var1;
      }

      private boolean isComparable(Object var1) {
         boolean var2;
         if(var1 != null && var1 instanceof Comparable) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      public int compare(HashMap var1, HashMap var2) {
         String var3 = this.mSortingKey;
         Object var4 = var1.get(var3);
         String var5 = this.mSortingKey;
         Object var6 = var2.get(var5);
         int var7;
         if(!this.isComparable(var4)) {
            if(this.isComparable(var6)) {
               var7 = 1;
            } else {
               var7 = 0;
            }
         } else if(!this.isComparable(var6)) {
            var7 = -1;
         } else {
            var7 = ((Comparable)var4).compareTo(var6);
         }

         return var7;
      }

      public void setSortingKey(String var1) {
         this.mSortingKey = var1;
      }
   }
}
