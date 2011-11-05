package com.facebook.katana;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.katana.IntentUriHandler;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.MyTabHost;
import com.facebook.katana.PageInfoActivity;
import com.facebook.katana.TabProgressListener;
import com.facebook.katana.TabProgressSource;
import com.facebook.katana.UserInfoActivity;
import com.facebook.katana.activity.BaseFacebookTabActivity;
import com.facebook.katana.activity.media.AlbumsActivity;
import com.facebook.katana.activity.places.PlacesInfoActivity;
import com.facebook.katana.activity.profilelist.GroupMemberListActivity;
import com.facebook.katana.activity.stream.StreamActivity;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.model.FacebookPlace;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.model.FacebookStreamType;
import com.facebook.katana.model.FacebookUserFull;
import com.facebook.katana.provider.PhotosProvider;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.service.method.FqlGetPageFanStatus;
import com.facebook.katana.service.method.PagesAddFan;
import com.facebook.katana.service.method.PlacesFlag;
import com.facebook.katana.service.method.UserSetContactInfo;
import com.facebook.katana.util.ApplicationUtils;
import com.facebook.katana.util.GrowthUtils;
import com.facebook.katana.util.StringUtils;
import org.json.JSONException;

public class ProfileTabHostActivity extends BaseFacebookTabActivity implements MyTabHost.OnTabChangeListener {

   public static final String EXTRA_STARTING_TAB = "tab";
   private static final int FLAG_PLACES_DIALOG = 1;
   private static final int FLAG_PLACES_ID = 101;
   private static final int MESSAGE_ID = 102;
   private static final int PHONE_NUMBER_ENTER = 2;
   private static final int PHONE_NUMBER_FAIL = 3;
   public static final String TAG_GROUP_MEMBERS = "group_members";
   public static final String TAG_INFO = "info";
   public static final String TAG_PHOTOS = "photos";
   public static final String TAG_WALL = "wall";
   private AppSession mAppSession;
   private AppSessionListener mAppSessionListener;
   private boolean mCanLike;
   private TabProgressSource mCurrentSource;
   private long mMyUserId;
   private String mPendingLikeReqId;
   private long mProfileId;
   private int mProfileType;
   private TabProgressListener mProgressListener;


   public ProfileTabHostActivity() {}

