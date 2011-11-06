// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BaseAdapter.java

package com.facebook.katana.activity.findfriends;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.text.*;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.*;
import android.widget.Button;
import android.widget.TextView;
import com.facebook.katana.model.FacebookPhonebookContact;
import java.util.*;

public abstract class BaseAdapter extends com.facebook.katana.activity.profilelist.ProfileListActivity.ProfileListAdapter
{
    private class SortAndRefreshTask extends AsyncTask
    {

        protected volatile Object doInBackground(Object aobj[])
        {
            return doInBackground((List[])aobj);
        }

        protected transient List doInBackground(List alist[])
        {
            Comparator comparator = new Comparator() {

                public int compare(FacebookPhonebookContact facebookphonebookcontact, FacebookPhonebookContact facebookphonebookcontact1)
                {
                    return facebookphonebookcontact.name.toLowerCase().compareTo(facebookphonebookcontact1.name.toLowerCase());
                }

                public volatile int compare(Object obj, Object obj1)
                {
                    return compare((FacebookPhonebookContact)obj, (FacebookPhonebookContact)obj1);
                }

                final SortAndRefreshTask this$1;

                
                {
                    this$1 = SortAndRefreshTask.this;
                    super();
                }
            }
;
            ArrayList arraylist = new ArrayList(alist[0]);
            Collections.sort(arraylist, comparator);
            return arraylist;
        }

        protected volatile void onPostExecute(Object obj)
        {
            onPostExecute((List)obj);
        }

        protected void onPostExecute(List list)
        {
            List list1 = refreshData(list);
            mAllContacts = new ArrayList(list);
            mSections = list1;
            mSelectedContacts.clear();
            notifyDataSetChanged();
        }

        protected List refreshData(List list)
        {
            ArrayList arraylist = new ArrayList();
            if(list.size() != 0)
            {
                String s = "";
                int i = -1;
                int j = 0;
                int k = -1;
                for(Iterator iterator = list.iterator(); iterator.hasNext();)
                {
                    FacebookPhonebookContact facebookphonebookcontact = (FacebookPhonebookContact)iterator.next();
                    k++;
                    String s1 = facebookphonebookcontact.name.substring(0, 1).toUpperCase();
                    if(s.equals(s1))
                    {
                        j++;
                    } else
                    {
                        if(i >= 0)
                            arraylist.add(new FirstLetterFriendSection(s, i, j));
                        s = s1;
                        i = k;
                        j = 1;
                    }
                }

                arraylist.add(new FirstLetterFriendSection(s, i, j));
            }
            return arraylist;
        }

        final BaseAdapter this$0;

        private SortAndRefreshTask()
        {
            this$0 = BaseAdapter.this;
            super();
        }

    }

    public static class FirstLetterFriendSection
    {

        public int getChildrenCount()
        {
            return mCount;
        }

        public int getStartPosition()
        {
            return mStartPosition;
        }

        public String toString()
        {
            return mSectionName;
        }

        protected final int mCount;
        protected final String mSectionName;
        protected final int mStartPosition;

        public FirstLetterFriendSection(String s, int i, int j)
        {
            mSectionName = s;
            mStartPosition = i;
            mCount = j;
        }
    }


    protected BaseAdapter(Context context)
    {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mSections = new ArrayList();
        mViewHolders = new ArrayList();
    }

    private String normalizeName(String s)
    {
        int i = s.indexOf('@');
        String s1;
        if(i > 0)
            s1 = s.substring(0, i);
        else
            s1 = s;
        return s1;
    }

    protected abstract String getActionTakenString();

    public ArrayList getAllContactIds()
    {
        ArrayList arraylist = new ArrayList();
        for(Iterator iterator = mAllContacts.iterator(); iterator.hasNext(); arraylist.add(new Long(getContactId((FacebookPhonebookContact)iterator.next()))));
        return arraylist;
    }

    public Object getChild(int i, int j)
    {
        return mAllContacts.get(getOverallPosition(i, j));
    }

    public View getChildView(int i, int j, boolean flag, View view, ViewGroup viewgroup)
    {
        FacebookPhonebookContact facebookphonebookcontact = (FacebookPhonebookContact)getChild(i, j);
        View view1 = view;
        boolean flag1 = false;
        if(view1 == null)
        {
            view1 = mInflater.inflate(0x7f030061, null);
            flag1 = true;
        }
        setupPic(view1, facebookphonebookcontact, flag1);
        int k = getOverallPosition(i, j);
        ((TextView)view1.findViewById(0x7f0e0100)).setText(normalizeName(facebookphonebookcontact.name));
        TextView textview = (TextView)view1.findViewById(0x7f0e0101);
        textview.setText(getContactDisplayText(facebookphonebookcontact, k, view1), android.widget.TextView.BufferType.SPANNABLE);
        textview.setMovementMethod(LinkMovementMethod.getInstance());
        Button button = (Button)view1.findViewById(0x7f0e0102);
        button.setText(getSelectButtonText());
        button.setOnClickListener(getToggleListener(k, view1));
        if(mSelectedContacts.contains(Long.valueOf(getContactId(facebookphonebookcontact))))
            button.setVisibility(8);
        else
            button.setVisibility(0);
        return view1;
    }

