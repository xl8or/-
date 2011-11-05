package org.jivesoftware.smack.util.collections;

import java.util.Iterator;
import org.jivesoftware.smack.util.collections.AbstractEmptyIterator;
import org.jivesoftware.smack.util.collections.ResettableIterator;

public class EmptyIterator<E extends Object> extends AbstractEmptyIterator<E> implements ResettableIterator<E> {

   public static final Iterator INSTANCE = RESETTABLE_INSTANCE;
   public static final ResettableIterator RESETTABLE_INSTANCE = new EmptyIterator();


   protected EmptyIterator() {}

   public static <T extends Object> Iterator<T> getInstance() {
      return INSTANCE;
   }
}
