package com.facebook.katana.util;

import android.util.SparseArray;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

class Entities {

   private static final String[][] BASIC_ARRAY;
   public static final Entities HTML40;
   static final String[][] HTML40_ARRAY;
   static final String[][] ISO8859_1_ARRAY;
   Entities.EntityMap map;


   static {
      String[] var0 = new String[4];
      String[] var1 = new String[]{"quot", "34"};
      var0[0] = var1;
      String[] var2 = new String[]{"amp", "38"};
      var0[1] = var2;
      String[] var3 = new String[]{"lt", "60"};
      var0[2] = var3;
      String[] var4 = new String[]{"gt", "62"};
      var0[3] = var4;
      BASIC_ARRAY = var0;
      String[] var5 = new String[96];
      String[] var6 = new String[]{"nbsp", "160"};
      var5[0] = var6;
      String[] var7 = new String[]{"iexcl", "161"};
      var5[1] = var7;
      String[] var8 = new String[]{"cent", "162"};
      var5[2] = var8;
      String[] var9 = new String[]{"pound", "163"};
      var5[3] = var9;
      String[] var10 = new String[]{"curren", "164"};
      var5[4] = var10;
      String[] var11 = new String[]{"yen", "165"};
      var5[5] = var11;
      String[] var12 = new String[]{"brvbar", "166"};
      var5[6] = var12;
      String[] var13 = new String[]{"sect", "167"};
      var5[7] = var13;
      String[] var14 = new String[]{"uml", "168"};
      var5[8] = var14;
      String[] var15 = new String[]{"copy", "169"};
      var5[9] = var15;
      String[] var16 = new String[]{"ordf", "170"};
      var5[10] = var16;
      String[] var17 = new String[]{"laquo", "171"};
      var5[11] = var17;
      String[] var18 = new String[]{"not", "172"};
      var5[12] = var18;
      String[] var19 = new String[]{"shy", "173"};
      var5[13] = var19;
      String[] var20 = new String[]{"reg", "174"};
      var5[14] = var20;
      String[] var21 = new String[]{"macr", "175"};
      var5[15] = var21;
      String[] var22 = new String[]{"deg", "176"};
      var5[16] = var22;
      String[] var23 = new String[]{"plusmn", "177"};
      var5[17] = var23;
      String[] var24 = new String[]{"sup2", "178"};
      var5[18] = var24;
      String[] var25 = new String[]{"sup3", "179"};
      var5[19] = var25;
      String[] var26 = new String[]{"acute", "180"};
      var5[20] = var26;
      String[] var27 = new String[]{"micro", "181"};
      var5[21] = var27;
      String[] var28 = new String[]{"para", "182"};
      var5[22] = var28;
      String[] var29 = new String[]{"middot", "183"};
      var5[23] = var29;
      String[] var30 = new String[]{"cedil", "184"};
      var5[24] = var30;
      String[] var31 = new String[]{"sup1", "185"};
      var5[25] = var31;
      String[] var32 = new String[]{"ordm", "186"};
      var5[26] = var32;
      String[] var33 = new String[]{"raquo", "187"};
      var5[27] = var33;
      String[] var34 = new String[]{"frac14", "188"};
      var5[28] = var34;
      String[] var35 = new String[]{"frac12", "189"};
      var5[29] = var35;
      String[] var36 = new String[]{"frac34", "190"};
      var5[30] = var36;
      String[] var37 = new String[]{"iquest", "191"};
      var5[31] = var37;
      String[] var38 = new String[]{"Agrave", "192"};
      var5[32] = var38;
      String[] var39 = new String[]{"Aacute", "193"};
      var5[33] = var39;
      String[] var40 = new String[]{"Acirc", "194"};
      var5[34] = var40;
      String[] var41 = new String[]{"Atilde", "195"};
      var5[35] = var41;
      String[] var42 = new String[]{"Auml", "196"};
      var5[36] = var42;
      String[] var43 = new String[]{"Aring", "197"};
      var5[37] = var43;
      String[] var44 = new String[]{"AElig", "198"};
      var5[38] = var44;
      String[] var45 = new String[]{"Ccedil", "199"};
      var5[39] = var45;
      String[] var46 = new String[]{"Egrave", "200"};
      var5[40] = var46;
      String[] var47 = new String[]{"Eacute", "201"};
      var5[41] = var47;
      String[] var48 = new String[]{"Ecirc", "202"};
      var5[42] = var48;
      String[] var49 = new String[]{"Euml", "203"};
      var5[43] = var49;
      String[] var50 = new String[]{"Igrave", "204"};
      var5[44] = var50;
      String[] var51 = new String[]{"Iacute", "205"};
      var5[45] = var51;
      String[] var52 = new String[]{"Icirc", "206"};
      var5[46] = var52;
      String[] var53 = new String[]{"Iuml", "207"};
      var5[47] = var53;
      String[] var54 = new String[]{"ETH", "208"};
      var5[48] = var54;
      String[] var55 = new String[]{"Ntilde", "209"};
      var5[49] = var55;
      String[] var56 = new String[]{"Ograve", "210"};
      var5[50] = var56;
      String[] var57 = new String[]{"Oacute", "211"};
      var5[51] = var57;
      String[] var58 = new String[]{"Ocirc", "212"};
      var5[52] = var58;
      String[] var59 = new String[]{"Otilde", "213"};
      var5[53] = var59;
      String[] var60 = new String[]{"Ouml", "214"};
      var5[54] = var60;
      String[] var61 = new String[]{"times", "215"};
      var5[55] = var61;
      String[] var62 = new String[]{"Oslash", "216"};
      var5[56] = var62;
      String[] var63 = new String[]{"Ugrave", "217"};
      var5[57] = var63;
      String[] var64 = new String[]{"Uacute", "218"};
      var5[58] = var64;
      String[] var65 = new String[]{"Ucirc", "219"};
      var5[59] = var65;
      String[] var66 = new String[]{"Uuml", "220"};
      var5[60] = var66;
      String[] var67 = new String[]{"Yacute", "221"};
      var5[61] = var67;
      String[] var68 = new String[]{"THORN", "222"};
      var5[62] = var68;
      String[] var69 = new String[]{"szlig", "223"};
      var5[63] = var69;
      String[] var70 = new String[]{"agrave", "224"};
      var5[64] = var70;
      String[] var71 = new String[]{"aacute", "225"};
      var5[65] = var71;
      String[] var72 = new String[]{"acirc", "226"};
      var5[66] = var72;
      String[] var73 = new String[]{"atilde", "227"};
      var5[67] = var73;
      String[] var74 = new String[]{"auml", "228"};
      var5[68] = var74;
      String[] var75 = new String[]{"aring", "229"};
      var5[69] = var75;
      String[] var76 = new String[]{"aelig", "230"};
      var5[70] = var76;
      String[] var77 = new String[]{"ccedil", "231"};
      var5[71] = var77;
      String[] var78 = new String[]{"egrave", "232"};
      var5[72] = var78;
      String[] var79 = new String[]{"eacute", "233"};
      var5[73] = var79;
      String[] var80 = new String[]{"ecirc", "234"};
      var5[74] = var80;
      String[] var81 = new String[]{"euml", "235"};
      var5[75] = var81;
      String[] var82 = new String[]{"igrave", "236"};
      var5[76] = var82;
      String[] var83 = new String[]{"iacute", "237"};
      var5[77] = var83;
      String[] var84 = new String[]{"icirc", "238"};
      var5[78] = var84;
      String[] var85 = new String[]{"iuml", "239"};
      var5[79] = var85;
      String[] var86 = new String[]{"eth", "240"};
      var5[80] = var86;
      String[] var87 = new String[]{"ntilde", "241"};
      var5[81] = var87;
      String[] var88 = new String[]{"ograve", "242"};
      var5[82] = var88;
      String[] var89 = new String[]{"oacute", "243"};
      var5[83] = var89;
      String[] var90 = new String[]{"ocirc", "244"};
      var5[84] = var90;
      String[] var91 = new String[]{"otilde", "245"};
      var5[85] = var91;
      String[] var92 = new String[]{"ouml", "246"};
      var5[86] = var92;
      String[] var93 = new String[]{"divide", "247"};
      var5[87] = var93;
      String[] var94 = new String[]{"oslash", "248"};
      var5[88] = var94;
      String[] var95 = new String[]{"ugrave", "249"};
      var5[89] = var95;
      String[] var96 = new String[]{"uacute", "250"};
      var5[90] = var96;
      String[] var97 = new String[]{"ucirc", "251"};
      var5[91] = var97;
      String[] var98 = new String[]{"uuml", "252"};
      var5[92] = var98;
      String[] var99 = new String[]{"yacute", "253"};
      var5[93] = var99;
      String[] var100 = new String[]{"thorn", "254"};
      var5[94] = var100;
      String[] var101 = new String[]{"yuml", "255"};
      var5[95] = var101;
      ISO8859_1_ARRAY = var5;
      String[] var102 = new String[151];
      String[] var103 = new String[]{"fnof", "402"};
      var102[0] = var103;
      String[] var104 = new String[]{"Alpha", "913"};
      var102[1] = var104;
      String[] var105 = new String[]{"Beta", "914"};
      var102[2] = var105;
      String[] var106 = new String[]{"Gamma", "915"};
      var102[3] = var106;
      String[] var107 = new String[]{"Delta", "916"};
      var102[4] = var107;
      String[] var108 = new String[]{"Epsilon", "917"};
      var102[5] = var108;
      String[] var109 = new String[]{"Zeta", "918"};
      var102[6] = var109;
      String[] var110 = new String[]{"Eta", "919"};
      var102[7] = var110;
      String[] var111 = new String[]{"Theta", "920"};
      var102[8] = var111;
      String[] var112 = new String[]{"Iota", "921"};
      var102[9] = var112;
      String[] var113 = new String[]{"Kappa", "922"};
      var102[10] = var113;
      String[] var114 = new String[]{"Lambda", "923"};
      var102[11] = var114;
      String[] var115 = new String[]{"Mu", "924"};
      var102[12] = var115;
      String[] var116 = new String[]{"Nu", "925"};
      var102[13] = var116;
      String[] var117 = new String[]{"Xi", "926"};
      var102[14] = var117;
      String[] var118 = new String[]{"Omicron", "927"};
      var102[15] = var118;
      String[] var119 = new String[]{"Pi", "928"};
      var102[16] = var119;
      String[] var120 = new String[]{"Rho", "929"};
      var102[17] = var120;
      String[] var121 = new String[]{"Sigma", "931"};
      var102[18] = var121;
      String[] var122 = new String[]{"Tau", "932"};
      var102[19] = var122;
      String[] var123 = new String[]{"Upsilon", "933"};
      var102[20] = var123;
      String[] var124 = new String[]{"Phi", "934"};
      var102[21] = var124;
      String[] var125 = new String[]{"Chi", "935"};
      var102[22] = var125;
      String[] var126 = new String[]{"Psi", "936"};
      var102[23] = var126;
      String[] var127 = new String[]{"Omega", "937"};
      var102[24] = var127;
      String[] var128 = new String[]{"alpha", "945"};
      var102[25] = var128;
      String[] var129 = new String[]{"beta", "946"};
      var102[26] = var129;
      String[] var130 = new String[]{"gamma", "947"};
      var102[27] = var130;
      String[] var131 = new String[]{"delta", "948"};
      var102[28] = var131;
      String[] var132 = new String[]{"epsilon", "949"};
      var102[29] = var132;
      String[] var133 = new String[]{"zeta", "950"};
      var102[30] = var133;
      String[] var134 = new String[]{"eta", "951"};
      var102[31] = var134;
      String[] var135 = new String[]{"theta", "952"};
      var102[32] = var135;
      String[] var136 = new String[]{"iota", "953"};
      var102[33] = var136;
      String[] var137 = new String[]{"kappa", "954"};
      var102[34] = var137;
      String[] var138 = new String[]{"lambda", "955"};
      var102[35] = var138;
      String[] var139 = new String[]{"mu", "956"};
      var102[36] = var139;
      String[] var140 = new String[]{"nu", "957"};
      var102[37] = var140;
      String[] var141 = new String[]{"xi", "958"};
      var102[38] = var141;
      String[] var142 = new String[]{"omicron", "959"};
      var102[39] = var142;
      String[] var143 = new String[]{"pi", "960"};
      var102[40] = var143;
      String[] var144 = new String[]{"rho", "961"};
      var102[41] = var144;
      String[] var145 = new String[]{"sigmaf", "962"};
      var102[42] = var145;
      String[] var146 = new String[]{"sigma", "963"};
      var102[43] = var146;
      String[] var147 = new String[]{"tau", "964"};
      var102[44] = var147;
      String[] var148 = new String[]{"upsilon", "965"};
      var102[45] = var148;
      String[] var149 = new String[]{"phi", "966"};
      var102[46] = var149;
      String[] var150 = new String[]{"chi", "967"};
      var102[47] = var150;
      String[] var151 = new String[]{"psi", "968"};
      var102[48] = var151;
      String[] var152 = new String[]{"omega", "969"};
      var102[49] = var152;
      String[] var153 = new String[]{"thetasym", "977"};
      var102[50] = var153;
      String[] var154 = new String[]{"upsih", "978"};
      var102[51] = var154;
      String[] var155 = new String[]{"piv", "982"};
      var102[52] = var155;
      String[] var156 = new String[]{"bull", "8226"};
      var102[53] = var156;
      String[] var157 = new String[]{"hellip", "8230"};
      var102[54] = var157;
      String[] var158 = new String[]{"prime", "8242"};
      var102[55] = var158;
      String[] var159 = new String[]{"Prime", "8243"};
      var102[56] = var159;
      String[] var160 = new String[]{"oline", "8254"};
      var102[57] = var160;
      String[] var161 = new String[]{"frasl", "8260"};
      var102[58] = var161;
      String[] var162 = new String[]{"weierp", "8472"};
      var102[59] = var162;
      String[] var163 = new String[]{"image", "8465"};
      var102[60] = var163;
      String[] var164 = new String[]{"real", "8476"};
      var102[61] = var164;
      String[] var165 = new String[]{"trade", "8482"};
      var102[62] = var165;
      String[] var166 = new String[]{"alefsym", "8501"};
      var102[63] = var166;
      String[] var167 = new String[]{"larr", "8592"};
      var102[64] = var167;
      String[] var168 = new String[]{"uarr", "8593"};
      var102[65] = var168;
      String[] var169 = new String[]{"rarr", "8594"};
      var102[66] = var169;
      String[] var170 = new String[]{"darr", "8595"};
      var102[67] = var170;
      String[] var171 = new String[]{"harr", "8596"};
      var102[68] = var171;
      String[] var172 = new String[]{"crarr", "8629"};
      var102[69] = var172;
      String[] var173 = new String[]{"lArr", "8656"};
      var102[70] = var173;
      String[] var174 = new String[]{"uArr", "8657"};
      var102[71] = var174;
      String[] var175 = new String[]{"rArr", "8658"};
      var102[72] = var175;
      String[] var176 = new String[]{"dArr", "8659"};
      var102[73] = var176;
      String[] var177 = new String[]{"hArr", "8660"};
      var102[74] = var177;
      String[] var178 = new String[]{"forall", "8704"};
      var102[75] = var178;
      String[] var179 = new String[]{"part", "8706"};
      var102[76] = var179;
      String[] var180 = new String[]{"exist", "8707"};
      var102[77] = var180;
      String[] var181 = new String[]{"empty", "8709"};
      var102[78] = var181;
      String[] var182 = new String[]{"nabla", "8711"};
      var102[79] = var182;
      String[] var183 = new String[]{"isin", "8712"};
      var102[80] = var183;
      String[] var184 = new String[]{"notin", "8713"};
      var102[81] = var184;
      String[] var185 = new String[]{"ni", "8715"};
      var102[82] = var185;
      String[] var186 = new String[]{"prod", "8719"};
      var102[83] = var186;
      String[] var187 = new String[]{"sum", "8721"};
      var102[84] = var187;
      String[] var188 = new String[]{"minus", "8722"};
      var102[85] = var188;
      String[] var189 = new String[]{"lowast", "8727"};
      var102[86] = var189;
      String[] var190 = new String[]{"radic", "8730"};
      var102[87] = var190;
      String[] var191 = new String[]{"prop", "8733"};
      var102[88] = var191;
      String[] var192 = new String[]{"infin", "8734"};
      var102[89] = var192;
      String[] var193 = new String[]{"ang", "8736"};
      var102[90] = var193;
      String[] var194 = new String[]{"and", "8743"};
      var102[91] = var194;
      String[] var195 = new String[]{"or", "8744"};
      var102[92] = var195;
      String[] var196 = new String[]{"cap", "8745"};
      var102[93] = var196;
      String[] var197 = new String[]{"cup", "8746"};
      var102[94] = var197;
      String[] var198 = new String[]{"int", "8747"};
      var102[95] = var198;
      String[] var199 = new String[]{"there4", "8756"};
      var102[96] = var199;
      String[] var200 = new String[]{"sim", "8764"};
      var102[97] = var200;
      String[] var201 = new String[]{"cong", "8773"};
      var102[98] = var201;
      String[] var202 = new String[]{"asymp", "8776"};
      var102[99] = var202;
      String[] var203 = new String[]{"ne", "8800"};
      var102[100] = var203;
      String[] var204 = new String[]{"equiv", "8801"};
      var102[101] = var204;
      String[] var205 = new String[]{"le", "8804"};
      var102[102] = var205;
      String[] var206 = new String[]{"ge", "8805"};
      var102[103] = var206;
      String[] var207 = new String[]{"sub", "8834"};
      var102[104] = var207;
      String[] var208 = new String[]{"sup", "8835"};
      var102[105] = var208;
      String[] var209 = new String[]{"sube", "8838"};
      var102[106] = var209;
      String[] var210 = new String[]{"supe", "8839"};
      var102[107] = var210;
      String[] var211 = new String[]{"oplus", "8853"};
      var102[108] = var211;
      String[] var212 = new String[]{"otimes", "8855"};
      var102[109] = var212;
      String[] var213 = new String[]{"perp", "8869"};
      var102[110] = var213;
      String[] var214 = new String[]{"sdot", "8901"};
      var102[111] = var214;
      String[] var215 = new String[]{"lceil", "8968"};
      var102[112] = var215;
      String[] var216 = new String[]{"rceil", "8969"};
      var102[113] = var216;
      String[] var217 = new String[]{"lfloor", "8970"};
      var102[114] = var217;
      String[] var218 = new String[]{"rfloor", "8971"};
      var102[115] = var218;
      String[] var219 = new String[]{"lang", "9001"};
      var102[116] = var219;
      String[] var220 = new String[]{"rang", "9002"};
      var102[117] = var220;
      String[] var221 = new String[]{"loz", "9674"};
      var102[118] = var221;
      String[] var222 = new String[]{"spades", "9824"};
      var102[119] = var222;
      String[] var223 = new String[]{"clubs", "9827"};
      var102[120] = var223;
      String[] var224 = new String[]{"hearts", "9829"};
      var102[121] = var224;
      String[] var225 = new String[]{"diams", "9830"};
      var102[122] = var225;
      String[] var226 = new String[]{"OElig", "338"};
      var102[123] = var226;
      String[] var227 = new String[]{"oelig", "339"};
      var102[124] = var227;
      String[] var228 = new String[]{"Scaron", "352"};
      var102[125] = var228;
      String[] var229 = new String[]{"scaron", "353"};
      var102[126] = var229;
      String[] var230 = new String[]{"Yuml", "376"};
      var102[127] = var230;
      String[] var231 = new String[]{"circ", "710"};
      var102[128] = var231;
      String[] var232 = new String[]{"tilde", "732"};
      var102[129] = var232;
      String[] var233 = new String[]{"ensp", "8194"};
      var102[130] = var233;
      String[] var234 = new String[]{"emsp", "8195"};
      var102[131] = var234;
      String[] var235 = new String[]{"thinsp", "8201"};
      var102[132] = var235;
      String[] var236 = new String[]{"zwnj", "8204"};
      var102[133] = var236;
      String[] var237 = new String[]{"zwj", "8205"};
      var102[134] = var237;
      String[] var238 = new String[]{"lrm", "8206"};
      var102[135] = var238;
      String[] var239 = new String[]{"rlm", "8207"};
      var102[136] = var239;
      String[] var240 = new String[]{"ndash", "8211"};
      var102[137] = var240;
      String[] var241 = new String[]{"mdash", "8212"};
      var102[138] = var241;
      String[] var242 = new String[]{"lsquo", "8216"};
      var102[139] = var242;
      String[] var243 = new String[]{"rsquo", "8217"};
      var102[140] = var243;
      String[] var244 = new String[]{"sbquo", "8218"};
      var102[141] = var244;
      String[] var245 = new String[]{"ldquo", "8220"};
      var102[142] = var245;
      String[] var246 = new String[]{"rdquo", "8221"};
      var102[143] = var246;
      String[] var247 = new String[]{"bdquo", "8222"};
      var102[144] = var247;
      String[] var248 = new String[]{"dagger", "8224"};
      var102[145] = var248;
      String[] var249 = new String[]{"Dagger", "8225"};
      var102[146] = var249;
      String[] var250 = new String[]{"permil", "8240"};
      var102[147] = var250;
      String[] var251 = new String[]{"lsaquo", "8249"};
      var102[148] = var251;
      String[] var252 = new String[]{"rsaquo", "8250"};
      var102[149] = var252;
      String[] var253 = new String[]{"euro", "8364"};
      var102[150] = var253;
      HTML40_ARRAY = var102;
      HTML40 = new Entities();
      fillWithHtml40Entities(HTML40);
   }

