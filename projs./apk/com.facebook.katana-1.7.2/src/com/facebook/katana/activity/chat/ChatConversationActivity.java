package com.facebook.katana.activity.chat;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.activity.BaseFacebookActivity;
import com.facebook.katana.activity.chat.BuddyListActivity;
import com.facebook.katana.activity.chat.ChatMessageAdapter;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.binding.ChatNotificationsManager;
import com.facebook.katana.binding.ChatSession;
import com.facebook.katana.binding.ProfileImage;
import com.facebook.katana.binding.ProfileImagesCache;
import com.facebook.katana.model.FacebookChatMessage;
import com.facebook.katana.model.FacebookChatUser;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.model.FacebookUser;
import com.facebook.katana.provider.ConnectionsProvider;
import com.facebook.katana.util.ApplicationUtils;
import java.util.Iterator;
import java.util.List;

public class ChatConversationActivity extends BaseFacebookActivity implements OnClickListener {

   public static final String EXTRA_BUDDY = "buddy";
   public static final String EXTRA_BUDDY_ID = "buddyId";
   public static final String EXTRA_DISPLAY_NAME = "displayName";
   public static final String EXTRA_TOKEN = "token";
   private boolean closeConversationOnExit = 0;
   private AppSession mAppSession;
   private FacebookChatUser mBuddy;
   private final ChatSession.FacebookChatListener mChatListener;
   private ChatSession mChatSession;
   private ChatMessageAdapter mMessageAdapter;
   private final AppSessionListener mSessionListener;
   private String mToken = null;
   private FacebookChatUser mUser;


   public ChatConversationActivity() {
      ChatConversationActivity.1 var1 = new ChatConversationActivity.1();
      this.mChatListener = var1;
      ChatConversationActivity.2 var2 = new ChatConversationActivity.2();
      this.mSessionListener = var2;
   }

   private void enableSending(boolean var1) {
      this.findViewById(2131623971).setEnabled(var1);
      this.findViewById(2131623973).setEnabled(var1);
      this.findViewById(2131623974).setEnabled(var1);
   }

   private void loadBuddyInfo(long var1) {
      if(var1 != 65535L) {
         FacebookChatUser var3 = this.mChatSession.getUser(var1);
         this.mBuddy = var3;
         if(this.mBuddy == null) {
            FacebookChatUser.Presence var4 = FacebookChatUser.Presence.OFFLINE;
            FacebookChatUser var5 = new FacebookChatUser(var1, var4);
            this.mBuddy = var5;
         }

         if(!this.mBuddy.infoInitialized) {
            String[] var6 = new String[1];
            String var7 = Long.toString(var1);
            var6[0] = var7;
            Uri var8 = ConnectionsProvider.FRIENDS_CONTENT_URI;
            String[] var9 = BuddyListActivity.PROJECTION;
            Cursor var10 = this.managedQuery(var8, var9, "user_id = ?", var6, (String)null);
            boolean var11 = var10.moveToFirst();
            if(var10.getCount() > 0) {
               FacebookChatUser var12 = this.mBuddy;
               String var13 = var10.getString(1);
               String var14 = var10.getString(2);
               String var15 = var10.getString(3);
               String var16 = var10.getString(4);
               var12.setUserInfo(var13, var14, var15, var16);
            }
         }
      }
   }

   private void loadConversationHistory() {
      this.mMessageAdapter.clear();
      ChatSession var1 = this.mChatSession;
      long var2 = this.mBuddy.mUserId;
      List var4 = var1.getConversationHistory(var2);
      if(var4 != null) {
         Iterator var5 = var4.iterator();

         while(var5.hasNext()) {
            FacebookChatMessage var6 = (FacebookChatMessage)var5.next();
            if(var6.mBody != null) {
               this.mMessageAdapter.add(var6);
            }
         }

      }
   }

