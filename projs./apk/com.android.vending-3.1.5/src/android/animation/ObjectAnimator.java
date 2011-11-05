package android.animation;

import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.util.Log;
import java.lang.reflect.Method;

public final class ObjectAnimator extends ValueAnimator {

   private String mPropertyName;
   private Object mTarget;


   public ObjectAnimator() {}

   private ObjectAnimator(Object var1, String var2) {
      this.mTarget = var1;
      this.setPropertyName(var2);
   }

   private Method getPropertyFunction(String var1, Class var2) {
      Method var3 = null;
      String var4 = this.mPropertyName.substring(0, 1);
      String var5 = this.mPropertyName.substring(1);
      String var6 = var4.toUpperCase();
      String var7 = var1 + var6 + var5;
      Class[] var8 = null;
      if(var2 != null) {
         var8 = new Class[]{var2};
      }

      Method var9;
      try {
         var9 = this.mTarget.getClass().getMethod(var7, var8);
      } catch (NoSuchMethodException var15) {
         StringBuilder var11 = (new StringBuilder()).append("Couldn\'t find setter/getter for property ");
         String var12 = this.mPropertyName;
         String var13 = var11.append(var12).append(": ").append(var15).toString();
         int var14 = Log.e("ObjectAnimator", var13);
         return var3;
      }

      var3 = var9;
      return var3;
   }

   public static ObjectAnimator ofDouble(Object var0, String var1, double ... var2) {
      ObjectAnimator var3 = new ObjectAnimator(var0, var1);
      var3.setDoubleValues(var2);
      return var3;
   }

   public static ObjectAnimator ofFloat(Object var0, String var1, float ... var2) {
      ObjectAnimator var3 = new ObjectAnimator(var0, var1);
      var3.setFloatValues(var2);
      return var3;
   }

   public static ObjectAnimator ofInt(Object var0, String var1, int ... var2) {
      ObjectAnimator var3 = new ObjectAnimator(var0, var1);
      var3.setIntValues(var2);
      return var3;
   }

   public static ObjectAnimator ofLong(Object var0, String var1, long ... var2) {
      ObjectAnimator var3 = new ObjectAnimator(var0, var1);
      var3.setLongValues(var2);
      return var3;
   }

   public static ObjectAnimator ofObject(Object var0, String var1, TypeEvaluator var2, Object ... var3) {
      ObjectAnimator var4 = new ObjectAnimator(var0, var1);
      var4.setObjectValues(var3);
      var4.setEvaluator(var2);
      return var4;
   }

   public static ObjectAnimator ofPropertyValuesHolder(Object var0, PropertyValuesHolder ... var1) {
      ObjectAnimator var2 = new ObjectAnimator();
      var2.mTarget = var0;
      var2.setValues(var1);
      return var2;
   }

   void animateValue(float var1) {
      super.animateValue(var1);
      int var2 = this.mValues.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         PropertyValuesHolder var4 = this.mValues[var3];
         Object var5 = this.mTarget;
         var4.setAnimatedValue(var5);
      }

   }

   public ObjectAnimator clone() {
      return (ObjectAnimator)super.clone();
   }

   public String getPropertyName() {
      return this.mPropertyName;
   }

   public Object getTarget() {
      return this.mTarget;
   }

   void initAnimation() {
      if(!this.mInitialized) {
         int var1 = this.mValues.length;

         for(int var2 = 0; var2 < var1; ++var2) {
            PropertyValuesHolder var3 = this.mValues[var2];
            Object var4 = this.mTarget;
            var3.setupSetterAndGetter(var4);
         }

         super.initAnimation();
      }
   }

   public void setDoubleValues(double ... var1) {
      if(this.mValues != null && this.mValues.length != 0) {
         super.setDoubleValues(var1);
      } else {
         PropertyValuesHolder[] var2 = new PropertyValuesHolder[1];
         PropertyValuesHolder var3 = PropertyValuesHolder.ofDouble(this.mPropertyName, var1);
         var2[0] = var3;
         this.setValues(var2);
      }
   }

   public ObjectAnimator setDuration(long var1) {
      super.setDuration(var1);
      return this;
   }

   public void setFloatValues(float ... var1) {
      if(this.mValues != null && this.mValues.length != 0) {
         super.setFloatValues(var1);
      } else {
         PropertyValuesHolder[] var2 = new PropertyValuesHolder[1];
         PropertyValuesHolder var3 = PropertyValuesHolder.ofFloat(this.mPropertyName, var1);
         var2[0] = var3;
         this.setValues(var2);
      }
   }

   public void setIntValues(int ... var1) {
      if(this.mValues != null && this.mValues.length != 0) {
         super.setIntValues(var1);
      } else {
         PropertyValuesHolder[] var2 = new PropertyValuesHolder[1];
         PropertyValuesHolder var3 = PropertyValuesHolder.ofInt(this.mPropertyName, var1);
         var2[0] = var3;
         this.setValues(var2);
      }
   }

   public void setLongValues(long ... var1) {
      if(this.mValues != null && this.mValues.length != 0) {
         super.setLongValues(var1);
      } else {
         PropertyValuesHolder[] var2 = new PropertyValuesHolder[1];
         PropertyValuesHolder var3 = PropertyValuesHolder.ofLong(this.mPropertyName, var1);
         var2[0] = var3;
         this.setValues(var2);
      }
   }

   public void setObjectValues(Object ... var1) {
      if(this.mValues != null && this.mValues.length != 0) {
         super.setObjectValues(var1);
      } else {
         PropertyValuesHolder[] var2 = new PropertyValuesHolder[1];
         String var3 = this.mPropertyName;
         TypeEvaluator var4 = (TypeEvaluator)false;
         PropertyValuesHolder var5 = PropertyValuesHolder.ofObject(var3, var4, var1);
         var2[0] = var5;
         this.setValues(var2);
      }
   }

   public void setPropertyName(String var1) {
      if(this.mValues != null) {
         PropertyValuesHolder var2 = this.mValues[0];
         String var3 = var2.getPropertyName();
         var2.setPropertyName(var1);
         this.mValuesMap.remove(var3);
         this.mValuesMap.put(var1, var2);
      }

      this.mPropertyName = var1;
      this.mInitialized = (boolean)0;
   }

   public void setTarget(Object var1) {
      this.mTarget = var1;
      this.mInitialized = (boolean)0;
   }

   public void setupEndValues() {
      this.initAnimation();
      int var1 = this.mValues.length;

      for(int var2 = 0; var2 < var1; ++var2) {
         PropertyValuesHolder var3 = this.mValues[var2];
         Object var4 = this.mTarget;
         var3.setupEndValue(var4);
      }

   }

   public void setupStartValues() {
      this.initAnimation();
      int var1 = this.mValues.length;

      for(int var2 = 0; var2 < var1; ++var2) {
         PropertyValuesHolder var3 = this.mValues[var2];
         Object var4 = this.mTarget;
         var3.setupStartValue(var4);
      }

   }
}
