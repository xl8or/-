package com.facebook.katana.util.jsonmirror;

import com.facebook.katana.Constants;
import com.facebook.katana.util.Log;
import com.facebook.katana.util.Tuple;
import com.facebook.katana.util.jsonmirror.JMAutogen;
import com.facebook.katana.util.jsonmirror.JMDictDestination;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.types.JMBase;
import com.facebook.katana.util.jsonmirror.types.JMBoolean;
import com.facebook.katana.util.jsonmirror.types.JMDict;
import com.facebook.katana.util.jsonmirror.types.JMDouble;
import com.facebook.katana.util.jsonmirror.types.JMEscaped;
import com.facebook.katana.util.jsonmirror.types.JMList;
import com.facebook.katana.util.jsonmirror.types.JMLong;
import com.facebook.katana.util.jsonmirror.types.JMSimpleDict;
import com.facebook.katana.util.jsonmirror.types.JMString;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

public class JMParser {

   // $FF: synthetic field
   static final boolean $assertionsDisabled;
   private static String TAG;


   static {
      byte var0;
      if(!JMParser.class.desiredAssertionStatus()) {
         var0 = 1;
      } else {
         var0 = 0;
      }

      $assertionsDisabled = (boolean)var0;
      TAG = "JMParser";
   }

   public JMParser() {}

   protected static List<Object> handleArrays(JsonParser var0, Set<JMBase> var1) throws JsonParseException, IOException, JMException {
      JMList var2 = (JMList)validateSpecification(JMList.class, var1);
      List var9;
      if(var2 != null) {
         ArrayList var3 = new ArrayList();
         Set var4 = var2.getObjectTypes();
         JsonToken var5 = var0.nextToken();

         while(true) {
            JsonToken var6 = JsonToken.END_ARRAY;
            if(var5 == var6) {
               var9 = Collections.unmodifiableList(var3);
               break;
            }

            Object var7 = parseJsonResponse(var0, var4);
            if(var7 != null) {
               var3.add(var7);
            }

            var5 = var0.nextToken();
         }
      } else {
         logUnexpectedToken(var0.getCurrentToken(), var1);
         var0.skipChildren();
         var9 = null;
      }

      return var9;
   }

   protected static Boolean handleBooleans(JsonParser var0, Set<JMBase> var1) {
      JsonToken var2 = var0.getCurrentToken();
      Boolean var3;
      if(validateSpecification(JMBoolean.class, var1) != null) {
         var3 = new Boolean;
         JsonToken var4 = JsonToken.VALUE_TRUE;
         byte var5;
         if(var2 == var4) {
            var5 = 1;
         } else {
            var5 = 0;
         }

         var3.<init>((boolean)var5);
      } else {
         logUnexpectedToken(var2, var1);
         var3 = null;
      }

      return var3;
   }

