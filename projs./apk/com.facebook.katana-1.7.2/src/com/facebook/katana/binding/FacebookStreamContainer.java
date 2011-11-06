// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookStreamContainer.java

package com.facebook.katana.binding;

import com.facebook.katana.model.FacebookPost;
import java.lang.ref.WeakReference;
import java.util.*;

public class FacebookStreamContainer
{
    static class FacebookPostHandle
    {

        public boolean equals(Object obj)
        {
            boolean flag;
            if((obj instanceof FacebookPostHandle) && mPostId.equals(((FacebookPostHandle)obj).mPostId))
                flag = true;
            else
                flag = false;
            return flag;
        }

        public int hashCode()
        {
            return mPostId.hashCode();
        }

        public static Comparator timeComparator = new Comparator() {

            public int compare(FacebookPostHandle facebookposthandle, FacebookPostHandle facebookposthandle1)
            {
                byte byte0;
                if(facebookposthandle.mCreatedTime > facebookposthandle1.mCreatedTime)
                    byte0 = -1;
                else
                if(facebookposthandle.mCreatedTime == facebookposthandle1.mCreatedTime)
                    byte0 = 0;
                else
                    byte0 = 1;
                return byte0;
            }

            public volatile int compare(Object obj, Object obj1)
            {
                return compare((FacebookPostHandle)obj, (FacebookPostHandle)obj1);
            }

        }
;
        long mCreatedTime;
        boolean mIsCanonical;
        final String mPostId;


        FacebookPostHandle(String s)
        {
            mPostId = s;
        }
    }

    static class FacebookPostCache
    {

        static FacebookPostHandle add(FacebookPost facebookpost)
        {
            FacebookPostHandle facebookposthandle = getCanonicalHandle(facebookpost.postId);
            facebookposthandle.mCreatedTime = facebookpost.createdTime;
            postCache.put(facebookposthandle, facebookpost);
            return facebookposthandle;
        }

        static FacebookPost get(FacebookPostHandle facebookposthandle)
        {
            if(!$assertionsDisabled && !facebookposthandle.mIsCanonical)
                throw new AssertionError();
            else
                return (FacebookPost)postCache.get(facebookposthandle);
        }

        static FacebookPost get(String s)
        {
            FacebookPostHandle facebookposthandle = getCanonicalHandle(s);
            return (FacebookPost)postCache.get(facebookposthandle);
        }

        /**
         * @deprecated Method getCanonicalHandle is deprecated
         */

        static FacebookPostHandle getCanonicalHandle(String s)
        {
            com/facebook/katana/binding/FacebookStreamContainer$FacebookPostCache;
            JVM INSTR monitorenter ;
            FacebookPostHandle facebookposthandle1;
label0:
            {
                FacebookPostHandle facebookposthandle = new FacebookPostHandle(s);
                WeakReference weakreference = (WeakReference)canonicalHandleMap.get(facebookposthandle);
                if(weakreference != null)
                {
                    facebookposthandle1 = (FacebookPostHandle)weakreference.get();
                    if(facebookposthandle1 != null)
                        break label0;
                }
                facebookposthandle1 = facebookposthandle;
                facebookposthandle1.mIsCanonical = true;
                WeakReference weakreference1 = new WeakReference(facebookposthandle1);
                canonicalHandleMap.put(facebookposthandle1, weakreference1);
            }
            if(!$assertionsDisabled && facebookposthandle1 == null)
                throw new AssertionError();
            break MISSING_BLOCK_LABEL_101;
            Exception exception;
            exception;
            com/facebook/katana/binding/FacebookStreamContainer$FacebookPostCache;
            JVM INSTR monitorexit ;
            throw exception;
            if(!$assertionsDisabled && !facebookposthandle1.mIsCanonical)
                throw new AssertionError();
            com/facebook/katana/binding/FacebookStreamContainer$FacebookPostCache;
            JVM INSTR monitorexit ;
            return facebookposthandle1;
        }

        static FacebookPostHandle remove(String s)
        {
            FacebookPostHandle facebookposthandle = getCanonicalHandle(s);
            postCache.remove(facebookposthandle);
            return facebookposthandle;
        }

