package com.google.android.finsky.utils;

import android.opengl.GLES10;
import android.text.TextUtils;
import com.google.android.finsky.config.PreferenceFile;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Lists;
import com.google.android.finsky.utils.Sets;
import com.google.android.finsky.utils.VendingPreferences;
import com.google.android.finsky.utils.VendingUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;

public class GlExtensionReader {

   private boolean mWasSystemUpgraded;


   public GlExtensionReader() {
      boolean var1 = VendingUtils.wasSystemUpgraded();
      this.mWasSystemUpgraded = var1;
   }

   GlExtensionReader(boolean var1) {
      this.mWasSystemUpgraded = var1;
   }

   private static void addExtensionsForConfig(EGL10 var0, EGLDisplay var1, EGLConfig var2, int[] var3, int[] var4, Set<String> var5) {
      EGLContext var6 = EGL10.EGL_NO_CONTEXT;
      EGLContext var7 = var0.eglCreateContext(var1, var2, var6, var4);
      EGLContext var8 = EGL10.EGL_NO_CONTEXT;
      if(var7 != var8) {
         EGLSurface var9 = var0.eglCreatePbufferSurface(var1, var2, var3);
         EGLSurface var10 = EGL10.EGL_NO_SURFACE;
         if(var9 == var10) {
            var0.eglDestroyContext(var1, var7);
         } else {
            var0.eglMakeCurrent(var1, var9, var9, var7);
            String var13 = GLES10.glGetString(7939);
            if(!TextUtils.isEmpty(var13)) {
               String[] var14 = var13.split(" ");
               int var15 = var14.length;

               for(int var16 = 0; var16 < var15; ++var16) {
                  String var17 = var14[var16];
                  var5.add(var17);
               }
            }

            EGLSurface var19 = EGL10.EGL_NO_SURFACE;
            EGLSurface var20 = EGL10.EGL_NO_SURFACE;
            var0.eglMakeCurrent(var1, var19, var20, var7);
            var0.eglDestroySurface(var1, var9);
            var0.eglDestroyContext(var1, var7);
         }
      }
   }

   public List<String> getGlExtensions() {
      ArrayList var1 = Lists.newArrayList();
      String var2 = (String)VendingPreferences.CACHED_GL_EXTENSIONS.get();
      if(!this.mWasSystemUpgraded && !TextUtils.isEmpty(var2)) {
         List var3 = Arrays.asList(var2.split(" "));
         var1.addAll(var3);
      } else {
         Object var5;
         if(((Boolean)VendingPreferences.GL_DRIVER_CRASHED.get()).booleanValue() && !this.mWasSystemUpgraded) {
            var5 = Sets.newHashSet();
            boolean var6 = ((Set)var5).add("_android_driver_crashed");
         } else {
            PreferenceFile.SharedPreference var9 = VendingPreferences.GL_DRIVER_CRASHED;
            Boolean var10 = Boolean.valueOf((boolean)1);
            var9.put(var10);
            var5 = this.getGlExtensionsFromDriver();
            VendingPreferences.GL_DRIVER_CRASHED.remove();
         }

         var1.addAll((Collection)var5);
         Collections.sort(var1);
         String var8 = TextUtils.join(" ", var1);
         VendingPreferences.CACHED_GL_EXTENSIONS.put(var8);
      }

      return var1;
   }

   Set<String> getGlExtensionsFromDriver() {
      HashSet var1 = new HashSet();
      EGL10 var2 = (EGL10)EGLContext.getEGL();
      if(var2 == null) {
         Object[] var3 = new Object[0];
         FinskyLog.e("Couldn\'t get EGL", var3);
      } else {
         Object var4 = EGL10.EGL_DEFAULT_DISPLAY;
         EGLDisplay var5 = var2.eglGetDisplay(var4);
         int[] var6 = new int[2];
         var2.eglInitialize(var5, var6);
         int[] var8 = new int[1];
         if(!var2.eglGetConfigs(var5, (EGLConfig[])null, 0, var8)) {
            Object[] var9 = new Object[0];
            FinskyLog.e("Couldn\'t get EGL config count", var9);
         } else {
            EGLConfig[] var10 = new EGLConfig[var8[0]];
            int var11 = var8[0];
            if(!var2.eglGetConfigs(var5, var10, var11, var8)) {
               Object[] var12 = new Object[0];
               FinskyLog.e("Couldn\'t get EGL configs", var12);
            } else {
               int[] var13 = new int[]{12375, 1, 12374, 1, 12344};
               int[] var14 = new int[]{12440, 2, 12344};
               int[] var15 = new int[1];
               int var16 = 0;

               while(true) {
                  int var17 = var8[0];
                  if(var16 >= var17) {
                     var2.eglTerminate(var5);
                     break;
                  }

                  EGLConfig var18 = var10[var16];
                  var2.eglGetConfigAttrib(var5, var18, 12327, var15);
                  if(var15[0] != 12368) {
                     EGLConfig var20 = var10[var16];
                     var2.eglGetConfigAttrib(var5, var20, 12352, var15);
                     if((var15[0] & 1) != 0) {
                        EGLConfig var22 = var10[var16];
                        addExtensionsForConfig(var2, var5, var22, var13, (int[])null, var1);
                     }

                     if((var15[0] & 4) != 0) {
                        EGLConfig var23 = var10[var16];
                        addExtensionsForConfig(var2, var5, var23, var13, var14, var1);
                     }
                  }

                  ++var16;
               }
            }
         }
      }

      return var1;
   }
}