   protected static Object handleObjects(JsonParser var0, Set<JMBase> var1) throws JsonParseException, IOException, JMException {
      String var2 = null;
      JMBase var3 = validateSpecification(JMDict.class, var1);
      JsonToken var39;
      Object var117;
      if(var3 != null) {
         JMDict var4 = (JMDict)var3;
         JMDictDestination var5 = var4.getNewInstance();
         JsonToken var6 = var0.nextToken();
         Object var7 = null;

         while(true) {
            JsonToken var8 = JsonToken.END_OBJECT;
            if(var6 == var8) {
               var5.postprocess();
               var117 = var5;
               break;
            }

            JsonToken var9 = JsonToken.FIELD_NAME;
            if(var6 == var9) {
               var2 = var0.getText();
            } else {
               label288: {
                  if(!$assertionsDisabled && var7 == null) {
                     throw new AssertionError();
                  }

                  Tuple var11 = var4.getObjectForJsonField((String)var7);
                  String var12 = null;
                  Object var13 = null;
                  JMBase var15;
                  if(var11 != null) {
                     String var14 = (String)var11.d0;
                     var15 = (JMBase)var11.d1;
                     var12 = var14;
                  } else {
                     var15 = (JMBase)var13;
                  }

                  JsonToken var16 = JsonToken.VALUE_NUMBER_INT;
                  if(var6 != var16) {
                     JsonToken var17 = JsonToken.VALUE_NUMBER_FLOAT;
                     if(var6 != var17) {
                        JsonToken var18 = JsonToken.VALUE_STRING;
                        if(var6 != var18) {
                           JsonToken var47 = JsonToken.VALUE_TRUE;
                           if(var6 != var47) {
                              JsonToken var48 = JsonToken.VALUE_FALSE;
                              if(var6 != var48) {
                                 JsonToken var51 = JsonToken.START_ARRAY;
                                 if(var6 != var51) {
                                    JsonToken var52 = JsonToken.START_OBJECT;
                                    if(var6 != var52) {
                                       break label288;
                                    }
                                 }

                                 if(var15 == null) {
                                    if(!var4.mIgnoreUnexpectedJsonFields) {
                                       logUnexpectedToken(var6, var15);
                                    }

                                    var0.skipChildren();
                                    break label288;
                                 }

                                 Object var118 = parseJsonResponse(var0, var15);
                                 if(var118 != null) {
                                    JsonToken var53 = JsonToken.START_ARRAY;
                                    if(var6 != var53) {
                                       List var54 = Collections.EMPTY_LIST;
                                       if(var118 != var54) {
                                          if(var118 instanceof JMDictDestination) {
                                             JMDictDestination var56 = (JMDictDestination)var118;
                                             var5.setDictionary(var12, var56);
                                             break label288;
                                          }

                                          if(var118 instanceof Map) {
                                             Map var57 = (Map)var118;
                                             var5.setSimpleDictionary(var12, var57);
                                             break label288;
                                          }

                                          String var58 = TAG;
                                          String var59 = "got a " + var6 + " but don\'t know what to do with it.";
                                          Log.e(var58, var59);
                                          break label288;
                                       }
                                    }

                                    if(!$assertionsDisabled && !(var118 instanceof List)) {
                                       throw new AssertionError();
                                    }

                                    List var55 = (List)var118;
                                    var5.setList(var12, var55);
                                 }
                                 break label288;
                              }
                           }

                           if(validateSpecification(JMBoolean.class, var15)) {
                              JsonToken var49 = JsonToken.VALUE_TRUE;
                              byte var50;
                              if(var6 == var49) {
                                 var50 = 1;
                              } else {
                                 var50 = 0;
                              }

                              var5.setBoolean(var12, (boolean)var50);
                           } else {
                              logUnexpectedToken(var6, var15);
                           }
                           break label288;
                        }
                     }
                  }

                  Object var19 = null;
                  boolean var24;
                  if(var19 == null) {
                     label235: {
                        JsonToken var20 = JsonToken.VALUE_NUMBER_INT;
                        if(var6 != var20) {
                           JsonToken var21 = JsonToken.VALUE_STRING;
                           if(var6 != var21) {
                              break label235;
                           }
                        }

                        if(validateSpecification(JMLong.class, var15)) {
                           label230: {
                              try {
                                 long var22 = Long.parseLong(var0.getText());
                                 var5.setLong(var12, var22);
                              } catch (NumberFormatException var114) {
                                 break label230;
                              }

                              var24 = true;
                           }
                        }
                     }
                  }

                  if(var19 == null) {
                     label225: {
                        JsonToken var25 = JsonToken.VALUE_NUMBER_INT;
                        if(var6 != var25) {
                           JsonToken var26 = JsonToken.VALUE_STRING;
                           if(var6 != var26) {
                              break label225;
                           }
                        }

                        if(validateSpecification(JMBoolean.class, var15)) {
                           byte var27 = 0;
                           if(var0.getText().equals("0")) {
                              var27 = 0;
                              var24 = true;
                           } else if(var0.getText().equals("1")) {
                              var27 = 1;
                              var24 = true;
                           }

                           if(var24) {
                              var5.setBoolean(var12, (boolean)var27);
                           }
                        }
                     }
                  }

                  Object var30;
                  if(var19 == null && validateSpecification(JMDouble.class, var15)) {
                     label216: {
                        try {
                           double var28 = Double.parseDouble(var0.getText());
                           var5.setDouble(var12, var28);
                        } catch (NumberFormatException var113) {
                           var30 = var19;
                           break label216;
                        }

                        var30 = null;
                     }
                  } else {
                     var30 = var19;
                  }

                  label212: {
                     if(var30 == null) {
                        JsonToken var31 = JsonToken.VALUE_STRING;
                        if(var6 == var31 && validateSpecification(JMEscaped.class, var15)) {
                           JMEscaped var32 = (JMEscaped)var15;
                           JsonFactory var33 = var32.mFactory;
                           String var34 = var0.getText();
                           JsonParser var35 = var33.createJsonParser(var34);
                           JsonToken var36 = var35.nextToken();
                           JMBase var37 = var32.mInnerObject;
                           var19 = parseJsonResponse(var35, var37);
                           if(var19 != null) {
                              if(var32.mInnerObject instanceof JMList) {
                                 List var38 = (List)var19;
                                 var5.setList(var12, var38);
                                 var39 = null;
                                 break label212;
                              }

                              if(var32.mInnerObject instanceof JMDict) {
                                 JMDictDestination var45 = (JMDictDestination)var19;
                                 var5.setDictionary(var12, var45);
                                 var39 = null;
                                 break label212;
                              }

                              if(var32.mInnerObject instanceof JMSimpleDict) {
                                 Map var46 = (Map)var19;
                                 var5.setSimpleDictionary(var12, var46);
                                 var39 = null;
                                 break label212;
                              }
                           }
                        }
                     }

                     var39 = (JsonToken)var30;
                  }

                  if(var39 == null) {
                     JsonToken var40 = JsonToken.VALUE_STRING;
                     if(var6 == var40 && validateSpecification(JMString.class, var15)) {
                        JMString var41 = (JMString)var15;
                        String var42 = var0.getText();
                        String var43 = var41.formatString(var42);
                        var5.setString(var12, var43);
                     }
                  }

                  if(var39 == null && !var4.mIgnoreUnexpectedJsonFields) {
                     logUnexpectedToken(var0.getCurrentToken(), var15);
                  }
               }
            }

            var6 = var0.nextToken();
         }
      } else {
         JMBase var62 = validateSpecification(JMSimpleDict.class, var1);
         if(var62 != null) {
            HashMap var63 = new HashMap();
            JsonToken var64 = var0.nextToken();
            var39 = null;
            JsonToken var116 = var64;

            while(true) {
               JsonToken var65 = JsonToken.END_OBJECT;
               if(var116 == var65) {
                  Map var94 = Collections.unmodifiableMap(var63);
                  var117 = var94;
                  break;
               }

               JsonToken var66 = JsonToken.FIELD_NAME;
               if(var116 == var66) {
                  String var67 = var0.getText();
               } else {
                  JsonToken var71 = JsonToken.VALUE_NUMBER_INT;
                  if(var116 == var71) {
                     Long var72 = Long.valueOf(var0.getLongValue());
                     var63.put(var39, var72);
                  } else {
                     JsonToken var74 = JsonToken.VALUE_NUMBER_FLOAT;
                     if(var116 == var74) {
                        Double var75 = Double.valueOf(var0.getDoubleValue());
                        var63.put(var39, var75);
                     } else {
                        JsonToken var77 = JsonToken.VALUE_STRING;
                        if(var116 == var77) {
                           try {
                              Long var78 = Long.valueOf(Long.parseLong(var0.getText()));
                              var63.put(var39, var78);
                           } catch (NumberFormatException var112) {
                              try {
                                 Double var81 = Double.valueOf(Double.parseDouble(var0.getText()));
                                 var63.put(var39, var81);
                              } catch (NumberFormatException var111) {
                                 String var84 = var0.getText();
                                 var63.put(var39, var84);
                              }
                           }
                        } else {
                           label285: {
                              JsonToken var86 = JsonToken.VALUE_TRUE;
                              if(var116 != var86) {
                                 JsonToken var87 = JsonToken.VALUE_FALSE;
                                 if(var116 != var87) {
                                    JsonToken var92 = JsonToken.START_ARRAY;
                                    if(var116 != var92) {
                                       JsonToken var93 = JsonToken.START_OBJECT;
                                       if(var116 != var93) {
                                          break label285;
                                       }
                                    }

                                    Log.e(TAG, "Unexpected object/array in simple dictionary");
                                    var0.skipChildren();
                                    break label285;
                                 }
                              }

                              JsonToken var88 = JsonToken.VALUE_TRUE;
                              byte var89;
                              if(var116 == var88) {
                                 var89 = 1;
                              } else {
                                 var89 = 0;
                              }

                              Boolean var90 = Boolean.valueOf((boolean)var89);
                              var63.put(var39, var90);
                           }
                        }
                     }
                  }
               }

               JsonToken var68 = var0.nextToken();
            }
         } else {
            if(validateSpecification(JMList.class, var1) != null) {
               var39 = var0.nextToken();
               JsonToken var98 = JsonToken.END_OBJECT;
               if(var39 == var98) {
                  List var99 = Collections.EMPTY_LIST;
                  boolean var100 = false;
                  var117 = var99;
                  return var117;
               }

               logUnexpectedToken(JsonToken.START_OBJECT, var1);
               JsonToken var115 = var39;

               while(true) {
                  JsonToken var103 = JsonToken.END_OBJECT;
                  if(var115 == var103) {
                     break;
                  }

                  label166: {
                     JsonToken var104 = JsonToken.START_ARRAY;
                     if(var115 != var104) {
                        JsonToken var105 = JsonToken.START_OBJECT;
                        if(var115 != var105) {
                           break label166;
                        }
                     }

                     var0.skipChildren();
                  }

                  JsonToken var106 = var0.nextToken();
               }
            } else {
               logUnexpectedToken(var0.getCurrentToken(), var1);
               var0.skipChildren();
            }

            boolean var107 = false;
            var117 = null;
         }
      }

      return var117;
   }

