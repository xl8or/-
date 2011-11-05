package com.google.wireless.gdata2.calendar.parser.xml;

import com.google.wireless.gdata2.calendar.data.EventEntry;
import com.google.wireless.gdata2.calendar.data.EventsFeed;
import com.google.wireless.gdata2.calendar.data.Reminder;
import com.google.wireless.gdata2.calendar.data.When;
import com.google.wireless.gdata2.calendar.data.Who;
import com.google.wireless.gdata2.data.Entry;
import com.google.wireless.gdata2.data.Feed;
import com.google.wireless.gdata2.data.StringUtils;
import com.google.wireless.gdata2.data.XmlUtils;
import com.google.wireless.gdata2.parser.ParseException;
import com.google.wireless.gdata2.parser.xml.XmlGDataParser;
import java.io.IOException;
import java.io.InputStream;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class XmlEventsGDataParser extends XmlGDataParser {

   private static String GCAL_NAMESPACE = "http://schemas.google.com/gCal/2005";
   private static String GD_NAMESPACE = "http://schemas.google.com/g/2005";
   private boolean hasSeenReminder = 0;


   public XmlEventsGDataParser(InputStream var1, XmlPullParser var2) throws ParseException {
      super(var1, var2);
   }

   private void handleOriginalEvent(EventEntry var1) throws XmlPullParserException, IOException {
      XmlPullParser var2 = this.getParser();
      int var3 = var2.getEventType();
      String var4 = var2.getName();
      if(var3 == 2) {
         String var5 = var2.getName();
         if("originalEvent".equals(var5)) {
            String var7 = var2.getAttributeValue((String)null, "href");
            var1.setOriginalEventId(var7);

            for(int var8 = var2.next(); var8 != 1; var8 = var2.next()) {
               switch(var8) {
               case 2:
                  String var9 = var2.getName();
                  if("when".equals(var9)) {
                     String var10 = var2.getAttributeValue((String)null, "startTime");
                     var1.setOriginalEventStartTime(var10);
                  }
                  break;
               case 3:
                  String var11 = var2.getName();
                  if("originalEvent".equals(var11)) {
                     return;
                  }
               }
            }

            return;
         }
      }

      String var6 = "Expected <originalEvent>: Actual element: <" + var4 + ">";
      throw new IllegalStateException(var6);
   }

   private void handleReminder(EventEntry var1) {
      XmlPullParser var2 = this.getParser();
      Reminder var3 = new Reminder();
      var1.addReminder(var3);
      String var4 = var2.getAttributeValue((String)null, "method");
      String var5 = var2.getAttributeValue((String)null, "minutes");
      String var6 = var2.getAttributeValue((String)null, "hours");
      String var7 = var2.getAttributeValue((String)null, "days");
      if(!StringUtils.isEmpty(var4)) {
         if("alert".equals(var4)) {
            var3.setMethod((byte)3);
         } else if("email".equals(var4)) {
            var3.setMethod((byte)1);
         } else if("sms".equals(var4)) {
            var3.setMethod((byte)2);
         }
      }

      int var8 = -1;
      if(!StringUtils.isEmpty(var5)) {
         var8 = StringUtils.parseInt(var5, var8);
      } else if(!StringUtils.isEmpty(var6)) {
         var8 = StringUtils.parseInt(var6, var8) * 60;
      } else if(!StringUtils.isEmpty(var7)) {
         var8 = StringUtils.parseInt(var7, var8) * 1440;
      }

      if(var8 < 0) {
         var8 = -1;
      }

      var3.setMinutes(var8);
   }

   private void handleWhen(EventEntry var1) throws XmlPullParserException, IOException {
      boolean var2 = false;
      XmlPullParser var3 = this.getParser();
      int var4 = var3.getEventType();
      String var5 = var3.getName();
      if(var4 == 2) {
         String var6 = var3.getName();
         if("when".equals(var6)) {
            String var8 = var3.getAttributeValue((String)null, "startTime");
            String var9 = var3.getAttributeValue((String)null, "endTime");
            When var10 = new When(var8, var9);
            var1.addWhen(var10);
            boolean var11;
            if(var1.getWhens().size() == 1) {
               var11 = true;
            } else {
               var11 = false;
            }

            if(var11 && !this.hasSeenReminder) {
               var2 = true;
            }

            for(int var12 = var3.next(); var12 != 1; var12 = var3.next()) {
               switch(var12) {
               case 2:
                  String var13 = var3.getName();
                  if("reminder".equals(var13) && var2) {
                     this.handleReminder(var1);
                  }
                  break;
               case 3:
                  String var14 = var3.getName();
                  if("when".equals(var14)) {
                     return;
                  }
               }
            }

            return;
         }
      }

      String var7 = "Expected <when>: Actual element: <" + var5 + ">";
      throw new IllegalStateException(var7);
   }

   private void handleWho(EventEntry var1) throws XmlPullParserException, IOException, ParseException {
      XmlPullParser var2 = this.getParser();
      int var3 = var2.getEventType();
      String var4 = var2.getName();
      if(var3 == 2) {
         String var5 = var2.getName();
         if("who".equals(var5)) {
            String var7 = var2.getAttributeValue((String)null, "email");
            String var8 = var2.getAttributeValue((String)null, "rel");
            String var9 = var2.getAttributeValue((String)null, "valueString");
            Who var10 = new Who();
            var10.setEmail(var7);
            var10.setValue(var9);
            byte var11;
            if("http://schemas.google.com/g/2005#event.attendee".equals(var8)) {
               var11 = 1;
            } else if("http://schemas.google.com/g/2005#event.organizer".equals(var8)) {
               var11 = 2;
            } else if("http://schemas.google.com/g/2005#event.performer".equals(var8)) {
               var11 = 3;
            } else if("http://schemas.google.com/g/2005#event.speaker".equals(var8)) {
               var11 = 4;
            } else {
               if(!StringUtils.isEmpty(var8)) {
                  String var12 = "Unexpected rel: " + var8;
                  throw new ParseException(var12);
               }

               var11 = 1;
            }

            var10.setRelationship(var11);
            var1.addAttendee(var10);

            for(; var3 != 1; var3 = var2.next()) {
               switch(var3) {
               case 2:
                  var4 = var2.getName();
                  if("attendeeStatus".equals(var4)) {
                     String var13 = var2.getAttributeValue((String)null, "value");
                     byte var14;
                     if("http://schemas.google.com/g/2005#event.accepted".equals(var13)) {
                        var14 = 1;
                     } else if("http://schemas.google.com/g/2005#event.declined".equals(var13)) {
                        var14 = 2;
                     } else if("http://schemas.google.com/g/2005#event.invited".equals(var13)) {
                        var14 = 3;
                     } else if("http://schemas.google.com/g/2005#event.tentative".equals(var13)) {
                        var14 = 4;
                     } else {
                        if(!StringUtils.isEmpty(var13)) {
                           String var15 = "Unexpected status: " + var13;
                           throw new ParseException(var15);
                        }

                        var14 = 4;
                     }

                     var10.setStatus(var14);
                  } else if("attendeeType".equals(var4)) {
                     String var16 = XmlUtils.extractChildText(var2);
                     byte var17;
                     if("http://schemas.google.com/g/2005#event.optional".equals(var16)) {
                        var17 = 1;
                     } else if("http://schemas.google.com/g/2005#event.required".equals(var16)) {
                        var17 = 2;
                     } else {
                        if(!StringUtils.isEmpty(var16)) {
                           String var18 = "Unexpected type: " + var16;
                           throw new ParseException(var18);
                        }

                        var17 = 2;
                     }

                     var10.setType(var17);
                  }
                  break;
               case 3:
                  String var19 = var2.getName();
                  if("who".equals(var19)) {
                     return;
                  }
               }
            }

            return;
         }
      }

      String var6 = "Expected <who>: Actual element: <" + var4 + ">";
      throw new IllegalStateException(var6);
   }

   protected Entry createEntry() {
      return new EventEntry();
   }

   protected Feed createFeed() {
      return new EventsFeed();
   }

   protected void handleEntry(Entry var1) throws XmlPullParserException, IOException, ParseException {
      this.hasSeenReminder = (boolean)0;
      super.handleEntry(var1);
   }

   protected void handleExtraElementInEntry(Entry var1) throws XmlPullParserException, IOException, ParseException {
      XmlPullParser var2 = this.getParser();
      if(!(var1 instanceof EventEntry)) {
         throw new IllegalArgumentException("Expected EventEntry!");
      } else {
         EventEntry var3 = (EventEntry)var1;
         String var4 = var2.getName();
         String var5 = GD_NAMESPACE;
         if(XmlUtils.matchNameSpaceUri(var2, var5)) {
            if("eventStatus".equals(var4)) {
               Object var6 = null;
               String var7 = "value";
               String var8 = var2.getAttributeValue((String)var6, var7);
               byte var9 = 0;
               if("http://schemas.google.com/g/2005#event.canceled".equals(var8)) {
                  var9 = 2;
               } else if("http://schemas.google.com/g/2005#event.confirmed".equals(var8)) {
                  var9 = 1;
               } else if("http://schemas.google.com/g/2005#event.tentative".equals(var8)) {
                  var9 = 0;
               }

               var3.setStatus(var9);
            } else if("recurrence".equals(var4)) {
               String var10 = XmlUtils.extractChildText(var2);
               var3.setRecurrence(var10);
            } else if("transparency".equals(var4)) {
               Object var11 = null;
               String var12 = "value";
               String var13 = var2.getAttributeValue((String)var11, var12);
               byte var14 = 0;
               if("http://schemas.google.com/g/2005#event.opaque".equals(var13)) {
                  var14 = 0;
               } else if("http://schemas.google.com/g/2005#event.transparent".equals(var13)) {
                  var14 = 1;
               }

               var3.setTransparency(var14);
            } else if("visibility".equals(var4)) {
               Object var15 = null;
               String var16 = "value";
               String var17 = var2.getAttributeValue((String)var15, var16);
               byte var18 = 0;
               String var19 = "http://schemas.google.com/g/2005#event.confidential";
               if(var19.equals(var17)) {
                  var18 = 1;
               } else {
                  String var21 = "http://schemas.google.com/g/2005#event.default";
                  if(var21.equals(var17)) {
                     var18 = 0;
                  } else {
                     String var23 = "http://schemas.google.com/g/2005#event.private";
                     if(var23.equals(var17)) {
                        var18 = 2;
                     } else {
                        String var25 = "http://schemas.google.com/g/2005#event.public";
                        if(var25.equals(var17)) {
                           var18 = 3;
                        }
                     }
                  }
               }

               var3.setVisibility(var18);
            } else if("who".equals(var4)) {
               this.handleWho(var3);
            } else if("when".equals(var4)) {
               this.handleWhen(var3);
            } else if("reminder".equals(var4)) {
               if(!this.hasSeenReminder) {
                  var3.clearReminders();
                  byte var27 = 1;
                  this.hasSeenReminder = (boolean)var27;
               }

               this.handleReminder(var3);
            } else if("originalEvent".equals(var4)) {
               this.handleOriginalEvent(var3);
            } else if("where".equals(var4)) {
               Object var28 = null;
               String var29 = "valueString";
               String var30 = var2.getAttributeValue((String)var28, var29);
               Object var31 = null;
               String var32 = "rel";
               String var33 = var2.getAttributeValue((String)var31, var32);
               if(StringUtils.isEmpty(var33) || "http://schemas.google.com/g/2005#event".equals(var33)) {
                  var3.setWhere(var30);
               }
            } else if("feedLink".equals(var4)) {
               Object var35 = null;
               String var36 = "href";
               String var37 = var2.getAttributeValue((String)var35, var36);
               var3.setCommentsUri(var37);
            } else if("extendedProperty".equals(var4)) {
               Object var38 = null;
               String var39 = "name";
               String var40 = var2.getAttributeValue((String)var38, var39);
               Object var41 = null;
               String var42 = "value";
               String var43 = var2.getAttributeValue((String)var41, var42);
               var3.addExtendedProperty(var40, var43);
            }
         } else {
            String var44 = GCAL_NAMESPACE;
            if(XmlUtils.matchNameSpaceUri(var2, var44)) {
               if("sendEventNotifications".equals(var4)) {
                  Object var45 = null;
                  String var46 = "value";
                  String var47 = var2.getAttributeValue((String)var45, var46);
                  boolean var48 = "true".equals(var47);
                  var3.setSendEventNotifications(var48);
               } else if("guestsCanModify".equals(var4)) {
                  Object var49 = null;
                  String var50 = "value";
                  String var51 = var2.getAttributeValue((String)var49, var50);
                  boolean var52 = "true".equals(var51);
                  var3.setGuestsCanModify(var52);
               } else if("guestsCanInviteOthers".equals(var4)) {
                  Object var53 = null;
                  String var54 = "value";
                  String var55 = var2.getAttributeValue((String)var53, var54);
                  boolean var56 = "true".equals(var55);
                  var3.setGuestsCanInviteOthers(var56);
               } else if("guestsCanSeeGuests".equals(var4)) {
                  Object var57 = null;
                  String var58 = "value";
                  String var59 = var2.getAttributeValue((String)var57, var58);
                  boolean var60 = "true".equals(var59);
                  var3.setGuestsCanSeeGuests(var60);
               } else if("uid".equals(var4)) {
                  Object var61 = null;
                  String var62 = "value";
                  String var63 = var2.getAttributeValue((String)var61, var62);
                  var3.setUid(var63);
               }
            }
         }
      }
   }

   protected void handleExtraElementInFeed(Feed var1) throws XmlPullParserException, IOException {
      XmlPullParser var2 = this.getParser();
      if(!(var1 instanceof EventsFeed)) {
         throw new IllegalArgumentException("Expected EventsFeed!");
      } else {
         EventsFeed var3 = (EventsFeed)var1;
         String var4 = var2.getName();
         if("timezone".equals(var4)) {
            String var5 = var2.getAttributeValue((String)null, "value");
            if(!StringUtils.isEmpty(var5)) {
               var3.setTimezone(var5);
            }
         }
      }
   }
}
