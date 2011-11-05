package android.support.v4.app;

import android.app.Activity;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManagerImpl;
import android.support.v4.util.DebugUtils;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.view.animation.Animation;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.HashMap;

public class Fragment implements ComponentCallbacks, OnCreateContextMenuListener {

   static final int ACTIVITY_CREATED = 2;
   static final int CREATED = 1;
   static final int INITIALIZING = 0;
   static final int RESUMED = 5;
   static final int STARTED = 4;
   static final int STOPPED = 3;
   private static final HashMap<String, Class<?>> sClassMap = new HashMap();
   FragmentActivity mActivity;
   boolean mAdded;
   View mAnimatingAway;
   Bundle mArguments;
   int mBackStackNesting;
   boolean mCalled;
   boolean mCheckedForLoaderManager;
   ViewGroup mContainer;
   int mContainerId;
   boolean mDetached;
   int mFragmentId;
   FragmentManager mFragmentManager;
   boolean mFromLayout;
   boolean mHasMenu;
   boolean mHidden;
   FragmentActivity mImmediateActivity;
   boolean mInLayout;
   int mIndex = -1;
   View mInnerView;
   LoaderManagerImpl mLoaderManager;
   boolean mLoadersStarted;
   int mNextAnim;
   boolean mRemoving;
   boolean mRestored;
   boolean mResumed;
   boolean mRetainInstance;
   boolean mRetaining;
   Bundle mSavedFragmentState;
   SparseArray<Parcelable> mSavedViewState;
   int mState = 0;
   int mStateAfterAnimating;
   String mTag;
   Fragment mTarget;
   int mTargetIndex = -1;
   int mTargetRequestCode;
   View mView;
   String mWho;


   public Fragment() {}

   public static Fragment instantiate(Context var0, String var1) {
      return instantiate(var0, var1, (Bundle)null);
   }

   public static Fragment instantiate(Context var0, String var1, Bundle var2) {
      try {
         Class var3 = (Class)sClassMap.get(var1);
         if(var3 == null) {
            var3 = var0.getClassLoader().loadClass(var1);
            sClassMap.put(var1, var3);
         }

         Fragment var5 = (Fragment)var3.newInstance();
         if(var2 != null) {
            ClassLoader var6 = var5.getClass().getClassLoader();
            var2.setClassLoader(var6);
            var5.mArguments = var2;
         }

         return var5;
      } catch (ClassNotFoundException var13) {
         String var8 = "Unable to instantiate fragment " + var1 + ": make sure class name exists, is public, and has an" + " empty constructor that is public";
         throw new Fragment.InstantiationException(var8, var13);
      } catch (java.lang.InstantiationException var14) {
         String var10 = "Unable to instantiate fragment " + var1 + ": make sure class name exists, is public, and has an" + " empty constructor that is public";
         throw new Fragment.InstantiationException(var10, var14);
      } catch (IllegalAccessException var15) {
         String var12 = "Unable to instantiate fragment " + var1 + ": make sure class name exists, is public, and has an" + " empty constructor that is public";
         throw new Fragment.InstantiationException(var12, var15);
      }
   }