   private void loadUserInfo() {
      if(this.mUser == null || !this.mUser.infoInitialized) {
         FacebookUser var1 = this.mAppSession.getSessionInfo().getProfile();
         long var2 = var1.mUserId;
         FacebookChatUser.Presence var4 = FacebookChatUser.Presence.AVAILABLE;
         FacebookChatUser var5 = new FacebookChatUser(var2, var4);
         this.mUser = var5;
         FacebookChatUser var6 = this.mUser;
         String var7 = var1.mFirstName;
         String var8 = var1.mLastName;
         String var9 = var1.mDisplayName;
         String var10 = var1.mImageUrl;
         var6.setUserInfo(var7, var8, var9, var10);
      }
   }

   private void setPresenceIcon(boolean var1) {
      ImageView var2 = (ImageView)this.findViewById(2131623970);
      if(var1) {
         var2.setImageResource(2130837571);
      } else {
         int[] var3 = ChatConversationActivity.4.$SwitchMap$com$facebook$katana$model$FacebookChatUser$Presence;
         int var4 = this.mBuddy.mPresence.ordinal();
         switch(var3[var4]) {
         case 1:
            var2.setImageResource(2130837568);
            break;
         case 2:
            var2.setImageResource(2130837569);
            break;
         default:
            var2.setImageResource(2130837570);
         }

         var2.setVisibility(0);
      }
   }

   private void setup() {
      this.loadUserInfo();
      ListView var1 = (ListView)this.findViewById(2131623971);
      FacebookChatUser var2 = this.mUser;
      FacebookChatUser var3 = this.mBuddy;
      ChatMessageAdapter var4 = new ChatMessageAdapter(this, var2, var3);
      this.mMessageAdapter = var4;
      ChatMessageAdapter var5 = this.mMessageAdapter;
      var1.setAdapter(var5);
      this.loadConversationHistory();
      if(this.mMessageAdapter.getCount() > 0) {
         int var6 = this.mMessageAdapter.getCount() - 1;
         var1.setSelection(var6);
      }

      FacebookChatUser.Presence var7 = this.mBuddy.mPresence;
      FacebookChatUser.Presence var8 = FacebookChatUser.Presence.OFFLINE;
      if(var7 == var8) {
         this.enableSending((boolean)0);
      }
   }

   private void showProgress(boolean var1) {
      if(var1) {
         this.findViewById(2131623960).setVisibility(0);
         this.findViewById(2131623970).setVisibility(8);
      } else {
         this.findViewById(2131623960).setVisibility(8);
         this.findViewById(2131623970).setVisibility(0);
      }
   }

   public void onClick(View var1) {
      EditText var2 = (EditText)this.findViewById(2131623973);
      if(var2.getText().length() > 0) {
         ChatSession var3 = this.mChatSession;
         long var4 = this.mBuddy.mUserId;
         String var6 = var2.getText().toString();
         var3.sendChatMessage(var4, var6);
         ChatMessageAdapter var7 = this.mMessageAdapter;
         long var8 = this.mUser.mUserId;
         long var10 = this.mBuddy.mUserId;
         String var12 = var2.getText().toString();
         Long var13 = Long.valueOf(System.currentTimeMillis());
         FacebookChatMessage var14 = new FacebookChatMessage(var8, var10, var12, var13);
         var7.add(var14);
         var2.setText("");
      }
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.setContentView(2130903051);
      ((Button)this.findViewById(2131623974)).setOnClickListener(this);
      TextView var2 = (TextView)this.findViewById(2131623969);
      ChatConversationActivity.3 var3 = new ChatConversationActivity.3();
      var2.setOnClickListener(var3);
   }

   public boolean onCreateOptionsMenu(Menu var1) {
      this.getMenuInflater().inflate(2131558401, var1);
      return true;
   }

   public boolean onOptionsItemSelected(MenuItem var1) {
      boolean var2;
      switch(var1.getItemId()) {
      case 2131624316:
         long var3 = this.mBuddy.mUserId;
         ApplicationUtils.OpenUserProfile(this, var3, (FacebookProfile)null);
         var2 = true;
         break;
      case 2131624317:
         this.closeConversationOnExit = (boolean)1;
         this.finish();
         var2 = true;
         break;
      default:
         var2 = super.onOptionsItemSelected(var1);
      }

      return var2;
   }

