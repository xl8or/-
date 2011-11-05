package com.android.email.activity;

import android.app.ListActivity;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.database.Cursor;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.format.DateFormat;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import com.android.email.Utility;
import com.android.email.activity.MessageList;
import com.android.email.provider.EmailContent;
import java.util.Date;

public class SearchActivity extends ListActivity implements OnItemClickListener {

   private static final String EXTRA_MAILBOX_ID = "com.android.email.activity.MAILBOX_ID";
   private static final String EXTRA_MESSAGE_ID = "com.android.email.MessageView_message_id";
   private static final String LOG_VIEW_ACTION = "com.android.email.LogProvider";
   private java.text.DateFormat mDateFormat;
   private java.text.DateFormat mDayFormat;
   private long mMailboxId;
   AsyncQueryHandler mQueryHandler;
   private ColorStateList mTextColorPrimary;
   private ColorStateList mTextColorSecondary;
   private java.text.DateFormat mTimeFormat;


   public SearchActivity() {}

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      int var3 = 2130903190;
      this.setContentView(var3);
      Intent var4 = this.getIntent();
      String var5 = "app_data";
      Bundle var6 = var4.getBundleExtra(var5);
      if(var6 != null) {
         long var7 = var6.getLong("com.android.email.activity.MAILBOX_ID");
         this.mMailboxId = var7;
      }

      Theme var9 = this.getTheme();
      Resources var10 = this.getResources();
      int[] var11 = new int[]{16842806};
      int var14 = var9.obtainStyledAttributes(var11).getResourceId(0, 0);
      ColorStateList var17 = var10.getColorStateList(var14);
      this.mTextColorPrimary = var17;
      int[] var18 = new int[]{16842808};
      int var21 = var9.obtainStyledAttributes(var18).getResourceId(0, 0);
      ColorStateList var24 = var10.getColorStateList(var21);
      this.mTextColorSecondary = var24;
      java.text.DateFormat var25 = DateFormat.getDateFormat(this);
      this.mDateFormat = var25;
      java.text.DateFormat var26 = DateFormat.getDateFormat(this);
      this.mDayFormat = var26;
      java.text.DateFormat var27 = DateFormat.getTimeFormat(this);
      this.mTimeFormat = var27;
      String var28 = this.getIntent().getStringExtra("query").trim();
      String var29;
      if(var28 != null) {
         var29 = var28.trim();
      } else {
         var29 = var28;
      }

