// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.map;

import java.lang.annotation.Annotation;
import org.codehaus.jackson.map.introspect.Annotated;
import org.codehaus.jackson.map.introspect.AnnotatedClass;
import org.codehaus.jackson.map.introspect.AnnotatedField;
import org.codehaus.jackson.map.introspect.AnnotatedMethod;

public abstract class AnnotationIntrospector
{
    public static class Pair extends AnnotationIntrospector
    {

        public Boolean findCachability(AnnotatedClass annotatedclass)
        {
            Boolean boolean1 = _primary.findCachability(annotatedclass);
            if(boolean1 == null)
                boolean1 = _secondary.findCachability(annotatedclass);
            return boolean1;
        }

        public Boolean findCreatorAutoDetection(AnnotatedClass annotatedclass)
        {
            Boolean boolean1 = _primary.findCreatorAutoDetection(annotatedclass);
            if(boolean1 == null)
                boolean1 = _secondary.findCreatorAutoDetection(annotatedclass);
            return boolean1;
        }

        public String findDeserializablePropertyName(AnnotatedField annotatedfield)
        {
            String s = _primary.findDeserializablePropertyName(annotatedfield);
            if(s != null) goto _L2; else goto _L1
_L1:
            s = _secondary.findDeserializablePropertyName(annotatedfield);
_L4:
            return s;
_L2:
            if(s.length() == 0)
            {
                String s1 = _secondary.findDeserializablePropertyName(annotatedfield);
                if(s1 != null)
                    s = s1;
            }
            if(true) goto _L4; else goto _L3
_L3:
        }

        public Class findDeserializationContentType(Annotated annotated)
        {
            Class class1 = _primary.findDeserializationContentType(annotated);
            if(class1 == null)
                class1 = _secondary.findDeserializationContentType(annotated);
            return class1;
        }

        public Class findDeserializationKeyType(Annotated annotated)
        {
            Class class1 = _primary.findDeserializationKeyType(annotated);
            if(class1 == null)
                class1 = _secondary.findDeserializationKeyType(annotated);
            return class1;
        }

        public Class findDeserializationType(Annotated annotated)
        {
            Class class1 = _primary.findDeserializationType(annotated);
            if(class1 == null)
                class1 = _secondary.findDeserializationType(annotated);
            return class1;
        }

        public Object findDeserializer(Annotated annotated)
        {
            Object obj = _primary.findDeserializer(annotated);
            if(obj == null)
                obj = _secondary.findDeserializer(annotated);
            return obj;
        }

        public String findEnumValue(Enum enum)
        {
            String s = _primary.findEnumValue(enum);
            if(s == null)
                s = _secondary.findEnumValue(enum);
            return s;
        }

        public Boolean findFieldAutoDetection(AnnotatedClass annotatedclass)
        {
            Boolean boolean1 = _primary.findFieldAutoDetection(annotatedclass);
            if(boolean1 == null)
                boolean1 = _secondary.findFieldAutoDetection(annotatedclass);
            return boolean1;
        }

        public String findGettablePropertyName(AnnotatedMethod annotatedmethod)
        {
            String s = _primary.findGettablePropertyName(annotatedmethod);
            if(s != null) goto _L2; else goto _L1
_L1:
            s = _secondary.findGettablePropertyName(annotatedmethod);
_L4:
            return s;
_L2:
            if(s.length() == 0)
            {
                String s1 = _secondary.findGettablePropertyName(annotatedmethod);
                if(s1 != null)
                    s = s1;
            }
            if(true) goto _L4; else goto _L3
_L3:
        }

        public Boolean findGetterAutoDetection(AnnotatedClass annotatedclass)
        {
            Boolean boolean1 = _primary.findGetterAutoDetection(annotatedclass);
            if(boolean1 == null)
                boolean1 = _secondary.findGetterAutoDetection(annotatedclass);
            return boolean1;
        }

        public String findSerializablePropertyName(AnnotatedField annotatedfield)
        {
            String s = _primary.findSerializablePropertyName(annotatedfield);
            if(s != null) goto _L2; else goto _L1
_L1:
            s = _secondary.findSerializablePropertyName(annotatedfield);
_L4:
            return s;
_L2:
            if(s.length() == 0)
            {
                String s1 = _secondary.findSerializablePropertyName(annotatedfield);
                if(s1 != null)
                    s = s1;
            }
            if(true) goto _L4; else goto _L3
_L3:
        }

        public org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion findSerializationInclusion(Annotated annotated, org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion inclusion)
        {
            org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion inclusion1 = _primary.findSerializationInclusion(annotated, inclusion);
            org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion inclusion2 = _secondary.findSerializationInclusion(annotated, inclusion);
            if(inclusion1.compareTo(inclusion2) < 0)
                inclusion1 = inclusion2;
            return inclusion1;
        }

        public Class findSerializationType(Annotated annotated)
        {
            Class class1 = _primary.findSerializationType(annotated);
            if(class1 == null)
                class1 = _secondary.findSerializationType(annotated);
            return class1;
        }

