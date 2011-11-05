package myorg.bouncycastle.crypto.engines;

import myorg.bouncycastle.crypto.BlockCipher;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.DataLengthException;
import myorg.bouncycastle.crypto.params.KeyParameter;

public class CamelliaEngine implements BlockCipher {

   private static final int BLOCK_SIZE = 16;
   private static final int MASK8 = 255;
   private static final int[] SBOX1_1110 = new int[]{1886416896, -2105376256, 741092352, -320017408, -1280068864, 656877312, -1061109760, -437918464, -454761472, -2054847232, 1465341696, 892679424, -353703424, 202116096, -1364283904, 1094795520, 589505280, -269488384, 1802201856, -1819045120, 1162167552, 421075200, -1515870976, 555819264, -303174400, 235802112, 1330597632, 1313754624, 488447232, 1701143808, -1835888128, -1111638784, -2038004224, -1195853824, -1347440896, -1886417152, 2088532992, -336860416, 522133248, -825307648, 1044266496, 808464384, -589505536, 1600085760, 1583242752, -976894720, 185273088, 437918208, -1499027968, -505290496, 960051456, -892679680, -707406592, 1195853568, 1566399744, 1027423488, -640034560, 16843008, 1515870720, -690563584, 1364283648, 1448498688, 1819044864, 1296911616, -1953789184, 218959104, -1701144064, 1717986816, -67372288, -858993664, -1330597888, 757935360, 1953788928, 303174144, 724249344, 538976256, -252645376, -1313754880, -2071690240, -1717987072, -538976512, 1280068608, -875836672, -1027423744, 875836416, 2122219008, 1987474944, 84215040, 1835887872, -1212696832, -1448498944, 825307392, -774778624, 387389184, 67372032, -673720576, 336860160, 1482184704, 976894464, 1633771776, -555819520, 454761216, 286331136, 471604224, 842150400, 252645120, -1667458048, 370546176, 1397969664, 404232192, -218959360, 572662272, -16843264, 1145324544, -808464640, -1296911872, -1010580736, -1246382848, 2054846976, -1852731136, 606348288, 134744064, -387389440, -1465341952, 1616928768, -50529280, 1768515840, 1347440640, -1431655936, -791621632, -1600086016, 2105376000, -1583243008, -1987475200, 1650614784, -1751673088, 1414812672, 1532713728, 505290240, -1785359104, -522133504, -256, 1684300800, -757935616, 269488128, -993737728, 0, 1212696576, -1549556992, -134744320, 1970631936, -606348544, -1970632192, 50529024, -421075456, -623191552, 151587072, 1061109504, -572662528, -1802202112, -2021161216, 1549556736, -2088533248, 33686016, -842150656, 1246382592, -1869574144, 858993408, 1936945920, 1734829824, -151587328, -202116352, -1650615040, 2139062016, -1077952768, -488447488, 1381126656, -1684301056, -656877568, 640034304, -926365696, 926365440, -960051712, 993737472, -2122219264, -1768516096, 1869573888, 1263225600, 320017152, -1094795776, 1667457792, 774778368, -370546432, 2038003968, -1482184960, -1936946176, -1616929024, 1852730880, -1128481792, -1903260160, 690563328, -168430336, -101058304, -1229539840, 791621376, -33686272, -1263225856, 1499027712, 2021160960, -1734830080, 101058048, 1785358848, -404232448, 1179010560, 1903259904, -1162167808, -724249600, 623191296, -1414812928, 1111638528, -2004318208, -1566400000, -1920103168, -84215296, 1920102912, 117901056, -1179010816, 1431655680, -117901312, -286331392, -1397969920, 168430080, 909522432, 1229539584, 707406336, 1751672832, 1010580480, 943208448, -235802368, -1532713984, 1077952512, 673720320, -741092608, 2071689984, -1145324800, -909522688, 1128481536, -1044266752, 353703168, -471604480, -1381126912, -185273344, 2004317952, -943208704, -2139062272, -1633772032};
   private static final int[] SBOX2_0222 = new int[]{14737632, 328965, 5789784, 14277081, 6776679, 5131854, 8487297, 13355979, 13224393, 723723, 11447982, 6974058, 14013909, 1579032, 6118749, 8553090, 4605510, 14671839, 14079702, 2565927, 9079434, 3289650, 4934475, 4342338, 14408667, 1842204, 10395294, 10263708, 3815994, 13290186, 2434341, 8092539, 855309, 7434609, 6250335, 2039583, 16316664, 14145495, 4079166, 10329501, 8158332, 6316128, 12171705, 12500670, 12369084, 9145227, 1447446, 3421236, 5066061, 12829635, 7500402, 9803157, 11250603, 9342606, 12237498, 8026746, 11776947, 131586, 11842740, 11382189, 10658466, 11316396, 14211288, 10132122, 1513239, 1710618, 3487029, 13421772, 16250871, 10066329, 6381921, 5921370, 15263976, 2368548, 5658198, 4210752, 14803425, 6513507, 592137, 3355443, 12566463, 10000536, 9934743, 8750469, 6842472, 16579836, 15527148, 657930, 14342874, 7303023, 5460819, 6447714, 10724259, 3026478, 526344, 11513775, 2631720, 11579568, 7631988, 12763842, 12434877, 3552822, 2236962, 3684408, 6579300, 1973790, 3750201, 2894892, 10921638, 3158064, 15066597, 4473924, 16645629, 8947848, 10461087, 6645093, 8882055, 7039851, 16053492, 2302755, 4737096, 1052688, 13750737, 5329233, 12632256, 16382457, 13816530, 10526880, 5592405, 10592673, 4276545, 16448250, 4408131, 1250067, 12895428, 3092271, 11053224, 11974326, 3947580, 2829099, 12698049, 16777215, 13158600, 10855845, 2105376, 9013641, 0, 9474192, 4671303, 15724527, 15395562, 12040119, 1381653, 394758, 13487565, 11908533, 1184274, 8289918, 12303291, 2697513, 986895, 12105912, 460551, 263172, 10197915, 9737364, 2171169, 6710886, 15132390, 13553358, 15592941, 15198183, 3881787, 16711422, 8355711, 12961221, 10790052, 3618615, 11645361, 5000268, 9539985, 7237230, 9276813, 7763574, 197379, 2960685, 14606046, 9868950, 2500134, 8224125, 13027014, 6052956, 13882323, 15921906, 5197647, 1644825, 4144959, 14474460, 7960953, 1907997, 5395026, 15461355, 15987699, 7171437, 6184542, 16514043, 6908265, 11711154, 15790320, 3223857, 789516, 13948116, 13619151, 9211020, 14869218, 7697781, 11119017, 4868682, 5723991, 8684676, 1118481, 4539717, 1776411, 16119285, 15000804, 921102, 7566195, 11184810, 15856113, 14540253, 5855577, 1315860, 7105644, 9605778, 5526612, 13684944, 7895160, 7368816, 14935011, 4802889, 8421504, 5263440, 10987431, 16185078, 7829367, 9671571, 8816262, 8618883, 2763306, 13092807, 5987163, 15329769, 15658734, 9408399, 65793, 4013373};
   private static final int[] SBOX3_3033 = new int[]{939538488, 1090535745, 369104406, 1979741814, -654255655, -1828678765, 1610637408, -234818830, 1912631922, -1040137534, -1426019413, -1711236454, 1962964341, 100664838, 1459640151, -1610571616, -1862233711, -150931465, -1258244683, -922695223, -1577016670, -1946121076, -771697966, -1879011184, -167708938, 117442311, -1493129305, 654321447, -1912566130, -1308577102, 1224755529, -570368290, 1124090691, 1543527516, -687810601, -956250169, 1040203326, -184486411, -1895788657, 1728079719, 520101663, 402659352, 1845522030, -1358909521, 788541231, -503258398, -2063563387, 218107149, 1392530259, -268373776, -1677681508, 1694524773, -369038614, -1560239197, -1375686994, -1644126562, -335483668, -2147450752, 754986285, 1795189611, -1476351832, 721431339, 905983542, -1509906778, -989805115, -2046785914, 1291865421, 855651123, -50266627, 1711302246, 1476417624, -1778346346, 973093434, 150997257, -1795123819, 268439568, 2013296760, -671033128, 1107313218, -872362804, -285151249, 637543974, -452925979, 1627414881, 436214298, 1056980799, 989870907, -2113895806, -1241467210, -620700709, -738143020, -1744791400, -402593560, -1962898549, 33554946, -352261141, 167774730, 738208812, 486546717, -1342132048, 1862299503, -1929343603, -2013230968, 234884622, 419436825, -2030008441, 1308642894, 184552203, -1459574359, 201329676, 2030074233, 285217041, 2130739071, 570434082, -419371033, 1493195097, -520035871, -637478182, 1023425853, -939472696, 301994514, 67109892, 1946186868, 1409307732, 805318704, 2113961598, -1275022156, 671098920, 1426085205, 1744857192, 1342197840, -1107247426, -805252912, -1006582588, 822096177, -889140277, 704653866, -1392464467, 251662095, -905917750, 1879076976, -16711681, 838873650, 1761634665, 134219784, 1644192354, 0, 603989028, -788475439, -83821573, -1174357318, -318706195, 1157645637, -2130673279, 1929409395, 1828744557, -2080340860, -1627349089, -301928722, 1241533002, -1023360061, 771763758, -1056915007, 16777473, -436148506, 620766501, 1207978056, -1728013927, -1191134791, -1291799629, 2063629179, -117376519, -838807858, -1090469953, -553590817, 1895854449, 687876393, -855585331, 1811967084, 318771987, 1677747300, -1694458981, 1660969827, -1660904035, -1073692480, 1258310475, -1224689737, -1526684251, -1996453495, 1593859935, -1325354575, 385881879, -201263884, -1140802372, -754920493, 1174423110, -822030385, 922761015, 1577082462, 1191200583, -1811901292, -100599046, -67044100, 1526750043, -1761568873, -33489154, 1509972570, -1409241940, 1006648380, 1275087948, 50332419, 889206069, -218041357, 587211555, -1207912264, 1560304989, 1778412138, -1845456238, -721365547, 553656609, 1140868164, 1358975313, -973027642, 2097184125, 956315961, -2097118333, -603923236, -1442796886, 2080406652, 1996519287, 1442862678, 83887365, 452991771, -1543461724, 352326933, 872428596, 503324190, 469769244, -134153992, 1375752786, 536879136, 335549460, -385816087, -1124024899, -587145763, -469703452, -1593794143, -536813344, -1979676022, -251596303, -704588074, 2046851706, -1157579845, -486480925, 1073758272, 1325420367};
   private static final int[] SBOX4_4404 = new int[]{1886388336, 741081132, -1280114509, -1061158720, -454819612, 1465319511, -353763094, -1364328274, 589496355, 1802174571, 1162149957, -1515913051, -303234835, 1330577487, 488439837, -1835925358, -2038038394, -1347485521, 2088501372, 522125343, 1044250686, -589561636, 1583218782, 185270283, -1499070298, 960036921, -707460907, 1566376029, -640089895, 1515847770, 1364262993, 1819017324, -1953824629, -1701183334, -67436293, -1330642768, 1953759348, 724238379, -252706576, -2071723900, -539033377, -875888437, 875823156, 1987444854, 1835860077, -1448542039, -774831919, 67371012, 336855060, 976879674, -555876130, 286326801, 842137650, -1667497828, 1397948499, -219021070, -16908034, -808517425, -1010630461, 2054815866, 606339108, -387448600, 1616904288, 1768489065, -1431699286, -1600126816, -1583284063, 1650589794, 1414791252, 505282590, -522190624, 1684275300, 269484048, 0, -1549598557, 1970602101, -1970667382, -421134106, 151584777, -572718883, -2021195641, -2088566653, -842202931, -1869610864, 1936916595, -151650058, -1650655075, -1078001473, 1381105746, -656932648, -926416696, -960102202, -2122252159, 1869545583, 320012307, 1667432547, -370605847, -1482227545, -1616969569, -1128529732, 690552873, -101121799, 791609391, -1263271756, 2021130360, 101056518, -404291353, 1903231089, -724303660, -1414856533, -2004352888, -1920139123, 1920073842, -1179057991, -117964552, -1398013780, 909508662, 707395626, 1010565180, -235863823, 1077936192, -741146413, -1145372485, 1128464451, 353697813, -1381171027, 2004287607, -2139094912, -2105409406, -320077588, 656867367, -437976859, -2054881147, 892665909, 202113036, 1094778945, -269549329, -1819082605, 421068825, 555810849, 235798542, 1313734734, 1701118053, -1111686979, -1195900744, -1886453617, -336920341, -825360178, 808452144, 1600061535, -976944955, 437911578, -505347871, -892731190, 1195835463, 1027407933, 16842753, -690618154, 1448476758, 1296891981, 218955789, 1717960806, -859045684, 757923885, 303169554, 538968096, -1313800015, -1718026087, 1280049228, -1027473214, 2122186878, 84213765, -1212743497, 825294897, 387383319, -673775401, 1482162264, 1633747041, 454754331, 471597084, 252641295, 370540566, 404226072, 572653602, 1145307204, -1296957262, -1246429003, -1852768111, 134742024, -1465384792, -50593540, 1347420240, -791674672, 2105344125, -1987510135, -1751711593, 1532690523, -1785397099, -65281, -757989166, -993787708, 1212678216, -134807305, -606404389, 50528259, -623247142, 1061093439, -1802239852, 1549533276, 33685506, 1246363722, 858980403, 1734803559, -202178317, 2139029631, -488505118, -1684340581, 640024614, 926351415, 993722427, -1768554346, 1263206475, -1094844226, 774766638, 2037973113, -1936981876, 1852702830, -1903296370, -168492811, -1229586250, -33750787, 1499005017, -1734868840, 1785331818, 1178992710, -1162215238, 623181861, 1111621698, -1566441310, -84279046, 117899271, 1431634005, -286392082, 168427530, 1229520969, 1751646312, 943194168, -1532755804, 673710120, 2071658619, -909573943, -1044315967, -471662365, -185335564, -943259449, -1633812322};
   private static final int[] SIGMA = new int[]{-1600231809, 1003262091, -1233459112, 1286239154, -957401297, -380665154, 1426019237, -237801700, 283453434, -563598051, -1336506174, -1276722691};
   private boolean _keyIs128;
   private boolean initialised = 0;
   private int[] ke;
   private int[] kw;
   private int[] state;
   private int[] subkey;


