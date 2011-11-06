// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JMParser.java

package com.facebook.katana.util.jsonmirror;

import com.facebook.katana.Constants;
import com.facebook.katana.util.Log;
import com.facebook.katana.util.Tuple;
import com.facebook.katana.util.jsonmirror.types.JMBase;
import com.facebook.katana.util.jsonmirror.types.JMBoolean;
import com.facebook.katana.util.jsonmirror.types.JMDict;
import com.facebook.katana.util.jsonmirror.types.JMDouble;
import com.facebook.katana.util.jsonmirror.types.JMEscaped;
import com.facebook.katana.util.jsonmirror.types.JMList;
import com.facebook.katana.util.jsonmirror.types.JMLong;
import com.facebook.katana.util.jsonmirror.types.JMSimpleDict;
import com.facebook.katana.util.jsonmirror.types.JMString;
import java.io.IOException;
import java.util.*;
import org.codehaus.jackson.*;

// Referenced classes of package com.facebook.katana.util.jsonmirror:
//            JMException, JMDictDestination, JMAutogen

public class JMParser
{

    public JMParser()
    {
    }

    protected static List handleArrays(JsonParser jsonparser, Set set)
        throws JsonParseException, IOException, JMException
    {
        JMList jmlist = (JMList)validateSpecification(com/facebook/katana/util/jsonmirror/types/JMList, set);
        List list;
        if(jmlist != null)
        {
            ArrayList arraylist = new ArrayList();
            Set set1 = jmlist.getObjectTypes();
            for(JsonToken jsontoken = jsonparser.nextToken(); jsontoken != JsonToken.END_ARRAY; jsontoken = jsonparser.nextToken())
            {
                Object obj = parseJsonResponse(jsonparser, set1);
                if(obj != null)
                    arraylist.add(obj);
            }

            list = Collections.unmodifiableList(arraylist);
        } else
        {
            logUnexpectedToken(jsonparser.getCurrentToken(), set);
            jsonparser.skipChildren();
            list = null;
        }
        return list;
    }

    protected static Boolean handleBooleans(JsonParser jsonparser, Set set)
    {
        JsonToken jsontoken = jsonparser.getCurrentToken();
        Boolean boolean1;
        if(validateSpecification(com/facebook/katana/util/jsonmirror/types/JMBoolean, set) != null)
        {
            boolean flag;
            if(jsontoken == JsonToken.VALUE_TRUE)
                flag = true;
            else
                flag = false;
            boolean1 = new Boolean(flag);
        } else
        {
            logUnexpectedToken(jsontoken, set);
            boolean1 = null;
        }
        return boolean1;
    }