        public Object findSerializer(Annotated annotated)
        {
            Object obj = _primary.findSerializer(annotated);
            if(obj == null)
                obj = _secondary.findSerializer(annotated);
            return obj;
        }

        public String findSettablePropertyName(AnnotatedMethod annotatedmethod)
        {
            String s = _primary.findSettablePropertyName(annotatedmethod);
            if(s != null) goto _L2; else goto _L1
_L1:
            s = _secondary.findSettablePropertyName(annotatedmethod);
_L4:
            return s;
_L2:
            if(s.length() == 0)
            {
                String s1 = _secondary.findSettablePropertyName(annotatedmethod);
                if(s1 != null)
                    s = s1;
            }
            if(true) goto _L4; else goto _L3
_L3:
        }

        public Boolean findSetterAutoDetection(AnnotatedClass annotatedclass)
        {
            Boolean boolean1 = _primary.findSetterAutoDetection(annotatedclass);
            if(boolean1 == null)
                boolean1 = _secondary.findSetterAutoDetection(annotatedclass);
            return boolean1;
        }

        public boolean hasAnySetterAnnotation(AnnotatedMethod annotatedmethod)
        {
            boolean flag;
            if(_primary.hasAnySetterAnnotation(annotatedmethod) || _secondary.hasAnySetterAnnotation(annotatedmethod))
                flag = true;
            else
                flag = false;
            return flag;
        }

        public boolean hasAsValueAnnotation(AnnotatedMethod annotatedmethod)
        {
            boolean flag;
            if(_primary.hasAsValueAnnotation(annotatedmethod) || _secondary.hasAsValueAnnotation(annotatedmethod))
                flag = true;
            else
                flag = false;
            return flag;
        }

        public boolean hasCreatorAnnotation(AnnotatedMethod annotatedmethod)
        {
            boolean flag;
            if(_primary.hasCreatorAnnotation(annotatedmethod) || _secondary.hasCreatorAnnotation(annotatedmethod))
                flag = true;
            else
                flag = false;
            return flag;
        }

        public boolean isHandled(Annotation annotation)
        {
            boolean flag;
            if(_primary.isHandled(annotation) || _secondary.isHandled(annotation))
                flag = true;
            else
                flag = false;
            return flag;
        }

        public boolean isIgnorableField(AnnotatedField annotatedfield)
        {
            boolean flag;
            if(_primary.isIgnorableField(annotatedfield) || _secondary.isIgnorableField(annotatedfield))
                flag = true;
            else
                flag = false;
            return flag;
        }

        public boolean isIgnorableMethod(AnnotatedMethod annotatedmethod)
        {
            boolean flag;
            if(_primary.isIgnorableMethod(annotatedmethod) || _secondary.isIgnorableMethod(annotatedmethod))
                flag = true;
            else
                flag = false;
            return flag;
        }

        final AnnotationIntrospector _primary;
        final AnnotationIntrospector _secondary;

        public Pair(AnnotationIntrospector annotationintrospector, AnnotationIntrospector annotationintrospector1)
        {
            _primary = annotationintrospector;
            _secondary = annotationintrospector1;
        }
    }


    public AnnotationIntrospector()
    {
    }

    public abstract Boolean findCachability(AnnotatedClass annotatedclass);

    public abstract Boolean findCreatorAutoDetection(AnnotatedClass annotatedclass);

    public abstract String findDeserializablePropertyName(AnnotatedField annotatedfield);

    public abstract Class findDeserializationContentType(Annotated annotated);

    public abstract Class findDeserializationKeyType(Annotated annotated);

    public abstract Class findDeserializationType(Annotated annotated);

    public abstract Object findDeserializer(Annotated annotated);

    public abstract String findEnumValue(Enum enum);

    public abstract Boolean findFieldAutoDetection(AnnotatedClass annotatedclass);

    public abstract String findGettablePropertyName(AnnotatedMethod annotatedmethod);

    public abstract Boolean findGetterAutoDetection(AnnotatedClass annotatedclass);

    public abstract String findSerializablePropertyName(AnnotatedField annotatedfield);

    public abstract org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion findSerializationInclusion(Annotated annotated, org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion inclusion);

    public abstract Class findSerializationType(Annotated annotated);

    public abstract Object findSerializer(Annotated annotated);

    public abstract String findSettablePropertyName(AnnotatedMethod annotatedmethod);

    public abstract Boolean findSetterAutoDetection(AnnotatedClass annotatedclass);

    public abstract boolean hasAnySetterAnnotation(AnnotatedMethod annotatedmethod);

    public abstract boolean hasAsValueAnnotation(AnnotatedMethod annotatedmethod);

    public abstract boolean hasCreatorAnnotation(AnnotatedMethod annotatedmethod);

    public abstract boolean isHandled(Annotation annotation);

    public abstract boolean isIgnorableField(AnnotatedField annotatedfield);

    public abstract boolean isIgnorableMethod(AnnotatedMethod annotatedmethod);
}
