// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.map;

import java.text.DateFormat;
import org.codehaus.jackson.map.introspect.AnnotatedClass;
import org.codehaus.jackson.map.util.StdDateFormat;

// Referenced classes of package org.codehaus.jackson.map:
//            AnnotationIntrospector, ClassIntrospector, BeanDescription

public class SerializationConfig
{
    public static final class Feature extends Enum
    {

        public static int collectDefaults()
        {
            int i = 0;
            Feature afeature[] = values();
            int j = afeature.length;
            int k = i;
            for(; i < j; i++)
            {
                Feature feature = afeature[i];
                if(feature.enabledByDefault())
                    k |= feature.getMask();
            }

            return k;
        }

        public static Feature valueOf(String s)
        {
            return (Feature)Enum.valueOf(org/codehaus/jackson/map/SerializationConfig$Feature, s);
        }

        public static Feature[] values()
        {
            return (Feature[])$VALUES.clone();
        }

        public boolean enabledByDefault()
        {
            return _defaultState;
        }

        public int getMask()
        {
            return 1 << ordinal();
        }

        private static final Feature $VALUES[];
        public static final Feature AUTO_DETECT_FIELDS;
        public static final Feature AUTO_DETECT_GETTERS;
        public static final Feature CAN_OVERRIDE_ACCESS_MODIFIERS;
        public static final Feature INDENT_OUTPUT;
        public static final Feature WRITE_DATES_AS_TIMESTAMPS;
        public static final Feature WRITE_NULL_PROPERTIES;
        final boolean _defaultState;

        static 
        {
            AUTO_DETECT_GETTERS = new Feature("AUTO_DETECT_GETTERS", 0, true);
            AUTO_DETECT_FIELDS = new Feature("AUTO_DETECT_FIELDS", 1, true);
            CAN_OVERRIDE_ACCESS_MODIFIERS = new Feature("CAN_OVERRIDE_ACCESS_MODIFIERS", 2, true);
            WRITE_NULL_PROPERTIES = new Feature("WRITE_NULL_PROPERTIES", 3, true);
            WRITE_DATES_AS_TIMESTAMPS = new Feature("WRITE_DATES_AS_TIMESTAMPS", 4, true);
            INDENT_OUTPUT = new Feature("INDENT_OUTPUT", 5, false);
            Feature afeature[] = new Feature[6];
            afeature[0] = AUTO_DETECT_GETTERS;
            afeature[1] = AUTO_DETECT_FIELDS;
            afeature[2] = CAN_OVERRIDE_ACCESS_MODIFIERS;
            afeature[3] = WRITE_NULL_PROPERTIES;
            afeature[4] = WRITE_DATES_AS_TIMESTAMPS;
            afeature[5] = INDENT_OUTPUT;
            $VALUES = afeature;
        }

        private Feature(String s, int i, boolean flag)
        {
            super(s, i);
            _defaultState = flag;
        }
    }


    public SerializationConfig(ClassIntrospector classintrospector, AnnotationIntrospector annotationintrospector)
    {
        _featureFlags = DEFAULT_FEATURE_FLAGS;
        _dateFormat = StdDateFormat.instance;
        _serializationInclusion = null;
        _classIntrospector = classintrospector;
        _annotationIntrospector = annotationintrospector;
    }

    protected SerializationConfig(SerializationConfig serializationconfig)
    {
        _featureFlags = DEFAULT_FEATURE_FLAGS;
        _dateFormat = StdDateFormat.instance;
        _serializationInclusion = null;
        _classIntrospector = serializationconfig._classIntrospector;
        _annotationIntrospector = serializationconfig._annotationIntrospector;
        _featureFlags = serializationconfig._featureFlags;
        _dateFormat = serializationconfig._dateFormat;
        _serializationInclusion = serializationconfig._serializationInclusion;
    }

    public SerializationConfig createUnshared()
    {
        return new SerializationConfig(this);
    }

    public void disable(Feature feature)
    {
        _featureFlags = _featureFlags & (-1 ^ feature.getMask());
    }

    public void enable(Feature feature)
    {
        _featureFlags = _featureFlags | feature.getMask();
    }

    public void fromAnnotations(Class class1)
    {
        AnnotatedClass annotatedclass = AnnotatedClass.construct(class1, _annotationIntrospector);
        Boolean boolean1 = _annotationIntrospector.findGetterAutoDetection(annotatedclass);
        if(boolean1 != null)
            set(Feature.AUTO_DETECT_GETTERS, boolean1.booleanValue());
        org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion inclusion = _annotationIntrospector.findSerializationInclusion(annotatedclass, null);
        if(inclusion != _serializationInclusion)
            setSerializationInclusion(inclusion);
    }

    public AnnotationIntrospector getAnnotationIntrospector()
    {
        return _annotationIntrospector;
    }

    public DateFormat getDateFormat()
    {
        return _dateFormat;
    }

    public org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion getSerializationInclusion()
    {
        org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion inclusion;
        if(_serializationInclusion != null)
            inclusion = _serializationInclusion;
        else
        if(isEnabled(Feature.WRITE_NULL_PROPERTIES))
            inclusion = org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion.ALWAYS;
        else
            inclusion = org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion.NON_NULL;
        return inclusion;
    }

    public BeanDescription introspect(Class class1)
    {
        return _classIntrospector.forSerialization(this, class1);
    }

    public BeanDescription introspectClassAnnotations(Class class1)
    {
        return _classIntrospector.forClassAnnotations(this, class1);
    }

    public final boolean isEnabled(Feature feature)
    {
        boolean flag;
        if((_featureFlags & feature.getMask()) != 0)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public void set(Feature feature, boolean flag)
    {
        if(flag)
            enable(feature);
        else
            disable(feature);
    }

    public void setAnnotationIntrospector(AnnotationIntrospector annotationintrospector)
    {
        _annotationIntrospector = annotationintrospector;
    }

    public void setDateFormat(DateFormat dateformat)
    {
        _dateFormat = dateformat;
        Feature feature = Feature.WRITE_DATES_AS_TIMESTAMPS;
        boolean flag;
        if(dateformat == null)
            flag = true;
        else
            flag = false;
        set(feature, flag);
    }

    public void setIntrospector(ClassIntrospector classintrospector)
    {
        _classIntrospector = classintrospector;
    }

    public void setSerializationInclusion(org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion inclusion)
    {
        _serializationInclusion = inclusion;
        if(inclusion == org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion.NON_NULL)
            disable(Feature.WRITE_NULL_PROPERTIES);
        else
            enable(Feature.WRITE_NULL_PROPERTIES);
    }

    public String toString()
    {
        return (new StringBuilder()).append("[SerializationConfig: flags=0x").append(Integer.toHexString(_featureFlags)).append("]").toString();
    }

    protected static final int DEFAULT_FEATURE_FLAGS = Feature.collectDefaults();
    protected AnnotationIntrospector _annotationIntrospector;
    protected ClassIntrospector _classIntrospector;
    protected DateFormat _dateFormat;
    protected int _featureFlags;
    protected org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion _serializationInclusion;

}
