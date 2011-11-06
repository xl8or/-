// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DropdownTagUsersAdapter.java

package com.facebook.katana.activity.media;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.*;
import android.widget.*;
import com.facebook.katana.ViewHolder;
import com.facebook.katana.binding.*;
import com.facebook.katana.model.FacebookSessionInfo;
import com.facebook.katana.model.FacebookUser;
import com.facebook.katana.provider.ConnectionsProvider;
import java.util.*;

// Referenced classes of package com.facebook.katana.activity.media:
//            UserHolder

public class DropdownTagUsersAdapter extends ArrayAdapter
{
    protected class DropdownAppSessionListener extends AppSessionListener
    {

        public void onProfileImageDownloaded(AppSession appsession, String s, int i, String s1, Exception exception, ProfileImage profileimage, ProfileImagesCache profileimagescache)
        {
            if(i == 200)
                updateProfileImage(profileimage);
        }

        public void onProfileImageLoaded(AppSession appsession, ProfileImage profileimage)
        {
            updateProfileImage(profileimage);
        }

        final DropdownTagUsersAdapter this$0;

        protected DropdownAppSessionListener()
        {
            this$0 = DropdownTagUsersAdapter.this;
            super();
        }
    }

    public static interface FriendsQuery
    {

        public static final int INDEX_USER_DISPLAY_NAME = 2;
        public static final int INDEX_USER_ID = 1;
        public static final int INDEX_USER_IMAGE_URL = 3;
        public static final String PROJECTION[] = as;

        
        {
            String as[] = new String[4];
            as[0] = "_id";
            as[1] = "user_id";
            as[2] = "display_name";
            as[3] = "user_image_url";
        }
    }


    public DropdownTagUsersAdapter(Context context, ProfileImagesCache profileimagescache)
    {
        super(context, 0x7f030078);
        mContext = context;
        mProfileImagesCache = profileimagescache;
        mAppSession = AppSession.getActiveSession(context, false);
        mAppSessionListener = new DropdownAppSessionListener();
        if(mAppSession != null)
            mAppSession.addListener(mAppSessionListener);
    }

    public void cleanUp()
    {
        if(mAppSession != null)
            mAppSession.removeListener(mAppSessionListener);
    }

    public Filter getFilter()
    {
        return mFilter;
    }

    public View getView(int i, View view, ViewGroup viewgroup)
    {
        View view1 = view;
        if(view1 == null)
            view1 = ((LayoutInflater)mContext.getSystemService("layout_inflater")).inflate(0x7f030078, null);
        UserHolder userholder = (UserHolder)getItem(i);
        ViewHolder viewholder = new ViewHolder(view1, 0x7f0e001a);
        view1.setTag(viewholder);
        userholder.setViewHolder(viewholder);
        viewholder.setItemId(Long.valueOf(userholder.getUid()));
        String s = userholder.getImageURL();
        if(s != null)
        {
            android.graphics.Bitmap bitmap = mProfileImagesCache.get(mContext, userholder.getUid(), s);
            String s1;
            if(bitmap != null)
                viewholder.mImageView.setImageBitmap(bitmap);
            else
                viewholder.mImageView.setImageResource(0x7f0200f3);
        } else
        {
            viewholder.mImageView.setImageResource(0x7f0200f3);
        }
        s1 = userholder.getDisplayName();
        if(s1 == null)
            s1 = mContext.getString(0x7f0a0067);
        ((TextView)view1.findViewById(0x7f0e001c)).setText(s1);
        return view1;
    }

    public boolean isEmpty()
    {
        boolean flag;
        if(getCount() == 0)
            flag = true;
        else
            flag = super.isEmpty();
        return flag;
    }

    protected void updateProfileImage(ProfileImage profileimage)
    {
        for(int i = 0; i < getCount(); i++)
        {
            UserHolder userholder = (UserHolder)getItem(i);
            Long long1 = Long.valueOf(userholder.getUid());
            if(long1 == null || !long1.equals(Long.valueOf(profileimage.id)))
                continue;
            ViewHolder viewholder = userholder.getViewHolder();
            if(viewholder != null)
                viewholder.mImageView.setImageBitmap(profileimage.getBitmap());
        }

    }

    private AppSession mAppSession;
    private DropdownAppSessionListener mAppSessionListener;
    private final Context mContext;
    private final Filter mFilter = new Filter() {

        protected android.widget.Filter.FilterResults performFiltering(CharSequence charsequence)
        {
            android.widget.Filter.FilterResults filterresults = new android.widget.Filter.FilterResults();
            ArrayList arraylist = new ArrayList();
            AppSession appsession = AppSession.getActiveSession(mContext, false);
            if(appsession != null)
            {
                FacebookUser facebookuser = appsession.getSessionInfo().getProfile();
                if(charsequence == null || charsequence.toString().length() == 0 || facebookuser.mDisplayName.toLowerCase().indexOf(charsequence.toString().toLowerCase()) != -1)
                {
                    UserHolder userholder2 = new UserHolder();
                    userholder2.setUid(facebookuser.mUserId);
                    userholder2.setDisplayName(facebookuser.mDisplayName);
                    userholder2.setImageURL(facebookuser.mImageUrl);
                    arraylist.add(userholder2);
                }
            }
            if(charsequence != null && charsequence.toString().length() > 0)
            {
                Uri uri = Uri.withAppendedPath(ConnectionsProvider.FRIENDS_SEARCH_CONTENT_URI, Uri.encode(charsequence.toString()));
                Cursor cursor = ((Activity)mContext).managedQuery(uri, FriendsQuery.PROJECTION, null, null, null);
                if(cursor != null && cursor.moveToFirst())
                    do
                    {
                        UserHolder userholder1 = new UserHolder();
                        userholder1.setUid(cursor.getLong(1));
                        userholder1.setDisplayName(cursor.getString(2));
                        userholder1.setImageURL(cursor.getString(2));
                        arraylist.add(userholder1);
                    } while(cursor.moveToNext());
                UserHolder userholder = new UserHolder();
                userholder.setUid(-1L);
                userholder.setDisplayName(charsequence.toString());
                userholder.setImageURL(null);
                arraylist.add(userholder);
            }
            filterresults.count = arraylist.size();
            filterresults.values = arraylist;
            return filterresults;
        }

        protected void publishResults(CharSequence charsequence, android.widget.Filter.FilterResults filterresults)
        {
            if(filterresults.values != null)
            {
                List list = (List)filterresults.values;
                clear();
                UserHolder userholder;
                for(Iterator iterator = list.iterator(); iterator.hasNext(); add(userholder))
                    userholder = (UserHolder)iterator.next();

                notifyDataSetChanged();
            }
        }

        final DropdownTagUsersAdapter this$0;

            
            {
                this$0 = DropdownTagUsersAdapter.this;
                super();
            }
    }
;
    private final ProfileImagesCache mProfileImagesCache;

}
