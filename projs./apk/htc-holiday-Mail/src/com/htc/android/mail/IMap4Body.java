package com.htc.android.mail;

import com.htc.android.mail.Headers;
import com.htc.android.mail.Imap4InfoObject;
import com.htc.android.mail.Mail;
import com.htc.android.mail.Mime;
import com.htc.android.mail.Rfc2822;
import com.htc.android.mail.ll;
import java.util.ArrayList;
import java.util.HashMap;

public class IMap4Body {

   private static final String TAG = "IMap4BodyParser";
   int alternativeNum;
   int attachment;
   String bodyType;
   public int bodycontenttype;
   int htmlbody;
   String index;
   StringBuilder infoString;
   int inlineNum;
   public ArrayList<IMap4Body.Part> mParts;
   ArrayList<IMap4Body.Part> mTotalParts;
   int mixAttachment;
   int mixedNum;
   String parsingStr;
   int related;
   int relatedNum;
   public HashMap<Integer, Imap4InfoObject> table;
   int textbody;


   public IMap4Body(String var1) {
      ArrayList var2 = new ArrayList();
      this.mTotalParts = var2;
      ArrayList var3 = new ArrayList();
      this.mParts = var3;
      this.bodyType = "";
      this.parsingStr = "";
      this.index = "";
      StringBuilder var4 = new StringBuilder();
      this.infoString = var4;
      int var5 = Rfc2822.CONTENTTYPE_DEFAULT;
      this.bodycontenttype = var5;
      HashMap var6 = new HashMap();
      this.table = var6;
      this.mixAttachment = 0;
      this.attachment = 0;
      this.textbody = 0;
      this.htmlbody = 0;
      this.mixedNum = 0;
      this.alternativeNum = 0;
      this.related = 0;
      this.relatedNum = 0;
      this.inlineNum = 0;
      this.parsingStr = var1;
      this.parseParts(var1);
   }

   public IMap4Body(String var1, String var2) {
      ArrayList var3 = new ArrayList();
      this.mTotalParts = var3;
      ArrayList var4 = new ArrayList();
      this.mParts = var4;
      this.bodyType = "";
      this.parsingStr = "";
      this.index = "";
      StringBuilder var5 = new StringBuilder();
      this.infoString = var5;
      int var6 = Rfc2822.CONTENTTYPE_DEFAULT;
      this.bodycontenttype = var6;
      HashMap var7 = new HashMap();
      this.table = var7;
      this.mixAttachment = 0;
      this.attachment = 0;
      this.textbody = 0;
      this.htmlbody = 0;
      this.mixedNum = 0;
      this.alternativeNum = 0;
      this.related = 0;
      this.relatedNum = 0;
      this.inlineNum = 0;
      this.index = var2;
      this.parsingStr = var1;
      this.parseParts(var1);
   }

   private void addToTotalParts(IMap4Body var1) {
      int var2 = 0;

      while(true) {
         int var3 = var1.mParts.size();
         if(var2 >= var3) {
            return;
         }

         if(((IMap4Body.Part)var1.mParts.get(var2)).imap4body != null) {
            IMap4Body var4 = ((IMap4Body.Part)var1.mParts.get(var2)).imap4body;
            this.addToTotalParts(var4);
         } else {
            if(Mail.MAIL_DETAIL_DEBUG) {
               StringBuilder var5 = (new StringBuilder()).append("body is:").append(var1).append(", bodycontenttype is ");
               int var6 = this.bodycontenttype;
               String var7 = var5.append(var6).toString();
               ll.d("IMap4BodyParser", var7);
            }

            IMap4Body.Part var8 = (IMap4Body.Part)var1.mParts.get(var2);
            int var9 = var1.bodycontenttype;
            var8.contenttype = var9;
            ArrayList var10 = this.mTotalParts;
            Object var11 = var1.mParts.get(var2);
            var10.add(var11);
         }

         ++var2;
      }
   }

