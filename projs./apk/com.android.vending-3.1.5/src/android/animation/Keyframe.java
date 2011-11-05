package android.animation;

import android.animation.TimeInterpolator;

public class Keyframe implements Cloneable {

   private float mFraction;
   private TimeInterpolator mInterpolator;
   private Object mValue;
   private Class mValueType;


   public Keyframe(float var1, double var2) {
      Double var4 = Double.valueOf(var2);
      Class var5 = Double.TYPE;
      this(var1, var4, var5);
   }

   public Keyframe(float var1, float var2) {
      Float var3 = Float.valueOf(var2);
      Class var4 = Float.TYPE;
      this(var1, var3, var4);
   }

   public Keyframe(float var1, int var2) {
      Integer var3 = Integer.valueOf(var2);
      Class var4 = Integer.TYPE;
      this(var1, var3, var4);
   }

   public Keyframe(float var1, Double var2) {
      this(var1, var2, Double.class);
   }

   public Keyframe(float var1, Float var2) {
      this(var1, var2, Float.class);
   }

   public Keyframe(float var1, Integer var2) {
      this(var1, var2, Integer.class);
   }

   public Keyframe(float var1, Object var2) {
      Class var3;
      if(var2 != null) {
         var3 = var2.getClass();
      } else {
         var3 = Object.class;
      }

      this(var1, var2, var3);
   }

   private Keyframe(float var1, Object var2, Class var3) {
      this.mInterpolator = null;
      this.mFraction = var1;
      this.mValue = var2;
      this.mValueType = var3;
   }

   public Keyframe clone() {
      float var1 = this.mFraction;
      Object var2 = this.mValue;
      Class var3 = this.mValueType;
      Keyframe var4 = new Keyframe(var1, var2, var3);
      TimeInterpolator var5 = this.mInterpolator;
      var4.setInterpolator(var5);
      return var4;
   }

   public float getFraction() {
      return this.mFraction;
   }

   public TimeInterpolator getInterpolator() {
      return this.mInterpolator;
   }

   public Class getType() {
      return this.mValueType;
   }

   public Object getValue() {
      return this.mValue;
   }

   public void setFraction(float var1) {
      this.mFraction = var1;
   }

   public void setInterpolator(TimeInterpolator var1) {
      this.mInterpolator = var1;
   }

   public void setValue(Object var1) {
      this.mValue = var1;
   }
}
