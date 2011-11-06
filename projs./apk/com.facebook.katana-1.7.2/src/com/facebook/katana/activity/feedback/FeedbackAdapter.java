// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FeedbackAdapter.java

package com.facebook.katana.activity.feedback;

import android.content.*;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.*;
import android.widget.*;
import com.facebook.katana.binding.*;
import com.facebook.katana.model.FacebookPost;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.provider.ConnectionsProvider;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.view.FacebookPostView;
import java.util.*;

public class FeedbackAdapter extends BaseAdapter
    implements android.widget.AbsListView.RecyclerListener
{
    public static interface CommentsListener
    {

        public abstract void onLike(boolean flag);

        public abstract void onMediaItemClicked(com.facebook.katana.model.FacebookPost.Attachment.MediaItem mediaitem);
    }

    protected static class CommentItem extends Item
    {

        public com.facebook.katana.model.FacebookPost.Comment getComment()
        {
            return mComment;
        }

        public String getTime()
        {
            return mTime;
        }

        private final com.facebook.katana.model.FacebookPost.Comment mComment;
        private final String mTime;

        protected CommentItem(Context context, com.facebook.katana.model.FacebookPost.Comment comment)
        {
            super(31);
            mComment = comment;
            mTime = StringUtils.getTimeAsString(context, com.facebook.katana.util.StringUtils.TimeFormatStyle.STREAM_RELATIVE_STYLE, 1000L * comment.time);
        }
    }

    protected static class PostItem extends Item
    {

        public FacebookPost getPost()
        {
            return mPost;
        }

        protected final FacebookPost mPost;

        protected PostItem(FacebookPost facebookpost, int i)
        {
            super(i);
            mPost = facebookpost;
        }
    }

    protected static class Item
    {

        public int getType()
        {
            return mType;
        }

        public static final int TYPE_CHECKIN_POST = 4;
        public static final int TYPE_COMMENT = 31;
        public static final int TYPE_LIKE_CONTROL = 32;
        public static final int TYPE_LINK_ATTACHMENT_POST = 2;
        public static final int TYPE_PHOTO_ATTACHMENT_POST = 1;
        public static final int TYPE_STATUS_POST = 0;
        public static final int TYPE_VIDEO_ATTACHMENT_POST = 3;
        public static final int TYPE_VIEW_MORE_COMMENTS = 30;
        private final int mType;

        public Item(int i)
        {
            mType = i;
        }
    }


    public FeedbackAdapter(Context context, ListView listview, FacebookPost facebookpost, ProfileImagesCache profileimagescache, StreamPhotosCache streamphotoscache, boolean flag, final CommentsListener commentListener)
    {
        mLikeUserId = -1L;
        mContext = context;
        mPost = facebookpost;
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), 0x7f020100);
        mPhotoDownloadErrorBitmap = BitmapFactory.decodeResource(mContext.getResources(), 0x7f0200ff);
        Bitmap bitmap1 = BitmapFactory.decodeResource(mContext.getResources(), 0x7f0200f3);
        mViewHolders = new ArrayList();
        mItems = new ArrayList();
        buildItems();
        android.view.View.OnClickListener onclicklistener = new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                com.facebook.katana.model.FacebookPost.Attachment.MediaItem mediaitem = (com.facebook.katana.model.FacebookPost.Attachment.MediaItem)view.getTag();
                commentListener.onMediaItemClicked(mediaitem);
            }

            final FeedbackAdapter this$0;
            final CommentsListener val$commentListener;

            
            {
                this$0 = FeedbackAdapter.this;
                commentListener = commentslistener;
                super();
            }
        }
;
        android.view.View.OnClickListener onclicklistener1 = new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                mLikePending = true;
                notifyDataSetChanged();
                CommentsListener commentslistener = commentListener;
                boolean flag1;
                if(!mPost.getLikes().doesUserLike())
                    flag1 = true;
                else
                    flag1 = false;
                commentslistener.onLike(flag1);
            }

            final FeedbackAdapter this$0;
            final CommentsListener val$commentListener;

            
            {
                this$0 = FeedbackAdapter.this;
                commentListener = commentslistener;
                super();
            }
        }
;
        mLikeClickListener = onclicklistener1;
        mExtras = new com.facebook.katana.view.FacebookPostView.Extras(profileimagescache, streamphotoscache, null, onclicklistener, bitmap, bitmap1, -1L);
        mGetCommentsPending = flag;
        listview.setRecyclerListener(this);
        com.facebook.katana.model.FacebookPost.Likes likes = facebookpost.getLikes();
        int i = likes.getCount();
        int j;
        long l;
        if(likes.doesUserLike())
            j = 1;
        else
            j = 0;
        if(i - j != 1) goto _L2; else goto _L1
