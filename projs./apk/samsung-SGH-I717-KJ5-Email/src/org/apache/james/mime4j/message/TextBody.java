package org.apache.james.mime4j.message;

import java.io.IOException;
import java.io.Reader;
import org.apache.james.mime4j.message.Body;

public interface TextBody extends Body {

   Reader getReader() throws IOException;
}