   public CamelliaEngine() {
      int[] var1 = new int[96];
      this.subkey = var1;
      int[] var2 = new int[8];
      this.kw = var2;
      int[] var3 = new int[12];
      this.ke = var3;
      int[] var4 = new int[4];
      this.state = var4;
   }

   private int bytes2int(byte[] var1, int var2) {
      int var3 = 0;

      for(int var4 = 0; var4 < 4; ++var4) {
         int var5 = var3 << 8;
         int var6 = var4 + var2;
         int var7 = var1[var6] & 255;
         var3 = var5 + var7;
      }

      return var3;
   }

   private void camelliaF2(int[] var1, int[] var2, int var3) {
      int var4 = var1[0];
      int var5 = var3 + 0;
      int var6 = var2[var5];
      int var7 = var4 ^ var6;
      int[] var8 = SBOX4_4404;
      int var9 = var7 & 255;
      int var10 = var8[var9];
      int[] var11 = SBOX3_3033;
      int var12 = var7 >>> 8 & 255;
      int var13 = var11[var12];
      int var14 = var10 ^ var13;
      int[] var15 = SBOX2_0222;
      int var16 = var7 >>> 16 & 255;
      int var17 = var15[var16];
      int var18 = var14 ^ var17;
      int[] var19 = SBOX1_1110;
      int var20 = var7 >>> 24 & 255;
      int var21 = var19[var20];
      int var22 = var18 ^ var21;
      int var23 = var1[1];
      int var24 = var3 + 1;
      int var25 = var2[var24];
      int var26 = var23 ^ var25;
      int[] var27 = SBOX1_1110;
      int var28 = var26 & 255;
      int var29 = var27[var28];
      int[] var30 = SBOX4_4404;
      int var31 = var26 >>> 8 & 255;
      int var32 = var30[var31];
      int var33 = var29 ^ var32;
      int[] var34 = SBOX3_3033;
      int var35 = var26 >>> 16 & 255;
      int var36 = var34[var35];
      int var37 = var33 ^ var36;
      int[] var38 = SBOX2_0222;
      int var39 = var26 >>> 24 & 255;
      int var40 = var38[var39];
      int var41 = var37 ^ var40;
      int var42 = var1[2];
      int var43 = var22 ^ var41;
      int var44 = var42 ^ var43;
      var1[2] = var44;
      int var45 = var1[3];
      int var46 = var22 ^ var41;
      int var47 = rightRotate(var22, 8);
      int var48 = var46 ^ var47;
      int var49 = var45 ^ var48;
      var1[3] = var49;
      int var50 = var1[2];
      int var51 = var3 + 2;
      int var52 = var2[var51];
      int var53 = var50 ^ var52;
      int[] var54 = SBOX4_4404;
      int var55 = var53 & 255;
      int var56 = var54[var55];
      int[] var57 = SBOX3_3033;
      int var58 = var53 >>> 8 & 255;
      int var59 = var57[var58];
      int var60 = var56 ^ var59;
      int[] var61 = SBOX2_0222;
      int var62 = var53 >>> 16 & 255;
      int var63 = var61[var62];
      int var64 = var60 ^ var63;
      int[] var65 = SBOX1_1110;
      int var66 = var53 >>> 24 & 255;
      int var67 = var65[var66];
      int var68 = var64 ^ var67;
      int var69 = var1[3];
      int var70 = var3 + 3;
      int var71 = var2[var70];
      int var72 = var69 ^ var71;
      int[] var73 = SBOX1_1110;
      int var74 = var72 & 255;
      int var75 = var73[var74];
      int[] var76 = SBOX4_4404;
      int var77 = var72 >>> 8 & 255;
      int var78 = var76[var77];
      int var79 = var75 ^ var78;
      int[] var80 = SBOX3_3033;
      int var81 = var72 >>> 16 & 255;
      int var82 = var80[var81];
      int var83 = var79 ^ var82;
      int[] var84 = SBOX2_0222;
      int var85 = var72 >>> 24 & 255;
      int var86 = var84[var85];
      int var87 = var83 ^ var86;
      int var88 = var1[0];
      int var89 = var68 ^ var87;
      int var90 = var88 ^ var89;
      var1[0] = var90;
      int var91 = var1[1];
      int var92 = var68 ^ var87;
      int var93 = rightRotate(var68, 8);
      int var94 = var92 ^ var93;
      int var95 = var91 ^ var94;
      var1[1] = var95;
   }

