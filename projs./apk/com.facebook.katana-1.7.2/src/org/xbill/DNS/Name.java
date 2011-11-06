// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Name.java

package org.xbill.DNS;

import java.io.*;
import java.text.DecimalFormat;

// Referenced classes of package org.xbill.DNS:
//            TextParseException, WireParseException, DNSInput, Options, 
//            NameTooLongException, DNAMERecord, Compression, DNSOutput

public class Name
    implements Comparable, Serializable
{

    private Name()
    {
    }

    public Name(String s)
        throws TextParseException
    {
        Name(s, ((Name) (null)));
    }

    public Name(String s, Name name1)
        throws TextParseException
    {
        if(s.equals(""))
            throw parseException(s, "empty name");
        if(s.equals("@"))
        {
            if(name1 == null)
                copy(empty, this);
            else
                copy(name1, this);
        } else
        {
label0:
            {
                if(!s.equals("."))
                    break label0;
                copy(root, this);
            }
        }
_L10:
        return;
        byte abyte0[];
        int i;
        int j;
        boolean flag;
        int k;
        int l;
        int i1;
        abyte0 = new byte[64];
        i = -1;
        j = 0;
        flag = false;
        k = 0;
        l = 0;
        i1 = 1;
_L9:
        if(j >= s.length()) goto _L2; else goto _L1
_L1:
        byte byte0 = (byte)s.charAt(j);
        if(!flag) goto _L4; else goto _L3
_L3:
        if(byte0 < 48 || byte0 > 57 || k >= 3) goto _L6; else goto _L5
_L5:
        k++;
        l = l * 10 + (byte0 - 48);
        if(l > 255)
            throw parseException(s, "bad escape");
        if(k >= 3) goto _L8; else goto _L7
_L7:
        j++;
          goto _L9
_L8:
        int k1;
        int l1;
        int i2;
        int l2 = l;
        k1 = k;
        l1 = l;
        i2 = l2;
        break MISSING_BLOCK_LABEL_194;
_L6:
        if(k > 0 && k < 3)
            throw parseException(s, "bad escape");
        break MISSING_BLOCK_LABEL_472;
_L11:
        if(i1 > 63)
            throw parseException(s, "label too long");
        int j2 = i1 + 1;
        abyte0[i1] = i2;
        int k2 = l1;
        k = k1;
        flag = false;
        l = k2;
        i = i1;
        i1 = j2;
          goto _L7
_L4:
        if(byte0 == 92)
        {
            k = 0;
            flag = true;
            l = 0;
        } else
        if(byte0 == 46)
        {
            if(i == -1)
                throw parseException(s, "invalid empty label");
            abyte0[0] = (byte)(i1 - 1);
            appendFromString(s, abyte0, 0, 1);
            i = -1;
            i1 = 1;
        } else
        {
            if(i == -1)
                i = j;
            if(i1 > 63)
                throw parseException(s, "label too long");
            int j1 = i1 + 1;
            abyte0[i1] = byte0;
            i1 = j1;
        }
          goto _L7
_L2:
        if(k > 0 && k < 3)
            throw parseException(s, "bad escape");
        if(flag)
            throw parseException(s, "bad escape");
        boolean flag1;
        if(i == -1)
        {
            appendFromString(s, emptyLabel, 0, 1);
            flag1 = true;
        } else
        {
            abyte0[0] = (byte)(i1 - 1);
            appendFromString(s, abyte0, 0, 1);
            flag1 = false;
        }
        if(name1 != null && !flag1)
            appendFromString(s, name1.name, 0, name1.getlabels());
          goto _L10
        k1 = k;
        l1 = l;
        i2 = byte0;
          goto _L11
    }

    public Name(DNSInput dnsinput)
        throws WireParseException
    {
        byte abyte0[] = new byte[64];
        boolean flag = false;
        boolean flag1 = false;
        do
        {
            if(flag1)
                break;
            int i = dnsinput.readU8();
            switch(i & 0xc0)
            {
            default:
                throw new WireParseException("bad label type");

            case 0: // '\0'
                if(getlabels() >= 128)
                    throw new WireParseException("too many labels");
                if(i == 0)
                {
                    append(emptyLabel, 0, 1);
                    flag1 = true;
                } else
                {
                    abyte0[0] = (byte)i;
                    dnsinput.readByteArray(abyte0, 1, i);
                    append(abyte0, 0, 1);
                }
                break;

            case 192: 
                int j = dnsinput.readU8() + ((i & 0xffffff3f) << 8);
                if(Options.check("verbosecompression"))
                    System.err.println((new StringBuilder()).append("currently ").append(dnsinput.current()).append(", pointer to ").append(j).toString());
                if(j >= dnsinput.current() - 2)
                    throw new WireParseException("bad compression");
                if(!flag)
                {
                    dnsinput.save();
                    flag = true;
                }
                dnsinput.jump(j);
                if(Options.check("verbosecompression"))
                    System.err.println((new StringBuilder()).append("current name '").append(this).append("', seeking to ").append(j).toString());
                break;
            }
        } while(true);
        if(flag)
            dnsinput.restore();
    }

    public Name(Name name1, int i)
    {
        int j = name1.labels();
        if(i > j)
            throw new IllegalArgumentException("attempted to remove too many labels");
        name = name1.name;
        setlabels(j - i);
        for(int k = 0; k < 7 && k < j - i; k++)
            setoffset(k, name1.offset(k + i));

    }

    public Name(byte abyte0[])
        throws IOException
    {
        Name(new DNSInput(abyte0));
    }

    private final void append(byte abyte0[], int i, int j)
        throws NameTooLongException
    {
        int k;
        int l;
        int i1;
        int j1;
        if(name == null)
            k = 0;
        else
            k = name.length - offset(0);
        l = i;
        i1 = 0;
        j1 = 0;
        for(; i1 < j; i1++)
        {
            byte byte0 = abyte0[l];
            if(byte0 > 63)
                throw new IllegalStateException("invalid label");
            int k2 = byte0 + 1;
            l += k2;
            j1 += k2;
        }

        int k1 = k + j1;
        if(k1 > 255)
            throw new NameTooLongException();
        int l1 = getlabels();
        int i2 = l1 + j;
        if(i2 > 128)
            throw new IllegalStateException("too many labels");
        byte abyte1[] = new byte[k1];
        if(k != 0)
            System.arraycopy(name, offset(0), abyte1, 0, k);
        System.arraycopy(abyte0, i, abyte1, k, j1);
        name = abyte1;
        for(int j2 = 0; j2 < j; j2++)
        {
            setoffset(l1 + j2, k);
            k += 1 + abyte1[k];
        }

        setlabels(i2);
    }

    private final void appendFromString(String s, byte abyte0[], int i, int j)
        throws TextParseException
    {
        try
        {
            append(abyte0, i, j);
            return;
        }
        catch(NameTooLongException nametoolongexception)
        {
            throw parseException(s, "Name too long");
        }
    }

    private final void appendSafe(byte abyte0[], int i, int j)
    {
        append(abyte0, i, j);
_L2:
        return;
        NameTooLongException nametoolongexception;
        nametoolongexception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    private String byteString(byte abyte0[], int i)
    {
        StringBuffer stringbuffer = new StringBuffer();
        int j = i + 1;
        byte byte0 = abyte0[i];
        int k = j;
        while(k < j + byte0) 
        {
            int l = 0xff & abyte0[k];
            if(l <= 32 || l >= 127)
            {
                stringbuffer.append('\\');
                stringbuffer.append(byteFormat.format(l));
            } else
            if(l == 34 || l == 40 || l == 41 || l == 46 || l == 59 || l == 92 || l == 64 || l == 36)
            {
                stringbuffer.append('\\');
                stringbuffer.append((char)l);
            } else
            {
                stringbuffer.append((char)l);
            }
            k++;
        }
        return stringbuffer.toString();
    }

    public static Name concatenate(Name name1, Name name2)
        throws NameTooLongException
    {
        Name name3;
        if(name1.isAbsolute())
        {
            name3 = name1;
        } else
        {
            name3 = new Name();
            copy(name1, name3);
            name3.append(name2.name, name2.offset(0), name2.getlabels());
        }
        return name3;
    }

    private static final void copy(Name name1, Name name2)
    {
        if(name1.offset(0) == 0)
        {
            name2.name = name1.name;
            name2.offsets = name1.offsets;
        } else
        {
            int i = name1.offset(0);
            int j = name1.name.length - i;
            int k = name1.labels();
            name2.name = new byte[j];
            System.arraycopy(name1.name, i, name2.name, 0, j);
            for(int l = 0; l < k && l < 7; l++)
                name2.setoffset(l, name1.offset(l) - i);

            name2.setlabels(k);
        }
    }

    private final boolean equals(byte abyte0[], int i)
    {
        int j;
        int k;
        int l;
        int i1;
        j = labels();
        k = offset(0);
        l = 0;
        i1 = i;
_L5:
        if(l >= j)
            break MISSING_BLOCK_LABEL_196;
        if(name[k] == abyte0[i1]) goto _L2; else goto _L1
_L1:
        boolean flag = false;
_L3:
        return flag;
_L2:
        byte byte0;
        int l1;
        int i2;
        int j2;
        byte abyte1[] = name;
        int j1 = k + 1;
        byte0 = abyte1[k];
        int k1 = i1 + 1;
        if(byte0 > 63)
            throw new IllegalStateException("invalid label");
        l1 = j1;
        i2 = k1;
        j2 = 0;
_L4:
        int k2;
        int l2;
label0:
        {
            if(j2 >= byte0)
                break MISSING_BLOCK_LABEL_182;
            byte abyte2[] = lowercase;
            byte abyte3[] = name;
            k2 = l1 + 1;
            byte byte1 = abyte2[0xff & abyte3[l1]];
            byte abyte4[] = lowercase;
            l2 = i2 + 1;
            if(byte1 == abyte4[0xff & abyte0[i2]])
                break label0;
            flag = false;
        }
          goto _L3
        j2++;
        l1 = k2;
        i2 = l2;
          goto _L4
        l++;
        i1 = i2;
        k = l1;
          goto _L5
        flag = true;
          goto _L3
    }

    public static Name fromConstantString(String s)
    {
        Name name1;
        try
        {
            name1 = fromString(s, null);
        }
        catch(TextParseException textparseexception)
        {
            throw new IllegalArgumentException((new StringBuilder()).append("Invalid name '").append(s).append("'").toString());
        }
        return name1;
    }

    public static Name fromString(String s)
        throws TextParseException
    {
        return fromString(s, null);
    }

    public static Name fromString(String s, Name name1)
        throws TextParseException
    {
        Name name2;
        if(s.equals("@") && name1 != null)
            name2 = name1;
        else
        if(s.equals("."))
            name2 = root;
        else
            name2 = new Name(s, name1);
        return name2;
    }

    private final int getlabels()
    {
        return (int)(255L & offsets);
    }

    private final int offset(int i)
    {
        int l;
        if(i == 0 && getlabels() == 0)
        {
            l = 0;
        } else
        {
            if(i < 0 || i >= getlabels())
                throw new IllegalArgumentException("label out of range");
            if(i < 7)
            {
                int i1 = 8 * (7 - i);
                l = 0xff & (int)(offsets >>> i1);
            } else
            {
                int j = offset(6);
                for(int k = 6; k < i; k++)
                    j += 1 + name[j];

                l = j;
            }
        }
        return l;
    }

    private static TextParseException parseException(String s, String s1)
    {
        return new TextParseException((new StringBuilder()).append("'").append(s).append("': ").append(s1).toString());
    }

    private final void setlabels(int i)
    {
        offsets = -256L & offsets;
        offsets = offsets | (long)i;
    }

    private final void setoffset(int i, int j)
    {
        if(i < 7)
        {
            int k = 8 * (7 - i);
            offsets = offsets & (-1L ^ 255L << k);
            offsets = offsets | (long)j << k;
        }
    }

    public int compareTo(Object obj)
    {
        Name name1 = (Name)obj;
        if(this != name1) goto _L2; else goto _L1
_L1:
        int i1 = 0;
_L4:
        return i1;
_L2:
        int i = labels();
        int j = name1.labels();
        int k;
        int l;
        if(i > j)
            k = j;
        else
            k = i;
        l = 1;
        do
        {
            if(l > k)
                break;
            int j1 = offset(i - l);
            int k1 = name1.offset(j - l);
            byte byte0 = name[j1];
            byte byte1 = name1.name[k1];
            int l1 = 0;
            do
            {
                if(l1 >= byte0 || l1 >= byte1)
                    break;
                int i2 = lowercase[0xff & name[1 + (l1 + j1)]] - lowercase[0xff & name1.name[1 + (l1 + k1)]];
                if(i2 != 0)
                {
                    i1 = i2;
                    continue; /* Loop/switch isn't completed */
                }
                l1++;
            } while(true);
            if(byte0 != byte1)
            {
                i1 = byte0 - byte1;
                continue; /* Loop/switch isn't completed */
            }
            l++;
        } while(true);
        i1 = i - j;
        if(true) goto _L4; else goto _L3
_L3:
    }

    public boolean equals(Object obj)
    {
        boolean flag;
        if(obj == this)
            flag = true;
        else
        if(obj == null || !(obj instanceof Name))
        {
            flag = false;
        } else
        {
            Name name1 = (Name)obj;
            if(name1.hashcode == 0)
                name1.hashCode();
            if(hashcode == 0)
                hashCode();
            if(name1.hashcode != hashcode)
                flag = false;
            else
            if(name1.labels() != labels())
                flag = false;
            else
                flag = equals(name1.name, name1.offset(0));
        }
        return flag;
    }

    public Name fromDNAME(DNAMERecord dnamerecord)
        throws NameTooLongException
    {
        Name name1 = dnamerecord.getName();
        Name name2 = dnamerecord.getTarget();
        Name name4;
        if(!subdomain(name1))
        {
            name4 = null;
        } else
        {
            int i = labels() - name1.labels();
            int j = length() - name1.length();
            int k = offset(0);
            int l = name2.labels();
            short word0 = name2.length();
            if(j + word0 > 255)
                throw new NameTooLongException();
            Name name3 = new Name();
            name3.setlabels(i + l);
            name3.name = new byte[j + word0];
            System.arraycopy(name, k, name3.name, 0, j);
            System.arraycopy(name2.name, 0, name3.name, j, word0);
            int i1 = 0;
            for(int j1 = 0; j1 < 7 && j1 < i + l; j1++)
            {
                name3.setoffset(j1, i1);
                i1 += 1 + name3.name[i1];
            }

            name4 = name3;
        }
        return name4;
    }

    public byte[] getLabel(int i)
    {
        int j = offset(i);
        byte byte0 = (byte)(1 + name[j]);
        byte abyte0[] = new byte[byte0];
        System.arraycopy(name, j, abyte0, 0, byte0);
        return abyte0;
    }

    public String getLabelString(int i)
    {
        int j = offset(i);
        return byteString(name, j);
    }

    public int hashCode()
    {
        int i = 0;
        int k;
        if(hashcode != 0)
        {
            k = hashcode;
        } else
        {
            for(int j = offset(i); j < name.length; j++)
                i += (i << 3) + lowercase[0xff & name[j]];

            hashcode = i;
            k = hashcode;
        }
        return k;
    }

    public boolean isAbsolute()
    {
        boolean flag;
        if(labels() == 0)
            flag = false;
        else
        if(name[name.length - 1] == 0)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean isWild()
    {
        boolean flag;
        if(labels() == 0)
            flag = false;
        else
        if(name[0] == 1 && name[1] == 42)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public int labels()
    {
        return getlabels();
    }

    public short length()
    {
        short word0;
        if(getlabels() == 0)
            word0 = 0;
        else
            word0 = (short)(name.length - offset(0));
        return word0;
    }

    public Name relativize(Name name1)
    {
        Name name2;
        if(name1 == null || !subdomain(name1))
        {
            name2 = this;
        } else
        {
            name2 = new Name();
            copy(this, name2);
            int i = length() - name1.length();
            name2.setlabels(name2.labels() - name1.labels());
            name2.name = new byte[i];
            System.arraycopy(name, offset(0), name2.name, 0, i);
        }
        return name2;
    }

    public boolean subdomain(Name name1)
    {
        int i = labels();
        int j = name1.labels();
        boolean flag;
        if(j > i)
            flag = false;
        else
        if(j == i)
            flag = equals(name1);
        else
            flag = name1.equals(name, offset(i - j));
        return flag;
    }

    public String toString()
    {
        int i;
        int j;
        i = 0;
        j = labels();
        if(j != 0) goto _L2; else goto _L1
_L1:
        String s = "@";
_L4:
        return s;
_L2:
        StringBuffer stringbuffer;
        int k;
        if(j == 1 && name[offset(i)] == 0)
        {
            s = ".";
            continue; /* Loop/switch isn't completed */
        }
        stringbuffer = new StringBuffer();
        k = offset(i);
_L5:
        byte byte0;
label0:
        {
            if(i < j)
            {
                byte0 = name[k];
                if(byte0 > 63)
                    throw new IllegalStateException("invalid label");
                if(byte0 != 0)
                    break label0;
            }
            if(!isAbsolute())
                stringbuffer.deleteCharAt(stringbuffer.length() - 1);
            s = stringbuffer.toString();
        }
        if(true) goto _L4; else goto _L3
_L3:
        stringbuffer.append(byteString(name, k));
        stringbuffer.append('.');
        k += byte0 + 1;
        i++;
          goto _L5
        if(true) goto _L4; else goto _L6
_L6:
    }

    public void toWire(DNSOutput dnsoutput, Compression compression)
    {
        int i;
        int j;
        if(!isAbsolute())
            throw new IllegalArgumentException("toWire() called on non-absolute name");
        i = labels();
        j = 0;
_L5:
        if(j >= i - 1) goto _L2; else goto _L1
_L1:
        Name name1;
        int k;
        if(j == 0)
            name1 = this;
        else
            name1 = new Name(this, j);
        k = -1;
        if(compression != null)
            k = compression.get(name1);
        if(k < 0) goto _L4; else goto _L3
_L3:
        dnsoutput.writeU16(0xc000 | k);
_L6:
        return;
_L4:
        if(compression != null)
            compression.add(dnsoutput.current(), name1);
        int l = offset(j);
        dnsoutput.writeByteArray(name, l, 1 + name[l]);
        j++;
          goto _L5
_L2:
        dnsoutput.writeU8(0);
          goto _L6
    }

    public void toWire(DNSOutput dnsoutput, Compression compression, boolean flag)
    {
        if(flag)
            toWireCanonical(dnsoutput);
        else
            toWire(dnsoutput, compression);
    }

    public byte[] toWire()
    {
        DNSOutput dnsoutput = new DNSOutput();
        toWire(dnsoutput, null);
        return dnsoutput.toByteArray();
    }

    public void toWireCanonical(DNSOutput dnsoutput)
    {
        dnsoutput.writeByteArray(toWireCanonical());
    }

    public byte[] toWireCanonical()
    {
        int i = labels();
        byte abyte1[];
        if(i == 0)
        {
            abyte1 = new byte[0];
        } else
        {
            byte abyte0[] = new byte[name.length - offset(0)];
            int j = offset(0);
            int k = 0;
            int l = 0;
            while(k < i) 
            {
                byte byte0 = name[j];
                if(byte0 > 63)
                    throw new IllegalStateException("invalid label");
                int i1 = l + 1;
                byte abyte2[] = name;
                int j1 = j + 1;
                abyte0[l] = abyte2[j];
                int k1 = 0;
                int l1 = i1;
                int i2;
                int k2;
                for(i2 = j1; k1 < byte0; i2 = k2)
                {
                    int j2 = l1 + 1;
                    byte abyte3[] = lowercase;
                    byte abyte4[] = name;
                    k2 = i2 + 1;
                    abyte0[l1] = abyte3[0xff & abyte4[i2]];
                    k1++;
                    l1 = j2;
                }

                k++;
                l = l1;
                j = i2;
            }
            abyte1 = abyte0;
        }
        return abyte1;
    }

    public Name wild(int i)
    {
        if(i < 1)
            throw new IllegalArgumentException("must replace 1 or more labels");
        Name name1;
        try
        {
            name1 = new Name();
            copy(wild, name1);
            name1.append(name, offset(i), getlabels() - i);
        }
        catch(NameTooLongException nametoolongexception)
        {
            throw new IllegalStateException("Name.wild: concatenate failed");
        }
        return name1;
    }

    private static final int LABEL_COMPRESSION = 192;
    private static final int LABEL_MASK = 192;
    private static final int LABEL_NORMAL = 0;
    private static final int MAXLABEL = 63;
    private static final int MAXLABELS = 128;
    private static final int MAXNAME = 255;
    private static final int MAXOFFSETS = 7;
    private static final DecimalFormat byteFormat;
    public static final Name empty;
    private static final byte emptyLabel[];
    private static final byte lowercase[];
    public static final Name root;
    private static final long serialVersionUID = 0x227749f4L;
    private static final Name wild;
    private static final byte wildLabel[];
    private int hashcode;
    private byte name[] = new byte[0];
    private long offsets;

    static 
    {
        byte abyte0[] = new byte[1];
        abyte0[0] = 0;
        emptyLabel = abyte0;
        byte abyte1[] = new byte[2];
        abyte1[0] = 1;
        abyte1[1] = 42;
        wildLabel = abyte1;
        byteFormat = new DecimalFormat();
        lowercase = new byte[256];
        byteFormat.setMinimumIntegerDigits(3);
        int i = 0;
        while(i < lowercase.length) 
        {
            if(i < 65 || i > 90)
                lowercase[i] = (byte)i;
            else
                lowercase[i] = (byte)(97 + (i - 65));
            i++;
        }
        root = new Name();
        root.appendSafe(emptyLabel, 0, 1);
        empty = new Name();
        wild = new Name();
        wild.appendSafe(wildLabel, 0, 1);
    }
}
