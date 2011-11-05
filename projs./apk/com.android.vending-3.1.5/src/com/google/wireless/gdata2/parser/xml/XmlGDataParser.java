package com.google.wireless.gdata2.parser.xml;

import com.google.wireless.gdata2.data.Entry;
import com.google.wireless.gdata2.data.Feed;
import com.google.wireless.gdata2.data.StringUtils;
import com.google.wireless.gdata2.data.XmlUtils;
import com.google.wireless.gdata2.data.batch.BatchInterrupted;
import com.google.wireless.gdata2.data.batch.BatchStatus;
import com.google.wireless.gdata2.data.batch.BatchUtils;
import com.google.wireless.gdata2.parser.GDataParser;
import com.google.wireless.gdata2.parser.ParseException;
import com.google.wireless.gdata2.parser.xml.XmlNametable;
import java.io.IOException;
import java.io.InputStream;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class XmlGDataParser implements GDataParser {

   public static final String NAMESPACE_ATOM_URI = "http://www.w3.org/2005/Atom";
   public static final String NAMESPACE_BATCH = "batch";
   public static final String NAMESPACE_BATCH_URI = "http://schemas.google.com/gdata/batch";
   public static final String NAMESPACE_GD = "gd";
   public static final String NAMESPACE_GD_URI = "http://schemas.google.com/g/2005";
   public static final String NAMESPACE_OPENSEARCH_1_0_URI = "http://a9.com/-/spec/opensearchrss/1.0/";
   public static final String NAMESPACE_OPENSEARCH_1_1_URI = "http://a9.com/-/spec/opensearch/1.1/";
   private String fields;
   private final InputStream is;
   private boolean isInBadState;
   private final XmlPullParser parser;


   public XmlGDataParser(InputStream var1, XmlPullParser var2) throws ParseException {
      this.is = var1;
      this.parser = var2;
      if(!var2.getFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces")) {
         throw new IllegalStateException("A XmlGDataParser needs to be constructed with a namespace aware XmlPullParser");
      } else {
         this.isInBadState = (boolean)0;
         if(this.is != null) {
            try {
               this.parser.setInput(var1, (String)null);
            } catch (XmlPullParserException var4) {
               throw new ParseException("Could not create XmlGDataParser", var4);
            }
         }
      }
   }

   private static String getAttribute(XmlPullParser var0, String var1) {
      return var0.getAttributeValue((String)null, var1);
   }

   private static int getIntAttribute(XmlPullParser var0, String var1) {
      return Integer.parseInt(getAttribute(var0, var1));
   }

   private void handleAuthor(Entry var1) throws XmlPullParserException, IOException {
      int var2 = this.parser.getEventType();
      String var3 = this.parser.getName();
      if(var2 == 2) {
         String var4 = XmlNametable.AUTHOR;
         String var5 = this.parser.getName();
         if(var4.equals(var5)) {
            for(int var9 = this.parser.next(); var9 != 1; var9 = this.parser.next()) {
               switch(var9) {
               case 2:
                  String var10 = this.parser.getName();
                  if(XmlNametable.NAME.equals(var10)) {
                     String var11 = XmlUtils.extractChildText(this.parser);
                     var1.setAuthor(var11);
                  } else if(XmlNametable.EMAIL.equals(var10)) {
                     String var12 = XmlUtils.extractChildText(this.parser);
                     var1.setEmail(var12);
                  }
                  break;
               case 3:
                  String var13 = this.parser.getName();
                  if(XmlNametable.AUTHOR.equals(var13)) {
                     return;
                  }
               }
            }

            return;
         }
      }

      StringBuilder var6 = (new StringBuilder()).append("Expected <author>: Actual element: <");
      String var7 = this.parser.getName();
      String var8 = var6.append(var7).append(">").toString();
      throw new IllegalStateException(var8);
   }

   private void handleBatchInfo(Entry var1) throws IOException, XmlPullParserException {
      String var2 = this.parser.getName();
      if(XmlNametable.STATUS.equals(var2)) {
         BatchStatus var3 = new BatchStatus();
         BatchUtils.setBatchStatus(var1, var3);
         XmlPullParser var4 = this.parser;
         String var5 = XmlNametable.CODE;
         int var6 = getIntAttribute(var4, var5);
         var3.setStatusCode(var6);
         XmlPullParser var7 = this.parser;
         String var8 = XmlNametable.REASON;
         String var9 = getAttribute(var7, var8);
         var3.setReason(var9);
         XmlPullParser var10 = this.parser;
         String var11 = XmlNametable.CONTENT_TYPE;
         String var12 = getAttribute(var10, var11);
         var3.setContentType(var12);
         this.skipSubTree();
      } else if(XmlNametable.ID.equals(var2)) {
         String var13 = XmlUtils.extractChildText(this.parser);
         BatchUtils.setBatchId(var1, var13);
      } else if(XmlNametable.OPERATION.equals(var2)) {
         XmlPullParser var14 = this.parser;
         String var15 = XmlNametable.TYPE;
         String var16 = getAttribute(var14, var15);
         BatchUtils.setBatchOperation(var1, var16);
      } else if("interrupted".equals(var2)) {
         BatchInterrupted var17 = new BatchInterrupted();
         BatchUtils.setBatchInterrupted(var1, var17);
         XmlPullParser var18 = this.parser;
         String var19 = XmlNametable.REASON;
         String var20 = getAttribute(var18, var19);
         var17.setReason(var20);
         XmlPullParser var21 = this.parser;
         String var22 = XmlNametable.ERROR;
         int var23 = getIntAttribute(var21, var22);
         var17.setErrorCount(var23);
         XmlPullParser var24 = this.parser;
         String var25 = XmlNametable.SUCCESS;
         int var26 = getIntAttribute(var24, var25);
         var17.setSuccessCount(var26);
         XmlPullParser var27 = this.parser;
         String var28 = XmlNametable.PARSED;
         int var29 = getIntAttribute(var27, var28);
         var17.setTotalCount(var29);
         this.skipSubTree();
      } else {
         String var30 = "Unexpected batch element " + var2;
         throw new XmlPullParserException(var30);
      }
   }

   private final Feed parseFeed() throws XmlPullParserException, IOException {
      Feed var1 = this.createFeed();
      XmlPullParser var2 = this.parser;
      String var3 = XmlNametable.ETAG;
      String var4 = var2.getAttributeValue("http://schemas.google.com/g/2005", var3);
      var1.setETag(var4);
      int var5 = this.parser.next();

      while(var5 != 1) {
         switch(var5) {
         case 2:
            String var6 = this.parser.getName();
            String var7 = this.parser.getNamespace();
            if(!"http://a9.com/-/spec/opensearchrss/1.0/".equals(var7) && !"http://a9.com/-/spec/opensearch/1.1/".equals(var7)) {
               if("http://www.w3.org/2005/Atom".equals(var7)) {
                  if(XmlNametable.TITLE.equals(var6)) {
                     String var11 = XmlUtils.extractChildText(this.parser);
                     var1.setTitle(var11);
                  } else if(XmlNametable.ID.equals(var6)) {
                     String var12 = XmlUtils.extractChildText(this.parser);
                     var1.setId(var12);
                  } else if(XmlNametable.UPDATED.equals(var6)) {
                     String var13 = XmlUtils.extractChildText(this.parser);
                     var1.setLastUpdated(var13);
                  } else if(XmlNametable.CATEGORY.equals(var6)) {
                     XmlPullParser var14 = this.parser;
                     String var15 = XmlNametable.TERM;
                     String var16 = var14.getAttributeValue((String)null, var15);
                     if(!StringUtils.isEmpty(var16)) {
                        var1.setCategory(var16);
                     }

                     XmlPullParser var17 = this.parser;
                     String var18 = XmlNametable.SCHEME;
                     String var19 = var17.getAttributeValue((String)null, var18);
                     if(!StringUtils.isEmpty(var19)) {
                        var1.setCategoryScheme(var19);
                     }
                  } else if(XmlNametable.ENTRY.equals(var6)) {
                     return var1;
                  }
               } else {
                  this.handleExtraElementInFeed(var1);
               }
            } else if(XmlNametable.TOTAL_RESULTS.equals(var6)) {
               int var8 = StringUtils.parseInt(XmlUtils.extractChildText(this.parser), 0);
               var1.setTotalResults(var8);
            } else if(XmlNametable.START_INDEX.equals(var6)) {
               int var9 = StringUtils.parseInt(XmlUtils.extractChildText(this.parser), 0);
               var1.setStartIndex(var9);
            } else if(XmlNametable.ITEMS_PER_PAGE.equals(var6)) {
               int var10 = StringUtils.parseInt(XmlUtils.extractChildText(this.parser), 0);
               var1.setItemsPerPage(var10);
            }
         default:
            var5 = this.parser.next();
         }
      }

      return var1;
   }

   private final Feed parsePartialFeed() throws XmlPullParserException, IOException {
      Feed var1 = null;
      XmlPullParser var2 = this.parser;
      String var3 = XmlNametable.FIELDS;
      String var4 = var2.getAttributeValue(var1, var3);
      this.fields = var4;
      int var5 = this.parser.next();

      while(var5 != 1) {
         switch(var5) {
         case 2:
            String var6 = this.parser.getName();
            String var7 = this.parser.getNamespace();
            if("http://www.w3.org/2005/Atom".equals(var7) && XmlNametable.FEED.equals(var6)) {
               var1 = this.parseFeed();
               return var1;
            }
         default:
            var5 = this.parser.next();
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

   protected boolean handleDefaultEntryElements(Entry var1) throws XmlPullParserException, IOException {
      return false;
   }

   protected void handleEntry(Entry var1) throws XmlPullParserException, IOException, ParseException {
      String var2 = XmlNametable.ENTRY;
      String var3 = this.parser.getName();
      if(!var2.equals(var3)) {
         StringBuilder var4 = (new StringBuilder()).append("Expected <entry>: Actual element: <");
         String var5 = this.parser.getName();
         String var6 = var4.append(var5).append(">").toString();
         throw new IllegalStateException(var6);
      } else {
         XmlPullParser var7 = this.parser;
         String var8 = XmlNametable.ETAG;
         String var9 = var7.getAttributeValue("http://schemas.google.com/g/2005", var8);
         var1.setETag(var9);
         String var10 = this.fields;
         var1.setFields(var10);
         int var11 = this.parser.next();
         int var12 = this.parser.getEventType();

         while(var12 != 1) {
            switch(var12) {
            case 2:
               String var13 = this.parser.getName();
               if(XmlNametable.ENTRY.equals(var13)) {
                  return;
               }

               if(!this.handleDefaultEntryElements(var1)) {
                  String var14 = this.parser.getNamespace();
                  if("http://schemas.google.com/gdata/batch".equals(var14)) {
                     this.handleBatchInfo(var1);
                  } else if(XmlNametable.ID.equals(var13)) {
                     String var15 = XmlUtils.extractChildText(this.parser);
                     var1.setId(var15);
                  } else if(XmlNametable.TITLE.equals(var13)) {
                     String var16 = XmlUtils.extractChildText(this.parser);
                     var1.setTitle(var16);
                  } else if(XmlNametable.LINK.equals(var13)) {
                     XmlPullParser var17 = this.parser;
                     String var18 = XmlNametable.REL;
                     String var19 = var17.getAttributeValue((String)null, var18);
                     XmlPullParser var20 = this.parser;
                     String var21 = XmlNametable.TYPE;
                     String var22 = var20.getAttributeValue((String)null, var21);
                     XmlPullParser var23 = this.parser;
                     String var24 = XmlNametable.HREF;
                     String var25 = var23.getAttributeValue((String)null, var24);
                     if(XmlNametable.EDIT_REL.equals(var19)) {
                        var1.setEditUri(var25);
                     } else if(XmlNametable.ALTERNATE_REL.equals(var19) && XmlNametable.TEXTHTML.equals(var22)) {
                        var1.setHtmlUri(var25);
                     } else {
                        this.handleExtraLinkInEntry(var19, var22, var25, var1);
                     }
                  } else if(XmlNametable.SUMMARY.equals(var13)) {
                     String var26 = XmlUtils.extractChildText(this.parser);
                     var1.setSummary(var26);
                  } else if(XmlNametable.CONTENT.equals(var13)) {
                     XmlPullParser var27 = this.parser;
                     String var28 = XmlNametable.TYPE;
                     String var29 = var27.getAttributeValue((String)null, var28);
                     var1.setContentType(var29);
                     XmlPullParser var30 = this.parser;
                     String var31 = XmlNametable.SRC;
                     String var32 = var30.getAttributeValue((String)null, var31);
                     var1.setContentSource(var32);
                     String var33 = XmlUtils.extractChildText(this.parser);
                     var1.setContent(var33);
                  } else if(XmlNametable.AUTHOR.equals(var13)) {
                     this.handleAuthor(var1);
                  } else if(XmlNametable.CATEGORY.equals(var13)) {
                     XmlPullParser var34 = this.parser;
                     String var35 = XmlNametable.TERM;
                     String var36 = var34.getAttributeValue((String)null, var35);
                     if(var36 != null && var36.length() > 0) {
                        var1.setCategory(var36);
                     }

                     XmlPullParser var37 = this.parser;
                     String var38 = XmlNametable.SCHEME;
                     String var39 = var37.getAttributeValue((String)null, var38);
                     if(var39 != null && var36.length() > 0) {
                        var1.setCategoryScheme(var39);
                     }
                  } else if(XmlNametable.PUBLISHED.equals(var13)) {
                     String var40 = XmlUtils.extractChildText(this.parser);
                     var1.setPublicationDate(var40);
                  } else if(XmlNametable.UPDATED.equals(var13)) {
                     String var41 = XmlUtils.extractChildText(this.parser);
                     var1.setUpdateDate(var41);
                  } else if(XmlNametable.DELETED.equals(var13)) {
                     var1.setDeleted((boolean)1);
                  } else {
                     this.handleExtraElementInEntry(var1);
                  }
               }
            default:
               var12 = this.parser.next();
            }
         }

         var1.validate();
      }
   }

   protected void handleExtraElementInEntry(Entry var1) throws XmlPullParserException, IOException, ParseException {}

   protected void handleExtraElementInFeed(Feed var1) throws XmlPullParserException, IOException {}

   protected void handleExtraLinkInEntry(String var1, String var2, String var3, Entry var4) throws XmlPullParserException, IOException {}

   protected void handlePartialEntry(Entry var1) throws XmlPullParserException, IOException, ParseException {
      String var2 = XmlNametable.PARTIAL;
      String var3 = this.parser.getName();
      if(!var2.equals(var3)) {
         StringBuilder var4 = (new StringBuilder()).append("Expected <partial>: Actual element: <");
         String var5 = this.parser.getName();
         String var6 = var4.append(var5).append(">").toString();
         throw new IllegalStateException(var6);
      } else {
         XmlPullParser var7 = this.parser;
         String var8 = XmlNametable.FIELDS;
         String var9 = var7.getAttributeValue((String)null, var8);
         this.fields = var9;
         int var10 = this.parser.next();
         int var11 = this.parser.getEventType();

         while(var11 != 1) {
            switch(var11) {
            case 2:
               String var12 = this.parser.getName();
               if(XmlNametable.ENTRY.equals(var12)) {
                  this.handleEntry(var1);
                  return;
               }
            default:
               var11 = this.parser.next();
            }
         }

      }
   }

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

   public final Feed parseFeedEnvelope() throws ParseException {
      this.fields = null;

      int var1;
      try {
         var1 = this.parser.getEventType();
      } catch (XmlPullParserException var23) {
         throw new ParseException("Could not parse GData feed.", var23);
      }

      if(var1 != 0) {
         throw new ParseException("Attempting to initialize parsing beyond the start of the document.");
      } else {
         try {
            var1 = this.parser.next();
         } catch (XmlPullParserException var21) {
            throw new ParseException("Could not read next event.", var21);
         } catch (IOException var22) {
            throw new ParseException("Could not read next event.", var22);
         }

         int var3 = var1;

         while(var3 != 1) {
            switch(var3) {
            case 2:
               String var6 = this.parser.getName();
               Feed var7;
               Feed var8;
               if(XmlNametable.PARTIAL.equals(var6)) {
                  try {
                     var7 = this.parsePartialFeed();
                  } catch (XmlPullParserException var17) {
                     throw new ParseException("Unable to parse <partial> feed start", var17);
                  } catch (IOException var18) {
                     throw new ParseException("Unable to parse <partial> feed start", var18);
                  }

                  var8 = var7;
                  return var8;
               }

               if(XmlNametable.FEED.equals(var6)) {
                  try {
                     var7 = this.parseFeed();
                  } catch (XmlPullParserException var15) {
                     throw new ParseException("Unable to parse <feed>.", var15);
                  } catch (IOException var16) {
                     throw new ParseException("Unable to parse <feed>.", var16);
                  }

                  var8 = var7;
                  return var8;
               }
            default:
               try {
                  var1 = this.parser.next();
               } catch (XmlPullParserException var19) {
                  throw new ParseException("Could not read next event.", var19);
               } catch (IOException var20) {
                  throw new ParseException("Could not read next event.", var20);
               }

               var3 = var1;
            }
         }

         throw new ParseException("No <feed> found in document.");
      }
   }

   public Entry parseStandaloneEntry() throws ParseException, IOException {
      this.fields = null;
      Entry var1 = this.createEntry();

      int var2;
      try {
         var2 = this.parser.getEventType();
      } catch (XmlPullParserException var20) {
         throw new ParseException("Could not parse GData entry.", var20);
      }

      if(var2 != 0) {
         throw new ParseException("Attempting to initialize parsing beyond the start of the document.");
      } else {
         try {
            var2 = this.parser.next();
         } catch (XmlPullParserException var18) {
            throw new ParseException("Could not read next event.", var18);
         } catch (IOException var19) {
            throw new ParseException("Could not read next event.", var19);
         }

         int var4 = var2;

         while(var4 != 1) {
            switch(var4) {
            case 2:
               String var7 = this.parser.getName();
               if(XmlNametable.PARTIAL.equals(var7)) {
                  try {
                     this.handlePartialEntry(var1);
                     return var1;
                  } catch (XmlPullParserException var15) {
                     throw new ParseException("Unable to parse <partial> entry.", var15);
                  } catch (IOException var16) {
                     throw new ParseException("Unable to parse <partial> entry.", var16);
                  }
               }

               if(XmlNametable.ENTRY.equals(var7)) {
                  try {
                     this.handleEntry(var1);
                     return var1;
                  } catch (XmlPullParserException var13) {
                     throw new ParseException("Unable to parse <entry>.", var13);
                  } catch (IOException var14) {
                     throw new ParseException("Unable to parse <entry>.", var14);
                  }
               }
            default:
               try {
                  var2 = this.parser.next();
               } catch (XmlPullParserException var17) {
                  throw new ParseException("Could not read next event.", var17);
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
         } catch (XmlPullParserException var20) {
            throw new ParseException("Could not parse entry.", var20);
         }

         if(var2 != 2) {
            StringBuilder var4 = (new StringBuilder()).append("Expected event START_TAG: Actual event: ");
            String var5 = XmlPullParser.TYPES[var2];
            String var6 = var4.append(var5).toString();
            throw new ParseException(var6);
         } else {
            String var8 = this.parser.getName();
            if(!XmlNametable.ENTRY.equals(var8) && !XmlNametable.PARTIAL.equals(var8)) {
               String var9 = "Expected <entry> or <partial>: Actual element: <" + var8 + ">";
               throw new ParseException(var9);
            } else {
               if(var1 == null) {
                  var1 = this.createEntry();
               } else {
                  var1.clear();
               }

               try {
                  if(XmlNametable.ENTRY.equals(var8)) {
                     this.handleEntry(var1);
                  } else {
                     this.handlePartialEntry(var1);
                  }

                  return var1;
               } catch (ParseException var18) {
                  try {
                     if(this.hasMoreData()) {
                        this.skipToNextEntry();
                     }
                  } catch (XmlPullParserException var16) {
                     this.isInBadState = (boolean)1;
                  }

                  String var11 = "Could not parse <entry>, " + var1;
                  throw new ParseException(var11, var18);
               } catch (XmlPullParserException var19) {
                  try {
                     if(this.hasMoreData()) {
                        this.skipToNextEntry();
                     }
                  } catch (XmlPullParserException var17) {
                     this.isInBadState = (boolean)1;
                  }

                  String var14 = "Could not parse <entry>, " + var1;
                  throw new ParseException(var14, var19);
               }
            }
         }
      }
   }

   protected void skipSubTree() throws XmlPullParserException, IOException {
      int var1 = 1;

      while(var1 > 0) {
         switch(this.parser.next()) {
         case 2:
            ++var1;
            break;
         case 3:
            var1 += -1;
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
               String var2 = XmlNametable.ENTRY;
               String var3 = this.parser.getName();
               if(var2.equals(var3)) {
                  return;
               }
            default:
               var1 = this.parser.next();
            }
         }

      }
   }
}
