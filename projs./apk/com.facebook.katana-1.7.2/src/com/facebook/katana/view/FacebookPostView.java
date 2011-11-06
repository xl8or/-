// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookPostView.java

package com.facebook.katana.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.text.*;
import android.text.method.LinkMovementMethod;
import android.text.style.*;
import android.view.*;
import android.widget.*;
import com.facebook.katana.activity.profilelist.TaggedUsersActivity;
import com.facebook.katana.binding.*;
import com.facebook.katana.model.*;
import com.facebook.katana.service.method.PlacesRemoveTag;
import com.facebook.katana.ui.SaneLinkMovementMethod;
import com.facebook.katana.util.*;
import java.util.*;

public class FacebookPostView
{
    public static class ViewHolder
    {

        public void detach()
        {
            mUserId = -1L;
            mPhotoCount = 0;
        }

        public void setImageUrl(int i, String s)
        {
            mPhotoUrls[i] = s;
        }

        public boolean setPhotoBitmap(Context context, Bitmap bitmap, String s)
        {
            int i = 0;
_L3:
            if(i >= mPhotoCount)
                break MISSING_BLOCK_LABEL_50;
            if(!s.equals(mPhotoUrls[i])) goto _L2; else goto _L1
_L1:
            boolean flag;
            ImageUtils.formatPhotoStreamImageView(context, mImageHolders[i], bitmap);
            flag = true;
_L4:
            return flag;
_L2:
            i++;
              goto _L3
            flag = false;
              goto _L4
        }

        public void setUserImageBitmap(ProfileImage profileimage)
        {
            if(mUserId == profileimage.id)
                mUserImageView.setImageBitmap(profileimage.getBitmap());
        }

        public TextView mAttachmentCaption;
        public TextView mAttachmentDescription;
        public TextView mAttachmentName;
        public TextView mCommentsCountView;
        public LinearLayout mImageHolders[];
        public TextView mLikesCountView;
        public int mPhotoCount;
        public String mPhotoUrls[];
        public TextView mRemoveTag;
        public TextView mTaggedUsers;
        public TextView mTextTextView;
        public TextView mTimeTextView;
        public long mUserId;
        public ImageView mUserImageView;

        public ViewHolder()
        {
            mImageHolders = new LinearLayout[3];
            mPhotoUrls = new String[3];
        }
    }

    public static class Extras
    {

        public final android.view.View.OnClickListener mMediaItemClickListener;
        public final Bitmap mNoAvatarBitmap;
        public final Bitmap mPhotoDownloadingBitmap;
        public final StreamPhotosCache mPhotosCache;
        public final long mStreamId;
        public final android.view.View.OnClickListener mUserImageClickListener;
        public final ProfileImagesCache mUserImagesCache;

        public Extras(ProfileImagesCache profileimagescache, StreamPhotosCache streamphotoscache, android.view.View.OnClickListener onclicklistener, android.view.View.OnClickListener onclicklistener1, Bitmap bitmap, Bitmap bitmap1, long l)
        {
            mUserImagesCache = profileimagescache;
            mPhotosCache = streamphotoscache;
            mUserImageClickListener = onclicklistener;
            mMediaItemClickListener = onclicklistener1;
            mPhotoDownloadingBitmap = bitmap;
            mNoAvatarBitmap = bitmap1;
            mStreamId = l;
        }
    }

    public static final class FacebookPostViewType extends Enum
    {

        public static FacebookPostViewType valueOf(String s)
        {
            return (FacebookPostViewType)Enum.valueOf(com/facebook/katana/view/FacebookPostView$FacebookPostViewType, s);
        }

        public static FacebookPostViewType[] values()
        {
            return (FacebookPostViewType[])$VALUES.clone();
        }

        private static final FacebookPostViewType $VALUES[];
        public static final FacebookPostViewType FEEDBACK_VIEW;
        public static final FacebookPostViewType STREAM_VIEW;

        static 
        {
            STREAM_VIEW = new FacebookPostViewType("STREAM_VIEW", 0);
            FEEDBACK_VIEW = new FacebookPostViewType("FEEDBACK_VIEW", 1);
            FacebookPostViewType afacebookpostviewtype[] = new FacebookPostViewType[2];
            afacebookpostviewtype[0] = STREAM_VIEW;
            afacebookpostviewtype[1] = FEEDBACK_VIEW;
            $VALUES = afacebookpostviewtype;
        }