   protected static Object handleStringsAndNumbers(JsonToken var0, JsonParser var1, Set<JMBase> var2) throws JsonParseException, IOException {
      Object var6;
      label88: {
         JsonToken var3 = JsonToken.VALUE_NUMBER_INT;
         if(var0 != var3) {
            JsonToken var4 = JsonToken.VALUE_STRING;
            if(var0 != var4) {
               break label88;
            }
         }

         if(validateSpecification(JMLong.class, var2) != null) {
            label113: {
               Long var5;
               try {
                  var5 = Long.valueOf(Long.parseLong(var1.getText()));
               } catch (NumberFormatException var26) {
                  break label113;
               }

               var6 = var5;
               return var6;
            }
         }
      }

      if(validateSpecification(JMDouble.class, var2) != null) {
         label114: {
            Double var28;
            try {
               var28 = Double.valueOf(Double.parseDouble(var1.getText()));
            } catch (NumberFormatException var25) {
               break label114;
            }

            var6 = var28;
            return var6;
         }
      }

      label72: {
         JsonToken var9 = JsonToken.VALUE_NUMBER_INT;
         if(var0 != var9) {
            JsonToken var10 = JsonToken.VALUE_STRING;
            if(var0 != var10) {
               break label72;
            }
         }

         if(validateSpecification(JMBoolean.class, var2) != null) {
            if(var1.getText().equals("0")) {
               var6 = Boolean.valueOf((boolean)0);
               return var6;
            }

            if(var1.getText().equals("1")) {
               var6 = Boolean.valueOf((boolean)1);
               return var6;
            }
         }
      }

      JsonToken var11 = JsonToken.VALUE_STRING;
      JMBase var12;
      if(var0 == var11) {
         var12 = validateSpecification(JMEscaped.class, var2);
         if(var12 != null) {
            label93: {
               if(!$assertionsDisabled && !(var12 instanceof JMEscaped)) {
                  throw new AssertionError();
               }

               JMEscaped var13 = (JMEscaped)var12;
               JsonFactory var14 = var13.mFactory;
               String var15 = var1.getText();
               JsonParser var16 = var14.createJsonParser(var15);
               JsonToken var17 = var16.nextToken();

               Object var27;
               try {
                  JMBase var18 = var13.mInnerObject;
                  var27 = parseJsonResponse(var16, var18);
               } catch (JMException var24) {
                  break label93;
               }

               if(var27 != null) {
                  var6 = var27;
                  return var6;
               }
            }
         }
      }

      JsonToken var21 = JsonToken.VALUE_STRING;
      if(var0 == var21) {
         var12 = validateSpecification(JMString.class, var2);
         if(var12 != null) {
            if(!$assertionsDisabled && !(var12 instanceof JMString)) {
               throw new AssertionError();
            }

            JMString var22 = (JMString)var12;
            String var23 = var1.getText();
            var6 = var22.formatString(var23);
            return var6;
         }
      }

      logUnexpectedToken(var1.getCurrentToken(), var2);
      var6 = null;
      return var6;
   }

