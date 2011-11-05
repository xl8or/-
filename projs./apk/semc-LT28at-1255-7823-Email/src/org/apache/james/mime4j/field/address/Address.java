package org.apache.james.mime4j.field.address;

import java.util.ArrayList;

public abstract class Address {

   public Address() {}

   final void addMailboxesTo(ArrayList var1) {
      this.doAddMailboxesTo(var1);
   }

   protected abstract void doAddMailboxesTo(ArrayList var1);
}
