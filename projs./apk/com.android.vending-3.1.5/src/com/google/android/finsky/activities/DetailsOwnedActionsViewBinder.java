package com.google.android.finsky.activities;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RatingBar;
import android.widget.TextView;
import com.google.android.finsky.FinskyInstance;
import com.google.android.finsky.activities.ReviewDialog;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.local.AssetStore;
import com.google.android.finsky.local.LocalAsset;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.remoting.protos.Rev;
import com.google.android.finsky.utils.AssetSupport;
import com.google.android.finsky.utils.CorpusMetadata;
import com.google.android.vending.remoting.protos.VendingProtos;

public class DetailsOwnedActionsViewBinder {

   private static final String TAG_REVIEW_DIALOG = "review_dialog";
   protected Fragment mContainerFragment;
   private Context mContext;
   private Document mDoc;
   private View mLayout;
   private NavigationManager mNavigationManager;
   private Rev.Review mReview;


   public DetailsOwnedActionsViewBinder() {}

   private void configureAutoUpdateSection() {
      View var1 = this.mLayout.findViewById(2131755031);
      if(this.mDoc.getBackend() != 3) {
         var1.setVisibility(8);
      } else {
         String var2 = this.mDoc.getAppDetails().getPackageName();
         View var3 = this.mLayout;
         AssetSupport.bindAutoUpdateSection(var2, var3);
      }
   }

   private void configureRateAndReviewSection() {
      View var1 = this.mLayout.findViewById(2131755146);
      DetailsOwnedActionsViewBinder.1 var2 = new DetailsOwnedActionsViewBinder.1();
      var1.setOnClickListener(var2);
      TextView var3 = (TextView)var1.findViewById(2131755147);
      if(this.mReview == null) {
         this.refreshReviewBanner(-1);
      } else {
         int var10 = this.mReview.getStarRating();
         this.refreshReviewBanner(var10);
      }

      Context var4 = this.mContext;
      int var5 = this.mDoc.getBackend();
      int var6 = CorpusMetadata.getBackendHintColor(var4, var5);
      var3.setTextColor(var6);
      if(this.mDoc.getBackend() == 3) {
         AssetStore var7 = FinskyInstance.get().getAssetStore();
         String var8 = this.mDoc.getAppDetails().getPackageName();
         LocalAsset var9 = var7.getAsset(var8);
         if(var9 == null || var9.isDownloadingOrInstalling() || !var9.isInstalled()) {
            var1.setVisibility(8);
            return;
         }
      }

      var1.setVisibility(0);
   }

   private void launchFullReviewDialog() {
      NavigationManager var1 = this.mNavigationManager;
      Document var2 = this.mDoc;
      var1.goToAllReviews(var2);
   }

   private void launchSmallReviewDialog() {
      byte var1 = 1;
      FragmentManager var2 = this.mContainerFragment.getFragmentManager();
      if(var2.findFragmentByTag("review_dialog") == null) {
         VendingProtos.GetMarketMetadataResponseProto var3 = FinskyInstance.get().getMarketMetadata();
         boolean var4;
         if(var3.hasCommentPostEnabled() && var3.getCommentPostEnabled()) {
            var4 = true;
         } else {
            var4 = false;
         }

         if(var4) {
            var1 = 2;
         }

         String var5 = this.mDoc.getDocId();
         Rev.Review var6 = this.mReview;
         ReviewDialog var7 = ReviewDialog.newInstance(var1, var5, var6);
         Fragment var8 = this.mContainerFragment;
         var7.setTargetFragment(var8, 0);
         var7.show(var2, "review_dialog");
      }
   }

   private void refreshReviewBanner(int var1) {
      View var2 = this.mLayout.findViewById(2131755146);
      TextView var3 = (TextView)var2.findViewById(2131755147);
      int var4;
      if(var1 < 0) {
         var4 = 2131231077;
      } else {
         var4 = 2131231078;
         RatingBar var6 = (RatingBar)var2.findViewById(2131755148);
         float var7 = (float)var1;
         var6.setRating(var7);
      }

      String var5 = this.mContext.getString(var4).toUpperCase();
      var3.setText(var5);
   }

   public void bind(View var1, Document var2, Rev.Review var3) {
      this.mLayout = var1;
      this.mDoc = var2;
      this.mReview = var3;
      this.configureAutoUpdateSection();
      this.configureRateAndReviewSection();
      View var4 = this.mLayout.findViewById(2131755031);
      View var5 = this.mLayout.findViewById(2131755146);
      if(var4.getVisibility() == 8 && var5.getVisibility() == 8) {
         this.mLayout.setVisibility(8);
      } else {
         this.mLayout.setVisibility(0);
      }
   }

   public void init(Context var1, Fragment var2, NavigationManager var3) {
      this.mContext = var1;
      this.mContainerFragment = var2;
      this.mNavigationManager = var3;
   }

   public void onDestroyView() {}

   public void updateRating(int var1) {
      this.refreshReviewBanner(var1);
   }

   class 1 implements OnClickListener {

      1() {}

      public void onClick(View var1) {
         if(DetailsOwnedActionsViewBinder.this.mContext.getResources().getBoolean(2131296256)) {
            DetailsOwnedActionsViewBinder.this.launchFullReviewDialog();
         } else {
            DetailsOwnedActionsViewBinder.this.launchSmallReviewDialog();
         }
      }
   }
}
