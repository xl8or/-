package org.apache.commons.io.input;

import java.io.InputStream;
import org.apache.commons.io.input.ClosedInputStream;
import org.apache.commons.io.input.ProxyInputStream;

public class CloseShieldInputStream extends ProxyInputStream {

   public CloseShieldInputStream(InputStream var1) {
      super(var1);
   }

   public void close() {
      ClosedInputStream var1 = new ClosedInputStream();
      this.in = var1;
   }
}
