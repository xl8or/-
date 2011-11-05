package com.htc.android.mail;

import com.htc.android.mail.Base64;
import com.htc.android.mail.ByteString;
import com.htc.android.mail.DateParser;
import com.htc.android.mail.Mail;
import com.htc.android.mail.Mailaddress;
import com.htc.android.mail.QuotedPrintable;
import com.htc.android.mail.Regex;
import com.htc.android.mail.Util;
import com.htc.android.mail.ll;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Headers {

   private static final boolean DEBUG = Mail.MAIL_DEBUG;
   public static final int SUBJECT_TYPE_FORWARD = 2;
   public static final int SUBJECT_TYPE_NONE = 0;
   public static final int SUBJECT_TYPE_REPLY = 1;
   private static final String TAG = "Headers";
   static final Pattern mailaddress = Pattern.compile("(.*)@(.*)");
   public static final Pattern sFromP = Pattern.compile("^(.*)<(.*)>.*$");
   public static final Pattern sFromP2 = Pattern.compile("^<(.*)@(.*)>$");
   public static final Pattern sFromP3 = Pattern.compile("(.*)<(.*)@(.*)>(.*)");


   public Headers() {}

   private static String AddMailaddress(StringBuilder var0, String var1) {
      while(true) {
         if(var1.length() > 0) {
            int var2 = var1.indexOf(", ");
            if(var2 < 0) {
               var2 = var1.indexOf("; ");
               if(var2 < 0) {
                  return var1;
               }
            }

            String var3 = var1.substring(0, var2);
            if(mailaddress.matcher(var3).matches()) {
               StringBuilder var4 = new StringBuilder();
               String var5 = rfc2047(var3.trim()).trim();
               String var6 = var4.append(var5).append(", ").toString();
               var0.append(var6);
               int var8 = var2 + 1;
               var1 = var1.substring(var8);
               continue;
            }
         }

         return var1;
      }
   }

   private static void AddMatchNameAndMailaddress(StringBuilder var0, Matcher var1) {
      String var2 = var1.group(1);
      String var3 = AddMailaddress(var0, var2);
      if(var3 != null && var3.length() > 0) {
         while(var3 != null && var3.length() >= 2) {
            var3 = var3.trim();
            if(var3.charAt(0) != 34) {
               break;
            }

            int var4 = var3.length() - 1;
            if(var3.charAt(var4) != 34) {
               break;
            }

            if(var3.length() <= 2) {
               var3 = null;
            } else {
               int var5 = var3.length() - 1;
               var3 = var3.substring(1, var5);
            }
         }
      }

      String var6 = var1.group(2);
      String var7 = var1.group(3);
      if(var3 != null && var3.length() > 0) {
         StringBuilder var8 = new StringBuilder();
         String var9 = var3.trim();
         String var10 = var8.append(var9).append(" ").toString();
         var0.append(var10);
         StringBuilder var12 = (new StringBuilder()).append("<");
         String var13 = var6.trim();
         StringBuilder var14 = var12.append(var13).append("@");
         String var15 = var7.trim();
         String var16 = var14.append(var15).append(">, ").toString();
         var0.append(var16);
      } else {
         StringBuilder var18 = new StringBuilder();
         String var19 = var6.trim();
         StringBuilder var20 = var18.append(var19).append("@");
         String var21 = var7.trim();
         String var22 = var20.append(var21).append(", ").toString();
         var0.append(var22);
      }
   }

   public static String AddressArrayToString(ArrayList<Mailaddress> var0) {
      StringBuilder var1 = new StringBuilder();
      String var2;
      if(var0 == null) {
         var2 = "";
      } else {
         int var3 = 0;

         while(true) {
            int var4 = var0.size();
            if(var3 >= var4) {
               var2 = var1.toString();
               break;
            }

            if(((Mailaddress)var0.get(var3)).mDisplayName != null && !((Mailaddress)var0.get(var3)).mDisplayName.equals("")) {
               if(var1.toString().length() == 0) {
                  StringBuilder var11 = var1.append("\"");
                  String var12 = ((Mailaddress)var0.get(var3)).mDisplayName;
                  StringBuilder var13 = var11.append(var12).append("\" <");
                  String var14 = ((Mailaddress)var0.get(var3)).mEmail.trim();
                  StringBuilder var15 = var13.append(var14).append(">");
               } else {
                  StringBuilder var16 = var1.append(", \"");
                  String var17 = ((Mailaddress)var0.get(var3)).mDisplayName;
                  StringBuilder var18 = var16.append(var17).append("\" <");
                  String var19 = ((Mailaddress)var0.get(var3)).mEmail.trim();
                  StringBuilder var20 = var18.append(var19).append(">");
               }
            } else if(var1.toString().length() == 0) {
               StringBuilder var5 = var1.append("<");
               String var6 = ((Mailaddress)var0.get(var3)).mEmail.trim();
               StringBuilder var7 = var5.append(var6).append(">");
            } else {
               StringBuilder var8 = var1.append(", <");
               String var9 = ((Mailaddress)var0.get(var3)).mEmail.trim();
               StringBuilder var10 = var8.append(var9).append(">");
            }

            ++var3;
         }
      }

      return var2;
   }

   public static final String ParseMailAddress(String var0) {
      StringBuilder var1 = new StringBuilder();
      int var2 = -1;
      int var3 = -1;

      while(true) {
         Matcher var4 = Regex.getInstance().getPattern(4).matcher(var0);
         if(!var4.find()) {
            break;
         }

         String var5 = rfc2047(var4.group());
         String var6 = var4.group();
         var0 = var0.replace(var6, var5);
         if(var4.start() == var3 || var4.end() == var2) {
            break;
         }

         var3 = var4.start();
         var2 = var4.end();
      }

      if(var0.length() > 0) {
         String var7 = var0.trim().replace('\t', ' ').replace('\r', ' ');
         int var8 = 0;
         int var9 = 0;

         while(true) {
            String var10 = "; ";
            if(var8 <= 0) {
               var8 = 0;
            }

            int var11 = var7.indexOf(var10, var8);
            if(var11 == -1) {
               String var12 = ">,";
               if(var11 > 0) {
                  ;
               }

               var7.indexOf(var12, var11);
            }

            if(var11 == -1) {
               String var14 = "> ";
               if(var11 > 0) {
                  ;
               }

               var7.indexOf(var14, var11);
            }

            int var16 = var7.length();
            if(var9 == var16 && DEBUG) {
               ll.d("Headers", "Can\'t get more correct recipients");
            }

            int var17 = var7.length();
            if(var11 == -1) {
               var0 = ParseNameAndMailaddress(var1, var7, var11);
               if(var0.length() > 0) {
                  AddMailaddress(var1, var0);
               }
               break;
            }

            var7 = ParseNameAndMailaddress(var1, var7, var11);
            if(var7.length() <= 0) {
               break;
            }

            var9 = var17;
            var8 = var11;
         }

         if(var0.length() > 0) {
            String var19 = var0.trim();
            var1.append(var19);
         }

         StringBuilder var21 = (new StringBuilder()).append("Result = ");
         String var22 = var1.toString();
         String var23 = var21.append(var22).toString();
         ll.d("HeaderParser", var23);
      } else {
         boolean var24 = false;
      }

      return var1.toString();
   }

   private static String ParseNameAndMailaddress(StringBuilder var0, String var1, int var2) {
      int var3 = 0;

      while(true) {
         int var4 = var1.length();
         if(var2 > var4) {
            break;
         }

         int var5 = var3 + 1;
         var2 = var1.indexOf(62, var5);
         if(var2 < 0) {
            break;
         }

         int var6 = var1.indexOf(60, var3);
         if(var2 <= var6) {
            var3 = var2;
         } else {
            int var7 = var2 + 1;
            String var8 = var1.substring(0, var7);
            Matcher var9 = sFromP3.matcher(var8);
            if(var9.matches()) {
               label41: {
                  if(var1.length() > var2) {
                     label39: {
                        int var10 = var1.indexOf(44, var2);
                        int var11 = var2 + 1;
                        if(var10 != var11) {
                           int var12 = var1.indexOf(59, var2);
                           int var13 = var2 + 1;
                           if(var12 != var13) {
                              int var14 = var1.indexOf(32, var2);
                              int var15 = var2 + 1;
                              if(var14 != var15) {
                                 break label39;
                              }
                           }
                        }

                        int var16 = var2 + 2;
                        var1 = var1.substring(var16);
                        break label41;
                     }
                  }

                  int var17 = var2 + 1;
                  var1 = var1.substring(var17);
               }

               var2 = 0;
               var3 = 0;
               AddMatchNameAndMailaddress(var0, var9);
            } else if(!sFromP.matcher(var8).matches()) {
               var3 = var2;
            } else {
               label50: {
                  if(var1.length() > var2) {
                     label48: {
                        int var18 = var1.indexOf(44, var2);
                        int var19 = var2 + 1;
                        if(var18 != var19) {
                           int var20 = var1.indexOf(59, var2);
                           int var21 = var2 + 1;
                           if(var20 != var21) {
                              int var22 = var1.indexOf(32, var2);
                              int var23 = var2 + 1;
                              if(var22 != var23) {
                                 break label48;
                              }
                           }
                        }

                        int var24 = var2 + 2;
                        var1 = var1.substring(var24);
                        break label50;
                     }
                  }

                  int var29 = var2 + 1;
                  var1 = var1.substring(var29);
               }

               var2 = 0;
               var3 = 0;
               StringBuilder var25 = new StringBuilder();
               String var26 = var8.trim();
               String var27 = var25.append(var26).append(", ").toString();
               var0.append(var27);
            }
         }
      }

      return var1;
   }

   public static final void extract(ArrayList<ByteString> var0, int var1, int var2, HashMap<String, String> var3) {
      for(int var4 = var1; var4 < var2; ++var4) {
         String[] var5 = ((ByteString)var0.get(var4)).toString("ISO8859-1").split(":", 2);
         if(var5.length >= 2) {
            String var6 = var5[0].trim();
            String var7 = var5[1].trim();
            if(Mail.MAIL_DETAIL_DEBUG) {
               String var8 = "check value:" + var7;
               ll.d("Headers", var8);
            }

            if(var3.containsKey("received") && var6.equals("Received")) {
               if(Mail.MAIL_DETAIL_DEBUG) {
                  String var13 = "none save key=" + var6 + ",value=" + var7;
                  ll.d("Headers", var13);
               }
            } else {
               if(Mail.MAIL_DETAIL_DEBUG) {
                  String var9 = "key=" + var6 + ",value=" + var7;
                  ll.d("Headers", var9);
               }

               Locale var10 = Locale.US;
               String var11 = var6.toLowerCase(var10);
               var3.put(var11, var7);
            }
         }
      }

      process(var3);
   }

   public static final void extract(ArrayList<ByteString> var0, int var1, HashMap<String, String> var2) {
      extract(var0, 0, var1, var2);
   }

   static final String getAttribute(String var0, String var1, String var2) {
      int var3 = var1.length();
      Locale var4 = Locale.US;
      String var5 = var0.toLowerCase(var4);
      int var6 = var5.indexOf(var1);
      String var7;
      if(var6 == -1) {
         var7 = var2;
      } else {
         int var8 = var6 + var3;
         int var9 = var5.indexOf(";", var8);
         if(var9 == -1) {
            var9 = var5.length();
         }

         int var10 = var6 + var3;
         int var11 = var5.indexOf("=", var10);
         if(var11 == -1) {
            var7 = var2;
         } else {
            int var12 = var11 + 1;
            String var13 = var5.substring(var12, var9).trim();
            byte var14 = 0;
            boolean var15 = false;
            if(var13.charAt(0) == 34) {
               var15 = true;
               var14 = 1;
            }

            if(var15) {
               int var16 = var13.length() - 1;
               if(var13.charAt(var16) == 34) {
                  int var17 = var13.length() - 1;
                  var7 = var13.substring(var14, var17);
                  return var7;
               }
            }

            var7 = var13;
         }
      }

      return var7;
   }

   static final String getAttributeCaseSens(String var0, String var1, String var2) {
      int var3 = var1.length();
      Locale var5 = Locale.US;
      String var6 = var0.toLowerCase(var5);
      int var7 = var6.indexOf(var1);
      if(var7 == -1) {
         var6 = var2;
      } else {
         int var8 = var7 + var3;
         int var9 = var6.indexOf(";", var8);
         if(var9 == -1) {
            int var10 = var6.length();
         }

         int var11 = var3 + var7;
         int var12 = var6.indexOf("=", var11);
         if(var12 == -1) {
            var6 = var2;
         } else {
            int var13 = var12 + 1;
            var2 = var0.substring(var13, var9).trim();
            byte var17 = 0;
            boolean var14 = false;
            if(var2.charAt(0) == 34) {
               var14 = true;
            }

            if(var14) {
               int var15 = var2.length() - 1;
               if(var2.charAt(var15) == 34) {
                  int var16 = var2.length() - 1;
                  var6 = var2.substring(var17, var16);
                  return var6;
               }
            }

            var6 = var2;
         }
      }

      return var6;
   }

   static final String getBase(String var0) {
      int var1 = var0.indexOf(";");
      String var3;
      if(var1 == -1) {
         Locale var2 = Locale.US;
         var3 = var0.toLowerCase(var2);
      } else {
         String var4 = var0.substring(0, var1);
         Locale var5 = Locale.US;
         var3 = var4.toLowerCase(var5);
      }

      return var3;
   }

   public static String getCharsetFromHeader(String var0) {
      Matcher var1 = Regex.getInstance().getPattern(4).matcher(var0);
      String var2;
      if(var1.find(0)) {
         var2 = var1.group(1);
      } else {
         var2 = null;
      }

      return var2;
   }

   static final String getReceivedAttribute(String var0) {
      String var1;
      if(var0 == null) {
         var1 = null;
      } else {
         if(DEBUG) {
            String var2 = "****** POP receive date:" + var0;
            ll.d("Headers", var2);
         }

         String[] var3 = var0.split(";");
         if(var3 != null && var3.length > 1) {
            int var4 = var3.length - 1;
            String var5 = var3[var4].trim();
            Calendar var6 = DateParser.parse(var5);
            if(DEBUG) {
               String var7 = "****** POP receive date:" + var5;
               ll.d("Headers", var7);
            }

            var1 = var5;
         } else {
            var1 = null;
         }
      }

      return var1;
   }

   public static HashMap<String, String> parseFromAddress(String var0, String var1, String var2) {
      HashMap var10;
      if(var0 != null) {
         HashMap var3 = new HashMap();
         Matcher var4 = sFromP.matcher(var0);
         String var5;
         String var6;
         if(var4.matches()) {
            var5 = rfc2047(var4.group(1), var1, var2);
            var6 = rfc2047(var4.group(2));
            if(var5.trim().length() == 0) {
               var5 = var6.split("@")[0];
            } else {
               var5 = var5.replace("\"", "");
            }

            var3.put("from", var5);
            String var8 = rfc2047(var4.group(2));
            var3.put("fromEmail", var8);
            var10 = var3;
         } else {
            var0 = rfc2047(var0);
            if(Mail.MAIL_DETAIL_DEBUG) {
               String var11 = "from:" + var0;
               ll.d("Headers", var11);
            }

            Matcher var12 = sFromP.matcher(var0);
            if(var12.matches()) {
               String var13 = var12.group(1).replace("\"", "");
               var6 = var12.group(2);
               if(var13.trim().length() == 0) {
                  var5 = var6.split("@")[0];
               } else {
                  var5 = var13.replace("\"", "");
               }
            } else if(var0.length() == 0) {
               var5 = "none";
               var6 = "none";
            } else if(var0.startsWith("<")) {
               var5 = null;
               var6 = var0.replace("<", "").replace(">", "");
            } else if(var0.contains("@")) {
               var5 = null;
               var6 = var0;
            } else {
               var5 = var0;
               var6 = var0;
            }

            var3.put("from", var5);
            var3.put("fromEmail", var6);
            var10 = var3;
         }
      } else {
         var10 = null;
      }

      return var10;
   }

   static final void process(HashMap<String, String> var0) {
      String var1 = (String)var0.get("content-type");
      String var2 = Mail.getDefaultCharset();
      if(var1 == null) {
         Object var3 = var0.put("content-type", "text/plain; charset=ISO8859-1");
      } else {
         String var32 = Mail.getDefaultCharset();
         var2 = getAttribute(var1, "charset", var32);
      }

      String var4 = (String)var0.get("from");
      if(var4 != null) {
         Matcher var5 = sFromP.matcher(var4);
         String var6;
         if(var5.matches()) {
            var6 = rfc2047(var5.group(1), var2);
            String var7 = rfc2047(var5.group(2));
            String var8 = "from";
            String var9;
            if(var6.trim().length() == 0) {
               var9 = "";
            } else {
               var9 = var6;
            }

            var0.put(var8, var9);
            String var11 = rfc2047(var5.group(2));
            var0.put("fromEmail", var11);
         } else {
            var4 = rfc2047(var4);
            if(Mail.MAIL_DETAIL_DEBUG) {
               String var33 = "from:" + var4;
               ll.d("ROY", var33);
            }

            Matcher var34 = sFromP.matcher(var4);
            String var35;
            if(var34.matches()) {
               var6 = var34.group(1);
               var35 = var34.group(2);
            } else if(var4.length() == 0) {
               var6 = "none";
               var35 = "none";
            } else if(var4.startsWith("<")) {
               var6 = "";
               var35 = var4.replace("<", "").replace(">", "");
            } else if(var4.contains("@")) {
               var6 = "";
               var35 = var4;
            } else {
               var6 = var4;
               var35 = var4;
            }

            String var36 = "from";
            String var37;
            if(var6.trim().length() == 0) {
               var37 = "";
            } else {
               var37 = var6;
            }

            var0.put(var36, var37);
            var0.put("fromEmail", var35);
         }
      }

      String var13 = (String)var0.get("subject");
      if(var13 != null) {
         String var14 = rfc2047(var13, var2);
         var0.put("subject", var14);
         String var16 = getCharsetFromHeader(var13);
         var0.put("subjectCharset", var16);
      }

      String var18 = (String)var0.get("cc");
      if(var18 != null) {
         String var19 = var18.replace("\\\"", "");
         if(var19.startsWith("=?") && var19.endsWith("?=")) {
            var19 = rfc2047(var19);
         }

         String var20 = AddressArrayToString(splitMailAddress(var19, (boolean)1, var2));
         var0.put("cc", var20);
      }

      String var22 = (String)var0.get("to");
      if(var22 != null) {
         String var23 = var22.replace("\\\"", "");
         if(var23.startsWith("=?") && var23.endsWith("?=")) {
            var23 = rfc2047(var23);
         }

         String var24 = AddressArrayToString(splitMailAddress(var23, (boolean)1, var2));
         var0.put("to", var24);
      }

      String var26 = (String)var0.get("reply-to");
      if(var26 != null) {
         String var27 = var26.replace("\\\"", "");
         if(var27.startsWith("=?") && var27.endsWith("?=")) {
            var27 = rfc2047(var27);
         }

         String var28 = AddressArrayToString(splitMailAddress(var27, (boolean)1, var2));
         var0.put("reply-to", var28);
      }

      String var30 = (String)var0.get("importance");
      var0.put("importance", var30);
   }

   public static final String rfc2047(String var0) {
      String var1 = Mail.getDefaultCharset();
      return rfc2047(var0, var1);
   }

   public static final String rfc2047(String var0, String var1) {
      return rfc2047(var0, var1, "ISO8859-1");
   }

   public static final String rfc2047(String var0, String var1, String var2) {
      if(var0 == null) {
         var0 = "";
      } else {
         if(var1 == null) {
            var1 = Mail.getDefaultCharset();
         }

         var0 = var0.trim();
         if(var0.length() >= 2 && var0.startsWith("\"") && var0.endsWith("\"")) {
            int var3 = var0.length() - 1;
            var0.substring(1, var3);
         }

         Matcher var5 = Regex.getInstance().getPattern(4).matcher(var0);
         boolean var6 = var5.find(0);
         if(!var6) {
            int var7 = 0;

            while(true) {
               int var8 = var0.length();
               if(var7 >= var8) {
                  break;
               }

               if(var0.charAt(var7) > 126) {
                  String var44;
                  try {
                     byte[] var9 = var0.getBytes(var2);
                     var44 = new String(var9, var1);
                  } catch (UnsupportedEncodingException var43) {
                     break;
                  }

                  var0 = var44;
                  break;
               }

               ++var7;
            }
         } else {
            StringBuilder var10 = new StringBuilder();
            int var11 = 0;
            ArrayList var12 = new ArrayList(1);
            byte var13 = 0;
            boolean var14 = var6;
            int var15 = var13;

            while(true) {
               if(!var14) {
                  break;
               }

               int var16 = var15 + 1;
               if(var16 > 20) {
                  break;
               }

               int var21 = var5.start();
               int var22 = var5.end();
               if(var0.substring(var11, var21).trim().length() != 0) {
                  String var34 = var0.substring(var11, var21);
                  var10.append(var34);
               }

               String var23 = var5.group(1);
               String var24 = var5.group(2);
               String var25 = var5.group(3);
               if(var24.equalsIgnoreCase("q")) {
                  var25 = var25.replace('_', ' ');
                  ByteString var26 = new ByteString(var25);
                  var12.add(var26);
                  QuotedPrintable.decode(var12, 0, 1, (String)null);
                  String var28 = Util.linesToString(var12, 0, 1, var23);
                  String var29;
                  if(var23.compareToIgnoreCase("BIG5") == 0) {
                     var29 = Util.FixBig5UnicodeForJP(var28);
                  } else {
                     var29 = var28;
                  }

                  Object var30 = var12.remove(0);
                  var10.append(var29);
               } else if(var24.equalsIgnoreCase("b")) {
                  String var37;
                  label63: {
                     String var36;
                     try {
                        var36 = Base64.decodeToString(var25, var23);
                     } catch (UnsupportedEncodingException var42) {
                        var42.printStackTrace();
                        var37 = "";
                        break label63;
                     }

                     var37 = var36;
                  }

                  if(var23.compareToIgnoreCase("BIG5") == 0) {
                     var23 = Util.FixBig5UnicodeForJP(var37);
                  } else {
                     var23 = var37;
                  }

                  var10.append(var23);
               }

               var14 = var5.find(var22);
               var11 = var22;
               var15 = var16;
            }

            if(var0.length() - var11 > 1) {
               int var18 = var0.length();
               String var19 = var0.substring(var11, var18);
               var10.append(var19);
            }

            var0 = var10.toString();
         }
      }

      return var0;
   }

   public static final ArrayList<Mailaddress> splitMailAddress(String var0, boolean var1) {
      String var2 = Mail.getDefaultCharset();
      return splitMailAddress(var0, var1, var2, "ISO8859-1");
   }

   public static final ArrayList<Mailaddress> splitMailAddress(String var0, boolean var1, String var2) {
      return splitMailAddress(var0, var1, var2, "ISO8859-1");
   }

   public static final ArrayList<Mailaddress> splitMailAddress(String var0, boolean var1, String var2, String var3) {
      if(var2 == null) {
         var2 = Mail.getDefaultCharset();
      }

      ArrayList var4 = new ArrayList();
      Mailaddress var6;
      if(var0 != null) {
         for(Matcher var5 = Regex.getInstance().getPattern(13).matcher(var0); var5.find(); var4.add(var6)) {
            var6 = new Mailaddress();
            String var7 = var5.group(1);
            String var8 = var5.group(2);
            String var9 = var5.group(3);
            if(var8 != null) {
               var6.mEmail = var8;
            } else {
               var6.mEmail = var9;
            }

            if(var7 == null) {
               var7 = "";
            }

            String var10 = var7.trim();
            if(var10.startsWith("\"") && var10.endsWith("\"")) {
               int var11 = var10.length() - 1;
               var10 = var10.substring(1, var11);
            }

            if(!var1) {
               var6.mDisplayName = var10;
            } else if(var3 == null) {
               String var13 = rfc2047(var10, var2);
               var6.mDisplayName = var13;
            } else {
               String var14 = rfc2047(var10, var2, var3);
               var6.mDisplayName = var14;
            }
         }
      }

      return var4;
   }

   public static final ArrayList<Mailaddress> splitMailAddressFor2003(String var0, boolean var1, String var2, String var3) {
      if(var2 == null) {
         var2 = Mail.getDefaultCharset();
      }

      ArrayList var4 = new ArrayList();
      Mailaddress var6;
      if(var0 != null) {
         for(Matcher var5 = Regex.getInstance().getPattern(14).matcher(var0); var5.find(); var4.add(var6)) {
            var6 = new Mailaddress();
            String var7 = var5.group(1);
            String var8 = var5.group(2);
            String var9 = var5.group(3);
            if(var8 != null) {
               var6.mEmail = var8;
            } else {
               var6.mEmail = var9;
            }

            if(var7 == null) {
               var7 = "";
            }

            String var10 = var7.trim();
            if(var10.startsWith("\"") && var10.endsWith("\"")) {
               int var11 = var10.length() - 1;
               var10 = var10.substring(1, var11);
            }

            if(!var1) {
               var6.mDisplayName = var10;
            } else if(var3 == null) {
               String var13 = rfc2047(var10, var2);
               var6.mDisplayName = var13;
            } else {
               String var14 = rfc2047(var10, var2, var3);
               var6.mDisplayName = var14;
            }
         }
      }

      return var4;
   }
}
