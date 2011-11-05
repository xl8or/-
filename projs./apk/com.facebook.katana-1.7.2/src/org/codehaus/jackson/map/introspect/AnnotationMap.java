package org.codehaus.jackson.map.introspect;

import java.lang.annotation.Annotation;
import java.util.HashMap;

public final class AnnotationMap {

   HashMap<Class<? extends Annotation>, Annotation> _annotations;


   public AnnotationMap() {}

   protected final void _add(Annotation var1) {
      if(this._annotations == null) {
         HashMap var2 = new HashMap();
         this._annotations = var2;
      }

      HashMap var3 = this._annotations;
      Class var4 = var1.annotationType();
      var3.put(var4, var1);
   }

   public void add(Annotation var1) {
      this._add(var1);
   }

   public void addIfNotPresent(Annotation var1) {
      if(this._annotations != null) {
         HashMap var2 = this._annotations;
         Class var3 = var1.annotationType();
         if(var2.containsKey(var3)) {
            return;
         }
      }

      this._add(var1);
   }

   public <A extends Object & Annotation> A get(Class<A> var1) {
      Annotation var2;
      if(this._annotations == null) {
         var2 = null;
      } else {
         var2 = (Annotation)this._annotations.get(var1);
      }

      return var2;
   }

   public int size() {
      int var1;
      if(this._annotations == null) {
         var1 = 0;
      } else {
         var1 = this._annotations.size();
      }

      return var1;
   }

   public String toString() {
      String var1;
      if(this._annotations == null) {
         var1 = "[null]";
      } else {
         var1 = this._annotations.toString();
      }

      return var1;
   }
}
