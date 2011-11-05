package com.htc.android.mail;

import com.htc.android.mail.Headers;
import com.htc.android.mail.Mail;
import com.htc.android.mail.ll;
import java.util.ArrayList;

public class Imap4InfoParser {

   private static final boolean DEBUG = Mail.MAIL_DEBUG;
   private static final String TAG = "Imap4InfoParser";
   int alternativeNum;
   int attachment;
   String fieldFormat;
   int htmlbody;
   private String infoString = "";
   private ArrayList<String> mIndex;
   private ArrayList<String> mInfoReady;
   private ArrayList<String> mInfoString;
   int mixedNum;
   int related;
   int relatedNum;
   int textbody;
   int uid;


   public Imap4InfoParser(String var1) {
      ArrayList var2 = new ArrayList();
      this.mInfoString = var2;
      ArrayList var3 = new ArrayList();
      this.mInfoReady = var3;
      ArrayList var4 = new ArrayList();
      this.mIndex = var4;
      this.uid = 0;
      this.fieldFormat = "";
      this.attachment = 0;
      this.textbody = 0;
      this.htmlbody = 0;
      this.mixedNum = 0;
      this.alternativeNum = 0;
      this.related = 0;
      this.relatedNum = 0;
      this.parseInfo(var1);
   }

   private int parseFieldInfo(String var1) {
      boolean var2 = false;
      int var3 = 0;
      int var4 = 0;
      int var5 = 0;
      int var6 = 0;

      while(true) {
         int var7 = var1.length();
         if(var6 >= var7) {
            break;
         }

         if(var6 == 0 && var1.charAt(var6) == 34) {
            int var8 = var1.length();
            ArrayList var9 = this.mInfoReady;
            String var10 = var1.substring(0, var8);
            var9.add(var10);
            break;
         }

         if(var1.charAt(var6) == 40) {
            if(!var2) {
               var2 = true;
            }

            ++var4;
            if(var4 == 1) {
               var3 = var6 + 1;
            }
         }

         if(var1.charAt(var6) == 41 && var2) {
            ++var5;
         }

         if(var4 == var5 && var2) {
            if(DEBUG) {
               StringBuilder var13 = (new StringBuilder()).append("PARSED:");
               String var14 = var1.substring(var3, var6);
               String var15 = var13.append(var14).toString();
               ll.d("Imap4InfoParser", var15);
            }

            if(!var1.substring(var3, var6).startsWith("\"MESSAGE\"")) {
               ArrayList var16 = this.mInfoReady;
               String var17 = var1.substring(var3, var6);
               var16.add(var17);
            } else {
               if(DEBUG) {
                  ll.d("Imap4InfoParser", "unsupport type - message.");
               }

               boolean var20 = this.mInfoReady.add("MESSAGE NIL NIL NIL NIL NIL NIL NIL");
            }

            var3 = 0;
            var4 = 0;
            var5 = 0;
            var2 = false;
         }

         if(!var2 && var1.charAt(var6) == 34) {
            if(var3 == 0) {
               int var19 = var6 + 1;
            } else {
               String var22 = var1.substring(var3, var6);
               this.fieldFormat = var22;
               if(DEBUG) {
                  StringBuilder var23 = (new StringBuilder()).append("fieldFormat:");
                  String var24 = this.fieldFormat;
                  String var25 = var23.append(var24).toString();
                  ll.d("Imap4InfoParser", var25);
               }
            }
         }

         ++var6;
      }

      return this.mInfoReady.size();
   }

