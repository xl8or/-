package com.google.android.finsky.activities;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RatingBar.OnRatingBarChangeListener;
import com.android.volley.NetworkError;
import com.android.volley.Response;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.FinskyInstance;
import com.google.android.finsky.activities.ReviewsFragment;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.local.AssetStore;
import com.google.android.finsky.local.LocalAsset;
import com.google.android.finsky.remoting.protos.Rev;
import com.google.android.finsky.remoting.protos.ReviewResponse;
import com.google.android.finsky.utils.CorpusMetadata;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.PackageInfoCache;
import com.google.android.vending.remoting.protos.VendingProtos;

public class ReviewsFragmentXLarge extends ReviewsFragment {

   private boolean mCanPostComments;
   private Button mCancel;
   private final Response.ErrorListener mErrorListener;
   private final Response.Listener<ReviewResponse> mListener;
   private RatingBar mRating;
   private EditText mReviewEdit;
   private View[] mReviewElements;
   private EditText mReviewTitle;
   private Button mSubmit;
   private final TextWatcher mTextWatcher;
   private TextView mUserReviewHeader;


   public ReviewsFragmentXLarge() {
      ReviewsFragmentXLarge.1 var1 = new ReviewsFragmentXLarge.1();
      this.mListener = var1;
      ReviewsFragmentXLarge.2 var2 = new ReviewsFragmentXLarge.2();
      this.mErrorListener = var2;
      ReviewsFragmentXLarge.3 var3 = new ReviewsFragmentXLarge.3();
      this.mTextWatcher = var3;
   }

   private boolean canPostComments() {
      boolean var1 = false;
      if(this.canRate()) {
         VendingProtos.GetMarketMetadataResponseProto var2 = FinskyInstance.get().getMarketMetadata();
         if(var2.hasCommentPostEnabled() && var2.getCommentPostEnabled()) {
            var1 = true;
         }
      }

      return var1;
   }

   private boolean canRate() {
      byte var1 = 0;
      if(this.mDocument.getBackend() == 3) {
         AssetStore var2 = FinskyInstance.get().getAssetStore();
         String var3 = this.mDocument.getAppDetails().getPackageName();
         LocalAsset var4 = var2.getAsset(var3);
         if(var4 != null && !var4.isDownloadingOrInstalling() && var4.isInstalled()) {
            var1 = 1;
         }
      } else {
         Document var5 = this.mDocument;
         PackageInfoCache var6 = FinskyApp.get().getPackageInfoCache();
         var1 = var5.ownedByUser(var6);
      }

      return (boolean)var1;
   }

   private void collapseForm() {
      if(this.mCanPostComments) {
         this.mReviewEdit.setVisibility(8);
         this.mSubmit.setVisibility(8);
         this.mCancel.setVisibility(8);
      }
   }

   private void configureViews() {
      EditText var1 = this.mReviewTitle;
      ReviewsFragmentXLarge.4 var2 = new ReviewsFragmentXLarge.4();
      var1.setOnClickListener(var2);
      EditText var3 = this.mReviewTitle;
      ReviewsFragmentXLarge.5 var4 = new ReviewsFragmentXLarge.5();
      var3.setOnFocusChangeListener(var4);
      Button var5 = this.mCancel;
      ReviewsFragmentXLarge.6 var6 = new ReviewsFragmentXLarge.6();
      var5.setOnClickListener(var6);
      Button var7 = this.mSubmit;
      ReviewsFragmentXLarge.7 var8 = new ReviewsFragmentXLarge.7();
      var7.setOnClickListener(var8);
      RatingBar var9 = this.mRating;
      ReviewsFragmentXLarge.8 var10 = new ReviewsFragmentXLarge.8();
      var9.setOnRatingBarChangeListener(var10);
      EditText var11 = this.mReviewTitle;
      TextWatcher var12 = this.mTextWatcher;
      var11.addTextChangedListener(var12);
      EditText var13 = this.mReviewEdit;
      TextWatcher var14 = this.mTextWatcher;
      var13.addTextChangedListener(var14);
      this.updateSubmitState();
   }