   private void camelliaFLs(int[] var1, int[] var2, int var3) {
      int var4 = var1[1];
      int var5 = var1[0];
      int var6 = var3 + 0;
      int var7 = var2[var6];
      int var8 = leftRotate(var5 & var7, 1);
      int var9 = var4 ^ var8;
      var1[1] = var9;
      int var10 = var1[0];
      int var11 = var3 + 1;
      int var12 = var2[var11];
      int var13 = var1[1];
      int var14 = var12 | var13;
      int var15 = var10 ^ var14;
      var1[0] = var15;
      int var16 = var1[2];
      int var17 = var3 + 3;
      int var18 = var2[var17];
      int var19 = var1[3];
      int var20 = var18 | var19;
      int var21 = var16 ^ var20;
      var1[2] = var21;
      int var22 = var1[3];
      int var23 = var3 + 2;
      int var24 = var2[var23];
      int var25 = var1[2];
      int var26 = leftRotate(var24 & var25, 1);
      int var27 = var22 ^ var26;
      var1[3] = var27;
   }

   private static void decroldq(int var0, int[] var1, int var2, int[] var3, int var4) {
      int var5 = var4 + 2;
      int var6 = var2 + 0;
      int var7 = var1[var6] << var0;
      int var8 = var2 + 1;
      int var9 = var1[var8];
      int var10 = 32 - var0;
      int var11 = var9 >>> var10;
      int var12 = var7 | var11;
      var3[var5] = var12;
      int var13 = var4 + 3;
      int var14 = var2 + 1;
      int var15 = var1[var14] << var0;
      int var16 = var2 + 2;
      int var17 = var1[var16];
      int var18 = 32 - var0;
      int var19 = var17 >>> var18;
      int var20 = var15 | var19;
      var3[var13] = var20;
      int var21 = var4 + 0;
      int var22 = var2 + 2;
      int var23 = var1[var22] << var0;
      int var24 = var2 + 3;
      int var25 = var1[var24];
      int var26 = 32 - var0;
      int var27 = var25 >>> var26;
      int var28 = var23 | var27;
      var3[var21] = var28;
      int var29 = var4 + 1;
      int var30 = var2 + 3;
      int var31 = var1[var30] << var0;
      int var32 = var2 + 0;
      int var33 = var1[var32];
      int var34 = 32 - var0;
      int var35 = var33 >>> var34;
      int var36 = var31 | var35;
      var3[var29] = var36;
      int var37 = var2 + 0;
      int var38 = var4 + 2;
      int var39 = var3[var38];
      var1[var37] = var39;
      int var40 = var2 + 1;
      int var41 = var4 + 3;
      int var42 = var3[var41];
      var1[var40] = var42;
      int var43 = var2 + 2;
      int var44 = var4 + 0;
      int var45 = var3[var44];
      var1[var43] = var45;
      int var46 = var2 + 3;
      int var47 = var4 + 1;
      int var48 = var3[var47];
      var1[var46] = var48;
   }

