// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ComposerActivity.java

package com.facebook.katana;

import android.app.Dialog;
import android.content.*;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.*;
import android.text.method.ArrowKeyMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.*;
import android.view.animation.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.facebook.katana.activity.BaseFacebookActivity;
import com.facebook.katana.activity.media.MediaUploader;
import com.facebook.katana.activity.media.PhotoUploader;
import com.facebook.katana.activity.places.PlacesNearbyActivity;
import com.facebook.katana.activity.profilelist.FriendMultiSelectorActivity;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.binding.NetworkRequestCallback;
import com.facebook.katana.binding.ProfileImage;
import com.facebook.katana.binding.ProfileImagesCache;
import com.facebook.katana.features.composer.AudienceAdapter;
import com.facebook.katana.features.composer.AudienceOption;
import com.facebook.katana.features.composer.ComposerUserSettings;
import com.facebook.katana.features.composer.MinorStatus;
import com.facebook.katana.features.places.PlacesNearby;
import com.facebook.katana.model.FacebookAlbum;
import com.facebook.katana.model.FacebookGroup;
import com.facebook.katana.model.FacebookPhotoWithTag;
import com.facebook.katana.model.FacebookPlace;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.model.FacebookSessionInfo;
import com.facebook.katana.model.FriendList;
import com.facebook.katana.model.GeoRegion;
import com.facebook.katana.model.PrivacySetting;
import com.facebook.katana.provider.ConnectionsProvider;
import com.facebook.katana.service.UploadManager;
import com.facebook.katana.service.method.AudienceSettings;
import com.facebook.katana.service.method.FqlGetNearbyRegions;
import com.facebook.katana.service.method.FqlGetPlacesNearby;
import com.facebook.katana.ui.ComposerEditText;
import com.facebook.katana.ui.SectionedListView;
import com.facebook.katana.util.FBLocationManager;
import com.facebook.katana.util.FileUtils;
import com.facebook.katana.util.ImageUtils;
import com.facebook.katana.util.IntentUtils;
import com.facebook.katana.util.Log;
import com.facebook.katana.util.StringUtils;
import custom.android.Gallery;
import java.io.*;
import java.util.*;
import org.json.JSONException;
import org.json.JSONObject;

// Referenced classes of package com.facebook.katana:
//            LoginActivity, AlertDialogs, IntentUriHandler

public class ComposerActivity extends BaseFacebookActivity
{
    private class PhotoThumbnailer extends Thread
    {

        public void run()
        {
            int i = getResources().getDimensionPixelSize(0x7f090000);
            final File copyFile;
            IOException ioexception;
            final Bitmap bm;
            BufferedInputStream bufferedinputstream = new BufferedInputStream(getContentResolver().openInputStream(mPhotoUri));
            copyFile = new File(ComposerActivity.getTempDir(ComposerActivity.this), (new StringBuilder()).append("copy_").append(StringUtils.randomString(6)).toString());
            FileUtils.copy(bufferedinputstream, new FileOutputStream(copyFile));
            if(PhotoUploader.TEMP_PHOTO_FILE.exists() && mPhotoUri.compareTo(PhotoUploader.TEMP_PHOTO_URI) == 0)
                PhotoUploader.TEMP_PHOTO_FILE.delete();
            Uri uri = Uri.fromFile(copyFile);
            bm = ImageUtils.scaleImage(ComposerActivity.this, uri, i, i);
            if(bm == null)
                throw new IOException("unable to decode image");
              goto _L1
_L3:
            return;
_L1:
            try
            {
                mHandler.post(new Runnable() {

                    public void run()
                    {
                        mPhotosAdapter.bitmaps.add(bm);
                        mPhotosAdapter.files.add(copyFile);
                        mTempFiles.add(copyFile);
                        ImageView imageview = (ImageView)findViewById(0x7f0e0044);
                        imageview.setImageBitmap(bm);
                        imageview.setVisibility(0);
                        mPhotosAdapter.notifyDataSetChanged();
                        updateAddPhotoIcon();
                    }

                    final PhotoThumbnailer this$1;
                    final Bitmap val$bm;
                    final File val$copyFile;

                
                {
                    this$1 = PhotoThumbnailer.this;
                    bm = bitmap;
                    copyFile = file;
                    super();
                }
                }
);
            }
            // Misplaced declaration of an exception variable
            catch(IOException ioexception)
            {
                Log.d(getTag(), "Error thumbnailing photo in composer", ioexception);
            }
            if(true) goto _L3; else goto _L2
_L2:
        }

        private Uri mPhotoUri;
        final ComposerActivity this$0;

        public PhotoThumbnailer(Uri uri)
        {
            this$0 = ComposerActivity.this;
            super();
            mPhotoUri = uri;
        }
    }

    private class ComposerPhotoUploads extends Thread
    {

