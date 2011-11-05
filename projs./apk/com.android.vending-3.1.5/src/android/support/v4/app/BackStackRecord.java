package android.support.v4.app;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManagerImpl;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;

final class BackStackRecord extends FragmentTransaction implements FragmentManager.BackStackEntry, Runnable {

   static final int OP_ADD = 1;
   static final int OP_ATTACH = 7;
   static final int OP_DETACH = 6;
   static final int OP_HIDE = 4;
   static final int OP_NULL = 0;
   static final int OP_REMOVE = 3;
   static final int OP_REPLACE = 2;
   static final int OP_SHOW = 5;
   static final String TAG = "BackStackEntry";
   boolean mAddToBackStack;
   boolean mAllowAddToBackStack = 1;
   int mBreadCrumbShortTitleRes;
   CharSequence mBreadCrumbShortTitleText;
   int mBreadCrumbTitleRes;
   CharSequence mBreadCrumbTitleText;
   boolean mCommitted;
   int mEnterAnim;
   int mExitAnim;
   BackStackRecord.Op mHead;
   int mIndex;
   final FragmentManagerImpl mManager;
   String mName;
   int mNumOp;
   BackStackRecord.Op mTail;
   int mTransition;
   int mTransitionStyle;


   public BackStackRecord(FragmentManagerImpl var1) {
      this.mManager = var1;
   }

   private void doAddOp(int var1, Fragment var2, String var3, int var4) {
      if(var2.mImmediateActivity != null) {
         String var5 = "Fragment already added: " + var2;
         throw new IllegalStateException(var5);
      } else {
         FragmentActivity var6 = this.mManager.mActivity;
         var2.mImmediateActivity = var6;
         FragmentManagerImpl var7 = this.mManager;
         var2.mFragmentManager = var7;
         if(var3 != null) {
            if(var2.mTag != null) {
               String var8 = var2.mTag;
               if(!var3.equals(var8)) {
                  StringBuilder var9 = (new StringBuilder()).append("Can\'t change tag of fragment ").append(var2).append(": was ");
                  String var10 = var2.mTag;
                  String var11 = var9.append(var10).append(" now ").append(var3).toString();
                  throw new IllegalStateException(var11);
               }
            }

            var2.mTag = var3;
         }

         if(var1 != 0) {
            if(var2.mFragmentId != 0 && var2.mFragmentId != var1) {
               StringBuilder var12 = (new StringBuilder()).append("Can\'t change container ID of fragment ").append(var2).append(": was ");
               int var13 = var2.mFragmentId;
               String var14 = var12.append(var13).append(" now ").append(var1).toString();
               throw new IllegalStateException(var14);
            }

            var2.mFragmentId = var1;
            var2.mContainerId = var1;
         }

         BackStackRecord.Op var15 = new BackStackRecord.Op();
         var15.cmd = var4;
         var15.fragment = var2;
         this.addOp(var15);
      }
   }

   public FragmentTransaction add(int var1, Fragment var2) {
      this.doAddOp(var1, var2, (String)null, 1);
      return this;
   }

   public FragmentTransaction add(int var1, Fragment var2, String var3) {
      this.doAddOp(var1, var2, var3, 1);
      return this;
   }

   public FragmentTransaction add(Fragment var1, String var2) {
      this.doAddOp(0, var1, var2, 1);
      return this;
   }

   void addOp(BackStackRecord.Op var1) {
      if(this.mHead == null) {
         this.mTail = var1;
         this.mHead = var1;
      } else {
         BackStackRecord.Op var5 = this.mTail;
         var1.prev = var5;
         this.mTail.next = var1;
         this.mTail = var1;
      }

      int var2 = this.mEnterAnim;
      var1.enterAnim = var2;
      int var3 = this.mExitAnim;
      var1.exitAnim = var3;
      int var4 = this.mNumOp + 1;
      this.mNumOp = var4;
   }

