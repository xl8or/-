// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Tokenizer.java

package org.xbill.DNS;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.xbill.DNS.utils.base16;
import org.xbill.DNS.utils.base32;
import org.xbill.DNS.utils.base64;

// Referenced classes of package org.xbill.DNS:
//            TextParseException, Address, Name, RelativeNameException, 
//            TTL

public class Tokenizer
{
    static class TokenizerException extends TextParseException
    {

        public String getBaseMessage()
        {
            return message;
        }

        String message;

        public TokenizerException(String s, int i, String s1)
        {
            TextParseException((new StringBuilder()).append(s).append(":").append(i).append(": ").append(s1).toString());
            message = s1;
        }
    }

    public static class Token
    {

        private Token set(int i, StringBuffer stringbuffer)
        {
            if(i < 0)
                throw new IllegalArgumentException();
            type = i;
            String s;
            if(stringbuffer == null)
                s = null;
            else
                s = stringbuffer.toString();
            value = s;
            return this;
        }

        public boolean isEOL()
        {
            boolean flag;
            if(type == 1 || type == 0)
                flag = true;
            else
                flag = false;
            return flag;
        }

        public boolean isString()
        {
            boolean flag;
            if(type == 3 || type == 4)
                flag = true;
            else
                flag = false;
            return flag;
        }

        public String toString()
        {
            type;
            JVM INSTR tableswitch 0 5: default 44
        //                       0 49
        //                       1 55
        //                       2 61
        //                       3 67
        //                       4 98
        //                       5 129;
               goto _L1 _L2 _L3 _L4 _L5 _L6 _L7
_L1:
            String s = "<unknown>";
_L9:
            return s;
_L2:
            s = "<eof>";
            continue; /* Loop/switch isn't completed */
_L3:
            s = "<eol>";
            continue; /* Loop/switch isn't completed */
_L4:
            s = "<whitespace>";
            continue; /* Loop/switch isn't completed */
_L5:
            s = (new StringBuilder()).append("<identifier: ").append(value).append(">").toString();
            continue; /* Loop/switch isn't completed */
_L6:
            s = (new StringBuilder()).append("<quoted_string: ").append(value).append(">").toString();
            continue; /* Loop/switch isn't completed */
_L7:
            s = (new StringBuilder()).append("<comment: ").append(value).append(">").toString();
            if(true) goto _L9; else goto _L8
_L8:
        }

        public int type;
        public String value;


        private Token()
        {
            type = -1;
            value = null;
        }

    }


    public Tokenizer(File file)
        throws FileNotFoundException
    {
        Tokenizer(((InputStream) (new FileInputStream(file))));
        wantClose = true;
        filename = file.getName();
    }

    public Tokenizer(InputStream inputstream)
    {
        Object obj;
        if(!(inputstream instanceof BufferedInputStream))
            obj = new BufferedInputStream(inputstream);
        else
            obj = inputstream;
        is = new PushbackInputStream(((InputStream) (obj)), 2);
        ungottenToken = false;
        multiline = 0;
        quoting = false;
        delimiters = delim;
        current = new Token();
        sb = new StringBuffer();
        filename = "<none>";
        line = 1;
    }

    public Tokenizer(String s)
    {
        Tokenizer(((InputStream) (new ByteArrayInputStream(s.getBytes()))));
    }

    private String _getIdentifier(String s)
        throws IOException
    {
        Token token = get();
        if(token.type != 3)
            throw exception((new StringBuilder()).append("expected ").append(s).toString());
        else
            return token.value;
    }

    private void checkUnbalancedParens()
        throws TextParseException
    {
        if(multiline > 0)
            throw exception("unbalanced parentheses");
        else
            return;
    }

    private int getChar()
        throws IOException
    {
        int i = is.read();
        if(i == 13)
        {
            int j = is.read();
            if(j != 10)
                is.unread(j);
            i = 10;
        }
        if(i == 10)
            line = 1 + line;
        return i;
    }

    private String remainingStrings()
        throws IOException
    {
        StringBuffer stringbuffer = null;
        do
        {
            Token token = get();
            if(!token.isString())
            {
                unget();
                String s;
                if(stringbuffer == null)
                    s = null;
                else
                    s = stringbuffer.toString();
                return s;
            }
            if(stringbuffer == null)
                stringbuffer = new StringBuffer();
            stringbuffer.append(token.value);
        } while(true);
    }

    private int skipWhitespace()
        throws IOException
    {
        int i = 0;
        do
        {
            int j = getChar();
            if(j != 32 && j != 9 && (j != 10 || multiline <= 0))
            {
                ungetChar(j);
                return i;
            }
            i++;
        } while(true);
    }

