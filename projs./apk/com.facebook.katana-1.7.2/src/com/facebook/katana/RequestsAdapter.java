// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RequestsAdapter.java

package com.facebook.katana;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.view.*;
import android.widget.*;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.binding.ProfileImage;
import com.facebook.katana.binding.ProfileImagesCache;
import com.facebook.katana.model.FacebookSessionInfo;
import com.facebook.katana.model.FacebookUser;
import com.facebook.katana.util.ApplicationUtils;
import com.facebook.katana.util.Toaster;
import java.util.*;

public class RequestsAdapter extends BaseAdapter
{
    private class OnClick
        implements android.view.View.OnClickListener
    {

        public void onClick(View view)
        {
            if(mConfirm)
                mSyncRequired = true;
            FriendRequest friendrequest = mFriendRequest;
            RequestState requeststate;
            if(mConfirm)
                requeststate = RequestState.RESPONSE_CONFIRMING;
            else
                requeststate = RequestState.RESPONSE_IGNORING;
            friendrequest.setState(requeststate);
            mAppSession.respondToFriendRequest(mContext, mFriendRequest.mRequestor.mUserId, mConfirm);
        }

        boolean mConfirm;
        FriendRequest mFriendRequest;
        final RequestsAdapter this$0;

        public OnClick(FriendRequest friendrequest, boolean flag)
        {
            this$0 = RequestsAdapter.this;
            super();
            mFriendRequest = friendrequest;
            mConfirm = flag;
        }
    }

    private class FriendRequest
    {

        RequestState getState()
        {
            return mState;
        }

        void setMutualFriendCount(int i)
        {
            mMutualFriends = i;
            updateView();
        }

        void setProfilePicture(Bitmap bitmap)
        {
            mProfilePicture = bitmap;
            updateView();
        }

        void setState(RequestState requeststate)
        {
            mState = requeststate;
            updateView();
        }

        /**
         * @deprecated Method updateView is deprecated
         */

        void updateView()
        {
            this;
            JVM INSTR monitorenter ;
            View view = mView;
            if(view != null) goto _L2; else goto _L1
_L1:
            this;
            JVM INSTR monitorexit ;
            return;
_L2:
            Button button;
            Button button1;
            TextView textview;
            mView.setOnClickListener(new android.view.View.OnClickListener() {

                public void onClick(View view1)
                {
                    ApplicationUtils.OpenUserProfile(mContext, mRequestor.mUserId, null);
                }

                final FriendRequest this$1;

                
                {
                    this$1 = FriendRequest.this;
                    super();
                }
            }
);
            ((TextView)mView.findViewById(0x7f0e0120)).setText(mRequestor.getDisplayName());
            button = (Button)mView.findViewById(0x7f0e0122);
            button1 = (Button)mView.findViewById(0x7f0e0123);
            textview = (TextView)mView.findViewById(0x7f0e0121);
            if(mState != RequestState.NOT_RESPONDED) goto _L4; else goto _L3
_L3:
            button.setVisibility(0);
            button1.setVisibility(0);
            button.setOnClickListener(new OnClick(this, true));
            button1.setOnClickListener(new OnClick(this, false));
            mView.setBackgroundResource(0x106000d);
            textview.setTextColor(0x7f070015);
            if(mMutualFriends != -1) goto _L6; else goto _L5
_L5:
            textview.setText("");
_L9:
            ImageView imageview = (ImageView)mView.findViewById(0x7f0e011f);
            if(mProfilePicture == null) goto _L8; else goto _L7
_L7:
            imageview.setImageBitmap(mProfilePicture);
              goto _L1
            Exception exception;
            exception;
            throw exception;
_L6:
            Resources resources = mContext.getResources();
            int j = mMutualFriends;
            Object aobj[] = new Object[1];
            aobj[0] = Integer.valueOf(mMutualFriends);
            textview.setText(resources.getQuantityString(0x7f0b0003, j, aobj));
              goto _L9
_L4:
            int i;
            button.setVisibility(4);
            button1.setVisibility(4);
            i = -1;
            class _cls2
            {

                static final int $SwitchMap$com$facebook$katana$RequestsAdapter$RequestState[];

                static 
                {
                    $SwitchMap$com$facebook$katana$RequestsAdapter$RequestState = new int[RequestState.values().length];
                    NoSuchFieldError nosuchfielderror3;
                    try
                    {
                        $SwitchMap$com$facebook$katana$RequestsAdapter$RequestState[RequestState.RESPONSE_CONFIRMING.ordinal()] = 1;
                    }
                    catch(NoSuchFieldError nosuchfielderror) { }
                    try
                    {
                        $SwitchMap$com$facebook$katana$RequestsAdapter$RequestState[RequestState.RESPONSE_IGNORING.ordinal()] = 2;
                    }
                    catch(NoSuchFieldError nosuchfielderror1) { }
                    try
                    {
                        $SwitchMap$com$facebook$katana$RequestsAdapter$RequestState[RequestState.RESPONSE_CONFIRMED.ordinal()] = 3;
                    }
                    catch(NoSuchFieldError nosuchfielderror2) { }
                    $SwitchMap$com$facebook$katana$RequestsAdapter$RequestState[RequestState.RESPONSE_IGNORED.ordinal()] = 4;
_L2:
                    return;
                    nosuchfielderror3;
                    if(true) goto _L2; else goto _L1
_L1:
                }
            }

            _cls2..SwitchMap.com.facebook.katana.RequestsAdapter.RequestState[mState.ordinal()];
            JVM INSTR tableswitch 1 4: default 328
        //                       1 387
        //                       2 394
        //                       3 345
        //                       4 361;
               goto _L10 _L11 _L12 _L13 _L14
_L10:
            break; /* Loop/switch isn't completed */
_L12:
            break MISSING_BLOCK_LABEL_394;
_L15:
            textview.setText(i);
            textview.setTextColor(0x7f070016);
              goto _L9
_L13:
            mView.setBackgroundResource(0x7f070014);
            i = 0x7f0a017f;
              goto _L15
_L14:
            mView.setBackgroundResource(0x7f070014);
            i = 0x7f0a0183;
              goto _L15
_L8:
            imageview.setImageResource(0x7f0200f3);
              goto _L1
_L11:
            i = 0x7f0a0180;
              goto _L15
            i = 0x7f0a0184;
              goto _L15
        }

