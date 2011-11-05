package com.facebook.katana.activity.stream;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AbsListView.RecyclerListener;
import com.facebook.katana.binding.FacebookStreamContainer;
import com.facebook.katana.binding.ProfileImage;
import com.facebook.katana.binding.ProfileImagesCache;
import com.facebook.katana.binding.StreamPhotosCache;
import com.facebook.katana.model.FacebookPost;
import com.facebook.katana.view.FacebookPostView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StreamAdapter extends BaseAdapter implements RecyclerListener {

   private final Context mContext;
   private FacebookPostView.Extras mExtras;
   private boolean mLoading;
   private final Bitmap mPhotoDownloadErrorBitmap;
   private FacebookStreamContainer mStreamContainer;
   private final List<FacebookPostView.ViewHolder> mViewHolders;


   public StreamAdapter(Context var1, ListView var2, FacebookStreamContainer var3, ProfileImagesCache var4, StreamPhotosCache var5, StreamAdapter.StreamAdapterListener var6, long var7) {
      this.mContext = var1;
      this.mStreamContainer = var3;
      Bitmap var9 = BitmapFactory.decodeResource(this.mContext.getResources(), 2130837760);
      Bitmap var10 = BitmapFactory.decodeResource(this.mContext.getResources(), 2130837759);
      this.mPhotoDownloadErrorBitmap = var10;
      Bitmap var11 = BitmapFactory.decodeResource(this.mContext.getResources(), 2130837747);
      ArrayList var12 = new ArrayList();
      this.mViewHolders = var12;
      StreamAdapter.1 var13 = new StreamAdapter.1(var6);
      StreamAdapter.2 var14 = new StreamAdapter.2(var6);
      FacebookPostView.Extras var19 = new FacebookPostView.Extras(var4, var5, var13, var14, var9, var11, var7);
      this.mExtras = var19;
      var2.setRecyclerListener(this);
   }

   private void fillPostFooterView(View var1, FacebookPostView.ViewHolder var2, FacebookPost var3) {
      View var4 = var1.findViewById(2131624189);
      FacebookPost.Comments var5 = var3.getComments();
      FacebookPost.Likes var6 = var3.getLikes();
      if(!var3.canInteractWithFeedback()) {
         var4.setVisibility(8);
      } else if(var5.getTotalCount() == 0 && var6.getCount() == 0) {
         var4.setVisibility(8);
      } else {
         var4.setVisibility(0);
         var4.setTag(var3);
         TextView var7 = var2.mCommentsCountView;
         int var8 = var5.getTotalCount();
         if(var8 == 0) {
            if(var5.canPost) {
               var7.setText(2131362224);
               var7.setVisibility(0);
            } else {
               var7.setVisibility(4);
            }
         } else if(var8 == 1) {
            var7.setVisibility(0);
            String var15 = this.mContext.getString(2131362240);
            var7.setText(var15);
         } else {
            var7.setVisibility(0);
            Context var16 = this.mContext;
            Object[] var17 = new Object[1];
            Integer var18 = Integer.valueOf(var8);
            var17[0] = var18;
            String var19 = var16.getString(2131362226, var17);
            var7.setText(var19);
         }

         TextView var9 = var2.mLikesCountView;
         int var10 = var6.getCount();
         if(var10 > 0) {
            var9.setVisibility(0);
            Resources var11 = this.mContext.getResources();
            Object[] var12 = new Object[1];
            Integer var13 = Integer.valueOf(var10);
            var12[0] = var13;
            String var14 = var11.getQuantityString(2131427332, var10, var12);
            var9.setText(var14);
         } else if(var6.canLike) {
            var9.setVisibility(0);
            var9.setText(2131362231);
         } else {
            var9.setVisibility(4);
         }
      }
   }

   public void close() {
      this.mViewHolders.clear();
   }

   public int getCount() {
      int var1;
      if(this.mStreamContainer != null) {
         var1 = this.mStreamContainer.getPostCount() + 1;
      } else {
         var1 = 0;
      }

      return var1;
   }

   public Object getItem(int var1) {
      return Integer.valueOf(var1);
   }

   public FacebookPost getItemByPosition(int var1) {
      FacebookPost var3;
      if(this.mStreamContainer != null) {
         int var2 = this.mStreamContainer.getPostCount();
         if(var1 >= var2) {
            var3 = null;
         } else {
            var3 = this.mStreamContainer.getPost(var1);
         }
      } else {
         var3 = null;
      }

      return var3;
   }

   public long getItemId(int var1) {
      return (long)var1;
   }

   public int getItemViewType(int var1) {
      int var2 = this.mStreamContainer.getPostCount();
      byte var3;
      if(var1 >= var2) {
         if(this.mStreamContainer != null && !this.mStreamContainer.isComplete()) {
            var3 = 0;
         } else {
            var3 = 7;
         }
      } else {
         switch(this.mStreamContainer.getPost(var1).getPostType()) {
         case 0:
            var3 = 2;
            break;
         case 1:
            var3 = 5;
            break;
         case 2:
            var3 = 3;
            break;
         case 3:
            var3 = 4;
            break;
         case 4:
            var3 = 6;
            break;
         default:
            var3 = 1;
         }
      }

      return var3;
   }

   public View getView(int var1, View var2, ViewGroup var3) {
      FacebookPost var4 = null;
      int var5 = this.mStreamContainer.getPostCount();
      View var11;
      if(var1 < var5) {
         var4 = this.mStreamContainer.getPost(var1);
      } else if(this.mStreamContainer.isComplete()) {
         Context var12 = this.mContext;
         var11 = new View(var12);
         return var11;
      }

      FacebookPostView.ViewHolder var6 = null;
      View var8;
      if(var2 == null) {
         LayoutInflater var7 = (LayoutInflater)this.mContext.getSystemService("layout_inflater");
         if(var4 == null) {
            var8 = var7.inflate(2130903162, (ViewGroup)null);
         } else {
            var8 = FacebookPostView.inflatePostView(var4, var7);
            var6 = (FacebookPostView.ViewHolder)var8.getTag();
            this.mViewHolders.add(var6);
         }
      } else {
         var8 = var2;
         var6 = (FacebookPostView.ViewHolder)var2.getTag();
      }

      if(var4 == null) {
         TextView var9 = (TextView)var8.findViewById(2131624240);
         int var10;
         if(this.mLoading) {
            var10 = 2131362235;
         } else {
            var10 = 2131362232;
         }

         var9.setText(var10);
      } else {
         Context var14 = this.mContext;
         FacebookPostView.Extras var15 = this.mExtras;
         FacebookPostView.renderPostView(var14, var4, var6, var15);
         this.fillPostFooterView(var8, var6, var4);
      }

      var11 = var8;
      return var11;
   }

   public int getViewTypeCount() {
      return 8;
   }

   public boolean isLoadingMore() {
      return this.mLoading;
   }

   public void loadingMore(boolean var1) {
      this.mLoading = var1;
      this.notifyDataSetChanged();
   }

   public void onMovedToScrapHeap(View var1) {
      Object var2 = var1.getTag();
      if(var2 instanceof FacebookPostView.ViewHolder) {
         ((FacebookPostView.ViewHolder)var2).detach();
      }
   }

   public void refreshStream() {
      this.notifyDataSetChanged();
   }

   public void refreshStream(FacebookStreamContainer var1) {
      this.mStreamContainer = var1;
      this.notifyDataSetChanged();
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

   class 2 implements OnClickListener {

      // $FF: synthetic field
      final StreamAdapter.StreamAdapterListener val$listener;


      2(StreamAdapter.StreamAdapterListener var2) {
         this.val$listener = var2;
      }

      public void onClick(View var1) {
         FacebookPost.Attachment.MediaItem var2 = (FacebookPost.Attachment.MediaItem)var1.getTag();
         this.val$listener.onOpenMediaItem(var2);
      }
   }

   protected interface StreamAdapterListener {

      void onOpenMediaItem(FacebookPost.Attachment.MediaItem var1);

      void onUserImageClicked(FacebookPost var1);
   }

   class 1 implements OnClickListener {

      // $FF: synthetic field
      final StreamAdapter.StreamAdapterListener val$listener;


      1(StreamAdapter.StreamAdapterListener var2) {
         this.val$listener = var2;
      }

      public void onClick(View var1) {
         StreamAdapter.StreamAdapterListener var2 = this.val$listener;
         FacebookPost var3 = (FacebookPost)var1.getTag();
         var2.onUserImageClicked(var3);
      }
   }
}
