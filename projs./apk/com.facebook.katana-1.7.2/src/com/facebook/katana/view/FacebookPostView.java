package com.facebook.katana.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.facebook.katana.activity.profilelist.TaggedUsersActivity;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.ProfileImage;
import com.facebook.katana.binding.ProfileImagesCache;
import com.facebook.katana.binding.StreamPhotosCache;
import com.facebook.katana.model.FacebookPost;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.service.method.PlacesRemoveTag;
import com.facebook.katana.ui.SaneLinkMovementMethod;
import com.facebook.katana.util.ApplicationUtils;
import com.facebook.katana.util.ImageUtils;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.Tuple;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class FacebookPostView {

   // $FF: synthetic field
   static final boolean $assertionsDisabled = false;
   private static final int MAX_PHOTOS = 3;


   static {
      byte var0;
      if(!FacebookPostView.class.desiredAssertionStatus()) {
         var0 = 1;
      } else {
         var0 = 0;
      }

      $assertionsDisabled = (boolean)var0;
   }

   public FacebookPostView() {}

   private static SpannableStringBuilder addTaggedUser(Context var0, SpannableStringBuilder var1, FacebookProfile var2, boolean var3) {
      int var4 = var1.length();
      String var5 = var2.mDisplayName;
      var1.append(var5);
      int var7 = var1.length();
      StyleSpan var8 = new StyleSpan(1);
      var1.setSpan(var8, var4, var7, 33);
      int var9 = var0.getResources().getColor(2131165191);
      ForegroundColorSpan var10 = new ForegroundColorSpan(var9);
      var1.setSpan(var10, var4, var7, 33);
      if(var3) {
         FacebookPostView.3 var11 = new FacebookPostView.3(var0, var2);
         var1.setSpan(var11, var4, var7, 33);
      }

      return var1;
   }

   private static Spannable buildStatus(Context var0, FacebookPost var1, FacebookPostView.Extras var2) {
      SpannableStringBuilder var3 = new SpannableStringBuilder();
      FacebookProfile var4 = var1.getProfile();
      FacebookProfile var5 = var1.getTargetProfile();
      long var6 = var4.mId;
      long var8 = var2.mStreamId;
      byte var10;
      if(var6 != var8) {
         var10 = 1;
      } else {
         var10 = 0;
      }

      addTaggedUser(var0, var3, var4, (boolean)var10);
      if(var5 != null) {
         long var12 = var5.mId;
         long var14 = var2.mStreamId;
         if(var12 != var14) {
            SpannableStringBuilder var16 = var3.append(" > ");
            long var17 = var5.mId;
            long var19 = var2.mStreamId;
            if(var17 != var19) {
               var10 = 1;
            } else {
               var10 = 0;
            }

            addTaggedUser(var0, var3, var5, (boolean)var10);
         }
      }

      int var22 = var3.length();
      if(var1.message != null) {
         SpannableStringBuilder var23 = var3.append(' ');
         String var24 = var1.message;
         var23.append(var24);
      }

      StyleSpan var26 = new StyleSpan(1);
      var3.setSpan(var26, 0, var22, 33);
      int var27 = var0.getResources().getColor(2131165191);
      ForegroundColorSpan var28 = new ForegroundColorSpan(var27);
      var3.setSpan(var28, 0, var22, 33);
      return var3;
   }

   private static Spannable buildTaggedText(Context var0, Set<FacebookProfile> var1) {
      if(!$assertionsDisabled && var1.size() <= 0) {
         throw new AssertionError();
      } else {
         SpannableStringBuilder var2 = new SpannableStringBuilder();
         if(var1.size() == 1) {
            Object[] var3 = new Object[]{""};
            String var4 = var0.getString(2131362283, var3);
            var2.append(var4);
            FacebookProfile var6 = (FacebookProfile)var1.iterator().next();
            addTaggedUser(var0, var2, var6, (boolean)1);
         } else {
            int var8 = var2.length();
            Object[] var9 = new Object[1];
            Integer var10 = Integer.valueOf(var1.size());
            var9[0] = var10;
            String var11 = var0.getString(2131362284, var9);
            var2.append(var11);
            int var13 = var2.length();
            StyleSpan var14 = new StyleSpan(1);
            var2.setSpan(var14, var8, var13, 33);
            int var15 = var0.getResources().getColor(2131165191);
            ForegroundColorSpan var16 = new ForegroundColorSpan(var15);
            var2.setSpan(var16, var8, var13, 33);
            FacebookPostView.2 var17 = new FacebookPostView.2(var0, var1);
            var2.setSpan(var17, var8, var13, 33);
         }

         return var2;
      }
   }

   public static View inflatePostView(FacebookPost var0, LayoutInflater var1) {
      View var2 = var1.inflate(2130903085, (ViewGroup)null);
      FacebookPostView.ViewHolder var3 = new FacebookPostView.ViewHolder();
      ViewStub var4 = (ViewStub)var2.findViewById(2131624074);
      View var5;
      switch(var0.getPostType()) {
      case 0:
         ((ViewGroup)var4.getParent()).removeView(var4);
         break;
      case 1:
         var4.setLayoutResource(2130903098);
         View var27 = var4.inflate();
         TextView var28 = (TextView)var2.findViewById(2131623985);
         var3.mAttachmentName = var28;
         TextView var29 = (TextView)var2.findViewById(2131623986);
         var3.mAttachmentCaption = var29;
         TextView var30 = (TextView)var2.findViewById(2131624109);
         var3.mAttachmentDescription = var30;
         LinearLayout[] var31 = var3.mImageHolders;
         LinearLayout var32 = (LinearLayout)var2.findViewById(2131623984);
         var31[0] = var32;
         break;
      case 2:
         var4.setLayoutResource(2130903124);
         View var12 = var4.inflate();
         TextView var13 = (TextView)var2.findViewById(2131623985);
         var3.mAttachmentName = var13;
         TextView var14 = (TextView)var2.findViewById(2131623986);
         var3.mAttachmentCaption = var14;
         LinearLayout[] var15 = var3.mImageHolders;
         LinearLayout var16 = (LinearLayout)var2.findViewById(2131624166);
         var15[0] = var16;
         LinearLayout[] var17 = var3.mImageHolders;
         LinearLayout var18 = (LinearLayout)var2.findViewById(2131624167);
         var17[1] = var18;
         LinearLayout[] var19 = var3.mImageHolders;
         LinearLayout var20 = (LinearLayout)var2.findViewById(2131624168);
         var19[2] = var20;
         break;
      case 3:
         var4.setLayoutResource(2130903181);
         View var21 = var4.inflate();
         TextView var22 = (TextView)var2.findViewById(2131623985);
         var3.mAttachmentName = var22;
         TextView var23 = (TextView)var2.findViewById(2131623986);
         var3.mAttachmentCaption = var23;
         TextView var24 = (TextView)var2.findViewById(2131624109);
         var3.mAttachmentDescription = var24;
         LinearLayout[] var25 = var3.mImageHolders;
         LinearLayout var26 = (LinearLayout)var2.findViewById(2131623984);
         var25[0] = var26;
         break;
      case 4:
         var4.setLayoutResource(2130903055);
         View var33 = var4.inflate();
         TextView var34 = (TextView)var2.findViewById(2131623985);
         var3.mAttachmentName = var34;
         TextView var35 = (TextView)var2.findViewById(2131623986);
         var3.mAttachmentCaption = var35;
         LinearLayout[] var36 = var3.mImageHolders;
         LinearLayout var37 = (LinearLayout)var2.findViewById(2131623984);
         var36[0] = var37;
         TextView var38 = (TextView)((ViewStub)var2.findViewById(2131624077)).inflate();
         var3.mRemoveTag = var38;
         break;
      default:
         var5 = null;
         return var5;
      }

      ImageView var6 = (ImageView)var2.findViewById(2131624072);
      var3.mUserImageView = var6;
      TextView var7 = (TextView)var2.findViewById(2131624073);
      var3.mTextTextView = var7;
      setMovementMethod(var3.mTextTextView);
      TextView var8 = (TextView)var2.findViewById(2131624075);
      var3.mTaggedUsers = var8;
      setMovementMethod(var3.mTaggedUsers);
      TextView var9 = (TextView)var2.findViewById(2131624076);
      var3.mTimeTextView = var9;
      TextView var10 = (TextView)var2.findViewById(2131624190);
      var3.mCommentsCountView = var10;
      TextView var11 = (TextView)var2.findViewById(2131624191);
      var3.mLikesCountView = var11;
      var2.setTag(var3);
      var5 = var2;
      return var5;
   }

   public static void renderPostView(Context var0, FacebookPost var1, FacebookPostView.ViewHolder var2, FacebookPostView.Extras var3) {
      FacebookPost.Attachment var83;
      label186:
      switch(var1.getPostType()) {
      case 0:
         var2.mPhotoCount = 0;
         break;
      case 1:
         LinearLayout var48 = var2.mImageHolders[0];
         var83 = var1.getAttachment();
         if(var83.getMediaItems().size() > 0) {
            FacebookPost.Attachment.MediaItem var49 = (FacebookPost.Attachment.MediaItem)var83.getMediaItems().get(0);
            var48.setTag(var49);
            String var50 = var49.src;
            if(var50 != null) {
               Bitmap var51 = var3.mPhotosCache.get(var0, var50);
               if(var51 == null) {
                  var51 = var3.mPhotoDownloadingBitmap;
               }

               ImageUtils.formatPhotoStreamImageView(var0, var48, var51);
            } else {
               ImageUtils.formatPhotoStreamImageView(var0, var48, (Bitmap)null);
            }

            OnClickListener var52 = var3.mMediaItemClickListener;
            var48.setOnClickListener(var52);
            var48.setVisibility(0);
            var2.setImageUrl(0, var50);
            var2.mPhotoCount = 1;
         } else {
            var48.setVisibility(8);
            var2.mPhotoCount = 0;
         }

         TextView var53 = var2.mAttachmentName;
         String var54 = var83.name;
         if(var54 != null && var54.length() > 0) {
            var53.setVisibility(0);
            var53.setText(var54);
         } else {
            var53.setVisibility(8);
         }

         TextView var55 = var2.mAttachmentCaption;
         String var56 = var83.caption;
         if(var56 != null && var56.length() > 0) {
            var55.setVisibility(0);
            var55.setText(var56);
         } else {
            var55.setVisibility(8);
         }

         if(var83.description != null) {
            var2.mAttachmentDescription.setVisibility(0);
            TextView var57 = var2.mAttachmentDescription;
            String var58 = var83.description;
            var57.setText(var58);
         } else {
            var2.mAttachmentDescription.setVisibility(8);
         }
         break;
      case 2:
         FacebookPost.Attachment var27 = var1.getAttachment();
         TextView var28 = var2.mAttachmentName;
         String var29 = var27.name;
         if(var29 != null && var29.length() > 0) {
            var28.setVisibility(0);
            var28.setText(var29);
         } else {
            var28.setVisibility(8);
         }

         TextView var30 = var2.mAttachmentCaption;
         String var31 = var27.caption;
         if(var31 != null && var31.length() > 0) {
            var30.setVisibility(0);
            var30.setText(var31);
         } else {
            var30.setVisibility(8);
         }

         int var32 = 0;

         for(Iterator var33 = var27.getMediaItems().iterator(); var33.hasNext(); ++var32) {
            FacebookPost.Attachment.MediaItem var82 = (FacebookPost.Attachment.MediaItem)var33.next();
            if(var32 < 3) {
               setupImageViewAtIndex(var0, var2, var82, var3, var32);
            }
         }

         int var34 = Math.min(var32, 3);
         var2.mPhotoCount = var34;
         int var35 = var32;

         while(true) {
            if(var35 >= 3) {
               break label186;
            }

            var2.mImageHolders[var35].setVisibility(8);
            ++var35;
         }
      case 3:
         var83 = var1.getAttachment();
         FacebookPost.Attachment.MediaItem var37 = (FacebookPost.Attachment.MediaItem)var83.getMediaItems().get(0);
         LinearLayout var38 = var2.mImageHolders[0];
         var38.setTag(var37);
         OnClickListener var39 = var3.mMediaItemClickListener;
         var38.setOnClickListener(var39);
         String var40 = var37.src;
         if(var40 != null) {
            Bitmap var41 = var3.mPhotosCache.get(var0, var40);
            if(var41 == null) {
               var41 = var3.mPhotoDownloadingBitmap;
            }

            ImageUtils.formatPhotoStreamImageView(var0, var38, var41);
         } else {
            ImageUtils.formatPhotoStreamImageView(var0, var38, (Bitmap)null);
         }

         var2.setImageUrl(0, var40);
         var2.mPhotoCount = 1;
         TextView var42 = var2.mAttachmentName;
         String var43 = var83.name;
         if(var43 != null && var43.length() > 0) {
            var42.setVisibility(0);
            var42.setText(var43);
         } else {
            var42.setVisibility(8);
         }

         TextView var44 = var2.mAttachmentCaption;
         String var45 = var83.caption;
         if(var45 != null && var45.length() > 0) {
            var44.setVisibility(0);
            var44.setText(var45);
         } else {
            var44.setVisibility(8);
         }

         if(var83.description != null) {
            var2.mAttachmentDescription.setVisibility(0);
            TextView var46 = var2.mAttachmentDescription;
            String var47 = var83.description;
            var46.setText(var47);
         } else {
            var2.mAttachmentDescription.setVisibility(8);
         }
         break;
      case 4:
         LinearLayout var59 = var2.mImageHolders[0];
         FacebookPost.Attachment var60 = var1.getAttachment();
         if(var60.getMediaItems().size() > 0) {
            FacebookPost.Attachment.MediaItem var61 = (FacebookPost.Attachment.MediaItem)var60.getMediaItems().get(0);
            var59.setTag(var61);
            String var62 = var61.src;
            if(var62 != null) {
               Bitmap var63 = var3.mPhotosCache.get(var0, var62);
               if(var63 == null) {
                  var63 = var3.mPhotoDownloadingBitmap;
               }

               ImageUtils.formatPhotoStreamImageView(var0, var59, var63);
            } else {
               ImageUtils.formatPhotoStreamImageView(var0, var59, (Bitmap)null);
            }

            OnClickListener var64 = var3.mMediaItemClickListener;
            var59.setOnClickListener(var64);
            var59.setVisibility(0);
            var2.setImageUrl(0, var62);
            var2.mPhotoCount = 1;
         } else {
            var59.setVisibility(8);
            var2.mPhotoCount = 0;
         }

         TextView var65 = var2.mAttachmentName;
         String var36 = var60.name;
         if(var36 != null && var36.length() > 0) {
            var65.setVisibility(0);
            var65.setText(var36);
         } else {
            var65.setVisibility(8);
         }

         TextView var66 = var2.mAttachmentCaption;
         String var67 = var60.caption;
         if(var67 != null && var67.length() > 0) {
            var66.setVisibility(0);
            var66.setText(var67);
         } else {
            var66.setVisibility(8);
         }

         if(var1.mTaggedIds != null) {
            List var68 = var1.mTaggedIds;
            Long var69 = Long.valueOf(AppSession.getActiveSession(var0, (boolean)0).getSessionInfo().userId);
            if(var68.contains(var69)) {
               var2.mRemoveTag.setVisibility(0);
               TextView var70 = var2.mRemoveTag;
               FacebookPostView.1 var71 = new FacebookPostView.1(var0, var1);
               var70.setOnClickListener(var71);
               break;
            }
         }

         var2.mRemoveTag.setVisibility(4);
         break;
      default:
         return;
      }

      long var4 = var1.actorId;
      var2.mUserId = var4;
      ImageView var6 = var2.mUserImageView;
      var6.setTag(var1);
      if(var3.mUserImageClickListener != null) {
         OnClickListener var7 = var3.mUserImageClickListener;
         var6.setOnClickListener(var7);
      }

      ProfileImagesCache var8 = var3.mUserImagesCache;
      long var9 = var1.actorId;
      String var11 = var1.getProfile().mImageUrl;
      Bitmap var12 = var8.get(var0, var9, var11);
      if(var12 == null) {
         var12 = var3.mNoAvatarBitmap;
      }

      label123: {
         var6.setImageBitmap(var12);
         Tuple var13 = (Tuple)var1.getUserObject();
         if(var13 != null) {
            long var14 = var3.mStreamId;
            long var16 = ((Long)var13.d0).longValue();
            if(var14 == var16) {
               TextView var18 = var2.mTextTextView;
               CharSequence var19 = (CharSequence)var13.d1;
               var18.setText(var19);
               break label123;
            }
         }

         Long var72 = Long.valueOf(var3.mStreamId);
         Spannable var73 = buildStatus(var0, var1, var3);
         Tuple var74 = new Tuple(var72, var73);
         TextView var75 = var2.mTextTextView;
         CharSequence var76 = (CharSequence)var74.d1;
         var75.setText(var76);
         var1.setUserObject(var74);
      }

      Set var21 = var1.getTaggedProfiles();
      if(var2.mTaggedUsers != null) {
         if(var21 != null && var21.size() != 0) {
            var2.mTaggedUsers.setVisibility(0);
            TextView var22 = var2.mTaggedUsers;
            Spannable var23 = buildTaggedText(var0, var21);
            var22.setText(var23);
         } else {
            var2.mTaggedUsers.setVisibility(8);
         }
      }

      StringUtils.TimeFormatStyle var24 = StringUtils.TimeFormatStyle.STREAM_RELATIVE_STYLE;
      long var25 = var1.createdTime * 1000L;
      String var81 = StringUtils.getTimeAsString(var0, var24, var25);
      String var80 = var1.attribution;
      if(var80 == null) {
         var2.mTimeTextView.setText(var81);
      } else {
         Object[] var78 = new Object[]{var81, var80};
         String var79 = var0.getString(2131362416, var78);
         var2.mTimeTextView.setText(var79);
      }
   }

   private static void setMovementMethod(TextView var0) {
      MovementMethod var1 = var0.getMovementMethod();
      if(var1 == null || !(var1 instanceof LinkMovementMethod)) {
         if(var0.getLinksClickable()) {
            MovementMethod var2 = SaneLinkMovementMethod.getInstance();
            var0.setMovementMethod(var2);
            var0.setFocusable((boolean)0);
            var0.setClickable((boolean)0);
            var0.setLongClickable((boolean)0);
         }
      }
   }

   private static void setupImageViewAtIndex(Context var0, FacebookPostView.ViewHolder var1, FacebookPost.Attachment.MediaItem var2, FacebookPostView.Extras var3, int var4) {
      LinearLayout var5 = var1.mImageHolders[var4];
      OnClickListener var6 = var3.mMediaItemClickListener;
      var5.setOnClickListener(var6);
      var5.setTag(var2);
      String var7 = var2.src;
      if(var7 != null) {
         Bitmap var8 = var3.mPhotosCache.get(var0, var7);
         Bitmap var9;
         if(var8 != null) {
            var9 = var8;
         } else {
            var9 = var3.mPhotoDownloadingBitmap;
         }

         ImageUtils.formatPhotoStreamImageView(var0, var5, var9);
      } else {
         ImageUtils.formatPhotoStreamImageView(var0, var5, (Bitmap)null);
      }

      var1.setImageUrl(var4, var7);
   }

   public static enum FacebookPostViewType {

      // $FF: synthetic field
      private static final FacebookPostView.FacebookPostViewType[] $VALUES;
      FEEDBACK_VIEW("FEEDBACK_VIEW", 1),
      STREAM_VIEW("STREAM_VIEW", 0);


      static {
         FacebookPostView.FacebookPostViewType[] var0 = new FacebookPostView.FacebookPostViewType[2];
         FacebookPostView.FacebookPostViewType var1 = STREAM_VIEW;
         var0[0] = var1;
         FacebookPostView.FacebookPostViewType var2 = FEEDBACK_VIEW;
         var0[1] = var2;
         $VALUES = var0;
      }

      private FacebookPostViewType(String var1, int var2) {}
   }

   public static class Extras {

      public final OnClickListener mMediaItemClickListener;
      public final Bitmap mNoAvatarBitmap;
      public final Bitmap mPhotoDownloadingBitmap;
      public final StreamPhotosCache mPhotosCache;
      public final long mStreamId;
      public final OnClickListener mUserImageClickListener;
      public final ProfileImagesCache mUserImagesCache;


      public Extras(ProfileImagesCache var1, StreamPhotosCache var2, OnClickListener var3, OnClickListener var4, Bitmap var5, Bitmap var6, long var7) {
         this.mUserImagesCache = var1;
         this.mPhotosCache = var2;
         this.mUserImageClickListener = var3;
         this.mMediaItemClickListener = var4;
         this.mPhotoDownloadingBitmap = var5;
         this.mNoAvatarBitmap = var6;
         this.mStreamId = var7;
      }
   }

   static class 3 extends ClickableSpan {

      // $FF: synthetic field
      final Context val$context;
      // $FF: synthetic field
      final FacebookProfile val$profile;


      3(Context var1, FacebookProfile var2) {
         this.val$context = var1;
         this.val$profile = var2;
      }

      public void onClick(View var1) {
         Context var2 = this.val$context;
         int var3 = this.val$profile.mType;
         long var4 = this.val$profile.mId;
         FacebookProfile var6 = this.val$profile;
         ApplicationUtils.OpenProfile(var2, var3, var4, var6);
      }

      public void updateDrawState(TextPaint var1) {}
   }

   static class 1 implements OnClickListener {

      // $FF: synthetic field
      final Context val$context;
      // $FF: synthetic field
      final FacebookPost val$post;


      1(Context var1, FacebookPost var2) {
         this.val$context = var1;
         this.val$post = var2;
      }

      public void onClick(View var1) {
         AppSession var2 = AppSession.getActiveSession(this.val$context, (boolean)0);
         Context var3 = this.val$context;
         FacebookPost var4 = this.val$post;
         long var5 = AppSession.getActiveSession(this.val$context, (boolean)0).getSessionInfo().userId;
         PlacesRemoveTag.RemoveTag(var2, var3, var4, var5);
         var1.setVisibility(4);
      }
   }

   static class 2 extends ClickableSpan {

      // $FF: synthetic field
      final Context val$context;
      // $FF: synthetic field
      final Set val$taggedUsers;


      2(Context var1, Set var2) {
         this.val$context = var1;
         this.val$taggedUsers = var2;
      }

      public void onClick(View var1) {
         Context var2 = this.val$context;
         Intent var3 = new Intent(var2, TaggedUsersActivity.class);
         Set var4 = this.val$taggedUsers;
         ArrayList var5 = new ArrayList(var4);
         var3.putParcelableArrayListExtra("profiles", var5);
         this.val$context.startActivity(var3);
      }

      public void updateDrawState(TextPaint var1) {}
   }

   public static class ViewHolder {

      public TextView mAttachmentCaption;
      public TextView mAttachmentDescription;
      public TextView mAttachmentName;
      public TextView mCommentsCountView;
      public LinearLayout[] mImageHolders;
      public TextView mLikesCountView;
      public int mPhotoCount;
      public String[] mPhotoUrls;
      public TextView mRemoveTag;
      public TextView mTaggedUsers;
      public TextView mTextTextView;
      public TextView mTimeTextView;
      public long mUserId;
      public ImageView mUserImageView;


      public ViewHolder() {
         LinearLayout[] var1 = new LinearLayout[3];
         this.mImageHolders = var1;
         String[] var2 = new String[3];
         this.mPhotoUrls = var2;
      }

      public void detach() {
         this.mUserId = 65535L;
         this.mPhotoCount = 0;
      }

      public void setImageUrl(int var1, String var2) {
         this.mPhotoUrls[var1] = var2;
      }

      public boolean setPhotoBitmap(Context var1, Bitmap var2, String var3) {
         int var4 = 0;

         boolean var8;
         while(true) {
            int var5 = this.mPhotoCount;
            if(var4 >= var5) {
               var8 = false;
               break;
            }

            String var6 = this.mPhotoUrls[var4];
            if(var3.equals(var6)) {
               LinearLayout var7 = this.mImageHolders[var4];
               ImageUtils.formatPhotoStreamImageView(var1, var7, var2);
               var8 = true;
               break;
            }

            ++var4;
         }

         return var8;
      }

      public void setUserImageBitmap(ProfileImage var1) {
         long var2 = this.mUserId;
         long var4 = var1.id;
         if(var2 == var4) {
            ImageView var6 = this.mUserImageView;
            Bitmap var7 = var1.getBitmap();
            var6.setImageBitmap(var7);
         }
      }
   }
}
