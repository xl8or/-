package kankan.wheel.widget.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import kankan.wheel.widget.adapters.AbstractWheelAdapter;

public abstract class AbstractWheelTextAdapter extends AbstractWheelAdapter {

   public static final int DEFAULT_TEXT_COLOR = -15724528;
   public static final int DEFAULT_TEXT_SIZE = 24;
   public static final int LABEL_COLOR = -9437072;
   protected static final int NO_RESOURCE = 0;
   public static final int TEXT_VIEW_ITEM_RESOURCE = 255;
   protected Context context;
   protected int emptyItemResourceId;
   protected LayoutInflater inflater;
   protected int itemResourceId;
   protected int itemTextResourceId;
   private int textColor;
   private int textSize;


   protected AbstractWheelTextAdapter(Context var1) {
      this(var1, -1);
   }

   protected AbstractWheelTextAdapter(Context var1, int var2) {
      this(var1, var2, 0);
   }

   protected AbstractWheelTextAdapter(Context var1, int var2, int var3) {
      this.textColor = -15724528;
      this.textSize = 24;
      this.context = var1;
      this.itemResourceId = var2;
      this.itemTextResourceId = var3;
      LayoutInflater var4 = (LayoutInflater)var1.getSystemService("layout_inflater");
      this.inflater = var4;
   }

   private TextView getTextView(View param1, int param2) {
      // $FF: Couldn't be decompiled
   }

   private View getView(int var1, ViewGroup var2) {
      Object var3;
      switch(var1) {
      case -1:
         Context var4 = this.context;
         var3 = new TextView(var4);
         break;
      case 0:
         var3 = null;
         break;
      default:
         var3 = this.inflater.inflate(var1, var2, (boolean)0);
      }

      return (View)var3;
   }

   protected void configureTextView(TextView var1) {
      int var2 = this.textColor;
      var1.setTextColor(var2);
      var1.setGravity(17);
      float var3 = (float)this.textSize;
      var1.setTextSize(var3);
      var1.setLines(1);
      Typeface var4 = Typeface.SANS_SERIF;
      var1.setTypeface(var4, 1);
   }

   public View getEmptyItem(View var1, ViewGroup var2) {
      if(var1 == null) {
         int var3 = this.emptyItemResourceId;
         var1 = this.getView(var3, var2);
      }

      if(this.emptyItemResourceId == -1 && var1 instanceof TextView) {
         TextView var4 = (TextView)var1;
         this.configureTextView(var4);
      }

      return var1;
   }

   public int getEmptyItemResource() {
      return this.emptyItemResourceId;
   }

   public View getItem(int var1, View var2, ViewGroup var3) {
      View var9;
      if(var1 >= 0) {
         int var4 = this.getItemsCount();
         if(var1 < var4) {
            if(var2 == null) {
               int var5 = this.itemResourceId;
               var2 = this.getView(var5, var3);
            }

            int var6 = this.itemTextResourceId;
            TextView var7 = this.getTextView(var2, var6);
            if(var7 != null) {
               Object var8 = this.getItemText(var1);
               if(var8 == null) {
                  var8 = "";
               }

               var7.setText((CharSequence)var8);
               if(this.itemResourceId == -1) {
                  this.configureTextView(var7);
               }
            }

            var9 = var2;
            return var9;
         }
      }

      var9 = null;
      return var9;
   }

   public int getItemResource() {
      return this.itemResourceId;
   }

   protected abstract CharSequence getItemText(int var1);

   public int getItemTextResource() {
      return this.itemTextResourceId;
   }

   public int getTextColor() {
      return this.textColor;
   }

   public int getTextSize() {
      return this.textSize;
   }

   public void setEmptyItemResource(int var1) {
      this.emptyItemResourceId = var1;
   }

   public void setItemResource(int var1) {
      this.itemResourceId = var1;
   }

   public void setItemTextResource(int var1) {
      this.itemTextResourceId = var1;
   }

   public void setTextColor(int var1) {
      this.textColor = var1;
   }

   public void setTextSize(int var1) {
      this.textSize = var1;
   }
}
