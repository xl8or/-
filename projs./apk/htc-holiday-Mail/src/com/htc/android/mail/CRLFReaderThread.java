package com.htc.android.mail;

import android.os.Handler;
import android.os.Message;
import com.htc.android.mail.ByteString;
import java.io.InputStream;

public class CRLFReaderThread implements Runnable {

   static final byte CR = 13;
   static final byte LF = 10;
   String TAG = "CRLFReaderThread";
   byte[] mBuffer;
   Thread mContext;
   boolean mFinished;
   Handler mHandler;
   InputStream mInput = null;
   boolean mLastWasCR;
   boolean mWaitForNextCommand = 0;
   int mWhat;
   int mWriteCursor = 0;


   public CRLFReaderThread(InputStream var1, Handler var2, int var3) {
      this.mInput = var1;
      byte[] var4 = new byte[1024];
      this.mBuffer = var4;
      this.mHandler = var2;
      this.mWhat = var3;
   }

   private final void growIfNeeded() {
      int var1 = this.mWriteCursor;
      int var2 = this.mBuffer.length;
      if(var1 >= var2) {
         byte[] var3 = new byte[this.mBuffer.length * 2];
         byte[] var4 = this.mBuffer;
         int var5 = this.mWriteCursor;
         System.arraycopy(var4, 0, var3, 0, var5);
         this.mBuffer = var3;
      }
   }

   private final void issueLine(int var1) {
      byte[] var2 = this.mBuffer;
      ByteString var3 = new ByteString(var2, 0, var1);
      Handler var4 = this.mHandler;
      int var5 = this.mWhat;
      Message var6 = Message.obtain(var4, var5);
      var6.obj = var3;
      var6.sendToTarget();
      byte[] var7 = this.mBuffer;
      byte[] var8 = this.mBuffer;
      int var9 = this.mWriteCursor - var1;
      System.arraycopy(var7, var1, var8, 0, var9);
      int var10 = this.mWriteCursor - var1;
      this.mWriteCursor = var10;
   }

   private final void lookForCRLF(int var1) {
      int var2 = this.mWriteCursor;
      int var3 = this.mWriteCursor + var1;
      int var4 = this.mWriteCursor + var1;
      this.mWriteCursor = var4;

      int var7;
      for(int var5 = var2; var5 < var3; var5 = var7) {
         byte[] var6 = this.mBuffer;
         var7 = var5 + 1;
         byte var8 = var6[var5];
         if(var8 == 10) {
            if(this.mLastWasCR) {
               var3 -= var7;
               this.issueLine(var7);
               this.mLastWasCR = (boolean)0;
            }
         } else if(var8 == 13) {
            this.mLastWasCR = (boolean)1;
         } else {
            this.mLastWasCR = (boolean)0;
         }
      }

   }

   public final boolean isRunning() {
      boolean var1;
      if(!this.mFinished) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public final void run() {
      // $FF: Couldn't be decompiled
   }

   public final void start() {
      Thread var1 = new Thread(this, "CRLFReaderThread");
      this.mContext = var1;
      this.mContext.start();
   }

   public final void stop() {
      this.mFinished = (boolean)1;
      this.wakeUp();
   }

   public void waitForNextCommand() {
      this.mWaitForNextCommand = (boolean)1;
   }

   public void wakeUp() {
      this.mWaitForNextCommand = (boolean)0;
      synchronized(this) {
         this.notify();
      }
   }
}
