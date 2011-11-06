// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ExtendedReq.java

package com.facebook.katana.binding;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.features.places.PlacesUtils;
import com.facebook.katana.model.*;
import com.facebook.katana.service.method.*;
import com.facebook.katana.util.ReentrantCallback;
import java.util.*;

// Referenced classes of package com.facebook.katana.binding:
//            AppSession, AppSessionListener, NetworkRequestCallback, FacebookStreamContainer

class ExtendedReq
{

    ExtendedReq()
    {
    }

    static void onExtendedOperationComplete(AppSession appsession, Context context, Intent intent, int i, String s, Exception exception, ApiMethod apimethod)
    {
        int j;
        String s1;
        j = intent.getIntExtra("extended_type", 0);
        s1 = intent.getStringExtra("rid");
        j;
        JVM INSTR lookupswitch 4: default 60
    //                   120: 61
    //                   500: 135
    //                   501: 209
    //                   503: 362;
           goto _L1 _L2 _L3 _L4 _L5
_L1:
        return;
_L2:
        FqlGetEvents fqlgetevents = (FqlGetEvents)apimethod;
        java.util.List list3 = null;
        if(i == 200)
            list3 = fqlgetevents.getEvents();
        Iterator iterator3 = appsession.mListeners.getListeners().iterator();
        while(iterator3.hasNext()) 
            ((AppSessionListener)iterator3.next()).onUserGetEventsComplete(appsession, s1, i, s, exception, list3);
        continue; /* Loop/switch isn't completed */
_L3:
        FqlGetFriendCheckins fqlgetfriendcheckins = (FqlGetFriendCheckins)apimethod;
        java.util.List list2 = null;
        if(i == 200)
            list2 = fqlgetfriendcheckins.getCheckins();
        Iterator iterator2 = appsession.mListeners.getListeners().iterator();
        while(iterator2.hasNext()) 
            ((AppSessionListener)iterator2.next()).onFriendCheckinsComplete(appsession, s1, i, s, exception, list2);
        if(true) goto _L1; else goto _L4
_L4:
        FqlGetPlacesNearby fqlgetplacesnearby = (FqlGetPlacesNearby)apimethod;
        java.util.List list = null;
        java.util.List list1 = null;
        boolean flag = false;
        if(i == 200)
        {
            list = fqlgetplacesnearby.getPlaces();
            list1 = fqlgetplacesnearby.getRegions();
            flag = true;
        }
        if(fqlgetplacesnearby.callback != null)
        {
            com.facebook.katana.features.places.PlacesNearby.PlacesNearbyArgType placesnearbyargtype = new com.facebook.katana.features.places.PlacesNearby.PlacesNearbyArgType(fqlgetplacesnearby.location, fqlgetplacesnearby.maxDistance, fqlgetplacesnearby.filter, fqlgetplacesnearby.resultLimit);
            fqlgetplacesnearby.callback.callback(context, flag, placesnearbyargtype, null, fqlgetplacesnearby, null);
        }
        Iterator iterator1 = appsession.mListeners.getListeners().iterator();
        while(iterator1.hasNext()) 
            ((AppSessionListener)iterator1.next()).onGetPlacesNearbyComplete(appsession, s1, i, s, exception, list, list1, fqlgetplacesnearby.location);
        if(true)
            continue; /* Loop/switch isn't completed */
_L5:
        PlacesCheckin placescheckin = (PlacesCheckin)apimethod;
        FacebookPlace facebookplace = placescheckin.mPlace;
        if(!$assertionsDisabled && facebookplace.getPageInfo() == null)
            throw new AssertionError();
        long l = placescheckin.getCheckinId();
        FacebookPost facebookpost = null;
        if(i == 200 && l != -1L)
        {
            FacebookUser facebookuser = appsession.getSessionInfo().getProfile();
            ArrayList arraylist = new ArrayList(placescheckin.mTaggedUids);
            FacebookCheckinDetails facebookcheckindetails = new FacebookCheckinDetails(l, facebookuser.mUserId, facebookplace.mPageId, System.currentTimeMillis() / 1000L, arraylist, 0xa67c8e50L);
            facebookcheckindetails.setPlaceInfo(facebookplace);
            com.facebook.katana.model.FacebookPost.Attachment attachment = new com.facebook.katana.model.FacebookPost.Attachment(facebookplace.mName, facebookcheckindetails);
            facebookpost = new FacebookPost((new StringBuilder()).append(facebookuser.mUserId).append("_").append(l).toString(), 0xa67c8e50L, facebookuser.mUserId, -1L, placescheckin.mMessage, attachment, placescheckin.mTaggedUids, null, null);
            FacebookProfile facebookprofile = new FacebookProfile(facebookuser);
            facebookpost.setProfile(facebookprofile);
            if(appsession.mHomeStreamContainer != null)
                appsession.mHomeStreamContainer.insertFirst(facebookpost);
            FacebookStreamContainer facebookstreamcontainer = (FacebookStreamContainer)appsession.mWallContainerMap.get(Long.valueOf(facebookuser.mUserId));
            if(facebookstreamcontainer != null)
                facebookstreamcontainer.insertFirst(facebookpost);
            FacebookStreamContainer facebookstreamcontainer1 = (FacebookStreamContainer)appsession.mPlacesActivityContainerMap.get(Long.valueOf(facebookplace.mPageId));
            if(facebookstreamcontainer1 != null)
                facebookstreamcontainer1.insertFirst(facebookpost);
            FacebookCheckin facebookcheckin = new FacebookCheckin(facebookuser.mUserId, l);
            facebookcheckin.setDetails(facebookcheckindetails);
            facebookcheckin.setActor(facebookuser);
            PlacesUtils.setLastCheckin(context, facebookcheckin, System.currentTimeMillis());
        }
        Iterator iterator = appsession.mListeners.getListeners().iterator();
        while(iterator.hasNext()) 
            ((AppSessionListener)iterator.next()).onCheckinComplete(appsession, s1, i, s, exception, facebookpost);
        if(true) goto _L1; else goto _L6
_L6:
    }

    static final boolean $assertionsDisabled;

    static 
    {
        boolean flag;
        if(!com/facebook/katana/binding/ExtendedReq.desiredAssertionStatus())
            flag = true;
        else
            flag = false;
        $assertionsDisabled = flag;
    }
}