_L1:
        l = likes.getFriendUserId();
        if(l == -1L) goto _L4; else goto _L3
_L3:
        mLikeName = readNameFromContentProvider(context, l);
        if(mLikeName == null)
            mLikeUserId = l;
_L2:
        return;
_L4:
        long l1 = facebookpost.getLikes().getSampleUserId();
        if(l1 != -1L)
            mLikeUserId = l1;
        if(true) goto _L2; else goto _L5
_L5:
    }

    private static String readNameFromContentProvider(Context context, long l)
    {
        String s = null;
        android.net.Uri uri = ContentUris.withAppendedId(ConnectionsProvider.FRIEND_UID_CONTENT_URI, l);
        ContentResolver contentresolver = context.getContentResolver();
        String as[] = new String[1];
        as[0] = "display_name";
        Cursor cursor = contentresolver.query(uri, as, null, null, null);
        if(cursor.moveToFirst())
            s = cursor.getString(0);
        cursor.close();
        return s;
    }

    public void addCommentComplete()
    {
        refresh();
    }

    public void addLikeComplete(int i)
    {
        mLikePending = false;
        if(i == 200)
            refresh();
        else
            notifyDataSetChanged();
    }

    protected void buildItems()
    {
        mItems.clear();
        if(mPost != null) goto _L2; else goto _L1
_L1:
        return;
_L2:
        mPost.getPostType();
        JVM INSTR tableswitch 0 4: default 60
    //                   0 63
    //                   1 241
    //                   2 266
    //                   3 216
    //                   4 291;
           goto _L3 _L4 _L5 _L6 _L7 _L8
_L3:
        continue; /* Loop/switch isn't completed */
_L4:
        mItems.add(new PostItem(mPost, 0));
_L10:
        int j;
        mItems.add(new Item(32));
        int i = mPost.getComments().getCount();
        j = mPost.getComments().getTotalCount();
        if(i <= 0)
            break; /* Loop/switch isn't completed */
        List list = mPost.getComments().getComments();
        if(i < j)
            mItems.add(new Item(30));
        int k = 0;
        while(k < i) 
        {
            mItems.add(new CommentItem(mContext, (com.facebook.katana.model.FacebookPost.Comment)list.get(k)));
            k++;
        }
        continue; /* Loop/switch isn't completed */
_L7:
        mItems.add(new PostItem(mPost, 3));
        continue; /* Loop/switch isn't completed */
_L5:
        mItems.add(new PostItem(mPost, 2));
        continue; /* Loop/switch isn't completed */
_L6:
        mItems.add(new PostItem(mPost, 1));
        continue; /* Loop/switch isn't completed */
_L8:
        mItems.add(new PostItem(mPost, 4));
        if(true) goto _L10; else goto _L9
_L9:
        if(j > 0)
            mItems.add(new Item(30));
        if(true) goto _L1; else goto _L11
_L11:
    }

    public void close()
    {
        mItems.clear();
        mViewHolders.clear();
    }

    public int getCount()
    {
        return mItems.size();
    }

    public Object getItem(int i)
    {
        return Integer.valueOf(i);
    }

    public Item getItemByPosition(int i)
    {
        return (Item)mItems.get(i);
    }

    public long getItemId(int i)
    {
        return (long)i;
    }

    public int getItemViewType(int i)
    {
        ((Item)mItems.get(i)).getType();
        JVM INSTR lookupswitch 8: default 92
    //                   0: 96
    //                   1: 101
    //                   2: 111
    //                   3: 106
    //                   4: 116
    //                   30: 121
    //                   31: 126
    //                   32: 132;
           goto _L1 _L2 _L3 _L4 _L5 _L6 _L7 _L8 _L9
_L1:
        int j = 0;
_L11:
        return j;
_L2:
        j = 0;
        continue; /* Loop/switch isn't completed */
_L3:
        j = 1;
        continue; /* Loop/switch isn't completed */
_L5:
        j = 2;
        continue; /* Loop/switch isn't completed */
_L4:
        j = 3;
        continue; /* Loop/switch isn't completed */
_L6:
        j = 4;
        continue; /* Loop/switch isn't completed */
_L7:
        j = 5;
        continue; /* Loop/switch isn't completed */
_L8:
        j = 6;
        continue; /* Loop/switch isn't completed */
_L9:
        j = 7;
        if(true) goto _L11; else goto _L10
_L10:
    }

    public long getLikeUserId()
    {
        return mLikeUserId;
    }

    public View getView(int i, View view, ViewGroup viewgroup)
    {
        View view1;
        Item item;
        com.facebook.katana.view.FacebookPostView.ViewHolder viewholder;
        view1 = null;
        item = (Item)mItems.get(i);
        viewholder = null;
        if(view != null) goto _L2; else goto _L1
_L1:
        LayoutInflater layoutinflater = (LayoutInflater)mContext.getSystemService("layout_inflater");
        item.getType();
        JVM INSTR lookupswitch 8: default 120
    //                   0: 203
    //                   1: 203
    //                   2: 203
    //                   3: 203
    //                   4: 203
    //                   30: 243
    //                   31: 257
    //                   32: 421;
           goto _L3 _L4 _L4 _L4 _L4 _L4 _L5 _L6 _L7
_L3:
        item.getType();
        JVM INSTR lookupswitch 8: default 200
    //                   0: 451
    //                   1: 451
    //                   2: 451
    //                   3: 451
    //                   4: 451
    //                   30: 487
    //                   31: 613
    //                   32: 750;
           goto _L8 _L9 _L9 _L9 _L9 _L9 _L10 _L11 _L12
_L8:
        return view1;
_L4:
        view1 = FacebookPostView.inflatePostView(((PostItem)item).getPost(), layoutinflater);
        viewholder = (com.facebook.katana.view.FacebookPostView.ViewHolder)view1.getTag();
        mViewHolders.add(viewholder);
          goto _L3
_L5:
        view1 = layoutinflater.inflate(0x7f030048, null);
          goto _L3
_L6:
        view1 = layoutinflater.inflate(0x7f030011, null);
        viewholder = new com.facebook.katana.view.FacebookPostView.ViewHolder();
        ImageView imageview1 = (ImageView)view1.findViewById(0x7f0e0036);
        viewholder.mUserImageView = imageview1;
        TextView textview1 = (TextView)view1.findViewById(0x7f0e0039);
        viewholder.mTextTextView = textview1;
        TextView textview2 = (TextView)view1.findViewById(0x7f0e0038);
        viewholder.mTimeTextView = textview2;
        TextView textview3 = (TextView)view1.findViewById(0x7f0e0037);
        viewholder.mAttachmentName = textview3;
        viewholder.mAttachmentCaption = null;
        viewholder.mAttachmentDescription = null;
        viewholder.mImageHolders[0] = null;
        viewholder.mImageHolders[1] = null;
        viewholder.mImageHolders[2] = null;
        viewholder.mPhotoCount = 0;
        view1.setTag(viewholder);
        mViewHolders.add(viewholder);
          goto _L3
_L7:
        view1 = layoutinflater.inflate(0x7f030039, null);
          goto _L3
_L2:
        view1 = view;
        viewholder = (com.facebook.katana.view.FacebookPostView.ViewHolder)view1.getTag();
          goto _L3
_L9:
        FacebookPost facebookpost = ((PostItem)item).getPost();
        Context context4 = mContext;
        com.facebook.katana.view.FacebookPostView.Extras extras = mExtras;
        FacebookPostView.renderPostView(context4, facebookpost, viewholder, extras);
          goto _L8
_L10:
        int k = mPost.getComments().getTotalCount();
        String s1;
        if(mPost.getComments().getCount() > 0)
        {
            if(mGetCommentsPending)
                s1 = mContext.getString(0x7f0a01bc);
            else
                s1 = mContext.getString(0x7f0a01d5);
        } else
        if(k > 0)
        {
            if(mGetCommentsPending)
                s1 = mContext.getString(0x7f0a01ba);
            else
                s1 = mContext.getString(0x7f0a01d4);
        } else
        {
            s1 = null;
        }
        ((TextView)view1.findViewById(0x7f0e00d2)).setText(s1);
          goto _L8
_L11:
        CommentItem commentitem = (CommentItem)item;
        ImageView imageview = viewholder.mUserImageView;
        com.facebook.katana.model.FacebookPost.Comment comment = commentitem.getComment();
        long l = comment.fromId;
        viewholder.mUserId = l;
        Bitmap bitmap = mExtras.mUserImagesCache.get(mContext, comment.fromId, comment.getProfile().mImageUrl);
        Bitmap bitmap1;
        if(bitmap != null)
            bitmap1 = bitmap;
        else
            bitmap1 = mExtras.mNoAvatarBitmap;
        imageview.setImageBitmap(bitmap1);
        viewholder.mAttachmentName.setText(comment.getProfile().mDisplayName);
        viewholder.mTextTextView.setText(comment.text);
        viewholder.mTimeTextView.setText(commentitem.getTime());
          goto _L8
_L12:
        com.facebook.katana.model.FacebookPost.Likes likes = mPost.getLikes();
        ToggleButton togglebutton = (ToggleButton)view1.findViewById(0x7f0e00ab);
        int j;
        boolean flag;
        TextView textview;
        String s;
        View view2;
        if(likes.canLike)
            j = 0;
        else
            j = 8;
        togglebutton.setVisibility(j);
        togglebutton.setOnClickListener(mLikeClickListener);
        if(!likes.doesUserLike())
            flag = true;
        else
            flag = false;
        togglebutton.setChecked(flag);
        if(mLikePending)
        {
            togglebutton.setEnabled(false);
            view1.findViewById(0x7f0e00ac).setVisibility(0);
        } else
        {
            togglebutton.setEnabled(true);
            view1.findViewById(0x7f0e00ac).setVisibility(8);
        }
        textview = (TextView)view1.findViewById(0x7f0e00aa);
        if(likes.doesUserLike())
        {
            if(likes.getCount() == 1)
                s = mContext.getString(0x7f0a01de);
            else
            if(likes.getCount() == 2)
            {
                if(mLikeName != null)
                {
                    Context context3 = mContext;
                    Object aobj3[] = new Object[1];
                    aobj3[0] = mLikeName;
                    s = context3.getString(0x7f0a01dc, aobj3);
                } else
                {
                    s = mContext.getString(0x7f0a01db);
                }
            } else
            {
                Context context2 = mContext;
                Object aobj2[] = new Object[1];
                aobj2[0] = Integer.valueOf(likes.getCount() - 1);
                s = context2.getString(0x7f0a01dd, aobj2);
            }
        } else
        if(likes.getCount() == 1)
        {
            if(mLikeName != null)
            {
                Context context1 = mContext;
                Object aobj1[] = new Object[1];
                aobj1[0] = mLikeName;
                s = context1.getString(0x7f0a01c1, aobj1);
            } else
            {
                s = mContext.getString(0x7f0a01c2);
            }
        } else
        if(likes.getCount() > 1)
        {
            Context context = mContext;
            Object aobj[] = new Object[1];
            aobj[0] = Integer.valueOf(likes.getCount());
            s = context.getString(0x7f0a01c3, aobj);
        } else
        {
            s = "";
        }
        textview.setText(s);
        view2 = view1.findViewById(0x7f0e00a9);
        if(s.length() > 0)
            view2.setVisibility(0);
        else
            view2.setVisibility(8);
          goto _L8
    }

    public int getViewTypeCount()
    {
        return 8;
    }

    public void onMovedToScrapHeap(View view)
    {
        Object obj = view.getTag();
        if(obj instanceof com.facebook.katana.view.FacebookPostView.ViewHolder)
            ((com.facebook.katana.view.FacebookPostView.ViewHolder)obj).detach();
    }

    public void refresh()
    {
        buildItems();
        notifyDataSetChanged();
    }

    public void removeCommentComplete()
    {
        refresh();
    }

    public void removeLikeComplete(int i)
    {
        mLikePending = false;
        if(i == 200)
            refresh();
        else
            notifyDataSetChanged();
    }

    public void requestCommentsComplete(int i)
    {
        mGetCommentsPending = false;
        if(i == 200)
            refresh();
        else
            notifyDataSetChanged();
    }

    public void requestMoreComments()
    {
        mGetCommentsPending = true;
        notifyDataSetChanged();
    }

    public void updateLikeUserName(long l, String s)
    {
        if(l == mLikeUserId)
        {
            mLikeName = s;
            mLikeUserId = -1L;
            notifyDataSetChanged();
        }
    }

    public void updatePhoto(Bitmap bitmap, String s)
    {
        Iterator iterator = mViewHolders.iterator();
        com.facebook.katana.view.FacebookPostView.ViewHolder viewholder;
        do
        {
            if(!iterator.hasNext())
                break;
            viewholder = (com.facebook.katana.view.FacebookPostView.ViewHolder)iterator.next();
            if(bitmap == null)
                bitmap = mPhotoDownloadErrorBitmap;
        } while(!viewholder.setPhotoBitmap(mContext, bitmap, s));
    }

    public void updateUserImage(ProfileImage profileimage)
    {
        for(Iterator iterator = mViewHolders.iterator(); iterator.hasNext(); ((com.facebook.katana.view.FacebookPostView.ViewHolder)iterator.next()).setUserImageBitmap(profileimage));
    }

    private final Context mContext;
    private com.facebook.katana.view.FacebookPostView.Extras mExtras;
    private boolean mGetCommentsPending;
    private final List mItems;
    private final android.view.View.OnClickListener mLikeClickListener;
    private String mLikeName;
    private boolean mLikePending;
    private long mLikeUserId;
    private final Bitmap mPhotoDownloadErrorBitmap;
    private final FacebookPost mPost;
    private final List mViewHolders;


/*
    static boolean access$002(FeedbackAdapter feedbackadapter, boolean flag)
    {
        feedbackadapter.mLikePending = flag;
        return flag;
    }

*/

}
