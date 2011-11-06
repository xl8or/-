// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JMAutogen.java

package com.facebook.katana.util.jsonmirror;

import com.facebook.katana.service.method.FBJsonFactory;
import com.facebook.katana.util.ReflectionUtils;
import com.facebook.katana.util.Tuple;
import com.facebook.katana.util.jsonmirror.types.JMBase;
import com.facebook.katana.util.jsonmirror.types.JMDict;
import com.facebook.katana.util.jsonmirror.types.JMEscaped;
import com.facebook.katana.util.jsonmirror.types.JMList;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

// Referenced classes of package com.facebook.katana.util.jsonmirror:
//            JMDictDestination, JMFatalException

public class JMAutogen
{
    public static interface IgnoreSuperclassFields
        extends Annotation
    {
    }

    public static interface IgnoreUnexpectedJsonFields
        extends Annotation
    {
    }

    public static interface ListType
        extends Annotation
    {

        public abstract String jsonFieldName();

        public abstract Class[] listElementTypes();
    }

    public static interface EscapedObjectType
        extends Annotation
    {

        public abstract String jsonFieldName();
    }

    public static interface ExplicitType
        extends Annotation
    {

        public abstract String jsonFieldName();

        public abstract Class type();
    }

    public static interface InferredType
        extends Annotation
    {

        public abstract String jsonFieldName();
    }


    public JMAutogen()
    {
    }

    public static JMDict generateJMParser(Class class1)
    {
        JMDict jmdict;
        JMDict jmdict2;
        jmdict = (JMDict)jmParserCache.get(class1);
        if(jmdict != null)
            break MISSING_BLOCK_LABEL_528;
        HashMap hashmap = new HashMap();
        Iterator iterator = getRawFieldsFromClass(class1).entrySet().iterator();
label0:
        do
        {
label1:
            {
label2:
                {
label3:
                    {
                        if(!iterator.hasNext())
                            break label0;
                        java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
                        Field field = (Field)entry.getKey();
                        Annotation annotation = (Annotation)entry.getValue();
                        if(!(annotation instanceof InferredType) && !(annotation instanceof ExplicitType) && !(annotation instanceof EscapedObjectType))
                            break label1;
                        Class class2;
                        String s1;
                        String s2;
                        Object obj;
                        Object obj1;
                        if(annotation instanceof InferredType)
                        {
                            String s3 = ((InferredType)annotation).jsonFieldName();
                            class2 = field.getType();
                            s1 = s3;
                        } else
                        if(annotation instanceof ExplicitType)
                        {
                            ExplicitType explicittype = (ExplicitType)annotation;
                            s1 = explicittype.jsonFieldName();
                            class2 = explicittype.type();
                        } else
                        {
                            String s = ((EscapedObjectType)annotation).jsonFieldName();
                            class2 = field.getType();
                            s1 = s;
                        }
                        s2 = field.getName();
                        obj = JMBase.getInstanceForClass(class2);
                        if(obj == null)
                        {
                            if(!com/facebook/katana/util/jsonmirror/JMDictDestination.isAssignableFrom(class2))
                                break label3;
                            obj = generateJMParser(class2);
                        }
                        Annotation aannotation[];
                        int i;
                        int j;
                        boolean flag;
                        JMDict jmdict1;
                        ListType listtype;
                        String s4;
                        String s5;
                        JMList jmlist;
                        if(annotation instanceof EscapedObjectType)
                            obj1 = new JMEscaped(((JMBase) (obj)), new FBJsonFactory());
                        else
                            obj1 = obj;
                        if(!$assertionsDisabled && hashmap.containsKey(s1))
                            throw new AssertionError();
                        break label2;
                    }
                    throw new JMFatalException((new StringBuilder()).append("could not infer type for ").append(class1.getName()).append(":").append(s2).toString());
                }
                hashmap.put(s1, new Tuple(s2, obj1));
                continue;
            }
            if(annotation instanceof ListType)
            {
                listtype = (ListType)annotation;
                s4 = field.getName();
                s5 = listtype.jsonFieldName();
                jmlist = generateJMParser(listtype);
                if(!$assertionsDisabled && hashmap.containsKey(s5))
                    throw new AssertionError();
                hashmap.put(s5, new Tuple(s4, jmlist));
            }
        } while(true);
        aannotation = class1.getDeclaredAnnotations();
        i = aannotation.length;
        j = 0;
_L5:
        if(j >= i) goto _L2; else goto _L1
_L1:
        if(!(aannotation[j] instanceof IgnoreUnexpectedJsonFields)) goto _L4; else goto _L3
_L3:
        flag = true;
_L6:
        postprocessFields(class1, hashmap);
        jmdict1 = new JMDict(class1, hashmap, flag);
        jmParserCache.put(class1, jmdict1);
        jmdict2 = jmdict1;
_L7:
        return jmdict2;
_L4:
        j++;
          goto _L5
_L2:
        flag = false;
          goto _L6
        jmdict2 = jmdict;
          goto _L7
    }

