// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JMCachingDictDestination.java

package com.facebook.katana.util.jsonmirror;

import com.facebook.katana.util.*;
import com.facebook.katana.util.jsonmirror.types.JMBase;
import com.facebook.katana.util.jsonmirror.types.JMDict;
import java.io.IOException;
import java.io.StringReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.*;
import org.codehaus.jackson.*;
import org.json.*;

// Referenced classes of package com.facebook.katana.util.jsonmirror:
//            JMDictDestination, JMException, JMAutogen, JMParser

public abstract class JMCachingDictDestination extends JMDictDestination
{
    public static interface SerializableJsonObject
        extends Annotation
    {

        public abstract String jsonFieldName();

        public abstract Class type();
    }


    public JMCachingDictDestination()
    {
    }

    protected static JsonParser createJsonParser(String s)
    {
        StringReader stringreader = new StringReader(s);
        JsonParser jsonparser1;
        jsonparser1 = jf.createJsonParser(stringreader);
        jsonparser1.nextToken();
        JsonParser jsonparser = jsonparser1;
_L2:
        return jsonparser;
        JsonParseException jsonparseexception;
        jsonparseexception;
        jsonparser = null;
        continue; /* Loop/switch isn't completed */
        IOException ioexception;
        ioexception;
        jsonparser = null;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public static SerializableJsonObject getSerializableJsonObjectAnnotation(Field field)
    {
        Annotation aannotation[];
        int i;
        aannotation = field.getAnnotations();
        i = 0;
_L3:
        Annotation annotation;
        if(i >= aannotation.length)
            break MISSING_BLOCK_LABEL_40;
        annotation = aannotation[i];
        if(!(annotation instanceof SerializableJsonObject)) goto _L2; else goto _L1
_L1:
        SerializableJsonObject serializablejsonobject = (SerializableJsonObject)annotation;
_L4:
        return serializablejsonobject;
_L2:
        i++;
          goto _L3
        serializablejsonobject = null;
          goto _L4
    }

    public static Map getSerializableJsonObjectFields(Class class1)
    {
        return ReflectionUtils.getComponents(class1, new com.facebook.katana.util.ReflectionUtils.Filter() {

            public SerializableJsonObject mapper(AccessibleObject accessibleobject)
            {
                Annotation aannotation[];
                int i;
                aannotation = accessibleobject.getDeclaredAnnotations();
                i = 0;
_L3:
                Annotation annotation;
                if(i >= aannotation.length)
                    break MISSING_BLOCK_LABEL_42;
                annotation = aannotation[i];
                if(!(annotation instanceof SerializableJsonObject)) goto _L2; else goto _L1
_L1:
                SerializableJsonObject serializablejsonobject = (SerializableJsonObject)annotation;
_L4:
                return serializablejsonobject;
_L2:
                i++;
                  goto _L3
                serializablejsonobject = null;
                  goto _L4
            }

            public volatile Object mapper(AccessibleObject accessibleobject)
            {
                return mapper(accessibleobject);
            }

        }
, EnumSet.of(com.facebook.katana.util.ReflectionUtils.IncludeFlags.INCLUDE_SUPERCLASSES, com.facebook.katana.util.ReflectionUtils.IncludeFlags.INCLUDE_FIELDS));
    }

    public static JMCachingDictDestination jsonDecode(String s, Class class1)
    {
        HashMap hashmap = new HashMap();
        JSONObject jsonobject = new JSONObject(s);
        Iterator iterator = getSerializableJsonObjectFields(class1).entrySet().iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            java.util.Map.Entry entry1 = (java.util.Map.Entry)iterator.next();
            String s1 = ((SerializableJsonObject)entry1.getValue()).jsonFieldName();
            if(jsonobject.has(s1))
                try
                {
                    String s2 = jsonobject.get(s1).toString();
                    Field field1 = (Field)entry1.getKey();
                    SerializableJsonObject serializablejsonobject = (SerializableJsonObject)entry1.getValue();
                    hashmap.put(field1, serializablejsonobject.type().cast(jsonDecode(s2, serializablejsonobject.type())));
                    jsonobject.remove(s1);
                }
                catch(JSONException jsonexception) { }
        } while(true);
          goto _L1
        JSONException jsonexception1;
        jsonexception1;
        JMCachingDictDestination jmcachingdictdestination = null;
_L4:
        return jmcachingdictdestination;
_L1:
        JMCachingDictDestination jmcachingdictdestination1;
        Iterator iterator1;
        JMDict jmdict = JMAutogen.generateJMParser(class1);
        jmcachingdictdestination1 = (JMCachingDictDestination)parseJson(jsonobject.toString(), jmdict);
        iterator1 = hashmap.entrySet().iterator();
_L2:
        java.util.Map.Entry entry;
        Field field;
        if(!iterator1.hasNext())
            break MISSING_BLOCK_LABEL_265;
        entry = (java.util.Map.Entry)iterator1.next();
        field = (Field)entry.getKey();
        try
        {
            field.setAccessible(true);
            field.set(jmcachingdictdestination1, entry.getValue());
        }
        catch(IllegalAccessException illegalaccessexception) { }
          goto _L2
        jmcachingdictdestination = jmcachingdictdestination1;
        continue; /* Loop/switch isn't completed */
        JMException jmexception;
        jmexception;
        jmcachingdictdestination = null;
        continue; /* Loop/switch isn't completed */
        IOException ioexception;
        ioexception;
        jmcachingdictdestination = null;
        if(true) goto _L4; else goto _L3
_L3:
    }

