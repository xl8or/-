package com.seven.Z7.util;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public class HTMLConverter {

   private static Map _xmlEntities = new HashMap();


   static {
      Map var0 = _xmlEntities;
      Character var1 = new Character('&');
      var0.put("amp", var1);
      Map var3 = _xmlEntities;
      Character var4 = new Character('<');
      var3.put("lt", var4);
      Map var6 = _xmlEntities;
      Character var7 = new Character('>');
      var6.put("gt", var7);
      Map var9 = _xmlEntities;
      Character var10 = new Character('\"');
      var9.put("quot", var10);
      Map var12 = _xmlEntities;
      Character var13 = new Character('\'');
      var12.put("apos", var13);
      Map var15 = _xmlEntities;
      Character var16 = new Character('\u201c');
      var15.put("ldquo", var16);
      Map var18 = _xmlEntities;
      Character var19 = new Character('\u201d');
      var18.put("rdquo", var19);
      Map var21 = _xmlEntities;
      Character var22 = new Character('\u201e');
      var21.put("bdquo", var22);
      Map var24 = _xmlEntities;
      Character var25 = new Character('\u00ab');
      var24.put("laquo", var25);
      Map var27 = _xmlEntities;
      Character var28 = new Character('\u00bb');
      var27.put("raquo", var28);
      Map var30 = _xmlEntities;
      Character var31 = new Character('\u2018');
      var30.put("lsquo", var31);
      Map var33 = _xmlEntities;
      Character var34 = new Character('\u2019');
      var33.put("rsquo", var34);
      Map var36 = _xmlEntities;
      Character var37 = new Character('\u201a');
      var36.put("sbquo", var37);
      Map var39 = _xmlEntities;
      Character var40 = new Character('\u2039');
      var39.put("lsaquo", var40);
      Map var42 = _xmlEntities;
      Character var43 = new Character('\u203a');
      var42.put("rsaquo", var43);
      Map var45 = _xmlEntities;
      Character var46 = new Character('\u00ad');
      var45.put("shy", var46);
      Map var48 = _xmlEntities;
      Character var49 = new Character('\u2013');
      var48.put("ndash", var49);
      Map var51 = _xmlEntities;
      Character var52 = new Character('\u2014');
      var51.put("mdash", var52);
      Map var54 = _xmlEntities;
      Character var55 = new Character('\u2026');
      var54.put("hellip", var55);
      Map var57 = _xmlEntities;
      Character var58 = new Character('\u00ba');
      var57.put("ordm", var58);
      Map var60 = _xmlEntities;
      Character var61 = new Character('\u00aa');
      var60.put("ordf", var61);
      Map var63 = _xmlEntities;
      Character var64 = new Character('\u00bf');
      var63.put("iquest", var64);
      Map var66 = _xmlEntities;
      Character var67 = new Character('\u00a1');
      var66.put("iexcl", var67);
      Map var69 = _xmlEntities;
      Character var70 = new Character('\u00a0');
      var69.put("nbsp", var70);
      Map var72 = _xmlEntities;
      Character var73 = new Character('\u2002');
      var72.put("ensp", var73);
      Map var75 = _xmlEntities;
      Character var76 = new Character('\u2003');
      var75.put("emsp", var76);
      Map var78 = _xmlEntities;
      Character var79 = new Character('\u2009');
      var78.put("thinsp", var79);
      Map var81 = _xmlEntities;
      Character var82 = new Character('\u200c');
      var81.put("zwnj", var82);
      Map var84 = _xmlEntities;
      Character var85 = new Character('\u200d');
      var84.put("zwj", var85);
      Map var87 = _xmlEntities;
      Character var88 = new Character('\u200e');
      var87.put("lrm", var88);
      Map var90 = _xmlEntities;
      Character var91 = new Character('\u200f');
      var90.put("rlm", var91);
      Map var93 = _xmlEntities;
      Character var94 = new Character('\u00b4');
      var93.put("acute", var94);
      Map var96 = _xmlEntities;
      Character var97 = new Character('\u02c6');
      var96.put("circ", var97);
      Map var99 = _xmlEntities;
      Character var100 = new Character('\u02dc');
      var99.put("tilde", var100);
      Map var102 = _xmlEntities;
      Character var103 = new Character('\u00a8');
      var102.put("uml", var103);
      Map var105 = _xmlEntities;
      Character var106 = new Character('\u00b8');
      var105.put("cedil", var106);
      Map var108 = _xmlEntities;
      Character var109 = new Character('\u00af');
      var108.put("macr", var109);
      Map var111 = _xmlEntities;
      Character var112 = new Character('\u203e');
      var111.put("oline", var112);
      Map var114 = _xmlEntities;
      Character var115 = new Character('\u00c1');
      var114.put("Aacute", var115);
      Map var117 = _xmlEntities;
      Character var118 = new Character('\u00e1');
      var117.put("aacute", var118);
      Map var120 = _xmlEntities;
      Character var121 = new Character('\u00c2');
      var120.put("Acirc", var121);
      Map var123 = _xmlEntities;
      Character var124 = new Character('\u00e2');
      var123.put("acirc", var124);
      Map var126 = _xmlEntities;
      Character var127 = new Character('\u00c0');
      var126.put("Agrave", var127);
      Map var129 = _xmlEntities;
      Character var130 = new Character('\u00e0');
      var129.put("agrave", var130);
      Map var132 = _xmlEntities;
      Character var133 = new Character('\u00c5');
      var132.put("Aring", var133);
      Map var135 = _xmlEntities;
      Character var136 = new Character('\u00e5');
      var135.put("aring", var136);
      Map var138 = _xmlEntities;
      Character var139 = new Character('\u00c3');
      var138.put("Atilde", var139);
      Map var141 = _xmlEntities;
      Character var142 = new Character('\u00e3');
      var141.put("atilde", var142);
      Map var144 = _xmlEntities;
      Character var145 = new Character('\u00c4');
      var144.put("Auml", var145);
      Map var147 = _xmlEntities;
      Character var148 = new Character('\u00e4');
      var147.put("auml", var148);
      Map var150 = _xmlEntities;
      Character var151 = new Character('\u00c6');
      var150.put("AElig", var151);
      Map var153 = _xmlEntities;
      Character var154 = new Character('\u00e6');
      var153.put("aelig", var154);
      Map var156 = _xmlEntities;
      Character var157 = new Character('\u00c7');
      var156.put("Ccedil", var157);
      Map var159 = _xmlEntities;
      Character var160 = new Character('\u00e7');
      var159.put("ccedil", var160);
      Map var162 = _xmlEntities;
      Character var163 = new Character('\u00d0');
      var162.put("ETH", var163);
      Map var165 = _xmlEntities;
      Character var166 = new Character('\u00f0');
      var165.put("eth", var166);
      Map var168 = _xmlEntities;
      Character var169 = new Character('\u00c9');
      var168.put("Eacute", var169);
      Map var171 = _xmlEntities;
      Character var172 = new Character('\u00e9');
      var171.put("eacute", var172);
      Map var174 = _xmlEntities;
      Character var175 = new Character('\u00c8');
      var174.put("Egrave", var175);
      Map var177 = _xmlEntities;
      Character var178 = new Character('\u00e8');
      var177.put("egrave", var178);
      Map var180 = _xmlEntities;
      Character var181 = new Character('\u00cb');
      var180.put("Euml", var181);
      Map var183 = _xmlEntities;
      Character var184 = new Character('\u00eb');
      var183.put("euml", var184);
      Map var186 = _xmlEntities;
      Character var187 = new Character('\u00ca');
      var186.put("Ecirc", var187);
      Map var189 = _xmlEntities;
      Character var190 = new Character('\u00ea');
      var189.put("ecirc", var190);
      Map var192 = _xmlEntities;
      Character var193 = new Character('\u00cd');
      var192.put("Iacute", var193);
      Map var195 = _xmlEntities;
      Character var196 = new Character('\u00ed');
      var195.put("iacute", var196);
      Map var198 = _xmlEntities;
      Character var199 = new Character('\u00cc');
      var198.put("Igrave", var199);
      Map var201 = _xmlEntities;
      Character var202 = new Character('\u00ec');
      var201.put("igrave", var202);
      Map var204 = _xmlEntities;
      Character var205 = new Character('\u00cf');
      var204.put("Iuml", var205);
      Map var207 = _xmlEntities;
      Character var208 = new Character('\u00ef');
      var207.put("iuml", var208);
      Map var210 = _xmlEntities;
      Character var211 = new Character('\u00ce');
      var210.put("Icirc", var211);
      Map var213 = _xmlEntities;
      Character var214 = new Character('\u00ee');
      var213.put("icirc", var214);
      Map var216 = _xmlEntities;
      Character var217 = new Character('\u00d1');
      var216.put("Ntilde", var217);
      Map var219 = _xmlEntities;
      Character var220 = new Character('\u00f1');
      var219.put("ntilde", var220);
      Map var222 = _xmlEntities;
      Character var223 = new Character('\u00d3');
      var222.put("Oacute", var223);
      Map var225 = _xmlEntities;
      Character var226 = new Character('\u00f3');
      var225.put("oacute", var226);
      Map var228 = _xmlEntities;
      Character var229 = new Character('\u00d2');
      var228.put("Ograve", var229);
      Map var231 = _xmlEntities;
      Character var232 = new Character('\u00f2');
      var231.put("ograve", var232);
      Map var234 = _xmlEntities;
      Character var235 = new Character('\u00d5');
      var234.put("Otilde", var235);
      Map var237 = _xmlEntities;
      Character var238 = new Character('\u00f5');
      var237.put("otilde", var238);
      Map var240 = _xmlEntities;
      Character var241 = new Character('\u00d6');
      var240.put("Ouml", var241);
      Map var243 = _xmlEntities;
      Character var244 = new Character('\u00f6');
      var243.put("ouml", var244);
      Map var246 = _xmlEntities;
      Character var247 = new Character('\u00d4');
      var246.put("Ocirc", var247);
      Map var249 = _xmlEntities;
      Character var250 = new Character('\u00f4');
      var249.put("ocirc", var250);
      Map var252 = _xmlEntities;
      Character var253 = new Character('\u00d8');
      var252.put("Oslash", var253);
      Map var255 = _xmlEntities;
      Character var256 = new Character('\u00f8');
      var255.put("oslash", var256);
      Map var258 = _xmlEntities;
      Character var259 = new Character('\u0152');
      var258.put("OElig", var259);
      Map var261 = _xmlEntities;
      Character var262 = new Character('\u0153');
      var261.put("oelig", var262);
      Map var264 = _xmlEntities;
      Character var265 = new Character('\u0160');
      var264.put("Scaron", var265);
      Map var267 = _xmlEntities;
      Character var268 = new Character('\u0161');
      var267.put("scaron", var268);
      Map var270 = _xmlEntities;
      Character var271 = new Character('\u00df');
      var270.put("szlig", var271);
      Map var273 = _xmlEntities;
      Character var274 = new Character('\u00de');
      var273.put("THORN", var274);
      Map var276 = _xmlEntities;
      Character var277 = new Character('\u00fe');
      var276.put("thorn", var277);
      Map var279 = _xmlEntities;
      Character var280 = new Character('\u00da');
      var279.put("Uacute", var280);
      Map var282 = _xmlEntities;
      Character var283 = new Character('\u00fa');
      var282.put("uacute", var283);
      Map var285 = _xmlEntities;
      Character var286 = new Character('\u00d9');
      var285.put("Ugrave", var286);
      Map var288 = _xmlEntities;
      Character var289 = new Character('\u00f9');
      var288.put("ugrave", var289);
      Map var291 = _xmlEntities;
      Character var292 = new Character('\u00dc');
      var291.put("Uuml", var292);
      Map var294 = _xmlEntities;
      Character var295 = new Character('\u00fc');
      var294.put("uuml", var295);
      Map var297 = _xmlEntities;
      Character var298 = new Character('\u00db');
      var297.put("Ucirc", var298);
      Map var300 = _xmlEntities;
      Character var301 = new Character('\u00fb');
      var300.put("ucirc", var301);
      Map var303 = _xmlEntities;
      Character var304 = new Character('\u00dd');
      var303.put("Yacute", var304);
      Map var306 = _xmlEntities;
      Character var307 = new Character('\u00fd');
      var306.put("yacute", var307);
      Map var309 = _xmlEntities;
      Character var310 = new Character('\u0178');
      var309.put("Yuml", var310);
      Map var312 = _xmlEntities;
      Character var313 = new Character('\u00ff');
      var312.put("yuml", var313);
      Map var315 = _xmlEntities;
      Character var316 = new Character('\u0391');
      var315.put("Alpha", var316);
      Map var318 = _xmlEntities;
      Character var319 = new Character('\u03b1');
      var318.put("alpha", var319);
      Map var321 = _xmlEntities;
      Character var322 = new Character('\u0392');
      var321.put("Beta", var322);
      Map var324 = _xmlEntities;
      Character var325 = new Character('\u03b2');
      var324.put("beta", var325);
      Map var327 = _xmlEntities;
      Character var328 = new Character('\u0393');
      var327.put("Gamma", var328);
      Map var330 = _xmlEntities;
      Character var331 = new Character('\u03b3');
      var330.put("gamma", var331);
      Map var333 = _xmlEntities;
      Character var334 = new Character('\u0394');
      var333.put("Delta", var334);
      Map var336 = _xmlEntities;
      Character var337 = new Character('\u03b4');
      var336.put("delta", var337);
      Map var339 = _xmlEntities;
      Character var340 = new Character('\u0395');
      var339.put("Epsilon", var340);
      Map var342 = _xmlEntities;
      Character var343 = new Character('\u03b5');
      var342.put("epsilon", var343);
      Map var345 = _xmlEntities;
      Character var346 = new Character('\u0396');
      var345.put("Zeta", var346);
      Map var348 = _xmlEntities;
      Character var349 = new Character('\u03b6');
      var348.put("zeta", var349);
      Map var351 = _xmlEntities;
      Character var352 = new Character('\u0397');
      var351.put("Eta", var352);
      Map var354 = _xmlEntities;
      Character var355 = new Character('\u03b7');
      var354.put("eta", var355);
      Map var357 = _xmlEntities;
      Character var358 = new Character('\u0398');
      var357.put("Theta", var358);
      Map var360 = _xmlEntities;
      Character var361 = new Character('\u03b8');
      var360.put("theta", var361);
      Map var363 = _xmlEntities;
      Character var364 = new Character('\u0399');
      var363.put("Iota", var364);
      Map var366 = _xmlEntities;
      Character var367 = new Character('\u03b9');
      var366.put("iota", var367);
      Map var369 = _xmlEntities;
      Character var370 = new Character('\u039a');
      var369.put("Kappa", var370);
      Map var372 = _xmlEntities;
      Character var373 = new Character('\u03ba');
      var372.put("kappa", var373);
      Map var375 = _xmlEntities;
      Character var376 = new Character('\u039b');
      var375.put("Lambda", var376);
      Map var378 = _xmlEntities;
      Character var379 = new Character('\u03bb');
      var378.put("lambda", var379);
      Map var381 = _xmlEntities;
      Character var382 = new Character('\u039c');
      var381.put("Mu", var382);
      Map var384 = _xmlEntities;
      Character var385 = new Character('\u03bc');
      var384.put("mu", var385);
      Map var387 = _xmlEntities;
      Character var388 = new Character('\u039d');
      var387.put("Nu", var388);
      Map var390 = _xmlEntities;
      Character var391 = new Character('\u03bd');
      var390.put("nu", var391);
      Map var393 = _xmlEntities;
      Character var394 = new Character('\u039e');
      var393.put("Xi", var394);
      Map var396 = _xmlEntities;
      Character var397 = new Character('\u03be');
      var396.put("xi", var397);
      Map var399 = _xmlEntities;
      Character var400 = new Character('\u039f');
      var399.put("Omicron", var400);
      Map var402 = _xmlEntities;
      Character var403 = new Character('\u03bf');
      var402.put("omicron", var403);
      Map var405 = _xmlEntities;
      Character var406 = new Character('\u03a0');
      var405.put("Pi", var406);
      Map var408 = _xmlEntities;
      Character var409 = new Character('\u03c0');
      var408.put("pi", var409);
      Map var411 = _xmlEntities;
      Character var412 = new Character('\u03a1');
      var411.put("Rho", var412);
      Map var414 = _xmlEntities;
      Character var415 = new Character('\u03c1');
      var414.put("rho", var415);
      Map var417 = _xmlEntities;
      Character var418 = new Character('\u03a3');
      var417.put("Sigma", var418);
      Map var420 = _xmlEntities;
      Character var421 = new Character('\u03c3');
      var420.put("sigma", var421);
      Map var423 = _xmlEntities;
      Character var424 = new Character('\u03c2');
      var423.put("sigmaf", var424);
      Map var426 = _xmlEntities;
      Character var427 = new Character('\u03a4');
      var426.put("Tau", var427);
      Map var429 = _xmlEntities;
      Character var430 = new Character('\u03c4');
      var429.put("tau", var430);
      Map var432 = _xmlEntities;
      Character var433 = new Character('\u03a5');
      var432.put("Upsilon", var433);
      Map var435 = _xmlEntities;
      Character var436 = new Character('\u03c5');
      var435.put("upsilon", var436);
      Map var438 = _xmlEntities;
      Character var439 = new Character('\u03a6');
      var438.put("Phi", var439);
      Map var441 = _xmlEntities;
      Character var442 = new Character('\u03c6');
      var441.put("phi", var442);
      Map var444 = _xmlEntities;
      Character var445 = new Character('\u03a7');
      var444.put("Chi", var445);
      Map var447 = _xmlEntities;
      Character var448 = new Character('\u03c7');
      var447.put("chi", var448);
      Map var450 = _xmlEntities;
      Character var451 = new Character('\u03a8');
      var450.put("Psi", var451);
      Map var453 = _xmlEntities;
      Character var454 = new Character('\u03c8');
      var453.put("psi", var454);
      Map var456 = _xmlEntities;
      Character var457 = new Character('\u03a9');
      var456.put("Omega", var457);
      Map var459 = _xmlEntities;
      Character var460 = new Character('\u03c9');
      var459.put("omega", var460);
      Map var462 = _xmlEntities;
      Character var463 = new Character('\u00a9');
      var462.put("copy", var463);
      Map var465 = _xmlEntities;
      Character var466 = new Character('\u00ae');
      var465.put("reg", var466);
      Map var468 = _xmlEntities;
      Character var469 = new Character('\u2122');
      var468.put("trade", var469);
      Map var471 = _xmlEntities;
      Character var472 = new Character('\u00a2');
      var471.put("cent", var472);
      Map var474 = _xmlEntities;
      Character var475 = new Character('\u00a3');
      var474.put("pound", var475);
      Map var477 = _xmlEntities;
      Character var478 = new Character('\u00a5');
      var477.put("yen", var478);
      Map var480 = _xmlEntities;
      Character var481 = new Character('\u0192');
      var480.put("fnof", var481);
      Map var483 = _xmlEntities;
      Character var484 = new Character('\u20ac');
      var483.put("euro", var484);
      Map var486 = _xmlEntities;
      Character var487 = new Character('\u00a4');
      var486.put("curren", var487);
      Map var489 = _xmlEntities;
      Character var490 = new Character('\u00b6');
      var489.put("para", var490);
      Map var492 = _xmlEntities;
      Character var493 = new Character('\u00a7');
      var492.put("sect", var493);
      Map var495 = _xmlEntities;
      Character var496 = new Character('\u2020');
      var495.put("dagger", var496);
      Map var498 = _xmlEntities;
      Character var499 = new Character('\u2021');
      var498.put("Dagger", var499);
      Map var501 = _xmlEntities;
      Character var502 = new Character('\u00a6');
      var501.put("brvbar", var502);
      Map var504 = _xmlEntities;
      Character var505 = new Character('\u00b7');
      var504.put("middot", var505);
      Map var507 = _xmlEntities;
      Character var508 = new Character('\u2022');
      var507.put("bull", var508);
      Map var510 = _xmlEntities;
      Character var511 = new Character('\u25ca');
      var510.put("loz", var511);
      Map var513 = _xmlEntities;
      Character var514 = new Character('\u2660');
      var513.put("spades", var514);
      Map var516 = _xmlEntities;
      Character var517 = new Character('\u2663');
      var516.put("clubs", var517);
      Map var519 = _xmlEntities;
      Character var520 = new Character('\u2665');
      var519.put("hearts", var520);
      Map var522 = _xmlEntities;
      Character var523 = new Character('\u2666');
      var522.put("diams", var523);
      Map var525 = _xmlEntities;
      Character var526 = new Character('\u2190');
      var525.put("larr", var526);
      Map var528 = _xmlEntities;
      Character var529 = new Character('\u2191');
      var528.put("uarr", var529);
      Map var531 = _xmlEntities;
      Character var532 = new Character('\u2192');
      var531.put("rarr", var532);
      Map var534 = _xmlEntities;
      Character var535 = new Character('\u2193');
      var534.put("darr", var535);
      Map var537 = _xmlEntities;
      Character var538 = new Character('\u2194');
      var537.put("harr", var538);
      Map var540 = _xmlEntities;
      Character var541 = new Character('\u21b5');
      var540.put("crarr", var541);
      Map var543 = _xmlEntities;
      Character var544 = new Character('\u21d0');
      var543.put("lArr", var544);
      Map var546 = _xmlEntities;
      Character var547 = new Character('\u21d1');
      var546.put("uArr", var547);
      Map var549 = _xmlEntities;
      Character var550 = new Character('\u21d2');
      var549.put("rArr", var550);
      Map var552 = _xmlEntities;
      Character var553 = new Character('\u21d3');
      var552.put("dArr", var553);
      Map var555 = _xmlEntities;
      Character var556 = new Character('\u21d4');
      var555.put("hArr", var556);
      Map var558 = _xmlEntities;
      Character var559 = new Character('\u2030');
      var558.put("permil", var559);
      Map var561 = _xmlEntities;
      Character var562 = new Character('\u00d7');
      var561.put("times", var562);
      Map var564 = _xmlEntities;
      Character var565 = new Character('\u00f7');
      var564.put("divide", var565);
      Map var567 = _xmlEntities;
      Character var568 = new Character('\u00b9');
      var567.put("sup1", var568);
      Map var570 = _xmlEntities;
      Character var571 = new Character('\u00b2');
      var570.put("sup2", var571);
      Map var573 = _xmlEntities;
      Character var574 = new Character('\u00b3');
      var573.put("sup3", var574);
      Map var576 = _xmlEntities;
      Character var577 = new Character('\u00bc');
      var576.put("frac14", var577);
      Map var579 = _xmlEntities;
      Character var580 = new Character('\u00bd');
      var579.put("frac12", var580);
      Map var582 = _xmlEntities;
      Character var583 = new Character('\u00be');
      var582.put("frac34", var583);
      Map var585 = _xmlEntities;
      Character var586 = new Character('\u2044');
      var585.put("frasl", var586);
      Map var588 = _xmlEntities;
      Character var589 = new Character('\u00b1');
      var588.put("plusmn", var589);
      Map var591 = _xmlEntities;
      Character var592 = new Character('\u00b0');
      var591.put("deg", var592);
      Map var594 = _xmlEntities;
      Character var595 = new Character('\u00b5');
      var594.put("micro", var595);
      Map var597 = _xmlEntities;
      Character var598 = new Character('\u0192');
      var597.put("fnof", var598);
      Map var600 = _xmlEntities;
      Character var601 = new Character('\u00ac');
      var600.put("not", var601);
      Map var603 = _xmlEntities;
      Character var604 = new Character('\u2032');
      var603.put("prime", var604);
      Map var606 = _xmlEntities;
      Character var607 = new Character('\u2033');
      var606.put("Prime", var607);
      Map var609 = _xmlEntities;
      Character var610 = new Character('\u03d1');
      var609.put("thetasym", var610);
      Map var612 = _xmlEntities;
      Character var613 = new Character('\u03d2');
      var612.put("upsih", var613);
      Map var615 = _xmlEntities;
      Character var616 = new Character('\u03d6');
      var615.put("piv", var616);
      Map var618 = _xmlEntities;
      Character var619 = new Character('\u2118');
      var618.put("weierp", var619);
      Map var621 = _xmlEntities;
      Character var622 = new Character('\u2111');
      var621.put("image", var622);
      Map var624 = _xmlEntities;
      Character var625 = new Character('\u211c');
      var624.put("real", var625);
      Map var627 = _xmlEntities;
      Character var628 = new Character('\u2135');
      var627.put("alefsym", var628);
      Map var630 = _xmlEntities;
      Character var631 = new Character('\u2200');
      var630.put("forall", var631);
      Map var633 = _xmlEntities;
      Character var634 = new Character('\u2202');
      var633.put("part", var634);
      Map var636 = _xmlEntities;
      Character var637 = new Character('\u2203');
      var636.put("exist", var637);
      Map var639 = _xmlEntities;
      Character var640 = new Character('\u2205');
      var639.put("empty", var640);
      Map var642 = _xmlEntities;
      Character var643 = new Character('\u2207');
      var642.put("nabla", var643);
      Map var645 = _xmlEntities;
      Character var646 = new Character('\u2208');
      var645.put("isin", var646);
      Map var648 = _xmlEntities;
      Character var649 = new Character('\u2209');
      var648.put("notin", var649);
      Map var651 = _xmlEntities;
      Character var652 = new Character('\u220b');
      var651.put("ni", var652);
      Map var654 = _xmlEntities;
      Character var655 = new Character('\u220f');
      var654.put("prod", var655);
      Map var657 = _xmlEntities;
      Character var658 = new Character('\u2211');
      var657.put("sum", var658);
      Map var660 = _xmlEntities;
      Character var661 = new Character('\u2212');
      var660.put("minus", var661);
      Map var663 = _xmlEntities;
      Character var664 = new Character('\u2217');
      var663.put("lowast", var664);
      Map var666 = _xmlEntities;
      Character var667 = new Character('\u221a');
      var666.put("radic", var667);
      Map var669 = _xmlEntities;
      Character var670 = new Character('\u221d');
      var669.put("prop", var670);
      Map var672 = _xmlEntities;
      Character var673 = new Character('\u221e');
      var672.put("infin", var673);
      Map var675 = _xmlEntities;
      Character var676 = new Character('\u2220');
      var675.put("ang", var676);
      Map var678 = _xmlEntities;
      Character var679 = new Character('\u2227');
      var678.put("and", var679);
      Map var681 = _xmlEntities;
      Character var682 = new Character('\u2228');
      var681.put("or", var682);
      Map var684 = _xmlEntities;
      Character var685 = new Character('\u2229');
      var684.put("cap", var685);
      Map var687 = _xmlEntities;
      Character var688 = new Character('\u222a');
      var687.put("cup", var688);
      Map var690 = _xmlEntities;
      Character var691 = new Character('\u222b');
      var690.put("int", var691);
      Map var693 = _xmlEntities;
      Character var694 = new Character('\u2234');
      var693.put("there4", var694);
      Map var696 = _xmlEntities;
      Character var697 = new Character('\u223c');
      var696.put("sim", var697);
      Map var699 = _xmlEntities;
      Character var700 = new Character('\u2245');
      var699.put("cong", var700);
      Map var702 = _xmlEntities;
      Character var703 = new Character('\u2248');
      var702.put("asymp", var703);
      Map var705 = _xmlEntities;
      Character var706 = new Character('\u2260');
      var705.put("ne", var706);
      Map var708 = _xmlEntities;
      Character var709 = new Character('\u2261');
      var708.put("equiv", var709);
      Map var711 = _xmlEntities;
      Character var712 = new Character('\u2264');
      var711.put("le", var712);
      Map var714 = _xmlEntities;
      Character var715 = new Character('\u2265');
      var714.put("ge", var715);
      Map var717 = _xmlEntities;
      Character var718 = new Character('\u2282');
      var717.put("sub", var718);
      Map var720 = _xmlEntities;
      Character var721 = new Character('\u2283');
      var720.put("sup", var721);
      Map var723 = _xmlEntities;
      Character var724 = new Character('\u2284');
      var723.put("nsub", var724);
      Map var726 = _xmlEntities;
      Character var727 = new Character('\u2286');
      var726.put("sube", var727);
      Map var729 = _xmlEntities;
      Character var730 = new Character('\u2287');
      var729.put("supe", var730);
      Map var732 = _xmlEntities;
      Character var733 = new Character('\u2295');
      var732.put("oplus", var733);
      Map var735 = _xmlEntities;
      Character var736 = new Character('\u2297');
      var735.put("otimes", var736);
      Map var738 = _xmlEntities;
      Character var739 = new Character('\u22a5');
      var738.put("perp", var739);
      Map var741 = _xmlEntities;
      Character var742 = new Character('\u22c5');
      var741.put("sdot", var742);
      Map var744 = _xmlEntities;
      Character var745 = new Character('\u2308');
      var744.put("lceil", var745);
      Map var747 = _xmlEntities;
      Character var748 = new Character('\u2309');
      var747.put("rceil", var748);
      Map var750 = _xmlEntities;
      Character var751 = new Character('\u230a');
      var750.put("lfloor", var751);
      Map var753 = _xmlEntities;
      Character var754 = new Character('\u230b');
      var753.put("rfloor", var754);
      Map var756 = _xmlEntities;
      Character var757 = new Character('\u2329');
      var756.put("lang", var757);
      Map var759 = _xmlEntities;
      Character var760 = new Character('\u232a');
      var759.put("rang", var760);
   }

   public HTMLConverter() {}

   public static String convertToPlainText(String var0) {
      return decodeEntities(html2Text(var0));
   }

   public static String decodeEntities(String var0) {
      if(var0 == null) {
         var0 = null;
      } else {
         int var1 = var0.indexOf(38);
         if(var1 != -1) {
            int var2 = var0.length();
            StringBuffer var3 = new StringBuffer(var2);
            byte var4 = 0;

            while(var1 != -1) {
               int var5 = var1 + 1;
               if(var5 >= var2) {
                  break;
               }

               if(var0.charAt(var5) == 32) {
                  var1 = var0.indexOf(38, var5);
               } else {
                  int var9 = var0.indexOf(59, var5);
                  if(var9 == -1) {
                     break;
                  }

                  int var11 = var9 - var5;
                  if(var11 >= 2 && var11 <= 8) {
                     int var12 = var5 - 1;
                     String var13 = var0.substring(var4, var12);
                     var3.append(var13);
                     int var15 = var9 + 1;
                     String var16 = var0.substring(var5, var9);
                     char var17 = var16.charAt(0);
                     var4 = 35;
                     if(var17 == var4) {
                        String var18 = var16.substring(1);
                        byte var19 = 0;
                        String var21;
                        if(var18.charAt(0) == 120) {
                           String var20 = var18.substring(1);
                           var4 = 1;
                           var21 = var20;
                        } else {
                           var4 = var19;
                           var21 = var18;
                        }

                        byte var35;
                        if(var4 != 0) {
                           var35 = 16;
                        } else {
                           var35 = 10;
                        }

                        try {
                           char var22 = (char)Integer.parseInt(var21, var35);
                           var3.append(var22);
                        } catch (NumberFormatException var34) {
                           StringBuffer var26 = var3.append("&#");
                           if(var4 != 0) {
                              StringBuffer var27 = var3.append('x');
                           }

                           var3.append(var21);
                           StringBuffer var29 = var3.append(';');
                        }
                     } else {
                        Character var36 = (Character)_xmlEntities.get(var16);
                        if(var36 != null) {
                           var3.append(var36);
                        } else {
                           StringBuffer var31 = var3.append('&');
                           var3.append(var16);
                           StringBuffer var33 = var3.append(';');
                        }
                     }
                  }

                  var1 = var0.indexOf(38, var9);
               }
            }

            String var7 = var0.substring(var4);
            var3.append(var7);
            var0 = var3.toString();
         }
      }

      return var0;
   }

   public static String escapeHtmlTags(String var0, boolean var1) {
      int var2 = var0.length();
      StringBuffer var3 = new StringBuffer(var2);
      boolean var4 = false;
      int var5 = var0.length();

      for(int var6 = 0; var6 < var5; ++var6) {
         char var7 = var0.charAt(var6);
         if(var7 == 32) {
            if(var4) {
               var4 = false;
               StringBuffer var8 = var3.append("&nbsp;");
            } else {
               var4 = true;
               StringBuffer var9 = var3.append(' ');
            }
         } else {
            var4 = false;
            if(var7 == 34) {
               StringBuffer var10 = var3.append("&quot;");
            } else if(var7 == 38) {
               StringBuffer var11 = var3.append("&amp;");
            } else if(var7 == 60) {
               StringBuffer var12 = var3.append("&lt;");
            } else if(var7 == 62) {
               StringBuffer var13 = var3.append("&gt;");
            } else if(var7 == 10) {
               if(var1) {
                  StringBuffer var14 = var3.append("&lt;br/&gt;");
               } else {
                  StringBuffer var15 = var3.append('\n');
               }
            } else {
               int var16 = '\uffff' & var7;
               if(var16 < 160) {
                  var3.append(var7);
               } else {
                  StringBuffer var18 = var3.append("&#");
                  String var19 = (new Integer(var16)).toString();
                  var3.append(var19);
                  StringBuffer var21 = var3.append(';');
               }
            }
         }
      }

      return var3.toString();
   }

   public static String html2Text(String var0) {
      return stripAllHtmlTags(preserveSomeHtmlTagsAndRemoveWhitespaces(stripAllCRs(var0)));
   }

   public static void main(String[] var0) {
      PrintStream var1 = System.out;
      String var2 = escapeHtmlTags("<html><body>text\r\ntext2</body></html> ", (boolean)0);
      var1.println(var2);
   }

   public static String preserveSomeHtmlTagsAndRemoveWhitespaces(String param0) {
      // $FF: Couldn't be decompiled
   }

   public static String stripAllCRs(String var0) {
      String var1;
      if(var0 == null) {
         var1 = null;
      } else {
         int var2 = var0.indexOf(10);
         if(var2 == -1) {
            var1 = var0;
         } else {
            int var3 = var0.length();
            StringBuffer var4 = new StringBuffer(var3);

            int var5;
            int var15;
            for(var5 = 0; var2 != -1; var2 = var0.indexOf(10, var15)) {
               byte var6 = 1;
               if(var2 > 0) {
                  int var7 = var2 - 1;
                  if(var0.charAt(var7) == 13) {
                     var2 += -1;
                     var6 = 2;
                  }
               }

               String var8 = var0.substring(var5, var2);
               var4.append(var8);
               var5 = var2 + var6;
               if(var2 > 0 && var2 + var6 < var3) {
                  int var10 = var2 - 1;
                  char var11 = var0.charAt(var10);
                  int var12 = var2 + var6;
                  char var13 = var0.charAt(var12);
                  if(!Character.isWhitespace(var13) && (var11 == 10 || !Character.isWhitespace(var11)) && var11 != 62 && var13 != 60) {
                     StringBuffer var14 = var4.append(' ');
                  }
               }

               var15 = var2 + var6;
            }

            String var16 = var0.substring(var5);
            var4.append(var16);
            var1 = var4.toString();
         }
      }

      return var1;
   }

   public static String stripAllHtmlTags(String var0) {
      return stripTags(stripHTMLHeader(var0));
   }

   public static String stripHTMLHeader(String var0) {
      String var1;
      if(var0 == null) {
         var1 = null;
      } else {
         int var2 = var0.length();
         int var3 = var0.indexOf(60);
         if(var3 < 0) {
            var1 = var0;
         } else {
            for(int var4 = 0; var3 >= 0; var3 = var0.indexOf(60, var3)) {
               int var20;
               while(var3 >= 0) {
                  int var5 = var2 - 6;
                  if(var3 <= var5) {
                     int var6 = var3 + 6;
                     String var7 = var0.substring(var3, var6);
                     if("<head>".equalsIgnoreCase(var7)) {
                        var4 = var0.indexOf("<!--", var4);
                        if(var4 == -1 || var3 < var4) {
                           break;
                        }

                        var20 = var0.indexOf("-->", var4);
                        if(var20 != -1 && var20 < var3) {
                           var4 = var20 + 3;
                           continue;
                        }

                        if(var20 == -1) {
                           break;
                        }

                        int var21 = var20 + 3;
                        var3 = var20 + 2;
                     }
                  }

                  int var22 = var3 + 1;
                  var3 = var0.indexOf(60, var22);
               }

               if(var3 < 0) {
                  break;
               }

               int var9 = var3 + 6;
               int var10 = var0.indexOf(60, var9);
               var4 = var3 + 6;

               while(var10 >= 0) {
                  int var11 = var2 - 7;
                  if(var10 <= var11) {
                     int var12 = var10 + 7;
                     String var13 = var0.substring(var10, var12);
                     if("</head>".equalsIgnoreCase(var13)) {
                        var4 = var0.indexOf("<!--", var4);
                        if(var4 == -1 || var10 < var4) {
                           break;
                        }

                        var20 = var0.indexOf("-->", var4);
                        if(var20 != -1 && var20 < var10) {
                           var4 = var20 + 3;
                           continue;
                        }

                        if(var20 == -1) {
                           break;
                        }

                        int var23 = var20 + 3;
                        var10 = var20 + 2;
                     }
                  }

                  int var24 = var10 + 1;
                  var10 = var0.indexOf(60, var24);
               }

               if(var10 < 0) {
                  break;
               }

               StringBuilder var15 = new StringBuilder();
               String var16 = var0.substring(0, var3);
               StringBuilder var17 = var15.append(var16);
               int var18 = var10 + 7;
               String var19 = var0.substring(var18);
               var0 = var17.append(var19).toString();
               var2 = var0.length();
               var4 = 0;
            }

            var1 = var0;
         }
      }

      return var1;
   }

   public static String stripTags(String var0) {
      String var1;
      if(var0 == null) {
         var1 = null;
      } else {
         int var2 = var0.indexOf(60);
         if(var2 < 0) {
            var1 = var0;
         } else {
            int var3 = var0.length();
            StringBuffer var4 = new StringBuffer(var3);
            int var5 = 0;

            while(var2 >= 0) {
               int var6 = var2 + 7;
               int var8;
               if(var3 >= var6) {
                  int var7 = var2 + 7;
                  if(var0.substring(var2, var7).equalsIgnoreCase("<script")) {
                     var8 = var0.indexOf("</script>", var2);
                     if(var8 == -1) {
                        var8 = var0.indexOf("</SCRIPT>", var2);
                     }

                     if(var8 > 0) {
                        String var9 = var0.substring(var5, var2);
                        var4.append(var9);
                        var5 = var8 + 9;
                        var2 = var0.indexOf(60, var5);
                        continue;
                     }
                  }
               }

               int var11 = var2 + 6;
               if(var3 >= var11) {
                  int var12 = var2 + 6;
                  if(var0.substring(var2, var12).equalsIgnoreCase("<style")) {
                     var8 = var0.indexOf("</style>", var2);
                     if(var8 == -1) {
                        var0.indexOf("</STYLE>", var2);
                     }

                     if(var8 > 0) {
                        String var14 = var0.substring(var5, var2);
                        var4.append(var14);
                        var5 = var8 + 8;
                        var2 = var0.indexOf(60, var5);
                        continue;
                     }
                  }
               }

               var8 = var0.indexOf(62, var2);
               if(var8 < 0) {
                  String var16 = var0.substring(var5);
                  var4.append(var16);
                  var5 = var3;
                  break;
               }

               String var21 = var0.substring(var5, var2);
               var4.append(var21);
               var5 = var8 + 1;
               var2 = var0.indexOf(60, var5);
            }

            int var18 = var0.length();
            if(var5 < var18) {
               String var19 = var0.substring(var5);
               var4.append(var19);
            }

            var1 = var4.toString();
         }
      }

      return var1;
   }
}