    protected static JMList generateJMParser(ListType listtype)
    {
        HashSet hashset;
label0:
        {
            hashset = new HashSet();
            Class aclass[] = listtype.listElementTypes();
            int i = 0;
            Class class1;
            do
            {
                if(i >= aclass.length)
                    break label0;
                class1 = aclass[i];
                Object obj = JMBase.getInstanceForClass(class1);
                if(obj == null)
                {
                    if(!com/facebook/katana/util/jsonmirror/JMDictDestination.isAssignableFrom(class1))
                        break;
                    obj = generateJMParser(class1);
                }
                hashset.add(obj);
                i++;
            } while(true);
            throw new JMFatalException((new StringBuilder()).append("could not infer type for ").append(class1.getName()).toString());
        }
        return new JMList(hashset);
    }

    public static Set getJsonFieldsFromClass(Class class1)
    {
        Map map = getRawFieldsFromClass(class1);
        HashMap hashmap = new HashMap();
        for(Iterator iterator = map.values().iterator(); iterator.hasNext();)
        {
            Annotation annotation = (Annotation)iterator.next();
            if(annotation instanceof InferredType)
                hashmap.put(((InferredType)annotation).jsonFieldName(), null);
            else
            if(annotation instanceof ExplicitType)
                hashmap.put(((ExplicitType)annotation).jsonFieldName(), null);
            else
            if(annotation instanceof ListType)
                hashmap.put(((ListType)annotation).jsonFieldName(), null);
            else
                throw new JMFatalException("Got a class with unexpected JMAutogen annotations");
        }

        postprocessFields(class1, hashmap);
        return hashmap.keySet();
    }

    private static Map getRawFieldsFromClass(final Class cls)
    {
        return ReflectionUtils.getComponents(cls, new com.facebook.katana.util.ReflectionUtils.Filter() {

            public volatile Object mapper(AccessibleObject accessibleobject)
            {
                return mapper(accessibleobject);
            }

            public Annotation mapper(AccessibleObject accessibleobject)
            {
                Annotation aannotation[] = accessibleobject.getDeclaredAnnotations();
                Annotation annotation = null;
                for(int i = 0; i < aannotation.length; i++)
                {
                    Annotation annotation1 = aannotation[i];
                    if(!(annotation1 instanceof InferredType) && !(annotation1 instanceof ExplicitType) && !(annotation1 instanceof ListType) && !(annotation1 instanceof EscapedObjectType))
                        continue;
                    if(annotation != null)
                        throw new JMFatalException((new StringBuilder()).append(cls.getName()).append(":").append(accessibleobject.toString()).append(" has more than one JM annotation").toString());
                    annotation = annotation1;
                }

                return annotation;
            }

            final Class val$cls;

            
            {
                cls = class1;
                super();
            }
        }
, EnumSet.of(com.facebook.katana.util.ReflectionUtils.IncludeFlags.INCLUDE_SUPERCLASSES, com.facebook.katana.util.ReflectionUtils.IncludeFlags.INCLUDE_FIELDS));
    }

    private static void postprocessFields(Class class1, Map map)
    {
        Class aclass[] = new Class[1];
        aclass[0] = java/util/Map;
        Method method = class1.getDeclaredMethod("postprocessJMAutogenFields", aclass);
        if(method != null)
        {
            method.setAccessible(true);
            Object aobj[] = new Object[1];
            aobj[0] = map;
            method.invoke(null, aobj);
        }
_L2:
        return;
        InvocationTargetException invocationtargetexception;
        invocationtargetexception;
_L3:
        if($assertionsDisabled) goto _L2; else goto _L1
_L1:
        throw new AssertionError();
        IllegalAccessException illegalaccessexception;
        illegalaccessexception;
          goto _L3
        NoSuchMethodException nosuchmethodexception;
        nosuchmethodexception;
          goto _L3
    }

    static final boolean $assertionsDisabled;
    protected static Map jmParserCache = new HashMap();

    static 
    {
        boolean flag;
        if(!com/facebook/katana/util/jsonmirror/JMAutogen.desiredAssertionStatus())
            flag = true;
        else
            flag = false;
        $assertionsDisabled = flag;
    }
}
