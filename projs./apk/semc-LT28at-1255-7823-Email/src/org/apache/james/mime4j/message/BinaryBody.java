package org.apache.james.mime4j.message;

import java.io.IOException;
import java.io.InputStream;
import org.apache.james.mime4j.message.Body;

public interface BinaryBody extends Body {

   InputStream getInputStream() throws IOException;
}
