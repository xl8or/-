package com.android.email.mail;

import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;

public class Snippet {

   static final Map<String, Character> ESCAPE_STRINGS;
   private static final int MAX_PLAIN_TEXT_SCAN_LENGTH = 200;
   static final char NON_BREAKING_SPACE_CHARACTER = '\u00a0';
   static final String[] STRIP_TAGS;
   static final int STRIP_TAG_LENGTH = 6;


   static {
      String[] var0 = new String[]{"title", "script", "style", "applet", "head"};
      STRIP_TAGS = var0;
      ESCAPE_STRINGS = new HashMap(252);
      Map var1 = ESCAPE_STRINGS;
      Character var2 = Character.valueOf('\u00a0');
      var1.put("&nbsp", var2);
      Map var4 = ESCAPE_STRINGS;
      Character var5 = Character.valueOf('\u00a1');
      var4.put("&iexcl", var5);
      Map var7 = ESCAPE_STRINGS;
      Character var8 = Character.valueOf('\u00a2');
      var7.put("&cent", var8);
      Map var10 = ESCAPE_STRINGS;
      Character var11 = Character.valueOf('\u00a3');
      var10.put("&pound", var11);
      Map var13 = ESCAPE_STRINGS;
      Character var14 = Character.valueOf('\u00a4');
      var13.put("&curren", var14);
      Map var16 = ESCAPE_STRINGS;
      Character var17 = Character.valueOf('\u00a5');
      var16.put("&yen", var17);
      Map var19 = ESCAPE_STRINGS;
      Character var20 = Character.valueOf('\u00a6');
      var19.put("&brvbar", var20);
      Map var22 = ESCAPE_STRINGS;
      Character var23 = Character.valueOf('\u00a7');
      var22.put("&sect", var23);
      Map var25 = ESCAPE_STRINGS;
      Character var26 = Character.valueOf('\u00a8');
      var25.put("&uml", var26);
      Map var28 = ESCAPE_STRINGS;
      Character var29 = Character.valueOf('\u00a9');
      var28.put("&copy", var29);
      Map var31 = ESCAPE_STRINGS;
      Character var32 = Character.valueOf('\u00aa');
      var31.put("&ordf", var32);
      Map var34 = ESCAPE_STRINGS;
      Character var35 = Character.valueOf('\u00ab');
      var34.put("&laquo", var35);
      Map var37 = ESCAPE_STRINGS;
      Character var38 = Character.valueOf('\u00ac');
      var37.put("&not", var38);
      Map var40 = ESCAPE_STRINGS;
      Character var41 = Character.valueOf('\u00ad');
      var40.put("&shy", var41);
      Map var43 = ESCAPE_STRINGS;
      Character var44 = Character.valueOf('\u00ae');
      var43.put("&reg", var44);
      Map var46 = ESCAPE_STRINGS;
      Character var47 = Character.valueOf('\u00af');
      var46.put("&macr", var47);
      Map var49 = ESCAPE_STRINGS;
      Character var50 = Character.valueOf('\u00b0');
      var49.put("&deg", var50);
      Map var52 = ESCAPE_STRINGS;
      Character var53 = Character.valueOf('\u00b1');
      var52.put("&plusmn", var53);
      Map var55 = ESCAPE_STRINGS;
      Character var56 = Character.valueOf('\u00b2');
      var55.put("&sup2", var56);
      Map var58 = ESCAPE_STRINGS;
      Character var59 = Character.valueOf('\u00b3');
      var58.put("&sup3", var59);
      Map var61 = ESCAPE_STRINGS;
      Character var62 = Character.valueOf('\u00b4');
      var61.put("&acute", var62);
      Map var64 = ESCAPE_STRINGS;
      Character var65 = Character.valueOf('\u00b5');
      var64.put("&micro", var65);
      Map var67 = ESCAPE_STRINGS;
      Character var68 = Character.valueOf('\u00b6');
      var67.put("&para", var68);
      Map var70 = ESCAPE_STRINGS;
      Character var71 = Character.valueOf('\u00b7');
      var70.put("&middot", var71);
      Map var73 = ESCAPE_STRINGS;
      Character var74 = Character.valueOf('\u00b8');
      var73.put("&cedil", var74);
      Map var76 = ESCAPE_STRINGS;
      Character var77 = Character.valueOf('\u00b9');
      var76.put("&sup1", var77);
      Map var79 = ESCAPE_STRINGS;
      Character var80 = Character.valueOf('\u00ba');
      var79.put("&ordm", var80);
      Map var82 = ESCAPE_STRINGS;
      Character var83 = Character.valueOf('\u00bb');
      var82.put("&raquo", var83);
      Map var85 = ESCAPE_STRINGS;
      Character var86 = Character.valueOf('\u00bc');
      var85.put("&frac14", var86);
      Map var88 = ESCAPE_STRINGS;
      Character var89 = Character.valueOf('\u00bd');
      var88.put("&frac12", var89);
      Map var91 = ESCAPE_STRINGS;
      Character var92 = Character.valueOf('\u00be');
      var91.put("&frac34", var92);
      Map var94 = ESCAPE_STRINGS;
      Character var95 = Character.valueOf('\u00bf');
      var94.put("&iquest", var95);
      Map var97 = ESCAPE_STRINGS;
      Character var98 = Character.valueOf('\u00c0');
      var97.put("&Agrave", var98);
      Map var100 = ESCAPE_STRINGS;
      Character var101 = Character.valueOf('\u00c1');
      var100.put("&Aacute", var101);
      Map var103 = ESCAPE_STRINGS;
      Character var104 = Character.valueOf('\u00c2');
      var103.put("&Acirc", var104);
      Map var106 = ESCAPE_STRINGS;
      Character var107 = Character.valueOf('\u00c3');
      var106.put("&Atilde", var107);
      Map var109 = ESCAPE_STRINGS;
      Character var110 = Character.valueOf('\u00c4');
      var109.put("&Auml", var110);
      Map var112 = ESCAPE_STRINGS;
      Character var113 = Character.valueOf('\u00c5');
      var112.put("&Aring", var113);
      Map var115 = ESCAPE_STRINGS;
      Character var116 = Character.valueOf('\u00c6');
      var115.put("&AElig", var116);
      Map var118 = ESCAPE_STRINGS;
      Character var119 = Character.valueOf('\u00c7');
      var118.put("&Ccedil", var119);
      Map var121 = ESCAPE_STRINGS;
      Character var122 = Character.valueOf('\u00c8');
      var121.put("&Egrave", var122);
      Map var124 = ESCAPE_STRINGS;
      Character var125 = Character.valueOf('\u00c9');
      var124.put("&Eacute", var125);
      Map var127 = ESCAPE_STRINGS;
      Character var128 = Character.valueOf('\u00ca');
      var127.put("&Ecirc", var128);
      Map var130 = ESCAPE_STRINGS;
      Character var131 = Character.valueOf('\u00cb');
      var130.put("&Euml", var131);
      Map var133 = ESCAPE_STRINGS;
      Character var134 = Character.valueOf('\u00cc');
      var133.put("&Igrave", var134);
      Map var136 = ESCAPE_STRINGS;
      Character var137 = Character.valueOf('\u00cd');
      var136.put("&Iacute", var137);
      Map var139 = ESCAPE_STRINGS;
      Character var140 = Character.valueOf('\u00ce');
      var139.put("&Icirc", var140);
      Map var142 = ESCAPE_STRINGS;
      Character var143 = Character.valueOf('\u00cf');
      var142.put("&Iuml", var143);
      Map var145 = ESCAPE_STRINGS;
      Character var146 = Character.valueOf('\u00d0');
      var145.put("&ETH", var146);
      Map var148 = ESCAPE_STRINGS;
      Character var149 = Character.valueOf('\u00d1');
      var148.put("&Ntilde", var149);
      Map var151 = ESCAPE_STRINGS;
      Character var152 = Character.valueOf('\u00d2');
      var151.put("&Ograve", var152);
      Map var154 = ESCAPE_STRINGS;
      Character var155 = Character.valueOf('\u00d3');
      var154.put("&Oacute", var155);
      Map var157 = ESCAPE_STRINGS;
      Character var158 = Character.valueOf('\u00d4');
      var157.put("&Ocirc", var158);
      Map var160 = ESCAPE_STRINGS;
      Character var161 = Character.valueOf('\u00d5');
      var160.put("&Otilde", var161);
      Map var163 = ESCAPE_STRINGS;
      Character var164 = Character.valueOf('\u00d6');
      var163.put("&Ouml", var164);
      Map var166 = ESCAPE_STRINGS;
      Character var167 = Character.valueOf('\u00d7');
      var166.put("&times", var167);
      Map var169 = ESCAPE_STRINGS;
      Character var170 = Character.valueOf('\u00d8');
      var169.put("&Oslash", var170);
      Map var172 = ESCAPE_STRINGS;
      Character var173 = Character.valueOf('\u00d9');
      var172.put("&Ugrave", var173);
      Map var175 = ESCAPE_STRINGS;
      Character var176 = Character.valueOf('\u00da');
      var175.put("&Uacute", var176);
      Map var178 = ESCAPE_STRINGS;
      Character var179 = Character.valueOf('\u00db');
      var178.put("&Ucirc", var179);
      Map var181 = ESCAPE_STRINGS;
      Character var182 = Character.valueOf('\u00dc');
      var181.put("&Uuml", var182);
      Map var184 = ESCAPE_STRINGS;
      Character var185 = Character.valueOf('\u00dd');
      var184.put("&Yacute", var185);
      Map var187 = ESCAPE_STRINGS;
      Character var188 = Character.valueOf('\u00de');
      var187.put("&THORN", var188);
      Map var190 = ESCAPE_STRINGS;
      Character var191 = Character.valueOf('\u00df');
      var190.put("&szlig", var191);
      Map var193 = ESCAPE_STRINGS;
      Character var194 = Character.valueOf('\u00e0');
      var193.put("&agrave", var194);
      Map var196 = ESCAPE_STRINGS;
      Character var197 = Character.valueOf('\u00e1');
      var196.put("&aacute", var197);
      Map var199 = ESCAPE_STRINGS;
      Character var200 = Character.valueOf('\u00e2');
      var199.put("&acirc", var200);
      Map var202 = ESCAPE_STRINGS;
      Character var203 = Character.valueOf('\u00e3');
      var202.put("&atilde", var203);
      Map var205 = ESCAPE_STRINGS;
      Character var206 = Character.valueOf('\u00e4');
      var205.put("&auml", var206);
      Map var208 = ESCAPE_STRINGS;
      Character var209 = Character.valueOf('\u00e5');
      var208.put("&aring", var209);
      Map var211 = ESCAPE_STRINGS;
      Character var212 = Character.valueOf('\u00e6');
      var211.put("&aelig", var212);
      Map var214 = ESCAPE_STRINGS;
      Character var215 = Character.valueOf('\u00e7');
      var214.put("&ccedil", var215);
      Map var217 = ESCAPE_STRINGS;
      Character var218 = Character.valueOf('\u00e8');
      var217.put("&egrave", var218);
      Map var220 = ESCAPE_STRINGS;
      Character var221 = Character.valueOf('\u00e9');
      var220.put("&eacute", var221);
      Map var223 = ESCAPE_STRINGS;
      Character var224 = Character.valueOf('\u00ea');
      var223.put("&ecirc", var224);
      Map var226 = ESCAPE_STRINGS;
      Character var227 = Character.valueOf('\u00eb');
      var226.put("&euml", var227);
      Map var229 = ESCAPE_STRINGS;
      Character var230 = Character.valueOf('\u00ec');
      var229.put("&igrave", var230);
      Map var232 = ESCAPE_STRINGS;
      Character var233 = Character.valueOf('\u00ed');
      var232.put("&iacute", var233);
      Map var235 = ESCAPE_STRINGS;
      Character var236 = Character.valueOf('\u00ee');
      var235.put("&icirc", var236);
      Map var238 = ESCAPE_STRINGS;
      Character var239 = Character.valueOf('\u00ef');
      var238.put("&iuml", var239);
      Map var241 = ESCAPE_STRINGS;
      Character var242 = Character.valueOf('\u00f0');
      var241.put("&eth", var242);
      Map var244 = ESCAPE_STRINGS;
      Character var245 = Character.valueOf('\u00f1');
      var244.put("&ntilde", var245);
      Map var247 = ESCAPE_STRINGS;
      Character var248 = Character.valueOf('\u00f2');
      var247.put("&ograve", var248);
      Map var250 = ESCAPE_STRINGS;
      Character var251 = Character.valueOf('\u00f3');
      var250.put("&oacute", var251);
      Map var253 = ESCAPE_STRINGS;
      Character var254 = Character.valueOf('\u00f4');
      var253.put("&ocirc", var254);
      Map var256 = ESCAPE_STRINGS;
      Character var257 = Character.valueOf('\u00f5');
      var256.put("&otilde", var257);
      Map var259 = ESCAPE_STRINGS;
      Character var260 = Character.valueOf('\u00f6');
      var259.put("&ouml", var260);
      Map var262 = ESCAPE_STRINGS;
      Character var263 = Character.valueOf('\u00f7');
      var262.put("&divide", var263);
      Map var265 = ESCAPE_STRINGS;
      Character var266 = Character.valueOf('\u00f8');
      var265.put("&oslash", var266);
      Map var268 = ESCAPE_STRINGS;
      Character var269 = Character.valueOf('\u00f9');
      var268.put("&ugrave", var269);
      Map var271 = ESCAPE_STRINGS;
      Character var272 = Character.valueOf('\u00fa');
      var271.put("&uacute", var272);
      Map var274 = ESCAPE_STRINGS;
      Character var275 = Character.valueOf('\u00fb');
      var274.put("&ucirc", var275);
      Map var277 = ESCAPE_STRINGS;
      Character var278 = Character.valueOf('\u00fc');
      var277.put("&uuml", var278);
      Map var280 = ESCAPE_STRINGS;
      Character var281 = Character.valueOf('\u00fd');
      var280.put("&yacute", var281);
      Map var283 = ESCAPE_STRINGS;
      Character var284 = Character.valueOf('\u00fe');
      var283.put("&thorn", var284);
      Map var286 = ESCAPE_STRINGS;
      Character var287 = Character.valueOf('\u00ff');
      var286.put("&yuml", var287);
      Map var289 = ESCAPE_STRINGS;
      Character var290 = Character.valueOf('\u0192');
      var289.put("&fnof", var290);
      Map var292 = ESCAPE_STRINGS;
      Character var293 = Character.valueOf('\u0391');
      var292.put("&Alpha", var293);
      Map var295 = ESCAPE_STRINGS;
      Character var296 = Character.valueOf('\u0392');
      var295.put("&Beta", var296);
      Map var298 = ESCAPE_STRINGS;
      Character var299 = Character.valueOf('\u0393');
      var298.put("&Gamma", var299);
      Map var301 = ESCAPE_STRINGS;
      Character var302 = Character.valueOf('\u0394');
      var301.put("&Delta", var302);
      Map var304 = ESCAPE_STRINGS;
      Character var305 = Character.valueOf('\u0395');
      var304.put("&Epsilon", var305);
      Map var307 = ESCAPE_STRINGS;
      Character var308 = Character.valueOf('\u0396');
      var307.put("&Zeta", var308);
      Map var310 = ESCAPE_STRINGS;
      Character var311 = Character.valueOf('\u0397');
      var310.put("&Eta", var311);
      Map var313 = ESCAPE_STRINGS;
      Character var314 = Character.valueOf('\u0398');
      var313.put("&Theta", var314);
      Map var316 = ESCAPE_STRINGS;
      Character var317 = Character.valueOf('\u0399');
      var316.put("&Iota", var317);
      Map var319 = ESCAPE_STRINGS;
      Character var320 = Character.valueOf('\u039a');
      var319.put("&Kappa", var320);
      Map var322 = ESCAPE_STRINGS;
      Character var323 = Character.valueOf('\u039b');
      var322.put("&Lambda", var323);
      Map var325 = ESCAPE_STRINGS;
      Character var326 = Character.valueOf('\u039c');
      var325.put("&Mu", var326);
      Map var328 = ESCAPE_STRINGS;
      Character var329 = Character.valueOf('\u039d');
      var328.put("&Nu", var329);
      Map var331 = ESCAPE_STRINGS;
      Character var332 = Character.valueOf('\u039e');
      var331.put("&Xi", var332);
      Map var334 = ESCAPE_STRINGS;
      Character var335 = Character.valueOf('\u039f');
      var334.put("&Omicron", var335);
      Map var337 = ESCAPE_STRINGS;
      Character var338 = Character.valueOf('\u03a0');
      var337.put("&Pi", var338);
      Map var340 = ESCAPE_STRINGS;
      Character var341 = Character.valueOf('\u03a1');
      var340.put("&Rho", var341);
      Map var343 = ESCAPE_STRINGS;
      Character var344 = Character.valueOf('\u03a3');
      var343.put("&Sigma", var344);
      Map var346 = ESCAPE_STRINGS;
      Character var347 = Character.valueOf('\u03a4');
      var346.put("&Tau", var347);
      Map var349 = ESCAPE_STRINGS;
      Character var350 = Character.valueOf('\u03a5');
      var349.put("&Upsilon", var350);
      Map var352 = ESCAPE_STRINGS;
      Character var353 = Character.valueOf('\u03a6');
      var352.put("&Phi", var353);
      Map var355 = ESCAPE_STRINGS;
      Character var356 = Character.valueOf('\u03a7');
      var355.put("&Chi", var356);
      Map var358 = ESCAPE_STRINGS;
      Character var359 = Character.valueOf('\u03a8');
      var358.put("&Psi", var359);
      Map var361 = ESCAPE_STRINGS;
      Character var362 = Character.valueOf('\u03a9');
      var361.put("&Omega", var362);
      Map var364 = ESCAPE_STRINGS;
      Character var365 = Character.valueOf('\u03b1');
      var364.put("&alpha", var365);
      Map var367 = ESCAPE_STRINGS;
      Character var368 = Character.valueOf('\u03b2');
      var367.put("&beta", var368);
      Map var370 = ESCAPE_STRINGS;
      Character var371 = Character.valueOf('\u03b3');
      var370.put("&gamma", var371);
      Map var373 = ESCAPE_STRINGS;
      Character var374 = Character.valueOf('\u03b4');
      var373.put("&delta", var374);
      Map var376 = ESCAPE_STRINGS;
      Character var377 = Character.valueOf('\u03b5');
      var376.put("&epsilon", var377);
      Map var379 = ESCAPE_STRINGS;
      Character var380 = Character.valueOf('\u03b6');
      var379.put("&zeta", var380);
      Map var382 = ESCAPE_STRINGS;
      Character var383 = Character.valueOf('\u03b7');
      var382.put("&eta", var383);
      Map var385 = ESCAPE_STRINGS;
      Character var386 = Character.valueOf('\u03b8');
      var385.put("&theta", var386);
      Map var388 = ESCAPE_STRINGS;
      Character var389 = Character.valueOf('\u03b9');
      var388.put("&iota", var389);
      Map var391 = ESCAPE_STRINGS;
      Character var392 = Character.valueOf('\u03ba');
      var391.put("&kappa", var392);
      Map var394 = ESCAPE_STRINGS;
      Character var395 = Character.valueOf('\u03bb');
      var394.put("&lambda", var395);
      Map var397 = ESCAPE_STRINGS;
      Character var398 = Character.valueOf('\u03bc');
      var397.put("&mu", var398);
      Map var400 = ESCAPE_STRINGS;
      Character var401 = Character.valueOf('\u03bd');
      var400.put("&nu", var401);
      Map var403 = ESCAPE_STRINGS;
      Character var404 = Character.valueOf('\u03be');
      var403.put("&xi", var404);
      Map var406 = ESCAPE_STRINGS;
      Character var407 = Character.valueOf('\u03bf');
      var406.put("&omicron", var407);
      Map var409 = ESCAPE_STRINGS;
      Character var410 = Character.valueOf('\u03c0');
      var409.put("&pi", var410);
      Map var412 = ESCAPE_STRINGS;
      Character var413 = Character.valueOf('\u03c1');
      var412.put("&rho", var413);
      Map var415 = ESCAPE_STRINGS;
      Character var416 = Character.valueOf('\u03c2');
      var415.put("&sigmaf", var416);
      Map var418 = ESCAPE_STRINGS;
      Character var419 = Character.valueOf('\u03c3');
      var418.put("&sigma", var419);
      Map var421 = ESCAPE_STRINGS;
      Character var422 = Character.valueOf('\u03c4');
      var421.put("&tau", var422);
      Map var424 = ESCAPE_STRINGS;
      Character var425 = Character.valueOf('\u03c5');
      var424.put("&upsilon", var425);
      Map var427 = ESCAPE_STRINGS;
      Character var428 = Character.valueOf('\u03c6');
      var427.put("&phi", var428);
      Map var430 = ESCAPE_STRINGS;
      Character var431 = Character.valueOf('\u03c7');
      var430.put("&chi", var431);
      Map var433 = ESCAPE_STRINGS;
      Character var434 = Character.valueOf('\u03c8');
      var433.put("&psi", var434);
      Map var436 = ESCAPE_STRINGS;
      Character var437 = Character.valueOf('\u03c9');
      var436.put("&omega", var437);
      Map var439 = ESCAPE_STRINGS;
      Character var440 = Character.valueOf('\u03d1');
      var439.put("&thetasym", var440);
      Map var442 = ESCAPE_STRINGS;
      Character var443 = Character.valueOf('\u03d2');
      var442.put("&upsih", var443);
      Map var445 = ESCAPE_STRINGS;
      Character var446 = Character.valueOf('\u03d6');
      var445.put("&piv", var446);
      Map var448 = ESCAPE_STRINGS;
      Character var449 = Character.valueOf('\u2022');
      var448.put("&bull", var449);
      Map var451 = ESCAPE_STRINGS;
      Character var452 = Character.valueOf('\u2026');
      var451.put("&hellip", var452);
      Map var454 = ESCAPE_STRINGS;
      Character var455 = Character.valueOf('\u2032');
      var454.put("&prime", var455);
      Map var457 = ESCAPE_STRINGS;
      Character var458 = Character.valueOf('\u2033');
      var457.put("&Prime", var458);
      Map var460 = ESCAPE_STRINGS;
      Character var461 = Character.valueOf('\u203e');
      var460.put("&oline", var461);
      Map var463 = ESCAPE_STRINGS;
      Character var464 = Character.valueOf('\u2044');
      var463.put("&frasl", var464);
      Map var466 = ESCAPE_STRINGS;
      Character var467 = Character.valueOf('\u2118');
      var466.put("&weierp", var467);
      Map var469 = ESCAPE_STRINGS;
      Character var470 = Character.valueOf('\u2111');
      var469.put("&image", var470);
      Map var472 = ESCAPE_STRINGS;
      Character var473 = Character.valueOf('\u211c');
      var472.put("&real", var473);
      Map var475 = ESCAPE_STRINGS;
      Character var476 = Character.valueOf('\u2122');
      var475.put("&trade", var476);
      Map var478 = ESCAPE_STRINGS;
      Character var479 = Character.valueOf('\u2135');
      var478.put("&alefsym", var479);
      Map var481 = ESCAPE_STRINGS;
      Character var482 = Character.valueOf('\u2190');
      var481.put("&larr", var482);
      Map var484 = ESCAPE_STRINGS;
      Character var485 = Character.valueOf('\u2191');
      var484.put("&uarr", var485);
      Map var487 = ESCAPE_STRINGS;
      Character var488 = Character.valueOf('\u2192');
      var487.put("&rarr", var488);
      Map var490 = ESCAPE_STRINGS;
      Character var491 = Character.valueOf('\u2193');
      var490.put("&darr", var491);
      Map var493 = ESCAPE_STRINGS;
      Character var494 = Character.valueOf('\u2194');
      var493.put("&harr", var494);
      Map var496 = ESCAPE_STRINGS;
      Character var497 = Character.valueOf('\u21b5');
      var496.put("&crarr", var497);
      Map var499 = ESCAPE_STRINGS;
      Character var500 = Character.valueOf('\u21d0');
      var499.put("&lArr", var500);
      Map var502 = ESCAPE_STRINGS;
      Character var503 = Character.valueOf('\u21d1');
      var502.put("&uArr", var503);
      Map var505 = ESCAPE_STRINGS;
      Character var506 = Character.valueOf('\u21d2');
      var505.put("&rArr", var506);
      Map var508 = ESCAPE_STRINGS;
      Character var509 = Character.valueOf('\u21d3');
      var508.put("&dArr", var509);
      Map var511 = ESCAPE_STRINGS;
      Character var512 = Character.valueOf('\u21d4');
      var511.put("&hArr", var512);
      Map var514 = ESCAPE_STRINGS;
      Character var515 = Character.valueOf('\u2200');
      var514.put("&forall", var515);
      Map var517 = ESCAPE_STRINGS;
      Character var518 = Character.valueOf('\u2202');
      var517.put("&part", var518);
      Map var520 = ESCAPE_STRINGS;
      Character var521 = Character.valueOf('\u2203');
      var520.put("&exist", var521);
      Map var523 = ESCAPE_STRINGS;
      Character var524 = Character.valueOf('\u2205');
      var523.put("&empty", var524);
      Map var526 = ESCAPE_STRINGS;
      Character var527 = Character.valueOf('\u2207');
      var526.put("&nabla", var527);
      Map var529 = ESCAPE_STRINGS;
      Character var530 = Character.valueOf('\u2208');
      var529.put("&isin", var530);
      Map var532 = ESCAPE_STRINGS;
      Character var533 = Character.valueOf('\u2209');
      var532.put("&notin", var533);
      Map var535 = ESCAPE_STRINGS;
      Character var536 = Character.valueOf('\u220b');
      var535.put("&ni", var536);
      Map var538 = ESCAPE_STRINGS;
      Character var539 = Character.valueOf('\u220f');
      var538.put("&prod", var539);
      Map var541 = ESCAPE_STRINGS;
      Character var542 = Character.valueOf('\u2211');
      var541.put("&sum", var542);
      Map var544 = ESCAPE_STRINGS;
      Character var545 = Character.valueOf('\u2212');
      var544.put("&minus", var545);
      Map var547 = ESCAPE_STRINGS;
      Character var548 = Character.valueOf('\u2217');
      var547.put("&lowast", var548);
      Map var550 = ESCAPE_STRINGS;
      Character var551 = Character.valueOf('\u221a');
      var550.put("&radic", var551);
      Map var553 = ESCAPE_STRINGS;
      Character var554 = Character.valueOf('\u221d');
      var553.put("&prop", var554);
      Map var556 = ESCAPE_STRINGS;
      Character var557 = Character.valueOf('\u221e');
      var556.put("&infin", var557);
      Map var559 = ESCAPE_STRINGS;
      Character var560 = Character.valueOf('\u2220');
      var559.put("&ang", var560);
      Map var562 = ESCAPE_STRINGS;
      Character var563 = Character.valueOf('\u2227');
      var562.put("&and", var563);
      Map var565 = ESCAPE_STRINGS;
      Character var566 = Character.valueOf('\u2228');
      var565.put("&or", var566);
      Map var568 = ESCAPE_STRINGS;
      Character var569 = Character.valueOf('\u2229');
      var568.put("&cap", var569);
      Map var571 = ESCAPE_STRINGS;
      Character var572 = Character.valueOf('\u222a');
      var571.put("&cup", var572);
      Map var574 = ESCAPE_STRINGS;
      Character var575 = Character.valueOf('\u222b');
      var574.put("&int", var575);
      Map var577 = ESCAPE_STRINGS;
      Character var578 = Character.valueOf('\u2234');
      var577.put("&there4", var578);
      Map var580 = ESCAPE_STRINGS;
      Character var581 = Character.valueOf('\u223c');
      var580.put("&sim", var581);
      Map var583 = ESCAPE_STRINGS;
      Character var584 = Character.valueOf('\u2245');
      var583.put("&cong", var584);
      Map var586 = ESCAPE_STRINGS;
      Character var587 = Character.valueOf('\u2248');
      var586.put("&asymp", var587);
      Map var589 = ESCAPE_STRINGS;
      Character var590 = Character.valueOf('\u2260');
      var589.put("&ne", var590);
      Map var592 = ESCAPE_STRINGS;
      Character var593 = Character.valueOf('\u2261');
      var592.put("&equiv", var593);
      Map var595 = ESCAPE_STRINGS;
      Character var596 = Character.valueOf('\u2264');
      var595.put("&le", var596);
      Map var598 = ESCAPE_STRINGS;
      Character var599 = Character.valueOf('\u2265');
      var598.put("&ge", var599);
      Map var601 = ESCAPE_STRINGS;
      Character var602 = Character.valueOf('\u2282');
      var601.put("&sub", var602);
      Map var604 = ESCAPE_STRINGS;
      Character var605 = Character.valueOf('\u2283');
      var604.put("&sup", var605);
      Map var607 = ESCAPE_STRINGS;
      Character var608 = Character.valueOf('\u2284');
      var607.put("&nsub", var608);
      Map var610 = ESCAPE_STRINGS;
      Character var611 = Character.valueOf('\u2286');
      var610.put("&sube", var611);
      Map var613 = ESCAPE_STRINGS;
      Character var614 = Character.valueOf('\u2287');
      var613.put("&supe", var614);
      Map var616 = ESCAPE_STRINGS;
      Character var617 = Character.valueOf('\u2295');
      var616.put("&oplus", var617);
      Map var619 = ESCAPE_STRINGS;
      Character var620 = Character.valueOf('\u2297');
      var619.put("&otimes", var620);
      Map var622 = ESCAPE_STRINGS;
      Character var623 = Character.valueOf('\u22a5');
      var622.put("&perp", var623);
      Map var625 = ESCAPE_STRINGS;
      Character var626 = Character.valueOf('\u22c5');
      var625.put("&sdot", var626);
      Map var628 = ESCAPE_STRINGS;
      Character var629 = Character.valueOf('\u2308');
      var628.put("&lceil", var629);
      Map var631 = ESCAPE_STRINGS;
      Character var632 = Character.valueOf('\u2309');
      var631.put("&rceil", var632);
      Map var634 = ESCAPE_STRINGS;
      Character var635 = Character.valueOf('\u230a');
      var634.put("&lfloor", var635);
      Map var637 = ESCAPE_STRINGS;
      Character var638 = Character.valueOf('\u230b');
      var637.put("&rfloor", var638);
      Map var640 = ESCAPE_STRINGS;
      Character var641 = Character.valueOf('\u2329');
      var640.put("&lang", var641);
      Map var643 = ESCAPE_STRINGS;
      Character var644 = Character.valueOf('\u232a');
      var643.put("&rang", var644);
      Map var646 = ESCAPE_STRINGS;
      Character var647 = Character.valueOf('\u25ca');
      var646.put("&loz", var647);
      Map var649 = ESCAPE_STRINGS;
      Character var650 = Character.valueOf('\u2660');
      var649.put("&spades", var650);
      Map var652 = ESCAPE_STRINGS;
      Character var653 = Character.valueOf('\u2663');
      var652.put("&clubs", var653);
      Map var655 = ESCAPE_STRINGS;
      Character var656 = Character.valueOf('\u2665');
      var655.put("&hearts", var656);
      Map var658 = ESCAPE_STRINGS;
      Character var659 = Character.valueOf('\u2666');
      var658.put("&diams", var659);
      Map var661 = ESCAPE_STRINGS;
      Character var662 = Character.valueOf('\"');
      var661.put("&quot", var662);
      Map var664 = ESCAPE_STRINGS;
      Character var665 = Character.valueOf('&');
      var664.put("&amp", var665);
      Map var667 = ESCAPE_STRINGS;
      Character var668 = Character.valueOf('<');
      var667.put("&lt", var668);
      Map var670 = ESCAPE_STRINGS;
      Character var671 = Character.valueOf('>');
      var670.put("&gt", var671);
      Map var673 = ESCAPE_STRINGS;
      Character var674 = Character.valueOf('\u0152');
      var673.put("&OElig", var674);
      Map var676 = ESCAPE_STRINGS;
      Character var677 = Character.valueOf('\u0153');
      var676.put("&oelig", var677);
      Map var679 = ESCAPE_STRINGS;
      Character var680 = Character.valueOf('\u0160');
      var679.put("&Scaron", var680);
      Map var682 = ESCAPE_STRINGS;
      Character var683 = Character.valueOf('\u0161');
      var682.put("&scaron", var683);
      Map var685 = ESCAPE_STRINGS;
      Character var686 = Character.valueOf('\u0178');
      var685.put("&Yuml", var686);
      Map var688 = ESCAPE_STRINGS;
      Character var689 = Character.valueOf('\u02c6');
      var688.put("&circ", var689);
      Map var691 = ESCAPE_STRINGS;
      Character var692 = Character.valueOf('\u02dc');
      var691.put("&tilde", var692);
      Map var694 = ESCAPE_STRINGS;
      Character var695 = Character.valueOf('\u2002');
      var694.put("&ensp", var695);
      Map var697 = ESCAPE_STRINGS;
      Character var698 = Character.valueOf('\u2003');
      var697.put("&emsp", var698);
      Map var700 = ESCAPE_STRINGS;
      Character var701 = Character.valueOf('\u2009');
      var700.put("&thinsp", var701);
      Map var703 = ESCAPE_STRINGS;
      Character var704 = Character.valueOf('\u200c');
      var703.put("&zwnj", var704);
      Map var706 = ESCAPE_STRINGS;
      Character var707 = Character.valueOf('\u200d');
      var706.put("&zwj", var707);
      Map var709 = ESCAPE_STRINGS;
      Character var710 = Character.valueOf('\u200e');
      var709.put("&lrm", var710);
      Map var712 = ESCAPE_STRINGS;
      Character var713 = Character.valueOf('\u200f');
      var712.put("&rlm", var713);
      Map var715 = ESCAPE_STRINGS;
      Character var716 = Character.valueOf('\u2013');
      var715.put("&ndash", var716);
      Map var718 = ESCAPE_STRINGS;
      Character var719 = Character.valueOf('\u2014');
      var718.put("&mdash", var719);
      Map var721 = ESCAPE_STRINGS;
      Character var722 = Character.valueOf('\u2018');
      var721.put("&lsquo", var722);
      Map var724 = ESCAPE_STRINGS;
      Character var725 = Character.valueOf('\u2019');
      var724.put("&rsquo", var725);
      Map var727 = ESCAPE_STRINGS;
      Character var728 = Character.valueOf('\u201a');
      var727.put("&sbquo", var728);
      Map var730 = ESCAPE_STRINGS;
      Character var731 = Character.valueOf('\u201c');
      var730.put("&ldquo", var731);
      Map var733 = ESCAPE_STRINGS;
      Character var734 = Character.valueOf('\u201d');
      var733.put("&rdquo", var734);
      Map var736 = ESCAPE_STRINGS;
      Character var737 = Character.valueOf('\u201e');
      var736.put("&bdquo", var737);
      Map var739 = ESCAPE_STRINGS;
      Character var740 = Character.valueOf('\u2020');
      var739.put("&dagger", var740);
      Map var742 = ESCAPE_STRINGS;
      Character var743 = Character.valueOf('\u2021');
      var742.put("&Dagger", var743);
      Map var745 = ESCAPE_STRINGS;
      Character var746 = Character.valueOf('\u2030');
      var745.put("&permil", var746);
      Map var748 = ESCAPE_STRINGS;
      Character var749 = Character.valueOf('\u2039');
      var748.put("&lsaquo", var749);
      Map var751 = ESCAPE_STRINGS;
      Character var752 = Character.valueOf('\u203a');
      var751.put("&rsaquo", var752);
      Map var754 = ESCAPE_STRINGS;
      Character var755 = Character.valueOf('\u20ac');
      var754.put("&euro", var755);
   }

