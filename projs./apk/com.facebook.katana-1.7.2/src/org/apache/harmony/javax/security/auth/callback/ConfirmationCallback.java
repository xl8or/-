// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ConfirmationCallback.java

package org.apache.harmony.javax.security.auth.callback;

import java.io.Serializable;

// Referenced classes of package org.apache.harmony.javax.security.auth.callback:
//            Callback

public class ConfirmationCallback
    implements Callback, Serializable
{

    public ConfirmationCallback(int i, int j, int k)
    {
        optionType = -1;
        if(i > 2 || i < 0)
            throw new IllegalArgumentException("auth.16");
        switch(j)
        {
        default:
            throw new IllegalArgumentException("auth.18");

        case 0: // '\0'
            if(k != 0 && k != 1)
                throw new IllegalArgumentException("auth.17");
            break;

        case 1: // '\001'
            if(k != 0 && k != 1 && k != 2)
                throw new IllegalArgumentException("auth.17");
            break;

        case 2: // '\002'
            if(k != 3 && k != 2)
                throw new IllegalArgumentException("auth.17");
            break;
        }
        messageType = i;
        optionType = j;
        defaultOption = k;
    }

    public ConfirmationCallback(int i, String as[], int j)
    {
        optionType = -1;
        if(i > 2 || i < 0)
            throw new IllegalArgumentException("auth.16");
        if(as == null || as.length == 0)
            throw new IllegalArgumentException("auth.1A");
        for(int k = 0; k < as.length; k++)
            if(as[k] == null || as[k].length() == 0)
                throw new IllegalArgumentException("auth.1A");

        if(j < 0 || j >= as.length)
        {
            throw new IllegalArgumentException("auth.17");
        } else
        {
            options = as;
            defaultOption = j;
            messageType = i;
            return;
        }
    }

    public ConfirmationCallback(String s, int i, int j, int k)
    {
        optionType = -1;
        if(s == null || s.length() == 0)
            throw new IllegalArgumentException("auth.14");
        if(i > 2 || i < 0)
            throw new IllegalArgumentException("auth.16");
        switch(j)
        {
        default:
            throw new IllegalArgumentException("auth.18");

        case 0: // '\0'
            if(k != 0 && k != 1)
                throw new IllegalArgumentException("auth.17");
            break;

        case 1: // '\001'
            if(k != 0 && k != 1 && k != 2)
                throw new IllegalArgumentException("auth.17");
            break;

        case 2: // '\002'
            if(k != 3 && k != 2)
                throw new IllegalArgumentException("auth.17");
            break;
        }
        prompt = s;
        messageType = i;
        optionType = j;
        defaultOption = k;
    }

    public ConfirmationCallback(String s, int i, String as[], int j)
    {
        optionType = -1;
        if(s == null || s.length() == 0)
            throw new IllegalArgumentException("auth.14");
        if(i > 2 || i < 0)
            throw new IllegalArgumentException("auth.16");
        if(as == null || as.length == 0)
            throw new IllegalArgumentException("auth.1A");
        for(int k = 0; k < as.length; k++)
            if(as[k] == null || as[k].length() == 0)
                throw new IllegalArgumentException("auth.1A");

        if(j < 0 || j >= as.length)
        {
            throw new IllegalArgumentException("auth.17");
        } else
        {
            options = as;
            defaultOption = j;
            messageType = i;
            prompt = s;
            return;
        }
    }

    public int getDefaultOption()
    {
        return defaultOption;
    }

    public int getMessageType()
    {
        return messageType;
    }

    public int getOptionType()
    {
        return optionType;
    }

    public String[] getOptions()
    {
        return options;
    }

    public String getPrompt()
    {
        return prompt;
    }

    public int getSelectedIndex()
    {
        return selection;
    }

    public void setSelectedIndex(int i)
    {
        if(options == null) goto _L2; else goto _L1
_L1:
        if(i < 0 || i > options.length) goto _L4; else goto _L3
_L3:
        selection = i;
_L10:
        return;
_L4:
        throw new ArrayIndexOutOfBoundsException("auth.1B");
_L2:
        optionType;
        JVM INSTR tableswitch 0 2: default 68
    //                   0 76
    //                   1 95
    //                   2 119;
           goto _L5 _L6 _L7 _L8
_L8:
        break MISSING_BLOCK_LABEL_119;
_L5:
        break; /* Loop/switch isn't completed */
_L6:
        break; /* Loop/switch isn't completed */
_L11:
        selection = i;
        if(true) goto _L10; else goto _L9
_L9:
        if(i != 0 && i != 1)
            throw new IllegalArgumentException("auth.19");
          goto _L11
_L7:
        if(i != 0 && i != 1 && i != 2)
            throw new IllegalArgumentException("auth.19");
          goto _L11
        if(i != 3 && i != 2)
            throw new IllegalArgumentException("auth.19");
          goto _L11
    }

    public static final int CANCEL = 2;
    public static final int ERROR = 2;
    public static final int INFORMATION = 0;
    public static final int NO = 1;
    public static final int OK = 3;
    public static final int OK_CANCEL_OPTION = 2;
    public static final int UNSPECIFIED_OPTION = -1;
    public static final int WARNING = 1;
    public static final int YES = 0;
    public static final int YES_NO_CANCEL_OPTION = 1;
    public static final int YES_NO_OPTION = 0;
    private static final long serialVersionUID = 0x11661d28L;
    private int defaultOption;
    private int messageType;
    private int optionType;
    private String options[];
    private String prompt;
    private int selection;
}