   private static void decroldqo32(int var0, int[] var1, int var2, int[] var3, int var4) {
      int var5 = var4 + 2;
      int var6 = var2 + 1;
      int var7 = var1[var6];
      int var8 = var0 - 32;
      int var9 = var7 << var8;
      int var10 = var2 + 2;
      int var11 = var1[var10];
      int var12 = 64 - var0;
      int var13 = var11 >>> var12;
      int var14 = var9 | var13;
      var3[var5] = var14;
      int var15 = var4 + 3;
      int var16 = var2 + 2;
      int var17 = var1[var16];
      int var18 = var0 - 32;
      int var19 = var17 << var18;
      int var20 = var2 + 3;
      int var21 = var1[var20];
      int var22 = 64 - var0;
      int var23 = var21 >>> var22;
      int var24 = var19 | var23;
      var3[var15] = var24;
      int var25 = var4 + 0;
      int var26 = var2 + 3;
      int var27 = var1[var26];
      int var28 = var0 - 32;
      int var29 = var27 << var28;
      int var30 = var2 + 0;
      int var31 = var1[var30];
      int var32 = 64 - var0;
      int var33 = var31 >>> var32;
      int var34 = var29 | var33;
      var3[var25] = var34;
      int var35 = var4 + 1;
      int var36 = var2 + 0;
      int var37 = var1[var36];
      int var38 = var0 - 32;
      int var39 = var37 << var38;
      int var40 = var2 + 1;
      int var41 = var1[var40];
      int var42 = 64 - var0;
      int var43 = var41 >>> var42;
      int var44 = var39 | var43;
      var3[var35] = var44;
      int var45 = var2 + 0;
      int var46 = var4 + 2;
      int var47 = var3[var46];
      var1[var45] = var47;
      int var48 = var2 + 1;
      int var49 = var4 + 3;
      int var50 = var3[var49];
      var1[var48] = var50;
      int var51 = var2 + 2;
      int var52 = var4 + 0;
      int var53 = var3[var52];
      var1[var51] = var53;
      int var54 = var2 + 3;
      int var55 = var4 + 1;
      int var56 = var3[var55];
      var1[var54] = var56;
   }

   private void int2bytes(int var1, byte[] var2, int var3) {
      for(int var4 = 0; var4 < 4; ++var4) {
         int var5 = 3 - var4 + var3;
         byte var6 = (byte)var1;
         var2[var5] = var6;
         var1 >>>= 8;
      }

   }

