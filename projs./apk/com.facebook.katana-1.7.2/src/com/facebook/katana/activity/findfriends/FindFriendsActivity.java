// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FindFriendsActivity.java

package com.facebook.katana.activity.findfriends;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.*;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.activity.profilelist.ProfileListActivity;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.ProfileImagesCache;
import com.facebook.katana.model.*;
import com.facebook.katana.service.method.*;
import com.facebook.katana.ui.SectionedListView;
import com.facebook.katana.util.PhonebookUtils;
import com.facebook.katana.util.StringUtils;
import java.util.*;

// Referenced classes of package com.facebook.katana.activity.findfriends:
//            InvitesAdapter, FriendsAdapter

public class FindFriendsActivity extends ProfileListActivity
{
    private class GetPhoneBookTask extends AsyncTask
    {

        protected volatile Object doInBackground(Object aobj[])
        {
            return doInBackground((Void[])aobj);
        }

        protected transient List doInBackground(Void avoid[])
        {
            return PhonebookUtils.extractAddressBook(FindFriendsActivity.this);
        }

        protected volatile void onPostExecute(Object obj)
        {
            onPostExecute((List)obj);
        }

        protected void onPostExecute(List list)
        {
            mPhonebookContactsMap = new HashMap();
            FacebookPhonebookContact facebookphonebookcontact;
            for(Iterator iterator = list.iterator(); iterator.hasNext(); mPhonebookContactsMap.put(Long.valueOf(facebookphonebookcontact.recordId), facebookphonebookcontact))
                facebookphonebookcontact = (FacebookPhonebookContact)iterator.next();

            contactImportApiCalls();
        }

        final FindFriendsActivity this$0;

        private GetPhoneBookTask()
        {
            this$0 = FindFriendsActivity.this;
            super();
        }

    }

    private class FindFriendsSelectSessionListener extends com.facebook.katana.activity.profilelist.ProfileListActivity.ProfileListListener
    {

        public void onPhonebookLookupComplete(AppSession appsession, String s, int i, String s1, Exception exception, List list)
        {
            if(s.equals(mLookupReqId) && mPhonebookContactsMap != null)
            {
                HashMap hashmap = new HashMap();
                HashMap hashmap1 = new HashMap();
                Iterator iterator = list.iterator();
                do
                {
                    if(!iterator.hasNext())
                        break;
                    FacebookPhonebookContact facebookphonebookcontact = (FacebookPhonebookContact)iterator.next();
                    if(!facebookphonebookcontact.isFriend)
                        if(facebookphonebookcontact.userId == 0L)
                        {
                            FacebookPhonebookContact facebookphonebookcontact1 = (FacebookPhonebookContact)mPhonebookContactsMap.get(Long.valueOf(facebookphonebookcontact.recordId));
                            if(facebookphonebookcontact1 != null)
                            {
                                facebookphonebookcontact.name = facebookphonebookcontact1.name;
                                if(facebookphonebookcontact.email != null || facebookphonebookcontact.phone != null)
                                    hashmap1.put(Long.valueOf(facebookphonebookcontact.recordId), facebookphonebookcontact);
                            }
                        } else
                        {
                            hashmap.put(Long.valueOf(facebookphonebookcontact.userId), facebookphonebookcontact);
                        }
                } while(true);
                mInviteFriendsAdapter.setAllContacts(new ArrayList(hashmap1.values()));
                mInvitesCandidates = hashmap1;
                mFriendCandidates = hashmap;
                handlePhonebookLookupComplete();
            }
        }

