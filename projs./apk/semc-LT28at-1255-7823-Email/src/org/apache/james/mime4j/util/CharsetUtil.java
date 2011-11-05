package org.apache.james.mime4j.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.HashMap;
import java.util.TreeSet;
import org.apache.james.mime4j.Log;
import org.apache.james.mime4j.LogFactory;

public class CharsetUtil {

   public static final int CR = 13;
   public static final String CRLF = "\r\n";
   public static final int HT = 9;
   public static final java.nio.charset.Charset ISO_8859_1;
   private static CharsetUtil.Charset[] JAVA_CHARSETS;
   public static final int LF = 10;
   public static final int SP = 32;
   public static final java.nio.charset.Charset US_ASCII;
   public static final java.nio.charset.Charset UTF_8;
   private static HashMap charsetMap;
   private static TreeSet decodingSupported;
   private static TreeSet encodingSupported;
   private static Log log = LogFactory.getLog(CharsetUtil.class);


   static {
      CharsetUtil.Charset[] var0 = new CharsetUtil.Charset[147];
      String[] var1 = new String[]{"ISO_8859-1:1987", "iso-ir-100", "ISO_8859-1", "latin1", "l1", "IBM819", "CP819", "csISOLatin1", "8859_1", "819", "IBM-819", "ISO8859-1", "ISO_8859_1"};
      CharsetUtil.Charset var2 = new CharsetUtil.Charset("ISO8859_1", "ISO-8859-1", var1, (CharsetUtil.1)null);
      var0[0] = var2;
      String[] var3 = new String[]{"ISO_8859-2:1987", "iso-ir-101", "ISO_8859-2", "latin2", "l2", "csISOLatin2", "8859_2", "iso8859_2"};
      CharsetUtil.Charset var4 = new CharsetUtil.Charset("ISO8859_2", "ISO-8859-2", var3, (CharsetUtil.1)null);
      var0[1] = var4;
      String[] var5 = new String[]{"ISO_8859-3:1988", "iso-ir-109", "ISO_8859-3", "latin3", "l3", "csISOLatin3", "8859_3"};
      CharsetUtil.Charset var6 = new CharsetUtil.Charset("ISO8859_3", "ISO-8859-3", var5, (CharsetUtil.1)null);
      var0[2] = var6;
      String[] var7 = new String[]{"ISO_8859-4:1988", "iso-ir-110", "ISO_8859-4", "latin4", "l4", "csISOLatin4", "8859_4"};
      CharsetUtil.Charset var8 = new CharsetUtil.Charset("ISO8859_4", "ISO-8859-4", var7, (CharsetUtil.1)null);
      var0[3] = var8;
      String[] var9 = new String[]{"ISO_8859-5:1988", "iso-ir-144", "ISO_8859-5", "cyrillic", "csISOLatinCyrillic", "8859_5"};
      CharsetUtil.Charset var10 = new CharsetUtil.Charset("ISO8859_5", "ISO-8859-5", var9, (CharsetUtil.1)null);
      var0[4] = var10;
      String[] var11 = new String[]{"ISO_8859-6:1987", "iso-ir-127", "ISO_8859-6", "ECMA-114", "ASMO-708", "arabic", "csISOLatinArabic", "8859_6"};
      CharsetUtil.Charset var12 = new CharsetUtil.Charset("ISO8859_6", "ISO-8859-6", var11, (CharsetUtil.1)null);
      var0[5] = var12;
      String[] var13 = new String[]{"ISO_8859-7:1987", "iso-ir-126", "ISO_8859-7", "ELOT_928", "ECMA-118", "greek", "greek8", "csISOLatinGreek", "8859_7", "sun_eu_greek"};
      CharsetUtil.Charset var14 = new CharsetUtil.Charset("ISO8859_7", "ISO-8859-7", var13, (CharsetUtil.1)null);
      var0[6] = var14;
      String[] var15 = new String[]{"ISO_8859-8:1988", "iso-ir-138", "ISO_8859-8", "hebrew", "csISOLatinHebrew", "8859_8"};
      CharsetUtil.Charset var16 = new CharsetUtil.Charset("ISO8859_8", "ISO-8859-8", var15, (CharsetUtil.1)null);
      var0[7] = var16;
      String[] var17 = new String[]{"ISO_8859-9:1989", "iso-ir-148", "ISO_8859-9", "latin5", "l5", "csISOLatin5", "8859_9"};
      CharsetUtil.Charset var18 = new CharsetUtil.Charset("ISO8859_9", "ISO-8859-9", var17, (CharsetUtil.1)null);
      var0[8] = var18;
      String[] var19 = new String[0];
      CharsetUtil.Charset var20 = new CharsetUtil.Charset("ISO8859_13", "ISO-8859-13", var19, (CharsetUtil.1)null);
      var0[9] = var20;
      String[] var21 = new String[]{"ISO_8859-15", "Latin-9", "8859_15", "csISOlatin9", "IBM923", "cp923", "923", "L9", "IBM-923", "ISO8859-15", "LATIN9", "LATIN0", "csISOlatin0", "ISO8859_15_FDIS"};
      CharsetUtil.Charset var22 = new CharsetUtil.Charset("ISO8859_15", "ISO-8859-15", var21, (CharsetUtil.1)null);
      var0[10] = var22;
      String[] var23 = new String[]{"csKOI8R", "koi8"};
      CharsetUtil.Charset var24 = new CharsetUtil.Charset("KOI8_R", "KOI8-R", var23, (CharsetUtil.1)null);
      var0[11] = var24;
      String[] var25 = new String[]{"ANSI_X3.4-1968", "iso-ir-6", "ANSI_X3.4-1986", "ISO_646.irv:1991", "ISO646-US", "us", "IBM367", "cp367", "csASCII", "ascii7", "646", "iso_646.irv:1983"};
      CharsetUtil.Charset var26 = new CharsetUtil.Charset("ASCII", "US-ASCII", var25, (CharsetUtil.1)null);
      var0[12] = var26;
      String[] var27 = new String[0];
      CharsetUtil.Charset var28 = new CharsetUtil.Charset("UTF8", "UTF-8", var27, (CharsetUtil.1)null);
      var0[13] = var28;
      String[] var29 = new String[]{"UTF_16"};
      CharsetUtil.Charset var30 = new CharsetUtil.Charset("UTF-16", "UTF-16", var29, (CharsetUtil.1)null);
      var0[14] = var30;
      String[] var31 = new String[]{"X-UTF-16BE", "UTF_16BE", "ISO-10646-UCS-2"};
      CharsetUtil.Charset var32 = new CharsetUtil.Charset("UnicodeBigUnmarked", "UTF-16BE", var31, (CharsetUtil.1)null);
      var0[15] = var32;
      String[] var33 = new String[]{"UTF_16LE", "X-UTF-16LE"};
      CharsetUtil.Charset var34 = new CharsetUtil.Charset("UnicodeLittleUnmarked", "UTF-16LE", var33, (CharsetUtil.1)null);
      var0[16] = var34;
      String[] var35 = new String[]{"csBig5", "CN-Big5", "BIG-FIVE", "BIGFIVE"};
      CharsetUtil.Charset var36 = new CharsetUtil.Charset("Big5", "Big5", var35, (CharsetUtil.1)null);
      var0[17] = var36;
      String[] var37 = new String[]{"big5hkscs"};
      CharsetUtil.Charset var38 = new CharsetUtil.Charset("Big5_HKSCS", "Big5-HKSCS", var37, (CharsetUtil.1)null);
      var0[18] = var38;
      String[] var39 = new String[]{"csEUCPkdFmtJapanese", "Extended_UNIX_Code_Packed_Format_for_Japanese", "eucjis", "x-eucjp", "eucjp", "x-euc-jp"};
      CharsetUtil.Charset var40 = new CharsetUtil.Charset("EUC_JP", "EUC-JP", var39, (CharsetUtil.1)null);
      var0[19] = var40;
      String[] var41 = new String[]{"csEUCKR", "ksc5601", "5601", "ksc5601_1987", "ksc_5601", "ksc5601-1987", "ks_c_5601-1987", "euckr"};
      CharsetUtil.Charset var42 = new CharsetUtil.Charset("EUC_KR", "EUC-KR", var41, (CharsetUtil.1)null);
      var0[20] = var42;
      String[] var43 = new String[]{"gb18030-2000"};
      CharsetUtil.Charset var44 = new CharsetUtil.Charset("GB18030", "GB18030", var43, (CharsetUtil.1)null);
      var0[21] = var44;
      String[] var45 = new String[]{"x-EUC-CN", "csGB2312", "euccn", "euc-cn", "gb2312-80", "gb2312-1980", "CN-GB", "CN-GB-ISOIR165"};
      CharsetUtil.Charset var46 = new CharsetUtil.Charset("EUC_CN", "GB2312", var45, (CharsetUtil.1)null);
      var0[22] = var46;
      String[] var47 = new String[]{"CP936", "MS936", "ms_936", "x-mswin-936"};
      CharsetUtil.Charset var48 = new CharsetUtil.Charset("GBK", "windows-936", var47, (CharsetUtil.1)null);
      var0[23] = var48;
      String[] var49 = new String[]{"ebcdic-cp-us", "ebcdic-cp-ca", "ebcdic-cp-wt", "ebcdic-cp-nl", "csIBM037"};
      CharsetUtil.Charset var50 = new CharsetUtil.Charset("Cp037", "IBM037", var49, (CharsetUtil.1)null);
      var0[24] = var50;
      String[] var51 = new String[]{"csIBM273"};
      CharsetUtil.Charset var52 = new CharsetUtil.Charset("Cp273", "IBM273", var51, (CharsetUtil.1)null);
      var0[25] = var52;
      String[] var53 = new String[]{"EBCDIC-CP-DK", "EBCDIC-CP-NO", "csIBM277"};
      CharsetUtil.Charset var54 = new CharsetUtil.Charset("Cp277", "IBM277", var53, (CharsetUtil.1)null);
      var0[26] = var54;
      String[] var55 = new String[]{"CP278", "ebcdic-cp-fi", "ebcdic-cp-se", "csIBM278"};
      CharsetUtil.Charset var56 = new CharsetUtil.Charset("Cp278", "IBM278", var55, (CharsetUtil.1)null);
      var0[27] = var56;
      String[] var57 = new String[]{"ebcdic-cp-it", "csIBM280"};
      CharsetUtil.Charset var58 = new CharsetUtil.Charset("Cp280", "IBM280", var57, (CharsetUtil.1)null);
      var0[28] = var58;
      String[] var59 = new String[]{"ebcdic-cp-es", "csIBM284"};
      CharsetUtil.Charset var60 = new CharsetUtil.Charset("Cp284", "IBM284", var59, (CharsetUtil.1)null);
      var0[29] = var60;
      String[] var61 = new String[]{"ebcdic-cp-gb", "csIBM285"};
      CharsetUtil.Charset var62 = new CharsetUtil.Charset("Cp285", "IBM285", var61, (CharsetUtil.1)null);
      var0[30] = var62;
      String[] var63 = new String[]{"ebcdic-cp-fr", "csIBM297"};
      CharsetUtil.Charset var64 = new CharsetUtil.Charset("Cp297", "IBM297", var63, (CharsetUtil.1)null);
      var0[31] = var64;
      String[] var65 = new String[]{"ebcdic-cp-ar1", "csIBM420"};
      CharsetUtil.Charset var66 = new CharsetUtil.Charset("Cp420", "IBM420", var65, (CharsetUtil.1)null);
      var0[32] = var66;
      String[] var67 = new String[]{"ebcdic-cp-he", "csIBM424"};
      CharsetUtil.Charset var68 = new CharsetUtil.Charset("Cp424", "IBM424", var67, (CharsetUtil.1)null);
      var0[33] = var68;
      String[] var69 = new String[]{"437", "csPC8CodePage437"};
      CharsetUtil.Charset var70 = new CharsetUtil.Charset("Cp437", "IBM437", var69, (CharsetUtil.1)null);
      var0[34] = var70;
      String[] var71 = new String[]{"ebcdic-cp-be", "ebcdic-cp-ch", "csIBM500"};
      CharsetUtil.Charset var72 = new CharsetUtil.Charset("Cp500", "IBM500", var71, (CharsetUtil.1)null);
      var0[35] = var72;
      String[] var73 = new String[]{"csPC775Baltic"};
      CharsetUtil.Charset var74 = new CharsetUtil.Charset("Cp775", "IBM775", var73, (CharsetUtil.1)null);
      var0[36] = var74;
      String[] var75 = new String[0];
      CharsetUtil.Charset var76 = new CharsetUtil.Charset("Cp838", "IBM-Thai", var75, (CharsetUtil.1)null);
      var0[37] = var76;
      String[] var77 = new String[]{"850", "csPC850Multilingual"};
      CharsetUtil.Charset var78 = new CharsetUtil.Charset("Cp850", "IBM850", var77, (CharsetUtil.1)null);
      var0[38] = var78;
      String[] var79 = new String[]{"852", "csPCp852"};
      CharsetUtil.Charset var80 = new CharsetUtil.Charset("Cp852", "IBM852", var79, (CharsetUtil.1)null);
      var0[39] = var80;
      String[] var81 = new String[]{"855", "csIBM855"};
      CharsetUtil.Charset var82 = new CharsetUtil.Charset("Cp855", "IBM855", var81, (CharsetUtil.1)null);
      var0[40] = var82;
      String[] var83 = new String[]{"857", "csIBM857"};
      CharsetUtil.Charset var84 = new CharsetUtil.Charset("Cp857", "IBM857", var83, (CharsetUtil.1)null);
      var0[41] = var84;
      String[] var85 = new String[]{"CCSID00858", "CP00858", "PC-Multilingual-850+euro"};
      CharsetUtil.Charset var86 = new CharsetUtil.Charset("Cp858", "IBM00858", var85, (CharsetUtil.1)null);
      var0[42] = var86;
      String[] var87 = new String[]{"860", "csIBM860"};
      CharsetUtil.Charset var88 = new CharsetUtil.Charset("Cp860", "IBM860", var87, (CharsetUtil.1)null);
      var0[43] = var88;
      String[] var89 = new String[]{"861", "cp-is", "csIBM861"};
      CharsetUtil.Charset var90 = new CharsetUtil.Charset("Cp861", "IBM861", var89, (CharsetUtil.1)null);
      var0[44] = var90;
      String[] var91 = new String[]{"862", "csPC862LatinHebrew"};
      CharsetUtil.Charset var92 = new CharsetUtil.Charset("Cp862", "IBM862", var91, (CharsetUtil.1)null);
      var0[45] = var92;
      String[] var93 = new String[]{"863", "csIBM863"};
      CharsetUtil.Charset var94 = new CharsetUtil.Charset("Cp863", "IBM863", var93, (CharsetUtil.1)null);
      var0[46] = var94;
      String[] var95 = new String[]{"cp864", "csIBM864"};
      CharsetUtil.Charset var96 = new CharsetUtil.Charset("Cp864", "IBM864", var95, (CharsetUtil.1)null);
      var0[47] = var96;
      String[] var97 = new String[]{"865", "csIBM865"};
      CharsetUtil.Charset var98 = new CharsetUtil.Charset("Cp865", "IBM865", var97, (CharsetUtil.1)null);
      var0[48] = var98;
      String[] var99 = new String[]{"866", "csIBM866"};
      CharsetUtil.Charset var100 = new CharsetUtil.Charset("Cp866", "IBM866", var99, (CharsetUtil.1)null);
      var0[49] = var100;
      String[] var101 = new String[]{"cp-ar", "csIBM868"};
      CharsetUtil.Charset var102 = new CharsetUtil.Charset("Cp868", "IBM868", var101, (CharsetUtil.1)null);
      var0[50] = var102;
      String[] var103 = new String[]{"cp-gr", "csIBM869"};
      CharsetUtil.Charset var104 = new CharsetUtil.Charset("Cp869", "IBM869", var103, (CharsetUtil.1)null);
      var0[51] = var104;
      String[] var105 = new String[]{"ebcdic-cp-roece", "ebcdic-cp-yu", "csIBM870"};
      CharsetUtil.Charset var106 = new CharsetUtil.Charset("Cp870", "IBM870", var105, (CharsetUtil.1)null);
      var0[52] = var106;
      String[] var107 = new String[]{"ebcdic-cp-is", "csIBM871"};
      CharsetUtil.Charset var108 = new CharsetUtil.Charset("Cp871", "IBM871", var107, (CharsetUtil.1)null);
      var0[53] = var108;
      String[] var109 = new String[]{"ebcdic-cp-ar2", "csIBM918"};
      CharsetUtil.Charset var110 = new CharsetUtil.Charset("Cp918", "IBM918", var109, (CharsetUtil.1)null);
      var0[54] = var110;
      String[] var111 = new String[]{"csIBM1026"};
      CharsetUtil.Charset var112 = new CharsetUtil.Charset("Cp1026", "IBM1026", var111, (CharsetUtil.1)null);
      var0[55] = var112;
      String[] var113 = new String[]{"IBM-1047"};
      CharsetUtil.Charset var114 = new CharsetUtil.Charset("Cp1047", "IBM1047", var113, (CharsetUtil.1)null);
      var0[56] = var114;
      String[] var115 = new String[]{"CCSID01140", "CP01140", "ebcdic-us-37+euro"};
      CharsetUtil.Charset var116 = new CharsetUtil.Charset("Cp1140", "IBM01140", var115, (CharsetUtil.1)null);
      var0[57] = var116;
      String[] var117 = new String[]{"CCSID01141", "CP01141", "ebcdic-de-273+euro"};
      CharsetUtil.Charset var118 = new CharsetUtil.Charset("Cp1141", "IBM01141", var117, (CharsetUtil.1)null);
      var0[58] = var118;
      String[] var119 = new String[]{"CCSID01142", "CP01142", "ebcdic-dk-277+euro", "ebcdic-no-277+euro"};
      CharsetUtil.Charset var120 = new CharsetUtil.Charset("Cp1142", "IBM01142", var119, (CharsetUtil.1)null);
      var0[59] = var120;
      String[] var121 = new String[]{"CCSID01143", "CP01143", "ebcdic-fi-278+euro", "ebcdic-se-278+euro"};
      CharsetUtil.Charset var122 = new CharsetUtil.Charset("Cp1143", "IBM01143", var121, (CharsetUtil.1)null);
      var0[60] = var122;
      String[] var123 = new String[]{"CCSID01144", "CP01144", "ebcdic-it-280+euro"};
      CharsetUtil.Charset var124 = new CharsetUtil.Charset("Cp1144", "IBM01144", var123, (CharsetUtil.1)null);
      var0[61] = var124;
      String[] var125 = new String[]{"CCSID01145", "CP01145", "ebcdic-es-284+euro"};
      CharsetUtil.Charset var126 = new CharsetUtil.Charset("Cp1145", "IBM01145", var125, (CharsetUtil.1)null);
      var0[62] = var126;
      String[] var127 = new String[]{"CCSID01146", "CP01146", "ebcdic-gb-285+euro"};
      CharsetUtil.Charset var128 = new CharsetUtil.Charset("Cp1146", "IBM01146", var127, (CharsetUtil.1)null);
      var0[63] = var128;
      String[] var129 = new String[]{"CCSID01147", "CP01147", "ebcdic-fr-297+euro"};
      CharsetUtil.Charset var130 = new CharsetUtil.Charset("Cp1147", "IBM01147", var129, (CharsetUtil.1)null);
      var0[64] = var130;
      String[] var131 = new String[]{"CCSID01148", "CP01148", "ebcdic-international-500+euro"};
      CharsetUtil.Charset var132 = new CharsetUtil.Charset("Cp1148", "IBM01148", var131, (CharsetUtil.1)null);
      var0[65] = var132;
      String[] var133 = new String[]{"CCSID01149", "CP01149", "ebcdic-is-871+euro"};
      CharsetUtil.Charset var134 = new CharsetUtil.Charset("Cp1149", "IBM01149", var133, (CharsetUtil.1)null);
      var0[66] = var134;
      String[] var135 = new String[0];
      CharsetUtil.Charset var136 = new CharsetUtil.Charset("Cp1250", "windows-1250", var135, (CharsetUtil.1)null);
      var0[67] = var136;
      String[] var137 = new String[0];
      CharsetUtil.Charset var138 = new CharsetUtil.Charset("Cp1251", "windows-1251", var137, (CharsetUtil.1)null);
      var0[68] = var138;
      String[] var139 = new String[0];
      CharsetUtil.Charset var140 = new CharsetUtil.Charset("Cp1252", "windows-1252", var139, (CharsetUtil.1)null);
      var0[69] = var140;
      String[] var141 = new String[0];
      CharsetUtil.Charset var142 = new CharsetUtil.Charset("Cp1253", "windows-1253", var141, (CharsetUtil.1)null);
      var0[70] = var142;
      String[] var143 = new String[0];
      CharsetUtil.Charset var144 = new CharsetUtil.Charset("Cp1254", "windows-1254", var143, (CharsetUtil.1)null);
      var0[71] = var144;
      String[] var145 = new String[0];
      CharsetUtil.Charset var146 = new CharsetUtil.Charset("Cp1255", "windows-1255", var145, (CharsetUtil.1)null);
      var0[72] = var146;
      String[] var147 = new String[0];
      CharsetUtil.Charset var148 = new CharsetUtil.Charset("Cp1256", "windows-1256", var147, (CharsetUtil.1)null);
      var0[73] = var148;
      String[] var149 = new String[0];
      CharsetUtil.Charset var150 = new CharsetUtil.Charset("Cp1257", "windows-1257", var149, (CharsetUtil.1)null);
      var0[74] = var150;
      String[] var151 = new String[0];
      CharsetUtil.Charset var152 = new CharsetUtil.Charset("Cp1258", "windows-1258", var151, (CharsetUtil.1)null);
      var0[75] = var152;
      String[] var153 = new String[0];
      CharsetUtil.Charset var154 = new CharsetUtil.Charset("ISO2022CN", "ISO-2022-CN", var153, (CharsetUtil.1)null);
      var0[76] = var154;
      String[] var155 = new String[]{"csISO2022JP", "JIS", "jis_encoding", "csjisencoding"};
      CharsetUtil.Charset var156 = new CharsetUtil.Charset("ISO2022JP", "ISO-2022-JP", var155, (CharsetUtil.1)null);
      var0[77] = var156;
      String[] var157 = new String[]{"csISO2022KR"};
      CharsetUtil.Charset var158 = new CharsetUtil.Charset("ISO2022KR", "ISO-2022-KR", var157, (CharsetUtil.1)null);
      var0[78] = var158;
      String[] var159 = new String[]{"X0201", "JIS0201", "csHalfWidthKatakana"};
      CharsetUtil.Charset var160 = new CharsetUtil.Charset("JIS_X0201", "JIS_X0201", var159, (CharsetUtil.1)null);
      var0[79] = var160;
      String[] var161 = new String[]{"iso-ir-159", "x0212", "JIS0212", "csISO159JISX02121990"};
      CharsetUtil.Charset var162 = new CharsetUtil.Charset("JIS_X0212-1990", "JIS_X0212-1990", var161, (CharsetUtil.1)null);
      var0[80] = var162;
      String[] var163 = new String[]{"x-JIS0208", "JIS0208", "csISO87JISX0208", "x0208", "JIS_X0208-1983", "iso-ir-87"};
      CharsetUtil.Charset var164 = new CharsetUtil.Charset("JIS_C6626-1983", "JIS_C6626-1983", var163, (CharsetUtil.1)null);
      var0[81] = var164;
      String[] var165 = new String[]{"MS_Kanji", "csShiftJIS", "shift-jis", "x-sjis", "pck"};
      CharsetUtil.Charset var166 = new CharsetUtil.Charset("SJIS", "Shift_JIS", var165, (CharsetUtil.1)null);
      var0[82] = var166;
      String[] var167 = new String[0];
      CharsetUtil.Charset var168 = new CharsetUtil.Charset("TIS620", "TIS-620", var167, (CharsetUtil.1)null);
      var0[83] = var168;
      String[] var169 = new String[]{"windows-932", "csWindows31J", "x-ms-cp932"};
      CharsetUtil.Charset var170 = new CharsetUtil.Charset("MS932", "Windows-31J", var169, (CharsetUtil.1)null);
      var0[84] = var170;
      String[] var171 = new String[]{"x-EUC-TW", "cns11643", "euctw"};
      CharsetUtil.Charset var172 = new CharsetUtil.Charset("EUC_TW", "EUC-TW", var171, (CharsetUtil.1)null);
      var0[85] = var172;
      String[] var173 = new String[]{"johab", "cp1361", "ms1361", "ksc5601-1992", "ksc5601_1992"};
      CharsetUtil.Charset var174 = new CharsetUtil.Charset("x-Johab", "johab", var173, (CharsetUtil.1)null);
      var0[86] = var174;
      String[] var175 = new String[0];
      CharsetUtil.Charset var176 = new CharsetUtil.Charset("MS950_HKSCS", "", var175, (CharsetUtil.1)null);
      var0[87] = var176;
      String[] var177 = new String[]{"cp874"};
      CharsetUtil.Charset var178 = new CharsetUtil.Charset("MS874", "windows-874", var177, (CharsetUtil.1)null);
      var0[88] = var178;
      String[] var179 = new String[]{"windows949", "ms_949", "x-windows-949"};
      CharsetUtil.Charset var180 = new CharsetUtil.Charset("MS949", "windows-949", var179, (CharsetUtil.1)null);
      var0[89] = var180;
      String[] var181 = new String[]{"x-windows-950"};
      CharsetUtil.Charset var182 = new CharsetUtil.Charset("MS950", "windows-950", var181, (CharsetUtil.1)null);
      var0[90] = var182;
      String[] var183 = new String[0];
      CharsetUtil.Charset var184 = new CharsetUtil.Charset("Cp737", (String)null, var183, (CharsetUtil.1)null);
      var0[91] = var184;
      String[] var185 = new String[0];
      CharsetUtil.Charset var186 = new CharsetUtil.Charset("Cp856", (String)null, var185, (CharsetUtil.1)null);
      var0[92] = var186;
      String[] var187 = new String[0];
      CharsetUtil.Charset var188 = new CharsetUtil.Charset("Cp875", (String)null, var187, (CharsetUtil.1)null);
      var0[93] = var188;
      String[] var189 = new String[0];
      CharsetUtil.Charset var190 = new CharsetUtil.Charset("Cp921", (String)null, var189, (CharsetUtil.1)null);
      var0[94] = var190;
      String[] var191 = new String[0];
      CharsetUtil.Charset var192 = new CharsetUtil.Charset("Cp922", (String)null, var191, (CharsetUtil.1)null);
      var0[95] = var192;
      String[] var193 = new String[0];
      CharsetUtil.Charset var194 = new CharsetUtil.Charset("Cp930", (String)null, var193, (CharsetUtil.1)null);
      var0[96] = var194;
      String[] var195 = new String[0];
      CharsetUtil.Charset var196 = new CharsetUtil.Charset("Cp933", (String)null, var195, (CharsetUtil.1)null);
      var0[97] = var196;
      String[] var197 = new String[0];
      CharsetUtil.Charset var198 = new CharsetUtil.Charset("Cp935", (String)null, var197, (CharsetUtil.1)null);
      var0[98] = var198;
      String[] var199 = new String[0];
      CharsetUtil.Charset var200 = new CharsetUtil.Charset("Cp937", (String)null, var199, (CharsetUtil.1)null);
      var0[99] = var200;
      String[] var201 = new String[0];
      CharsetUtil.Charset var202 = new CharsetUtil.Charset("Cp939", (String)null, var201, (CharsetUtil.1)null);
      var0[100] = var202;
      String[] var203 = new String[0];
      CharsetUtil.Charset var204 = new CharsetUtil.Charset("Cp942", (String)null, var203, (CharsetUtil.1)null);
      var0[101] = var204;
      String[] var205 = new String[0];
      CharsetUtil.Charset var206 = new CharsetUtil.Charset("Cp942C", (String)null, var205, (CharsetUtil.1)null);
      var0[102] = var206;
      String[] var207 = new String[0];
      CharsetUtil.Charset var208 = new CharsetUtil.Charset("Cp943", (String)null, var207, (CharsetUtil.1)null);
      var0[103] = var208;
      String[] var209 = new String[0];
      CharsetUtil.Charset var210 = new CharsetUtil.Charset("Cp943C", (String)null, var209, (CharsetUtil.1)null);
      var0[104] = var210;
      String[] var211 = new String[0];
      CharsetUtil.Charset var212 = new CharsetUtil.Charset("Cp948", (String)null, var211, (CharsetUtil.1)null);
      var0[105] = var212;
      String[] var213 = new String[0];
      CharsetUtil.Charset var214 = new CharsetUtil.Charset("Cp949", (String)null, var213, (CharsetUtil.1)null);
      var0[106] = var214;
      String[] var215 = new String[0];
      CharsetUtil.Charset var216 = new CharsetUtil.Charset("Cp949C", (String)null, var215, (CharsetUtil.1)null);
      var0[107] = var216;
      String[] var217 = new String[0];
      CharsetUtil.Charset var218 = new CharsetUtil.Charset("Cp950", (String)null, var217, (CharsetUtil.1)null);
      var0[108] = var218;
      String[] var219 = new String[0];
      CharsetUtil.Charset var220 = new CharsetUtil.Charset("Cp964", (String)null, var219, (CharsetUtil.1)null);
      var0[109] = var220;
      String[] var221 = new String[0];
      CharsetUtil.Charset var222 = new CharsetUtil.Charset("Cp970", (String)null, var221, (CharsetUtil.1)null);
      var0[110] = var222;
      String[] var223 = new String[0];
      CharsetUtil.Charset var224 = new CharsetUtil.Charset("Cp1006", (String)null, var223, (CharsetUtil.1)null);
      var0[111] = var224;
      String[] var225 = new String[0];
      CharsetUtil.Charset var226 = new CharsetUtil.Charset("Cp1025", (String)null, var225, (CharsetUtil.1)null);
      var0[112] = var226;
      String[] var227 = new String[0];
      CharsetUtil.Charset var228 = new CharsetUtil.Charset("Cp1046", (String)null, var227, (CharsetUtil.1)null);
      var0[113] = var228;
      String[] var229 = new String[0];
      CharsetUtil.Charset var230 = new CharsetUtil.Charset("Cp1097", (String)null, var229, (CharsetUtil.1)null);
      var0[114] = var230;
      String[] var231 = new String[0];
      CharsetUtil.Charset var232 = new CharsetUtil.Charset("Cp1098", (String)null, var231, (CharsetUtil.1)null);
      var0[115] = var232;
      String[] var233 = new String[0];
      CharsetUtil.Charset var234 = new CharsetUtil.Charset("Cp1112", (String)null, var233, (CharsetUtil.1)null);
      var0[116] = var234;
      String[] var235 = new String[0];
      CharsetUtil.Charset var236 = new CharsetUtil.Charset("Cp1122", (String)null, var235, (CharsetUtil.1)null);
      var0[117] = var236;
      String[] var237 = new String[0];
      CharsetUtil.Charset var238 = new CharsetUtil.Charset("Cp1123", (String)null, var237, (CharsetUtil.1)null);
      var0[118] = var238;
      String[] var239 = new String[0];
      CharsetUtil.Charset var240 = new CharsetUtil.Charset("Cp1124", (String)null, var239, (CharsetUtil.1)null);
      var0[119] = var240;
      String[] var241 = new String[0];
      CharsetUtil.Charset var242 = new CharsetUtil.Charset("Cp1381", (String)null, var241, (CharsetUtil.1)null);
      var0[120] = var242;
      String[] var243 = new String[0];
      CharsetUtil.Charset var244 = new CharsetUtil.Charset("Cp1383", (String)null, var243, (CharsetUtil.1)null);
      var0[121] = var244;
      String[] var245 = new String[0];
      CharsetUtil.Charset var246 = new CharsetUtil.Charset("Cp33722", (String)null, var245, (CharsetUtil.1)null);
      var0[122] = var246;
      String[] var247 = new String[0];
      CharsetUtil.Charset var248 = new CharsetUtil.Charset("Big5_Solaris", (String)null, var247, (CharsetUtil.1)null);
      var0[123] = var248;
      String[] var249 = new String[0];
      CharsetUtil.Charset var250 = new CharsetUtil.Charset("EUC_JP_LINUX", (String)null, var249, (CharsetUtil.1)null);
      var0[124] = var250;
      String[] var251 = new String[0];
      CharsetUtil.Charset var252 = new CharsetUtil.Charset("EUC_JP_Solaris", (String)null, var251, (CharsetUtil.1)null);
      var0[125] = var252;
      String[] var253 = new String[]{"x-ISCII91", "iscii"};
      CharsetUtil.Charset var254 = new CharsetUtil.Charset("ISCII91", (String)null, var253, (CharsetUtil.1)null);
      var0[126] = var254;
      String[] var255 = new String[0];
      CharsetUtil.Charset var256 = new CharsetUtil.Charset("ISO2022_CN_CNS", (String)null, var255, (CharsetUtil.1)null);
      var0[127] = var256;
      String[] var257 = new String[0];
      CharsetUtil.Charset var258 = new CharsetUtil.Charset("ISO2022_CN_GB", (String)null, var257, (CharsetUtil.1)null);
      var0[128] = var258;
      String[] var259 = new String[0];
      CharsetUtil.Charset var260 = new CharsetUtil.Charset("x-iso-8859-11", (String)null, var259, (CharsetUtil.1)null);
      var0[129] = var260;
      String[] var261 = new String[0];
      CharsetUtil.Charset var262 = new CharsetUtil.Charset("JISAutoDetect", (String)null, var261, (CharsetUtil.1)null);
      var0[130] = var262;
      String[] var263 = new String[0];
      CharsetUtil.Charset var264 = new CharsetUtil.Charset("MacArabic", (String)null, var263, (CharsetUtil.1)null);
      var0[131] = var264;
      String[] var265 = new String[0];
      CharsetUtil.Charset var266 = new CharsetUtil.Charset("MacCentralEurope", (String)null, var265, (CharsetUtil.1)null);
      var0[132] = var266;
      String[] var267 = new String[0];
      CharsetUtil.Charset var268 = new CharsetUtil.Charset("MacCroatian", (String)null, var267, (CharsetUtil.1)null);
      var0[133] = var268;
      String[] var269 = new String[0];
      CharsetUtil.Charset var270 = new CharsetUtil.Charset("MacCyrillic", (String)null, var269, (CharsetUtil.1)null);
      var0[134] = var270;
      String[] var271 = new String[0];
      CharsetUtil.Charset var272 = new CharsetUtil.Charset("MacDingbat", (String)null, var271, (CharsetUtil.1)null);
      var0[135] = var272;
      String[] var273 = new String[0];
      CharsetUtil.Charset var274 = new CharsetUtil.Charset("MacGreek", "MacGreek", var273, (CharsetUtil.1)null);
      var0[136] = var274;
      String[] var275 = new String[0];
      CharsetUtil.Charset var276 = new CharsetUtil.Charset("MacHebrew", (String)null, var275, (CharsetUtil.1)null);
      var0[137] = var276;
      String[] var277 = new String[0];
      CharsetUtil.Charset var278 = new CharsetUtil.Charset("MacIceland", (String)null, var277, (CharsetUtil.1)null);
      var0[138] = var278;
      String[] var279 = new String[]{"Macintosh", "MAC", "csMacintosh"};
      CharsetUtil.Charset var280 = new CharsetUtil.Charset("MacRoman", "MacRoman", var279, (CharsetUtil.1)null);
      var0[139] = var280;
      String[] var281 = new String[0];
      CharsetUtil.Charset var282 = new CharsetUtil.Charset("MacRomania", (String)null, var281, (CharsetUtil.1)null);
      var0[140] = var282;
      String[] var283 = new String[0];
      CharsetUtil.Charset var284 = new CharsetUtil.Charset("MacSymbol", (String)null, var283, (CharsetUtil.1)null);
      var0[141] = var284;
      String[] var285 = new String[0];
      CharsetUtil.Charset var286 = new CharsetUtil.Charset("MacThai", (String)null, var285, (CharsetUtil.1)null);
      var0[142] = var286;
      String[] var287 = new String[0];
      CharsetUtil.Charset var288 = new CharsetUtil.Charset("MacTurkish", (String)null, var287, (CharsetUtil.1)null);
      var0[143] = var288;
      String[] var289 = new String[0];
      CharsetUtil.Charset var290 = new CharsetUtil.Charset("MacUkraine", (String)null, var289, (CharsetUtil.1)null);
      var0[144] = var290;
      String[] var291 = new String[0];
      CharsetUtil.Charset var292 = new CharsetUtil.Charset("UnicodeBig", (String)null, var291, (CharsetUtil.1)null);
      var0[145] = var292;
      String[] var293 = new String[0];
      CharsetUtil.Charset var294 = new CharsetUtil.Charset("UnicodeLittle", (String)null, var293, (CharsetUtil.1)null);
      var0[146] = var294;
      JAVA_CHARSETS = var0;
      decodingSupported = null;
      encodingSupported = null;
      charsetMap = null;
      decodingSupported = new TreeSet();
      encodingSupported = new TreeSet();
      byte[] var295 = new byte[]{(byte)100, (byte)117, (byte)109, (byte)109, (byte)121};
      int var296 = 0;

      while(true) {
         int var297 = JAVA_CHARSETS.length;
         if(var296 >= var297) {
            charsetMap = new HashMap();
            int var308 = 0;

            while(true) {
               int var309 = JAVA_CHARSETS.length;
               if(var308 >= var309) {
                  if(log.isDebugEnabled()) {
                     Log var322 = log;
                     StringBuilder var323 = (new StringBuilder()).append("Character sets which support decoding: ");
                     TreeSet var324 = decodingSupported;
                     String var325 = var323.append(var324).toString();
                     var322.debug(var325);
                     Log var326 = log;
                     StringBuilder var327 = (new StringBuilder()).append("Character sets which support encoding: ");
                     TreeSet var328 = encodingSupported;
                     String var329 = var327.append(var328).toString();
                     var326.debug(var329);
                  }

                  US_ASCII = java.nio.charset.Charset.forName("US-ASCII");
                  ISO_8859_1 = java.nio.charset.Charset.forName("ISO-8859-1");
                  UTF_8 = java.nio.charset.Charset.forName("UTF-8");
                  return;
               }

               CharsetUtil.Charset var310 = JAVA_CHARSETS[var308];
               HashMap var311 = charsetMap;
               String var312 = var310.canonical.toLowerCase();
               var311.put(var312, var310);
               if(var310.mime != null) {
                  HashMap var314 = charsetMap;
                  String var315 = var310.mime.toLowerCase();
                  var314.put(var315, var310);
               }

               if(var310.aliases != null) {
                  int var317 = 0;

                  while(true) {
                     int var318 = var310.aliases.length;
                     if(var317 >= var318) {
                        break;
                     }

                     HashMap var319 = charsetMap;
                     String var320 = var310.aliases[var317].toLowerCase();
                     var319.put(var320, var310);
                     ++var317;
                  }
               }

               ++var308;
            }
         }

         try {
            String var298 = JAVA_CHARSETS[var296].canonical;
            new String(var295, var298);
            TreeSet var300 = decodingSupported;
            String var301 = JAVA_CHARSETS[var296].canonical.toLowerCase();
            var300.add(var301);
         } catch (UnsupportedOperationException var336) {
            ;
         } catch (UnsupportedEncodingException var337) {
            ;
         }

         try {
            String var303 = JAVA_CHARSETS[var296].canonical;
            byte[] var304 = "dummy".getBytes(var303);
            TreeSet var305 = encodingSupported;
            String var306 = JAVA_CHARSETS[var296].canonical.toLowerCase();
            var305.add(var306);
         } catch (UnsupportedOperationException var334) {
            ;
         } catch (UnsupportedEncodingException var335) {
            ;
         }

         ++var296;
      }
   }