   Entities() {
      Entities.LookupEntityMap var1 = new Entities.LookupEntityMap();
      this.map = var1;
   }

   static void fillWithHtml40Entities(Entities var0) {
      String[][] var1 = BASIC_ARRAY;
      var0.addEntities(var1);
      String[][] var2 = ISO8859_1_ARRAY;
      var0.addEntities(var2);
      String[][] var3 = HTML40_ARRAY;
      var0.addEntities(var3);
   }

   public void addEntities(String[][] var1) {
      int var2 = 0;

      while(true) {
         int var3 = var1.length;
         if(var2 >= var3) {
            return;
         }

         String var4 = var1[var2][0];
         int var5 = Integer.parseInt(var1[var2][1]);
         this.addEntity(var4, var5);
         ++var2;
      }
   }

   public void addEntity(String var1, int var2) {
      this.map.add(var1, var2);
   }

   public String entityName(int var1) {
      return this.map.name(var1);
   }

   public int entityValue(String var1) {
      return this.map.value(var1);
   }

   public String unescape(String param1) {
      // $FF: Couldn't be decompiled
   }

   interface EntityMap {

      void add(String var1, int var2);

      String name(int var1);

      int value(String var1);
   }

   static class HashEntityMap extends Entities.MapIntMap {

      public HashEntityMap() {
         HashMap var1 = new HashMap();
         this.mapNameToValue = var1;
         HashMap var2 = new HashMap();
         this.mapValueToName = var2;
      }
   }