        public void run()
        {
            Iterator iterator = mFiles.iterator();
_L9:
            File file;
            if(!iterator.hasNext())
                break MISSING_BLOCK_LABEL_484;
            file = (File)iterator.next();
            Bitmap bitmap;
            Uri uri = Uri.fromFile(file);
            bitmap = ImageUtils.scaleImage(ComposerActivity.this, uri, 960, 960);
            if(bitmap == null)
                throw new IOException("unable to decode image");
            break MISSING_BLOCK_LABEL_110;
            IOException ioexception;
            ioexception;
            Log.d(getTag(), "Error resizing: ", ioexception);
            try
            {
                file.delete();
            }
            catch(Exception exception2)
            {
                Log.d(getTag(), "Error cleaning up: ", exception2);
            }
            continue; /* Loop/switch isn't completed */
            File file1;
            file1 = new File(ComposerActivity.getTempDir(ComposerActivity.this), (new StringBuilder()).append("resized_").append(StringUtils.randomString(6)).toString());
            FileOutputStream fileoutputstream = new FileOutputStream(file1);
            bitmap.compress(android.graphics.Bitmap.CompressFormat.JPEG, 100, fileoutputstream);
            fileoutputstream.flush();
            fileoutputstream.close();
            bitmap.recycle();
            if(mAlbum == null) goto _L2; else goto _L1
_L1:
            String s = mAlbum.getAlbumId();
_L5:
            long l = -1L;
            if(mTaggedPlace == null) goto _L4; else goto _L3
_L3:
            l = mTaggedPlace.mPageId;
_L7:
            ArrayList arraylist;
            arraylist = null;
            if(mTaggedUids != null && !mTaggedUids.isEmpty())
            {
                arraylist = new ArrayList();
                FacebookPhotoWithTag facebookphotowithtag;
                for(Iterator iterator1 = mTaggedUids.iterator(); iterator1.hasNext(); arraylist.add(facebookphotowithtag))
                {
                    long l1 = ((Long)iterator1.next()).longValue();
                    facebookphotowithtag = new FacebookPhotoWithTag(l1);
                }

            }
            break MISSING_BLOCK_LABEL_389;
            Exception exception;
            exception;
            Intent intent;
            Exception exception3;
            try
            {
                file.delete();
            }
            catch(Exception exception1)
            {
                Log.d(getTag(), "Error cleaning up: ", exception1);
            }
            throw exception;
_L2:
            s = null;
              goto _L5
_L4:
            if(!mImplicitLocationOn || mImplicitLoc == null) goto _L7; else goto _L6
_L6:
            l = mImplicitLoc.pageId.longValue();
              goto _L7
            intent = UploadManager.createUploadIntent(ComposerActivity.this, file1.getAbsolutePath(), s, mStatus, -1L, -1L, true, arraylist, l, mPrivacy, mTargetId);
            startService(intent);
            try
            {
                file.delete();
            }
            // Misplaced declaration of an exception variable
            catch(Exception exception3)
            {
                Log.d(getTag(), "Error cleaning up: ", exception3);
            }
            continue; /* Loop/switch isn't completed */
            return;
            if(true) goto _L9; else goto _L8
_L8:
        }

        private final List mFiles;
        private final String mPrivacy;
        private final String mStatus;
        private final long mTargetId;
        final ComposerActivity this$0;

        public ComposerPhotoUploads(List list, String s, String s1, long l)
        {
            this$0 = ComposerActivity.this;
            super();
            mFiles = list;
            mStatus = s;
            mPrivacy = s1;
            mTargetId = l;
        }
    }

    class ComposerMovementMethod extends ArrowKeyMovementMethod
    {

        public boolean onTouchEvent(TextView textview, Spannable spannable, MotionEvent motionevent)
        {
            if(motionevent.getAction() != 1) goto _L2; else goto _L1
_L1:
            ClickableSpan aclickablespan[];
            int i = (int)motionevent.getX();
            int j = (int)motionevent.getY();
            int k = i - textview.getTotalPaddingLeft();
            int l = j - textview.getTotalPaddingTop();
            int i1 = k + textview.getScrollX();
            int j1 = l + textview.getScrollY();
            Layout layout = textview.getLayout();
            int k1 = layout.getOffsetForHorizontal(layout.getLineForVertical(j1), i1);
            aclickablespan = (ClickableSpan[])spannable.getSpans(k1, k1, android/text/style/ClickableSpan);
            if(aclickablespan.length == 0) goto _L2; else goto _L3
_L3:
            boolean flag;
            aclickablespan[0].onClick(textview);
            flag = true;
_L5:
            return flag;
_L2:
            flag = super.onTouchEvent(textview, spannable, motionevent);
            if(true) goto _L5; else goto _L4
_L4:
        }

        final ComposerActivity this$0;

        ComposerMovementMethod()
        {
            this$0 = ComposerActivity.this;
            super();
        }
    }

    public class MetaText extends ForegroundColorSpan
    {

        final ComposerActivity this$0;

        public MetaText(int i)
        {
            this$0 = ComposerActivity.this;
            super(i);
        }
    }

    public class PhotosAdapter extends BaseAdapter
    {

        public void deletePhoto(int i)
        {
            files.remove(i);
            bitmaps.remove(i);
            notifyDataSetChanged();
        }

        public int getCount()
        {
            return files.size();
        }

        public Object getItem(int i)
        {
            return files.get(i);
        }

        public long getItemId(int i)
        {
            return (long)i;
        }

        public View getView(int i, View view, ViewGroup viewgroup)
        {
            View view1;
            if(view != null)
                view1 = view;
            else
                view1 = ((LayoutInflater)getSystemService("layout_inflater")).inflate(0x7f030012, null);
            ((ImageView)view1.findViewById(0x7f0e003a)).setImageBitmap((Bitmap)bitmaps.get(i));
            ((ImageView)view1.findViewById(0x7f0e003b)).setTag(Integer.valueOf(i));
            view1.setLayoutParams(new custom.android.Gallery.LayoutParams(-2, -2));
            return view1;
        }

        public List bitmaps;
        public ArrayList files;
        final ComposerActivity this$0;

        public PhotosAdapter()
        {
            this$0 = ComposerActivity.this;
            super();
            files = new ArrayList();
            bitmaps = new ArrayList();
        }
    }

    private class ComposerAppSessionListener extends AppSessionListener
    {

        public void onGetAudienceSettingsComplete(AppSession appsession, String s, int i, String s1, Exception exception, AudienceSettings audiencesettings)
        {
            if(i == 200 && mAudienceAdapter != null)
                updateAudienceSettings(audiencesettings);
        }

        public void onGetNearbyRegionsComplete(AppSession appsession, String s, int i, String s1, Exception exception, List list)
        {
            ListIterator listiterator = mPendingRequestIds.listIterator();
            do
            {
                if(!listiterator.hasPrevious())
                    break;
                if(!s.equals((String)listiterator.previous()))
                    continue;
                listiterator.remove();
                for(; listiterator.hasPrevious(); listiterator.remove())
                    listiterator.previous();

                if(i == 200)
                {
                    mImplicitLoc = GeoRegion.createImplicitLocation(list);
                    updateImplicitLocation();
                }
                break;
            } while(true);
        }

        public void onProfileImageDownloaded(AppSession appsession, String s, int i, String s1, Exception exception, ProfileImage profileimage, ProfileImagesCache profileimagescache)
        {
            if(i == 200 && mProfile != null && profileimage.id == mProfile.mId)
                setProfilePic();
        }

        public void onProfileImageLoaded(AppSession appsession, ProfileImage profileimage)
        {
            if(mProfile != null && mProfile.mId == profileimage.id)
                setProfilePic();
        }