   private static int leftRotate(int var0, int var1) {
      int var2 = var0 << var1;
      int var3 = 32 - var1;
      int var4 = var0 >>> var3;
      return var2 + var4;
   }

   private int processBlock128(byte[] var1, int var2, byte[] var3, int var4) {
      for(int var5 = 0; var5 < 4; ++var5) {
         int[] var6 = this.state;
         int var7 = var5 * 4 + var2;
         int var8 = this.bytes2int(var1, var7);
         var6[var5] = var8;
         int[] var9 = this.state;
         int var10 = var9[var5];
         int var11 = this.kw[var5];
         int var12 = var10 ^ var11;
         var9[var5] = var12;
      }

      int[] var13 = this.state;
      int[] var14 = this.subkey;
      this.camelliaF2(var13, var14, 0);
      int[] var15 = this.state;
      int[] var16 = this.subkey;
      this.camelliaF2(var15, var16, 4);
      int[] var17 = this.state;
      int[] var18 = this.subkey;
      this.camelliaF2(var17, var18, 8);
      int[] var19 = this.state;
      int[] var20 = this.ke;
      this.camelliaFLs(var19, var20, 0);
      int[] var21 = this.state;
      int[] var22 = this.subkey;
      this.camelliaF2(var21, var22, 12);
      int[] var23 = this.state;
      int[] var24 = this.subkey;
      this.camelliaF2(var23, var24, 16);
      int[] var25 = this.state;
      int[] var26 = this.subkey;
      this.camelliaF2(var25, var26, 20);
      int[] var27 = this.state;
      int[] var28 = this.ke;
      this.camelliaFLs(var27, var28, 4);
      int[] var29 = this.state;
      int[] var30 = this.subkey;
      this.camelliaF2(var29, var30, 24);
      int[] var31 = this.state;
      int[] var32 = this.subkey;
      this.camelliaF2(var31, var32, 28);
      int[] var33 = this.state;
      int[] var34 = this.subkey;
      this.camelliaF2(var33, var34, 32);
      int[] var35 = this.state;
      int var36 = var35[2];
      int var37 = this.kw[4];
      int var38 = var36 ^ var37;
      var35[2] = var38;
      int[] var39 = this.state;
      int var40 = var39[3];
      int var41 = this.kw[5];
      int var42 = var40 ^ var41;
      var39[3] = var42;
      int[] var43 = this.state;
      int var44 = var43[0];
      int var45 = this.kw[6];
      int var46 = var44 ^ var45;
      var43[0] = var46;
      int[] var47 = this.state;
      int var48 = var47[1];
      int var49 = this.kw[7];
      int var50 = var48 ^ var49;
      var47[1] = var50;
      int var51 = this.state[2];
      this.int2bytes(var51, var3, var4);
      int var52 = this.state[3];
      int var53 = var4 + 4;
      this.int2bytes(var52, var3, var53);
      int var54 = this.state[0];
      int var55 = var4 + 8;
      this.int2bytes(var54, var3, var55);
      int var56 = this.state[1];
      int var57 = var4 + 12;
      this.int2bytes(var56, var3, var57);
      return 16;
   }

   private int processBlock192or256(byte[] var1, int var2, byte[] var3, int var4) {
      for(int var5 = 0; var5 < 4; ++var5) {
         int[] var6 = this.state;
         int var7 = var5 * 4 + var2;
         int var8 = this.bytes2int(var1, var7);
         var6[var5] = var8;
         int[] var9 = this.state;
         int var10 = var9[var5];
         int var11 = this.kw[var5];
         int var12 = var10 ^ var11;
         var9[var5] = var12;
      }

      int[] var13 = this.state;
      int[] var14 = this.subkey;
      this.camelliaF2(var13, var14, 0);
      int[] var15 = this.state;
      int[] var16 = this.subkey;
      this.camelliaF2(var15, var16, 4);
      int[] var17 = this.state;
      int[] var18 = this.subkey;
      this.camelliaF2(var17, var18, 8);
      int[] var19 = this.state;
      int[] var20 = this.ke;
      this.camelliaFLs(var19, var20, 0);
      int[] var21 = this.state;
      int[] var22 = this.subkey;
      this.camelliaF2(var21, var22, 12);
      int[] var23 = this.state;
      int[] var24 = this.subkey;
      this.camelliaF2(var23, var24, 16);
      int[] var25 = this.state;
      int[] var26 = this.subkey;
      this.camelliaF2(var25, var26, 20);
      int[] var27 = this.state;
      int[] var28 = this.ke;
      this.camelliaFLs(var27, var28, 4);
      int[] var29 = this.state;
      int[] var30 = this.subkey;
      this.camelliaF2(var29, var30, 24);
      int[] var31 = this.state;
      int[] var32 = this.subkey;
      this.camelliaF2(var31, var32, 28);
      int[] var33 = this.state;
      int[] var34 = this.subkey;
      this.camelliaF2(var33, var34, 32);
      int[] var35 = this.state;
      int[] var36 = this.ke;
      this.camelliaFLs(var35, var36, 8);
      int[] var37 = this.state;
      int[] var38 = this.subkey;
      this.camelliaF2(var37, var38, 36);
      int[] var39 = this.state;
      int[] var40 = this.subkey;
      this.camelliaF2(var39, var40, 40);
      int[] var41 = this.state;
      int[] var42 = this.subkey;
      this.camelliaF2(var41, var42, 44);
      int[] var43 = this.state;
      int var44 = var43[2];
      int var45 = this.kw[4];
      int var46 = var44 ^ var45;
      var43[2] = var46;
      int[] var47 = this.state;
      int var48 = var47[3];
      int var49 = this.kw[5];
      int var50 = var48 ^ var49;
      var47[3] = var50;
      int[] var51 = this.state;
      int var52 = var51[0];
      int var53 = this.kw[6];
      int var54 = var52 ^ var53;
      var51[0] = var54;
      int[] var55 = this.state;
      int var56 = var55[1];
      int var57 = this.kw[7];
      int var58 = var56 ^ var57;
      var55[1] = var58;
      int var59 = this.state[2];
      this.int2bytes(var59, var3, var4);
      int var60 = this.state[3];
      int var61 = var4 + 4;
      this.int2bytes(var60, var3, var61);
      int var62 = this.state[0];
      int var63 = var4 + 8;
      this.int2bytes(var62, var3, var63);
      int var64 = this.state[1];
      int var65 = var4 + 12;
      this.int2bytes(var64, var3, var65);
      return 16;
   }