   static class LookupEntityMap extends Entities.PrimitiveEntityMap {

      private int LOOKUP_TABLE_SIZE = 256;
      private String[] lookupTable;


      LookupEntityMap() {}

      private void createLookupTable() {
         String[] var1 = new String[this.LOOKUP_TABLE_SIZE];
         this.lookupTable = var1;
         int var2 = 0;

         while(true) {
            int var3 = this.LOOKUP_TABLE_SIZE;
            if(var2 >= var3) {
               return;
            }

            String[] var4 = this.lookupTable;
            String var5 = super.name(var2);
            var4[var2] = var5;
            ++var2;
         }
      }

      private String[] lookupTable() {
         if(this.lookupTable == null) {
            this.createLookupTable();
         }

         return this.lookupTable;
      }

      public String name(int var1) {
         int var2 = this.LOOKUP_TABLE_SIZE;
         String var3;
         if(var1 < var2) {
            var3 = this.lookupTable()[var1];
         } else {
            var3 = super.name(var1);
         }

         return var3;
      }
   }

   static class PrimitiveEntityMap implements Entities.EntityMap {

      private Map<String, Integer> mapNameToValue;
      private SparseArray<String> mapValueToName;


      PrimitiveEntityMap() {
         HashMap var1 = new HashMap();
         this.mapNameToValue = var1;
         SparseArray var2 = new SparseArray();
         this.mapValueToName = var2;
      }

