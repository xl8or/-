package com.facebook.katana.model;

import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookCheckinDetails;
import com.facebook.katana.model.FacebookPhoto;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.model.FacebookVideo;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.jsonmirror.JMAutogen;
import com.facebook.katana.util.jsonmirror.JMCachingDictDestination;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import com.facebook.katana.util.jsonmirror.types.JMLong;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

public class FacebookPost extends JMCachingDictDestination {

   public static final int TYPE_CHECKIN_POST = 4;
   public static final int TYPE_LINK_ATTACHMENT_POST = 1;
   public static final int TYPE_PHOTO_ATTACHMENT_POST = 2;
   public static final int TYPE_STATUS_POST = 0;
   public static final int TYPE_UNSUPPORTED = 255;
   public static final int TYPE_VIDEO_ATTACHMENT_POST = 3;
   public static Comparator<FacebookPost> timeComparator = new FacebookPost.1();
   @JMAutogen.InferredType(
      jsonFieldName = "actor_id"
   )
   public final long actorId;
   @JMAutogen.InferredType(
      jsonFieldName = "app_id"
   )
   public final long appId;
   @JMAutogen.InferredType(
      jsonFieldName = "attribution"
   )
   public final String attribution;
   @JMAutogen.InferredType(
      jsonFieldName = "created_time"
   )
   public final long createdTime;
   @JMAutogen.InferredType(
      jsonFieldName = "attachment"
   )
   private final FacebookPost.Attachment mAttachment;
   @JMAutogen.InferredType(
      jsonFieldName = "comments"
   )
   private final FacebookPost.Comments mComments;
   @JMAutogen.InferredType(
      jsonFieldName = "likes"
   )
   private final FacebookPost.Likes mLikes;
   private int mPostType;
   private FacebookProfile mProfile;
   @JMAutogen.ListType(
      jsonFieldName = "tagged_ids",
      listElementTypes = {JMLong.class}
   )
   public final List<Long> mTaggedIds;
   private Set<FacebookProfile> mTaggedProfiles;
   private FacebookProfile mTargetProfile;
   private Object mUserObject;
   @JMAutogen.ExplicitType(
      jsonFieldName = "message",
      type = StringUtils.JMNulledString.class
   )
   public final String message;
   @JMAutogen.InferredType(
      jsonFieldName = "post_id"
   )
   public final String postId;
   @JMAutogen.InferredType(
      jsonFieldName = "target_id"
   )
   public final long targetId;


   private FacebookPost() {
      this.postId = null;
      this.appId = 65535L;
      this.actorId = 65535L;
      this.targetId = 65535L;
      this.message = null;
      this.mAttachment = null;
      this.mLikes = null;
      this.mComments = null;
      this.mTaggedIds = null;
      this.createdTime = 65535L;
      this.attribution = null;
   }

   public FacebookPost(String var1, long var2, long var4, long var6, String var8, FacebookPost.Attachment var9, String var10) {
      this.postId = var1;
      this.appId = var2;
      this.actorId = var4;
      this.targetId = var6;
      this.message = var8;
      this.mAttachment = var9;
      FacebookPost.Likes var11 = new FacebookPost.Likes((FacebookPost.1)null);
      this.mLikes = var11;
      FacebookPost.Comments var12 = new FacebookPost.Comments();
      this.mComments = var12;
      this.mTaggedIds = null;
      long var13 = System.currentTimeMillis() / 1000L;
      this.createdTime = var13;
      this.attribution = var10;
      this.postprocess();
   }

   public FacebookPost(String var1, long var2, long var4, long var6, String var8, FacebookPost.Attachment var9, Set<Long> var10, Set<FacebookProfile> var11, String var12) {
      this.postId = var1;
      this.appId = var2;
      this.actorId = var4;
      this.targetId = var6;
      this.message = var8;
      this.mAttachment = var9;
      FacebookPost.Likes var13 = new FacebookPost.Likes((FacebookPost.1)null);
      this.mLikes = var13;
      FacebookPost.Comments var14 = new FacebookPost.Comments();
      this.mComments = var14;
      if(var10 != null) {
         ArrayList var15 = new ArrayList(var10);
         this.mTaggedIds = var15;
      } else {
         this.mTaggedIds = null;
      }

      this.mTaggedProfiles = var11;
      long var19 = System.currentTimeMillis() / 1000L;
      this.createdTime = var19;
      this.attribution = var12;
      this.postprocess();
   }