   private void expandForm() {
      View[] var1 = this.mReviewElements;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         var1[var3].setVisibility(0);
      }

      boolean var4 = this.mReviewTitle.requestFocus();
      EditText var5 = this.mReviewTitle;
      this.setKeyboardVisible(var5, (boolean)1);
   }

   private void inflateReviewForm(View var1) {
      boolean var2 = this.canPostComments();
      this.mCanPostComments = var2;
      int var3 = 2131755217;
      if(!this.mCanPostComments) {
         var3 = 2131755219;
      }

      View var4 = ((ViewStub)var1.findViewById(var3)).inflate();
      if(this.canRate()) {
         this.mUserReviewHeader.setVisibility(0);
         var4.setVisibility(0);
      }
   }

   private void resetForm() {
      this.mReviewTitle.clearFocus();
      this.mReviewTitle.setText("");
      this.mReviewEdit.clearFocus();
      this.mReviewEdit.setText("");
      this.mRating.setRating(0.0F);
      EditText var1 = this.mReviewEdit;
      this.setKeyboardVisible(var1, (boolean)0);
   }

   private void setFormEnabled(boolean var1) {
      this.mReviewTitle.setEnabled(var1);
      this.mReviewEdit.setEnabled(var1);
      this.mRating.setEnabled(var1);
      this.mSubmit.setEnabled(var1);
      this.mCancel.setEnabled(var1);
   }

   private void setKeyboardVisible(View var1, boolean var2) {
      InputMethodManager var3 = (InputMethodManager)this.getActivity().getSystemService("input_method");
      if(var2) {
         var3.showSoftInput(var1, 0);
      } else {
         IBinder var5 = var1.getWindowToken();
         var3.hideSoftInputFromWindow(var5, 0);
      }
   }

   private void updateSubmitState() {
      boolean var1 = true;
      boolean var2;
      if(this.mRating.getRating() > 0.0F) {
         var2 = true;
      } else {
         var2 = false;
      }

      boolean var3 = true & var2;
      if(this.mCanPostComments) {
         boolean var4;
         if(this.mReviewEdit.getText().length() > 0) {
            var4 = true;
         } else {
            var4 = false;
         }

         boolean var5 = var3 & var4;
         if(this.mReviewTitle.getText().length() <= 0) {
            var1 = false;
         }

         var3 = var5 & var1;
      }

      this.mSubmit.setEnabled(var3);
   }

   public void onActivityCreated(Bundle var1) {
      super.onActivityCreated(var1);
      this.configureViews();
   }

   public View onCreateView(LayoutInflater var1, ViewGroup var2, Bundle var3) {
      View var4 = super.onCreateView(var1, var2, var3);
      TextView var5 = (TextView)var4.findViewById(2131755299);
      TextView var6 = (TextView)var4.findViewById(2131755216);
      this.mUserReviewHeader = var6;
      Context var7 = var2.getContext();
      int var8 = this.mDocument.getBackend();
      int var9 = CorpusMetadata.getBackendForegroundColor(var7, var8);
      var5.setTextColor(var9);
      this.mUserReviewHeader.setText(2131231144);
      this.mUserReviewHeader.setTextColor(var9);
      this.inflateReviewForm(var4);
      EditText var10 = (EditText)var4.findViewById(2131755221);
      this.mReviewTitle = var10;
      EditText var11 = (EditText)var4.findViewById(2131755222);
      this.mReviewEdit = var11;
      RatingBar var12 = (RatingBar)var4.findViewById(2131755220);
      this.mRating = var12;
      Button var13 = (Button)var4.findViewById(2131755223);
      this.mSubmit = var13;
      Button var14 = (Button)var4.findViewById(2131755224);
      this.mCancel = var14;
      if(!this.mCanPostComments) {
         FragmentActivity var15 = this.getActivity();
         EditText var16 = new EditText(var15);
         this.mReviewTitle = var16;
         FragmentActivity var17 = this.getActivity();
         EditText var18 = new EditText(var17);
         this.mReviewEdit = var18;
         FragmentActivity var19 = this.getActivity();
         Button var20 = new Button(var19);
         this.mCancel = var20;
      }

      View[] var21 = new View[5];
      EditText var22 = this.mReviewTitle;
      var21[0] = var22;
      EditText var23 = this.mReviewEdit;
      var21[1] = var23;
      RatingBar var24 = this.mRating;
      var21[2] = var24;
      Button var25 = this.mSubmit;
      var21[3] = var25;
      Button var26 = this.mCancel;
      var21[4] = var26;
      this.mReviewElements = var21;
      return var4;
   }

   protected void rebindViews() {
      super.rebindViews();
      this.mReviewsBinder.getAdapter().setHeaderVisible((boolean)0);
      Rev.Review var1 = this.mDfeDetails.getUserReview();
      if(var1 != null) {
         EditText var2 = this.mReviewTitle;
         String var3 = var1.getTitle();
         var2.setText(var3);
         EditText var4 = this.mReviewEdit;
         String var5 = var1.getComment();
         var4.setText(var5);
         RatingBar var6 = this.mRating;
         float var7 = (float)var1.getStarRating();
         var6.setRating(var7);
      }
   }

   class 5 implements OnFocusChangeListener {

      5() {}

      public void onFocusChange(View var1, boolean var2) {
         if(var2) {
            ReviewsFragmentXLarge.this.expandForm();
         }
      }
   }

   class 6 implements OnClickListener {

      6() {}

      public void onClick(View var1) {
         ReviewsFragmentXLarge.this.resetForm();
         ReviewsFragmentXLarge.this.collapseForm();
      }
   }

   class 3 implements TextWatcher {

      3() {}

      public void afterTextChanged(Editable var1) {
         ReviewsFragmentXLarge.this.updateSubmitState();
      }

      public void beforeTextChanged(CharSequence var1, int var2, int var3, int var4) {}

      public void onTextChanged(CharSequence var1, int var2, int var3, int var4) {}
   }

   class 4 implements OnClickListener {

      4() {}

      public void onClick(View var1) {
         ReviewsFragmentXLarge.this.expandForm();
      }
   }

   class 1 implements Response.Listener<ReviewResponse> {

      1() {}

      public void onResponse(ReviewResponse var1) {
         ReviewsFragmentXLarge.this.collapseForm();
         ReviewsFragmentXLarge.this.setFormEnabled((boolean)1);
         Toast.makeText(ReviewsFragmentXLarge.this.getActivity(), 2131230989, 0).show();
      }
   }

   class 2 implements Response.ErrorListener {

      2() {}

      public void onErrorResponse(Response.ErrorCode var1, String var2, NetworkError var3) {
         Toast.makeText(ReviewsFragmentXLarge.this.getActivity(), 2131230990, 0).show();
         Object[] var4 = new Object[2];
         String var5 = var1.name();
         var4[0] = var5;
         var4[1] = var2;
         FinskyLog.d("Unable to submit response: (%s) %s", var4);
         ReviewsFragmentXLarge.this.setFormEnabled((boolean)1);
      }
   }

   class 8 implements OnRatingBarChangeListener {

      8() {}

      public void onRatingChanged(RatingBar var1, float var2, boolean var3) {
         ReviewsFragmentXLarge.this.updateSubmitState();
      }
   }

   class 7 implements OnClickListener {

      7() {}

      public void onClick(View var1) {
         ReviewsFragmentXLarge.this.setFormEnabled((boolean)0);
         String var2 = ReviewsFragmentXLarge.this.mDocument.getDocId();
         String var3 = String.valueOf(ReviewsFragmentXLarge.this.mReviewTitle.getText());
         String var4 = String.valueOf(ReviewsFragmentXLarge.this.mReviewEdit.getText());
         int var5 = (int)ReviewsFragmentXLarge.this.mRating.getRating();
         DfeApi var6 = FinskyApp.get().getDfeApi();
         Response.Listener var7 = ReviewsFragmentXLarge.this.mListener;
         Response.ErrorListener var8 = ReviewsFragmentXLarge.this.mErrorListener;
         var6.addReview(var2, var3, var4, var5, var7, var8);
         Toast.makeText(ReviewsFragmentXLarge.this.getActivity(), 2131230988, 0).show();
      }
   }
}