      public void add(String var1, int var2) {
         Map var3 = this.mapNameToValue;
         Integer var4 = new Integer(var2);
         var3.put(var1, var4);
         this.mapValueToName.put(var2, var1);
      }

      public String name(int var1) {
         return (String)this.mapValueToName.get(var1);
      }

      public int value(String var1) {
         Object var2 = this.mapNameToValue.get(var1);
         int var3;
         if(var2 == null) {
            var3 = -1;
         } else {
            var3 = ((Integer)var2).intValue();
         }

         return var3;
      }
   }

   abstract static class MapIntMap implements Entities.EntityMap {

      protected Map<String, Integer> mapNameToValue;
      protected Map<Integer, String> mapValueToName;


      MapIntMap() {}

      public void add(String var1, int var2) {
         Map var3 = this.mapNameToValue;
         Integer var4 = new Integer(var2);
         var3.put(var1, var4);
         Map var6 = this.mapValueToName;
         Integer var7 = new Integer(var2);
         var6.put(var7, var1);
      }

      public String name(int var1) {
         Map var2 = this.mapValueToName;
         Integer var3 = new Integer(var1);
         return (String)var2.get(var3);
      }

      public int value(String var1) {
         Object var2 = this.mapNameToValue.get(var1);
         int var3;
         if(var2 == null) {
            var3 = -1;
         } else {
            var3 = ((Integer)var2).intValue();
         }

         return var3;
      }
   }

