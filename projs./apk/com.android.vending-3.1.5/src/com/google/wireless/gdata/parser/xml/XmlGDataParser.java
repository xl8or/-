package com.google.wireless.gdata.parser.xml;

import com.google.wireless.gdata.data.Entry;
import com.google.wireless.gdata.data.Feed;
import com.google.wireless.gdata.data.StringUtils;
import com.google.wireless.gdata.data.XmlUtils;
import com.google.wireless.gdata.parser.GDataParser;
import com.google.wireless.gdata.parser.ParseException;
import java.io.IOException;
import java.io.InputStream;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class XmlGDataParser implements GDataParser {

   public static final String NAMESPACE_ATOM_URI = "http://www.w3.org/2005/Atom";
   public static final String NAMESPACE_GD = "gd";
   public static final String NAMESPACE_GD_URI = "http://schemas.google.com/g/2005";
   public static final String NAMESPACE_OPENSEARCH = "openSearch";
   public static final String NAMESPACE_OPENSEARCH_URI = "http://a9.com/-/spec/opensearchrss/1.0/";
   private final InputStream is;
   private boolean isInBadState;
   private final XmlPullParser parser;


   public XmlGDataParser(InputStream var1, XmlPullParser var2) throws ParseException {
      this.is = var1;
      this.parser = var2;
      this.isInBadState = (boolean)0;
      if(this.is != null) {
         try {
            this.parser.setInput(var1, (String)null);
         } catch (XmlPullParserException var4) {
            throw new ParseException("Could not create XmlGDataParser", var4);
         }
      }
   }

   private void handleAuthor(Entry var1) throws XmlPullParserException, IOException {
      int var2 = this.parser.getEventType();
      String var3 = this.parser.getName();
      if(var2 == 2) {
         String var4 = this.parser.getName();
         if("author".equals(var4)) {
            for(int var8 = this.parser.next(); var8 != 1; var8 = this.parser.next()) {
               switch(var8) {
               case 2:
                  String var9 = this.parser.getName();
                  if("name".equals(var9)) {
                     String var10 = XmlUtils.extractChildText(this.parser);
                     var1.setAuthor(var10);
                  } else if("email".equals(var9)) {
                     String var11 = XmlUtils.extractChildText(this.parser);
                     var1.setEmail(var11);
                  }
                  break;
               case 3:
                  String var12 = this.parser.getName();
                  if("author".equals(var12)) {
                     return;
                  }
               }
            }

            return;
         }
      }

      StringBuilder var5 = (new StringBuilder()).append("Expected <author>: Actual element: <");
      String var6 = this.parser.getName();
      String var7 = var5.append(var6).append(">").toString();
      throw new IllegalStateException(var7);
   }

   private final Feed parseFeed() throws XmlPullParserException, IOException {
      Feed var1 = this.createFeed();
      int var2 = this.parser.next();

      while(var2 != 1) {
         switch(var2) {
         case 2:
            String var3 = this.parser.getName();
            if("totalResults".equals(var3)) {
               int var4 = StringUtils.parseInt(XmlUtils.extractChildText(this.parser), 0);
               var1.setTotalResults(var4);
            } else if("startIndex".equals(var3)) {
               int var5 = StringUtils.parseInt(XmlUtils.extractChildText(this.parser), 0);
               var1.setStartIndex(var5);
            } else if("itemsPerPage".equals(var3)) {
               int var6 = StringUtils.parseInt(XmlUtils.extractChildText(this.parser), 0);
               var1.setItemsPerPage(var6);
            } else if("title".equals(var3)) {
               String var7 = XmlUtils.extractChildText(this.parser);
               var1.setTitle(var7);
            } else if("id".equals(var3)) {
               String var8 = XmlUtils.extractChildText(this.parser);
               var1.setId(var8);
            } else if("updated".equals(var3)) {
               String var9 = XmlUtils.extractChildText(this.parser);
               var1.setLastUpdated(var9);
            } else if("category".equals(var3)) {
               String var10 = this.parser.getAttributeValue((String)null, "term");
               if(!StringUtils.isEmpty(var10)) {
                  var1.setCategory(var10);
               }

               String var11 = this.parser.getAttributeValue((String)null, "scheme");
               if(!StringUtils.isEmpty(var11)) {
                  var1.setCategoryScheme(var11);
               }
            } else {
               if("entry".equals(var3)) {
                  return var1;
               }

               this.handleExtraElementInFeed(var1);
            }
         default:
            var2 = this.parser.next();
         }
      }

      return var1;
   }

   public void close() {
      if(this.is != null) {
         try {
            this.is.close();
         } catch (IOException var2) {
            ;
         }
      }
   }

   protected Entry createEntry() {
      return new Entry();
   }

   protected Feed createFeed() {
      return new Feed();
   }

   protected final XmlPullParser getParser() {
      return this.parser;
   }

   protected void handleEntry(Entry var1) throws XmlPullParserException, IOException, ParseException {
      int var2 = this.parser.getEventType();

      while(var2 != 1) {
         switch(var2) {
         case 2:
            String var3 = this.parser.getName();
            if("entry".equals(var3)) {
               return;
            }

            if("id".equals(var3)) {
               String var4 = XmlUtils.extractChildText(this.parser);
               var1.setId(var4);
            } else if("title".equals(var3)) {
               String var5 = XmlUtils.extractChildText(this.parser);
               var1.setTitle(var5);
            } else if("link".equals(var3)) {
               String var6 = this.parser.getAttributeValue((String)null, "rel");
               String var7 = this.parser.getAttributeValue((String)null, "type");
               String var8 = this.parser.getAttributeValue((String)null, "href");
               if("edit".equals(var6)) {
                  var1.setEditUri(var8);
               } else if("alternate".equals(var6) && "text/html".equals(var7)) {
                  var1.setHtmlUri(var8);
               } else {
                  this.handleExtraLinkInEntry(var6, var7, var8, var1);
               }
            } else if("summary".equals(var3)) {
               String var9 = XmlUtils.extractChildText(this.parser);
               var1.setSummary(var9);
            } else if("content".equals(var3)) {
               String var10 = XmlUtils.extractChildText(this.parser);
               var1.setContent(var10);
            } else if("author".equals(var3)) {
               this.handleAuthor(var1);
            } else if("category".equals(var3)) {
               String var11 = this.parser.getAttributeValue((String)null, "term");
               if(var11 != null && var11.length() > 0) {
                  var1.setCategory(var11);
               }

               String var12 = this.parser.getAttributeValue((String)null, "scheme");
               if(var12 != null && var11.length() > 0) {
                  var1.setCategoryScheme(var12);
               }
            } else if("published".equals(var3)) {
               String var13 = XmlUtils.extractChildText(this.parser);
               var1.setPublicationDate(var13);
            } else if("updated".equals(var3)) {
               String var14 = XmlUtils.extractChildText(this.parser);
               var1.setUpdateDate(var14);
            } else if("deleted".equals(var3)) {
               var1.setDeleted((boolean)1);
            } else {
               this.handleExtraElementInEntry(var1);
            }
         default:
            var2 = this.parser.next();
         }
      }

   }

   protected void handleExtraElementInEntry(Entry var1) throws XmlPullParserException, IOException, ParseException {}

   protected void handleExtraElementInFeed(Feed var1) throws XmlPullParserException, IOException {}

   protected void handleExtraLinkInEntry(String var1, String var2, String var3, Entry var4) throws XmlPullParserException, IOException {}

   public boolean hasMoreData() {
      boolean var1 = true;
      boolean var2 = false;
      if(!this.isInBadState) {
         int var3;
         try {
            var3 = this.parser.getEventType();
         } catch (XmlPullParserException var5) {
            return var2;
         }

         if(var3 == 1) {
            var1 = false;
         }

         var2 = var1;
      }

      return var2;
   }

   public final Feed init() throws ParseException {
      int var1;
      try {
         var1 = this.parser.getEventType();
      } catch (XmlPullParserException var17) {
         throw new ParseException("Could not parse GData feed.", var17);
      }

      if(var1 != 0) {
         throw new ParseException("Attempting to initialize parsing beyond the start of the document.");
      } else {
         try {
            var1 = this.parser.next();
         } catch (XmlPullParserException var15) {
            throw new ParseException("Could not read next event.", var15);
         } catch (IOException var16) {
            throw new ParseException("Could not read next event.", var16);
         }

         int var3 = var1;

         while(var3 != 1) {
            switch(var3) {
            case 2:
               String var6 = this.parser.getName();
               if("feed".equals(var6)) {
                  try {
                     Feed var18 = this.parseFeed();
                     return var18;
                  } catch (XmlPullParserException var11) {
                     throw new ParseException("Unable to parse <feed>.", var11);
                  } catch (IOException var12) {
                     throw new ParseException("Unable to parse <feed>.", var12);
                  }
               }
            default:
               try {
                  var1 = this.parser.next();
               } catch (XmlPullParserException var13) {
                  throw new ParseException("Could not read next event.", var13);
               } catch (IOException var14) {
                  throw new ParseException("Could not read next event.", var14);
               }

               var3 = var1;
            }
         }

         throw new ParseException("No <feed> found in document.");
      }
   }

   public Entry parseStandaloneEntry() throws ParseException, IOException {
      Entry var1 = this.createEntry();

      int var2;
      try {
         var2 = this.parser.getEventType();
      } catch (XmlPullParserException var17) {
         throw new ParseException("Could not parse GData entry.", var17);
      }

      if(var2 != 0) {
         throw new ParseException("Attempting to initialize parsing beyond the start of the document.");
      } else {
         try {
            var2 = this.parser.next();
         } catch (XmlPullParserException var15) {
            throw new ParseException("Could not read next event.", var15);
         } catch (IOException var16) {
            throw new ParseException("Could not read next event.", var16);
         }

         int var4 = var2;

         while(var4 != 1) {
            switch(var4) {
            case 2:
               String var7 = this.parser.getName();
               if("entry".equals(var7)) {
                  try {
                     int var8 = this.parser.next();
                     this.handleEntry(var1);
                     return var1;
                  } catch (XmlPullParserException var12) {
                     throw new ParseException("Unable to parse <entry>.", var12);
                  } catch (IOException var13) {
                     throw new ParseException("Unable to parse <entry>.", var13);
                  }
               }
            default:
               try {
                  var2 = this.parser.next();
               } catch (XmlPullParserException var14) {
                  throw new ParseException("Could not read next event.", var14);
               }

               var4 = var2;
            }
         }

         throw new ParseException("No <entry> found in document.");
      }
   }

   public Entry readNextEntry(Entry var1) throws ParseException, IOException {
      if(!this.hasMoreData()) {
         throw new IllegalStateException("you shouldn\'t call this if hasMoreData() is false");
      } else {
         int var2;
         try {
            var2 = this.parser.getEventType();
         } catch (XmlPullParserException var21) {
            throw new ParseException("Could not parse entry.", var21);
         }

         if(var2 != 2) {
            StringBuilder var4 = (new StringBuilder()).append("Expected event START_TAG: Actual event: ");
            String var5 = XmlPullParser.TYPES[var2];
            String var6 = var4.append(var5).toString();
            throw new ParseException(var6);
         } else {
            String var8 = this.parser.getName();
            if(!"entry".equals(var8)) {
               String var9 = "Expected <entry>: Actual element: <" + var8 + ">";
               throw new ParseException(var9);
            } else {
               if(var1 == null) {
                  var1 = this.createEntry();
               } else {
                  var1.clear();
               }

               try {
                  int var10 = this.parser.next();
                  this.handleEntry(var1);
                  var1.validate();
                  return var1;
               } catch (ParseException var19) {
                  try {
                     if(this.hasMoreData()) {
                        this.skipToNextEntry();
                     }
                  } catch (XmlPullParserException var18) {
                     this.isInBadState = (boolean)1;
                  }

                  String var12 = "Could not parse <entry>, " + var1;
                  throw new ParseException(var12, var19);
               } catch (XmlPullParserException var20) {
                  try {
                     if(this.hasMoreData()) {
                        this.skipToNextEntry();
                     }
                  } catch (XmlPullParserException var17) {
                     this.isInBadState = (boolean)1;
                  }

                  String var15 = "Could not parse <entry>, " + var1;
                  throw new ParseException(var15, var20);
               }
            }
         }
      }
   }

   protected void skipToNextEntry() throws IOException, XmlPullParserException {
      if(!this.hasMoreData()) {
         throw new IllegalStateException("you shouldn\'t call this if hasMoreData() is false");
      } else {
         int var1 = this.parser.getEventType();

         while(var1 != 1) {
            switch(var1) {
            case 2:
               String var2 = this.parser.getName();
               if("entry".equals(var2)) {
                  return;
               }
            default:
               var1 = this.parser.next();
            }
         }

      }
   }
}
