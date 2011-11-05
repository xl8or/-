package com.google.wireless.gdata2.serializer;

import com.google.wireless.gdata2.parser.ParseException;
import java.io.IOException;
import java.io.OutputStream;

public interface GDataSerializer {

   int FORMAT_BATCH = 3;
   int FORMAT_CREATE = 1;
   int FORMAT_FULL = 0;
   int FORMAT_UPDATE = 2;


   String getContentType();

   boolean isPartial();

   void serialize(OutputStream var1, int var2) throws IOException, ParseException;
}