   private void parseParts(String var1) {
      if(var1.startsWith("(")) {
         this.seperateParts(var1);
      } else {
         if(Mail.MAIL_DETAIL_DEBUG) {
            StringBuilder var9 = (new StringBuilder()).append(" single part info:");
            int var10 = var1.length();
            String var11 = var1.substring(0, var10);
            String var12 = var9.append(var11).toString();
            ll.d("IMap4BodyParser", var12);
         }

         int var13 = var1.length();
         String var14 = var1.substring(0, var13);
         IMap4Body.Part var15 = new IMap4Body.Part(var14, "1");
         this.mParts.add(var15);
      }

      if(this.index.length() == 0) {
         String var2 = this.bodyType;
         int var3 = this.mParts.size();
         this.setValueByBodyType(var2, var3);
         int var4 = 0;

         while(true) {
            int var5 = this.mParts.size();
            if(var4 >= var5) {
               int var22 = 0;

               while(true) {
                  int var23 = this.mTotalParts.size();
                  if(var22 >= var23) {
                     return;
                  }

                  if(((IMap4Body.Part)this.mTotalParts.get(var22)).value != null && !((String)((IMap4Body.Part)this.mTotalParts.get(var22)).value.get("type")).equalsIgnoreCase("report-type") && !((String)((IMap4Body.Part)this.mTotalParts.get(var22)).value.get("type")).equalsIgnoreCase("error")) {
                     if(Mail.MAIL_DETAIL_DEBUG) {
                        StringBuilder var24 = (new StringBuilder()).append(var22).append(" #### type:");
                        String var25 = (String)((IMap4Body.Part)this.mTotalParts.get(var22)).value.get("type");
                        String var26 = var24.append(var25).toString();
                        ll.d("IMap4BodyParser", var26);
                     }

                     if(Mail.MAIL_DETAIL_DEBUG) {
                        StringBuilder var27 = (new StringBuilder()).append(var22).append(" #### index:");
                        String var28 = ((IMap4Body.Part)this.mTotalParts.get(var22)).index;
                        String var29 = var27.append(var28).toString();
                        ll.d("IMap4BodyParser", var29);
                     }

                     if(((String)((IMap4Body.Part)this.mTotalParts.get(var22)).value.get("type")).equalsIgnoreCase("TEXT") && ((String)((IMap4Body.Part)this.mTotalParts.get(var22)).value.get("subtype")).equalsIgnoreCase("PLAIN") && ((IMap4Body.Part)this.mTotalParts.get(var22)).value.get("name") == null && ((IMap4Body.Part)this.mTotalParts.get(var22)).value.get("filename") == null) {
                        this.textbody = 1;
                        if(Mail.MAIL_DETAIL_DEBUG) {
                           ll.d("IMap4BodyParser", "we have text");
                        }
                     } else if(((String)((IMap4Body.Part)this.mTotalParts.get(var22)).value.get("type")).equalsIgnoreCase("TEXT") && ((String)((IMap4Body.Part)this.mTotalParts.get(var22)).value.get("subtype")).equalsIgnoreCase("HTML") && ((IMap4Body.Part)this.mTotalParts.get(var22)).value.get("name") == null && ((IMap4Body.Part)this.mTotalParts.get(var22)).value.get("filename") == null) {
                        this.htmlbody = 1;
                        if(Mail.MAIL_DETAIL_DEBUG) {
                           ll.d("IMap4BodyParser", "we have html");
                        }
                     }

                     HashMap var30 = ((IMap4Body.Part)this.mTotalParts.get(var22)).value;
                     if(var30.get("type") != null && ((String)var30.get("type")).equalsIgnoreCase("message") && var30.get("subtype") != null && ((String)var30.get("subtype")).equalsIgnoreCase("rfc822")) {
                        Object var31 = var30.remove("filename");
                        String var32 = null;
                        if(var30.get("bodyline") != null) {
                           String var33 = (String)var30.get("bodyline");
                           var32 = this.parseRfc822FileName(var33);
                        }

                        String var34;
                        if(var32 != null) {
                           var34 = var32 + ".msg";
                        } else {
                           var34 = "Message.msg";
                        }

                        var30.put("filename", var34);
                     }

                     Imap4InfoObject var36 = new Imap4InfoObject();
                     String var37 = (String)var30.get("type");
                     var36.type = var37;
                     String var38 = (String)var30.get("subtype");
                     var36.subtype = var38;
                     String var39;
                     if(var30.get("filename") == null) {
                        if(var30.get("charset") == null) {
                           var39 = Headers.rfc2047((String)var30.get("name"));
                        } else {
                           var39 = (String)var30.get("charset");
                        }
                     } else {
                        var39 = Headers.rfc2047((String)var30.get("filename"));
                     }

                     var36.typename = var39;
                     String var40 = ((String)var30.get("bodyid")).replaceAll("<", "").replaceAll(">", "");
                     var36.cid = var40;
                     String var41 = (String)var30.get("bodydesc");
                     var36.cidfname = var41;
                     String var42 = (String)var30.get("bodyencoding");
                     var36.encode = var42;
                     String var43 = (String)var30.get("bodysize");
                     var36.size = var43;
                     String var44 = ((IMap4Body.Part)this.mTotalParts.get(var22)).index;
                     var36.index = var44;
                     String var45;
                     if(var30.get("inline") == null) {
                        var45 = "0";
                     } else {
                        var45 = "1";
                     }

                     var36.inline = var45;
                     String var46 = String.valueOf(((IMap4Body.Part)this.mTotalParts.get(var22)).contenttype);
                     var36.contenttype = var46;
                     String var47;
                     if(var30.get("name") == null && var30.get("filename") == null) {
                        var47 = "0";
                     } else {
                        var47 = "1";
                     }

                     var36.isfile = var47;
                     HashMap var48 = this.table;
                     Integer var49 = Integer.valueOf(var22);
                     var48.put(var49, var36);
                     int var51 = ((IMap4Body.Part)this.mTotalParts.get(var22)).contenttype;
                     int var52 = Rfc2822.CONTENTTYPE_MIXED;
                     if(var51 == var52 && (var30.get("name") != null || var30.get("filename") != null)) {
                        StringBuilder var53 = new StringBuilder();
                        String var54 = (String)var30.get("type");
                        StringBuilder var55 = var53.append(var54).append("/");
                        String var56 = (String)var30.get("subtype");
                        if(!Mime.dropAttach(var55.append(var56).toString())) {
                           int var57 = this.mixAttachment + 1;
                           this.mixAttachment = var57;
                        }
                     }

                     if(var30.get("name") != null || var30.get("filename") != null) {
                        StringBuilder var58 = new StringBuilder();
                        String var59 = (String)var30.get("type");
                        StringBuilder var60 = var58.append(var59).append("/");
                        String var61 = (String)var30.get("subtype");
                        if(!Mime.dropAttach(var60.append(var61).toString())) {
                           int var62 = this.attachment + 1;
                           this.attachment = var62;
                        }
                     }
                  }

                  ++var22;
               }
            }

            if(((IMap4Body.Part)this.mParts.get(var4)).imap4body != null) {
               IMap4Body var6 = ((IMap4Body.Part)this.mParts.get(var4)).imap4body;
               String var7 = var6.bodyType;
               int var8 = var6.mParts.size();
               this.setValueByBodyType(var7, var8);
               this.addToTotalParts(var6);
            } else {
               IMap4Body.Part var17 = (IMap4Body.Part)this.mParts.get(var4);
               int var18 = this.bodycontenttype;
               var17.contenttype = var18;
               ArrayList var19 = this.mTotalParts;
               Object var20 = this.mParts.get(var4);
               var19.add(var20);
            }

            ++var4;
         }
      }
   }

