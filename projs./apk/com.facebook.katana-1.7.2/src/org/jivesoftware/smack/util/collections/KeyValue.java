package org.jivesoftware.smack.util.collections;


public interface KeyValue<K extends Object, V extends Object> {

   K getKey();

   V getValue();
}
