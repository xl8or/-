package com.facebook.katana.util.jsonmirror;

import com.facebook.katana.service.method.FBJsonFactory;
import com.facebook.katana.util.ReflectionUtils;
import com.facebook.katana.util.Tuple;
import com.facebook.katana.util.jsonmirror.JMDictDestination;
import com.facebook.katana.util.jsonmirror.JMFatalException;
import com.facebook.katana.util.jsonmirror.types.JMBase;
import com.facebook.katana.util.jsonmirror.types.JMDict;
import com.facebook.katana.util.jsonmirror.types.JMEscaped;
import com.facebook.katana.util.jsonmirror.types.JMList;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import org.codehaus.jackson.JsonFactory;

public class JMAutogen {

   // $FF: synthetic field
   static final boolean $assertionsDisabled;
   protected static Map<Class<? extends JMDictDestination>, JMDict> jmParserCache;


   static {
      byte var0;
      if(!JMAutogen.class.desiredAssertionStatus()) {
         var0 = 1;
      } else {
         var0 = 0;
      }

      $assertionsDisabled = (boolean)var0;
      jmParserCache = new HashMap();
   }

   public JMAutogen() {}

   public static JMDict generateJMParser(Class<? extends JMDictDestination> var0) {
      JMDict var1 = (JMDict)jmParserCache.get(var0);
      JMDict var31;
      if(var1 == null) {
         HashMap var2 = new HashMap();
         Iterator var3 = getRawFieldsFromClass(var0).entrySet().iterator();

         while(var3.hasNext()) {
            Entry var4 = (Entry)var3.next();
            Field var5 = (Field)var4.getKey();
            Annotation var6 = (Annotation)var4.getValue();
            String var10;
            if(!(var6 instanceof JMAutogen.InferredType) && !(var6 instanceof JMAutogen.ExplicitType) && !(var6 instanceof JMAutogen.EscapedObjectType)) {
               if(var6 instanceof JMAutogen.ListType) {
                  JMAutogen.ListType var21 = (JMAutogen.ListType)var6;
                  var10 = var5.getName();
                  String var32 = var21.jsonFieldName();
                  JMList var34 = generateJMParser(var21);
                  if(!$assertionsDisabled && var2.containsKey(var32)) {
                     throw new AssertionError();
                  }

                  Tuple var22 = new Tuple(var10, var34);
                  var2.put(var32, var22);
               }
            } else {
               Object var8;
               String var9;
               if(var6 instanceof JMAutogen.InferredType) {
                  String var7 = ((JMAutogen.InferredType)var6).jsonFieldName();
                  var8 = var5.getType();
                  var9 = var7;
               } else if(var6 instanceof JMAutogen.ExplicitType) {
                  JMAutogen.ExplicitType var12 = (JMAutogen.ExplicitType)var6;
                  var9 = var12.jsonFieldName();
                  Class var13 = var12.type();
               } else {
                  String var14 = ((JMAutogen.EscapedObjectType)var6).jsonFieldName();
                  Class var15 = var5.getType();
                  var9 = var14;
               }

               var10 = var5.getName();
               Object var11 = JMBase.getInstanceForClass((Class)var8);
               if(var11 == null) {
                  if(!JMDictDestination.class.isAssignableFrom((Class)var8)) {
                     StringBuilder var16 = (new StringBuilder()).append("could not infer type for ");
                     String var17 = var0.getName();
                     String var18 = var16.append(var17).append(":").append(var10).toString();
                     throw new JMFatalException(var18);
                  }

                  var11 = generateJMParser((Class)var8);
               }

               Object var33;
               if(var6 instanceof JMAutogen.EscapedObjectType) {
                  var8 = new FBJsonFactory();
                  var33 = new JMEscaped((JMBase)var11, (JsonFactory)var8);
               } else {
                  var33 = var11;
               }

               if(!$assertionsDisabled && var2.containsKey(var9)) {
                  throw new AssertionError();
               }

               Tuple var19 = new Tuple(var10, var33);
               var2.put(var9, var19);
            }
         }

         byte var24 = 0;
         Annotation[] var25 = var0.getDeclaredAnnotations();
         int var26 = var25.length;
         int var27 = 0;

         byte var28;
         while(true) {
            if(var27 >= var26) {
               var28 = var24;
               break;
            }

            if(var25[var27] instanceof JMAutogen.IgnoreUnexpectedJsonFields) {
               var28 = 1;
               break;
            }

            ++var27;
         }

         postprocessFields(var0, var2);
         JMDict var29 = new JMDict(var0, var2, (boolean)var28);
         jmParserCache.put(var0, var29);
         var31 = var29;
      } else {
         var31 = var1;
      }

      return var31;
   }

