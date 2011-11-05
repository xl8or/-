package com.facebook.katana.binding;

import android.graphics.Bitmap;
import android.location.Location;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.FacebookStreamContainer;
import com.facebook.katana.binding.ProfileImage;
import com.facebook.katana.binding.ProfileImagesCache;
import com.facebook.katana.model.FacebookAlbum;
import com.facebook.katana.model.FacebookCheckin;
import com.facebook.katana.model.FacebookEvent;
import com.facebook.katana.model.FacebookGroup;
import com.facebook.katana.model.FacebookPhonebookContact;
import com.facebook.katana.model.FacebookPhoto;
import com.facebook.katana.model.FacebookPhotoComment;
import com.facebook.katana.model.FacebookPhotoTag;
import com.facebook.katana.model.FacebookPlace;
import com.facebook.katana.model.FacebookPost;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.model.FacebookUser;
import com.facebook.katana.model.FacebookUserFull;
import com.facebook.katana.model.FacebookVideoUploadResponse;
import com.facebook.katana.model.GeoRegion;
import com.facebook.katana.service.method.AudienceSettings;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AppSessionListener {

   public AppSessionListener() {}

   public void onCheckinComplete(AppSession var1, String var2, int var3, String var4, Exception var5, FacebookPost var6) {}

   public void onDownloadAlbumThumbnailComplete(AppSession var1, String var2, int var3, String var4, Exception var5, String var6) {}

   public void onDownloadPhotoFullComplete(AppSession var1, String var2, int var3, String var4, Exception var5, String var6, String var7) {}

   public void onDownloadPhotoRawComplete(AppSession var1, String var2, int var3, String var4, Exception var5, Bitmap var6) {}

   public void onDownloadPhotoThumbnailComplete(AppSession var1, String var2, int var3, String var4, Exception var5, String var6, String var7) {}

   public void onDownloadStreamPhotoComplete(AppSession var1, String var2, int var3, String var4, Exception var5, String var6, Bitmap var7) {}

   public void onEventGetMembersComplete(AppSession var1, String var2, int var3, String var4, Exception var5, long var6, Map<FacebookEvent.RsvpStatus, List<FacebookUser>> var8) {}

   public void onEventRsvpComplete(AppSession var1, String var2, int var3, String var4, Exception var5, long var6, boolean var8) {}

   public void onFlagPlaceComplete(AppSession var1, String var2, int var3, String var4, Exception var5) {}

   public void onFriendCheckinsComplete(AppSession var1, String var2, int var3, String var4, Exception var5, List<FacebookCheckin> var6) {}

   public void onFriendRequestRespondComplete(AppSession var1, String var2, int var3, String var4, Exception var5, long var6, boolean var8) {}

   public void onFriendRequestsMutualFriendsComplete(AppSession var1, String var2, int var3, String var4, Exception var5, Map<Long, List<Long>> var6) {}

   public void onFriendsAddFriendComplete(AppSession var1, String var2, int var3, String var4, Exception var5, List<Long> var6) {}

   public void onFriendsSyncComplete(AppSession var1, String var2, int var3, String var4, Exception var5) {}

   public void onGetAudienceSettingsComplete(AppSession var1, String var2, int var3, String var4, Exception var5, AudienceSettings var6) {}

   public void onGetGroupsComplete(AppSession var1, String var2, int var3, String var4, Exception var5, List<FacebookGroup> var6) {}

   public void onGetGroupsMembersComplete(AppSession var1, String var2, int var3, String var4, Exception var5, Map<Long, FacebookProfile> var6) {}

   public void onGetNearbyRegionsComplete(AppSession var1, String var2, int var3, String var4, Exception var5, List<GeoRegion> var6) {}

   public void onGetNotificationHistoryComplete(AppSession var1, String var2, int var3, String var4, Exception var5) {}

   public void onGetPageFanStatusComplete(AppSession var1, String var2, int var3, String var4, Exception var5, long var6, boolean var8) {}

   public void onGetPlacesNearbyComplete(AppSession var1, String var2, int var3, String var4, Exception var5, List<FacebookPlace> var6, List<GeoRegion> var7, Location var8) {}

   public void onGetProfileComplete(AppSession var1, String var2, int var3, String var4, Exception var5, FacebookProfile var6) {}

   public void onGkSettingsGetComplete(AppSession var1, String var2, int var3, String var4, Exception var5, String var6, boolean var7) {}

   public void onHomeStreamUpdated(AppSession var1) {}

   public void onLoginComplete(AppSession var1, String var2, int var3, String var4, Exception var5) {}

   public void onLogoutComplete(AppSession var1, String var2, int var3, String var4, Exception var5) {}

   public void onMailboxDeleteThreadComplete(AppSession var1, String var2, int var3, String var4, Exception var5, long[] var6) {}

   public void onMailboxGetThreadMessagesComplete(AppSession var1, String var2, int var3, String var4, Exception var5, int var6, long var7) {}

   public void onMailboxMarkThreadComplete(AppSession var1, String var2, int var3, String var4, Exception var5, long[] var6, boolean var7) {}

   public void onMailboxReplyComplete(AppSession var1, String var2, int var3, String var4, Exception var5, long var6) {}

   public void onMailboxSendComplete(AppSession var1, String var2, int var3, String var4, Exception var5) {}

   public void onMailboxSyncComplete(AppSession var1, String var2, int var3, String var4, Exception var5, int var6) {}

   public void onMarkGroupReadComplete(AppSession var1, String var2, int var3, String var4, Exception var5, long var6) {}

   public void onPagesAddFanComplete(AppSession var1, String var2, int var3, String var4, Exception var5, boolean var6) {}

   public <typeClass extends Object> void onPagesGetInfoComplete(AppSession var1, String var2, int var3, String var4, Exception var5, long var6, typeClass var8) {}

   public void onPagesSearchComplete(AppSession var1, String var2, int var3, String var4, Exception var5, int var6, int var7) {}

   public void onPhonebookLookupComplete(AppSession var1, String var2, int var3, String var4, Exception var5, List<FacebookPhonebookContact> var6) {}

   public void onPhotoAddCommentComplete(AppSession var1, String var2, int var3, String var4, Exception var5, String var6, FacebookPhotoComment var7) {}

   public void onPhotoAddTagsComplete(AppSession var1, String var2, int var3, String var4, Exception var5, String var6, List<FacebookPhotoTag> var7) {}

   public void onPhotoCreateAlbumComplete(AppSession var1, String var2, int var3, String var4, Exception var5, FacebookAlbum var6) {}

   public void onPhotoDecodeComplete(AppSession var1, Bitmap var2, String var3) {}

   public void onPhotoDeleteAlbumComplete(AppSession var1, String var2, int var3, String var4, Exception var5, String var6) {}

   public void onPhotoDeletePhotoComplete(AppSession var1, String var2, int var3, String var4, Exception var5, String var6, String var7) {}

   public void onPhotoEditAlbumComplete(AppSession var1, String var2, int var3, String var4, Exception var5, String var6) {}

   public void onPhotoEditPhotoComplete(AppSession var1, String var2, int var3, String var4, Exception var5, String var6, String var7) {}

   public void onPhotoGetAlbumsComplete(AppSession var1, String var2, int var3, String var4, Exception var5, String[] var6, long var7) {}

   public void onPhotoGetCommentsComplete(AppSession var1, String var2, int var3, String var4, Exception var5, String var6, List<FacebookPhotoComment> var7, boolean var8) {}

   public void onPhotoGetPhotosComplete(AppSession var1, String var2, int var3, String var4, Exception var5, String var6, String[] var7, long var8) {}

   public void onPhotoGetTagsComplete(AppSession var1, String var2, int var3, String var4, Exception var5, List<FacebookPhotoTag> var6) {}

   public void onPhotoUploadComplete(AppSession var1, String var2, int var3, String var4, Exception var5, int var6, FacebookPhoto var7, String var8, long var9, long var11, long var13) {}

   public void onPhotosPublishComplete(AppSession var1, String var2, int var3, String var4, Exception var5, List<String> var6, int var7, int var8) {}

   public void onPlacesCreateComplete(AppSession var1, String var2, int var3, String var4, Exception var5, long var6) {}

   public void onPlacesEditCheckinComplete(AppSession var1, String var2, int var3, String var4, Exception var5, long var6, String var8, Set<Long> var9) {}

   public void onPlacesRemoveTagComplete(AppSession var1, String var2, int var3, String var4, Exception var5, FacebookPost var6, long var7) {}

   public void onProfileImageDownloaded(AppSession var1, String var2, int var3, String var4, Exception var5, ProfileImage var6, ProfileImagesCache var7) {}

   public void onProfileImageLoaded(AppSession var1, ProfileImage var2) {}

   public void onSetTaggingOptInStatusComplete(AppSession var1, String var2, int var3, String var4, Exception var5) {}

   public void onStreamAddCommentComplete(AppSession var1, String var2, int var3, String var4, Exception var5, String var6, FacebookPost.Comment var7) {}

   public void onStreamAddLikeComplete(AppSession var1, String var2, int var3, String var4, Exception var5, String var6) {}

   public void onStreamGetCommentsComplete(AppSession var1, String var2, int var3, String var4, Exception var5, String var6) {}

   public void onStreamGetComplete(AppSession var1, String var2, int var3, String var4, Exception var5, long var6, int var8, FacebookStreamContainer var9, List<FacebookPost> var10) {}

   public void onStreamGetComplete(AppSession var1, String var2, int var3, String var4, Exception var5, long var6, long[] var8, long var9, long var11, int var13, int var14, List<FacebookPost> var15, FacebookStreamContainer var16) {}

   public void onStreamPublishComplete(AppSession var1, String var2, int var3, String var4, Exception var5, FacebookPost var6) {}

   public void onStreamRemoveCommentComplete(AppSession var1, String var2, int var3, String var4, Exception var5, String var6) {}

   public void onStreamRemoveLikeComplete(AppSession var1, String var2, int var3, String var4, Exception var5, String var6) {}

   public void onStreamRemovePostComplete(AppSession var1, String var2, int var3, String var4, Exception var5) {}

   public void onUserGetEventsComplete(AppSession var1, String var2, int var3, String var4, Exception var5, List<FacebookEvent> var6) {}

   public void onUserGetFriendRequestsComplete(AppSession var1, String var2, int var3, String var4, Exception var5, Map<Long, FacebookUser> var6) {}

   public void onUserSetContactInfoComplete(AppSession var1, String var2, int var3, String var4, Exception var5) {}

   public void onUsersGetInfoComplete(AppSession var1, String var2, int var3, String var4, Exception var5, long var6, FacebookUser var8, boolean var9) {}

   public void onUsersGetInfoComplete(AppSession var1, String var2, int var3, String var4, Exception var5, long var6, FacebookUserFull var8, boolean var9) {}

   public void onUsersGetInfoComplete(AppSession var1, String var2, int var3, String var4, Exception var5, List<FacebookUser> var6) {}

   public void onUsersInviteComplete(AppSession var1, String var2, int var3, String var4, Exception var5, List<String> var6) {}

   public void onUsersSearchComplete(AppSession var1, String var2, int var3, String var4, Exception var5, int var6, int var7) {}

   public void onVideoUploadComplete(AppSession var1, String var2, int var3, String var4, Exception var5, FacebookVideoUploadResponse var6, String var7) {}

   public interface GetObjectListener<ObjectType extends Object> {

      void onLoadError(Exception var1);

      void onObjectLoaded(ObjectType var1);
   }
}