   public static Intent intentForProfile(Context var0, long var1) {
      String var3 = "fb://profile/" + var1;
      return IntentUriHandler.getIntentForUri(var0, var3);
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      byte var3 = 1;
      AppSession var4 = AppSession.getActiveSession(this, (boolean)var3);
      this.mAppSession = var4;
      if(this.mAppSession == null) {
         Intent var5 = this.getIntent();
         LoginActivity.toLogin(this, var5);
      } else {
         long var8 = this.mAppSession.getSessionInfo().userId;
         this.mMyUserId = var8;
         long var10 = this.mMyUserId;
         this.mProfileId = var10;
         Uri var12 = this.getIntent().getData();
         String var13 = null;
         if(var12 != null) {
            String var14 = var12.getScheme();
            String var15 = "content";
            if(var14.equals(var15)) {
               String var16 = var12.getAuthority();
               if("com.android.contacts".equals(var16)) {
                  ContentResolver var17 = this.getContentResolver();
                  String var18 = this.getIntent().resolveType(var17);
                  if("vnd.android.cursor.item/vnd.facebook.profile".equals(var18)) {
                     String[] var19 = new String[]{"data1"};
                     Cursor var20 = var17.query(var12, var19, (String)null, (String[])null, (String)null);
                     if(var20 != null) {
                        try {
                           if(var20.moveToFirst()) {
                              long var21 = var20.getLong(0);
                              this.mProfileId = var21;
                           }
                        } finally {
                           if(var20 != null) {
                              var20.close();
                           }

                        }
                     }
                  }
               }
            }
         } else {
            Intent var142 = this.getIntent();
            long var143 = this.mMyUserId;
            long var145 = var142.getLongExtra("extra_user_id", var143);
            this.mProfileId = var145;
            var13 = this.getIntent().getStringExtra("tab");
         }

         if(var13 == null) {
            var13 = "wall";
         }

         Intent var23 = this.getIntent();
         int var24 = var23.getIntExtra("extra_user_type", 0);
         this.mProfileType = var24;
         int var26 = 2130903145;
         this.setContentView(var26);
         MyTabHost var27 = (MyTabHost)this.getTabHost();
         Bundle var28 = new Bundle();
         String var30 = "within_tab";
         byte var31 = 1;
         var28.putBoolean(var30, (boolean)var31);
         String var32 = this.getTag();
         String var34 = "extra_parent_tag";
         var28.putString(var34, var32);
         int var36 = this.mProfileType;
         String var38 = "extra_user_type";
         var28.putInt(var38, var36);
         long var40 = this.mProfileId;
         String var43 = "extra_user_id";
         var28.putLong(var43, var40);
         if(this.mProfileType == 2) {
            Parcelable var46 = var23.getParcelableExtra("extra_place");
            String var48 = "extra_place";
            var28.putParcelable(var48, var46);
         }

         Intent var56;
         if(this.mProfileType == 0) {
            StringBuilder var50 = (new StringBuilder()).append("fb://profile/");
            long var51 = this.mProfileId;
            String var53 = var50.append(var51).append("/wall/inner").toString();
            var56 = IntentUriHandler.getIntentForUri(this, var53);
         } else if(this.mProfileType == 3) {
            StringBuilder var147 = (new StringBuilder()).append("fb://group/");
            long var148 = this.mProfileId;
            String var150 = var147.append(var148).append("/wall/inner").toString();
            var56 = IntentUriHandler.getIntentForUri(this, var150);
         } else if(this.mProfileType == 4) {
            StringBuilder var153 = (new StringBuilder()).append("fb://event/");
            long var154 = this.mProfileId;
            String var156 = var153.append(var154).append("/wall/inner").toString();
            var56 = IntentUriHandler.getIntentForUri(this, var156);
         } else {
            var56 = new Intent;
            Class var161 = StreamActivity.class;
            var56.<init>(this, var161);
            long var162 = this.mProfileId;
            var56.putExtra("extra_user_id", var162);
            if(this.mProfileType == 2) {
               String var165 = FacebookStreamType.PLACE_ACTIVITY_STREAM.toString();
               var56.putExtra("extra_type", var165);
            } else if(this.mProfileType == 3) {
               String var171 = FacebookStreamType.GROUP_WALL_STREAM.toString();
               var56.putExtra("extra_type", var171);
            } else if(this.mProfileType == 1) {
               String var173 = FacebookStreamType.PAGE_WALL_STREAM.toString();
               var56.putExtra("extra_type", var173);
            } else {
               String var175 = FacebookStreamType.PROFILE_WALL_STREAM.toString();
               var56.putExtra("extra_type", var175);
            }

            String var167 = var23.getStringExtra("extra_user_display_name");
            var56.putExtra("extra_user_display_name", var167);
            String var169 = var23.getStringExtra("extra_image_url");
            var56.putExtra("extra_image_url", var169);
         }

         int var57;
         if(this.mProfileType == 2) {
            var57 = 2131362117;
         } else {
            var57 = 2131362168;
         }

         String var59 = "wall";
         RadioButton var61 = this.setupAndGetTabView(var59, var57);
         Intent var64 = var56.putExtras(var28);
         String var66 = "wall";
         MyTabHost.TabSpec var68 = var27.myNewTabSpec(var66, var61);
         MyTabHost.TabSpec var71 = var68.setContent(var56);
         var27.addTab(var68);
         Intent var72 = null;
         if(this.mProfileType == 2) {
            var72 = new Intent;
            Class var75 = PlacesInfoActivity.class;
            var72.<init>(this, var75);
         } else if(this.mProfileType == 0) {
            var72 = new Intent;
            Class var179 = UserInfoActivity.class;
            var72.<init>(this, var179);
            long var180 = this.mProfileId;
            var72.putExtra("com.facebook.katana.profile.id", var180);
            Intent var183 = var72.putExtra("com.facebook.katana.profile.show_photo", (boolean)0);
         } else if(this.mProfileType == 1) {
            var72 = new Intent;
            Class var186 = PageInfoActivity.class;
            var72.<init>(this, var186);
            long var187 = this.mProfileId;
            var72.putExtra("com.facebook.katana.profile.id", var187);
            Intent var190 = var72.putExtra("com.facebook.katana.profile.show_photo", (boolean)0);
         }

         if(var72 != null) {
            Intent var78 = var72.putExtras(var28);
            String var80 = "info";
            int var81 = 2131362130;
            RadioButton var82 = this.setupAndGetTabView(var80, var81);
            String var84 = "info";
            MyTabHost.TabSpec var86 = var27.myNewTabSpec(var84, var82);
            MyTabHost.TabSpec var89 = var86.setContent(var72);
            var27.addTab(var86);
         }

         if(this.mProfileType == 3) {
            Intent var90 = new Intent;
            Class var93 = GroupMemberListActivity.class;
            var90.<init>(this, var93);
            long var94 = this.mProfileId;
            var90.putExtra("group_id", var94);
            Intent var99 = var90.putExtras(var28);
            String var101 = "group_members";
            int var102 = 2131362128;
            RadioButton var103 = this.setupAndGetTabView(var101, var102);
            String var105 = "group_members";
            MyTabHost.TabSpec var107 = var27.myNewTabSpec(var105, var103);
            MyTabHost.TabSpec var110 = var107.setContent(var90);
            var27.addTab(var107);
         } else {
            Intent var191 = new Intent;
            Class var194 = AlbumsActivity.class;
            var191.<init>(this, var194);
            Intent var195 = var191.setAction("android.intent.action.VIEW");
            Intent var198 = var191.putExtras(var28);
            long var199 = this.mProfileId;
            long var201 = this.mMyUserId;
            if(var199 != var201) {
               Intent var203 = var191.putExtra("extra_exclude_empty", (boolean)1);
            }

            Uri var204 = PhotosProvider.ALBUMS_OWNER_CONTENT_URI;
            String var205 = String.valueOf(this.mProfileId);
            Uri var206 = Uri.withAppendedPath(var204, var205);
            Intent var209 = var191.setData(var206);
            String var211 = "photos";
            int var212 = 2131362141;
            RadioButton var213 = this.setupAndGetTabView(var211, var212);
            String var215 = "photos";
            MyTabHost.TabSpec var217 = var27.myNewTabSpec(var215, var213);
            MyTabHost.TabSpec var220 = var217.setContent(var191);
            var27.addTab(var217);
         }

         this.setupTabs();
         var27.setCurrentTabByTag(var13);
         this.onTabChanged(var13);
         var27.setOnTabChangedListener(this);
         ProfileTabHostActivity.1 var117 = new ProfileTabHostActivity.1();
         this.mProgressListener = var117;
         TabProgressSource var121 = (TabProgressSource)this.getCurrentActivity();
         this.mCurrentSource = var121;
         TabProgressSource var122 = this.mCurrentSource;
         TabProgressListener var123 = this.mProgressListener;
         var122.setProgressListener(var123);
         ProfileTabHostActivity.ProfileAppSessionListener var124 = new ProfileTabHostActivity.ProfileAppSessionListener;
         Object var127 = null;
         var124.<init>((ProfileTabHostActivity.1)var127);
         this.mAppSessionListener = var124;
         long var129 = this.mProfileId;
         long var131 = this.mMyUserId;
         if(var129 == var131) {
            if(GrowthUtils.showPhoneNumberDialog(this)) {
               AppSession var133 = this.mAppSession;
               long var134 = this.mMyUserId;
               String var140 = var133.usersGetInfo(this, var134);
            }
         }
      }
   }

