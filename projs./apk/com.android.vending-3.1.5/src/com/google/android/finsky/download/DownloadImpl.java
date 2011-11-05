package com.google.android.finsky.download;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.download.Download;
import com.google.android.finsky.download.DownloadManager;
import com.google.android.finsky.download.DownloadProgress;
import com.google.android.finsky.download.DownloadRequest;
import com.google.android.finsky.download.InternalDownload;
import com.google.android.finsky.download.obb.Obb;
import com.google.android.finsky.download.obb.ObbDownloadListener;
import com.google.android.finsky.download.obb.ObbState;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Lists;
import com.google.android.finsky.utils.Notifier;
import com.google.android.finsky.utils.Utils;
import java.io.File;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class DownloadImpl implements InternalDownload {

   private final EnumSet<Download.DownloadState> COMPLETED_STATES;
   private final EnumSet<Download.DownloadState> UNSTARTED_STATES;
   private Uri mContentUri;
   private final String mCookieName;
   private final String mCookieValue;
   private DownloadManager mDownloadManager;
   private final Uri mFileUri;
   int mHttpStatus;
   private DownloadProgress mLastProgress;
   private final LinkedList<Download.DownloadListener> mListeners;
   private final Obb mMainObb;
   private Notifier mNotificationHelper;
   private final List<InternalDownload> mObbDownloads;
   private final Download.PackageProperties mPackageProperties;
   private final Obb mPatchObb;
   private long mSize;
   Download.DownloadState mState;
   private final String mTitle;
   private final String mUrl;


   public DownloadImpl(String var1, String var2, Download.PackageProperties var3, String var4, String var5, Uri var6, Notifier var7, long var8) {
      Download.DownloadState var10 = Download.DownloadState.CANCELLED;
      Download.DownloadState var11 = Download.DownloadState.ERROR;
      Download.DownloadState var12 = Download.DownloadState.SUCCESS;
      EnumSet var13 = EnumSet.of(var10, var11, var12);
      this.COMPLETED_STATES = var13;
      Download.DownloadState var14 = Download.DownloadState.UNQUEUED;
      Download.DownloadState var15 = Download.DownloadState.QUEUED;
      EnumSet var16 = EnumSet.of(var14, var15);
      this.UNSTARTED_STATES = var16;
      ArrayList var17 = Lists.newArrayList();
      this.mObbDownloads = var17;
      this.mUrl = var1;
      this.mTitle = var2;
      this.mPackageProperties = var3;
      if(var3 != null) {
         Obb var18 = var3.mainObb;
         this.mMainObb = var18;
         Obb var19 = var3.patchObb;
         this.mPatchObb = var19;
      } else {
         this.mMainObb = null;
         this.mPatchObb = null;
      }

      this.mCookieName = var4;
      this.mCookieValue = var5;
      LinkedList var20 = new LinkedList();
      this.mListeners = var20;
      this.mFileUri = var6;
      this.mNotificationHelper = var7;
      this.mSize = var8;
      Download.DownloadState var21 = Download.DownloadState.UNQUEUED;
      this.setState(var21);
   }

   private void addObbDownload(List<InternalDownload> var1, Obb var2, InternalDownload var3) {
      if(var3 != null) {
         ObbState var4 = ObbState.DOWNLOAD_PENDING;
         var2.setState(var4);
         var1.add(var3);
         this.mObbDownloads.add(var3);
      }
   }

   private void addObbListeners(Download var1, Download var2) {
      if(var1 != null) {
         if(var2 != null) {
            Obb var3 = this.mMainObb;
            Notifier var4 = this.mNotificationHelper;
            ObbDownloadListener var5 = new ObbDownloadListener(var1, var3, var2, var4);
            var1.addListener(var5);
         } else {
            Obb var9 = this.mMainObb;
            Notifier var10 = this.mNotificationHelper;
            ObbDownloadListener var11 = new ObbDownloadListener(var1, var9, this, var10);
            var1.addListener(var11);
         }
      }

      if(var2 != null) {
         Obb var6 = this.mPatchObb;
         Notifier var7 = this.mNotificationHelper;
         ObbDownloadListener var8 = new ObbDownloadListener(var2, var6, this, var7);
         var2.addListener(var8);
      }
   }

   private InternalDownload createObbDownload(Obb var1) {
      DownloadImpl var2 = null;
      if(var1 != null) {
         var1.syncStateWithStorage();
         if(!ObbState.shouldDownload(var1.getState())) {
            Object[] var3 = new Object[1];
            String var4 = var1.getState().toString();
            var3[0] = var4;
            FinskyLog.d("Skipping obb download with state %s", var3);
         } else {
            FinskyApp var5 = FinskyApp.get();
            File var6 = var1.getTempFile();
            boolean var7 = var6.delete();
            String var8 = var1.getUrl();
            Object[] var9 = new Object[1];
            String var10 = this.mTitle;
            var9[0] = var10;
            String var11 = var5.getString(2131231022, var9);
            String var12 = this.mCookieName;
            String var13 = this.mCookieValue;
            Uri var14 = Uri.fromFile(var6);
            Notifier var15 = this.mNotificationHelper;
            long var16 = var1.getSize();
            var2 = new DownloadImpl(var8, var11, (Download.PackageProperties)null, var12, var13, var14, var15, var16);
         }
      }

      return var2;
   }

   public void addListener(Download.DownloadListener var1) {
      Utils.ensureOnMainThread();
      this.mListeners.add(var1);
   }

   public void cancel() {
      Utils.ensureOnMainThread();
      if(!this.isCompleted()) {
         Download.DownloadState var1 = this.mState;
         Download.DownloadState var2 = Download.DownloadState.DOWNLOADING;
         if(var1.equals(var2)) {
            DownloadManager var3 = this.mDownloadManager;
            Uri var4 = this.mContentUri;
            var3.remove(var4);
         }

         Download.DownloadState var5 = Download.DownloadState.CANCELLED;
         this.setState(var5);
         Iterator var6 = this.mObbDownloads.iterator();

         while(var6.hasNext()) {
            InternalDownload var7 = (InternalDownload)var6.next();
            if(!var7.isCompleted()) {
               var7.cancel();
            }
         }

      }
   }

   public DownloadRequest createDownloadRequest(String var1, String var2) {
      Uri var3 = Uri.parse(this.getUrl());
      String var4 = this.mTitle;
      String var5 = this.mCookieName;
      String var6 = this.mCookieValue;
      Uri var7 = this.mFileUri;
      return new DownloadRequest(var3, var4, var1, var2, var5, var6, var7);
   }

   public boolean equals(Object var1) {
      byte var2;
      if(var1 == this) {
         var2 = 1;
      } else if(!(var1 instanceof DownloadImpl)) {
         var2 = 0;
      } else {
         DownloadImpl var3 = (DownloadImpl)var1;
         String var4 = this.mUrl;
         String var5 = var3.mUrl;
         var2 = var4.equals(var5);
      }

      return (boolean)var2;
   }

   public long getBytesCompleted() {
      long var1 = 0L;
      Utils.ensureOnMainThread();
      Download.DownloadState var3 = Download.DownloadState.SUCCESS;
      Download.DownloadState var4 = this.mState;
      if(var3.equals(var4)) {
         var1 = this.mSize;
      } else if(this.mLastProgress != null && this.mLastProgress.bytesCompleted >= 0L) {
         var1 = this.mLastProgress.bytesCompleted;
      }

      return var1;
   }

   public Uri getContentUri() {
      Utils.ensureOnMainThread();
      EnumSet var1 = this.UNSTARTED_STATES;
      Download.DownloadState var2 = this.mState;
      if(var1.contains(var2)) {
         throw new IllegalStateException("Attempted to fetch content uri when in an unstarted state.");
      } else {
         if(this.mContentUri == null) {
            Object[] var3 = new Object[0];
            FinskyLog.wtf("Called getContentUri when content uri is null.", var3);
         }

         return this.mContentUri;
      }
   }

   public Download.PackageProperties getPackageProperties() {
      Utils.ensureOnMainThread();
      return this.mPackageProperties;
   }

   public DownloadProgress getProgress() {
      Utils.ensureOnMainThread();
      return this.mLastProgress;
   }

   public Uri getRequestedDestination() {
      return this.mFileUri;
   }

   public long getSize() {
      return this.mSize;
   }

   public Download.DownloadState getState() {
      Utils.ensureOnMainThread();
      return this.mState;
   }

   public String getTitle() {
      return this.mTitle;
   }

   public String getUrl() {
      Utils.ensureOnMainThread();
      return this.mUrl;
   }

   public List<InternalDownload> getWrappedObbDownloads() {
      ArrayList var1 = Lists.newArrayList();
      Obb var2 = this.mMainObb;
      InternalDownload var3 = this.createObbDownload(var2);
      Obb var4 = this.mPatchObb;
      InternalDownload var5 = this.createObbDownload(var4);
      this.addObbListeners(var3, var5);
      Obb var6 = this.mMainObb;
      this.addObbDownload(var1, var6, var3);
      Obb var7 = this.mPatchObb;
      this.addObbDownload(var1, var7, var5);
      return var1;
   }

   public int hashCode() {
      return this.mUrl.hashCode();
   }

   public Uri internalGetContentUri() {
      Utils.ensureOnMainThread();
      return this.mContentUri;
   }

   public boolean isCompleted() {
      EnumSet var1 = this.COMPLETED_STATES;
      Download.DownloadState var2 = this.mState;
      return var1.contains(var2);
   }

   void notifyListeners(DownloadImpl.UpdateListenerType var1) {
      DownloadProgress var2 = this.mLastProgress;
      int[] var3 = DownloadImpl.7.$SwitchMap$com$google$android$finsky$download$DownloadImpl$UpdateListenerType;
      int var4 = var1.ordinal();
      Object var5;
      switch(var3[var4]) {
      case 1:
         var5 = new DownloadImpl.1(var1);
         break;
      case 2:
         var5 = new DownloadImpl.2(var1);
         break;
      case 3:
         var5 = new DownloadImpl.3(var1, var2);
         break;
      case 4:
         var5 = new DownloadImpl.4(var1);
         break;
      case 5:
         var5 = new DownloadImpl.5(var1);
         break;
      case 6:
         var5 = new DownloadImpl.6(var1);
         break;
      default:
         throw new IllegalStateException("Bad listener type.");
      }

      Looper var6 = Looper.getMainLooper();
      boolean var7 = (new Handler(var6)).post((Runnable)var5);
   }

   public void onNotificationClicked() {
      DownloadImpl.UpdateListenerType var1 = DownloadImpl.UpdateListenerType.NOTIFICATION_CLICKED;
      this.notifyListeners(var1);
   }

   public void removeListener(Download.DownloadListener var1) {
      Utils.ensureOnMainThread();
      this.mListeners.remove(var1);
   }

   public void setContentUri(Uri var1) {
      this.mContentUri = var1;
   }

   public void setDownloadManager(DownloadManager var1) {
      this.mDownloadManager = var1;
   }

   public void setHttpStatus(int var1) {
      this.mHttpStatus = var1;
   }

   public void setState(Download.DownloadState var1) {
      if(this.isCompleted()) {
         throw new IllegalStateException("Received state update when already completed.");
      } else {
         if(this.mState == var1) {
            Object[] var2 = new Object[2];
            String var3 = this.mTitle;
            var2[0] = var3;
            Download.DownloadState var4 = this.mState;
            var2[1] = var4;
            FinskyLog.d("Duplicate state set for \'%s\' (%s). Already in that state", var2);
         }

         Object[] var5 = new Object[3];
         String var6 = this.mTitle;
         var5[0] = var6;
         Download.DownloadState var7 = this.mState;
         var5[1] = var7;
         var5[2] = var1;
         FinskyLog.d("Download \'%s\' transitioned from (%s) to (%s).", var5);
         this.mState = var1;
         int[] var8 = DownloadImpl.7.$SwitchMap$com$google$android$finsky$download$Download$DownloadState;
         int var9 = this.mState.ordinal();
         switch(var8[var9]) {
         case 1:
            DownloadImpl.UpdateListenerType var10 = DownloadImpl.UpdateListenerType.START;
            this.notifyListeners(var10);
            return;
         case 2:
            DownloadImpl.UpdateListenerType var11 = DownloadImpl.UpdateListenerType.CANCEL;
            this.notifyListeners(var11);
            return;
         case 3:
            DownloadImpl.UpdateListenerType var12 = DownloadImpl.UpdateListenerType.ERROR;
            this.notifyListeners(var12);
            return;
         case 4:
            DownloadImpl.UpdateListenerType var13 = DownloadImpl.UpdateListenerType.COMPLETE;
            this.notifyListeners(var13);
            return;
         default:
         }
      }
   }

   public String toString() {
      String var1;
      if(this.mTitle == null) {
         var1 = "(untitled download)";
      } else {
         StringBuilder var2 = (new StringBuilder()).append("Download : ");
         String var3 = this.mTitle;
         var1 = var2.append(var3).toString();
      }

      return var1;
   }

   public void updateDownloadProgress(DownloadProgress var1) {
      Utils.ensureOnMainThread();
      DownloadProgress var2 = this.mLastProgress;
      if(!var1.equals(var2)) {
         this.mLastProgress = var1;
         DownloadImpl.UpdateListenerType var3 = DownloadImpl.UpdateListenerType.PROGRESS;
         this.notifyListeners(var3);
      }
   }

   private abstract class ListenerNotifier implements Runnable {

      DownloadImpl.UpdateListenerType mType;


      public ListenerNotifier(DownloadImpl.UpdateListenerType var2) {
         this.mType = var2;
      }

      public void run() {
         Iterator var1 = DownloadImpl.this.mListeners.iterator();

         while(var1.hasNext()) {
            Download.DownloadListener var2 = (Download.DownloadListener)var1.next();

            try {
               this.updateListener(var2);
            } catch (Exception var8) {
               StringBuilder var4 = (new StringBuilder()).append("Download listener threw an exception during ");
               DownloadImpl.UpdateListenerType var5 = this.mType;
               String var6 = var4.append(var5).toString();
               Object[] var7 = new Object[0];
               FinskyLog.wtf(var8, var6, var7);
            }
         }

      }

      abstract void updateListener(Download.DownloadListener var1);
   }

   class 1 extends DownloadImpl.ListenerNotifier {

      1(DownloadImpl.UpdateListenerType var2) {
         super(var2);
      }

      public void updateListener(Download.DownloadListener var1) {
         var1.onNotificationClicked();
      }
   }

   static enum UpdateListenerType {

      // $FF: synthetic field
      private static final DownloadImpl.UpdateListenerType[] $VALUES;
      CANCEL("CANCEL", 3),
      COMPLETE("COMPLETE", 1),
      ERROR("ERROR", 5),
      NOTIFICATION_CLICKED("NOTIFICATION_CLICKED", 0),
      PROGRESS("PROGRESS", 2),
      START("START", 4);


      static {
         DownloadImpl.UpdateListenerType[] var0 = new DownloadImpl.UpdateListenerType[6];
         DownloadImpl.UpdateListenerType var1 = NOTIFICATION_CLICKED;
         var0[0] = var1;
         DownloadImpl.UpdateListenerType var2 = COMPLETE;
         var0[1] = var2;
         DownloadImpl.UpdateListenerType var3 = PROGRESS;
         var0[2] = var3;
         DownloadImpl.UpdateListenerType var4 = CANCEL;
         var0[3] = var4;
         DownloadImpl.UpdateListenerType var5 = START;
         var0[4] = var5;
         DownloadImpl.UpdateListenerType var6 = ERROR;
         var0[5] = var6;
         $VALUES = var0;
      }

      private UpdateListenerType(String var1, int var2) {}
   }

   class 2 extends DownloadImpl.ListenerNotifier {

      2(DownloadImpl.UpdateListenerType var2) {
         super(var2);
      }

      public void updateListener(Download.DownloadListener var1) {
         DownloadImpl var2 = DownloadImpl.this;
         var1.onComplete(var2);
      }
   }

   class 3 extends DownloadImpl.ListenerNotifier {

      // $FF: synthetic field
      final DownloadProgress val$currentProgress;


      3(DownloadImpl.UpdateListenerType var2, DownloadProgress var3) {
         super(var2);
         this.val$currentProgress = var3;
      }

      public void updateListener(Download.DownloadListener var1) {
         DownloadProgress var2 = this.val$currentProgress;
         var1.onProgress(var2);
      }
   }

   class 4 extends DownloadImpl.ListenerNotifier {

      4(DownloadImpl.UpdateListenerType var2) {
         super(var2);
      }

      public void updateListener(Download.DownloadListener var1) {
         var1.onCancel();
      }
   }

   class 5 extends DownloadImpl.ListenerNotifier {

      5(DownloadImpl.UpdateListenerType var2) {
         super(var2);
      }

      public void updateListener(Download.DownloadListener var1) {
         int var2 = DownloadImpl.this.mHttpStatus;
         var1.onError(var2);
      }
   }

   // $FF: synthetic class
   static class 7 {

      // $FF: synthetic field
      static final int[] $SwitchMap$com$google$android$finsky$download$Download$DownloadState = new int[Download.DownloadState.values().length];
      // $FF: synthetic field
      static final int[] $SwitchMap$com$google$android$finsky$download$DownloadImpl$UpdateListenerType;


      static {
         try {
            int[] var0 = $SwitchMap$com$google$android$finsky$download$Download$DownloadState;
            int var1 = Download.DownloadState.DOWNLOADING.ordinal();
            var0[var1] = 1;
         } catch (NoSuchFieldError var47) {
            ;
         }

         try {
            int[] var2 = $SwitchMap$com$google$android$finsky$download$Download$DownloadState;
            int var3 = Download.DownloadState.CANCELLED.ordinal();
            var2[var3] = 2;
         } catch (NoSuchFieldError var46) {
            ;
         }

         try {
            int[] var4 = $SwitchMap$com$google$android$finsky$download$Download$DownloadState;
            int var5 = Download.DownloadState.ERROR.ordinal();
            var4[var5] = 3;
         } catch (NoSuchFieldError var45) {
            ;
         }

         try {
            int[] var6 = $SwitchMap$com$google$android$finsky$download$Download$DownloadState;
            int var7 = Download.DownloadState.SUCCESS.ordinal();
            var6[var7] = 4;
         } catch (NoSuchFieldError var44) {
            ;
         }

         try {
            int[] var8 = $SwitchMap$com$google$android$finsky$download$Download$DownloadState;
            int var9 = Download.DownloadState.QUEUED.ordinal();
            var8[var9] = 5;
         } catch (NoSuchFieldError var43) {
            ;
         }

         try {
            int[] var10 = $SwitchMap$com$google$android$finsky$download$Download$DownloadState;
            int var11 = Download.DownloadState.UNQUEUED.ordinal();
            var10[var11] = 6;
         } catch (NoSuchFieldError var42) {
            ;
         }

         $SwitchMap$com$google$android$finsky$download$DownloadImpl$UpdateListenerType = new int[DownloadImpl.UpdateListenerType.values().length];

         try {
            int[] var12 = $SwitchMap$com$google$android$finsky$download$DownloadImpl$UpdateListenerType;
            int var13 = DownloadImpl.UpdateListenerType.NOTIFICATION_CLICKED.ordinal();
            var12[var13] = 1;
         } catch (NoSuchFieldError var41) {
            ;
         }

         try {
            int[] var14 = $SwitchMap$com$google$android$finsky$download$DownloadImpl$UpdateListenerType;
            int var15 = DownloadImpl.UpdateListenerType.COMPLETE.ordinal();
            var14[var15] = 2;
         } catch (NoSuchFieldError var40) {
            ;
         }

         try {
            int[] var16 = $SwitchMap$com$google$android$finsky$download$DownloadImpl$UpdateListenerType;
            int var17 = DownloadImpl.UpdateListenerType.PROGRESS.ordinal();
            var16[var17] = 3;
         } catch (NoSuchFieldError var39) {
            ;
         }

         try {
            int[] var18 = $SwitchMap$com$google$android$finsky$download$DownloadImpl$UpdateListenerType;
            int var19 = DownloadImpl.UpdateListenerType.CANCEL.ordinal();
            var18[var19] = 4;
         } catch (NoSuchFieldError var38) {
            ;
         }

         try {
            int[] var20 = $SwitchMap$com$google$android$finsky$download$DownloadImpl$UpdateListenerType;
            int var21 = DownloadImpl.UpdateListenerType.ERROR.ordinal();
            var20[var21] = 5;
         } catch (NoSuchFieldError var37) {
            ;
         }

         try {
            int[] var22 = $SwitchMap$com$google$android$finsky$download$DownloadImpl$UpdateListenerType;
            int var23 = DownloadImpl.UpdateListenerType.START.ordinal();
            var22[var23] = 6;
         } catch (NoSuchFieldError var36) {
            ;
         }
      }
   }

   class 6 extends DownloadImpl.ListenerNotifier {

      6(DownloadImpl.UpdateListenerType var2) {
         super(var2);
      }

      public void updateListener(Download.DownloadListener var1) {
         var1.onStart();
      }
   }
}