    protected static Object handleObjects(JsonParser jsonparser, Set set)
        throws JsonParseException, IOException, JMException
    {
        JMBase jmbase = validateSpecification(com/facebook/katana/util/jsonmirror/types/JMDict, set);
        if(jmbase == null) goto _L2; else goto _L1
_L1:
        JMDict jmdict;
        JMDictDestination jmdictdestination;
        JsonToken jsontoken5;
        String s2;
        jmdict = (JMDict)jmbase;
        jmdictdestination = jmdict.getNewInstance();
        jsontoken5 = jsonparser.nextToken();
        s2 = null;
_L7:
        if(jsontoken5 == JsonToken.END_OBJECT) goto _L4; else goto _L3
_L3:
        if(jsontoken5 != JsonToken.FIELD_NAME) goto _L6; else goto _L5
_L5:
        String s4 = jsonparser.getText();
_L13:
        jsontoken5 = jsonparser.nextToken();
        s2 = s4;
          goto _L7
_L6:
        if(!$assertionsDisabled && s2 == null)
            throw new AssertionError();
        Tuple tuple = jmdict.getObjectForJsonField(s2);
        String s3 = null;
        HashMap hashmap;
        JsonToken jsontoken;
        String s;
        JsonToken jsontoken1;
        Map map;
        Object obj;
        boolean flag;
        String s1;
        JsonToken jsontoken2;
        NumberFormatException numberformatexception;
        NumberFormatException numberformatexception1;
        JsonToken jsontoken3;
        JsonToken jsontoken4;
        JMBase jmbase1;
        boolean flag1;
        boolean flag2;
        boolean flag3;
        JMEscaped jmescaped;
        JsonParser jsonparser1;
        Object obj1;
        NumberFormatException numberformatexception2;
        boolean flag4;
        NumberFormatException numberformatexception3;
        boolean flag5;
        Object obj2;
        if(tuple != null)
        {
            String s5 = (String)tuple.d0;
            jmbase1 = (JMBase)tuple.d1;
            s3 = s5;
        } else
        {
            jmbase1 = null;
        }
        if(jsontoken5 != JsonToken.VALUE_NUMBER_INT && jsontoken5 != JsonToken.VALUE_NUMBER_FLOAT && jsontoken5 != JsonToken.VALUE_STRING) goto _L9; else goto _L8
_L8:
        flag1 = false;
        if(flag1 || jsontoken5 != JsonToken.VALUE_NUMBER_INT && jsontoken5 != JsonToken.VALUE_STRING || !validateSpecification(com/facebook/katana/util/jsonmirror/types/JMLong, jmbase1))
            break MISSING_BLOCK_LABEL_203;
        jmdictdestination.setLong(s3, Long.parseLong(jsonparser.getText()));
        flag1 = true;
_L22:
        if(!flag1 && (jsontoken5 == JsonToken.VALUE_NUMBER_INT || jsontoken5 == JsonToken.VALUE_STRING) && validateSpecification(com/facebook/katana/util/jsonmirror/types/JMBoolean, jmbase1))
        {
            flag4 = false;
            if(jsonparser.getText().equals("0"))
            {
                flag4 = false;
                flag1 = true;
            } else
            if(jsonparser.getText().equals("1"))
            {
                flag4 = true;
                flag1 = true;
            }
            if(flag1)
                jmdictdestination.setBoolean(s3, flag4);
        }
        if(flag1 || !validateSpecification(com/facebook/katana/util/jsonmirror/types/JMDouble, jmbase1))
            break MISSING_BLOCK_LABEL_1280;
        jmdictdestination.setDouble(s3, Double.parseDouble(jsonparser.getText()));
        flag2 = true;
_L14:
        if(flag2 || jsontoken5 != JsonToken.VALUE_STRING || !validateSpecification(com/facebook/katana/util/jsonmirror/types/JMEscaped, jmbase1)) goto _L11; else goto _L10
_L10:
        jmescaped = (JMEscaped)jmbase1;
        jsonparser1 = jmescaped.mFactory.createJsonParser(jsonparser.getText());
        jsonparser1.nextToken();
        obj1 = parseJsonResponse(jsonparser1, jmescaped.mInnerObject);
        if(obj1 == null) goto _L11; else goto _L12
_L12:
        if(jmescaped.mInnerObject instanceof JMList)
        {
            jmdictdestination.setList(s3, (List)obj1);
            flag3 = true;
        } else
        {
label0:
            {
                if(!(jmescaped.mInnerObject instanceof JMDict))
                    break label0;
                jmdictdestination.setDictionary(s3, (JMDictDestination)obj1);
                flag3 = true;
            }
        }
_L16:
        if(!flag3 && jsontoken5 == JsonToken.VALUE_STRING && validateSpecification(com/facebook/katana/util/jsonmirror/types/JMString, jmbase1))
        {
            jmdictdestination.setString(s3, ((JMString)jmbase1).formatString(jsonparser.getText()));
            flag3 = true;
        }
        if(!flag3 && !jmdict.mIgnoreUnexpectedJsonFields)
            logUnexpectedToken(jsonparser.getCurrentToken(), jmbase1);
        s4 = s2;
          goto _L13
        numberformatexception2;
        flag2 = flag1;
          goto _L14
        if(!(jmescaped.mInnerObject instanceof JMSimpleDict)) goto _L11; else goto _L15
_L15:
        jmdictdestination.setSimpleDictionary(s3, (Map)obj1);
        flag3 = true;
          goto _L16
_L9:
        if(jsontoken5 == JsonToken.VALUE_TRUE || jsontoken5 == JsonToken.VALUE_FALSE)
        {
            if(validateSpecification(com/facebook/katana/util/jsonmirror/types/JMBoolean, jmbase1))
            {
                if(jsontoken5 == JsonToken.VALUE_TRUE)
                    flag5 = true;
                else
                    flag5 = false;
                jmdictdestination.setBoolean(s3, flag5);
                s4 = s2;
            } else
            {
                logUnexpectedToken(jsontoken5, jmbase1);
                s4 = s2;
            }
        } else
        {
            if(jsontoken5 != JsonToken.START_ARRAY && jsontoken5 != JsonToken.START_OBJECT)
                break MISSING_BLOCK_LABEL_832;
            if(jmbase1 == null)
            {
                if(!jmdict.mIgnoreUnexpectedJsonFields)
                    logUnexpectedToken(jsontoken5, jmbase1);
                jsonparser.skipChildren();
                s4 = s2;
            } else
            {
                obj2 = parseJsonResponse(jsonparser, jmbase1);
                if(obj2 == null)
                    break MISSING_BLOCK_LABEL_832;
                if(jsontoken5 == JsonToken.START_ARRAY || obj2 == Collections.EMPTY_LIST)
                {
                    if(!$assertionsDisabled && !(obj2 instanceof List))
                        throw new AssertionError();
                    jmdictdestination.setList(s3, (List)obj2);
                    s4 = s2;
                } else
                if(obj2 instanceof JMDictDestination)
                {
                    jmdictdestination.setDictionary(s3, (JMDictDestination)obj2);
                    s4 = s2;
                } else
                {
label1:
                    {
                        if(!(obj2 instanceof Map))
                            break label1;
                        jmdictdestination.setSimpleDictionary(s3, (Map)obj2);
                        s4 = s2;
                    }
                }
            }
        }
          goto _L13
        Log.e(TAG, (new StringBuilder()).append("got a ").append(jsontoken5).append(" but don't know what to do with it.").toString());
        s4 = s2;
          goto _L13
_L4:
        jmdictdestination.postprocess();
        obj = jmdictdestination;
        s2;
_L21:
        return obj;
_L2:
        if(validateSpecification(com/facebook/katana/util/jsonmirror/types/JMSimpleDict, set) == null)
            break MISSING_BLOCK_LABEL_1171;
        hashmap = new HashMap();
        jsontoken = jsonparser.nextToken();
        s = null;
        jsontoken1 = jsontoken;
_L18:
        if(jsontoken1 == JsonToken.END_OBJECT)
            break MISSING_BLOCK_LABEL_1155;
        if(jsontoken1 != JsonToken.FIELD_NAME)
            break; /* Loop/switch isn't completed */
        s1 = jsonparser.getText();
_L19:
        jsontoken2 = jsonparser.nextToken();
        s = s1;
        jsontoken1 = jsontoken2;
        if(true) goto _L18; else goto _L17
_L17:
        if(jsontoken1 == JsonToken.VALUE_NUMBER_INT)
        {
            hashmap.put(s, Long.valueOf(jsonparser.getLongValue()));
            s1 = s;
        } else
        {
label2:
            {
                if(jsontoken1 != JsonToken.VALUE_NUMBER_FLOAT)
                    break label2;
                hashmap.put(s, Double.valueOf(jsonparser.getDoubleValue()));
                s1 = s;
            }
        }
          goto _L19
        if(jsontoken1 != JsonToken.VALUE_STRING)
            break MISSING_BLOCK_LABEL_1065;
        hashmap.put(s, Long.valueOf(Long.parseLong(jsonparser.getText())));
        s1 = s;
          goto _L19
        numberformatexception;
        try
        {
            hashmap.put(s, Double.valueOf(Double.parseDouble(jsonparser.getText())));
        }
        // Misplaced declaration of an exception variable
        catch(NumberFormatException numberformatexception1)
        {
            hashmap.put(s, jsonparser.getText());
        }
        s1 = s;
          goto _L19
        if(jsontoken1 == JsonToken.VALUE_TRUE || jsontoken1 == JsonToken.VALUE_FALSE)
        {
            if(jsontoken1 == JsonToken.VALUE_TRUE)
                flag = true;
            else
                flag = false;
            hashmap.put(s, Boolean.valueOf(flag));
            s1 = s;
        } else
        {
            if(jsontoken1 == JsonToken.START_ARRAY || jsontoken1 == JsonToken.START_OBJECT)
            {
                Log.e(TAG, "Unexpected object/array in simple dictionary");
                jsonparser.skipChildren();
            }
            s1 = s;
        }
          goto _L19
        map = Collections.unmodifiableMap(hashmap);
        s;
        obj = map;
        continue; /* Loop/switch isn't completed */
        if(validateSpecification(com/facebook/katana/util/jsonmirror/types/JMList, set) != null)
        {
            jsontoken3 = jsonparser.nextToken();
            if(jsontoken3 == JsonToken.END_OBJECT)
            {
                obj = Collections.EMPTY_LIST;
                continue; /* Loop/switch isn't completed */
            }
            logUnexpectedToken(JsonToken.START_OBJECT, set);
            for(jsontoken4 = jsontoken3; jsontoken4 != JsonToken.END_OBJECT; jsontoken4 = jsonparser.nextToken())
                if(jsontoken4 == JsonToken.START_ARRAY || jsontoken4 == JsonToken.START_OBJECT)
                    jsonparser.skipChildren();

        } else
        {
            logUnexpectedToken(jsonparser.getCurrentToken(), set);
            jsonparser.skipChildren();
        }
        obj = null;
        if(true) goto _L21; else goto _L20
_L20:
        numberformatexception3;
          goto _L22
_L11:
        flag3 = flag2;
          goto _L16
        flag2 = flag1;
          goto _L14
    }

