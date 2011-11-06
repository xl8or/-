// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StreamAdapter.java

package com.facebook.katana.activity.stream;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.*;
import android.widget.*;
import com.facebook.katana.binding.*;
import com.facebook.katana.model.FacebookPost;
import com.facebook.katana.view.FacebookPostView;
import java.util.*;

public class StreamAdapter extends BaseAdapter
    implements android.widget.AbsListView.RecyclerListener
{
    protected static interface StreamAdapterListener
    {

        public abstract void onOpenMediaItem(com.facebook.katana.model.FacebookPost.Attachment.MediaItem mediaitem);

        public abstract void onUserImageClicked(FacebookPost facebookpost);
    }


    public StreamAdapter(Context context, ListView listview, FacebookStreamContainer facebookstreamcontainer, ProfileImagesCache profileimagescache, StreamPhotosCache streamphotoscache, final StreamAdapterListener listener, long l)
    {
        mContext = context;
        mStreamContainer = facebookstreamcontainer;
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), 0x7f020100);
        mPhotoDownloadErrorBitmap = BitmapFactory.decodeResource(mContext.getResources(), 0x7f0200ff);
        Bitmap bitmap1 = BitmapFactory.decodeResource(mContext.getResources(), 0x7f0200f3);
        mExtras = new com.facebook.katana.view.FacebookPostView.Extras(profileimagescache, streamphotoscache, new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                listener.onUserImageClicked((FacebookPost)view.getTag());
            }

            final StreamAdapter this$0;
            final StreamAdapterListener val$listener;

            
            {
                this$0 = StreamAdapter.this;
                listener = streamadapterlistener;
                super();
            }
        }
, new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                com.facebook.katana.model.FacebookPost.Attachment.MediaItem mediaitem = (com.facebook.katana.model.FacebookPost.Attachment.MediaItem)view.getTag();
                listener.onOpenMediaItem(mediaitem);
            }

            final StreamAdapter this$0;
            final StreamAdapterListener val$listener;

            
            {
                this$0 = StreamAdapter.this;
                listener = streamadapterlistener;
                super();
            }
        }