   private String parseRfc822FileName(String var1) {
      StringBuilder var2 = new StringBuilder();
      int var3 = 0;

      String var5;
      while(true) {
         int var4 = var1.length();
         if(var3 >= var4) {
            var5 = null;
            break;
         }

         if(var1.charAt(var3) == 34) {
            if(false) {
               if(-1 == -1) {
                  var2 = new StringBuilder();
               } else if(true) {
                  if(0 + 1 == 2) {
                     var5 = var2.toString();
                     break;
                  }

                  var2 = new StringBuilder();
               }
            }
         } else {
            label47: {
               if(var1.charAt(var3) != 34 && false) {
                  if(-1 == -1) {
                     if(var1.charAt(var3) != 32) {
                        var2 = new StringBuilder();
                        char var8 = var1.charAt(var3);
                        var2.append(var8);
                     }
                     break label47;
                  }

                  if(true && var1.charAt(var3) == 32 && false) {
                     if(0 + 1 == 2) {
                        var5 = var2.toString();
                        break;
                     }

                     var2 = new StringBuilder();
                     break label47;
                  }
               }

               char var6 = var1.charAt(var3);
               var2.append(var6);
            }
         }

         ++var3;
      }

      return var5;
   }

   private void seperateParts(String var1) {
      StringBuilder var2 = new StringBuilder();
      if(Mail.MAIL_DETAIL_DEBUG) {
         String var3 = "############ seperateParts :" + var1;
         ll.d("IMap4BodyParser", var3);
      }

      int var4 = 0;

      while(true) {
         int var5 = var1.length();
         if(var4 >= var5) {
            return;
         }

         label128: {
            if(var1.charAt(var4) == 40) {
               if(-1 == -1) {
                  int var6 = 0 + 1;
                  break label128;
               }

               if(true) {
                  if(Mail.MAIL_DETAIL_DEBUG) {
                     ll.d("IMap4BodyParser", "should not happened 1.");
                  }
                  break label128;
               }

               if(false) {
                  int var7 = 0 + 1;
               }
            } else if(var1.charAt(var4) == 41) {
               if(-1 == -1) {
                  if(Mail.MAIL_DETAIL_DEBUG) {
                     ll.d("IMap4BodyParser", "should not happened 2.");
                  }
                  break label128;
               }

               if(false) {
                  if(0 + -1 <= 0) {
                     if(!var2.toString().toUpperCase().startsWith("\"BOUNDARY")) {
                        if(!var2.toString().toUpperCase().startsWith("\"TYPE")) {
                           if(Mail.MAIL_DETAIL_DEBUG) {
                              StringBuilder var13 = (new StringBuilder()).append("######## seperateParts:parts done:");
                              String var14 = var2.toString();
                              String var15 = var13.append(var14).toString();
                              ll.d("IMap4BodyParser", var15);
                           }

                           IMap4Body.Part var16 = new IMap4Body.Part;
                           String var17 = var2.toString().trim();
                           StringBuilder var18 = new StringBuilder();
                           String var21;
                           if(this.index.length() > 0) {
                              StringBuilder var19 = new StringBuilder();
                              String var20 = this.index;
                              var21 = var19.append(var20).append(".").toString();
                           } else {
                              var21 = "";
                           }

                           StringBuilder var22 = var18.append(var21);
                           int var23 = this.mParts.size() + 1;
                           String var24 = var22.append(var23).toString();
                           var16.<init>(var17, var24);
                           this.mParts.add(var16);
                           var2 = new StringBuilder();
                           break label128;
                        }

                        if(Mail.MAIL_DETAIL_DEBUG) {
                           StringBuilder var10 = (new StringBuilder()).append("######## seperateParts:parts type:");
                           String var11 = var2.toString();
                           String var12 = var10.append(var11).toString();
                           ll.d("IMap4BodyParser", var12);
                        }
                     } else {
                        if(var2.toString().toUpperCase().startsWith("\"BOUNDARY")) {
                           if(Mail.MAIL_DETAIL_DEBUG) {
                              StringBuilder var26 = (new StringBuilder()).append("######## seperateParts:parts boundary:");
                              String var27 = var2.toString();
                              String var28 = var26.append(var27).toString();
                              ll.d("IMap4BodyParser", var28);
                           }

                           new StringBuilder();
                           return;
                        }

                        if(Mail.MAIL_DETAIL_DEBUG) {
                           ll.d("IMap4BodyParser", "########### xxxxxxxxxxxx");
                        }
                     }
                  }
               } else if(true && Mail.MAIL_DETAIL_DEBUG) {
                  ll.d("IMap4BodyParser", "should not happened 3.");
               }
            } else if(var1.charAt(var4) == 34) {
               if(-1 == -1) {
                  var2 = new StringBuilder();
                  break label128;
               }

               if(true) {
                  String var30 = var2.toString();
                  this.bodyType = var30;
                  if(Mail.MAIL_DETAIL_DEBUG) {
                     StringBuilder var31 = (new StringBuilder()).append("$$$$ bodyType is:");
                     String var32 = this.bodyType;
                     String var33 = var31.append(var32).toString();
                     ll.d("IMap4BodyParser", var33);
                  }

                  if(this.bodyType.equalsIgnoreCase("mixed")) {
                     int var34 = Rfc2822.CONTENTTYPE_MIXED;
                     this.bodycontenttype = var34;
                  } else if(this.bodyType.equalsIgnoreCase("related")) {
                     int var38 = Rfc2822.CONTENTTYPE_RELATED;
                     this.bodycontenttype = var38;
                  } else if(this.bodyType.equalsIgnoreCase("alternative")) {
                     int var39 = Rfc2822.CONTENTTYPE_ALTERNATIVE;
                     this.bodycontenttype = var39;
                  }

                  if(Mail.MAIL_DETAIL_DEBUG) {
                     StringBuilder var35 = (new StringBuilder()).append("body:").append(this).append(", bodycontenttype:");
                     int var36 = this.bodycontenttype;
                     String var37 = var35.append(var36).toString();
                     ll.d("IMap4BodyParser", var37);
                  }

                  var2 = new StringBuilder();
                  break label128;
               }

               if(false) {
                  ;
               }
            } else if(var1.charAt(var4) == 32 && true && false) {
               if(-1 == -1 && var2.toString().length() > 0) {
                  if(Mail.MAIL_DETAIL_DEBUG) {
                     StringBuilder var40 = (new StringBuilder()).append("sperate save value is:");
                     String var41 = var2.toString();
                     String var42 = var40.append(var41).toString();
                     ll.d("IMap4BodyParser", var42);
                  }

                  var2 = new StringBuilder();
                  break label128;
               }

               if(-1 == -1 && var2.toString().length() <= 0) {
                  break label128;
               }
            }

            char var8 = var1.charAt(var4);
            var2.append(var8);
         }

         ++var4;
      }
   }

