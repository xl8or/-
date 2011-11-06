// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.map.introspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;
import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.util.ClassUtil;

// Referenced classes of package org.codehaus.jackson.map.introspect:
//            Annotated, AnnotationMap, AnnotatedConstructor, AnnotatedField, 
//            AnnotatedMethod, AnnotatedMethodMap, MethodFilter

public final class AnnotatedClass extends Annotated
{
    public static final class FactoryMethodFilter
        implements MethodFilter
    {

        public boolean includeMethod(Method method)
        {
            boolean flag;
            if(!Modifier.isStatic(method.getModifiers()))
                flag = false;
            else
            if(method.getParameterTypes().length != 1)
                flag = false;
            else
            if(method.getReturnType() == Void.TYPE)
                flag = false;
            else
                flag = true;
            return flag;
        }

        public static final FactoryMethodFilter instance = new FactoryMethodFilter();


        public FactoryMethodFilter()
        {
        }
    }


    private AnnotatedClass(Class class1, List list, AnnotationIntrospector annotationintrospector)
    {
        _class = class1;
        _superTypes = list;
        _annotationIntrospector = annotationintrospector;
    }

    private void _addFields(List list, Class class1)
    {
        Class class2 = class1.getSuperclass();
        if(class2 != null)
        {
            _addFields(list, class2);
            Field afield[] = class1.getDeclaredFields();
            int i = afield.length;
            int j = 0;
            while(j < i) 
            {
                Field field = afield[j];
                if(_isIncludableField(field))
                {
                    Annotation aannotation[] = field.getAnnotations();
                    if(Modifier.isPublic(field.getModifiers()) || aannotation.length > 0)
                    {
                        AnnotatedField annotatedfield = _constructField(field);
                        if(!_annotationIntrospector.isIgnorableField(annotatedfield))
                            list.add(annotatedfield);
                    }
                }
                j++;
            }
        }
    }

    private boolean _isIncludableField(Field field)
    {
        boolean flag;
        if(field.isSynthetic())
        {
            flag = false;
        } else
        {
            int i = field.getModifiers();
            if(Modifier.isStatic(i) || Modifier.isTransient(i))
                flag = false;
            else
                flag = true;
        }
        return flag;
    }

    public static AnnotatedClass construct(Class class1, AnnotationIntrospector annotationintrospector)
    {
        AnnotatedClass annotatedclass = new AnnotatedClass(class1, ClassUtil.findSuperTypes(class1, null), annotationintrospector);
        annotatedclass.resolveClassAnnotations();
        return annotatedclass;
    }

    protected AnnotationMap _collectRelevantAnnotations(Annotation aannotation[])
    {
        AnnotationMap annotationmap = new AnnotationMap();
        if(aannotation != null)
        {
            int i = aannotation.length;
            for(int j = 0; j < i; j++)
            {
                Annotation annotation = aannotation[j];
                if(_annotationIntrospector.isHandled(annotation))
                    annotationmap.add(annotation);
            }

        }
        return annotationmap;
    }

    protected AnnotatedConstructor _constructConstructor(Constructor constructor)
    {
        return new AnnotatedConstructor(constructor, _collectRelevantAnnotations(constructor.getDeclaredAnnotations()));
    }

    protected AnnotatedField _constructField(Field field)
    {
        return new AnnotatedField(field, _collectRelevantAnnotations(field.getDeclaredAnnotations()));
    }

    protected AnnotatedMethod _constructMethod(Method method)
    {
        return new AnnotatedMethod(method, _collectRelevantAnnotations(method.getDeclaredAnnotations()));
    }

    protected boolean _isIncludableMethod(Method method)
    {
        boolean flag;
        if(method.isSynthetic() || method.isBridge())
            flag = false;
        else
            flag = true;
        return flag;
    }

    public Iterable fields()
    {
        List list;
        if(_fields == null)
            list = Collections.emptyList();
        else
            list = _fields;
        return list;
    }

    public AnnotatedMethod findMethod(String s, Class aclass[])
    {
        return _memberMethods.find(s, aclass);
    }

    public Class getAnnotated()
    {
        return _class;
    }

    public volatile AnnotatedElement getAnnotated()
    {
        return getAnnotated();
    }

    public Annotation getAnnotation(Class class1)
    {
        Annotation annotation;
        if(_classAnnotations == null)
            annotation = null;
        else
            annotation = _classAnnotations.get(class1);
        return annotation;
    }

    public AnnotatedConstructor getDefaultConstructor()
    {
        return _defaultConstructor;
    }

    public int getFieldCount()
    {
        int i;
        if(_fields == null)
            i = 0;
        else
            i = _fields.size();
        return i;
    }

    public int getMemberMethodCount()
    {
        return _memberMethods.size();
    }

    public int getModifiers()
    {
        return _class.getModifiers();
    }

    public String getName()
    {
        return _class.getName();
    }

    public List getSingleArgConstructors()
    {
        List list;
        if(_singleArgConstructors == null)
            list = Collections.emptyList();
        else
            list = _singleArgConstructors;
        return list;
    }