        static final boolean $assertionsDisabled;
        private static final Map canonicalHandleMap = new WeakHashMap();
        private static final Map postCache = new WeakHashMap();

        static 
        {
            boolean flag;
            if(!com/facebook/katana/binding/FacebookStreamContainer.desiredAssertionStatus())
                flag = true;
            else
                flag = false;
            $assertionsDisabled = flag;
        }

        FacebookPostCache()
        {
        }
    }


    public FacebookStreamContainer()
    {
        managedContainers.add(this);
    }

    public static void deregister(FacebookStreamContainer facebookstreamcontainer)
    {
        managedContainers.remove(facebookstreamcontainer);
    }

    public static FacebookPost get(String s)
    {
        return FacebookPostCache.get(s);
    }

    public static void remove(String s)
    {
        FacebookPostHandle facebookposthandle = FacebookPostCache.remove(s);
        for(Iterator iterator = managedContainers.iterator(); iterator.hasNext(); ((FacebookStreamContainer)iterator.next()).mHandles.remove(facebookposthandle));
    }

    public void addPosts(List list, int i, int j)
    {
        int k;
        Iterator iterator = list.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            if(((FacebookPost)iterator.next()).getPostType() == -1)
                iterator.remove();
        } while(true);
        k = 0;
        if(j != 0) goto _L2; else goto _L1
_L1:
        mHandles.clear();
        mLastStreamGetAllTime = System.currentTimeMillis();
_L4:
        FacebookPostHandle facebookposthandle;
        for(Iterator iterator3 = list.iterator(); iterator3.hasNext(); mHandles.add(facebookposthandle))
            facebookposthandle = FacebookPostCache.add((FacebookPost)iterator3.next());

        break MISSING_BLOCK_LABEL_213;
_L2:
        k = mHandles.size();
        Iterator iterator1 = mHandles.iterator();
label0:
        do
        {
            if(!iterator1.hasNext())
                break;
            String s = ((FacebookPostHandle)iterator1.next()).mPostId;
            Iterator iterator2 = list.iterator();
            do
                if(!iterator2.hasNext())
                    continue label0;
            while(!((FacebookPost)iterator2.next()).postId.equals(s));
            iterator1.remove();
        } while(true);
        if(true) goto _L4; else goto _L3
_L3:
        Collections.sort(mHandles, FacebookPostHandle.timeComparator);
        boolean flag;
        if(k == mHandles.size())
            flag = true;
        else
            flag = false;
        mComplete = flag;
        return;
    }

    public void clear()
    {
        mHandles.clear();
        mComplete = false;
    }

    public long getLastGetAllTime()
    {
        return mLastStreamGetAllTime;
    }

    public FacebookPost getPost(int i)
    {
        return FacebookPostCache.get((FacebookPostHandle)mHandles.get(i));
    }

    public FacebookPost getPost(String s)
    {
        Iterator iterator = mHandles.iterator();
_L4:
        if(!iterator.hasNext()) goto _L2; else goto _L1
_L1:
        FacebookPostHandle facebookposthandle = (FacebookPostHandle)iterator.next();
        if(!facebookposthandle.mPostId.equals(s)) goto _L4; else goto _L3
_L3:
        FacebookPost facebookpost = FacebookPostCache.get(facebookposthandle);
_L6:
        return facebookpost;
_L2:
        facebookpost = null;
        if(true) goto _L6; else goto _L5
_L5:
    }

    public int getPostCount()
    {
        return mHandles.size();
    }

    public List getPosts()
    {
        ArrayList arraylist = new ArrayList();
        for(Iterator iterator = mHandles.iterator(); iterator.hasNext(); arraylist.add(FacebookPostCache.get((FacebookPostHandle)iterator.next())));
        return arraylist;
    }

    public void insertFirst(FacebookPost facebookpost)
    {
        FacebookPostHandle facebookposthandle = FacebookPostCache.add(facebookpost);
        mHandles.add(0, facebookposthandle);
    }

    public boolean isComplete()
    {
        return mComplete;
    }

    public static final int PAGE_SIZE = 20;
    private static final Set managedContainers = new HashSet();
    private boolean mComplete;
    private final List mHandles = new ArrayList();
    private long mLastStreamGetAllTime;

}
