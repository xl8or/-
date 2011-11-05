package org.apache.james.mime4j;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BodyDescriptor {

   private static Log log = LogFactory.getLog(BodyDescriptor.class);
   private String boundary;
   private String charset;
   private boolean contentTransferEncSet;
   private boolean contentTypeSet;
   private String mimeType;
   private Map parameters;
   private String transferEncoding;


   public BodyDescriptor() {
      this((BodyDescriptor)null);
   }

   public BodyDescriptor(BodyDescriptor var1) {
      this.mimeType = "text/plain";
      this.boundary = null;
      this.charset = "us-ascii";
      this.transferEncoding = "7bit";
      HashMap var2 = new HashMap();
      this.parameters = var2;
      this.contentTypeSet = (boolean)0;
      this.contentTransferEncSet = (boolean)0;
      if(var1 != null && var1.isMimeType("multipart/digest")) {
         this.mimeType = "message/rfc822";
      } else {
         this.mimeType = "text/plain";
      }
   }

   private Map getHeaderParams(String var1) {
      HashMap var2 = new HashMap();
      String var4 = ";";
      int var5 = var1.indexOf(var4);
      char var6 = '\uffff';
      String var7;
      String var8;
      if(var5 == var6) {
         var7 = var1;
         var8 = null;
      } else {
         String var24 = ";";
         int var25 = var1.indexOf(var24);
         byte var27 = 0;
         var7 = var1.substring(var27, var25);
         int var29 = var7.length() + 1;
         var8 = var1.substring(var29);
      }

      String var10 = "";
      var2.put(var10, var7);
      if(var8 != null) {
         char[] var13 = var8.toCharArray();
         StringBuffer var14 = new StringBuffer();
         StringBuffer var15 = new StringBuffer();
         byte var16 = 0;
         boolean var17 = false;
         int var18 = 0;

         while(true) {
            int var19 = var13.length;
            if(var18 >= var19) {
               byte var72 = 3;
               if(var16 == var72) {
                  String var73 = var14.toString().trim().toLowerCase();
                  String var74 = var15.toString().trim();
                  Object var78 = var2.put(var73, var74);
               }
               break;
            }

            char var22 = var13[var18];
            boolean var41;
            label77:
            switch(var16) {
            case 0:
               byte var35 = 61;
               if(var22 == var35) {
                  log.error("Expected header param name, got \'=\'");
                  var16 = 99;
                  break;
               } else {
                  var14 = new StringBuffer();
                  var15 = new StringBuffer();
                  var16 = 1;
               }
            case 1:
               byte var37 = 61;
               if(var22 == var37) {
                  if(var14.length() == 0) {
                     var16 = 99;
                  } else {
                     var16 = 2;
                  }
               } else {
                  StringBuffer var40 = var14.append(var22);
               }
               break;
            case 2:
               var41 = false;
               switch(var22) {
               case 9:
               case 32:
                  break;
               case 34:
                  var16 = 4;
                  break;
               default:
                  var16 = 3;
                  var41 = true;
               }

               if(!var41) {
                  break;
               }
            case 3:
               var41 = false;
               switch(var22) {
               case 9:
               case 32:
               case 59:
                  String var45 = var14.toString().trim().toLowerCase();
                  String var46 = var15.toString().trim();
                  Object var50 = var2.put(var45, var46);
                  var16 = 5;
                  var41 = true;
                  break;
               default:
                  StringBuffer var44 = var15.append(var22);
               }

               if(!var41) {
                  break;
               }
            case 4:
               switch(var22) {
               case 34:
                  if(!var17) {
                     String var57 = var14.toString().trim().toLowerCase();
                     String var58 = var15.toString();
                     Object var62 = var2.put(var57, var58);
                     var16 = 5;
                  } else {
                     StringBuffer var65 = var15.append(var22);
                  }
                  break label77;
               case 92:
                  if(var17) {
                     char var67 = 92;
                     var15.append(var67);
                  }

                  if(!var17) {
                     boolean var69 = true;
                  } else {
                     boolean var70 = false;
                  }
                  break label77;
               default:
                  if(var17) {
                     char var52 = 92;
                     var15.append(var52);
                  }

                  StringBuffer var56 = var15.append(var22);
                  break label77;
               }
            case 5:
               switch(var22) {
               case 9:
               case 32:
                  break label77;
               case 59:
                  var16 = 0;
                  break label77;
               default:
                  var16 = 99;
                  break label77;
               }
            case 99:
               byte var33 = 59;
               if(var22 == var33) {
                  var16 = 0;
               }
            }

            ++var18;
         }
      }

      return var2;
   }

   public void addField(String var1, String var2) {
      String var3 = var1.trim().toLowerCase();
      if(var3.equals("content-transfer-encoding") && !this.contentTransferEncSet) {
         this.contentTransferEncSet = (boolean)1;
         var2 = var2.trim().toLowerCase();
         if(var2.length() > 0) {
            this.transferEncoding = var2;
         }
      } else if(var3.equals("content-type")) {
         if(!this.contentTypeSet) {
            this.contentTypeSet = (boolean)1;
            var2 = var2.trim();
            StringBuffer var4 = new StringBuffer();
            int var5 = 0;

            while(true) {
               int var6 = var2.length();
               if(var5 >= var6) {
                  String var9 = var4.toString();
                  Map var10 = this.getHeaderParams(var9);
                  String var11 = (String)var10.get("");
                  if(var11 != null) {
                     var11 = var11.toLowerCase().trim();
                     int var12 = var11.indexOf(47);
                     boolean var13 = false;
                     if(var12 != -1) {
                        String var14 = var11.substring(0, var12).trim();
                        int var15 = var12 + 1;
                        String var16 = var11.substring(var15).trim();
                        if(var14.length() > 0 && var16.length() > 0) {
                           var14 + "/" + var16;
                           var13 = true;
                        }
                     }

                     if(!var13) {
                        var11 = null;
                     }
                  }

                  String var18 = (String)var10.get("boundary");
                  if(var11 != null && (var11.startsWith("multipart/") && var18 != null || !var11.startsWith("multipart/"))) {
                     this.mimeType = var11;
                  }

                  if(this.isMultipart()) {
                     this.boundary = var18;
                  }

                  String var19 = (String)var10.get("charset");
                  if(var19 != null) {
                     var19 = var19.trim();
                     if(var19.length() > 0) {
                        String var20 = var19.toLowerCase();
                        this.charset = var20;
                     }
                  }

                  this.parameters.putAll(var10);
                  Object var21 = this.parameters.remove("");
                  Object var22 = this.parameters.remove("boundary");
                  Object var23 = this.parameters.remove("charset");
                  return;
               }

               char var7 = var2.charAt(var5);
               if(var7 != 13 && var7 != 10) {
                  var4.append(var7);
               }

               ++var5;
            }
         }
      }
   }

   public String getBoundary() {
      return this.boundary;
   }

   public String getCharset() {
      return this.charset;
   }

   public String getMimeType() {
      return this.mimeType;
   }

   public Map getParameters() {
      return this.parameters;
   }

   public String getTransferEncoding() {
      return this.transferEncoding;
   }

   public boolean isBase64Encoded() {
      String var1 = this.transferEncoding;
      return "base64".equals(var1);
   }

   public boolean isMessage() {
      return this.mimeType.equals("message/rfc822");
   }

   public boolean isMimeType(String var1) {
      String var2 = this.mimeType;
      String var3 = var1.toLowerCase();
      return var2.equals(var3);
   }

   public boolean isMultipart() {
      return this.mimeType.startsWith("multipart/");
   }

   public boolean isQuotedPrintableEncoded() {
      String var1 = this.transferEncoding;
      return "quoted-printable".equals(var1);
   }

   public String toString() {
      return this.mimeType;
   }
}