    protected static Field mapField(JMCachingDictDestination jmcachingdictdestination, String s)
        throws JMException
    {
        Field field = null;
        Class class1 = jmcachingdictdestination.getClass();
        Object obj = (Map)reflectionCache.get(class1);
        if(obj != null)
        {
            field = (Field)((Map) (obj)).get(s);
        } else
        {
            obj = new HashMap();
            reflectionCache.put(class1, obj);
        }
        if(field == null)
        {
            Class class2 = class1;
            while(class2 != null && field == null) 
            {
                try
                {
                    field = class2.getDeclaredField(s);
                    field.setAccessible(true);
                    ((Map) (obj)).put(s, field);
                }
                catch(NoSuchFieldException nosuchfieldexception) { }
                class2 = class2.getSuperclass();
            }
        }
        if(field == null)
            throw new JMException((new StringBuilder()).append("cannot find field ").append(s).append(" in class ").append(class1.getName()).toString());
        else
            return field;
    }

    private static Object parseJson(String s, JMBase jmbase)
        throws IOException, JMException
    {
        return JMParser.parseJsonResponse(createJsonParser(s), jmbase);
    }

    public String jsonEncode()
    {
        JSONObject jsonobject = new JSONObject();
        Iterator iterator = JMAutogen.generateJMParser(getClass()).getFieldTypes().entrySet().iterator();
_L3:
        if(!iterator.hasNext()) goto _L2; else goto _L1
_L1:
        String s4;
        Tuple tuple;
        java.util.Map.Entry entry1 = (java.util.Map.Entry)iterator.next();
        s4 = (String)entry1.getKey();
        tuple = (Tuple)entry1.getValue();
        JSONException jsonexception;
        String s;
        Iterator iterator1;
        String s2;
        Field field;
        JMCachingDictDestination jmcachingdictdestination;
        try
        {
            Field field1 = mapField(this, (String)tuple.d0);
            if(field1.get(this) instanceof Collection)
                jsonobject.put(s4, new JSONArray((Collection)field1.get(this)));
            else
            if(field1.get(this) instanceof Map)
            {
                Map map = (Map)field1.get(this);
                JSONObject jsonobject2 = new JSONObject(map);
                jsonobject.put(s4, jsonobject2);
            } else
            {
                jsonobject.put(s4, field1.get(this));
            }
        }
        catch(IllegalAccessException illegalaccessexception1) { }
          goto _L3
        jsonexception;
        s = null;
          goto _L4
_L2:
        iterator1 = getSerializableJsonObjectFields(getClass()).entrySet().iterator();
_L5:
        if(!iterator1.hasNext())
            break MISSING_BLOCK_LABEL_325;
        java.util.Map.Entry entry = (java.util.Map.Entry)iterator1.next();
        s2 = ((SerializableJsonObject)entry.getValue()).jsonFieldName();
        field = (Field)entry.getKey();
        field.setAccessible(true);
        jmcachingdictdestination = (JMCachingDictDestination)field.get(this);
        if(jmcachingdictdestination == null)
            continue; /* Loop/switch isn't completed */
        String s1;
        try
        {
            String s3 = jmcachingdictdestination.jsonEncode();
            JSONObject jsonobject1 = new JSONObject(s3);
            jsonobject.put(s2, jsonobject1);
            continue; /* Loop/switch isn't completed */
        }
        catch(JSONException jsonexception1)
        {
            s = null;
            break; /* Loop/switch isn't completed */
        }
        catch(IllegalAccessException illegalaccessexception) { }
        continue; /* Loop/switch isn't completed */
        s1 = null;
        if(jsonobject.length() > 0)
            s1 = jsonobject.toString();
        s = s1;
        break; /* Loop/switch isn't completed */
        if(true) goto _L5; else goto _L4
        JMException jmexception;
        jmexception;
        s = null;
_L4:
        return s;
    }

