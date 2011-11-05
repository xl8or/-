package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractMapBasedMultiset;
import com.google.common.collect.Iterables;
import com.google.common.collect.Serialization;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.EnumMap;
import java.util.Iterator;

@GwtCompatible
public final class EnumMultiset<E extends Enum<E>> extends AbstractMapBasedMultiset<E> {

   private static final long serialVersionUID;
   private transient Class<E> type;


   private EnumMultiset(Class<E> var1) {
      EnumMap var2 = new EnumMap(var1);
      super(var2);
      this.type = var1;
   }

   public static <E extends Enum<E>> EnumMultiset<E> create(Class<E> var0) {
      return new EnumMultiset(var0);
   }

   public static <E extends Enum<E>> EnumMultiset<E> create(Iterable<E> var0) {
      Iterator var1 = var0.iterator();
      Preconditions.checkArgument(var1.hasNext(), "EnumMultiset constructor passed empty Iterable");
      Class var2 = ((Enum)var1.next()).getDeclaringClass();
      EnumMultiset var3 = new EnumMultiset(var2);
      Iterables.addAll(var3, var0);
      return var3;
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      var1.defaultReadObject();
      Class var2 = (Class)var1.readObject();
      this.type = var2;
      Class var3 = this.type;
      EnumMap var4 = new EnumMap(var3);
      this.setBackingMap(var4);
      Serialization.populateMultiset(this, var1);
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      var1.defaultWriteObject();
      Class var2 = this.type;
      var1.writeObject(var2);
      Serialization.writeMultiset(this, var1);
   }
}
