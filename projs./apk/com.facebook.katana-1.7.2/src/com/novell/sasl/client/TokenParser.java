// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TokenParser.java

package com.novell.sasl.client;

import org.apache.harmony.javax.security.sasl.SaslException;

class TokenParser
{

    TokenParser(String s)
    {
        m_tokens = s;
        m_curPos = 0;
        m_scanStart = 0;
        m_state = 1;
    }

    boolean isValidTokenChar(char c)
    {
        boolean flag;
        if(c >= 0 && c <= ' ' || c >= ':' && c <= '@' || c >= '[' && c <= ']' || ',' == c || '%' == c || '(' == c || ')' == c || '{' == c || '}' == c || '\177' == c)
            flag = false;
        else
            flag = true;
        return flag;
    }

    boolean isWhiteSpace(char c)
    {
        boolean flag;
        if('\t' == c || '\n' == c || '\r' == c || ' ' == c)
            flag = true;
        else
            flag = false;
        return flag;
    }

    String parseToken()
        throws SaslException
    {
        if(m_state != 6) goto _L2; else goto _L1
_L1:
        String s = null;
_L10:
        return s;
_L7:
        char c;
        if(m_curPos >= m_tokens.length() || s != null)
            continue; /* Loop/switch isn't completed */
        c = m_tokens.charAt(m_curPos);
        m_state;
        JVM INSTR tableswitch 1 4: default 76
    //                   1 89
    //                   2 89
    //                   3 156
    //                   4 261;
           goto _L3 _L4 _L4 _L5 _L6
_L3:
        break; /* Loop/switch isn't completed */
_L6:
        break MISSING_BLOCK_LABEL_261;
_L8:
        m_curPos = 1 + m_curPos;
          goto _L7
_L4:
        if(!isWhiteSpace(c))
            if(isValidTokenChar(c))
            {
                m_scanStart = m_curPos;
                m_state = 3;
            } else
            {
                m_state = 5;
                throw new SaslException((new StringBuilder()).append("Invalid token character at position ").append(m_curPos).toString());
            }
          goto _L8
_L5:
        if(!isValidTokenChar(c))
            if(isWhiteSpace(c))
            {
                s = m_tokens.substring(m_scanStart, m_curPos);
                m_state = 4;
            } else
            if(',' == c)
            {
                s = m_tokens.substring(m_scanStart, m_curPos);
                m_state = 2;
            } else
            {
                m_state = 5;
                throw new SaslException((new StringBuilder()).append("Invalid token character at position ").append(m_curPos).toString());
            }
          goto _L8
        if(!isWhiteSpace(c))
            if(c == ',')
            {
                m_state = 2;
            } else
            {
                m_state = 5;
                throw new SaslException((new StringBuilder()).append("Expected a comma, found '").append(c).append("' at postion ").append(m_curPos).toString());
            }
          goto _L8
        if(s != null) goto _L10; else goto _L9
_L9:
        switch(m_state)
        {
        case 2: // '\002'
            throw new SaslException("Trialing comma");

        case 3: // '\003'
            s = m_tokens.substring(m_scanStart);
            m_state = 6;
            break;
        }
        continue; /* Loop/switch isn't completed */
_L2:
        s = null;
          goto _L7
        if(true) goto _L10; else goto _L11
_L11:
    }

    private static final int STATE_DONE = 6;
    private static final int STATE_LOOKING_FOR_COMMA = 4;
    private static final int STATE_LOOKING_FOR_FIRST_TOKEN = 1;
    private static final int STATE_LOOKING_FOR_TOKEN = 2;
    private static final int STATE_PARSING_ERROR = 5;
    private static final int STATE_SCANNING_TOKEN = 3;
    private int m_curPos;
    private int m_scanStart;
    private int m_state;
    private String m_tokens;
}