   public void dump(String var1, FileDescriptor var2, PrintWriter var3, String[] var4) {
      var3.print(var1);
      var3.print("mFragmentId=#");
      String var5 = Integer.toHexString(this.mFragmentId);
      var3.print(var5);
      var3.print(" mContainerId#=");
      String var6 = Integer.toHexString(this.mContainerId);
      var3.print(var6);
      var3.print(" mTag=");
      String var7 = this.mTag;
      var3.println(var7);
      var3.print(var1);
      var3.print("mState=");
      int var8 = this.mState;
      var3.print(var8);
      var3.print(" mIndex=");
      int var9 = this.mIndex;
      var3.print(var9);
      var3.print(" mWho=");
      String var10 = this.mWho;
      var3.print(var10);
      var3.print(" mBackStackNesting=");
      int var11 = this.mBackStackNesting;
      var3.println(var11);
      var3.print(var1);
      var3.print("mAdded=");
      boolean var12 = this.mAdded;
      var3.print(var12);
      var3.print(" mRemoving=");
      boolean var13 = this.mRemoving;
      var3.print(var13);
      var3.print(" mResumed=");
      boolean var14 = this.mResumed;
      var3.print(var14);
      var3.print(" mFromLayout=");
      boolean var15 = this.mFromLayout;
      var3.print(var15);
      var3.print(" mInLayout=");
      boolean var16 = this.mInLayout;
      var3.println(var16);
      var3.print(var1);
      var3.print("mHidden=");
      boolean var17 = this.mHidden;
      var3.print(var17);
      var3.print(" mDetached=");
      boolean var18 = this.mDetached;
      var3.print(var18);
      var3.print(" mRetainInstance=");
      boolean var19 = this.mRetainInstance;
      var3.print(var19);
      var3.print(" mRetaining=");
      boolean var20 = this.mRetaining;
      var3.print(var20);
      var3.print(" mHasMenu=");
      boolean var21 = this.mHasMenu;
      var3.println(var21);
      if(this.mFragmentManager != null) {
         var3.print(var1);
         var3.print("mFragmentManager=");
         FragmentManager var22 = this.mFragmentManager;
         var3.println(var22);
      }

      if(this.mImmediateActivity != null) {
         var3.print(var1);
         var3.print("mImmediateActivity=");
         FragmentActivity var23 = this.mImmediateActivity;
         var3.println(var23);
      }

      if(this.mActivity != null) {
         var3.print(var1);
         var3.print("mActivity=");
         FragmentActivity var24 = this.mActivity;
         var3.println(var24);
      }

      if(this.mArguments != null) {
         var3.print(var1);
         var3.print("mArguments=");
         Bundle var25 = this.mArguments;
         var3.println(var25);
      }

      if(this.mSavedFragmentState != null) {
         var3.print(var1);
         var3.print("mSavedFragmentState=");
         Bundle var26 = this.mSavedFragmentState;
         var3.println(var26);
      }

      if(this.mSavedViewState != null) {
         var3.print(var1);
         var3.print("mSavedViewState=");
         SparseArray var27 = this.mSavedViewState;
         var3.println(var27);
      }

      if(this.mTarget != null) {
         var3.print(var1);
         var3.print("mTarget=");
         Fragment var28 = this.mTarget;
         var3.print(var28);
         var3.print(" mTargetRequestCode=");
         int var29 = this.mTargetRequestCode;
         var3.println(var29);
      }

      if(this.mNextAnim != 0) {
         var3.print(var1);
         var3.print("mNextAnim=");
         int var30 = this.mNextAnim;
         var3.println(var30);
      }

      if(this.mContainer != null) {
         var3.print(var1);
         var3.print("mContainer=");
         ViewGroup var31 = this.mContainer;
         var3.println(var31);
      }

      if(this.mView != null) {
         var3.print(var1);
         var3.print("mView=");
         View var32 = this.mView;
         var3.println(var32);
      }

      if(this.mInnerView != null) {
         var3.print(var1);
         var3.print("mInnerView=");
         View var33 = this.mView;
         var3.println(var33);
      }

      if(this.mAnimatingAway != null) {
         var3.print(var1);
         var3.print("mAnimatingAway=");
         View var34 = this.mAnimatingAway;
         var3.println(var34);
         var3.print(var1);
         var3.print("mStateAfterAnimating=");
         int var35 = this.mStateAfterAnimating;
         var3.println(var35);
      }

      if(this.mLoaderManager != null) {
         var3.print(var1);
         var3.println("Loader Manager:");
         LoaderManagerImpl var36 = this.mLoaderManager;
         String var37 = var1 + "  ";
         var36.dump(var37, var2, var3, var4);
      }
   }

   public final boolean equals(Object var1) {
      return super.equals(var1);
   }

   public final FragmentActivity getActivity() {
      return this.mActivity;
   }

   public final Bundle getArguments() {
      return this.mArguments;
   }