   public void onPause() {
      super.onPause();
      ChatNotificationsManager var1 = this.mChatSession.getChatNotificationsManager();
      long var2 = this.mBuddy.mUserId;
      var1.unMarkConversationAsRunning(var2);
      if(!this.closeConversationOnExit) {
         ChatSession var4 = this.mChatSession;
         long var5 = this.mBuddy.mUserId;
         var4.markConversationAsRead(var5);
      } else {
         ChatSession var11 = this.mChatSession;
         long var12 = this.mBuddy.mUserId;
         var11.closeActiveConversation(var12);
      }

      ChatSession var7 = this.mChatSession;
      ChatSession.FacebookChatListener var8 = this.mChatListener;
      var7.removeListener(var8);
      AppSession var9 = this.mAppSession;
      AppSessionListener var10 = this.mSessionListener;
      var9.removeListener(var10);
   }

   protected void onResume() {
      super.onResume();
      AppSession var1 = AppSession.getActiveSession(this, (boolean)1);
      this.mAppSession = var1;
      if(this.mAppSession == null) {
         LoginActivity.toLogin(this);
      } else {
         ChatSession var2 = this.mChatSession;
         ChatSession.FacebookChatListener var3 = this.mChatListener;
         var2.addListener(var3);
         ChatSession var4 = this.mChatSession;
         String var5 = this.mToken;
         var4.connect((boolean)0, var5);
         this.mToken = null;
         ChatNotificationsManager var6 = this.mChatSession.getChatNotificationsManager();
         long var7 = this.mBuddy.mUserId;
         var6.markConversationAsRunning(var7);
         if(this.mChatSession.isConnected()) {
            this.setup();
         } else {
            this.enableSending((boolean)0);
            this.showProgress((boolean)1);
         }

         AppSession var9 = this.mAppSession;
         AppSessionListener var10 = this.mSessionListener;
         var9.addListener(var10);
      }
   }

   protected void onStart() {
      super.onStart();
      Intent var1 = this.getIntent();
      AppSession var2 = AppSession.getActiveSession(this, (boolean)1);
      this.mAppSession = var2;
      if(this.mAppSession == null) {
         LoginActivity.toLogin(this, var1);
      } else {
         String var3 = this.getIntent().getStringExtra("token");
         this.mToken = var3;
         ChatSession var4 = ChatSession.getActiveChatSession(this);
         this.mChatSession = var4;
         FacebookChatUser var5 = (FacebookChatUser)var1.getParcelableExtra("buddy");
         this.mBuddy = var5;
         if(var5 == null) {
            long var6 = var1.getLongExtra("buddyId", 65535L);
            this.loadBuddyInfo(var6);
         }

         this.setPresenceIcon((boolean)0);
         TextView var8 = (TextView)this.findViewById(2131623969);
         String var9 = this.mBuddy.getDisplayName();
         var8.setText(var9);
      }
   }

   class 1 implements ChatSession.FacebookChatListener {

      1() {}

      public void onConnectionClosed() {
         ChatConversationActivity.this.enableSending((boolean)0);
         ChatConversationActivity.this.showProgress((boolean)1);
      }

      public void onConnectionEstablished() {
         ChatConversationActivity.this.showProgress((boolean)0);
         ChatConversationActivity.this.setup();
         if(ChatConversationActivity.this.mBuddy != null) {
            FacebookChatUser.Presence var1 = ChatConversationActivity.this.mBuddy.mPresence;
            FacebookChatUser.Presence var2 = FacebookChatUser.Presence.OFFLINE;
            if(var1 != var2) {
               ChatConversationActivity.this.enableSending((boolean)1);
            }

            ChatConversationActivity.this.setPresenceIcon((boolean)0);
         }
      }

      public void onNewChatMessage(FacebookChatMessage var1) {
         long var2 = var1.mSenderUid;
         long var4 = ChatConversationActivity.this.mBuddy.mUserId;
         if(var2 == var4) {
            int[] var6 = ChatConversationActivity.4.$SwitchMap$com$facebook$katana$model$FacebookChatMessage$Type;
            int var7 = var1.mMessageType.ordinal();
            switch(var6[var7]) {
            case 1:
               ChatConversationActivity.this.setPresenceIcon((boolean)0);
               ChatConversationActivity.this.mMessageAdapter.add(var1);
            case 2:
               ChatConversationActivity.this.setPresenceIcon((boolean)0);
               return;
            case 3:
               ChatConversationActivity.this.setPresenceIcon((boolean)1);
               return;
            default:
            }
         }
      }

