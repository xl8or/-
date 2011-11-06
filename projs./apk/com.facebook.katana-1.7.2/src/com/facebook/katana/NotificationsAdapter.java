// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NotificationsAdapter.java

package com.facebook.katana;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.TextAppearanceSpan;
import android.util.DisplayMetrics;
import android.view.*;
import android.widget.*;
import com.facebook.katana.binding.StreamPhotosCache;
import com.facebook.katana.util.StringUtils;
import java.util.*;

// Referenced classes of package com.facebook.katana:
//            ViewHolder

public class NotificationsAdapter extends CursorAdapter
{
    public static interface NotificationsQuery
    {

        public static final int INDEX_APP_ID = 2;
        public static final int INDEX_APP_IMAGE = 11;
        public static final int INDEX_BODY = 7;
        public static final int INDEX_CREATED_TIME = 8;
        public static final int INDEX_HREF = 9;
        public static final int INDEX_IS_READ = 10;
        public static final int INDEX_NOTIFICATION_ID = 1;
        public static final int INDEX_OBJECT_ID = 12;
        public static final int INDEX_OBJECT_TYPE = 13;
        public static final int INDEX_SENDER_ID = 3;
        public static final int INDEX_SENDER_IMAGE_URL = 5;
        public static final int INDEX_SENDER_NAME = 4;
        public static final int INDEX_TITLE = 6;
        public static final String PROJECTION[] = as;

        
        {
            String as[] = new String[14];
            as[0] = "_id";
            as[1] = "notif_id";
            as[2] = "app_id";
            as[3] = "sender_id";
            as[4] = "sender_name";
            as[5] = "sender_url";
            as[6] = "title";
            as[7] = "body";
            as[8] = "created";
            as[9] = "href";
            as[10] = "is_read";
            as[11] = "app_image";
            as[12] = "object_id";
            as[13] = "object_type";
        }
    }


    public NotificationsAdapter(Context context, Cursor cursor, StreamPhotosCache streamphotoscache)
    {
        super(context, cursor);
        mContext = context;
        mPhotosCache = streamphotoscache;
        Resources resources = mContext.getResources();
        mNameSpan = new TextAppearanceSpan(null, 1, (int)(14F * resources.getDisplayMetrics().density), ColorStateList.valueOf(resources.getColor(0x7f070007)), null);
    }

    private Spannable buildTitle(String s, String s1)
    {
        SpannableStringBuilder spannablestringbuilder = new SpannableStringBuilder(s);
        if(s1 != null)
        {
            if(s1.startsWith(s))
                s1 = s1.substring(s.length());
            spannablestringbuilder.append(s1);
        }
        spannablestringbuilder.setSpan(mNameSpan, 0, s.length(), 33);
        return spannablestringbuilder;
    }

    public void bindView(View view, Context context, Cursor cursor)
    {
        boolean flag;
        ViewHolder viewholder;
        String s;
        ImageView imageview;
        long l;
        String s2;
        String s3;
        TextView textview;
        String s4;
        TextView textview1;
        String s5;
        if(cursor.getInt(10) == 1)
            flag = true;
        else
            flag = false;
        if(flag)
            view.setBackgroundResource(0x7f020016);
        else
            view.setBackgroundResource(0x7f02012f);
        viewholder = (ViewHolder)view.getTag();
        viewholder.mImageView.setImageResource(0x7f0200f3);
        s = cursor.getString(5);
        viewholder.setItemId(s);
        if(s != null)
        {
            Bitmap bitmap1 = mPhotosCache.get(mContext, s, 1);
            if(bitmap1 != null)
                viewholder.mImageView.setImageBitmap(bitmap1);
        }
        imageview = (ImageView)view.findViewById(0x7f0e00d6);
        l = cursor.getLong(2);
        if(l == 0x94c27427L)
            imageview.setImageResource(0x7f02009f);
        else
        if(l == 0x8967ab9cL)
            imageview.setImageResource(0x7f02009f);
        else
        if(l == 0x8fa5a3c6L)
            imageview.setImageResource(0x7f0200aa);
        else
        if(l == 0xa2151494L)
            imageview.setImageResource(0x7f0200c3);
        else
        if(l == 0x8cc6b0c6L)
        {
            imageview.setImageResource(0x7f0200a2);
        } else
        {
            imageview.setImageResource(0x7f0200c9);
            String s1 = cursor.getString(11);
            if(s1 != null)
            {
                Bitmap bitmap = mPhotosCache.get(mContext, s1, 1);
                if(bitmap != null)
                    imageview.setImageBitmap(bitmap);
                else
                    imageview.setImageResource(0x7f0200c9);
            } else
            {
                imageview.setImageResource(0x7f0200c9);
            }
        }
        s2 = cursor.getString(6);
        s3 = cursor.getString(4);
        textview = (TextView)view.findViewById(0x7f0e00d5);
        if(s3 != null)
            textview.setText(buildTitle(s3, s2));
        else
            textview.setText(s2);
        s4 = StringUtils.getTimeAsString(mContext, com.facebook.katana.util.StringUtils.TimeFormatStyle.STREAM_RELATIVE_STYLE, 1000L * cursor.getLong(8));
        ((TextView)view.findViewById(0x7f0e00d7)).setText(s4);
        textview1 = (TextView)view.findViewById(0x7f0e00d8);
        s5 = cursor.getString(7);
        if(s5 != null && s5.length() > 0)
        {
            textview1.setText(s5);
            textview1.setVisibility(0);
        } else
        {
            textview1.setVisibility(8);
        }
    }

    public void clearViews()
    {
        mViewHolders.clear();
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
        View view = ((LayoutInflater)mContext.getSystemService("layout_inflater")).inflate(0x7f03004a, null);
        ViewHolder viewholder = new ViewHolder(view, 0x7f0e001a);
        view.setTag(viewholder);
        mViewHolders.add(viewholder);
        return view;
    }

    public void updatePhoto(Bitmap bitmap, String s)
    {
        Iterator iterator = mViewHolders.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            ViewHolder viewholder = (ViewHolder)iterator.next();
            String s1 = (String)viewholder.getItemId();
            if(s1 != null && s1.equals(s))
                viewholder.mImageView.setImageBitmap(bitmap);
        } while(true);
    }

    private static final long COMMENTS_APP_ID = 0x94c27427L;
    private static final long GROUPS_APP_ID = 0x8cc6b0c6L;
    private static final long LIKE_APP_ID = 0x8fa5a3c6L;
    private static final long PHOTO_COMMENTS_APP_ID = 0x8967ab9cL;
    private static final long WALL_APP_ID = 0xa2151494L;
    private final Context mContext;
    private final TextAppearanceSpan mNameSpan;
    private final StreamPhotosCache mPhotosCache;
    private final List mViewHolders = new ArrayList();
}