        public void onUsersGetInfoComplete(AppSession appsession, String s, int i, String s1, Exception exception, List list)
        {
            if(s.equals(mGetInfoReqId) && mFriendCandidates != null)
            {
                ArrayList arraylist = new ArrayList(list.size());
                Iterator iterator = list.iterator();
                do
                {
                    if(!iterator.hasNext())
                        break;
                    FacebookUser facebookuser = (FacebookUser)iterator.next();
                    if(facebookuser.mUserId > 0L && !StringUtils.isBlank(facebookuser.mDisplayName))
                    {
                        FacebookPhonebookContact facebookphonebookcontact = (FacebookPhonebookContact)mFriendCandidates.get(Long.valueOf(facebookuser.mUserId));
                        if(facebookphonebookcontact != null)
                            arraylist.add(new FacebookPhonebookContactUser(facebookuser.mDisplayName, facebookphonebookcontact.email, facebookphonebookcontact.phone, facebookphonebookcontact.userId, facebookuser.mImageUrl));
                    }
                } while(true);
                mFriendCandidateUsers = arraylist;
                mAddFriendsAdapter.setAllContacts(arraylist);
                setupAddFriendsView();
                
// JavaClassFileOutputException: get_constant: invalid tag

        final FindFriendsActivity this$0;

        private FindFriendsSelectSessionListener()
        {
            this$0 = FindFriendsActivity.this;
            super(FindFriendsActivity.this);
        }

    }


    public FindFriendsActivity()
    {
        mRequestsSent = false;
        mInvitesSent = false;
        mAllFriendsSelected = false;
        mAllInvitesSelected = false;
    }

    private Spanned getAllSelectedSubtext()
    {
        SpannableString spannablestring = new SpannableString(getString(0x7f0a01fe));
        spannablestring.setSpan(new ClickableSpan() {

            public void onClick(View view)
            {
                if(mCurrentView == 1)
                    mAllFriendsSelected = false;
                else
                    mAllInvitesSelected = false;
                ((TextView)findViewById(0x7f0e0083)).setText(getContactsFoundSubtext());
                ((Button)findViewById(0x7f0e0084)).setVisibility(0);
            }

            public void updateDrawState(TextPaint textpaint)
            {
                textpaint.setUnderlineText(false);
                textpaint.setColor(getResources().getColor(0x7f07000f));
            }

            final FindFriendsActivity this$0;

            
            {
                this$0 = FindFriendsActivity.this;
                super();
            }
        }
, 0, spannablestring.length(), 33);
        Spanned spanned;
        if(mCurrentView == 1)
        {
            SpannableString spannablestring1 = new SpannableString((new StringBuilder()).append(getString(0x7f0a0292)).append(" ").toString());
            CharSequence acharsequence[] = new CharSequence[2];
            acharsequence[0] = spannablestring1;
            acharsequence[1] = spannablestring;
            spanned = (Spanned)TextUtils.concat(acharsequence);
        } else
        {
            SpannableString spannablestring2 = new SpannableString((new StringBuilder()).append(getString(0x7f0a0293)).append(" ").toString());
            CharSequence acharsequence1[] = new CharSequence[2];
            acharsequence1[0] = spannablestring2;
            acharsequence1[1] = spannablestring;
            spanned = (Spanned)TextUtils.concat(acharsequence1);
        }
        return spanned;
    }

    private Spanned getContactsFoundSubtext()
    {
        Spanned spanned;
        if(mCurrentView == 1)
            spanned = getContactsFoundText(mFriendCandidateUsers.size());
        else
            spanned = getContactsFoundText(mInvitesCandidates.size());
        return spanned;
    }

    private Spanned getContactsFoundText(int i)
    {
        SpannableString spannablestring;
        if(i == 1)
        {
            spannablestring = new SpannableString(getString(0x7f0a028e));
        } else
        {
            Object aobj[] = new Object[1];
            aobj[0] = Integer.valueOf(i);
            spannablestring = new SpannableString(getString(0x7f0a028f, aobj));
        }
        return spannablestring;
    }

    private Spanned getInterstitialLegalDisclaimer()
    {
        return new SpannableString(getString(0x7f0a0295));
    }

    public void addFriendSelectedUsers(List list)
    {
        if(!list.isEmpty())
        {
            FriendsAddFriend.friendsAddFriends(mAppSession, this, list, null);
            mRequestsSent = true;
        }
        handleSkip();
    }

    protected void contactImport()
    {
        if(mPhonebookContactsMap == null)
        {
            GetPhoneBookTask getphonebooktask = new GetPhoneBookTask();
            Void avoid[] = new Void[1];
            avoid[0] = (Void)null;
            getphonebooktask.execute(avoid);
        } else
        {
            contactImportApiCalls();
        }
    }

    protected void contactImportApiCalls()
    {
        if(mFriendCandidates != null) goto _L2; else goto _L1
_L1:
        mLookupReqId = PhonebookLookup.lookup(mAppSession, this, new ArrayList(mPhonebookContactsMap.values()), true, Locale.getDefault().getCountry());
_L4:
        return;
_L2:
        if(mFriendCandidateUsers == null)
            fetchFriendCandidateInformation();
        if(true) goto _L4; else goto _L3
_L3:
    }

    protected void fetchFriendCandidateInformation()
    {
        if(mFriendCandidates.size() > 0)
        {
            Long along[] = (Long[])mFriendCandidates.keySet().toArray(new Long[mFriendCandidates.size()]);
            mGetInfoReqId = FqlUsersGetBriefInfo.getUsersBriefInfo(mAppSession, this, along);
        } else
        {
            mFriendCandidateUsers = new ArrayList();
        }
    }

    protected void handlePhonebookLookupComplete()
    {
        if(mFriendCandidates.size() > 0)
            fetchFriendCandidateInformation();
        else
            setupInviteFriendsView();
    }

    public void handleSkip()
    {
        if(mCurrentView == 1 && !mInviteFriendsAdapter.isEmpty())
            setupInviteFriendsView();
        else
        if(mRequestsSent || mInvitesSent)
            showDialog(1);
        else
            finish();
    }

    public void inviteSelectedContacts(List list)
    {
        if(!list.isEmpty())
        {
            ArrayList arraylist = new ArrayList();
            Long long1;
            for(Iterator iterator = list.iterator(); iterator.hasNext(); arraylist.add(((FacebookPhonebookContact)mInvitesCandidates.get(long1)).getContactAddress()))
                long1 = (Long)iterator.next();

            UsersInvite.invite(mAppSession, this, arraylist, null, Locale.getDefault().getCountry());
            mInvitesSent = true;
        }
        handleSkip();
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f030027);
        mAppSession = AppSession.getActiveSession(this, true);
        if(mAppSession == null)
        {
            LoginActivity.toLogin(this);
        } else
        {
            mUserImagesCache = mAppSession.getUserImagesCache();
            hideSearchButton();
            setListLoadingText(0x7f0a027b);
            setListEmptyText(0x7f0a027f);
            mAddFriendsAdapter = new FriendsAdapter(this, new ArrayList(), mUserImagesCache);
            mInviteFriendsAdapter = new InvitesAdapter(this, new ArrayList(), getInterstitialLegalDisclaimer());
            mAdapter = mAddFriendsAdapter;
            mAppSessionListener = new FindFriendsSelectSessionListener();
            SectionedListView sectionedlistview = (SectionedListView)getListView();
            sectionedlistview.setSectionedListAdapter(mAddFriendsAdapter);
            sectionedlistview.setItemsCanFocus(true);
        }
    }

