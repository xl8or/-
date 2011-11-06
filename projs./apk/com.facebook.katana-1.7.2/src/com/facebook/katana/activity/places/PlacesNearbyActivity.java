// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PlacesNearbyActivity.java

package com.facebook.katana.activity.places;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.*;
import com.facebook.katana.AlertDialogs;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.activity.BaseFacebookListActivity;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.features.places.PlacesNearby;
import com.facebook.katana.model.FacebookPlace;
import com.facebook.katana.model.GeoRegion;
import com.facebook.katana.service.method.FqlGetPlacesNearby;
import com.facebook.katana.ui.ExtendableListAdapter;
import com.facebook.katana.util.*;
import java.util.*;

// Referenced classes of package com.facebook.katana.activity.places:
//            PlacesNearbyAdapter, AddPlaceActivity

public class PlacesNearbyActivity extends BaseFacebookListActivity
    implements TextWatcher
{
    public static final class Extras
    {

        public static final String IS_CHECKIN = "extra_is_checkin";
        public static final String NEARBY_LOC = "extra_nearby_location";

        public Extras()
        {
        }
    }


    public PlacesNearbyActivity()
    {
        mRequestSearch = "";
        mRequestResultLimit = 20;
        mPendingRequestIds = new LinkedList();
        mAppSessionListener = new AppSessionListener() {

            public void onGetPlacesNearbyComplete(AppSession appsession, String s, int i, String s1, Exception exception, List list, List list1, 
                    Location location)
            {
                ListIterator listiterator = mPendingRequestIds.listIterator(mPendingRequestIds.size());
                do
                {
                    if(!listiterator.hasPrevious())
                        break;
                    if(!s.equals((String)listiterator.previous()))
                        continue;
                    listiterator.remove();
                    for(; listiterator.hasPrevious(); listiterator.remove())
                        listiterator.previous();

                    if(i == 200 && (mDisplayedLocation == null || mCurrentLocation.distanceTo(location) <= mCurrentLocation.distanceTo(mDisplayedLocation)))
                    {
                        updatePlacesNearbyList(list);
                        if(mLaunchedForPlaceId)
                            updateSelectedLocation(list1);
                        mDisplayedLocation = location;
                    }
                    break;
                } while(true);
                if(mPendingRequestIds.size() == 0)
                    findViewById(0x7f0e00f1).setVisibility(8);
            }

            final PlacesNearbyActivity this$0;

            
            {
                this$0 = PlacesNearbyActivity.this;
                super();
            }
        }
;
        mLocationListener = new com.facebook.katana.util.FBLocationManager.FBLocationListener() {

            public void onLocationChanged(Location location)
            {
                mCurrentLocation = location;
                fetchNearbyPlacesIfNeeded(location, mRequestSearch, mRequestResultLimit);
            }

            public void onTimeOut()
            {
                Toaster.toast(PlacesNearbyActivity.this, 0x7f0a0279);
                setListLoading(false);
            }

            final PlacesNearbyActivity this$0;

            
            {
                this$0 = PlacesNearbyActivity.this;
                super();
            }
        }
;
    }

    private void fetchNearbyPlacesIfNeeded(Location location, String s, int i)
    {
        if(location != null && (mRequestLocation == null || location.distanceTo(mRequestLocation) >= 20F || !s.equals(mRequestSearch) || i != mRequestResultLimit || mPlacesNearbyAdapter.getCount() == 0 && mPendingRequestIds.size() == 0))
        {
            double d;
            FqlGetPlacesNearby fqlgetplacesnearby;
            if(StringUtils.isBlank(s))
                d = 750D;
            else
                d = 2000D;
            fqlgetplacesnearby = PlacesNearby.get(this, new com.facebook.katana.features.places.PlacesNearby.PlacesNearbyArgType(location, d, s, i));
            if(fqlgetplacesnearby != null && s.equals(fqlgetplacesnearby.filter) && location.distanceTo(fqlgetplacesnearby.location) < 20F && i == fqlgetplacesnearby.resultLimit)
            {
                updatePlacesNearbyList(fqlgetplacesnearby.getPlaces());
                if(mLaunchedForPlaceId)
                    updateSelectedLocation(fqlgetplacesnearby.getRegions());
                mDisplayedLocation = location;
                mRequestLocation = location;
                mRequestResultLimit = i;
                mRequestSearch = s;
            } else
            {
                mRequestLocation = location;
                mRequestResultLimit = i;
                mRequestSearch = s;
                AppSession appsession = mAppSession;
                String s1 = mRequestSearch;
                int j = mRequestResultLimit;
                String s2 = appsession.getPlacesNearby(this, location, d, s1, j, null);
                mPendingRequestIds.add(s2);
                setListLoading(true);
            }
        }
    }

    private void setupList()
    {
        com.facebook.katana.ui.ExtendableListAdapter.LoadMoreCallback loadmorecallback = new com.facebook.katana.ui.ExtendableListAdapter.LoadMoreCallback() {

            public boolean hasMore()
            {
                return mHasMore;
            }

            public void loadMore()
            {
                fetchMorePlaces();
            }

            final PlacesNearbyActivity this$0;

            
            {
                this$0 = PlacesNearbyActivity.this;
                super();
            }
        }
;
        mPlacesNearbyAdapter = new PlacesNearbyAdapter(this, new ArrayList());
        mListAdapter = new ExtendableListAdapter(this, mPlacesNearbyAdapter, loadmorecallback);
        mListAdapter.setLoadMoreTextResId(0x7f0a0250);
        setListAdapter(mListAdapter);
    }

    private void setupViews()
    {
        setContentView(0x7f03005c);
        EditText edittext = (EditText)findViewById(0x7f0e0125);
        edittext.setHint(0x7f0a0264);
        edittext.setText(mRequestSearch);
        edittext.addTextChangedListener(this);
        setListEmptyText(0x7f0a025a);
        setListLoadingText(0x7f0a008c);
    }

    private void updatePlacesNearbyList(List list)
    {
        if(list != null) goto _L2; else goto _L1
_L1:
        return;
_L2:
        View view = getWindow().getCurrentFocus();
        int i = getListView().getFirstVisiblePosition();
        mPlacesNearbyAdapter.setList(list);
        setListLoading(false);
        getListView().setSelection(Math.min(getListAdapter().getCount() - 1, i));
        boolean flag;
        if(list.size() == mRequestResultLimit)
            flag = true;
        else
            flag = false;
        mHasMore = flag;
        getListView().invalidate();
        if(view != null)
            view.requestFocus();
        if(true) goto _L1; else goto _L3
_L3:
    }

    private void updateSelectedLocation(List list)
    {
        if(!mIsCheckin) goto _L2; else goto _L1
_L1:
        return;
_L2:
        String s;
        if(list != null)
            mImplicitLocation = GeoRegion.createImplicitLocation(list);
        s = null;
        if(mTaggedPlace == null)
            break; /* Loop/switch isn't completed */
        Object aobj[] = new Object[1];
        aobj[0] = mTaggedPlace.mName;
        s = getString(0x7f0a008d, aobj);
_L5:
        if(s != null)
        {
            LinearLayout linearlayout = (LinearLayout)findViewById(0x7f0e00f2);
            linearlayout.setVisibility(0);
            linearlayout.bringToFront();
            ((TextView)findViewById(0x7f0e00f3)).setText(s);
        }
        if(true) goto _L1; else goto _L3
_L3:
        if(mImplicitLocation == null) goto _L5; else goto _L4
_L4:
        s = mImplicitLocation.label;
          goto _L5
    }

    public void afterTextChanged(Editable editable)
    {
        String s;
        if(editable == null)
            s = "";
        else
            s = editable.toString();
        if(StringUtils.isBlank(s))
        {
            mPlacesNearbyAdapter.setAddPlaceVisibility(false);
        } else
        {
            mPlacesNearbyAdapter.setAddPlaceVisibility(true);
            PlacesNearbyAdapter placesnearbyadapter = mPlacesNearbyAdapter;
            Object aobj[] = new Object[1];
            aobj[0] = s.trim();
            placesnearbyadapter.setAddPlaceString(getString(0x7f0a0238, aobj));
        }
        fetchNearbyPlacesIfNeeded(mCurrentLocation, s, 20);
    }

    public void beforeTextChanged(CharSequence charsequence, int i, int j, int k)
    {
    }

    public void fetchMorePlaces()
    {
        fetchNearbyPlacesIfNeeded(mCurrentLocation, mRequestSearch, 20 + mRequestResultLimit);
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        mAppSession = AppSession.getActiveSession(this, true);
        if(mAppSession == null)
        {
            LoginActivity.toLogin(this);
        } else
        {
            setupViews();
            mLaunchedForPlaceId = getIntent().getBooleanExtra("launched_for_place", false);
            mIsCheckin = getIntent().getBooleanExtra("extra_is_checkin", false);
            if(mLaunchedForPlaceId)
            {
                setPrimaryActionFace(-1, getString(0x7f0a004b));
                ((Button)findViewById(0x7f0e003d)).setOnClickListener(new android.view.View.OnClickListener() {

                    public void onClick(View view)
                    {
                        Intent intent = new Intent();
                        if(mTaggedPlace == null) goto _L2; else goto _L1
_L1:
                        intent.putExtra("extra_place", mTaggedPlace);
_L4:
                        setResult(-1, intent);
                        finish();
                        return;
_L2:
                        if(mImplicitLocation != null)
                            intent.putExtra("extra_implicit_location", mImplicitLocation);
                        if(true) goto _L4; else goto _L3
_L3:
                    }

                    final PlacesNearbyActivity this$0;

            
            {
                this$0 = PlacesNearbyActivity.this;
                super();
            }
                }
);
                mTaggedPlace = (FacebookPlace)getIntent().getParcelableExtra("extra_place");
                getWindow().setSoftInputMode(2);
            } else
            {
                getWindow().setSoftInputMode(4);
            }
        }
    }

    protected Dialog onCreateDialog(int i)
    {
        i;
        JVM INSTR tableswitch 1 1: default 20
    //                   1 26;
           goto _L1 _L2
_L1:
        Object obj = null;
_L4:
        return ((Dialog) (obj));
_L2:
        android.content.DialogInterface.OnClickListener onclicklistener = new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int j)
            {
                startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
            }

            final PlacesNearbyActivity this$0;

            
            {
                this$0 = PlacesNearbyActivity.this;
                super();
            }
        }