   protected static void logUnexpectedToken(JsonToken var0, JMBase var1) {
      if(Constants.isBetaBuild()) {
         StringBuilder var2 = new StringBuilder("Unexpected token ");
         String var3 = var0.toString();
         var2.append(var3);
         if(var1 != null) {
            StringBuilder var5 = var2.append("; expecting a ");
            String var6 = var1.toString();
            var2.append(var6);
         } else {
            StringBuilder var10 = var2.append("; field not defined.");
         }

         String var8 = TAG;
         String var9 = var2.toString();
         Log.e(var8, var9);
      }
   }

   protected static void logUnexpectedToken(JsonToken var0, Set<JMBase> var1) {
      if(Constants.isBetaBuild()) {
         boolean var2 = true;
         StringBuilder var3 = new StringBuilder("Unexpected token ");
         String var4 = var0.toString();
         var3.append(var4);
         StringBuilder var6 = var3.append("; expecting one of the following: (");
         Iterator var7 = var1.iterator();

         while(var7.hasNext()) {
            JMBase var8 = (JMBase)var7.next();
            if(var2) {
               var2 = false;
            } else {
               StringBuilder var11 = var3.append(", ");
            }

            String var9 = var8.toString();
            var3.append(var9);
         }

         StringBuilder var12 = var3.append(")");
         String var13 = TAG;
         String var14 = var3.toString();
         Log.e(var13, var14);
      }
   }