        final ComposerActivity this$0;

        private ComposerAppSessionListener()
        {
            this$0 = ComposerActivity.this;
            super();
        }

    }

    private static final class OverlayMode extends Enum
    {

        public static OverlayMode valueOf(String s)
        {
            return (OverlayMode)Enum.valueOf(com/facebook/katana/ComposerActivity$OverlayMode, s);
        }

        public static OverlayMode[] values()
        {
            return (OverlayMode[])$VALUES.clone();
        }

        private static final OverlayMode $VALUES[];
        public static final OverlayMode AUDIENCE_SELECTOR;
        public static final OverlayMode PHOTO_STACK;

        static 
        {
            AUDIENCE_SELECTOR = new OverlayMode("AUDIENCE_SELECTOR", 0);
            PHOTO_STACK = new OverlayMode("PHOTO_STACK", 1);
            OverlayMode aoverlaymode[] = new OverlayMode[2];
            aoverlaymode[0] = AUDIENCE_SELECTOR;
            aoverlaymode[1] = PHOTO_STACK;
            $VALUES = aoverlaymode;
        }

        private OverlayMode(String s, int i)
        {
            super(s, i);
        }
    }

    private static final class StateKeys
    {

        static String IMPLICIT_LOCATION = "implicit_location";
        static String IS_IMPLICIT_LOCATION_ON = "is_implicit_location_on";
        static String SHOULD_EXIT_ON_CANCEL = "should_exit_on_cancel";
        static String TAGGED_PLACE_LOCATION = "tagged_place_location";
        static String TAGGED_PLACE_PROFILE = "tagged_place_profile";
        static String XED_LOCATION = "xed_location";


        private StateKeys()
        {
        }
    }

    public static final class Extras
    {

        public static final String COMPOSER_TITLE = "extra_composer_title";
        public static final String FIXED_AUDIENCE_ID = "extra_fixed_audience_id";
        public static final String IS_CHECKIN = "extra_is_checkin";
        public static final String PHOTO_REQUEST_CODE = "extra_photo_request_code";
        public static final String PHOTO_UPLOAD_STARTED = "extra_photo_upload_started";
        public static final String TAGGED_LOC = "extra_tagged_location";

        public Extras()
        {
        }
    }


    public ComposerActivity()
    {
        mPendingRequestIds = new LinkedList();
        int ai[] = new int[2];
        ai[0] = 0x7f0e004e;
        ai[1] = 0x7f0e0051;
        mOverlayViewIds = ai;
        int ai1[] = new int[2];
        ai1[0] = 0x7f0e004c;
        ai1[1] = 0x7f0e004f;
        mOverlayViewWrapperIds = ai1;
        mShouldHideTagFriendsDialog = false;
        mShouldExitOnCancel = true;
    }

    private void adjustOverlayItemHeights()
    {
        int i = (int)(0.5D * (double)getWindowManager().getDefaultDisplay().getHeight());
        int ai[] = mOverlayViewIds;
        int j = ai.length;
        for(int k = 0; k < j; k++)
            findViewById(ai[k]).setLayoutParams(new android.widget.LinearLayout.LayoutParams(-1, i));

    }

    public static void cleanup(final Context context)
    {
        (new Thread() {

            public void run()
            {
                File file = ComposerActivity.getTempDir(context);
                if(file.exists())
                {
                    File afile[] = file.listFiles();
                    int i = afile.length;
                    for(int j = 0; j < i; j++)
                        afile[j].delete();

                }
            }

            final Context val$context;

            
            {
                context = context1;
                super();
            }
        }
).start();
    }

    private void fetchNearbyRegionsIfNeeded(Location location)
    {
        if((mImplicitLoc == null || mRequestLocation == null || location.distanceTo(mRequestLocation) >= 20F) && location != null)
        {
            FqlGetPlacesNearby fqlgetplacesnearby = PlacesNearby.get(this, new com.facebook.katana.features.places.PlacesNearby.PlacesNearbyArgType(location));
            if(fqlgetplacesnearby != null && fqlgetplacesnearby.filter.length() == 0 && location.distanceTo(fqlgetplacesnearby.location) < 20F && 20 == fqlgetplacesnearby.resultLimit)
            {
                mImplicitLoc = GeoRegion.createImplicitLocation(fqlgetplacesnearby.getRegions());
                updateImplicitLocation();
                mRequestLocation = fqlgetplacesnearby.location;
            } else
            {
                mRequestLocation = location;
                Locale locale = (Locale)null;
                Object aobj[] = new Object[4];
                aobj[0] = Double.valueOf(location.getLatitude());
                aobj[1] = Double.valueOf(location.getLongitude());
                aobj[2] = com.facebook.katana.model.GeoRegion.Type.city;
                aobj[3] = com.facebook.katana.model.GeoRegion.Type.state;
                String s = FqlGetNearbyRegions.GetRegions(this, String.format(locale, "latitude='%f' and longitude='%f' and type in ('%s','%s')", aobj));
                mPendingRequestIds.add(s);
            }
        }
    }

    public static int getEndIndex(CharSequence charsequence)
    {
        SpannableStringBuilder spannablestringbuilder = new SpannableStringBuilder(charsequence);
        MetaText ametatext[] = (MetaText[])spannablestringbuilder.getSpans(0, spannablestringbuilder.length(), com/facebook/katana/ComposerActivity$MetaText);
        int i = spannablestringbuilder.length();
        int j = ametatext.length;
        for(int k = 0; k < j; k++)
        {
            int l = spannablestringbuilder.getSpanStart(ametatext[k]);
            if(l != -1 && l < i)
                i = l;
        }

        return Math.max(0, i);
    }

