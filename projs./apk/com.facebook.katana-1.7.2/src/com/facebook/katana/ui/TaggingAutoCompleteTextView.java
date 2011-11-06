// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TaggingAutoCompleteTextView.java

package com.facebook.katana.ui;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;
import com.facebook.katana.DropdownFriendsAdapter;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.binding.ProfileImagesCache;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.util.Log;
import com.facebook.katana.util.Utils;
import java.util.HashMap;
import java.util.Map;

public class TaggingAutoCompleteTextView extends AutoCompleteTextView
{
    protected class Watcher
        implements TextWatcher
    {

        public void afterTextChanged(Editable editable)
        {
            FacebookProfile afacebookprofile[] = (FacebookProfile[])editable.getSpans(0, editable.length(), com/facebook/katana/model/FacebookProfile);
            int i = afacebookprofile.length;
            for(int j = 0; j < i; j++)
            {
                FacebookProfile facebookprofile = afacebookprofile[j];
                int k = editable.getSpanStart(facebookprofile);
                int l = editable.getSpanEnd(facebookprofile);
                if(facebookprofile.mDisplayName.equals(editable.subSequence(k, l).toString()))
                    continue;
                editable.removeSpan(facebookprofile);
                StyleSpan astylespan[] = (StyleSpan[])editable.getSpans(k, k + 1, android/text/style/StyleSpan);
                if(astylespan.length != 1)
                    Log.e(TaggingAutoCompleteTextView.TAG, "unexpected number of style spans to invalidate");
                int i1 = astylespan.length;
                for(int j1 = 0; j1 < i1; j1++)
                    editable.removeSpan(astylespan[j1]);

                TaggingAutoCompleteTextView taggingautocompletetextview = TaggingAutoCompleteTextView.this;
                taggingautocompletetextview.mNumTags = taggingautocompletetextview.mNumTags - 1;
            }

        }

        public void beforeTextChanged(CharSequence charsequence, int i, int j, int k)
        {
        }

        public void onTextChanged(CharSequence charsequence, int i, int j, int k)
        {
        }

        final TaggingAutoCompleteTextView this$0;

        protected Watcher()
        {
            this$0 = TaggingAutoCompleteTextView.this;
            super();
        }
    }

    public static class TaggedUser
        implements CharSequence
    {

        public char charAt(int i)
        {
            return profile.mDisplayName.charAt(i);
        }

        public int length()
        {
            return profile.mDisplayName.length();
        }

        public CharSequence subSequence(int i, int j)
        {
            return profile.mDisplayName.subSequence(i, j);
        }

        public String toString()
        {
            return profile.mDisplayName;
        }

        public final FacebookProfile profile;

        public TaggedUser(FacebookProfile facebookprofile)
        {
            profile = facebookprofile;
        }
    }


    public TaggingAutoCompleteTextView(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        mCachedResults = new HashMap();
        setInputType(0xfffeffff & getInputType());
        mNumTags = 0;
    }

    public AppSessionListener configureView(Activity activity, ProfileImagesCache profileimagescache)
    {
        mActivity = activity;
        mDropdownAdapter = new DropdownFriendsAdapter(activity, null, profileimagescache);
        setAdapter(mDropdownAdapter);
        addTextChangedListener(new Watcher());
        return mDropdownAdapter.appSessionListener;
    }

    protected CharSequence convertSelectionToString(Object obj)
    {
        Cursor cursor = (Cursor)obj;
        int i = cursor.getColumnIndexOrThrow("user_id");
        int j = cursor.getColumnIndexOrThrow("display_name");
        int k = cursor.getColumnIndexOrThrow("user_image_url");
        FacebookProfile facebookprofile = new FacebookProfile(cursor.getLong(i), cursor.getString(j), cursor.getString(k), 0);
        mCachedResults.put(facebookprofile.mDisplayName, facebookprofile);
        return new TaggedUser(facebookprofile);
    }

    protected void performFiltering(CharSequence charsequence, int i)
    {
        int j;
        boolean flag;
        j = getSelectionStart();
        flag = false;
        break MISSING_BLOCK_LABEL_8;
_L2:
        int k;
        do
            return;
        while(mNumTags >= 6 || j > charsequence.length());
        k = j - 1;
_L3:
label0:
        {
            if(k >= 0)
            {
                if(charsequence.charAt(k) != '@')
                    break label0;
                flag = true;
            }
            if(!flag)
            {
                dismissDropDown();
            } else
            {
                CharSequence charsequence1 = charsequence.subSequence(k + 1, j);
                if(charsequence1.length() < 1)
                    dismissDropDown();
                else
                    super.performFiltering(charsequence1, i);
            }
        }
        if(true) goto _L2; else goto _L1
_L1:
        k--;
          goto _L3
    }

    protected void replaceText(CharSequence charsequence)
    {
        Editable editable = getEditableText();
        if(editable == null)
            throw new IllegalStateException("not editable text");
        FacebookProfile facebookprofile;
        int i;
        boolean flag;
        int j;
        if(charsequence instanceof TaggedUser)
            facebookprofile = ((TaggedUser)charsequence).profile;
        else
            facebookprofile = (FacebookProfile)mCachedResults.remove(charsequence.toString());
        i = getSelectionStart();
        flag = false;
        j = i - 1;
label0:
        do
        {
label1:
            {
                if(j >= 0)
                {
                    if(editable.charAt(j) != '@')
                        break label1;
                    flag = true;
                }
                if(!flag)
                    throw new IllegalStateException("attempted to complete name without \"@\" marker");
                break label0;
            }
            j--;
        } while(true);
        if((editable.length() - (i - j)) + charsequence.length() <= mActivity.getResources().getInteger(0x7f080000))
        {
            editable.delete(j, i);
            editable.insert(j, charsequence);
            editable.setSpan(new StyleSpan(1), j, j + charsequence.length(), 33);
            editable.setSpan(facebookprofile, j, j + charsequence.length(), 33);
            mNumTags = 1 + mNumTags;
            mDropdownAdapter.changeCursor(null);
        }
    }

    public static final int MAX_TAGS = 6;
    public static final String TAG = Utils.getClassName(com/facebook/katana/ui/TaggingAutoCompleteTextView);
    protected Activity mActivity;
    protected Map mCachedResults;
    protected DropdownFriendsAdapter mDropdownAdapter;
    protected int mNumTags;

}
