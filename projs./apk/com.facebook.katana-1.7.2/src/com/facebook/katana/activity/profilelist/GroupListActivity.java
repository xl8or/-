package com.facebook.katana.activity.profilelist;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.ProfileTabHostActivity;
import com.facebook.katana.activity.profilelist.GroupListAdapter;
import com.facebook.katana.activity.profilelist.ProfileListActivity;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.ProfileImagesCache;
import com.facebook.katana.model.FacebookGroup;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.service.method.FqlGetGroups;
import com.facebook.katana.ui.SectionedListView;
import java.util.List;

public class GroupListActivity extends ProfileListActivity {

   private static final int REFRESH_ID = 2;


   public GroupListActivity() {}

   private void setupEmptyView() {
      TextView var1 = (TextView)this.findViewById(2131624022);
      TextView var2 = (TextView)this.findViewById(2131624024);
      var1.setText(2131361915);
      var2.setText(2131361914);
   }

   private void showProgress(boolean var1) {
      if(var1) {
         this.findViewById(2131624177).setVisibility(0);
      } else {
         this.findViewById(2131624177).setVisibility(8);
      }

      this.setListLoading(var1);
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.setContentView(2130903153);
      AppSession var2 = AppSession.getActiveSession(this, (boolean)1);
      this.mAppSession = var2;
      if(this.mAppSession == null) {
         LoginActivity.toLogin(this);
      } else {
         ProfileImagesCache var3 = this.mAppSession.getUserImagesCache();
         GroupListAdapter var4 = new GroupListAdapter(this, var3);
         this.mAdapter = var4;
         SectionedListView var5 = (SectionedListView)this.getListView();
         ProfileListActivity.ProfileListAdapter var6 = this.mAdapter;
         var5.setSectionedListAdapter(var6);
         this.setupEmptyView();
         GroupListActivity.GroupsListListener var7 = new GroupListActivity.GroupsListListener();
         this.mAppSessionListener = var7;
      }
   }

   public boolean onCreateOptionsMenu(Menu var1) {
      super.onCreateOptionsMenu(var1);
      MenuItem var3 = var1.add(0, 2, 0, 2131362246).setIcon(2130837692);
      return true;
   }

   public void onListItemClick(ListView var1, View var2, int var3, long var4) {
      FacebookProfile var6 = (FacebookProfile)this.mAdapter.getItem(var3);
      long var7 = var6.mId;
      Intent var9 = ProfileTabHostActivity.intentForProfile(this, var7);
      String var10 = var6.mDisplayName;
      var9.putExtra("extra_user_display_name", var10);
      String var12 = var6.mImageUrl;
      var9.putExtra("extra_image_url", var12);
      Intent var14 = var9.putExtra("extra_user_type", 3);
      this.startActivity(var9);
   }

   public boolean onOptionsItemSelected(MenuItem var1) {
      boolean var2;
      switch(var1.getItemId()) {
      case 2:
         String var3 = FqlGetGroups.RequestGroups(this);
         this.showProgress((boolean)1);
         var2 = true;
         break;
      default:
         var2 = super.onOptionsItemSelected(var1);
      }

      return var2;
   }

   public void onResume() {
      super.onResume();
      AppSession var1 = AppSession.getActiveSession(this, (boolean)1);
      this.mAppSession = var1;
      if(this.mAppSession == null) {
         LoginActivity.toLogin(this);
      } else {
         String var2 = FqlGetGroups.RequestGroups(this);
         this.showProgress((boolean)1);
      }
   }

   public class GroupsListListener extends ProfileListActivity.ProfileListListener {

      public GroupsListListener() {
         super();
      }

      public void onGetGroupsComplete(AppSession var1, String var2, int var3, String var4, Exception var5, List<FacebookGroup> var6) {
         if(var3 == 200) {
            ((GroupListAdapter)GroupListActivity.this.mAdapter).updateProfileList(var6);
         }

         GroupListActivity.this.showProgress((boolean)0);
      }
   }
}
