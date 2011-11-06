// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AddPlaceActivity.java

package com.facebook.katana.activity.places;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.activity.BaseFacebookActivity;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.provider.UserValuesManager;
import com.facebook.katana.service.method.PlacesCreate;
import com.facebook.katana.service.method.PlacesCreateException;
import com.facebook.katana.util.ApplicationUtils;
import com.facebook.katana.util.FBLocationManager;
import com.facebook.katana.util.LocationUtils;
import com.facebook.katana.util.Toaster;
import java.util.ArrayList;
import java.util.List;

public class AddPlaceActivity extends BaseFacebookActivity
{
    static final class State extends Enum
    {

        public static State valueOf(String s)
        {
            return (State)Enum.valueOf(com/facebook/katana/activity/places/AddPlaceActivity$State, s);
        }

        public static State[] values()
        {
            return (State[])$VALUES.clone();
        }

        private static final State $VALUES[];
        public static final State NONE;
        public static final State PROCESSING;

        static 
        {
            NONE = new State("NONE", 0);
            PROCESSING = new State("PROCESSING", 1);
            State astate[] = new State[2];
            astate[0] = NONE;
            astate[1] = PROCESSING;
            $VALUES = astate;
        }

        private State(String s, int i)
        {
            super(s, i);
        }
    }


    public AddPlaceActivity()
    {
        mAppSessionListener = new AppSessionListener() {

            public void onDownloadPhotoRawComplete(AppSession appsession, String s, int i, String s1, Exception exception, Bitmap bitmap)
            {
                if(i == 200)
                    updateMap(null, bitmap);
            }

            public void onPlacesCreateComplete(AppSession appsession, String s, int i, String s1, Exception exception, final long placeId)
            {
                boolean flag;
                String s2;
                mState = State.NONE;
                mProgressDlg.dismiss();
                flag = false;
                s2 = ((EditText)findViewById(0x7f0e00ed)).getText().toString().trim();
                if(i != 200) goto _L2; else goto _L1
_L1:
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(AddPlaceActivity.this);
                builder.setCancelable(false);
                builder.setMessage(0x7f0a0256);
                android.content.DialogInterface.OnClickListener onclicklistener = new android.content.DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialoginterface, int j)
                    {
                        ApplicationUtils.OpenPlaceProfile(_fld0, placeId);
                    }

                    final _cls1 this$1;
                    final long val$placeId;

                    
                    {
                        this$1 = _cls1.this;
                        placeId = l;
                        super();
                    }
                }
;
                builder.setPositiveButton(0x7f0a0271, onclicklistener);
                builder.show();
_L4:
                return;
_L2:
                if(exception instanceof PlacesCreateException)
                {
                    PlacesCreateException placescreateexception = (PlacesCreateException)exception;
                    if(placescreateexception.getErrorCode() == 2406 && placescreateexception.getSimilarPlaces().size() > 0)
                    {
                        flag = true;
                        final com.facebook.katana.service.method.PlacesCreateException.SimilarPlace similar = (com.facebook.katana.service.method.PlacesCreateException.SimilarPlace)placescreateexception.getSimilarPlaces().get(0);
                        android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(AddPlaceActivity.this);
                        builder1.setCancelable(false);
                        builder1.setNegativeButton(0x7f0a0271, new android.content.DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialoginterface, int j)
                            {
                                ApplicationUtils.OpenPlaceProfile(_fld0, similar.mId);
                            }

                            final _cls1 this$1;
                            final com.facebook.katana.service.method.PlacesCreateException.SimilarPlace val$similar;

                    
                    {
                        this$1 = _cls1.this;
                        similar = similarplace;
                        super();
                    }
                        }
);
                        Object aobj[];
                        if(s2.compareToIgnoreCase(similar.mName) == 0)
                        {
                            AddPlaceActivity addplaceactivity1 = AddPlaceActivity.this;
                            Object aobj2[] = new Object[1];
                            aobj2[0] = similar.mName;
                            builder1.setMessage(addplaceactivity1.getString(0x7f0a0258, aobj2));
                        } else
                        {
                            AddPlaceActivity addplaceactivity = AddPlaceActivity.this;
                            Object aobj1[] = new Object[3];
                            aobj1[0] = similar.mName;
                            aobj1[1] = s2;
                            aobj1[2] = similar.mName;
                            builder1.setMessage(addplaceactivity.getString(0x7f0a0259, aobj1));
                            builder1.setNeutralButton(0x7f0a0021, new android.content.DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialoginterface, int j)
                                {
                                    mOverrideIds.add(Long.valueOf(similar.mId));
                                    createPlace();
                                    dialoginterface.dismiss();
                                }

                                final _cls1 this$1;
                                final com.facebook.katana.service.method.PlacesCreateException.SimilarPlace val$similar;

                    
                    {
                        this$1 = _cls1.this;
                        similar = similarplace;
                        super();
                    }
                            }
);
                        }
                        builder1.show();
                    }
                }
                if(!flag)
                {
                    aobj = new Object[1];
                    aobj[0] = exception.getMessage();
                    com.facebook.katana.util.Log.e("AddPlaceActivity", String.format("Exception when adding place: %s", aobj));
                    Toaster.toast(AddPlaceActivity.this, 0x7f0a0257);
                }
                if(true) goto _L4; else goto _L3
