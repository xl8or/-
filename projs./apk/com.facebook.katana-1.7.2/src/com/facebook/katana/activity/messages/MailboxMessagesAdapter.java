// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MailboxMessagesAdapter.java

package com.facebook.katana.activity.messages;

import android.content.Context;
import android.database.Cursor;
import android.view.*;
import android.widget.*;
import com.facebook.katana.ViewHolder;
import com.facebook.katana.binding.ProfileImage;
import com.facebook.katana.binding.ProfileImagesCache;
import com.facebook.katana.util.StringUtils;
import java.util.*;

// Referenced classes of package com.facebook.katana.activity.messages:
//            UserSelectionListener

public class MailboxMessagesAdapter extends CursorAdapter
{
    public static interface MessageQuery
    {

        public static final int INDEX_AUTHOR_IMAGE_URL = 5;
        public static final int INDEX_AUTHOR_NAME = 4;
        public static final int INDEX_AUTHOR_USER_ID = 1;
        public static final int INDEX_BODY = 3;
        public static final int INDEX_OBJECT_IMAGE_URL = 7;
        public static final int INDEX_OBJECT_NAME = 6;
        public static final int INDEX_SPECIFIC_ID = 0;
        public static final int INDEX_TIME_SENT = 2;
        public static final String PROJECTION[] = as;

        
        {
            String as[] = new String[8];
            as[0] = "_id";
            as[1] = "author_id";
            as[2] = "sent";
            as[3] = "body";
            as[4] = "author_name";
            as[5] = "author_image_url";
            as[6] = "object_name";
            as[7] = "object_image_url";
        }
    }


    public MailboxMessagesAdapter(Context context, Cursor cursor, ProfileImagesCache profileimagescache, UserSelectionListener userselectionlistener)
    {
        super(context, cursor);
        mContext = context;
        mUserImagesCache = profileimagescache;
        mUserSelectionListener = userselectionlistener;
    }

    public void bindView(View view, Context context, Cursor cursor)
    {
        long l = cursor.getLong(1);
        ViewHolder viewholder = (ViewHolder)view.getTag();
        viewholder.setItemId(Long.valueOf(l));
        String s = cursor.getString(6);
        if(s == null)
        {
            s = cursor.getString(4);
            if(s == null)
                s = mContext.getString(0x7f0a0067);
        }
        ((TextView)view.findViewById(0x7f0e00b8)).setText(s);
        String s1 = StringUtils.getTimeAsString(mContext, com.facebook.katana.util.StringUtils.TimeFormatStyle.MAILBOX_RELATIVE_STYLE, 1000L * cursor.getLong(2));
        ((TextView)view.findViewById(0x7f0e00b9)).setText(s1);
        ((TextView)view.findViewById(0x7f0e00ba)).setText(cursor.getString(3));
        String s2 = cursor.getString(7);
        if(s2 == null)
            s2 = cursor.getString(5);
        if(s2 != null)
        {
            android.graphics.Bitmap bitmap = mUserImagesCache.get(mContext, l, s2);
            if(bitmap != null)
                viewholder.mImageView.setImageBitmap(bitmap);
            else
                viewholder.mImageView.setImageResource(0x7f0200f3);
        } else
        {
            viewholder.mImageView.setImageResource(0x7f0200f3);
        }
        viewholder.mImageView.setTag(Long.valueOf(l));
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
        View view = ((LayoutInflater)mContext.getSystemService("layout_inflater")).inflate(0x7f03003f, null);
        ViewHolder viewholder = new ViewHolder(view, 0x7f0e00b7);
        view.setTag(viewholder);
        mViewHolders.add(viewholder);
        viewholder.mImageView.setOnClickListener(mClickListener);
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

    private final android.view.View.OnClickListener mClickListener = new android.view.View.OnClickListener() {

        public void onClick(View view)
        {
            long l = ((Long)view.getTag()).longValue();
            mUserSelectionListener.onUserSelected(l);
        }

        final MailboxMessagesAdapter this$0;

            
            {
                this$0 = MailboxMessagesAdapter.this;
                super();
            }
    }
;
    private final Context mContext;
    private final ProfileImagesCache mUserImagesCache;
    private final UserSelectionListener mUserSelectionListener;
    private final List mViewHolders = new ArrayList();

}
