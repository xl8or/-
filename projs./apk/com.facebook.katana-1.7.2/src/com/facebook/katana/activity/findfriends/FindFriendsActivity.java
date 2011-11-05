package com.facebook.katana.activity.findfriends;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.activity.findfriends.FriendsAdapter;
import com.facebook.katana.activity.findfriends.InvitesAdapter;
import com.facebook.katana.activity.profilelist.ProfileListActivity;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.ProfileImagesCache;
import com.facebook.katana.model.FacebookPhonebookContact;
import com.facebook.katana.model.FacebookPhonebookContactUser;
import com.facebook.katana.model.FacebookUser;
import com.facebook.katana.service.method.FqlUsersGetBriefInfo;
import com.facebook.katana.service.method.FriendsAddFriend;
import com.facebook.katana.service.method.PhonebookLookup;
import com.facebook.katana.service.method.UsersInvite;
import com.facebook.katana.ui.SectionedListView;
import com.facebook.katana.util.PhonebookUtils;
import com.facebook.katana.util.StringUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class FindFriendsActivity extends ProfileListActivity {

   private static final int LEGAL_DIALOG = 2;
   private static final int SUMMARY_DIALOG = 1;
   private static final int VIEW_ADD_FRIENDS = 1;
   private static final int VIEW_INVITE_FRIENDS = 2;
   private FriendsAdapter mAddFriendsAdapter;
   private boolean mAllFriendsSelected = 0;
   private boolean mAllInvitesSelected = 0;
   private AppSession mAppSession;
   private int mCurrentView;
   private List<FacebookPhonebookContactUser> mFriendCandidateUsers;
   private Map<Long, FacebookPhonebookContact> mFriendCandidates;
   private String mGetInfoReqId;
   private InvitesAdapter mInviteFriendsAdapter;
   private Map<Long, FacebookPhonebookContact> mInvitesCandidates;
   private boolean mInvitesSent = 0;
   private String mLookupReqId;
   private Map<Long, FacebookPhonebookContact> mPhonebookContactsMap;
   private boolean mRequestsSent = 0;
   private ProfileImagesCache mUserImagesCache;


   public FindFriendsActivity() {}

   private Spanned getAllSelectedSubtext() {
      String var1 = this.getString(2131362302);
      SpannableString var2 = new SpannableString(var1);
      FindFriendsActivity.3 var3 = new FindFriendsActivity.3();
      int var4 = var2.length();
      var2.setSpan(var3, 0, var4, 33);
      Spanned var10;
      if(this.mCurrentView == 1) {
         StringBuilder var5 = new StringBuilder();
         String var6 = this.getString(2131362450);
         String var7 = var5.append(var6).append(" ").toString();
         SpannableString var8 = new SpannableString(var7);
         CharSequence[] var9 = new CharSequence[]{var8, var2};
         var10 = (Spanned)TextUtils.concat(var9);
      } else {
         StringBuilder var11 = new StringBuilder();
         String var12 = this.getString(2131362451);
         String var13 = var11.append(var12).append(" ").toString();
         SpannableString var14 = new SpannableString(var13);
         CharSequence[] var15 = new CharSequence[]{var14, var2};
         var10 = (Spanned)TextUtils.concat(var15);
      }

      return var10;
   }

   private Spanned getContactsFoundSubtext() {
      Spanned var2;
      if(this.mCurrentView == 1) {
         int var1 = this.mFriendCandidateUsers.size();
         var2 = this.getContactsFoundText(var1);
      } else {
         int var3 = this.mInvitesCandidates.size();
         var2 = this.getContactsFoundText(var3);
      }

      return var2;
   }

   private Spanned getContactsFoundText(int var1) {
      SpannableString var3;
      if(var1 == 1) {
         String var2 = this.getString(2131362446);
         var3 = new SpannableString(var2);
      } else {
         Object[] var4 = new Object[1];
         Integer var5 = Integer.valueOf(var1);
         var4[0] = var5;
         String var6 = this.getString(2131362447, var4);
         var3 = new SpannableString(var6);
      }

      return var3;
   }

   private Spanned getInterstitialLegalDisclaimer() {
      String var1 = this.getString(2131362453);
      return new SpannableString(var1);
   }

   public void addFriendSelectedUsers(List<Long> var1) {
      if(!var1.isEmpty()) {
         FriendsAddFriend.friendsAddFriends(this.mAppSession, this, var1, (String)null);
         this.mRequestsSent = (boolean)1;
      }

      this.handleSkip();
   }

   protected void contactImport() {
      if(this.mPhonebookContactsMap == null) {
         FindFriendsActivity.GetPhoneBookTask var1 = new FindFriendsActivity.GetPhoneBookTask((FindFriendsActivity.1)null);
         Void[] var2 = new Void[1];
         Void var3 = (Void)false;
         var2[0] = var3;
         var1.execute(var2);
      } else {
         this.contactImportApiCalls();
      }
   }

   protected void contactImportApiCalls() {
      if(this.mFriendCandidates == null) {
         AppSession var1 = this.mAppSession;
         Collection var2 = this.mPhonebookContactsMap.values();
         ArrayList var3 = new ArrayList(var2);
         String var4 = Locale.getDefault().getCountry();
         String var5 = PhonebookLookup.lookup(var1, this, var3, (boolean)1, var4);
         this.mLookupReqId = var5;
      } else if(this.mFriendCandidateUsers == null) {
         this.fetchFriendCandidateInformation();
      }
   }

   protected void fetchFriendCandidateInformation() {
      if(this.mFriendCandidates.size() > 0) {
         Set var1 = this.mFriendCandidates.keySet();
         Long[] var2 = new Long[this.mFriendCandidates.size()];
         Long[] var3 = (Long[])var1.toArray(var2);
         String var4 = FqlUsersGetBriefInfo.getUsersBriefInfo(this.mAppSession, this, var3);
         this.mGetInfoReqId = var4;
      } else {
         ArrayList var5 = new ArrayList();
         this.mFriendCandidateUsers = var5;
      }
   }

   protected void handlePhonebookLookupComplete() {
      if(this.mFriendCandidates.size() > 0) {
         this.fetchFriendCandidateInformation();
      } else {
         this.setupInviteFriendsView();
      }
   }

   public void handleSkip() {
      if(this.mCurrentView == 1 && !this.mInviteFriendsAdapter.isEmpty()) {
         this.setupInviteFriendsView();
      } else if(this.mRequestsSent != 1 && this.mInvitesSent != 1) {
         this.finish();
      } else {
         this.showDialog(1);
      }
   }

   public void inviteSelectedContacts(List<Long> var1) {
      if(!var1.isEmpty()) {
         ArrayList var2 = new ArrayList();
         Iterator var3 = var1.iterator();

         while(var3.hasNext()) {
            Long var4 = (Long)var3.next();
            String var5 = ((FacebookPhonebookContact)this.mInvitesCandidates.get(var4)).getContactAddress();
            var2.add(var5);
         }

         AppSession var7 = this.mAppSession;
         String var8 = Locale.getDefault().getCountry();
         UsersInvite.invite(var7, this, var2, (String)null, var8);
         this.mInvitesSent = (boolean)1;
      }

      this.handleSkip();
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.setContentView(2130903079);
      AppSession var2 = AppSession.getActiveSession(this, (boolean)1);
      this.mAppSession = var2;
      if(this.mAppSession == null) {
         LoginActivity.toLogin(this);
      } else {
         ProfileImagesCache var3 = this.mAppSession.getUserImagesCache();
         this.mUserImagesCache = var3;
         this.hideSearchButton();
         this.setListLoadingText(2131362427);
         this.setListEmptyText(2131362431);
         ArrayList var4 = new ArrayList();
         ProfileImagesCache var5 = this.mUserImagesCache;
         FriendsAdapter var6 = new FriendsAdapter(this, var4, var5);
         this.mAddFriendsAdapter = var6;
         ArrayList var7 = new ArrayList();
         Spanned var8 = this.getInterstitialLegalDisclaimer();
         InvitesAdapter var9 = new InvitesAdapter(this, var7, var8);
         this.mInviteFriendsAdapter = var9;
         FriendsAdapter var10 = this.mAddFriendsAdapter;
         this.mAdapter = var10;
         FindFriendsActivity.FindFriendsSelectSessionListener var11 = new FindFriendsActivity.FindFriendsSelectSessionListener((FindFriendsActivity.1)null);
         this.mAppSessionListener = var11;
         SectionedListView var12 = (SectionedListView)this.getListView();
         FriendsAdapter var13 = this.mAddFriendsAdapter;
         var12.setSectionedListAdapter(var13);
         var12.setItemsCanFocus((boolean)1);
      }
   }

   protected Dialog onCreateDialog(int var1) {
      AlertDialog var2;
      switch(var1) {
      case 1:
         View var3 = LayoutInflater.from(this).inflate(2130903080, (ViewGroup)null);
         TextView var4 = (TextView)var3.findViewById(2131624069);
         if(this.mRequestsSent == 1 && this.mInvitesSent == 1) {
            var4.setText(2131362434);
         } else if(this.mRequestsSent == 1) {
            var4.setText(2131362433);
         } else if(this.mInvitesSent == 1) {
            var4.setText(2131362432);
         }

         FindFriendsActivity.4 var5 = new FindFriendsActivity.4();
         ((Button)var3.findViewById(2131624070)).setOnClickListener(var5);
         var2 = (new Builder(this)).setCancelable((boolean)0).setView(var3).create();
         break;
      case 2:
         Builder var6 = (new Builder(this)).setCancelable((boolean)1).setMessage(2131362457);
         FindFriendsActivity.5 var7 = new FindFriendsActivity.5();
         var2 = var6.setPositiveButton(2131362013, var7).create();
         break;
      default:
         var2 = null;
      }

      return var2;
   }

   public void onResume() {
      super.onResume();
      this.contactImport();
   }

   public void setupAddFriendsView() {
      this.mCurrentView = 1;
      FriendsAdapter var1 = this.mAddFriendsAdapter;
      this.mAdapter = var1;
      SectionedListView var2 = (SectionedListView)this.getListView();
      FriendsAdapter var3 = this.mAddFriendsAdapter;
      var2.setSectionedListAdapter(var3);
      ((TextView)this.findViewById(2131624066)).setText(2131362430);
      TextView var4 = (TextView)this.findViewById(2131624067);
      Spanned var5 = this.getContactsFoundSubtext();
      var4.setText(var5);
      MovementMethod var6 = LinkMovementMethod.getInstance();
      var4.setMovementMethod(var6);
      FindFriendsActivity.1 var7 = new FindFriendsActivity.1();
      Button var8 = (Button)this.findViewById(2131624068);
      var8.setText(2131362444);
      var8.setOnClickListener(var7);
      if(this.mAllFriendsSelected) {
         var8.setVisibility(8);
      } else {
         var8.setVisibility(0);
      }

      if(this.mInviteFriendsAdapter.isEmpty()) {
         String var9 = this.getString(2131361867);
         this.setPrimaryActionFace(-1, var9);
      } else {
         String var10 = this.getString(2131362003);
         this.setPrimaryActionFace(-1, var10);
      }

      this.setupCommonViewChanges();
   }

   public void setupCommonViewChanges() {
      ((Button)this.findViewById(2131623997)).setVisibility(0);
      ((LinearLayout)this.findViewById(2131624065)).setVisibility(0);
   }

   public void setupInviteFriendsView() {
      this.mCurrentView = 2;
      this.setListLoading((boolean)0);
      InvitesAdapter var1 = this.mInviteFriendsAdapter;
      this.mAdapter = var1;
      SectionedListView var2 = (SectionedListView)this.getListView();
      InvitesAdapter var3 = this.mInviteFriendsAdapter;
      var2.setSectionedListAdapter(var3);
      String var4 = this.getString(2131361867);
      this.setPrimaryActionFace(-1, var4);
      if(this.mInvitesCandidates.size() != 0) {
         ((TextView)this.findViewById(2131624066)).setText(2131362429);
         TextView var5 = (TextView)this.findViewById(2131624067);
         Spanned var6 = this.getContactsFoundSubtext();
         var5.setText(var6);
         MovementMethod var7 = LinkMovementMethod.getInstance();
         var5.setMovementMethod(var7);
         FindFriendsActivity.2 var8 = new FindFriendsActivity.2();
         Button var9 = (Button)this.findViewById(2131624068);
         var9.setText(2131362445);
         var9.setOnClickListener(var8);
         if(this.mAllInvitesSelected) {
            var9.setVisibility(8);
         } else {
            var9.setVisibility(0);
         }

         this.setupCommonViewChanges();
      }
   }

   public void titleBarPrimaryActionClickHandler(View var1) {
      if(this.mCurrentView == 2) {
         if(this.mAllInvitesSelected) {
            ArrayList var2 = this.mInviteFriendsAdapter.getAllContactIds();
            this.inviteSelectedContacts(var2);
         } else {
            ArrayList var3 = this.mInviteFriendsAdapter.getSelectedContacts();
            this.inviteSelectedContacts(var3);
         }
      } else if(this.mAllFriendsSelected) {
         ArrayList var4 = this.mAddFriendsAdapter.getAllContactIds();
         this.addFriendSelectedUsers(var4);
      } else {
         ArrayList var5 = this.mAddFriendsAdapter.getSelectedContacts();
         this.addFriendSelectedUsers(var5);
      }
   }

   private class GetPhoneBookTask extends AsyncTask<Void, Void, List<FacebookPhonebookContact>> {

      private GetPhoneBookTask() {}

      // $FF: synthetic method
      GetPhoneBookTask(FindFriendsActivity.1 var2) {
         this();
      }

      protected List<FacebookPhonebookContact> doInBackground(Void ... var1) {
         return PhonebookUtils.extractAddressBook(FindFriendsActivity.this);
      }

      protected void onPostExecute(List<FacebookPhonebookContact> var1) {
         FindFriendsActivity var2 = FindFriendsActivity.this;
         HashMap var3 = new HashMap();
         var2.mPhonebookContactsMap = var3;
         Iterator var5 = var1.iterator();

         while(var5.hasNext()) {
            FacebookPhonebookContact var6 = (FacebookPhonebookContact)var5.next();
            Map var7 = FindFriendsActivity.this.mPhonebookContactsMap;
            Long var8 = Long.valueOf(var6.recordId);
            var7.put(var8, var6);
         }

         FindFriendsActivity.this.contactImportApiCalls();
      }
   }

   class 5 implements OnClickListener {

      5() {}

      public void onClick(DialogInterface var1, int var2) {
         var1.cancel();
      }
   }

   class 4 implements android.view.View.OnClickListener {

      4() {}

      public void onClick(View var1) {
         FindFriendsActivity.this.removeDialog(1);
         FindFriendsActivity.this.finish();
      }
   }

   class 3 extends ClickableSpan {

      3() {}

      public void onClick(View var1) {
         if(FindFriendsActivity.this.mCurrentView == 1) {
            boolean var2 = (boolean)(FindFriendsActivity.this.mAllFriendsSelected = (boolean)0);
         } else {
            boolean var5 = (boolean)(FindFriendsActivity.this.mAllInvitesSelected = (boolean)0);
         }

         TextView var3 = (TextView)FindFriendsActivity.this.findViewById(2131624067);
         Spanned var4 = FindFriendsActivity.this.getContactsFoundSubtext();
         var3.setText(var4);
         ((Button)FindFriendsActivity.this.findViewById(2131624068)).setVisibility(0);
      }

      public void updateDrawState(TextPaint var1) {
         var1.setUnderlineText((boolean)0);
         int var2 = FindFriendsActivity.this.getResources().getColor(2131165199);
         var1.setColor(var2);
      }
   }

   class 2 implements android.view.View.OnClickListener {

      2() {}

      public void onClick(View var1) {
         boolean var2 = (boolean)(FindFriendsActivity.this.mAllInvitesSelected = (boolean)1);
         TextView var3 = (TextView)FindFriendsActivity.this.findViewById(2131624067);
         Spanned var4 = FindFriendsActivity.this.getAllSelectedSubtext();
         var3.setText(var4);
         ((Button)FindFriendsActivity.this.findViewById(2131624068)).setVisibility(8);
      }
   }

   class 1 implements android.view.View.OnClickListener {

      1() {}

      public void onClick(View var1) {
         boolean var2 = (boolean)(FindFriendsActivity.this.mAllFriendsSelected = (boolean)1);
         TextView var3 = (TextView)FindFriendsActivity.this.findViewById(2131624067);
         Spanned var4 = FindFriendsActivity.this.getAllSelectedSubtext();
         var3.setText(var4);
         ((Button)FindFriendsActivity.this.findViewById(2131624068)).setVisibility(8);
      }
   }

   private class FindFriendsSelectSessionListener extends ProfileListActivity.ProfileListListener {

      private FindFriendsSelectSessionListener() {
         super();
      }

      // $FF: synthetic method
      FindFriendsSelectSessionListener(FindFriendsActivity.1 var2) {
         this();
      }

      public void onPhonebookLookupComplete(AppSession var1, String var2, int var3, String var4, Exception var5, List<FacebookPhonebookContact> var6) {
         String var7 = FindFriendsActivity.this.mLookupReqId;
         if(var2.equals(var7)) {
            if(FindFriendsActivity.this.mPhonebookContactsMap != null) {
               HashMap var8 = new HashMap();
               HashMap var9 = new HashMap();
               Iterator var10 = var6.iterator();

               while(var10.hasNext()) {
                  FacebookPhonebookContact var11 = (FacebookPhonebookContact)var10.next();
                  if(!var11.isFriend) {
                     if(var11.userId == 0L) {
                        Map var12 = FindFriendsActivity.this.mPhonebookContactsMap;
                        Long var13 = Long.valueOf(var11.recordId);
                        FacebookPhonebookContact var14 = (FacebookPhonebookContact)var12.get(var13);
                        if(var14 != null) {
                           String var15 = var14.name;
                           var11.name = var15;
                           if(var11.email != null || var11.phone != null) {
                              Long var16 = Long.valueOf(var11.recordId);
                              var9.put(var16, var11);
                           }
                        }
                     } else {
                        Long var18 = Long.valueOf(var11.userId);
                        var8.put(var18, var11);
                     }
                  }
               }

               InvitesAdapter var20 = FindFriendsActivity.this.mInviteFriendsAdapter;
               Collection var21 = var9.values();
               ArrayList var22 = new ArrayList(var21);
               var20.setAllContacts(var22);
               FindFriendsActivity.this.mInvitesCandidates = var9;
               FindFriendsActivity.this.mFriendCandidates = var8;
               FindFriendsActivity.this.handlePhonebookLookupComplete();
            }
         }
      }

      public void onUsersGetInfoComplete(AppSession var1, String var2, int var3, String var4, Exception var5, List<FacebookUser> var6) {
         String var7 = FindFriendsActivity.this.mGetInfoReqId;
         if(var2.equals(var7)) {
            if(FindFriendsActivity.this.mFriendCandidates != null) {
               int var8 = var6.size();
               ArrayList var9 = new ArrayList(var8);
               Iterator var10 = var6.iterator();

               while(var10.hasNext()) {
                  FacebookUser var11 = (FacebookUser)var10.next();
                  if(var11.mUserId > 0L && !StringUtils.isBlank(var11.mDisplayName)) {
                     Map var12 = FindFriendsActivity.this.mFriendCandidates;
                     Long var13 = Long.valueOf(var11.mUserId);
                     FacebookPhonebookContact var14 = (FacebookPhonebookContact)var12.get(var13);
                     if(var14 != null) {
                        String var15 = var11.mDisplayName;
                        String var16 = var14.email;
                        String var17 = var14.phone;
                        long var18 = var14.userId;
                        String var20 = var11.mImageUrl;
                        FacebookPhonebookContactUser var21 = new FacebookPhonebookContactUser(var15, var16, var17, var18, var20);
                        var9.add(var21);
                     }
                  }
               }

               FindFriendsActivity.this.mFriendCandidateUsers = var9;
               FindFriendsActivity.this.mAddFriendsAdapter.setAllContacts(var9);
               FindFriendsActivity.this.setupAddFriendsView();
               FindFriendsActivity.this.mAdapter.notifyDataSetChanged();
            }
         }
      }
   }
}
