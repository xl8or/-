package com.google.wireless.gdata2.client;

import com.google.wireless.gdata2.data.Entry;
import com.google.wireless.gdata2.parser.GDataParser;
import com.google.wireless.gdata2.parser.ParseException;
import com.google.wireless.gdata2.serializer.GDataSerializer;
import java.io.InputStream;
import java.util.Enumeration;

public interface GDataParserFactory {

   GDataParser createParser(InputStream var1) throws ParseException;

   GDataParser createParser(Class var1, InputStream var2) throws ParseException;

   GDataSerializer createSerializer(Entry var1);

   GDataSerializer createSerializer(Entry var1, Entry var2);

   GDataSerializer createSerializer(Enumeration var1);
}