    protected static Object handleStringsAndNumbers(JsonToken jsontoken, JsonParser jsonparser, Set set)
        throws JsonParseException, IOException
    {
        if(jsontoken != JsonToken.VALUE_NUMBER_INT && jsontoken != JsonToken.VALUE_STRING || validateSpecification(com/facebook/katana/util/jsonmirror/types/JMLong, set) == null) goto _L2; else goto _L1
_L1:
        Long long1 = Long.valueOf(Long.parseLong(jsonparser.getText()));
        Object obj = long1;
_L4:
        return obj;
        NumberFormatException numberformatexception1;
        numberformatexception1;
_L2:
        if(validateSpecification(com/facebook/katana/util/jsonmirror/types/JMDouble, set) == null)
            break MISSING_BLOCK_LABEL_71;
        Double double1 = Double.valueOf(Double.parseDouble(jsonparser.getText()));
        obj = double1;
        continue; /* Loop/switch isn't completed */
        NumberFormatException numberformatexception;
        numberformatexception;
        JMEscaped jmescaped;
        JsonParser jsonparser1;
        if((jsontoken == JsonToken.VALUE_NUMBER_INT || jsontoken == JsonToken.VALUE_STRING) && validateSpecification(com/facebook/katana/util/jsonmirror/types/JMBoolean, set) != null)
        {
            if(jsonparser.getText().equals("0"))
            {
                obj = Boolean.valueOf(false);
                continue; /* Loop/switch isn't completed */
            }
            if(jsonparser.getText().equals("1"))
            {
                obj = Boolean.valueOf(true);
                continue; /* Loop/switch isn't completed */
            }
        }
        if(jsontoken != JsonToken.VALUE_STRING)
            break MISSING_BLOCK_LABEL_228;
        JMBase jmbase1 = validateSpecification(com/facebook/katana/util/jsonmirror/types/JMEscaped, set);
        if(jmbase1 == null)
            break MISSING_BLOCK_LABEL_228;
        if(!$assertionsDisabled && !(jmbase1 instanceof JMEscaped))
            throw new AssertionError();
        jmescaped = (JMEscaped)jmbase1;
        jsonparser1 = jmescaped.mFactory.createJsonParser(jsonparser.getText());
        jsonparser1.nextToken();
        Object obj1 = parseJsonResponse(jsonparser1, jmescaped.mInnerObject);
        if(obj1 != null)
        {
            obj = obj1;
            continue; /* Loop/switch isn't completed */
        }
        break MISSING_BLOCK_LABEL_228;
        JMException jmexception;
        jmexception;
        if(jsontoken == JsonToken.VALUE_STRING)
        {
            JMBase jmbase = validateSpecification(com/facebook/katana/util/jsonmirror/types/JMString, set);
            if(jmbase != null)
            {
                if(!$assertionsDisabled && !(jmbase instanceof JMString))
                    throw new AssertionError();
                obj = ((JMString)jmbase).formatString(jsonparser.getText());
                continue; /* Loop/switch isn't completed */
            }
        }
        logUnexpectedToken(jsonparser.getCurrentToken(), set);
        obj = null;
        if(true) goto _L4; else goto _L3
_L3:
    }