        private FacebookPostViewType(String s, int i)
        {
            super(s, i);
        }
    }


    public FacebookPostView()
    {
    }

    private static SpannableStringBuilder addTaggedUser(final Context context, SpannableStringBuilder spannablestringbuilder, final FacebookProfile profile, boolean flag)
    {
        int i = spannablestringbuilder.length();
        spannablestringbuilder.append(profile.mDisplayName);
        int j = spannablestringbuilder.length();
        spannablestringbuilder.setSpan(new StyleSpan(1), i, j, 33);
        spannablestringbuilder.setSpan(new ForegroundColorSpan(context.getResources().getColor(0x7f070007)), i, j, 33);
        if(flag)
            spannablestringbuilder.setSpan(new ClickableSpan() {

                public void onClick(View view)
                {
                    ApplicationUtils.OpenProfile(context, profile.mType, profile.mId, profile);
                }

                public void updateDrawState(TextPaint textpaint)
                {
                }

                final Context val$context;
                final FacebookProfile val$profile;

            
            {
                context = context1;
                profile = facebookprofile;
                super();
            }
            }
, i, j, 33);
        return spannablestringbuilder;
    }

    private static Spannable buildStatus(Context context, FacebookPost facebookpost, Extras extras)
    {
        SpannableStringBuilder spannablestringbuilder = new SpannableStringBuilder();
        FacebookProfile facebookprofile = facebookpost.getProfile();
        FacebookProfile facebookprofile1 = facebookpost.getTargetProfile();
        boolean flag;
        if(facebookprofile.mId != extras.mStreamId)
            flag = true;
        else
            flag = false;
        addTaggedUser(context, spannablestringbuilder, facebookprofile, flag);
        if(facebookprofile1 != null && facebookprofile1.mId != extras.mStreamId)
        {
            spannablestringbuilder.append(" > ");
            int i;
            boolean flag1;
            if(facebookprofile1.mId != extras.mStreamId)
                flag1 = true;
            else
                flag1 = false;
            addTaggedUser(context, spannablestringbuilder, facebookprofile1, flag1);
        }
        i = spannablestringbuilder.length();
        if(facebookpost.message != null)
            spannablestringbuilder.append(' ').append(facebookpost.message);
        spannablestringbuilder.setSpan(new StyleSpan(1), 0, i, 33);
        spannablestringbuilder.setSpan(new ForegroundColorSpan(context.getResources().getColor(0x7f070007)), 0, i, 33);
        return spannablestringbuilder;
    }

    private static Spannable buildTaggedText(final Context context, final Set taggedUsers)
    {
        if(!$assertionsDisabled && taggedUsers.size() <= 0)
            throw new AssertionError();
        SpannableStringBuilder spannablestringbuilder = new SpannableStringBuilder();
        if(taggedUsers.size() == 1)
        {
            Object aobj1[] = new Object[1];
            aobj1[0] = "";
            spannablestringbuilder.append(context.getString(0x7f0a01eb, aobj1));
            addTaggedUser(context, spannablestringbuilder, (FacebookProfile)taggedUsers.iterator().next(), true);
        } else
        {
            int i = spannablestringbuilder.length();
            Object aobj[] = new Object[1];
            aobj[0] = Integer.valueOf(taggedUsers.size());
            spannablestringbuilder.append(context.getString(0x7f0a01ec, aobj));
            int j = spannablestringbuilder.length();
            spannablestringbuilder.setSpan(new StyleSpan(1), i, j, 33);
            spannablestringbuilder.setSpan(new ForegroundColorSpan(context.getResources().getColor(0x7f070007)), i, j, 33);
            spannablestringbuilder.setSpan(new ClickableSpan() {

                public void onClick(View view)
                {
                    Intent intent = new Intent(context, com/facebook/katana/activity/profilelist/TaggedUsersActivity);
                    intent.putParcelableArrayListExtra("profiles", new ArrayList(taggedUsers));
                    context.startActivity(intent);
                }

                public void updateDrawState(TextPaint textpaint)
                {
                }

                final Context val$context;
                final Set val$taggedUsers;

            
            {
                context = context1;
                taggedUsers = set;
                super();
            }
            }
, i, j, 33);
        }
        return spannablestringbuilder;
    }