   public FragmentTransaction addToBackStack(String var1) {
      if(!this.mAllowAddToBackStack) {
         throw new IllegalStateException("This FragmentTransaction is not allowed to be added to the back stack.");
      } else {
         this.mAddToBackStack = (boolean)1;
         this.mName = var1;
         return this;
      }
   }

   public FragmentTransaction attach(Fragment var1) {
      BackStackRecord.Op var2 = new BackStackRecord.Op();
      var2.cmd = 7;
      var2.fragment = var1;
      this.addOp(var2);
      return this;
   }

   void bumpBackStackNesting(int var1) {
      if(this.mAddToBackStack) {
         if(FragmentManagerImpl.DEBUG) {
            String var2 = "Bump nesting in " + this + " by " + var1;
            int var3 = Log.v("BackStackEntry", var2);
         }

         for(BackStackRecord.Op var4 = this.mHead; var4 != null; var4 = var4.next) {
            Fragment var5 = var4.fragment;
            int var6 = var5.mBackStackNesting + var1;
            var5.mBackStackNesting = var6;
            if(FragmentManagerImpl.DEBUG) {
               StringBuilder var7 = (new StringBuilder()).append("Bump nesting of ");
               Fragment var8 = var4.fragment;
               StringBuilder var9 = var7.append(var8).append(" to ");
               int var10 = var4.fragment.mBackStackNesting;
               String var11 = var9.append(var10).toString();
               int var12 = Log.v("BackStackEntry", var11);
            }

            if(var4.removed != null) {
               for(int var13 = var4.removed.size() + -1; var13 >= 0; var13 += -1) {
                  Fragment var14 = (Fragment)var4.removed.get(var13);
                  int var15 = var14.mBackStackNesting + var1;
                  var14.mBackStackNesting = var15;
                  if(FragmentManagerImpl.DEBUG) {
                     StringBuilder var16 = (new StringBuilder()).append("Bump nesting of ").append(var14).append(" to ");
                     int var17 = var14.mBackStackNesting;
                     String var18 = var16.append(var17).toString();
                     int var19 = Log.v("BackStackEntry", var18);
                  }
               }
            }
         }

      }
   }

   public int commit() {
      return this.commitInternal((boolean)0);
   }

   public int commitAllowingStateLoss() {
      return this.commitInternal((boolean)1);
   }

   int commitInternal(boolean var1) {
      if(this.mCommitted) {
         throw new IllegalStateException("commit already called");
      } else {
         if(FragmentManagerImpl.DEBUG) {
            String var2 = "Commit: " + this;
            int var3 = Log.v("BackStackEntry", var2);
         }

         this.mCommitted = (boolean)1;
         if(this.mAddToBackStack) {
            int var4 = this.mManager.allocBackStackIndex(this);
            this.mIndex = var4;
         } else {
            this.mIndex = -1;
         }

         this.mManager.enqueueAction(this, var1);
         return this.mIndex;
      }
   }

   public FragmentTransaction detach(Fragment var1) {
      BackStackRecord.Op var2 = new BackStackRecord.Op();
      var2.cmd = 6;
      var2.fragment = var1;
      this.addOp(var2);
      return this;
   }

   public FragmentTransaction disallowAddToBackStack() {
      if(this.mAddToBackStack) {
         throw new IllegalStateException("This transaction is already being added to the back stack");
      } else {
         this.mAllowAddToBackStack = (boolean)0;
         return this;
      }
   }