   private void setValueByBodyType(String var1, int var2) {
      if(Mail.MAIL_DETAIL_DEBUG) {
         String var3 = "setValueByBodyType:" + var1 + " , size = " + var2;
         ll.d("IMap4BodyParser", var3);
      }

      if(var1.toUpperCase().compareTo("MIXED") == 0) {
         this.mixedNum = var2;
      } else if(var1.toUpperCase().compareTo("RELATED") == 0) {
         this.related = 1;
         this.relatedNum = var2;
      } else if(var1.toUpperCase().compareTo("ALTERNATIVE") == 0) {
         this.textbody = 1;
         this.htmlbody = 1;
         this.alternativeNum = var2;
      } else if(var1.length() == 0) {
         if(Mail.MAIL_DETAIL_DEBUG) {
            ll.d("IMap4BodyParser", "It\'s a single field, no bodyType.");
         }
      } else if(Mail.MAIL_DETAIL_DEBUG) {
         String var4 = "Unhandle bodyType... call rOy now -_-|||," + var1;
         ll.d("IMap4BodyParser", var4);
      }
   }

   public final int alternativeNum() {
      return this.alternativeNum;
   }

   public final Imap4InfoObject get(String var1) {
      int var2 = 0;

      Imap4InfoObject var7;
      while(true) {
         int var3 = this.table.size();
         if(var2 >= var3) {
            var7 = null;
            break;
         }

         HashMap var4 = this.table;
         Integer var5 = Integer.valueOf(var2);
         Imap4InfoObject var6 = (Imap4InfoObject)var4.get(var5);
         if(var6.index.equals(var1)) {
            var7 = var6;
            break;
         }

         ++var2;
      }

      return var7;
   }

