// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MailboxThreadsAdapter.java

package com.facebook.katana.activity.messages;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.*;
import android.widget.*;
import com.facebook.katana.CheckboxAdapterListener;
import com.facebook.katana.ViewHolder;
import com.facebook.katana.binding.*;
import com.facebook.katana.model.FacebookSessionInfo;
import com.facebook.katana.util.StringUtils;
import java.util.*;

// Referenced classes of package com.facebook.katana.activity.messages:
//            UserSelectionListener

public class MailboxThreadsAdapter extends CursorAdapter
{
    public static interface ThreadsQuery
    {

        public static final String FILTER = "other_party > 0";
        public static final int INDEX_LAST_UPDATE = 8;
        public static final int INDEX_MSG_COUNT = 6;
        public static final int INDEX_OTHER_PARTY_USER_ID = 5;
        public static final int INDEX_PARTICIPANTS = 2;
        public static final int INDEX_PROFILE_IMAGE_URL = 9;
        public static final int INDEX_SNIPPET = 4;
        public static final int INDEX_SPECIFIC_ID = 0;
        public static final int INDEX_SUBJECT = 3;
        public static final int INDEX_THREAD_ID = 1;
        public static final int INDEX_UNREAD_COUNT = 7;
        public static final String PROJECTION[] = as;

        
        {
            String as[] = new String[10];
            as[0] = "mailbox_threads._id";
            as[1] = "tid";
            as[2] = "participants";
            as[3] = "subject";
            as[4] = "snippet";
            as[5] = "other_party";
            as[6] = "msg_count";
            as[7] = "unread_count";
            as[8] = "last_update";
            as[9] = "profile_image_url";
        }
    }


    public MailboxThreadsAdapter(Context context, Cursor cursor, int i, ProfileImagesCache profileimagescache, final CheckboxAdapterListener checkboxListener, UserSelectionListener userselectionlistener)
    {
        super(context, cursor);
        mContext = context;
        mUserImagesCache = profileimagescache;
        mUserSelectionListener = userselectionlistener;
        mLocalUserId = AppSession.getActiveSession(context, false).getSessionInfo().userId;
        mCheckBoxListener = new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                Long long1 = (Long)view.getTag();
                CheckBox checkbox = (CheckBox)view;
                if(checkbox.isChecked())
                    mMarkedThreads.put(long1, null);
                else
                    mMarkedThreads.remove(long1);
                if(!checkboxListener.shouldChangeState(long1.longValue(), checkbox.isChecked(), mMarkedThreads.size()))
                    checkbox.setChecked(false);
            }

            final MailboxThreadsAdapter this$0;
            final CheckboxAdapterListener val$checkboxListener;

            
            {
                this$0 = MailboxThreadsAdapter.this;
                checkboxListener = checkboxadapterlistener;
                super();
            }
        }