   static class BinaryEntityMap extends Entities.ArrayEntityMap {

      public BinaryEntityMap() {}

      public BinaryEntityMap(int var1) {
         super(var1);
      }

      private int binarySearch(int var1) {
         int var2 = 0;
         int var3 = this.size - 1;

         int var6;
         while(true) {
            if(var2 <= var3) {
               int var4 = var2 + var3 >> 1;
               int var5 = this.values[var4];
               if(var5 < var1) {
                  var2 = var4 + 1;
                  continue;
               }

               if(var5 > var1) {
                  var3 = var4 - 1;
                  continue;
               }

               var6 = var4;
               break;
            }

            var6 = -(var2 + 1);
            break;
         }

         return var6;
      }

      public void add(String var1, int var2) {
         int var3 = this.size + 1;
         this.ensureCapacity(var3);
         int var4 = this.binarySearch(var2);
         if(var4 <= 0) {
            int var5 = -(var4 + 1);
            int[] var6 = this.values;
            int[] var7 = this.values;
            int var8 = var5 + 1;
            int var9 = this.size - var5;
            System.arraycopy(var6, var5, var7, var8, var9);
            this.values[var5] = var2;
            String[] var10 = this.names;
            String[] var11 = this.names;
            int var12 = var5 + 1;
            int var13 = this.size - var5;
            System.arraycopy(var10, var5, var11, var12, var13);
            this.names[var5] = var1;
            int var14 = this.size + 1;
            this.size = var14;
         }
      }