   public final String getInfo() {
      return this.table.toString();
   }

   public final int getRelated() {
      return this.related;
   }

   public final int getRelatedNum() {
      return this.relatedNum;
   }

   public final HashMap<Integer, Imap4InfoObject> getTable() {
      return this.table;
   }

   public final Imap4InfoObject getTextHtmlIndex(String var1, String var2) {
      int var3 = 0;

      Imap4InfoObject var8;
      while(true) {
         int var4 = this.table.size();
         if(var3 >= var4) {
            var8 = null;
            break;
         }

         HashMap var5 = this.table;
         Integer var6 = Integer.valueOf(var3);
         Imap4InfoObject var7 = (Imap4InfoObject)var5.get(var6);
         if(var7 == null) {
            var8 = null;
            break;
         }

         int var10;
         label30: {
            int var9;
            try {
               var9 = Integer.valueOf(var7.size).intValue();
            } catch (Exception var11) {
               var11.printStackTrace();
               var10 = 0;
               break label30;
            }

            var10 = var9;
         }

         if(var7.type.equalsIgnoreCase(var1) && var7.subtype.equalsIgnoreCase(var2) && var7.isfile.equals("0") && var10 > 0) {
            var8 = var7;
            break;
         }

         ++var3;
      }

      return var8;
   }

   public final int includeAttach() {
      return this.attachment;
   }

   public final int includeHtml() {
      return this.htmlbody;
   }

   public final int includeMixAttach() {
      return this.mixAttachment;
   }

   public final int includeText() {
      return this.textbody;
   }

   public final int mixedNum() {
      return this.mixedNum;
   }

   class Part {

      public int contenttype;
      public IMap4Body imap4body = null;
      String index = "";
      String parsingStr = "";
      public HashMap<String, String> value;


      Part(String var2, String var3) {
         HashMap var4 = new HashMap();
         this.value = var4;
         int var5 = Rfc2822.CONTENTTYPE_DEFAULT;
         this.contenttype = var5;
         this.index = var3;
         this.parsingStr = var2;
         int var6 = this.parseFieldInfo();
      }

      private void findParts(String var1, String var2) {
         IMap4Body var3 = new IMap4Body(var1, var2);
         this.imap4body = var3;
      }