        int mMutualFriends;
        Bitmap mProfilePicture;
        FacebookUser mRequestor;
        RequestState mState;
        View mView;
        final RequestsAdapter this$0;

        FriendRequest(FacebookUser facebookuser)
        {
            this$0 = RequestsAdapter.this;
            super();
            mState = RequestState.NOT_RESPONDED;
            mMutualFriends = -1;
            mRequestor = facebookuser;
        }
    }

    private static final class RequestState extends Enum
    {

        public static RequestState valueOf(String s)
        {
            return (RequestState)Enum.valueOf(com/facebook/katana/RequestsAdapter$RequestState, s);
        }

        public static RequestState[] values()
        {
            return (RequestState[])$VALUES.clone();
        }

        private static final RequestState $VALUES[];
        public static final RequestState NOT_RESPONDED;
        public static final RequestState RESPONSE_CONFIRMED;
        public static final RequestState RESPONSE_CONFIRMING;
        public static final RequestState RESPONSE_IGNORED;
        public static final RequestState RESPONSE_IGNORING;

        static 
        {
            NOT_RESPONDED = new RequestState("NOT_RESPONDED", 0);
            RESPONSE_CONFIRMING = new RequestState("RESPONSE_CONFIRMING", 1);
            RESPONSE_IGNORING = new RequestState("RESPONSE_IGNORING", 2);
            RESPONSE_CONFIRMED = new RequestState("RESPONSE_CONFIRMED", 3);
            RESPONSE_IGNORED = new RequestState("RESPONSE_IGNORED", 4);
            RequestState arequeststate[] = new RequestState[5];
            arequeststate[0] = NOT_RESPONDED;
            arequeststate[1] = RESPONSE_CONFIRMING;
            arequeststate[2] = RESPONSE_IGNORING;
            arequeststate[3] = RESPONSE_CONFIRMED;
            arequeststate[4] = RESPONSE_IGNORED;
            $VALUES = arequeststate;
        }

        private RequestState(String s, int i)
        {
            super(s, i);
        }
    }


