package com.google.android.finsky.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

public class RateReviewDialog extends DialogFragment {

   private static final String ARG_ID_DOC_ID = "doc_id";
   private static final String ARG_ID_PREVIOUS_RATING = "previous_rating";
   private static final String ARG_ID_REVIEW_ID = "rating_id";
   RateReviewDialog.CommentRating mRating = null;


   public RateReviewDialog() {}

   private RateReviewDialog.Listener getListener() {
      Fragment var1 = this.getTargetFragment();
      RateReviewDialog.Listener var3;
      if(var1 instanceof RateReviewDialog.Listener) {
         var3 = (RateReviewDialog.Listener)var1;
      } else {
         FragmentActivity var2 = this.getActivity();
         if(var2 instanceof RateReviewDialog.Listener) {
            var3 = (RateReviewDialog.Listener)var2;
         } else {
            var3 = null;
         }
      }

      return var3;
   }

   private RateReviewDialog.CommentRating getRatingForIndex(int var1) {
      RateReviewDialog.CommentRating[] var2 = RateReviewDialog.CommentRating.values();
      int var3 = var2.length;
      int var4 = 0;

      RateReviewDialog.CommentRating var5;
      while(true) {
         if(var4 >= var3) {
            var5 = null;
            break;
         }

         var5 = var2[var4];
         if(var5.getIndex() == var1) {
            break;
         }

         ++var4;
      }

      return var5;
   }

   public static RateReviewDialog newInstance(String var0, String var1, RateReviewDialog.CommentRating var2) {
      RateReviewDialog var3 = new RateReviewDialog();
      Bundle var4 = new Bundle();
      var4.putString("doc_id", var0);
      var4.putString("rating_id", var1);
      if(var2 != null) {
         int var5 = var2.getIndex();
         var4.putInt("previous_rating", var5);
      }

      var3.setArguments(var4);
      return var3;
   }

   private void setRating(int var1) {
      RateReviewDialog.CommentRating var2 = this.getRatingForIndex(var1);
      this.mRating = var2;
      this.syncOkButtonState();
   }

   private void syncOkButtonState() {
      byte var1;
      if(this.mRating != null) {
         var1 = 1;
      } else {
         var1 = 0;
      }

      ((AlertDialog)this.getDialog()).getButton(-1).setEnabled((boolean)var1);
   }

   public Dialog onCreateDialog(Bundle var1) {
      FragmentActivity var2 = this.getActivity();
      Bundle var3 = this.getArguments();
      String var4 = var3.getString("rating_id");
      String var5 = var3.getString("doc_id");
      Bundle var6;
      if(var1 != null) {
         var6 = var1;
      } else {
         var6 = var3;
      }

      int var7 = var6.getInt("previous_rating", -1);
      RateReviewDialog.CommentRating var8 = this.getRatingForIndex(var7);
      this.mRating = var8;
      CharSequence[] var9 = new CharSequence[RateReviewDialog.CommentRating.values().length];
      RateReviewDialog.CommentRating[] var10 = RateReviewDialog.CommentRating.values();
      int var11 = var10.length;

      for(int var12 = 0; var12 < var11; ++var12) {
         RateReviewDialog.CommentRating var13 = var10[var12];
         int var14 = var13.getIndex();
         int var15 = var13.getTextResourceId();
         String var16 = var2.getString(var15);
         var9[var14] = var16;
      }

      Builder var17 = new Builder(var2);
      Builder var18 = var17.setTitle(2131231003);
      Builder var19 = var17.setCancelable((boolean)1);
      RateReviewDialog.1 var20 = new RateReviewDialog.1();
      var17.setSingleChoiceItems(var9, var7, var20);
      RateReviewDialog.2 var22 = new RateReviewDialog.2(var5, var4);
      var17.setPositiveButton(17039370, var22);
      Builder var24 = var17.setNegativeButton(17039360, (OnClickListener)null);
      return var17.create();
   }

   public void onSaveInstanceState(Bundle var1) {
      if(this.mRating != null) {
         int var2 = this.mRating.getIndex();
         var1.putInt("previous_rating", var2);
      }
   }

   public void onStart() {
      super.onStart();
      this.syncOkButtonState();
   }

   class 1 implements OnClickListener {

      1() {}

      public void onClick(DialogInterface var1, int var2) {
         RateReviewDialog.this.setRating(var2);
         RateReviewDialog.this.syncOkButtonState();
      }
   }

   class 2 implements OnClickListener {

      // $FF: synthetic field
      final String val$docId;
      // $FF: synthetic field
      final String val$reviewId;


      2(String var2, String var3) {
         this.val$docId = var2;
         this.val$reviewId = var3;
      }

      public void onClick(DialogInterface var1, int var2) {
         RateReviewDialog.Listener var3 = RateReviewDialog.this.getListener();
         if(var3 != null) {
            if(RateReviewDialog.this.mRating != null) {
               String var4 = this.val$docId;
               String var5 = this.val$reviewId;
               RateReviewDialog.CommentRating var6 = RateReviewDialog.this.mRating;
               var3.onPositiveClick(var4, var5, var6);
            }
         }
      }
   }

   public interface Listener {

      void onPositiveClick(String var1, String var2, RateReviewDialog.CommentRating var3);
   }

   public static enum CommentRating {

      // $FF: synthetic field
      private static final RateReviewDialog.CommentRating[] $VALUES;
      HELPFUL,
      NOT_HELPFUL,
      SPAM("SPAM", 0, 3, 2, 2131231004);
      private int mDisplayStringId;
      private int mIndex;
      private int mRpcId;


      static {
         byte var0 = 1;
         byte var1 = 0;
         HELPFUL = new RateReviewDialog.CommentRating("HELPFUL", 1, var0, var1, 2131231005);
         byte var2 = 2;
         byte var3 = 2;
         byte var4 = 1;
         NOT_HELPFUL = new RateReviewDialog.CommentRating("NOT_HELPFUL", var2, var3, var4, 2131231006);
         RateReviewDialog.CommentRating[] var5 = new RateReviewDialog.CommentRating[3];
         RateReviewDialog.CommentRating var6 = SPAM;
         var5[0] = var6;
         RateReviewDialog.CommentRating var7 = HELPFUL;
         var5[1] = var7;
         RateReviewDialog.CommentRating var8 = NOT_HELPFUL;
         var5[2] = var8;
         $VALUES = var5;
      }

      private CommentRating(String var1, int var2, int var3, int var4, int var5) {
         this.mRpcId = var3;
         this.mIndex = var4;
         this.mDisplayStringId = var5;
      }

      private int getIndex() {
         return this.mIndex;
      }

      private int getTextResourceId() {
         return this.mDisplayStringId;
      }

      public int getRpcId() {
         return this.mRpcId;
      }
   }
}