   public final FragmentManager getFragmentManager() {
      return this.mFragmentManager;
   }

   public final int getId() {
      return this.mFragmentId;
   }

   public LayoutInflater getLayoutInflater(Bundle var1) {
      return this.mActivity.getLayoutInflater();
   }

   public LoaderManager getLoaderManager() {
      LoaderManagerImpl var1;
      if(this.mLoaderManager != null) {
         var1 = this.mLoaderManager;
      } else {
         if(this.mActivity == null) {
            String var2 = "Fragment " + this + " not attached to Activity";
            throw new IllegalStateException(var2);
         }

         this.mCheckedForLoaderManager = (boolean)1;
         FragmentActivity var3 = this.mActivity;
         int var4 = this.mIndex;
         boolean var5 = this.mLoadersStarted;
         LoaderManagerImpl var6 = var3.getLoaderManager(var4, var5, (boolean)1);
         this.mLoaderManager = var6;
         var1 = this.mLoaderManager;
      }

      return var1;
   }

   public final Resources getResources() {
      if(this.mActivity == null) {
         String var1 = "Fragment " + this + " not attached to Activity";
         throw new IllegalStateException(var1);
      } else {
         return this.mActivity.getResources();
      }
   }

   public final boolean getRetainInstance() {
      return this.mRetainInstance;
   }

   public final String getString(int var1) {
      return this.getResources().getString(var1);
   }

   public final String getString(int var1, Object ... var2) {
      return this.getResources().getString(var1, var2);
   }

   public final String getTag() {
      return this.mTag;
   }

   public final Fragment getTargetFragment() {
      return this.mTarget;
   }

   public final int getTargetRequestCode() {
      return this.mTargetRequestCode;
   }

   public final CharSequence getText(int var1) {
      return this.getResources().getText(var1);
   }

   public View getView() {
      return this.mView;
   }

   public final int hashCode() {
      return super.hashCode();
   }

   void initState() {
      this.mIndex = -1;
      this.mWho = null;
      this.mAdded = (boolean)0;
      this.mRemoving = (boolean)0;
      this.mResumed = (boolean)0;
      this.mFromLayout = (boolean)0;
      this.mInLayout = (boolean)0;
      this.mRestored = (boolean)0;
      this.mBackStackNesting = 0;
      this.mFragmentManager = null;
      this.mImmediateActivity = null;
      this.mActivity = null;
      this.mFragmentId = 0;
      this.mContainerId = 0;
      this.mTag = null;
      this.mHidden = (boolean)0;
      this.mDetached = (boolean)0;
      this.mRetaining = (boolean)0;
      this.mLoaderManager = null;
      this.mLoadersStarted = (boolean)0;
      this.mCheckedForLoaderManager = (boolean)0;
   }