    public RequestsAdapter(Context context, AppSession appsession, Map map)
    {
        mFriendRequestsById = new HashMap();
        mAppSessionListener = new AppSessionListener() {

            public void onFriendRequestRespondComplete(AppSession appsession1, String s, int i, String s1, Exception exception, long l, 
                    boolean flag)
            {
                FriendRequest friendrequest = (FriendRequest)mFriendRequestsById.get(Long.valueOf(l));
                if(!flag || friendrequest == null) goto _L2; else goto _L1
_L1:
                RequestState requeststate;
                if(friendrequest.getState() == RequestState.RESPONSE_CONFIRMING)
                    requeststate = RequestState.RESPONSE_CONFIRMED;
                else
                    requeststate = RequestState.RESPONSE_IGNORED;
                friendrequest.setState(requeststate);
_L4:
                return;
_L2:
                Toaster.toast(mContext, 0x7f0a0186);
                if(friendrequest != null)
                    friendrequest.setState(RequestState.NOT_RESPONDED);
                if(true) goto _L4; else goto _L3
_L3:
            }

            public void onFriendRequestsMutualFriendsComplete(AppSession appsession1, String s, int i, String s1, Exception exception, Map map1)
            {
                if(map1 != null)
                {
                    Iterator iterator = map1.keySet().iterator();
                    do
                    {
                        if(!iterator.hasNext())
                            break;
                        Long long1 = (Long)iterator.next();
                        FriendRequest friendrequest1 = (FriendRequest)mFriendRequestsById.get(long1);
                        if(friendrequest1 != null)
                            friendrequest1.setMutualFriendCount(((List)map1.get(long1)).size());
                    } while(true);
                    Iterator iterator1 = mFriendRequests.iterator();
                    do
                    {
                        if(!iterator1.hasNext())
                            break;
                        FriendRequest friendrequest = (FriendRequest)iterator1.next();
                        if(friendrequest.mMutualFriends == -1)
                            friendrequest.setMutualFriendCount(0);
                    } while(true);
                }
            }

            public void onProfileImageDownloaded(AppSession appsession1, String s, int i, String s1, Exception exception, ProfileImage profileimage, ProfileImagesCache profileimagescache)
            {
                if(profileimage != null)
                {
                    FriendRequest friendrequest = (FriendRequest)mFriendRequestsById.get(Long.valueOf(profileimage.id));
                    if(friendrequest != null)
                        friendrequest.setProfilePicture(profileimage.getBitmap());
                }
            }

            final RequestsAdapter this$0;

            
            {
                this$0 = RequestsAdapter.this;
                super();
            }
        }
;
        mContext = context;
        mAppSession = appsession;
        mSyncRequired = false;
        mAppSession.addListener(mAppSessionListener);
        setupRequestors(map);
    }

    public boolean areAllItemsEnabled()
    {
        return true;
    }

    public int getCount()
    {
        return mFriendRequests.size();
    }

    public Object getItem(int i)
    {
        return ((FriendRequest)mFriendRequests.get(i)).mRequestor;
    }

    public long getItemId(int i)
    {
        return ((FriendRequest)mFriendRequests.get(i)).mRequestor.mUserId;
    }

    public int getItemViewType(int i)
    {
        return 0;
    }

    public View getView(int i, View view, ViewGroup viewgroup)
    {
        FriendRequest friendrequest = (FriendRequest)mFriendRequests.get(i);
        if(friendrequest.mView == null)
            friendrequest.mView = ((LayoutInflater)mContext.getSystemService("layout_inflater")).inflate(0x7f03006f, null);
        friendrequest.updateView();
        return friendrequest.mView;
    }

    public int getViewTypeCount()
    {
        return 1;
    }

    public boolean hasStableIds()
    {
        return true;
    }

    public boolean isEmpty()
    {
        return mFriendRequests.isEmpty();
    }

    public boolean isEnabled(int i)
    {
        boolean flag;
        if(mFriendRequests.get(i) != null)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public void onDestroy()
    {
        mAppSession.removeListener(mAppSessionListener);
    }

    public void setupRequestors(Map map)
    {
        mFriendRequests = new ArrayList(map.size());
        FacebookUser facebookuser1;
        FriendRequest friendrequest;
        for(Iterator iterator = map.values().iterator(); iterator.hasNext(); mFriendRequestsById.put(Long.valueOf(facebookuser1.mUserId), friendrequest))
        {
            facebookuser1 = (FacebookUser)iterator.next();
            friendrequest = new FriendRequest(facebookuser1);
            mFriendRequests.add(friendrequest);
        }

        mAppSession.getFriendRequestsMutualFriends(mContext, mAppSession.getSessionInfo().userId);
        ProfileImagesCache profileimagescache = mAppSession.getUserImagesCache();
        for(Iterator iterator1 = map.values().iterator(); iterator1.hasNext();)
        {
            FacebookUser facebookuser = (FacebookUser)iterator1.next();
            ((FriendRequest)mFriendRequestsById.get(Long.valueOf(facebookuser.mUserId))).mProfilePicture = profileimagescache.get(mContext, facebookuser.mUserId, facebookuser.mImageUrl);
        }

    }

    private AppSession mAppSession;
    private AppSessionListener mAppSessionListener;
    private Context mContext;
    private ArrayList mFriendRequests;
    private Map mFriendRequestsById;
    boolean mSyncRequired;




}
