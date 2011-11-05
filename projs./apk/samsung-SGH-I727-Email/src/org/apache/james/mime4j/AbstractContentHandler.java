package org.apache.james.mime4j;

import java.io.IOException;
import java.io.InputStream;
import org.apache.james.mime4j.BodyDescriptor;
import org.apache.james.mime4j.ContentHandler;

public abstract class AbstractContentHandler implements ContentHandler {

   public AbstractContentHandler() {}

   public void body(BodyDescriptor var1, InputStream var2) throws IOException {}

   public void endBodyPart() {}

   public void endHeader() {}

   public void endMessage() {}

   public void endMultipart() {}

   public void epilogue(InputStream var1) throws IOException {}

   public void field(String var1) {}

   public void preamble(InputStream var1) throws IOException {}

   public void raw(InputStream var1) throws IOException {}

   public void startBodyPart() {}

   public void startHeader() {}

   public void startMessage() {}

   public void startMultipart(BodyDescriptor var1) {}
}