   public static Object parseJsonResponse(JsonParser var0, JMBase var1) throws IOException, JMException {
      HashSet var2 = new HashSet();
      var2.add(var1);
      return parseJsonResponse(var0, (Set)var2);
   }

   public static Object parseJsonResponse(JsonParser var0, Set<JMBase> var1) throws IOException, JMException {
      JsonToken var2 = var0.getCurrentToken();
      Object var3 = null;
      JsonToken var4 = JsonToken.VALUE_NUMBER_INT;
      if(var2 != var4) {
         JsonToken var5 = JsonToken.VALUE_NUMBER_FLOAT;
         if(var2 != var5) {
            JsonToken var6 = JsonToken.VALUE_STRING;
            if(var2 != var6) {
               JsonToken var7 = JsonToken.VALUE_TRUE;
               if(var2 != var7) {
                  JsonToken var8 = JsonToken.VALUE_FALSE;
                  if(var2 != var8) {
                     JsonToken var9 = JsonToken.START_ARRAY;
                     if(var2 == var9) {
                        var3 = handleArrays(var0, var1);
                     } else {
                        JsonToken var10 = JsonToken.START_OBJECT;
                        if(var2 == var10) {
                           var3 = handleObjects(var0, var1);
                           return var3;
                        }
                     }

                     return var3;
                  }
               }

               var3 = handleBooleans(var0, var1);
               return var3;
            }
         }
      }

      var3 = handleStringsAndNumbers(var2, var0, var1);
      return var3;
   }

   public static <typeClass extends Object> typeClass parseObjectJson(JsonParser var0, Class<typeClass> var1) throws JsonParseException, IOException, JMException {
      JMDict var2 = JMAutogen.generateJMParser(var1);
      Object var3 = parseJsonResponse(var0, (JMBase)var2);
      if(!$assertionsDisabled && var3 != null && var3.getClass() != var1) {
         throw new AssertionError();
      } else {
         return var3;
      }
   }

   public static <typeClass extends Object> List<typeClass> parseObjectListJson(JsonParser var0, Class<typeClass> var1) throws JsonParseException, IOException, JMException {
      JMDict var2 = JMAutogen.generateJMParser(var1);
      HashSet var3 = new HashSet();
      var3.add(var2);
      JMList var5 = new JMList(var3);
      Object var6 = parseJsonResponse(var0, (JMBase)var5);
      if(!$assertionsDisabled && var6 != null && !(var6 instanceof List)) {
         throw new AssertionError();
      } else {
         return (List)var6;
      }
   }

   protected static JMBase validateSpecification(Class<? extends JMBase> var0, Set<JMBase> var1) {
      Iterator var2 = var1.iterator();

      JMBase var4;
      while(true) {
         if(var2.hasNext()) {
            JMBase var3 = (JMBase)var2.next();
            if(!var0.isInstance(var3)) {
               continue;
            }

            var4 = var3;
            break;
         }

         var4 = null;
         break;
      }

      return var4;
   }

   protected static boolean validateSpecification(Class<? extends JMBase> var0, JMBase var1) {
      return var0.isInstance(var1);
   }
}