   protected static JMList generateJMParser(JMAutogen.ListType var0) {
      HashSet var1 = new HashSet();
      Class[] var2 = var0.listElementTypes();
      int var3 = 0;

      while(true) {
         int var4 = var2.length;
         if(var3 >= var4) {
            return new JMList(var1);
         }

         Class var5 = var2[var3];
         Object var6 = JMBase.getInstanceForClass(var5);
         if(var6 == null) {
            if(!JMDictDestination.class.isAssignableFrom(var5)) {
               StringBuilder var8 = (new StringBuilder()).append("could not infer type for ");
               String var9 = var5.getName();
               String var10 = var8.append(var9).toString();
               throw new JMFatalException(var10);
            }

            var6 = generateJMParser(var5);
         }

         var1.add(var6);
         ++var3;
      }
   }

   public static Set<String> getJsonFieldsFromClass(Class<? extends JMDictDestination> var0) {
      Map var1 = getRawFieldsFromClass(var0);
      HashMap var2 = new HashMap();
      Iterator var3 = var1.values().iterator();

      while(var3.hasNext()) {
         Annotation var4 = (Annotation)var3.next();
         if(var4 instanceof JMAutogen.InferredType) {
            String var5 = ((JMAutogen.InferredType)var4).jsonFieldName();
            var2.put(var5, (Object)null);
         } else if(var4 instanceof JMAutogen.ExplicitType) {
            String var7 = ((JMAutogen.ExplicitType)var4).jsonFieldName();
            var2.put(var7, (Object)null);
         } else {
            if(!(var4 instanceof JMAutogen.ListType)) {
               throw new JMFatalException("Got a class with unexpected JMAutogen annotations");
            }

            String var9 = ((JMAutogen.ListType)var4).jsonFieldName();
            var2.put(var9, (Object)null);
         }
      }

      postprocessFields(var0, var2);
      return var2.keySet();
   }

   private static Map<Field, Annotation> getRawFieldsFromClass(Class<?> var0) {
      JMAutogen.1 var1 = new JMAutogen.1(var0);
      ReflectionUtils.IncludeFlags var2 = ReflectionUtils.IncludeFlags.INCLUDE_SUPERCLASSES;
      ReflectionUtils.IncludeFlags var3 = ReflectionUtils.IncludeFlags.INCLUDE_FIELDS;
      EnumSet var4 = EnumSet.of(var2, var3);
      return ReflectionUtils.getComponents(var0, var1, var4);
   }

   private static void postprocessFields(Class<? extends JMDictDestination> var0, Map<String, ? extends Object> var1) {
      try {
         Class[] var2 = new Class[]{Map.class};
         Method var3 = var0.getDeclaredMethod("postprocessJMAutogenFields", var2);
         if(var3 == null) {
            return;
         }

         var3.setAccessible((boolean)1);
         Object[] var4 = new Object[]{var1};
         var3.invoke((Object)null, var4);
         return;
      } catch (NoSuchMethodException var9) {
         ;
      } catch (IllegalAccessException var10) {
         ;
      } catch (InvocationTargetException var11) {
         ;
      }

      if(!$assertionsDisabled) {
         throw new AssertionError();
      }
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target({ElementType.FIELD})
   public @interface ExplicitType {

      String jsonFieldName();

      Class<? extends JMBase> type();
   }

   static class 1 implements ReflectionUtils.Filter<Annotation> {

      // $FF: synthetic field
      final Class val$cls;


      1(Class var1) {
         this.val$cls = var1;
      }

      public Annotation mapper(AccessibleObject var1) {
         Annotation[] var2 = var1.getDeclaredAnnotations();
         Annotation var3 = null;
         int var4 = 0;

         while(true) {
            int var5 = var2.length;
            if(var4 >= var5) {
               return var3;
            }

            Annotation var6 = var2[var4];
            if(var6 instanceof JMAutogen.InferredType || var6 instanceof JMAutogen.ExplicitType || var6 instanceof JMAutogen.ListType || var6 instanceof JMAutogen.EscapedObjectType) {
               if(var3 != null) {
                  StringBuilder var7 = new StringBuilder();
                  String var8 = this.val$cls.getName();
                  StringBuilder var9 = var7.append(var8).append(":");
                  String var10 = var1.toString();
                  String var11 = var9.append(var10).append(" has more than one JM annotation").toString();
                  throw new JMFatalException(var11);
               }

               var3 = var6;
            }

            ++var4;
         }
      }
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target({ElementType.FIELD})
   public @interface ListType {

      String jsonFieldName();

      Class<?>[] listElementTypes();
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target({ElementType.TYPE})
   public @interface IgnoreUnexpectedJsonFields {
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target({ElementType.FIELD})
   public @interface InferredType {

      String jsonFieldName();
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target({ElementType.TYPE})
   public @interface IgnoreSuperclassFields {
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target({ElementType.FIELD})
   public @interface EscapedObjectType {

      String jsonFieldName();
   }
}
