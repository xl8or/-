package com.android.email.activity.bubblelayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import com.android.email.activity.bubblelayout.BubbleButton;
import com.android.email.activity.bubblelayout.BubbleData;
import com.android.email.mail.Address;
import java.util.ArrayList;
import java.util.Iterator;

public class BubbleLayout extends LinearLayout {

   public static final int ACTIVITY_BUBLELAYOUT_REQUEST_ADD_TO_CONTACT = 117510676;
   public static final int MOVE_TO_BCC = 2;
   public static final int MOVE_TO_CC = 1;
   public static final int MOVE_TO_TO = 0;
   public static final int MSG_BUBBLELAYOUT_REQUEST_EDIT = 117510676;
   public static final int MSG_BUBBLELAYOUT_REQUEST_REBUILD = 117510677;
   public static final int RECIPIENT_TYPE_BCC = 2;
   public static final int RECIPIENT_TYPE_CC = 1;
   public static final int RECIPIENT_TYPE_TO = 0;
   private static final int SUMMARY_TEXT_VIEW_MARGIN_RIGHT = 27;
   private static final int SUMMARY_TEXT_VIEW_PADDING_LEFT = 0;
   public static final int VIEW_MODE_BUBBLE = 2;
   public static final int VIEW_MODE_NONE = 0;
   public static final int VIEW_MODE_SUMMARY = 1;
   private LinearLayout mBottomLayout;
   private ArrayList<BubbleButton> mBubbleButtonList;
   private Builder mBubbleClickMenuDialog;
   private ArrayList<BubbleData> mBubbleDataList;
   private LinearLayout mBubbleRecipientLayout;
   private Context mContext;
   private BubbleButton mCurBubbleButton = null;
   private int mExpectedLayoutWidth = 0;
   private Handler mHandler;
   private int mIndexOfSelectedBubbleByClickingButton;
   private boolean mIsShowContextMenu = 0;
   private int mRecipientType = 0;
   private TextView mSummaryTextView;


   public BubbleLayout(Context var1, AttributeSet var2) {
      super(var1, var2);
      this.mContext = var1;
      ArrayList var3 = new ArrayList();
      this.mBubbleButtonList = var3;
   }

   public BubbleLayout(Context var1, AttributeSet var2, Handler var3) {
      super(var1, var2);
      this.mContext = var1;
      this.mHandler = var3;
      ArrayList var4 = new ArrayList();
      this.mBubbleButtonList = var4;
   }

   private Boolean addButton(BubbleButton var1) {
      int var2 = 0;
      int var3 = this.mBubbleRecipientLayout.getChildCount();
      Boolean var4;
      if(var1 == null) {
         var4 = Boolean.valueOf((boolean)0);
      } else {
         int var5 = var1.getExpectedWidth();
         if(var3 > 0 && this.mBottomLayout != null) {
            LinearLayout var13 = this.mBubbleRecipientLayout;
            int var14 = var3 - 1;
            LinearLayout var15 = (LinearLayout)var13.getChildAt(var14);
            this.mBottomLayout = var15;
         } else {
            this.mBubbleRecipientLayout.setVisibility(0);
            Context var6 = this.mContext;
            LinearLayout var7 = this.makeOneLineLinearLayout(var6);
            this.mBottomLayout = var7;
            LinearLayout var8 = this.mBubbleRecipientLayout;
            LinearLayout var9 = this.mBottomLayout;
            var8.addView(var9);
         }

         int var10 = this.mBottomLayout.getChildCount();

         for(int var11 = 0; var11 < var10; ++var11) {
            int var12 = ((BubbleButton)this.mBottomLayout.getChildAt(var11)).getExpectedWidth();
            var2 += var12;
         }

         var2 += var5;
         int var16 = this.mBubbleRecipientLayout.getWidth();
         if(var16 == 0) {
            if(this.getWidth() == 0) {
               int var17 = this.getExpectedLayoutWidth();
            } else {
               var16 = this.getWidth();
            }
         }

         if(var2 <= var16) {
            this.mBottomLayout.addView(var1);
         } else if(var2 == var5) {
            this.mBottomLayout.addView(var1);
         } else {
            Context var18 = this.mContext;
            LinearLayout var19 = this.makeOneLineLinearLayout(var18);
            this.mBottomLayout = var19;
            LinearLayout var20 = this.mBubbleRecipientLayout;
            LinearLayout var21 = this.mBottomLayout;
            var20.addView(var21);
            this.mBottomLayout.addView(var1);
         }

         var4 = Boolean.valueOf((boolean)1);
      }

      return var4;
   }

