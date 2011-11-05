package com.facebook.katana.util.jsonmirror.types;

import com.facebook.katana.util.jsonmirror.JMFatalException;
import com.facebook.katana.util.jsonmirror.types.JMBoolean;
import com.facebook.katana.util.jsonmirror.types.JMDouble;
import com.facebook.katana.util.jsonmirror.types.JMLong;
import com.facebook.katana.util.jsonmirror.types.JMSimpleDict;
import com.facebook.katana.util.jsonmirror.types.JMString;
import java.util.HashMap;
import java.util.Map;

public abstract class JMBase {

   public static final JMBoolean BOOLEAN = new JMBoolean();
   public static final JMDouble DOUBLE = new JMDouble();
   public static final JMLong LONG = new JMLong();
   public static final JMSimpleDict SIMPLE_DICT = new JMSimpleDict();
   public static final JMString STRING = new JMString();
   protected static Map<Class<?>, JMBase> classCache = new HashMap();


   public JMBase() {}

   public static JMBase getInstanceForClass(Class<?> var0) {
      Object var1 = (JMBase)classCache.get(var0);
      if(var1 == null) {
         if(var0 == String.class) {
            var1 = STRING;
         } else {
            label52: {
               if(var0 != Boolean.class) {
                  Class var3 = Boolean.TYPE;
                  if(var0 != var3) {
                     if(var0 != Long.class) {
                        Class var4 = Long.TYPE;
                        if(var0 != var4 && var0 != Integer.class) {
                           Class var5 = Integer.TYPE;
                           if(var0 != var5) {
                              if(var0 != Double.class) {
                                 Class var6 = Double.TYPE;
                                 if(var0 != var6 && var0 != Float.class) {
                                    Class var7 = Float.TYPE;
                                    if(var0 != var7) {
                                       if(Map.class.isAssignableFrom(var0)) {
                                          var1 = SIMPLE_DICT;
                                       } else if(JMBase.class.isAssignableFrom(var0)) {
                                          try {
                                             var1 = (JMBase)var0.newInstance();
                                          } catch (IllegalAccessException var16) {
                                             StringBuilder var9 = (new StringBuilder()).append("Error instantiating element parser for class ");
                                             String var10 = var0.getName();
                                             String var11 = var9.append(var10).toString();
                                             throw new JMFatalException(var11);
                                          } catch (InstantiationException var17) {
                                             StringBuilder var13 = (new StringBuilder()).append("Error instantiating element parser for class ");
                                             String var14 = var0.getName();
                                             String var15 = var13.append(var14).toString();
                                             throw new JMFatalException(var15);
                                          }
                                       }
                                       break label52;
                                    }
                                 }
                              }

                              var1 = DOUBLE;
                              break label52;
                           }
                        }
                     }

                     var1 = LONG;
                     break label52;
                  }
               }

               var1 = BOOLEAN;
            }
         }

         if(var1 != null) {
            classCache.put(var0, var1);
         }
      }

      return (JMBase)var1;
   }
}
