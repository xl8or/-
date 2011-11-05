package com.facebook.katana.activity.profilelist;

import android.os.Bundle;
import android.widget.TextView;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.activity.profilelist.ProfileListActivity;
import com.facebook.katana.activity.profilelist.ProfileListDynamicAdapter;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.ProfileImagesCache;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.ui.SectionedListView;
import java.util.ArrayList;
import java.util.List;

public class TaggedUsersActivity extends ProfileListActivity {

   public static final String EXTRA_PROFILES = "profiles";
   protected List<FacebookProfile> mProfiles;


   public TaggedUsersActivity() {}

   private void setupEmptyView() {
      TextView var1 = (TextView)this.findViewById(2131624022);
      TextView var2 = (TextView)this.findViewById(2131624024);
      var1.setText(2131361904);
      var2.setText(2131361903);
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.setContentView(2130903153);
      AppSession var2 = AppSession.getActiveSession(this, (boolean)1);
      this.mAppSession = var2;
      if(this.mAppSession == null) {
         LoginActivity.toLogin(this);
      } else {
         ArrayList var3 = this.getIntent().getParcelableArrayListExtra("profiles");
         this.mProfiles = var3;
         ProfileImagesCache var4 = this.mAppSession.getUserImagesCache();
         ProfileListDynamicAdapter var5 = new ProfileListDynamicAdapter(this, var4);
         this.mAdapter = var5;
         ProfileListDynamicAdapter var6 = (ProfileListDynamicAdapter)this.mAdapter;
         List var7 = this.mProfiles;
         var6.updateProfileList(var7);
         SectionedListView var8 = (SectionedListView)this.getListView();
         ProfileListActivity.ProfileListAdapter var9 = this.mAdapter;
         var8.setSectionedListAdapter(var9);
         this.setupEmptyView();
         ProfileListActivity.ProfileListListener var10 = new ProfileListActivity.ProfileListListener();
         this.mAppSessionListener = var10;
      }
   }
}
