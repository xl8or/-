// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookPost.java

package com.facebook.katana.model;

import com.facebook.katana.util.jsonmirror.*;
import java.io.IOException;
import java.util.*;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

// Referenced classes of package com.facebook.katana.model:
//            FacebookApiException, FacebookProfile, FacebookCheckinDetails, FacebookPhoto, 
//            FacebookVideo

public class FacebookPost extends JMCachingDictDestination
{
    public static class Likes extends JMCachingDictDestination
    {

        public boolean doesUserLike()
        {
            return mUserLikes;
        }

        public int getCount()
        {
            return mCount;
        }

        public long getFriendUserId()
        {
            return mFriendUserId;
        }

        public long getSampleUserId()
        {
            return mSampleUserId;
        }

        protected void postprocess()
        {
            if(mSampleUsers != null && mSampleUsers.size() != 0)
                mSampleUserId = ((Long)mSampleUsers.get(0)).longValue();
            mSampleUsers = null;
            if(mFriendUsers != null && mFriendUsers.size() != 0)
                mFriendUserId = ((Long)mFriendUsers.get(0)).longValue();
            mFriendUsers = null;
        }

        public void setUserLikes(boolean flag)
        {
            if(mUserLikes != flag)
            {
                if(flag)
                    mCount = 1 + mCount;
                else
                    mCount = mCount - 1;
                mUserLikes = flag;
            }
        }

        public final boolean canLike;
        private int mCount;
        private long mFriendUserId;
        private List mFriendUsers;
        private long mSampleUserId;
        private List mSampleUsers;
        private boolean mUserLikes;

        private Likes()
        {
            mUserLikes = false;
            canLike = true;
            mCount = 0;
            mSampleUserId = -1L;
            mFriendUserId = -1L;
        }

    }

    public static class Comment extends JMCachingDictDestination
    {

        public static Comment parseJson(JsonParser jsonparser)
            throws JsonParseException, IOException, JMException
        {
            return (Comment)JMParser.parseObjectJson(jsonparser, com/facebook/katana/model/FacebookPost$Comment);
        }

        public FacebookProfile getProfile()
        {
            return mProfile;
        }

        public void setProfile(FacebookProfile facebookprofile)
        {
            mProfile = facebookprofile;
        }

        public final long fromId;
        public final String id;
        private FacebookProfile mProfile;
        public final String text;
        public final long time;

        private Comment()
        {
            id = null;
            fromId = -1L;
            time = -1L;
            text = null;
        }

        public Comment(String s, long l, long l1, String s1)
        {
            id = s;
            fromId = l;
            time = l1;
            text = s1;
        }
    }

    public static class Comments extends JMCachingDictDestination
    {

        public void addComment(Comment comment)
        {
            mComments.add(comment);
            mTotalCount = 1 + mTotalCount;
        }

        public void deleteComment(String s)
        {
            Iterator iterator = mComments.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                Comment comment = (Comment)iterator.next();
                if(!comment.id.equals(s))
                    continue;
                mComments.remove(comment);
                if(mTotalCount > 0)
                    mTotalCount = mTotalCount - 1;
                break;
            } while(true);
        }

        public List getComments()
        {
            return mComments;
        }

        public int getCount()
        {
            return mComments.size();
        }

        public int getTotalCount()
        {
            return mTotalCount;
        }

        protected void postprocess()
            throws JMException
        {
            if(mComments != null)
                setList("mComments", new ArrayList(mComments));
        }

        public void updateComments(List list)
        {
            mComments.clear();
            mComments.addAll(list);
            mTotalCount = mComments.size();
        }

        public final boolean canPost = true;
        public final boolean canRemove = true;
        private final List mComments = new ArrayList();
        private int mTotalCount;

