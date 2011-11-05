package org.codehaus.jackson.map;

import java.lang.annotation.Annotation;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.introspect.Annotated;
import org.codehaus.jackson.map.introspect.AnnotatedClass;
import org.codehaus.jackson.map.introspect.AnnotatedField;
import org.codehaus.jackson.map.introspect.AnnotatedMethod;

public abstract class AnnotationIntrospector {

   public AnnotationIntrospector() {}

   public abstract Boolean findCachability(AnnotatedClass var1);

   public abstract Boolean findCreatorAutoDetection(AnnotatedClass var1);

   public abstract String findDeserializablePropertyName(AnnotatedField var1);

   public abstract Class<?> findDeserializationContentType(Annotated var1);

   public abstract Class<?> findDeserializationKeyType(Annotated var1);

   public abstract Class<?> findDeserializationType(Annotated var1);

   public abstract Object findDeserializer(Annotated var1);

   public abstract String findEnumValue(Enum<?> var1);

   public abstract Boolean findFieldAutoDetection(AnnotatedClass var1);

   public abstract String findGettablePropertyName(AnnotatedMethod var1);

   public abstract Boolean findGetterAutoDetection(AnnotatedClass var1);

   public abstract String findSerializablePropertyName(AnnotatedField var1);

   public abstract JsonSerialize.Inclusion findSerializationInclusion(Annotated var1, JsonSerialize.Inclusion var2);

   public abstract Class<?> findSerializationType(Annotated var1);

   public abstract Object findSerializer(Annotated var1);

   public abstract String findSettablePropertyName(AnnotatedMethod var1);

   public abstract Boolean findSetterAutoDetection(AnnotatedClass var1);

   public abstract boolean hasAnySetterAnnotation(AnnotatedMethod var1);

   public abstract boolean hasAsValueAnnotation(AnnotatedMethod var1);

   public abstract boolean hasCreatorAnnotation(AnnotatedMethod var1);

   public abstract boolean isHandled(Annotation var1);

   public abstract boolean isIgnorableField(AnnotatedField var1);

   public abstract boolean isIgnorableMethod(AnnotatedMethod var1);

   public static class Pair extends AnnotationIntrospector {

      final AnnotationIntrospector _primary;
      final AnnotationIntrospector _secondary;


      public Pair(AnnotationIntrospector var1, AnnotationIntrospector var2) {
         this._primary = var1;
         this._secondary = var2;
      }

      public Boolean findCachability(AnnotatedClass var1) {
         Boolean var2 = this._primary.findCachability(var1);
         if(var2 == null) {
            var2 = this._secondary.findCachability(var1);
         }

         return var2;
      }

      public Boolean findCreatorAutoDetection(AnnotatedClass var1) {
         Boolean var2 = this._primary.findCreatorAutoDetection(var1);
         if(var2 == null) {
            var2 = this._secondary.findCreatorAutoDetection(var1);
         }

         return var2;
      }

      public String findDeserializablePropertyName(AnnotatedField var1) {
         String var2 = this._primary.findDeserializablePropertyName(var1);
         if(var2 == null) {
            var2 = this._secondary.findDeserializablePropertyName(var1);
         } else if(var2.length() == 0) {
            String var3 = this._secondary.findDeserializablePropertyName(var1);
            if(var3 != null) {
               var2 = var3;
            }
         }

         return var2;
      }

      public Class<?> findDeserializationContentType(Annotated var1) {
         Class var2 = this._primary.findDeserializationContentType(var1);
         if(var2 == null) {
            var2 = this._secondary.findDeserializationContentType(var1);
         }

         return var2;
      }

      public Class<?> findDeserializationKeyType(Annotated var1) {
         Class var2 = this._primary.findDeserializationKeyType(var1);
         if(var2 == null) {
            var2 = this._secondary.findDeserializationKeyType(var1);
         }

         return var2;
      }

      public Class<?> findDeserializationType(Annotated var1) {
         Class var2 = this._primary.findDeserializationType(var1);
         if(var2 == null) {
            var2 = this._secondary.findDeserializationType(var1);
         }

         return var2;
      }

      public Object findDeserializer(Annotated var1) {
         Object var2 = this._primary.findDeserializer(var1);
         if(var2 == null) {
            var2 = this._secondary.findDeserializer(var1);
         }

         return var2;
      }

      public String findEnumValue(Enum<?> var1) {
         String var2 = this._primary.findEnumValue(var1);
         if(var2 == null) {
            var2 = this._secondary.findEnumValue(var1);
         }

         return var2;
      }

