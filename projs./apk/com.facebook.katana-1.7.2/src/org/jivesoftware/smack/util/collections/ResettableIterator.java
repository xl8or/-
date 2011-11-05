package org.jivesoftware.smack.util.collections;

import java.util.Iterator;

public interface ResettableIterator<E extends Object> extends Iterator<E> {

   void reset();
}