   private static int rightRotate(int var0, int var1) {
      int var2 = var0 >>> var1;
      int var3 = 32 - var1;
      int var4 = var0 << var3;
      return var2 + var4;
   }

   private static void roldq(int var0, int[] var1, int var2, int[] var3, int var4) {
      int var5 = var4 + 0;
      int var6 = var2 + 0;
      int var7 = var1[var6] << var0;
      int var8 = var2 + 1;
      int var9 = var1[var8];
      int var10 = 32 - var0;
      int var11 = var9 >>> var10;
      int var12 = var7 | var11;
      var3[var5] = var12;
      int var13 = var4 + 1;
      int var14 = var2 + 1;
      int var15 = var1[var14] << var0;
      int var16 = var2 + 2;
      int var17 = var1[var16];
      int var18 = 32 - var0;
      int var19 = var17 >>> var18;
      int var20 = var15 | var19;
      var3[var13] = var20;
      int var21 = var4 + 2;
      int var22 = var2 + 2;
      int var23 = var1[var22] << var0;
      int var24 = var2 + 3;
      int var25 = var1[var24];
      int var26 = 32 - var0;
      int var27 = var25 >>> var26;
      int var28 = var23 | var27;
      var3[var21] = var28;
      int var29 = var4 + 3;
      int var30 = var2 + 3;
      int var31 = var1[var30] << var0;
      int var32 = var2 + 0;
      int var33 = var1[var32];
      int var34 = 32 - var0;
      int var35 = var33 >>> var34;
      int var36 = var31 | var35;
      var3[var29] = var36;
      int var37 = var2 + 0;
      int var38 = var4 + 0;
      int var39 = var3[var38];
      var1[var37] = var39;
      int var40 = var2 + 1;
      int var41 = var4 + 1;
      int var42 = var3[var41];
      var1[var40] = var42;
      int var43 = var2 + 2;
      int var44 = var4 + 2;
      int var45 = var3[var44];
      var1[var43] = var45;
      int var46 = var2 + 3;
      int var47 = var4 + 3;
      int var48 = var3[var47];
      var1[var46] = var48;
   }

   private static void roldqo32(int var0, int[] var1, int var2, int[] var3, int var4) {
      int var5 = var4 + 0;
      int var6 = var2 + 1;
      int var7 = var1[var6];
      int var8 = var0 - 32;
      int var9 = var7 << var8;
      int var10 = var2 + 2;
      int var11 = var1[var10];
      int var12 = 64 - var0;
      int var13 = var11 >>> var12;
      int var14 = var9 | var13;
      var3[var5] = var14;
      int var15 = var4 + 1;
      int var16 = var2 + 2;
      int var17 = var1[var16];
      int var18 = var0 - 32;
      int var19 = var17 << var18;
      int var20 = var2 + 3;
      int var21 = var1[var20];
      int var22 = 64 - var0;
      int var23 = var21 >>> var22;
      int var24 = var19 | var23;
      var3[var15] = var24;
      int var25 = var4 + 2;
      int var26 = var2 + 3;
      int var27 = var1[var26];
      int var28 = var0 - 32;
      int var29 = var27 << var28;
      int var30 = var2 + 0;
      int var31 = var1[var30];
      int var32 = 64 - var0;
      int var33 = var31 >>> var32;
      int var34 = var29 | var33;
      var3[var25] = var34;
      int var35 = var4 + 3;
      int var36 = var2 + 0;
      int var37 = var1[var36];
      int var38 = var0 - 32;
      int var39 = var37 << var38;
      int var40 = var2 + 1;
      int var41 = var1[var40];
      int var42 = 64 - var0;
      int var43 = var41 >>> var42;
      int var44 = var39 | var43;
      var3[var35] = var44;
      int var45 = var2 + 0;
      int var46 = var4 + 0;
      int var47 = var3[var46];
      var1[var45] = var47;
      int var48 = var2 + 1;
      int var49 = var4 + 1;
      int var50 = var3[var49];
      var1[var48] = var50;
      int var51 = var2 + 2;
      int var52 = var4 + 2;
      int var53 = var3[var52];
      var1[var51] = var53;
      int var54 = var2 + 3;
      int var55 = var4 + 3;
      int var56 = var3[var55];
      var1[var54] = var56;
   }