   public Snippet() {}

   static int findTagEnd(String var0, String var1, int var2) {
      if(var1.endsWith(" ")) {
         int var3 = var1.length() - 1;
         var1 = var1.substring(0, var3);
      }

      int var4 = var0.length();
      char var5 = 0;
      int var6 = var2;

      int var8;
      while(true) {
         if(var6 < var4) {
            char var7 = var0.charAt(var6);
            if(var7 != 62) {
               var5 = var7;
               ++var6;
               continue;
            }

            if(var5 == 47) {
               var8 = var6 - 1;
               break;
            }
         }

         String var9 = "/" + var1;
         var8 = var0.indexOf(var9, var2);
         break;
      }

      return var8;
   }

   public static String fromHtmlText(String var0) {
      return fromText(var0, (boolean)1);
   }

   public static String fromPlainText(String var0) {
      return fromText(var0, (boolean)0);
   }

   public static String fromText(String var0, boolean var1) {
      if(TextUtils.isEmpty(var0)) {
         var0 = "";
      } else {
         int var2 = var0.length();
         char[] var3 = new char[200];
         int[] var4 = new int[1];
         char var5 = 32;
         int var6 = 0;
         int var7 = 0;
         int var8 = 0;

         while(true) {
            if(var6 >= var2 || var7 >= 200) {
               break;
            }

            int var55;
            int var53;
            char var57;
            label121: {
               char var11;
               int var52;
               label142: {
                  var11 = var0.charAt(var6);
                  if(var1 && var8 == 0 && var11 == 60) {
                     int var12 = var2 - 1;
                     if(var6 < var12) {
                        int var13 = var6 + 1;
                        char var16 = var0.charAt(var13);
                        int var51;
                        int var50;
                        if(var16 != 33 && var16 != 45 && var16 != 47 && !Character.isLetter(var16)) {
                           var51 = var8;
                           var50 = var6;
                        } else {
                           label138: {
                              byte var17 = 1;
                              int var18 = var2 - 8;
                              if(var6 < var18) {
                                 int var19 = var6 + 1;
                                 int var20 = var6 + 6 + 1;
                                 String var24 = var0.substring(var19, var20);
                                 String var25 = var24.toLowerCase();
                                 boolean var26 = false;
                                 String[] var27 = STRIP_TAGS;
                                 int var28 = var27.length;
                                 int var29 = 0;

                                 String var78;
                                 boolean var33;
                                 while(true) {
                                    if(var29 >= var28) {
                                       var33 = var26;
                                       var78 = var24;
                                       break;
                                    }

                                    String var30 = var27[var29];
                                    if(var25.startsWith(var30)) {
                                       var33 = true;
                                       int var34 = var30.length();
                                       byte var36 = 0;
                                       var78 = var24.substring(var36, var34);
                                       break;
                                    }

                                    ++var29;
                                 }

                                 if(var33) {
                                    var8 = findTagEnd(var0, var78, var6);
                                    if(var8 < 0) {
                                       break;
                                    }

                                    var50 = var8;
                                    var51 = var17;
                                    break label138;
                                 }
                              }

                              var50 = var6;
                              var51 = var17;
                           }
                        }

                        var52 = var51;
                        var6 = var50;
                        break label142;
                     }
                  } else {
                     if(!var1) {
                        var52 = var8;
                        break label142;
                     }

                     if(var8 != 0 && var11 == 62) {
                        var57 = var5;
                        boolean var56 = false;
                        var53 = var7;
                        var55 = var6;
                        break label121;
                     }
                  }

                  var52 = var8;
               }

               if(var52 != 0) {
                  var53 = var7;
                  var55 = var6;
                  var57 = var5;
               } else {
                  label144: {
                     char var67;
                     if(var1) {
                        if(var11 == 38) {
                           var67 = stripHtmlEntity(var0, var6, var4);
                           var6 += var4[0];
                        } else {
                           var67 = var11;
                        }
                     } else {
                        var67 = var11;
                     }

                     char var70;
                     if(!Character.isWhitespace(var67) && var67 != 160) {
                        if((var67 == 45 || var67 == 61) && var5 == var67) {
                           var55 = var6;
                           var57 = var5;
                           var53 = var7;
                           break label144;
                        }

                        var70 = var67;
                     } else {
                        if(var5 == 32) {
                           var55 = var6;
                           var57 = var5;
                           var53 = var7;
                           break label144;
                        }

                        var70 = 32;
                     }

                     var53 = var7 + 1;
                     var3[var7] = var70;
                     var55 = var6;
                     var57 = var70;
                  }
               }
            }

            int var58 = var55 + 1;
            var5 = var57;
            var7 = var53;
         }

         int var44;
         label68: {
            if(var7 > 0) {
               byte var43 = 32;
               if(var5 == var43) {
                  var44 = var7 + -1;
                  break label68;
               }
            }

            var44 = var7;
         }

         String var45 = new String;
         byte var48 = 0;
         var45.<init>(var3, var48, var44);
         var0 = var45;
      }

      return var0;
   }

   static char stripHtmlEntity(String param0, int param1, int[] param2) {
      // $FF: Couldn't be decompiled
   }
}
