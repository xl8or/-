// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PickFriendsAdapter.java

package com.facebook.katana;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Rect;
import android.net.Uri;
import android.view.*;
import android.widget.*;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.ProfileImage;
import com.facebook.katana.binding.ProfileImagesCache;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.model.FacebookSessionInfo;
import com.facebook.katana.platform.PlatformFastTrack;
import com.facebook.katana.provider.ConnectionsProvider;
import com.facebook.katana.util.PlatformUtils;
import java.util.*;

// Referenced classes of package com.facebook.katana:
//            ViewHolder, CheckboxAdapterListener

public class PickFriendsAdapter extends CursorAdapter
    implements SectionIndexer
{
    public static interface FriendsQuery
    {

        public static final int INDEX_USER_DISPLAY_NAME = 4;
        public static final int INDEX_USER_FIRST_NAME = 2;
        public static final int INDEX_USER_ID = 1;
        public static final int INDEX_USER_IMAGE_URL = 5;
        public static final int INDEX_USER_LAST_NAME = 3;
        public static final String PROJECTION[] = as;

        
        {
            String as[] = new String[6];
            as[0] = "_id";
            as[1] = "user_id";
            as[2] = "first_name";
            as[3] = "last_name";
            as[4] = "display_name";
            as[5] = "user_image_url";
        }
    }


    public PickFriendsAdapter(final Context context, Cursor cursor, ProfileImagesCache profileimagescache, CheckboxAdapterListener checkboxadapterlistener, List list)
    {
        super(context, cursor);
        mContext = context;
        mUserImagesCache = profileimagescache;
        mCheckBoxAdapterListener = checkboxadapterlistener;
        mInitialMarkedFriends = new ArrayList(list);
        FacebookProfile facebookprofile;
        for(Iterator iterator = list.iterator(); iterator.hasNext(); mUsersById.put(Long.valueOf(facebookprofile.mId), facebookprofile))
        {
            facebookprofile = (FacebookProfile)iterator.next();
            mMarkedFriends.add(Long.valueOf(facebookprofile.mId));
        }

        mFilter = new Filter() {

            protected android.widget.Filter.FilterResults performFiltering(CharSequence charsequence)
            {
                Cursor cursor1;
                android.widget.Filter.FilterResults filterresults;
                if(charsequence != null && charsequence.length() > 0)
                {
                    Uri uri = Uri.withAppendedPath(ConnectionsProvider.FRIENDS_SEARCH_CONTENT_URI, charsequence.toString());
                    cursor1 = ((Activity)context).managedQuery(uri, FriendsQuery.PROJECTION, null, null, null);
                } else
                {
                    cursor1 = ((Activity)context).managedQuery(ConnectionsProvider.FRIENDS_CONTENT_URI, FriendsQuery.PROJECTION, null, null, null);
                }
                filterresults = new android.widget.Filter.FilterResults();
                filterresults.count = cursor1.getCount();
                filterresults.values = cursor1;
                return filterresults;
            }

            protected void publishResults(CharSequence charsequence, android.widget.Filter.FilterResults filterresults)
            {
                if(filterresults.values != null)
                {
                    Cursor cursor1 = (Cursor)filterresults.values;
                    changeCursor(cursor1);
                }
                notifyDataSetChanged();
            }

            final PickFriendsAdapter this$0;
            final Context val$context;

            
            {
                this$0 = PickFriendsAdapter.this;
                context = context1;
                super();
            }
        }
;
        mAlphabetIndex = new AlphabetIndexer(cursor, 4, context.getString(0x7f0a001d));
        registerDataSetObserver(mAlphabetIndex);
    }

    public void bindView(View view, Context context, Cursor cursor)
    {
        TextView textview = (TextView)view.findViewById(0x7f0e0087);
        int i = cursor.getPosition();
        int j = mAlphabetIndex.getSectionForPosition(i);
        long l;
        ViewHolder viewholder;
        String s;
        if(i > 0)
        {
            if(i == mAlphabetIndex.getPositionForSection(j))
            {
                textview.setText(mAlphabetIndex.getSections()[j].toString());
                textview.setVisibility(0);
            } else
            {
                textview.setVisibility(8);
            }
        } else
        {
            textview.setText(mAlphabetIndex.getSections()[j].toString());
            textview.setVisibility(0);
        }
        l = cursor.getLong(1);
        viewholder = (ViewHolder)view.getTag();
        viewholder.setItemId(Long.valueOf(l));
        s = cursor.getString(5);
        if(s != null)
        {
            android.graphics.Bitmap bitmap = mUserImagesCache.get(mContext, l, s);
            String s1;
            CheckBox checkbox;
            if(bitmap != null)
                viewholder.mImageView.setImageBitmap(bitmap);
            else
                viewholder.mImageView.setImageResource(0x7f0200f3);
        } else
        {
            viewholder.mImageView.setImageResource(0x7f0200f3);
        }
        if(PlatformUtils.platformStorageSupported(context))
            PlatformFastTrack.prepareBadge(viewholder.mImageView, AppSession.getActiveSession(mContext, false).getSessionInfo().username, l, new String[0]);
        s1 = cursor.getString(4);
        if(s1 == null)
            s1 = mContext.getString(0x7f0a0067);
        ((TextView)view.findViewById(0x7f0e001c)).setText(s1);
        checkbox = (CheckBox)view.findViewById(0x7f0e002f);
        checkbox.setChecked(mMarkedFriends.contains(Long.valueOf(l)));
        checkbox.setTag(Integer.valueOf(cursor.getPosition()));
        checkbox.setOnClickListener(mCheckBoxListener);
        if(view.getTouchDelegate() == null)
            view.setTouchDelegate(new TouchDelegate(mTouchDelegateRect, checkbox));
    }

    public void changeCursor(Cursor cursor)
    {
        super.changeCursor(cursor);
        mAlphabetIndex.setCursor(cursor);
    }

    public void flipMarked(int i)
    {
        Cursor cursor;
        long l;
        cursor = (Cursor)getItem(i);
        l = cursor.getLong(1);
        if(!mMarkedFriends.contains(Long.valueOf(l))) goto _L2; else goto _L1
_L1:
        mMarkedFriends.remove(Long.valueOf(l));
        mAddedFriends.remove(Long.valueOf(l));
        mRemovedFriends.add(Long.valueOf(l));
        mCheckBoxAdapterListener.onMarkChanged(l, false, mMarkedFriends.size());
_L4:
        notifyDataSetChanged();
        return;
_L2:
        if(mCheckBoxAdapterListener.shouldChangeState(l, true, mMarkedFriends.size()))
        {
            if((FacebookProfile)mUsersById.get(Long.valueOf(l)) == null)
            {
                String s = cursor.getString(4);
                if(s == null)
                    s = mContext.getString(0x7f0a0067);
                FacebookProfile facebookprofile = new FacebookProfile(l, s, cursor.getString(5), 0);
                mUsersById.put(Long.valueOf(l), facebookprofile);
            }
            mMarkedFriends.add(Long.valueOf(l));
            mAddedFriends.add(Long.valueOf(l));
            mRemovedFriends.remove(Long.valueOf(l));
            mCheckBoxAdapterListener.onMarkChanged(l, true, mMarkedFriends.size());
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    public Filter getFilter()
    {
        return mFilter;
    }

    public ArrayList getMarkedFriends()
    {
        ArrayList arraylist = new ArrayList(mInitialMarkedFriends);
        for(int i = arraylist.size() - 1; i >= 0; i--)
            if(mRemovedFriends.contains(Long.valueOf(((FacebookProfile)arraylist.get(i)).mId)))
                arraylist.remove(i);

        ArrayList arraylist1 = new ArrayList();
        FacebookProfile facebookprofile;
        for(Iterator iterator = mAddedFriends.iterator(); iterator.hasNext(); arraylist1.add(facebookprofile))
        {
            Long long1 = (Long)iterator.next();
            facebookprofile = (FacebookProfile)mUsersById.get(long1);
            if(!$assertionsDisabled && facebookprofile == null)
                throw new AssertionError();
        }

        Collections.sort(arraylist1, new Comparator() {

            public int compare(FacebookProfile facebookprofile1, FacebookProfile facebookprofile2)
            {
                return facebookprofile1.mDisplayName.compareTo(facebookprofile2.mDisplayName);
            }

            public volatile int compare(Object obj, Object obj1)
            {
                return compare((FacebookProfile)obj, (FacebookProfile)obj1);
            }

            final PickFriendsAdapter this$0;

            
            {
                this$0 = PickFriendsAdapter.this;
                super();
            }
        }
);
        arraylist.addAll(arraylist1);
        return arraylist;
    }

    public int getPositionForSection(int i)
    {
        return mAlphabetIndex.getPositionForSection(i);
    }

    public int getSectionForPosition(int i)
    {
        return mAlphabetIndex.getSectionForPosition(i);
    }

    public Object[] getSections()
    {
        return mAlphabetIndex.getSections();
    }

    public boolean isEmpty()
    {
        boolean flag;
        if(getCursor() == null)
            flag = true;
        else
            flag = super.isEmpty();
        return flag;
    }

    public View newView(Context context, Cursor cursor, ViewGroup viewgroup)
    {
        LayoutInflater layoutinflater = (LayoutInflater)mContext.getSystemService("layout_inflater");
        int i;
        View view;
        ViewHolder viewholder;
        if(PlatformUtils.platformStorageSupported(context))
            i = 0x7f030056;
        else
            i = 0x7f030057;
        view = layoutinflater.inflate(i, null);
        viewholder = new ViewHolder(view, 0x7f0e001a);
        view.setTag(viewholder);
        mViewHolders.add(viewholder);
        return view;
    }

    public void updateUserImage(ProfileImage profileimage)
    {
        Iterator iterator = mViewHolders.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            ViewHolder viewholder = (ViewHolder)iterator.next();
            Long long1 = (Long)viewholder.getItemId();
            if(long1 != null && long1.equals(Long.valueOf(profileimage.id)))
                viewholder.mImageView.setImageBitmap(profileimage.getBitmap());
        } while(true);
    }

    static final boolean $assertionsDisabled;
    private final Set mAddedFriends = new HashSet();
    private final AlphabetIndexer mAlphabetIndex;
    private final CheckboxAdapterListener mCheckBoxAdapterListener;
    private final android.view.View.OnClickListener mCheckBoxListener = new android.view.View.OnClickListener() {

        public void onClick(View view)
        {
            int i = ((Integer)view.getTag()).intValue();
            flipMarked(i);
        }

        final PickFriendsAdapter this$0;

            
            {
                this$0 = PickFriendsAdapter.this;
                super();
            }
    }
;
    private final Context mContext;
    private final Filter mFilter;
    private final List mInitialMarkedFriends;
    private final Set mMarkedFriends = new HashSet();
    private final Set mRemovedFriends = new HashSet();
    private final Rect mTouchDelegateRect = new Rect(0, 0, 40, 40);
    private final ProfileImagesCache mUserImagesCache;
    private final Map mUsersById = new HashMap();
    private final List mViewHolders = new ArrayList();

    static 
    {
        boolean flag;
        if(!com/facebook/katana/PickFriendsAdapter.desiredAssertionStatus())
            flag = true;
        else
            flag = false;
        $assertionsDisabled = flag;
    }
}