        public Comments()
        {
            mTotalCount = 0;
        }
    }

    public static class Attachment extends JMCachingDictDestination
    {
        public static class MediaItem extends JMCachingDictDestination
        {

            public FacebookPhoto getPhoto()
            {
                return mPhoto;
            }

            public FacebookVideo getVideo()
            {
                return mVideo;
            }

            public static final String TYPE_FLASH = "flash";
            public static final String TYPE_LINK = "link";
            public static final String TYPE_MP3 = "mp3";
            public static final String TYPE_PHOTO = "photo";
            public static final String TYPE_VIDEO = "video";
            public final String href = null;
            private final FacebookPhoto mPhoto = null;
            private final FacebookVideo mVideo = null;
            public final String src = null;
            public final String type = null;

            private MediaItem()
            {
            }
        }


        public List getMediaItems()
        {
            return mMediaItems;
        }

        public final String caption;
        public final String description;
        public final String href;
        public final FacebookCheckinDetails mCheckinDetails;
        private final List mMediaItems;
        public final String name;

        private Attachment()
        {
            name = null;
            caption = null;
            description = null;
            href = null;
            mMediaItems = null;
            mCheckinDetails = null;
        }

        public Attachment(String s, FacebookCheckinDetails facebookcheckindetails)
        {
            name = s;
            caption = null;
            description = null;
            href = null;
            mMediaItems = Collections.emptyList();
            mCheckinDetails = facebookcheckindetails;
        }
    }


    private FacebookPost()
    {
        postId = null;
        appId = -1L;
        actorId = -1L;
        targetId = -1L;
        message = null;
        mAttachment = null;
        mLikes = null;
        mComments = null;
        mTaggedIds = null;
        createdTime = -1L;
        attribution = null;
    }

    public FacebookPost(String s, long l, long l1, long l2, 
            String s1, Attachment attachment, String s2)
    {
        postId = s;
        appId = l;
        actorId = l1;
        targetId = l2;
        message = s1;
        mAttachment = attachment;
        mLikes = new Likes();
        mComments = new Comments();
        mTaggedIds = null;
        createdTime = System.currentTimeMillis() / 1000L;
        attribution = s2;
        postprocess();
    }

    public FacebookPost(String s, long l, long l1, long l2, 
            String s1, Attachment attachment, Set set, Set set1, String s2)
    {
        postId = s;
        appId = l;
        actorId = l1;
        targetId = l2;
        message = s1;
        mAttachment = attachment;
        mLikes = new Likes();
        mComments = new Comments();
        if(set != null)
        {
            ArrayList arraylist = new ArrayList(set);
            mTaggedIds = arraylist;
        } else
        {
            mTaggedIds = null;
        }
        mTaggedProfiles = set1;
        createdTime = System.currentTimeMillis() / 1000L;
        attribution = s2;
        postprocess();
    }

    public static FacebookPost parseJson(JsonParser jsonparser)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        return (FacebookPost)JMParser.parseObjectJson(jsonparser, com/facebook/katana/model/FacebookPost);
    }

    public void addComment(Comment comment)
    {
        mComments.addComment(comment);
    }

    public void buildTaggedProfiles(Map map)
    {
        HashSet hashset = new HashSet();
        Iterator iterator = mTaggedIds.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            FacebookProfile facebookprofile = (FacebookProfile)map.get(Long.valueOf(((Long)iterator.next()).longValue()));
            if(facebookprofile != null)
                hashset.add(facebookprofile);
        } while(true);
        mTaggedProfiles = Collections.unmodifiableSet(hashset);
    }

    public boolean canInteractWithFeedback()
    {
        boolean flag;
        if(getComments().canPost || getLikes().canLike || getComments().getTotalCount() > 0)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public void deleteComment(String s)
    {
        mComments.deleteComment(s);
    }

    public Attachment getAttachment()
    {
        return mAttachment;
    }

    public Comments getComments()
    {
        return mComments;
    }

    public Likes getLikes()
    {
        return mLikes;
    }

    public int getPostType()
    {
        return mPostType;
    }

    public FacebookProfile getProfile()
    {
        return mProfile;
    }

    public Set getTaggedProfiles()
    {
        return mTaggedProfiles;
    }

    public FacebookProfile getTargetProfile()
    {
        return mTargetProfile;
    }

    public Object getUserObject()
    {
        return mUserObject;
    }

    protected void postprocess()
    {
        if(mAttachment != null)
        {
            List list = mAttachment.getMediaItems();
            if(mAttachment.mCheckinDetails != null)
                mPostType = 4;
            else
            if(list != null && list.size() > 0)
            {
                String s = ((Attachment.MediaItem)list.get(0)).type;
                if(s.equals("link"))
                {
                    String s1 = ((Attachment.MediaItem)list.get(0)).href;
                    if(s1 == null)
                        mPostType = -1;
                    else
                    if(s1.contains("/apps.facebook.com"))
                        mPostType = -1;
                    else
                    if(s1.contains(".applatform.com/"))
                        mPostType = -1;
                    else
                        mPostType = 1;
                } else
                if(s.equals("photo"))
                    mPostType = 2;
                else
                if(s.equals("video"))
                    mPostType = 3;
                else
                    mPostType = -1;
            } else
            if(mAttachment.href != null)
            {
                if(mAttachment.name != null || mAttachment.caption != null || mAttachment.description != null)
                    mPostType = 1;
                else
                if(message != null)
                    mPostType = 0;
                else
                    mPostType = -1;
            } else
            if(message != null)
                mPostType = 0;
            else
                mPostType = -1;
        } else
        if(message != null)
            mPostType = 0;
        else
            mPostType = -1;
    }

    public void setProfile(FacebookProfile facebookprofile)
    {
        mProfile = facebookprofile;
    }

    public void setTargetProfile(FacebookProfile facebookprofile)
    {
        mTargetProfile = facebookprofile;
    }

    public void setUserLikes(boolean flag)
    {
        mLikes.setUserLikes(flag);
    }

    public void setUserObject(Object obj)
    {
        mUserObject = obj;
    }

    public void updateComments(List list)
    {
        mComments.updateComments(list);
    }

    public static final int TYPE_CHECKIN_POST = 4;
    public static final int TYPE_LINK_ATTACHMENT_POST = 1;
    public static final int TYPE_PHOTO_ATTACHMENT_POST = 2;
    public static final int TYPE_STATUS_POST = 0;
    public static final int TYPE_UNSUPPORTED = -1;
    public static final int TYPE_VIDEO_ATTACHMENT_POST = 3;
    public static Comparator timeComparator = new Comparator() {

        public int compare(FacebookPost facebookpost, FacebookPost facebookpost1)
        {
            byte byte0;
            if(facebookpost.createdTime > facebookpost1.createdTime)
                byte0 = -1;
            else
            if(facebookpost.createdTime == facebookpost1.createdTime)
                byte0 = 0;
            else
                byte0 = 1;
            return byte0;
        }

        public volatile int compare(Object obj, Object obj1)
        {
            return compare((FacebookPost)obj, (FacebookPost)obj1);
        }

    }
;
    public final long actorId;
    public final long appId;
    public final String attribution;
    public final long createdTime;
    private final Attachment mAttachment;
    private final Comments mComments;
    private final Likes mLikes;
    private int mPostType;
    private FacebookProfile mProfile;
    public final List mTaggedIds;
    private Set mTaggedProfiles;
    private FacebookProfile mTargetProfile;
    private Object mUserObject;
    public final String message;
    public final String postId;
    public final long targetId;

}