   private void setKey(boolean var1, byte[] var2) {
      int[] var3 = new int[8];
      int[] var4 = new int[4];
      int[] var5 = new int[4];
      int[] var6 = new int[4];
      switch(var2.length) {
      case 16:
         this._keyIs128 = (boolean)1;
         int var7 = this.bytes2int(var2, 0);
         var3[0] = var7;
         int var8 = this.bytes2int(var2, 4);
         var3[1] = var8;
         int var9 = this.bytes2int(var2, 8);
         var3[2] = var9;
         int var10 = this.bytes2int(var2, 12);
         var3[3] = var10;
         var3[7] = 0;
         var3[6] = 0;
         var3[5] = 0;
         var3[4] = 0;
         break;
      case 24:
         int var16 = this.bytes2int(var2, 0);
         var3[0] = var16;
         int var17 = this.bytes2int(var2, 4);
         var3[1] = var17;
         int var18 = this.bytes2int(var2, 8);
         var3[2] = var18;
         int var19 = this.bytes2int(var2, 12);
         var3[3] = var19;
         int var20 = this.bytes2int(var2, 16);
         var3[4] = var20;
         int var21 = this.bytes2int(var2, 20);
         var3[5] = var21;
         int var22 = ~var3[4];
         var3[6] = var22;
         int var23 = ~var3[5];
         var3[7] = var23;
         this._keyIs128 = (boolean)0;
         break;
      case 32:
         int var24 = this.bytes2int(var2, 0);
         var3[0] = var24;
         int var25 = this.bytes2int(var2, 4);
         var3[1] = var25;
         int var26 = this.bytes2int(var2, 8);
         var3[2] = var26;
         int var27 = this.bytes2int(var2, 12);
         var3[3] = var27;
         int var28 = this.bytes2int(var2, 16);
         var3[4] = var28;
         int var29 = this.bytes2int(var2, 20);
         var3[5] = var29;
         int var30 = this.bytes2int(var2, 24);
         var3[6] = var30;
         int var31 = this.bytes2int(var2, 28);
         var3[7] = var31;
         this._keyIs128 = (boolean)0;
         break;
      default:
         throw new IllegalArgumentException("key sizes are only 16/24/32 bytes.");
      }

      for(int var11 = 0; var11 < 4; ++var11) {
         int var12 = var3[var11];
         int var13 = var11 + 4;
         int var14 = var3[var13];
         int var15 = var12 ^ var14;
         var4[var11] = var15;
      }

      int[] var32 = SIGMA;
      this.camelliaF2(var4, var32, 0);

      for(int var33 = 0; var33 < 4; ++var33) {
         int var34 = var4[var33];
         int var35 = var3[var33];
         int var36 = var34 ^ var35;
         var4[var33] = var36;
      }

      int[] var37 = SIGMA;
      this.camelliaF2(var4, var37, 4);
      if(this._keyIs128) {
         if(var1) {
            int[] var38 = this.kw;
            int var39 = var3[0];
            var38[0] = var39;
            int[] var40 = this.kw;
            int var41 = var3[1];
            var40[1] = var41;
            int[] var42 = this.kw;
            int var43 = var3[2];
            var42[2] = var43;
            int[] var44 = this.kw;
            int var45 = var3[3];
            var44[3] = var45;
            int[] var46 = this.subkey;
            roldq(15, var3, 0, var46, 4);
            int[] var47 = this.subkey;
            roldq(30, var3, 0, var47, 12);
            roldq(15, var3, 0, var6, 0);
            int[] var48 = this.subkey;
            int var49 = var6[2];
            var48[18] = var49;
            int[] var50 = this.subkey;
            int var51 = var6[3];
            var50[19] = var51;
            int[] var52 = this.ke;
            roldq(17, var3, 0, var52, 4);
            int[] var53 = this.subkey;
            roldq(17, var3, 0, var53, 24);
            int[] var54 = this.subkey;
            roldq(17, var3, 0, var54, 32);
            int[] var55 = this.subkey;
            int var56 = var4[0];
            var55[0] = var56;
            int[] var57 = this.subkey;
            int var58 = var4[1];
            var57[1] = var58;
            int[] var59 = this.subkey;
            int var60 = var4[2];
            var59[2] = var60;
            int[] var61 = this.subkey;
            int var62 = var4[3];
            var61[3] = var62;
            int[] var63 = this.subkey;
            roldq(15, var4, 0, var63, 8);
            int[] var64 = this.ke;
            roldq(15, var4, 0, var64, 0);
            roldq(15, var4, 0, var6, 0);
            int[] var65 = this.subkey;
            int var66 = var6[0];
            var65[16] = var66;
            int[] var67 = this.subkey;
            int var68 = var6[1];
            var67[17] = var68;
            int[] var69 = this.subkey;
            roldq(15, var4, 0, var69, 20);
            int[] var70 = this.subkey;
            roldqo32(34, var4, 0, var70, 28);
            int[] var71 = this.kw;
            roldq(17, var4, 0, var71, 4);
         } else {
            int[] var72 = this.kw;
            int var73 = var3[0];
            var72[4] = var73;
            int[] var74 = this.kw;
            int var75 = var3[1];
            var74[5] = var75;
            int[] var76 = this.kw;
            int var77 = var3[2];
            var76[6] = var77;
            int[] var78 = this.kw;
            int var79 = var3[3];
            var78[7] = var79;
            int[] var80 = this.subkey;
            decroldq(15, var3, 0, var80, 28);
            int[] var81 = this.subkey;
            decroldq(30, var3, 0, var81, 20);
            decroldq(15, var3, 0, var6, 0);
            int[] var82 = this.subkey;
            int var83 = var6[0];
            var82[16] = var83;
            int[] var84 = this.subkey;
            int var85 = var6[1];
            var84[17] = var85;
            int[] var86 = this.ke;
            decroldq(17, var3, 0, var86, 0);
            int[] var87 = this.subkey;
            decroldq(17, var3, 0, var87, 8);
            int[] var88 = this.subkey;
            decroldq(17, var3, 0, var88, 0);
            int[] var89 = this.subkey;
            int var90 = var4[0];
            var89[34] = var90;
            int[] var91 = this.subkey;
            int var92 = var4[1];
            var91[35] = var92;
            int[] var93 = this.subkey;
            int var94 = var4[2];
            var93[32] = var94;
            int[] var95 = this.subkey;
            int var96 = var4[3];
            var95[33] = var96;
            int[] var97 = this.subkey;
            decroldq(15, var4, 0, var97, 24);
            int[] var98 = this.ke;
            decroldq(15, var4, 0, var98, 4);
            decroldq(15, var4, 0, var6, 0);
            int[] var99 = this.subkey;
            int var100 = var6[2];
            var99[18] = var100;
            int[] var101 = this.subkey;
            int var102 = var6[3];
            var101[19] = var102;
            int[] var103 = this.subkey;
            decroldq(15, var4, 0, var103, 12);
            int[] var104 = this.subkey;
            decroldqo32(34, var4, 0, var104, 4);
            int[] var105 = this.kw;
            roldq(17, var4, 0, var105, 0);
         }
      } else {
         int var110;
         for(byte var188 = 0; var188 < 4; var110 = var188 + 1) {
            int var106 = var4[var188];
            int var107 = var188 + 4;
            int var108 = var3[var107];
            int var109 = var106 ^ var108;
            var5[var188] = var109;
         }

         int[] var111 = SIGMA;
         this.camelliaF2(var5, var111, 8);
         if(var1) {
            int[] var112 = this.kw;
            int var113 = var3[0];
            var112[0] = var113;
            int[] var114 = this.kw;
            int var115 = var3[1];
            var114[1] = var115;
            int[] var116 = this.kw;
            int var117 = var3[2];
            var116[2] = var117;
            int[] var118 = this.kw;
            int var119 = var3[3];
            var118[3] = var119;
            int[] var120 = this.subkey;
            roldqo32(45, var3, 0, var120, 16);
            int[] var121 = this.ke;
            roldq(15, var3, 0, var121, 4);
            int[] var122 = this.subkey;
            roldq(17, var3, 0, var122, 32);
            int[] var123 = this.subkey;
            roldqo32(34, var3, 0, var123, 44);
            int[] var124 = this.subkey;
            roldq(15, var3, 4, var124, 4);
            int[] var125 = this.ke;
            roldq(15, var3, 4, var125, 0);
            int[] var126 = this.subkey;
            roldq(30, var3, 4, var126, 24);
            int[] var127 = this.subkey;
            roldqo32(34, var3, 4, var127, 36);
            int[] var128 = this.subkey;
            roldq(15, var4, 0, var128, 8);
            int[] var129 = this.subkey;
            roldq(30, var4, 0, var129, 20);
            int[] var130 = this.ke;
            int var131 = var4[1];
            var130[8] = var131;
            int[] var132 = this.ke;
            int var133 = var4[2];
            var132[9] = var133;
            int[] var134 = this.ke;
            int var135 = var4[3];
            var134[10] = var135;
            int[] var136 = this.ke;
            int var137 = var4[0];
            var136[11] = var137;
            int[] var138 = this.subkey;
            roldqo32(49, var4, 0, var138, 40);
            int[] var139 = this.subkey;
            int var140 = var5[0];
            var139[0] = var140;
            int[] var141 = this.subkey;
            int var142 = var5[1];
            var141[1] = var142;
            int[] var143 = this.subkey;
            int var144 = var5[2];
            var143[2] = var144;
            int[] var145 = this.subkey;
            int var146 = var5[3];
            var145[3] = var146;
            int[] var147 = this.subkey;
            roldq(30, var5, 0, var147, 12);
            int[] var148 = this.subkey;
            roldq(30, var5, 0, var148, 28);
            int[] var149 = this.kw;
            roldqo32(51, var5, 0, var149, 4);
         } else {
            int[] var150 = this.kw;
            int var151 = var3[0];
            var150[4] = var151;
            int[] var152 = this.kw;
            int var153 = var3[1];
            var152[5] = var153;
            int[] var154 = this.kw;
            int var155 = var3[2];
            var154[6] = var155;
            int[] var156 = this.kw;
            int var157 = var3[3];
            var156[7] = var157;
            int[] var158 = this.subkey;
            decroldqo32(45, var3, 0, var158, 28);
            int[] var159 = this.ke;
            decroldq(15, var3, 0, var159, 4);
            int[] var160 = this.subkey;
            decroldq(17, var3, 0, var160, 12);
            int[] var161 = this.subkey;
            decroldqo32(34, var3, 0, var161, 0);
            int[] var162 = this.subkey;
            decroldq(15, var3, 4, var162, 40);
            int[] var163 = this.ke;
            decroldq(15, var3, 4, var163, 8);
            int[] var164 = this.subkey;
            decroldq(30, var3, 4, var164, 20);
            int[] var165 = this.subkey;
            decroldqo32(34, var3, 4, var165, 8);
            int[] var166 = this.subkey;
            decroldq(15, var4, 0, var166, 36);
            int[] var167 = this.subkey;
            decroldq(30, var4, 0, var167, 24);
            int[] var168 = this.ke;
            int var169 = var4[1];
            var168[2] = var169;
            int[] var170 = this.ke;
            int var171 = var4[2];
            var170[3] = var171;
            int[] var172 = this.ke;
            int var173 = var4[3];
            var172[0] = var173;
            int[] var174 = this.ke;
            int var175 = var4[0];
            var174[1] = var175;
            int[] var176 = this.subkey;
            decroldqo32(49, var4, 0, var176, 4);
            int[] var177 = this.subkey;
            int var178 = var5[0];
            var177[46] = var178;
            int[] var179 = this.subkey;
            int var180 = var5[1];
            var179[47] = var180;
            int[] var181 = this.subkey;
            int var182 = var5[2];
            var181[44] = var182;
            int[] var183 = this.subkey;
            int var184 = var5[3];
            var183[45] = var184;
            int[] var185 = this.subkey;
            decroldq(30, var5, 0, var185, 32);
            int[] var186 = this.subkey;
            decroldq(30, var5, 0, var186, 16);
            int[] var187 = this.kw;
            roldqo32(51, var5, 0, var187, 0);
         }
      }
   }