    protected Dialog onCreateDialog(int i)
    {
        i;
        JVM INSTR tableswitch 1 2: default 24
    //                   1 28
    //                   2 162;
           goto _L1 _L2 _L3
_L1:
        Object obj = null;
_L8:
        return ((Dialog) (obj));
_L2:
        View view;
        TextView textview;
        view = LayoutInflater.from(this).inflate(0x7f030028, null);
        textview = (TextView)view.findViewById(0x7f0e0085);
        if(!mRequestsSent || !mInvitesSent) goto _L5; else goto _L4
_L4:
        textview.setText(0x7f0a0282);
_L6:
        android.view.View.OnClickListener onclicklistener = new android.view.View.OnClickListener() {

            public void onClick(View view1)
            {
                removeDialog(1);
                finish();
            }

            final FindFriendsActivity this$0;

            
            {
                this$0 = FindFriendsActivity.this;
                super();
            }
        }
;
        ((Button)view.findViewById(0x7f0e0086)).setOnClickListener(onclicklistener);
        obj = (new android.app.AlertDialog.Builder(this)).setCancelable(false).setView(view).create();
        continue; /* Loop/switch isn't completed */
_L5:
        if(mRequestsSent)
            textview.setText(0x7f0a0281);
        else
        if(mInvitesSent)
            textview.setText(0x7f0a0280);
        if(true) goto _L6; else goto _L3
_L3:
        obj = (new android.app.AlertDialog.Builder(this)).setCancelable(true).setMessage(0x7f0a0299).setPositiveButton(0x7f0a00dd, new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int j)
            {
                dialoginterface.cancel();
            }

            final FindFriendsActivity this$0;

            
            {
                this$0 = FindFriendsActivity.this;
                super();
            }
        }
).create();
        if(true) goto _L8; else goto _L7
_L7:
    }

    public void onResume()
    {
        super.onResume();
        contactImport();
    }

    public void setupAddFriendsView()
    {
        mCurrentView = 1;
        mAdapter = mAddFriendsAdapter;
        ((SectionedListView)getListView()).setSectionedListAdapter(mAddFriendsAdapter);
        ((TextView)findViewById(0x7f0e0082)).setText(0x7f0a027e);
        TextView textview = (TextView)findViewById(0x7f0e0083);
        textview.setText(getContactsFoundSubtext());
        textview.setMovementMethod(LinkMovementMethod.getInstance());
        android.view.View.OnClickListener onclicklistener = new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                mAllFriendsSelected = true;
                ((TextView)findViewById(0x7f0e0083)).setText(getAllSelectedSubtext());
                ((Button)findViewById(0x7f0e0084)).setVisibility(8);
            }

            final FindFriendsActivity this$0;

            
            {
                this$0 = FindFriendsActivity.this;
                super();
            }
        }
