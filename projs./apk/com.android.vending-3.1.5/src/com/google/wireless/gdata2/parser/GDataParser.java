package com.google.wireless.gdata2.parser;

import com.google.wireless.gdata2.data.Entry;
import com.google.wireless.gdata2.data.Feed;
import com.google.wireless.gdata2.parser.ParseException;
import java.io.IOException;

public interface GDataParser {

   void close();

   boolean hasMoreData();

   Feed parseFeedEnvelope() throws ParseException;

   Entry parseStandaloneEntry() throws ParseException, IOException;

   Entry readNextEntry(Entry var1) throws ParseException, IOException;
}