    protected static void logUnexpectedToken(JsonToken jsontoken, JMBase jmbase)
    {
        if(Constants.isBetaBuild())
        {
            StringBuilder stringbuilder = new StringBuilder("Unexpected token ");
            stringbuilder.append(jsontoken.toString());
            if(jmbase != null)
            {
                stringbuilder.append("; expecting a ");
                stringbuilder.append(jmbase.toString());
            } else
            {
                stringbuilder.append("; field not defined.");
            }
            Log.e(TAG, stringbuilder.toString());
        }
    }

    protected static void logUnexpectedToken(JsonToken jsontoken, Set set)
    {
        if(Constants.isBetaBuild())
        {
            boolean flag = true;
            StringBuilder stringbuilder = new StringBuilder("Unexpected token ");
            stringbuilder.append(jsontoken.toString());
            stringbuilder.append("; expecting one of the following: (");
            Iterator iterator = set.iterator();
            while(iterator.hasNext()) 
            {
                JMBase jmbase = (JMBase)iterator.next();
                if(flag)
                    flag = false;
                else
                    stringbuilder.append(", ");
                stringbuilder.append(jmbase.toString());
            }
            stringbuilder.append(")");
            Log.e(TAG, stringbuilder.toString());
        }
    }

    public static Object parseJsonResponse(JsonParser jsonparser, JMBase jmbase)
        throws IOException, JMException
    {
        HashSet hashset = new HashSet();
        hashset.add(jmbase);
        return parseJsonResponse(jsonparser, ((Set) (hashset)));
    }

