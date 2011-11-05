package com.android.exchange.adapter;

import android.util.Log;
import com.android.email.codec.binary.Base64;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class EasBase64EncodedURI {

   private static final String TAG = "EasBase64EncodedURI";
   static final byte acceptMultiPart = 39;
   static final byte attachmentName = 0;
   static final byte collectionId = 1;
   static final byte collectionName = 2;
   static final byte createCollection = 6;
   static final byte deleteCollection = 7;
   static final byte folderCreate = 10;
   static final byte folderDelete = 11;
   static final byte folderSync = 9;
   static final byte folderUpdate = 12;
   static final byte getAttachment = 4;
   static final byte getHierarchy = 5;
   static final byte getItemEstimate = 14;
   static final byte itemId = 3;
   static final byte itemOperations = 19;
   static final byte longId = 4;
   static final byte maxCommand = 23;
   static final byte maxParamTag = 63;
   static final byte meetingResponse = 15;
   static final byte moveCollection = 8;
   static final byte moveItems = 13;
   static final byte occurrence = 6;
   static final byte options = 7;
   static final byte parentId = 5;
   static final byte ping = 18;
   static final byte provision = 20;
   static final byte resolveRecipients = 21;
   static final byte roundTripId = 8;
   static final byte saveInSent = 23;
   static final byte search = 16;
   static final byte sendMail = 1;
   static final byte settings = 17;
   static final byte smartForward = 2;
   static final byte smartReply = 3;
   static final byte sync = 0;
   static final byte validateCert = 22;
   byte[] mCmd;
   String mDeviceId;
   String mDeviceType;
   String mExtra;
   String mExtraCmd;
   String mHostAddress;
   byte[] mLocale;
   String mPolicyKey;
   byte[] mProtocol;
   boolean mSsl;
   boolean mTrustSsl;
   String mUserName;


   public EasBase64EncodedURI(boolean var1, boolean var2, String var3, String var4) {
      byte[] var5 = new byte[]{(byte)121};
      this.mProtocol = var5;
      this.mDeviceId = null;
      this.mDeviceType = "Android";
      byte[] var6 = new byte[]{(byte)23};
      this.mCmd = var6;
      this.mExtraCmd = null;
      this.mExtra = null;
      this.mPolicyKey = null;
      byte[] var7 = new byte[]{(byte)9, (byte)4};
      this.mLocale = var7;
      this.mSsl = var1;
      this.mTrustSsl = var2;
      this.mHostAddress = var3;
      this.mUserName = var4;
      this.mExtraCmd = null;
      this.mExtra = null;
   }

   private String encodeUriString() throws IOException {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();
      byte[] var2 = new byte[5];
      byte[] var3 = new byte[]{(byte)0};
      byte[] var4 = new byte[]{(byte)1};
      StringBuilder var5 = new StringBuilder();
      String var6;
      if(this.mSsl) {
         if(this.mTrustSsl) {
            var6 = "httpts";
         } else {
            var6 = "https";
         }
      } else {
         var6 = "http";
      }

      StringBuilder var7 = var5.append(var6).append("://");
      String var8 = this.mHostAddress;
      StringBuilder var9 = var7.append(var8);
      String var10;
      if(this.mSsl) {
         var10 = ":443";
      } else {
         var10 = "";
      }

      String var11 = var9.append(var10).append("/Microsoft-Server-ActiveSync").toString();
      byte[] var12 = this.mProtocol;
      var1.write(var12);
      byte[] var15 = this.mCmd;
      var1.write(var15);
      byte[] var18 = this.mLocale;
      var1.write(var18);
      if(this.mDeviceId == null) {
         byte var22 = 0;
         var1.write(var22);
      } else {
         byte var57 = (byte)this.mDeviceId.length();
         var4[0] = var57;
         byte var58 = var4[0];
         byte var59 = 16;
         if(var58 > var59) {
            int var60 = var4[0] - 16;
            String var61 = this.mDeviceId;
            String var63 = var61.substring(var60);
            this.mDeviceId = var63;
            var4[0] = 16;
         }

         var1.write(var4);
         String var64 = this.mDeviceId;
         this.writeLiteralString(var1, var64);
      }

      if(this.mPolicyKey == null) {
         byte var24 = 0;
         var1.write(var24);
      } else {
         String var68 = this.mPolicyKey;
         if("0".equals(var68)) {
            int var69 = 1;

            while(true) {
               byte var71 = 5;
               if(var69 >= var71) {
                  var2[0] = 4;
                  var1.write(var2);
                  break;
               }

               var2[var69] = 0;
               ++var69;
            }
         } else {
            String var72 = Long.toHexString(Long.valueOf(this.mPolicyKey).longValue());
            int var73 = var72.length();
            byte var74 = 8;
            if(var73 != var74) {
               byte var76 = 0;
               var1.write(var76);
            } else {
               byte var113 = 0;

               while(true) {
                  byte var78 = 4;
                  if(var113 >= var78) {
                     var2[0] = 4;
                     StringBuilder var90 = (new StringBuilder()).append("policyByte: length[");
                     byte var91 = var2[0];
                     StringBuilder var92 = var90.append(var91).append("] ");
                     byte var93 = var2[1];
                     StringBuilder var94 = var92.append(var93).append(" ");
                     byte var95 = var2[2];
                     StringBuilder var96 = var94.append(var95).append(" ");
                     byte var97 = var2[3];
                     StringBuilder var98 = var96.append(var97).append(" ");
                     byte var99 = var2[4];
                     String var100 = var98.append(var99).toString();
                     int var101 = Log.v("EasBase64EncodedURI", var100);
                     var1.write(var2);
                     break;
                  }

                  int var79 = 4 - var113;
                  int var80 = var113 * 2;
                  int var83 = hex2int(var72.charAt(var80)) << 4;
                  int var84 = var113 * 2 + 1;
                  char var87 = hex2int(var72.charAt(var84));
                  byte var88 = (byte)(var83 | var87);
                  var2[var79] = var88;
                  int var89 = var113 + 1;
               }
            }
         }
      }

      if(this.mDeviceType == null) {
         byte var26 = 0;
         var1.write(var26);
      } else {
         byte var102 = (byte)this.mDeviceType.length();
         var4[0] = var102;
         byte var103 = var4[0];
         byte var104 = 16;
         if(var103 > var104) {
            int var105 = var4[0] - 16;
            String var106 = this.mDeviceType;
            String var108 = var106.substring(var105);
            this.mDeviceType = var108;
            var4[0] = 16;
         }

         var1.write(var4);
         String var109 = this.mDeviceType;
         this.writeLiteralString(var1, var109);
      }

      String var27 = this.mExtraCmd;
      this.writeCmdParams(var1, var3, var27);
      String var32 = this.mExtra;
      this.writeCmdParams(var1, var3, var32);
      if(var3[0] != 0) {
         byte var38 = 7;
         var1.write(var38);
         byte var40 = 1;
         var1.write(var40);
         byte var41 = var3[0];
         var1.write(var41);
      }

      byte[] var44 = var1.toByteArray();
      String var45 = new String;
      byte[] var46 = Base64.encodeBase64(var44);
      var45.<init>(var46);
      StringBuilder var49 = (new StringBuilder()).append("encoded: ");
      String var51 = var49.append(var45).toString();
      int var52 = Log.i("EasBase64EncodedURI", var51);
      StringBuilder var53 = new StringBuilder();
      StringBuilder var55 = var53.append(var11);
      return var55.append(var45).toString();
   }

   private byte getCommandCode(String var1) {
      String var2 = var1.toLowerCase();
      byte var3;
      if(var2.startsWith("sync")) {
         var3 = 0;
      } else if(var2.startsWith("sendmail")) {
         var3 = 1;
      } else if(var2.startsWith("smartforward")) {
         var3 = 2;
      } else if(var2.startsWith("smartreply")) {
         var3 = 3;
      } else if(var2.startsWith("getattachment")) {
         var3 = 4;
      } else if(var2.startsWith("gethierarchy")) {
         var3 = 5;
      } else if(var2.startsWith("createcollection")) {
         var3 = 6;
      } else if(var2.startsWith("deletecollection")) {
         var3 = 7;
      } else if(var2.startsWith("movecollection")) {
         var3 = 8;
      } else if(var2.startsWith("foldersync")) {
         var3 = 9;
      } else if(var2.startsWith("foldercreate")) {
         var3 = 10;
      } else if(var2.startsWith("folderdelete")) {
         var3 = 11;
      } else if(var2.startsWith("folderupdate")) {
         var3 = 12;
      } else if(var2.startsWith("moveitems")) {
         var3 = 13;
      } else if(var2.startsWith("getitemestimate")) {
         var3 = 14;
      } else if(var2.startsWith("meetingresponse")) {
         var3 = 15;
      } else if(var2.startsWith("search")) {
         var3 = 16;
      } else if(var2.startsWith("settings")) {
         var3 = 17;
      } else if(var2.startsWith("ping")) {
         var3 = 18;
      } else if(var2.startsWith("itemoperations")) {
         var3 = 19;
      } else if(var2.startsWith("provision")) {
         var3 = 20;
      } else if(var2.startsWith("resolverecipients")) {
         var3 = 21;
      } else if(var2.startsWith("validatecert")) {
         var3 = 22;
      } else {
         var3 = 23;
      }

      return var3;
   }

   private byte getParameterTag(String var1) {
      String var2 = var1.toLowerCase();
      byte var3;
      if(var2.startsWith("attachmentname")) {
         var3 = 0;
      } else if(var2.startsWith("collectionid")) {
         var3 = 1;
      } else if(var2.startsWith("collectionname")) {
         var3 = 2;
      } else if(var2.startsWith("itemid")) {
         var3 = 3;
      } else if(var2.startsWith("longid")) {
         var3 = 4;
      } else if(var2.startsWith("parentid")) {
         var3 = 5;
      } else if(var2.startsWith("occurrence")) {
         var3 = 6;
      } else if(var2.startsWith("options")) {
         var3 = 7;
      } else if(var2.startsWith("roundtripid")) {
         var3 = 8;
      } else if(var2.startsWith("saveinsent")) {
         var3 = 23;
      } else if(var2.startsWith("acceptmultipart")) {
         var3 = 39;
      } else {
         var3 = 63;
      }

      return var3;
   }

   public static char hex2int(char var0) {
      char var1;
      if(var0 >= 97) {
         var1 = (char)(var0 - 87);
      } else {
         var1 = (char)(var0 - 48);
      }

      return (char)(var1 & 15);
   }

   private void writeCmdParams(OutputStream var1, byte[] var2, String var3) throws IOException {
      if(var3 != null) {
         int var4 = var3.length();
         byte[] var5 = new byte[]{(byte)1};

         for(var3 = var3.substring(1); var4 > 0; var4 = var3.length()) {
            int var6 = var3.indexOf(38);
            String var7;
            if(var6 != -1) {
               var7 = var3.substring(0, var6);
               int var8 = var6 + 1;
               var3 = var3.substring(var8);
            } else {
               var7 = var3.substring(0);
               var3 = null;
            }

            int var9 = var7.indexOf(61);
            if(var9 == -1) {
               ;
            }

            String var10 = var7.substring(0, var9);
            int var11 = var9 + 1;
            String var12 = var7.substring(var11);
            byte[] var13 = new byte[]{(byte)63};
            byte var14 = this.getParameterTag(var10);
            if(var13[0] == 63) {
               ;
            }

            byte var15 = (byte)(var14 & 15);
            var13[0] = var15;
            if(var13[0] == 7) {
               byte var16 = (byte)((var14 & 48) >> 4);
               byte var17 = (byte)(var2[0] | var16);
               var2[0] = var17;
            } else {
               var1.write(var13);
               if(var12 == null) {
                  var1.write(0);
               } else {
                  byte var18 = (byte)var12.length();
                  var5[0] = var18;
                  var1.write(var5);
                  this.writeLiteralString(var1, var12);
               }
            }

            if(var3 == null) {
               return;
            }
         }

      }
   }

   private void writeLiteralString(OutputStream var1, String var2) throws IOException {
      if(var2 != null) {
         byte[] var3 = var2.getBytes("UTF-8");
         var1.write(var3);
      }
   }

   public String getUriString(String var1, String var2, String var3, String var4, String var5, String var6) {
      if(Double.parseDouble(var1) >= 14.0D) {
         this.mProtocol[0] = (byte)'\uff8c';
      } else {
         this.mProtocol[0] = 121;
      }

      int var7 = var2.indexOf(38);
      if(var7 != -1) {
         String var8 = var2.substring(var7);
         this.mExtraCmd = var8;
         var2 = var2.substring(0, var7);
      }

      byte[] var9 = this.mCmd;
      byte var10 = this.getCommandCode(var2);
      var9[0] = var10;
      this.mExtra = var3;
      this.mDeviceId = var4;
      this.mPolicyKey = var5;
      this.mDeviceType = var6;
      int var14 = this.mDeviceId.length();
      if(var14 > 16) {
         int var15 = var14 - 16;
         String var16 = this.mDeviceId.substring(var15);
         this.mDeviceId = var16;
      }

      String var17 = null;

      String var18;
      try {
         var18 = this.encodeUriString();
      } catch (IOException var19) {
         var19.printStackTrace();
         return var17;
      }

      var17 = var18;
      return var17;
   }
}