    public static View inflatePostView(FacebookPost facebookpost, LayoutInflater layoutinflater)
    {
        View view;
        ViewHolder viewholder;
        ViewStub viewstub;
        view = layoutinflater.inflate(0x7f03002d, null);
        viewholder = new ViewHolder();
        viewstub = (ViewStub)view.findViewById(0x7f0e008a);
        facebookpost.getPostType();
        JVM INSTR tableswitch 0 4: default 64
    //                   0 70
    //                   1 343
    //                   2 186
    //                   3 273
    //                   4 413;
           goto _L1 _L2 _L3 _L4 _L5 _L6
_L6:
        break MISSING_BLOCK_LABEL_413;
_L1:
        View view1 = null;
_L7:
        return view1;
_L2:
        ((ViewGroup)viewstub.getParent()).removeView(viewstub);
_L8:
        viewholder.mUserImageView = (ImageView)view.findViewById(0x7f0e0088);
        viewholder.mTextTextView = (TextView)view.findViewById(0x7f0e0089);
        setMovementMethod(viewholder.mTextTextView);
        viewholder.mTaggedUsers = (TextView)view.findViewById(0x7f0e008b);
        setMovementMethod(viewholder.mTaggedUsers);
        viewholder.mTimeTextView = (TextView)view.findViewById(0x7f0e008c);
        viewholder.mCommentsCountView = (TextView)view.findViewById(0x7f0e00fe);
        viewholder.mLikesCountView = (TextView)view.findViewById(0x7f0e00ff);
        view.setTag(viewholder);
        view1 = view;
        if(true) goto _L7; else goto _L4
_L4:
        viewstub.setLayoutResource(0x7f030054);
        viewstub.inflate();
        viewholder.mAttachmentName = (TextView)view.findViewById(0x7f0e0031);
        viewholder.mAttachmentCaption = (TextView)view.findViewById(0x7f0e0032);
        viewholder.mImageHolders[0] = (LinearLayout)view.findViewById(0x7f0e00e6);
        viewholder.mImageHolders[1] = (LinearLayout)view.findViewById(0x7f0e00e7);
        viewholder.mImageHolders[2] = (LinearLayout)view.findViewById(0x7f0e00e8);
          goto _L8
_L5:
        viewstub.setLayoutResource(0x7f03008d);
        viewstub.inflate();
        viewholder.mAttachmentName = (TextView)view.findViewById(0x7f0e0031);
        viewholder.mAttachmentCaption = (TextView)view.findViewById(0x7f0e0032);
        viewholder.mAttachmentDescription = (TextView)view.findViewById(0x7f0e00ad);
        viewholder.mImageHolders[0] = (LinearLayout)view.findViewById(0x7f0e0030);
          goto _L8
_L3:
        viewstub.setLayoutResource(0x7f03003a);
        viewstub.inflate();
        viewholder.mAttachmentName = (TextView)view.findViewById(0x7f0e0031);
        viewholder.mAttachmentCaption = (TextView)view.findViewById(0x7f0e0032);
        viewholder.mAttachmentDescription = (TextView)view.findViewById(0x7f0e00ad);
        viewholder.mImageHolders[0] = (LinearLayout)view.findViewById(0x7f0e0030);
          goto _L8
        viewstub.setLayoutResource(0x7f03000f);
        viewstub.inflate();
        viewholder.mAttachmentName = (TextView)view.findViewById(0x7f0e0031);
        viewholder.mAttachmentCaption = (TextView)view.findViewById(0x7f0e0032);
        viewholder.mImageHolders[0] = (LinearLayout)view.findViewById(0x7f0e0030);
        viewholder.mRemoveTag = (TextView)((ViewStub)view.findViewById(0x7f0e008d)).inflate();
          goto _L8
    }

