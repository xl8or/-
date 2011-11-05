package org.codehaus.jackson.map;

import java.text.DateFormat;
import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.BeanDescription;
import org.codehaus.jackson.map.ClassIntrospector;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.introspect.AnnotatedClass;
import org.codehaus.jackson.map.util.StdDateFormat;

public class SerializationConfig {

   protected static final int DEFAULT_FEATURE_FLAGS = SerializationConfig.Feature.collectDefaults();
   protected AnnotationIntrospector _annotationIntrospector;
   protected ClassIntrospector<? extends BeanDescription> _classIntrospector;
   protected DateFormat _dateFormat;
   protected int _featureFlags;
   protected JsonSerialize.Inclusion _serializationInclusion;


   public SerializationConfig(ClassIntrospector<? extends BeanDescription> var1, AnnotationIntrospector var2) {
      int var3 = DEFAULT_FEATURE_FLAGS;
      this._featureFlags = var3;
      StdDateFormat var4 = StdDateFormat.instance;
      this._dateFormat = var4;
      this._serializationInclusion = null;
      this._classIntrospector = var1;
      this._annotationIntrospector = var2;
   }

   protected SerializationConfig(SerializationConfig var1) {
      int var2 = DEFAULT_FEATURE_FLAGS;
      this._featureFlags = var2;
      StdDateFormat var3 = StdDateFormat.instance;
      this._dateFormat = var3;
      this._serializationInclusion = null;
      ClassIntrospector var4 = var1._classIntrospector;
      this._classIntrospector = var4;
      AnnotationIntrospector var5 = var1._annotationIntrospector;
      this._annotationIntrospector = var5;
      int var6 = var1._featureFlags;
      this._featureFlags = var6;
      DateFormat var7 = var1._dateFormat;
      this._dateFormat = var7;
      JsonSerialize.Inclusion var8 = var1._serializationInclusion;
      this._serializationInclusion = var8;
   }

   public SerializationConfig createUnshared() {
      return new SerializationConfig(this);
   }

   public void disable(SerializationConfig.Feature var1) {
      int var2 = this._featureFlags;
      int var3 = ~var1.getMask();
      int var4 = var2 & var3;
      this._featureFlags = var4;
   }

   public void enable(SerializationConfig.Feature var1) {
      int var2 = this._featureFlags;
      int var3 = var1.getMask();
      int var4 = var2 | var3;
      this._featureFlags = var4;
   }

   public void fromAnnotations(Class<?> var1) {
      AnnotationIntrospector var2 = this._annotationIntrospector;
      AnnotatedClass var3 = AnnotatedClass.construct(var1, var2);
      Boolean var4 = this._annotationIntrospector.findGetterAutoDetection(var3);
      if(var4 != null) {
         SerializationConfig.Feature var5 = SerializationConfig.Feature.AUTO_DETECT_GETTERS;
         boolean var6 = var4.booleanValue();
         this.set(var5, var6);
      }

      JsonSerialize.Inclusion var7 = this._annotationIntrospector.findSerializationInclusion(var3, (JsonSerialize.Inclusion)null);
      JsonSerialize.Inclusion var8 = this._serializationInclusion;
      if(var7 != var8) {
         this.setSerializationInclusion(var7);
      }
   }

   public AnnotationIntrospector getAnnotationIntrospector() {
      return this._annotationIntrospector;
   }

   public DateFormat getDateFormat() {
      return this._dateFormat;
   }

   public JsonSerialize.Inclusion getSerializationInclusion() {
      JsonSerialize.Inclusion var1;
      if(this._serializationInclusion != null) {
         var1 = this._serializationInclusion;
      } else {
         SerializationConfig.Feature var2 = SerializationConfig.Feature.WRITE_NULL_PROPERTIES;
         if(this.isEnabled(var2)) {
            var1 = JsonSerialize.Inclusion.ALWAYS;
         } else {
            var1 = JsonSerialize.Inclusion.NON_NULL;
         }
      }

      return var1;
   }

   public <T extends BeanDescription> T introspect(Class<?> var1) {
      return this._classIntrospector.forSerialization(this, var1);
   }

   public <T extends BeanDescription> T introspectClassAnnotations(Class<?> var1) {
      return this._classIntrospector.forClassAnnotations(this, var1);
   }

   public final boolean isEnabled(SerializationConfig.Feature var1) {
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

   public void set(SerializationConfig.Feature var1, boolean var2) {
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
      this._dateFormat = var1;
      SerializationConfig.Feature var2 = SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS;
      byte var3;
      if(var1 == null) {
         var3 = 1;
      } else {
         var3 = 0;
      }

      this.set(var2, (boolean)var3);
   }

   public void setIntrospector(ClassIntrospector<? extends BeanDescription> var1) {
      this._classIntrospector = var1;
   }

   public void setSerializationInclusion(JsonSerialize.Inclusion var1) {
      this._serializationInclusion = var1;
      JsonSerialize.Inclusion var2 = JsonSerialize.Inclusion.NON_NULL;
      if(var1 == var2) {
         SerializationConfig.Feature var3 = SerializationConfig.Feature.WRITE_NULL_PROPERTIES;
         this.disable(var3);
      } else {
         SerializationConfig.Feature var4 = SerializationConfig.Feature.WRITE_NULL_PROPERTIES;
         this.enable(var4);
      }
   }

   public String toString() {
      StringBuilder var1 = (new StringBuilder()).append("[SerializationConfig: flags=0x");
      String var2 = Integer.toHexString(this._featureFlags);
      return var1.append(var2).append("]").toString();
   }

   public static enum Feature {

      // $FF: synthetic field
      private static final SerializationConfig.Feature[] $VALUES;
      AUTO_DETECT_FIELDS("AUTO_DETECT_FIELDS", 1, (boolean)1),
      AUTO_DETECT_GETTERS("AUTO_DETECT_GETTERS", 0, (boolean)1),
      CAN_OVERRIDE_ACCESS_MODIFIERS("CAN_OVERRIDE_ACCESS_MODIFIERS", 2, (boolean)1),
      INDENT_OUTPUT("INDENT_OUTPUT", 5, (boolean)0),
      WRITE_DATES_AS_TIMESTAMPS("WRITE_DATES_AS_TIMESTAMPS", 4, (boolean)1),
      WRITE_NULL_PROPERTIES("WRITE_NULL_PROPERTIES", 3, (boolean)1);
      final boolean _defaultState;


      static {
         SerializationConfig.Feature[] var0 = new SerializationConfig.Feature[6];
         SerializationConfig.Feature var1 = AUTO_DETECT_GETTERS;
         var0[0] = var1;
         SerializationConfig.Feature var2 = AUTO_DETECT_FIELDS;
         var0[1] = var2;
         SerializationConfig.Feature var3 = CAN_OVERRIDE_ACCESS_MODIFIERS;
         var0[2] = var3;
         SerializationConfig.Feature var4 = WRITE_NULL_PROPERTIES;
         var0[3] = var4;
         SerializationConfig.Feature var5 = WRITE_DATES_AS_TIMESTAMPS;
         var0[4] = var5;
         SerializationConfig.Feature var6 = INDENT_OUTPUT;
         var0[5] = var6;
         $VALUES = var0;
      }

      private Feature(String var1, int var2, boolean var3) {
         this._defaultState = var3;
      }

      public static int collectDefaults() {
         int var0 = 0;
         SerializationConfig.Feature[] var1 = values();
         int var2 = var1.length;

         int var3;
         for(var3 = var0; var0 < var2; ++var0) {
            SerializationConfig.Feature var4 = var1[var0];
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
