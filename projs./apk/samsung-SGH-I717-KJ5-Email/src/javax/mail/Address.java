package javax.mail;

import java.io.Serializable;

public abstract class Address implements Serializable {

   public Address() {}

   public abstract boolean equals(Object var1);

   public abstract String getType();

   public abstract String toString();
}