   public static FacebookPost parseJson(JsonParser var0) throws FacebookApiException, JsonParseException, IOException, JMException {
      return (FacebookPost)JMParser.parseObjectJson(var0, FacebookPost.class);
   }

   public void addComment(FacebookPost.Comment var1) {
      this.mComments.addComment(var1);
   }

   public void buildTaggedProfiles(Map<Long, FacebookProfile> var1) {
      HashSet var2 = new HashSet();
      Iterator var3 = this.mTaggedIds.iterator();

      while(var3.hasNext()) {
         Long var4 = Long.valueOf(((Long)var3.next()).longValue());
         FacebookProfile var5 = (FacebookProfile)var1.get(var4);
         if(var5 != null) {
            var2.add(var5);
         }
      }

      Set var7 = Collections.unmodifiableSet(var2);
      this.mTaggedProfiles = var7;
   }

   public boolean canInteractWithFeedback() {
      boolean var1;
      if(!this.getComments().canPost && !this.getLikes().canLike && this.getComments().getTotalCount() <= 0) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public void deleteComment(String var1) {
      this.mComments.deleteComment(var1);
   }

   public FacebookPost.Attachment getAttachment() {
      return this.mAttachment;
   }

   public FacebookPost.Comments getComments() {
      return this.mComments;
   }

   public FacebookPost.Likes getLikes() {
      return this.mLikes;
   }

   public int getPostType() {
      return this.mPostType;
   }

   public FacebookProfile getProfile() {
      return this.mProfile;
   }

   public Set<FacebookProfile> getTaggedProfiles() {
      return this.mTaggedProfiles;
   }

   public FacebookProfile getTargetProfile() {
      return this.mTargetProfile;
   }

   public Object getUserObject() {
      return this.mUserObject;
   }

   protected void postprocess() {
      if(this.mAttachment != null) {
         List var1 = this.mAttachment.getMediaItems();
         if(this.mAttachment.mCheckinDetails != null) {
            this.mPostType = 4;
         } else if(var1 != null && var1.size() > 0) {
            String var2 = ((FacebookPost.Attachment.MediaItem)var1.get(0)).type;
            if(var2.equals("link")) {
               String var3 = ((FacebookPost.Attachment.MediaItem)var1.get(0)).href;
               if(var3 == null) {
                  this.mPostType = -1;
               } else if(var3.contains("/apps.facebook.com")) {
                  this.mPostType = -1;
               } else if(var3.contains(".applatform.com/")) {
                  this.mPostType = -1;
               } else {
                  this.mPostType = 1;
               }
            } else if(var2.equals("photo")) {
               this.mPostType = 2;
            } else if(var2.equals("video")) {
               this.mPostType = 3;
            } else {
               this.mPostType = -1;
            }
         } else if(this.mAttachment.href != null) {
            if(this.mAttachment.name == null && this.mAttachment.caption == null && this.mAttachment.description == null) {
               if(this.message != null) {
                  this.mPostType = 0;
               } else {
                  this.mPostType = -1;
               }
            } else {
               this.mPostType = 1;
            }
         } else if(this.message != null) {
            this.mPostType = 0;
         } else {
            this.mPostType = -1;
         }
      } else if(this.message != null) {
         this.mPostType = 0;
      } else {
         this.mPostType = -1;
      }
   }

   public void setProfile(FacebookProfile var1) {
      this.mProfile = var1;
   }

   public void setTargetProfile(FacebookProfile var1) {
      this.mTargetProfile = var1;
   }

   public void setUserLikes(boolean var1) {
      this.mLikes.setUserLikes(var1);
   }

   public void setUserObject(Object var1) {
      this.mUserObject = var1;
   }

   public void updateComments(List<FacebookPost.Comment> var1) {
      this.mComments.updateComments(var1);
   }

   @JMAutogen.IgnoreUnexpectedJsonFields
   public static class Likes extends JMCachingDictDestination {

      @JMAutogen.InferredType(
         jsonFieldName = "can_like"
      )
      public final boolean canLike;
      @JMAutogen.InferredType(
         jsonFieldName = "count"
      )
      private int mCount;
      private long mFriendUserId;
      @JMAutogen.ListType(
         jsonFieldName = "friends",
         listElementTypes = {JMLong.class}
      )
      private List<Long> mFriendUsers;
      private long mSampleUserId;
      @JMAutogen.ListType(
         jsonFieldName = "sample",
         listElementTypes = {JMLong.class}
      )
      private List<Long> mSampleUsers;
      @JMAutogen.InferredType(
         jsonFieldName = "user_likes"
      )
      private boolean mUserLikes;


      private Likes() {
         this.mUserLikes = (boolean)0;
         this.canLike = (boolean)1;
         this.mCount = 0;
         this.mSampleUserId = 65535L;
         this.mFriendUserId = 65535L;
      }

      // $FF: synthetic method
      Likes(FacebookPost.1 var1) {
         this();
      }

      public boolean doesUserLike() {
         return this.mUserLikes;
      }

      public int getCount() {
         return this.mCount;
      }

      public long getFriendUserId() {
         return this.mFriendUserId;
      }

      public long getSampleUserId() {
         return this.mSampleUserId;
      }

      protected void postprocess() {
         if(this.mSampleUsers != null && this.mSampleUsers.size() != 0) {
            long var1 = ((Long)this.mSampleUsers.get(0)).longValue();
            this.mSampleUserId = var1;
         }

         this.mSampleUsers = null;
         if(this.mFriendUsers != null && this.mFriendUsers.size() != 0) {
            long var3 = ((Long)this.mFriendUsers.get(0)).longValue();
            this.mFriendUserId = var3;
         }

         this.mFriendUsers = null;
      }

      public void setUserLikes(boolean var1) {
         if(this.mUserLikes != var1) {
            if(var1) {
               int var2 = this.mCount + 1;
               this.mCount = var2;
            } else {
               int var3 = this.mCount - 1;
               this.mCount = var3;
            }

            this.mUserLikes = var1;
         }
      }
   }

   static class 1 implements Comparator<FacebookPost> {

      1() {}

      public int compare(FacebookPost var1, FacebookPost var2) {
         long var3 = var1.createdTime;
         long var5 = var2.createdTime;
         byte var7;
         if(var3 > var5) {
            var7 = -1;
         } else {
            long var8 = var1.createdTime;
            long var10 = var2.createdTime;
            if(var8 == var10) {
               var7 = 0;
            } else {
               var7 = 1;
            }
         }

         return var7;
      }
   }

   public static class Comments extends JMCachingDictDestination {

      @JMAutogen.InferredType(
         jsonFieldName = "can_post"
      )
      public final boolean canPost;
      @JMAutogen.InferredType(
         jsonFieldName = "can_remove"
      )
      public final boolean canRemove;
      @JMAutogen.ListType(
         jsonFieldName = "comment_list",
         listElementTypes = {FacebookPost.Comment.class}
      )
      private final List<FacebookPost.Comment> mComments;
      @JMAutogen.InferredType(
         jsonFieldName = "count"
      )
      private int mTotalCount;


      public Comments() {
         ArrayList var1 = new ArrayList();
         this.mComments = var1;
         this.canRemove = (boolean)1;
         this.canPost = (boolean)1;
         this.mTotalCount = 0;
      }

      public void addComment(FacebookPost.Comment var1) {
         this.mComments.add(var1);
         int var3 = this.mTotalCount + 1;
         this.mTotalCount = var3;
      }

      public void deleteComment(String var1) {
         Iterator var2 = this.mComments.iterator();

         while(var2.hasNext()) {
            FacebookPost.Comment var3 = (FacebookPost.Comment)var2.next();
            if(var3.id.equals(var1)) {
               this.mComments.remove(var3);
               if(this.mTotalCount <= 0) {
                  return;
               }

               int var5 = this.mTotalCount - 1;
               this.mTotalCount = var5;
               return;
            }
         }

      }

      public List<FacebookPost.Comment> getComments() {
         return this.mComments;
      }

      public int getCount() {
         return this.mComments.size();
      }

      public int getTotalCount() {
         return this.mTotalCount;
      }

      protected void postprocess() throws JMException {
         if(this.mComments != null) {
            List var1 = this.mComments;
            ArrayList var2 = new ArrayList(var1);
            this.setList("mComments", var2);
         }
      }

      public void updateComments(List<FacebookPost.Comment> var1) {
         this.mComments.clear();
         this.mComments.addAll(var1);
         int var3 = this.mComments.size();
         this.mTotalCount = var3;
      }
   }

   @JMAutogen.IgnoreUnexpectedJsonFields
   public static class Attachment extends JMCachingDictDestination {

      @JMAutogen.ExplicitType(
         jsonFieldName = "caption",
         type = StringUtils.JMNulledString.class
      )
      public final String caption;
      @JMAutogen.ExplicitType(
         jsonFieldName = "description",
         type = StringUtils.JMNulledStrippedString.class
      )
      public final String description;
      @JMAutogen.InferredType(
         jsonFieldName = "href"
      )
      public final String href;
      @JMAutogen.InferredType(
         jsonFieldName = "fb_checkin"
      )
      public final FacebookCheckinDetails mCheckinDetails;
      @JMAutogen.ListType(
         jsonFieldName = "media",
         listElementTypes = {FacebookPost.Attachment.MediaItem.class}
      )
      private final List<FacebookPost.Attachment.MediaItem> mMediaItems;
      @JMAutogen.ExplicitType(
         jsonFieldName = "name",
         type = StringUtils.JMNulledString.class
      )
      public final String name;


      private Attachment() {
         this.name = null;
         this.caption = null;
         this.description = null;
         this.href = null;
         this.mMediaItems = null;
         this.mCheckinDetails = null;
      }

      public Attachment(String var1, FacebookCheckinDetails var2) {
         this.name = var1;
         this.caption = null;
         this.description = null;
         this.href = null;
         List var3 = Collections.emptyList();
         this.mMediaItems = var3;
         this.mCheckinDetails = var2;
      }

      public List<FacebookPost.Attachment.MediaItem> getMediaItems() {
         return this.mMediaItems;
      }

      @JMAutogen.IgnoreUnexpectedJsonFields
      public static class MediaItem extends JMCachingDictDestination {

         public static final String TYPE_FLASH = "flash";
         public static final String TYPE_LINK = "link";
         public static final String TYPE_MP3 = "mp3";
         public static final String TYPE_PHOTO = "photo";
         public static final String TYPE_VIDEO = "video";
         @JMAutogen.InferredType(
            jsonFieldName = "href"
         )
         public final String href = null;
         @JMAutogen.InferredType(
            jsonFieldName = "photo"
         )
         private final FacebookPhoto mPhoto = null;
         @JMAutogen.InferredType(
            jsonFieldName = "video"
         )
         private final FacebookVideo mVideo = null;
         @JMAutogen.ExplicitType(
            jsonFieldName = "src",
            type = StringUtils.JMNulledString.class
         )
         public final String src = null;
         @JMAutogen.InferredType(
            jsonFieldName = "type"
         )
         public final String type = null;


         private MediaItem() {}

         public FacebookPhoto getPhoto() {
            return this.mPhoto;
         }

         public FacebookVideo getVideo() {
            return this.mVideo;
         }
      }
   }

   public static class Comment extends JMCachingDictDestination {

      @JMAutogen.InferredType(
         jsonFieldName = "fromid"
      )
      public final long fromId;
      @JMAutogen.InferredType(
         jsonFieldName = "id"
      )
      public final String id;
      private FacebookProfile mProfile;
      @JMAutogen.InferredType(
         jsonFieldName = "text"
      )
      public final String text;
      @JMAutogen.InferredType(
         jsonFieldName = "time"
      )
      public final long time;


      private Comment() {
         this.id = null;
         this.fromId = 65535L;
         this.time = 65535L;
         this.text = null;
      }

      public Comment(String var1, long var2, long var4, String var6) {
         this.id = var1;
         this.fromId = var2;
         this.time = var4;
         this.text = var6;
      }

      public static FacebookPost.Comment parseJson(JsonParser var0) throws JsonParseException, IOException, JMException {
         return (FacebookPost.Comment)JMParser.parseObjectJson(var0, FacebookPost.Comment.class);
      }

      public FacebookProfile getProfile() {
         return this.mProfile;
      }

      public void setProfile(FacebookProfile var1) {
         this.mProfile = var1;
      }
   }
}