;
    }

    public boolean areAllThreadsChecked()
    {
        boolean flag;
        if(getCursor() != null)
        {
            if(getCursor().getCount() == mMarkedThreads.size())
                flag = true;
            else
                flag = false;
        } else
        {
            flag = false;
        }
        return flag;
    }

    public boolean areAnyThreadsChecked()
    {
        boolean flag;
        if(mMarkedThreads.size() > 0)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public void bindView(View view, Context context, Cursor cursor)
    {
        long l = cursor.getLong(5);
        ViewHolder viewholder = (ViewHolder)view.getTag();
        viewholder.setItemId(Long.valueOf(l));
        String s = cursor.getString(2);
        if(s == null)
            s = "";
        ((TextView)view.findViewById(0x7f0e00b8)).setText(s);
        String s1 = cursor.getString(9);
        TextView textview;
        boolean flag;
        int i;
        if(s1 != null)
        {
            android.graphics.Bitmap bitmap = mUserImagesCache.get(mContext, l, s1);
            String s2;
            Long long1;
            CheckBox checkbox;
            TouchDelegate touchdelegate;
            View view1;
            if(bitmap != null)
                viewholder.mImageView.setImageBitmap(bitmap);
            else
                viewholder.mImageView.setImageResource(0x7f0200f3);
        } else
        {
            viewholder.mImageView.setImageResource(0x7f0200f3);
        }
        viewholder.mImageView.setTag(Long.valueOf(l));
        ((TextView)view.findViewById(0x7f0e00b9)).setText(StringUtils.getTimeAsString(mContext, com.facebook.katana.util.StringUtils.TimeFormatStyle.MAILBOX_RELATIVE_STYLE, 1000L * cursor.getLong(8)));
        ((TextView)view.findViewById(0x7f0e00ba)).setText(cursor.getString(4));
        textview = (TextView)view.findViewById(0x7f0e00bb);
        if(cursor.getInt(7) > 0)
        {
            textview.setTypeface(Typeface.DEFAULT_BOLD);
            view.setBackgroundResource(0x7f02004f);
        } else
        {
            textview.setTypeface(Typeface.DEFAULT);
            view.setBackgroundDrawable(null);
        }
        s2 = cursor.getString(3);
        if(s2 != null && s2.length() > 0)
            textview.setText(s2);
        else
            textview.setText(mContext.getString(0x7f0a00b6));
        long1 = Long.valueOf(cursor.getLong(1));
        checkbox = (CheckBox)view.findViewById(0x7f0e00c6);
        checkbox.setChecked(mMarkedThreads.containsKey(long1));
        checkbox.setTag(long1);
        checkbox.setOnClickListener(mCheckBoxListener);
        if(view.getTouchDelegate() == null)
        {
            touchdelegate = new TouchDelegate(mTouchDelegateRect, checkbox);
            view.setTouchDelegate(touchdelegate);
        }
        view1 = view.findViewById(0x7f0e00c7);
        if(-1L == l || l == mLocalUserId)
            flag = true;
        else
            flag = false;
        if(flag)
            i = 0;
        else
            i = 8;
        view1.setVisibility(i);
    }

    public void checkAll()
    {
        Cursor cursor = getCursor();
        if(cursor != null && mMarkedThreads.size() < cursor.getCount())
        {
            int i = cursor.getPosition();
            if(cursor.moveToFirst())
            {
                do
                    mMarkedThreads.put(Long.valueOf(cursor.getLong(1)), null);
                while(cursor.moveToNext());
                cursor.moveToPosition(i);
                notifyDataSetChanged();
            }
        }
    }

    public long[] getMarkedThreads()
    {
        long al[] = new long[mMarkedThreads.size()];
        int i = 0;
        for(Iterator iterator = mMarkedThreads.keySet().iterator(); iterator.hasNext();)
        {
            al[i] = ((Long)iterator.next()).longValue();
            i++;
        }

        return al;
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
        View view = ((LayoutInflater)mContext.getSystemService("layout_inflater")).inflate(0x7f030042, null);
        ViewHolder viewholder = new ViewHolder(view, 0x7f0e00b7);
        view.setTag(viewholder);
        mViewHolders.add(viewholder);
        viewholder.mImageView.setOnClickListener(mUserImageClickListener);
        return view;
    }

    public void uncheckAll()
    {
        if(mMarkedThreads.size() > 0)
        {
            mMarkedThreads.clear();
            notifyDataSetChanged();
        }
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

    private final android.view.View.OnClickListener mCheckBoxListener;
    private final Context mContext;
    private final long mLocalUserId;
    private final Map mMarkedThreads = new HashMap();
    private final Rect mTouchDelegateRect = new Rect(0, 0, 40, 40);
    private final android.view.View.OnClickListener mUserImageClickListener = new android.view.View.OnClickListener() {

        public void onClick(View view)
        {
            long l = ((Long)view.getTag()).longValue();
            mUserSelectionListener.onUserSelected(l);
        }

        final MailboxThreadsAdapter this$0;

            
            {
                this$0 = MailboxThreadsAdapter.this;
                super();
            }
    }
;
    private final ProfileImagesCache mUserImagesCache;
    private final UserSelectionListener mUserSelectionListener;
    private final List mViewHolders = new ArrayList();


}