;
        android.content.DialogInterface.OnClickListener onclicklistener1 = new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int j)
            {
                finish();
            }

            final PlacesNearbyActivity this$0;

            
            {
                this$0 = PlacesNearbyActivity.this;
                super();
            }
        }
;
        obj = AlertDialogs.createAlert(this, getString(0x7f0a024d), 0x108009b, getString(0x7f0a024e), getString(0x7f0a0265), onclicklistener, getString(0x7f0a01a6), onclicklistener1, null, true);
        if(true) goto _L4; else goto _L3
_L3:
    }

    protected void onListItemClick(ListView listview, View view, int i, long l)
    {
        if(l != -2L) goto _L2; else goto _L1
_L1:
        return;
_L2:
        if(l == -1L)
        {
            TextView textview = (TextView)findViewById(0x7f0e0125);
            Intent intent1 = new Intent(this, com/facebook/katana/activity/places/AddPlaceActivity);
            intent1.putExtra("android.intent.extra.SUBJECT", textview.getText());
            startActivity(intent1);
        } else
        {
            FacebookPlace facebookplace = (FacebookPlace)mListAdapter.getItem(i);
            if(mLaunchedForPlaceId)
            {
                Intent intent = new Intent();
                intent.putExtra("extra_place", facebookplace);
                if(mDisplayedLocation != null)
                    intent.putExtra("extra_nearby_location", mDisplayedLocation);
                if(mImplicitLocation != null)
                    intent.putExtra("extra_implicit_location", mImplicitLocation);
                setResult(-1, intent);
                finish();
            } else
            if(facebookplace != null)
                ApplicationUtils.OpenPlaceProfile(this, facebookplace);
        }
        if(true) goto _L1; else goto _L3
_L3:
    }

    public void onLocationX(View view)
    {
        Intent intent = new Intent();
        intent.putExtra("extra_xed_location", true);
        setResult(-1, intent);
        finish();
    }

    protected void onPause()
    {
        super.onPause();
        mAppSession.removeListener(mAppSessionListener);
        FBLocationManager.removeLocationListener(mLocationListener);
    }

    protected void onResume()
    {
        super.onResume();
        mAppSession = AppSession.getActiveSession(this, true);
        if(mAppSession == null)
        {
            LoginActivity.toLogin(this);
        } else
        {
            getWindow().setSoftInputMode(3);
            if(getListAdapter() == null)
                setupList();
            mAppSession.addListener(mAppSessionListener);
            if(FBLocationManager.areLocationServicesEnabled(this))
                FBLocationManager.addLocationListener(this, mLocationListener);
            else
                showDialog(1);
            updateSelectedLocation(null);
        }
    }

    public void onTextChanged(CharSequence charsequence, int i, int j, int k)
    {
    }

    protected void setListLoading(boolean flag)
    {
        super.setListLoading(flag);
        View view = findViewById(0x7f0e00f1);
        byte byte0;
        if(mPendingRequestIds.size() == 0)
            byte0 = 8;
        else
            byte0 = 0;
        view.setVisibility(byte0);
    }

    protected static final int LOCATION_SERVICES_DISABLED_DIALOG_ID = 1;
    private AppSession mAppSession;
    private AppSessionListener mAppSessionListener;
    private Location mCurrentLocation;
    private Location mDisplayedLocation;
    private boolean mHasMore;
    private com.facebook.katana.model.GeoRegion.ImplicitLocation mImplicitLocation;
    private boolean mIsCheckin;
    private boolean mLaunchedForPlaceId;
    private ExtendableListAdapter mListAdapter;
    private com.facebook.katana.util.FBLocationManager.FBLocationListener mLocationListener;
    private LinkedList mPendingRequestIds;
    private PlacesNearbyAdapter mPlacesNearbyAdapter;
    private Location mRequestLocation;
    private int mRequestResultLimit;
    private String mRequestSearch;
    private FacebookPlace mTaggedPlace;





/*
    static Location access$102(PlacesNearbyActivity placesnearbyactivity, Location location)
    {
        placesnearbyactivity.mDisplayedLocation = location;
        return location;
    }

*/




/*
    static Location access$202(PlacesNearbyActivity placesnearbyactivity, Location location)
    {
        placesnearbyactivity.mCurrentLocation = location;
        return location;
    }

*/







}