    private void ungetChar(int i)
        throws IOException
    {
        if(i != -1) goto _L2; else goto _L1
_L1:
        return;
_L2:
        is.unread(i);
        if(i == 10)
            line = line - 1;
        if(true) goto _L1; else goto _L3
_L3:
    }

    public void close()
    {
        if(!wantClose)
            break MISSING_BLOCK_LABEL_14;
        is.close();
_L2:
        return;
        IOException ioexception;
        ioexception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public TextParseException exception(String s)
    {
        return new TokenizerException(filename, line, s);
    }

    protected void finalize()
    {
        close();
    }

    public Token get()
        throws IOException
    {
        return get(false, false);
    }

    public Token get(boolean flag, boolean flag1)
        throws IOException
    {
        if(!ungottenToken) goto _L2; else goto _L1
_L1:
        ungottenToken = false;
        if(current.type != 2) goto _L4; else goto _L3
_L3:
        if(!flag) goto _L2; else goto _L5
_L5:
        Token token = current;
_L11:
        return token;
_L4:
        if(current.type == 5)
        {
            if(flag1)
            {
                token = current;
                continue; /* Loop/switch isn't completed */
            }
        } else
        {
            if(current.type == 1)
                line = 1 + line;
            token = current;
            continue; /* Loop/switch isn't completed */
        }
_L2:
        byte byte0;
        if(skipWhitespace() > 0 && flag)
        {
            token = current.set(2, null);
            continue; /* Loop/switch isn't completed */
        }
        byte0 = 3;
        sb.setLength(0);
_L7:
        int i = getChar();
        if(i == -1 || delimiters.indexOf(i) != -1)
        {
            if(i == -1)
            {
                if(quoting)
                    throw exception("EOF in quoted string");
                if(sb.length() == 0)
                    token = current.set(0, null);
                else
                    token = current.set(byte0, sb);
                continue; /* Loop/switch isn't completed */
            }
            if(sb.length() == 0 && byte0 != 4)
            {
                if(i == 40)
                {
                    multiline = 1 + multiline;
                    skipWhitespace();
                    continue; /* Loop/switch isn't completed */
                }
                if(i == 41)
                {
                    if(multiline <= 0)
                        throw exception("invalid close parenthesis");
                    multiline = multiline - 1;
                    skipWhitespace();
                    continue; /* Loop/switch isn't completed */
                }
                if(i == 34)
                {
                    if(!quoting)
                    {
                        quoting = true;
                        delimiters = quotes;
                        byte0 = 4;
                    } else
                    {
                        quoting = false;
                        delimiters = delim;
                        skipWhitespace();
                    }
                    continue; /* Loop/switch isn't completed */
                }
                if(i == 10)
                {
                    token = current.set(1, null);
                    continue; /* Loop/switch isn't completed */
                }
                if(i == 59)
                {
                    int j;
                    do
                    {
                        j = getChar();
                        if(j == 10 || j == -1)
                        {
                            if(!flag1)
                                break;
                            ungetChar(j);
                            token = current.set(5, sb);
                            continue; /* Loop/switch isn't completed */
                        }
                        sb.append((char)j);
                    } while(true);
                    if(j == -1 && byte0 != 4)
                    {
                        checkUnbalancedParens();
                        token = current.set(0, null);
                    } else
                    {
                        if(multiline > 0)
                        {
                            skipWhitespace();
                            sb.setLength(0);
                            continue; /* Loop/switch isn't completed */
                        }
                        token = current.set(1, null);
                    }
                } else
                {
                    throw new IllegalStateException();
                }
            } else
            {
                ungetChar(i);
                if(sb.length() == 0 && byte0 != 4)
                {
                    checkUnbalancedParens();
                    token = current.set(0, null);
                } else
                {
                    token = current.set(byte0, sb);
                }
            }
            continue; /* Loop/switch isn't completed */
        }
        if(i != 92)
            break; /* Loop/switch isn't completed */
        i = getChar();
        if(i == -1)
            throw exception("unterminated escape sequence");
        sb.append('\\');
_L9:
        sb.append((char)i);
        if(true) goto _L7; else goto _L6
_L6:
        if(!quoting || i != 10) goto _L9; else goto _L8
_L8:
        throw exception("newline in quoted string");
        if(true) goto _L11; else goto _L10
_L10:
    }

    public InetAddress getAddress(int i)
        throws IOException
    {
        String s = _getIdentifier("an address");
        InetAddress inetaddress;
        try
        {
            inetaddress = Address.getByAddress(s, i);
        }
        catch(UnknownHostException unknownhostexception)
        {
            throw exception(unknownhostexception.getMessage());
        }
        return inetaddress;
    }

    public byte[] getBase32String(base32 base32_1)
        throws IOException
    {
        byte abyte0[] = base32_1.fromString(_getIdentifier("a base32 string"));
        if(abyte0 == null)
            throw exception("invalid base32 encoding");
        else
            return abyte0;
    }

    public byte[] getBase64()
        throws IOException
    {
        return getBase64(false);
    }

    public byte[] getBase64(boolean flag)
        throws IOException
    {
        String s = remainingStrings();
        byte abyte0[];
        if(s == null)
        {
            if(flag)
                throw exception("expected base64 encoded string");
            abyte0 = null;
        } else
        {
            abyte0 = base64.fromString(s);
            if(abyte0 == null)
                throw exception("invalid base64 encoding");
        }
        return abyte0;
    }

    public void getEOL()
        throws IOException
    {
        Token token = get();
        if(token.type != 1 && token.type != 0)
            throw exception("expected EOL or EOF");
        else
            return;
    }

    public byte[] getHex()
        throws IOException
    {
        return getHex(false);
    }

    public byte[] getHex(boolean flag)
        throws IOException
    {
        String s = remainingStrings();
        byte abyte0[];
        if(s == null)
        {
            if(flag)
                throw exception("expected hex encoded string");
            abyte0 = null;
        } else
        {
            abyte0 = base16.fromString(s);
            if(abyte0 == null)
                throw exception("invalid hex encoding");
        }
        return abyte0;
    }

    public byte[] getHexString()
        throws IOException
    {
        byte abyte0[] = base16.fromString(_getIdentifier("a hex string"));
        if(abyte0 == null)
            throw exception("invalid hex encoding");
        else
            return abyte0;
    }

    public String getIdentifier()
        throws IOException
    {
        return _getIdentifier("an identifier");
    }

    public long getLong()
        throws IOException
    {
        String s = _getIdentifier("an integer");
        if(!Character.isDigit(s.charAt(0)))
            throw exception("expected an integer");
        long l;
        try
        {
            l = Long.parseLong(s);
        }
        catch(NumberFormatException numberformatexception)
        {
            throw exception("expected an integer");
        }
        return l;
    }

    public Name getName(Name name)
        throws IOException
    {
        String s = _getIdentifier("a name");
        Name name1;
        try
        {
            name1 = Name.fromString(s, name);
            if(!name1.isAbsolute())
                throw new RelativeNameException(name1);
        }
        catch(TextParseException textparseexception)
        {
            throw exception(textparseexception.getMessage());
        }
        return name1;
    }

    public String getString()
        throws IOException
    {
        Token token = get();
        if(!token.isString())
            throw exception("expected a string");
        else
            return token.value;
    }

    public long getTTL()
        throws IOException
    {
        String s = _getIdentifier("a TTL value");
        long l;
        try
        {
            l = TTL.parseTTL(s);
        }
        catch(NumberFormatException numberformatexception)
        {
            throw exception("expected a TTL value");
        }
        return l;
    }

    public long getTTLLike()
        throws IOException
    {
        String s = _getIdentifier("a TTL-like value");
        long l;
        try
        {
            l = TTL.parse(s, false);
        }
        catch(NumberFormatException numberformatexception)
        {
            throw exception("expected a TTL-like value");
        }
        return l;
    }

    public int getUInt16()
        throws IOException
    {
        long l = getLong();
        if(l < 0L || l > 65535L)
            throw exception("expected an 16 bit unsigned integer");
        else
            return (int)l;
    }

    public long getUInt32()
        throws IOException
    {
        long l = getLong();
        if(l < 0L || l > 0xffffffffL)
            throw exception("expected an 32 bit unsigned integer");
        else
            return l;
    }

    public int getUInt8()
        throws IOException
    {
        long l = getLong();
        if(l < 0L || l > 255L)
            throw exception("expected an 8 bit unsigned integer");
        else
            return (int)l;
    }

    public void unget()
    {
        if(ungottenToken)
            throw new IllegalStateException("Cannot unget multiple tokens");
        if(current.type == 1)
            line = line - 1;
        ungottenToken = true;
    }

    public static final int COMMENT = 5;
    public static final int EOF = 0;
    public static final int EOL = 1;
    public static final int IDENTIFIER = 3;
    public static final int QUOTED_STRING = 4;
    public static final int WHITESPACE = 2;
    private static String delim = " \t\n;()\"";
    private static String quotes = "\"";
    private Token current;
    private String delimiters;
    private String filename;
    private PushbackInputStream is;
    private int line;
    private int multiline;
    private boolean quoting;
    private StringBuffer sb;
    private boolean ungottenToken;
    private boolean wantClose;

}