   public CharsetUtil() {}

   public static java.nio.charset.Charset getCharset(String var0) {
      if(var0 == null) {
         var0 = "ISO-8859-1";
      }

      java.nio.charset.Charset var1;
      java.nio.charset.Charset var2;
      try {
         var1 = java.nio.charset.Charset.forName(var0);
      } catch (IllegalCharsetNameException var9) {
         Log var4 = log;
         String var5 = "Illegal charset " + var0 + ", fallback to " + "ISO-8859-1" + ": " + var9;
         var4.info(var5);
         var2 = java.nio.charset.Charset.forName("ISO-8859-1");
         return var2;
      } catch (UnsupportedCharsetException var10) {
         Log var7 = log;
         String var8 = "Unsupported charset " + var0 + ", fallback to " + "ISO-8859-1" + ": " + var10;
         var7.info(var8);
         var2 = java.nio.charset.Charset.forName("ISO-8859-1");
         return var2;
      }

      var2 = var1;
      return var2;
   }

   public static boolean isDecodingSupported(String var0) {
      TreeSet var1 = decodingSupported;
      String var2 = var0.toLowerCase();
      return var1.contains(var2);
   }

   public static boolean isEncodingSupported(String var0) {
      TreeSet var1 = encodingSupported;
      String var2 = var0.toLowerCase();
      return var1.contains(var2);
   }

