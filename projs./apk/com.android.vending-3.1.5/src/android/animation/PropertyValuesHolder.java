package android.animation;

import android.animation.DoubleEvaluator;
import android.animation.FloatEvaluator;
import android.animation.IntEvaluator;
import android.animation.Keyframe;
import android.animation.KeyframeSet;
import android.animation.TypeEvaluator;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class PropertyValuesHolder implements Cloneable {

   private static Class[] DOUBLE_VARIANTS;
   private static Class[] FLOAT_VARIANTS;
   private static Class[] INTEGER_VARIANTS;
   private static final TypeEvaluator sDoubleEvaluator = new DoubleEvaluator();
   private static final TypeEvaluator sFloatEvaluator = new FloatEvaluator();
   private static final HashMap<Class, HashMap<String, Method>> sGetterPropertyMap;
   private static final TypeEvaluator sIntEvaluator = new IntEvaluator();
   private static final HashMap<Class, HashMap<String, Method>> sSetterPropertyMap;
   private Object mAnimatedValue;
   private TypeEvaluator mEvaluator;
   private Method mGetter = null;
   private KeyframeSet mKeyframeSet = null;
   private String mPropertyName;
   private Method mSetter = null;
   private Object[] mTmpValueArray;
   private Class mValueType;
   private ReentrantReadWriteLock propertyMapLock;


   static {
      Class[] var0 = new Class[6];
      Class var1 = Float.TYPE;
      var0[0] = var1;
      var0[1] = Float.class;
      Class var2 = Double.TYPE;
      var0[2] = var2;
      Class var3 = Integer.TYPE;
      var0[3] = var3;
      var0[4] = Double.class;
      var0[5] = Integer.class;
      FLOAT_VARIANTS = var0;
      Class[] var4 = new Class[6];
      Class var5 = Integer.TYPE;
      var4[0] = var5;
      var4[1] = Integer.class;
      Class var6 = Float.TYPE;
      var4[2] = var6;
      Class var7 = Double.TYPE;
      var4[3] = var7;
      var4[4] = Float.class;
      var4[5] = Double.class;
      INTEGER_VARIANTS = var4;
      Class[] var8 = new Class[6];
      Class var9 = Double.TYPE;
      var8[0] = var9;
      var8[1] = Double.class;
      Class var10 = Float.TYPE;
      var8[2] = var10;
      Class var11 = Integer.TYPE;
      var8[3] = var11;
      var8[4] = Float.class;
      var8[5] = Integer.class;
      DOUBLE_VARIANTS = var8;
      sSetterPropertyMap = new HashMap();
      sGetterPropertyMap = new HashMap();
   }

   private PropertyValuesHolder(String var1) {
      ReentrantReadWriteLock var2 = new ReentrantReadWriteLock();
      this.propertyMapLock = var2;
      Object[] var3 = new Object[1];
      this.mTmpValueArray = var3;
      this.mPropertyName = var1;
   }

   private Method getPropertyFunction(Class var1, String var2, Class var3) {
      Method var4 = null;
      String var5 = this.mPropertyName.substring(0, 1);
      String var6 = this.mPropertyName.substring(1);
      String var7 = var5.toUpperCase();
      StringBuilder var8 = new StringBuilder();
      String var10 = var8.append(var2).append(var7).append(var6).toString();
      Class[] var11 = null;
      Method var13;
      if(var3 == null) {
         label33: {
            Method var12;
            try {
               var12 = var1.getMethod(var10, (Class[])null);
            } catch (NoSuchMethodException var32) {
               StringBuilder var15 = (new StringBuilder()).append("Couldn\'t find no-arg method for property ");
               String var16 = this.mPropertyName;
               String var17 = var15.append(var16).append(": ").append(var32).toString();
               int var18 = Log.e("PropertyValuesHolder", var17);
               break label33;
            }

            var4 = var12;
         }
      } else {
         var11 = new Class[1];
         Class[] var19;
         if(this.mValueType.equals(Float.class)) {
            var19 = FLOAT_VARIANTS;
         } else if(this.mValueType.equals(Integer.class)) {
            var19 = INTEGER_VARIANTS;
         } else if(this.mValueType.equals(Double.class)) {
            var19 = DOUBLE_VARIANTS;
         } else {
            var19 = new Class[1];
            Class var24 = this.mValueType;
            var19[0] = var24;
         }

         Class[] var20 = var19;
         int var21 = var19.length;
         int var22 = 0;

         while(true) {
            if(var22 < var21) {
               Class var23 = var20[var22];
               var11[0] = var23;

               try {
                  var4 = var1.getMethod(var10, var11);
                  this.mValueType = var23;
               } catch (NoSuchMethodException var33) {
                  ++var22;
                  continue;
               }

               var13 = var4;
               return var13;
            }

            StringBuilder var26 = (new StringBuilder()).append("Couldn\'t find setter/getter for property ");
            String var27 = this.mPropertyName;
            StringBuilder var28 = var26.append(var27).append("with value type ");
            Class var29 = this.mValueType;
            String var30 = var28.append(var29).toString();
            int var31 = Log.e("PropertyValuesHolder", var30);
            break;
         }
      }

      var13 = var4;
      return var13;
   }

   public static PropertyValuesHolder ofDouble(String var0, double ... var1) {
      PropertyValuesHolder var2 = new PropertyValuesHolder(var0);
      var2.setDoubleValues(var1);
      return var2;
   }

   public static PropertyValuesHolder ofFloat(String var0, float ... var1) {
      PropertyValuesHolder var2 = new PropertyValuesHolder(var0);
      var2.setFloatValues(var1);
      return var2;
   }

   public static PropertyValuesHolder ofInt(String var0, int ... var1) {
      PropertyValuesHolder var2 = new PropertyValuesHolder(var0);
      var2.setIntValues(var1);
      return var2;
   }

   public static PropertyValuesHolder ofKeyframe(String var0, Keyframe ... var1) {
      PropertyValuesHolder var2 = new PropertyValuesHolder(var0);
      var2.setKeyframes(var1);
      return var2;
   }

   public static PropertyValuesHolder ofLong(String var0, long ... var1) {
      PropertyValuesHolder var2 = new PropertyValuesHolder(var0);
      var2.setLongValues(var1);
      return var2;
   }

   public static PropertyValuesHolder ofObject(String var0, TypeEvaluator var1, Object ... var2) {
      PropertyValuesHolder var3 = new PropertyValuesHolder(var0);
      var3.setObjectValues(var2);
      var3.setEvaluator(var1);
      return var3;
   }

   private void setupGetter(Class var1) {
      HashMap var2 = sGetterPropertyMap;
      Method var3 = this.setupSetterOrGetter(var1, var2, "get", (Class)null);
      this.mGetter = var3;
   }

   private void setupSetter(Class var1) {
      HashMap var2 = sSetterPropertyMap;
      Class var3 = this.mValueType;
      Method var4 = this.setupSetterOrGetter(var1, var2, "set", var3);
      this.mSetter = var4;
   }

   private Method setupSetterOrGetter(Class var1, HashMap<Class, HashMap<String, Method>> var2, String var3, Class var4) {
      Method var5 = null;

      try {
         this.propertyMapLock.writeLock().lock();
         HashMap var6 = (HashMap)var2.get(var1);
         if(var6 != null) {
            String var7 = this.mPropertyName;
            var5 = (Method)var6.get(var7);
         }

         if(var5 == null) {
            var5 = this.getPropertyFunction(var1, var3, var4);
            if(var6 == null) {
               var6 = new HashMap();
               var2.put(var1, var6);
            }

            String var9 = this.mPropertyName;
            var6.put(var9, var5);
         }
      } finally {
         this.propertyMapLock.writeLock().unlock();
      }

      return var5;
   }

   private void setupValue(Object var1, Keyframe var2) {
      try {
         if(this.mGetter == null) {
            Class var3 = var1.getClass();
            this.setupGetter(var3);
         }

         Method var4 = this.mGetter;
         Object[] var5 = new Object[0];
         Object var6 = var4.invoke(var1, var5);
         var2.setValue(var6);
      } catch (InvocationTargetException var11) {
         String var7 = var11.toString();
         int var8 = Log.e("PropertyValuesHolder", var7);
      } catch (IllegalAccessException var12) {
         String var9 = var12.toString();
         int var10 = Log.e("PropertyValuesHolder", var9);
      }
   }

   Object calculateValue(float var1) {
      KeyframeSet var2 = this.mKeyframeSet;
      TypeEvaluator var3 = this.mEvaluator;
      Object var4 = var2.getValue(var1, var3);
      this.mAnimatedValue = var4;
      return this.mAnimatedValue;
   }

   public PropertyValuesHolder clone() {
      ArrayList var1 = this.mKeyframeSet.mKeyframes;
      int var2 = this.mKeyframeSet.mKeyframes.size();
      Keyframe[] var3 = new Keyframe[var2];

      for(int var4 = 0; var4 < var2; ++var4) {
         Keyframe var5 = ((Keyframe)var1.get(var4)).clone();
         var3[var4] = var5;
      }

      return ofKeyframe(this.mPropertyName, var3);
   }

   Object getAnimatedValue() {
      return this.mAnimatedValue;
   }

   public Method getGetter() {
      return this.mGetter;
   }

   public String getPropertyName() {
      return this.mPropertyName;
   }

   public Method getSetter() {
      return this.mSetter;
   }

   void init() {
      if(this.mEvaluator == null) {
         Class var1 = this.mValueType;
         Class var2 = Integer.TYPE;
         TypeEvaluator var3;
         if(var1 != var2 && this.mValueType != Integer.class) {
            Class var4 = this.mValueType;
            Class var5 = Double.TYPE;
            if(var4 != var5 && this.mValueType != Double.class) {
               var3 = sFloatEvaluator;
            } else {
               var3 = sDoubleEvaluator;
            }
         } else {
            var3 = sIntEvaluator;
         }

         this.mEvaluator = var3;
      }
   }

   void setAnimatedValue(Object var1) {
      if(this.mSetter != null) {
         try {
            Object[] var2 = this.mTmpValueArray;
            Object var3 = this.mAnimatedValue;
            var2[0] = var3;
            Method var4 = this.mSetter;
            Object[] var5 = this.mTmpValueArray;
            var4.invoke(var1, var5);
         } catch (InvocationTargetException var11) {
            String var7 = var11.toString();
            int var8 = Log.e("PropertyValuesHolder", var7);
         } catch (IllegalAccessException var12) {
            String var9 = var12.toString();
            int var10 = Log.e("PropertyValuesHolder", var9);
         }
      }
   }

   public void setDoubleValues(double ... var1) {
      Class var2 = Double.TYPE;
      this.mValueType = var2;
      KeyframeSet var3 = KeyframeSet.ofDouble(var1);
      this.mKeyframeSet = var3;
   }

   public void setEvaluator(TypeEvaluator var1) {
      this.mEvaluator = var1;
   }

   public void setFloatValues(float ... var1) {
      Class var2 = Float.TYPE;
      this.mValueType = var2;
      KeyframeSet var3 = KeyframeSet.ofFloat(var1);
      this.mKeyframeSet = var3;
   }

   public void setGetter(Method var1) {
      this.mGetter = var1;
   }

   public void setIntValues(int ... var1) {
      Class var2 = Integer.TYPE;
      this.mValueType = var2;
      KeyframeSet var3 = KeyframeSet.ofInt(var1);
      this.mKeyframeSet = var3;
   }

   public void setKeyframes(Keyframe ... var1) {
      int var2 = var1.length;
      Keyframe[] var3 = new Keyframe[Math.max(var2, 2)];
      Class var4 = var1[0].getType();
      this.mValueType = var4;

      for(int var5 = 0; var5 < var2; ++var5) {
         Keyframe var6 = var1[var5];
         var3[var5] = var6;
      }

      KeyframeSet var7 = new KeyframeSet(var3);
      this.mKeyframeSet = var7;
   }

   public void setLongValues(long ... var1) {
      Class var2 = Long.TYPE;
      this.mValueType = var2;
      KeyframeSet var3 = KeyframeSet.ofLong(var1);
      this.mKeyframeSet = var3;
   }

   public void setObjectValues(Object ... var1) {
      Class var2 = var1[0].getClass();
      this.mValueType = var2;
      KeyframeSet var3 = KeyframeSet.ofObject(var1);
      this.mKeyframeSet = var3;
   }

   public void setPropertyName(String var1) {
      this.mPropertyName = var1;
   }

   public void setSetter(Method var1) {
      this.mSetter = var1;
   }

   void setupEndValue(Object var1) {
      ArrayList var2 = this.mKeyframeSet.mKeyframes;
      int var3 = this.mKeyframeSet.mKeyframes.size() + -1;
      Keyframe var4 = (Keyframe)var2.get(var3);
      this.setupValue(var1, var4);
   }

   void setupSetterAndGetter(Object var1) {
      Class var2 = var1.getClass();
      if(this.mSetter == null) {
         this.setupSetter(var2);
      }

      Iterator var3 = this.mKeyframeSet.mKeyframes.iterator();

      while(var3.hasNext()) {
         Keyframe var4 = (Keyframe)var3.next();
         if(var4.getValue() == null) {
            if(this.mGetter == null) {
               this.setupGetter(var2);
            }

            try {
               Method var5 = this.mGetter;
               Object[] var6 = new Object[0];
               Object var7 = var5.invoke(var1, var6);
               var4.setValue(var7);
            } catch (InvocationTargetException var12) {
               String var8 = var12.toString();
               int var9 = Log.e("PropertyValuesHolder", var8);
            } catch (IllegalAccessException var13) {
               String var10 = var13.toString();
               int var11 = Log.e("PropertyValuesHolder", var10);
            }
         }
      }

   }

   void setupStartValue(Object var1) {
      Keyframe var2 = (Keyframe)this.mKeyframeSet.mKeyframes.get(0);
      this.setupValue(var1, var2);
   }
}
