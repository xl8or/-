// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TaggableView.java

package com.facebook.katana.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.*;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.facebook.katana.model.FacebookPhotoTag;
import java.util.*;

// Referenced classes of package com.facebook.katana.ui:
//            TagView, TagSuggestionView

public class TaggableView extends FrameLayout
    implements android.view.GestureDetector.OnGestureListener
{
    public static interface TaggableViewListener
    {

        public abstract void onClicked(float f, float f1);
    }


    public TaggableView(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        mContext = context;
        ((LayoutInflater)context.getSystemService("layout_inflater")).inflate(0x7f030084, this);
        mGestureDetector = new GestureDetector(this);
        mTags = new HashMap();
        mSuggestions = new ArrayList();
        mTextId = -1;
        getViewTreeObserver().addOnGlobalLayoutListener(new android.view.ViewTreeObserver.OnGlobalLayoutListener() {

            public void onGlobalLayout()
            {
                updateTagPosition();
            }

            final TaggableView this$0;

            
            {
                this$0 = TaggableView.this;
                super();
            }
        }
);
    }

    private int getTagPositionX(TagView tagview)
    {
        ImageView imageview = (ImageView)findViewById(0x7f0e00e5);
        int i = (int)(tagview.x * (float)imageview.getWidth());
        int j = tagview.getFullWidth();
        int k = i - j / 2;
        if(k + j > mLastWidth)
            k = mLastWidth - j;
        if(k < 0)
            k = 0;
        return k;
    }

    private int getTagPositionY(TagView tagview)
    {
        ImageView imageview = (ImageView)findViewById(0x7f0e00e5);
        int i = (int)(tagview.y * (float)imageview.getHeight());
        int j = tagview.getFullHeight();
        if(i + j > mLastHeight)
            i = mLastHeight - j;
        return i;
    }

    private int getTagSuggestionPositionX(TagSuggestionView tagsuggestionview)
    {
        ImageView imageview = (ImageView)findViewById(0x7f0e00e5);
        int i = (int)(tagsuggestionview.getX() * (float)imageview.getWidth());
        int j = tagsuggestionview.getFullWidth();
        int k = i - j / 2;
        if(k + j > mLastWidth)
            k = mLastWidth - j;
        return k;
    }

    private int getTagSuggestionPositionY(TagSuggestionView tagsuggestionview)
    {
        ImageView imageview = (ImageView)findViewById(0x7f0e00e5);
        int i = (int)(tagsuggestionview.getY() * (float)imageview.getHeight());
        int j = tagsuggestionview.getFullHeight();
        int k = i - tagsuggestionview.getFaceBoxHeight() / 3;
        if(k + j > mLastHeight)
            k = mLastHeight - j;
        return k;
    }

    public TagSuggestionView addSuggestion(float f, float f1, float f2)
    {
        final TagSuggestionView tagSugg = (TagSuggestionView)((LayoutInflater)mContext.getSystemService("layout_inflater")).inflate(0x7f030081, null);
        tagSugg.setImageSize(mLastWidth);
        tagSugg.setEyeDistance(f2);
        tagSugg.setX(f);
        tagSugg.setY(f1);
        android.widget.FrameLayout.LayoutParams layoutparams = new android.widget.FrameLayout.LayoutParams(-2, -2, 0);
        layoutparams.setMargins(getTagSuggestionPositionX(tagSugg), getTagSuggestionPositionY(tagSugg), 0, 0);
        addView(tagSugg, layoutparams);
        mSuggestions.add(tagSugg);
        tagSugg.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                deleteSuggestion(tagSugg);
                mListener.onClicked(tagSugg.getX(), tagSugg.getY());
            }

            final TaggableView this$0;
            final TagSuggestionView val$tagSugg;

            
            {
                this$0 = TaggableView.this;
                tagSugg = tagsuggestionview;
                super();
            }
        }
);
        return tagSugg;
    }

    public TagView addTag(long l, float f, float f1, String s)
    {
        TagView tagview;
        if(l == -1L || !mTags.containsKey(Long.valueOf(l)))
        {
            tagview = (TagView)((LayoutInflater)mContext.getSystemService("layout_inflater")).inflate(0x7f030082, null);
            tagview.x = f;
            tagview.y = f1;
            tagview.userId = l;
            tagview.setText(s);
            if(l == -1L)
                tagview.userId = getNextTextId();
            createTag(tagview);
            mTags.put(Long.valueOf(tagview.userId), tagview);
        } else
        {
            tagview = (TagView)mTags.get(Long.valueOf(l));
            tagview.x = f;
            tagview.y = f1;
            updateTag(tagview);
        }
        return tagview;
    }

    public void createTag(TagView tagview)
    {
        android.widget.FrameLayout.LayoutParams layoutparams = new android.widget.FrameLayout.LayoutParams(-2, -2, 0);
        layoutparams.setMargins(getTagPositionX(tagview), getTagPositionY(tagview), 0, 0);
        addView(tagview, layoutparams);
    }

    public void deleteSuggestion(TagSuggestionView tagsuggestionview)
    {
        if(mSuggestions.contains(tagsuggestionview))
        {
            mSuggestions.remove(tagsuggestionview);
            removeView(tagsuggestionview);
        }
    }

    public void deleteTag(long l)
    {
        if(mTags.containsKey(Long.valueOf(l)))
        {
            TagView tagview = (TagView)mTags.get(Long.valueOf(l));
            mTags.remove(Long.valueOf(l));
            removeView(tagview);
        }
    }

    public int getNextTextId()
    {
        int i = mTextId;
        mTextId = i - 1;
        return i;
    }

    public List getTags()
    {
        ArrayList arraylist = new ArrayList();
        Iterator iterator = mTags.keySet().iterator();
        while(iterator.hasNext()) 
        {
            long l = ((Long)iterator.next()).longValue();
            TagView tagview = (TagView)mTags.get(Long.valueOf(l));
            FacebookPhotoTag facebookphototag;
            if(tagview.userId <= -1L)
                facebookphototag = new FacebookPhotoTag("", tagview.getText().toString(), -1L, 100F * tagview.x, 100F * tagview.y, -1L);
            else
                facebookphototag = new FacebookPhotoTag("", null, tagview.userId, 100F * tagview.x, 100F * tagview.y, -1L);
            arraylist.add(facebookphototag);
        }
        return arraylist;
    }

    public boolean onDown(MotionEvent motionevent)
    {
        return false;
    }

    public boolean onFling(MotionEvent motionevent, MotionEvent motionevent1, float f, float f1)
    {
        return false;
    }

    public void onLongPress(MotionEvent motionevent)
    {
    }

    public boolean onScroll(MotionEvent motionevent, MotionEvent motionevent1, float f, float f1)
    {
        return false;
    }

    public void onShowPress(MotionEvent motionevent)
    {
    }

    public boolean onSingleTapUp(MotionEvent motionevent)
    {
        ImageView imageview = (ImageView)findViewById(0x7f0e00e5);
        float f = motionevent.getX() / (float)imageview.getWidth();
        float f1 = motionevent.getY() / (float)imageview.getHeight();
        mListener.onClicked(f, f1);
        return false;
    }

    public boolean onTouchEvent(MotionEvent motionevent)
    {
        mGestureDetector.onTouchEvent(motionevent);
        return true;
    }

    public void setImage(Bitmap bitmap)
    {
        ImageView imageview = (ImageView)findViewById(0x7f0e00e5);
        imageview.setImageBitmap(bitmap);
        findViewById(0x7f0e00e5).setVisibility(0);
        mLastWidth = imageview.getWidth();
        mLastHeight = imageview.getHeight();
    }

    public void setListener(TaggableViewListener taggableviewlistener)
    {
        mListener = taggableviewlistener;
    }

    public void updateTag(TagView tagview)
    {
        android.widget.FrameLayout.LayoutParams layoutparams = (android.widget.FrameLayout.LayoutParams)tagview.getLayoutParams();
        layoutparams.setMargins(getTagPositionX(tagview), getTagPositionY(tagview), 0, 0);
        tagview.setLayoutParams(layoutparams);
    }

    public void updateTagPosition()
    {
        ImageView imageview = (ImageView)findViewById(0x7f0e00e5);
        int i = imageview.getWidth();
        int j = imageview.getHeight();
        if(mLastWidth != i)
        {
            mLastWidth = i;
            mLastHeight = j;
            long l;
            for(Iterator iterator = mTags.keySet().iterator(); iterator.hasNext(); updateTag((TagView)mTags.get(Long.valueOf(l))))
                l = ((Long)iterator.next()).longValue();

            for(Iterator iterator1 = mSuggestions.iterator(); iterator1.hasNext(); updateTagSuggestion((TagSuggestionView)iterator1.next()));
        }
    }

    public void updateTagSuggestion(TagSuggestionView tagsuggestionview)
    {
        android.widget.FrameLayout.LayoutParams layoutparams = (android.widget.FrameLayout.LayoutParams)tagsuggestionview.getLayoutParams();
        tagsuggestionview.setImageSize(mLastWidth);
        layoutparams.setMargins(getTagSuggestionPositionX(tagsuggestionview), getTagSuggestionPositionY(tagsuggestionview), 0, 0);
        tagsuggestionview.setLayoutParams(layoutparams);
    }

    private Context mContext;
    private GestureDetector mGestureDetector;
    private int mLastHeight;
    private int mLastWidth;
    private TaggableViewListener mListener;
    private ArrayList mSuggestions;
    private HashMap mTags;
    private int mTextId;

}