   public String getAlgorithmName() {
      return "Camellia";
   }

   public int getBlockSize() {
      return 16;
   }

   public void init(boolean var1, CipherParameters var2) throws IllegalArgumentException {
      if(!(var2 instanceof KeyParameter)) {
         throw new IllegalArgumentException("only simple KeyParameter expected.");
      } else {
         byte[] var3 = ((KeyParameter)var2).getKey();
         this.setKey(var1, var3);
         this.initialised = (boolean)1;
      }
   }

   public int processBlock(byte[] var1, int var2, byte[] var3, int var4) throws DataLengthException, IllegalStateException {
      if(!this.initialised) {
         throw new IllegalStateException("Camellia engine not initialised");
      } else {
         int var5 = var2 + 16;
         int var6 = var1.length;
         if(var5 > var6) {
            throw new DataLengthException("input buffer too short");
         } else {
            int var7 = var4 + 16;
            int var8 = var3.length;
            if(var7 > var8) {
               throw new DataLengthException("output buffer too short");
            } else {
               int var9;
               if(this._keyIs128) {
                  var9 = this.processBlock128(var1, var2, var3, var4);
               } else {
                  var9 = this.processBlock192or256(var1, var2, var3, var4);
               }

               return var9;
            }
         }
      }
   }

   public void reset() {}
}