    public List getSingleArgStaticMethods()
    {
        List list;
        if(_singleArgStaticMethods == null)
            list = Collections.emptyList();
        else
            list = _singleArgStaticMethods;
        return list;
    }

    public Class getType()
    {
        return _class;
    }

    public Iterable memberMethods()
    {
        return _memberMethods;
    }

    protected void resolveClassAnnotations()
    {
        _classAnnotations = new AnnotationMap();
        Annotation aannotation[] = _class.getDeclaredAnnotations();
        int i = aannotation.length;
        for(int j = 0; j < i; j++)
        {
            Annotation annotation1 = aannotation[j];
            if(_annotationIntrospector.isHandled(annotation1))
                _classAnnotations.add(annotation1);
        }

        for(Iterator iterator = _superTypes.iterator(); iterator.hasNext();)
        {
            Annotation aannotation1[] = ((Class)iterator.next()).getDeclaredAnnotations();
            int k = aannotation1.length;
            int l = 0;
            while(l < k) 
            {
                Annotation annotation = aannotation1[l];
                if(_annotationIntrospector.isHandled(annotation))
                    _classAnnotations.addIfNotPresent(annotation);
                l++;
            }
        }

    }

    public void resolveCreators(boolean flag)
    {
        Constructor aconstructor[];
        int i;
        int j;
        _singleArgConstructors = null;
        aconstructor = _class.getDeclaredConstructors();
        i = aconstructor.length;
        j = 0;
_L2:
        Constructor constructor;
        if(j >= i)
            break MISSING_BLOCK_LABEL_120;
        constructor = aconstructor[j];
        switch(constructor.getParameterTypes().length)
        {
        default:
            break;

        case 0: // '\0'
            break; /* Loop/switch isn't completed */

        case 1: // '\001'
            break;
        }
        break MISSING_BLOCK_LABEL_79;
_L3:
        j++;
        if(true) goto _L2; else goto _L1
_L1:
        _defaultConstructor = _constructConstructor(constructor);
          goto _L3
        if(flag)
        {
            if(_singleArgConstructors == null)
                _singleArgConstructors = new ArrayList();
            _singleArgConstructors.add(_constructConstructor(constructor));
        }
          goto _L3
        _singleArgStaticMethods = null;
        if(flag)
        {
            Method amethod[] = _class.getDeclaredMethods();
            int k = amethod.length;
            for(int l = 0; l < k; l++)
            {
                Method method = amethod[l];
                if(!Modifier.isStatic(method.getModifiers()) || method.getParameterTypes().length != 1)
                    continue;
                if(_singleArgStaticMethods == null)
                    _singleArgStaticMethods = new ArrayList();
                _singleArgStaticMethods.add(_constructMethod(method));
            }

        }
        return;
    }

    public void resolveFields()
    {
        _fields = new ArrayList();
        _addFields(_fields, _class);
    }

    public void resolveMemberMethods(MethodFilter methodfilter)
    {
        _memberMethods = new AnnotatedMethodMap();
        Method amethod[] = _class.getDeclaredMethods();
        int i = amethod.length;
        for(int j = 0; j < i; j++)
        {
            Method method1 = amethod[j];
            if(_isIncludableMethod(method1))
                _memberMethods.add(_constructMethod(method1));
        }

        for(Iterator iterator = _superTypes.iterator(); iterator.hasNext();)
        {
            Method amethod1[] = ((Class)iterator.next()).getDeclaredMethods();
            int k = amethod1.length;
            int l = 0;
            while(l < k) 
            {
                Method method = amethod1[l];
                if(_isIncludableMethod(method))
                {
                    AnnotatedMethod annotatedmethod1 = _memberMethods.find(method);
                    if(annotatedmethod1 == null)
                    {
                        _memberMethods.add(_constructMethod(method));
                    } else
                    {
                        Annotation aannotation[] = method.getDeclaredAnnotations();
                        int i1 = aannotation.length;
                        int j1 = 0;
                        while(j1 < i1) 
                        {
                            Annotation annotation = aannotation[j1];
                            if(_annotationIntrospector.isHandled(annotation))
                                annotatedmethod1.addIfNotPresent(annotation);
                            j1++;
                        }
                    }
                }
                l++;
            }
        }

        Iterator iterator1 = _memberMethods.iterator();
        do
        {
            if(!iterator1.hasNext())
                break;
            AnnotatedMethod annotatedmethod = (AnnotatedMethod)iterator1.next();
            if(_annotationIntrospector.isIgnorableMethod(annotatedmethod))
                iterator1.remove();
        } while(true);
    }

    public String toString()
    {
        return (new StringBuilder()).append("[AnnotedClass ").append(_class.getName()).append("]").toString();
    }

    final AnnotationIntrospector _annotationIntrospector;
    final Class _class;
    AnnotationMap _classAnnotations;
    AnnotatedConstructor _defaultConstructor;
    List _fields;
    AnnotatedMethodMap _memberMethods;
    List _singleArgConstructors;
    List _singleArgStaticMethods;
    final Collection _superTypes;
}