   public final boolean isAdded() {
      boolean var1;
      if(this.mActivity != null && this.mAdded) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public final boolean isDetached() {
      return this.mDetached;
   }

   public final boolean isHidden() {
      return this.mHidden;
   }

   final boolean isInBackStack() {
      boolean var1;
      if(this.mBackStackNesting > 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public final boolean isInLayout() {
      return this.mInLayout;
   }

   public final boolean isRemoving() {
      return this.mRemoving;
   }

   public final boolean isResumed() {
      return this.mResumed;
   }

   public final boolean isVisible() {
      boolean var1;
      if(this.isAdded() && !this.isHidden() && this.mView != null && this.mView.getWindowToken() != null && this.mView.getVisibility() == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public void onActivityCreated(Bundle var1) {
      this.mCalled = (boolean)1;
   }

   public void onActivityResult(int var1, int var2, Intent var3) {}

   public void onAttach(Activity var1) {
      this.mCalled = (boolean)1;
   }

   public void onConfigurationChanged(Configuration var1) {
      this.mCalled = (boolean)1;
   }

   public boolean onContextItemSelected(MenuItem var1) {
      return false;
   }

   public void onCreate(Bundle var1) {
      this.mCalled = (boolean)1;
   }

   public Animation onCreateAnimation(int var1, boolean var2, int var3) {
      return null;
   }

   public void onCreateContextMenu(ContextMenu var1, View var2, ContextMenuInfo var3) {
      this.getActivity().onCreateContextMenu(var1, var2, var3);
   }

   public void onCreateOptionsMenu(Menu var1, MenuInflater var2) {}

   public View onCreateView(LayoutInflater var1, ViewGroup var2, Bundle var3) {
      return null;
   }

   public void onDestroy() {
      this.mCalled = (boolean)1;
      if(!this.mCheckedForLoaderManager) {
         this.mCheckedForLoaderManager = (boolean)1;
         FragmentActivity var1 = this.mActivity;
         int var2 = this.mIndex;
         boolean var3 = this.mLoadersStarted;
         LoaderManagerImpl var4 = var1.getLoaderManager(var2, var3, (boolean)0);
         this.mLoaderManager = var4;
      }

      if(this.mLoaderManager != null) {
         this.mLoaderManager.doDestroy();
      }
   }

   public void onDestroyOptionsMenu() {}

   public void onDestroyView() {
      this.mCalled = (boolean)1;
   }

   public void onDetach() {
      this.mCalled = (boolean)1;
   }

   public void onHiddenChanged(boolean var1) {}

   public void onInflate(Activity var1, AttributeSet var2, Bundle var3) {
      this.mCalled = (boolean)1;
   }

   public void onLowMemory() {
      this.mCalled = (boolean)1;
   }

   public boolean onOptionsItemSelected(MenuItem var1) {
      return false;
   }

   public void onOptionsMenuClosed(Menu var1) {}

   public void onPause() {
      this.mCalled = (boolean)1;
   }

   public void onPrepareOptionsMenu(Menu var1) {}

   public void onResume() {
      this.mCalled = (boolean)1;
   }

   public void onSaveInstanceState(Bundle var1) {}

   public void onStart() {
      this.mCalled = (boolean)1;
      if(!this.mLoadersStarted) {
         this.mLoadersStarted = (boolean)1;
         if(!this.mCheckedForLoaderManager) {
            this.mCheckedForLoaderManager = (boolean)1;
            FragmentActivity var1 = this.mActivity;
            int var2 = this.mIndex;
            boolean var3 = this.mLoadersStarted;
            LoaderManagerImpl var4 = var1.getLoaderManager(var2, var3, (boolean)0);
            this.mLoaderManager = var4;
         }

         if(this.mLoaderManager != null) {
            this.mLoaderManager.doStart();
         }
      }
   }

   public void onStop() {
      this.mCalled = (boolean)1;
   }

   public void onViewCreated(View var1, Bundle var2) {}

   void performDestroyView() {
      this.onDestroyView();
      if(this.mLoaderManager != null) {
         this.mLoaderManager.doReportNextStart();
      }
   }

   void performReallyStop(boolean var1) {
      if(this.mLoadersStarted) {
         this.mLoadersStarted = (boolean)0;
         if(!this.mCheckedForLoaderManager) {
            this.mCheckedForLoaderManager = (boolean)1;
            FragmentActivity var2 = this.mActivity;
            int var3 = this.mIndex;
            boolean var4 = this.mLoadersStarted;
            LoaderManagerImpl var5 = var2.getLoaderManager(var3, var4, (boolean)0);
            this.mLoaderManager = var5;
         }

         if(this.mLoaderManager != null) {
            if(!var1) {
               this.mLoaderManager.doStop();
            } else {
               this.mLoaderManager.doRetain();
            }
         }
      }
   }

   void performStart() {
      this.onStart();
      if(this.mLoaderManager != null) {
         this.mLoaderManager.doReportStart();
      }
   }

   void performStop() {
      this.onStop();
   }

   public void registerForContextMenu(View var1) {
      var1.setOnCreateContextMenuListener(this);
   }

   final void restoreViewState() {
      if(this.mSavedViewState != null) {
         View var1 = this.mInnerView;
         SparseArray var2 = this.mSavedViewState;
         var1.restoreHierarchyState(var2);
         this.mSavedViewState = null;
      }
   }

   public void setArguments(Bundle var1) {
      if(this.mIndex >= 0) {
         throw new IllegalStateException("Fragment already active");
      } else {
         this.mArguments = var1;
      }
   }

   public void setHasOptionsMenu(boolean var1) {
      if(this.mHasMenu != var1) {
         this.mHasMenu = var1;
         if(this.isAdded()) {
            if(!this.isHidden()) {
               this.mActivity.supportInvalidateOptionsMenu();
            }
         }
      }
   }

   final void setIndex(int var1) {
      this.mIndex = var1;
      StringBuilder var2 = (new StringBuilder()).append("android:fragment:");
      int var3 = this.mIndex;
      String var4 = var2.append(var3).toString();
      this.mWho = var4;
   }

   public void setInitialSavedState(Fragment.SavedState var1) {
      if(this.mIndex >= 0) {
         throw new IllegalStateException("Fragment already active");
      } else {
         Bundle var2;
         if(var1 != null && var1.mState != null) {
            var2 = var1.mState;
         } else {
            var2 = null;
         }

         this.mSavedFragmentState = var2;
      }
   }

   public void setRetainInstance(boolean var1) {
      this.mRetainInstance = var1;
   }

   public void setTargetFragment(Fragment var1, int var2) {
      this.mTarget = var1;
      this.mTargetRequestCode = var2;
   }

   public void startActivity(Intent var1) {
      if(this.mActivity == null) {
         String var2 = "Fragment " + this + " not attached to Activity";
         throw new IllegalStateException(var2);
      } else {
         this.mActivity.startActivityFromFragment(this, var1, -1);
      }
   }

   public void startActivityForResult(Intent var1, int var2) {
      if(this.mActivity == null) {
         String var3 = "Fragment " + this + " not attached to Activity";
         throw new IllegalStateException(var3);
      } else {
         this.mActivity.startActivityFromFragment(this, var1, var2);
      }
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder(128);
      DebugUtils.buildShortClassTag(this, var1);
      if(this.mIndex >= 0) {
         StringBuilder var2 = var1.append(" #");
         int var3 = this.mIndex;
         var1.append(var3);
      }

      if(this.mFragmentId != 0) {
         StringBuilder var5 = var1.append(" id=0x");
         String var6 = Integer.toHexString(this.mFragmentId);
         var1.append(var6);
      }

      if(this.mTag != null) {
         StringBuilder var8 = var1.append(" ");
         String var9 = this.mTag;
         var1.append(var9);
      }

      StringBuilder var11 = var1.append('}');
      return var1.toString();
   }

   public void unregisterForContextMenu(View var1) {
      var1.setOnCreateContextMenuListener((OnCreateContextMenuListener)null);
   }

   public static class SavedState implements Parcelable {

      public static final Creator<Fragment.SavedState> CREATOR = new Fragment.SavedState.1();
      final Bundle mState;


      SavedState(Bundle var1) {
         this.mState = var1;
      }

      SavedState(Parcel var1, ClassLoader var2) {
         Bundle var3 = var1.readBundle();
         this.mState = var3;
         if(var2 != null) {
            if(this.mState != null) {
               this.mState.setClassLoader(var2);
            }
         }
      }

      public int describeContents() {
         return 0;
      }

      public void writeToParcel(Parcel var1, int var2) {
         Bundle var3 = this.mState;
         var1.writeBundle(var3);
      }

      static class 1 implements Creator<Fragment.SavedState> {

         1() {}

         public Fragment.SavedState createFromParcel(Parcel var1) {
            return new Fragment.SavedState(var1, (ClassLoader)null);
         }

         public Fragment.SavedState[] newArray(int var1) {
            return new Fragment.SavedState[var1];
         }
      }
   }

   public static class InstantiationException extends RuntimeException {

      public InstantiationException(String var1, Exception var2) {
         super(var1, var2);
      }
   }
}