      public String name(int var1) {
         int var2 = this.binarySearch(var1);
         String var3;
         if(var2 < 0) {
            var3 = null;
         } else {
            var3 = this.names[var2];
         }

         return var3;
      }
   }

   static class TreeEntityMap extends Entities.MapIntMap {

      public TreeEntityMap() {
         TreeMap var1 = new TreeMap();
         this.mapNameToValue = var1;
         TreeMap var2 = new TreeMap();
         this.mapValueToName = var2;
      }
   }

   static class ArrayEntityMap implements Entities.EntityMap {

      protected int mGrowBy = 100;
      protected String[] names;
      protected int size = 0;
      protected int[] values;


      public ArrayEntityMap() {
         String[] var1 = new String[this.mGrowBy];
         this.names = var1;
         int[] var2 = new int[this.mGrowBy];
         this.values = var2;
      }

      public ArrayEntityMap(int var1) {
         this.mGrowBy = var1;
         String[] var2 = new String[var1];
         this.names = var2;
         int[] var3 = new int[var1];
         this.values = var3;
      }

      public void add(String var1, int var2) {
         int var3 = this.size + 1;
         this.ensureCapacity(var3);
         String[] var4 = this.names;
         int var5 = this.size;
         var4[var5] = var1;
         int[] var6 = this.values;
         int var7 = this.size;
         var6[var7] = var2;
         int var8 = this.size + 1;
         this.size = var8;
      }

