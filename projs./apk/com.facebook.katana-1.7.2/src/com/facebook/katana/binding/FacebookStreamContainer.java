package com.facebook.katana.binding;

import com.facebook.katana.model.FacebookPost;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public class FacebookStreamContainer {

   public static final int PAGE_SIZE = 20;
   private static final Set<FacebookStreamContainer> managedContainers = new HashSet();
   private boolean mComplete;
   private final List<FacebookStreamContainer.FacebookPostHandle> mHandles;
   private long mLastStreamGetAllTime;


   public FacebookStreamContainer() {
      ArrayList var1 = new ArrayList();
      this.mHandles = var1;
      boolean var2 = managedContainers.add(this);
   }

   public static void deregister(FacebookStreamContainer var0) {
      boolean var1 = managedContainers.remove(var0);
   }

   public static FacebookPost get(String var0) {
      return FacebookStreamContainer.FacebookPostCache.get(var0);
   }

   public static void remove(String var0) {
      FacebookStreamContainer.FacebookPostHandle var1 = FacebookStreamContainer.FacebookPostCache.remove(var0);
      Iterator var2 = managedContainers.iterator();

      while(var2.hasNext()) {
         ((FacebookStreamContainer)var2.next()).mHandles.remove(var1);
      }

   }

   public void addPosts(List<FacebookPost> var1, int var2, int var3) {
      Iterator var4 = var1.iterator();

      while(var4.hasNext()) {
         if(((FacebookPost)var4.next()).getPostType() == -1) {
            var4.remove();
         }
      }

      int var5 = 0;
      if(var3 == 0) {
         this.mHandles.clear();
         long var6 = System.currentTimeMillis();
         this.mLastStreamGetAllTime = var6;
      } else {
         var5 = this.mHandles.size();
         Iterator var11 = this.mHandles.iterator();

         while(var11.hasNext()) {
            String var12 = ((FacebookStreamContainer.FacebookPostHandle)var11.next()).mPostId;
            Iterator var13 = var1.iterator();

            while(var13.hasNext()) {
               if(((FacebookPost)var13.next()).postId.equals(var12)) {
                  var11.remove();
                  break;
               }
            }
         }
      }

      Iterator var8 = var1.iterator();

      while(var8.hasNext()) {
         FacebookStreamContainer.FacebookPostHandle var9 = FacebookStreamContainer.FacebookPostCache.add((FacebookPost)var8.next());
         this.mHandles.add(var9);
      }

      List var14 = this.mHandles;
      Comparator var15 = FacebookStreamContainer.FacebookPostHandle.timeComparator;
      Collections.sort(var14, var15);
      int var16 = this.mHandles.size();
      byte var17;
      if(var5 == var16) {
         var17 = 1;
      } else {
         var17 = 0;
      }

      this.mComplete = (boolean)var17;
   }

   public void clear() {
      this.mHandles.clear();
      this.mComplete = (boolean)0;
   }

   public long getLastGetAllTime() {
      return this.mLastStreamGetAllTime;
   }

   public FacebookPost getPost(int var1) {
      return FacebookStreamContainer.FacebookPostCache.get((FacebookStreamContainer.FacebookPostHandle)this.mHandles.get(var1));
   }

   public FacebookPost getPost(String var1) {
      Iterator var2 = this.mHandles.iterator();

      FacebookPost var4;
      while(true) {
         if(var2.hasNext()) {
            FacebookStreamContainer.FacebookPostHandle var3 = (FacebookStreamContainer.FacebookPostHandle)var2.next();
            if(!var3.mPostId.equals(var1)) {
               continue;
            }

            var4 = FacebookStreamContainer.FacebookPostCache.get(var3);
            break;
         }

         var4 = null;
         break;
      }

      return var4;
   }

   public int getPostCount() {
      return this.mHandles.size();
   }

   public List<FacebookPost> getPosts() {
      ArrayList var1 = new ArrayList();
      Iterator var2 = this.mHandles.iterator();

      while(var2.hasNext()) {
         FacebookPost var3 = FacebookStreamContainer.FacebookPostCache.get((FacebookStreamContainer.FacebookPostHandle)var2.next());
         var1.add(var3);
      }

      return var1;
   }

   public void insertFirst(FacebookPost var1) {
      FacebookStreamContainer.FacebookPostHandle var2 = FacebookStreamContainer.FacebookPostCache.add(var1);
      this.mHandles.add(0, var2);
   }

   public boolean isComplete() {
      return this.mComplete;
   }

   static class FacebookPostCache {

      // $FF: synthetic field
      static final boolean $assertionsDisabled;
      private static final Map<FacebookStreamContainer.FacebookPostHandle, WeakReference<FacebookStreamContainer.FacebookPostHandle>> canonicalHandleMap;
      private static final Map<FacebookStreamContainer.FacebookPostHandle, FacebookPost> postCache;


      static {
         byte var0;
         if(!FacebookStreamContainer.class.desiredAssertionStatus()) {
            var0 = 1;
         } else {
            var0 = 0;
         }

         $assertionsDisabled = (boolean)var0;
         postCache = new WeakHashMap();
         canonicalHandleMap = new WeakHashMap();
      }

      FacebookPostCache() {}

      static FacebookStreamContainer.FacebookPostHandle add(FacebookPost var0) {
         FacebookStreamContainer.FacebookPostHandle var1 = getCanonicalHandle(var0.postId);
         long var2 = var0.createdTime;
         var1.mCreatedTime = var2;
         postCache.put(var1, var0);
         return var1;
      }

      static FacebookPost get(FacebookStreamContainer.FacebookPostHandle var0) {
         if(!$assertionsDisabled && var0.mIsCanonical != 1) {
            throw new AssertionError();
         } else {
            return (FacebookPost)postCache.get(var0);
         }
      }

      static FacebookPost get(String var0) {
         FacebookStreamContainer.FacebookPostHandle var1 = getCanonicalHandle(var0);
         return (FacebookPost)postCache.get(var1);
      }

      static FacebookStreamContainer.FacebookPostHandle getCanonicalHandle(String var0) {
         synchronized(FacebookStreamContainer.FacebookPostCache.class){}

         FacebookStreamContainer.FacebookPostHandle var3;
         try {
            label61: {
               FacebookStreamContainer.FacebookPostHandle var1 = new FacebookStreamContainer.FacebookPostHandle(var0);
               WeakReference var2 = (WeakReference)canonicalHandleMap.get(var1);
               if(var2 != null) {
                  var3 = (FacebookStreamContainer.FacebookPostHandle)var2.get();
                  if(var3 != null) {
                     break label61;
                  }
               }

               var3 = var1;
               var1.mIsCanonical = (boolean)1;
               WeakReference var4 = new WeakReference(var1);
               Object var5 = canonicalHandleMap.put(var1, var4);
            }

            if(!$assertionsDisabled && var3 == null) {
               throw new AssertionError();
            }

            if(!$assertionsDisabled && !var3.mIsCanonical) {
               throw new AssertionError();
            }
         } finally {
            ;
         }

         return var3;
      }

      static FacebookStreamContainer.FacebookPostHandle remove(String var0) {
         FacebookStreamContainer.FacebookPostHandle var1 = getCanonicalHandle(var0);
         Object var2 = postCache.remove(var1);
         return var1;
      }
   }

   static class FacebookPostHandle {

      public static Comparator<FacebookStreamContainer.FacebookPostHandle> timeComparator = new FacebookStreamContainer.FacebookPostHandle.1();
      long mCreatedTime;
      boolean mIsCanonical;
      final String mPostId;


      FacebookPostHandle(String var1) {
         this.mPostId = var1;
      }

      public boolean equals(Object var1) {
         boolean var4;
         if(var1 instanceof FacebookStreamContainer.FacebookPostHandle) {
            String var2 = this.mPostId;
            String var3 = ((FacebookStreamContainer.FacebookPostHandle)var1).mPostId;
            if(var2.equals(var3)) {
               var4 = true;
               return var4;
            }
         }

         var4 = false;
         return var4;
      }

      public int hashCode() {
         return this.mPostId.hashCode();
      }

      static class 1 implements Comparator<FacebookStreamContainer.FacebookPostHandle> {

         1() {}

         public int compare(FacebookStreamContainer.FacebookPostHandle var1, FacebookStreamContainer.FacebookPostHandle var2) {
            long var3 = var1.mCreatedTime;
            long var5 = var2.mCreatedTime;
            byte var7;
            if(var3 > var5) {
               var7 = -1;
            } else {
               long var8 = var1.mCreatedTime;
               long var10 = var2.mCreatedTime;
               if(var8 == var10) {
                  var7 = 0;
               } else {
                  var7 = 1;
               }
            }

            return var7;
         }
      }
   }
}
