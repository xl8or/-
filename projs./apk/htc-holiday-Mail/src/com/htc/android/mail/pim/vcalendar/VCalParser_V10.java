package com.htc.android.mail.pim.vcalendar;

import com.htc.android.mail.pim.VBuilder;
import com.htc.android.mail.pim.VParser;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VCalParser_V10 extends VParser {

   private static final HashSet<String> mEscAllowedProps;
   private static final HashSet<String> mEvtPropNameGroup1;
   private static final HashSet<String> mEvtPropNameGroup2;
   private static final HashMap<String, HashSet<String>> mSpecialValueSetMap;
   private static final HashSet<String> mValueCAT;
   private static final HashSet<String> mValueCLASS;
   private static final HashSet<String> mValueRES;
   private static final HashSet<String> mValueSTAT;


   static {
      String[] var0 = new String[]{"ATTACH", "ATTENDEE", "DCREATED", "COMPLETED", "DESCRIPTION", "DUE", "DTEND", "EXRULE", "LAST-MODIFIED", "LOCATION", "RNUM", "PRIORITY", "RELATED-TO", "RRULE", "SEQUENCE", "DTSTART", "SUMMARY", "TRANSP", "URL", "UID", "CLASS", "STATUS"};
      List var1 = Arrays.asList(var0);
      mEvtPropNameGroup1 = new HashSet(var1);
      String[] var2 = new String[]{"AALARM", "CATEGORIES", "DALARM", "EXDATE", "MALARM", "PALARM", "RDATE", "RESOURCES"};
      List var3 = Arrays.asList(var2);
      mEvtPropNameGroup2 = new HashSet(var3);
      String[] var4 = new String[]{"APPOINTMENT", "BUSINESS", "EDUCATION", "HOLIDAY", "MEETING", "MISCELLANEOUS", "PERSONAL", "PHONE CALL", "SICK DAY", "SPECIAL OCCASION", "TRAVEL", "VACATION"};
      List var5 = Arrays.asList(var4);
      mValueCAT = new HashSet(var5);
      String[] var6 = new String[]{"PUBLIC", "PRIVATE", "CONFIDENTIAL"};
      List var7 = Arrays.asList(var6);
      mValueCLASS = new HashSet(var7);
      String[] var8 = new String[]{"CATERING", "CHAIRS", "EASEL", "PROJECTOR", "VCR", "VEHICLE"};
      List var9 = Arrays.asList(var8);
      mValueRES = new HashSet(var9);
      String[] var10 = new String[]{"ACCEPTED", "NEEDS ACTION", "SENT", "TENTATIVE", "CONFIRMED", "DECLINED", "COMPLETED", "DELEGATED"};
      List var11 = Arrays.asList(var10);
      mValueSTAT = new HashSet(var11);
      String[] var12 = new String[]{"DESCRIPTION", "SUMMARY", "AALARM", "DALARM", "MALARM", "PALARM"};
      List var13 = Arrays.asList(var12);
      mEscAllowedProps = new HashSet(var13);
      mSpecialValueSetMap = new HashMap();
      HashMap var14 = mSpecialValueSetMap;
      HashSet var15 = mValueCAT;
      var14.put("CATEGORIES", var15);
      HashMap var17 = mSpecialValueSetMap;
      HashSet var18 = mValueCLASS;
      var17.put("CLASS", var18);
      HashMap var20 = mSpecialValueSetMap;
      HashSet var21 = mValueRES;
      var20.put("RESOURCES", var21);
      HashMap var23 = mSpecialValueSetMap;
      HashSet var24 = mValueSTAT;
      var23.put("STATUS", var24);
   }

   public VCalParser_V10() {}

   private String exportEntpropValue(String var1, String var2) {
      String var3;
      if(var1 != null && var2 != null) {
         if(!"".equals(var1) && !"".equals(var2)) {
            if(!mEscAllowedProps.contains(var1)) {
               var3 = var2;
            } else {
               var3 = var2.replace("\\\\", "\n\r\n").replace("\\;", ";").replace("\\:", ":").replace("\\,", ",").replace("\n\r\n", "\\");
            }
         } else {
            var3 = "";
         }
      } else {
         var3 = null;
      }

      return var3;
   }

   private int parseCalentities(int var1) {
      int var2 = -1;
      boolean var3 = false;
      int var4 = this.parseCalentity(var1);
      if(var2 != var4) {
         var1 += var4;
         int var7 = 0 + var4;

         while(true) {
            while(true) {
               var4 = this.parseCrlf(var1);
               if(-1 == var4) {
                  var4 = this.parseCalentity(var1);
                  if(-1 == var4) {
                     var2 = var7;
                     return var2;
                  }

                  var1 += var4;
                  var7 += var4;
               } else {
                  int var10000 = var1 + var4;
                  var10000 = var7 + var4;
               }
            }
         }
      } else {
         return var2;
      }
   }

   private int parseCalentity(int var1) {
      int var2 = -1;
      int var3 = this.parseEvententity(var1);
      if(var2 != var3) {
         var2 = var3;
      } else {
         var3 = this.parseTodoentity(var1);
         if(-1 != var3) {
            var2 = var3;
         }
      }

      return var2;
   }

   private int parseCalprop(int var1) {
      int var2 = this.parseCalprop0(var1, "DAYLIGHT");
      int var3;
      if(-1 != var2) {
         var3 = var2;
      } else {
         var2 = this.parseCalprop0(var1, "GEO");
         if(-1 != var2) {
            var3 = var2;
         } else {
            var2 = this.parseCalprop0(var1, "PRODID");
            if(-1 != var2) {
               var3 = var2;
            } else {
               var2 = this.parseCalprop0(var1, "TZ");
               if(-1 != var2) {
                  var3 = var2;
               } else {
                  var2 = this.parseCalprop1(var1);
                  if(-1 != var2) {
                     var3 = var2;
                  } else {
                     var3 = -1;
                  }
               }
            }
         }
      }

      return var3;
   }

   private int parseCalprop0(int var1, String var2) {
      boolean var3 = false;
      int var4 = this.parseString(var1, var2, (boolean)1);
      int var5;
      if(-1 == var4) {
         var5 = -1;
      } else {
         var1 += var4;
         int var13 = 0 + var4;
         if(this.mBuilder != null) {
            this.mBuilder.propertyName(var2);
         }

         int var6 = this.parseParams(var1);
         if(-1 != var6) {
            int var10000 = var1 + var6;
            var10000 = var13 + var6;
         }

         var4 = this.parseString(var1, ":", (boolean)1);
         if(-1 == var4) {
            var5 = -1;
         } else {
            var1 += var4;
            var13 += var4;
            int var9 = var1;
            var4 = this.parseValue(var1);
            if(-1 == var4) {
               var5 = -1;
            } else {
               var1 += var4;
               var13 += var4;
               if(this.mBuilder != null) {
                  ArrayList var10 = new ArrayList();
                  String var11 = this.mBuffer.substring(var9, var1);
                  var10.add(var11);
                  this.mBuilder.propertyValues(var10);
               }

               var4 = this.parseCrlf(var1);
               if(-1 == var4) {
                  var5 = -1;
               } else {
                  var5 = var13 + var4;
               }
            }
         }
      }

      return var5;
   }

   private int parseCalprop1(int var1) {
      boolean var2 = false;
      int var3 = this.parseString(var1, "VERSION", (boolean)1);
      int var4;
      if(-1 == var3) {
         var4 = -1;
      } else {
         var1 += var3;
         int var10 = 0 + var3;
         if(this.mBuilder != null) {
            this.mBuilder.propertyName("VERSION");
         }

         int var5 = this.parseParams(var1);
         if(-1 != var5) {
            int var10000 = var1 + var5;
            var10000 = var10 + var5;
         }

         var3 = this.parseString(var1, ":", (boolean)1);
         if(-1 == var3) {
            var4 = -1;
         } else {
            var1 += var3;
            var10 += var3;
            var3 = this.parseString(var1, "1.0", (boolean)1);
            if(-1 == var3) {
               var4 = -1;
            } else {
               var1 += var3;
               var10 += var3;
               if(this.mBuilder != null) {
                  ArrayList var8 = new ArrayList();
                  boolean var9 = var8.add("1.0");
                  this.mBuilder.propertyValues(var8);
               }

               var3 = this.parseCrlf(var1);
               if(-1 == var3) {
                  var4 = -1;
               } else {
                  var4 = var10 + var3;
               }
            }
         }
      }

      return var4;
   }

   private int parseCalprops(int var1) {
      boolean var2 = false;
      if(this.mBuilder != null) {
         this.mBuilder.startProperty();
      }

      int var3 = this.parseCalprop(var1);
      int var4;
      if(-1 == var3) {
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
               if(-1 == var3) {
                  if(this.mBuilder != null) {
                     this.mBuilder.startProperty();
                  }

                  var3 = this.parseCalprop(var1);
                  if(-1 == var3) {
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

   private int parseEntprop(int var1) {
      int var2 = -1;
      int var3 = this.parseEntprop0(var1);
      if(var2 != var3) {
         var2 = var3;
      } else {
         var3 = this.parseEntprop1(var1);
         if(-1 != var3) {
            var2 = var3;
         }
      }

      return var2;
   }

   private int parseEntprop0(int var1) {
      int var2 = this.removeWs(var1);
      int var3 = var1 + var2;
      int var4 = 0 + var2;
      String var5 = this.getWord(var3).toUpperCase();
      int var7;
      if(!mEvtPropNameGroup1.contains(var5)) {
         int var6 = this.parseXWord(var3);
         if(-1 == var6) {
            var7 = -1;
            return var7;
         }
      }

      int var8 = var5.length();
      var3 += var8;
      var4 += var8;
      if(this.mBuilder != null) {
         this.mBuilder.propertyName(var5);
      }

      int var9 = this.parseParams(var3);
      if(-1 != var9) {
         int var10000 = var3 + var9;
         var10000 = var4 + var9;
      }

      int var12 = this.parseString(var3, ":", (boolean)0);
      if(-1 == var12) {
         var7 = -1;
      } else {
         var3 += var12;
         var4 += var12;
         int var13 = var3;
         var12 = this.parseValue(var3);
         if(-1 == var12) {
            var7 = -1;
         } else {
            var3 += var12;
            var4 += var12;
            if(this.mBuilder != null) {
               ArrayList var14 = new ArrayList();
               String var15 = this.mBuffer.substring(var13, var3);
               String var16 = this.exportEntpropValue(var5, var15);
               var14.add(var16);
               this.mBuilder.propertyValues(var14);
               int var18 = this.valueFilter(var5, var14);
               if(-1 == var18) {
                  var7 = -1;
                  return var7;
               }
            }

            var12 = this.parseCrlf(var3);
            if(-1 == var12) {
               var7 = -1;
            } else {
               var7 = var4 + var12;
            }
         }
      }

      return var7;
   }

   private int parseEntprop1(int var1) {
      int var2 = this.removeWs(var1);
      int var3 = var1 + var2;
      int var4 = 0 + var2;
      String var5 = this.getWord(var3).toUpperCase();
      int var6;
      if(!mEvtPropNameGroup2.contains(var5)) {
         var6 = -1;
      } else {
         int var7 = var5.length();
         var3 += var7;
         var4 += var7;
         if(this.mBuilder != null) {
            this.mBuilder.propertyName(var5);
         }

         int var8 = this.parseParams(var3);
         if(-1 != var8) {
            int var10000 = var3 + var8;
            var10000 = var4 + var8;
         }

         int var11 = this.parseString(var3, ":", (boolean)0);
         if(-1 == var11) {
            var6 = -1;
         } else {
            var3 += var11;
            var4 += var11;
            int var12 = var3;
            var11 = this.parseValue(var3);
            if(-1 == var11) {
               var6 = -1;
            } else {
               var3 += var11;
               var4 += var11;
               if(this.mBuilder != null) {
                  ArrayList var13 = new ArrayList();
                  Pattern var14 = Pattern.compile("([^;\\\\]*(\\\\[\\\\;:,])*[^;\\\\]*)(;?)");
                  String var15 = this.mBuffer.substring(var12, var3);
                  Matcher var16 = var14.matcher(var15);

                  while(var16.find()) {
                     String var17 = var16.group(1);
                     String var18 = this.exportEntpropValue(var5, var17);
                     var13.add(var18);
                     int var20 = var16.end();
                     int var21 = var12 + var20;
                     if(var3 == var21) {
                        String var22 = var16.group(3);
                        if(";".equals(var22)) {
                           boolean var23 = var13.add("");
                        }
                        break;
                     }
                  }

                  this.mBuilder.propertyValues(var13);
                  int var24 = this.valueFilter(var5, var13);
                  if(-1 == var24) {
                     var6 = -1;
                     return var6;
                  }
               }

               var11 = this.parseCrlf(var3);
               if(-1 == var11) {
                  var6 = -1;
               } else {
                  var6 = var4 + var11;
               }
            }
         }
      }

      return var6;
   }

   private int parseEntprops(int var1) {
      boolean var2 = false;
      if(this.mBuilder != null) {
         this.mBuilder.startProperty();
      }

      int var3 = this.parseEntprop(var1);
      int var4;
      if(-1 == var3) {
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
               if(-1 == var3) {
                  if(this.mBuilder != null) {
                     this.mBuilder.startProperty();
                  }

                  var3 = this.parseEntprop(var1);
                  if(-1 == var3) {
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

   private int parseEvententity(int var1) {
      boolean var2 = false;
      int var3 = this.parseString(var1, "BEGIN", (boolean)0);
      int var4;
      if(-1 == var3) {
         var4 = -1;
      } else {
         int var5 = var1 + var3;
         int var6 = 0 + var3;
         int var7 = this.removeWs(var5);
         var1 = var5 + var7;
         int var33 = var6 + var7;
         var3 = this.parseString(var1, ":", (boolean)0);
         if(-1 == var3) {
            var4 = -1;
         } else {
            int var8 = var1 + var3;
            int var9 = var33 + var3;
            int var10 = this.removeWs(var8);
            var1 = var8 + var10;
            var33 = var9 + var10;
            var3 = this.parseString(var1, "VEVENT", (boolean)0);
            if(-1 == var3) {
               var4 = -1;
            } else {
               int var11 = var1 + var3;
               int var12 = var33 + var3;
               if(this.mBuilder != null) {
                  this.mBuilder.startRecord("VEVENT");
               }

               int var13 = this.removeWs(var11);
               var1 = var11 + var13;
               var33 = var12 + var13;
               var3 = this.parseCrlf(var1);
               if(-1 == var3) {
                  var4 = -1;
               } else {
                  var1 += var3;
                  var33 += var3;

                  while(true) {
                     var3 = this.parseCrlf(var1);
                     int var10000;
                     if(-1 == var3) {
                        var3 = this.parseEntprops(var1);
                        if(-1 == var3) {
                           var4 = -1;
                           break;
                        } else {
                           int var16 = var1 + var3;
                           int var17 = var33 + var3;
                           int var18 = this.removeWs(var16);
                           var1 = var16 + var18;
                           var33 = var17 + var18;

                           while(true) {
                              int var19 = this.parseCrlf(var1);
                              if(-1 == var19) {
                                 var3 = this.parseString(var1, "END", (boolean)0);
                                 if(-1 == var3) {
                                    var4 = -1;
                                    return var4;
                                 } else {
                                    int var22 = var1 + var3;
                                    int var23 = var33 + var3;
                                    int var24 = this.removeWs(var22);
                                    var1 = var22 + var24;
                                    var33 = var23 + var24;
                                    var3 = this.parseString(var1, ":", (boolean)0);
                                    if(-1 == var3) {
                                       var4 = -1;
                                       return var4;
                                    } else {
                                       int var25 = var1 + var3;
                                       int var26 = var33 + var3;
                                       int var27 = this.removeWs(var25);
                                       var1 = var25 + var27;
                                       var33 = var26 + var27;
                                       var3 = this.parseString(var1, "VEVENT", (boolean)0);
                                       if(-1 == var3) {
                                          var4 = -1;
                                       } else {
                                          int var28 = var1 + var3;
                                          int var29 = var33 + var3;
                                          if(this.mBuilder != null) {
                                             this.mBuilder.endRecord();
                                          }

                                          int var30 = this.removeWs(var28);
                                          var1 = var28 + var30;
                                          var33 = var29 + var30;
                                          var3 = this.parseCrlf(var1);
                                          if(-1 == var3) {
                                             var4 = -1;
                                          } else {
                                             var1 += var3;
                                             var33 += var3;

                                             while(true) {
                                                var3 = this.parseCrlf(var1);
                                                if(-1 == var3) {
                                                   var4 = var33;
                                                   return var4;
                                                }

                                                var10000 = var1 + var3;
                                                var10000 = var33 + var3;
                                             }
                                          }
                                       }

                                       return var4;
                                    }
                                 }
                              }

                              var10000 = var1 + var19;
                              var10000 = var33 + var19;
                           }
                        }
                     }

                     var10000 = var1 + var3;
                     var10000 = var33 + var3;
                  }
               }
            }
         }
      }

      return var4;
   }

   private int parseKnownType(int var1) {
      int var2 = this.parseString(var1, "WAVE", (boolean)1);
      int var3;
      if(-1 != var2) {
         var3 = var2;
      } else {
         var2 = this.parseString(var1, "PCM", (boolean)1);
         if(-1 != var2) {
            var3 = var2;
         } else {
            var2 = this.parseString(var1, "VCARD", (boolean)1);
            if(-1 != var2) {
               var3 = var2;
            } else {
               var2 = this.parseXWord(var1);
               if(-1 != var2) {
                  var3 = var2;
               } else {
                  var3 = -1;
               }
            }
         }
      }

      return var3;
   }

   private int parseParam(int var1) {
      int var2 = -1;
      int var3 = this.parseParam0(var1);
      if(var2 != var3) {
         var2 = var3;
      } else {
         var3 = this.parseParam1(var1);
         if(-1 != var3) {
            var2 = var3;
         } else {
            var3 = this.parseParam2(var1);
            if(-1 != var3) {
               var2 = var3;
            } else {
               var3 = this.parseParam3(var1);
               if(-1 != var3) {
                  var2 = var3;
               } else {
                  var3 = this.parseParam4(var1);
                  if(-1 != var3) {
                     var2 = var3;
                  } else {
                     var3 = this.parseParam5(var1);
                     if(-1 != var3) {
                        var2 = var3;
                     } else {
                        var3 = this.parseParam6(var1);
                        if(-1 != var3) {
                           var2 = var3;
                        } else {
                           var3 = this.parseParam7(var1);
                           if(-1 != var3) {
                              var2 = var3;
                           } else {
                              int var4 = var1;
                              var3 = this.parseKnownType(var1);
                              if(-1 != var3) {
                                 var1 += var3;
                                 if(this.mBuilder != null) {
                                    this.mBuilder.propertyParamType((String)null);
                                    VBuilder var5 = this.mBuilder;
                                    String var6 = this.mBuffer.substring(var4, var1);
                                    var5.propertyParamValue(var6);
                                 }

                                 var2 = var3;
                              }
                           }
                        }
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
      if(-1 == var4) {
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
         if(-1 == var4) {
            var5 = -1;
         } else {
            int var11 = var1 + var4;
            int var12 = var16 + var4;
            int var13 = this.removeWs(var11);
            var1 = var11 + var13;
            var16 = var12 + var13;
            int var3 = var1;
            var4 = this.parsePtypeval(var1);
            if(-1 == var4) {
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
      int var2 = 0;
      boolean var4 = false;
      int var5 = this.parseString(var1, "VALUE", (boolean)1);
      if(-1 != var5) {
         var1 += var5;
         var2 = 0 + var5;
         var4 = true;
      }

      if(var4 && this.mBuilder != null) {
         VBuilder var6 = this.mBuilder;
         String var7 = this.mBuffer.substring(var1, var1);
         var6.propertyParamType(var7);
      }

      int var8 = this.removeWs(var1);
      int var9 = var1 + var8;
      int var10 = var2 + var8;
      int var11 = this.parseString(var9, "=", (boolean)1);
      int var12;
      if(-1 != var11) {
         if(!var4) {
            var12 = -1;
            return var12;
         }

         var9 += var11;
         var10 += var11;
      } else if(var4) {
         var12 = -1;
         return var12;
      }

      int var13 = this.removeWs(var9);
      int var14 = var9 + var13;
      int var15 = var10 + var13;
      var11 = this.parsePValueVal(var14);
      if(-1 == var11) {
         var12 = -1;
      } else {
         var9 = var14 + var11;
         var10 = var15 + var11;
         if(this.mBuilder != null) {
            VBuilder var16 = this.mBuilder;
            String var17 = this.mBuffer.substring(var14, var9);
            var16.propertyParamValue(var17);
         }

         var12 = var10;
      }

      return var12;
   }

   private int parseParam2(int var1) {
      int var2 = 0;
      boolean var4 = false;
      int var5 = this.parseString(var1, "ENCODING", (boolean)1);
      if(-1 != var5) {
         var1 += var5;
         var2 = 0 + var5;
         var4 = true;
      }

      if(var4 && this.mBuilder != null) {
         VBuilder var6 = this.mBuilder;
         String var7 = this.mBuffer.substring(var1, var1);
         var6.propertyParamType(var7);
      }

      int var8 = this.removeWs(var1);
      int var9 = var1 + var8;
      int var10 = var2 + var8;
      int var11 = this.parseString(var9, "=", (boolean)1);
      int var12;
      if(-1 != var11) {
         if(!var4) {
            var12 = -1;
            return var12;
         }

         var9 += var11;
         var10 += var11;
      } else if(var4) {
         var12 = -1;
         return var12;
      }

      int var13 = this.removeWs(var9);
      int var14 = var9 + var13;
      int var15 = var10 + var13;
      var11 = this.parsePEncodingVal(var14);
      if(-1 == var11) {
         var12 = -1;
      } else {
         var9 = var14 + var11;
         var10 = var15 + var11;
         if(this.mBuilder != null) {
            VBuilder var16 = this.mBuilder;
            String var17 = this.mBuffer.substring(var14, var9);
            var16.propertyParamValue(var17);
         }

         var12 = var10;
      }

      return var12;
   }

   private int parseParam3(int var1) {
      boolean var2 = false;
      int var4 = this.parseString(var1, "CHARSET", (boolean)1);
      int var5;
      if(-1 == var4) {
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
         var4 = this.parseString(var1, "=", (boolean)1);
         if(-1 == var4) {
            var5 = -1;
         } else {
            int var11 = var1 + var4;
            int var12 = var16 + var4;
            int var13 = this.removeWs(var11);
            var1 = var11 + var13;
            var16 = var12 + var13;
            int var3 = var1;
            var4 = this.parseCharsetVal(var1);
            if(-1 == var4) {
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
      if(-1 == var4) {
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
         var4 = this.parseString(var1, "=", (boolean)1);
         if(-1 == var4) {
            var5 = -1;
         } else {
            int var11 = var1 + var4;
            int var12 = var16 + var4;
            int var13 = this.removeWs(var11);
            var1 = var11 + var13;
            var16 = var12 + var13;
            int var3 = var1;
            var4 = this.parseLangVal(var1);
            if(-1 == var4) {
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
      int var4 = this.parseString(var1, "ROLE", (boolean)1);
      int var5;
      if(-1 == var4) {
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
         var4 = this.parseString(var1, "=", (boolean)1);
         if(-1 == var4) {
            var5 = -1;
         } else {
            int var11 = var1 + var4;
            int var12 = var16 + var4;
            int var13 = this.removeWs(var11);
            var1 = var11 + var13;
            var16 = var12 + var13;
            int var3 = var1;
            var4 = this.parseRoleVal(var1);
            if(-1 == var4) {
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

   private int parseParam6(int var1) {
      boolean var2 = false;
      int var4 = this.parseString(var1, "STATUS", (boolean)1);
      int var5;
      if(-1 == var4) {
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
         var4 = this.parseString(var1, "=", (boolean)1);
         if(-1 == var4) {
            var5 = -1;
         } else {
            int var11 = var1 + var4;
            int var12 = var16 + var4;
            int var13 = this.removeWs(var11);
            var1 = var11 + var13;
            var16 = var12 + var13;
            int var3 = var1;
            var4 = this.parseStatuVal(var1);
            if(-1 == var4) {
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

   private int parseParam7(int var1) {
      boolean var2 = false;
      int var4 = this.parseXWord(var1);
      int var5;
      if(-1 == var4) {
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
         var4 = this.parseString(var1, "=", (boolean)1);
         if(-1 == var4) {
            var5 = -1;
         } else {
            int var11 = var1 + var4;
            int var12 = var16 + var4;
            int var13 = this.removeWs(var11);
            var1 = var11 + var13;
            var16 = var12 + var13;
            int var3 = var1;
            var4 = this.parseWord(var1);
            if(-1 == var4) {
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

   private int parseParamlist(int var1) {
      boolean var2 = false;
      int var3 = this.parseParam(var1);
      int var4;
      if(-1 == var3) {
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
            if(-1 == var3) {
               var4 = var14;
               break;
            }

            int var11 = var9 + var3;
            int var12 = var10 + var3;
            int var13 = this.removeWs(var11);
            var9 = var11 + var13;
            var10 = var12 + var13;
            var3 = this.parseParam(var9);
            if(-1 == var3) {
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
      int var3 = this.parseString(var1, ";", (boolean)1);
      int var4;
      if(-1 == var3) {
         var4 = -1;
      } else {
         int var5 = var1 + var3;
         int var6 = 0 + var3;
         int var7 = this.removeWs(var5);
         int var8 = var5 + var7;
         int var9 = var6 + var7;
         var3 = this.parseParamlist(var8);
         if(-1 == var3) {
            var4 = -1;
         } else {
            var4 = var9 + var3;
         }
      }

      return var4;
   }

   private int parsePtypeval(int var1) {
      int var2 = -1;
      int var3 = this.parseKnownType(var1);
      if(var2 != var3) {
         var2 = var3;
      } else {
         var3 = this.parseXWord(var1);
         if(-1 != var3) {
            var2 = var3;
         }
      }

      return var2;
   }

   private int parseRoleVal(int var1) {
      int var2 = this.parseString(var1, "ATTENDEE", (boolean)1);
      int var3;
      if(-1 != var2) {
         var3 = var2;
      } else {
         var2 = this.parseString(var1, "ORGANIZER", (boolean)1);
         if(-1 != var2) {
            var3 = var2;
         } else {
            var2 = this.parseString(var1, "OWNER", (boolean)1);
            if(-1 != var2) {
               var3 = var2;
            } else {
               var2 = this.parseXWord(var1);
               if(-1 != var2) {
                  var3 = var2;
               } else {
                  var3 = -1;
               }
            }
         }
      }

      return var3;
   }

   private int parseStatuVal(int var1) {
      int var2 = this.parseString(var1, "ACCEPTED", (boolean)1);
      int var3;
      if(-1 != var2) {
         var3 = var2;
      } else {
         var2 = this.parseString(var1, "NEED ACTION", (boolean)1);
         if(-1 != var2) {
            var3 = var2;
         } else {
            var2 = this.parseString(var1, "TENTATIVE", (boolean)1);
            if(-1 != var2) {
               var3 = var2;
            } else {
               var2 = this.parseString(var1, "CONFIRMED", (boolean)1);
               if(-1 != var2) {
                  var3 = var2;
               } else {
                  var2 = this.parseString(var1, "DECLINED", (boolean)1);
                  if(-1 != var2) {
                     var3 = var2;
                  } else {
                     var2 = this.parseString(var1, "COMPLETED", (boolean)1);
                     if(-1 != var2) {
                        var3 = var2;
                     } else {
                        var2 = this.parseString(var1, "DELEGATED", (boolean)1);
                        if(-1 != var2) {
                           var3 = var2;
                        } else {
                           var2 = this.parseXWord(var1);
                           if(-1 != var2) {
                              var3 = var2;
                           } else {
                              var3 = -1;
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      return var3;
   }

   private int parseTodoentity(int var1) {
      boolean var2 = false;
      int var3 = this.parseString(var1, "BEGIN", (boolean)0);
      int var4;
      if(-1 == var3) {
         var4 = -1;
      } else {
         int var5 = var1 + var3;
         int var6 = 0 + var3;
         int var7 = this.removeWs(var5);
         var1 = var5 + var7;
         int var30 = var6 + var7;
         var3 = this.parseString(var1, ":", (boolean)0);
         if(-1 == var3) {
            var4 = -1;
         } else {
            int var8 = var1 + var3;
            int var9 = var30 + var3;
            int var10 = this.removeWs(var8);
            var1 = var8 + var10;
            var30 = var9 + var10;
            var3 = this.parseString(var1, "VTODO", (boolean)0);
            if(-1 == var3) {
               var4 = -1;
            } else {
               var1 += var3;
               var30 += var3;
               if(this.mBuilder != null) {
                  this.mBuilder.startRecord("VTODO");
               }

               var3 = this.parseCrlf(var1);
               if(-1 == var3) {
                  var4 = -1;
               } else {
                  var1 += var3;
                  var30 += var3;

                  while(true) {
                     var3 = this.parseCrlf(var1);
                     int var10000;
                     if(-1 == var3) {
                        var3 = this.parseEntprops(var1);
                        if(-1 == var3) {
                           var4 = -1;
                           break;
                        } else {
                           int var13 = var1 + var3;
                           int var14 = var30 + var3;
                           int var15 = this.removeWs(var13);
                           var1 = var13 + var15;
                           var30 = var14 + var15;

                           while(true) {
                              int var16 = this.parseCrlf(var1);
                              if(-1 == var16) {
                                 var3 = this.parseString(var1, "END", (boolean)0);
                                 if(-1 == var3) {
                                    var4 = -1;
                                    return var4;
                                 } else {
                                    int var19 = var1 + var3;
                                    int var20 = var30 + var3;
                                    int var21 = this.removeWs(var19);
                                    var1 = var19 + var21;
                                    var30 = var20 + var21;
                                    var3 = this.parseString(var1, ":", (boolean)0);
                                    if(-1 == var3) {
                                       var4 = -1;
                                       return var4;
                                    } else {
                                       int var22 = var1 + var3;
                                       int var23 = var30 + var3;
                                       int var24 = this.removeWs(var22);
                                       var1 = var22 + var24;
                                       var30 = var23 + var24;
                                       var3 = this.parseString(var1, "VTODO", (boolean)0);
                                       if(-1 == var3) {
                                          var4 = -1;
                                       } else {
                                          int var25 = var1 + var3;
                                          int var26 = var30 + var3;
                                          if(this.mBuilder != null) {
                                             this.mBuilder.endRecord();
                                          }

                                          int var27 = this.removeWs(var25);
                                          var1 = var25 + var27;
                                          var30 = var26 + var27;
                                          var3 = this.parseCrlf(var1);
                                          if(-1 == var3) {
                                             var4 = -1;
                                          } else {
                                             var1 += var3;
                                             var30 += var3;

                                             while(true) {
                                                var3 = this.parseCrlf(var1);
                                                if(-1 == var3) {
                                                   var4 = var30;
                                                   return var4;
                                                }

                                                var10000 = var1 + var3;
                                                var10000 = var30 + var3;
                                             }
                                          }
                                       }

                                       return var4;
                                    }
                                 }
                              }

                              var10000 = var1 + var16;
                              var10000 = var30 + var16;
                           }
                        }
                     }

                     var10000 = var1 + var3;
                     var10000 = var30 + var3;
                  }
               }
            }
         }
      }

      return var4;
   }

   private int parseVCal(int var1) {
      boolean var2 = false;
      int var3 = this.parseString(var1, "BEGIN", (boolean)0);
      int var4;
      if(-1 == var3) {
         var4 = -1;
      } else {
         int var5 = var1 + var3;
         int var6 = 0 + var3;
         int var7 = this.removeWs(var5);
         var1 = var5 + var7;
         int var33 = var6 + var7;
         var3 = this.parseString(var1, ":", (boolean)0);
         if(-1 == var3) {
            var4 = -1;
         } else {
            int var8 = var1 + var3;
            int var9 = var33 + var3;
            int var10 = this.removeWs(var8);
            var1 = var8 + var10;
            var33 = var9 + var10;
            var3 = this.parseString(var1, "VCALENDAR", (boolean)0);
            if(-1 == var3) {
               var4 = -1;
            } else {
               int var11 = var1 + var3;
               int var12 = var33 + var3;
               if(this.mBuilder != null) {
                  this.mBuilder.startRecord("VCALENDAR");
               }

               int var13 = this.removeWs(var11);
               var1 = var11 + var13;
               var33 = var12 + var13;
               var3 = this.parseCrlf(var1);
               if(-1 == var3) {
                  var4 = -1;
               } else {
                  var1 += var3;
                  var33 += var3;

                  while(true) {
                     var3 = this.parseCrlf(var1);
                     int var10000;
                     if(-1 == var3) {
                        var3 = this.parseCalprops(var1);
                        if(-1 == var3) {
                           var4 = -1;
                        } else {
                           var1 += var3;
                           var33 += var3;
                           var3 = this.parseCalentities(var1);
                           if(-1 == var3) {
                              var4 = -1;
                           } else {
                              int var16 = var1 + var3;
                              int var17 = var33 + var3;
                              int var18 = this.removeWs(var16);
                              var1 = var16 + var18;
                              var33 = var17 + var18;

                              while(true) {
                                 int var19 = this.parseCrlf(var1);
                                 if(-1 == var19) {
                                    var3 = this.parseString(var1, "END", (boolean)0);
                                    if(-1 == var3) {
                                       var4 = -1;
                                       return var4;
                                    } else {
                                       int var22 = var1 + var3;
                                       int var23 = var33 + var3;
                                       int var24 = this.removeWs(var22);
                                       var1 = var22 + var24;
                                       var33 = var23 + var24;
                                       var3 = this.parseString(var1, ":", (boolean)0);
                                       if(-1 == var3) {
                                          var4 = -1;
                                          return var4;
                                       } else {
                                          int var25 = var1 + var3;
                                          int var26 = var33 + var3;
                                          int var27 = this.removeWs(var25);
                                          var1 = var25 + var27;
                                          var33 = var26 + var27;
                                          var3 = this.parseString(var1, "VCALENDAR", (boolean)0);
                                          if(-1 == var3) {
                                             var4 = -1;
                                          } else {
                                             int var28 = var1 + var3;
                                             int var29 = var33 + var3;
                                             if(this.mBuilder != null) {
                                                this.mBuilder.endRecord();
                                             }

                                             int var30 = this.removeWs(var28);
                                             var1 = var28 + var30;
                                             var33 = var29 + var30;
                                             var3 = this.parseCrlf(var1);
                                             if(-1 == var3) {
                                                var4 = -1;
                                             } else {
                                                var1 += var3;
                                                var33 += var3;

                                                while(true) {
                                                   var3 = this.parseCrlf(var1);
                                                   if(-1 == var3) {
                                                      var4 = var33;
                                                      return var4;
                                                   }

                                                   var10000 = var1 + var3;
                                                   var10000 = var33 + var3;
                                                }
                                             }
                                          }

                                          return var4;
                                       }
                                    }
                                 }

                                 var10000 = var1 + var19;
                                 var10000 = var33 + var19;
                              }
                           }
                        }
                        break;
                     }

                     var10000 = var1 + var3;
                     var10000 = var33 + var3;
                  }
               }
            }
         }
      }

      return var4;
   }

   private int parseVCalFile(int var1) {
      int var2 = -1;
      int var3 = 0;

      while(true) {
         int var4 = this.parseWsls(var1);
         if(-1 == var4) {
            int var5 = this.parseVCal(var1);
            if(-1 != var5) {
               var1 += var5;
               var3 += var5;

               while(true) {
                  var5 = this.parseWsls(var1);
                  if(-1 == var5) {
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

   private int valueFilter(String var1, ArrayList<String> var2) {
      byte var3;
      if(var1 != null && !var1.equals("") && var2 != null && !var2.isEmpty()) {
         if(mSpecialValueSetMap.containsKey(var1)) {
            Iterator var4 = var2.iterator();

            while(var4.hasNext()) {
               String var5 = (String)var4.next();
               if(!((HashSet)mSpecialValueSetMap.get(var1)).contains(var5) && !var5.startsWith("X-")) {
                  var3 = -1;
                  return var3;
               }
            }
         }

         var3 = 1;
      } else {
         var3 = 1;
      }

      return var3;
   }

   protected int parseVFile(int var1) {
      return this.parseVCalFile(var1);
   }
}