      public Boolean findFieldAutoDetection(AnnotatedClass var1) {
         Boolean var2 = this._primary.findFieldAutoDetection(var1);
         if(var2 == null) {
            var2 = this._secondary.findFieldAutoDetection(var1);
         }

         return var2;
      }

      public String findGettablePropertyName(AnnotatedMethod var1) {
         String var2 = this._primary.findGettablePropertyName(var1);
         if(var2 == null) {
            var2 = this._secondary.findGettablePropertyName(var1);
         } else if(var2.length() == 0) {
            String var3 = this._secondary.findGettablePropertyName(var1);
            if(var3 != null) {
               var2 = var3;
            }
         }

         return var2;
      }

      public Boolean findGetterAutoDetection(AnnotatedClass var1) {
         Boolean var2 = this._primary.findGetterAutoDetection(var1);
         if(var2 == null) {
            var2 = this._secondary.findGetterAutoDetection(var1);
         }

         return var2;
      }

      public String findSerializablePropertyName(AnnotatedField var1) {
         String var2 = this._primary.findSerializablePropertyName(var1);
         if(var2 == null) {
            var2 = this._secondary.findSerializablePropertyName(var1);
         } else if(var2.length() == 0) {
            String var3 = this._secondary.findSerializablePropertyName(var1);
            if(var3 != null) {
               var2 = var3;
            }
         }

         return var2;
      }

      public JsonSerialize.Inclusion findSerializationInclusion(Annotated var1, JsonSerialize.Inclusion var2) {
         JsonSerialize.Inclusion var3 = this._primary.findSerializationInclusion(var1, var2);
         JsonSerialize.Inclusion var4 = this._secondary.findSerializationInclusion(var1, var2);
         if(var3.compareTo(var4) < 0) {
            var3 = var4;
         }

         return var3;
      }

      public Class<?> findSerializationType(Annotated var1) {
         Class var2 = this._primary.findSerializationType(var1);
         if(var2 == null) {
            var2 = this._secondary.findSerializationType(var1);
         }

         return var2;
      }

      public Object findSerializer(Annotated var1) {
         Object var2 = this._primary.findSerializer(var1);
         if(var2 == null) {
            var2 = this._secondary.findSerializer(var1);
         }

         return var2;
      }

      public String findSettablePropertyName(AnnotatedMethod var1) {
         String var2 = this._primary.findSettablePropertyName(var1);
         if(var2 == null) {
            var2 = this._secondary.findSettablePropertyName(var1);
         } else if(var2.length() == 0) {
            String var3 = this._secondary.findSettablePropertyName(var1);
            if(var3 != null) {
               var2 = var3;
            }
         }

         return var2;
      }

      public Boolean findSetterAutoDetection(AnnotatedClass var1) {
         Boolean var2 = this._primary.findSetterAutoDetection(var1);
         if(var2 == null) {
            var2 = this._secondary.findSetterAutoDetection(var1);
         }

         return var2;
      }

      public boolean hasAnySetterAnnotation(AnnotatedMethod var1) {
         boolean var2;
         if(!this._primary.hasAnySetterAnnotation(var1) && !this._secondary.hasAnySetterAnnotation(var1)) {
            var2 = false;
         } else {
            var2 = true;
         }

         return var2;
      }

      public boolean hasAsValueAnnotation(AnnotatedMethod var1) {
         boolean var2;
         if(!this._primary.hasAsValueAnnotation(var1) && !this._secondary.hasAsValueAnnotation(var1)) {
            var2 = false;
         } else {
            var2 = true;
         }

         return var2;
      }

      public boolean hasCreatorAnnotation(AnnotatedMethod var1) {
         boolean var2;
         if(!this._primary.hasCreatorAnnotation(var1) && !this._secondary.hasCreatorAnnotation(var1)) {
            var2 = false;
         } else {
            var2 = true;
         }

         return var2;
      }

      public boolean isHandled(Annotation var1) {
         boolean var2;
         if(!this._primary.isHandled(var1) && !this._secondary.isHandled(var1)) {
            var2 = false;
         } else {
            var2 = true;
         }

         return var2;
      }

      public boolean isIgnorableField(AnnotatedField var1) {
         boolean var2;
         if(!this._primary.isIgnorableField(var1) && !this._secondary.isIgnorableField(var1)) {
            var2 = false;
         } else {
            var2 = true;
         }

         return var2;
      }

      public boolean isIgnorableMethod(AnnotatedMethod var1) {
         boolean var2;
         if(!this._primary.isIgnorableMethod(var1) && !this._secondary.isIgnorableMethod(var1)) {
            var2 = false;
         } else {
            var2 = true;
         }

         return var2;
      }
   }
}