    public void setBoolean(String s, boolean flag)
        throws JMException
    {
        boolean flag1;
        Field field;
        flag1 = false;
        field = mapField(this, s);
        if(field == null)
            break MISSING_BLOCK_LABEL_23;
        field.setBoolean(this, flag);
        flag1 = true;
_L2:
        if(!flag1)
            Log.e("JMCachingDictDestination", (new StringBuilder()).append("Unable to write to field ").append(s).toString());
        return;
        IllegalAccessException illegalaccessexception;
        illegalaccessexception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public void setDictionary(String s, JMDictDestination jmdictdestination)
        throws JMException
    {
        setObject(s, jmdictdestination);
    }

    public void setDouble(String s, double d)
        throws JMException
    {
        boolean flag;
        Field field;
        flag = false;
        field = mapField(this, s);
        if(field == null) goto _L2; else goto _L1
_L1:
        Class class1 = field.getType();
        if(class1 != java/lang/Double && class1 != Double.TYPE) goto _L4; else goto _L3
_L3:
        field.setDouble(this, d);
        flag = true;
_L2:
        if(!flag)
            Log.e("JMCachingDictDestination", (new StringBuilder()).append("Unable to write to field ").append(s).toString());
        return;
_L4:
        if(class1 != java/lang/Float && class1 != Float.TYPE)
            continue; /* Loop/switch isn't completed */
        field.setFloat(this, (float)d);
        flag = true;
        continue; /* Loop/switch isn't completed */
        IllegalAccessException illegalaccessexception;
        illegalaccessexception;
        if(true) goto _L2; else goto _L5
_L5:
    }

    public void setList(String s, List list)
        throws JMException
    {
        setObject(s, list);
    }

    public void setLong(String s, long l)
        throws JMException
    {
        boolean flag;
        Field field;
        flag = false;
        field = mapField(this, s);
        if(field == null) goto _L2; else goto _L1
_L1:
        Class class1 = field.getType();
        if(class1 != java/lang/Long && class1 != Long.TYPE) goto _L4; else goto _L3
_L3:
        field.setLong(this, l);
        flag = true;
_L2:
        if(!flag)
            Log.e("JMCachingDictDestination", (new StringBuilder()).append("Unable to write to field ").append(s).toString());
        return;
_L4:
        if(class1 != java/lang/Integer && class1 != Integer.TYPE)
            continue; /* Loop/switch isn't completed */
        field.setInt(this, (int)l);
        flag = true;
        continue; /* Loop/switch isn't completed */
        IllegalAccessException illegalaccessexception;
        illegalaccessexception;
        if(true) goto _L2; else goto _L5
_L5:
    }

    protected void setObject(String s, Object obj)
        throws JMException
    {
        boolean flag;
        Field field;
        flag = false;
        field = mapField(this, s);
        if(field == null)
            break MISSING_BLOCK_LABEL_23;
        field.set(this, obj);
        flag = true;
_L2:
        if(!flag)
            Log.e("JMCachingDictDestination", (new StringBuilder()).append("Unable to write to field ").append(s).toString());
        return;
        IllegalAccessException illegalaccessexception;
        illegalaccessexception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public void setSimpleDictionary(String s, Map map)
        throws JMException
    {
        setObject(s, map);
    }

    public void setString(String s, String s1)
        throws JMException
    {
        setObject(s, s1);
    }

    protected static final String TAG = "JMCachingDictDestination";
    protected static JsonFactory jf = new JsonFactory();
    protected static final Map reflectionCache = new HashMap();

}