;
        Button button = (Button)findViewById(0x7f0e0084);
        button.setText(0x7f0a028c);
        button.setOnClickListener(onclicklistener);
        if(mAllFriendsSelected)
            button.setVisibility(8);
        else
            button.setVisibility(0);
        if(mInviteFriendsAdapter.isEmpty())
            setPrimaryActionFace(-1, getString(0x7f0a004b));
        else
            setPrimaryActionFace(-1, getString(0x7f0a00d3));
        setupCommonViewChanges();
    }

    public void setupCommonViewChanges()
    {
        ((Button)findViewById(0x7f0e003d)).setVisibility(0);
        ((LinearLayout)findViewById(0x7f0e0081)).setVisibility(0);
    }

    public void setupInviteFriendsView()
    {
        mCurrentView = 2;
        setListLoading(false);
        mAdapter = mInviteFriendsAdapter;
        ((SectionedListView)getListView()).setSectionedListAdapter(mInviteFriendsAdapter);
        setPrimaryActionFace(-1, getString(0x7f0a004b));
        if(mInvitesCandidates.size() != 0)
        {
            ((TextView)findViewById(0x7f0e0082)).setText(0x7f0a027d);
            TextView textview = (TextView)findViewById(0x7f0e0083);
            textview.setText(getContactsFoundSubtext());
            textview.setMovementMethod(LinkMovementMethod.getInstance());
            android.view.View.OnClickListener onclicklistener = new android.view.View.OnClickListener() {

                public void onClick(View view)
                {
                    mAllInvitesSelected = true;
                    ((TextView)findViewById(0x7f0e0083)).setText(getAllSelectedSubtext());
                    ((Button)findViewById(0x7f0e0084)).setVisibility(8);
                }

                final FindFriendsActivity this$0;

            
            {
                this$0 = FindFriendsActivity.this;
                super();
            }
            }
;
            Button button = (Button)findViewById(0x7f0e0084);
            button.setText(0x7f0a028d);
            button.setOnClickListener(onclicklistener);
            if(mAllInvitesSelected)
                button.setVisibility(8);
            else
                button.setVisibility(0);
            setupCommonViewChanges();
        }
    }

    public void titleBarPrimaryActionClickHandler(View view)
    {
        if(mCurrentView == 2)
        {
            if(mAllInvitesSelected)
                inviteSelectedContacts(mInviteFriendsAdapter.getAllContactIds());
            else
                inviteSelectedContacts(mInviteFriendsAdapter.getSelectedContacts());
        } else
        if(mAllFriendsSelected)
            addFriendSelectedUsers(mAddFriendsAdapter.getAllContactIds());
        else
            addFriendSelectedUsers(mAddFriendsAdapter.getSelectedContacts());
    }

    private static final int LEGAL_DIALOG = 2;
    private static final int SUMMARY_DIALOG = 1;
    private static final int VIEW_ADD_FRIENDS = 1;
    private static final int VIEW_INVITE_FRIENDS = 2;
    private FriendsAdapter mAddFriendsAdapter;
    private boolean mAllFriendsSelected;
    private boolean mAllInvitesSelected;
    private AppSession mAppSession;
    private int mCurrentView;
    private List mFriendCandidateUsers;
    private Map mFriendCandidates;
    private String mGetInfoReqId;
    private InvitesAdapter mInviteFriendsAdapter;
    private Map mInvitesCandidates;
    private boolean mInvitesSent;
    private String mLookupReqId;
    private Map mPhonebookContactsMap;
    private boolean mRequestsSent;
    private ProfileImagesCache mUserImagesCache;





/*
    static Map access$102(FindFriendsActivity findfriendsactivity, Map map)
    {
        findfriendsactivity.mPhonebookContactsMap = map;
        return map;
    }

*/


/*
    static boolean access$1102(FindFriendsActivity findfriendsactivity, boolean flag)
    {
        findfriendsactivity.mAllInvitesSelected = flag;
        return flag;
    }

*/





/*
    static Map access$302(FindFriendsActivity findfriendsactivity, Map map)
    {
        findfriendsactivity.mInvitesCandidates = map;
        return map;
    }

*/



/*
    static Map access$402(FindFriendsActivity findfriendsactivity, Map map)
    {
        findfriendsactivity.mFriendCandidates = map;
        return map;
    }

*/



/*
    static List access$602(FindFriendsActivity findfriendsactivity, List list)
    {
        findfriendsactivity.mFriendCandidateUsers = list;
        return list;
    }

*/




/*
    static boolean access$902(FindFriendsActivity findfriendsactivity, boolean flag)
    {
        findfriendsactivity.mAllFriendsSelected = flag;
        return flag;
    }

*/
}