   protected Dialog onCreateDialog(int var1) {
      AlertDialog var2;
      switch(var1) {
      case 1:
         CharSequence[] var3 = new CharSequence[4];
         CharSequence var4 = this.getText(2131362376);
         var3[0] = var4;
         CharSequence var5 = this.getText(2131362372);
         var3[1] = var5;
         CharSequence var6 = this.getText(2131362400);
         var3[2] = var6;
         CharSequence var7 = this.getText(2131362375);
         var3[3] = var7;
         String[] var8 = new String[4];
         String var9 = PlacesFlag.INFO_INCORRECT;
         var8[0] = var9;
         String var10 = PlacesFlag.OFFENSIVE;
         var8[1] = var10;
         String var11 = PlacesFlag.CLOSED;
         var8[2] = var11;
         String var12 = PlacesFlag.DUPLICATE;
         var8[3] = var12;
         Builder var13 = new Builder(this);
         Builder var14 = var13.setTitle(2131362371);
         ProfileTabHostActivity.2 var15 = new ProfileTabHostActivity.2(var8);
         var13.setItems(var3, var15);
         var2 = var13.create();
         break;
      case 2:
         View var17 = LayoutInflater.from(this).inflate(2130903119, (ViewGroup)null);
         String var18 = ((TelephonyManager)this.getSystemService("phone")).getLine1Number();
         if(!StringUtils.isBlank(var18)) {
            ((EditText)var17.findViewById(2131624162)).setText(var18);
         }

         Builder var19 = (new Builder(this)).setCancelable((boolean)1).setTitle(2131362438).setView(var17);
         ProfileTabHostActivity.4 var20 = new ProfileTabHostActivity.4(var17);
         Builder var21 = var19.setPositiveButton(2131362441, var20);
         ProfileTabHostActivity.3 var22 = new ProfileTabHostActivity.3();
         var2 = var21.setNegativeButton(2131362396, var22).create();
         break;
      case 3:
         Builder var23 = (new Builder(this)).setCancelable((boolean)1).setTitle(2131361869).setMessage(2131362442);
         ProfileTabHostActivity.6 var24 = new ProfileTabHostActivity.6();
         Builder var25 = var23.setPositiveButton(2131362299, var24);
         ProfileTabHostActivity.5 var26 = new ProfileTabHostActivity.5();
         var2 = var25.setNegativeButton(2131362396, var26).create();
         break;
      default:
         var2 = null;
      }

      return var2;
   }

