package com.facebook.katana.activity.profilelist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.ProfileTabHostActivity;
import com.facebook.katana.TabProgressListener;
import com.facebook.katana.TabProgressSource;
import com.facebook.katana.activity.profilelist.ProfileListActivity;
import com.facebook.katana.activity.profilelist.ProfileListDynamicAdapter;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.ProfileImagesCache;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.service.method.FqlGetProfile;
import com.facebook.katana.ui.SectionedListView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class GroupMemberListActivity extends ProfileListActivity implements TabProgressSource {

   public static final String EXTRA_GROUP_ID = "group_id";
   protected long mGroupId = 65535L;
   protected TabProgressListener mProgressListener;
   protected boolean mShowingProgress;


   public GroupMemberListActivity() {}

   private void setupEmptyView() {
      TextView var1 = (TextView)this.findViewById(2131624022);
      TextView var2 = (TextView)this.findViewById(2131624024);
      var1.setText(2131362138);
      var2.setText(2131361932);
   }

   private void showProgress(boolean var1) {
      if(this.mProgressListener != null) {
         this.mProgressListener.onShowProgress(var1);
      }

      this.mShowingProgress = var1;
   }

   public void onCreate(Bundle var1) {
      this.mHasFatTitleHeader = (boolean)1;
      super.onCreate(var1);
      this.setContentView(2130903088);
      AppSession var2 = AppSession.getActiveSession(this, (boolean)1);
      this.mAppSession = var2;
      if(this.mAppSession == null) {
         LoginActivity.toLogin(this);
      } else {
         this.setupListHeaders();
         this.setupFatTitleHeader();
         ProfileImagesCache var3 = this.mAppSession.getUserImagesCache();
         ProfileListDynamicAdapter var4 = new ProfileListDynamicAdapter(this, var3);
         this.mAdapter = var4;
         SectionedListView var5 = (SectionedListView)this.getListView();
         ProfileListActivity.ProfileListAdapter var6 = this.mAdapter;
         var5.setSectionedListAdapter(var6);
         this.setupEmptyView();
         GroupMemberListActivity.GroupMemberListListener var7 = new GroupMemberListActivity.GroupMemberListListener();
         this.mAppSessionListener = var7;
      }
   }

   public void onListItemClick(ListView var1, View var2, int var3, long var4) {
      ProfileListActivity.ProfileListAdapter var6 = this.mAdapter;
      int var7 = this.getCursorPosition(var3);
      FacebookProfile var8 = (FacebookProfile)var6.getItem(var7);
      long var9 = var8.mId;
      Intent var11 = ProfileTabHostActivity.intentForProfile(this, var9);
      String var12 = var8.mDisplayName;
      var11.putExtra("extra_user_display_name", var12);
      String var14 = var8.mImageUrl;
      var11.putExtra("extra_image_url", var14);
      Intent var16 = var11.putExtra("extra_user_type", 0);
      this.startActivity(var11);
   }

   public void onResume() {
      super.onResume();
      AppSession var1 = AppSession.getActiveSession(this, (boolean)1);
      this.mAppSession = var1;
      if(this.mAppSession == null) {
         LoginActivity.toLogin(this);
      } else {
         long var2 = this.getIntent().getLongExtra("group_id", 65535L);
         this.mGroupId = var2;
         long var4 = this.mGroupId;
         FqlGetProfile.RequestGroupMembers(this, var4);
         this.logStepDataRequested();
         this.showProgress((boolean)1);
      }
   }

   public void setProgressListener(TabProgressListener var1) {
      this.mProgressListener = var1;
      if(this.mProgressListener != null) {
         TabProgressListener var2 = this.mProgressListener;
         boolean var3 = this.mShowingProgress;
         var2.onShowProgress(var3);
      }
   }

   public class GroupMemberListListener extends ProfileListActivity.ProfileListListener {

      public GroupMemberListListener() {
         super();
      }

      public void onGetGroupsMembersComplete(AppSession var1, String var2, int var3, String var4, Exception var5, Map<Long, FacebookProfile> var6) {
         if(var3 == 200) {
            GroupMemberListActivity.this.logStepDataReceived();
            ArrayList var7 = new ArrayList();
            Iterator var8 = var6.values().iterator();

            while(var8.hasNext()) {
               FacebookProfile var9 = (FacebookProfile)var8.next();
               var7.add(var9);
            }

            ((ProfileListDynamicAdapter)GroupMemberListActivity.this.mAdapter).updateProfileList(var7);
         }

         GroupMemberListActivity.this.showProgress((boolean)0);
      }
   }
}
