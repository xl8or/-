package android.support.v4.widget;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class ResourceCursorAdapter extends CursorAdapter {

   private int mDropDownLayout;
   private LayoutInflater mInflater;
   private int mLayout;


   @Deprecated
   public ResourceCursorAdapter(Context var1, int var2, Cursor var3) {
      super(var1, var3);
      this.mDropDownLayout = var2;
      this.mLayout = var2;
      LayoutInflater var4 = (LayoutInflater)var1.getSystemService("layout_inflater");
      this.mInflater = var4;
   }

   public ResourceCursorAdapter(Context var1, int var2, Cursor var3, int var4) {
      super(var1, var3, var4);
      this.mDropDownLayout = var2;
      this.mLayout = var2;
      LayoutInflater var5 = (LayoutInflater)var1.getSystemService("layout_inflater");
      this.mInflater = var5;
   }

   public ResourceCursorAdapter(Context var1, int var2, Cursor var3, boolean var4) {
      super(var1, var3, var4);
      this.mDropDownLayout = var2;
      this.mLayout = var2;
      LayoutInflater var5 = (LayoutInflater)var1.getSystemService("layout_inflater");
      this.mInflater = var5;
   }

   public View newDropDownView(Context var1, Cursor var2, ViewGroup var3) {
      LayoutInflater var4 = this.mInflater;
      int var5 = this.mDropDownLayout;
      return var4.inflate(var5, var3, (boolean)0);
   }

   public View newView(Context var1, Cursor var2, ViewGroup var3) {
      LayoutInflater var4 = this.mInflater;
      int var5 = this.mLayout;
      return var4.inflate(var5, var3, (boolean)0);
   }

   public void setDropDownViewResource(int var1) {
      this.mDropDownLayout = var1;
   }

   public void setViewResource(int var1) {
      this.mLayout = var1;
   }
}
