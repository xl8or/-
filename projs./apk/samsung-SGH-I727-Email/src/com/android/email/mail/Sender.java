package com.android.email.mail;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import com.android.email.activity.setup.AccountSetupOutgoing;
import com.android.email.mail.MessagingException;
import java.lang.reflect.Method;
import java.util.HashMap;

public abstract class Sender {

   protected static final int SOCKET_CONNECT_TIMEOUT = 10000;
   private static HashMap<String, Sender> mSenders = new HashMap();


   public Sender() {}

   private static Sender findSender(Context param0, int param1, String param2) throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public static Sender getInstance(Context var0, String var1) throws MessagingException {
      synchronized(Sender.class){}

      Sender var2;
      try {
         var2 = (Sender)mSenders.get(var1);
         if(var2 == null) {
            var2 = findSender(var0, 2131034136, var1);
            if(var2 == null) {
               var2 = findSender(var0, 2131034135, var1);
            }

            if(var2 != null) {
               mSenders.put(var1, var2);
            }
         }

         if(var2 == null) {
            String var4 = "Unable to locate an applicable Transport for " + var1;
            throw new MessagingException(var4);
         }
      } finally {
         ;
      }

      return var2;
   }

   private static Sender instantiateSender(Context var0, String var1, String var2) throws MessagingException {
      Object var7;
      try {
         Class var3 = Class.forName(var1);
         Class[] var4 = new Class[]{Context.class, String.class};
         Method var5 = var3.getMethod("newInstance", var4);
         Object[] var6 = new Object[]{var0, var2};
         var7 = var5.invoke((Object)null, var6);
      } catch (Exception var16) {
         Object[] var11 = new Object[3];
         String var12 = var16.toString();
         var11[0] = var12;
         var11[1] = var1;
         var11[2] = var2;
         String var13 = String.format("exception %s invoking %s.newInstance.(Context, String) method for %s", var11);
         int var14 = Log.d("Email", var13);
         String var15 = "can not instantiate Sender object for " + var2;
         throw new MessagingException(var15);
      }

      if(!(var7 instanceof Sender)) {
         String var9 = var2 + ": " + var1 + " create incompatible object";
         throw new MessagingException(var9);
      } else {
         return (Sender)var7;
      }
   }

   public static Sender newInstance(Context var0, String var1) throws MessagingException {
      String var2 = "Sender.newInstance: Unknown scheme in " + var1;
      throw new MessagingException(var2);
   }

   public void checkSenderLimitation(long var1) throws Sender.LimitViolationException {}

   public abstract void close() throws MessagingException;

   public Class<? extends Activity> getSettingActivityClass() {
      return AccountSetupOutgoing.class;
   }

   public abstract void open() throws MessagingException;

   public abstract void sendMessage(long var1) throws MessagingException;

   public String validateSenderLimit(long var1) {
      return null;
   }

   public static class LimitViolationException extends MessagingException {

      public final long mActual;
      public final long mLimit;
      public final int mMsgResourceId;


      private LimitViolationException(int var1, long var2, long var4) {
         super(0);
         this.mMsgResourceId = var1;
         this.mActual = var2;
         this.mLimit = var4;
      }

      public static void check(int var0, long var1, long var3) throws Sender.LimitViolationException {
         if(var1 > var3) {
            throw new Sender.LimitViolationException(var0, var1, var3);
         }
      }
   }
}
