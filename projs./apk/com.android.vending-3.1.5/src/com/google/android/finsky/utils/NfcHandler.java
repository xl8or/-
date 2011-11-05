package com.google.android.finsky.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import com.google.android.finsky.activities.DetailsFragment;
import com.google.android.finsky.api.model.Document;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class NfcHandler implements InvocationHandler {

   private static final String CALLBACK_CLASS = "android.nfc.NfcAdapter$NdefPushCallback";
   private static final boolean DBG = false;
   private static final String DISABLE_METHOD = "disableForegroundNdefPush";
   private static final String ENABLE_METHOD = "enableForegroundNdefPush";
   private static final String GETDEFADAPTER_METHOD = "getDefaultAdapter";
   private static final String NDEFMESSAGE_CLASS = "android.nfc.NdefMessage";
   private static final String NDEFRECORD_CLASS = "android.nfc.NdefRecord";
   private static final String NFCADAPTER_CLASS = "android.nfc.NfcAdapter";
   private static final String TAG = "FinskyNfcHandler";
   private static Method mDisableMethod;
   private static Method mEnableMethod;
   private static Constructor<?> mNdefMessageConstructor;
   private static Class<?> mNdefRecordClass;
   private static Constructor<?> mNdefRecordConstructor;
   private boolean mEnabled;
   private DetailsFragment mFragment;
   private boolean mHasCallback;
   private Object mNfcAdapter;
   private Object mProxy;


   public NfcHandler(DetailsFragment var1) {
      this.mFragment = var1;

      Class var2;
      try {
         var2 = Class.forName("android.nfc.NfcAdapter$NdefPushCallback");
         this.mHasCallback = (boolean)1;
      } catch (ClassNotFoundException var31) {
         this.mHasCallback = (boolean)0;
      }

      try {
         Class var3 = Class.forName("android.nfc.NfcAdapter");
         Class[] var4 = new Class[]{Context.class};
         Method var5 = var3.getMethod("getDefaultAdapter", var4);
         Object[] var6 = new Object[1];
         FragmentActivity var7 = var1.getActivity();
         var6[0] = var7;
         Object var8 = var5.invoke((Object)null, var6);
         mNdefRecordClass = Class.forName("android.nfc.NdefRecord");
         Class var9 = mNdefRecordClass;
         Class[] var10 = new Class[4];
         Class var11 = Short.TYPE;
         var10[0] = var11;
         var10[1] = byte[].class;
         var10[2] = byte[].class;
         var10[3] = byte[].class;
         mNdefRecordConstructor = var9.getConstructor(var10);
         Class var12 = Class.forName("[Landroid.nfc.NdefRecord;");
         Class var13 = Class.forName("android.nfc.NdefMessage");
         Class[] var14 = new Class[]{var12};
         mNdefMessageConstructor = var13.getConstructor(var14);
         if(this.mHasCallback) {
            ClassLoader var15 = var2.getClassLoader();
            Class[] var16 = new Class[]{var2};
            Object var17 = Proxy.newProxyInstance(var15, var16, this);
            this.mProxy = var17;
            Class[] var18 = new Class[]{Activity.class, var2};
            mEnableMethod = var3.getMethod("enableForegroundNdefPush", var18);
            Class[] var19 = new Class[]{Activity.class};
            mDisableMethod = var3.getMethod("disableForegroundNdefPush", var19);
         } else {
            Class[] var21 = new Class[]{Activity.class, var13};
            mEnableMethod = var3.getMethod("enableForegroundNdefPush", var21);
            Class[] var22 = new Class[]{Activity.class};
            mDisableMethod = var3.getMethod("disableForegroundNdefPush", var22);
         }

         this.mNfcAdapter = var8;
      } catch (ClassNotFoundException var27) {
         ;
      } catch (NoSuchMethodException var28) {
         ;
      } catch (IllegalAccessException var29) {
         ;
      } catch (InvocationTargetException var30) {
         ;
      }
   }

   private Object createMessage() {
      Object var1 = null;
      Document var2 = this.mFragment.getDocument();
      if(var2 != null) {
         String var3 = var2.getShareUrl();

         Object var16;
         try {
            byte[] var4 = var3.getBytes("UTF-8");
            byte[] var5 = new byte[var4.length + 1];
            var5[0] = 0;
            int var6 = var4.length;
            System.arraycopy(var4, 0, var5, 1, var6);
            Constructor var7 = mNdefRecordConstructor;
            Object[] var8 = new Object[4];
            Short var9 = Short.valueOf((short)1);
            var8[0] = var9;
            byte[] var10 = new byte[]{(byte)85};
            var8[1] = var10;
            byte[] var11 = new byte[0];
            var8[2] = var11;
            var8[3] = var5;
            Object var12 = var7.newInstance(var8);
            Object var13 = Array.newInstance(mNdefRecordClass, 1);
            Array.set(var13, 0, var12);
            Constructor var14 = mNdefMessageConstructor;
            Object[] var15 = new Object[]{var13};
            var16 = var14.newInstance(var15);
         } catch (UnsupportedEncodingException var21) {
            return var1;
         } catch (InstantiationException var22) {
            return var1;
         } catch (IllegalAccessException var23) {
            return var1;
         } catch (InvocationTargetException var24) {
            return var1;
         }

         var1 = var16;
      }

      return var1;
   }

   public Object invoke(Object var1, Method var2, Object[] var3) throws Throwable {
      Object var4;
      if(var2.getName().equals("createMessage")) {
         var4 = this.createMessage();
      } else {
         var4 = null;
      }

      return var4;
   }

   public void onDataChanged() {
      if(this.mNfcAdapter != null) {
         if(!this.mHasCallback) {
            Object var1 = this.createMessage();
            if(var1 != null) {
               try {
                  Method var2 = mEnableMethod;
                  Object var3 = this.mNfcAdapter;
                  Object[] var4 = new Object[2];
                  FragmentActivity var5 = this.mFragment.getActivity();
                  var4[0] = var5;
                  var4[1] = var1;
                  var2.invoke(var3, var4);
                  this.mEnabled = (boolean)1;
               } catch (IllegalAccessException var9) {
                  ;
               } catch (InvocationTargetException var10) {
                  ;
               }
            }
         }
      }
   }

   public void onPause() {
      if(this.mNfcAdapter != null) {
         try {
            if(this.mEnabled) {
               Method var1 = mDisableMethod;
               Object var2 = this.mNfcAdapter;
               Object[] var3 = new Object[1];
               FragmentActivity var4 = this.mFragment.getActivity();
               var3[0] = var4;
               var1.invoke(var2, var3);
               this.mEnabled = (boolean)0;
            }
         } catch (IllegalAccessException var8) {
            ;
         } catch (InvocationTargetException var9) {
            ;
         }
      }
   }

   public void onResume() {
      if(this.mNfcAdapter != null) {
         try {
            if(this.mHasCallback) {
               Method var1 = mEnableMethod;
               Object var2 = this.mNfcAdapter;
               Object[] var3 = new Object[2];
               FragmentActivity var4 = this.mFragment.getActivity();
               var3[0] = var4;
               Object var5 = this.mProxy;
               var3[1] = var5;
               var1.invoke(var2, var3);
               this.mEnabled = (boolean)1;
            } else {
               this.onDataChanged();
            }
         } catch (IllegalAccessException var9) {
            ;
         } catch (InvocationTargetException var10) {
            ;
         }
      }
   }
}
