package com.htc.android.mail.pim.vcard;

import com.htc.android.mail.pim.VBuilder;
import com.htc.android.mail.pim.VParser;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VCardParser_V21 extends VParser {

   private static final HashSet<String> mKnownTypeSet;
   private static final HashSet<String> mName;


   static {
      String[] var0 = new String[]{"DOM", "INTL", "POSTAL", "PARCEL", "HOME", "WORK", "PREF", "VOICE", "FAX", "MSG", "CELL", "PAGER", "BBS", "MODEM", "CAR", "ISDN", "VIDEO", "AOL", "APPLELINK", "ATTMAIL", "CIS", "EWORLD", "INTERNET", "IBMMAIL", "MCIMAIL", "POWERSHARE", "PRODIGY", "TLX", "X400", "GIF", "CGM", "WMF", "BMP", "MET", "PMB", "DIB", "PICT", "TIFF", "PDF", "PS", "JPEG", "QTIME", "MPEG", "MPEG2", "AVI", "WAVE", "AIFF", "PCM", "X509", "PGP", "X-OTHER"};
      List var1 = Arrays.asList(var0);
      mKnownTypeSet = new HashSet(var1);
      String[] var2 = new String[]{"LOGO", "PHOTO", "LABEL", "FN", "TITLE", "SOUND", "VERSION", "TEL", "EMAIL", "TZ", "GEO", "NOTE", "URL", "BDAY", "ROLE", "REV", "UID", "KEY", "MAILER", "X-IM", "X-ORGTYPE", "X-TEL-CUSTOM-TYPE", "X-ADR-CUSTOM-TYPE", "X-EMAIL-CUSTOM-TYPE"};
      List var3 = Arrays.asList(var2);
      mName = new HashSet(var3);
   }

   public VCardParser_V21() {}

   private String escapeTranslator(String var1) {
      String var2;
      if(var1 == null) {
         var2 = null;
      } else {
         var2 = var1.replace("\\\\", "\n\r\n").replace("\\:", ":").replace("\\,", ",").replace("\n\r\n", "\\");
      }

      return var2;
   }

   private int parseGroups(int var1) {
      boolean var2 = false;
      int var3 = this.parseWord(var1);
      int var4;
      if(var3 == -1) {
         var4 = -1;
      } else {
         var1 += var3;
         int var8 = 0 + var3;

         while(true) {
            var3 = this.parseString(var1, ".", (boolean)0);
            if(var3 == -1) {
               break;
            }

            int var5 = this.parseWord(var1);
            if(var5 == -1) {
               break;
            }

            int var6 = var3 + var5;
            var1 += var6;
            int var7 = var3 + var5;
            var8 += var7;
         }

         var4 = var8;
      }

      return var4;
   }

   private int parseGroupsWithDot(int var1) {
      boolean var2 = false;
      int var3 = this.parseGroups(var1);
      int var4;
      if(var3 == -1) {
         var4 = -1;
      } else {
         int var5 = var1 + var3;
         int var6 = 0 + var3;
         var3 = this.parseString(var5, ".", (boolean)0);
         if(var3 == -1) {
            var4 = -1;
         } else {
            var4 = var6 + var3;
         }
      }

      return var4;
   }

   private int parseItem(int var1) {
      this.mEncoding = "8BIT";
      int var2 = this.parseItem0(var1);
      int var3;
      if(var2 != -1) {
         var3 = 0 + var2;
      } else {
         var2 = this.parseItem1(var1);
         if(var2 != -1) {
            var3 = 0 + var2;
         } else {
            var2 = this.parseItem2(var1);
            if(var2 != -1) {
               var3 = 0 + var2;
            } else {
               var3 = -1;
            }
         }
      }

      return var3;
   }

   private int parseItem0(int var1) {
      int var2 = 0;
      int var3 = var1;
      String var4 = "";
      int var5 = this.parseGroupsWithDot(var1);
      if(var5 != -1) {
         var1 += var5;
         var2 = 0 + var5;
      }

      int var6 = this.parseName(var1);
      int var7;
      if(var6 == -1) {
         var7 = -1;
      } else {
         var1 += var6;
         var2 += var6;
         if(this.mBuilder != null) {
            var4 = this.mBuffer.substring(var3, var1).trim();
            this.mBuilder.propertyName(var4);
         }

         int var8 = this.parseParams(var1);
         if(var8 != -1) {
            int var10000 = var1 + var8;
            var10000 = var2 + var8;
         }

         var6 = this.parseString(var1, ":", (boolean)0);
         if(var6 == -1) {
            var7 = -1;
         } else {
            var1 += var6;
            var2 += var6;
            var3 = var1;
            var6 = this.parseValue(var1);
            if(var6 == -1) {
               var7 = -1;
            } else {
               var1 += var6;
               var2 += var6;
               String var11 = this.mBuffer.substring(var3, var1);
               if(var4.equals("VERSION") && !var11.equals("2.1")) {
                  var7 = -1;
               } else {
                  if(this.mBuilder != null) {
                     ArrayList var12 = new ArrayList();
                     var12.add(var11);
                     this.mBuilder.propertyValues(var12);
                  }

                  var6 = this.parseCrlf(var1);
                  if(var6 == -1) {
                     var7 = -1;
                  } else {
                     var7 = var2 + var6;
                  }
               }
            }
         }
      }

      return var7;
   }

   private int parseItem1(int var1) {
      int var2 = 0;
      int var3 = var1;
      int var4 = this.parseGroupsWithDot(var1);
      if(var4 != -1) {
         var1 += var4;
         var2 = 0 + var4;
      }

      int var5 = this.parseString(var1, "ADR", (boolean)1);
      int var6;
      if(var5 == -1) {
         var5 = this.parseString(var1, "ORG", (boolean)1);
         if(var5 == -1) {
            var5 = this.parseString(var1, "N", (boolean)1);
            if(var5 == -1) {
               var6 = -1;
               return var6;
            }
         }
      }

      var1 += var5;
      var2 += var5;
      if(this.mBuilder != null) {
         VBuilder var7 = this.mBuilder;
         String var8 = this.mBuffer.substring(var3, var1).trim();
         var7.propertyName(var8);
      }

      int var9 = this.parseParams(var1);
      if(var9 != -1) {
         int var10000 = var1 + var9;
         var10000 = var2 + var9;
      }

      var5 = this.parseString(var1, ":", (boolean)0);
      if(var5 == -1) {
         var6 = -1;
      } else {
         var1 += var5;
         var2 += var5;
         var3 = var1;
         var5 = this.parseValue(var1);
         if(var5 == -1) {
            var6 = -1;
         } else {
            var1 += var5;
            var2 += var5;
            if(this.mBuilder != null) {
               ArrayList var12 = new ArrayList();
               Pattern var13 = Pattern.compile("([^;\\\\]*((\\\\[\\\\;:,])*[^;\\\\]*)*)(;?)");
               String var14 = this.mBuffer.substring(var3, var1);
               Matcher var15 = var13.matcher(var14);

               while(var15.find()) {
                  String var16 = var15.group(1);
                  String var17 = this.escapeTranslator(var16);
                  var12.add(var17);
                  int var19 = var15.end();
                  int var20 = var3 + var19;
                  if(var1 == var20) {
                     String var21 = var15.group(4);
                     if(";".equals(var21)) {
                        boolean var22 = var12.add("");
                     }
                     break;
                  }
               }

               this.mBuilder.propertyValues(var12);
            }

            var5 = this.parseCrlf(var1);
            if(var5 == -1) {
               var6 = -1;
            } else {
               var6 = var2 + var5;
            }
         }
      }

      return var6;
   }

   private int parseItem2(int var1) {
      int var2 = 0;
      int var3 = var1;
      int var4 = this.parseGroupsWithDot(var1);
      if(var4 != -1) {
         var1 += var4;
         var2 = 0 + var4;
      }

      int var5 = this.parseString(var1, "AGENT", (boolean)1);
      int var6;
      if(var5 == -1) {
         var6 = -1;
      } else {
         var1 += var5;
         var2 += var5;
         if(this.mBuilder != null) {
            VBuilder var7 = this.mBuilder;
            String var8 = this.mBuffer.substring(var3, var1);
            var7.propertyName(var8);
         }

         int var9 = this.parseParams(var1);
         int var10000;
         if(var9 != -1) {
            var10000 = var1 + var9;
            var10000 = var2 + var9;
         }

         var5 = this.parseString(var1, ":", (boolean)0);
         if(var5 == -1) {
            var6 = -1;
         } else {
            var1 += var5;
            var2 += var5;
            int var12 = this.parseCrlf(var1);
            if(var12 != -1) {
               var10000 = var1 + var12;
               var10000 = var2 + var12;
            }

            var5 = this.parseVCard(var1);
            if(var5 == -1) {
               var6 = -1;
            } else {
               var1 += var5;
               var2 += var5;
               if(this.mBuilder != null) {
                  VBuilder var15 = this.mBuilder;
                  ArrayList var16 = new ArrayList();
                  var15.propertyValues(var16);
               }

               var5 = this.parseCrlf(var1);
               if(var5 == -1) {
                  var6 = -1;
               } else {
                  var6 = var2 + var5;
               }
            }
         }
      }

      return var6;
   }

   private int parseItems(int var1) {
      boolean var2 = false;
      if(this.mBuilder != null) {
         this.mBuilder.startProperty();
      }

      int var3 = this.parseItem(var1);
      int var4;
      if(var3 == -1) {
         var4 = -1;
         return var4;
      } else {
         var1 += var3;
         int var7 = 0 + var3;
         if(this.mBuilder != null) {
            this.mBuilder.endProperty();
         }

         while(true) {
            while(true) {
               var3 = this.parseCrlf(var1);
               if(var3 == -1) {
                  if(this.mBuilder != null) {
                     this.mBuilder.startProperty();
                  }

                  var3 = this.parseItem(var1);
                  if(var3 == -1) {
                     var4 = var7;
                     return var4;
                  }

                  var1 += var3;
                  var7 += var3;
                  if(this.mBuilder != null) {
                     this.mBuilder.endProperty();
                  }
               } else {
                  int var10000 = var1 + var3;
                  var10000 = var7 + var3;
               }
            }
         }
      }
   }

   private int parseKnownType(int var1) {
      String var2 = this.getWord(var1);
      HashSet var3 = mKnownTypeSet;
      String var4 = var2.toUpperCase();
      int var5;
      if(var3.contains(var4)) {
         var5 = var2.length();
      } else {
         var5 = -1;
      }

      return var5;
   }

   private int parseName(int var1) {
      int var2 = this.parseXWord(var1);
      int var3;
      if(var2 != -1) {
         var3 = var2;
      } else {
         String var4 = this.getWord(var1).toUpperCase();
         if(mName.contains(var4)) {
            var3 = var4.length();
         } else {
            var3 = -1;
         }
      }

      return var3;
   }

   private int parsePTypeVal(int var1) {
      int var2 = this.parseKnownType(var1);
      int var3;
      if(var2 != -1) {
         var3 = 0 + var2;
      } else {
         var2 = this.parseXWord(var1);
         if(var2 != -1) {
            var3 = 0 + var2;
         } else {
            var3 = 0 + var2;
         }
      }

      return var3;
   }

   private int parseParam(int var1) {
      int var2 = -1;
      boolean var3 = false;
      int var4 = this.parseParam0(var1);
      if(var4 != var2) {
         var2 = 0 + var4;
      } else {
         var4 = this.parseParam1(var1);
         if(var4 != -1) {
            var2 = 0 + var4;
         } else {
            var4 = this.parseParam2(var1);
            if(var4 != -1) {
               var2 = 0 + var4;
            } else {
               var4 = this.parseParam3(var1);
               if(var4 != -1) {
                  var2 = 0 + var4;
               } else {
                  var4 = this.parseParam4(var1);
                  if(var4 != -1) {
                     var2 = 0 + var4;
                  } else {
                     var4 = this.parseParam5(var1);
                     if(var4 != -1) {
                        var2 = 0 + var4;
                     } else {
                        int var5 = var1;
                        var4 = this.parseKnownType(var1);
                        if(var4 == -1) {
                           var4 = this.parseUnknownType(var1);
                           if(var4 == -1) {
                              return var2;
                           }
                        }

                        var1 += var4;
                        int var9 = 0 + var4;
                        if(this.mBuilder != null) {
                           this.mBuilder.propertyParamType((String)null);
                           this.mBuffer.substring(var5, var1);
                           VBuilder var7 = this.mBuilder;
                           String var8 = this.mBuffer.substring(var5, var1);
                           var7.propertyParamValue(var8);
                        }

                        var2 = var9;
                     }
                  }
               }
            }
         }
      }

      return var2;
   }

   private int parseParam0(int var1) {
      boolean var2 = false;
      int var4 = this.parseString(var1, "TYPE", (boolean)1);
      int var5;
      if(var4 == -1) {
         var5 = -1;
      } else {
         int var6 = var1 + var4;
         int var7 = 0 + var4;
         if(this.mBuilder != null) {
            VBuilder var8 = this.mBuilder;
            String var9 = this.mBuffer.substring(var1, var6);
            var8.propertyParamType(var9);
         }

         int var10 = this.removeWs(var6);
         var1 = var6 + var10;
         int var16 = var7 + var10;
         var4 = this.parseString(var1, "=", (boolean)0);
         if(var4 == -1) {
            var5 = -1;
         } else {
            int var11 = var1 + var4;
            int var12 = var16 + var4;
            int var13 = this.removeWs(var11);
            var1 = var11 + var13;
            var16 = var12 + var13;
            int var3 = var1;
            var4 = this.parsePTypeVal(var1);
            if(var4 == -1) {
               var5 = -1;
            } else {
               var1 += var4;
               var16 += var4;
               if(this.mBuilder != null) {
                  VBuilder var14 = this.mBuilder;
                  String var15 = this.mBuffer.substring(var3, var1);
                  var14.propertyParamValue(var15);
               }

               var5 = var16;
            }
         }
      }

      return var5;
   }

   private int parseParam1(int var1) {
      boolean var2 = false;
      int var4 = this.parseString(var1, "VALUE", (boolean)1);
      int var5;
      if(var4 == -1) {
         var5 = -1;
      } else {
         int var6 = var1 + var4;
         int var7 = 0 + var4;
         if(this.mBuilder != null) {
            VBuilder var8 = this.mBuilder;
            String var9 = this.mBuffer.substring(var1, var6);
            var8.propertyParamType(var9);
         }

         int var10 = this.removeWs(var6);
         var1 = var6 + var10;
         int var16 = var7 + var10;
         var4 = this.parseString(var1, "=", (boolean)0);
         if(var4 == -1) {
            var5 = -1;
         } else {
            int var11 = var1 + var4;
            int var12 = var16 + var4;
            int var13 = this.removeWs(var11);
            var1 = var11 + var13;
            var16 = var12 + var13;
            int var3 = var1;
            var4 = this.parsePValueVal(var1);
            if(var4 == -1) {
               var5 = -1;
            } else {
               var1 += var4;
               var16 += var4;
               if(this.mBuilder != null) {
                  VBuilder var14 = this.mBuilder;
                  String var15 = this.mBuffer.substring(var3, var1);
                  var14.propertyParamValue(var15);
               }

               var5 = var16;
            }
         }
      }

      return var5;
   }

   private int parseParam2(int var1) {
      boolean var2 = false;
      int var4 = this.parseString(var1, "ENCODING", (boolean)1);
      int var5;
      if(var4 == -1) {
         var5 = -1;
      } else {
         int var6 = var1 + var4;
         int var7 = 0 + var4;
         if(this.mBuilder != null) {
            VBuilder var8 = this.mBuilder;
            String var9 = this.mBuffer.substring(var1, var6);
            var8.propertyParamType(var9);
         }

         int var10 = this.removeWs(var6);
         var1 = var6 + var10;
         int var16 = var7 + var10;
         var4 = this.parseString(var1, "=", (boolean)0);
         if(var4 == -1) {
            var5 = -1;
         } else {
            int var11 = var1 + var4;
            int var12 = var16 + var4;
            int var13 = this.removeWs(var11);
            var1 = var11 + var13;
            var16 = var12 + var13;
            int var3 = var1;
            var4 = this.parsePEncodingVal(var1);
            if(var4 == -1) {
               var5 = -1;
            } else {
               var1 += var4;
               var16 += var4;
               if(this.mBuilder != null) {
                  VBuilder var14 = this.mBuilder;
                  String var15 = this.mBuffer.substring(var3, var1);
                  var14.propertyParamValue(var15);
               }

               var5 = var16;
            }
         }
      }

      return var5;
   }

   private int parseParam3(int var1) {
      boolean var2 = false;
      int var4 = this.parseString(var1, "CHARSET", (boolean)1);
      int var5;
      if(var4 == -1) {
         var5 = -1;
      } else {
         int var6 = var1 + var4;
         int var7 = 0 + var4;
         if(this.mBuilder != null) {
            VBuilder var8 = this.mBuilder;
            String var9 = this.mBuffer.substring(var1, var6);
            var8.propertyParamType(var9);
         }

         int var10 = this.removeWs(var6);
         var1 = var6 + var10;
         int var16 = var7 + var10;
         var4 = this.parseString(var1, "=", (boolean)0);
         if(var4 == -1) {
            var5 = -1;
         } else {
            int var11 = var1 + var4;
            int var12 = var16 + var4;
            int var13 = this.removeWs(var11);
            var1 = var11 + var13;
            var16 = var12 + var13;
            int var3 = var1;
            var4 = this.parseCharsetVal(var1);
            if(var4 == -1) {
               var5 = -1;
            } else {
               var1 += var4;
               var16 += var4;
               if(this.mBuilder != null) {
                  VBuilder var14 = this.mBuilder;
                  String var15 = this.mBuffer.substring(var3, var1);
                  var14.propertyParamValue(var15);
               }

               var5 = var16;
            }
         }
      }

      return var5;
   }

   private int parseParam4(int var1) {
      boolean var2 = false;
      int var4 = this.parseString(var1, "LANGUAGE", (boolean)1);
      int var5;
      if(var4 == -1) {
         var5 = -1;
      } else {
         int var6 = var1 + var4;
         int var7 = 0 + var4;
         if(this.mBuilder != null) {
            VBuilder var8 = this.mBuilder;
            String var9 = this.mBuffer.substring(var1, var6);
            var8.propertyParamType(var9);
         }

         int var10 = this.removeWs(var6);
         var1 = var6 + var10;
         int var16 = var7 + var10;
         var4 = this.parseString(var1, "=", (boolean)0);
         if(var4 == -1) {
            var5 = -1;
         } else {
            int var11 = var1 + var4;
            int var12 = var16 + var4;
            int var13 = this.removeWs(var11);
            var1 = var11 + var13;
            var16 = var12 + var13;
            int var3 = var1;
            var4 = this.parseLangVal(var1);
            if(var4 == -1) {
               var5 = -1;
            } else {
               var1 += var4;
               var16 += var4;
               if(this.mBuilder != null) {
                  VBuilder var14 = this.mBuilder;
                  String var15 = this.mBuffer.substring(var3, var1);
                  var14.propertyParamValue(var15);
               }

               var5 = var16;
            }
         }
      }

      return var5;
   }

   private int parseParam5(int var1) {
      boolean var2 = false;
      int var4 = this.parseXWord(var1);
      int var5;
      if(var4 == -1) {
         var5 = -1;
      } else {
         int var6 = var1 + var4;
         int var7 = 0 + var4;
         if(this.mBuilder != null) {
            VBuilder var8 = this.mBuilder;
            String var9 = this.mBuffer.substring(var1, var6);
            var8.propertyParamType(var9);
         }

         int var10 = this.removeWs(var6);
         var1 = var6 + var10;
         int var16 = var7 + var10;
         var4 = this.parseString(var1, "=", (boolean)0);
         if(var4 == -1) {
            var5 = -1;
         } else {
            int var11 = var1 + var4;
            int var12 = var16 + var4;
            int var13 = this.removeWs(var11);
            var1 = var11 + var13;
            var16 = var12 + var13;
            int var3 = var1;
            var4 = this.parseWord(var1);
            if(var4 == -1) {
               var5 = -1;
            } else {
               var1 += var4;
               var16 += var4;
               if(this.mBuilder != null) {
                  VBuilder var14 = this.mBuilder;
                  String var15 = this.mBuffer.substring(var3, var1);
                  var14.propertyParamValue(var15);
               }

               var5 = var16;
            }
         }
      }

      return var5;
   }

   private int parseParamList(int var1) {
      boolean var2 = false;
      int var3 = this.parseParam(var1);
      int var4;
      if(var3 == -1) {
         var4 = -1;
      } else {
         int var5 = var1 + var3;
         int var14 = 0 + var3;
         int var6 = var5;
         int var7 = var14;

         while(true) {
            int var8 = this.removeWs(var6);
            int var9 = var6 + var8;
            int var10 = var7 + var8;
            var3 = this.parseString(var9, ";", (boolean)0);
            if(var3 == -1) {
               var4 = var14;
               break;
            }

            int var11 = var9 + var3;
            int var12 = var10 + var3;
            int var13 = this.removeWs(var11);
            var9 = var11 + var13;
            var10 = var12 + var13;
            var3 = this.parseParam(var9);
            if(var3 == -1) {
               var4 = var14;
               break;
            }

            var6 = var9 + var3;
            var7 = var10 + var3;
            var14 = var7;
         }
      }

      return var4;
   }

   private int parseParams(int var1) {
      boolean var2 = false;
      int var3 = this.parseString(var1, ";", (boolean)0);
      int var4;
      if(var3 == -1) {
         var4 = -1;
      } else {
         int var5 = var1 + var3;
         int var6 = 0 + var3;
         int var7 = this.removeWs(var5);
         int var8 = var5 + var7;
         int var9 = var6 + var7;
         var3 = this.parseParamList(var8);
         if(var3 == -1) {
            var4 = -1;
         } else {
            var4 = var9 + var3;
         }
      }

      return var4;
   }

   private int parseUnknownType(int var1) {
      return this.getWord(var1).length();
   }

   private int parseVCard(int var1) {
      boolean var2 = false;
      int var3 = this.parseString(var1, "BEGIN", (boolean)0);
      int var4;
      if(var3 == -1) {
         var4 = -1;
      } else {
         int var5 = var1 + var3;
         int var6 = 0 + var3;
         int var7 = this.removeWs(var5);
         var1 = var5 + var7;
         int var25 = var6 + var7;
         var3 = this.parseString(var1, ":", (boolean)0);
         if(var3 == -1) {
            var4 = -1;
         } else {
            int var8 = var1 + var3;
            int var9 = var25 + var3;
            int var10 = this.removeWs(var8);
            var1 = var8 + var10;
            var25 = var9 + var10;
            var3 = this.parseString(var1, "VCARD", (boolean)0);
            if(var3 == -1) {
               var4 = -1;
            } else {
               int var11 = var1 + var3;
               int var12 = var25 + var3;
               if(this.mBuilder != null) {
                  this.mBuilder.startRecord("VCARD");
               }

               int var13 = this.removeWs(var11);
               var1 = var11 + var13;
               var25 = var12 + var13;
               var3 = this.parseCrlf(var1);
               if(var3 == -1) {
                  var4 = -1;
               } else {
                  var1 += var3;
                  var25 += var3;

                  while(true) {
                     var3 = this.parseCrlf(var1);
                     int var10000;
                     if(var3 == -1) {
                        var3 = this.parseItems(var1);
                        if(var3 == -1) {
                           var4 = -1;
                           break;
                        } else {
                           var1 += var3;
                           var25 += var3;

                           while(true) {
                              var3 = this.parseCrlf(var1);
                              if(var3 == -1) {
                                 var3 = this.parseString(var1, "END", (boolean)0);
                                 if(var3 == -1) {
                                    var4 = -1;
                                    return var4;
                                 } else {
                                    int var18 = var1 + var3;
                                    int var19 = var25 + var3;
                                    int var20 = this.removeWs(var18);
                                    var1 = var18 + var20;
                                    var25 = var19 + var20;
                                    var3 = this.parseString(var1, ":", (boolean)0);
                                    if(var3 == -1) {
                                       var4 = -1;
                                       return var4;
                                    } else {
                                       int var21 = var1 + var3;
                                       int var22 = var25 + var3;
                                       int var23 = this.removeWs(var21);
                                       int var24 = var21 + var23;
                                       var25 = var22 + var23;
                                       var3 = this.parseString(var24, "VCARD", (boolean)0);
                                       if(var3 == -1) {
                                          var4 = -1;
                                       } else {
                                          var25 += var3;
                                          if(this.mBuilder != null) {
                                             this.mBuilder.endRecord();
                                          }

                                          var4 = var25;
                                       }

                                       return var4;
                                    }
                                 }
                              }

                              var10000 = var1 + var3;
                              var10000 = var25 + var3;
                           }
                        }
                     }

                     var10000 = var1 + var3;
                     var10000 = var25 + var3;
                  }
               }
            }
         }
      }

      return var4;
   }

   int parseVCardFile(int var1) {
      int var2 = -1;
      int var3 = 0;

      while(true) {
         int var4 = this.parseWsls(var1);
         if(var4 == -1) {
            int var5 = this.parseVCard(var1);
            if(var5 != -1) {
               var1 += var5;
               var3 += var5;

               while(true) {
                  var5 = this.parseWsls(var1);
                  if(var5 == -1) {
                     var2 = var3;
                     break;
                  }

                  int var10000 = var1 + var5;
                  var10000 = var3 + var5;
               }
            }

            return var2;
         }

         var1 += var4;
         var3 += var4;
      }
   }

   protected int parseVFile(int var1) {
      return this.parseVCardFile(var1);
   }
}
