// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DirectiveList.java

package com.novell.sasl.client;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.harmony.javax.security.sasl.SaslException;

// Referenced classes of package com.novell.sasl.client:
//            ParsedDirective

class DirectiveList
{

    DirectiveList(byte abyte0[])
    {
        m_curPos = 0;
        m_state = 1;
        m_directiveList = new ArrayList(10);
        m_scanStart = 0;
        m_errorPos = -1;
        m_directives = new String(abyte0, "UTF-8");
_L1:
        return;
        UnsupportedEncodingException unsupportedencodingexception;
        unsupportedencodingexception;
        m_state = 9;
          goto _L1
    }

    void addDirective(String s, boolean flag)
    {
        String s1;
        int k;
        if(!flag)
        {
            s1 = m_directives.substring(m_scanStart, m_curPos);
        } else
        {
            StringBuffer stringbuffer = new StringBuffer(m_curPos - m_scanStart);
            int i = 0;
            for(int j = m_scanStart; j < m_curPos; j++)
            {
                if('\\' == m_directives.charAt(j))
                    j++;
                stringbuffer.setCharAt(i, m_directives.charAt(j));
                i++;
            }

            s1 = new String(stringbuffer);
        }
        if(m_state == 7)
            k = 1;
        else
            k = 2;
        m_directiveList.add(new ParsedDirective(s, s1, k));
    }

    Iterator getIterator()
    {
        return m_directiveList.iterator();
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

    void parseDirectives()
        throws SaslException
    {
        String s;
        boolean flag;
        int i;
        s = "<no name>";
        if(m_state == 9)
            throw new SaslException("No UTF-8 support on platform");
        flag = false;
        i = 0;
_L18:
        if(m_curPos >= m_directives.length()) goto _L2; else goto _L1
_L1:
        char c = m_directives.charAt(m_curPos);
        m_state;
        JVM INSTR tableswitch 1 8: default 104
    //                   1 165
    //                   2 165
    //                   3 217
    //                   4 308
    //                   5 350
    //                   6 540
    //                   7 501
    //                   8 429;
           goto _L3 _L4 _L4 _L5 _L6 _L7 _L8 _L9 _L10
_L3:
        if(true) goto _L11; else goto _L2
_L2:
        if(false) goto _L13; else goto _L12
_L12:
        m_state;
        JVM INSTR tableswitch 1 8: default 164
    //                   1 164
    //                   2 607
    //                   3 617
    //                   4 617
    //                   5 617
    //                   6 164
    //                   7 627
    //                   8 598;
           goto _L13 _L13 _L14 _L15 _L15 _L15 _L13 _L16 _L17
_L13:
        return;
_L4:
        if(!isWhiteSpace(c))
            if(isValidTokenChar(c))
            {
                m_scanStart = m_curPos;
                m_state = 3;
            } else
            {
                m_errorPos = m_curPos;
                throw new SaslException("Parse error: Invalid name character");
            }
          goto _L3
_L5:
        if(!isValidTokenChar(c))
            if(isWhiteSpace(c))
            {
                s = m_directives.substring(m_scanStart, m_curPos);
                m_state = 4;
            } else
            if('=' == c)
            {
                s = m_directives.substring(m_scanStart, m_curPos);
                m_state = 5;
            } else
            {
                m_errorPos = m_curPos;
                throw new SaslException("Parse error: Invalid name character");
            }
          goto _L3
_L6:
        if(!isWhiteSpace(c))
            if('=' == c)
            {
                m_state = 5;
            } else
            {
                m_errorPos = m_curPos;
                throw new SaslException("Parse error: Expected equals sign '='.");
            }
          goto _L3
_L7:
        if(!isWhiteSpace(c))
            if('"' == c)
            {
                m_scanStart = 1 + m_curPos;
                m_state = 7;
            } else
            if(isValidTokenChar(c))
            {
                m_scanStart = m_curPos;
                m_state = 8;
            } else
            {
                m_errorPos = m_curPos;
                throw new SaslException("Parse error: Unexpected character");
            }
          goto _L3
_L10:
        if(!isValidTokenChar(c))
            if(isWhiteSpace(c))
            {
                addDirective(s, false);
                m_state = 6;
            } else
            if(',' == c)
            {
                addDirective(s, false);
                m_state = 2;
            } else
            {
                m_errorPos = m_curPos;
                throw new SaslException("Parse error: Invalid value character");
            }
          goto _L3
_L9:
        if('\\' == c)
            flag = true;
        if('"' == c && 92 != i)
        {
            addDirective(s, flag);
            m_state = 6;
            flag = false;
        }
          goto _L3
_L8:
        if(!isWhiteSpace(c))
            if(c == ',')
            {
                m_state = 2;
            } else
            {
                m_errorPos = m_curPos;
                throw new SaslException("Parse error: Expected a comma.");
            }
          goto _L3
_L11:
        m_curPos = 1 + m_curPos;
        i = c;
          goto _L18
_L17:
        addDirective(s, false);
          goto _L13
_L14:
        throw new SaslException("Parse error: Trailing comma.");
_L15:
        throw new SaslException("Parse error: Missing value.");
_L16:
        throw new SaslException("Parse error: Missing closing quote.");
          goto _L3
    }

    private static final int STATE_LOOKING_FOR_COMMA = 6;
    private static final int STATE_LOOKING_FOR_DIRECTIVE = 2;
    private static final int STATE_LOOKING_FOR_EQUALS = 4;
    private static final int STATE_LOOKING_FOR_FIRST_DIRECTIVE = 1;
    private static final int STATE_LOOKING_FOR_VALUE = 5;
    private static final int STATE_NO_UTF8_SUPPORT = 9;
    private static final int STATE_SCANNING_NAME = 3;
    private static final int STATE_SCANNING_QUOTED_STRING_VALUE = 7;
    private static final int STATE_SCANNING_TOKEN_VALUE = 8;
    private String m_curName;
    private int m_curPos;
    private ArrayList m_directiveList;
    private String m_directives;
    private int m_errorPos;
    private int m_scanStart;
    private int m_state;
}