    public static void renderPostView(final Context context, final FacebookPost post, ViewHolder viewholder, Extras extras)
    {
        post.getPostType();
        JVM INSTR tableswitch 0 4: default 40
    //                   0 41
    //                   1 708
    //                   2 252
    //                   3 453
    //                   4 997;
           goto _L1 _L2 _L3 _L4 _L5 _L6
_L1:
        return;
_L2:
        viewholder.mPhotoCount = 0;
_L7:
        viewholder.mUserId = post.actorId;
        ImageView imageview = viewholder.mUserImageView;
        imageview.setTag(post);
        if(extras.mUserImageClickListener != null)
            imageview.setOnClickListener(extras.mUserImageClickListener);
        Bitmap bitmap = extras.mUserImagesCache.get(context, post.actorId, post.getProfile().mImageUrl);
        LinearLayout linearlayout;
        com.facebook.katana.model.FacebookPost.Attachment attachment;
        TextView textview;
        String s;
        TextView textview1;
        String s1;
        Tuple tuple;
        Set set;
        String s2;
        String s3;
        com.facebook.katana.model.FacebookPost.Attachment.MediaItem mediaitem;
        String s5;
        Bitmap bitmap1;
        LinearLayout linearlayout1;
        com.facebook.katana.model.FacebookPost.Attachment attachment1;
        TextView textview2;
        String s6;
        TextView textview3;
        String s7;
        com.facebook.katana.model.FacebookPost.Attachment.MediaItem mediaitem1;
        String s8;
        Bitmap bitmap2;
        com.facebook.katana.model.FacebookPost.Attachment attachment2;
        com.facebook.katana.model.FacebookPost.Attachment.MediaItem mediaitem2;
        LinearLayout linearlayout2;
        String s9;
        TextView textview4;
        String s10;
        TextView textview5;
        String s11;
        Bitmap bitmap3;
        com.facebook.katana.model.FacebookPost.Attachment attachment3;
        TextView textview6;
        String s12;
        TextView textview7;
        String s13;
        int i;
        Iterator iterator;
        int j;
        com.facebook.katana.model.FacebookPost.Attachment.MediaItem mediaitem3;
        if(bitmap == null)
            bitmap = extras.mNoAvatarBitmap;
        imageview.setImageBitmap(bitmap);
        tuple = (Tuple)post.getUserObject();
        if(tuple != null && extras.mStreamId == ((Long)tuple.d0).longValue())
        {
            viewholder.mTextTextView.setText((CharSequence)tuple.d1);
        } else
        {
            Tuple tuple1 = new Tuple(Long.valueOf(extras.mStreamId), buildStatus(context, post, extras));
            viewholder.mTextTextView.setText((CharSequence)tuple1.d1);
            post.setUserObject(tuple1);
        }
        set = post.getTaggedProfiles();
        if(viewholder.mTaggedUsers != null)
            if(set != null && set.size() != 0)
            {
                viewholder.mTaggedUsers.setVisibility(0);
                viewholder.mTaggedUsers.setText(buildTaggedText(context, set));
            } else
            {
                viewholder.mTaggedUsers.setVisibility(8);
            }
        s2 = StringUtils.getTimeAsString(context, com.facebook.katana.util.StringUtils.TimeFormatStyle.STREAM_RELATIVE_STYLE, 1000L * post.createdTime);
        s3 = post.attribution;
        if(s3 == null)
        {
            viewholder.mTimeTextView.setText(s2);
        } else
        {
            Object aobj[] = new Object[2];
            aobj[0] = s2;
            aobj[1] = s3;
            String s4 = context.getString(0x7f0a0270, aobj);
            viewholder.mTimeTextView.setText(s4);
        }
        if(true) goto _L1; else goto _L4
_L4:
        attachment3 = post.getAttachment();
        textview6 = viewholder.mAttachmentName;
        s12 = attachment3.name;
        if(s12 != null && s12.length() > 0)
        {
            textview6.setVisibility(0);
            textview6.setText(s12);
        } else
        {
            textview6.setVisibility(8);
        }
        textview7 = viewholder.mAttachmentCaption;
        s13 = attachment3.caption;
        if(s13 != null && s13.length() > 0)
        {
            textview7.setVisibility(0);
            textview7.setText(s13);
        } else
        {
            textview7.setVisibility(8);
        }
        i = 0;
        for(iterator = attachment3.getMediaItems().iterator(); iterator.hasNext();)
        {
            mediaitem3 = (com.facebook.katana.model.FacebookPost.Attachment.MediaItem)iterator.next();
            if(i < 3)
                setupImageViewAtIndex(context, viewholder, mediaitem3, extras, i);
            i++;
        }

        viewholder.mPhotoCount = Math.min(i, 3);
        j = i;
        while(j < 3) 
        {
            viewholder.mImageHolders[j].setVisibility(8);
            j++;
        }
          goto _L7
_L5:
        attachment2 = post.getAttachment();
        mediaitem2 = (com.facebook.katana.model.FacebookPost.Attachment.MediaItem)attachment2.getMediaItems().get(0);
        linearlayout2 = viewholder.mImageHolders[0];
        linearlayout2.setTag(mediaitem2);
        linearlayout2.setOnClickListener(extras.mMediaItemClickListener);
        s9 = mediaitem2.src;
        if(s9 != null)
        {
            bitmap3 = extras.mPhotosCache.get(context, s9);
            if(bitmap3 == null)
                bitmap3 = extras.mPhotoDownloadingBitmap;
            ImageUtils.formatPhotoStreamImageView(context, linearlayout2, bitmap3);
        } else
        {
            ImageUtils.formatPhotoStreamImageView(context, linearlayout2, null);
        }
        viewholder.setImageUrl(0, s9);
        viewholder.mPhotoCount = 1;
        textview4 = viewholder.mAttachmentName;
        s10 = attachment2.name;
        if(s10 != null && s10.length() > 0)
        {
            textview4.setVisibility(0);
            textview4.setText(s10);
        } else
        {
            textview4.setVisibility(8);
        }
        textview5 = viewholder.mAttachmentCaption;
        s11 = attachment2.caption;
        if(s11 != null && s11.length() > 0)
        {
            textview5.setVisibility(0);
            textview5.setText(s11);
        } else
        {
            textview5.setVisibility(8);
        }
        if(attachment2.description != null)
        {
            viewholder.mAttachmentDescription.setVisibility(0);
            viewholder.mAttachmentDescription.setText(attachment2.description);
        } else
        {
            viewholder.mAttachmentDescription.setVisibility(8);
        }
          goto _L7
_L3:
        linearlayout1 = viewholder.mImageHolders[0];
        attachment1 = post.getAttachment();
        if(attachment1.getMediaItems().size() > 0)
        {
            mediaitem1 = (com.facebook.katana.model.FacebookPost.Attachment.MediaItem)attachment1.getMediaItems().get(0);
            linearlayout1.setTag(mediaitem1);
            s8 = mediaitem1.src;
            if(s8 != null)
            {
                bitmap2 = extras.mPhotosCache.get(context, s8);
                if(bitmap2 == null)
                    bitmap2 = extras.mPhotoDownloadingBitmap;
                ImageUtils.formatPhotoStreamImageView(context, linearlayout1, bitmap2);
            } else
            {
                ImageUtils.formatPhotoStreamImageView(context, linearlayout1, null);
            }
            linearlayout1.setOnClickListener(extras.mMediaItemClickListener);
            linearlayout1.setVisibility(0);
            viewholder.setImageUrl(0, s8);
            viewholder.mPhotoCount = 1;
        } else
        {
            linearlayout1.setVisibility(8);
            viewholder.mPhotoCount = 0;
        }
        textview2 = viewholder.mAttachmentName;
        s6 = attachment1.name;
        if(s6 != null && s6.length() > 0)
        {
            textview2.setVisibility(0);
            textview2.setText(s6);
        } else
        {
            textview2.setVisibility(8);
        }
        textview3 = viewholder.mAttachmentCaption;
        s7 = attachment1.caption;
        if(s7 != null && s7.length() > 0)
        {
            textview3.setVisibility(0);
            textview3.setText(s7);
        } else
        {
            textview3.setVisibility(8);
        }
        if(attachment1.description != null)
        {
            viewholder.mAttachmentDescription.setVisibility(0);
            viewholder.mAttachmentDescription.setText(attachment1.description);
        } else
        {
            viewholder.mAttachmentDescription.setVisibility(8);
        }
          goto _L7
_L6:
        linearlayout = viewholder.mImageHolders[0];
        attachment = post.getAttachment();
        if(attachment.getMediaItems().size() > 0)
        {
            mediaitem = (com.facebook.katana.model.FacebookPost.Attachment.MediaItem)attachment.getMediaItems().get(0);
            linearlayout.setTag(mediaitem);
            s5 = mediaitem.src;
            if(s5 != null)
            {
                bitmap1 = extras.mPhotosCache.get(context, s5);
                if(bitmap1 == null)
                    bitmap1 = extras.mPhotoDownloadingBitmap;
                ImageUtils.formatPhotoStreamImageView(context, linearlayout, bitmap1);
            } else
            {
                ImageUtils.formatPhotoStreamImageView(context, linearlayout, null);
            }
            linearlayout.setOnClickListener(extras.mMediaItemClickListener);
            linearlayout.setVisibility(0);
            viewholder.setImageUrl(0, s5);
            viewholder.mPhotoCount = 1;
        } else
        {
            linearlayout.setVisibility(8);
            viewholder.mPhotoCount = 0;
        }
        textview = viewholder.mAttachmentName;
        s = attachment.name;
        if(s != null && s.length() > 0)
        {
            textview.setVisibility(0);
            textview.setText(s);
        } else
        {
            textview.setVisibility(8);
        }
        textview1 = viewholder.mAttachmentCaption;
        s1 = attachment.caption;
        if(s1 != null && s1.length() > 0)
        {
            textview1.setVisibility(0);
            textview1.setText(s1);
        } else
        {
            textview1.setVisibility(8);
        }
        if(post.mTaggedIds != null && post.mTaggedIds.contains(Long.valueOf(AppSession.getActiveSession(context, false).getSessionInfo().userId)))
        {
            viewholder.mRemoveTag.setVisibility(0);
            viewholder.mRemoveTag.setOnClickListener(new android.view.View.OnClickListener() {

                public void onClick(View view)
                {
                    PlacesRemoveTag.RemoveTag(AppSession.getActiveSession(context, false), context, post, AppSession.getActiveSession(context, false).getSessionInfo().userId);
                    view.setVisibility(4);
                }

                final Context val$context;
                final FacebookPost val$post;

            
            {
                context = context1;
                post = facebookpost;
                super();
            }
            }
);
        } else
        {
            viewholder.mRemoveTag.setVisibility(4);
        }
          goto _L7
    }