    private String getTaggedWithSpanText()
    {
        if(mTaggedUids.size() != 0) goto _L2; else goto _L1
_L1:
        String s2 = null;
_L4:
        return s2;
_L2:
        String s;
        String s1;
        long l = ((Long)(new ArrayList(mTaggedUids)).get(0)).longValue();
        s = null;
        FacebookProfile facebookprofile = ConnectionsProvider.getFriendProfileFromId(this, l);
        if(facebookprofile != null)
            s = facebookprofile.mDisplayName;
        if(mTaggedUids.size() != 1)
            break; /* Loop/switch isn't completed */
        s1 = s;
_L5:
        s2 = s1;
        if(true) goto _L4; else goto _L3
_L3:
        if(mTaggedUids.size() == 2)
        {
            Object aobj1[] = new Object[1];
            aobj1[0] = s;
            s1 = getString(0x7f0a01a7, aobj1);
        } else
        {
            Object aobj[] = new Object[2];
            aobj[0] = s;
            aobj[1] = Integer.valueOf(mTaggedUids.size() - 1);
            s1 = getString(0x7f0a01a8, aobj);
        }
          goto _L5
        if(true) goto _L4; else goto _L6
_L6:
    }

    private static File getTempDir(Context context)
    {
        return context.getDir("composer_temp", 0);
    }

    private CharSequence getUserStatus()
    {
        int i = getEndIndex(mStatusField.getText());
        return (new SpannableString(mStatusField.getText())).subSequence(0, i);
    }

    private void hideAudienceSelector()
    {
        findViewById(0x7f0e004c).setVisibility(8);
        showKeyboard();
        mOverlayMode = null;
    }

    private void hideKeyboard()
    {
        ((InputMethodManager)getSystemService("input_method")).hideSoftInputFromWindow(mStatusField.getWindowToken(), 0);
    }

    private void hidePhotosStack()
    {
        findViewById(0x7f0e004f).setVisibility(8);
        showKeyboard();
        mOverlayMode = null;
    }

    private void initStatusField()
    {
        mStatusField = (EditText)findViewById(0x7f0e0043);
        mStatusField.setMovementMethod(new ComposerMovementMethod());
        mStatusField.requestFocus();
        mStatusField.setSelection(0, 0);
    }

    private boolean isOverlayMode()
    {
        int ai[];
        int i;
        int j;
        ai = mOverlayViewWrapperIds;
        i = ai.length;
        j = 0;
_L3:
        if(j >= i)
            break MISSING_BLOCK_LABEL_42;
        if(findViewById(ai[j]).getVisibility() == 8) goto _L2; else goto _L1
_L1:
        boolean flag = true;
_L4:
        return flag;
_L2:
        j++;
          goto _L3
        flag = false;
          goto _L4
    }

    private void launchPlacesNearby()
    {
        Intent intent = new Intent(this, com/facebook/katana/activity/places/PlacesNearbyActivity);
        intent.putExtra("launched_for_place", true);
        if(mIsCheckin)
            intent.putExtra("extra_is_checkin", true);
        if(mTaggedPlace != null)
            intent.putExtra("extra_place", mTaggedPlace);
        startActivityForResult(intent, 2);
    }

    private void setProfilePic()
    {
        ImageView imageview = (ImageView)findViewById(0x7f0e0042);
        Bitmap bitmap = null;
        if(mProfile != null)
            bitmap = mAppSession.getUserImagesCache().get(this, mProfile.mId, mProfile.mImageUrl);
        if(bitmap != null)
            imageview.setImageBitmap(bitmap);
        else
            imageview.setImageDrawable(getResources().getDrawable(0x7f0200f3));
    }

    private void showKeyboard()
    {
        hideKeyboard();
        ((InputMethodManager)getSystemService("input_method")).toggleSoftInput(0, 0);
    }

    private void showPhotosStack()
    {
        mOverlayMode = OverlayMode.PHOTO_STACK;
        hideKeyboard();
        findViewById(0x7f0e0050).setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                hidePhotosStack();
            }

