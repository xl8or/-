package com.google.android.finsky.navigationmanager;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.CorporaHomeFragment;
import com.google.android.finsky.activities.DetailsFragment;
import com.google.android.finsky.activities.FlagContentFragment;
import com.google.android.finsky.activities.MainActivity;
import com.google.android.finsky.activities.MyDownloadsFragment;
import com.google.android.finsky.activities.PurchaseDialog;
import com.google.android.finsky.activities.ReviewsActivity;
import com.google.android.finsky.activities.ScreenshotsActivity;
import com.google.android.finsky.activities.SearchFragment;
import com.google.android.finsky.activities.SimpleAlertDialog;
import com.google.android.finsky.activities.TabbedBrowseFragment;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.fragments.PageFragment;
import com.google.android.finsky.navigationmanager.NavigationState;
import com.google.android.finsky.remoting.protos.Toc;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.IntentUtils;
import com.google.android.finsky.utils.MainThreadStack;
import com.google.android.finsky.utils.PurchaseInitiator;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.Stack;

public class NavigationManager {

   private static final int ALERT_ID_APP_NEEDED = 1;
   private static final String ARGS_DETAILS_URL = "dialog_details_url";
   private static final boolean DEBUG = false;
   private static final String KEY_NAVIGATIONMANAGER_SELECTED_CHANNEL = "nm_selected_channel";
   private static final String KEY_NAVIGATIONMANAGER_STATE = "nm_state";
   private static final String TAG_APP_NEEDED_DIALOG = "app_needed_dialog";
   private MainActivity mActivity;
   private final Stack<NavigationState> mBackStack;
   private FragmentManager mFragmentManager;


   public NavigationManager(MainActivity var1) {
      MainThreadStack var2 = new MainThreadStack();
      this.mBackStack = var2;
      this.init(var1);
   }

