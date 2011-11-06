// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Entities.java

package com.facebook.katana.util;

import android.util.SparseArray;
import java.util.*;

class Entities
{
    static class BinaryEntityMap extends ArrayEntityMap
    {

        private int binarySearch(int i)
        {
            l = i1;
_L2:
            return l;
            int j = 0;
            int l;
            int i1;
            for(int k = size - 1; j <= k;)
            {
                i1 = j + k >> 1;
                int j1 = values[i1];
                if(j1 < i)
                {
                    j = i1 + 1;
                } else
                {
label0:
                    {
                        if(j1 <= i)
                            break label0;
                        k = i1 - 1;
                    }
                }
            }

            l = -(j + 1);
            if(true) goto _L2; else goto _L1
_L1:
        }

        public void add(String s, int i)
        {
            ensureCapacity(1 + size);
            int j = binarySearch(i);
            if(j <= 0)
            {
                int k = -(j + 1);
                System.arraycopy(values, k, values, k + 1, size - k);
                values[k] = i;
                System.arraycopy(names, k, names, k + 1, size - k);
                names[k] = s;
                size = 1 + size;
            }
        }

        public String name(int i)
        {
            int j = binarySearch(i);
            String s;
            if(j < 0)
                s = null;
            else
                s = names[j];
            return s;
        }

        public BinaryEntityMap()
        {
        }

        public BinaryEntityMap(int i)
        {
            super(i);
        }
    }

    static class ArrayEntityMap
        implements EntityMap
    {

        public void add(String s, int i)
        {
            ensureCapacity(1 + size);
            names[size] = s;
            values[size] = i;
            size = 1 + size;
        }

        protected void ensureCapacity(int i)
        {
            if(i > names.length)
            {
                int j = Math.max(i, size + mGrowBy);
                String as[] = new String[j];
                System.arraycopy(names, 0, as, 0, size);
                names = as;
                int ai[] = new int[j];
                System.arraycopy(values, 0, ai, 0, size);
                values = ai;
            }
        }

        public String name(int i)
        {
            int j = 0;
_L3:
            if(j >= size)
                break MISSING_BLOCK_LABEL_35;
            if(values[j] != i) goto _L2; else goto _L1
_L1:
            String s = names[j];
_L4:
            return s;
_L2:
            j++;
              goto _L3
            s = null;
              goto _L4
        }

        public int value(String s)
        {
            int i = 0;
_L3:
            if(i >= size)
                break MISSING_BLOCK_LABEL_38;
            if(!names[i].equals(s)) goto _L2; else goto _L1
_L1:
            int j = values[i];
_L4:
            return j;
_L2:
            i++;
              goto _L3
            j = -1;
              goto _L4
        }

        protected int mGrowBy;
        protected String names[];
        protected int size;
        protected int values[];

        public ArrayEntityMap()
        {
            mGrowBy = 100;
            size = 0;
            names = new String[mGrowBy];
            values = new int[mGrowBy];
        }

        public ArrayEntityMap(int i)
        {
            mGrowBy = 100;
            size = 0;
            mGrowBy = i;
            names = new String[i];
            values = new int[i];
        }
    }

    static class LookupEntityMap extends PrimitiveEntityMap
    {

        private void createLookupTable()
        {
            lookupTable = new String[LOOKUP_TABLE_SIZE];
            for(int i = 0; i < LOOKUP_TABLE_SIZE; i++)
                lookupTable[i] = super.name(i);

        }

        private String[] lookupTable()
        {
            if(lookupTable == null)
                createLookupTable();
            return lookupTable;
        }

        public String name(int i)
        {
            String s;
            if(i < LOOKUP_TABLE_SIZE)
                s = lookupTable()[i];
            else
                s = super.name(i);
            return s;
        }

        private int LOOKUP_TABLE_SIZE;
        private String lookupTable[];

        LookupEntityMap()
        {
            LOOKUP_TABLE_SIZE = 256;
        }
    }

    static class TreeEntityMap extends MapIntMap
    {

        public TreeEntityMap()
        {
            mapNameToValue = new TreeMap();
            mapValueToName = new TreeMap();
        }
    }

    static class HashEntityMap extends MapIntMap
    {

        public HashEntityMap()
        {
            mapNameToValue = new HashMap();
            mapValueToName = new HashMap();
        }
    }

    static abstract class MapIntMap
        implements EntityMap
    {

        public void add(String s, int i)
        {
            mapNameToValue.put(s, new Integer(i));
            mapValueToName.put(new Integer(i), s);
        }

        public String name(int i)
        {
            return (String)mapValueToName.get(new Integer(i));
        }

        public int value(String s)
        {
            Object obj = mapNameToValue.get(s);
            int i;
            if(obj == null)
                i = -1;
            else
                i = ((Integer)obj).intValue();
            return i;
        }

        protected Map mapNameToValue;
        protected Map mapValueToName;

        MapIntMap()
        {
        }
    }

