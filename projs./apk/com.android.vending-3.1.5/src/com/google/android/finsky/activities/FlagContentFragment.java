package com.google.android.finsky.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import com.android.volley.NetworkError;
import com.android.volley.Response;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.FinskyInstance;
import com.google.android.finsky.activities.BinderFactory;
import com.google.android.finsky.activities.DetailsSummaryViewBinder;
import com.google.android.finsky.activities.FlagContentDialog;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.DfeDetails;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.api.model.OnDataChangedListener;
import com.google.android.finsky.config.G;
import com.google.android.finsky.fragments.PageFragmentHost;
import com.google.android.finsky.fragments.UrlBasedPageFragment;
import com.google.android.finsky.local.AssetState;
import com.google.android.finsky.local.AssetStore;
import com.google.android.finsky.local.LocalAsset;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.utils.BitmapLoader;
import com.google.android.finsky.utils.CorpusMetadata;
import com.google.android.finsky.utils.ErrorStrings;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.PurchaseInitiator;
import com.google.android.vending.remoting.api.VendingApi;
import com.google.android.vending.remoting.protos.VendingProtos;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FlagContentFragment extends UrlBasedPageFragment implements FlagContentDialog.Listener {

   private static final String KEY_DOCUMENT = "doc";
   private static final String KEY_FLAG_MESSAGE = "flag_message";
   private static final String TAG_CONTENT_DIALOG = "flag_content_dialog";
   private ViewGroup mDetailsPanel;
   private DfeDetails mDfeDetails;
   private Document mDoc;
   private String mFlagMessage;
   private RadioGroup mFlagRadioButtons;
   private FlagContentFragment.FlagType mFlagType;
   private DetailsSummaryViewBinder mSummaryViewBinder;


   public FlagContentFragment() {}

   public static FlagContentFragment newInstance(String var0) {
      FlagContentFragment var1 = new FlagContentFragment();
      DfeToc var2 = FinskyApp.get().getToc();
      var1.setArguments(var2, var0);
      return var1;
   }

   private void postFlag() {
      boolean var1 = this.mNavigationManager.goBack();
      VendingApi var2 = FinskyApp.get().getVendingApi();
      String var3 = PurchaseInitiator.generateAssetId(this.mDoc.getAppDetails());
      int var4 = this.mFlagType.flagRpcId();
      String var5 = this.mFlagMessage;
      FlagContentFragment.6 var6 = new FlagContentFragment.6();
      FlagContentFragment.7 var7 = new FlagContentFragment.7();
      var2.flagAsset(var3, var4, var5, var6, var7);
   }

   protected int getLayoutRes() {
      return 2130968651;
   }

   protected boolean isDataReady() {
      return true;
   }

   public void onActivityCreated(Bundle var1) {
      super.onActivityCreated(var1);
      if(var1 != null && var1.containsKey("doc")) {
         Document var2 = (Document)var1.getParcelable("doc");
         this.onDocumentLoaded(var2);
      } else {
         this.switchToLoading();
         DfeApi var4 = this.mDfeApi;
         String var5 = this.mUrl;
         DfeDetails var6 = new DfeDetails(var4, var5);
         this.mDfeDetails = var6;
         DfeDetails var7 = this.mDfeDetails;
         FlagContentFragment.4 var8 = new FlagContentFragment.4();
         var7.addDataChangedListener(var8);
         DfeDetails var9 = this.mDfeDetails;
         FlagContentFragment.5 var10 = new FlagContentFragment.5();
         var9.addErrorListener(var10);
      }

      if(var1 != null) {
         String var3 = var1.getString("flag_message");
         this.mFlagMessage = var3;
      }
   }

   public void onDestroyView() {
      super.onDestroyView();
      if(this.mSummaryViewBinder != null) {
         this.mSummaryViewBinder.onDestroyView();
      }
   }

   public void onDocumentLoaded(Document var1) {
      this.mDoc = var1;
      this.mDetailsPanel.setVisibility(0);
      if(this.mSummaryViewBinder == null) {
         DfeToc var2 = this.getToc();
         int var3 = this.mDoc.getBackend();
         DetailsSummaryViewBinder var4 = BinderFactory.getSummaryViewBinder(var2, var3);
         this.mSummaryViewBinder = var4;
         this.mSummaryViewBinder.hideDynamicFeatures();
         DetailsSummaryViewBinder var5 = this.mSummaryViewBinder;
         Context var6 = this.mContext;
         NavigationManager var7 = this.mNavigationManager;
         BitmapLoader var8 = this.mBitmapLoader;
         byte var10 = 0;
         var5.init(var6, var7, var8, this, (boolean)0, (boolean)var10, (String)null);
      }

      AssetStore var11 = FinskyInstance.get().getAssetStore();
      String var12 = this.mDoc.getAppDetails().getPackageName();
      LocalAsset var13 = var11.getAsset(var12);
      if(var13 != null) {
         boolean var19;
         label30: {
            AssetState var14 = var13.getState();
            AssetState var15 = AssetState.INSTALLED;
            if(var14 != var15) {
               AssetState var16 = AssetState.UNINSTALLED;
               if(var14 != var16) {
                  AssetState var17 = AssetState.UNINSTALLING;
                  if(var14 != var17) {
                     AssetState var18 = AssetState.UNINSTALL_FAILED;
                     if(var14 != var18) {
                        var19 = false;
                        break label30;
                     }
                  }
               }
            }

            var19 = true;
         }

         if(var19) {
            Iterator var20 = FlagContentFragment.FlagType.SHOW_ONLY_IF_OWNED.iterator();

            while(var20.hasNext()) {
               FlagContentFragment.FlagType var21 = (FlagContentFragment.FlagType)var20.next();
               View var22 = this.getView();
               int var23 = var21.flagRadioButtonId();
               var22.findViewById(var23).setVisibility(0);
            }
         }
      }

      this.onDataChanged();
   }

   protected void onInitViewBinders() {
      View var1 = this.getView();
      ViewGroup var2 = (ViewGroup)var1.findViewById(2131755194);
      this.mDetailsPanel = var2;
      RadioGroup var3 = (RadioGroup)var1.findViewById(2131755196);
      this.mFlagRadioButtons = var3;
      if(((Boolean)G.vendingHideContentRating.get()).booleanValue()) {
         this.mFlagRadioButtons.findViewById(2131755201).setVisibility(8);
      }

      Button var4 = (Button)var1.findViewById(2131755204);
      FlagContentFragment.1 var5 = new FlagContentFragment.1();
      var4.setOnClickListener(var5);
      RadioGroup var6 = this.mFlagRadioButtons;
      FlagContentFragment.2 var7 = new FlagContentFragment.2(var4);
      var6.setOnCheckedChangeListener(var7);
      Button var8 = (Button)var1.findViewById(2131755054);
      FlagContentFragment.3 var9 = new FlagContentFragment.3();
      var8.setOnClickListener(var9);
   }

   public void onPositiveClick(String var1) {
      this.mFlagMessage = var1;
      FlagContentFragment.FlagType var2 = FlagContentFragment.FlagType.forRadioId(this.mFlagRadioButtons.getCheckedRadioButtonId());
      this.mFlagType = var2;
      this.postFlag();
   }

   public void onSaveInstanceState(Bundle var1) {
      super.onSaveInstanceState(var1);
      if(this.mDoc != null) {
         Document var2 = this.mDoc;
         var1.putParcelable("doc", var2);
         String var3 = this.mFlagMessage;
         var1.putString("flag_message", var3);
      }
   }

   public void rebindActionBar() {
      PageFragmentHost var1 = this.mPageFragmentHost;
      String var2 = this.mContext.getString(2131231101);
      var1.updateBreadcrumb(var2);
      this.mPageFragmentHost.updateCurrentBackendId(3);
   }

   public void rebindViews() {
      if(this.mDoc != null) {
         this.mDetailsPanel.removeAllViews();
         int var1 = CorpusMetadata.getCorpusDetailsLayoutResource(this.mDoc.getBackend());
         LayoutInflater var2 = LayoutInflater.from(this.mDetailsPanel.getContext());
         ViewGroup var3 = this.mDetailsPanel;
         var2.inflate(var1, var3, (boolean)1);
         DetailsSummaryViewBinder var5 = this.mSummaryViewBinder;
         ViewGroup var6 = this.mDetailsPanel;
         Document var7 = this.mDoc;
         var5.bind(var6, var7);
         this.rebindActionBar();
      }
   }

   protected void requestData() {}

   class 7 implements Response.ErrorListener {

      7() {}

      public void onErrorResponse(Response.ErrorCode var1, String var2, NetworkError var3) {}
   }

   class 6 implements Response.Listener<VendingProtos.ModifyCommentResponseProto> {

      6() {}

      public void onResponse(VendingProtos.ModifyCommentResponseProto var1) {
         Toast.makeText(FlagContentFragment.this.mContext, 2131231112, 1).show();
      }
   }

   public static enum FlagType {

      // $FF: synthetic field
      private static final FlagContentFragment.FlagType[] $VALUES;
      GRAPHIC_VIOLENCE("GRAPHIC_VIOLENCE", 1, 2, 2131755198),
      HARMFUL_TO_DEVICE("HARMFUL_TO_DEVICE", 3, 4, 2131755200, 2131231111),
      HATEFUL_CONTENT("HATEFUL_CONTENT", 2, 3, 2131755199),
      IMPROPER_CONTENT_RATING("IMPROPER_CONTENT_RATING", 4, 6, 2131755201);
      private static final int INVALID_STRING_RES_ID = 255;
      OTHER_OBJECTION;
      private static final Map<Integer, FlagContentFragment.FlagType> RADIO_MAP;
      SEXUAL_CONTENT("SEXUAL_CONTENT", 0, 1, 2131755197);
      public static EnumSet<FlagContentFragment.FlagType> SHOW_ONLY_IF_OWNED;
      private int mFlagPromptStringResId;
      private int mFlagRadioId;
      private int mFlagRpcId;


      static {
         byte var0 = 5;
         OTHER_OBJECTION = new FlagContentFragment.FlagType("OTHER_OBJECTION", 5, var0, 2131755202, 2131231110);
         FlagContentFragment.FlagType[] var1 = new FlagContentFragment.FlagType[6];
         FlagContentFragment.FlagType var2 = SEXUAL_CONTENT;
         var1[0] = var2;
         FlagContentFragment.FlagType var3 = GRAPHIC_VIOLENCE;
         var1[1] = var3;
         FlagContentFragment.FlagType var4 = HATEFUL_CONTENT;
         var1[2] = var4;
         FlagContentFragment.FlagType var5 = HARMFUL_TO_DEVICE;
         var1[3] = var5;
         FlagContentFragment.FlagType var6 = IMPROPER_CONTENT_RATING;
         var1[4] = var6;
         FlagContentFragment.FlagType var7 = OTHER_OBJECTION;
         var1[5] = var7;
         $VALUES = var1;
         SHOW_ONLY_IF_OWNED = EnumSet.of(HARMFUL_TO_DEVICE);
         RADIO_MAP = new HashMap();
         FlagContentFragment.FlagType[] var8 = values();
         int var9 = var8.length;

         for(int var10 = 0; var10 < var9; ++var10) {
            FlagContentFragment.FlagType var11 = var8[var10];
            Map var12 = RADIO_MAP;
            Integer var13 = Integer.valueOf(var11.mFlagRadioId);
            var12.put(var13, var11);
         }

      }

      private FlagType(String var1, int var2, int var3, int var4) {
         this(var1, var2, var3, var4, -1);
      }

      private FlagType(String var1, int var2, int var3, int var4, int var5) {
         this.mFlagRpcId = var3;
         this.mFlagRadioId = var4;
         this.mFlagPromptStringResId = var5;
      }

      public static FlagContentFragment.FlagType forRadioId(int var0) {
         Map var1 = RADIO_MAP;
         Integer var2 = Integer.valueOf(var0);
         return (FlagContentFragment.FlagType)var1.get(var2);
      }

      public int flagPromptStringResId() {
         return this.mFlagPromptStringResId;
      }

      public int flagRadioButtonId() {
         return this.mFlagRadioId;
      }

      public int flagRpcId() {
         return this.mFlagRpcId;
      }

      public boolean requireUserComment() {
         boolean var1;
         if(this.mFlagPromptStringResId != -1) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }
   }

   class 5 implements Response.ErrorListener {

      5() {}

      public void onErrorResponse(Response.ErrorCode var1, String var2, NetworkError var3) {
         String var4 = ErrorStrings.get(FlagContentFragment.this.getActivity(), var1, var2);
         if(var4 != null) {
            FlagContentFragment.this.mPageFragmentHost.showErrorDialog((String)null, var4, (boolean)1);
         } else {
            FlagContentFragment.this.mPageFragmentHost.goBack();
         }
      }
   }

   class 4 implements OnDataChangedListener {

      4() {}

      public void onDataChanged() {
         if(FlagContentFragment.this.mDoc == null) {
            FlagContentFragment var1 = FlagContentFragment.this;
            Document var2 = FlagContentFragment.this.mDfeDetails.getDocument();
            var1.onDocumentLoaded(var2);
         } else {
            Object[] var3 = new Object[0];
            FinskyLog.d("Ignoring soft TTL refresh.", var3);
         }
      }
   }

   class 3 implements OnClickListener {

      3() {}

      public void onClick(View var1) {
         boolean var2 = FlagContentFragment.this.mNavigationManager.goBack();
      }
   }

   class 2 implements OnCheckedChangeListener {

      // $FF: synthetic field
      final Button val$submitButton;


      2(Button var2) {
         this.val$submitButton = var2;
      }

      public void onCheckedChanged(RadioGroup var1, int var2) {
         if(!this.val$submitButton.isEnabled()) {
            this.val$submitButton.setEnabled((boolean)1);
         }
      }
   }

   class 1 implements OnClickListener {

      1() {}

      public void onClick(View var1) {
         FlagContentFragment var2 = FlagContentFragment.this;
         FlagContentFragment.FlagType var3 = FlagContentFragment.FlagType.forRadioId(FlagContentFragment.this.mFlagRadioButtons.getCheckedRadioButtonId());
         var2.mFlagType = var3;
         if(FlagContentFragment.this.mFlagType.requireUserComment()) {
            FragmentManager var5 = FlagContentFragment.this.getFragmentManager();
            if(var5.findFragmentByTag("flag_content_dialog") == null) {
               FlagContentDialog var6 = FlagContentDialog.newInstance(FlagContentFragment.this.mFlagType);
               FlagContentFragment var7 = FlagContentFragment.this;
               var6.setTargetFragment(var7, 0);
               var6.show(var5, "flag_content_dialog");
            }
         } else {
            FlagContentFragment.this.postFlag();
         }
      }
   }
}
