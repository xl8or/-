package org.apache.james.mime4j.message;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.james.mime4j.message.Entity;

public interface Body {

   Entity getParent();

   void setParent(Entity var1);

   void writeTo(OutputStream var1) throws IOException;
}
