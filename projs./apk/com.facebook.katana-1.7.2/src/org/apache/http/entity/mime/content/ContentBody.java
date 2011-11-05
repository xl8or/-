package org.apache.http.entity.mime.content;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.http.entity.mime.content.ContentDescriptor;

public interface ContentBody extends ContentDescriptor {

   String getFilename();

   void writeTo(OutputStream var1) throws IOException;
}
