package com.android.volley;

import android.os.Handler;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ResponseDelivery;
import java.util.concurrent.Executor;

public class ExecutorDelivery implements ResponseDelivery {

   private int mDiscardBefore = 0;
   private final Executor mResponsePoster;


   public ExecutorDelivery(Handler var1) {
      ExecutorDelivery.1 var2 = new ExecutorDelivery.1(var1);
      this.mResponsePoster = var2;
   }

   public ExecutorDelivery(Executor var1) {
      this.mResponsePoster = var1;
   }

   public void discardBefore(int var1) {
      this.mDiscardBefore = var1;
   }

   public void postError(Request<?> var1, Response.ErrorCode var2, NetworkError var3) {
      var1.addMarker("post-error");
      String var4 = var3.displayError;
      Response var5 = Response.error(var2, var4, var3);
      Executor var6 = this.mResponsePoster;
      ExecutorDelivery.ResponseDeliveryRunnable var7 = new ExecutorDelivery.ResponseDeliveryRunnable(var1, var5);
      var6.execute(var7);
   }

   public void postResponse(Request<?> var1, Response<?> var2) {
      var1.addMarker("post-response");
      Executor var3 = this.mResponsePoster;
      ExecutorDelivery.ResponseDeliveryRunnable var4 = new ExecutorDelivery.ResponseDeliveryRunnable(var1, var2);
      var3.execute(var4);
   }

   class 1 implements Executor {

      // $FF: synthetic field
      final Handler val$handler;


      1(Handler var2) {
         this.val$handler = var2;
      }

      public void execute(Runnable var1) {
         this.val$handler.post(var1);
      }
   }

   private class ResponseDeliveryRunnable implements Runnable {

      private final Request mRequest;
      private final Response mResponse;


      public ResponseDeliveryRunnable(Request var2, Response var3) {
         this.mRequest = var2;
         this.mResponse = var3;
      }

      public void run() {
         boolean var3;
         label31: {
            if(this.mRequest.isDrainable()) {
               int var1 = this.mRequest.getSequence();
               int var2 = ExecutorDelivery.this.mDiscardBefore;
               if(var1 < var2) {
                  var3 = true;
                  break label31;
               }
            }

            var3 = false;
         }

         if(!var3 && !this.mRequest.isCanceled()) {
            Response.ErrorCode var4 = this.mResponse.errorCode;
            Response.ErrorCode var5 = Response.ErrorCode.OK;
            if(var4 == var5 && this.mResponse.result != null) {
               Request var6 = this.mRequest;
               Object var7 = this.mResponse.result;
               var6.deliverResponse(var7);
            } else {
               Request var8 = this.mRequest;
               Response.ErrorCode var9 = this.mResponse.errorCode;
               String var10 = this.mResponse.message;
               NetworkError var11 = this.mResponse.error;
               var8.deliverError(var9, var10, var11);
            }

            if(this.mResponse.intermediate) {
               this.mRequest.addMarker("intermediate-response");
            } else {
               this.mRequest.finish("done");
            }
         } else {
            this.mRequest.finish("canceled-at-delivery");
         }
      }
   }
}
