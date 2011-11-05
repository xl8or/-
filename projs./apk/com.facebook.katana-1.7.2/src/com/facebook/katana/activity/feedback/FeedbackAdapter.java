package com.facebook.katana.activity.feedback;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.AbsListView.RecyclerListener;
import com.facebook.katana.binding.ProfileImage;
import com.facebook.katana.binding.ProfileImagesCache;
import com.facebook.katana.binding.StreamPhotosCache;
import com.facebook.katana.model.FacebookPost;
import com.facebook.katana.provider.ConnectionsProvider;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.view.FacebookPostView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FeedbackAdapter extends BaseAdapter implements RecyclerListener {

   private final Context mContext;
   private FacebookPostView.Extras mExtras;
   private boolean mGetCommentsPending;
   private final List<FeedbackAdapter.Item> mItems;
   private final OnClickListener mLikeClickListener;
   private String mLikeName;
   private boolean mLikePending;
   private long mLikeUserId;
   private final Bitmap mPhotoDownloadErrorBitmap;
   private final FacebookPost mPost;
   private final List<FacebookPostView.ViewHolder> mViewHolders;


   public FeedbackAdapter(Context var1, ListView var2, FacebookPost var3, ProfileImagesCache var4, StreamPhotosCache var5, boolean var6, FeedbackAdapter.CommentsListener var7) {
      long var8 = 65535L;
      this.mLikeUserId = var8;
      this.mContext = var1;
      this.mPost = var3;
      Bitmap var12 = BitmapFactory.decodeResource(this.mContext.getResources(), 2130837760);
      Bitmap var13 = BitmapFactory.decodeResource(this.mContext.getResources(), 2130837759);
      this.mPhotoDownloadErrorBitmap = var13;
      Bitmap var14 = BitmapFactory.decodeResource(this.mContext.getResources(), 2130837747);
      ArrayList var15 = new ArrayList();
      this.mViewHolders = var15;
      ArrayList var16 = new ArrayList();
      this.mItems = var16;
      this.buildItems();
      FeedbackAdapter.1 var17 = new FeedbackAdapter.1(var7);
      FeedbackAdapter.2 var21 = new FeedbackAdapter.2(var7);
      this.mLikeClickListener = var21;
      FacebookPostView.Extras var28 = new FacebookPostView.Extras(var4, var5, (OnClickListener)null, var17, var12, var14, 65535L);
      this.mExtras = var28;
      this.mGetCommentsPending = var6;
      var2.setRecyclerListener(this);
      FacebookPost.Likes var32 = var3.getLikes();
      int var33 = var32.getCount();
      byte var34;
      if(var32.doesUserLike()) {
         var34 = 1;
      } else {
         var34 = 0;
      }

      if(var33 - var34 == 1) {
         long var35 = var32.getFriendUserId();
         if(var35 != 65535L) {
            String var40 = readNameFromContentProvider(var1, var35);
            this.mLikeName = var40;
            if(this.mLikeName == null) {
               this.mLikeUserId = var35;
            }
         } else {
            long var43 = var3.getLikes().getSampleUserId();
            if(var43 != 65535L) {
               this.mLikeUserId = var43;
            }
         }
      }
   }

   private static String readNameFromContentProvider(Context var0, long var1) {
      String var3 = null;
      Uri var4 = ContentUris.withAppendedId(ConnectionsProvider.FRIEND_UID_CONTENT_URI, var1);
      ContentResolver var5 = var0.getContentResolver();
      String[] var6 = new String[]{"display_name"};
      Object var7 = null;
      Object var8 = null;
      Cursor var9 = var5.query(var4, var6, (String)null, (String[])var7, (String)var8);
      if(var9.moveToFirst()) {
         var3 = var9.getString(0);
      }

      var9.close();
      return var3;
   }

   public void addCommentComplete() {
      this.refresh();
   }

   public void addLikeComplete(int var1) {
      this.mLikePending = (boolean)0;
      if(var1 == 200) {
         this.refresh();
      } else {
         this.notifyDataSetChanged();
      }
   }

   protected void buildItems() {
      this.mItems.clear();
      if(this.mPost != null) {
         switch(this.mPost.getPostType()) {
         case 0:
            List var1 = this.mItems;
            FacebookPost var2 = this.mPost;
            FeedbackAdapter.PostItem var3 = new FeedbackAdapter.PostItem(var2, 0);
            var1.add(var3);
            break;
         case 1:
            List var24 = this.mItems;
            FacebookPost var25 = this.mPost;
            FeedbackAdapter.PostItem var26 = new FeedbackAdapter.PostItem(var25, 2);
            var24.add(var26);
            break;
         case 2:
            List var28 = this.mItems;
            FacebookPost var29 = this.mPost;
            FeedbackAdapter.PostItem var30 = new FeedbackAdapter.PostItem(var29, 1);
            var28.add(var30);
            break;
         case 3:
            List var20 = this.mItems;
            FacebookPost var21 = this.mPost;
            FeedbackAdapter.PostItem var22 = new FeedbackAdapter.PostItem(var21, 3);
            var20.add(var22);
            break;
         case 4:
            List var32 = this.mItems;
            FacebookPost var33 = this.mPost;
            FeedbackAdapter.PostItem var34 = new FeedbackAdapter.PostItem(var33, 4);
            var32.add(var34);
            break;
         default:
            return;
         }

         List var5 = this.mItems;
         FeedbackAdapter.Item var6 = new FeedbackAdapter.Item(32);
         var5.add(var6);
         int var8 = this.mPost.getComments().getCount();
         int var9 = this.mPost.getComments().getTotalCount();
         if(var8 <= 0) {
            if(var9 > 0) {
               List var36 = this.mItems;
               FeedbackAdapter.Item var37 = new FeedbackAdapter.Item(30);
               var36.add(var37);
            }
         } else {
            List var10 = this.mPost.getComments().getComments();
            if(var8 < var9) {
               List var11 = this.mItems;
               FeedbackAdapter.Item var12 = new FeedbackAdapter.Item(30);
               var11.add(var12);
            }

            for(int var14 = 0; var14 < var8; ++var14) {
               List var15 = this.mItems;
               Context var16 = this.mContext;
               FacebookPost.Comment var17 = (FacebookPost.Comment)var10.get(var14);
               FeedbackAdapter.CommentItem var18 = new FeedbackAdapter.CommentItem(var16, var17);
               var15.add(var18);
            }

         }
      }
   }

   public void close() {
      this.mItems.clear();
      this.mViewHolders.clear();
   }

   public int getCount() {
      return this.mItems.size();
   }

   public Object getItem(int var1) {
      return Integer.valueOf(var1);
   }

   public FeedbackAdapter.Item getItemByPosition(int var1) {
      return (FeedbackAdapter.Item)this.mItems.get(var1);
   }

   public long getItemId(int var1) {
      return (long)var1;
   }

   public int getItemViewType(int var1) {
      byte var2;
      switch(((FeedbackAdapter.Item)this.mItems.get(var1)).getType()) {
      case 0:
         var2 = 0;
         break;
      case 1:
         var2 = 1;
         break;
      case 2:
         var2 = 3;
         break;
      case 3:
         var2 = 2;
         break;
      case 4:
         var2 = 4;
         break;
      case 30:
         var2 = 5;
         break;
      case 31:
         var2 = 6;
         break;
      case 32:
         var2 = 7;
         break;
      default:
         var2 = 0;
      }

      return var2;
   }

   public long getLikeUserId() {
      return this.mLikeUserId;
   }

   public View getView(int var1, View var2, ViewGroup var3) {
      View var4 = null;
      List var5 = this.mItems;
      FeedbackAdapter.Item var7 = (FeedbackAdapter.Item)var5.get(var1);
      FacebookPostView.ViewHolder var8 = null;
      if(var2 == null) {
         LayoutInflater var9 = (LayoutInflater)this.mContext.getSystemService("layout_inflater");
         switch(var7.getType()) {
         case 0:
         case 1:
         case 2:
         case 3:
         case 4:
            FacebookPost var10 = ((FeedbackAdapter.PostItem)var7).getPost();
            var4 = FacebookPostView.inflatePostView(var10, var9);
            var8 = (FacebookPostView.ViewHolder)var4.getTag();
            List var12 = this.mViewHolders;
            var12.add(var8);
            break;
         case 30:
            int var16 = 2130903112;
            Object var17 = null;
            var4 = var9.inflate(var16, (ViewGroup)var17);
            break;
         case 31:
            int var19 = 2130903057;
            Object var20 = null;
            var4 = var9.inflate(var19, (ViewGroup)var20);
            var8 = new FacebookPostView.ViewHolder();
            int var22 = 2131623990;
            ImageView var23 = (ImageView)var4.findViewById(var22);
            var8.mUserImageView = var23;
            int var25 = 2131623993;
            TextView var26 = (TextView)var4.findViewById(var25);
            var8.mTextTextView = var26;
            int var28 = 2131623992;
            TextView var29 = (TextView)var4.findViewById(var28);
            var8.mTimeTextView = var29;
            int var31 = 2131623991;
            TextView var32 = (TextView)var4.findViewById(var31);
            var8.mAttachmentName = var32;
            Object var33 = null;
            var8.mAttachmentCaption = (TextView)var33;
            Object var34 = null;
            var8.mAttachmentDescription = (TextView)var34;
            var8.mImageHolders[0] = false;
            var8.mImageHolders[1] = false;
            var8.mImageHolders[2] = false;
            byte var35 = 0;
            var8.mPhotoCount = var35;
            var4.setTag(var8);
            List var38 = this.mViewHolders;
            var38.add(var8);
            break;
         case 32:
            int var42 = 2130903097;
            Object var43 = null;
            var4 = var9.inflate(var42, (ViewGroup)var43);
         }
      } else {
         var4 = var2;
         var8 = (FacebookPostView.ViewHolder)var2.getTag();
      }

      switch(var7.getType()) {
      case 0:
      case 1:
      case 2:
      case 3:
      case 4:
         FacebookPost var44 = ((FeedbackAdapter.PostItem)var7).getPost();
         Context var45 = this.mContext;
         FacebookPostView.Extras var46 = this.mExtras;
         FacebookPostView.renderPostView(var45, var44, var8, var46);
         break;
      case 30:
         int var51 = this.mPost.getComments().getTotalCount();
         String var52;
         if(this.mPost.getComments().getCount() > 0) {
            if(this.mGetCommentsPending) {
               var52 = this.mContext.getString(2131362236);
            } else {
               var52 = this.mContext.getString(2131362261);
            }
         } else if(var51 > 0) {
            if(this.mGetCommentsPending) {
               var52 = this.mContext.getString(2131362234);
            } else {
               var52 = this.mContext.getString(2131362260);
            }
         } else {
            var52 = null;
         }

         int var54 = 2131624146;
         TextView var55 = (TextView)var4.findViewById(var54);
         var55.setText(var52);
         break;
      case 31:
         FeedbackAdapter.CommentItem var57 = (FeedbackAdapter.CommentItem)var7;
         ImageView var58 = var8.mUserImageView;
         FacebookPost.Comment var59 = var57.getComment();
         long var60 = var59.fromId;
         var8.mUserId = var60;
         ProfileImagesCache var62 = this.mExtras.mUserImagesCache;
         Context var63 = this.mContext;
         long var64 = var59.fromId;
         String var66 = var59.getProfile().mImageUrl;
         Bitmap var67 = var62.get(var63, var64, var66);
         Bitmap var68;
         if(var67 != null) {
            var68 = var67;
         } else {
            var68 = this.mExtras.mNoAvatarBitmap;
         }

         var58.setImageBitmap(var68);
         TextView var71 = var8.mAttachmentName;
         String var72 = var59.getProfile().mDisplayName;
         var71.setText(var72);
         TextView var73 = var8.mTextTextView;
         String var74 = var59.text;
         var73.setText(var74);
         TextView var75 = var8.mTimeTextView;
         String var76 = var57.getTime();
         var75.setText(var76);
         break;
      case 32:
         FacebookPost.Likes var77 = this.mPost.getLikes();
         int var79 = 2131624107;
         ToggleButton var80 = (ToggleButton)var4.findViewById(var79);
         byte var81;
         if(var77.canLike) {
            var81 = 0;
         } else {
            var81 = 8;
         }

         var80.setVisibility(var81);
         OnClickListener var84 = this.mLikeClickListener;
         var80.setOnClickListener(var84);
         byte var87;
         if(!var77.doesUserLike()) {
            var87 = 1;
         } else {
            var87 = 0;
         }

         var80.setChecked((boolean)var87);
         if(this.mLikePending) {
            byte var91 = 0;
            var80.setEnabled((boolean)var91);
            int var93 = 2131624108;
            var4.findViewById(var93).setVisibility(0);
         } else {
            byte var106 = 1;
            var80.setEnabled((boolean)var106);
            int var108 = 2131624108;
            var4.findViewById(var108).setVisibility(8);
         }

         int var95 = 2131624106;
         TextView var96 = (TextView)var4.findViewById(var95);
         String var99;
         if(var77.doesUserLike()) {
            int var97 = var77.getCount();
            byte var98 = 1;
            if(var97 == var98) {
               var99 = this.mContext.getString(2131362270);
            } else {
               int var109 = var77.getCount();
               byte var110 = 2;
               if(var109 == var110) {
                  if(this.mLikeName != null) {
                     Context var111 = this.mContext;
                     Object[] var112 = new Object[1];
                     String var113 = this.mLikeName;
                     var112[0] = var113;
                     var99 = var111.getString(2131362268, var112);
                  } else {
                     var99 = this.mContext.getString(2131362267);
                  }
               } else {
                  Context var114 = this.mContext;
                  Object[] var115 = new Object[1];
                  Integer var116 = Integer.valueOf(var77.getCount() - 1);
                  var115[0] = var116;
                  var99 = var114.getString(2131362269, var115);
               }
            }
         } else {
            int var117 = var77.getCount();
            byte var118 = 1;
            if(var117 == var118) {
               if(this.mLikeName != null) {
                  Context var119 = this.mContext;
                  Object[] var120 = new Object[1];
                  String var121 = this.mLikeName;
                  var120[0] = var121;
                  var99 = var119.getString(2131362241, var120);
               } else {
                  var99 = this.mContext.getString(2131362242);
               }
            } else {
               int var122 = var77.getCount();
               byte var123 = 1;
               if(var122 > var123) {
                  Context var124 = this.mContext;
                  Object[] var125 = new Object[1];
                  Integer var126 = Integer.valueOf(var77.getCount());
                  var125[0] = var126;
                  var99 = var124.getString(2131362243, var125);
               } else {
                  var99 = "";
               }
            }
         }

         var96.setText(var99);
         int var101 = 2131624105;
         View var102 = var4.findViewById(var101);
         if(var99.length() > 0) {
            byte var104 = 0;
            var102.setVisibility(var104);
         } else {
            byte var128 = 8;
            var102.setVisibility(var128);
         }
      }

      return var4;
   }

   public int getViewTypeCount() {
      return 8;
   }

   public void onMovedToScrapHeap(View var1) {
      Object var2 = var1.getTag();
      if(var2 instanceof FacebookPostView.ViewHolder) {
         ((FacebookPostView.ViewHolder)var2).detach();
      }
   }

   public void refresh() {
      this.buildItems();
      this.notifyDataSetChanged();
   }

   public void removeCommentComplete() {
      this.refresh();
   }

   public void removeLikeComplete(int var1) {
      this.mLikePending = (boolean)0;
      if(var1 == 200) {
         this.refresh();
      } else {
         this.notifyDataSetChanged();
      }
   }

   public void requestCommentsComplete(int var1) {
      this.mGetCommentsPending = (boolean)0;
      if(var1 == 200) {
         this.refresh();
      } else {
         this.notifyDataSetChanged();
      }
   }

   public void requestMoreComments() {
      this.mGetCommentsPending = (boolean)1;
      this.notifyDataSetChanged();
   }

   public void updateLikeUserName(long var1, String var3) {
      long var4 = this.mLikeUserId;
      if(var1 == var4) {
         this.mLikeName = var3;
         this.mLikeUserId = 65535L;
         this.notifyDataSetChanged();
      }
   }

   public void updatePhoto(Bitmap var1, String var2) {
      Iterator var3 = this.mViewHolders.iterator();

      FacebookPostView.ViewHolder var4;
      Context var5;
      do {
         if(!var3.hasNext()) {
            return;
         }

         var4 = (FacebookPostView.ViewHolder)var3.next();
         if(var1 == null) {
            var1 = this.mPhotoDownloadErrorBitmap;
         }

         var5 = this.mContext;
      } while(!var4.setPhotoBitmap(var5, var1, var2));

   }

   public void updateUserImage(ProfileImage var1) {
      Iterator var2 = this.mViewHolders.iterator();

      while(var2.hasNext()) {
         ((FacebookPostView.ViewHolder)var2.next()).setUserImageBitmap(var1);
      }

   }

   public interface CommentsListener {

      void onLike(boolean var1);

      void onMediaItemClicked(FacebookPost.Attachment.MediaItem var1);
   }

   protected static class PostItem extends FeedbackAdapter.Item {

      protected final FacebookPost mPost;


      protected PostItem(FacebookPost var1, int var2) {
         super(var2);
         this.mPost = var1;
      }

      public FacebookPost getPost() {
         return this.mPost;
      }
   }

   class 2 implements OnClickListener {

      // $FF: synthetic field
      final FeedbackAdapter.CommentsListener val$commentListener;


      2(FeedbackAdapter.CommentsListener var2) {
         this.val$commentListener = var2;
      }

      public void onClick(View var1) {
         boolean var2 = (boolean)(FeedbackAdapter.this.mLikePending = (boolean)1);
         FeedbackAdapter.this.notifyDataSetChanged();
         FeedbackAdapter.CommentsListener var3 = this.val$commentListener;
         byte var4;
         if(!FeedbackAdapter.this.mPost.getLikes().doesUserLike()) {
            var4 = 1;
         } else {
            var4 = 0;
         }

         var3.onLike((boolean)var4);
      }
   }

   protected static class Item {

      public static final int TYPE_CHECKIN_POST = 4;
      public static final int TYPE_COMMENT = 31;
      public static final int TYPE_LIKE_CONTROL = 32;
      public static final int TYPE_LINK_ATTACHMENT_POST = 2;
      public static final int TYPE_PHOTO_ATTACHMENT_POST = 1;
      public static final int TYPE_STATUS_POST = 0;
      public static final int TYPE_VIDEO_ATTACHMENT_POST = 3;
      public static final int TYPE_VIEW_MORE_COMMENTS = 30;
      private final int mType;


      public Item(int var1) {
         this.mType = var1;
      }

      public int getType() {
         return this.mType;
      }
   }

   class 1 implements OnClickListener {

      // $FF: synthetic field
      final FeedbackAdapter.CommentsListener val$commentListener;


      1(FeedbackAdapter.CommentsListener var2) {
         this.val$commentListener = var2;
      }

      public void onClick(View var1) {
         FacebookPost.Attachment.MediaItem var2 = (FacebookPost.Attachment.MediaItem)var1.getTag();
         this.val$commentListener.onMediaItemClicked(var2);
      }
   }

   protected static class CommentItem extends FeedbackAdapter.Item {

      private final FacebookPost.Comment mComment;
      private final String mTime;


      protected CommentItem(Context var1, FacebookPost.Comment var2) {
         super(31);
         this.mComment = var2;
         StringUtils.TimeFormatStyle var3 = StringUtils.TimeFormatStyle.STREAM_RELATIVE_STYLE;
         long var4 = var2.time * 1000L;
         String var6 = StringUtils.getTimeAsString(var1, var3, var4);
         this.mTime = var6;
      }

      public FacebookPost.Comment getComment() {
         return this.mComment;
      }

      public String getTime() {
         return this.mTime;
      }
   }
}