      private void parseAtom(String var1, HashMap<String, String> var2) {
         byte var3 = -1;
         byte var4 = -1;
         ArrayList var5 = new ArrayList();
         StringBuilder var6 = new StringBuilder();
         int var7 = 0;

         while(true) {
            int var8 = var1.length();
            if(var7 >= var8) {
               int var32 = 0;

               while(true) {
                  int var33 = var5.size() - 1;
                  if(var32 >= var33) {
                     return;
                  }

                  if(var5.get(var32) != null) {
                     int var34 = var32 + 1;
                     if(var5.get(var34) != null) {
                        if(Mail.MAIL_DETAIL_DEBUG) {
                           StringBuilder var35 = (new StringBuilder()).append("put :");
                           String var36 = ((String)var5.get(var32)).toLowerCase();
                           StringBuilder var37 = var35.append(var36).append(", value is :");
                           int var38 = var32 + 1;
                           String var39 = (String)var5.get(var38);
                           String var40 = var37.append(var39).toString();
                           ll.d("IMap4BodyParser", var40);
                        }

                        String var41 = ((String)var5.get(var32)).toLowerCase();
                        int var42 = var32 + 1;
                        Object var43 = var5.get(var42);
                        var2.put(var41, var43);
                     }
                  }

                  var32 += 2;
               }
            }

            if(var4 == 1) {
               var4 = 2;
            } else if(var4 == 2 && var1.charAt(var7) != 41) {
               char var9 = var1.charAt(var7);
               var6.append(var9);
            } else if(var4 == 2 && var1.charAt(var7) == 41) {
               String var11 = var6.toString();
               var5.add(var11);
               if(Mail.MAIL_DETAIL_DEBUG) {
                  StringBuilder var13 = (new StringBuilder()).append("parseAtom add :");
                  String var14 = var6.toString();
                  String var15 = var13.append(var14).toString();
                  ll.d("IMap4BodyParser", var15);
               }

               new StringBuilder();
               var3 = -1;
               var4 = -1;
            } else {
               label97: {
                  if(var1.charAt(var7) == 34) {
                     if(var3 == -1) {
                        var3 = 0;
                        break label97;
                     }

                     if(var3 == 0) {
                        label99: {
                           if(var6.length() > 0) {
                              int var17 = var6.length() - 1;
                              if(var6.charAt(var17) == 92) {
                                 int var18 = var6.length() - 1;
                                 var6.deleteCharAt(var18);
                                 break label99;
                              }
                           }

                           String var22 = var6.toString();
                           var5.add(var22);
                           if(Mail.MAIL_DETAIL_DEBUG) {
                              StringBuilder var24 = (new StringBuilder()).append("parseAtom add:");
                              String var25 = var6.toString();
                              String var26 = var24.append(var25).toString();
                              ll.d("IMap4BodyParser", var26);
                           }

                           new StringBuilder();
                           var3 = -1;
                           break label97;
                        }
                     }
                  } else if(var1.charAt(var7) == 123) {
                     if(var3 == -1) {
                        var3 = 1;
                        break label97;
                     }
                  } else if(var1.charAt(var7) == 125 && var3 == 1) {
                     if(Mail.MAIL_DETAIL_DEBUG) {
                        StringBuilder var28 = (new StringBuilder()).append("skip:");
                        String var29 = var6.toString();
                        String var30 = var28.append(var29).toString();
                        ll.d("IMap4BodyParser", var30);
                     }

                     new StringBuilder();
                     var3 = -1;
                     var4 = 1;
                     break label97;
                  }

                  if(var3 != -1) {
                     char var20 = var1.charAt(var7);
                     var6.append(var20);
                  }
               }
            }

            ++var7;
         }
      }

      private int parseFieldInfo() {
         if(this.parsingStr.startsWith("(") && !this.parsingStr.endsWith(")") && this.parsingStr.endsWith("NIL")) {
            if(Mail.MAIL_DETAIL_DEBUG) {
               ll.d("IMap4BodyParser", "##### parts: multi parts");
            }

            String var1 = this.parsingStr;
            String var2 = this.index;
            this.findParts(var1, var2);
         } else if(this.parsingStr.startsWith("(") && this.parsingStr.endsWith(")")) {
            if(Mail.MAIL_DETAIL_DEBUG) {
               ll.d("IMap4BodyParser", "##### parts: multi parts");
            }

            String var3 = this.parsingStr;
            String var4 = this.index;
            this.findParts(var3, var4);
         } else if(!this.parsingStr.startsWith("(") && !this.parsingStr.endsWith(")")) {
            if(Mail.MAIL_DETAIL_DEBUG) {
               ll.d("IMap4BodyParser", "##### parts: single parts");
            }

            String var5 = this.parsingStr;
            HashMap var6 = this.parseSingleParts(var5);
            this.value = var6;
         } else if(!this.parsingStr.startsWith("(") && this.parsingStr.endsWith(")")) {
            String var7 = this.parsingStr;
            HashMap var8 = this.parseSingleParts(var7);
            this.value = var8;
         }

         return 0;
      }

