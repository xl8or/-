package com.facebook.katana.activity.messages;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import com.facebook.katana.AlertDialogs;
import com.facebook.katana.DropdownFriendsAdapter;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.PickFriendsActivity;
import com.facebook.katana.ViewHolder;
import com.facebook.katana.activity.BaseFacebookActivity;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.binding.ProfileImage;
import com.facebook.katana.binding.ProfileImagesCache;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.provider.ConnectionsProvider;
import com.facebook.katana.service.method.FqlGetProfile;
import com.facebook.katana.ui.MailAutoCompleteTextView;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.Toaster;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MessageComposeActivity extends BaseFacebookActivity {

   public static final String EXTRA_SUBJECT = "extra_subject";
   private static final int PROGRESS_SEND_DIALOG = 1;
   private static final int QUIT_DIALOG = 2;
   private static final int REQUEST_CODE_PICK_FRIEND = 1;
   private AppSession mAppSession;
   private List<AppSessionListener> mAppSessionListeners;
   private DropdownFriendsAdapter mDropdownAdapter;
   private final ArrayList<FacebookProfile> mRecipients;
   private ViewGroup mRecipientsContainer;
   private OnClickListener mRemoveButtonListener;
   private String mSendReqId;
   private final List<ViewHolder<Long>> mViewHolders;


   public MessageComposeActivity() {
      ArrayList var1 = new ArrayList();
      this.mRecipients = var1;
      ArrayList var2 = new ArrayList();
      this.mViewHolders = var2;
   }

   private void addRecipient(FacebookProfile var1) {
      long var2 = var1.mId;
      Iterator var4 = this.mRecipients.iterator();

      do {
         if(!var4.hasNext()) {
            this.mRecipients.add(var1);
            View var6 = ((LayoutInflater)this.getSystemService("layout_inflater")).inflate(2130903176, (ViewGroup)null);
            var6.setTag(var1);
            ViewHolder var7 = new ViewHolder(var6, 2131623962);
            Long var8 = Long.valueOf(var2);
            var7.setItemId(var8);
            this.mViewHolders.add(var7);
            String var10 = var1.mImageUrl;
            if(var10 != null) {
               Bitmap var11 = this.mAppSession.getUserImagesCache().get(this, var2, var10);
               if(var11 != null) {
                  var7.mImageView.setImageBitmap(var11);
               } else {
                  var7.mImageView.setImageResource(2130837747);
               }
            } else {
               var7.mImageView.setImageResource(2130837747);
            }

            String var12 = var1.mDisplayName;
            if(var12 == null) {
               var12 = this.getString(2131361895);
            }

            ((TextView)var6.findViewById(2131623964)).setText(var12);
            View var13 = var6.findViewById(2131624264);
            OnClickListener var14 = this.mRemoveButtonListener;
            var13.setOnClickListener(var14);
            var13.setTag(var1);
            this.mRecipientsContainer.addView(var6, 0);
            return;
         }
      } while(((FacebookProfile)var4.next()).mId != var2);

   }

   private void removeRecipient(FacebookProfile var1) {
      View var2 = this.mRecipientsContainer.findViewWithTag(var1);
      this.mRecipientsContainer.removeView(var2);
      this.mRecipients.remove(var1);
      Iterator var4 = this.mViewHolders.iterator();

      while(var4.hasNext()) {
         ViewHolder var5 = (ViewHolder)var4.next();
         Long var6 = (Long)var5.getItemId();
         if(var6 != null) {
            Long var7 = Long.valueOf(var1.mId);
            if(var6.equals(var7)) {
               this.mViewHolders.remove(var5);
               break;
            }
         }
      }

      this.mRecipientsContainer.requestLayout();
   }

   private void send() {
      if(this.mRecipients.size() == 0) {
         Toaster.toast(this, 2131361987);
      } else {
         CharSequence var1 = ((TextView)this.findViewById(2131624141)).getText();
         CharSequence var2 = ((TextView)this.findViewById(2131624142)).getText();
         if(var2.length() > 0) {
            AppSession var3 = this.mAppSession;
            ArrayList var4 = this.mRecipients;
            String var5 = var1.toString();
            String var6 = var2.toString();
            String var7 = var3.mailboxSend(this, var4, var5, var6);
            this.mSendReqId = var7;
            this.showDialog(1);
         }
      }
   }

   private void updateUserViewImage(ProfileImage var1) {
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
               return;
            }
         }
      }

   }

   public boolean facebookOnBackPressed() {
      if(((TextView)this.findViewById(2131624139)).getText().length() > 0) {
         this.showDialog(2);
      } else if(((TextView)this.findViewById(2131624141)).getText().length() > 0) {
         this.showDialog(2);
      } else if(((TextView)this.findViewById(2131624142)).getText().length() > 0) {
         this.showDialog(2);
      } else {
         this.setResult(0);
         this.finish();
      }

      return true;
   }

   protected void onActivityResult(int var1, int var2, Intent var3) {
      super.onActivityResult(var1, var2, var3);
      if(var2 != 0) {
         switch(var1) {
         case 1:
            ArrayList var4 = this.mRecipients;
            Iterator var5 = (new ArrayList(var4)).iterator();

            while(var5.hasNext()) {
               FacebookProfile var6 = (FacebookProfile)var5.next();
               this.removeRecipient(var6);
            }

            this.mRecipients.clear();
            Iterator var7 = var3.getParcelableArrayListExtra("com.facebook.katana.PickFriendsActivity.result_friends").iterator();

            while(var7.hasNext()) {
               FacebookProfile var8 = (FacebookProfile)var7.next();
               this.addRecipient(var8);
            }

            return;
         default:
         }
      }
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.setContentView(2130903109);
      AppSession var2 = AppSession.getActiveSession(this, (boolean)1);
      this.mAppSession = var2;
      if(this.mAppSession == null) {
         LoginActivity.toLogin(this);
      } else {
         MailAutoCompleteTextView var3 = (MailAutoCompleteTextView)this.findViewById(2131624139);
         ProfileImagesCache var4 = this.mAppSession.getUserImagesCache();
         DropdownFriendsAdapter var5 = new DropdownFriendsAdapter(this, (Cursor)null, var4);
         this.mDropdownAdapter = var5;
         DropdownFriendsAdapter var6 = this.mDropdownAdapter;
         var3.setAdapter(var6);
         MessageComposeActivity.1 var7 = new MessageComposeActivity.1(var3);
         var3.setOnItemClickListener(var7);
         MessageComposeActivity.2 var8 = new MessageComposeActivity.2();
         var3.addTextChangedListener(var8);
         View var9 = this.findViewById(2131624143);
         MessageComposeActivity.3 var10 = new MessageComposeActivity.3();
         var9.setOnClickListener(var10);
         View var11 = this.findViewById(2131624144);
         MessageComposeActivity.4 var12 = new MessageComposeActivity.4();
         var11.setOnClickListener(var12);
         MessageComposeActivity.5 var13 = new MessageComposeActivity.5();
         this.findViewById(2131624138).setOnClickListener(var13);
         MessageComposeActivity.6 var14 = new MessageComposeActivity.6();
         this.mRemoveButtonListener = var14;
         ViewGroup var15 = (ViewGroup)this.findViewById(2131624140);
         this.mRecipientsContainer = var15;
         List var16 = (List)this.getLastNonConfigurationInstance();
         if(var16 != null) {
            Iterator var17 = var16.iterator();

            while(var17.hasNext()) {
               FacebookProfile var18 = (FacebookProfile)var17.next();
               this.addRecipient(var18);
            }
         }

         ArrayList var19 = new ArrayList();
         this.mAppSessionListeners = var19;
         List var20 = this.mAppSessionListeners;
         AppSessionListener var21 = this.mDropdownAdapter.appSessionListener;
         var20.add(var21);
         List var23 = this.mAppSessionListeners;
         MessageComposeActivity.ComposeAppSessionListener var24 = new MessageComposeActivity.ComposeAppSessionListener((MessageComposeActivity.1)null);
         var23.add(var24);
         Long var26 = Long.valueOf(this.getIntent().getLongExtra("extra_user_id", 65535L));
         if(var26.longValue() != 65535L) {
            long var27 = var26.longValue();
            FacebookProfile var29 = ConnectionsProvider.getFriendProfileFromId(this, var27);
            if(var29 != null) {
               this.addRecipient(var29);
            } else {
               long var31 = var26.longValue();
               FqlGetProfile.RequestSingleProfile(this, var31);
            }

            boolean var30 = this.findViewById(2131624141).requestFocus();
         }
      }
   }

   protected Dialog onCreateDialog(int var1) {
      Object var2;
      switch(var1) {
      case 1:
         ProgressDialog var3 = new ProgressDialog(this);
         var3.setProgressStyle(0);
         CharSequence var4 = this.getText(2131361981);
         var3.setMessage(var4);
         var3.setIndeterminate((boolean)1);
         var3.setCancelable((boolean)0);
         var2 = var3;
         break;
      case 2:
         String var5 = this.getString(2131361989);
         String var6 = this.getString(2131361988);
         String var7 = this.getString(2131362355);
         MessageComposeActivity.7 var8 = new MessageComposeActivity.7();
         String var9 = this.getString(2131362004);
         Object var11 = null;
         var2 = AlertDialogs.createAlert(this, var5, 17301543, var6, var7, var8, var9, (android.content.DialogInterface.OnClickListener)null, (OnCancelListener)var11, (boolean)1);
         break;
      default:
         var2 = null;
      }

      return (Dialog)var2;
   }

   protected void onPause() {
      super.onPause();
      if(this.mAppSession != null) {
         Iterator var1 = this.mAppSessionListeners.iterator();

         while(var1.hasNext()) {
            AppSessionListener var2 = (AppSessionListener)var1.next();
            this.mAppSession.removeListener(var2);
         }

      }
   }

   public void onResume() {
      super.onResume();
      AppSession var1 = AppSession.getActiveSession(this, (boolean)1);
      this.mAppSession = var1;
      if(this.mAppSession == null) {
         LoginActivity.toLogin(this);
      } else {
         if(this.mSendReqId != null) {
            AppSession var2 = this.mAppSession;
            String var3 = this.mSendReqId;
            if(!var2.isRequestPending(var3)) {
               this.removeDialog(1);
               this.mSendReqId = null;
            }
         }

         Iterator var4 = this.mAppSessionListeners.iterator();

         while(var4.hasNext()) {
            AppSessionListener var5 = (AppSessionListener)var4.next();
            this.mAppSession.addListener(var5);
         }

      }
   }

   public Object onRetainNonConfigurationInstance() {
      return this.mRecipients;
   }

   class 7 implements android.content.DialogInterface.OnClickListener {

      7() {}

      public void onClick(DialogInterface var1, int var2) {
         MessageComposeActivity.this.setResult(0);
         MessageComposeActivity.this.finish();
      }
   }

   class 6 implements OnClickListener {

      6() {}

      public void onClick(View var1) {
         MessageComposeActivity var2 = MessageComposeActivity.this;
         FacebookProfile var3 = (FacebookProfile)var1.getTag();
         var2.removeRecipient(var3);
      }
   }

   class 5 implements OnClickListener {

      5() {}

      public void onClick(View var1) {
         MessageComposeActivity var2 = MessageComposeActivity.this;
         Intent var3 = new Intent(var2, PickFriendsActivity.class);
         Intent var4 = var3.setAction("android.intent.action.PICK");
         ArrayList var5 = MessageComposeActivity.this.mRecipients;
         var3.putExtra("com.facebook.katana.PickFriendsActivity.initial_friends", var5);
         MessageComposeActivity.this.startActivityForResult(var3, 1);
      }
   }

   class 4 implements OnClickListener {

      4() {}

      public void onClick(View var1) {
         MessageComposeActivity.this.setResult(0);
         MessageComposeActivity.this.finish();
      }
   }

   class 3 implements OnClickListener {

      3() {}

      public void onClick(View var1) {
         MessageComposeActivity.this.send();
      }
   }

   class 2 implements TextWatcher {

      2() {}

      public void afterTextChanged(Editable var1) {}

      public void beforeTextChanged(CharSequence var1, int var2, int var3, int var4) {}

      public void onTextChanged(CharSequence var1, int var2, int var3, int var4) {
         if(var1.length() == 1) {
            Uri var5 = ConnectionsProvider.FRIENDS_SEARCH_CONTENT_URI;
            String var6 = Uri.encode(var1.toString());
            Uri var7 = Uri.withAppendedPath(var5, var6);
            MessageComposeActivity var8 = MessageComposeActivity.this;
            String[] var9 = DropdownFriendsAdapter.FriendsQuery.PROJECTION;
            Object var10 = null;
            Object var11 = null;
            Cursor var12 = var8.managedQuery(var7, var9, (String)null, (String[])var10, (String)var11);
            MessageComposeActivity.this.mDropdownAdapter.changeCursor(var12);
         }
      }
   }

   class 1 implements OnItemClickListener {

      // $FF: synthetic field
      final MailAutoCompleteTextView val$userName;


      1(MailAutoCompleteTextView var2) {
         this.val$userName = var2;
      }

      public void onItemClick(AdapterView<?> var1, View var2, int var3, long var4) {
         Cursor var6 = (Cursor)MessageComposeActivity.this.mDropdownAdapter.getItem(var3);
         long var7 = var6.getLong(1);
         String var9 = var6.getString(2);
         String var10 = var6.getString(3);
         MessageComposeActivity var11 = MessageComposeActivity.this;
         FacebookProfile var12 = new FacebookProfile(var7, var9, var10, 0);
         var11.addRecipient(var12);
         this.val$userName.setText((CharSequence)null);
      }
   }

   private class ComposeAppSessionListener extends AppSessionListener {

      private ComposeAppSessionListener() {}

      // $FF: synthetic method
      ComposeAppSessionListener(MessageComposeActivity.1 var2) {
         this();
      }

      public void onGetProfileComplete(AppSession var1, String var2, int var3, String var4, Exception var5, FacebookProfile var6) {
         if(var6 != null) {
            MessageComposeActivity.this.addRecipient(var6);
         }
      }

      public void onMailboxSendComplete(AppSession var1, String var2, int var3, String var4, Exception var5) {
         MessageComposeActivity.this.removeDialog(1);
         String var6 = MessageComposeActivity.this.mSendReqId = null;
         if(var3 == 200) {
            MessageComposeActivity.this.setResult(-1);
            MessageComposeActivity.this.finish();
         } else {
            MessageComposeActivity var7 = MessageComposeActivity.this;
            String var8 = MessageComposeActivity.this.getString(2131361991);
            String var9 = StringUtils.getErrorString(var7, var8, var3, var4, var5);
            Toaster.toast(MessageComposeActivity.this, var9);
         }
      }

      public void onProfileImageDownloaded(AppSession var1, String var2, int var3, String var4, Exception var5, ProfileImage var6, ProfileImagesCache var7) {
         if(var3 == 200) {
            MessageComposeActivity.this.updateUserViewImage(var6);
         }
      }

      public void onProfileImageLoaded(AppSession var1, ProfileImage var2) {
         MessageComposeActivity.this.updateUserViewImage(var2);
      }
   }
}