   private void addToContact(BubbleData var1) {
      try {
         Intent var2 = new Intent("android.intent.action.INSERT_OR_EDIT");
         String var3 = var1.getAddress();
         var2.putExtra("email", var3);
         String var5 = var1.getName();
         if(!TextUtils.isEmpty(var5)) {
            var2.putExtra("name", var5);
         }

         Intent var7 = var2.setType("vnd.android.cursor.item/raw_contact");
         if(this.mContext instanceof Activity) {
            ((Activity)this.mContext).startActivityForResult(var2, 117510676);
         } else {
            this.mContext.startActivity(var2);
         }
      } catch (ActivityNotFoundException var8) {
         var8.printStackTrace();
      }
   }

   private int getExpectedLayoutWidth() {
      return this.mExpectedLayoutWidth;
   }

   private int getRecipientType() {
      return this.mRecipientType;
   }

   private String getSummaryText(int var1) {
      StringBuffer var2 = new StringBuffer("");
      String var3 = "";
      String var4;
      if(this.mBubbleButtonList != null && !this.mBubbleButtonList.isEmpty()) {
         int var5 = this.mBubbleButtonList.size();
         String var6 = ((BubbleButton)this.mBubbleButtonList.get(0)).getText().toString();
         if(var5 == 1) {
            var3 = var6;
         } else {
            int var7;
            if(var1 > 0) {
               var7 = var1;
            } else {
               var7 = this.mSummaryTextView.getWidth();
            }

            int var9;
            if(var7 == 0) {
               int var8;
               if(this.getWidth() == 0) {
                  var8 = this.getExpectedLayoutWidth();
               } else {
                  var8 = this.getWidth();
               }

               var9 = var8 - 0 - 27;
            } else {
               var9 = var7 + -10;
            }

            var2.append(var6);

            for(int var11 = 1; var11 <= var5; ++var11) {
               String var12;
               if(var11 == var5) {
                  var12 = var2.toString();
               } else {
                  Context var16 = this.mContext;
                  Object[] var17 = new Object[2];
                  String var18 = var2.toString();
                  var17[0] = var18;
                  Integer var19 = Integer.valueOf(var5 - var11);
                  var17[1] = var19;
                  var12 = var16.getString(2131167068, var17);
               }

               if((int)this.mSummaryTextView.getPaint().measureText(var12) >= var9) {
                  if(var11 == 1) {
                     Context var20 = this.mContext;
                     Object[] var21 = new Object[2];
                     String var22 = var2.toString();
                     var21[0] = var22;
                     Integer var23 = Integer.valueOf(var5 - var11);
                     var21[1] = var23;
                     var3 = var20.getString(2131167068, var21);
                  }
                  break;
               }

               var3 = var12;
               if(var11 < var5) {
                  StringBuffer var13 = var2.append("; ");
                  String var14 = ((BubbleButton)this.mBubbleButtonList.get(var11)).getText().toString();
                  var13.append(var14);
               }
            }
         }

         var4 = var3;
      } else {
         var4 = "";
      }

      return var4;
   }

   private BubbleButton makeOneBubbleButton(Context var1, BubbleData var2, boolean var3) {
      BubbleButton var4 = new BubbleButton(var1, var2);
      BubbleLayout.2 var5 = new BubbleLayout.2();
      var4.setOnClickListener(var5);
      if(var3) {
         Animation var6 = AnimationUtils.loadAnimation(this.mContext, 2130968578);
         var4.setAnimation(var6);
      }

      this.mBubbleButtonList.add(var4);
      return var4;
   }

   private LinearLayout makeOneLineLinearLayout(Context var1) {
      LinearLayout var2 = new LinearLayout(var1);
      LayoutParams var3 = new LayoutParams(-1, -1);
      var2.setLayoutParams(var3);
      var2.setOrientation(0);
      return var2;
   }

