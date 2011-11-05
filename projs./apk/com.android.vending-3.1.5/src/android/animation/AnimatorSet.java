package android.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public final class AnimatorSet extends Animator {

   boolean mCanceled;
   private long mDuration;
   private boolean mNeedsSort;
   private HashMap<Animator, AnimatorSet.Node> mNodeMap;
   private ArrayList<AnimatorSet.Node> mNodes;
   private ArrayList<Animator> mPlayingSet;
   private AnimatorSet.AnimatorSetListener mSetListener;
   private ArrayList<AnimatorSet.Node> mSortedNodes;
   private long mStartDelay;


   public AnimatorSet() {
      ArrayList var1 = new ArrayList();
      this.mPlayingSet = var1;
      HashMap var2 = new HashMap();
      this.mNodeMap = var2;
      ArrayList var3 = new ArrayList();
      this.mNodes = var3;
      ArrayList var4 = new ArrayList();
      this.mSortedNodes = var4;
      this.mNeedsSort = (boolean)1;
      this.mSetListener = null;
      this.mCanceled = (boolean)0;
      this.mStartDelay = 0L;
      this.mDuration = 65535L;
   }

   private void sortNodes() {
      Iterator var2;
      if(this.mNeedsSort) {
         this.mSortedNodes.clear();
         ArrayList var1 = new ArrayList();
         var2 = this.mNodes.iterator();

         while(var2.hasNext()) {
            AnimatorSet.Node var3 = (AnimatorSet.Node)var2.next();
            if(var3.dependencies == null || var3.dependencies.size() == 0) {
               var1.add(var3);
            }
         }

         ArrayList var5 = new ArrayList();

         while(var1.size() > 0) {
            var2 = var1.iterator();

            while(var2.hasNext()) {
               AnimatorSet.Node var6 = (AnimatorSet.Node)var2.next();
               this.mSortedNodes.add(var6);
               if(var6.nodeDependents != null) {
                  Iterator var8 = var6.nodeDependents.iterator();

                  while(var8.hasNext()) {
                     AnimatorSet.Node var9 = (AnimatorSet.Node)var8.next();
                     var9.nodeDependencies.remove(var6);
                     if(var9.nodeDependencies.size() == 0) {
                        var5.add(var9);
                     }
                  }
               }
            }

            var1.clear();
            var1.addAll(var5);
            var5.clear();
         }

         this.mNeedsSort = (boolean)0;
         int var13 = this.mSortedNodes.size();
         int var14 = this.mNodes.size();
         if(var13 != var14) {
            throw new IllegalStateException("Circular dependencies cannot exist in AnimatorSet");
         }
      } else {
         AnimatorSet.Node var15;
         for(var2 = this.mNodes.iterator(); var2.hasNext(); var15.done = (boolean)0) {
            var15 = (AnimatorSet.Node)var2.next();
            if(var15.dependencies != null && var15.dependencies.size() > 0) {
               Iterator var16 = var15.dependencies.iterator();

               while(var16.hasNext()) {
                  AnimatorSet.Dependency var17 = (AnimatorSet.Dependency)var16.next();
                  if(var15.nodeDependencies == null) {
                     ArrayList var18 = new ArrayList();
                     var15.nodeDependencies = var18;
                  }

                  ArrayList var19 = var15.nodeDependencies;
                  AnimatorSet.Node var20 = var17.node;
                  if(!var19.contains(var20)) {
                     ArrayList var21 = var15.nodeDependencies;
                     AnimatorSet.Node var22 = var17.node;
                     var21.add(var22);
                  }
               }
            }
         }

      }
   }

   public void cancel() {
      this.mCanceled = (boolean)1;
      Iterator var1;
      if(this.mListeners != null) {
         var1 = ((ArrayList)this.mListeners.clone()).iterator();

         while(var1.hasNext()) {
            ((Animator.AnimatorListener)var1.next()).onAnimationCancel(this);
         }
      }

      if(this.mSortedNodes.size() > 0) {
         var1 = this.mSortedNodes.iterator();

         while(var1.hasNext()) {
            ((AnimatorSet.Node)var1.next()).animation.cancel();
         }

      }
   }

   public AnimatorSet clone() {
      AnimatorSet var1 = (AnimatorSet)super.clone();
      var1.mNeedsSort = (boolean)1;
      var1.mCanceled = (boolean)0;
      ArrayList var2 = new ArrayList();
      var1.mPlayingSet = var2;
      HashMap var3 = new HashMap();
      var1.mNodeMap = var3;
      ArrayList var4 = new ArrayList();
      var1.mNodes = var4;
      ArrayList var5 = new ArrayList();
      var1.mSortedNodes = var5;
      HashMap var6 = new HashMap();
      Iterator var7 = this.mNodes.iterator();

      while(var7.hasNext()) {
         AnimatorSet.Node var8 = (AnimatorSet.Node)var7.next();
         AnimatorSet.Node var9 = var8.clone();
         var6.put(var8, var9);
         var1.mNodes.add(var9);
         HashMap var12 = var1.mNodeMap;
         Animator var13 = var9.animation;
         var12.put(var13, var9);
         var9.dependencies = null;
         var9.tmpDependencies = null;
         var9.nodeDependents = null;
         var9.nodeDependencies = null;
         ArrayList var15 = var9.animation.getListeners();
         if(var15 != null) {
            ArrayList var16 = null;
            Iterator var17 = var15.iterator();

            while(var17.hasNext()) {
               Animator.AnimatorListener var18 = (Animator.AnimatorListener)var17.next();
               if(var18 instanceof AnimatorSet.AnimatorSetListener) {
                  if(var16 == null) {
                     var16 = new ArrayList();
                  }

                  var16.add(var18);
               }
            }

            if(var16 != null) {
               var17 = var16.iterator();

               while(var17.hasNext()) {
                  Animator.AnimatorListener var20 = (Animator.AnimatorListener)var17.next();
                  var15.remove(var20);
               }
            }
         }
      }

      Iterator var22 = this.mNodes.iterator();

      while(var22.hasNext()) {
         AnimatorSet.Node var23 = (AnimatorSet.Node)var22.next();
         AnimatorSet.Node var24 = (AnimatorSet.Node)var6.get(var23);
         if(var23.dependencies != null) {
            Iterator var25 = var23.dependencies.iterator();

            while(var25.hasNext()) {
               AnimatorSet.Dependency var26 = (AnimatorSet.Dependency)var25.next();
               AnimatorSet.Node var27 = var26.node;
               AnimatorSet.Node var28 = (AnimatorSet.Node)var6.get(var27);
               int var29 = var26.rule;
               AnimatorSet.Dependency var30 = new AnimatorSet.Dependency(var28, var29);
               var24.addDependency(var30);
            }
         }
      }

      return var1;
   }

   public void end() {
      this.mCanceled = (boolean)1;
      int var1 = this.mSortedNodes.size();
      int var2 = this.mNodes.size();
      Iterator var3;
      if(var1 != var2) {
         this.sortNodes();
         var3 = this.mSortedNodes.iterator();

         while(var3.hasNext()) {
            AnimatorSet.Node var4 = (AnimatorSet.Node)var3.next();
            if(this.mSetListener == null) {
               AnimatorSet.AnimatorSetListener var5 = new AnimatorSet.AnimatorSetListener(this);
               this.mSetListener = var5;
            }

            Animator var6 = var4.animation;
            AnimatorSet.AnimatorSetListener var7 = this.mSetListener;
            var6.addListener(var7);
         }
      }

      if(this.mSortedNodes.size() > 0) {
         var3 = this.mSortedNodes.iterator();

         while(var3.hasNext()) {
            ((AnimatorSet.Node)var3.next()).animation.end();
         }

      }
   }

   public ArrayList<Animator> getChildAnimations() {
      ArrayList var1 = new ArrayList();
      Iterator var2 = this.mNodes.iterator();

      while(var2.hasNext()) {
         Animator var3 = ((AnimatorSet.Node)var2.next()).animation;
         var1.add(var3);
      }

      return var1;
   }

   public long getDuration() {
      return this.mDuration;
   }

   public long getStartDelay() {
      return this.mStartDelay;
   }

   public boolean isRunning() {
      Iterator var1 = this.mNodes.iterator();

      boolean var2;
      while(true) {
         if(var1.hasNext()) {
            if(!((AnimatorSet.Node)var1.next()).animation.isRunning()) {
               continue;
            }

            var2 = true;
            break;
         }

         var2 = false;
         break;
      }

      return var2;
   }

   public AnimatorSet.Builder play(Animator var1) {
      AnimatorSet.Builder var2;
      if(var1 != null) {
         this.mNeedsSort = (boolean)1;
         var2 = new AnimatorSet.Builder(var1);
      } else {
         var2 = null;
      }

      return var2;
   }

   public void playSequentially(Animator ... var1) {
      if(var1 != null) {
         this.mNeedsSort = (boolean)1;
         if(var1.length == 1) {
            Animator var2 = var1[0];
            this.play(var2);
         } else {
            int var4 = 0;

            while(true) {
               int var5 = var1.length + -1;
               if(var4 >= var5) {
                  return;
               }

               Animator var6 = var1[var4];
               AnimatorSet.Builder var7 = this.play(var6);
               int var8 = var4 + 1;
               Animator var9 = var1[var8];
               var7.before(var9);
               ++var4;
            }
         }
      }
   }

   public void playTogether(Animator ... var1) {
      if(var1 != null) {
         this.mNeedsSort = (boolean)1;
         Animator var2 = var1[0];
         AnimatorSet.Builder var3 = this.play(var2);
         int var4 = 1;

         while(true) {
            int var5 = var1.length;
            if(var4 >= var5) {
               return;
            }

            Animator var6 = var1[var4];
            var3.with(var6);
            ++var4;
         }
      }
   }

   public AnimatorSet setDuration(long var1) {
      if(var1 < 0L) {
         throw new IllegalArgumentException("duration must be a value of zero or greater");
      } else {
         Iterator var3 = this.mNodes.iterator();

         while(var3.hasNext()) {
            ((AnimatorSet.Node)var3.next()).animation.setDuration(var1);
         }

         this.mDuration = var1;
         return this;
      }
   }

   public void setInterpolator(TimeInterpolator var1) {
      Iterator var2 = this.mNodes.iterator();

      while(var2.hasNext()) {
         ((AnimatorSet.Node)var2.next()).animation.setInterpolator(var1);
      }

   }

   public void setStartDelay(long var1) {
      this.mStartDelay = var1;
   }

   public void setTarget(Object var1) {
      Iterator var2 = this.mNodes.iterator();

      while(var2.hasNext()) {
         Animator var3 = ((AnimatorSet.Node)var2.next()).animation;
         if(var3 instanceof AnimatorSet) {
            ((AnimatorSet)var3).setTarget(var1);
         } else if(var3 instanceof ObjectAnimator) {
            ((ObjectAnimator)var3).setTarget(var1);
         }
      }

   }

   public void start() {
      this.mCanceled = (boolean)0;
      this.sortNodes();
      ArrayList var1 = new ArrayList();
      Iterator var2 = this.mSortedNodes.iterator();

      while(var2.hasNext()) {
         AnimatorSet.Node var3 = (AnimatorSet.Node)var2.next();
         if(this.mSetListener == null) {
            AnimatorSet.AnimatorSetListener var4 = new AnimatorSet.AnimatorSetListener(this);
            this.mSetListener = var4;
         }

         if(var3.dependencies != null && var3.dependencies.size() != 0) {
            Iterator var8 = var3.dependencies.iterator();

            while(var8.hasNext()) {
               AnimatorSet.Dependency var9 = (AnimatorSet.Dependency)var8.next();
               Animator var10 = var9.node.animation;
               int var11 = var9.rule;
               AnimatorSet.DependencyListener var12 = new AnimatorSet.DependencyListener(this, var3, var11);
               var10.addListener(var12);
            }

            ArrayList var13 = (ArrayList)var3.dependencies.clone();
            var3.tmpDependencies = var13;
         } else {
            var1.add(var3);
         }

         Animator var6 = var3.animation;
         AnimatorSet.AnimatorSetListener var7 = this.mSetListener;
         var6.addListener(var7);
      }

      if(this.mStartDelay <= 0L) {
         var2 = var1.iterator();

         while(var2.hasNext()) {
            AnimatorSet.Node var14 = (AnimatorSet.Node)var2.next();
            var14.animation.start();
            ArrayList var15 = this.mPlayingSet;
            Animator var16 = var14.animation;
            var15.add(var16);
         }
      } else {
         ValueAnimator var18 = ValueAnimator.ofFloat(new float[]{(float)false, (float)1065353216});
         long var19 = this.mStartDelay;
         var18.setDuration(var19);
         AnimatorSet.1 var22 = new AnimatorSet.1(var1);
         var18.addListener(var22);
      }

      if(this.mListeners != null) {
         var2 = ((ArrayList)this.mListeners.clone()).iterator();

         while(var2.hasNext()) {
            ((Animator.AnimatorListener)var2.next()).onAnimationStart(this);
         }

      }
   }

   private static class Node implements Cloneable {

      public Animator animation;
      public ArrayList<AnimatorSet.Dependency> dependencies = null;
      public boolean done = 0;
      public ArrayList<AnimatorSet.Node> nodeDependencies = null;
      public ArrayList<AnimatorSet.Node> nodeDependents = null;
      public ArrayList<AnimatorSet.Dependency> tmpDependencies = null;


      public Node(Animator var1) {
         this.animation = var1;
      }

      public void addDependency(AnimatorSet.Dependency var1) {
         if(this.dependencies == null) {
            ArrayList var2 = new ArrayList();
            this.dependencies = var2;
            ArrayList var3 = new ArrayList();
            this.nodeDependencies = var3;
         }

         this.dependencies.add(var1);
         ArrayList var5 = this.nodeDependencies;
         AnimatorSet.Node var6 = var1.node;
         if(!var5.contains(var6)) {
            ArrayList var7 = this.nodeDependencies;
            AnimatorSet.Node var8 = var1.node;
            var7.add(var8);
         }

         AnimatorSet.Node var10 = var1.node;
         if(var10.nodeDependents == null) {
            ArrayList var11 = new ArrayList();
            var10.nodeDependents = var11;
         }

         var10.nodeDependents.add(this);
      }

      public AnimatorSet.Node clone() {
         try {
            AnimatorSet.Node var1 = (AnimatorSet.Node)super.clone();
            Animator var2 = this.animation.clone();
            var1.animation = var2;
            return var1;
         } catch (CloneNotSupportedException var4) {
            throw new AssertionError();
         }
      }
   }

   class 1 extends AnimatorListenerAdapter {

      // $FF: synthetic field
      final ArrayList val$nodesToStart;


      1(ArrayList var2) {
         this.val$nodesToStart = var2;
      }

      public void onAnimationEnd(Animator var1) {
         Iterator var2 = this.val$nodesToStart.iterator();

         while(var2.hasNext()) {
            AnimatorSet.Node var3 = (AnimatorSet.Node)var2.next();
            var3.animation.start();
            ArrayList var4 = AnimatorSet.this.mPlayingSet;
            Animator var5 = var3.animation;
            var4.add(var5);
         }

      }
   }

   private class AnimatorSetListener implements Animator.AnimatorListener {

      private AnimatorSet mAnimatorSet;


      AnimatorSetListener(AnimatorSet var2) {
         this.mAnimatorSet = var2;
      }

      public void onAnimationCancel(Animator var1) {
         if(AnimatorSet.this.mPlayingSet.size() == 0) {
            if(AnimatorSet.this.mListeners != null) {
               Iterator var2 = AnimatorSet.this.mListeners.iterator();

               while(var2.hasNext()) {
                  Animator.AnimatorListener var3 = (Animator.AnimatorListener)var2.next();
                  AnimatorSet var4 = this.mAnimatorSet;
                  var3.onAnimationCancel(var4);
               }

            }
         }
      }

      public void onAnimationEnd(Animator var1) {
         var1.removeListener(this);
         boolean var2 = AnimatorSet.this.mPlayingSet.remove(var1);
         ((AnimatorSet.Node)this.mAnimatorSet.mNodeMap.get(var1)).done = (boolean)1;
         ArrayList var3 = this.mAnimatorSet.mSortedNodes;
         boolean var4 = true;
         Iterator var5 = var3.iterator();

         while(var5.hasNext()) {
            if(!((AnimatorSet.Node)var5.next()).done) {
               var4 = false;
               break;
            }
         }

         if(var4) {
            if(AnimatorSet.this.mListeners != null) {
               var5 = ((ArrayList)AnimatorSet.this.mListeners.clone()).iterator();

               while(var5.hasNext()) {
                  Animator.AnimatorListener var6 = (Animator.AnimatorListener)var5.next();
                  AnimatorSet var7 = this.mAnimatorSet;
                  var6.onAnimationEnd(var7);
               }

            }
         }
      }

      public void onAnimationRepeat(Animator var1) {}

      public void onAnimationStart(Animator var1) {}
   }

   private static class DependencyListener implements Animator.AnimatorListener {

      private AnimatorSet mAnimatorSet;
      private AnimatorSet.Node mNode;
      private int mRule;


      public DependencyListener(AnimatorSet var1, AnimatorSet.Node var2, int var3) {
         this.mAnimatorSet = var1;
         this.mNode = var2;
         this.mRule = var3;
      }

      private void startIfReady(Animator var1) {
         if(!this.mAnimatorSet.mCanceled) {
            AnimatorSet.Dependency var2 = null;
            Iterator var3 = this.mNode.tmpDependencies.iterator();

            while(var3.hasNext()) {
               AnimatorSet.Dependency var4 = (AnimatorSet.Dependency)var3.next();
               int var5 = var4.rule;
               int var6 = this.mRule;
               if(var5 == var6 && var4.node.animation == var1) {
                  var2 = var4;
                  var1.removeListener(this);
                  break;
               }
            }

            this.mNode.tmpDependencies.remove(var2);
            if(this.mNode.tmpDependencies.size() == 0) {
               this.mNode.animation.start();
               ArrayList var8 = this.mAnimatorSet.mPlayingSet;
               Animator var9 = this.mNode.animation;
               var8.add(var9);
            }
         }
      }

      public void onAnimationCancel(Animator var1) {}

      public void onAnimationEnd(Animator var1) {
         if(this.mRule == 1) {
            this.startIfReady(var1);
         }
      }

      public void onAnimationRepeat(Animator var1) {}

      public void onAnimationStart(Animator var1) {
         if(this.mRule == 0) {
            this.startIfReady(var1);
         }
      }
   }

   private static class Dependency {

      static final int AFTER = 1;
      static final int WITH;
      public AnimatorSet.Node node;
      public int rule;


      public Dependency(AnimatorSet.Node var1, int var2) {
         this.node = var1;
         this.rule = var2;
      }
   }

   public class Builder {

      private AnimatorSet.Node mCurrentNode;


      Builder(Animator var2) {
         AnimatorSet.Node var3 = (AnimatorSet.Node)AnimatorSet.this.mNodeMap.get(var2);
         this.mCurrentNode = var3;
         if(this.mCurrentNode == null) {
            AnimatorSet.Node var4 = new AnimatorSet.Node(var2);
            this.mCurrentNode = var4;
            HashMap var5 = AnimatorSet.this.mNodeMap;
            AnimatorSet.Node var6 = this.mCurrentNode;
            var5.put(var2, var6);
            ArrayList var8 = AnimatorSet.this.mNodes;
            AnimatorSet.Node var9 = this.mCurrentNode;
            var8.add(var9);
         }
      }

      public void after(long var1) {
         ValueAnimator var3 = ValueAnimator.ofFloat(new float[]{(float)false, (float)1065353216});
         var3.setDuration(var1);
         this.after(var3);
      }

      public void after(Animator var1) {
         AnimatorSet.Node var2 = (AnimatorSet.Node)AnimatorSet.this.mNodeMap.get(var1);
         if(var2 == null) {
            var2 = new AnimatorSet.Node(var1);
            AnimatorSet.this.mNodeMap.put(var1, var2);
            boolean var4 = AnimatorSet.this.mNodes.add(var2);
         }

         AnimatorSet.Dependency var5 = new AnimatorSet.Dependency(var2, 1);
         this.mCurrentNode.addDependency(var5);
      }

      public void before(Animator var1) {
         AnimatorSet.Node var2 = (AnimatorSet.Node)AnimatorSet.this.mNodeMap.get(var1);
         if(var2 == null) {
            var2 = new AnimatorSet.Node(var1);
            AnimatorSet.this.mNodeMap.put(var1, var2);
            boolean var4 = AnimatorSet.this.mNodes.add(var2);
         }

         AnimatorSet.Node var5 = this.mCurrentNode;
         AnimatorSet.Dependency var6 = new AnimatorSet.Dependency(var5, 1);
         var2.addDependency(var6);
      }

      public void with(Animator var1) {
         AnimatorSet.Node var2 = (AnimatorSet.Node)AnimatorSet.this.mNodeMap.get(var1);
         if(var2 == null) {
            var2 = new AnimatorSet.Node(var1);
            AnimatorSet.this.mNodeMap.put(var1, var2);
            boolean var4 = AnimatorSet.this.mNodes.add(var2);
         }

         AnimatorSet.Node var5 = this.mCurrentNode;
         AnimatorSet.Dependency var6 = new AnimatorSet.Dependency(var5, 0);
         var2.addDependency(var6);
      }
   }
}