            final ComposerActivity this$0;

            
            {
                this$0 = ComposerActivity.this;
                super();
            }
        }
);
        findViewById(0x7f0e004f).setVisibility(0);
        findViewById(0x7f0e0051).startAnimation(AnimationUtils.loadAnimation(this, 0x7f040001));
    }

    private void updateText(String s)
    {
        String s1;
        String s2;
        SpannableStringBuilder spannablestringbuilder;
        SpannableStringBuilder spannablestringbuilder1;
        int i;
        int j;
        Object aobj2[];
        if(s != null)
            spannablestringbuilder = new SpannableStringBuilder(s);
        else
            spannablestringbuilder = new SpannableStringBuilder(getUserStatus());
        s1 = null;
        s2 = getTaggedWithSpanText();
        if(mTaggedUids.size() <= 0 || mTaggedPlace != null) goto _L2; else goto _L1
_L1:
        aobj2 = new Object[1];
        aobj2[0] = s2;
        s1 = getString(0x7f0a01a9, aobj2);
_L4:
        if(s1 != null)
        {
            spannablestringbuilder1 = new SpannableStringBuilder(" \u2014 ");
            spannablestringbuilder1.append(s1);
            spannablestringbuilder1.setSpan(new MetaText(0x7f070018), 0, spannablestringbuilder1.length(), 33);
            if(s2 != null)
            {
                j = spannablestringbuilder1.toString().indexOf(s2);
                spannablestringbuilder1.setSpan(new ForegroundColorSpan(0xff0000ff), j, j + s2.length(), 33);
                spannablestringbuilder1.setSpan(new ClickableSpan() {

                    public void onClick(View view)
                    {
                        Intent intent = new Intent(ComposerActivity.this, com/facebook/katana/activity/profilelist/FriendMultiSelectorActivity);
                        intent.putExtra("profiles", IntentUtils.setToPrimitive(mTaggedUids));
                        startActivityForResult(intent, 1);
                    }

                    public void updateDrawState(TextPaint textpaint)
                    {
                    }

                    final ComposerActivity this$0;

            
            {
                this$0 = ComposerActivity.this;
                super();
            }
                }
, j, j + s2.length(), 33);
            }
            if(mTaggedPlace != null)
            {
                i = spannablestringbuilder1.toString().indexOf(mTaggedPlace.mName);
                spannablestringbuilder1.setSpan(new ForegroundColorSpan(0xff0000ff), i, i + mTaggedPlace.mName.length(), 33);
                spannablestringbuilder1.setSpan(new ClickableSpan() {

                    public void onClick(View view)
                    {
                        launchPlacesNearby();
                    }

                    public void updateDrawState(TextPaint textpaint)
                    {
                    }

                    final ComposerActivity this$0;

            
            {
                this$0 = ComposerActivity.this;
                super();
            }
                }
, i, i + mTaggedPlace.mName.length(), 33);
            }
            spannablestringbuilder.append(spannablestringbuilder1);
        }
        mStatusField.setText(spannablestringbuilder);
        return;
_L2:
        if(mTaggedPlace != null && mTaggedUids.size() == 0)
        {
            Object aobj1[] = new Object[1];
            aobj1[0] = mTaggedPlace.mName;
            s1 = getString(0x7f0a01aa, aobj1);
        } else
        if(mTaggedUids.size() > 0 && mTaggedPlace != null)
        {
            Object aobj[] = new Object[2];
            aobj[0] = s2;
            aobj[1] = mTaggedPlace.mName;
            s1 = getString(0x7f0a01ab, aobj);
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    public void closeAudienceSelector(View view)
    {
        hideAudienceSelector();
    }

    public boolean facebookOnBackPressed()
    {
        boolean flag;
        if(mOverlayMode == OverlayMode.PHOTO_STACK)
        {
            hidePhotosStack();
            flag = true;
        } else
        if(mOverlayMode == OverlayMode.AUDIENCE_SELECTOR)
        {
            hideAudienceSelector();
            flag = true;
        } else
        {
            flag = super.facebookOnBackPressed();
        }
        return flag;
    }

    public void hideImplicitLocation()
    {
        findViewById(0x7f0e0045).setVisibility(8);
    }

    protected void onActivityResult(int i, int j, Intent intent)
    {
        super.onActivityResult(i, j, intent);
        if(j != 0) goto _L2; else goto _L1
_L1:
        if(mShouldExitOnCancel && mIsCheckin)
            finish();
_L4:
        return;
_L2:
        Uri uri;
        switch(i)
        {
        default:
            continue; /* Loop/switch isn't completed */

        case 1: // '\001'
            if(j == -1 && intent.hasExtra("profiles"))
            {
                mTaggedUids = IntentUtils.primitiveToSet(intent.getLongArrayExtra("profiles"));
                updateText(null);
                updateTagFriendsIcon();
            }
            continue; /* Loop/switch isn't completed */

        case 2: // '\002'
            if(j != -1)
                continue; /* Loop/switch isn't completed */
            mXedLocation = intent.getBooleanExtra("extra_xed_location", false);
            if(mXedLocation)
            {
                mTaggedPlace = null;
                mTaggedLocation = null;
                mImplicitLoc = null;
                mImplicitLocationOn = false;
            } else
            {
                if(intent.hasExtra("extra_place"))
                {
                    mTaggedPlace = (FacebookPlace)intent.getParcelableExtra("extra_place");
                    if(intent.hasExtra("extra_nearby_location"))
                        mTaggedLocation = (Location)intent.getParcelableExtra("extra_nearby_location");
                }
                if(intent.hasExtra("extra_implicit_location"))
                {
                    mImplicitLocationOn = true;
                    mImplicitLoc = (com.facebook.katana.model.GeoRegion.ImplicitLocation)intent.getParcelableExtra("extra_implicit_location");
                }
            }
            updateTaggedPlaceViews();
            mShouldExitOnCancel = false;
            continue; /* Loop/switch isn't completed */

        case 133701: 
        case 133702: 
            if(intent == null)
                intent = new Intent();
            uri = null;
            if(i != 0x20a46)
                break; /* Loop/switch isn't completed */
            uri = intent.getData();
            break;
        }
_L6:
        mShouldExitOnCancel = false;
        (new PhotoThumbnailer(uri)).start();
        if(true) goto _L4; else goto _L3
_L3:
        if(i != 0x20a45) goto _L6; else goto _L5
_L5:
        uri = PhotoUploader.TEMP_PHOTO_URI;
          goto _L6
    }

    public void onConfigurationChanged(Configuration configuration)
    {
        super.onConfigurationChanged(configuration);
        if(getResources().getConfiguration().orientation != 2) goto _L2; else goto _L1
_L1:
        ((ComposerEditText)findViewById(0x7f0e0043)).setImeOptions(6);
        mStatusField.setImeOptions(6);
_L4:
        adjustOverlayItemHeights();
        return;
_L2:
        if(!isOverlayMode())
            showKeyboard();
        if(true) goto _L4; else goto _L3
_L3:
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        mAppSession = AppSession.getActiveSession(this, true);
        if(mAppSession != null) goto _L2; else goto _L1
_L1:
        LoginActivity.toLogin(this);
_L6:
        return;
_L2:
        Uri uri;
        setContentView(0x7f030013);
        if(getIntent().hasExtra("extra_composer_title"))
        {
            int i = getIntent().getExtras().getInt("extra_composer_title");
            ((TextView)findViewById(0x7f0e0017)).setText(i);
        }
        mMediaUploader = new MediaUploader(this, null);
        mIsCheckin = getIntent().getBooleanExtra("extra_is_checkin", false);
        mAppSessionListener = new ComposerAppSessionListener();
        mProfile = new FacebookProfile(mAppSession.getSessionInfo().getProfile());
        mTaggedUids = new HashSet();
        mPhotosAdapter = new PhotosAdapter();
        mTempFiles = new ArrayList();
        mHandler = new Handler();
        Gallery gallery = (Gallery)findViewById(0x7f0e0052);
        gallery.setAdapter(mPhotosAdapter);
        gallery.setUnselectedAlpha(1F);
        if(mIsCheckin)
            ((TextView)findViewById(0x7f0e0017)).setText(getString(0x7f0a023d));
        mOverlayMode = null;
        initStatusField();
        if(bundle != null)
        {
            mTaggedPlace = (FacebookPlace)bundle.getParcelable(StateKeys.TAGGED_PLACE_PROFILE);
            mTaggedLocation = (Location)bundle.getParcelable(StateKeys.TAGGED_PLACE_LOCATION);
            mXedLocation = bundle.getBoolean(StateKeys.XED_LOCATION);
            mImplicitLoc = (com.facebook.katana.model.GeoRegion.ImplicitLocation)bundle.getParcelable(StateKeys.IMPLICIT_LOCATION);
            mImplicitLocationOn = bundle.getBoolean(StateKeys.IS_IMPLICIT_LOCATION_ON);
            mShouldExitOnCancel = bundle.getBoolean(StateKeys.SHOULD_EXIT_ON_CANCEL);
            updateTaggedPlaceViews();
        }
        if(mIsCheckin && mShouldExitOnCancel)
        {
            findViewById(0x7f0e0046).setVisibility(0);
            mShouldHideTagFriendsDialog = false;
            launchPlacesNearby();
        }
        uri = null;
        getIntent().getIntExtra("extra_photo_request_code", -1);
        JVM INSTR tableswitch 133701 133702: default 400
    //                   133701 611
    //                   133702 618;
           goto _L3 _L4 _L5
_L3:
        if(uri != null)
            (new PhotoThumbnailer(uri)).start();
        setProfilePic();
        findViewById(0x7f0e0048).setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                launchPlacesNearby();
            }

            final ComposerActivity this$0;

            
            {
                this$0 = ComposerActivity.this;
                super();
            }
        }
);
        mFixedAudienceId = getIntent().getLongExtra("extra_fixed_audience_id", -1L);
        if(mFixedAudienceId == -1L)
        {
            mAudienceAdapter = new AudienceAdapter(this, Boolean.TRUE.equals(MinorStatus.get(this)));
            updateAudienceSettings(AudienceSettings.get(this, com.facebook.katana.model.PrivacySetting.Category.composer_sticky));
            SectionedListView sectionedlistview = (SectionedListView)findViewById(0x7f0e004e);
            sectionedlistview.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

                public void onItemClick(AdapterView adapterview, View view, int j, long l)
                {
                    mAudienceAdapter.hasManuallySetAudience = true;
                    int ai[] = mAudienceAdapter.decodeFlatPosition(j);
                    int ai1[] = mAudienceAdapter.getCurrentlySelectedPosition();
                    mAudienceAdapter.setCurrentlySelectedPosition(ai);
                    if(ai1 != null)
                        mAudienceAdapter.notifyDataSetChanged();
                    view.findViewById(0x7f0e0014).setVisibility(0);
                    if(mAudienceAdapter.isCurrentlySelectedAudienceGroup())
                        mAlbum = null;
                    updateAudienceIcon();
                    hideAudienceSelector();
                }

                final ComposerActivity this$0;

            
            {
                this$0 = ComposerActivity.this;
                super();
            }
            }
);
            sectionedlistview.setSectionedListAdapter(mAudienceAdapter);
        } else
        {
            findViewById(0x7f0e004a).setVisibility(8);
        }
        setPrimaryActionFace(-1, getString(0x7f0a002e));
        ((Button)findViewById(0x7f0e003d)).setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                String s = getUserStatus().toString().trim();
                if(s.length() <= 0 && (!mIsCheckin || mTaggedPlace == null) && mPhotosAdapter.getCount() <= 0) goto _L2; else goto _L1