      private HashMap parseSingleParts(String var1) {
         String[] var2 = new String[]{"type", "subtype", "paramlist", "bodyid", "bodydesc", "bodyencoding", "bodysize", "bodyline", "attachinfo", "textattachinfo"};
         StringBuilder var3 = new StringBuilder();
         HashMap var4 = new HashMap();
         int var5 = 0;

         while(true) {
            int var6 = var1.length();
            if(var5 >= var6) {
               String var92 = (String)var4.get("paramlist");
               if(!var92.equalsIgnoreCase("NIL")) {
                  this.parseAtom(var92, var4);
               }

               String var96 = (String)var4.get("attachinfo");
               HashMap var107;
               if(var96 == null) {
                  if(var4.get("type") == null || var4.get("subtype") == null) {
                     var107 = null;
                     return var107;
                  }

                  var96 = "NIL";
               }

               if(!var96.equalsIgnoreCase("NIL")) {
                  String var98 = " ";
                  byte var99 = 2;
                  String[] var100 = var96.split(var98, var99);
                  if(var100[0].toUpperCase().contains("ATTACHMENT")) {
                     if(var100[1].toUpperCase().contains("FILENAME")) {
                        String var101 = var100[1];
                        int var102 = var100[1].length() - 1;
                        String var103 = var101.substring(1, var102);
                        this.parseAtom(var103, var4);
                     } else if(var4.get("name") != null && ((String)var4.get("name")).length() > 0) {
                        String var108 = "FILENAME".toLowerCase();
                        String var110 = "name";
                        Object var111 = var4.get(var110);
                        Object var115 = var4.put(var108, var111);
                     } else {
                        String var116 = "FILENAME".toLowerCase();
                        String var119 = "noname";
                        var4.put(var116, var119);
                     }
                  } else {
                     String var121 = (String)var4.get("type");
                     String var122 = "text";
                     if(!var121.equalsIgnoreCase(var122) && var96.toUpperCase().contains("INLINE")) {
                        if(var4.get("name") != null && ((String)var4.get("name")).length() > 0) {
                           String var123 = "FILENAME".toLowerCase();
                           String var125 = "name";
                           Object var126 = var4.get(var125);
                           Object var130 = var4.put(var123, var126);
                        } else {
                           String var131 = "FILENAME".toLowerCase();
                           String var134 = "noname";
                           var4.put(var131, var134);
                        }
                     }
                  }
               } else {
                  String var136 = (String)var4.get("type");
                  String var137 = "text";
                  if(var136.equalsIgnoreCase(var137) && var4.get("textattachinfo") != null && ((String)var4.get("textattachinfo")).toUpperCase().contains("ATTACHMENT")) {
                     String var138 = (String)var4.get("textattachinfo");
                     String var139 = " ";
                     byte var140 = 2;
                     String[] var141 = var138.split(var139, var140);
                     if(var141[1].toUpperCase().contains("FILENAME")) {
                        String var142 = var141[1];
                        int var143 = var141[1].length() - 1;
                        String var144 = var142.substring(1, var143);
                        this.parseAtom(var144, var4);
                     } else if(var4.get("name") != null && ((String)var4.get("name")).length() > 0) {
                        String var148 = "FILENAME".toLowerCase();
                        String var150 = "name";
                        Object var151 = var4.get(var150);
                        Object var155 = var4.put(var148, var151);
                     } else {
                        String var156 = "FILENAME".toLowerCase();
                        String var159 = "noname";
                        var4.put(var156, var159);
                     }
                  } else {
                     label218: {
                        String var161 = (String)var4.get("type");
                        String var162 = "text";
                        if(var161.equalsIgnoreCase(var162) && var4.get("textattachinfo") != null && ((String)var4.get("textattachinfo")).toUpperCase().contains("INLINE")) {
                           label146: {
                              String var163 = (String)var4.get("subtype");
                              String var164 = "plain";
                              if(!var163.equalsIgnoreCase(var164)) {
                                 String var165 = (String)var4.get("subtype");
                                 String var166 = "html";
                                 if(!var165.equalsIgnoreCase(var166)) {
                                    break label146;
                                 }
                              }

                              IMap4Body var167 = IMap4Body.this;
                              int var168 = var167.inlineNum + 1;
                              var167.inlineNum = var168;
                              String var170 = "inline";
                              String var171 = "1";
                              var4.put(var170, var171);
                              break label218;
                           }
                        }

                        String var173 = (String)var4.get("type");
                        String var174 = "text";
                        if(var173.equalsIgnoreCase(var174) && var4.get("textattachinfo") != null && ((String)var4.get("textattachinfo")).toUpperCase().contains("INLINE")) {
                           String var175 = (String)var4.get("subtype");
                           String var176 = "plain";
                           if(!var175.equalsIgnoreCase(var176)) {
                              String var177 = (String)var4.get("subtype");
                              String var178 = "html";
                              if(!var177.equalsIgnoreCase(var178)) {
                                 String var179 = "FILENAME".toLowerCase();
                                 String var182 = "noname";
                                 var4.put(var179, var182);
                                 break label218;
                              }
                           }
                        }

                        String var184 = (String)var4.get("type");
                        String var185 = "text";
                        if(!var184.equalsIgnoreCase(var185)) {
                           if(var4.get("name") != null && ((String)var4.get("name")).length() > 0) {
                              String var186 = "FILENAME".toLowerCase();
                              String var188 = "name";
                              Object var189 = var4.get(var188);
                              Object var193 = var4.put(var186, var189);
                           } else {
                              String var194 = "FILENAME".toLowerCase();
                              String var197 = "noname";
                              var4.put(var194, var197);
                           }
                        }
                     }
                  }
               }

               var107 = var4;
               return var107;
            }

            label209: {
               char var9 = var1.charAt(var5);
               byte var10 = 34;
               int var20;
               if(var9 == var10) {
                  if(false) {
                     if(-1 == -1) {
                        var3 = new StringBuilder();
                        break label209;
                     }

                     if(true) {
                        int var19 = var2.length;
                        if(0 < var19) {
                           var20 = 0 + 1;
                           String var21 = var2[0];
                           String var22 = var3.toString();
                           Object var26 = var4.put(var21, var22);
                           if(Mail.MAIL_DETAIL_DEBUG) {
                              StringBuilder var27 = (new StringBuilder()).append("22 save ");
                              int var28 = var20 - 1;
                              String var29 = var2[var28];
                              StringBuilder var30 = var27.append(var29).append(", value is:");
                              String var31 = var3.toString();
                              String var32 = var30.append(var31).toString();
                              ll.d("IMap4BodyParser", var32);
                           }
                        } else if(Mail.MAIL_DETAIL_DEBUG) {
                           StringBuilder var34 = (new StringBuilder()).append("save value is:");
                           String var35 = var3.toString();
                           String var36 = var34.append(var35).toString();
                           ll.d("IMap4BodyParser", var36);
                        }

                        var3 = new StringBuilder();
                        break label209;
                     }
                  }
               } else {
                  char var39 = var1.charAt(var5);
                  byte var40 = 40;
                  if(var39 == var40) {
                     if(false) {
                        int var41 = 0 + 1;
                     } else if(true) {
                        int var42 = 0 + 1;
                     } else if(-1 == -1) {
                        var3 = new StringBuilder();
                        int var43 = 0 + 1;
                        break label209;
                     }
                  } else {
                     char var46 = var1.charAt(var5);
                     byte var47 = 41;
                     if(var46 == var47) {
                        if(true) {
                           int var48 = 0 + -1;
                        } else if(false) {
                           if(0 + -1 <= 0) {
                              int var49 = var2.length;
                              if(0 < var49) {
                                 var20 = 0 + 1;
                                 String var50 = var2[0];
                                 String var51 = var3.toString();
                                 Object var55 = var4.put(var50, var51);
                                 if(Mail.MAIL_DETAIL_DEBUG) {
                                    StringBuilder var56 = (new StringBuilder()).append("29 save ");
                                    int var57 = var20 - 1;
                                    String var58 = var2[var57];
                                    StringBuilder var59 = var56.append(var58).append(", value is:");
                                    String var60 = var3.toString();
                                    String var61 = var59.append(var60).toString();
                                    ll.d("IMap4BodyParser", var61);
                                 }
                              } else if(Mail.MAIL_DETAIL_DEBUG) {
                                 StringBuilder var63 = (new StringBuilder()).append("save value is:");
                                 String var64 = var3.toString();
                                 String var65 = var63.append(var64).toString();
                                 ll.d("IMap4BodyParser", var65);
                              }

                              var3 = new StringBuilder();
                              break label209;
                           }
                        } else if(false) {
                           if(Mail.MAIL_DETAIL_DEBUG) {
                              ll.d("IMap4BodyParser", "XXXXX");
                           }
                           break label209;
                        }
                     } else {
                        char var68 = var1.charAt(var5);
                        byte var69 = 32;
                        if(var68 == var69 && true && false) {
                           if(-1 == -1 && var3.toString().length() > 0) {
                              int var70 = var2.length;
                              if(0 < var70) {
                                 var20 = 0 + 1;
                                 String var71 = var2[0];
                                 String var72 = var3.toString();
                                 Object var76 = var4.put(var71, var72);
                                 if(Mail.MAIL_DETAIL_DEBUG) {
                                    StringBuilder var77 = (new StringBuilder()).append("20 save ");
                                    int var78 = var20 - 1;
                                    String var79 = var2[var78];
                                    StringBuilder var80 = var77.append(var79).append(", value is:");
                                    String var81 = var3.toString();
                                    String var82 = var80.append(var81).toString();
                                    ll.d("IMap4BodyParser", var82);
                                 }
                              } else {
                                 if(Mail.MAIL_DETAIL_DEBUG) {
                                    StringBuilder var84 = (new StringBuilder()).append("fieldCount is ");
                                    byte var85 = 0;
                                    StringBuilder var86 = var84.append(var85).append(", keys.length is ");
                                    int var87 = var2.length;
                                    String var88 = var86.append(var87).toString();
                                    ll.d("IMap4BodyParser", var88);
                                 }

                                 if(Mail.MAIL_DETAIL_DEBUG) {
                                    StringBuilder var89 = (new StringBuilder()).append("not save value is:");
                                    String var90 = var3.toString();
                                    String var91 = var89.append(var90).toString();
                                    ll.d("IMap4BodyParser", var91);
                                 }
                              }

                              var3 = new StringBuilder();
                              break label209;
                           }

                           if(-1 == -1 && var3.toString().length() <= 0) {
                              break label209;
                           }
                        }
                     }
                  }
               }

               char var13 = var1.charAt(var5);
               var3.append(var13);
               int var15 = var1.length() - 1;
               if(var5 == var15 && var3.toString().length() > 0 && Mail.MAIL_DETAIL_DEBUG) {
                  StringBuilder var16 = (new StringBuilder()).append("not save value is:");
                  String var17 = var3.toString();
                  String var18 = var16.append(var17).toString();
                  ll.d("IMap4BodyParser", var18);
               }
            }

            ++var5;
         }
      }
   }
}
