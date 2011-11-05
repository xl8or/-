package org.codehaus.jackson.map.introspect;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import org.codehaus.jackson.map.introspect.AnnotatedMethod;
import org.codehaus.jackson.map.introspect.MethodKey;

public final class AnnotatedMethodMap implements Iterable<AnnotatedMethod> {

   LinkedHashMap<MethodKey, AnnotatedMethod> _methods;


   public AnnotatedMethodMap() {}

   public void add(AnnotatedMethod var1) {
      if(this._methods == null) {
         LinkedHashMap var2 = new LinkedHashMap();
         this._methods = var2;
      }

      LinkedHashMap var3 = this._methods;
      Method var4 = var1.getAnnotated();
      MethodKey var5 = new MethodKey(var4);
      var3.put(var5, var1);
   }

   public AnnotatedMethod find(String var1, Class<?>[] var2) {
      AnnotatedMethod var3;
      if(this._methods == null) {
         var3 = null;
      } else {
         LinkedHashMap var4 = this._methods;
         MethodKey var5 = new MethodKey(var1, var2);
         var3 = (AnnotatedMethod)var4.get(var5);
      }

      return var3;
   }

   public AnnotatedMethod find(Method var1) {
      AnnotatedMethod var2;
      if(this._methods == null) {
         var2 = null;
      } else {
         LinkedHashMap var3 = this._methods;
         MethodKey var4 = new MethodKey(var1);
         var2 = (AnnotatedMethod)var3.get(var4);
      }

      return var2;
   }

   public boolean isEmpty() {
      boolean var1;
      if(this._methods != null && this._methods.size() != 0) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public Iterator<AnnotatedMethod> iterator() {
      Iterator var1;
      if(this._methods != null) {
         var1 = this._methods.values().iterator();
      } else {
         var1 = Collections.emptyList().iterator();
      }

      return var1;
   }

   public void remove(AnnotatedMethod var1) {
      if(this._methods != null) {
         LinkedHashMap var2 = this._methods;
         Method var3 = var1.getAnnotated();
         MethodKey var4 = new MethodKey(var3);
         var2.remove(var4);
      }
   }

   public int size() {
      int var1;
      if(this._methods == null) {
         var1 = 0;
      } else {
         var1 = this._methods.size();
      }

      return var1;
   }
}