_L1:
                Intent intent;
                long l;
                PrivacySetting privacysetting;
                String s1;
                intent = new Intent();
                intent.putExtra("extra_status_text", s);
                intent.putExtra("extra_tagged_ids", IntentUtils.setToPrimitive(mTaggedUids));
                int ai[];
                AudienceSettings audiencesettings;
                if(mTaggedPlace != null)
                {
                    intent.putExtra("extra_place", mTaggedPlace);
                    if(mTaggedLocation != null)
                        intent.putExtra("extra_tagged_location", mTaggedLocation);
                } else
                if(mImplicitLoc != null)
                    intent.putExtra("extra_tagged_place_id", String.valueOf(mImplicitLoc.pageId));
                if(mXedLocation)
                    intent.putExtra("extra_xed_location", "true");
                if(mAudienceAdapter != null)
                    ai = mAudienceAdapter.getCurrentlySelectedPosition();
                else
                    ai = null;
                l = mFixedAudienceId;
                privacysetting = null;
                s1 = null;
                if(ai == null) goto _L4; else goto _L3
_L3:
                AudienceOption audienceoption = mAudienceAdapter.getChild(ai[0], ai[1]);
                if(audienceoption.getType() == com.facebook.katana.features.composer.AudienceOption.Type.PRIVACY_SETTING)
                    privacysetting = ((com.facebook.katana.features.composer.AudienceAdapter.PrivacySettingAudienceOption)audienceoption).getPrivacySetting();
                else
                if(audienceoption.getType() == com.facebook.katana.features.composer.AudienceOption.Type.FRIEND_LIST)
                    privacysetting = ((com.facebook.katana.features.composer.AudienceAdapter.FriendListAudienceOption)audienceoption).getFriendList().toPrivacySetting();
                else
                if(audienceoption.getType() == com.facebook.katana.features.composer.AudienceOption.Type.GROUP)
                {
                    com.facebook.katana.features.composer.AudienceAdapter.GroupAudienceOption groupaudienceoption = (com.facebook.katana.features.composer.AudienceAdapter.GroupAudienceOption)audienceoption;
                    intent.putExtra("extra_status_target_id", groupaudienceoption.getGroup().mId);
                    l = groupaudienceoption.getGroup().mId;
                }
                if(privacysetting == null) goto _L4; else goto _L5
_L5:
                if(!"CUSTOM".equals(privacysetting.value)) goto _L7; else goto _L6
_L6:
                s1 = privacysetting.jsonEncode();
_L9:
                intent.putExtra("extra_status_privacy", s1);
                audiencesettings = mAudienceAdapter.getServerAudienceSettings();
                if(audiencesettings != null)
                {
                    audiencesettings.setPrivacySetting(privacysetting);
                    audiencesettings.getCallback().callback(ComposerActivity.this, true, com.facebook.katana.model.PrivacySetting.Category.composer_sticky, "", audiencesettings, null);
                }
_L4:
                if(mIsCheckin && mTaggedPlace != null)
                    intent.putExtra("extra_is_checkin", true);
                if(mPhotosAdapter.getCount() > 0)
                {
                    mTempFiles.clear();
                    (new ComposerPhotoUploads(mPhotosAdapter.files, getUserStatus().toString().trim(), s1, l)).start();
                    intent.putExtra("extra_photo_upload_started", true);
                    if(mAlbum != null)
                        intent.putExtra("extra_status_target_id", mAlbum.getObjectId());
                }
                String s2;
                JSONException jsonexception;
                String s3;
                if(mImplicitLocationOn)
                    s2 = "1";
                else
                    s2 = "";
                ComposerUserSettings.setSetting(ComposerActivity.this, "composer_share_location", s2);
                setResult(-1, intent);
                finish();
