package com.google.wireless.gdata.parser;

import com.google.wireless.gdata.data.Entry;
import com.google.wireless.gdata.data.Feed;
import com.google.wireless.gdata.parser.ParseException;
import java.io.IOException;

public interface GDataParser {

   void close();

   boolean hasMoreData();

   Feed init() throws ParseException;

   Entry parseStandaloneEntry() throws ParseException, IOException;

   Entry readNextEntry(Entry var1) throws ParseException, IOException;
}
