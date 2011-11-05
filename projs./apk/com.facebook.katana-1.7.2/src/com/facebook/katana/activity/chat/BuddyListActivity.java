package com.facebook.katana.activity.chat;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.activity.BaseFacebookListActivity;
import com.facebook.katana.activity.chat.BuddyListSectionedAdapter;
import com.facebook.katana.activity.chat.ChatConversationActivity;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.binding.ChatSession;
import com.facebook.katana.binding.ProfileImage;
import com.facebook.katana.binding.ProfileImagesCache;
import com.facebook.katana.model.FacebookChatMessage;
import com.facebook.katana.model.FacebookChatUser;
import com.facebook.katana.provider.ConnectionsProvider;
import com.facebook.katana.ui.SectionedListView;
import com.facebook.katana.util.Toaster;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class BuddyListActivity extends BaseFacebookListActivity implements OnItemClickListener, OnClickListener {

   public static final int INDEX_USER_DISPLAY_NAME = 3;
   public static final int INDEX_USER_FIRST_NAME = 1;
   public static final int INDEX_USER_ID = 0;
   public static final int INDEX_USER_IMAGE_URL = 4;
   public static final int INDEX_USER_LAST_NAME = 2;
   public static final String[] PROJECTION;
   private BuddyListSectionedAdapter mAdapter;
   private AppSession mAppSession;
   private Map<Long, FacebookChatUser> mBuddiesCache;
   private final ChatSession.FacebookChatListener mChatListener;
   private ChatSession mChatSession;
   private String mCurrentQuery;
   private final SortedSet<FacebookChatUser> mDisplayCache;
   private ProfileImagesCache mImageCache;
   private SectionedListView mListView;
   private BuddyListActivity.QueryHandler mQueryHandler;
   private final AppSessionListener mSessionListener;
   private TextView mTextBox;
   private String mToken;
   private int numConnFailures;


   static {
      String[] var0 = new String[]{"user_id", "first_name", "last_name", "display_name", "user_image_url"};
      PROJECTION = var0;
   }

   public BuddyListActivity() {
      TreeSet var1 = new TreeSet();
      this.mDisplayCache = var1;
      this.mCurrentQuery = null;
      this.mToken = null;
      this.numConnFailures = 0;
      BuddyListActivity.1 var2 = new BuddyListActivity.1();
      this.mChatListener = var2;
      BuddyListActivity.2 var3 = new BuddyListActivity.2();
      this.mSessionListener = var3;
   }

   // $FF: synthetic method
   static int access$208(BuddyListActivity var0) {
      int var1 = var0.numConnFailures;
      int var2 = var1 + 1;
      var0.numConnFailures = var2;
      return var1;
   }

   private void loadBuddyList(String var1, boolean var2) {
      if(this.mChatSession.isConnected()) {
         if(this.mAdapter == null) {
            BuddyListSectionedAdapter var3 = new BuddyListSectionedAdapter;
            ProfileImagesCache var4 = this.mImageCache;
            var3.<init>(this, var4);
            this.mAdapter = var3;
            SectionedListView var9 = this.mListView;
            BuddyListSectionedAdapter var10 = this.mAdapter;
            var9.setSectionedListAdapter(var10);
         }

         Map var11 = this.mChatSession.getOnlineUsers();
         this.mBuddiesCache = var11;
         if(this.mBuddiesCache.isEmpty()) {
            byte var13 = 0;
            this.showProgress((boolean)var13);
         }

         ArrayList var14 = new ArrayList();
         StringBuilder var15 = new StringBuilder;
         String var17 = "user_id";
         var15.<init>(var17);
         String var19 = " IN (";
         var15.append(var19);
         boolean var21 = true;
         boolean var22;
         if(this.mDisplayCache.size() > 0 && !var2) {
            var22 = true;
         } else {
            var22 = false;
         }

         Iterator var23 = this.mBuddiesCache.values().iterator();

         while(var23.hasNext()) {
            FacebookChatUser var24 = (FacebookChatUser)var23.next();
            if(!var22 || !var24.infoInitialized) {
               String var25 = Long.toString(var24.mUserId);
               boolean var28 = var14.add(var25);
               if(!var21) {
                  String var30 = ", ";
                  var15.append(var30);
               }

               String var33 = "?";
               var15.append(var33);
               var21 = false;
            }
         }

         for(Iterator var35 = this.mChatSession.getActiveConversations().keySet().iterator(); var35.hasNext(); var21 = false) {
            String var36 = Long.toString(((Long)var35.next()).longValue());
            boolean var39 = var14.add(var36);
            if(!var21) {
               String var41 = ", ";
               var15.append(var41);
            }

            String var44 = "?";
            var15.append(var44);
         }

         String var47 = ")";
         var15.append(var47);
         if(var14.size() != 0) {
            if(var1 != null) {
               StringBuilder var49 = (new StringBuilder()).append(" AND (first_name LIKE ");
               StringBuilder var50 = new StringBuilder();
               String var52 = DatabaseUtils.sqlEscapeString(var50.append(var1).append("%").toString());
               StringBuilder var53 = var49.append(var52).append(" OR ").append("last_name").append(" LIKE ");
               StringBuilder var54 = new StringBuilder();
               String var56 = DatabaseUtils.sqlEscapeString(var54.append(var1).append("%").toString());
               StringBuilder var57 = var53.append(var56).append(" OR ").append("display_name").append(" LIKE ");
               StringBuilder var58 = new StringBuilder();
               String var60 = DatabaseUtils.sqlEscapeString(var58.append(var1).append("%").toString());
               String var61 = var57.append(var60).append(")").toString();
               StringBuilder var64 = var15.append(var61);
            }

            BuddyListActivity.QueryHandler var65 = this.mQueryHandler;
            Uri var66 = ConnectionsProvider.FRIENDS_CONTENT_URI;
            String[] var67 = PROJECTION;
            String var68 = var15.toString();
            String[] var69 = new String[0];
            String[] var72 = (String[])var14.toArray(var69);
            var65.startQuery(1, var1, var66, var67, var68, var72, (String)null);
         }
      }
   }

   private void modifyBuddy(FacebookChatUser var1) {
      if(this.mChatSession.isConnected()) {
         if(var1 != null) {
            if(!var1.infoInitialized) {
               if(this.mAdapter == null) {
                  ProfileImagesCache var2 = this.mImageCache;
                  BuddyListSectionedAdapter var3 = new BuddyListSectionedAdapter(this, var2);
                  this.mAdapter = var3;
                  SectionedListView var4 = this.mListView;
                  BuddyListSectionedAdapter var5 = this.mAdapter;
                  var4.setSectionedListAdapter(var5);
               }

               Map var6 = this.mChatSession.getOnlineUsers();
               this.mBuddiesCache = var6;
               StringBuilder var7 = new StringBuilder("user_id");
               StringBuilder var8 = var7.append("=?");
               String[] var9 = new String[1];
               int var10 = 0;

               while(true) {
                  int var11 = var9.length;
                  if(var10 >= var11) {
                     BuddyListActivity.QueryHandler var13 = this.mQueryHandler;
                     Uri var14 = ConnectionsProvider.FRIENDS_CONTENT_URI;
                     String[] var15 = PROJECTION;
                     String var16 = var7.toString();
                     Object var17 = null;
                     var13.startQuery(1, (Object)null, var14, var15, var16, var9, (String)var17);
                     return;
                  }

                  String var12 = String.valueOf(var1.mUserId);
                  var9[var10] = var12;
                  ++var10;
               }
            }
         }
      }
   }

   private void showProgress(boolean var1) {
      if(var1) {
         this.findViewById(2131623960).setVisibility(0);
         this.findViewById(2131624023).setVisibility(0);
         this.findViewById(2131624022).setVisibility(8);
         this.getListView().setVisibility(8);
      } else {
         this.findViewById(2131623960).setVisibility(8);
         this.findViewById(2131624023).setVisibility(8);
         this.findViewById(2131624022).setVisibility(0);
         this.getListView().setVisibility(0);
      }
   }

   private void updateDisplay() {
      int var1 = this.mListView.getFirstVisiblePosition();
      if(this.mAdapter != null) {
         if(this.mDisplayCache.size() > 0) {
            this.showProgress((boolean)0);
         }

         BuddyListSectionedAdapter var2 = this.mAdapter;
         SortedSet var3 = this.mDisplayCache;
         Map var4 = this.mChatSession.getActiveConversations();
         byte var5;
         if(this.mCurrentQuery != null) {
            var5 = 1;
         } else {
            var5 = 0;
         }

         var2.redraw(var3, var4, (boolean)var5);
         if(var1 > 0) {
            this.mListView.setSelection(var1);
         }
      }
   }

   public void onClick(View var1) {
      boolean var2 = this.facebookOnBackPressed();
   }

   public void onConfigurationChanged(Configuration var1) {
      super.onConfigurationChanged(var1);
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.setContentView(2130903049);
      this.getListView().setOnItemClickListener(this);
      ((TextView)this.findViewById(2131623959)).setOnClickListener(this);
      this.mBuddiesCache = null;
      this.mCurrentQuery = null;
      BuddyListActivity.QueryHandler var2 = new BuddyListActivity.QueryHandler(this);
      this.mQueryHandler = var2;
      SectionedListView var3 = (SectionedListView)this.getListView();
      this.mListView = var3;
      TextView var4 = (TextView)this.findViewById(2131623961);
      this.mTextBox = var4;
      String var5 = this.getIntent().getStringExtra("token");
      this.mToken = var5;
      ((TextView)this.findViewById(2131624024)).setText(2131362310);
      ((TextView)this.findViewById(2131624022)).setText(2131361833);
   }

   public boolean onCreateOptionsMenu(Menu var1) {
      this.getMenuInflater().inflate(2131558400, var1);
      return true;
   }

   public void onItemClick(AdapterView var1, View var2, int var3, long var4) {
      FacebookChatUser var6 = (FacebookChatUser)this.mAdapter.getItem(var3);
      Intent var7 = new Intent(this, ChatConversationActivity.class);
      var7.putExtra("buddy", var6);
      this.startActivity(var7);
   }

   public boolean onOptionsItemSelected(MenuItem var1) {
      switch(var1.getItemId()) {
      case 2131624315:
         ChatSession.shutdown((boolean)0);
         this.finish();
      default:
         return true;
      }
   }

   protected void onPause() {
      super.onPause();
      AppSession var1 = this.mAppSession;
      AppSessionListener var2 = this.mSessionListener;
      var1.removeListener(var2);
      ChatSession var3 = this.mChatSession;
      ChatSession.FacebookChatListener var4 = this.mChatListener;
      var3.removeListener(var4);
   }

   protected void onResume() {
      super.onResume();
      AppSession var1 = AppSession.getActiveSession(this, (boolean)1);
      this.mAppSession = var1;
      if(this.mAppSession == null) {
         LoginActivity.toLogin(this);
      } else {
         AppSession var2 = this.mAppSession;
         AppSessionListener var3 = this.mSessionListener;
         var2.addListener(var3);
         ChatSession var4 = ChatSession.getActiveChatSession(this);
         this.mChatSession = var4;
         ChatSession var5 = this.mChatSession;
         ChatSession.FacebookChatListener var6 = this.mChatListener;
         var5.addListener(var6);
         ChatSession var7 = this.mChatSession;
         String var8 = this.mToken;
         var7.connect((boolean)0, var8);
         ProfileImagesCache var9 = this.mAppSession.getUserImagesCache();
         this.mImageCache = var9;
         this.mChatSession.getChatNotificationsManager().clear();
         if(this.mChatSession.isConnected()) {
            String var10 = this.mCurrentQuery;
            this.loadBuddyList(var10, (boolean)1);
         } else {
            this.showProgress((boolean)1);
         }

         this.mToken = null;
         this.getWindow().setSoftInputMode(3);
         TextView var11 = this.mTextBox;
         BuddyListActivity.3 var12 = new BuddyListActivity.3();
         var11.addTextChangedListener(var12);
      }
   }

   private final class QueryHandler extends AsyncQueryHandler {

      public static final int QUERY_FRIENDS_TOKEN = 1;


      public QueryHandler(Context var2) {
         ContentResolver var3 = var2.getContentResolver();
         super(var3);
      }

      protected void onQueryComplete(int var1, Object var2, Cursor var3) {
         if(var3 != null) {
            if(BuddyListActivity.this.mBuddiesCache != null) {
               if(BuddyListActivity.this.isFinishing()) {
                  var3.close();
               } else {
                  if(var2 != null) {
                     BuddyListActivity.this.mDisplayCache.clear();
                  }

                  boolean var13;
                  for(boolean var4 = var3.moveToFirst(); !var3.isAfterLast(); var13 = var3.moveToNext()) {
                     Map var5 = BuddyListActivity.this.mBuddiesCache;
                     Long var6 = Long.valueOf(var3.getLong(0));
                     FacebookChatUser var7 = (FacebookChatUser)var5.get(var6);
                     if(var7 != null) {
                        if(!var7.infoInitialized) {
                           String var8 = var3.getString(1);
                           String var9 = var3.getString(2);
                           String var10 = var3.getString(3);
                           String var11 = var3.getString(4);
                           var7.setUserInfo(var8, var9, var10, var11);
                        }

                        boolean var12 = BuddyListActivity.this.mDisplayCache.add(var7);
                     }
                  }

                  BuddyListActivity.this.updateDisplay();
                  var3.close();
               }
            }
         }
      }
   }

   class 3 implements TextWatcher {

      3() {}

      public void afterTextChanged(Editable var1) {}

      public void beforeTextChanged(CharSequence var1, int var2, int var3, int var4) {}

      public void onTextChanged(CharSequence var1, int var2, int var3, int var4) {
         if(!TextUtils.isEmpty(var1)) {
            BuddyListActivity var5 = BuddyListActivity.this;
            String var6 = var1.toString();
            var5.mCurrentQuery = var6;
         } else {
            String var10 = BuddyListActivity.this.mCurrentQuery = null;
         }

         BuddyListActivity var8 = BuddyListActivity.this;
         String var9 = BuddyListActivity.this.mCurrentQuery;
         var8.loadBuddyList(var9, (boolean)1);
      }
   }

   class 2 extends AppSessionListener {

      2() {}

      public void onFriendsSyncComplete(AppSession var1, String var2, int var3, String var4, Exception var5) {
         BuddyListActivity var6 = BuddyListActivity.this;
         String var7 = BuddyListActivity.this.mCurrentQuery;
         var6.loadBuddyList(var7, (boolean)0);
      }

      public void onProfileImageDownloaded(AppSession var1, String var2, int var3, String var4, Exception var5, ProfileImage var6, ProfileImagesCache var7) {
         if(BuddyListActivity.this.mAdapter != null) {
            if(var3 == 200) {
               BuddyListActivity.this.mAdapter.updateUserImage(var6);
            }
         }
      }

      public void onProfileImageLoaded(AppSession var1, ProfileImage var2) {
         if(BuddyListActivity.this.mAdapter != null) {
            BuddyListActivity.this.mAdapter.updateUserImage(var2);
         }
      }
   }

   class 1 implements ChatSession.FacebookChatListener {

      1() {}

      public void onConnectionClosed() {
         BuddyListActivity.this.showProgress((boolean)1);
         int var1 = BuddyListActivity.access$208(BuddyListActivity.this);
         if(BuddyListActivity.this.numConnFailures > 1) {
            Toaster.toast(BuddyListActivity.this, 2131361902);
            int var2 = BuddyListActivity.this.numConnFailures = 0;
         }
      }

      public void onConnectionEstablished() {
         BuddyListActivity var1 = BuddyListActivity.this;
         String var2 = BuddyListActivity.this.mCurrentQuery;
         var1.loadBuddyList(var2, (boolean)1);
         int var3 = BuddyListActivity.this.numConnFailures = 0;
      }

      public void onNewChatMessage(FacebookChatMessage var1) {
         FacebookChatMessage.Type var2 = var1.mMessageType;
         FacebookChatMessage.Type var3 = FacebookChatMessage.Type.NORMAL;
         if(var2 == var3) {
            BuddyListActivity.this.updateDisplay();
         }
      }

      public void onPresenceChange(FacebookChatUser var1, boolean var2) {
         if(var2) {
            BuddyListActivity.this.modifyBuddy(var1);
         } else {
            BuddyListActivity.this.updateDisplay();
         }
      }

      public void onShutdown() {}
   }
}
