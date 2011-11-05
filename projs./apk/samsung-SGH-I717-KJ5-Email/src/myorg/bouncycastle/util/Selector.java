package myorg.bouncycastle.util;


public interface Selector extends Cloneable {

   Object clone();

   boolean match(Object var1);
}
