package org.codehaus.jackson.map;

import java.text.DateFormat;
import org.codehaus.jackson.Base64Variant;
import org.codehaus.jackson.Base64Variants;
import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.BeanDescription;
import org.codehaus.jackson.map.ClassIntrospector;
import org.codehaus.jackson.map.DeserializationProblemHandler;
import org.codehaus.jackson.map.introspect.AnnotatedClass;
import org.codehaus.jackson.map.util.LinkedNode;
import org.codehaus.jackson.map.util.StdDateFormat;
import org.codehaus.jackson.type.JavaType;

public class DeserializationConfig {

   protected static final DateFormat DEFAULT_DATE_FORMAT = StdDateFormat.instance;
   protected static final int DEFAULT_FEATURE_FLAGS = DeserializationConfig.Feature.collectDefaults();
   protected AnnotationIntrospector _annotationIntrospector;
   protected ClassIntrospector<? extends BeanDescription> _classIntrospector;
   protected DateFormat _dateFormat;
   protected int _featureFlags;
   protected LinkedNode<DeserializationProblemHandler> _problemHandlers;


   public DeserializationConfig(ClassIntrospector<? extends BeanDescription> var1, AnnotationIntrospector var2) {
      int var3 = DEFAULT_FEATURE_FLAGS;
      this._featureFlags = var3;
      DateFormat var4 = DEFAULT_DATE_FORMAT;
      this._dateFormat = var4;
      this._classIntrospector = var1;
      this._annotationIntrospector = var2;
   }

   protected DeserializationConfig(DeserializationConfig var1) {
      int var2 = DEFAULT_FEATURE_FLAGS;
      this._featureFlags = var2;
      DateFormat var3 = DEFAULT_DATE_FORMAT;
      this._dateFormat = var3;
      ClassIntrospector var4 = var1._classIntrospector;
      this._classIntrospector = var4;
      AnnotationIntrospector var5 = var1._annotationIntrospector;
      this._annotationIntrospector = var5;
      int var6 = var1._featureFlags;
      this._featureFlags = var6;
      LinkedNode var7 = var1._problemHandlers;
      this._problemHandlers = var7;
      DateFormat var8 = var1._dateFormat;
      this._dateFormat = var8;
   }

   public void addHandler(DeserializationProblemHandler var1) {
      if(!LinkedNode.contains(this._problemHandlers, var1)) {
         LinkedNode var2 = this._problemHandlers;
         LinkedNode var3 = new LinkedNode(var1, var2);
         this._problemHandlers = var3;
      }
   }

   public void clearHandlers() {
      this._problemHandlers = null;
   }

   public DeserializationConfig createUnshared() {
      return new DeserializationConfig(this);
   }

   public void disable(DeserializationConfig.Feature var1) {
      int var2 = this._featureFlags;
      int var3 = ~var1.getMask();
      int var4 = var2 & var3;
      this._featureFlags = var4;
   }

   public void enable(DeserializationConfig.Feature var1) {
      int var2 = this._featureFlags;
      int var3 = var1.getMask();
      int var4 = var2 | var3;
      this._featureFlags = var4;
   }

   public void fromAnnotations(Class<?> var1) {
      AnnotationIntrospector var2 = this._annotationIntrospector;
      AnnotatedClass var3 = AnnotatedClass.construct(var1, var2);
      Boolean var4 = this._annotationIntrospector.findSetterAutoDetection(var3);
      if(var4 != null) {
         DeserializationConfig.Feature var5 = DeserializationConfig.Feature.AUTO_DETECT_SETTERS;
         boolean var6 = var4.booleanValue();
         this.set(var5, var6);
      }

      Boolean var7 = this._annotationIntrospector.findCreatorAutoDetection(var3);
      if(var7 != null) {
         DeserializationConfig.Feature var8 = DeserializationConfig.Feature.AUTO_DETECT_CREATORS;
         boolean var9 = var7.booleanValue();
         this.set(var8, var9);
      }
   }

   public AnnotationIntrospector getAnnotationIntrospector() {
      return this._annotationIntrospector;
   }

   public Base64Variant getBase64Variant() {
      return Base64Variants.getDefaultVariant();
   }

   public DateFormat getDateFormat() {
      return this._dateFormat;
   }