_L2:
                return;
_L7:
                s3 = (new JSONObject()).put("value", privacysetting.value).toString();
                s1 = s3;
                continue; /* Loop/switch isn't completed */
                jsonexception;
                Log.e(getTag(), "inconceivable JSON exception", jsonexception);
                if(true) goto _L9; else goto _L8
_L8:
            }

            final ComposerActivity this$0;

            
            {
                this$0 = ComposerActivity.this;
                super();
            }
        }
);
        findViewById(0x7f0e0044).setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                showPhotosStack();
            }

            final ComposerActivity this$0;

            
            {
                this$0 = ComposerActivity.this;
                super();
            }
        }
);
        mLocationListener = new com.facebook.katana.util.FBLocationManager.FBLocationListener() {

            public void onLocationChanged(Location location)
            {
                fetchNearbyRegionsIfNeeded(location);
            }

            public void onTimeOut()
            {
            }

            final ComposerActivity this$0;

            
            {
                this$0 = ComposerActivity.this;
                super();
            }
        }
;
        mImplicitLocationOn = ComposerUserSettings.isImplicitLocOn(this);
        adjustOverlayItemHeights();
        if(true) goto _L6; else goto _L4
_L4:
        uri = PhotoUploader.TEMP_PHOTO_URI;
          goto _L3
_L5:
        uri = getIntent().getData();
          goto _L3
    }

    protected Dialog onCreateDialog(int i)
    {
        i;
        JVM INSTR lookupswitch 2: default 28
    //                   100: 43
    //                   255255256: 32;
           goto _L1 _L2 _L3
_L1:
        Object obj = null;
_L5:
        return ((Dialog) (obj));
_L3:
        obj = mMediaUploader.createPhotoDialog();
        continue; /* Loop/switch isn't completed */
_L2:
        android.content.DialogInterface.OnClickListener onclicklistener = new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int j)
            {
                IntentUriHandler.handleUri(ComposerActivity.this, "fb://composertour");
            }

            final ComposerActivity this$0;

            
            {
                this$0 = ComposerActivity.this;
                super();
            }
        }
;
        obj = AlertDialogs.createAlert(this, getString(0x7f0a0034), 0, getString(0x7f0a0035), getString(0x7f0a0036), onclicklistener, null, null, null, false);
        if(true) goto _L5; else goto _L4
