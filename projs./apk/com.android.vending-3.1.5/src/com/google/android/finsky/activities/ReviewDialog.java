package com.google.android.finsky.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.RatingBar.OnRatingBarChangeListener;
import com.google.android.finsky.remoting.protos.Rev;

public class ReviewDialog extends DialogFragment {

   private static final String ARG_ID_DOC_ID = "doc_id";
   private static final String ARG_ID_PREVIOUS_COMMENT = "previous_comment";
   private static final String ARG_ID_PREVIOUS_RATING = "previous_rating";
   private static final String ARG_ID_PREVIOUS_TITLE = "previous_title";
   private static final String ARG_ID_REVIEW_MODE = "review_mode";
   private static final int[] DESCRIPTION_MAP = new int[]{2131230994, 2131230995, 2131230996, 2131230997, 2131230998, 2131230999};
   public static final int MODE_REVIEW_HIDDEN = 1;
   public static final int MODE_REVIEW_OPTIONAL = 2;
   public static final int MODE_REVIEW_REQUIRED = 3;
   TextView mCommentView;
   RatingBar mRatingBar;
   int mReviewMode;
   TextView mTitleView;


   public ReviewDialog() {}

   private ReviewDialog.Listener getListener() {
      Fragment var1 = this.getTargetFragment();
      ReviewDialog.Listener var3;
      if(var1 instanceof ReviewDialog.Listener) {
         var3 = (ReviewDialog.Listener)var1;
      } else {
         FragmentActivity var2 = this.getActivity();
         if(var2 instanceof ReviewDialog.Listener) {
            var3 = (ReviewDialog.Listener)var2;
         } else {
            var3 = null;
         }
      }

      return var3;
   }

   private String getUserComment() {
      return this.mCommentView.getText().toString().trim();
   }

   private int getUserRating() {
      return Math.round(this.mRatingBar.getRating());
   }

   private String getUserTitle() {
      return this.mTitleView.getText().toString().trim();
   }

   public static ReviewDialog newInstance(int var0, String var1, Rev.Review var2) {
      ReviewDialog var3 = new ReviewDialog();
      Bundle var4 = new Bundle();
      var4.putInt("review_mode", var0);
      var4.putString("doc_id", var1);
      if(var2 != null) {
         int var5 = var2.getStarRating();
         var4.putInt("previous_rating", var5);
         String var6 = var2.getTitle();
         var4.putString("previous_title", var6);
         String var7 = var2.getComment();
         var4.putString("previous_comment", var7);
      }

      var3.setArguments(var4);
      return var3;
   }

   private void syncOkButtonState() {
      byte var1;
      if(this.getUserRating() > 0) {
         var1 = 1;
      } else {
         var1 = 0;
      }

      if(var1 != 0) {
         boolean var2 = TextUtils.isEmpty(this.getUserTitle());
         boolean var3 = TextUtils.isEmpty(this.getUserComment());
         if(this.mReviewMode == 3) {
            if(!var2 && !var3) {
               var1 = 1;
            } else {
               var1 = 0;
            }
         } else if(var2 && !var3) {
            var1 = 0;
         } else {
            var1 = 1;
         }
      }

      ((AlertDialog)this.getDialog()).getButton(-1).setEnabled((boolean)var1);
   }