      ContentResolver var30 = this.getContentResolver();
      ListView var31 = this.getListView();
      byte var33 = 1;
      var31.setItemsCanFocus((boolean)var33);
      byte var35 = 1;
      var31.setFocusable((boolean)var35);
      byte var37 = 1;
      var31.setClickable((boolean)var37);
      var31.setOnItemClickListener(this);
      String var41 = "";
      this.setTitle(var41);
      SearchActivity.1 var42 = new SearchActivity.1(var30, var29, var31);
      this.mQueryHandler = var42;
      Uri var49 = EmailContent.Message.CONTENT_URI;
      AsyncQueryHandler var50 = this.mQueryHandler;
      String[] var51 = EmailContent.Message.CONTENT_PROJECTION;
      StringBuilder var52 = (new StringBuilder()).append("mailboxKey=\'");
      long var53 = this.mMailboxId;
      StringBuilder var55 = var52.append(var53).append("\' and subject like \'%");
      StringBuilder var57 = var55.append(var29).append("%\' or displayName like \'%");
      String var59 = var57.append(var29).append("%\'").toString();
      var50.startQuery(0, (Object)null, var49, var51, var59, (String[])null, "timeStamp DESC");
   }

   public void onItemClick(AdapterView<?> var1, View var2, int var3, long var4) {}

   class 1 extends AsyncQueryHandler {

      // $FF: synthetic field
      final ListView val$listView;
      // $FF: synthetic field
      final String val$searchString;


      1(ContentResolver var2, String var3, ListView var4) {
         super(var2);
         this.val$searchString = var3;
         this.val$listView = var4;
      }

      protected void onQueryComplete(int var1, Object var2, Cursor var3) {
         String var5 = "_id";
         int var6 = var3.getColumnIndex(var5);
         String var8 = "displayName";
         int var9 = var3.getColumnIndex(var8);
         String var11 = "timeStamp";
         int var12 = var3.getColumnIndex(var11);
         String var14 = "subject";
         int var15 = var3.getColumnIndex(var14);
         String var17 = "flagRead";
         int var18 = var3.getColumnIndex(var17);
         String var20 = "mailboxKey";
         int var21 = var3.getColumnIndex(var20);
         int var22 = var3.getCount();
         SearchActivity var23 = SearchActivity.this;
         Resources var24 = SearchActivity.this.getResources();
         Object[] var25 = new Object[2];
         Integer var26 = Integer.valueOf(var22);
         var25[0] = var26;
         String var27 = this.val$searchString;
         var25[1] = var27;
         String var28 = var24.getQuantityString(2131558402, var22, var25);
         var23.setTitle(var28);
         SearchActivity var29 = SearchActivity.this;
         SearchActivity var30 = SearchActivity.this;
         SearchActivity.1.1 var33 = new SearchActivity.1.1(var30, var3, var9, var15, var12, var18, var6, var21);
         var29.setListAdapter(var33);
         this.val$listView.setFocusable((boolean)1);
         this.val$listView.setFocusableInTouchMode((boolean)1);
         boolean var34 = this.val$listView.requestFocus();
      }

      class 1 extends CursorAdapter {

         // $FF: synthetic field
         final int val$displayNamePos;
         // $FF: synthetic field
         final int val$flagReadPos;
         // $FF: synthetic field
         final int val$mailboxKeyPos;
         // $FF: synthetic field
         final int val$rowidPos;
         // $FF: synthetic field
         final int val$subjectPos;
         // $FF: synthetic field
         final int val$timeStampPos;


         1(Context var2, Cursor var3, int var4, int var5, int var6, int var7, int var8, int var9) {
            super(var2, var3);
            this.val$displayNamePos = var4;
            this.val$subjectPos = var5;
            this.val$timeStampPos = var6;
            this.val$flagReadPos = var7;
            this.val$rowidPos = var8;
            this.val$mailboxKeyPos = var9;
         }

         public void bindView(View var1, Context var2, Cursor var3) {
            int var5 = 2131361809;
            TextView var6 = (TextView)((TextView)var1.findViewById(var5));
            int var8 = 2131362064;
            TextView var9 = (TextView)((TextView)var1.findViewById(var8));
            int var11 = 2131362504;
            SearchActivity.TextViewSnippet var12 = (SearchActivity.TextViewSnippet)((SearchActivity.TextViewSnippet)var1.findViewById(var11));
            int var13 = this.val$displayNamePos;
            String var16 = var3.getString(var13);
            var6.setText(var16);
            int var17 = this.val$subjectPos;
            String var20 = var3.getString(var17);
            String var21 = 1.this.val$searchString;
            var12.setText(var20, var21);
            int var22 = this.val$timeStampPos;
            long var25 = var3.getLong(var22);
            Date var27 = new Date(var25);
            String var28;
            if(Utility.isDateToday(var27)) {
               var28 = SearchActivity.this.mTimeFormat.format(var27);
            } else {
               var28 = SearchActivity.this.mDateFormat.format(var27);
            }

            var9.setText(var28);
            int var29 = this.val$flagReadPos;
            boolean var32;
            if(var3.getInt(var29) != 0) {
               var32 = true;
            } else {
               var32 = false;
            }

            if(var32) {
               Typeface var33 = var12.getTypeface();
               var12.setTypeface(var33, 0);
               Typeface var34 = var6.getTypeface();
               var6.setTypeface(var34, 0);
               ColorStateList var35 = SearchActivity.this.mTextColorPrimary;
               var6.setTextColor(var35);
               Drawable var36 = var2.getResources().getDrawable(2130838032);
               var1.setBackgroundDrawable(var36);
            } else {
               Typeface var51 = var12.getTypeface();
               var12.setTypeface(var51, 1);
               Typeface var52 = var6.getTypeface();
               var6.setTypeface(var52, 1);
               ColorStateList var53 = SearchActivity.this.mTextColorPrimary;
               var6.setTextColor(var53);
               Drawable var54 = var2.getResources().getDrawable(2130838033);
               var1.setBackgroundDrawable(var54);
            }

            int var39 = this.val$rowidPos;
            long var42 = var3.getLong(var39);
            int var44 = this.val$mailboxKeyPos;
            long var47 = var3.getLong(var44);
            ListView var49 = 1.this.val$listView;
            SearchActivity.1.1.1 var50 = new SearchActivity.1.1.1();
            var49.setOnItemClickListener(var50);
         }

         public View newView(Context var1, Cursor var2, ViewGroup var3) {
            return LayoutInflater.from(var1).inflate(2130903178, var3, (boolean)0);
         }

         class 1 implements OnItemClickListener {

            1() {}

            public void onItemClick(AdapterView<?> var1, View var2, int var3, long var4) {
               SearchActivity var6 = SearchActivity.this;
               Intent var7 = new Intent(var6, MessageList.class);
               var7.putExtra("com.android.email.MessageView_message_id", var4);
               Intent var9 = var7.putExtra("com.android.email.LogProvider", "com.android.email.LogProvider");
               SearchActivity.this.startActivity(var7);
            }
         }
      }
   }

   public static class TextViewSnippet extends TextView {

      private static String sEllipsis = "…";
      private static int sTypefaceHighlight = 3;
      private String mFullText;
      private String mTargetString;


      public TextViewSnippet(Context var1) {
         super(var1);
      }

      public TextViewSnippet(Context var1, AttributeSet var2) {
         super(var1, var2);
      }

      public TextViewSnippet(Context var1, AttributeSet var2, int var3) {
         super(var1, var2, var3);
      }

      protected void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
         if(this.mFullText != null && this.mTargetString != null) {
            String var6 = this.mFullText.toLowerCase();
            String var7 = this.mTargetString.toLowerCase();
            int var10 = var6.indexOf(var7);
            int var11 = var7.length();
            int var12 = var6.length();
            TextPaint var13 = this.getPaint();
            String var14 = this.mTargetString;
            float var15 = var13.measureText(var14);
            float var16 = (float)this.getWidth();
            String var17 = null;
            if(var15 > var16) {
               String var18 = this.mFullText;
               int var19 = var10 + var11;
               var17 = var18.substring(var10, var19);
            } else {
               String var36 = sEllipsis;
               float var37 = var13.measureText(var36);
               float var38 = 2.0F * var37;
               var16 -= var38;
               int var39 = -1;
               int var40 = '\uffff';
               int var41 = -1;

               while(true) {
                  ++var39;
                  int var42 = var10 - var39;
                  int var43 = Math.max(0, var42);
                  int var44 = var10 + var11 + var39;
                  int var47 = Math.min(var12, var44);
                  if(var43 == var40 && var47 == var41) {
                     break;
                  }

                  var40 = var43;
                  var41 = var47;
                  String var50 = this.mFullText;
                  String var53 = var50.substring(var43, var47);
                  if(var13.measureText(var53) > var16) {
                     break;
                  }

                  String var56 = "%s%s%s";
                  Object[] var57 = new Object[3];
                  byte var58 = 0;
                  String var59;
                  if(var43 == 0) {
                     var59 = "";
                  } else {
                     var59 = sEllipsis;
                  }

                  var57[var58] = var59;
                  var57[1] = var53;
                  byte var60 = 2;
                  String var61;
                  if(var47 == var12) {
                     var61 = "";
                  } else {
                     var61 = sEllipsis;
                  }

                  var57[var60] = var61;
                  var17 = String.format(var56, var57);
               }
            }

            String var23 = null;
            if(var17 != null) {
               var23 = var17.toLowerCase();
            }

            SpannableString var24 = new SpannableString(var17);
            int var27 = 0;

            while(true) {
               int var28 = 0;
               if(var23 != null) {
                  var28 = var23.indexOf(var7, var27);
               }

               char var33 = '\uffff';
               if(var28 == var33) {
                  this.setText(var24);
                  super.onLayout(var1, var2, var3, var4, var5);
                  return;
               }

               int var62 = sTypefaceHighlight;
               StyleSpan var63 = new StyleSpan(var62);
               int var64 = var28 + var11;
               byte var69 = 0;
               var24.setSpan(var63, var28, var64, var69);
               var27 = var28 + var11;
            }
         } else {
            super.onLayout(var1, var2, var3, var4, var5);
         }
      }

      public void setText(String var1, String var2) {
         this.mFullText = var1;
         this.mTargetString = var2;
         this.requestLayout();
      }
   }
}
