package com.facebook.katana.activity.places;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.facebook.katana.AlertDialogs;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.activity.BaseFacebookListActivity;
import com.facebook.katana.activity.places.AddPlaceActivity;
import com.facebook.katana.activity.places.PlacesNearbyAdapter;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.binding.NetworkRequestCallback;
import com.facebook.katana.features.places.PlacesNearby;
import com.facebook.katana.model.FacebookPlace;
import com.facebook.katana.model.GeoRegion;
import com.facebook.katana.service.method.FqlGetPlacesNearby;
import com.facebook.katana.ui.ExtendableListAdapter;
import com.facebook.katana.util.ApplicationUtils;
import com.facebook.katana.util.FBLocationManager;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.Toaster;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class PlacesNearbyActivity extends BaseFacebookListActivity implements TextWatcher {

   protected static final int LOCATION_SERVICES_DISABLED_DIALOG_ID = 1;
   private AppSession mAppSession;
   private AppSessionListener mAppSessionListener;
   private Location mCurrentLocation;
   private Location mDisplayedLocation;
   private boolean mHasMore;
   private GeoRegion.ImplicitLocation mImplicitLocation;
   private boolean mIsCheckin;
   private boolean mLaunchedForPlaceId;
   private ExtendableListAdapter<FacebookPlace> mListAdapter;
   private FBLocationManager.FBLocationListener mLocationListener;
   private LinkedList<String> mPendingRequestIds;
   private PlacesNearbyAdapter mPlacesNearbyAdapter;
   private Location mRequestLocation;
   private int mRequestResultLimit = 20;
   private String mRequestSearch = "";
   private FacebookPlace mTaggedPlace;


   public PlacesNearbyActivity() {
      LinkedList var1 = new LinkedList();
      this.mPendingRequestIds = var1;
      PlacesNearbyActivity.1 var2 = new PlacesNearbyActivity.1();
      this.mAppSessionListener = var2;
      PlacesNearbyActivity.2 var3 = new PlacesNearbyActivity.2();
      this.mLocationListener = var3;
   }

   private void fetchNearbyPlacesIfNeeded(Location var1, String var2, int var3) {
      if(var1 != null) {
         if(this.mRequestLocation != null) {
            Location var4 = this.mRequestLocation;
            if(var1.distanceTo(var4) < 20.0F) {
               String var7 = this.mRequestSearch;
               if(var2.equals(var7)) {
                  int var10 = this.mRequestResultLimit;
                  if(var3 == var10) {
                     if(this.mPlacesNearbyAdapter.getCount() != 0) {
                        return;
                     }

                     if(this.mPendingRequestIds.size() != 0) {
                        return;
                     }
                  }
               }
            }
         }

         double var13;
         if(StringUtils.isBlank(var2)) {
            var13 = 750.0D;
         } else {
            var13 = 2000.0D;
         }

         PlacesNearby.PlacesNearbyArgType var18 = new PlacesNearby.PlacesNearbyArgType(var1, var13, var2, var3);
         FqlGetPlacesNearby var21 = PlacesNearby.get(this, var18);
         if(var21 != null) {
            String var22 = var21.filter;
            if(var2.equals(var22)) {
               Location var25 = var21.location;
               if(var1.distanceTo(var25) < 20.0F) {
                  int var28 = var21.resultLimit;
                  if(var3 == var28) {
                     List var31 = var21.getPlaces();
                     this.updatePlacesNearbyList(var31);
                     if(this.mLaunchedForPlaceId) {
                        List var34 = var21.getRegions();
                        this.updateSelectedLocation(var34);
                     }

                     this.mDisplayedLocation = var1;
                     this.mRequestLocation = var1;
                     this.mRequestResultLimit = var3;
                     this.mRequestSearch = var2;
                     return;
                  }
               }
            }
         }

         this.mRequestLocation = var1;
         this.mRequestResultLimit = var3;
         this.mRequestSearch = var2;
         AppSession var44 = this.mAppSession;
         String var45 = this.mRequestSearch;
         int var46 = this.mRequestResultLimit;
         String var51 = var44.getPlacesNearby(this, var1, var13, var45, var46, (NetworkRequestCallback)null);
         this.mPendingRequestIds.add(var51);
         byte var54 = 1;
         this.setListLoading((boolean)var54);
      }
   }

   private void setupList() {
      PlacesNearbyActivity.4 var1 = new PlacesNearbyActivity.4();
      ArrayList var2 = new ArrayList();
      PlacesNearbyAdapter var3 = new PlacesNearbyAdapter(this, var2);
      this.mPlacesNearbyAdapter = var3;
      PlacesNearbyAdapter var4 = this.mPlacesNearbyAdapter;
      ExtendableListAdapter var5 = new ExtendableListAdapter(this, var4, var1);
      this.mListAdapter = var5;
      this.mListAdapter.setLoadMoreTextResId(2131362384);
      ExtendableListAdapter var6 = this.mListAdapter;
      this.setListAdapter(var6);
   }

   private void setupViews() {
      this.setContentView(2130903132);
      EditText var1 = (EditText)this.findViewById(2131624229);
      var1.setHint(2131362404);
      String var2 = this.mRequestSearch;
      var1.setText(var2);
      var1.addTextChangedListener(this);
      this.setListEmptyText(2131362394);
      this.setListLoadingText(2131361932);
   }

   private void updatePlacesNearbyList(List<FacebookPlace> var1) {
      if(var1 != null) {
         View var2 = this.getWindow().getCurrentFocus();
         int var3 = this.getListView().getFirstVisiblePosition();
         this.mPlacesNearbyAdapter.setList(var1);
         this.setListLoading((boolean)0);
         ListView var4 = this.getListView();
         int var5 = Math.min(this.getListAdapter().getCount() - 1, var3);
         var4.setSelection(var5);
         int var6 = var1.size();
         int var7 = this.mRequestResultLimit;
         byte var8;
         if(var6 == var7) {
            var8 = 1;
         } else {
            var8 = 0;
         }

         this.mHasMore = (boolean)var8;
         this.getListView().invalidate();
         if(var2 != null) {
            boolean var9 = var2.requestFocus();
         }
      }
   }

   private void updateSelectedLocation(List<GeoRegion> var1) {
      if(!this.mIsCheckin) {
         if(var1 != null) {
            GeoRegion.ImplicitLocation var2 = GeoRegion.createImplicitLocation(var1);
            this.mImplicitLocation = var2;
         }

         String var3 = null;
         if(this.mTaggedPlace != null) {
            Object[] var4 = new Object[1];
            String var5 = this.mTaggedPlace.mName;
            var4[0] = var5;
            var3 = this.getString(2131361933, var4);
         } else if(this.mImplicitLocation != null) {
            var3 = this.mImplicitLocation.label;
         }

         if(var3 != null) {
            LinearLayout var6 = (LinearLayout)this.findViewById(2131624178);
            var6.setVisibility(0);
            var6.bringToFront();
            ((TextView)this.findViewById(2131624179)).setText(var3);
         }
      }
   }

   public void afterTextChanged(Editable var1) {
      String var2;
      if(var1 == null) {
         var2 = "";
      } else {
         var2 = var1.toString();
      }

      if(StringUtils.isBlank(var2)) {
         this.mPlacesNearbyAdapter.setAddPlaceVisibility((boolean)0);
      } else {
         this.mPlacesNearbyAdapter.setAddPlaceVisibility((boolean)1);
         PlacesNearbyAdapter var4 = this.mPlacesNearbyAdapter;
         Object[] var5 = new Object[1];
         String var6 = var2.trim();
         var5[0] = var6;
         String var7 = this.getString(2131362360, var5);
         var4.setAddPlaceString(var7);
      }

      Location var3 = this.mCurrentLocation;
      this.fetchNearbyPlacesIfNeeded(var3, var2, 20);
   }

   public void beforeTextChanged(CharSequence var1, int var2, int var3, int var4) {}

   public void fetchMorePlaces() {
      Location var1 = this.mCurrentLocation;
      String var2 = this.mRequestSearch;
      int var3 = this.mRequestResultLimit + 20;
      this.fetchNearbyPlacesIfNeeded(var1, var2, var3);
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      AppSession var2 = AppSession.getActiveSession(this, (boolean)1);
      this.mAppSession = var2;
      if(this.mAppSession == null) {
         LoginActivity.toLogin(this);
      } else {
         this.setupViews();
         boolean var3 = this.getIntent().getBooleanExtra("launched_for_place", (boolean)0);
         this.mLaunchedForPlaceId = var3;
         boolean var4 = this.getIntent().getBooleanExtra("extra_is_checkin", (boolean)0);
         this.mIsCheckin = var4;
         if(this.mLaunchedForPlaceId) {
            String var5 = this.getString(2131361867);
            this.setPrimaryActionFace(-1, var5);
            Button var6 = (Button)this.findViewById(2131623997);
            PlacesNearbyActivity.3 var7 = new PlacesNearbyActivity.3();
            var6.setOnClickListener(var7);
            FacebookPlace var8 = (FacebookPlace)this.getIntent().getParcelableExtra("extra_place");
            this.mTaggedPlace = var8;
            this.getWindow().setSoftInputMode(2);
         } else {
            this.getWindow().setSoftInputMode(4);
         }
      }
   }

   protected Dialog onCreateDialog(int var1) {
      AlertDialog var2;
      switch(var1) {
      case 1:
         PlacesNearbyActivity.5 var3 = new PlacesNearbyActivity.5();
         PlacesNearbyActivity.6 var4 = new PlacesNearbyActivity.6();
         String var5 = this.getString(2131362381);
         String var6 = this.getString(2131362382);
         String var7 = this.getString(2131362405);
         String var8 = this.getString(2131362214);
         var2 = AlertDialogs.createAlert(this, var5, 17301659, var6, var7, var3, var8, var4, (OnCancelListener)null, (boolean)1);
         break;
      default:
         var2 = null;
      }

      return var2;
   }

   protected void onListItemClick(ListView var1, View var2, int var3, long var4) {
      if(var4 != 65534L) {
         if(var4 == 65535L) {
            TextView var6 = (TextView)this.findViewById(2131624229);
            Intent var7 = new Intent(this, AddPlaceActivity.class);
            CharSequence var8 = var6.getText();
            var7.putExtra("android.intent.extra.SUBJECT", var8);
            this.startActivity(var7);
         } else {
            FacebookPlace var10 = (FacebookPlace)this.mListAdapter.getItem(var3);
            if(this.mLaunchedForPlaceId) {
               Intent var11 = new Intent();
               var11.putExtra("extra_place", var10);
               if(this.mDisplayedLocation != null) {
                  Location var13 = this.mDisplayedLocation;
                  var11.putExtra("extra_nearby_location", var13);
               }

               if(this.mImplicitLocation != null) {
                  GeoRegion.ImplicitLocation var15 = this.mImplicitLocation;
                  var11.putExtra("extra_implicit_location", var15);
               }

               this.setResult(-1, var11);
               this.finish();
            } else if(var10 != null) {
               ApplicationUtils.OpenPlaceProfile(this, var10);
            }
         }
      }
   }

   public void onLocationX(View var1) {
      Intent var2 = new Intent();
      Intent var3 = var2.putExtra("extra_xed_location", (boolean)1);
      this.setResult(-1, var2);
      this.finish();
   }

   protected void onPause() {
      super.onPause();
      AppSession var1 = this.mAppSession;
      AppSessionListener var2 = this.mAppSessionListener;
      var1.removeListener(var2);
      FBLocationManager.removeLocationListener(this.mLocationListener);
   }

   protected void onResume() {
      super.onResume();
      AppSession var1 = AppSession.getActiveSession(this, (boolean)1);
      this.mAppSession = var1;
      if(this.mAppSession == null) {
         LoginActivity.toLogin(this);
      } else {
         this.getWindow().setSoftInputMode(3);
         if(this.getListAdapter() == null) {
            this.setupList();
         }

         AppSession var2 = this.mAppSession;
         AppSessionListener var3 = this.mAppSessionListener;
         var2.addListener(var3);
         if(FBLocationManager.areLocationServicesEnabled(this)) {
            FBLocationManager.FBLocationListener var4 = this.mLocationListener;
            FBLocationManager.addLocationListener(this, var4);
         } else {
            this.showDialog(1);
         }

         this.updateSelectedLocation((List)null);
      }
   }

   public void onTextChanged(CharSequence var1, int var2, int var3, int var4) {}

   protected void setListLoading(boolean var1) {
      super.setListLoading(var1);
      View var2 = this.findViewById(2131624177);
      byte var3;
      if(this.mPendingRequestIds.size() == 0) {
         var3 = 8;
      } else {
         var3 = 0;
      }

      var2.setVisibility(var3);
   }

   class 6 implements OnClickListener {

      6() {}

      public void onClick(DialogInterface var1, int var2) {
         PlacesNearbyActivity.this.finish();
      }
   }

   public static final class Extras {

      public static final String IS_CHECKIN = "extra_is_checkin";
      public static final String NEARBY_LOC = "extra_nearby_location";


      public Extras() {}
   }

   class 5 implements OnClickListener {

      5() {}

      public void onClick(DialogInterface var1, int var2) {
         PlacesNearbyActivity var3 = PlacesNearbyActivity.this;
         Intent var4 = new Intent("android.settings.LOCATION_SOURCE_SETTINGS");
         var3.startActivity(var4);
      }
   }

   class 4 implements ExtendableListAdapter.LoadMoreCallback {

      4() {}

      public boolean hasMore() {
         return PlacesNearbyActivity.this.mHasMore;
      }

      public void loadMore() {
         PlacesNearbyActivity.this.fetchMorePlaces();
      }
   }

   class 3 implements android.view.View.OnClickListener {

      3() {}

      public void onClick(View var1) {
         Intent var2 = new Intent();
         if(PlacesNearbyActivity.this.mTaggedPlace != null) {
            FacebookPlace var3 = PlacesNearbyActivity.this.mTaggedPlace;
            var2.putExtra("extra_place", var3);
         } else if(PlacesNearbyActivity.this.mImplicitLocation != null) {
            GeoRegion.ImplicitLocation var5 = PlacesNearbyActivity.this.mImplicitLocation;
            var2.putExtra("extra_implicit_location", var5);
         }

         PlacesNearbyActivity.this.setResult(-1, var2);
         PlacesNearbyActivity.this.finish();
      }
   }

   class 2 implements FBLocationManager.FBLocationListener {

      2() {}

      public void onLocationChanged(Location var1) {
         PlacesNearbyActivity.this.mCurrentLocation = var1;
         PlacesNearbyActivity var3 = PlacesNearbyActivity.this;
         String var4 = PlacesNearbyActivity.this.mRequestSearch;
         int var5 = PlacesNearbyActivity.this.mRequestResultLimit;
         var3.fetchNearbyPlacesIfNeeded(var1, var4, var5);
      }

      public void onTimeOut() {
         Toaster.toast(PlacesNearbyActivity.this, 2131362425);
         PlacesNearbyActivity.this.setListLoading((boolean)0);
      }
   }

   class 1 extends AppSessionListener {

      1() {}

      public void onGetPlacesNearbyComplete(AppSession var1, String var2, int var3, String var4, Exception var5, List<FacebookPlace> var6, List<GeoRegion> var7, Location var8) {
         LinkedList var9 = PlacesNearbyActivity.this.mPendingRequestIds;
         int var10 = PlacesNearbyActivity.this.mPendingRequestIds.size();
         ListIterator var11 = var9.listIterator(var10);

         while(var11.hasPrevious()) {
            String var12 = (String)var11.previous();
            if(var2.equals(var12)) {
               var11.remove();

               while(var11.hasPrevious()) {
                  Object var13 = var11.previous();
                  var11.remove();
               }

               if(var3 == 200) {
                  if(PlacesNearbyActivity.this.mDisplayedLocation != null) {
                     float var14 = PlacesNearbyActivity.this.mCurrentLocation.distanceTo(var8);
                     Location var15 = PlacesNearbyActivity.this.mCurrentLocation;
                     Location var16 = PlacesNearbyActivity.this.mDisplayedLocation;
                     float var17 = var15.distanceTo(var16);
                     if(var14 > var17) {
                        break;
                     }
                  }

                  PlacesNearbyActivity.this.updatePlacesNearbyList(var6);
                  if(PlacesNearbyActivity.this.mLaunchedForPlaceId) {
                     PlacesNearbyActivity.this.updateSelectedLocation(var7);
                  }

                  PlacesNearbyActivity.this.mDisplayedLocation = var8;
               }
               break;
            }
         }

         if(PlacesNearbyActivity.this.mPendingRequestIds.size() == 0) {
            PlacesNearbyActivity.this.findViewById(2131624177).setVisibility(8);
         }
      }
   }
}
