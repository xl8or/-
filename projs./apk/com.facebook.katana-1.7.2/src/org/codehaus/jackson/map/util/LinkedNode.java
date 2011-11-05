package org.codehaus.jackson.map.util;


public final class LinkedNode<T extends Object> {

   final LinkedNode<T> _next;
   final T _value;


   public LinkedNode(T var1, LinkedNode<T> var2) {
      this._value = var1;
      this._next = var2;
   }

   public static <ST extends Object> boolean contains(LinkedNode<ST> var0, ST var1) {
      LinkedNode var2 = var0;

      boolean var3;
      while(true) {
         if(var2 == null) {
            var3 = false;
            break;
         }

         if(var2.value() == var1) {
            var3 = true;
            break;
         }

         var2 = var2.next();
      }

      return var3;
   }

   public LinkedNode<T> next() {
      return this._next;
   }

   public T value() {
      return this._value;
   }
}
