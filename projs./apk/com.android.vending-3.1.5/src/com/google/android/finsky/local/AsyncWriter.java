package com.google.android.finsky.local;

import com.google.android.finsky.local.PersistentAssetStore;
import com.google.android.finsky.local.Writable;
import com.google.android.finsky.local.Writer;
import com.google.android.finsky.utils.FinskyLog;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class AsyncWriter implements Writer {

   private volatile boolean mDead = 0;
   private final BlockingQueue<Writer.Item> mQueue;
   protected final Thread mThread;


   public AsyncWriter(PersistentAssetStore var1) {
      LinkedBlockingQueue var2 = new LinkedBlockingQueue();
      this.mQueue = var2;
      AsyncWriter.1 var3 = new AsyncWriter.1(var1);
      Thread var4 = new Thread(var3);
      this.mThread = var4;
      this.mThread.setDaemon((boolean)1);
      this.mThread.start();
   }

   private void mainLoop(PersistentAssetStore var1) {
      boolean var2 = true;

      while(var2) {
         try {
            Writer.Item var3 = (Writer.Item)this.mQueue.take();
            int[] var4 = AsyncWriter.2.$SwitchMap$com$google$android$finsky$local$Writer$Op;
            int var5 = var3.mOperation.ordinal();
            switch(var4[var5]) {
            case 1:
               break;
            default:
               if(var3.mWritable != null) {
                  Writable var6 = var3.mWritable;
                  Writer.Op var7 = var3.mOperation;
                  var6.write(var1, var7);
               } else {
                  StringBuilder var10 = (new StringBuilder()).append("AsyncWriter asked to write with operation ");
                  String var11 = var3.mOperation.name();
                  String var12 = var10.append(var11).append(" and null writeable").toString();
                  Object[] var13 = new Object[0];
                  FinskyLog.w(var12, var13);
               }
               continue;
            }
         } catch (InterruptedException var14) {
            Object[] var9 = new Object[0];
            FinskyLog.wtf(var14, "Interrupted!", var9);
            continue;
         }

         var2 = false;
      }

   }

   public void delete(Writable var1) {
      this.write(var1, (boolean)0);
   }

   public void insert(Writable var1) {
      this.write(var1, (boolean)1);
   }

   public void kill() {
      this.mDead = (boolean)1;
      BlockingQueue var1 = this.mQueue;
      Writer.Op var2 = Writer.Op.POISON;
      Writer.Item var3 = new Writer.Item(var2, (Writable)null);
      var1.offer(var3);
   }

   protected void write(Writable var1, boolean var2) {
      if(this.mDead) {
         throw new IllegalStateException("Can\'t queue operations when writer is dead!");
      } else {
         BlockingQueue var3 = this.mQueue;
         Writer.Item var4 = new Writer.Item;
         Writer.Op var5;
         if(var2) {
            var5 = Writer.Op.INSERT;
         } else {
            var5 = Writer.Op.DELETE;
         }

         var4.<init>(var5, var1);
         var3.offer(var4);
      }
   }

   class 1 implements Runnable {

      // $FF: synthetic field
      final PersistentAssetStore val$persistentStore;


      1(PersistentAssetStore var2) {
         this.val$persistentStore = var2;
      }

      public void run() {
         AsyncWriter var1 = AsyncWriter.this;
         PersistentAssetStore var2 = this.val$persistentStore;
         var1.mainLoop(var2);
      }
   }

   // $FF: synthetic class
   static class 2 {

      // $FF: synthetic field
      static final int[] $SwitchMap$com$google$android$finsky$local$Writer$Op = new int[Writer.Op.values().length];


      static {
         try {
            int[] var0 = $SwitchMap$com$google$android$finsky$local$Writer$Op;
            int var1 = Writer.Op.POISON.ordinal();
            var0[var1] = 1;
         } catch (NoSuchFieldError var3) {
            ;
         }
      }
   }
}