    private static void setMovementMethod(TextView textview)
    {
        android.text.method.MovementMethod movementmethod = textview.getMovementMethod();
        if((movementmethod == null || !(movementmethod instanceof LinkMovementMethod)) && textview.getLinksClickable())
        {
            textview.setMovementMethod(SaneLinkMovementMethod.getInstance());
            textview.setFocusable(false);
            textview.setClickable(false);
            textview.setLongClickable(false);
        }
    }

    private static void setupImageViewAtIndex(Context context, ViewHolder viewholder, com.facebook.katana.model.FacebookPost.Attachment.MediaItem mediaitem, Extras extras, int i)
    {
        LinearLayout linearlayout = viewholder.mImageHolders[i];
        linearlayout.setOnClickListener(extras.mMediaItemClickListener);
        linearlayout.setTag(mediaitem);
        String s = mediaitem.src;
        if(s != null)
        {
            Bitmap bitmap = extras.mPhotosCache.get(context, s);
            Bitmap bitmap1;
            if(bitmap != null)
                bitmap1 = bitmap;
            else
                bitmap1 = extras.mPhotoDownloadingBitmap;
            ImageUtils.formatPhotoStreamImageView(context, linearlayout, bitmap1);
        } else
        {
            ImageUtils.formatPhotoStreamImageView(context, linearlayout, null);
        }
        viewholder.setImageUrl(i, s);
    }

    static final boolean $assertionsDisabled = false;
    private static final int MAX_PHOTOS = 3;

    static 
    {
        boolean flag;
        if(!com/facebook/katana/view/FacebookPostView.desiredAssertionStatus())
            flag = true;
        else
            flag = false;
        $assertionsDisabled = flag;
    }
}