    static class PrimitiveEntityMap
        implements EntityMap
    {

        public void add(String s, int i)
        {
            mapNameToValue.put(s, new Integer(i));
            mapValueToName.put(i, s);
        }

        public String name(int i)
        {
            return (String)mapValueToName.get(i);
        }

        public int value(String s)
        {
            Object obj = mapNameToValue.get(s);
            int i;
            if(obj == null)
                i = -1;
            else
                i = ((Integer)obj).intValue();
            return i;
        }

        private Map mapNameToValue;
        private SparseArray mapValueToName;

        PrimitiveEntityMap()
        {
            mapNameToValue = new HashMap();
            mapValueToName = new SparseArray();
        }
    }

    static interface EntityMap
    {

        public abstract void add(String s, int i);

        public abstract String name(int i);

        public abstract int value(String s);
    }


    Entities()
    {
        map = new LookupEntityMap();
    }

    static void fillWithHtml40Entities(Entities entities)
    {
        entities.addEntities(BASIC_ARRAY);
        entities.addEntities(ISO8859_1_ARRAY);
        entities.addEntities(HTML40_ARRAY);
    }

    public void addEntities(String as[][])
    {
        for(int i = 0; i < as.length; i++)
            addEntity(as[i][0], Integer.parseInt(as[i][1]));

    }

    public void addEntity(String s, int i)
    {
        map.add(s, i);
    }

    public String entityName(int i)
    {
        return map.name(i);
    }

    public int entityValue(String s)
    {
        return map.value(s);
    }

    public String unescape(String s)
    {
        StringBuffer stringbuffer;
        int i;
        stringbuffer = new StringBuffer(s.length());
        i = 0;
_L2:
        char c;
        int j;
        if(i >= s.length())
            break MISSING_BLOCK_LABEL_224;
        c = s.charAt(i);
        if(c != '&')
            break MISSING_BLOCK_LABEL_214;
        j = s.indexOf(';', i + 1);
        if(j != -1)
            break; /* Loop/switch isn't completed */
        stringbuffer.append(c);
_L5:
        i++;
        if(true) goto _L2; else goto _L1
_L1:
        String s1;
        s1 = s.substring(i + 1, j);
        if(s1.charAt(0) != '#')
            break MISSING_BLOCK_LABEL_192;
        char c1 = s1.charAt(1);
        if(c1 != 'x' && c1 != 'X') goto _L4; else goto _L3
_L3:
        int l = Integer.valueOf(s1.substring(2), 16).intValue();
        int k = l;
_L6:
        Exception exception;
        int i1;
        if(k == -1)
        {
            stringbuffer.append('&');
            stringbuffer.append(s1);
            stringbuffer.append(';');
        } else
        {
            stringbuffer.append((char)k);
        }
        i = j;
          goto _L5
_L4:
        i1 = Integer.parseInt(s1.substring(1));
        k = i1;
          goto _L6
        exception;
        k = -1;
          goto _L6
        k = entityValue(s1);
          goto _L6
        stringbuffer.append(c);
          goto _L5
        return stringbuffer.toString();
    }

    private static final String BASIC_ARRAY[][];
    public static final Entities HTML40;
    static final String HTML40_ARRAY[][];
    static final String ISO8859_1_ARRAY[][];
    EntityMap map;