   public static boolean isWhitespace(char var0) {
      boolean var1;
      if(var0 != 32 && var0 != 9 && var0 != 13 && var0 != 10) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public static boolean isWhitespace(String var0) {
      if(var0 == null) {
         throw new IllegalArgumentException("String may not be null");
      } else {
         int var1 = var0.length();
         int var2 = 0;

         boolean var3;
         while(true) {
            if(var2 >= var1) {
               var3 = true;
               break;
            }

            if(!isWhitespace(var0.charAt(var2))) {
               var3 = false;
               break;
            }

            ++var2;
         }

         return var3;
      }
   }

   public static String toJavaCharset(String var0) {
      HashMap var1 = charsetMap;
      String var2 = var0.toLowerCase();
      CharsetUtil.Charset var3 = (CharsetUtil.Charset)var1.get(var2);
      String var4;
      if(var3 != null) {
         var4 = var3.canonical;
      } else {
         var4 = null;
      }

      return var4;
   }

   public static String toMimeCharset(String var0) {
      HashMap var1 = charsetMap;
      String var2 = var0.toLowerCase();
      CharsetUtil.Charset var3 = (CharsetUtil.Charset)var1.get(var2);
      String var4;
      if(var3 != null) {
         var4 = var3.mime;
      } else {
         var4 = null;
      }

      return var4;
   }

   // $FF: synthetic class
   static class 1 {
   }

   private static class Charset implements Comparable {

      private String[] aliases;
      private String canonical;
      private String mime;


      private Charset(String var1, String var2, String[] var3) {
         this.canonical = null;
         this.mime = null;
         this.aliases = null;
         this.canonical = var1;
         this.mime = var2;
         this.aliases = var3;
      }

      // $FF: synthetic method
      Charset(String var1, String var2, String[] var3, CharsetUtil.1 var4) {
         this(var1, var2, var3);
      }

      public int compareTo(Object var1) {
         CharsetUtil.Charset var2 = (CharsetUtil.Charset)var1;
         String var3 = this.canonical;
         String var4 = var2.canonical;
         return var3.compareTo(var4);
      }
   }
}
