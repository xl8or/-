package com.android.exchange.adapter;

import android.util.Log;
import com.android.exchange.OoODataList;
import com.android.exchange.adapter.AbstractSyncAdapter;
import com.android.exchange.adapter.AbstractSyncParser;
import com.android.exchange.adapter.Parser;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class OoOCommandParser extends AbstractSyncParser {

   public static final String DATE_PARSE_EXCEPTION = "date_parse";
   private Date mEndDate;
   private int mExtKnownMsgEnable;
   private String mExtMsgKnown;
   private String mExtMsgUnKnown;
   private int mExtUnKnownMsgEnable;
   private String mInternalMsg;
   private int mInternalMsgEnable;
   private int mOofState;
   private boolean mResult = 0;
   private Date mStartDate;
   OoODataList oodl;


   public OoOCommandParser(InputStream var1, AbstractSyncAdapter var2) throws IOException {
      super(var1, var2);
   }

   private String checkForBadCharacters(String var1) {
      char[] var2 = var1.toCharArray();
      boolean var3 = false;

      for(int var4 = var2.length - 1; var4 >= 0 && (var2[var4] == 13 || var2[var4] == 10); var4 += -1) {
         var2[var4] = 32;
         var3 = true;
      }

      String var5;
      if(var3) {
         var5 = (new String(var2)).trim();
      } else {
         var5 = var1;
      }

      return var5;
   }

   private Date convertUTCToLocal(String var1) throws ParseException {
      String var2 = var1.replace("T", " ").replace(".000Z", "");
      SimpleDateFormat var3 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
      var3.parse(var2);
      Calendar var5 = var3.getCalendar();
      int var6 = var5.get(15);
      int var7 = var5.get(16);
      long var8 = (long)(var6 + var7);
      int var10 = this.getUTCAMPM(var2);
      var5.set(9, var10);
      Date var11 = new Date();
      long var12 = var5.getTimeInMillis() + var8;
      var11.setTime(var12);
      return var11;
   }

   private int getUTCAMPM(String var1) {
      byte[] var2 = var1.split(" ")[1].getBytes();
      int var3 = Integer.valueOf(var2[0]).intValue() - 48;
      int var4 = Integer.valueOf(var2[1]).intValue() - 48;
      byte var5;
      if(var3 * 10 + var4 < 12) {
         var5 = 0;
      } else {
         var5 = 1;
      }

      return var5;
   }

   private void parseGetTag() throws IOException, ParseException {
      while(this.nextTag(1159) != 3) {
         if(this.tag == 1162) {
            int var1 = this.getValueInt();
            this.mOofState = var1;
            if(this.mOofState == 0) {
               this.mOofState = 0;
            }
         }

         if(this.tag == 1163) {
            if(this.mOofState == 2) {
               String var2 = this.getValue();
               Date var3 = this.convertUTCToLocal(var2);
               this.mStartDate = var3;
            } else {
               String var6 = this.getValue();
            }
         }

         if(this.tag == 1164) {
            if(this.mOofState == 2) {
               String var4 = this.getValue();
               Date var5 = this.convertUTCToLocal(var4);
               this.mEndDate = var5;
            } else {
               String var7 = this.getValue();
            }
         }

         if(this.tag == 1165) {
            this.parseOoOMessageTag();
         }
      }

   }

   private void parseOoOMessageTag() throws IOException {
      boolean var1 = false;
      boolean var2 = false;
      boolean var3 = false;
      boolean var4 = false;

      while(this.nextTag(1165) != 3) {
         if(this.tag == 1166) {
            var1 = true;
         } else if(this.tag == 1167) {
            var2 = true;
         } else if(this.tag == 1168) {
            var3 = true;
         } else if(this.tag == 1169) {
            if(this.getValueInt() == 1) {
               var4 = true;
            }
         } else if(this.tag == 1170) {
            if(var1) {
               String var5 = this.getValue();
               this.mInternalMsg = var5;
               String var6 = this.mInternalMsg;
               String var7 = this.checkForBadCharacters(var6);
               this.mInternalMsg = var7;
               if(var4) {
                  this.mInternalMsgEnable = 1;
               } else {
                  this.mInternalMsgEnable = 0;
               }

               var1 = false;
               var4 = var1;
            } else if(var2) {
               String var8 = this.getValue();
               this.mExtMsgKnown = var8;
               String var9 = this.mExtMsgKnown;
               String var10 = this.checkForBadCharacters(var9);
               this.mExtMsgKnown = var10;
               if(var4) {
                  this.mExtKnownMsgEnable = 1;
               } else {
                  this.mExtKnownMsgEnable = 0;
               }

               var2 = false;
               var4 = var2;
            } else if(var3) {
               String var11 = this.getValue();
               this.mExtMsgUnKnown = var11;
               String var12 = this.mExtMsgUnKnown;
               String var13 = this.checkForBadCharacters(var12);
               this.mExtMsgUnKnown = var13;
               if(var4) {
                  this.mExtUnKnownMsgEnable = 1;
               } else {
                  this.mExtUnKnownMsgEnable = 0;
               }

               var3 = false;
               var4 = var3;
            }
         } else if(this.tag == 1171) {
            String var14 = this.getValue();
         }
      }

   }

   public void commandsParser() throws IOException {}

   public void commit() throws IOException {}

   public OoODataList getParsedData() {
      OoODataList var1;
      if(this.mResult) {
         var1 = this.oodl;
      } else {
         var1 = null;
      }

      return var1;
   }

   public void moveResponseParser() throws IOException {}

   public boolean parse() throws IOException {
      boolean var1 = false;
      boolean var2 = false;
      OoODataList var3 = new OoODataList();
      this.oodl = var3;
      if(this.nextTag(0) != 1157) {
         throw new Parser.EasParserException();
      } else {
         while(this.nextTag(0) != 3) {
            if(this.tag == 1158) {
               int var4 = this.getValueInt();
               if(var4 == 1) {
                  if(!var1 && !var2) {
                     var1 = true;
                  } else if(var1 && !var2) {
                     var2 = true;
                     this.mResult = (boolean)1;
                  }
               } else {
                  this.isProvisioningStatus(var4);
                  this.mResult = (boolean)0;
               }
            }

            if(this.tag == 1174) {
               int var5 = Log.d("OoOCommandParser", "Hurray! DeviceInformation was set successfully");
            } else if(this.tag == 1159) {
               try {
                  this.parseGetTag();
               } catch (ParseException var84) {
                  throw new IOException("date_parse");
               }

               if(this.mOofState == 0) {
                  this.mResult = (boolean)1;
                  if(this.mInternalMsg != null) {
                     OoODataList var7 = this.oodl;
                     int var8 = this.mInternalMsgEnable;
                     String var9 = this.mInternalMsg;
                     var7.AddOoOData(4, 0, var8, var9);
                  } else {
                     OoODataList var20 = this.oodl;
                     int var21 = this.mInternalMsgEnable;
                     var20.AddOoOData(4, 0, var21, (String)null);
                  }

                  if(this.mExtMsgKnown != null) {
                     OoODataList var11 = this.oodl;
                     int var12 = this.mExtKnownMsgEnable;
                     String var13 = this.mExtMsgKnown;
                     var11.AddOoOData(5, 0, var12, var13);
                  } else {
                     OoODataList var23 = this.oodl;
                     int var24 = this.mExtKnownMsgEnable;
                     var23.AddOoOData(5, 0, var24, (String)null);
                  }

                  if(this.mExtMsgUnKnown != null) {
                     OoODataList var15 = this.oodl;
                     int var16 = this.mExtUnKnownMsgEnable;
                     String var17 = this.mExtMsgUnKnown;
                     var15.AddOoOData(6, 0, var16, var17);
                  } else {
                     OoODataList var26 = this.oodl;
                     int var27 = this.mExtUnKnownMsgEnable;
                     var26.AddOoOData(6, 0, var27, (String)null);
                  }
               } else {
                  this.mResult = (boolean)1;
                  if(this.mOofState == 2) {
                     if(this.mInternalMsg != null) {
                        OoODataList var29 = this.oodl;
                        int var30 = this.mInternalMsgEnable;
                        String var31 = this.mInternalMsg;
                        Date var32 = this.mStartDate;
                        Date var33 = this.mEndDate;
                        var29.AddOoOData(4, 2, var30, var31, var32, var33);
                     } else {
                        OoODataList var47 = this.oodl;
                        int var48 = this.mInternalMsgEnable;
                        Date var49 = this.mStartDate;
                        Date var50 = this.mEndDate;
                        var47.AddOoOData(4, 2, var48, (String)null, var49, var50);
                     }

                     if(this.mExtMsgKnown != null) {
                        OoODataList var35 = this.oodl;
                        int var36 = this.mExtKnownMsgEnable;
                        String var37 = this.mExtMsgKnown;
                        Date var38 = this.mStartDate;
                        Date var39 = this.mEndDate;
                        var35.AddOoOData(5, 2, var36, var37, var38, var39);
                     } else {
                        OoODataList var52 = this.oodl;
                        int var53 = this.mExtKnownMsgEnable;
                        Date var54 = this.mStartDate;
                        Date var55 = this.mEndDate;
                        var52.AddOoOData(5, 2, var53, (String)null, var54, var55);
                     }

                     if(this.mExtMsgUnKnown != null) {
                        OoODataList var41 = this.oodl;
                        int var42 = this.mExtUnKnownMsgEnable;
                        String var43 = this.mExtMsgUnKnown;
                        Date var44 = this.mStartDate;
                        Date var45 = this.mEndDate;
                        var41.AddOoOData(6, 2, var42, var43, var44, var45);
                     } else {
                        OoODataList var57 = this.oodl;
                        int var58 = this.mExtUnKnownMsgEnable;
                        Date var59 = this.mStartDate;
                        Date var60 = this.mEndDate;
                        var57.AddOoOData(6, 2, var58, (String)null, var59, var60);
                     }
                  } else {
                     if(this.mInternalMsg != null) {
                        OoODataList var62 = this.oodl;
                        int var63 = this.mInternalMsgEnable;
                        String var64 = this.mInternalMsg;
                        var62.AddOoOData(4, 1, var63, var64);
                     } else {
                        OoODataList var74 = this.oodl;
                        int var75 = this.mInternalMsgEnable;
                        var74.AddOoOData(4, 1, var75, (String)null);
                     }

                     if(this.mExtMsgKnown != null) {
                        OoODataList var66 = this.oodl;
                        int var67 = this.mExtKnownMsgEnable;
                        String var68 = this.mExtMsgKnown;
                        var66.AddOoOData(5, 1, var67, var68);
                     } else {
                        OoODataList var77 = this.oodl;
                        int var78 = this.mExtKnownMsgEnable;
                        var77.AddOoOData(5, 1, var78, (String)null);
                     }

                     if(this.mExtMsgUnKnown != null) {
                        OoODataList var70 = this.oodl;
                        int var71 = this.mExtUnKnownMsgEnable;
                        String var72 = this.mExtMsgUnKnown;
                        var70.AddOoOData(6, 1, var71, var72);
                     } else {
                        OoODataList var80 = this.oodl;
                        int var81 = this.mExtUnKnownMsgEnable;
                        var80.AddOoOData(6, 1, var81, (String)null);
                     }
                  }
               }
            }
         }

         byte var83;
         if(var1 && var2) {
            var83 = 1;
         } else {
            var83 = 0;
         }

         this.mResult = (boolean)var83;
         return (boolean)var83;
      }
   }

   public void responsesParser() throws IOException {}

   public void wipe() {}
}
