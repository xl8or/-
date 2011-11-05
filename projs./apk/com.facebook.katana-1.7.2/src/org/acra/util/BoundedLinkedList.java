package org.acra.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public class BoundedLinkedList<E extends Object> extends LinkedList<E> {

   private int maxSize = -1;


   public BoundedLinkedList(int var1) {
      this.maxSize = var1;
   }

   public void add(int var1, E var2) {
      int var3 = this.size();
      int var4 = this.maxSize;
      if(var3 == var4) {
         Object var5 = this.removeFirst();
      }

      super.add(var1, var2);
   }

   public boolean add(E var1) {
      int var2 = this.size();
      int var3 = this.maxSize;
      if(var2 == var3) {
         Object var4 = this.removeFirst();
      }

      return super.add(var1);
   }

   public boolean addAll(int var1, Collection<? extends E> var2) {
      throw new UnsupportedOperationException();
   }

   public boolean addAll(Collection<? extends E> var1) {
      int var2 = this.size();
      int var3 = var1.size();
      int var4 = var2 + var3;
      int var5 = this.maxSize;
      int var6 = var4 - var5;
      if(var6 > 0) {
         this.removeRange(0, var6);
      }

      return super.addAll(var1);
   }

   public void addFirst(E var1) {
      throw new UnsupportedOperationException();
   }

   public void addLast(E var1) {
      this.add(var1);
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      Iterator var2 = this.iterator();

      while(var2.hasNext()) {
         String var3 = var2.next().toString();
         var1.append(var3);
      }

      return var1.toString();
   }
}