    public static Object parseJsonResponse(JsonParser jsonparser, Set set)
        throws IOException, JMException
    {
        JsonToken jsontoken;
        Object obj;
        jsontoken = jsonparser.getCurrentToken();
        obj = null;
        if(jsontoken != JsonToken.VALUE_NUMBER_INT && jsontoken != JsonToken.VALUE_NUMBER_FLOAT && jsontoken != JsonToken.VALUE_STRING) goto _L2; else goto _L1
_L1:
        obj = handleStringsAndNumbers(jsontoken, jsonparser, set);
_L4:
        return obj;
_L2:
        if(jsontoken == JsonToken.VALUE_TRUE || jsontoken == JsonToken.VALUE_FALSE)
            obj = handleBooleans(jsonparser, set);
        else
        if(jsontoken == JsonToken.START_ARRAY)
            obj = handleArrays(jsonparser, set);
        else
        if(jsontoken == JsonToken.START_OBJECT)
            obj = handleObjects(jsonparser, set);
        if(true) goto _L4; else goto _L3
_L3:
    }

    public static Object parseObjectJson(JsonParser jsonparser, Class class1)
        throws JsonParseException, IOException, JMException
    {
        Object obj = parseJsonResponse(jsonparser, JMAutogen.generateJMParser(class1));
        if(!$assertionsDisabled && obj != null && obj.getClass() != class1)
            throw new AssertionError();
        else
            return obj;
    }

    public static List parseObjectListJson(JsonParser jsonparser, Class class1)
        throws JsonParseException, IOException, JMException
    {
        JMDict jmdict = JMAutogen.generateJMParser(class1);
        HashSet hashset = new HashSet();
        hashset.add(jmdict);
        Object obj = parseJsonResponse(jsonparser, new JMList(hashset));
        if(!$assertionsDisabled && obj != null && !(obj instanceof List))
            throw new AssertionError();
        else
            return (List)obj;
    }

    protected static JMBase validateSpecification(Class class1, Set set)
    {
        Iterator iterator = set.iterator();
_L4:
        if(!iterator.hasNext()) goto _L2; else goto _L1
_L1:
        JMBase jmbase1 = (JMBase)iterator.next();
        if(!class1.isInstance(jmbase1)) goto _L4; else goto _L3
_L3:
        JMBase jmbase = jmbase1;
_L6:
        return jmbase;
_L2:
        jmbase = null;
        if(true) goto _L6; else goto _L5
_L5:
    }

    protected static boolean validateSpecification(Class class1, JMBase jmbase)
    {
        return class1.isInstance(jmbase);
    }

    static final boolean $assertionsDisabled;
    private static String TAG = "JMParser";

    static 
    {
        boolean flag;
        if(!com/facebook/katana/util/jsonmirror/JMParser.desiredAssertionStatus())
            flag = true;
        else
            flag = false;
        $assertionsDisabled = flag;
    }
}