    static 
    {
        String as[][] = new String[4][];
        String as1[] = new String[2];
        as1[0] = "quot";
        as1[1] = "34";
        as[0] = as1;
        String as2[] = new String[2];
        as2[0] = "amp";
        as2[1] = "38";
        as[1] = as2;
        String as3[] = new String[2];
        as3[0] = "lt";
        as3[1] = "60";
        as[2] = as3;
        String as4[] = new String[2];
        as4[0] = "gt";
        as4[1] = "62";
        as[3] = as4;
        BASIC_ARRAY = as;
        String as5[][] = new String[96][];
        String as6[] = new String[2];
        as6[0] = "nbsp";
        as6[1] = "160";
        as5[0] = as6;
        String as7[] = new String[2];
        as7[0] = "iexcl";
        as7[1] = "161";
        as5[1] = as7;
        String as8[] = new String[2];
        as8[0] = "cent";
        as8[1] = "162";
        as5[2] = as8;
        String as9[] = new String[2];
        as9[0] = "pound";
        as9[1] = "163";
        as5[3] = as9;
        String as10[] = new String[2];
        as10[0] = "curren";
        as10[1] = "164";
        as5[4] = as10;
        String as11[] = new String[2];
        as11[0] = "yen";
        as11[1] = "165";
        as5[5] = as11;
        String as12[] = new String[2];
        as12[0] = "brvbar";
        as12[1] = "166";
        as5[6] = as12;
        String as13[] = new String[2];
        as13[0] = "sect";
        as13[1] = "167";
        as5[7] = as13;
        String as14[] = new String[2];
        as14[0] = "uml";
        as14[1] = "168";
        as5[8] = as14;
        String as15[] = new String[2];
        as15[0] = "copy";
        as15[1] = "169";
        as5[9] = as15;
        String as16[] = new String[2];
        as16[0] = "ordf";
        as16[1] = "170";
        as5[10] = as16;
        String as17[] = new String[2];
        as17[0] = "laquo";
        as17[1] = "171";
        as5[11] = as17;
        String as18[] = new String[2];
        as18[0] = "not";
        as18[1] = "172";
        as5[12] = as18;
        String as19[] = new String[2];
        as19[0] = "shy";
        as19[1] = "173";
        as5[13] = as19;
        String as20[] = new String[2];
        as20[0] = "reg";
        as20[1] = "174";
        as5[14] = as20;
        String as21[] = new String[2];
        as21[0] = "macr";
        as21[1] = "175";
        as5[15] = as21;
        String as22[] = new String[2];
        as22[0] = "deg";
        as22[1] = "176";
        as5[16] = as22;
        String as23[] = new String[2];
        as23[0] = "plusmn";
        as23[1] = "177";
        as5[17] = as23;
        String as24[] = new String[2];
        as24[0] = "sup2";
        as24[1] = "178";
        as5[18] = as24;
        String as25[] = new String[2];
        as25[0] = "sup3";
        as25[1] = "179";
        as5[19] = as25;
        String as26[] = new String[2];
        as26[0] = "acute";
        as26[1] = "180";
        as5[20] = as26;
        String as27[] = new String[2];
        as27[0] = "micro";
        as27[1] = "181";
        as5[21] = as27;
        String as28[] = new String[2];
        as28[0] = "para";
        as28[1] = "182";
        as5[22] = as28;
        String as29[] = new String[2];
        as29[0] = "middot";
        as29[1] = "183";
        as5[23] = as29;
        String as30[] = new String[2];
        as30[0] = "cedil";
        as30[1] = "184";
        as5[24] = as30;
        String as31[] = new String[2];
        as31[0] = "sup1";
        as31[1] = "185";
        as5[25] = as31;
        String as32[] = new String[2];
        as32[0] = "ordm";
        as32[1] = "186";
        as5[26] = as32;
        String as33[] = new String[2];
        as33[0] = "raquo";
        as33[1] = "187";
        as5[27] = as33;
        String as34[] = new String[2];
        as34[0] = "frac14";
        as34[1] = "188";
        as5[28] = as34;
        String as35[] = new String[2];
        as35[0] = "frac12";
        as35[1] = "189";
        as5[29] = as35;
        String as36[] = new String[2];
        as36[0] = "frac34";
        as36[1] = "190";
        as5[30] = as36;
        String as37[] = new String[2];
        as37[0] = "iquest";
        as37[1] = "191";
        as5[31] = as37;
        String as38[] = new String[2];
        as38[0] = "Agrave";
        as38[1] = "192";
        as5[32] = as38;
        String as39[] = new String[2];
        as39[0] = "Aacute";
        as39[1] = "193";
        as5[33] = as39;
        String as40[] = new String[2];
        as40[0] = "Acirc";
        as40[1] = "194";
        as5[34] = as40;
        String as41[] = new String[2];
        as41[0] = "Atilde";
        as41[1] = "195";
        as5[35] = as41;
        String as42[] = new String[2];
        as42[0] = "Auml";
        as42[1] = "196";
        as5[36] = as42;
        String as43[] = new String[2];
        as43[0] = "Aring";
        as43[1] = "197";
        as5[37] = as43;
        String as44[] = new String[2];
        as44[0] = "AElig";
        as44[1] = "198";
        as5[38] = as44;
        String as45[] = new String[2];
        as45[0] = "Ccedil";
        as45[1] = "199";
        as5[39] = as45;
        String as46[] = new String[2];
        as46[0] = "Egrave";
        as46[1] = "200";
        as5[40] = as46;
        String as47[] = new String[2];
        as47[0] = "Eacute";
        as47[1] = "201";
        as5[41] = as47;
        String as48[] = new String[2];
        as48[0] = "Ecirc";
        as48[1] = "202";
        as5[42] = as48;
        String as49[] = new String[2];
        as49[0] = "Euml";
        as49[1] = "203";
        as5[43] = as49;
        String as50[] = new String[2];
        as50[0] = "Igrave";
        as50[1] = "204";
        as5[44] = as50;
        String as51[] = new String[2];
        as51[0] = "Iacute";
        as51[1] = "205";
        as5[45] = as51;
        String as52[] = new String[2];
        as52[0] = "Icirc";
        as52[1] = "206";
        as5[46] = as52;
        String as53[] = new String[2];
        as53[0] = "Iuml";
        as53[1] = "207";
        as5[47] = as53;
        String as54[] = new String[2];
        as54[0] = "ETH";
        as54[1] = "208";
        as5[48] = as54;
        String as55[] = new String[2];
        as55[0] = "Ntilde";
        as55[1] = "209";
        as5[49] = as55;
        String as56[] = new String[2];
        as56[0] = "Ograve";
        as56[1] = "210";
        as5[50] = as56;
        String as57[] = new String[2];
        as57[0] = "Oacute";
        as57[1] = "211";
        as5[51] = as57;
        String as58[] = new String[2];
        as58[0] = "Ocirc";
        as58[1] = "212";
        as5[52] = as58;
        String as59[] = new String[2];
        as59[0] = "Otilde";
        as59[1] = "213";
        as5[53] = as59;
        String as60[] = new String[2];
        as60[0] = "Ouml";
        as60[1] = "214";
        as5[54] = as60;
        String as61[] = new String[2];
        as61[0] = "times";
        as61[1] = "215";
        as5[55] = as61;
        String as62[] = new String[2];
        as62[0] = "Oslash";
        as62[1] = "216";
        as5[56] = as62;
        String as63[] = new String[2];
        as63[0] = "Ugrave";
        as63[1] = "217";
        as5[57] = as63;
        String as64[] = new String[2];
        as64[0] = "Uacute";
        as64[1] = "218";
        as5[58] = as64;
        String as65[] = new String[2];
        as65[0] = "Ucirc";
        as65[1] = "219";
        as5[59] = as65;
        String as66[] = new String[2];
        as66[0] = "Uuml";
        as66[1] = "220";
        as5[60] = as66;
        String as67[] = new String[2];
        as67[0] = "Yacute";
        as67[1] = "221";
        as5[61] = as67;
        String as68[] = new String[2];
        as68[0] = "THORN";
        as68[1] = "222";
        as5[62] = as68;
        String as69[] = new String[2];
        as69[0] = "szlig";
        as69[1] = "223";
        as5[63] = as69;
        String as70[] = new String[2];
        as70[0] = "agrave";
        as70[1] = "224";
        as5[64] = as70;
        String as71[] = new String[2];
        as71[0] = "aacute";
        as71[1] = "225";
        as5[65] = as71;
        String as72[] = new String[2];
        as72[0] = "acirc";
        as72[1] = "226";
        as5[66] = as72;
        String as73[] = new String[2];
        as73[0] = "atilde";
        as73[1] = "227";
        as5[67] = as73;
        String as74[] = new String[2];
        as74[0] = "auml";
        as74[1] = "228";
        as5[68] = as74;
        String as75[] = new String[2];
        as75[0] = "aring";
        as75[1] = "229";
        as5[69] = as75;
        String as76[] = new String[2];
        as76[0] = "aelig";
        as76[1] = "230";
        as5[70] = as76;
        String as77[] = new String[2];
        as77[0] = "ccedil";
        as77[1] = "231";
        as5[71] = as77;
        String as78[] = new String[2];
        as78[0] = "egrave";
        as78[1] = "232";
        as5[72] = as78;
        String as79[] = new String[2];
        as79[0] = "eacute";
        as79[1] = "233";
        as5[73] = as79;
        String as80[] = new String[2];
        as80[0] = "ecirc";
        as80[1] = "234";
        as5[74] = as80;
        String as81[] = new String[2];
        as81[0] = "euml";
        as81[1] = "235";
        as5[75] = as81;
        String as82[] = new String[2];
        as82[0] = "igrave";
        as82[1] = "236";
        as5[76] = as82;
        String as83[] = new String[2];
        as83[0] = "iacute";
        as83[1] = "237";
        as5[77] = as83;
        String as84[] = new String[2];
        as84[0] = "icirc";
        as84[1] = "238";
        as5[78] = as84;
        String as85[] = new String[2];
        as85[0] = "iuml";
        as85[1] = "239";
        as5[79] = as85;
        String as86[] = new String[2];
        as86[0] = "eth";
        as86[1] = "240";
        as5[80] = as86;
        String as87[] = new String[2];
        as87[0] = "ntilde";
        as87[1] = "241";
        as5[81] = as87;
        String as88[] = new String[2];
        as88[0] = "ograve";
        as88[1] = "242";
        as5[82] = as88;
        String as89[] = new String[2];
        as89[0] = "oacute";
        as89[1] = "243";
        as5[83] = as89;
        String as90[] = new String[2];
        as90[0] = "ocirc";
        as90[1] = "244";
        as5[84] = as90;
        String as91[] = new String[2];
        as91[0] = "otilde";
        as91[1] = "245";
        as5[85] = as91;
        String as92[] = new String[2];
        as92[0] = "ouml";
        as92[1] = "246";
        as5[86] = as92;
        String as93[] = new String[2];
        as93[0] = "divide";
        as93[1] = "247";
        as5[87] = as93;
        String as94[] = new String[2];
        as94[0] = "oslash";
        as94[1] = "248";
        as5[88] = as94;
        String as95[] = new String[2];
        as95[0] = "ugrave";
        as95[1] = "249";
        as5[89] = as95;
        String as96[] = new String[2];
        as96[0] = "uacute";
        as96[1] = "250";
        as5[90] = as96;
        String as97[] = new String[2];
        as97[0] = "ucirc";
        as97[1] = "251";
        as5[91] = as97;
        String as98[] = new String[2];
        as98[0] = "uuml";
        as98[1] = "252";
        as5[92] = as98;
        String as99[] = new String[2];
        as99[0] = "yacute";
        as99[1] = "253";
        as5[93] = as99;
        String as100[] = new String[2];
        as100[0] = "thorn";
        as100[1] = "254";
        as5[94] = as100;
        String as101[] = new String[2];
        as101[0] = "yuml";
        as101[1] = "255";
        as5[95] = as101;
        ISO8859_1_ARRAY = as5;
        String as102[][] = new String[151][];
        String as103[] = new String[2];
        as103[0] = "fnof";
        as103[1] = "402";
        as102[0] = as103;
        String as104[] = new String[2];
        as104[0] = "Alpha";
        as104[1] = "913";
        as102[1] = as104;
        String as105[] = new String[2];
        as105[0] = "Beta";
        as105[1] = "914";
        as102[2] = as105;
        String as106[] = new String[2];
        as106[0] = "Gamma";
        as106[1] = "915";
        as102[3] = as106;
        String as107[] = new String[2];
        as107[0] = "Delta";
        as107[1] = "916";
        as102[4] = as107;
        String as108[] = new String[2];
        as108[0] = "Epsilon";
        as108[1] = "917";
        as102[5] = as108;
        String as109[] = new String[2];
        as109[0] = "Zeta";
        as109[1] = "918";
        as102[6] = as109;
        String as110[] = new String[2];
        as110[0] = "Eta";
        as110[1] = "919";
        as102[7] = as110;
        String as111[] = new String[2];
        as111[0] = "Theta";
        as111[1] = "920";
        as102[8] = as111;
        String as112[] = new String[2];
        as112[0] = "Iota";
        as112[1] = "921";
        as102[9] = as112;
        String as113[] = new String[2];
        as113[0] = "Kappa";
        as113[1] = "922";
        as102[10] = as113;
        String as114[] = new String[2];
        as114[0] = "Lambda";
        as114[1] = "923";
        as102[11] = as114;
        String as115[] = new String[2];
        as115[0] = "Mu";
        as115[1] = "924";
        as102[12] = as115;
        String as116[] = new String[2];
        as116[0] = "Nu";
        as116[1] = "925";
        as102[13] = as116;
        String as117[] = new String[2];
        as117[0] = "Xi";
        as117[1] = "926";
        as102[14] = as117;
        String as118[] = new String[2];
        as118[0] = "Omicron";
        as118[1] = "927";
        as102[15] = as118;
        String as119[] = new String[2];
        as119[0] = "Pi";
        as119[1] = "928";
        as102[16] = as119;
        String as120[] = new String[2];
        as120[0] = "Rho";
        as120[1] = "929";
        as102[17] = as120;
        String as121[] = new String[2];
        as121[0] = "Sigma";
        as121[1] = "931";
        as102[18] = as121;
        String as122[] = new String[2];
        as122[0] = "Tau";
        as122[1] = "932";
        as102[19] = as122;
        String as123[] = new String[2];
        as123[0] = "Upsilon";
        as123[1] = "933";
        as102[20] = as123;
        String as124[] = new String[2];
        as124[0] = "Phi";
        as124[1] = "934";
        as102[21] = as124;
        String as125[] = new String[2];
        as125[0] = "Chi";
        as125[1] = "935";
        as102[22] = as125;
        String as126[] = new String[2];
        as126[0] = "Psi";
        as126[1] = "936";
        as102[23] = as126;
        String as127[] = new String[2];
        as127[0] = "Omega";
        as127[1] = "937";
        as102[24] = as127;
        String as128[] = new String[2];
        as128[0] = "alpha";
        as128[1] = "945";
        as102[25] = as128;
        String as129[] = new String[2];
        as129[0] = "beta";
        as129[1] = "946";
        as102[26] = as129;
        String as130[] = new String[2];
        as130[0] = "gamma";
        as130[1] = "947";
        as102[27] = as130;
        String as131[] = new String[2];
        as131[0] = "delta";
        as131[1] = "948";
        as102[28] = as131;
        String as132[] = new String[2];
        as132[0] = "epsilon";
        as132[1] = "949";
        as102[29] = as132;
        String as133[] = new String[2];
        as133[0] = "zeta";
        as133[1] = "950";
        as102[30] = as133;
        String as134[] = new String[2];
        as134[0] = "eta";
        as134[1] = "951";
        as102[31] = as134;
        String as135[] = new String[2];
        as135[0] = "theta";
        as135[1] = "952";
        as102[32] = as135;
        String as136[] = new String[2];
        as136[0] = "iota";
        as136[1] = "953";
        as102[33] = as136;
        String as137[] = new String[2];
        as137[0] = "kappa";
        as137[1] = "954";
        as102[34] = as137;
        String as138[] = new String[2];
        as138[0] = "lambda";
        as138[1] = "955";
        as102[35] = as138;
        String as139[] = new String[2];
        as139[0] = "mu";
        as139[1] = "956";
        as102[36] = as139;
        String as140[] = new String[2];
        as140[0] = "nu";
        as140[1] = "957";
        as102[37] = as140;
        String as141[] = new String[2];
        as141[0] = "xi";
        as141[1] = "958";
        as102[38] = as141;
        String as142[] = new String[2];
        as142[0] = "omicron";
        as142[1] = "959";
        as102[39] = as142;
        String as143[] = new String[2];
        as143[0] = "pi";
        as143[1] = "960";
        as102[40] = as143;
        String as144[] = new String[2];
        as144[0] = "rho";
        as144[1] = "961";
        as102[41] = as144;
        String as145[] = new String[2];
        as145[0] = "sigmaf";
        as145[1] = "962";
        as102[42] = as145;
        String as146[] = new String[2];
        as146[0] = "sigma";
        as146[1] = "963";
        as102[43] = as146;
        String as147[] = new String[2];
        as147[0] = "tau";
        as147[1] = "964";
        as102[44] = as147;
        String as148[] = new String[2];
        as148[0] = "upsilon";
        as148[1] = "965";
        as102[45] = as148;
        String as149[] = new String[2];
        as149[0] = "phi";
        as149[1] = "966";
        as102[46] = as149;
        String as150[] = new String[2];
        as150[0] = "chi";
        as150[1] = "967";
        as102[47] = as150;
        String as151[] = new String[2];
        as151[0] = "psi";
        as151[1] = "968";
        as102[48] = as151;
        String as152[] = new String[2];
        as152[0] = "omega";
        as152[1] = "969";
        as102[49] = as152;
        String as153[] = new String[2];
        as153[0] = "thetasym";
        as153[1] = "977";
        as102[50] = as153;
        String as154[] = new String[2];
        as154[0] = "upsih";
        as154[1] = "978";
        as102[51] = as154;
        String as155[] = new String[2];
        as155[0] = "piv";
        as155[1] = "982";
        as102[52] = as155;
        String as156[] = new String[2];
        as156[0] = "bull";
        as156[1] = "8226";
        as102[53] = as156;
        String as157[] = new String[2];
        as157[0] = "hellip";
        as157[1] = "8230";
        as102[54] = as157;
        String as158[] = new String[2];
        as158[0] = "prime";
        as158[1] = "8242";
        as102[55] = as158;
        String as159[] = new String[2];
        as159[0] = "Prime";
        as159[1] = "8243";
        as102[56] = as159;
        String as160[] = new String[2];
        as160[0] = "oline";
        as160[1] = "8254";
        as102[57] = as160;
        String as161[] = new String[2];
        as161[0] = "frasl";
        as161[1] = "8260";
        as102[58] = as161;
        String as162[] = new String[2];
        as162[0] = "weierp";
        as162[1] = "8472";
        as102[59] = as162;
        String as163[] = new String[2];
        as163[0] = "image";
        as163[1] = "8465";
        as102[60] = as163;
        String as164[] = new String[2];
        as164[0] = "real";
        as164[1] = "8476";
        as102[61] = as164;
        String as165[] = new String[2];
        as165[0] = "trade";
        as165[1] = "8482";
        as102[62] = as165;
        String as166[] = new String[2];
        as166[0] = "alefsym";
        as166[1] = "8501";
        as102[63] = as166;
        String as167[] = new String[2];
        as167[0] = "larr";
        as167[1] = "8592";
        as102[64] = as167;
        String as168[] = new String[2];
        as168[0] = "uarr";
        as168[1] = "8593";
        as102[65] = as168;
        String as169[] = new String[2];
        as169[0] = "rarr";
        as169[1] = "8594";
        as102[66] = as169;
        String as170[] = new String[2];
        as170[0] = "darr";
        as170[1] = "8595";
        as102[67] = as170;
        String as171[] = new String[2];
        as171[0] = "harr";
        as171[1] = "8596";
        as102[68] = as171;
        String as172[] = new String[2];
        as172[0] = "crarr";
        as172[1] = "8629";
        as102[69] = as172;
        String as173[] = new String[2];
        as173[0] = "lArr";
        as173[1] = "8656";
        as102[70] = as173;
        String as174[] = new String[2];
        as174[0] = "uArr";
        as174[1] = "8657";
        as102[71] = as174;
        String as175[] = new String[2];
        as175[0] = "rArr";
        as175[1] = "8658";
        as102[72] = as175;
        String as176[] = new String[2];
        as176[0] = "dArr";
        as176[1] = "8659";
        as102[73] = as176;
        String as177[] = new String[2];
        as177[0] = "hArr";
        as177[1] = "8660";
        as102[74] = as177;
        String as178[] = new String[2];
        as178[0] = "forall";
        as178[1] = "8704";
        as102[75] = as178;
        String as179[] = new String[2];
        as179[0] = "part";
        as179[1] = "8706";
        as102[76] = as179;
        String as180[] = new String[2];
        as180[0] = "exist";
        as180[1] = "8707";
        as102[77] = as180;
        String as181[] = new String[2];
        as181[0] = "empty";
        as181[1] = "8709";
        as102[78] = as181;
        String as182[] = new String[2];
        as182[0] = "nabla";
        as182[1] = "8711";
        as102[79] = as182;
        String as183[] = new String[2];
        as183[0] = "isin";
        as183[1] = "8712";
        as102[80] = as183;
        String as184[] = new String[2];
        as184[0] = "notin";
        as184[1] = "8713";
        as102[81] = as184;
        String as185[] = new String[2];
        as185[0] = "ni";
        as185[1] = "8715";
        as102[82] = as185;
        String as186[] = new String[2];
        as186[0] = "prod";
        as186[1] = "8719";
        as102[83] = as186;
        String as187[] = new String[2];
        as187[0] = "sum";
        as187[1] = "8721";
        as102[84] = as187;
        String as188[] = new String[2];
        as188[0] = "minus";
        as188[1] = "8722";
        as102[85] = as188;
        String as189[] = new String[2];
        as189[0] = "lowast";
        as189[1] = "8727";
        as102[86] = as189;
        String as190[] = new String[2];
        as190[0] = "radic";
        as190[1] = "8730";
        as102[87] = as190;
        String as191[] = new String[2];
        as191[0] = "prop";
        as191[1] = "8733";
        as102[88] = as191;
        String as192[] = new String[2];
        as192[0] = "infin";
        as192[1] = "8734";
        as102[89] = as192;
        String as193[] = new String[2];
        as193[0] = "ang";
        as193[1] = "8736";
        as102[90] = as193;
        String as194[] = new String[2];
        as194[0] = "and";
        as194[1] = "8743";
        as102[91] = as194;
        String as195[] = new String[2];
        as195[0] = "or";
        as195[1] = "8744";
        as102[92] = as195;
        String as196[] = new String[2];
        as196[0] = "cap";
        as196[1] = "8745";
        as102[93] = as196;
        String as197[] = new String[2];
        as197[0] = "cup";
        as197[1] = "8746";
        as102[94] = as197;
        String as198[] = new String[2];
        as198[0] = "int";
        as198[1] = "8747";
        as102[95] = as198;
        String as199[] = new String[2];
        as199[0] = "there4";
        as199[1] = "8756";
        as102[96] = as199;
        String as200[] = new String[2];
        as200[0] = "sim";
        as200[1] = "8764";
        as102[97] = as200;
        String as201[] = new String[2];
        as201[0] = "cong";
        as201[1] = "8773";
        as102[98] = as201;
        String as202[] = new String[2];
        as202[0] = "asymp";
        as202[1] = "8776";
        as102[99] = as202;
        String as203[] = new String[2];
        as203[0] = "ne";
        as203[1] = "8800";
        as102[100] = as203;
        String as204[] = new String[2];
        as204[0] = "equiv";
        as204[1] = "8801";
        as102[101] = as204;
        String as205[] = new String[2];
        as205[0] = "le";
        as205[1] = "8804";
        as102[102] = as205;
        String as206[] = new String[2];
        as206[0] = "ge";
        as206[1] = "8805";
        as102[103] = as206;
        String as207[] = new String[2];
        as207[0] = "sub";
        as207[1] = "8834";
        as102[104] = as207;
        String as208[] = new String[2];
        as208[0] = "sup";
        as208[1] = "8835";
        as102[105] = as208;
        String as209[] = new String[2];
        as209[0] = "sube";
        as209[1] = "8838";
        as102[106] = as209;
        String as210[] = new String[2];
        as210[0] = "supe";
        as210[1] = "8839";
        as102[107] = as210;
        String as211[] = new String[2];
        as211[0] = "oplus";
        as211[1] = "8853";
        as102[108] = as211;
        String as212[] = new String[2];
        as212[0] = "otimes";
        as212[1] = "8855";
        as102[109] = as212;
        String as213[] = new String[2];
        as213[0] = "perp";
        as213[1] = "8869";
        as102[110] = as213;
        String as214[] = new String[2];
        as214[0] = "sdot";
        as214[1] = "8901";
        as102[111] = as214;
        String as215[] = new String[2];
        as215[0] = "lceil";
        as215[1] = "8968";
        as102[112] = as215;
        String as216[] = new String[2];
        as216[0] = "rceil";
        as216[1] = "8969";
        as102[113] = as216;
        String as217[] = new String[2];
        as217[0] = "lfloor";
        as217[1] = "8970";
        as102[114] = as217;
        String as218[] = new String[2];
        as218[0] = "rfloor";
        as218[1] = "8971";
        as102[115] = as218;
        String as219[] = new String[2];
        as219[0] = "lang";
        as219[1] = "9001";
        as102[116] = as219;
        String as220[] = new String[2];
        as220[0] = "rang";
        as220[1] = "9002";
        as102[117] = as220;
        String as221[] = new String[2];
        as221[0] = "loz";
        as221[1] = "9674";
        as102[118] = as221;
        String as222[] = new String[2];
        as222[0] = "spades";
        as222[1] = "9824";
        as102[119] = as222;
        String as223[] = new String[2];
        as223[0] = "clubs";
        as223[1] = "9827";
        as102[120] = as223;
        String as224[] = new String[2];
        as224[0] = "hearts";
        as224[1] = "9829";
        as102[121] = as224;
        String as225[] = new String[2];
        as225[0] = "diams";
        as225[1] = "9830";
        as102[122] = as225;
        String as226[] = new String[2];
        as226[0] = "OElig";
        as226[1] = "338";
        as102[123] = as226;
        String as227[] = new String[2];
        as227[0] = "oelig";
        as227[1] = "339";
        as102[124] = as227;
        String as228[] = new String[2];
        as228[0] = "Scaron";
        as228[1] = "352";
        as102[125] = as228;
        String as229[] = new String[2];
        as229[0] = "scaron";
        as229[1] = "353";
        as102[126] = as229;
        String as230[] = new String[2];
        as230[0] = "Yuml";
        as230[1] = "376";
        as102[127] = as230;
        String as231[] = new String[2];
        as231[0] = "circ";
        as231[1] = "710";
        as102[128] = as231;
        String as232[] = new String[2];
        as232[0] = "tilde";
        as232[1] = "732";
        as102[129] = as232;
        String as233[] = new String[2];
        as233[0] = "ensp";
        as233[1] = "8194";
        as102[130] = as233;
        String as234[] = new String[2];
        as234[0] = "emsp";
        as234[1] = "8195";
        as102[131] = as234;
        String as235[] = new String[2];
        as235[0] = "thinsp";
        as235[1] = "8201";
        as102[132] = as235;
        String as236[] = new String[2];
        as236[0] = "zwnj";
        as236[1] = "8204";
        as102[133] = as236;
        String as237[] = new String[2];
        as237[0] = "zwj";
        as237[1] = "8205";
        as102[134] = as237;
        String as238[] = new String[2];
        as238[0] = "lrm";
        as238[1] = "8206";
        as102[135] = as238;
        String as239[] = new String[2];
        as239[0] = "rlm";
        as239[1] = "8207";
        as102[136] = as239;
        String as240[] = new String[2];
        as240[0] = "ndash";
        as240[1] = "8211";
        as102[137] = as240;
        String as241[] = new String[2];
        as241[0] = "mdash";
        as241[1] = "8212";
        as102[138] = as241;
        String as242[] = new String[2];
        as242[0] = "lsquo";
        as242[1] = "8216";
        as102[139] = as242;
        String as243[] = new String[2];
        as243[0] = "rsquo";
        as243[1] = "8217";
        as102[140] = as243;
        String as244[] = new String[2];
        as244[0] = "sbquo";
        as244[1] = "8218";
        as102[141] = as244;
        String as245[] = new String[2];
        as245[0] = "ldquo";
        as245[1] = "8220";
        as102[142] = as245;
        String as246[] = new String[2];
        as246[0] = "rdquo";
        as246[1] = "8221";
        as102[143] = as246;
        String as247[] = new String[2];
        as247[0] = "bdquo";
        as247[1] = "8222";
        as102[144] = as247;
        String as248[] = new String[2];
        as248[0] = "dagger";
        as248[1] = "8224";
        as102[145] = as248;
        String as249[] = new String[2];
        as249[0] = "Dagger";
        as249[1] = "8225";
        as102[146] = as249;
        String as250[] = new String[2];
        as250[0] = "permil";
        as250[1] = "8240";
        as102[147] = as250;
        String as251[] = new String[2];
        as251[0] = "lsaquo";
        as251[1] = "8249";
        as102[148] = as251;
        String as252[] = new String[2];
        as252[0] = "rsaquo";
        as252[1] = "8250";
        as102[149] = as252;
        String as253[] = new String[2];
        as253[0] = "euro";
        as253[1] = "8364";
        as102[150] = as253;
        HTML40_ARRAY = as102;
        HTML40 = new Entities();
        fillWithHtml40Entities(HTML40);
    }
}
