package com.google.android.finsky.billing.carrierbilling.debug;

import com.google.android.finsky.billing.carrierbilling.debug.DcbDetail;
import com.google.android.finsky.billing.carrierbilling.debug.DcbDetailExtractor;
import com.google.android.finsky.billing.carrierbilling.debug.SimpleDetail;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Lists;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ReflectionDcbDetailExtractor implements DcbDetailExtractor {

   private Collection<DcbDetail> mCachedDetails = null;
   private final Object mObject;
   private final String mRootName;


   public ReflectionDcbDetailExtractor(Object var1, String var2) {
      this.mObject = var1;
      this.mRootName = var2;
   }

   private static Collection<DcbDetail> buildDetails(Object var0, String var1) {
      ArrayList var2 = new ArrayList();
      buildDetailsHelper(var0, var1, var2);
      return var2;
   }

   private static void buildDetailsHelper(Object var0, String var1, List<DcbDetail> var2) {
      if(var0 == null) {
         SimpleDetail var3 = new SimpleDetail;
         Object var6 = null;
         var3.<init>(var1, (String)var6);
         boolean var9 = var2.add(var3);
      } else {
         Class var10 = var0.getClass();
         boolean var11 = isArray(var0);
         boolean var12 = isIterable(var0);
         if(!var10.isPrimitive()) {
            Class var13 = Number.class;
            if(!var10.isAssignableFrom(var13)) {
               Class var14 = String.class;
               if(!var10.isAssignableFrom(var14)) {
                  if(!var12 && !var11) {
                     ArrayList var33 = Lists.newArrayList();
                     Method[] var34 = var10.getDeclaredMethods();
                     int var35 = var34.length;

                     int var51;
                     for(byte var79 = 0; var79 < var35; var51 = var79 + 1) {
                        Method var36 = var34[var79];
                        if(var36.getName().startsWith("get") && (var36.getModifiers() & 1) != 0 && var36.getParameterTypes().length == 0) {
                           String var37 = methodNameToTitle(var36.getName());
                           byte var38 = 0;

                           Object var42;
                           try {
                              Object[] var39 = new Object[var38];
                              var42 = var36.invoke(var0, var39);
                           } catch (IllegalArgumentException var76) {
                              Object[] var53 = new Object[0];
                              String var54 = "Shouldn\'t happen with no-arg methods";
                              FinskyLog.wtf(var76, var54, var53);
                              continue;
                           } catch (IllegalAccessException var77) {
                              Object[] var57 = new Object[0];
                              String var58 = "Shouldn\'t happen with public methods";
                              FinskyLog.wtf(var77, var58, var57);
                              continue;
                           } catch (InvocationTargetException var78) {
                              String var61 = "unknown";
                              if(var78.getCause() != null) {
                                 var61 = var78.getCause().getClass().getName();
                              }

                              Object[] var62 = new Object[3];
                              String var63 = var36.getName();
                              var62[0] = var63;
                              var62[1] = var61;
                              String var64 = var78.getMessage();
                              var62[2] = var64;
                              FinskyLog.e("%s throw exception (%s): %s", var62);
                              continue;
                           }

                           StringBuilder var44 = new StringBuilder();
                           StringBuilder var46 = var44.append(var1).append(".");
                           String var48 = var46.append(var37).toString();
                           buildDetailsHelper(var42, var48, var33);
                        }
                     }

                     if(!var33.isEmpty()) {
                        boolean var67 = var2.addAll(var33);
                        return;
                     }

                     SimpleDetail var68 = new SimpleDetail;
                     String var69 = var0.toString();
                     var68.<init>(var1, var69);
                     boolean var75 = var2.add(var68);
                     return;
                  }

                  Object var23;
                  if(var11) {
                     var23 = Arrays.asList((Object[])((Object[])var0));
                  } else {
                     var23 = (Iterable)var0;
                  }

                  int var24 = 0;

                  for(Iterator var25 = ((Iterable)var23).iterator(); var25.hasNext(); ++var24) {
                     Object var26 = var25.next();
                     StringBuilder var27 = new StringBuilder();
                     StringBuilder var29 = var27.append(var1).append(".");
                     String var31 = var29.append(var24).toString();
                     buildDetailsHelper(var26, var31, var2);
                  }

                  return;
               }
            }
         }

         SimpleDetail var15 = new SimpleDetail;
         String var16 = var0.toString();
         var15.<init>(var1, var16);
         boolean var22 = var2.add(var15);
      }
   }

   static boolean isArray(Object var0) {
      byte var1;
      if(var0 == null) {
         var1 = 0;
      } else {
         try {
            Object[] var2 = (Object[])((Object[])var0);
         } catch (ClassCastException var4) {
            var1 = var0.getClass().isArray();
            return (boolean)var1;
         }

         var1 = 1;
      }

      return (boolean)var1;
   }

   static boolean isIterable(Object var0) {
      boolean var1 = false;
      if(var0 != null) {
         try {
            Iterable var2 = (Iterable)var0;
         } catch (ClassCastException var4) {
            return var1;
         }

         var1 = true;
      }

      return var1;
   }

   private static String methodNameToTitle(String var0) {
      if(var0.startsWith("get")) {
         var0 = var0.substring(3);
      }

      return var0;
   }

   public Collection<DcbDetail> getDetails() {
      if(this.mCachedDetails == null) {
         Object var1 = this.mObject;
         String var2 = this.mRootName;
         Collection var3 = buildDetails(var1, var2);
         this.mCachedDetails = var3;
      }

      return this.mCachedDetails;
   }
}
