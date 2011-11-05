package android.support.v4.widget;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.widget.ResourceCursorAdapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SimpleCursorAdapter extends ResourceCursorAdapter {

   private SimpleCursorAdapter.CursorToStringConverter mCursorToStringConverter;
   protected int[] mFrom;
   String[] mOriginalFrom;
   private int mStringConversionColumn = -1;
   protected int[] mTo;
   private SimpleCursorAdapter.ViewBinder mViewBinder;


   @Deprecated
   public SimpleCursorAdapter(Context var1, int var2, Cursor var3, String[] var4, int[] var5) {
      super(var1, var2, var3);
      this.mTo = var5;
      this.mOriginalFrom = var4;
      this.findColumns(var4);
   }

   public SimpleCursorAdapter(Context var1, int var2, Cursor var3, String[] var4, int[] var5, int var6) {
      super(var1, var2, var3, var6);
      this.mTo = var5;
      this.mOriginalFrom = var4;
      this.findColumns(var4);
   }

   private void findColumns(String[] var1) {
      if(this.mCursor == null) {
         this.mFrom = null;
      } else {
         int var2 = var1.length;
         if(this.mFrom == null || this.mFrom.length != var2) {
            int[] var3 = new int[var2];
            this.mFrom = var3;
         }

         for(int var4 = 0; var4 < var2; ++var4) {
            int[] var5 = this.mFrom;
            Cursor var6 = this.mCursor;
            String var7 = var1[var4];
            int var8 = var6.getColumnIndexOrThrow(var7);
            var5[var4] = var8;
         }

      }
   }

   public void bindView(View var1, Context var2, Cursor var3) {
      SimpleCursorAdapter.ViewBinder var4 = this.mViewBinder;
      int var5 = this.mTo.length;
      int[] var6 = this.mFrom;
      int[] var7 = this.mTo;

      for(int var8 = 0; var8 < var5; ++var8) {
         int var9 = var7[var8];
         View var10 = var1.findViewById(var9);
         if(var10 != null) {
            byte var11 = 0;
            if(var4 != null) {
               int var12 = var6[var8];
               var11 = var4.setViewValue(var10, var3, var12);
            }

            if(var11 == 0) {
               int var13 = var6[var8];
               String var14 = var3.getString(var13);
               if(var14 == null) {
                  var14 = "";
               }

               if(var10 instanceof TextView) {
                  TextView var15 = (TextView)var10;
                  this.setViewText(var15, var14);
               } else {
                  if(!(var10 instanceof ImageView)) {
                     StringBuilder var17 = new StringBuilder();
                     String var18 = var10.getClass().getName();
                     String var19 = var17.append(var18).append(" is not a ").append(" view that can be bounds by this SimpleCursorAdapter").toString();
                     throw new IllegalStateException(var19);
                  }

                  ImageView var16 = (ImageView)var10;
                  this.setViewImage(var16, var14);
               }
            }
         }
      }

   }

   public void changeCursorAndColumns(Cursor var1, String[] var2, int[] var3) {
      this.mOriginalFrom = var2;
      this.mTo = var3;
      super.changeCursor(var1);
      String[] var4 = this.mOriginalFrom;
      this.findColumns(var4);
   }

   public CharSequence convertToString(Cursor var1) {
      Object var2;
      if(this.mCursorToStringConverter != null) {
         var2 = this.mCursorToStringConverter.convertToString(var1);
      } else if(this.mStringConversionColumn > -1) {
         int var3 = this.mStringConversionColumn;
         var2 = var1.getString(var3);
      } else {
         var2 = super.convertToString(var1);
      }

      return (CharSequence)var2;
   }

   public SimpleCursorAdapter.CursorToStringConverter getCursorToStringConverter() {
      return this.mCursorToStringConverter;
   }

   public int getStringConversionColumn() {
      return this.mStringConversionColumn;
   }

   public SimpleCursorAdapter.ViewBinder getViewBinder() {
      return this.mViewBinder;
   }

   public void setCursorToStringConverter(SimpleCursorAdapter.CursorToStringConverter var1) {
      this.mCursorToStringConverter = var1;
   }

   public void setStringConversionColumn(int var1) {
      this.mStringConversionColumn = var1;
   }

   public void setViewBinder(SimpleCursorAdapter.ViewBinder var1) {
      this.mViewBinder = var1;
   }

   public void setViewImage(ImageView var1, String var2) {
      try {
         int var3 = Integer.parseInt(var2);
         var1.setImageResource(var3);
      } catch (NumberFormatException var6) {
         Uri var5 = Uri.parse(var2);
         var1.setImageURI(var5);
      }
   }

   public void setViewText(TextView var1, String var2) {
      var1.setText(var2);
   }

   public Cursor swapCursor(Cursor var1) {
      Cursor var2 = super.swapCursor(var1);
      String[] var3 = this.mOriginalFrom;
      this.findColumns(var3);
      return var2;
   }

   public interface ViewBinder {

      boolean setViewValue(View var1, Cursor var2, int var3);
   }

   public interface CursorToStringConverter {

      CharSequence convertToString(Cursor var1);
   }
}
