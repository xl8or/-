package com.facebook.katana.activity.faceweb;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView.OnEditorActionListener;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.TabProgressListener;
import com.facebook.katana.TabProgressSource;
import com.facebook.katana.activity.BaseFacebookActivity;
import com.facebook.katana.activity.faceweb.AppLogHandler;
import com.facebook.katana.activity.faceweb.SetNavBarTitleHandler;
import com.facebook.katana.activity.media.MediaUploader;
import com.facebook.katana.activity.messages.MessageComposeActivity;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.binding.ProfileImagesCache;
import com.facebook.katana.dialog.Dialogs;
import com.facebook.katana.features.Gatekeeper;
import com.facebook.katana.features.composer.ComposerUserSettings;
import com.facebook.katana.features.places.PlacesNearby;
import com.facebook.katana.model.FacebookPhoto;
import com.facebook.katana.model.FacebookPlace;
import com.facebook.katana.model.FacebookPost;
import com.facebook.katana.model.NewsFeedToggleOption;
import com.facebook.katana.model.PrivacySetting;
import com.facebook.katana.service.method.ApiLogging;
import com.facebook.katana.service.method.AudienceSettings;
import com.facebook.katana.service.method.FBJsonFactory;
import com.facebook.katana.service.method.PerfLogging;
import com.facebook.katana.ui.TaggingAutoCompleteTextView;
import com.facebook.katana.util.FBLocationManager;
import com.facebook.katana.util.IntentUtils;
import com.facebook.katana.util.Log;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.Toaster;
import com.facebook.katana.util.Utils;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import com.facebook.katana.webview.FacebookRpcCall;
import com.facebook.katana.webview.FacebookWebView;
import com.facebook.katana.webview.FacewebWebView;
import com.facebook.katana.webview.RefreshableFacewebWebViewContainer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kankan.wheel.widget.OnWheelClickedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FacewebChromeActivity extends BaseFacebookActivity implements TabProgressSource, FBLocationManager.FBLocationListener {

   protected static final int ACTION_MENU_BASE_ID = 1000;
   private static final long DESTROY_WEBVIEW_DELAY_TIME_MS = 30000L;
   public static final String EXTRA_MOBILE_PAGE = "mobile_page";
   public static final String EXTRA_RAW_PARAMETERS = "raw_parameters";
   private static final String PROFILE_ID = "PROFILE_ID";
   protected static final int PROGRESS_COMMENTING_DIALOG = 3;
   protected static final int PROGRESS_PUBLISHING_DIALOG = 2;
   protected static final int PROGRESS_REPLYING_DIALOG = 4;
   protected static final int REFRESH_ID = 100;
   protected static final String SAVE_ACTIVE_STATE = "save_active_state";
   protected static final int STRUCTURED_COMPOSER = 10;
   protected JSONObject[] mActionMenuItems = null;
   private AppSession mAppSession;
   private List<AppSessionListener> mAppSessionListeners;
   private String mCheckinReqId;
   private boolean mFirstResume = 1;
   protected Handler mHandler;
   private String mHref;
   private boolean mIsInElder = 0;
   private long mLastPausedTime;
   private boolean mLoading;
   private FBLocationManager.FBLocationListener mLocationListener;
   private MediaUploader mMediaUploader;
   protected String mMsgComposerCallback;
   protected String mNavButtonCallback;
   protected FacewebChromeActivity.ShowPickerView mPickerView;
   protected String mPickerViewCallback;
   protected long mProfileId = 65535L;
   private TabProgressListener mProgressListener;
   protected View mPublisher = null;
   protected String mPublisherCallback;
   private boolean mRefreshable;
   private boolean mShowingProgress;
   private boolean mWithinTab;
   protected FacewebWebView mWv;


   public FacewebChromeActivity() {}

   // $FF: synthetic method
   static String access$500(FacewebChromeActivity var0) {
      return var0.getTag();
   }

   protected static String getStringParam(FacebookRpcCall var0, String var1) {
      String var2 = var0.getParameterByName(var1);
      String var3;
      if(var2 instanceof String) {
         var3 = (String)var2;
      } else {
         var3 = null;
      }

      return var3;
   }

   protected MediaUploader getMediaUploader() {
      if(this.mMediaUploader == null) {
         if(this.mProfileId != 65535L) {
            long var1 = this.mProfileId;
            long var3 = this.mAppSession.getSessionInfo().userId;
            if(var1 != var3) {
               long var5 = this.mProfileId;
               MediaUploader var7 = new MediaUploader(this, var5);
               this.mMediaUploader = var7;
               return this.mMediaUploader;
            }
         }

         MediaUploader var8 = new MediaUploader(this, (String)null);
         this.mMediaUploader = var8;
      }

      return this.mMediaUploader;
   }

   protected void launchComposer(Uri var1, Bundle var2, Integer var3) {
      long var4 = this.mProfileId;
      this.launchComposer(var1, var2, var3, var4);
   }

   protected void onActivityResult(int var1, int var2, Intent var3) {
      super.onActivityResult(var1, var2, var3);
      if(var2 != 0) {
         switch(var1) {
         case 10:
            String var5 = "extra_status_text";
            String var6 = var3.getStringExtra(var5);
            String var8 = "extra_tagged_ids";
            long[] var9 = var3.getLongArrayExtra(var8);
            ArrayList var10 = new ArrayList();
            long[] var11 = var9;
            int var12 = var9.length;

            for(int var13 = 0; var13 < var12; ++var13) {
               Long var16 = Long.valueOf(var11[var13]);
               boolean var19 = var10.add(var16);
            }

            String var35 = "extra_tagged_place_id";
            String var36 = var3.getStringExtra(var35);
            String var38 = "extra_xed_location";
            String var39 = var3.getStringExtra(var38);
            String var41 = "extra_place";
            FacebookPlace var42 = (FacebookPlace)var3.getParcelableExtra(var41);
            String var44 = "extra_is_checkin";
            byte var45 = 0;
            boolean var46 = var3.getBooleanExtra(var44, (boolean)var45);
            String var48 = "extra_tagged_location";
            Location var49 = (Location)var3.getParcelableExtra(var48);
            String var51 = "extra_status_privacy";
            String var52 = var3.getStringExtra(var51);
            String var54 = "extra_status_target_id";
            long var55 = 65535L;
            Long var57 = Long.valueOf(var3.getLongExtra(var54, var55));
            String var58;
            if(var42 != null) {
               var58 = String.valueOf(var42.mPageId);
            } else {
               var58 = var36;
            }

            String var60 = "extra_photo_upload_started";
            byte var61 = 0;
            if(var3.getBooleanExtra(var60, (boolean)var61)) {
               int var63 = 2131362328;
               Toaster.toast(this, var63);
               return;
            } else if(var46) {
               try {
                  AppSession var64 = this.mAppSession;
                  Set var65 = IntentUtils.primitiveToSet(var9);
                  Long var66 = Long.valueOf(this.mProfileId);
                  String var68 = var64.checkin(this, var42, var49, var6, var65, var66, var52);
                  this.mCheckinReqId = var68;
                  return;
               } catch (JSONException var119) {
                  int var71 = 2131362368;
                  Toaster.toast(this, var71);
                  return;
               }
            } else if(var6 == null) {
               return;
            } else {
               JSONObject var72 = new JSONObject();

               try {
                  String var74 = "action";
                  String var75 = "didPostStatus";
                  var72.put(var74, var75);
                  String var78 = "text";
                  var72.put(var78, var6);
                  if(var10 != null) {
                     Set var81 = IntentUtils.primitiveToSet(var9);
                     JSONArray var82 = new JSONArray(var81);
                     String var84 = "users_with";
                     var72.put(var84, var82);
                  }

                  if(var58 != null) {
                     String var88 = "at";
                     var72.put(var88, var58);
                  }

                  if(var52 != null) {
                     JSONObject var91 = new JSONObject(var52);
                     String var93 = "privacy";
                     var72.put(var93, var91);
                  }

                  if(var39 != null) {
                     String var97 = "disable_location";
                     var72.put(var97, var39);
                  }

                  if(var57.longValue() != 65535L) {
                     String var101 = "target";
                     var72.put(var101, var57);
                  }
               } catch (JSONException var120) {
                  String var116 = this.getTag();
                  String var117 = "inconceivable exception";
                  Log.e(var116, var117, var120);
               }

               ArrayList var104 = new ArrayList();
               boolean var107 = var104.add(var72);
               byte var109 = 2;
               this.showDialog(var109);
               FacewebWebView var110 = this.mWv;
               String var111 = this.mPublisherCallback;
               FacewebChromeActivity.ShareHandler var112 = new FacewebChromeActivity.ShareHandler();
               var110.executeJsFunction(var111, var104, var112);
               return;
            }
         case 133701:
         case 133702:
            if(this.mIsInElder) {
               Uri var20;
               if(var3 != null) {
                  var20 = var3.getData();
               } else {
                  var20 = null;
               }

               Bundle var21 = new Bundle();
               String var23 = "extra_photo_request_code";
               var21.putInt(var23, var1);
               Integer var25 = Integer.valueOf(10);
               this.launchComposer(var20, var21, var25);
               return;
            }
         case 133703:
         case 133704:
            if(var3 == null) {
               var3 = new Intent();
            }

            MediaUploader var30 = this.getMediaUploader();
            var30.onActivityResult(var1, var2, var3);
            return;
         default:
         }
      }
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      if(var1 != null) {
         long var2 = var1.getLong("PROFILE_ID", 65535L);
         this.mProfileId = var2;
      }

      String var4 = this.getIntent().getStringExtra("mobile_page");
      this.mHref = var4;
      if(this.mHref == null) {
         this.mHref = "/home.php";
      }

      PerfLogging.Step var5 = PerfLogging.Step.ONCREATE;
      String var6 = this.getTag();
      long var7 = this.getActivityId();
      String var9 = this.mHref;
      PerfLogging.logStep(this, var5, var6, var7, var9);
      if(var1 != null && var1.getBoolean("save_active_state")) {
         ApiLogging.incrementKillCount();
      }

      AppSession var10 = AppSession.getActiveSession(this, (boolean)1);
      this.mAppSession = var10;
      if(this.mAppSession == null) {
         Intent var11 = this.getIntent();
         LoginActivity.toLogin(this, var11);
      } else {
         Handler var12 = new Handler();
         this.mHandler = var12;
         this.setContentView(2130903071);
         this.hideSearchButton();
         byte var13;
         if(this.getParent() != null) {
            var13 = 1;
         } else {
            var13 = 0;
         }

         this.mWithinTab = (boolean)var13;
         FacewebWebView var14 = ((RefreshableFacewebWebViewContainer)this.findViewById(2131624042)).getWebView();
         this.mWv = var14;
         FacewebWebView var15 = this.mWv;
         String var16 = this.mHref;
         var15.loadMobilePage(var16);
         if(!this.mWithinTab) {
            FacewebWebView var17 = this.mWv;
            Handler var18 = this.mHandler;
            SetNavBarTitleHandler var19 = new SetNavBarTitleHandler(this, var18);
            var17.registerNativeCallHandler("setNavBarTitle", var19);
            FacewebWebView var20 = this.mWv;
            Handler var21 = this.mHandler;
            FacewebChromeActivity.SetToolbarSegmentsHandler var22 = new FacewebChromeActivity.SetToolbarSegmentsHandler(var21);
            var20.registerNativeCallHandler("setToolbarSegments", var22);
         } else {
            this.findViewById(2131623958).setVisibility(8);
         }

         FacewebWebView var23 = this.mWv;
         Handler var24 = this.mHandler;
         FacewebChromeActivity.ShowCommentPublisherHandler var25 = new FacewebChromeActivity.ShowCommentPublisherHandler(var24);
         var23.registerNativeCallHandler("showCommentPublisher", var25);
         FacewebWebView var26 = this.mWv;
         Handler var27 = this.mHandler;
         FacewebChromeActivity.ShowPublisherHandler var28 = new FacewebChromeActivity.ShowPublisherHandler(var27);
         var26.registerNativeCallHandler("showPublisher", var28);
         FacewebWebView var29 = this.mWv;
         Handler var30 = this.mHandler;
         FacewebChromeActivity.1 var31 = new FacewebChromeActivity.1(var30);
         var29.registerNativeCallHandler("removePublisher", var31);
         FacewebWebView var32 = this.mWv;
         Handler var33 = this.mHandler;
         FacewebChromeActivity.ShowMessageComposerHandler var34 = new FacewebChromeActivity.ShowMessageComposerHandler(var33);
         var32.registerNativeCallHandler("showMsgComposer", var34);
         FacewebWebView var35 = this.mWv;
         Handler var36 = this.mHandler;
         FacewebChromeActivity.ShowReplyPublisherHandler var37 = new FacewebChromeActivity.ShowReplyPublisherHandler(var36);
         var35.registerNativeCallHandler("showMsgReplyPublisher", var37);
         Handler var38 = this.mHandler;
         FacewebChromeActivity.UpdateNativeLoadingIndicator var39 = new FacewebChromeActivity.UpdateNativeLoadingIndicator(var38);
         this.mWv.registerNativeCallHandler("pageLoading", var39);
         this.mWv.registerNativeCallHandler("pageLoaded", var39);
         FacewebWebView var40 = this.mWv;
         String var41 = this.getTag();
         AppLogHandler var42 = new AppLogHandler(var41);
         var40.registerNativeCallHandler("appLog", var42);
         FacewebWebView var43 = this.mWv;
         Handler var44 = this.mHandler;
         FacewebChromeActivity.SetNavBarButton var45 = new FacewebChromeActivity.SetNavBarButton(var44);
         var43.registerNativeCallHandler("setNavBarButton", var45);
         FacewebWebView var46 = this.mWv;
         Handler var47 = this.mHandler;
         FacewebChromeActivity.SetActionMenuHandler var48 = new FacewebChromeActivity.SetActionMenuHandler(var47);
         var46.registerNativeCallHandler("setActionMenu", var48);
         Handler var49 = this.mHandler;
         FacewebChromeActivity.ShowPickerView var50 = new FacewebChromeActivity.ShowPickerView(var49);
         this.mPickerView = var50;
         FacewebWebView var51 = this.mWv;
         FacewebChromeActivity.ShowPickerView var52 = this.mPickerView;
         var51.registerNativeCallHandler("showPickerView", var52);
         this.mRefreshable = (boolean)0;
         FacewebWebView var53 = this.mWv;
         FacewebChromeActivity.2 var54 = new FacewebChromeActivity.2();
         var53.registerNativeCallHandler("enablePullToRefresh", var54);
         FacewebWebView var55 = this.mWv;
         Handler var56 = this.mHandler;
         FacewebChromeActivity.3 var57 = new FacewebChromeActivity.3(var56);
         var55.registerNativeCallHandler("close", var57);
         this.mWv.setScrollBarStyle(33554432);
         FacewebWebView var58 = this.mWv;
         Handler var59 = this.mHandler;
         FacewebChromeActivity.SetNavBarHiddenHandler var60 = new FacewebChromeActivity.SetNavBarHiddenHandler(var59);
         var58.registerNativeCallHandler("setNavBarHidden", var60);
         FacewebWebView var61 = this.mWv;
         Handler var62 = this.mHandler;
         FacewebChromeActivity.DismissModalDialog var63 = new FacewebChromeActivity.DismissModalDialog(var62);
         var61.registerNativeCallHandler("dismissModalDialog", var63);
         FacewebWebView var64 = this.mWv;
         FacewebChromeActivity.4 var65 = new FacewebChromeActivity.4();
         var64.registerNativeCallHandler("perf.cachedResponseLoaded", var65);
         FacewebWebView var66 = this.mWv;
         Handler var67 = this.mHandler;
         FacewebChromeActivity.5 var68 = new FacewebChromeActivity.5(var67);
         var66.registerNativeCallHandler("addFriend", var68);
         Button var69 = (Button)this.findViewById(2131624039);
         if(var69 != null) {
            FacewebChromeActivity.6 var70 = new FacewebChromeActivity.6();
            var69.setOnClickListener(var70);
         }

         ArrayList var71 = new ArrayList();
         this.mAppSessionListeners = var71;
         List var72 = this.mAppSessionListeners;
         FacewebChromeActivity.FacewebAppSessionListener var73 = new FacewebChromeActivity.FacewebAppSessionListener();
         var72.add(var73);
         this.mWv.updateScrollingEnabled();
         long var75 = System.currentTimeMillis();
         this.mLastPausedTime = var75;
         Boolean var77 = Gatekeeper.get(this, "meta_composer");
         if(var77 != null) {
            if(var77.booleanValue() == 1) {
               this.mIsInElder = (boolean)1;
            }
         }
      }
   }

   protected Dialog onCreateDialog(int var1) {
      Object var2;
      switch(var1) {
      case 2:
      case 3:
      case 4:
         int var3 = -1;
         if(var1 == 2) {
            var3 = 2131362245;
         } else if(var1 == 3) {
            var3 = 2131362222;
         } else if(var1 == 4) {
            var3 = 2131361981;
         }

         ProgressDialog var4 = new ProgressDialog(this);
         var4.setProgressStyle(0);
         if(var3 != -1) {
            CharSequence var5 = this.getText(var3);
            var4.setMessage(var5);
         }

         var4.setIndeterminate((boolean)1);
         var4.setCancelable((boolean)0);
         var2 = var4;
         break;
      case 255255255:
         var2 = this.getMediaUploader().createDialog();
         break;
      case 255255256:
         var2 = this.getMediaUploader().createPhotoDialog();
         break;
      case 255255257:
         var2 = this.getMediaUploader().createVideoDialog();
         break;
      default:
         var2 = null;
      }

      return (Dialog)var2;
   }

   protected void onDestroy() {
      RefreshableFacewebWebViewContainer var1 = (RefreshableFacewebWebViewContainer)this.findViewById(2131624042);
      if(var1 != null) {
         var1.removeAllViews();
      }

      if(this.mWv != null) {
         FacewebWebView var2 = this.mWv;
         Handler var3 = this.mHandler;
         FacewebChromeActivity.7 var4 = new FacewebChromeActivity.7(var2);
         var3.postDelayed(var4, 30000L);
         this.mWv = null;
      }

      super.onDestroy();
   }

   public boolean onKeyDown(int var1, KeyEvent var2) {
      byte var6;
      if(var1 == 4 && var2.getRepeatCount() == 0) {
         View var3 = this.findViewById(2131624045);
         if(var3.getVisibility() == 0) {
            var3.setVisibility(8);
            FacewebWebView var4 = this.mWv;
            String var5 = this.mPickerView.dismissScript;
            var4.executeJs(var5, (FacebookWebView.JsReturnHandler)null);
            var6 = 1;
            return (boolean)var6;
         }
      }

      var6 = super.onKeyDown(var1, var2);
      return (boolean)var6;
   }

   public void onLocationChanged(Location var1) {
      if(var1 != null) {
         PlacesNearby.PlacesNearbyArgType var2 = new PlacesNearby.PlacesNearbyArgType(var1);
         if(PlacesNearby.get(this, var2) != null) {
            FBLocationManager.removeLocationListener(this.mLocationListener);
         }
      } else {
         FBLocationManager.addLocationListener(this, this);
      }
   }

   public boolean onOptionsItemSelected(MenuItem var1) {
      switch(var1.getItemId()) {
      case 100:
         this.mWv.refresh();
      }

      if(this.mActionMenuItems != null && this.mActionMenuItems.length > 0) {
         int var2 = 0;

         while(true) {
            int var3 = this.mActionMenuItems.length;
            if(var2 >= var3) {
               break;
            }

            int var4 = var2 + 1000;
            if(var1.getItemId() == var4) {
               FacewebWebView var5 = this.mWv;
               String var6 = this.mActionMenuItems[var2].optString("callback");
               var5.executeJs(var6, (FacebookWebView.JsReturnHandler)null);
            }

            ++var2;
         }
      }

      return super.onOptionsItemSelected(var1);
   }

   protected void onPause() {
      if(this.mAppSession != null) {
         Iterator var1 = this.mAppSessionListeners.iterator();

         while(var1.hasNext()) {
            AppSessionListener var2 = (AppSessionListener)var1.next();
            this.mAppSession.removeListener(var2);
         }
      }

      long var3 = System.currentTimeMillis();
      this.mLastPausedTime = var3;
      FBLocationManager.removeLocationListener(this);
      super.onPause();
   }

   public boolean onPrepareOptionsMenu(Menu var1) {
      var1.clear();
      super.onPrepareOptionsMenu(var1);
      if(this.mRefreshable) {
         MenuItem var3 = var1.add(0, 100, 0, 2131362246).setIcon(2130837692);
         MenuItem var4 = var1.findItem(100);
         byte var5;
         if(!this.mShowingProgress) {
            var5 = 1;
         } else {
            var5 = 0;
         }

         var4.setEnabled((boolean)var5);
      }

      if(this.mActionMenuItems != null) {
         int var7 = 0;

         while(true) {
            int var8 = this.mActionMenuItems.length;
            if(var7 >= var8) {
               break;
            }

            int var9 = var7 + 1000;
            JSONObject var10 = this.mActionMenuItems[var7];
            String var11 = var10.optString("title");
            MenuItem var12 = var1.add(0, var9, 0, var11);
            if(var10.has("icon")) {
               int var13 = var10.optInt("icon");
               var12.setIcon(var13);
            }

            ++var7;
         }
      }

      return true;
   }

   public void onResume() {
      super.onResume();
      PerfLogging.Step var1 = PerfLogging.Step.ONRESUME;
      String var2 = this.getTag();
      long var3 = this.getActivityId();
      String var5 = this.mHref;
      PerfLogging.logStep(this, var1, var2, var3, var5);
      String var6 = this.getTag();
      long var7 = this.getActivityId();
      PerfLogging.logPageView(this, var6, var7);
      if(this.mFirstResume) {
         this.mFirstResume = (boolean)0;
      } else {
         ApiLogging.incrementResumeCount();
      }

      AppSession var9 = AppSession.getActiveSession(this, (boolean)1);
      this.mAppSession = var9;
      if(this.mAppSession == null) {
         LoginActivity.toLogin(this);
      } else {
         Iterator var10 = this.mAppSessionListeners.iterator();

         while(var10.hasNext()) {
            AppSessionListener var11 = (AppSessionListener)var10.next();
            this.mAppSession.addListener(var11);
         }

         this.mWv.ensureReadiness();
         long var12 = System.currentTimeMillis();
         long var14 = this.mLastPausedTime;
         long var16 = (var12 - var14) / 1000L;
         Object[] var18 = new Object[2];
         Long var19 = Long.valueOf(var16);
         var18[0] = var19;
         var18[1] = "true";
         String var20 = String.format("(function() { if (window.fwDidEnterForeground) { fwDidEnterForeground(%d, %s); } })()", var18);
         this.mWv.executeJs(var20, (FacebookWebView.JsReturnHandler)null);
      }
   }

   protected void onSaveInstanceState(Bundle var1) {
      super.onSaveInstanceState(var1);
      long var2 = this.mProfileId;
      var1.putLong("PROFILE_ID", var2);
      var1.putBoolean("save_active_state", (boolean)1);
   }

   public void onTimeOut() {}

   public void setProgressListener(TabProgressListener var1) {
      this.mProgressListener = var1;
      if(this.mProgressListener != null) {
         TabProgressListener var2 = this.mProgressListener;
         boolean var3 = this.mShowingProgress;
         var2.onShowProgress(var3);
      }
   }

   protected class ShowCommentPublisherHandler extends FacewebChromeActivity.ShowTextPublisherHandler {

      protected String mCommentCallback;
      protected String mPostId;


      public ShowCommentPublisherHandler(Handler var2) {
         super(var2);
      }

      public void handle(FacebookWebView var1, String var2, boolean var3, String var4) {
         FacewebChromeActivity.this.removeDialog(3);
         if(var3) {
            Toaster.toast(FacewebChromeActivity.this, 2131362220);
         } else {
            super.handle(var1, var2, var3, var4);
         }
      }

      public void handleNonUI(FacebookWebView var1, FacebookRpcCall var2) {
         String var3 = var2.getParameterByName("callback");
         this.mCommentCallback = var3;
         String var4 = var2.getParameterByName("post_id");
         this.mPostId = var4;
      }

      public void handleUI(FacebookWebView var1, FacebookRpcCall var2) {
         super.handleUI(var1, var2);
         TaggingAutoCompleteTextView var3 = (TaggingAutoCompleteTextView)((EditText)FacewebChromeActivity.this.findViewById(2131624054));
         FacewebChromeActivity var4 = FacewebChromeActivity.this;
         ProfileImagesCache var5 = FacewebChromeActivity.this.mAppSession.getUserImagesCache();
         AppSessionListener var6 = var3.configureView(var4, var5);
         if(FacewebChromeActivity.this.isOnTop()) {
            FacewebChromeActivity.this.mAppSession.addListener(var6);
         }

         boolean var7 = FacewebChromeActivity.this.mAppSessionListeners.add(var6);
      }

      protected void submitText(FacebookWebView var1, TextView var2) {
         super.submitText(var1, var2);
         String var3 = Utils.convertMentionTags(((EditText)var2).getEditableText());
         if(var3.length() > 0) {
            JSONObject var4 = new JSONObject();

            try {
               var4.put("text", var3);
               String var6 = this.mPostId;
               var4.put("post_id", var6);
            } catch (JSONException var12) {
               Log.e(FacewebChromeActivity.this.getTag(), "inconceivable exception", var12);
            }

            ArrayList var8 = new ArrayList();
            var8.add(var4);
            FacewebChromeActivity.this.showDialog(3);
            String var10 = this.mCommentCallback;
            var1.executeJsFunction(var10, var8, this);
         }
      }
   }

   protected class FacewebAppSessionListener extends AppSessionListener {

      protected FacewebAppSessionListener() {}

      public void onCheckinComplete(AppSession var1, String var2, int var3, String var4, Exception var5, FacebookPost var6) {
         String var7 = FacewebChromeActivity.this.mCheckinReqId;
         if(var2.equals(var7)) {
            String var8 = FacewebChromeActivity.this.mCheckinReqId = null;
            if(var3 == 200) {
               JSONObject var9 = new JSONObject();

               try {
                  JSONObject var10 = var9.put("action", "didCheckin");
                  String var11 = var6.postId;
                  var9.put("checkin_id", var11);
               } catch (JSONException var23) {
                  Log.e(FacewebChromeActivity.this.getTag(), "inconceivable exception", var23);
               }

               ArrayList var13 = new ArrayList();
               var13.add(var9);
               FacewebChromeActivity.this.showDialog(2);
               FacewebWebView var15 = FacewebChromeActivity.this.mWv;
               String var16 = FacewebChromeActivity.this.mPublisherCallback;
               FacewebChromeActivity var17 = FacewebChromeActivity.this;
               FacewebChromeActivity.ShareHandler var18 = var17.new ShareHandler();
               var15.executeJsFunction(var16, var13, var18);
            } else {
               FacewebChromeActivity var20 = FacewebChromeActivity.this;
               String var21 = FacewebChromeActivity.this.getString(2131362368);
               String var22 = StringUtils.getErrorString(var20, var21, var3, var4, var5);
               Toaster.toast(FacewebChromeActivity.this, var22);
            }
         }
      }

      public void onGkSettingsGetComplete(AppSession var1, String var2, int var3, String var4, Exception var5, String var6, boolean var7) {
         if(var3 == 200) {
            if("meta_composer".equals(var6)) {
               if(var7) {
                  boolean var8 = (boolean)(FacewebChromeActivity.this.mIsInElder = (boolean)1);
               }
            }
         }
      }

      public void onLoginComplete(AppSession var1, String var2, int var3, String var4, Exception var5) {
         FacewebChromeActivity.this.mWv.reset();
      }

      public void onPhotoUploadComplete(AppSession var1, String var2, int var3, String var4, Exception var5, int var6, FacebookPhoto var7, String var8, long var9, long var11, long var13) {
         long var15 = FacewebChromeActivity.this.mProfileId;
         if(var11 == var15) {
            FacewebChromeActivity.this.mWv.refresh();
         }
      }
   }

   protected class ShowMessageComposerHandler extends FacebookWebView.NativeUICallHandler {

      public ShowMessageComposerHandler(Handler var2) {
         super(var2);
      }

      public void handleUI(FacebookWebView var1, FacebookRpcCall var2) {
         FacewebChromeActivity var3 = FacewebChromeActivity.this;
         Intent var4 = new Intent(var3, MessageComposeActivity.class);
         FacewebChromeActivity.this.startActivity(var4);
      }
   }

   protected class SetActionMenuHandler extends FacebookWebView.NativeUICallHandler {

      private static final String ACTION_ARCHIVE = "archive";
      private static final String ACTION_DELETE = "delete";
      private static final String ACTION_FORWARD = "forward";
      private static final String ACTION_MARK_SPAM = "mark_spam";
      private static final String ACTION_MARK_UNREAD = "mark_unread";
      private static final String ACTION_MOVE = "move";
      private static final String ACTION_UNARCHIVE = "unarchive";


      public SetActionMenuHandler(Handler var2) {
         super(var2);
      }

      int getIconForAction(String var1) {
         int var2;
         if(var1.equals("mark_unread")) {
            var2 = 2130837690;
         } else if(var1.equals("mark_spam")) {
            var2 = 2130837688;
         } else if(var1.equals("archive")) {
            var2 = 2130837684;
         } else if(var1.equals("unarchive")) {
            var2 = 2130837689;
         } else if(var1.equals("move")) {
            var2 = 2130837687;
         } else if(var1.equals("delete")) {
            var2 = 2130837685;
         } else if(var1.equals("forward")) {
            var2 = 2130837686;
         } else {
            var2 = -1;
         }

         return var2;
      }

      public void handleUI(FacebookWebView param1, FacebookRpcCall param2) {
         // $FF: Couldn't be decompiled
      }
   }

   private class UpdateNativeLoadingIndicator extends FacebookWebView.NativeUICallHandler {

      public UpdateNativeLoadingIndicator(Handler var2) {
         super(var2);
      }

      public void handleNonUI(FacebookWebView var1, FacebookRpcCall var2) {
         FacewebChromeActivity var3 = FacewebChromeActivity.this;
         boolean var4 = var2.method.equals("pageLoading");
         var3.mLoading = var4;
      }

      public void handleUI(FacebookWebView var1, FacebookRpcCall var2) {
         byte var3;
         if(FacewebChromeActivity.this.mLoading) {
            var3 = 0;
         } else {
            var3 = 8;
         }

         if(FacewebChromeActivity.this.mProgressListener != null) {
            TabProgressListener var4 = FacewebChromeActivity.this.mProgressListener;
            boolean var5 = FacewebChromeActivity.this.mLoading;
            var4.onShowProgress(var5);
         }

         FacewebChromeActivity.this.findViewById(2131623953).setVisibility(var3);
         FacewebChromeActivity var6 = FacewebChromeActivity.this;
         boolean var7 = FacewebChromeActivity.this.mLoading;
         var6.mShowingProgress = var7;
         FacewebChromeActivity var9 = FacewebChromeActivity.this;
         PerfLogging.Step var10;
         if(FacewebChromeActivity.this.mLoading) {
            var10 = PerfLogging.Step.DATA_REQUESTED;
         } else {
            var10 = PerfLogging.Step.DATA_RECEIVED;
         }

         String var11 = FacewebChromeActivity.this.getTag();
         long var12 = FacewebChromeActivity.this.getActivityId();
         String var14 = FacewebChromeActivity.this.mHref;
         PerfLogging.logStep(var9, var10, var11, var12, var14);
      }
   }

   protected class DismissModalDialog extends FacebookWebView.NativeUICallHandler {

      private static final String PARAM_ANIMATED = "animated";


      public DismissModalDialog(Handler var2) {
         super(var2);
      }

      public void handleUI(FacebookWebView var1, FacebookRpcCall var2) {
         ComposerUserSettings.setSetting(FacewebChromeActivity.this, "composer_tour_completed", "1");
         String var3 = ComposerUserSettings.get(FacewebChromeActivity.this, "composer_share_location");
         FacewebChromeActivity.this.finish();
      }
   }

   protected abstract class ShowTextPublisherHandler extends FacebookWebView.NativeUICallHandler implements FacebookWebView.JsReturnHandler {

      private final Button mSendButton;


      public ShowTextPublisherHandler(Handler var2) {
         super(var2);
         Button var3 = (Button)FacewebChromeActivity.this.findViewById(2131624055);
         this.mSendButton = var3;
      }

      public void handle(FacebookWebView var1, String var2, boolean var3, String var4) {
         Handler var5 = this.mHandler;
         FacewebChromeActivity.ShowTextPublisherHandler.5 var6 = new FacewebChromeActivity.ShowTextPublisherHandler.5();
         var5.post(var6);
      }

      public void handleUI(FacebookWebView var1, FacebookRpcCall var2) {
         FacewebChromeActivity.this.findViewById(2131624053).setVisibility(0);
         EditText var3 = (EditText)FacewebChromeActivity.this.findViewById(2131624054);
         FacewebChromeActivity.ShowTextPublisherHandler.1 var4 = new FacewebChromeActivity.ShowTextPublisherHandler.1(var1);
         var3.setOnEditorActionListener(var4);
         FacewebChromeActivity.ShowTextPublisherHandler.2 var5 = new FacewebChromeActivity.ShowTextPublisherHandler.2();
         var3.setOnFocusChangeListener(var5);
         Button var6 = this.mSendButton;
         byte var7;
         if(var3.getText().length() > 0) {
            var7 = 1;
         } else {
            var7 = 0;
         }

         var6.setEnabled((boolean)var7);
         FacewebChromeActivity.ShowTextPublisherHandler.3 var8 = new FacewebChromeActivity.ShowTextPublisherHandler.3(var3);
         var3.addTextChangedListener(var8);
         Button var9 = this.mSendButton;
         FacewebChromeActivity.ShowTextPublisherHandler.4 var10 = new FacewebChromeActivity.ShowTextPublisherHandler.4(var1);
         var9.setOnClickListener(var10);
      }

      protected void submitText(FacebookWebView var1, TextView var2) {
         InputMethodManager var3 = (InputMethodManager)FacewebChromeActivity.this.getSystemService("input_method");
         IBinder var4 = var2.getWindowToken();
         var3.hideSoftInputFromWindow(var4, 0);
      }

      class 2 implements OnFocusChangeListener {

         2() {}

         public void onFocusChange(View var1, boolean var2) {
            Button var3 = ShowTextPublisherHandler.this.mSendButton;
            byte var4;
            if(var2) {
               var4 = 0;
            } else {
               var4 = 8;
            }

            var3.setVisibility(var4);
         }
      }

      class 3 implements TextWatcher {

         // $FF: synthetic field
         final EditText val$et;


         3(EditText var2) {
            this.val$et = var2;
         }

         public void afterTextChanged(Editable var1) {}

         public void beforeTextChanged(CharSequence var1, int var2, int var3, int var4) {}

         public void onTextChanged(CharSequence var1, int var2, int var3, int var4) {
            Button var5 = ShowTextPublisherHandler.this.mSendButton;
            byte var6;
            if(this.val$et.getText().length() > 0) {
               var6 = 1;
            } else {
               var6 = 0;
            }

            var5.setEnabled((boolean)var6);
         }
      }

      class 4 implements OnClickListener {

         // $FF: synthetic field
         final FacebookWebView val$wv;


         4(FacebookWebView var2) {
            this.val$wv = var2;
         }

         public void onClick(View var1) {
            EditText var2 = (EditText)FacewebChromeActivity.this.findViewById(2131624054);
            if(var2.getText().toString().trim().length() > 0) {
               FacewebChromeActivity.ShowTextPublisherHandler var3 = ShowTextPublisherHandler.this;
               FacebookWebView var4 = this.val$wv;
               var3.submitText(var4, var2);
            }
         }
      }

      class 5 implements Runnable {

         5() {}

         public void run() {
            ((EditText)FacewebChromeActivity.this.findViewById(2131624054)).setText((CharSequence)null);
         }
      }

      class 1 implements OnEditorActionListener {

         // $FF: synthetic field
         final FacebookWebView val$wv;


         1(FacebookWebView var2) {
            this.val$wv = var2;
         }

         public boolean onEditorAction(TextView var1, int var2, KeyEvent var3) {
            int var4 = var1.getImeActionId();
            if(var2 == var4 && var1.getText().toString().trim().length() > 0) {
               FacewebChromeActivity.ShowTextPublisherHandler var5 = ShowTextPublisherHandler.this;
               FacebookWebView var6 = this.val$wv;
               var5.submitText(var6, var1);
            }

            return false;
         }
      }
   }

   private class NewsFeedToggleOptionsAdapter extends AbstractWheelTextAdapter {

      private List<NewsFeedToggleOption> mOptions;


      protected NewsFeedToggleOptionsAdapter(Context var2, List var3) {
         super(var2, 2130903113, 0);
         this.mOptions = var3;
         this.setItemTextResource(2131624147);
      }

      protected CharSequence getItemText(int var1) {
         return ((NewsFeedToggleOption)this.mOptions.get(var1)).title;
      }

      public int getItemsCount() {
         return this.mOptions.size();
      }
   }

   protected class ShowPickerView extends FacebookWebView.NativeUICallHandler {

      private static final String PARAM_KEY_DISMISS_SCRIPT = "dismiss_script";
      private static final String PARAM_KEY_OPTIONS = "options";
      private static final String PARAM_KEY_SELECTED_INDEX = "selected_index";
      protected String dismissScript;
      protected Boolean shouldDismissOnScroll;


      public ShowPickerView(Handler var2) {
         super(var2);
      }

      private void dismissToggle(FacebookWebView var1) {
         FacewebChromeActivity.this.findViewById(2131624045).setVisibility(8);
         String var2 = this.dismissScript;
         var1.executeJs(var2, (FacebookWebView.JsReturnHandler)null);
      }

      public void handleUI(FacebookWebView var1, FacebookRpcCall var2) {
         String var3 = var2.getParameterByName("dismiss_script");
         this.dismissScript = var3;
         Boolean var4 = Boolean.valueOf((boolean)0);
         this.shouldDismissOnScroll = var4;

         try {
            String var5 = var2.getParameterByName("options");
            String var6 = new String(var5);
            JsonParser var7 = (new FBJsonFactory()).createJsonParser(var6);
            JsonToken var8 = var7.nextToken();
            List var9 = JMParser.parseObjectListJson(var7, NewsFeedToggleOption.class);
            View var10 = FacewebChromeActivity.this.findViewById(2131624045);
            var10.setVisibility(0);
            var10.bringToFront();
            WheelView var11 = (WheelView)FacewebChromeActivity.this.findViewById(2131624047);
            int var12 = var9.size();
            int var13 = Math.min(5, var12);
            var11.setVisibleItems(var13);
            FacewebChromeActivity var14 = FacewebChromeActivity.this;
            FacewebChromeActivity var15 = FacewebChromeActivity.this;
            FacewebChromeActivity.NewsFeedToggleOptionsAdapter var16 = var14.new NewsFeedToggleOptionsAdapter(var15, var9);
            var11.setViewAdapter(var16);
            int var17 = Integer.parseInt(var2.getParameterByName("selected_index"));
            var11.setCurrentItem(var17);
            FacewebChromeActivity.ShowPickerView.1 var18 = new FacewebChromeActivity.ShowPickerView.1(var1);
            var11.addClickingListener(var18);
            FacewebChromeActivity.ShowPickerView.2 var19 = new FacewebChromeActivity.ShowPickerView.2(var1);
            var11.addScrollingListener(var19);
            View var20 = FacewebChromeActivity.this.findViewById(2131624046);
            FacewebChromeActivity.ShowPickerView.3 var21 = new FacewebChromeActivity.ShowPickerView.3(var1);
            var20.setOnClickListener(var21);
         } catch (JsonParseException var25) {
            Log.e(FacewebChromeActivity.this.getTag(), "received bad faceweb data", var25);
         } catch (IOException var26) {
            Log.e(FacewebChromeActivity.this.getTag(), "received bad faceweb data", var26);
         } catch (JMException var27) {
            Log.e(FacewebChromeActivity.this.getTag(), "received bad faceweb data", var27);
         }
      }

      class 1 implements OnWheelClickedListener {

         // $FF: synthetic field
         final FacebookWebView val$wv;


         1(FacebookWebView var2) {
            this.val$wv = var2;
         }

         public void onItemClicked(WheelView var1, int var2) {
            if(var1.getCurrentItem() != var2) {
               var1.setCurrentItem(var2, (boolean)1);
               FacebookWebView var3 = this.val$wv;
               String var4 = ((NewsFeedToggleOption)((FacewebChromeActivity.NewsFeedToggleOptionsAdapter)var1.getViewAdapter()).mOptions.get(var2)).script;
               var3.executeJs(var4, (FacebookWebView.JsReturnHandler)null);
               FacewebChromeActivity.ShowPickerView var5 = ShowPickerView.this;
               Boolean var6 = Boolean.valueOf((boolean)1);
               var5.shouldDismissOnScroll = var6;
            } else {
               FacewebChromeActivity.ShowPickerView var7 = ShowPickerView.this;
               FacebookWebView var8 = this.val$wv;
               var7.dismissToggle(var8);
            }
         }
      }

      class 2 implements OnWheelScrollListener {

         // $FF: synthetic field
         final FacebookWebView val$wv;


         2(FacebookWebView var2) {
            this.val$wv = var2;
         }

         public void onScrollingFinished(WheelView var1) {
            FacebookWebView var2 = this.val$wv;
            List var3 = ((FacewebChromeActivity.NewsFeedToggleOptionsAdapter)var1.getViewAdapter()).mOptions;
            int var4 = var1.getCurrentItem();
            String var5 = ((NewsFeedToggleOption)var3.get(var4)).script;
            var2.executeJs(var5, (FacebookWebView.JsReturnHandler)null);
            if(ShowPickerView.this.shouldDismissOnScroll.booleanValue()) {
               FacewebChromeActivity.ShowPickerView var6 = ShowPickerView.this;
               Boolean var7 = Boolean.valueOf((boolean)0);
               var6.shouldDismissOnScroll = var7;
               FacewebChromeActivity.ShowPickerView var8 = ShowPickerView.this;
               FacebookWebView var9 = this.val$wv;
               var8.dismissToggle(var9);
            }
         }

         public void onScrollingStarted(WheelView var1) {}
      }

      class 3 implements OnClickListener {

         // $FF: synthetic field
         final FacebookWebView val$wv;


         3(FacebookWebView var2) {
            this.val$wv = var2;
         }

         public void onClick(View var1) {
            FacewebChromeActivity.ShowPickerView var2 = ShowPickerView.this;
            FacebookWebView var3 = this.val$wv;
            var2.dismissToggle(var3);
         }
      }
   }

   protected class SetToolbarSegmentsHandler extends FacebookWebView.NativeUICallHandler {

      private static final String PARAM_CURRENT_TAB = "current_tab";
      private static final String PARAM_SEGMENTS = "segments";
      private static final String TAB_CALLBACK = "callback";
      private static final String TAB_TITLE = "title";
      private String[] callbacks;
      private int mCurrentTab = -1;


      public SetToolbarSegmentsHandler(Handler var2) {
         super(var2);
      }

      public void handleUI(FacebookWebView var1, FacebookRpcCall var2) {
         try {
            String var4 = "segments";
            String var5 = var2.getParameterByName(var4);
            JSONArray var6 = new JSONArray(var5);
            RadioGroup var7 = (RadioGroup)FacewebChromeActivity.this.findViewById(2131624044);
            String[] var8 = new String[var6.length()];
            this.callbacks = var8;
            var7.removeAllViews();
            var7.clearCheck();
            int var9 = 0;

            while(true) {
               int var10 = var6.length();
               if(var9 >= var10) {
                  byte var19 = 0;
                  this.mCurrentTab = var19;
                  String var21 = "current_tab";
                  String var22 = var2.getParameterByName(var21);
                  if(var22 != null) {
                     int var23 = Integer.parseInt(var22);
                     this.mCurrentTab = var23;
                  }

                  int var24 = this.mCurrentTab;
                  var7.check(var24);
                  FacewebChromeActivity.SetToolbarSegmentsHandler.1 var25 = new FacewebChromeActivity.SetToolbarSegmentsHandler.1();
                  var7.setOnCheckedChangeListener(var25);
                  return;
               }

               JSONObject var11 = var6.getJSONObject(var9);
               String var12 = var11.optString("title");
               String var13 = var11.optString("callback");
               this.callbacks[var9] = var13;
               RadioButton var17 = this.setupAndGetTabView(var9, var12);
               var17.setTag(var13);
               var7.addView(var17);
               LayoutParams var18 = new LayoutParams(0, -1, 1.0F);
               var17.setLayoutParams(var18);
               ++var9;
            }
         } catch (JSONException var29) {
            Log.e(FacewebChromeActivity.this.getTag(), "Data format error", var29);
         }
      }

      protected RadioButton setupAndGetTabView(int var1, String var2) {
         RadioButton var3 = (RadioButton)FacewebChromeActivity.this.getLayoutInflater().inflate(2130903168, (ViewGroup)null);
         var3.setButtonDrawable(2130837600);
         var3.setId(var1);
         var3.setText(var2);
         return var3;
      }

      protected void switchTab(int var1) {
         if(this.mCurrentTab != var1) {
            this.mCurrentTab = var1;
            if(var1 >= 0) {
               int var2 = this.callbacks.length;
               if(var1 < var2) {
                  FacewebWebView var3 = FacewebChromeActivity.this.mWv;
                  String var4 = this.callbacks[var1];
                  var3.executeJs(var4, (FacebookWebView.JsReturnHandler)null);
               }
            }
         }
      }

      class 1 implements OnCheckedChangeListener {

         1() {}

         public void onCheckedChanged(RadioGroup var1, int var2) {
            SetToolbarSegmentsHandler.this.switchTab(var2);
         }
      }
   }

   protected class ShowReplyPublisherHandler extends FacewebChromeActivity.ShowTextPublisherHandler {

      String mReplyCallback;


      public ShowReplyPublisherHandler(Handler var2) {
         super(var2);
      }

      public void handle(FacebookWebView var1, String var2, boolean var3, String var4) {
         FacewebChromeActivity.this.removeDialog(4);
         if(var3) {
            Toaster.toast(FacewebChromeActivity.this, 2131362220);
         } else {
            super.handle(var1, var2, var3, var4);
         }
      }

      public void handleNonUI(FacebookWebView var1, FacebookRpcCall var2) {
         String var3 = var2.getParameterByName("callback");
         this.mReplyCallback = var3;
      }

      public void handleUI(FacebookWebView var1, FacebookRpcCall var2) {
         super.handleUI(var1, var2);
         ((EditText)FacewebChromeActivity.this.findViewById(2131624054)).setHint(2131361977);
         ((Button)FacewebChromeActivity.this.findViewById(2131624055)).setText(2131361980);
      }

      protected void submitText(FacebookWebView var1, TextView var2) {
         super.submitText(var1, var2);
         JSONObject var3 = new JSONObject();

         try {
            String var4 = var2.getText().toString().trim();
            var3.put("text", var4);
         } catch (JSONException var14) {
            String var10 = FacewebChromeActivity.this.getTag();
            StringBuilder var11 = (new StringBuilder()).append("inconceivable exception ");
            String var12 = var14.toString();
            String var13 = var11.append(var12).toString();
            Log.e(var10, var13);
         }

         ArrayList var6 = new ArrayList();
         var6.add(var3);
         FacewebChromeActivity.this.showDialog(4);
         String var8 = this.mReplyCallback;
         var1.executeJsFunction(var8, var6, this);
      }
   }

   class 7 implements Runnable {

      // $FF: synthetic field
      final FacewebWebView val$webViewToKill;


      7(FacewebWebView var2) {
         this.val$webViewToKill = var2;
      }

      public void run() {
         if(this.val$webViewToKill != null) {
            this.val$webViewToKill.destroy();
         }
      }
   }

   class 6 implements OnClickListener {

      6() {}

      public void onClick(View var1) {
         FacewebChromeActivity.this.titleBarClickHandler(var1);
      }
   }

   class 3 extends FacebookWebView.NativeUICallHandler {

      3(Handler var2) {
         super(var2);
      }

      public void handleUI(FacebookWebView var1, FacebookRpcCall var2) {
         FacewebChromeActivity.this.finish();
      }
   }

   class 2 implements FacebookWebView.NativeCallHandler {

      2() {}

      public void handle(Context var1, FacebookWebView var2, FacebookRpcCall var3) {
         boolean var4 = (boolean)(FacewebChromeActivity.this.mRefreshable = (boolean)1);
      }
   }

   protected class ShareHandler implements OnClickListener, FacebookWebView.JsReturnHandler {

      protected FacebookWebView mWebview;


      public ShareHandler() {}

      public ShareHandler(FacebookWebView var2) {
         this.mWebview = var2;
      }

      public void handle(FacebookWebView var1, String var2, boolean var3, String var4) {
         FacewebChromeActivity.this.removeDialog(2);
         if(var3) {
            Toaster.toast(FacewebChromeActivity.this, 2131362244);
         } else if(!FacewebChromeActivity.this.mIsInElder) {
            Handler var5 = FacewebChromeActivity.this.mHandler;
            FacewebChromeActivity.ShareHandler.1 var6 = new FacewebChromeActivity.ShareHandler.1();
            var5.post(var6);
         }
      }

      public void onClick(View var1) {
         String var2 = Utils.convertMentionTags(((EditText)FacewebChromeActivity.this.findViewById(2131624232)).getText());
         if(var2.length() > 0) {
            JSONObject var3 = new JSONObject();

            try {
               JSONObject var4 = var3.put("action", "didPostStatus");
               var3.put("text", var2);
            } catch (JSONException var15) {
               String var11 = FacewebChromeActivity.this.getTag();
               StringBuilder var12 = (new StringBuilder()).append("inconceivable exception ");
               String var13 = var15.toString();
               String var14 = var12.append(var13).toString();
               Log.e(var11, var14);
            }

            ArrayList var6 = new ArrayList();
            var6.add(var3);
            FacewebChromeActivity.this.showDialog(2);
            FacebookWebView var8 = this.mWebview;
            String var9 = FacewebChromeActivity.this.mPublisherCallback;
            var8.executeJsFunction(var9, var6, this);
         }
      }

      class 1 implements Runnable {

         1() {}

         public void run() {
            EditText var1 = (EditText)FacewebChromeActivity.this.findViewById(2131624232);
            var1.setText((CharSequence)null);
            InputMethodManager var2 = (InputMethodManager)FacewebChromeActivity.this.getSystemService("input_method");
            IBinder var3 = var1.getWindowToken();
            var2.hideSoftInputFromWindow(var3, 0);
         }
      }
   }

   class 5 extends FacebookWebView.NativeUICallHandler {

      5(Handler var2) {
         super(var2);
      }

      public void handleUI(FacebookWebView var1, FacebookRpcCall var2) {
         long var3;
         try {
            var3 = Long.parseLong(var2.getParameterByName("userId"));
         } catch (Exception var14) {
            return;
         }

         FacewebChromeActivity.5.1 var7 = new FacewebChromeActivity.5.1();
         boolean var8 = FacewebChromeActivity.this.mAppSessionListeners.add(var7);
         FacewebChromeActivity.this.mAppSession.addListener(var7);
         FacewebChromeActivity var9 = FacewebChromeActivity.this;
         FacewebChromeActivity.5.2 var10 = new FacewebChromeActivity.5.2();
         Dialog var11 = Dialogs.addFriend(var9, var3, var10);
         FacewebChromeActivity var12 = FacewebChromeActivity.this;
         var11.setOwnerActivity(var12);
         var11.show();
      }

      class 1 extends AppSessionListener {

         1() {}

         public void onFriendsAddFriendComplete(AppSession var1, String var2, int var3, String var4, Exception var5, List<Long> var6) {
            if(var3 == 200) {
               FacewebChromeActivity.this.mWv.refresh();
            }
         }
      }

      class 2 implements Dialogs.AddFriendListener {

         2() {}

         public void onAddFriendStart(String var1) {}
      }
   }

   protected class SetNavBarHiddenHandler extends FacebookWebView.NativeUICallHandler {

      private static final String PARAM_ANIMATED = "animated";
      private static final String PARAM_KEY_HIDDEN = "hidden";


      public SetNavBarHiddenHandler(Handler var2) {
         super(var2);
      }

      public void handleUI(FacebookWebView var1, FacebookRpcCall var2) {
         byte var3;
         if(Boolean.valueOf(var2.getParameterByName("hidden")).booleanValue()) {
            var3 = 8;
         } else {
            var3 = 0;
         }

         FacewebChromeActivity.this.findViewById(2131623958).setVisibility(var3);
      }
   }

   class 4 implements FacebookWebView.NativeCallHandler {

      4() {}

      public void handle(Context var1, FacebookWebView var2, FacebookRpcCall var3) {
         FacewebChromeActivity var4 = FacewebChromeActivity.this;
         PerfLogging.Step var5 = PerfLogging.Step.UI_DRAWN_STALE;
         String var6 = FacewebChromeActivity.this.getTag();
         long var7 = FacewebChromeActivity.this.getActivityId();
         String var9 = FacewebChromeActivity.this.mHref;
         PerfLogging.logStep(var4, var5, var6, var7, var9);
      }
   }

   protected class SetNavBarButton extends FacebookWebView.NativeUICallHandler {

      private static final String BUTTON_TYPE_COMPOSE = "compose";
      private static final String PARAM_KEY_ANIMATED = "animated";
      private static final String PARAM_KEY_IS_DISABLED = "isDisabled";
      private static final String PARAM_KEY_POSITION = "position";
      private static final String PARAM_KEY_SCRIPT = "script";
      private static final String PARAM_KEY_TITLE = "title";
      private static final String PARAM_KEY_TYPE = "type";


      public SetNavBarButton(Handler var2) {
         super(var2);
      }

      public int getIconForType(String var1) {
         int var2;
         if(var1.equals("compose")) {
            var2 = 2130837754;
         } else {
            var2 = -1;
         }

         return var2;
      }

      public void handleNonUI(FacebookWebView var1, FacebookRpcCall var2) {
         FacewebChromeActivity var3 = FacewebChromeActivity.this;
         String var4 = FacewebChromeActivity.getStringParam(var2, "script");
         var3.mNavButtonCallback = var4;
      }

      public void handleUI(FacebookWebView var1, FacebookRpcCall var2) {
         String var3 = var2.getParameterByName("isDisabled");
         if(!"true".equals(var3)) {
            String var4 = var2.getParameterByName("position");
            if("right".equals(var4)) {
               String var5 = var2.getParameterByName("title");
               String var6 = var2.getParameterByName("type");
               int var7 = this.getIconForType(var6);
               if(var7 >= 0) {
                  FacewebChromeActivity.this.setPrimaryActionFace(var7, (String)null);
                  ImageButton var8 = (ImageButton)FacewebChromeActivity.this.findViewById(2131623998);
                  FacewebChromeActivity.SetNavBarButton.NavBarButtonHandler var9 = new FacewebChromeActivity.SetNavBarButton.NavBarButtonHandler(var1);
                  var8.setOnClickListener(var9);
               } else {
                  FacewebChromeActivity.this.setPrimaryActionFace(-1, var5);
                  Button var10 = (Button)FacewebChromeActivity.this.findViewById(2131623997);
                  FacewebChromeActivity.SetNavBarButton.NavBarButtonHandler var11 = new FacewebChromeActivity.SetNavBarButton.NavBarButtonHandler(var1);
                  var10.setOnClickListener(var11);
               }
            }
         }
      }

      class NavBarButtonHandler implements OnClickListener {

         protected FacebookWebView mWebview;


         public NavBarButtonHandler(FacebookWebView var2) {
            this.mWebview = var2;
         }

         public void onClick(View var1) {
            FacebookWebView var2 = this.mWebview;
            String var3 = FacewebChromeActivity.this.mNavButtonCallback;
            var2.executeJs(var3, (FacebookWebView.JsReturnHandler)null);
         }
      }
   }

   protected class ShowPublisherHandler extends FacebookWebView.NativeUICallHandler {

      private static final String PUBLISHER_TYPE_EVENT = "event";
      private static final String PUBLISHER_TYPE_FEED = "feed";
      private static final String PUBLISHER_TYPE_GROUP = "group";
      private static final String PUBLISHER_TYPE_USER = "user";
      protected boolean mExecuted;


      ShowPublisherHandler(Handler var2) {
         super(var2);
      }

      public void handleNonUI(FacebookWebView var1, FacebookRpcCall var2) {
         FacewebChromeActivity var3 = FacewebChromeActivity.this;
         String var4 = var2.getParameterByName("callback");
         var3.mPublisherCallback = var4;
         String var5 = var2.getParameterByName("target");

         try {
            FacewebChromeActivity var6 = FacewebChromeActivity.this;
            long var7 = Long.parseLong(var5);
            var6.mProfileId = var7;
         } catch (NumberFormatException var17) {
            ;
         } catch (NullPointerException var18) {
            ;
         }

         String var9 = ComposerUserSettings.get(FacewebChromeActivity.this, "composer_share_location");
         String var10 = ComposerUserSettings.get(FacewebChromeActivity.this, "composer_tour_completed");
         FacewebChromeActivity var11 = FacewebChromeActivity.this;
         PrivacySetting.Category var12 = PrivacySetting.Category.composer_sticky;
         AudienceSettings.get(var11, var12);
         Location var14 = FBLocationManager.getRecentLocation(300000);
         FacewebChromeActivity.this.onLocationChanged(var14);
      }

      public void handleUI(FacebookWebView var1, FacebookRpcCall var2) {
         if(FacewebChromeActivity.this.mPublisher != null) {
            FacewebChromeActivity.this.mPublisher.setVisibility(0);
         } else {
            LinearLayout var3 = (LinearLayout)FacewebChromeActivity.this.findViewById(2131624041);
            LayoutInflater var4 = (LayoutInflater)FacewebChromeActivity.this.getSystemService("layout_inflater");
            if(FacewebChromeActivity.this.mIsInElder) {
               LayoutParams var5 = new LayoutParams(-1, -1);
               FacewebChromeActivity var6 = FacewebChromeActivity.this;
               View var7 = var4.inflate(2130903147, (ViewGroup)null);
               var6.mPublisher = var7;
               View var8 = FacewebChromeActivity.this.mPublisher;
               var3.addView(var8, var5);
               this.setupPublisher(var2);
            } else {
               View var9 = var4.inflate(2130903156, (ViewGroup)null);
               var3.addView(var9);
               View var10 = FacewebChromeActivity.this.findViewById(2131624233);
               FacewebChromeActivity var11 = FacewebChromeActivity.this;
               FacewebChromeActivity.ShareHandler var12 = var11.new ShareHandler(var1);
               var10.setOnClickListener(var12);
               View var13 = FacewebChromeActivity.this.findViewById(2131624231);
               FacewebChromeActivity.ShowPublisherHandler.1 var14 = new FacewebChromeActivity.ShowPublisherHandler.1();
               var13.setOnClickListener(var14);
               TaggingAutoCompleteTextView var15 = (TaggingAutoCompleteTextView)((EditText)FacewebChromeActivity.this.findViewById(2131624232));
               FacewebChromeActivity var16 = FacewebChromeActivity.this;
               ProfileImagesCache var17 = FacewebChromeActivity.this.mAppSession.getUserImagesCache();
               AppSessionListener var18 = var15.configureView(var16, var17);
               if(FacewebChromeActivity.this.isOnTop()) {
                  FacewebChromeActivity.this.mAppSession.addListener(var18);
               }

               boolean var19 = FacewebChromeActivity.this.mAppSessionListeners.add(var18);
            }
         }
      }

      protected void setupCheckInOnClick() {
         View var1 = FacewebChromeActivity.this.findViewById(2131624211);
         FacewebChromeActivity.ShowPublisherHandler.4 var2 = new FacewebChromeActivity.ShowPublisherHandler.4();
         var1.setOnClickListener(var2);
         if(FacewebChromeActivity.this.getIntent().getAction() != null) {
            if(FacewebChromeActivity.this.getIntent().getAction().equals("com.facebook.katana.SHARE")) {
               FacewebChromeActivity var3 = FacewebChromeActivity.this;
               Integer var4 = Integer.valueOf(10);
               var3.launchComposer((Uri)null, (Bundle)null, var4);
            }
         }
      }

      protected void setupPhotoOnClick(boolean var1) {
         View var2 = FacewebChromeActivity.this.findViewById(2131624207);
         FacewebChromeActivity.ShowPublisherHandler.3 var3 = new FacewebChromeActivity.ShowPublisherHandler.3(var1);
         var2.setOnClickListener(var3);
      }

      protected void setupPublisher(FacebookRpcCall var1) {
         String var2 = var1.getParameterByName("type");
         String var3 = var1.getParameterByName("target");
         long var4 = 65535L;

         label42: {
            long var6;
            try {
               var6 = Long.parseLong(var3);
            } catch (NumberFormatException var12) {
               break label42;
            } catch (NullPointerException var13) {
               break label42;
            }

            var4 = var6;
         }

         if(!var2.equals("event") && (!var2.equals("user") || var4 == 65535L || FacewebChromeActivity.this.mAppSession.getSessionInfo().getProfile().mUserId == var4)) {
            this.setupPublisherButton(2131624207, 2131362098, 2130837771);
            this.setupPhotoOnClick((boolean)1);
            int var9;
            if(var2.equals("group")) {
               var9 = 2131361838;
            } else {
               var9 = 2131362171;
            }

            this.setupPublisherButton(2131624209, var9, 2130837772);
            this.setupStatusOnClick();
            this.setupPublisherButton(2131624211, 2131362366, 2130837770);
            this.setupCheckInOnClick();
         } else {
            this.setupPublisherButton(2131624207, 2131362170, 2130837771);
            byte var8;
            if(var2.equals("user") && var3 != null) {
               var8 = 0;
            } else {
               var8 = 1;
            }

            this.setupPhotoOnClick((boolean)var8);
            this.setupPublisherButton(2131624209, 2131362172, 2130837772);
            this.setupStatusOnClick();
            FacewebChromeActivity.this.findViewById(2131624211).setVisibility(8);
            FacewebChromeActivity.this.findViewById(2131624210).setVisibility(8);
         }
      }

      protected void setupPublisherButton(int var1, int var2, int var3) {
         Button var4 = (Button)FacewebChromeActivity.this.findViewById(var1);
         Drawable var5 = FacewebChromeActivity.this.getResources().getDrawable(var3);
         String var6 = FacewebChromeActivity.this.getString(var2);
         var4.setText(var6);
         var4.setCompoundDrawablesWithIntrinsicBounds(var5, (Drawable)null, (Drawable)null, (Drawable)null);
      }

      protected void setupStatusOnClick() {
         View var1 = FacewebChromeActivity.this.findViewById(2131624209);
         FacewebChromeActivity.ShowPublisherHandler.2 var2 = new FacewebChromeActivity.ShowPublisherHandler.2();
         var1.setOnClickListener(var2);
      }

      class 3 implements OnClickListener {

         // $FF: synthetic field
         final boolean val$photoAndVideo;


         3(boolean var2) {
            this.val$photoAndVideo = var2;
         }

         public void onClick(View var1) {
            FacewebChromeActivity var2 = FacewebChromeActivity.this;
            int var3;
            if(this.val$photoAndVideo) {
               var3 = 255255255;
            } else {
               var3 = 255255256;
            }

            var2.showDialog(var3);
         }
      }

      class 4 implements OnClickListener {

         4() {}

         public void onClick(View var1) {
            Bundle var2 = new Bundle();
            var2.putBoolean("extra_is_checkin", (boolean)1);
            FacewebChromeActivity var3 = FacewebChromeActivity.this;
            Integer var4 = Integer.valueOf(10);
            var3.launchComposer((Uri)null, var2, var4);
         }
      }

      class 1 implements OnClickListener {

         1() {}

         public void onClick(View var1) {
            FacewebChromeActivity.this.showDialog(255255255);
         }
      }

      class 2 implements OnClickListener {

         2() {}

         public void onClick(View var1) {
            Bundle var2 = null;
            if(FacewebChromeActivity.this.mProfileId == 65535L) {
               var2 = new Bundle();
               var2.putInt("extra_composer_title", 2131362300);
            }

            FacewebChromeActivity var3 = FacewebChromeActivity.this;
            Integer var4 = Integer.valueOf(10);
            var3.launchComposer((Uri)null, var2, var4);
         }
      }
   }

   class 1 extends FacebookWebView.NativeUICallHandler {

      1(Handler var2) {
         super(var2);
      }

      public void handleUI(FacebookWebView var1, FacebookRpcCall var2) {
         if((LinearLayout)FacewebChromeActivity.this.findViewById(2131624041) != null) {
            if(FacewebChromeActivity.this.mPublisher != null) {
               FacewebChromeActivity.this.mPublisher.setVisibility(8);
            }
         }
      }
   }
}
