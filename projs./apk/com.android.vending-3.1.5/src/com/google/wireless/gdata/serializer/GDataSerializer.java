package com.google.wireless.gdata.serializer;

import com.google.wireless.gdata.parser.ParseException;
import java.io.IOException;
import java.io.OutputStream;

public interface GDataSerializer {

   int FORMAT_CREATE = 1;
   int FORMAT_FULL = 0;
   int FORMAT_UPDATE = 2;


   String getContentType();

   void serialize(OutputStream var1, int var2) throws IOException, ParseException;
}
