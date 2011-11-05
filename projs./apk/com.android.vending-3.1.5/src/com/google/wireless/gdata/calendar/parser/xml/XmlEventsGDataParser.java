package com.google.wireless.gdata.calendar.parser.xml;

import com.google.wireless.gdata.calendar.data.EventEntry;
import com.google.wireless.gdata.calendar.data.EventsFeed;
import com.google.wireless.gdata.calendar.data.Reminder;
import com.google.wireless.gdata.calendar.data.When;
import com.google.wireless.gdata.calendar.data.Who;
import com.google.wireless.gdata.data.Entry;
import com.google.wireless.gdata.data.Feed;
import com.google.wireless.gdata.data.StringUtils;
import com.google.wireless.gdata.data.XmlUtils;
import com.google.wireless.gdata.parser.ParseException;
import com.google.wireless.gdata.parser.xml.XmlGDataParser;
import java.io.IOException;
import java.io.InputStream;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class XmlEventsGDataParser extends XmlGDataParser {

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
         if("eventStatus".equals(var4)) {
            Object var5 = null;
            String var6 = "value";
            String var7 = var2.getAttributeValue((String)var5, var6);
            byte var8 = 0;
            if("http://schemas.google.com/g/2005#event.canceled".equals(var7)) {
               var8 = 2;
            } else if("http://schemas.google.com/g/2005#event.confirmed".equals(var7)) {
               var8 = 1;
            } else if("http://schemas.google.com/g/2005#event.tentative".equals(var7)) {
               var8 = 0;
            }

            var3.setStatus(var8);
         } else if("recurrence".equals(var4)) {
            String var9 = XmlUtils.extractChildText(var2);
            var3.setRecurrence(var9);
         } else if("transparency".equals(var4)) {
            Object var10 = null;
            String var11 = "value";
            String var12 = var2.getAttributeValue((String)var10, var11);
            byte var13 = 0;
            if("http://schemas.google.com/g/2005#event.opaque".equals(var12)) {
               var13 = 0;
            } else if("http://schemas.google.com/g/2005#event.transparent".equals(var12)) {
               var13 = 1;
            }

            var3.setTransparency(var13);
         } else if("visibility".equals(var4)) {
            Object var14 = null;
            String var15 = "value";
            String var16 = var2.getAttributeValue((String)var14, var15);
            byte var17 = 0;
            String var18 = "http://schemas.google.com/g/2005#event.confidential";
            if(var18.equals(var16)) {
               var17 = 1;
            } else {
               String var20 = "http://schemas.google.com/g/2005#event.default";
               if(var20.equals(var16)) {
                  var17 = 0;
               } else {
                  String var22 = "http://schemas.google.com/g/2005#event.private";
                  if(var22.equals(var16)) {
                     var17 = 2;
                  } else {
                     String var24 = "http://schemas.google.com/g/2005#event.public";
                     if(var24.equals(var16)) {
                        var17 = 3;
                     }
                  }
               }
            }

            var3.setVisibility(var17);
         } else if("who".equals(var4)) {
            this.handleWho(var3);
         } else if("sendEventNotifications".equals(var4)) {
            Object var26 = null;
            String var27 = "value";
            String var28 = var2.getAttributeValue((String)var26, var27);
            boolean var29 = "true".equals(var28);
            var3.setSendEventNotifications(var29);
         } else if("guestsCanModify".equals(var4)) {
            Object var30 = null;
            String var31 = "value";
            String var32 = var2.getAttributeValue((String)var30, var31);
            boolean var33 = "true".equals(var32);
            var3.setGuestsCanModify(var33);
         } else if("guestsCanInviteOthers".equals(var4)) {
            Object var34 = null;
            String var35 = "value";
            String var36 = var2.getAttributeValue((String)var34, var35);
            boolean var37 = "true".equals(var36);
            var3.setGuestsCanInviteOthers(var37);
         } else if("guestsCanSeeGuests".equals(var4)) {
            Object var38 = null;
            String var39 = "value";
            String var40 = var2.getAttributeValue((String)var38, var39);
            boolean var41 = "true".equals(var40);
            var3.setGuestsCanSeeGuests(var41);
         } else if("when".equals(var4)) {
            this.handleWhen(var3);
         } else if("reminder".equals(var4)) {
            if(!this.hasSeenReminder) {
               var3.clearReminders();
               byte var42 = 1;
               this.hasSeenReminder = (boolean)var42;
            }

            this.handleReminder(var3);
         } else if("originalEvent".equals(var4)) {
            this.handleOriginalEvent(var3);
         } else if("where".equals(var4)) {
            Object var43 = null;
            String var44 = "valueString";
            String var45 = var2.getAttributeValue((String)var43, var44);
            Object var46 = null;
            String var47 = "rel";
            String var48 = var2.getAttributeValue((String)var46, var47);
            if(StringUtils.isEmpty(var48) || "http://schemas.google.com/g/2005#event".equals(var48)) {
               var3.setWhere(var45);
            }
         } else if("feedLink".equals(var4)) {
            Object var50 = null;
            String var51 = "href";
            String var52 = var2.getAttributeValue((String)var50, var51);
            var3.setCommentsUri(var52);
         } else if("extendedProperty".equals(var4)) {
            Object var53 = null;
            String var54 = "name";
            String var55 = var2.getAttributeValue((String)var53, var54);
            Object var56 = null;
            String var57 = "value";
            String var58 = var2.getAttributeValue((String)var56, var57);
            var3.addExtendedProperty(var55, var58);
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