_L3:
            }

            final AddPlaceActivity this$0;

            
            {
                this$0 = AddPlaceActivity.this;
                super();
            }
        }
;
        mLocationListener = new com.facebook.katana.util.FBLocationManager.FBLocationListener() {

            public void onLocationChanged(Location location)
            {
                mLocation = location;
                if(mLocation != null)
                    findViewById(0x7f0e00ef).setEnabled(true);
                float f;
                if(mMapLocation != null)
                    f = mMapLocation.distanceTo(location);
                else
                    f = 0F;
                if(mMapLocation == null || f >= 10F)
                    updateMap(location, null);
            }

            public void onTimeOut()
            {
            }

            final AddPlaceActivity this$0;

            
            {
                this$0 = AddPlaceActivity.this;
                super();
            }
        }
;
    }

    private void createPlace()
    {
        if(!$assertionsDisabled && mLocation == null)
            throw new AssertionError();
        if(FBLocationManager.areLocationServicesEnabled(this)) goto _L2; else goto _L1
_L1:
        Toaster.toast(this, 0x7f0a0257);
_L4:
        return;
_L2:
        if(mState != State.PROCESSING)
        {
            EditText edittext = (EditText)findViewById(0x7f0e00ed);
            EditText edittext1 = (EditText)findViewById(0x7f0e00ee);
            String s = edittext.getText().toString().trim();
            String s1 = edittext1.getText().toString().trim();
            if(s.length() != 0)
            {
                mProgressDlg = ProgressDialog.show(this, "", getString(0x7f0a0023), true);
                mState = State.PROCESSING;
                PlacesCreate.placesCreate(this, s, s1, mLocation, mOverrideIds);
            }
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    private void updateMap(Location location, Bitmap bitmap)
    {
        ImageView imageview = (ImageView)findViewById(0x7f0e00ec);
        if(bitmap == null) goto _L2; else goto _L1
_L1:
        imageview.setImageBitmap(bitmap);
_L4:
        return;
_L2:
        if(location != null)
        {
            float f = getResources().getDisplayMetrics().density;
            int i = getWindowManager().getDefaultDisplay().getWidth() - (int)(20F * f);
            int j = (int)(120F * f);
            String s = LocationUtils.generateGoogleMapsURL(location.getLatitude(), location.getLongitude(), 15, j, i);
            mAppSession.downloadPhotoRaw(this, s);
            mMapLocation = location;
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    public void onConfigurationChanged(Configuration configuration)
    {
        super.onConfigurationChanged(configuration);
        updateMap(mLocation, null);
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f03005a);
        Intent intent = getIntent();
        if(intent.hasExtra("android.intent.extra.SUBJECT"))
        {
            CharSequence charsequence = intent.getCharSequenceExtra("android.intent.extra.SUBJECT");
            ((TextView)findViewById(0x7f0e00ed)).setText(charsequence);
            Object aobj[] = new Object[1];
            aobj[0] = charsequence;
            Log.d("AddPlaceActivity", String.format("%s", aobj));
        }
        mOverrideIds = new ArrayList();
        ((Button)findViewById(0x7f0e00ef)).setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                createPlace();
            }

            final AddPlaceActivity this$0;

            
            {
                this$0 = AddPlaceActivity.this;
                super();
            }
        }
);
        if(!UserValuesManager.getBooleanValue(this, "places:has_created_place_before"))
        {
            UserValuesManager.setValue(this, "places:has_created_place_before", "true");
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
            builder.setMessage(getString(0x7f0a0255));
            builder.setPositiveButton(0x7f0a00dd, null);
            builder.show();
        }
    }

    protected void onPause()
    {
        super.onPause();
        mAppSession.removeListener(mAppSessionListener);
        FBLocationManager.removeLocationListener(mLocationListener);
    }

    public void onResume()
    {
        super.onResume();
        mAppSession = AppSession.getActiveSession(this, true);
        if(mAppSession == null)
        {
            LoginActivity.toLogin(this);
        } else
        {
            mAppSession.addListener(mAppSessionListener);
            FBLocationManager.addLocationListener(this, mLocationListener);
        }
    }

    static final boolean $assertionsDisabled = false;
    private static final int MAP_HEIGHT = 120;
    private static final int MAP_ZOOM = 15;
    private static final String TAG = "AddPlaceActivity";
    private AppSession mAppSession;
    private AppSessionListener mAppSessionListener;
    private Location mLocation;
    private com.facebook.katana.util.FBLocationManager.FBLocationListener mLocationListener;
    private Location mMapLocation;
    private List mOverrideIds;
    private ProgressDialog mProgressDlg;
    private State mState;

    static 
    {
        boolean flag;
        if(!com/facebook/katana/activity/places/AddPlaceActivity.desiredAssertionStatus())
            flag = true;
        else
            flag = false;
        $assertionsDisabled = flag;
    }


/*
    static State access$002(AddPlaceActivity addplaceactivity, State state)
    {
        addplaceactivity.mState = state;
        return state;
    }

*/







/*
    static Location access$502(AddPlaceActivity addplaceactivity, Location location)
    {
        addplaceactivity.mLocation = location;
        return location;
    }

*/

}
