// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.map;

import java.text.DateFormat;
import org.codehaus.jackson.Base64Variant;
import org.codehaus.jackson.Base64Variants;
import org.codehaus.jackson.map.introspect.AnnotatedClass;
import org.codehaus.jackson.map.util.LinkedNode;
import org.codehaus.jackson.map.util.StdDateFormat;
import org.codehaus.jackson.type.JavaType;

// Referenced classes of package org.codehaus.jackson.map:
//            AnnotationIntrospector, ClassIntrospector, DeserializationProblemHandler, BeanDescription

public class DeserializationConfig
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
            return (Feature)Enum.valueOf(org/codehaus/jackson/map/DeserializationConfig$Feature, s);
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
        public static final Feature AUTO_DETECT_CREATORS;
        public static final Feature AUTO_DETECT_FIELDS;
        public static final Feature AUTO_DETECT_SETTERS;
        public static final Feature CAN_OVERRIDE_ACCESS_MODIFIERS;
        public static final Feature USE_BIG_DECIMAL_FOR_FLOATS;
        public static final Feature USE_BIG_INTEGER_FOR_INTS;
        public static final Feature USE_GETTERS_AS_SETTERS;
        final boolean _defaultState;

        static 
        {
            AUTO_DETECT_SETTERS = new Feature("AUTO_DETECT_SETTERS", 0, true);
            AUTO_DETECT_CREATORS = new Feature("AUTO_DETECT_CREATORS", 1, true);
            AUTO_DETECT_FIELDS = new Feature("AUTO_DETECT_FIELDS", 2, true);
            USE_GETTERS_AS_SETTERS = new Feature("USE_GETTERS_AS_SETTERS", 3, true);
            CAN_OVERRIDE_ACCESS_MODIFIERS = new Feature("CAN_OVERRIDE_ACCESS_MODIFIERS", 4, true);
            USE_BIG_DECIMAL_FOR_FLOATS = new Feature("USE_BIG_DECIMAL_FOR_FLOATS", 5, false);
            USE_BIG_INTEGER_FOR_INTS = new Feature("USE_BIG_INTEGER_FOR_INTS", 6, false);
            Feature afeature[] = new Feature[7];
            afeature[0] = AUTO_DETECT_SETTERS;
            afeature[1] = AUTO_DETECT_CREATORS;
            afeature[2] = AUTO_DETECT_FIELDS;
            afeature[3] = USE_GETTERS_AS_SETTERS;
            afeature[4] = CAN_OVERRIDE_ACCESS_MODIFIERS;
            afeature[5] = USE_BIG_DECIMAL_FOR_FLOATS;
            afeature[6] = USE_BIG_INTEGER_FOR_INTS;
            $VALUES = afeature;
        }

        private Feature(String s, int i, boolean flag)
        {
            super(s, i);
            _defaultState = flag;
        }
    }


    public DeserializationConfig(ClassIntrospector classintrospector, AnnotationIntrospector annotationintrospector)
    {
        _featureFlags = DEFAULT_FEATURE_FLAGS;
        _dateFormat = DEFAULT_DATE_FORMAT;
        _classIntrospector = classintrospector;
        _annotationIntrospector = annotationintrospector;
    }

    protected DeserializationConfig(DeserializationConfig deserializationconfig)
    {
        _featureFlags = DEFAULT_FEATURE_FLAGS;
        _dateFormat = DEFAULT_DATE_FORMAT;
        _classIntrospector = deserializationconfig._classIntrospector;
        _annotationIntrospector = deserializationconfig._annotationIntrospector;
        _featureFlags = deserializationconfig._featureFlags;
        _problemHandlers = deserializationconfig._problemHandlers;
        _dateFormat = deserializationconfig._dateFormat;
    }

    public void addHandler(DeserializationProblemHandler deserializationproblemhandler)
    {
        if(!LinkedNode.contains(_problemHandlers, deserializationproblemhandler))
            _problemHandlers = new LinkedNode(deserializationproblemhandler, _problemHandlers);
    }

    public void clearHandlers()
    {
        _problemHandlers = null;
    }

    public DeserializationConfig createUnshared()
    {
        return new DeserializationConfig(this);
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
        Boolean boolean1 = _annotationIntrospector.findSetterAutoDetection(annotatedclass);
        if(boolean1 != null)
            set(Feature.AUTO_DETECT_SETTERS, boolean1.booleanValue());
        Boolean boolean2 = _annotationIntrospector.findCreatorAutoDetection(annotatedclass);
        if(boolean2 != null)
            set(Feature.AUTO_DETECT_CREATORS, boolean2.booleanValue());
    }

    public AnnotationIntrospector getAnnotationIntrospector()
    {
        return _annotationIntrospector;
    }

    public Base64Variant getBase64Variant()
    {
        return Base64Variants.getDefaultVariant();
    }

    public DateFormat getDateFormat()
    {
        return _dateFormat;
    }

    public LinkedNode getProblemHandlers()
    {
        return _problemHandlers;
    }

    public BeanDescription introspect(JavaType javatype)
    {
        return _classIntrospector.forDeserialization(this, javatype);
    }

    public BeanDescription introspectClassAnnotations(Class class1)
    {
        return _classIntrospector.forClassAnnotations(this, class1);
    }

    public BeanDescription introspectForCreation(Class class1)
    {
        return _classIntrospector.forCreation(this, class1);
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
        Object obj;
        if(dateformat == null)
            obj = StdDateFormat.instance;
        else
            obj = dateformat;
        _dateFormat = ((DateFormat) (obj));
    }

    public void setIntrospector(ClassIntrospector classintrospector)
    {
        _classIntrospector = classintrospector;
    }

    protected static final DateFormat DEFAULT_DATE_FORMAT;
    protected static final int DEFAULT_FEATURE_FLAGS = Feature.collectDefaults();
    protected AnnotationIntrospector _annotationIntrospector;
    protected ClassIntrospector _classIntrospector;
    protected DateFormat _dateFormat;
    protected int _featureFlags;
    protected LinkedNode _problemHandlers;

    static 
    {
        DEFAULT_DATE_FORMAT = StdDateFormat.instance;
    }
}
