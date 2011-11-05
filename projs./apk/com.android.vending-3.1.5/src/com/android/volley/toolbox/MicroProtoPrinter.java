package com.android.volley.toolbox;

import com.google.android.finsky.utils.FinskyLog;
import com.google.protobuf.micro.ByteStringMicro;
import com.google.protobuf.micro.MessageMicro;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class MicroProtoPrinter {

   private static String PRETTY_PRINT_INDENT = "  ";
   private static final int PRETTY_PRINT_MAX_STRING_LEN = 200;


   public MicroProtoPrinter() {}

   private static String deCamelCaseify(String var0) {
      StringBuffer var1 = new StringBuffer();
      int var2 = 0;

      while(true) {
         int var3 = var0.length();
         if(var2 >= var3) {
            return var1.toString();
         }

         char var4 = var0.charAt(var2);
         if(var2 == 0) {
            char var5 = Character.toLowerCase(var4);
            var1.append(var5);
         } else if(Character.isUpperCase(var4)) {
            StringBuffer var7 = var1.append('_');
            char var8 = Character.toLowerCase(var4);
            var7.append(var8);
         } else {
            var1.append(var4);
         }

         ++var2;
      }
   }

   private static String escapeString(String var0) {
      int var1 = var0.length();
      StringBuilder var2 = new StringBuilder(var1);

      for(int var3 = 0; var3 < var1; ++var3) {
         char var4 = var0.charAt(var3);
         if(var4 >= 32 && var4 <= 126) {
            var2.append(var4);
         } else {
            Object[] var6 = new Object[1];
            Integer var7 = Integer.valueOf(var4);
            var6[0] = var7;
            String var8 = String.format("\\u%04x", var6);
            var2.append(var8);
         }
      }

      return var2.toString();
   }

   public static <T extends MessageMicro> String prettyPrint(String var0, Class<T> var1, T var2) {
      StringBuffer var3 = new StringBuffer();

      String var4;
      try {
         prettyPrint(var0, var1, var2, "", var3);
      } catch (IllegalAccessException var14) {
         StringBuilder var6 = (new StringBuilder()).append("Error during pretty print: ");
         String var7 = var14.getMessage();
         var4 = var6.append(var7).toString();
         return var4;
      } catch (NoSuchFieldException var15) {
         StringBuilder var9 = (new StringBuilder()).append("Error during pretty print: ");
         String var10 = var15.getMessage();
         var4 = var9.append(var10).toString();
         return var4;
      } catch (InvocationTargetException var16) {
         StringBuilder var12 = (new StringBuilder()).append("Error during pretty print: ");
         String var13 = var16.getMessage();
         var4 = var12.append(var13).toString();
         return var4;
      }

      var4 = var3.toString();
      return var4;
   }

   private static void prettyPrint(String var0, Class<?> var1, Object var2, String var3, StringBuffer var4) throws IllegalAccessException, NoSuchFieldException, InvocationTargetException {
      Class var5 = MessageMicro.class;
      if(var5.isAssignableFrom(var1)) {
         StringBuilder var7 = new StringBuilder();
         StringBuilder var9 = var7.append(var3);
         String var10 = PRETTY_PRINT_INDENT;
         String var11 = var9.append(var10).toString();
         StringBuffer var14 = var4.append(var3);
         StringBuffer var16 = var14.append(var0).append(" <\n");
         Method[] var17 = var1.getMethods();
         int var18 = var17.length;

         for(int var19 = 0; var19 < var18; ++var19) {
            String var20 = var17[var19].getName();
            String var22 = "set";
            if(var20.startsWith(var22)) {
               byte var24 = 3;
               String var25 = var20.substring(var24);
               Method var26 = null;
               boolean var27 = false;

               Method var35;
               label70: {
                  try {
                     StringBuilder var28 = (new StringBuilder()).append("get");
                     String var30 = var28.append(var25).toString();
                     Class[] var31 = new Class[0];
                     var35 = var1.getMethod(var30, var31);
                  } catch (NoSuchMethodException var123) {
                     break label70;
                  }

                  var26 = var35;
               }

               label66: {
                  try {
                     StringBuilder var36 = (new StringBuilder()).append("get");
                     String var38 = var36.append(var25).append("List").toString();
                     Class[] var39 = new Class[0];
                     var35 = var1.getMethod(var38, var39);
                  } catch (NoSuchMethodException var122) {
                     break label66;
                  }

                  var26 = var35;
                  var27 = true;
               }

               if(var26 == null) {
                  StringBuilder var43 = (new StringBuilder()).append("No getter found for setter: ");
                  String var45 = var43.append(var20).toString();
                  Object[] var46 = new Object[0];
                  FinskyLog.v(var45, var46);
               } else {
                  Class var47 = var26.getReturnType();
                  Object[] var48 = new Object[0];
                  Object var51 = var26.invoke(var2, var48);
                  if(!var27) {
                     Method var59;
                     try {
                        StringBuilder var52 = (new StringBuilder()).append("has");
                        String var54 = var52.append(var25).toString();
                        Class[] var55 = new Class[0];
                        var59 = var1.getMethod(var54, var55);
                     } catch (NoSuchMethodException var120) {
                        continue;
                     }

                     Object[] var61 = new Object[0];
                     if(((Boolean)var59.invoke(var2, var61)).booleanValue()) {
                        prettyPrint(var25, var47, var51, var11, var4);
                     }
                  } else {
                     Method var70 = null;

                     label59: {
                        Method var79;
                        try {
                           StringBuilder var71 = (new StringBuilder()).append("get");
                           String var73 = var71.append(var25).toString();
                           Class[] var74 = new Class[1];
                           Class var75 = Integer.TYPE;
                           var74[0] = var75;
                           var79 = var1.getMethod(var73, var74);
                        } catch (NoSuchMethodException var121) {
                           break label59;
                        }

                        var70 = var79;
                     }

                     if(var70 != null) {
                        List var80 = (List)var51;
                        int var81 = 0;

                        while(true) {
                           int var82 = var80.size();
                           if(var81 >= var82) {
                              break;
                           }

                           Class var83 = var70.getReturnType();
                           Object var84 = var80.get(var81);
                           prettyPrint(var25, var83, var84, var11, var4);
                           ++var81;
                        }
                     }
                  }
               }
            }
         }

         StringBuffer var92 = var4.append(var3).append(">");
      } else {
         String var96 = deCamelCaseify(var0);
         StringBuffer var99 = var4.append(var3);
         StringBuffer var101 = var99.append(var96).append(": ");
         if(var2 instanceof String) {
            String var102 = sanitizeString((String)var2);
            char var104 = 34;
            StringBuffer var105 = var4.append(var104);
            StringBuffer var107 = var105.append(var102).append('\"');
         } else if(var2 instanceof ByteStringMicro) {
            ByteStringMicro var108 = (ByteStringMicro)var2;
            String var110 = "byte[";
            StringBuffer var111 = var4.append(var110);
            int var112 = var108.size();
            StringBuffer var113 = var111.append(var112).append(']');
         } else {
            StringBuffer var116 = var4.append(var2);
         }
      }

      char var94 = 10;
      var4.append(var94);
   }

   private static String sanitizeString(String var0) {
      if(!var0.startsWith("http") && var0.length() > 200) {
         StringBuilder var1 = new StringBuilder();
         String var2 = var0.substring(0, 200);
         var0 = var1.append(var2).append("[...]").toString();
      }

      return escapeString(var0);
   }
}