    public int getChildViewType(int i, int j)
    {
        return 1;
    }

    public int getChildrenCount(int i)
    {
        return ((FirstLetterFriendSection)mSections.get(i)).getChildrenCount();
    }

    protected String getContactAddress(FacebookPhonebookContact facebookphonebookcontact)
    {
        return facebookphonebookcontact.getContactAddress();
    }

    protected Spanned getContactDisplayText(FacebookPhonebookContact facebookphonebookcontact, int i, View view)
    {
        return getDisplayText(getContactAddress(facebookphonebookcontact), getActionTakenString(), mSelectedContacts.contains(Long.valueOf(getContactId(facebookphonebookcontact))), i, view);
    }

    protected abstract long getContactId(FacebookPhonebookContact facebookphonebookcontact);

    protected Spanned getDisplayText(String s, String s1, boolean flag, final int position, final View v)
    {
        Object obj;
        if(flag)
        {
            SpannableString spannablestring = new SpannableString((new StringBuilder()).append(s1).append(" ").toString());
            SpannableString spannablestring1 = new SpannableString(mContext.getString(0x7f0a01fe));
            spannablestring1.setSpan(new ClickableSpan() {

                public void onClick(View view)
                {
                    toggle(position, v);
                }

                public void updateDrawState(TextPaint textpaint)
                {
                    textpaint.setUnderlineText(false);
                    textpaint.setColor(mContext.getResources().getColor(0x7f07000f));
                }

                final BaseAdapter this$0;
                final int val$position;
                final View val$v;

            
            {
                this$0 = BaseAdapter.this;
                position = i;
                v = view;
                super();
            }
            }
, 0, spannablestring1.length(), 33);
            CharSequence acharsequence[] = new CharSequence[2];
            acharsequence[0] = spannablestring;
            acharsequence[1] = spannablestring1;
            obj = (Spanned)TextUtils.concat(acharsequence);
        } else
        {
            obj = new SpannableString(s);
        }
        return ((Spanned) (obj));
    }

    public int getOverallPosition(int i, int j)
    {
        return j + ((FirstLetterFriendSection)getSection(i)).getStartPosition();
    }

    public Object getSection(int i)
    {
        return mSections.get(i);
    }

    public int getSectionCount()
    {
        return mSections.size();
    }

    public View getSectionHeaderView(int i, View view, ViewGroup viewgroup)
    {
        View view1 = view;
        if(view1 == null)
            view1 = mInflater.inflate(0x7f03002e, null);
        ((TextView)view1).setText(((FirstLetterFriendSection)mSections.get(i)).toString());
        return view1;
    }

    public int getSectionHeaderViewType(int i)
    {
        return 0;
    }

    protected abstract String getSelectButtonText();

    public ArrayList getSelectedContacts()
    {
        return new ArrayList(mSelectedContacts);
    }

    protected android.view.View.OnClickListener getToggleListener(final int position, final View v)
    {
        return new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                toggle(position, v);
            }

            final BaseAdapter this$0;
            final int val$position;
            final View val$v;

            
            {
                this$0 = BaseAdapter.this;
                position = i;
                v = view;
                super();
            }
        }
;
    }

    public int getTotalContacts()
    {
        return mAllContacts.size();
    }

    public int getViewTypeCount()
    {
        return 2;
    }

    public boolean isEmpty()
    {
        boolean flag;
        if(mAllContacts.size() == 0)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean isEnabled(int i, int j)
    {
        return true;
    }

    public void setAllContacts(List list)
    {
        mSortTask = new SortAndRefreshTask();
        AsyncTask asynctask = mSortTask;
        List alist[] = new List[1];
        alist[0] = list;
        asynctask.execute(alist);
    }

    protected abstract void setupPic(View view, FacebookPhonebookContact facebookphonebookcontact, boolean flag);

    public void toggle(int i, View view)
    {
        FacebookPhonebookContact facebookphonebookcontact = (FacebookPhonebookContact)mAllContacts.get(i);
        long l = getContactId(facebookphonebookcontact);
        if(mSelectedContacts.contains(Long.valueOf(l)))
        {
            mSelectedContacts.remove(Long.valueOf(l));
            ((TextView)view.findViewById(0x7f0e0101)).setText(getContactDisplayText(facebookphonebookcontact, i, view));
            ((Button)view.findViewById(0x7f0e0102)).setVisibility(0);
        } else
        {
            mSelectedContacts.add(Long.valueOf(l));
            ((TextView)view.findViewById(0x7f0e0101)).setText(getContactDisplayText(facebookphonebookcontact, i, view));
            ((Button)view.findViewById(0x7f0e0102)).setVisibility(8);
        }
    }

    protected List mAllContacts;
    protected final Context mContext;
    protected final LayoutInflater mInflater;
    protected List mSections;
    protected final Set mSelectedContacts = new HashSet();
    protected AsyncTask mSortTask;
}
