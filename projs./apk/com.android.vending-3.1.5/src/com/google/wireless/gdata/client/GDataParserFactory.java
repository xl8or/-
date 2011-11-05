package com.google.wireless.gdata.client;

import com.google.wireless.gdata.data.Entry;
import com.google.wireless.gdata.parser.GDataParser;
import com.google.wireless.gdata.parser.ParseException;
import com.google.wireless.gdata.serializer.GDataSerializer;
import java.io.InputStream;

public interface GDataParserFactory {

   GDataParser createParser(InputStream var1) throws ParseException;

   GDataParser createParser(Class var1, InputStream var2) throws ParseException;

   GDataSerializer createSerializer(Entry var1);
}