   public LinkedNode<DeserializationProblemHandler> getProblemHandlers() {
      return this._problemHandlers;
   }

   public <T extends BeanDescription> T introspect(JavaType var1) {
      return this._classIntrospector.forDeserialization(this, var1);
   }

   public <T extends BeanDescription> T introspectClassAnnotations(Class<?> var1) {
      return this._classIntrospector.forClassAnnotations(this, var1);
   }

   public <T extends BeanDescription> T introspectForCreation(Class<?> var1) {
      return this._classIntrospector.forCreation(this, var1);
   }

   public final boolean isEnabled(DeserializationConfig.Feature var1) {
      int var2 = this._featureFlags;
      int var3 = var1.getMask();
      boolean var4;
      if((var2 & var3) != 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      return var4;
   }

   public void set(DeserializationConfig.Feature var1, boolean var2) {
      if(var2) {
         this.enable(var1);
      } else {
         this.disable(var1);
      }
   }

   public void setAnnotationIntrospector(AnnotationIntrospector var1) {
      this._annotationIntrospector = var1;
   }

   public void setDateFormat(DateFormat var1) {
      Object var2;
      if(var1 == null) {
         var2 = StdDateFormat.instance;
      } else {
         var2 = var1;
      }

      this._dateFormat = (DateFormat)var2;
   }

   public void setIntrospector(ClassIntrospector<? extends BeanDescription> var1) {
      this._classIntrospector = var1;
   }

   public static enum Feature {

      // $FF: synthetic field
      private static final DeserializationConfig.Feature[] $VALUES;
      AUTO_DETECT_CREATORS("AUTO_DETECT_CREATORS", 1, (boolean)1),
      AUTO_DETECT_FIELDS("AUTO_DETECT_FIELDS", 2, (boolean)1),
      AUTO_DETECT_SETTERS("AUTO_DETECT_SETTERS", 0, (boolean)1),
      CAN_OVERRIDE_ACCESS_MODIFIERS("CAN_OVERRIDE_ACCESS_MODIFIERS", 4, (boolean)1),
      USE_BIG_DECIMAL_FOR_FLOATS("USE_BIG_DECIMAL_FOR_FLOATS", 5, (boolean)0),
      USE_BIG_INTEGER_FOR_INTS("USE_BIG_INTEGER_FOR_INTS", 6, (boolean)0),
      USE_GETTERS_AS_SETTERS("USE_GETTERS_AS_SETTERS", 3, (boolean)1);
      final boolean _defaultState;


      static {
         DeserializationConfig.Feature[] var0 = new DeserializationConfig.Feature[7];
         DeserializationConfig.Feature var1 = AUTO_DETECT_SETTERS;
         var0[0] = var1;
         DeserializationConfig.Feature var2 = AUTO_DETECT_CREATORS;
         var0[1] = var2;
         DeserializationConfig.Feature var3 = AUTO_DETECT_FIELDS;
         var0[2] = var3;
         DeserializationConfig.Feature var4 = USE_GETTERS_AS_SETTERS;
         var0[3] = var4;
         DeserializationConfig.Feature var5 = CAN_OVERRIDE_ACCESS_MODIFIERS;
         var0[4] = var5;
         DeserializationConfig.Feature var6 = USE_BIG_DECIMAL_FOR_FLOATS;
         var0[5] = var6;
         DeserializationConfig.Feature var7 = USE_BIG_INTEGER_FOR_INTS;
         var0[6] = var7;
         $VALUES = var0;
      }

      private Feature(String var1, int var2, boolean var3) {
         this._defaultState = var3;
      }

      public static int collectDefaults() {
         int var0 = 0;
         DeserializationConfig.Feature[] var1 = values();
         int var2 = var1.length;

         int var3;
         for(var3 = var0; var0 < var2; ++var0) {
            DeserializationConfig.Feature var4 = var1[var0];
            if(var4.enabledByDefault()) {
               int var5 = var4.getMask();
               var3 |= var5;
            }
         }

         return var3;
      }

      public boolean enabledByDefault() {
         return this._defaultState;
      }

      public int getMask() {
         int var1 = this.ordinal();
         return 1 << var1;
      }
   }
}
