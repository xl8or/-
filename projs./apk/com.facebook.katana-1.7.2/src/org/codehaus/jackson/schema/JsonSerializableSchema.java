package org.codehaus.jackson.schema;

import dalvik.annotation.AnnotationDefault;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.codehaus.jackson.annotate.JacksonAnnotation;

@AnnotationDefault(   @JsonSerializableSchema(
      schemaItemDefinition = "##irrelevant",
      schemaObjectPropertiesDefinition = "##irrelevant",
      schemaType = "any"
   ))
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@JacksonAnnotation
public @interface JsonSerializableSchema {

   String schemaItemDefinition();

   String schemaObjectPropertiesDefinition();

   String schemaType();
}
