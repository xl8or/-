package org.jivesoftware.smack.util.collections;

import java.util.Map;
import org.jivesoftware.smack.util.collections.MapIterator;

public interface IterableMap<K extends Object, V extends Object> extends Map<K, V> {

   MapIterator<K, V> mapIterator();
}
