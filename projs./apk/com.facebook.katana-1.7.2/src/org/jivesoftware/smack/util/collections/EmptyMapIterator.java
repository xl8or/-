package org.jivesoftware.smack.util.collections;

import org.jivesoftware.smack.util.collections.AbstractEmptyIterator;
import org.jivesoftware.smack.util.collections.MapIterator;
import org.jivesoftware.smack.util.collections.ResettableIterator;

public class EmptyMapIterator extends AbstractEmptyIterator implements MapIterator, ResettableIterator {

   public static final MapIterator INSTANCE = new EmptyMapIterator();


   protected EmptyMapIterator() {}
}
