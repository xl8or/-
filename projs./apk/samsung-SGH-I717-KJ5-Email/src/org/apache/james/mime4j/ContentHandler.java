package org.apache.james.mime4j;

import java.io.IOException;
import java.io.InputStream;
import org.apache.james.mime4j.BodyDescriptor;

public interface ContentHandler {

   void body(BodyDescriptor var1, InputStream var2) throws IOException;

   void endBodyPart();

   void endHeader();

   void endMessage();

   void endMultipart();

   void epilogue(InputStream var1) throws IOException;

   void field(String var1);

   void preamble(InputStream var1) throws IOException;

   void raw(InputStream var1) throws IOException;

   void startBodyPart();

   void startHeader();

   void startMessage();

   void startMultipart(BodyDescriptor var1);
}