   public boolean onCreateOptionsMenu(Menu var1) {
      super.onCreateOptionsMenu(var1);
      if(this.mProfileType == 2) {
         MenuItem var3 = var1.add(0, 101, 196608, 2131362373).setIcon(2130837679);
      } else if(this.mProfileType == 0) {
         long var4 = this.mProfileId;
         long var6 = this.mMyUserId;
         if(var4 != var6) {
            MenuItem var8 = var1.add(0, 102, 196608, 2131362169).setIcon(2130837677);
         }
      }

      return true;
   }

   protected void onDestroy() {
      super.onDestroy();
      if(this.mCurrentSource != null) {
         this.mCurrentSource.setProgressListener((TabProgressListener)null);
      }
   }

   public boolean onOptionsItemSelected(MenuItem var1) {
      switch(var1.getItemId()) {
      case 101:
         this.showDialog(1);
         break;
      case 102:
         StringBuilder var2 = (new StringBuilder()).append("fb://messaging/compose/");
         long var3 = this.mProfileId;
         String var5 = var2.append(var3).toString();
         IntentUriHandler.handleUri(this, var5);
      }

      return super.onOptionsItemSelected(var1);
   }

   protected void onPause() {
      super.onPause();
      if(this.mAppSession != null) {
         AppSession var1 = this.mAppSession;
         AppSessionListener var2 = this.mAppSessionListener;
         var1.removeListener(var2);
      }
   }

   protected void onResume() {
      super.onResume();
      AppSession var1 = AppSession.getActiveSession(this, (boolean)1);
      this.mAppSession = var1;
      if(this.mAppSession == null) {
         LoginActivity.toLogin(this);
      } else {
         if(this.mProfileType == 1) {
            long var2 = this.mProfileId;
            FqlGetPageFanStatus.RequestPageFanStatus(this, var2);
         }

         AppSession var5 = this.mAppSession;
         AppSessionListener var6 = this.mAppSessionListener;
         var5.addListener(var6);
      }
   }

   public void onTabChanged(String var1) {
      if(this.mCurrentSource != null) {
         this.mCurrentSource.setProgressListener((TabProgressListener)null);
      }

      TabProgressSource var2 = (TabProgressSource)this.getCurrentActivity();
      this.mCurrentSource = var2;
      TabProgressSource var3 = this.mCurrentSource;
      TabProgressListener var4 = this.mProgressListener;
      var3.setProgressListener(var4);
   }

   public void titleBarPrimaryActionClickHandler(View var1) {
      if(this.mCanLike) {
         String var2 = this.mAppSession.getSessionInfo().sessionKey;
         long var3 = this.mProfileId;
         Object var6 = null;
         PagesAddFan var7 = new PagesAddFan(this, (Intent)null, var2, var3, (ApiMethodListener)var6);
         AppSession var8 = this.mAppSession;
         Object var11 = null;
         String var12 = var8.postToService(this, var7, 1001, 1020, (Bundle)var11);
         this.mPendingLikeReqId = var12;
         this.setPrimaryActionFace(-1, (String)null);
         this.findViewById(2131624177).setVisibility(0);
      }
   }

   class 1 implements TabProgressListener {

      1() {}

      public void onShowProgress(boolean var1) {
         View var2 = ProfileTabHostActivity.this.findViewById(2131624177);
         byte var3;
         if(var1) {
            var3 = 0;
         } else {
            var3 = 8;
         }

         var2.setVisibility(var3);
      }
   }

   class 2 implements OnClickListener {

      // $FF: synthetic field
      final String[] val$itemFlags;


      2(String[] var2) {
         this.val$itemFlags = var2;
      }