      protected void ensureCapacity(int var1) {
         int var2 = this.names.length;
         if(var1 > var2) {
            int var3 = this.size;
            int var4 = this.mGrowBy;
            int var5 = var3 + var4;
            int var6 = Math.max(var1, var5);
            String[] var7 = new String[var6];
            String[] var8 = this.names;
            int var9 = this.size;
            System.arraycopy(var8, 0, var7, 0, var9);
            this.names = var7;
            int[] var10 = new int[var6];
            int[] var11 = this.values;
            int var12 = this.size;
            System.arraycopy(var11, 0, var10, 0, var12);
            this.values = var10;
         }
      }

      public String name(int var1) {
         int var2 = 0;

         String var4;
         while(true) {
            int var3 = this.size;
            if(var2 >= var3) {
               var4 = null;
               break;
            }

            if(this.values[var2] == var1) {
               var4 = this.names[var2];
               break;
            }

            ++var2;
         }

         return var4;
      }

      public int value(String var1) {
         int var2 = 0;

         int var4;
         while(true) {
            int var3 = this.size;
            if(var2 >= var3) {
               var4 = -1;
               break;
            }

            if(this.names[var2].equals(var1)) {
               var4 = this.values[var2];
               break;
            }

            ++var2;
         }

         return var4;
      }
   }
}