   public void dump(String var1, FileDescriptor var2, PrintWriter var3, String[] var4) {
      var3.print(var1);
      var3.print("mName=");
      String var5 = this.mName;
      var3.print(var5);
      var3.print(" mIndex=");
      int var6 = this.mIndex;
      var3.print(var6);
      var3.print(" mCommitted=");
      boolean var7 = this.mCommitted;
      var3.println(var7);
      if(this.mTransition != 0) {
         var3.print(var1);
         var3.print("mTransition=#");
         String var8 = Integer.toHexString(this.mTransition);
         var3.print(var8);
         var3.print(" mTransitionStyle=#");
         String var9 = Integer.toHexString(this.mTransitionStyle);
         var3.println(var9);
      }

      if(this.mEnterAnim != 0 || this.mExitAnim != 0) {
         var3.print(var1);
         var3.print("mEnterAnim=#");
         String var10 = Integer.toHexString(this.mEnterAnim);
         var3.print(var10);
         var3.print(" mExitAnim=#");
         String var11 = Integer.toHexString(this.mExitAnim);
         var3.println(var11);
      }

      if(this.mBreadCrumbTitleRes != 0 || this.mBreadCrumbTitleText != null) {
         var3.print(var1);
         var3.print("mBreadCrumbTitleRes=#");
         String var12 = Integer.toHexString(this.mBreadCrumbTitleRes);
         var3.print(var12);
         var3.print(" mBreadCrumbTitleText=");
         CharSequence var13 = this.mBreadCrumbTitleText;
         var3.println(var13);
      }

      if(this.mBreadCrumbShortTitleRes != 0 || this.mBreadCrumbShortTitleText != null) {
         var3.print(var1);
         var3.print("mBreadCrumbShortTitleRes=#");
         String var14 = Integer.toHexString(this.mBreadCrumbShortTitleRes);
         var3.print(var14);
         var3.print(" mBreadCrumbShortTitleText=");
         CharSequence var15 = this.mBreadCrumbShortTitleText;
         var3.println(var15);
      }

      if(this.mHead != null) {
         var3.print(var1);
         var3.println("Operations:");
         String var16 = var1 + "    ";
         BackStackRecord.Op var17 = this.mHead;

         for(byte var18 = 0; var17 != null; var17 = var17.next) {
            var3.print(var1);
            var3.print("  Op #");
            var3.print(var18);
            var3.println(":");
            var3.print(var16);
            var3.print("cmd=");
            int var19 = var17.cmd;
            var3.print(var19);
            var3.print(" fragment=");
            Fragment var20 = var17.fragment;
            var3.println(var20);
            if(var17.enterAnim != 0 || var17.exitAnim != 0) {
               var3.print(var1);
               var3.print("enterAnim=");
               int var21 = var17.enterAnim;
               var3.print(var21);
               var3.print(" exitAnim=");
               int var22 = var17.exitAnim;
               var3.println(var22);
            }

            if(var17.removed != null && var17.removed.size() > 0) {
               int var23 = 0;

               while(true) {
                  int var24 = var17.removed.size();
                  if(var23 >= var24) {
                     break;
                  }

                  var3.print(var16);
                  if(var17.removed.size() == 1) {
                     var3.print("Removed: ");
                  } else {
                     var3.println("Removed:");
                     var3.print(var16);
                     var3.print("  #");
                     var3.print(var18);
                     var3.print(": ");
                  }

                  Object var25 = var17.removed.get(var23);
                  var3.println(var25);
                  ++var23;
               }
            }
         }

      }
   }

   public CharSequence getBreadCrumbShortTitle() {
      CharSequence var3;
      if(this.mBreadCrumbShortTitleRes != 0) {
         FragmentActivity var1 = this.mManager.mActivity;
         int var2 = this.mBreadCrumbShortTitleRes;
         var3 = var1.getText(var2);
      } else {
         var3 = this.mBreadCrumbShortTitleText;
      }

      return var3;
   }

   public int getBreadCrumbShortTitleRes() {
      return this.mBreadCrumbShortTitleRes;
   }

   public CharSequence getBreadCrumbTitle() {
      CharSequence var3;
      if(this.mBreadCrumbTitleRes != 0) {
         FragmentActivity var1 = this.mManager.mActivity;
         int var2 = this.mBreadCrumbTitleRes;
         var3 = var1.getText(var2);
      } else {
         var3 = this.mBreadCrumbTitleText;
      }

      return var3;
   }

   public int getBreadCrumbTitleRes() {
      return this.mBreadCrumbTitleRes;
   }

   public int getId() {
      return this.mIndex;
   }

