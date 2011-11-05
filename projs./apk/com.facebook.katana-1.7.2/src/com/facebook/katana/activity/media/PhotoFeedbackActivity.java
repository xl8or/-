package com.facebook.katana.activity.media;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.ViewHolder;
import com.facebook.katana.activity.BaseFacebookListActivity;
import com.facebook.katana.activity.FatTitleHeader;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.binding.ProfileImage;
import com.facebook.katana.binding.ProfileImagesCache;
import com.facebook.katana.model.FacebookPhoto;
import com.facebook.katana.model.FacebookPhotoComment;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.provider.ConnectionsProvider;
import com.facebook.katana.util.ApplicationUtils;
import com.facebook.katana.util.ImageUtils;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.Toaster;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class PhotoFeedbackActivity extends BaseFacebookListActivity implements FatTitleHeader {

   private static final int PROGRESS_ADD_COMMENT_DIALOG_ID = 1;
   private static final int REFRESH_MENU_ID = 1;
   private PhotoFeedbackActivity.PhotoFeedbackAdapter mAdapter;
   private String mAddCommentReqId;
   private AppSession mAppSession;
   private AppSessionListener mAppSessionListener;
   private FacebookPhoto mPhoto;
   private String mPhotoId;


   public PhotoFeedbackActivity() {}

   private void setupEmptyView() {
      ((TextView)this.findViewById(2131624022)).setText(2131362079);
      ((TextView)this.findViewById(2131624024)).setText(2131362078);
      View var1 = this.findViewById(16908292);
      Drawable var2 = this.getResources().getDrawable(2130837819);
      var1.setBackgroundDrawable(var2);
   }

   private void showProgress(boolean var1) {
      if(var1) {
         this.findViewById(2131624177).setVisibility(0);
         this.findViewById(2131624022).setVisibility(8);
         this.findViewById(2131624023).setVisibility(0);
      } else {
         this.findViewById(2131624177).setVisibility(8);
         this.findViewById(2131624022).setVisibility(0);
         this.findViewById(2131624023).setVisibility(8);
      }
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.setContentView(2130903120);
      AppSession var2 = AppSession.getActiveSession(this, (boolean)1);
      this.mAppSession = var2;
      if(this.mAppSession == null) {
         LoginActivity.toLogin(this);
      } else {
         String var3 = this.getIntent().getData().getLastPathSegment();
         this.mPhotoId = var3;
         Uri var4 = this.getIntent().getData();
         FacebookPhoto var5 = FacebookPhoto.readFromContentProvider(this, var4);
         this.mPhoto = var5;
         if(this.mPhoto == null) {
            String[] var6 = new String[1];
            String var7 = this.mPhotoId;
            var6[0] = var7;
            List var8 = Arrays.asList(var6);
            if(!this.mAppSession.isPhotosGetPending((Collection)var8, 65535L)) {
               AppSession var9 = this.mAppSession;
               var9.photoGetPhotos(this, (String)null, var8, 65535L);
            }
         }

         PhotoFeedbackActivity.PhotoFeedbackAppSessionListener var12 = new PhotoFeedbackActivity.PhotoFeedbackAppSessionListener((PhotoFeedbackActivity.1)null);
         this.mAppSessionListener = var12;
         this.setupFatTitleHeader();
         ProfileImagesCache var13 = this.mAppSession.getUserImagesCache();
         PhotoFeedbackActivity.PhotoFeedbackAdapter var14 = new PhotoFeedbackActivity.PhotoFeedbackAdapter(this, var13);
         this.mAdapter = var14;
         ListView var15 = this.getListView();
         PhotoFeedbackActivity.PhotoFeedbackAdapter var16 = this.mAdapter;
         var15.setAdapter(var16);
         View var17 = this.findViewById(2131624055);
         PhotoFeedbackActivity.1 var18 = new PhotoFeedbackActivity.1();
         var17.setOnClickListener(var18);
         EditText var19 = (EditText)this.findViewById(2131624054);
         PhotoFeedbackActivity.2 var20 = new PhotoFeedbackActivity.2();
         var19.setOnEditorActionListener(var20);
         PhotoFeedbackActivity.3 var21 = new PhotoFeedbackActivity.3();
         var19.setOnFocusChangeListener(var21);
         this.setupEmptyView();
      }
   }

   protected Dialog onCreateDialog(int var1) {
      ProgressDialog var2;
      switch(var1) {
      case 1:
         ProgressDialog var3 = new ProgressDialog(this);
         var3.setProgressStyle(0);
         CharSequence var4 = this.getText(2131362073);
         var3.setMessage(var4);
         var3.setIndeterminate((boolean)1);
         var3.setCancelable((boolean)0);
         var2 = var3;
         break;
      default:
         var2 = null;
      }

      return var2;
   }

   public boolean onCreateOptionsMenu(Menu var1) {
      super.onCreateOptionsMenu(var1);
      MenuItem var3 = var1.add(0, 1, 0, 2131362246).setIcon(2130837692);
      return true;
   }

   public void onListItemClick(ListView var1, View var2, int var3, long var4) {
      FacebookProfile var6 = ((FacebookPhotoComment)this.mAdapter.getItem(var3)).getFromProfile();
      long var7 = var6.mId;
      ApplicationUtils.OpenUserProfile(this, var7, var6);
   }

   public boolean onOptionsItemSelected(MenuItem var1) {
      switch(var1.getItemId()) {
      case 1:
         AppSession var2 = this.mAppSession;
         String var3 = this.mPhotoId;
         var2.photoGetComments(this, var3);
         this.showProgress((boolean)1);
      default:
         return super.onOptionsItemSelected(var1);
      }
   }

   protected void onPause() {
      super.onPause();
      if(this.mAppSession != null) {
         AppSession var1 = this.mAppSession;
         AppSessionListener var2 = this.mAppSessionListener;
         var1.removeListener(var2);
      }
   }

   public boolean onPrepareOptionsMenu(Menu var1) {
      super.onPrepareOptionsMenu(var1);
      AppSession var3 = this.mAppSession;
      String var4 = this.mPhotoId;
      boolean var5 = var3.isPhotoGetCommentPending(var4);
      MenuItem var6 = var1.findItem(1);
      byte var7;
      if(!var5) {
         var7 = 1;
      } else {
         var7 = 0;
      }

      var6.setEnabled((boolean)var7);
      return true;
   }

   protected void onResume() {
      super.onResume();
      AppSession var1 = AppSession.getActiveSession(this, (boolean)1);
      this.mAppSession = var1;
      if(this.mAppSession == null) {
         LoginActivity.toLogin(this);
      } else {
         boolean var2 = false;
         AppSession var3 = this.mAppSession;
         AppSessionListener var4 = this.mAppSessionListener;
         var3.addListener(var4);
         if(this.mAddCommentReqId != null) {
            AppSession var5 = this.mAppSession;
            String var6 = this.mAddCommentReqId;
            if(!var5.isRequestPending(var6)) {
               this.removeDialog(1);
               this.mAddCommentReqId = null;
               var2 = true;
               ((EditText)this.findViewById(2131624054)).setText((CharSequence)null);
            }
         }

         AppSession var7 = this.mAppSession;
         String var8 = this.mPhotoId;
         if(!var7.isPhotoGetCommentPending(var8)) {
            if(this.mAdapter.getCount() == 0 || var2) {
               AppSession var9 = this.mAppSession;
               String var10 = this.mPhotoId;
               var9.photoGetComments(this, var10);
               this.showProgress((boolean)1);
            }
         } else {
            this.showProgress((boolean)1);
         }
      }
   }

   public void setupFatTitleHeader() {
      ImageView var1 = (ImageView)this.findViewById(2131624048);
      var1.setVisibility(0);
      byte[] var2;
      if(this.mPhoto == null) {
         var2 = null;
      } else {
         var2 = this.mPhoto.getImageBytes();
      }

      if(var2 != null) {
         int var3 = var2.length;
         Bitmap var4 = ImageUtils.decodeByteArray(var2, 0, var3);
         if(var4 != null) {
            var1.setImageBitmap(var4);
         }
      } else if(this.mPhoto != null) {
         var1.setImageResource(2130837760);
         AppSession var12 = this.mAppSession;
         String var13 = this.mPhoto.getAlbumId();
         String var14 = this.mPhoto.getPhotoId();
         String var15 = this.mPhoto.getSrc();
         var12.downloadPhotoThumbail(this, var13, var14, var15);
      } else {
         var1.setImageResource(2130837760);
      }

      String var5;
      if(this.mPhoto == null) {
         var5 = null;
      } else {
         var5 = this.mPhoto.getCaption();
      }

      if(var5 == null) {
         var5 = this.getString(2131362303);
      }

      ((TextView)this.findViewById(2131624049)).setText(var5);
      StringUtils.TimeFormatStyle var6 = StringUtils.TimeFormatStyle.MONTH_DAY_YEAR_STYLE;
      long var7;
      if(this.mPhoto == null) {
         var7 = System.currentTimeMillis();
      } else {
         var7 = this.mPhoto.getCreatedMs();
      }

      String var9 = StringUtils.getTimeAsString(this, var6, var7);
      Object[] var10 = new Object[]{var9};
      String var11 = this.getString(2131362075, var10);
      ((TextView)this.findViewById(2131624050)).setText(var11);
   }

   public void updateFatTitleHeader() {}

   private class PhotoFeedbackAppSessionListener extends AppSessionListener {

      private PhotoFeedbackAppSessionListener() {}

      // $FF: synthetic method
      PhotoFeedbackAppSessionListener(PhotoFeedbackActivity.1 var2) {
         this();
      }

      public void onDownloadPhotoThumbnailComplete(AppSession var1, String var2, int var3, String var4, Exception var5, String var6, String var7) {
         if(PhotoFeedbackActivity.this.mPhotoId.equals(var7)) {
            ImageView var8 = (ImageView)PhotoFeedbackActivity.this.findViewById(2131624048);
            if(var3 == 200) {
               PhotoFeedbackActivity var9 = PhotoFeedbackActivity.this;
               FacebookPhoto var10 = FacebookPhoto.readFromContentProvider(PhotoFeedbackActivity.this, var7);
               var9.mPhoto = var10;
               byte[] var12 = PhotoFeedbackActivity.this.mPhoto.getImageBytes();
               if(var12 != null) {
                  int var13 = var12.length;
                  Bitmap var14 = ImageUtils.decodeByteArray(var12, 0, var13);
                  var8.setImageBitmap(var14);
               }
            } else {
               var8.setImageResource(2130837759);
            }
         }
      }

      public void onPhotoAddCommentComplete(AppSession var1, String var2, int var3, String var4, Exception var5, String var6, FacebookPhotoComment var7) {
         String var8 = PhotoFeedbackActivity.this.mPhotoId;
         if(var6.equals(var8)) {
            PhotoFeedbackActivity.this.removeDialog(1);
            if(var3 == 200) {
               ((EditText)PhotoFeedbackActivity.this.findViewById(2131624054)).setText((CharSequence)null);
               PhotoFeedbackActivity var9 = PhotoFeedbackActivity.this;
               long var10 = PhotoFeedbackActivity.this.mPhoto.getOwnerId();
               FacebookProfile var12 = ConnectionsProvider.getAdminProfile(var9, var10);
               if(var12 != null) {
                  var7.setFromProfile(var12);
               }

               PhotoFeedbackActivity.this.mAdapter.addComment(var7);
            } else {
               PhotoFeedbackActivity var13 = PhotoFeedbackActivity.this;
               String var14 = PhotoFeedbackActivity.this.getString(2131362072);
               String var15 = StringUtils.getErrorString(var13, var14, var3, var4, var5);
               Toaster.toast(PhotoFeedbackActivity.this, var15);
            }
         }
      }

      public void onPhotoGetCommentsComplete(AppSession var1, String var2, int var3, String var4, Exception var5, String var6, List<FacebookPhotoComment> var7, boolean var8) {
         String var9 = PhotoFeedbackActivity.this.mPhotoId;
         if(var6.equals(var9)) {
            PhotoFeedbackActivity.this.showProgress((boolean)0);
            if(var3 == 200) {
               PhotoFeedbackActivity.this.mAdapter.setComments(var7);
               if(var8) {
                  PhotoFeedbackActivity.this.findViewById(2131624053).setVisibility(0);
               }
            } else {
               PhotoFeedbackActivity var10 = PhotoFeedbackActivity.this;
               String var11 = PhotoFeedbackActivity.this.getString(2131362076);
               String var12 = StringUtils.getErrorString(var10, var11, var3, var4, var5);
               Toaster.toast(PhotoFeedbackActivity.this, var12);
            }
         }
      }

      public void onPhotoGetPhotosComplete(AppSession var1, String var2, int var3, String var4, Exception var5, String var6, String[] var7, long var8) {
         if(var3 == 200) {
            if(!PhotoFeedbackActivity.this.isFinishing()) {
               PhotoFeedbackActivity var10 = PhotoFeedbackActivity.this;
               PhotoFeedbackActivity var11 = PhotoFeedbackActivity.this;
               Uri var12 = PhotoFeedbackActivity.this.getIntent().getData();
               FacebookPhoto var13 = FacebookPhoto.readFromContentProvider(var11, var12);
               var10.mPhoto = var13;
               if(PhotoFeedbackActivity.this.mPhoto != null) {
                  AppSession var15 = PhotoFeedbackActivity.this.mAppSession;
                  PhotoFeedbackActivity var16 = PhotoFeedbackActivity.this;
                  String var17 = PhotoFeedbackActivity.this.mPhoto.getAlbumId();
                  String var18 = PhotoFeedbackActivity.this.mPhoto.getPhotoId();
                  String var19 = PhotoFeedbackActivity.this.mPhoto.getSrc();
                  var15.downloadPhotoThumbail(var16, var17, var18, var19);
                  PhotoFeedbackActivity.this.setupFatTitleHeader();
               }
            }
         }
      }

      public void onProfileImageDownloaded(AppSession var1, String var2, int var3, String var4, Exception var5, ProfileImage var6, ProfileImagesCache var7) {
         if(var3 == 200) {
            PhotoFeedbackActivity.this.mAdapter.updateUserImage(var6);
         }
      }

      public void onProfileImageLoaded(AppSession var1, ProfileImage var2) {
         PhotoFeedbackActivity.this.mAdapter.updateUserImage(var2);
      }
   }

   class 3 implements OnFocusChangeListener {

      3() {}

      public void onFocusChange(View var1, boolean var2) {
         View var3 = PhotoFeedbackActivity.this.findViewById(2131624055);
         byte var4;
         if(var2) {
            var4 = 0;
         } else {
            var4 = 8;
         }

         var3.setVisibility(var4);
      }
   }

   class 2 implements OnEditorActionListener {

      2() {}

      public boolean onEditorAction(TextView var1, int var2, KeyEvent var3) {
         if(var2 == 101) {
            String var4 = ((TextView)PhotoFeedbackActivity.this.findViewById(2131624054)).getText().toString().trim();
            if(var4.length() > 0) {
               PhotoFeedbackActivity.this.showDialog(1);
               PhotoFeedbackActivity var5 = PhotoFeedbackActivity.this;
               AppSession var6 = PhotoFeedbackActivity.this.mAppSession;
               PhotoFeedbackActivity var7 = PhotoFeedbackActivity.this;
               String var8 = PhotoFeedbackActivity.this.mPhotoId;
               String var9 = var6.photoAddComment(var7, var8, var4);
               var5.mAddCommentReqId = var9;
            }
         }

         return false;
      }
   }

   class 1 implements OnClickListener {

      1() {}

      public void onClick(View var1) {
         EditText var2 = (EditText)PhotoFeedbackActivity.this.findViewById(2131624054);
         String var3 = var2.getText().toString().trim();
         if(var3.length() > 0) {
            PhotoFeedbackActivity.this.showDialog(1);
            PhotoFeedbackActivity var4 = PhotoFeedbackActivity.this;
            AppSession var5 = PhotoFeedbackActivity.this.mAppSession;
            PhotoFeedbackActivity var6 = PhotoFeedbackActivity.this;
            String var7 = PhotoFeedbackActivity.this.mPhotoId;
            String var8 = var5.photoAddComment(var6, var7, var3);
            var4.mAddCommentReqId = var8;
            InputMethodManager var10 = (InputMethodManager)PhotoFeedbackActivity.this.getSystemService("input_method");
            IBinder var11 = var2.getWindowToken();
            var10.hideSoftInputFromWindow(var11, 0);
         }
      }
   }

   private static class PhotoFeedbackAdapter extends BaseAdapter {

      private final List<FacebookPhotoComment> mComments;
      private final Context mContext;
      private final ProfileImagesCache mUserImagesCache;
      private final List<ViewHolder<Long>> mViewHolders;


      public PhotoFeedbackAdapter(Context var1, ProfileImagesCache var2) {
         this.mContext = var1;
         ArrayList var3 = new ArrayList();
         this.mComments = var3;
         ArrayList var4 = new ArrayList();
         this.mViewHolders = var4;
         this.mUserImagesCache = var2;
      }

      public void addComment(FacebookPhotoComment var1) {
         this.mComments.add(var1);
         this.notifyDataSetChanged();
      }

      public int getCount() {
         return this.mComments.size();
      }

      public Object getItem(int var1) {
         return this.mComments.get(var1);
      }

      public long getItemId(int var1) {
         return (long)var1;
      }

      public View getView(int var1, View var2, ViewGroup var3) {
         View var4;
         ViewHolder var5;
         if(var2 == null) {
            var4 = ((LayoutInflater)this.mContext.getSystemService("layout_inflater")).inflate(2130903057, (ViewGroup)null);
            var5 = new ViewHolder(var4, 2131623990);
            var4.setTag(var5);
            this.mViewHolders.add(var5);
         } else {
            var4 = var2;
            var5 = (ViewHolder)var2.getTag();
         }

         List var7 = this.mComments;
         FacebookPhotoComment var9 = (FacebookPhotoComment)var7.get(var1);
         long var10 = var9.getFromProfileId();
         Long var12 = Long.valueOf(var10);
         var5.setItemId(var12);
         FacebookProfile var13 = var9.getFromProfile();
         String var14 = var13.mImageUrl;
         if(var14 != null) {
            ProfileImagesCache var15 = this.mUserImagesCache;
            Context var16 = this.mContext;
            Bitmap var17 = var15.get(var16, var10, var14);
            if(var17 != null) {
               var5.mImageView.setImageBitmap(var17);
            } else {
               var5.mImageView.setImageResource(2130837747);
            }
         } else {
            var5.mImageView.setImageResource(2130837747);
         }

         TextView var18 = (TextView)var4.findViewById(2131623991);
         String var19 = var13.mDisplayName;
         var18.setText(var19);
         TextView var22 = (TextView)var4.findViewById(2131623993);
         String var23 = var9.getBody();
         var22.setText(var23);
         Context var26 = this.mContext;
         StringUtils.TimeFormatStyle var27 = StringUtils.TimeFormatStyle.STREAM_RELATIVE_STYLE;
         long var28 = var9.getTime() * 1000L;
         String var30 = StringUtils.getTimeAsString(var26, var27, var28);
         TextView var31 = (TextView)var4.findViewById(2131623992);
         var31.setText(var30);
         return var4;
      }

      public void setComments(List<FacebookPhotoComment> var1) {
         this.mComments.clear();
         this.mComments.addAll(var1);
         this.notifyDataSetChanged();
      }

      public void updateUserImage(ProfileImage var1) {
         Iterator var2 = this.mViewHolders.iterator();

         while(var2.hasNext()) {
            ViewHolder var3 = (ViewHolder)var2.next();
            Long var4 = (Long)var3.getItemId();
            if(var4 != null) {
               Long var5 = Long.valueOf(var1.id);
               if(var4.equals(var5)) {
                  ImageView var6 = var3.mImageView;
                  Bitmap var7 = var1.getBitmap();
                  var6.setImageBitmap(var7);
               }
            }
         }

      }
   }
}
