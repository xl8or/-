// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AppSessionListener.java

package com.facebook.katana.binding;

import android.graphics.Bitmap;
import android.location.Location;
import com.facebook.katana.model.*;
import com.facebook.katana.service.method.AudienceSettings;
import java.util.*;

// Referenced classes of package com.facebook.katana.binding:
//            AppSession, ProfileImage, ProfileImagesCache, FacebookStreamContainer

public class AppSessionListener
{
    public static interface GetObjectListener
    {

        public abstract void onLoadError(Exception exception);

        public abstract void onObjectLoaded(Object obj);
    }


    public AppSessionListener()
    {
    }

    public void onCheckinComplete(AppSession appsession, String s, int i, String s1, Exception exception, FacebookPost facebookpost)
    {
    }

    public void onDownloadAlbumThumbnailComplete(AppSession appsession, String s, int i, String s1, Exception exception, String s2)
    {
    }

    public void onDownloadPhotoFullComplete(AppSession appsession, String s, int i, String s1, Exception exception, String s2, String s3)
    {
    }

    public void onDownloadPhotoRawComplete(AppSession appsession, String s, int i, String s1, Exception exception, Bitmap bitmap)
    {
    }

    public void onDownloadPhotoThumbnailComplete(AppSession appsession, String s, int i, String s1, Exception exception, String s2, String s3)
    {
    }

    public void onDownloadStreamPhotoComplete(AppSession appsession, String s, int i, String s1, Exception exception, String s2, Bitmap bitmap)
    {
    }

    public void onEventGetMembersComplete(AppSession appsession, String s, int i, String s1, Exception exception, long l, 
            Map map)
    {
    }

    public void onEventRsvpComplete(AppSession appsession, String s, int i, String s1, Exception exception, long l, 
            boolean flag)
    {
    }

    public void onFlagPlaceComplete(AppSession appsession, String s, int i, String s1, Exception exception)
    {
    }

    public void onFriendCheckinsComplete(AppSession appsession, String s, int i, String s1, Exception exception, List list)
    {
    }

    public void onFriendRequestRespondComplete(AppSession appsession, String s, int i, String s1, Exception exception, long l, 
            boolean flag)
    {
    }

    public void onFriendRequestsMutualFriendsComplete(AppSession appsession, String s, int i, String s1, Exception exception, Map map)
    {
    }

    public void onFriendsAddFriendComplete(AppSession appsession, String s, int i, String s1, Exception exception, List list)
    {
    }

    public void onFriendsSyncComplete(AppSession appsession, String s, int i, String s1, Exception exception)
    {
    }

    public void onGetAudienceSettingsComplete(AppSession appsession, String s, int i, String s1, Exception exception, AudienceSettings audiencesettings)
    {
    }

    public void onGetGroupsComplete(AppSession appsession, String s, int i, String s1, Exception exception, List list)
    {
    }

    public void onGetGroupsMembersComplete(AppSession appsession, String s, int i, String s1, Exception exception, Map map)
    {
    }

    public void onGetNearbyRegionsComplete(AppSession appsession, String s, int i, String s1, Exception exception, List list)
    {
    }

    public void onGetNotificationHistoryComplete(AppSession appsession, String s, int i, String s1, Exception exception)
    {
    }

    public void onGetPageFanStatusComplete(AppSession appsession, String s, int i, String s1, Exception exception, long l, 
            boolean flag)
    {
    }

    public void onGetPlacesNearbyComplete(AppSession appsession, String s, int i, String s1, Exception exception, List list, List list1, 
            Location location)
    {
    }

    public void onGetProfileComplete(AppSession appsession, String s, int i, String s1, Exception exception, FacebookProfile facebookprofile)
    {
    }

    public void onGkSettingsGetComplete(AppSession appsession, String s, int i, String s1, Exception exception, String s2, boolean flag)
    {
    }

    public void onHomeStreamUpdated(AppSession appsession)
    {
    }

    public void onLoginComplete(AppSession appsession, String s, int i, String s1, Exception exception)
    {
    }

    public void onLogoutComplete(AppSession appsession, String s, int i, String s1, Exception exception)
    {
    }

    public void onMailboxDeleteThreadComplete(AppSession appsession, String s, int i, String s1, Exception exception, long al[])
    {
    }

    public void onMailboxGetThreadMessagesComplete(AppSession appsession, String s, int i, String s1, Exception exception, int j, long l)
    {
    }

    public void onMailboxMarkThreadComplete(AppSession appsession, String s, int i, String s1, Exception exception, long al[], boolean flag)
    {
    }

    public void onMailboxReplyComplete(AppSession appsession, String s, int i, String s1, Exception exception, long l)
    {
    }

    public void onMailboxSendComplete(AppSession appsession, String s, int i, String s1, Exception exception)
    {
    }

    public void onMailboxSyncComplete(AppSession appsession, String s, int i, String s1, Exception exception, int j)
    {
    }

    public void onMarkGroupReadComplete(AppSession appsession, String s, int i, String s1, Exception exception, long l)
    {
    }

    public void onPagesAddFanComplete(AppSession appsession, String s, int i, String s1, Exception exception, boolean flag)
    {
    }

    public void onPagesGetInfoComplete(AppSession appsession, String s, int i, String s1, Exception exception, long l, 
            Object obj)
    {
    }

    public void onPagesSearchComplete(AppSession appsession, String s, int i, String s1, Exception exception, int j, int k)
    {
    }

    public void onPhonebookLookupComplete(AppSession appsession, String s, int i, String s1, Exception exception, List list)
    {
    }