   private void parseInfo(String var1) {
      String[] var2 = var1.split(" ", 4);
      if(var2[0].equals("UID")) {
         int var3 = Integer.valueOf(var2[1]).intValue();
         this.uid = var3;
         if(DEBUG) {
            StringBuilder var4 = (new StringBuilder()).append("uid is:");
            int var5 = this.uid;
            String var6 = var4.append(var5).toString();
            ll.d("Imap4InfoParser", var6);
         }

         if(var2[2].equals("BODYSTRUCTURE")) {
            String var7 = var2[3];
            int var8 = var2[3].length() - 1;
            String var9 = var7.substring(1, var8);
            this.parseFieldInfo(var9);
         }

         if(this.fieldFormat.length() == 0 && this.mInfoReady.size() > 0) {
            this.mixedNum = 0;
            this.alternativeNum = 0;
            boolean var11 = this.mIndex.add("1");
         } else {
            int var45;
            if(this.fieldFormat.equalsIgnoreCase("MIXED") && this.mInfoReady.size() > 0) {
               int var43 = this.mInfoReady.size();
               this.mixedNum = var43;
               if(this.mixedNum >= 2) {
                  this.attachment = 1;
               }

               ArrayList var44 = (ArrayList)this.mInfoReady.clone();
               this.mInfoString = var44;
               this.mInfoReady.clear();
               var45 = 0;

               while(true) {
                  int var46 = this.mInfoString.size();
                  if(var45 >= var46) {
                     break;
                  }

                  if(((String)this.mInfoString.get(var45)).startsWith("(")) {
                     String var47 = (String)this.mInfoString.get(var45);
                     int var48 = ((String)this.mInfoString.get(var45)).length();
                     String var49 = var47.substring(0, var48);
                     if(DEBUG) {
                        String var50 = "tmp info=" + var49;
                        ll.d("Imap4InfoParser", var50);
                     }

                     this.parseFieldInfo(var49);
                     if(this.fieldFormat.equalsIgnoreCase("alternative")) {
                        int var52 = this.mInfoReady.size();
                        this.alternativeNum = var52;
                     } else if(DEBUG) {
                        ll.d("Imap4InfoParser", "unsupport type.");
                     }

                     int var53 = 1;

                     while(true) {
                        int var54 = this.mInfoReady.size();
                        if(var53 > var54) {
                           break;
                        }

                        ArrayList var55 = this.mIndex;
                        StringBuilder var56 = new StringBuilder();
                        String var57 = String.valueOf(var45 + 1);
                        String var58 = var56.append(var57).append(".").append(var53).toString();
                        var55.add(var58);
                        ++var53;
                     }
                  } else {
                     ArrayList var60 = this.mInfoReady;
                     Object var61 = this.mInfoString.get(var45);
                     var60.add(var61);
                     ArrayList var63 = this.mIndex;
                     String var64 = String.valueOf(var45 + 1);
                     var63.add(var64);
                  }

                  ++var45;
               }
            } else if(this.fieldFormat.equalsIgnoreCase("RELATED") && this.mInfoReady.size() > 0) {
               this.related = 1;
               int var66 = this.mInfoReady.size();
               this.relatedNum = var66;
               ArrayList var67 = (ArrayList)this.mInfoReady.clone();
               this.mInfoString = var67;
               this.mInfoReady.clear();
               var45 = 0;

               while(true) {
                  int var68 = this.mInfoString.size();
                  if(var45 >= var68) {
                     break;
                  }

                  if(((String)this.mInfoString.get(var45)).startsWith("(")) {
                     String var69 = (String)this.mInfoString.get(var45);
                     int var70 = ((String)this.mInfoString.get(var45)).length();
                     String var71 = var69.substring(0, var70);
                     if(DEBUG) {
                        String var72 = "tmp info=" + var71;
                        ll.d("Imap4InfoParser", var72);
                     }

                     this.parseFieldInfo(var71);
                     if(this.fieldFormat.equalsIgnoreCase("alternative")) {
                        int var74 = this.mInfoReady.size();
                        this.alternativeNum = var74;
                     } else if(DEBUG) {
                        ll.d("Imap4InfoParser", "unsupport type.");
                     }

                     int var75 = 1;

                     while(true) {
                        int var76 = this.mInfoReady.size();
                        if(var75 > var76) {
                           break;
                        }

                        ArrayList var77 = this.mIndex;
                        StringBuilder var78 = new StringBuilder();
                        String var79 = String.valueOf(var45 + 1);
                        String var80 = var78.append(var79).append(".").append(var75).toString();
                        var77.add(var80);
                        ++var75;
                     }
                  } else {
                     ArrayList var82 = this.mInfoReady;
                     Object var83 = this.mInfoString.get(var45);
                     var82.add(var83);
                     ArrayList var85 = this.mIndex;
                     String var86 = String.valueOf(var45 + 1);
                     var85.add(var86);
                  }

                  ++var45;
               }
            } else if(this.fieldFormat.equalsIgnoreCase("alternative") && this.mInfoReady.size() > 0) {
               int var88 = this.mInfoReady.size();
               this.alternativeNum = var88;
               var45 = 1;

               while(true) {
                  int var89 = this.mInfoReady.size();
                  if(var45 > var89) {
                     break;
                  }

                  ArrayList var90 = this.mIndex;
                  String var91 = String.valueOf(var45);
                  var90.add(var91);
                  ++var45;
               }
            }
         }

         if(DEBUG) {
            StringBuilder var12 = (new StringBuilder()).append("mix:");
            int var13 = this.mixedNum;
            StringBuilder var14 = var12.append(var13).append(",alternative:");
            int var15 = this.alternativeNum;
            StringBuilder var16 = var14.append(var15).append(", related:");
            int var17 = this.related;
            StringBuilder var18 = var16.append(var17).append(", relatedNum:");
            int var19 = this.relatedNum;
            String var20 = var18.append(var19).toString();
            ll.d("Imap4InfoParser", var20);
         }

         int var21 = 0;

         while(true) {
            int var22 = this.mInfoReady.size();
            if(var21 >= var22) {
               return;
            }

            String var23 = ((String)this.mInfoReady.get(var21)).replace("(", "").replace(")", "").replace("\"", "");
            if(DEBUG) {
               String var24 = "clearInfo:" + var23;
               ll.d("Imap4InfoParser", var24);
            }

            String[] var25 = var23.split(" ");
            if(var25[0].equalsIgnoreCase("TEXT")) {
               if(this.mixedNum < 2 && this.relatedNum < 2) {
                  if(this.alternativeNum >= 2) {
                     if(var25[1].equalsIgnoreCase("PLAIN")) {
                        this.textbody = 1;
                     } else if(var25[1].equalsIgnoreCase("HTML")) {
                        this.htmlbody = 1;
                     }
                  } else if(var25[1].equalsIgnoreCase("PLAIN")) {
                     this.textbody = 1;
                  } else if(var25[1].equalsIgnoreCase("HTML")) {
                     this.htmlbody = 1;
                  }
               } else if(var25[1].equalsIgnoreCase("PLAIN")) {
                  if(((String)this.mIndex.get(var21)).startsWith("1")) {
                     this.textbody = 1;
                  }
               } else if(var25[1].equalsIgnoreCase("HTML") && ((String)this.mIndex.get(var21)).startsWith("1")) {
                  this.htmlbody = 1;
               }
            } else if(var25[0].equalsIgnoreCase("MESSAGE") && DEBUG) {
               ll.d("Imap4InfoParser", "It\'s message mail.");
            }

            if(this.infoString.length() == 0) {
               if(var25[2].equalsIgnoreCase("NIL")) {
                  StringBuilder var26 = new StringBuilder();
                  String var27 = var25[0];
                  StringBuilder var28 = var26.append(var27).append(":");
                  String var29 = var25[1];
                  StringBuilder var30 = var28.append(var29).append(":");
                  String var31 = var25[2];
                  StringBuilder var32 = var30.append(var31).append(":");
                  String var33 = var25[3];
                  StringBuilder var34 = var32.append(var33).append(":");
                  String var35 = var25[4];
                  StringBuilder var36 = var34.append(var35).append(":");
                  String var37 = var25[5];
                  StringBuilder var38 = var36.append(var37).append(":");
                  String var39 = var25[6];
                  StringBuilder var40 = var38.append(var39).append(":");
                  String var41 = (String)this.mIndex.get(var21);
                  String var42 = var40.append(var41).toString();
                  this.infoString = var42;
               } else if(var25[2].equalsIgnoreCase("NAME")) {
                  StringBuilder var93 = new StringBuilder();
                  String var94 = var25[0];
                  StringBuilder var95 = var93.append(var94).append(":");
                  String var96 = var25[1];
                  StringBuilder var97 = var95.append(var96).append(":");
                  String var98 = var25[3];
                  StringBuilder var99 = var97.append(var98).append(":");
                  String var100 = var25[4];
                  StringBuilder var101 = var99.append(var100).append(":");
                  String var102 = var25[5];
                  StringBuilder var103 = var101.append(var102).append(":");
                  String var104 = var25[6];
                  StringBuilder var105 = var103.append(var104).append(":");
                  String var106 = var25[7];
                  StringBuilder var107 = var105.append(var106).append(":");
                  String var108 = (String)this.mIndex.get(var21);
                  String var109 = var107.append(var108).toString();
                  this.infoString = var109;
               } else if(var25[2].equalsIgnoreCase("CHARSET") && var25[4].equalsIgnoreCase("format")) {
                  if(var25.length == 13) {
                     StringBuilder var110 = new StringBuilder();
                     String var111 = var25[0];
                     StringBuilder var112 = var110.append(var111).append(":");
                     String var113 = var25[1];
                     StringBuilder var114 = var112.append(var113).append(":");
                     String var115 = Headers.rfc2047(var25[3]);
                     StringBuilder var116 = var114.append(var115).append(":");
                     String var117 = var25[4];
                     StringBuilder var118 = var116.append(var117).append(":");
                     String var119 = var25[5];
                     StringBuilder var120 = var118.append(var119).append(":");
                     String var121 = var25[10];
                     StringBuilder var122 = var120.append(var121).append(":");
                     String var123 = var25[11];
                     StringBuilder var124 = var122.append(var123).append(":");
                     String var125 = (String)this.mIndex.get(var21);
                     String var126 = var124.append(var125).toString();
                     this.infoString = var126;
                  } else if(var25.length == 11) {
                     StringBuilder var127 = new StringBuilder();
                     String var128 = var25[0];
                     StringBuilder var129 = var127.append(var128).append(":");
                     String var130 = var25[1];
                     StringBuilder var131 = var129.append(var130).append(":");
                     String var132 = Headers.rfc2047(var25[3]);
                     StringBuilder var133 = var131.append(var132).append(":");
                     String var134 = var25[4];
                     StringBuilder var135 = var133.append(var134).append(":");
                     String var136 = var25[5];
                     StringBuilder var137 = var135.append(var136).append(":");
                     String var138 = var25[8];
                     StringBuilder var139 = var137.append(var138).append(":");
                     String var140 = var25[9];
                     StringBuilder var141 = var139.append(var140).append(":");
                     String var142 = (String)this.mIndex.get(var21);
                     String var143 = var141.append(var142).toString();
                     this.infoString = var143;
                  }
               } else if(var25[2].equalsIgnoreCase("CHARSET")) {
                  StringBuilder var144 = new StringBuilder();
                  String var145 = var25[0];
                  StringBuilder var146 = var144.append(var145).append(":");
                  String var147 = var25[1];
                  StringBuilder var148 = var146.append(var147).append(":");
                  String var149 = var25[3];
                  StringBuilder var150 = var148.append(var149).append(":");
                  String var151 = var25[4];
                  StringBuilder var152 = var150.append(var151).append(":");
                  String var153 = var25[5];
                  StringBuilder var154 = var152.append(var153).append(":");
                  String var155 = var25[6];
                  StringBuilder var156 = var154.append(var155).append(":");
                  String var157 = var25[7];
                  StringBuilder var158 = var156.append(var157).append(":");
                  String var159 = (String)this.mIndex.get(var21);
                  String var160 = var158.append(var159).toString();
                  this.infoString = var160;
               } else if(DEBUG) {
                  String var161 = "Dismiss:" + var23;
                  ll.d("Imap4InfoParser", var161);
               }
            } else if(var25[0].equalsIgnoreCase("MESSAGE")) {
               StringBuilder var162 = new StringBuilder();
               String var163 = this.infoString;
               StringBuilder var164 = var162.append(var163).append(";");
               String var165 = var25[0];
               StringBuilder var166 = var164.append(var165).append(":NIL:NIL:NIL:NIL:NIL:NIL:");
               String var167 = (String)this.mIndex.get(var21);
               String var168 = var166.append(var167).toString();
               this.infoString = var168;
            } else if(var25[2].equalsIgnoreCase("NIL")) {
               StringBuilder var169 = new StringBuilder();
               String var170 = this.infoString;
               StringBuilder var171 = var169.append(var170).append(";");
               String var172 = var25[0];
               StringBuilder var173 = var171.append(var172).append(":");
               String var174 = var25[1];
               StringBuilder var175 = var173.append(var174).append(":");
               String var176 = var25[2];
               StringBuilder var177 = var175.append(var176).append(":");
               String var178 = var25[3];
               StringBuilder var179 = var177.append(var178).append(":");
               String var180 = var25[4];
               StringBuilder var181 = var179.append(var180).append(":");
               String var182 = var25[5];
               StringBuilder var183 = var181.append(var182).append(":");
               String var184 = var25[6];
               StringBuilder var185 = var183.append(var184).append(":");
               String var186 = (String)this.mIndex.get(var21);
               String var187 = var185.append(var186).toString();
               this.infoString = var187;
            } else if(var25[2].equalsIgnoreCase("NAME")) {
               StringBuilder var188 = new StringBuilder();
               String var189 = this.infoString;
               StringBuilder var190 = var188.append(var189).append(";");
               String var191 = var25[0];
               StringBuilder var192 = var190.append(var191).append(":");
               String var193 = var25[1];
               StringBuilder var194 = var192.append(var193).append(":");
               String var195 = Headers.rfc2047(var25[3]);
               StringBuilder var196 = var194.append(var195).append(":");
               String var197 = var25[4];
               StringBuilder var198 = var196.append(var197).append(":");
               String var199 = var25[5];
               StringBuilder var200 = var198.append(var199).append(":");
               String var201 = var25[6];
               StringBuilder var202 = var200.append(var201).append(":");
               String var203 = var25[7];
               StringBuilder var204 = var202.append(var203).append(":");
               String var205 = (String)this.mIndex.get(var21);
               String var206 = var204.append(var205).toString();
               this.infoString = var206;
            } else if(var25[2].equalsIgnoreCase("CHARSET") && var25[4].equalsIgnoreCase("format")) {
               if(var25.length == 13) {
                  StringBuilder var207 = new StringBuilder();
                  String var208 = this.infoString;
                  StringBuilder var209 = var207.append(var208);
                  String var210 = var25[0];
                  StringBuilder var211 = var209.append(var210).append(":");
                  String var212 = var25[1];
                  StringBuilder var213 = var211.append(var212).append(":");
                  String var214 = Headers.rfc2047(var25[3]);
                  StringBuilder var215 = var213.append(var214).append(":");
                  String var216 = var25[4];
                  StringBuilder var217 = var215.append(var216).append(":");
                  String var218 = var25[5];
                  StringBuilder var219 = var217.append(var218).append(":");
                  String var220 = var25[10];
                  StringBuilder var221 = var219.append(var220).append(":");
                  String var222 = var25[11];
                  StringBuilder var223 = var221.append(var222).append(":");
                  String var224 = (String)this.mIndex.get(var21);
                  String var225 = var223.append(var224).toString();
                  this.infoString = var225;
               } else if(var25.length == 11) {
                  StringBuilder var226 = new StringBuilder();
                  String var227 = this.infoString;
                  StringBuilder var228 = var226.append(var227);
                  String var229 = var25[0];
                  StringBuilder var230 = var228.append(var229).append(":");
                  String var231 = var25[1];
                  StringBuilder var232 = var230.append(var231).append(":");
                  String var233 = Headers.rfc2047(var25[3]);
                  StringBuilder var234 = var232.append(var233).append(":");
                  String var235 = var25[4];
                  StringBuilder var236 = var234.append(var235).append(":");
                  String var237 = var25[5];
                  StringBuilder var238 = var236.append(var237).append(":");
                  String var239 = var25[8];
                  StringBuilder var240 = var238.append(var239).append(":");
                  String var241 = var25[9];
                  StringBuilder var242 = var240.append(var241).append(":");
                  String var243 = (String)this.mIndex.get(var21);
                  String var244 = var242.append(var243).toString();
                  this.infoString = var244;
               }
            } else if(var25[2].equalsIgnoreCase("CHARSET")) {
               StringBuilder var245 = new StringBuilder();
               String var246 = this.infoString;
               StringBuilder var247 = var245.append(var246).append(";");
               String var248 = var25[0];
               StringBuilder var249 = var247.append(var248).append(":");
               String var250 = var25[1];
               StringBuilder var251 = var249.append(var250).append(":");
               String var252 = Headers.rfc2047(var25[3]);
               StringBuilder var253 = var251.append(var252).append(":");
               String var254 = var25[4];
               StringBuilder var255 = var253.append(var254).append(":");
               String var256 = var25[5];
               StringBuilder var257 = var255.append(var256).append(":");
               String var258 = var25[6];
               StringBuilder var259 = var257.append(var258).append(":");
               String var260 = var25[7];
               StringBuilder var261 = var259.append(var260).append(":");
               String var262 = (String)this.mIndex.get(var21);
               String var263 = var261.append(var262).toString();
               this.infoString = var263;
            } else if(DEBUG) {
               String var264 = "Dismiss:" + var23;
               ll.d("Imap4InfoParser", var264);
            }

            ++var21;
         }
      }
   }

   public final int alternativeNum() {
      return this.alternativeNum;
   }

   public final String getInfo() {
      return this.infoString;
   }

   public final int getRelated() {
      return this.related;
   }

   public final int getRelatedNum() {
      return this.relatedNum;
   }

   public final int getuid() {
      return this.uid;
   }

   public final int includeAttach() {
      return this.attachment;
   }

   public final int includeHtml() {
      return this.htmlbody;
   }

   public final int includeText() {
      return this.textbody;
   }

   public final int mixedNum() {
      return this.mixedNum;
   }
}