_L4:
    }

    protected void onDestroy()
    {
        for(Iterator iterator = mTempFiles.iterator(); iterator.hasNext(); ((File)iterator.next()).delete());
        super.onDestroy();
    }

    protected void onPause()
    {
        if(mAppSession != null)
            mAppSession.removeListener(mAppSessionListener);
        FBLocationManager.removeLocationListener(mLocationListener);
        (new Handler()).postDelayed(new Runnable() {

            public void run()
            {
                hideKeyboard();
            }

            final ComposerActivity this$0;

            
            {
                this$0 = ComposerActivity.this;
                super();
            }
        }
, 1000L);
        if(mShouldHideTagFriendsDialog)
            findViewById(0x7f0e0046).setVisibility(8);
        mShouldHideTagFriendsDialog = true;
        super.onPause();
    }

    protected void onResume()
    {
        super.onResume();
        mAppSession = AppSession.getActiveSession(this, true);
        if(mAppSession != null) goto _L2; else goto _L1
_L1:
        LoginActivity.toLogin(this);
_L4:
        return;
_L2:
        mAppSession.addListener(mAppSessionListener);
        if(mOverlayMode == null)
            showKeyboard();
        mStatusField.setSelection(0, 0);
        if(!ComposerUserSettings.isTourComplete(this))
            showDialog(100);
        if(mAudienceAdapter != null && !mAudienceAdapter.hasReceivedNewOptions)
            AudienceSettings.get(this, com.facebook.katana.model.PrivacySetting.Category.composer_sticky);
        mAppSession.addListener(mAppSessionListener);
        if(mImplicitLocationOn && FBLocationManager.areLocationServicesEnabled(this))
            FBLocationManager.addLocationListener(this, mLocationListener);
        final View tagPeopleDialog = findViewById(0x7f0e0046);
        if(tagPeopleDialog.getVisibility() != 8)
        {
            AlphaAnimation alphaanimation = new AlphaAnimation(1F, 0F);
            alphaanimation.setInterpolator(new AccelerateInterpolator());
            alphaanimation.setStartOffset(3000L);
            alphaanimation.setDuration(1500L);
            alphaanimation.setAnimationListener(new android.view.animation.Animation.AnimationListener() {

                public void onAnimationEnd(Animation animation)
                {
                    tagPeopleDialog.setVisibility(8);
                }

                public void onAnimationRepeat(Animation animation)
                {
                }

                public void onAnimationStart(Animation animation)
                {
                }

                final ComposerActivity this$0;
                final View val$tagPeopleDialog;

            
            {
                this$0 = ComposerActivity.this;
                tagPeopleDialog = view;
                super();
            }
            }
);
            tagPeopleDialog.startAnimation(alphaanimation);
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    protected void onSaveInstanceState(Bundle bundle)
    {
        super.onSaveInstanceState(bundle);
        bundle.putParcelable(StateKeys.TAGGED_PLACE_PROFILE, mTaggedPlace);
        bundle.putParcelable(StateKeys.TAGGED_PLACE_PROFILE, mTaggedPlace);
        bundle.putBoolean(StateKeys.XED_LOCATION, mXedLocation);
        bundle.putParcelable(StateKeys.IMPLICIT_LOCATION, mImplicitLoc);
        bundle.putBoolean(StateKeys.IS_IMPLICIT_LOCATION_ON, mImplicitLocationOn);
        bundle.putBoolean(StateKeys.SHOULD_EXIT_ON_CANCEL, mShouldExitOnCancel);
    }

    public void openAudienceSelector(View view)
    {
        mOverlayMode = OverlayMode.AUDIENCE_SELECTOR;
        hideKeyboard();
        findViewById(0x7f0e004c).setVisibility(0);
        findViewById(0x7f0e004e).startAnimation(AnimationUtils.loadAnimation(this, 0x7f040001));
    }

    public void openTagFriends(View view)
    {
        Intent intent = new Intent(this, com/facebook/katana/activity/profilelist/FriendMultiSelectorActivity);
        intent.putExtra("profiles", IntentUtils.setToPrimitive(mTaggedUids));
        startActivityForResult(intent, 1);
    }

    public void removePhoto(View view)
    {
        Integer integer = (Integer)view.getTag();
        if(integer.intValue() == mPhotosAdapter.getCount() - 1)
        {
            ImageView imageview = (ImageView)findViewById(0x7f0e0044);
            if(integer.intValue() > 0)
            {
                imageview.setImageBitmap((Bitmap)mPhotosAdapter.bitmaps.get(integer.intValue() - 1));
            } else
            {
                imageview.setImageBitmap(null);
                imageview.setVisibility(8);
            }
        }
        mPhotosAdapter.deletePhoto(integer.intValue());
        if(mPhotosAdapter.getCount() == 0)
        {
            hidePhotosStack();
            updateAddPhotoIcon();
        }
    }

    public void showPhotoSourceDialog(View view)
    {
        if(mPhotosAdapter.getCount() > 0)
            showPhotosStack();
        else
            showDialog(0xf36e2d8);
    }

    public void updateAddPhotoIcon()
    {
        ImageButton imagebutton = (ImageButton)findViewById(0x7f0e0049);
        if(mPhotosAdapter != null && mPhotosAdapter.getCount() > 0)
            imagebutton.setImageResource(0x7f020055);
        else
            imagebutton.setImageResource(0x7f020054);
    }

    public void updateAudienceIcon()
    {
        int i = mAudienceAdapter.getIconForSelectedOption();
        if(i != -1)
            findViewById(0x7f0e004b).setBackgroundResource(i);
    }

    protected void updateAudienceSettings(AudienceSettings audiencesettings)
    {
        if(audiencesettings != null)
        {
            mAudienceAdapter.setServerAudienceSettings(audiencesettings);
            mAudienceAdapter.updateGroups(audiencesettings.getGroups());
            mAudienceAdapter.updateFriendLists(audiencesettings.getFriendLists());
            mAudienceAdapter.updateDefaultSetting(audiencesettings.getPrivacySetting());
            mAudienceAdapter.hasReceivedNewOptions = true;
            mAudienceAdapter.notifyDataSetChanged();
            updateAudienceIcon();
        }
    }

    public void updateImplicitLocation()
    {
        TextView textview = (TextView)findViewById(0x7f0e0045);
        if(!mXedLocation && mTaggedPlace == null) goto _L2; else goto _L1
_L1:
        hideImplicitLocation();
_L4:
        return;
_L2:
        if(mImplicitLoc != null)
        {
            textview.setVisibility(0);
            textview.setText(mImplicitLoc.label);
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    public void updateTagFriendsIcon()
    {
        ImageButton imagebutton = (ImageButton)findViewById(0x7f0e0047);
        if(mTaggedUids != null && mTaggedUids.size() > 0)
            imagebutton.setImageResource(0x7f020057);
        else
            imagebutton.setImageResource(0x7f020056);
    }

    public void updateTagPlaceIcon()
    {
        ImageButton imagebutton = (ImageButton)findViewById(0x7f0e0048);
        if(mTaggedPlace != null)
            imagebutton.setImageResource(0x7f020059);
        else
            imagebutton.setImageResource(0x7f020058);
    }

    protected void updateTaggedPlaceViews()
    {
        updateTagPlaceIcon();
        updateText(null);
        updateImplicitLocation();
    }

    private static final int COMPOSER_TOUR_DIALOG_ID = 100;
    private static final int KEYBOARD_FADE_DURATION_MS = 1000;
    public static final int NEARBY_PLACES_PRECACHE_TOLERANCE_MS = 0x493e0;
    private static final int TAG_FRIENDS_ACTIVITY_ID = 1;
    private static final int TAG_FRIENDS_TIP_DIALOG_FADE_OUT_DELAY = 3000;
    private static final int TAG_FRIENDS_TIP_DIALOG_FADE_OUT_DURATION = 1500;
    private static final int TAG_PLACE_ACTIVITY_ID = 2;
    private static final String TEMP_DIR = "composer_temp";
    private FacebookAlbum mAlbum;
    private AppSession mAppSession;
    private AppSessionListener mAppSessionListener;
    private AudienceAdapter mAudienceAdapter;
    private long mFixedAudienceId;
    private Handler mHandler;
    private com.facebook.katana.model.GeoRegion.ImplicitLocation mImplicitLoc;
    private boolean mImplicitLocationOn;
    private boolean mIsCheckin;
    private com.facebook.katana.util.FBLocationManager.FBLocationListener mLocationListener;
    private MediaUploader mMediaUploader;
    private OverlayMode mOverlayMode;
    private final int mOverlayViewIds[];
    private final int mOverlayViewWrapperIds[];
    private LinkedList mPendingRequestIds;
    private PhotosAdapter mPhotosAdapter;
    private FacebookProfile mProfile;
    private Location mRequestLocation;
    private boolean mShouldExitOnCancel;
    private boolean mShouldHideTagFriendsDialog;
    protected EditText mStatusField;
    private Location mTaggedLocation;
    private FacebookPlace mTaggedPlace;
    private Set mTaggedUids;
    private List mTempFiles;
    private boolean mXedLocation;


























/*
    static com.facebook.katana.model.GeoRegion.ImplicitLocation access$402(ComposerActivity composeractivity, com.facebook.katana.model.GeoRegion.ImplicitLocation implicitlocation)
    {
        composeractivity.mImplicitLoc = implicitlocation;
        return implicitlocation;
    }

*/




/*
    static FacebookAlbum access$702(ComposerActivity composeractivity, FacebookAlbum facebookalbum)
    {
        composeractivity.mAlbum = facebookalbum;
        return facebookalbum;
    }

*/


}