    public void onPhotoAddCommentComplete(AppSession appsession, String s, int i, String s1, Exception exception, String s2, FacebookPhotoComment facebookphotocomment)
    {
    }

    public void onPhotoAddTagsComplete(AppSession appsession, String s, int i, String s1, Exception exception, String s2, List list)
    {
    }

    public void onPhotoCreateAlbumComplete(AppSession appsession, String s, int i, String s1, Exception exception, FacebookAlbum facebookalbum)
    {
    }

    public void onPhotoDecodeComplete(AppSession appsession, Bitmap bitmap, String s)
    {
    }

    public void onPhotoDeleteAlbumComplete(AppSession appsession, String s, int i, String s1, Exception exception, String s2)
    {
    }

    public void onPhotoDeletePhotoComplete(AppSession appsession, String s, int i, String s1, Exception exception, String s2, String s3)
    {
    }

    public void onPhotoEditAlbumComplete(AppSession appsession, String s, int i, String s1, Exception exception, String s2)
    {
    }

    public void onPhotoEditPhotoComplete(AppSession appsession, String s, int i, String s1, Exception exception, String s2, String s3)
    {
    }

    public void onPhotoGetAlbumsComplete(AppSession appsession, String s, int i, String s1, Exception exception, String as[], long l)
    {
    }

    public void onPhotoGetCommentsComplete(AppSession appsession, String s, int i, String s1, Exception exception, String s2, List list, 
            boolean flag)
    {
    }

    public void onPhotoGetPhotosComplete(AppSession appsession, String s, int i, String s1, Exception exception, String s2, String as[], 
            long l)
    {
    }

    public void onPhotoGetTagsComplete(AppSession appsession, String s, int i, String s1, Exception exception, List list)
    {
    }

    public void onPhotoUploadComplete(AppSession appsession, String s, int i, String s1, Exception exception, int j, FacebookPhoto facebookphoto, 
            String s2, long l, long l1, long l2)
    {
    }

    public void onPhotosPublishComplete(AppSession appsession, String s, int i, String s1, Exception exception, List list, int j, 
            int k)
    {
    }

    public void onPlacesCreateComplete(AppSession appsession, String s, int i, String s1, Exception exception, long l)
    {
    }

    public void onPlacesEditCheckinComplete(AppSession appsession, String s, int i, String s1, Exception exception, long l, 
            String s2, Set set)
    {
    }

    public void onPlacesRemoveTagComplete(AppSession appsession, String s, int i, String s1, Exception exception, FacebookPost facebookpost, long l)
    {
    }

    public void onProfileImageDownloaded(AppSession appsession, String s, int i, String s1, Exception exception, ProfileImage profileimage, ProfileImagesCache profileimagescache)
    {
    }

    public void onProfileImageLoaded(AppSession appsession, ProfileImage profileimage)
    {
    }

    public void onSetTaggingOptInStatusComplete(AppSession appsession, String s, int i, String s1, Exception exception)
    {
    }

    public void onStreamAddCommentComplete(AppSession appsession, String s, int i, String s1, Exception exception, String s2, com.facebook.katana.model.FacebookPost.Comment comment)
    {
    }

    public void onStreamAddLikeComplete(AppSession appsession, String s, int i, String s1, Exception exception, String s2)
    {
    }

    public void onStreamGetCommentsComplete(AppSession appsession, String s, int i, String s1, Exception exception, String s2)
    {
    }

    public void onStreamGetComplete(AppSession appsession, String s, int i, String s1, Exception exception, long l, 
            int j, FacebookStreamContainer facebookstreamcontainer, List list)
    {
    }

    public void onStreamGetComplete(AppSession appsession, String s, int i, String s1, Exception exception, long l, 
            long al[], long l1, long l2, int j, int k, 
            List list, FacebookStreamContainer facebookstreamcontainer)
    {
    }

    public void onStreamPublishComplete(AppSession appsession, String s, int i, String s1, Exception exception, FacebookPost facebookpost)
    {
    }

    public void onStreamRemoveCommentComplete(AppSession appsession, String s, int i, String s1, Exception exception, String s2)
    {
    }

    public void onStreamRemoveLikeComplete(AppSession appsession, String s, int i, String s1, Exception exception, String s2)
    {
    }

    public void onStreamRemovePostComplete(AppSession appsession, String s, int i, String s1, Exception exception)
    {
    }

    public void onUserGetEventsComplete(AppSession appsession, String s, int i, String s1, Exception exception, List list)
    {
    }

    public void onUserGetFriendRequestsComplete(AppSession appsession, String s, int i, String s1, Exception exception, Map map)
    {
    }

    public void onUserSetContactInfoComplete(AppSession appsession, String s, int i, String s1, Exception exception)
    {
    }

    public void onUsersGetInfoComplete(AppSession appsession, String s, int i, String s1, Exception exception, long l, 
            FacebookUser facebookuser, boolean flag)
    {
    }

    public void onUsersGetInfoComplete(AppSession appsession, String s, int i, String s1, Exception exception, long l, 
            FacebookUserFull facebookuserfull, boolean flag)
    {
    }

    public void onUsersGetInfoComplete(AppSession appsession, String s, int i, String s1, Exception exception, List list)
    {
    }

    public void onUsersInviteComplete(AppSession appsession, String s, int i, String s1, Exception exception, List list)
    {
    }

    public void onUsersSearchComplete(AppSession appsession, String s, int i, String s1, Exception exception, int j, int k)
    {
    }

    public void onVideoUploadComplete(AppSession appsession, String s, int i, String s1, Exception exception, FacebookVideoUploadResponse facebookvideouploadresponse, String s2)
    {
    }
}
