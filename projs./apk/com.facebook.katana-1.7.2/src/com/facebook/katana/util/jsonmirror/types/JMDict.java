package com.facebook.katana.util.jsonmirror.types;

import com.facebook.katana.util.Tuple;
import com.facebook.katana.util.jsonmirror.JMDictDestination;
import com.facebook.katana.util.jsonmirror.JMFatalException;
import com.facebook.katana.util.jsonmirror.types.JMBase;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class JMDict extends JMBase {

   private final Constructor<? extends JMDictDestination> mConstructor;
   private final Map<String, Tuple<String, JMBase>> mFieldTypes;
   public final boolean mIgnoreUnexpectedJsonFields;


   public JMDict(Class<? extends JMDictDestination> var1, Map<String, Tuple<String, JMBase>> var2) {
      this(var1, var2, (boolean)0);
   }

   public JMDict(Class<? extends JMDictDestination> var1, Map<String, Tuple<String, JMBase>> var2, boolean var3) {
      try {
         Class[] var4 = new Class[0];
         Constructor var5 = var1.getDeclaredConstructor(var4);
         this.mConstructor = var5;
      } catch (NoSuchMethodException var7) {
         String var6 = var7.getMessage();
         throw new JMFatalException(var6);
      }

      this.mConstructor.setAccessible((boolean)1);
      this.mFieldTypes = var2;
      this.mIgnoreUnexpectedJsonFields = var3;
   }

   public Map<String, Tuple<String, JMBase>> getFieldTypes() {
      return this.mFieldTypes;
   }

   public JMDictDestination getNewInstance() {
      JMDictDestination var3;
      JMDictDestination var10;
      try {
         Constructor var1 = this.mConstructor;
         Object[] var2 = new Object[0];
         var10 = (JMDictDestination)var1.newInstance(var2);
      } catch (IllegalAccessException var7) {
         var3 = null;
         return var3;
      } catch (InvocationTargetException var8) {
         var3 = null;
         return var3;
      } catch (InstantiationException var9) {
         var3 = null;
         return var3;
      }

      var3 = var10;
      return var3;
   }

   public Tuple<String, JMBase> getObjectForJsonField(String var1) {
      return (Tuple)this.mFieldTypes.get(var1);
   }
}