   private boolean canNavigate() {
      boolean var1;
      if(this.mActivity != null && !this.mActivity.isStateSaved()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private String getConsumptionAppPackageName(int var1) {
      String var2;
      switch(var1) {
      case 1:
         var2 = "com.google.android.apps.books";
         break;
      case 2:
      case 3:
      default:
         var2 = null;
         break;
      case 4:
         var2 = "com.google.android.videos";
      }

      return var2;
   }

   private int getConsumptionAppRequiredString(int var1) {
      int var2;
      switch(var1) {
      case 1:
         var2 = 2131231093;
         break;
      case 2:
      case 3:
      default:
         var2 = -1;
         break;
      case 4:
         var2 = 2131231095;
      }

      return var2;
   }

   public static Intent getConsumptionIntent(Context var0, Document var1, String var2) {
      Intent var3 = null;
      if(var1 != null) {
         int var4 = var1.getBackend();
         if(var1.getBackendDocId() != null) {
            switch(var4) {
            case 1:
            case 3:
            case 4:
               var3 = IntentUtils.buildConsumptionAppViewItemIntent(var0.getPackageManager(), var1, var2);
            case 2:
               break;
            default:
               String var5 = "Cannot open an item from the corpus " + var4;
               throw new IllegalStateException(var5);
            }
         }
      }

      return var3;
   }

   private void goToAggregatedHome() {
      DfeToc var1 = this.getActivePage().getToc();
      this.goToAggregatedHome(var1);
   }

   private void goToCorpusHome() {
      if(this.mActivity != null) {
         if(!this.mActivity.isStateSaved()) {
            while(!this.mBackStack.isEmpty()) {
               Object var1 = this.mBackStack.peek();
               NavigationState var2 = NavigationState.CORPUS_HOME;
               if(var1 == var2) {
                  break;
               }

               Object var3 = this.mBackStack.pop();
               this.mFragmentManager.popBackStack();
            }

            if(this.mBackStack.isEmpty()) {
               this.goToAggregatedHome();
            }
         }
      }
   }

   private boolean isConsumptionAppNeeded(Document var1, String var2) {
      boolean var3 = true;
      int var4 = var1.getBackend();
      String var5 = this.getConsumptionAppPackageName(var4);
      if(var5 == null) {
         var3 = false;
      } else if(FinskyApp.get().getPackageInfoCache().isPackageInstalled(var5)) {
         Intent var6 = getConsumptionIntent(this.mActivity, var1, var2);
         if(var6 != null && FinskyApp.get().getPackageManager().resolveActivity(var6, 0) != null) {
            var3 = false;
         }
      }

      return var3;
   }

   private void popAllDetailsOff() {
      if(this.mActivity != null) {
         if(!this.mActivity.isStateSaved()) {
            if(!this.mBackStack.isEmpty()) {
               Object var1 = this.mBackStack.peek();
               NavigationState var2 = NavigationState.DETAILS;
               if(var1 == var2) {
                  while(!this.mBackStack.isEmpty()) {
                     Object var3 = this.mBackStack.peek();
                     NavigationState var4 = NavigationState.DETAILS;
                     if(var3 != var4) {
                        break;
                     }

                     Object var5 = this.mBackStack.pop();
                     this.mFragmentManager.popBackStack();
                  }

                  if(this.mBackStack.isEmpty()) {
                     this.goToAggregatedHome();
                  }
               }
            }
         }
      }
   }

   private void showPage(NavigationState var1, Fragment var2) {
      this.showPage(var1, var2, (boolean)0);
   }

   private void showPage(NavigationState var1, Fragment var2, boolean var3) {
      FragmentTransaction var4 = this.mFragmentManager.beginTransaction();
      var4.replace(2131755130, var2);
      if(var3) {
         if(!this.mBackStack.isEmpty()) {
            Object var6 = this.mBackStack.pop();
         }

         this.mFragmentManager.popBackStack();
      }

      FragmentTransaction var7 = var4.addToBackStack("unused name");
      FragmentTransaction var8 = var4.setTransition(4097);
      this.mBackStack.push(var1);
      int var10 = var4.commit();
   }

   public void addOnBackStackChangedListener(FragmentManager.OnBackStackChangedListener var1) {
      this.mFragmentManager.addOnBackStackChangedListener(var1);
   }

   public void buy(Document var1, int var2, String var3, String var4) {
      if(var1.skipPurchaseDialog(var2)) {
         PurchaseInitiator.makeFreePurchase(this, var1, var2, var4);
      } else {
         switch(var1.getBackend()) {
         case 0:
            throw new IllegalArgumentException("Unsupported backend for buy.");
         case 1:
         case 2:
         case 3:
         case 4:
            String var5 = var1.getDetailsUrl();
            String var6 = var1.getCookie();
            this.goToPurchase(var5, var2, var3, var6, var4);
            return;
         default:
         }
      }
   }

   public boolean canGoUp() {
      boolean var1 = true;
      boolean var2 = false;
      if(!this.mBackStack.isEmpty()) {
         NavigationState var3 = (NavigationState)this.mBackStack.peek();
         NavigationState var4 = NavigationState.AGGREGATED_HOME;
         if(var3 != var4) {
            NavigationState var5 = NavigationState.CORPUS_HOME;
            if(var3 != var5) {
               var2 = true;
            } else {
               if(this.getActivePage().getToc().getCorpusList().size() <= 1) {
                  var1 = false;
               }

               var2 = var1;
            }
         }
      }

      return var2;
   }

   public boolean canSearch() {
      int[] var1 = NavigationManager.6.$SwitchMap$com$google$android$finsky$navigationmanager$NavigationState;
      int var2 = this.getCurrentPageType().ordinal();
      boolean var3;
      switch(var1[var2]) {
      case 1:
      case 2:
         var3 = false;
         break;
      default:
         var3 = true;
      }

      return var3;
   }

   public void clear() {
      while(!this.mBackStack.isEmpty()) {
         Object var1 = this.mBackStack.pop();
         this.mFragmentManager.popBackStack();
      }

   }

   public OnClickListener createSearchMoreClickListener(String var1, int var2, String var3) {
      return new NavigationManager.2(var1, var2, var3);
   }

   public boolean deserialize(Bundle var1) {
      ArrayList var2 = var1.getParcelableArrayList("nm_state");
      int var3 = var1.getInt("nm_selected_channel");
      boolean var4;
      if(var2 != null && var2.size() != 0 && this.getActivePage() != null) {
         Iterator var5 = var2.iterator();

         while(var5.hasNext()) {
            NavigationState var6 = (NavigationState)var5.next();
            this.mBackStack.push(var6);
         }

         this.getActivePage().rebindActionBar();
         var4 = true;
      } else {
         var4 = false;
      }

      return var4;
   }

   public boolean flush() {
      return this.mFragmentManager.executePendingTransactions();
   }

   public PageFragment getActivePage() {
      return (PageFragment)this.mFragmentManager.findFragmentById(2131755130);
   }

   public OnClickListener getBuyClickListener(String var1, String var2, String var3, int var4, String var5) {
      return new NavigationManager.3(var1, var4, var2, var3, var5);
   }

   public OnClickListener getBuyImmediateClickListener(Document var1, int var2) {
      String var3 = var1.getDetailsUrl();
      return this.getBuyImmediateClickListener(var1, var2, var3, (String)null);
   }

   public OnClickListener getBuyImmediateClickListener(Document var1, int var2, String var3, String var4) {
      return new NavigationManager.4(var1, var2, var3, var4);
   }

   public OnClickListener getBuyImmediateClickListener(Document var1, String var2, int var3) {
      return this.getBuyImmediateClickListener(var1, var3, var2, (String)null);
   }

   public Document getCurrentDocument() {
      PageFragment var1 = this.getActivePage();
      Document var2;
      if(var1 != null && var1 instanceof DetailsFragment) {
         var2 = ((DetailsFragment)var1).getDocument();
      } else {
         var2 = null;
      }

      return var2;
   }

   public NavigationState getCurrentPageType() {
      NavigationState var1;
      if(this.mBackStack.isEmpty()) {
         var1 = NavigationState.INITIAL;
      } else {
         var1 = (NavigationState)this.mBackStack.peek();
      }

      return var1;
   }

   public OnClickListener getDetailsClickListener(Document var1, String var2) {
      return new NavigationManager.1(var1, var2);
   }

   public OnClickListener getOpenClickListener(Document var1, String var2) {
      return new NavigationManager.5(var1, var2);
   }

   public boolean goBack() {
      boolean var1 = false;
      if(this.mActivity != null && !this.mActivity.isStateSaved()) {
         try {
            NavigationState var2 = (NavigationState)this.mBackStack.pop();
            this.mFragmentManager.popBackStack();
            NavigationState var3 = (NavigationState)this.mBackStack.peek();
         } catch (EmptyStackException var5) {
            return var1;
         }

         var1 = true;
      }

      return var1;
   }

   public void goBrowse(String var1, String var2, int var3, String var4) {
      if(this.canNavigate()) {
         NavigationState var5 = NavigationState.BROWSE;
         TabbedBrowseFragment var6 = TabbedBrowseFragment.newInstance(var1, var2, var3, var4);
         this.showPage(var5, var6);
      }
   }

   public void goToAggregatedHome(DfeToc var1) {
      if(this.canNavigate()) {
         this.clear();
         if(var1.getCorpusList().size() == 1) {
            Toc.CorpusMetadata var2 = (Toc.CorpusMetadata)var1.getCorpusList().get(0);
            NavigationState var3 = NavigationState.CORPUS_HOME;
            String var4 = var2.getLandingUrl();
            String var5 = var2.getName();
            int var6 = var2.getBackend();
            TabbedBrowseFragment var7 = TabbedBrowseFragment.newInstance(var4, var5, var6, (String)null);
            this.showPage(var3, var7, (boolean)1);
         } else {
            String var8;
            if(!TextUtils.isEmpty(var1.getHomeUrl())) {
               var8 = var1.getHomeUrl();
            } else {
               var8 = var1.getCorpus(3).getLandingUrl();
            }

            NavigationState var9 = NavigationState.AGGREGATED_HOME;
            CorporaHomeFragment var10 = CorporaHomeFragment.newInstance(var1, var8);
            this.showPage(var9, var10, (boolean)1);
         }
      }
   }

   public void goToAllReviews(Document var1) {
      if(this.mActivity != null) {
         if(!this.mActivity.isStateSaved()) {
            ReviewsActivity.show(this.mActivity, var1);
         }
      }
   }

   public void goToCorpusHome(String var1, String var2, int var3) {
      if(this.canNavigate()) {
         NavigationState var4 = NavigationState.CORPUS_HOME;
         TabbedBrowseFragment var5 = TabbedBrowseFragment.newInstance(var1, var2, var3, (String)null);
         this.showPage(var4, var5);
      }
   }

   public void goToDetails(String var1, String var2) {
      this.goToDetails(var1, (String)null, var2);
   }

   public void goToDetails(String var1, String var2, String var3) {
      if(this.canNavigate()) {
         NavigationState var4 = NavigationState.DETAILS;
         DetailsFragment var5 = DetailsFragment.newInstance(var1, var2, var3);
         this.showPage(var4, var5);
      }
   }

   public void goToFlagContent(String var1) {
      if(this.canNavigate()) {
         NavigationState var2 = NavigationState.FLAG_CONTENT;
         FlagContentFragment var3 = FlagContentFragment.newInstance(var1);
         this.showPage(var2, var3);
      }
   }

   public void goToMyDownloads() {
      if(this.canNavigate()) {
         NavigationState var1 = NavigationState.MY_DOWNLOADS;
         MyDownloadsFragment var2 = MyDownloadsFragment.newInstance();
         this.showPage(var1, var2);
      }
   }

   public void goToPurchase(String var1, int var2, String var3, String var4, String var5) {
      this.goToPurchase(var1, var2, (boolean)0, var3, var4, var5);
   }

   public void goToPurchase(String var1, int var2, boolean var3, String var4, String var5, String var6) {
      if(this.canNavigate()) {
         MainActivity var7 = this.mActivity;
         PurchaseDialog.show(var7, var1, var2, var3, var4, var5, var6);
      }
   }

   public void goToScreenshots(Document var1, int var2) {
      if(this.mActivity != null) {
         if(!this.mActivity.isStateSaved()) {
            Intent var3 = new Intent("android.intent.action.VIEW");
            MainActivity var4 = this.mActivity;
            var3.setClass(var4, ScreenshotsActivity.class);
            var3.putExtra("finsky.ScreenshotsFragment.document", var1);
            var3.putExtra("finsky.ScreenshotsFragment.selectedIndex", var2);
            this.mActivity.startActivity(var3);
         }
      }
   }

   public void goToSearch(String var1, int var2, String var3) {
      if(this.canNavigate()) {
         NavigationState var4 = NavigationState.SEARCH;
         SearchFragment var5 = SearchFragment.newInstance(var1, var2, var3);
         this.showPage(var4, var5, (boolean)0);
      }
   }

   public void goUp() {
      if(this.mActivity != null) {
         if(!this.mActivity.isStateSaved()) {
            if(!this.mBackStack.isEmpty()) {
               NavigationState var1 = (NavigationState)this.mBackStack.peek();
               NavigationState var2 = NavigationState.DETAILS;
               if(var1 == var2) {
                  this.goToCorpusHome();
               } else {
                  NavigationState var3 = NavigationState.BROWSE;
                  if(var1 == var3) {
                     this.goToCorpusHome();
                  } else {
                     NavigationState var4 = NavigationState.SEARCH;
                     if(var1 == var4) {
                        this.goToCorpusHome();
                     } else {
                        NavigationState var5 = NavigationState.CORPUS_HOME;
                        if(var1 == var5) {
                           this.goToAggregatedHome();
                        } else {
                           NavigationState var6 = NavigationState.ALL_REVIEWS;
                           if(var1 == var6) {
                              boolean var7 = this.goBack();
                           } else {
                              NavigationState var8 = NavigationState.MY_DOWNLOADS;
                              if(var1 == var8) {
                                 this.goToCorpusHome();
                              } else {
                                 NavigationState var9 = NavigationState.FLAG_CONTENT;
                                 if(var1 == var9) {
                                    boolean var10 = this.goBack();
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public void init(MainActivity var1) {
      this.mActivity = var1;
      FragmentManager var2 = this.mActivity.getSupportFragmentManager();
      this.mFragmentManager = var2;
   }

   public boolean isEmpty() {
      return this.mBackStack.isEmpty();
   }

   public void onPositiveClick(int var1, Bundle var2) {
      if(var1 == 1) {
         if(var2 != null) {
            String var3 = var2.getString("dialog_details_url");
            if(var3 != null) {
               if(this.canNavigate()) {
                  this.goToDetails(var3, (String)null);
               }
            }
         }
      }
   }

   public void open(Document var1, String var2) {
      this.openItem(var1, var2);
   }

   public void openItem(Document var1, String var2) {
      if(this.isConsumptionAppNeeded(var1, var2)) {
         int var3 = var1.getBackend();
         this.showAppNeededDialog(var3);
      } else {
         Intent var4 = getConsumptionIntent(this.mActivity, var1, var2);
         ResolveInfo var5 = FinskyApp.get().getPackageManager().resolveActivity(var4, 0);
         if(var4 != null && var5 != null) {
            this.mActivity.startActivity(var4);
         } else {
            MainActivity var6 = this.mActivity;
            String var7 = this.mActivity.getString(2131230966);
            Toast.makeText(var6, var7, 0).show();
         }
      }
   }

   public void refreshPage() {
      if(!this.mBackStack.isEmpty()) {
         PageFragment var1 = this.getActivePage();
         if(var1 != null) {
            var1.refresh();
            var1.onDataChanged();
         } else {
            Object[] var2 = new Object[0];
            FinskyLog.e("Called refresh but there was no active page", var2);
         }
      }
   }

   public void removeOnBackStackChangedListener(FragmentManager.OnBackStackChangedListener var1) {
      this.mFragmentManager.removeOnBackStackChangedListener(var1);
   }

   public void searchFromSuggestion(String var1, int var2, String var3) {
      if(this.canNavigate()) {
         NavigationState var4 = NavigationState.SEARCH;
         SearchFragment var5 = SearchFragment.newInstance(var1, var2, var3);
         this.showPage(var4, var5, (boolean)1);
      }
   }

   public void serialize(Bundle var1) {
      if(this.mBackStack != null) {
         if(!this.mBackStack.isEmpty()) {
            Stack var2 = this.mBackStack;
            ArrayList var3 = new ArrayList(var2);
            var1.putParcelableArrayList("nm_state", var3);
         }
      }
   }

   public void showAppNeededDialog(int var1) {
      String var2 = this.getConsumptionAppPackageName(var1);
      if(var2 == null) {
         MainActivity var3 = this.mActivity;
         String var4 = this.mActivity.getString(2131230966);
         Toast.makeText(var3, var4, 0).show();
      } else if(this.mFragmentManager.findFragmentByTag("app_needed_dialog") == null) {
         int var5 = this.getConsumptionAppRequiredString(var1);
         String var6 = DfeApi.createDetailsUrlFromId(3, var2);
         Bundle var7 = new Bundle();
         var7.putString("dialog_details_url", var6);
         SimpleAlertDialog var8 = SimpleAlertDialog.newInstance(-1, var5, 2131231030, 2131230813);
         var8.setCallback((Fragment)null, 1, var7);
         FragmentManager var10 = this.mFragmentManager;
         var8.show(var10, "app_needed_dialog");
      }
   }

   public void terminate() {
      this.mActivity = null;
   }

   class 1 implements OnClickListener {

      // $FF: synthetic field
      final Document val$doc;
      // $FF: synthetic field
      final String val$referrerUrl;


      1(Document var2, String var3) {
         this.val$doc = var2;
         this.val$referrerUrl = var3;
      }

      public void onClick(View var1) {
         NavigationManager var2 = NavigationManager.this;
         String var3 = this.val$doc.getDetailsUrl();
         String var4 = this.val$doc.getCookie();
         String var5 = this.val$referrerUrl;
         var2.goToDetails(var3, var4, var5);
      }
   }

   class 5 implements OnClickListener {

      // $FF: synthetic field
      final String val$accountName;
      // $FF: synthetic field
      final Document val$doc;


      5(Document var2, String var3) {
         this.val$doc = var2;
         this.val$accountName = var3;
      }

      public void onClick(View var1) {
         NavigationManager var2 = NavigationManager.this;
         Document var3 = this.val$doc;
         String var4 = this.val$accountName;
         var2.openItem(var3, var4);
      }
   }

   class 4 implements OnClickListener {

      // $FF: synthetic field
      final Document val$doc;
      // $FF: synthetic field
      final String val$externalReferrer;
      // $FF: synthetic field
      final int val$offerType;
      // $FF: synthetic field
      final String val$referrerUrl;


      4(Document var2, int var3, String var4, String var5) {
         this.val$doc = var2;
         this.val$offerType = var3;
         this.val$referrerUrl = var4;
         this.val$externalReferrer = var5;
      }

      public void onClick(View var1) {
         NavigationManager var2 = NavigationManager.this;
         Document var3 = this.val$doc;
         int var4 = this.val$offerType;
         String var5 = this.val$referrerUrl;
         String var6 = this.val$externalReferrer;
         var2.buy(var3, var4, var5, var6);
      }
   }

   class 3 implements OnClickListener {

      // $FF: synthetic field
      final String val$cookie;
      // $FF: synthetic field
      final String val$externalReferrer;
      // $FF: synthetic field
      final int val$offerType;
      // $FF: synthetic field
      final String val$referrerUrl;
      // $FF: synthetic field
      final String val$url;


      3(String var2, int var3, String var4, String var5, String var6) {
         this.val$url = var2;
         this.val$offerType = var3;
         this.val$referrerUrl = var4;
         this.val$cookie = var5;
         this.val$externalReferrer = var6;
      }

      public void onClick(View var1) {
         NavigationManager var2 = NavigationManager.this;
         String var3 = this.val$url;
         int var4 = this.val$offerType;
         String var5 = this.val$referrerUrl;
         String var6 = this.val$cookie;
         String var7 = this.val$externalReferrer;
         var2.goToPurchase(var3, var4, var5, var6, var7);
      }
   }

   class 2 implements OnClickListener {

      // $FF: synthetic field
      final int val$backendId;
      // $FF: synthetic field
      final String val$query;
      // $FF: synthetic field
      final String val$referrerUrl;


      2(String var2, int var3, String var4) {
         this.val$query = var2;
         this.val$backendId = var3;
         this.val$referrerUrl = var4;
      }

      public void onClick(View var1) {
         NavigationManager var2 = NavigationManager.this;
         String var3 = this.val$query;
         int var4 = this.val$backendId;
         String var5 = this.val$referrerUrl;
         var2.goToSearch(var3, var4, var5);
      }
   }

   // $FF: synthetic class
   static class 6 {

      // $FF: synthetic field
      static final int[] $SwitchMap$com$google$android$finsky$navigationmanager$NavigationState = new int[NavigationState.values().length];


      static {
         try {
            int[] var0 = $SwitchMap$com$google$android$finsky$navigationmanager$NavigationState;
            int var1 = NavigationState.ALL_REVIEWS.ordinal();
            var0[var1] = 1;
         } catch (NoSuchFieldError var7) {
            ;
         }

         try {
            int[] var2 = $SwitchMap$com$google$android$finsky$navigationmanager$NavigationState;
            int var3 = NavigationState.FLAG_CONTENT.ordinal();
            var2[var3] = 2;
         } catch (NoSuchFieldError var6) {
            ;
         }
      }
   }
}
