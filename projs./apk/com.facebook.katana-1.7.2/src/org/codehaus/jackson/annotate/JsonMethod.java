// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.annotate;


public final class JsonMethod extends Enum
{

    private JsonMethod(String s, int i)
    {
        super(s, i);
    }

    public static JsonMethod valueOf(String s)
    {
        return (JsonMethod)Enum.valueOf(org/codehaus/jackson/annotate/JsonMethod, s);
    }

    public static JsonMethod[] values()
    {
        return (JsonMethod[])$VALUES.clone();
    }

    public boolean creatorEnabled()
    {
        boolean flag;
        if(this == CREATOR || this == ALL)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean fieldEnabled()
    {
        boolean flag;
        if(this == FIELD || this == ALL)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean getterEnabled()
    {
        boolean flag;
        if(this == GETTER || this == ALL)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean setterEnabled()
    {
        boolean flag;
        if(this == SETTER || this == ALL)
            flag = true;
        else
            flag = false;
        return flag;
    }

    private static final JsonMethod $VALUES[];
    public static final JsonMethod ALL;
    public static final JsonMethod CREATOR;
    public static final JsonMethod FIELD;
    public static final JsonMethod GETTER;
    public static final JsonMethod NONE;
    public static final JsonMethod SETTER;

    static 
    {
        GETTER = new JsonMethod("GETTER", 0);
        SETTER = new JsonMethod("SETTER", 1);
        CREATOR = new JsonMethod("CREATOR", 2);
        FIELD = new JsonMethod("FIELD", 3);
        NONE = new JsonMethod("NONE", 4);
        ALL = new JsonMethod("ALL", 5);
        JsonMethod ajsonmethod[] = new JsonMethod[6];
        ajsonmethod[0] = GETTER;
        ajsonmethod[1] = SETTER;
        ajsonmethod[2] = CREATOR;
        ajsonmethod[3] = FIELD;
        ajsonmethod[4] = NONE;
        ajsonmethod[5] = ALL;
        $VALUES = ajsonmethod;
    }
}