   private void showBubbleButtonClickMenu(View var1) {
      BubbleButton var2 = (BubbleButton)var1;
      int var3 = this.mBubbleButtonList.indexOf(var2);
      this.mIndexOfSelectedBubbleByClickingButton = var3;
      if(this.mBubbleClickMenuDialog == null) {
         Context var4 = this.mContext;
         Builder var5 = new Builder(var4);
         this.mBubbleClickMenuDialog = var5;
         Builder var6 = this.mBubbleClickMenuDialog.setIcon(0);
      }

      BubbleData var7 = var2.getBubbleData();
      Builder var8 = this.mBubbleClickMenuDialog;
      String var9 = var7.getAddress();
      String var10 = var7.getName();
      String var11 = (new Address(var9, var10)).toString();
      var8.setTitle(var11);
      String var13 = var7.getAddress();
      String var14 = var7.getName();
      Address var15 = new Address(var13, var14);
      String[] var16 = this.getResources().getStringArray(2131296275);
      switch(this.mRecipientType) {
      case 0:
         if(var2.getBubbleData().isFromContact()) {
            var16 = this.getResources().getStringArray(2131296275);
         } else {
            var16 = this.getResources().getStringArray(2131296276);
         }
         break;
      case 1:
         if(var2.getBubbleData().isFromContact()) {
            var16 = this.getResources().getStringArray(2131296277);
         } else {
            var16 = this.getResources().getStringArray(2131296278);
         }
         break;
      case 2:
         if(var2.getBubbleData().isFromContact()) {
            var16 = this.getResources().getStringArray(2131296279);
         } else {
            var16 = this.getResources().getStringArray(2131296280);
         }
      }

      Builder var17 = this.mBubbleClickMenuDialog;
      BubbleLayout.3 var18 = new BubbleLayout.3(var15);
      var17.setItems(var16, var18);
      Builder var20 = this.mBubbleClickMenuDialog;
      BubbleLayout.4 var21 = new BubbleLayout.4();
      var20.setOnCancelListener(var21);
      AlertDialog var23 = this.mBubbleClickMenuDialog.show();
   }

   public boolean addButton(BubbleData var1, boolean var2) {
      Context var3 = this.mContext;
      BubbleButton var4 = this.makeOneBubbleButton(var3, var1, var2);
      return this.addButton(var4).booleanValue();
   }

   public boolean addButton(Address var1, boolean var2) {
      String var3 = var1.getAddress();
      String var4 = var1.getPersonal();
      BubbleData var5 = new BubbleData(var3, var4, (String)null, (boolean)0);
      Context var6 = this.mContext;
      BubbleButton var7 = this.makeOneBubbleButton(var6, var5, var2);
      return this.addButton(var7).booleanValue();
   }

   public boolean addButton(String var1, String var2, String var3, boolean var4) {
      Context var5 = this.mContext;
      BubbleData var6 = new BubbleData(var1, var2, var3, (boolean)0);
      BubbleButton var7 = this.makeOneBubbleButton(var5, var6, var4);
      return this.addButton(var7).booleanValue();
   }

   public boolean addButton(String var1, String var2, boolean var3) {
      Context var4 = this.mContext;
      BubbleData var5 = new BubbleData(var1, var2);
      BubbleButton var6 = this.makeOneBubbleButton(var4, var5, var3);
      return this.addButton(var6).booleanValue();
   }

   public boolean addButton(String var1, boolean var2) {
      Context var3 = this.mContext;
      BubbleData var4 = new BubbleData(var1);
      BubbleButton var5 = this.makeOneBubbleButton(var3, var4, var2);
      return this.addButton(var5).booleanValue();
   }

   public void clickLastButton() {
      if(!this.mBubbleButtonList.isEmpty()) {
         ArrayList var1 = this.mBubbleButtonList;
         int var2 = this.mBubbleButtonList.size() - 1;
         boolean var3 = ((BubbleButton)var1.get(var2)).performClick();
      }
   }

   public void deSelectLastButton() {
      if(!this.mBubbleButtonList.isEmpty()) {
         ArrayList var1 = this.mBubbleButtonList;
         int var2 = this.mBubbleButtonList.size() - 1;
         ((BubbleButton)var1.get(var2)).setSelected((boolean)0);
      }
   }

   public void deleteSelectedLastButton() {
      if(!this.mBubbleButtonList.isEmpty()) {
         int var1 = this.mBubbleButtonList.size() - 1;
         this.removeButton(var1);
      }
   }