, bitmap, bitmap1, l);
        listview.setRecyclerListener(this);
    }

    private void fillPostFooterView(View view, com.facebook.katana.view.FacebookPostView.ViewHolder viewholder, FacebookPost facebookpost)
    {
        View view1 = view.findViewById(0x7f0e00fd);
        com.facebook.katana.model.FacebookPost.Comments comments = facebookpost.getComments();
        com.facebook.katana.model.FacebookPost.Likes likes = facebookpost.getLikes();
        if(!facebookpost.canInteractWithFeedback())
            view1.setVisibility(8);
        else
        if(comments.getTotalCount() == 0 && likes.getCount() == 0)
        {
            view1.setVisibility(8);
        } else
        {
            view1.setVisibility(0);
            view1.setTag(facebookpost);
            TextView textview = viewholder.mCommentsCountView;
            int i = comments.getTotalCount();
            TextView textview1;
            int j;
            if(i == 0)
            {
                if(comments.canPost)
                {
                    textview.setText(0x7f0a01b0);
                    textview.setVisibility(0);
                } else
                {
                    textview.setVisibility(4);
                }
            } else
            if(i == 1)
            {
                textview.setVisibility(0);
                textview.setText(mContext.getString(0x7f0a01c0));
            } else
            {
                textview.setVisibility(0);
                Context context = mContext;
                Object aobj[] = new Object[1];
                aobj[0] = Integer.valueOf(i);
                textview.setText(context.getString(0x7f0a01b2, aobj));
            }
            textview1 = viewholder.mLikesCountView;
            j = likes.getCount();
            if(j > 0)
            {
                textview1.setVisibility(0);
                Resources resources = mContext.getResources();
                Object aobj1[] = new Object[1];
                aobj1[0] = Integer.valueOf(j);
                textview1.setText(resources.getQuantityString(0x7f0b0004, j, aobj1));
            } else
            if(likes.canLike)
            {
                textview1.setVisibility(0);
                textview1.setText(0x7f0a01b7);
            } else
            {
                textview1.setVisibility(4);
            }
        }
    }

    public void close()
    {
        mViewHolders.clear();
    }

    public int getCount()
    {
        int i;
        if(mStreamContainer != null)
            i = 1 + mStreamContainer.getPostCount();
        else
            i = 0;
        return i;
    }

    public Object getItem(int i)
    {
        return Integer.valueOf(i);
    }

    public FacebookPost getItemByPosition(int i)
    {
        FacebookPost facebookpost;
        if(mStreamContainer != null)
        {
            if(i >= mStreamContainer.getPostCount())
                facebookpost = null;
            else
                facebookpost = mStreamContainer.getPost(i);
        } else
        {
            facebookpost = null;
        }
        return facebookpost;
    }

    public long getItemId(int i)
    {
        return (long)i;
    }

    public int getItemViewType(int i)
    {
        if(i < mStreamContainer.getPostCount()) goto _L2; else goto _L1
_L1:
        int j;
        if(mStreamContainer != null && !mStreamContainer.isComplete())
            j = 0;
        else
            j = 7;
_L4:
        return j;
_L2:
        switch(mStreamContainer.getPost(i).getPostType())
        {
        default:
            j = 1;
            break;

        case 0: // '\0'
            j = 2;
            break;

        case 2: // '\002'
            j = 3;
            break;

        case 3: // '\003'
            j = 4;
            break;

        case 1: // '\001'
            j = 5;
            break;

        case 4: // '\004'
            j = 6;
            break;
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    public View getView(int i, View view, ViewGroup viewgroup)
    {
        FacebookPost facebookpost = null;
        if(i >= mStreamContainer.getPostCount()) goto _L2; else goto _L1
_L1:
        facebookpost = mStreamContainer.getPost(i);
_L4:
        com.facebook.katana.view.FacebookPostView.ViewHolder viewholder = null;
        View view2;
        if(view == null)
        {
            LayoutInflater layoutinflater = (LayoutInflater)mContext.getSystemService("layout_inflater");
            View view1;
            TextView textview;
            if(facebookpost == null)
            {
                view2 = layoutinflater.inflate(0x7f03007a, null);
            } else
            {
                view2 = FacebookPostView.inflatePostView(facebookpost, layoutinflater);
                viewholder = (com.facebook.katana.view.FacebookPostView.ViewHolder)view2.getTag();
                mViewHolders.add(viewholder);
            }
        } else
        {
            view2 = view;
            viewholder = (com.facebook.katana.view.FacebookPostView.ViewHolder)view2.getTag();
        }
        if(facebookpost == null)
        {
            textview = (TextView)view2.findViewById(0x7f0e0130);
            int j;
            if(mLoading)
                j = 0x7f0a01bb;
            else
                j = 0x7f0a01b8;
            textview.setText(j);
        } else
        {
            FacebookPostView.renderPostView(mContext, facebookpost, viewholder, mExtras);
            fillPostFooterView(view2, viewholder, facebookpost);
        }
        view1 = view2;
_L5:
        return view1;
_L2:
        if(!mStreamContainer.isComplete()) goto _L4; else goto _L3
_L3:
        view1 = new View(mContext);
          goto _L5
    }

    public int getViewTypeCount()
    {
        return 8;
    }

    public boolean isLoadingMore()
    {
        return mLoading;
    }

    public void loadingMore(boolean flag)
    {
        mLoading = flag;
        notifyDataSetChanged();
    }

    public void onMovedToScrapHeap(View view)
    {
        Object obj = view.getTag();
        if(obj instanceof com.facebook.katana.view.FacebookPostView.ViewHolder)
            ((com.facebook.katana.view.FacebookPostView.ViewHolder)obj).detach();
    }

    public void refreshStream()
    {
        notifyDataSetChanged();
    }

    public void refreshStream(FacebookStreamContainer facebookstreamcontainer)
    {
        mStreamContainer = facebookstreamcontainer;
        notifyDataSetChanged();
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
    private boolean mLoading;
    private final Bitmap mPhotoDownloadErrorBitmap;
    private FacebookStreamContainer mStreamContainer;
    private final List mViewHolders = new ArrayList();
}
