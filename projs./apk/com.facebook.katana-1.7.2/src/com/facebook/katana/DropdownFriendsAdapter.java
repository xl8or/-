// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DropdownFriendsAdapter.java

package com.facebook.katana;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.*;
import android.widget.*;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.binding.ProfileImage;
import com.facebook.katana.binding.ProfileImagesCache;
import com.facebook.katana.provider.ConnectionsProvider;
import java.util.*;

// Referenced classes of package com.facebook.katana:
//            ViewHolder

public class DropdownFriendsAdapter extends CursorAdapter
{
    protected class DropdownAppSessionListener extends AppSessionListener
    {

        public void onProfileImageDownloaded(AppSession appsession, String s, int i, String s1, Exception exception, ProfileImage profileimage, ProfileImagesCache profileimagescache)
        {
            if(i == 200)
                updateUserImage(profileimage);
        }

        public void onProfileImageLoaded(AppSession appsession, ProfileImage profileimage)
        {
            updateUserImage(profileimage);
        }

        final DropdownFriendsAdapter this$0;

        protected DropdownAppSessionListener()
        {
            this$0 = DropdownFriendsAdapter.this;
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


    public DropdownFriendsAdapter(Context context, Cursor cursor, ProfileImagesCache profileimagescache)
    {
        super(context, cursor);
        mContext = context;
        mUserImagesCache = profileimagescache;
    }

    public void bindView(View view, Context context, Cursor cursor)
    {
        long l = cursor.getLong(1);
        ViewHolder viewholder = (ViewHolder)view.getTag();
        viewholder.setItemId(Long.valueOf(l));
        String s = cursor.getString(3);
        if(s != null)
        {
            android.graphics.Bitmap bitmap = mUserImagesCache.get(mContext, l, s);
            String s1;
            if(bitmap != null)
                viewholder.mImageView.setImageBitmap(bitmap);
            else
                viewholder.mImageView.setImageResource(0x7f0200f3);
        } else
        {
            viewholder.mImageView.setImageResource(0x7f0200f3);
        }
        s1 = cursor.getString(2);
        if(s1 == null)
            s1 = mContext.getString(0x7f0a0067);
        ((TextView)view.findViewById(0x7f0e001c)).setText(s1);
    }

    public Filter getFilter()
    {
        return mFilter;
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
        View view = ((LayoutInflater)mContext.getSystemService("layout_inflater")).inflate(0x7f030078, null);
        ViewHolder viewholder = new ViewHolder(view, 0x7f0e001a);
        view.setTag(viewholder);
        mViewHolders.add(viewholder);
        return view;
    }

    protected void updateUserImage(ProfileImage profileimage)
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

    public final AppSessionListener appSessionListener = new DropdownAppSessionListener();
    private final Context mContext;
    private final Filter mFilter = new Filter() {

        protected android.widget.Filter.FilterResults performFiltering(CharSequence charsequence)
        {
            android.widget.Filter.FilterResults filterresults = new android.widget.Filter.FilterResults();
            if(charsequence != null)
            {
                Uri uri = Uri.withAppendedPath(ConnectionsProvider.FRIENDS_SEARCH_CONTENT_URI, Uri.encode(charsequence.toString()));
                Cursor cursor1 = ((Activity)mContext).managedQuery(uri, FriendsQuery.PROJECTION, null, null, null);
                filterresults.count = cursor1.getCount();
                filterresults.values = cursor1;
            }
            return filterresults;
        }

        protected void publishResults(CharSequence charsequence, android.widget.Filter.FilterResults filterresults)
        {
            if(filterresults.values != null)
                changeCursor((Cursor)filterresults.values);
            notifyDataSetChanged();
        }

        final DropdownFriendsAdapter this$0;

            
            {
                this$0 = DropdownFriendsAdapter.this;
                super();
            }
    }
;
    private final ProfileImagesCache mUserImagesCache;
    private final List mViewHolders = new ArrayList();

}