   public String getAddressesAsDelimiterType() {
      StringBuffer var1 = new StringBuffer();

      String var3;
      StringBuffer var4;
      for(Iterator var2 = this.mBubbleButtonList.iterator(); var2.hasNext(); var4 = var1.append(var3).append("; ")) {
         var3 = ((BubbleButton)var2.next()).getBubbleData().getAddress();
      }

      return var1.toString().trim();
   }

   public String[] getAddressesAsStringArray() {
      int var1 = this.mBubbleButtonList.size();
      String[] var2 = new String[var1];

      for(int var3 = 0; var3 < var1; ++var3) {
         String var4 = ((BubbleButton)this.mBubbleButtonList.get(var3)).getBubbleData().getAddress();
         var2[var3] = var4;
      }

      return var2;
   }

   public LinearLayout getBubbleLayout() {
      return this.mBubbleRecipientLayout;
   }

   public BubbleButton getCurBubbleButton() {
      return this.mCurBubbleButton;
   }

   public View getSummaryTextView() {
      return this.mSummaryTextView;
   }

   public boolean isEmpty() {
      boolean var1;
      if(this.mBubbleButtonList != null && !this.mBubbleButtonList.isEmpty() && this.mBubbleRecipientLayout != null && this.mBubbleRecipientLayout.getChildCount() != 0) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public boolean isSelectedLastButton() {
      boolean var3;
      if(!this.mBubbleButtonList.isEmpty()) {
         ArrayList var1 = this.mBubbleButtonList;
         int var2 = this.mBubbleButtonList.size() - 1;
         var3 = ((BubbleButton)var1.get(var2)).isSelected();
      } else {
         var3 = false;
      }

      return var3;
   }

   public boolean isValidAddress() {
      Iterator var1 = this.mBubbleButtonList.iterator();

      boolean var2;
      while(true) {
         if(var1.hasNext()) {
            if(((BubbleButton)var1.next()).getBubbleData().isValidAddress()) {
               continue;
            }

            var2 = false;
            break;
         }

         var2 = true;
         break;
      }

      return var2;
   }

   public void rebuildBubbleLayout(int var1) {
      synchronized(this){}
      int var2 = 0;
      int var3 = -1;
      int var4 = 0;

      try {
         while(true) {
            int var5 = this.mBubbleButtonList.size();
            if(var4 >= var5) {
               break;
            }

            byte var6 = ((BubbleButton)this.mBubbleButtonList.get(var4)).isFocused();
            if(1 == var6) {
               var3 = var4;
               break;
            }

            ++var4;
         }

         if(-1 != var3) {
            this.mBubbleRecipientLayout.setFocusable((boolean)1);
            boolean var7 = this.mBubbleRecipientLayout.requestFocus();
         }

         int var8 = this.mBubbleRecipientLayout.getChildCount();

         for(var4 = 0; var4 < var8; ++var4) {
            ((ViewGroup)this.mBubbleRecipientLayout.getChildAt(var4)).removeAllViews();
         }

         this.mBubbleRecipientLayout.removeAllViews();
         if(!this.mBubbleButtonList.isEmpty()) {
            Context var9 = this.mContext;
            LinearLayout var10 = this.makeOneLineLinearLayout(var9);
            this.mBottomLayout = var10;
            LinearLayout var11 = this.mBubbleRecipientLayout;
            LinearLayout var12 = this.mBottomLayout;
            var11.addView(var12);
         } else {
            this.mBubbleRecipientLayout.setVisibility(8);
         }

         Iterator var13 = this.mBubbleButtonList.iterator();

         while(var13.hasNext()) {
            BubbleButton var14 = (BubbleButton)var13.next();
            int var15 = var14.getExpectedWidth();
            var2 += var15;
            int var16;
            if(var1 > 0) {
               var16 = var1;
            } else {
               var16 = this.mBubbleRecipientLayout.getWidth();
            }

            if(var2 <= var16) {
               this.mBottomLayout.addView(var14);
            } else {
               int var18 = var14.getExpectedWidth();
               if(var2 == var18) {
                  this.mBottomLayout.addView(var14);
               } else {
                  Context var19 = this.mContext;
                  LinearLayout var20 = this.makeOneLineLinearLayout(var19);
                  this.mBottomLayout = var20;
                  LinearLayout var21 = this.mBubbleRecipientLayout;
                  LinearLayout var22 = this.mBottomLayout;
                  var21.addView(var22);
                  this.mBottomLayout.addView(var14);
                  var2 = var14.getExpectedWidth();
               }
            }
         }

         if(-1 != var3) {
            boolean var23 = ((BubbleButton)this.mBubbleButtonList.get(var3)).requestFocus();
            this.mBubbleRecipientLayout.setFocusable((boolean)0);
         }
      } finally {
         ;
      }

   }

   public void rebuildSummaryText(int var1) {
      TextView var2 = this.mSummaryTextView;
      String var3 = this.getSummaryText(var1);
      var2.setText(var3);
   }

   public void registerChildViewsFromOwnXML() {
      TextView var1 = (TextView)this.getChildAt(0);
      this.mSummaryTextView = var1;
      LinearLayout var2 = (LinearLayout)this.getChildAt(1);
      this.mBubbleRecipientLayout = var2;
   }

   public void registerMessageHandler(Handler var1) {
      this.mHandler = var1;
   }

   public void removeAll() {
      this.mBubbleButtonList.clear();
      this.rebuildBubbleLayout(0);
      this.rebuildSummaryText(0);
   }

   public BubbleButton removeButton(int var1) {
      BubbleButton var2;
      BubbleButton var5;
      try {
         var2 = (BubbleButton)this.mBubbleButtonList.remove(var1);
      } catch (ArrayIndexOutOfBoundsException var6) {
         var6.printStackTrace();
         var5 = null;
         return var5;
      } catch (IndexOutOfBoundsException var7) {
         var7.printStackTrace();
         var5 = null;
         return var5;
      }

      if(this.mHandler != null) {
         Animation var3 = AnimationUtils.loadAnimation(this.mContext, 2130968579);
         BubbleLayout.1 var4 = new BubbleLayout.1();
         var3.setAnimationListener(var4);
         var2.setAnimation(var3);
         var2.startAnimation(var3);
      } else {
         this.rebuildBubbleLayout(0);
      }

      var5 = var2;
      return var5;
   }

   public boolean removeButton(BubbleButton var1) {
      boolean var2 = this.mBubbleButtonList.remove(var1);
      this.rebuildBubbleLayout(0);
      return var2;
   }

   public void resetCurBubbleButton() {
      this.mCurBubbleButton = null;
   }

   public boolean selectLastButton() {
      boolean var3;
      if(!this.mBubbleButtonList.isEmpty()) {
         ArrayList var1 = this.mBubbleButtonList;
         int var2 = this.mBubbleButtonList.size() - 1;
         ((BubbleButton)var1.get(var2)).setSelected((boolean)1);
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public void setBtnListFromDataList(ArrayList<BubbleData> var1) {
      ArrayList var2 = new ArrayList(var1);
      this.mBubbleDataList = var2;
   }

   public void setExpectedLayoutWidth(int var1) {
      this.mExpectedLayoutWidth = var1;
   }

   public void setRecipientType(int var1) {
      this.mRecipientType = var1;
   }

   public boolean setViewMode(int var1) {
      boolean var2;
      switch(var1) {
      case 0:
         this.setVisibility(8);
         break;
      case 1:
         this.setVisibility(0);
         TextView var3 = this.mSummaryTextView;
         String var4 = this.getSummaryText(0);
         var3.setText(var4);
         this.mSummaryTextView.setVisibility(0);
         this.mBubbleRecipientLayout.setVisibility(8);
         break;
      case 2:
         this.setVisibility(0);
         this.mSummaryTextView.setVisibility(8);
         if(this.mBubbleRecipientLayout.getChildCount() > 0) {
            this.mBubbleRecipientLayout.setVisibility(0);
         } else {
            this.mBubbleRecipientLayout.setVisibility(8);
         }
         break;
      default:
         var2 = false;
         return var2;
      }

      var2 = true;
      return var2;
   }

   public boolean updateBubbleAfterSavingContact() {
      ArrayList var1 = this.mBubbleButtonList;
      int var2 = this.mIndexOfSelectedBubbleByClickingButton;
      ((BubbleButton)var1.get(var2)).refreshButton();
      return true;
   }

   class 3 implements OnClickListener {

      // $FF: synthetic field
      final Address val$address;


      3(Address var2) {
         this.val$address = var2;
      }

      public void onClick(DialogInterface var1, int var2) {
         boolean var3 = (boolean)(BubbleLayout.this.mIsShowContextMenu = (boolean)0);
         switch(var2) {
         case 0:
            BubbleLayout var4 = BubbleLayout.this;
            int var5 = BubbleLayout.this.mIndexOfSelectedBubbleByClickingButton;
            var4.removeButton(var5);
            return;
         case 1:
            if(BubbleLayout.this.mHandler != null) {
               Handler var7 = BubbleLayout.this.mHandler;
               ArrayList var8 = BubbleLayout.this.mBubbleButtonList;
               int var9 = BubbleLayout.this.mIndexOfSelectedBubbleByClickingButton;
               String var10 = ((BubbleButton)var8.get(var9)).getBubbleData().getAddress();
               Message.obtain(var7, 117510676, var10).sendToTarget();
            }

            BubbleButton var11 = BubbleLayout.this.mCurBubbleButton;
            BubbleLayout var12 = BubbleLayout.this;
            BubbleLayout var13 = BubbleLayout.this;
            int var14 = BubbleLayout.this.mIndexOfSelectedBubbleByClickingButton;
            BubbleButton var15 = var13.removeButton(var14);
            var12.mCurBubbleButton = var15;
            if(var11 == null) {
               return;
            }

            boolean var17 = BubbleLayout.this.mBubbleButtonList.add(var11);
            return;
         case 2:
            BubbleLayout var18 = BubbleLayout.this;
            int var19 = BubbleLayout.this.mIndexOfSelectedBubbleByClickingButton;
            var18.removeButton(var19);
            switch(BubbleLayout.this.mRecipientType) {
            case 0:
               Handler var21 = BubbleLayout.this.mHandler;
               Address var22 = this.val$address;
               Message.obtain(var21, 1, var22).sendToTarget();
               break;
            case 1:
            case 2:
               Handler var23 = BubbleLayout.this.mHandler;
               Address var24 = this.val$address;
               Message.obtain(var23, 0, var24).sendToTarget();
            }

            BubbleLayout.this.rebuildBubbleLayout(0);
            return;
         case 3:
            BubbleLayout var25 = BubbleLayout.this;
            int var26 = BubbleLayout.this.mIndexOfSelectedBubbleByClickingButton;
            var25.removeButton(var26);
            switch(BubbleLayout.this.mRecipientType) {
            case 0:
            case 1:
               Handler var28 = BubbleLayout.this.mHandler;
               Address var29 = this.val$address;
               Message.obtain(var28, 2, var29).sendToTarget();
               break;
            case 2:
               Handler var30 = BubbleLayout.this.mHandler;
               Address var31 = this.val$address;
               Message.obtain(var30, 1, var31).sendToTarget();
            }

            BubbleLayout.this.rebuildBubbleLayout(0);
            return;
         case 4:
            BubbleLayout var32 = BubbleLayout.this;
            ArrayList var33 = BubbleLayout.this.mBubbleButtonList;
            int var34 = BubbleLayout.this.mIndexOfSelectedBubbleByClickingButton;
            BubbleData var35 = ((BubbleButton)var33.get(var34)).getBubbleData();
            var32.addToContact(var35);
            return;
         default:
         }
      }
   }

   class 2 implements android.view.View.OnClickListener {

      2() {}

      public void onClick(View var1) {
         if(!BubbleLayout.this.mIsShowContextMenu) {
            boolean var2 = (boolean)(BubbleLayout.this.mIsShowContextMenu = (boolean)1);
            BubbleLayout.this.showBubbleButtonClickMenu(var1);
         }
      }
   }

   class 1 implements AnimationListener {

      1() {}

      public void onAnimationEnd(Animation var1) {
         if(BubbleLayout.this.mHandler != null) {
            Message.obtain(BubbleLayout.this.mHandler, 117510677).sendToTarget();
         }
      }

      public void onAnimationRepeat(Animation var1) {}

      public void onAnimationStart(Animation var1) {}
   }

   class 4 implements OnCancelListener {

      4() {}

      public void onCancel(DialogInterface var1) {
         boolean var2 = (boolean)(BubbleLayout.this.mIsShowContextMenu = (boolean)0);
      }
   }
}