   public Dialog onCreateDialog(Bundle var1) {
      FragmentActivity var2 = this.getActivity();
      Bundle var3 = this.getArguments();
      int var4 = var3.getInt("review_mode");
      this.mReviewMode = var4;
      String var5 = var3.getString("doc_id");
      Bundle var6;
      if(var1 != null) {
         var6 = var1;
      } else {
         var6 = var3;
      }

      int var7 = var6.getInt("previous_rating", 0);
      String var8 = var6.getString("previous_title");
      String var9 = var6.getString("previous_comment");
      ReviewDialog.ReviewAlertDialog var10 = new ReviewDialog.ReviewAlertDialog(var2);
      View var11 = ((LayoutInflater)var2.getSystemService("layout_inflater")).inflate(2130968700, (ViewGroup)null);
      RatingBar var12 = (RatingBar)var11.findViewById(2131755295);
      this.mRatingBar = var12;
      TextView var13 = (TextView)var11.findViewById(2131755296);
      EditText var14 = (EditText)var11.findViewById(2131755297);
      this.mTitleView = var14;
      EditText var15 = (EditText)var11.findViewById(2131755298);
      this.mCommentView = var15;
      RatingBar var16 = this.mRatingBar;
      float var17 = (float)var7;
      var16.setRating(var17);
      if(this.mReviewMode == 1) {
         this.mTitleView.setVisibility(8);
         this.mCommentView.setVisibility(8);
      } else {
         this.mTitleView.setText(var8);
         this.mCommentView.setText(var9);
      }

      var10.setIcon(17301619);
      var10.setTitle(2131230991);
      var10.setView(var11);
      this.setCancelable((boolean)1);
      ReviewDialog.1 var18 = new ReviewDialog.1();
      this.mTitleView.addTextChangedListener(var18);
      this.mCommentView.addTextChangedListener(var18);
      String var19 = var2.getString(17039370);
      ReviewDialog.2 var20 = new ReviewDialog.2(var5);
      var10.setButton(-1, var19, var20);
      String var21 = var2.getString(17039360);
      ReviewDialog.3 var22 = new ReviewDialog.3();
      var10.setButton(-1, var21, var22);
      RatingBar var23 = this.mRatingBar;
      ReviewDialog.4 var24 = new ReviewDialog.4(var13);
      var23.setOnRatingBarChangeListener(var24);
      return var10;
   }

   public void onSaveInstanceState(Bundle var1) {
      int var2 = this.getUserRating();
      var1.putInt("previous_rating", var2);
      String var3 = this.getUserTitle();
      var1.putString("previous_title", var3);
      String var4 = this.getUserComment();
      var1.putString("previous_comment", var4);
   }

   public void onStart() {
      super.onStart();
      this.syncOkButtonState();
   }

   class 4 implements OnRatingBarChangeListener {

      // $FF: synthetic field
      final TextView val$ratingDescription;


      4(TextView var2) {
         this.val$ratingDescription = var2;
      }

      public void onRatingChanged(RatingBar var1, float var2, boolean var3) {
         ReviewDialog.this.syncOkButtonState();
         TextView var4 = this.val$ratingDescription;
         int[] var5 = ReviewDialog.DESCRIPTION_MAP;
         int var6 = Math.round(var2);
         int var7 = var5[var6];
         var4.setText(var7);
      }
   }

   class 3 implements OnClickListener {

      3() {}

      public void onClick(DialogInterface var1, int var2) {}
   }

   private static class ReviewAlertDialog extends AlertDialog {

      public ReviewAlertDialog(Context var1) {
         super(var1);
      }
   }

   public interface Listener {

      void onPositiveClick(String var1, int var2, String var3, String var4);
   }

   class 1 implements TextWatcher {

      1() {}

      public void afterTextChanged(Editable var1) {}

      public void beforeTextChanged(CharSequence var1, int var2, int var3, int var4) {}

      public void onTextChanged(CharSequence var1, int var2, int var3, int var4) {
         ReviewDialog.this.syncOkButtonState();
      }
   }

   class 2 implements OnClickListener {

      // $FF: synthetic field
      final String val$docId;


      2(String var2) {
         this.val$docId = var2;
      }

      public void onClick(DialogInterface var1, int var2) {
         ReviewDialog.Listener var3 = ReviewDialog.this.getListener();
         if(var3 != null) {
            String var4 = this.val$docId;
            int var5 = ReviewDialog.this.getUserRating();
            String var6 = ReviewDialog.this.getUserTitle();
            String var7 = ReviewDialog.this.getUserComment();
            var3.onPositiveClick(var4, var5, var6, var7);
         }
      }
   }
}