      public void onClick(DialogInterface var1, int var2) {
         String var3 = this.val$itemFlags[var2];

         try {
            FacebookPlace var4 = (FacebookPlace)ProfileTabHostActivity.this.getIntent().getParcelableExtra("extra_place");
            AppSession var5 = ProfileTabHostActivity.this.mAppSession;
            ProfileTabHostActivity var6 = ProfileTabHostActivity.this;
            PlacesFlag.FlagPlace(var5, var6, var4, var3);
         } catch (JSONException var14) {
            ;
         }

         var1.dismiss();
         LayoutInflater var8 = ProfileTabHostActivity.this.getLayoutInflater();
         ViewGroup var9 = (ViewGroup)ProfileTabHostActivity.this.findViewById(2131623980);
         View var10 = var8.inflate(2130903053, var9);
         ((ImageView)var10.findViewById(2131623981)).setImageResource(2130837580);
         ((TextView)var10.findViewById(2131623982)).setText(2131362374);
         Context var11 = ProfileTabHostActivity.this.getApplicationContext();
         Toast var12 = new Toast(var11);
         var12.setView(var10);
         var12.setGravity(17, 0, 0);
         var12.show();
      }
   }

   private class ProfileAppSessionListener extends AppSessionListener {

      private ProfileAppSessionListener() {}

      // $FF: synthetic method
      ProfileAppSessionListener(ProfileTabHostActivity.1 var2) {
         this();
      }

      public void onGetPageFanStatusComplete(AppSession var1, String var2, int var3, String var4, Exception var5, long var6, boolean var8) {
         long var9 = ProfileTabHostActivity.this.mProfileId;
         if(var6 == var9) {
            if(!var8) {
               boolean var11 = (boolean)(ProfileTabHostActivity.this.mCanLike = (boolean)1);
               ProfileTabHostActivity var12 = ProfileTabHostActivity.this;
               String var13 = ProfileTabHostActivity.this.getString(2131362231);
               var12.setPrimaryActionFace(-1, var13);
            } else {
               boolean var14 = (boolean)(ProfileTabHostActivity.this.mCanLike = (boolean)0);
               ProfileTabHostActivity.this.setPrimaryActionFace(-1, (String)null);
            }
         }
      }

      public void onPagesAddFanComplete(AppSession var1, String var2, int var3, String var4, Exception var5, boolean var6) {
         if(var3 == 200) {
            String var7 = ProfileTabHostActivity.this.mPendingLikeReqId;
            if(var2.equals(var7)) {
               if(ProfileTabHostActivity.this.isOnTop()) {
                  ProfileTabHostActivity var8 = ProfileTabHostActivity.this;
                  long var9 = ProfileTabHostActivity.this.mProfileId;
                  ApplicationUtils.OpenPageProfile(var8, var9, (FacebookProfile)null);
                  ProfileTabHostActivity.this.findViewById(2131624177).setVisibility(8);
                  ProfileTabHostActivity.this.finish();
               }
            }
         }
      }

      public void onUserSetContactInfoComplete(AppSession var1, String var2, int var3, String var4, Exception var5) {
         if(var3 != 200) {
            ProfileTabHostActivity.this.showDialog(3);
         }
      }

      public void onUsersGetInfoComplete(AppSession var1, String var2, int var3, String var4, Exception var5, long var6, FacebookUserFull var8, boolean var9) {
         if(var3 == 200) {
            long var10 = ProfileTabHostActivity.this.mProfileId;
            long var12 = ProfileTabHostActivity.this.mMyUserId;
            if(var10 == var12) {
               long var14 = ProfileTabHostActivity.this.mMyUserId;
               long var16 = var8.mUserId;
               if(var14 == var16) {
                  if(StringUtils.isBlank(var8.mCellPhone)) {
                     if(GrowthUtils.showPhoneNumberDialog(ProfileTabHostActivity.this)) {
                        GrowthUtils.stopPhoneNumberDialog(ProfileTabHostActivity.this);
                        ProfileTabHostActivity.this.showDialog(2);
                     }
                  }
               }
            }
         }
      }
   }

   class 4 implements OnClickListener {

      // $FF: synthetic field
      final View val$dialogView;


      4(View var2) {
         this.val$dialogView = var2;
      }

      public void onClick(DialogInterface var1, int var2) {
         String var3 = ((EditText)this.val$dialogView.findViewById(2131624162)).getText().toString();
         AppSession var4 = ProfileTabHostActivity.this.mAppSession;
         ProfileTabHostActivity var5 = ProfileTabHostActivity.this;
         UserSetContactInfo.setCellNumber(var4, var5, var3);
         ProfileTabHostActivity.this.removeDialog(2);
      }
   }

   class 3 implements OnClickListener {

      3() {}

      public void onClick(DialogInterface var1, int var2) {
         var1.cancel();
      }
   }

   class 6 implements OnClickListener {

      6() {}

      public void onClick(DialogInterface var1, int var2) {
         var1.cancel();
         ProfileTabHostActivity.this.showDialog(2);
      }
   }

   class 5 implements OnClickListener {

      5() {}

      public void onClick(DialogInterface var1, int var2) {
         var1.cancel();
      }
   }
}