   public String getName() {
      return this.mName;
   }

   public int getTransition() {
      return this.mTransition;
   }

   public int getTransitionStyle() {
      return this.mTransitionStyle;
   }

   public FragmentTransaction hide(Fragment var1) {
      if(var1.mImmediateActivity == null) {
         String var2 = "Fragment not added: " + var1;
         throw new IllegalStateException(var2);
      } else {
         BackStackRecord.Op var3 = new BackStackRecord.Op();
         var3.cmd = 4;
         var3.fragment = var1;
         this.addOp(var3);
         return this;
      }
   }

   public boolean isAddToBackStackAllowed() {
      return this.mAllowAddToBackStack;
   }

   public boolean isEmpty() {
      boolean var1;
      if(this.mNumOp == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public void popFromBackStack(boolean var1) {
      if(FragmentManagerImpl.DEBUG) {
         String var2 = "popFromBackStack: " + this;
         int var3 = Log.v("BackStackEntry", var2);
      }

      this.bumpBackStackNesting(-1);

      for(BackStackRecord.Op var4 = this.mTail; var4 != null; var4 = var4.prev) {
         switch(var4.cmd) {
         case 1:
            Fragment var8 = var4.fragment;
            var8.mImmediateActivity = null;
            FragmentManagerImpl var9 = this.mManager;
            int var10 = FragmentManagerImpl.reverseTransit(this.mTransition);
            int var11 = this.mTransitionStyle;
            var9.removeFragment(var8, var10, var11);
            break;
         case 2:
            Fragment var12 = var4.fragment;
            var12.mImmediateActivity = null;
            FragmentManagerImpl var13 = this.mManager;
            int var14 = FragmentManagerImpl.reverseTransit(this.mTransition);
            int var15 = this.mTransitionStyle;
            var13.removeFragment(var12, var14, var15);
            if(var4.removed != null) {
               int var16 = 0;

               while(true) {
                  int var17 = var4.removed.size();
                  if(var16 >= var17) {
                     break;
                  }

                  Fragment var18 = (Fragment)var4.removed.get(var16);
                  FragmentActivity var19 = this.mManager.mActivity;
                  var12.mImmediateActivity = var19;
                  this.mManager.addFragment(var18, (boolean)0);
                  ++var16;
               }
            }
            break;
         case 3:
            Fragment var20 = var4.fragment;
            FragmentActivity var21 = this.mManager.mActivity;
            var20.mImmediateActivity = var21;
            this.mManager.addFragment(var20, (boolean)0);
            break;
         case 4:
            Fragment var22 = var4.fragment;
            FragmentManagerImpl var23 = this.mManager;
            int var24 = FragmentManagerImpl.reverseTransit(this.mTransition);
            int var25 = this.mTransitionStyle;
            var23.showFragment(var22, var24, var25);
            break;
         case 5:
            Fragment var26 = var4.fragment;
            FragmentManagerImpl var27 = this.mManager;
            int var28 = FragmentManagerImpl.reverseTransit(this.mTransition);
            int var29 = this.mTransitionStyle;
            var27.hideFragment(var26, var28, var29);
            break;
         case 6:
            Fragment var30 = var4.fragment;
            FragmentManagerImpl var31 = this.mManager;
            int var32 = FragmentManagerImpl.reverseTransit(this.mTransition);
            int var33 = this.mTransitionStyle;
            var31.attachFragment(var30, var32, var33);
            break;
         case 7:
            Fragment var34 = var4.fragment;
            FragmentManagerImpl var35 = this.mManager;
            int var36 = FragmentManagerImpl.reverseTransit(this.mTransition);
            int var37 = this.mTransitionStyle;
            var35.detachFragment(var34, var36, var37);
            break;
         default:
            StringBuilder var5 = (new StringBuilder()).append("Unknown cmd: ");
            int var6 = var4.cmd;
            String var7 = var5.append(var6).toString();
            throw new IllegalArgumentException(var7);
         }
      }

      if(var1) {
         FragmentManagerImpl var38 = this.mManager;
         int var39 = this.mManager.mCurState;
         int var40 = FragmentManagerImpl.reverseTransit(this.mTransition);
         int var41 = this.mTransitionStyle;
         var38.moveToState(var39, var40, var41, (boolean)1);
      }

      if(this.mIndex >= 0) {
         FragmentManagerImpl var42 = this.mManager;
         int var43 = this.mIndex;
         var42.freeBackStackIndex(var43);
         this.mIndex = -1;
      }
   }

   public FragmentTransaction remove(Fragment var1) {
      if(var1.mImmediateActivity == null) {
         String var2 = "Fragment not added: " + var1;
         throw new IllegalStateException(var2);
      } else {
         var1.mImmediateActivity = null;
         BackStackRecord.Op var3 = new BackStackRecord.Op();
         var3.cmd = 3;
         var3.fragment = var1;
         this.addOp(var3);
         return this;
      }
   }

   public FragmentTransaction replace(int var1, Fragment var2) {
      return this.replace(var1, var2, (String)null);
   }

   public FragmentTransaction replace(int var1, Fragment var2, String var3) {
      if(var1 == 0) {
         throw new IllegalArgumentException("Must use non-zero containerViewId");
      } else {
         this.doAddOp(var1, var2, var3, 2);
         return this;
      }
   }

   public void run() {
      if(FragmentManagerImpl.DEBUG) {
         String var1 = "Run: " + this;
         int var2 = Log.v("BackStackEntry", var1);
      }

      if(this.mAddToBackStack && this.mIndex < 0) {
         throw new IllegalStateException("addToBackStack() called after commit()");
      } else {
         this.bumpBackStackNesting(1);

         for(BackStackRecord.Op var3 = this.mHead; var3 != null; var3 = var3.next) {
            switch(var3.cmd) {
            case 1:
               Fragment var7 = var3.fragment;
               int var8 = var3.enterAnim;
               var7.mNextAnim = var8;
               this.mManager.addFragment(var7, (boolean)0);
               break;
            case 2:
               Fragment var9 = var3.fragment;
               if(this.mManager.mAdded != null) {
                  int var10 = 0;

                  while(true) {
                     int var11 = this.mManager.mAdded.size();
                     if(var10 >= var11) {
                        break;
                     }

                     Fragment var12 = (Fragment)this.mManager.mAdded.get(var10);
                     if(FragmentManagerImpl.DEBUG) {
                        String var13 = "OP_REPLACE: adding=" + var9 + " old=" + var12;
                        int var14 = Log.v("BackStackEntry", var13);
                     }

                     int var15 = var12.mContainerId;
                     int var16 = var9.mContainerId;
                     if(var15 == var16) {
                        if(var3.removed == null) {
                           ArrayList var17 = new ArrayList();
                           var3.removed = var17;
                        }

                        var3.removed.add(var12);
                        int var19 = var3.exitAnim;
                        var12.mNextAnim = var19;
                        if(this.mAddToBackStack) {
                           int var20 = var12.mBackStackNesting + 1;
                           var12.mBackStackNesting = var20;
                           if(FragmentManagerImpl.DEBUG) {
                              StringBuilder var21 = (new StringBuilder()).append("Bump nesting of ").append(var12).append(" to ");
                              int var22 = var12.mBackStackNesting;
                              String var23 = var21.append(var22).toString();
                              int var24 = Log.v("BackStackEntry", var23);
                           }
                        }

                        FragmentManagerImpl var25 = this.mManager;
                        int var26 = this.mTransition;
                        int var27 = this.mTransitionStyle;
                        var25.removeFragment(var12, var26, var27);
                     }

                     ++var10;
                  }
               }

               int var28 = var3.enterAnim;
               var9.mNextAnim = var28;
               this.mManager.addFragment(var9, (boolean)0);
               break;
            case 3:
               Fragment var29 = var3.fragment;
               int var30 = var3.exitAnim;
               var29.mNextAnim = var30;
               FragmentManagerImpl var31 = this.mManager;
               int var32 = this.mTransition;
               int var33 = this.mTransitionStyle;
               var31.removeFragment(var29, var32, var33);
               break;
            case 4:
               Fragment var34 = var3.fragment;
               int var35 = var3.exitAnim;
               var34.mNextAnim = var35;
               FragmentManagerImpl var36 = this.mManager;
               int var37 = this.mTransition;
               int var38 = this.mTransitionStyle;
               var36.hideFragment(var34, var37, var38);
               break;
            case 5:
               Fragment var39 = var3.fragment;
               int var40 = var3.enterAnim;
               var39.mNextAnim = var40;
               FragmentManagerImpl var41 = this.mManager;
               int var42 = this.mTransition;
               int var43 = this.mTransitionStyle;
               var41.showFragment(var39, var42, var43);
               break;
            case 6:
               Fragment var44 = var3.fragment;
               int var45 = var3.exitAnim;
               var44.mNextAnim = var45;
               FragmentManagerImpl var46 = this.mManager;
               int var47 = this.mTransition;
               int var48 = this.mTransitionStyle;
               var46.detachFragment(var44, var47, var48);
               break;
            case 7:
               Fragment var49 = var3.fragment;
               int var50 = var3.enterAnim;
               var49.mNextAnim = var50;
               FragmentManagerImpl var51 = this.mManager;
               int var52 = this.mTransition;
               int var53 = this.mTransitionStyle;
               var51.attachFragment(var49, var52, var53);
               break;
            default:
               StringBuilder var4 = (new StringBuilder()).append("Unknown cmd: ");
               int var5 = var3.cmd;
               String var6 = var4.append(var5).toString();
               throw new IllegalArgumentException(var6);
            }
         }

         FragmentManagerImpl var54 = this.mManager;
         int var55 = this.mManager.mCurState;
         int var56 = this.mTransition;
         int var57 = this.mTransitionStyle;
         var54.moveToState(var55, var56, var57, (boolean)1);
         if(this.mAddToBackStack) {
            this.mManager.addBackStackState(this);
         }
      }
   }

   public FragmentTransaction setBreadCrumbShortTitle(int var1) {
      this.mBreadCrumbShortTitleRes = var1;
      this.mBreadCrumbShortTitleText = null;
      return this;
   }

   public FragmentTransaction setBreadCrumbShortTitle(CharSequence var1) {
      this.mBreadCrumbShortTitleRes = 0;
      this.mBreadCrumbShortTitleText = var1;
      return this;
   }

   public FragmentTransaction setBreadCrumbTitle(int var1) {
      this.mBreadCrumbTitleRes = var1;
      this.mBreadCrumbTitleText = null;
      return this;
   }

   public FragmentTransaction setBreadCrumbTitle(CharSequence var1) {
      this.mBreadCrumbTitleRes = 0;
      this.mBreadCrumbTitleText = var1;
      return this;
   }

   public FragmentTransaction setCustomAnimations(int var1, int var2) {
      this.mEnterAnim = var1;
      this.mExitAnim = var2;
      return this;
   }

   public FragmentTransaction setTransition(int var1) {
      this.mTransition = var1;
      return this;
   }

   public FragmentTransaction setTransitionStyle(int var1) {
      this.mTransitionStyle = var1;
      return this;
   }

   public FragmentTransaction show(Fragment var1) {
      if(var1.mImmediateActivity == null) {
         String var2 = "Fragment not added: " + var1;
         throw new IllegalStateException(var2);
      } else {
         BackStackRecord.Op var3 = new BackStackRecord.Op();
         var3.cmd = 5;
         var3.fragment = var1;
         this.addOp(var3);
         return this;
      }
   }

   static final class Op {

      int cmd;
      int enterAnim;
      int exitAnim;
      Fragment fragment;
      BackStackRecord.Op next;
      BackStackRecord.Op prev;
      ArrayList<Fragment> removed;


      Op() {}
   }
}
