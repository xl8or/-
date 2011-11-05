package org.apache.james.mime4j.message;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.james.mime4j.message.Entity;

public class BodyPart extends Entity {

   public BodyPart() {}

   public void writeTo(OutputStream var1) throws IOException {
      this.getHeader().writeTo(var1);
      this.getBody().writeTo(var1);
   }
}
