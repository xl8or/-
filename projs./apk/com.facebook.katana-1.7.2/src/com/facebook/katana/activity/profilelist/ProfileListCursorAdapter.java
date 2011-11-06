// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ProfileListCursorAdapter.java

package com.facebook.katana.activity.profilelist;

import android.database.Cursor;
import java.util.Comparator;
import java.util.List;

public abstract class ProfileListCursorAdapter extends ProfileListActivity.ProfileListAdapter
{
    public static class Section
    {

        public int getChildrenCount()
        {
            return mCount;
        }

        public int getCursorStartPosition()
        {
            return mCursorStartPosition;
        }

        public String getTitle()
        {
            return mSectionName;
        }

        public String toString()
        {
            return mSectionName;
        }

        static Comparator mComparator = new Comparator() {

            public int compare(Section section, Section section1)
            {
                return section.mSectionName.compareTo(section1.mSectionName);
            }

            public volatile int compare(Object obj, Object obj1)
            {
                return compare((Section)obj, (Section)obj1);
            }

        }
;
        protected int mCount;
        protected int mCursorStartPosition;
        protected final String mSectionName;


        public Section(String s, int i, int j)
        {
            mSectionName = s;
            mCursorStartPosition = i;
            mCount = j;
        }
    }


    public ProfileListCursorAdapter()
    {
    }

    protected int getChildActualPosition(int i, int j)
    {
        return j + ((Section)mSections.get(i)).getCursorStartPosition();
    }

    public int getChildViewType(int i, int j)
    {
        return 1;
    }

    public int getChildrenCount(int i)
    {
        return ((Section)mSections.get(i)).getChildrenCount();
    }

    public Cursor getCursor()
    {
        return mCursor;
    }

    public Object getSection(int i)
    {
        return mSections.get(i);
    }

    public int getSectionCount()
    {
        return mSections.size();
    }

    public int getSectionHeaderViewType(int i)
    {
        return 0;
    }

    public int getViewTypeCount()
    {
        return 2;
    }

    public boolean isEmpty()
    {
        return false;
    }

    public boolean isEnabled(int i, int j)
    {
        return true;
    }

    protected Cursor mCursor;
    protected List mSections;
}
