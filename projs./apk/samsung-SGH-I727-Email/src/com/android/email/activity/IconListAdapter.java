package com.android.email.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class IconListAdapter extends ArrayAdapter<IconListAdapter.IconListItem> {

   private static final int mResource = 2130903090;
   protected LayoutInflater mInflater;


   public IconListAdapter(Context var1, List<IconListAdapter.IconListItem> var2) {
      super(var1, 2130903090, var2);
      LayoutInflater var3 = (LayoutInflater)var1.getSystemService("layout_inflater");
      this.mInflater = var3;
   }

   public View getView(int var1, View var2, ViewGroup var3) {
      View var4;
      if(var2 == null) {
         var4 = this.mInflater.inflate(2130903090, var3, (boolean)0);
      } else {
         var4 = var2;
      }

      TextView var5 = (TextView)var4.findViewById(2131362064);
      String var6 = ((IconListAdapter.IconListItem)this.getItem(var1)).getTitle();
      var5.setText(var6);
      ImageView var7 = (ImageView)var4.findViewById(2131361951);
      int var8 = ((IconListAdapter.IconListItem)this.getItem(var1)).getResource();
      var7.setImageResource(var8);
      return var4;
   }

   public static class IconListItem {

      private final int mResource;
      private final String mTitle;


      public IconListItem(String var1, int var2) {
         this.mResource = var2;
         this.mTitle = var1;
      }

      public int getResource() {
         return this.mResource;
      }

      public String getTitle() {
         return this.mTitle;
      }
   }
}
