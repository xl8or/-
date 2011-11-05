package com.google.common.annotations;

import dalvik.annotation.AnnotationDefault;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@AnnotationDefault(   @GwtCompatible(
      emulated = false,
      serializable = false
   ))
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE, ElementType.METHOD})
@GwtCompatible
public @interface GwtCompatible {

   boolean emulated();

   boolean serializable();
}