      public void onPresenceChange(FacebookChatUser var1, boolean var2) {
         if(var1 != null) {
            long var3 = var1.mUserId;
            long var5 = ChatConversationActivity.this.mBuddy.mUserId;
            if(var3 == var5) {
               ChatConversationActivity.this.mBuddy = var1;
               ChatConversationActivity.this.setPresenceIcon((boolean)0);
               FacebookChatUser.Presence var8 = ChatConversationActivity.this.mBuddy.mPresence;
               FacebookChatUser.Presence var9 = FacebookChatUser.Presence.OFFLINE;
               if(var8 == var9) {
                  ChatConversationActivity.this.enableSending((boolean)0);
               } else {
                  ChatConversationActivity.this.enableSending((boolean)1);
               }
            }
         }
      }

      public void onShutdown() {
         ChatConversationActivity.this.finish();
      }
   }

   class 2 extends AppSessionListener {

      2() {}

      public void onProfileImageDownloaded(AppSession var1, String var2, int var3, String var4, Exception var5, ProfileImage var6, ProfileImagesCache var7) {
         if(var3 == 200) {
            if(ChatConversationActivity.this.mMessageAdapter != null) {
               ChatConversationActivity.this.mMessageAdapter.updateUserImage(var6);
            }
         }
      }

      public void onProfileImageLoaded(AppSession var1, ProfileImage var2) {
         if(ChatConversationActivity.this.mMessageAdapter != null) {
            ChatConversationActivity.this.mMessageAdapter.updateUserImage(var2);
         }
      }
   }

   class 3 implements OnClickListener {

      3() {}

      public void onClick(View var1) {
         boolean var2 = ChatConversationActivity.this.facebookOnBackPressed();
      }
   }

   // $FF: synthetic class
   static class 4 {

      // $FF: synthetic field
      static final int[] $SwitchMap$com$facebook$katana$model$FacebookChatMessage$Type;
      // $FF: synthetic field
      static final int[] $SwitchMap$com$facebook$katana$model$FacebookChatUser$Presence = new int[FacebookChatUser.Presence.values().length];


      static {
         try {
            int[] var0 = $SwitchMap$com$facebook$katana$model$FacebookChatUser$Presence;
            int var1 = FacebookChatUser.Presence.AVAILABLE.ordinal();
            var0[var1] = 1;
         } catch (NoSuchFieldError var19) {
            ;
         }

         try {
            int[] var2 = $SwitchMap$com$facebook$katana$model$FacebookChatUser$Presence;
            int var3 = FacebookChatUser.Presence.IDLE.ordinal();
            var2[var3] = 2;
         } catch (NoSuchFieldError var18) {
            ;
         }

         $SwitchMap$com$facebook$katana$model$FacebookChatMessage$Type = new int[FacebookChatMessage.Type.values().length];

         try {
            int[] var4 = $SwitchMap$com$facebook$katana$model$FacebookChatMessage$Type;
            int var5 = FacebookChatMessage.Type.NORMAL.ordinal();
            var4[var5] = 1;
         } catch (NoSuchFieldError var17) {
            ;
         }

         try {
            int[] var6 = $SwitchMap$com$facebook$katana$model$FacebookChatMessage$Type;
            int var7 = FacebookChatMessage.Type.ACTIVE.ordinal();
            var6[var7] = 2;
         } catch (NoSuchFieldError var16) {
            ;
         }

         try {
            int[] var8 = $SwitchMap$com$facebook$katana$model$FacebookChatMessage$Type;
            int var9 = FacebookChatMessage.Type.COMPOSING.ordinal();
            var8[var9] = 3;
         } catch (NoSuchFieldError var15) {
            ;
         }
      }
   }
}
