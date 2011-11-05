package com.htc.android.mail;

import android.util.Log;
import com.htc.android.mail.Mail;
import com.htc.android.mail.PropertyNode;
import com.htc.android.mail.VCardException;
import com.htc.android.mail.VCardParser;
import com.htc.android.mail.VDataBuilder;
import com.htc.android.mail.VNode;
import com.htc.android.mail.ll;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class Contact extends com.htc.android.mail.eassvc.contact.Contact {

   private static final boolean DEBUG = Mail.MAIL_DEBUG;
   private static final String VBEGIN = "BEGIN:VCARD";
   private static final String VEND = "END:VCARD";
   private final String TAG = "mailContact";
   private String vCard;


   public Contact() {}

   public Contact(String var1) {
      this.init(var1);
   }

   public Contact(byte[] var1) {
      this.init(var1);
   }

   private static String byte2String(byte[] var0) {
      char[] var1 = new char[var0.length];
      int var2 = 0;

      while(true) {
         int var3 = var1.length;
         if(var2 >= var3) {
            CharBuffer var5 = CharBuffer.wrap(var1);
            int var6 = var5.length();
            StringBuffer var7 = new StringBuffer(var6);
            var7.append(var5);
            return var7.toString();
         }

         char var4 = (char)var0[var2];
         var1[var2] = var4;
         ++var2;
      }
   }

   private int getAddressTypeByName(String var1) {
      byte var2;
      if(var1.length() == 0) {
         var2 = 0;
      } else if(var1.equalsIgnoreCase("HOME")) {
         var2 = 0;
      } else if(var1.equalsIgnoreCase("WORK")) {
         var2 = 1;
      } else {
         var2 = 2;
      }

      return var2;
   }

   public static String getRawString(byte[] param0) {
      // $FF: Couldn't be decompiled
   }

   private void init(String var1) {
      int var2 = var1.indexOf("BEGIN:VCARD");
      int var3 = var1.indexOf("END:VCARD");
      if(-1 != var2) {
         if(-1 != var3) {
            int var4 = "END:VCARD".length() + var3;
            String var5 = var1.substring(var2, var4);
            this.vCard = var5;
            VCardParser var6 = new VCardParser();
            VDataBuilder var7 = new VDataBuilder();

            try {
               String var8 = this.vCard;
               var6.parse(var8, var7);
            } catch (VCardException var16) {
               String var12 = var16.toString();
               int var13 = Log.e("VCardException: ", var12);
            } catch (IOException var17) {
               String var14 = var17.toString();
               int var15 = Log.e("VCardException: ", var14);
            }

            Iterator var10 = var7.vNodeList.iterator();

            while(var10.hasNext()) {
               VNode var11 = (VNode)var10.next();
               this.setContactsValue(var11);
            }

         }
      }
   }

   private void init(byte[] var1) {
      String var2 = getRawString(var1);
      this.init(var2);
   }

   public static boolean isNullOrEmpty(String var0) {
      boolean var1;
      if(var0 == null) {
         var1 = true;
      } else if(var0.trim().equals("")) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private String listToString(List<String> var1) {
      StringBuilder var2 = new StringBuilder("");

      String var4;
      StringBuilder var5;
      for(Iterator var3 = var1.iterator(); var3.hasNext(); var5 = var2.append(var4).append(";")) {
         var4 = (String)var3.next();
      }

      String var6 = var2.toString();
      String var8;
      if(var6.endsWith(";")) {
         int var7 = var6.length() - 1;
         var8 = var6.substring(0, var7);
      } else {
         var8 = var6;
      }

      return var8;
   }

   private void setContactsValue(VNode var1) {
      if(DEBUG) {
         String var2 = "setContactsValue>" + var1;
         ll.d("mailContact", var2);
      }

      HashSet var3 = new HashSet();
      Iterator var4 = var1.propList.iterator();

      while(var4.hasNext()) {
         PropertyNode var5 = (PropertyNode)var4.next();
         if(var5.propName.equalsIgnoreCase("TITLE") && var5.propValue != null) {
            String var6 = var5.propValue.replaceAll(";", " ").trim();
            this.title = var6;
            if(DEBUG) {
               StringBuilder var7 = (new StringBuilder()).append("vCard title>");
               String var8 = this.title;
               String var9 = var7.append(var8).toString();
               ll.d("mailContact", var9);
            }
         } else if(var5.propName.equalsIgnoreCase("ORG") && var5.propValue != null) {
            String var10 = var5.propValue.replaceAll(";", " ").trim();
            this.company = var10;
            if(DEBUG) {
               StringBuilder var11 = (new StringBuilder()).append("vCard company>");
               String var12 = this.company;
               String var13 = var11.append(var12).toString();
               ll.d("mailContact", var13);
            }
         } else if(var5.propName.equalsIgnoreCase("FN") && var5.propValue != null) {
            String var14 = var5.propValue;
            this.name = var14;
            if(DEBUG) {
               StringBuilder var15 = (new StringBuilder()).append("vCard name>");
               String var16 = this.name;
               String var17 = var15.append(var16).toString();
               ll.d("mailContact", var17);
            }
         } else if(var5.propName.equalsIgnoreCase("N") && var5.propValue != null) {
            String[] var18 = split(var5.propValue, ";");
            if(var18.length >= 2) {
               String var19 = var18[0];
               this.lastName = var19;
               String var20 = var18[1];
               this.firstName = var20;
            } else if(var18.length == 1) {
               String var26 = var18[0];
               this.lastName = var26;
            }

            if(DEBUG) {
               StringBuilder var21 = (new StringBuilder()).append("vCard lastName>");
               String var22 = this.lastName;
               StringBuilder var23 = var21.append(var22).append(",firstName");
               String var24 = this.firstName;
               String var25 = var23.append(var24).toString();
               ll.d("mailContact", var25);
            }
         } else {
            Iterator var35;
            if(var5.propName.equalsIgnoreCase("TEL") && var5.propValue != null) {
               var3.clear();
               Iterator var27 = var5.paraMap_TYPE.iterator();

               while(var27.hasNext()) {
                  String var28 = ((String)var27.next()).toUpperCase();
                  var3.add(var28);
               }

               if(var3.contains("FAX")) {
                  if(var3.contains("HOME")) {
                     ArrayList var30 = this.phoneHomeFax;
                     String var31 = var5.propValue;
                     var30.add(var31);
                     boolean var33 = var3.remove("HOME");
                  } else if(var3.contains("WORK")) {
                     ArrayList var43 = this.phoneWorkFax;
                     String var44 = var5.propValue;
                     var43.add(var44);
                     boolean var46 = var3.remove("WORK");
                  }

                  boolean var34 = var3.remove("FAX");
               }

               var35 = var3.iterator();

               while(var35.hasNext()) {
                  String var36 = (String)var35.next();
                  if(var36.equals("HOME")) {
                     ArrayList var37 = this.phoneHome;
                     String var38 = var5.propValue;
                     var37.add(var38);
                  } else if(var36.equals("WORK")) {
                     ArrayList var47 = this.phoneWork;
                     String var48 = var5.propValue;
                     var47.add(var48);
                  } else if(var36.equals("PAGER")) {
                     ArrayList var50 = this.phonePager;
                     String var51 = var5.propValue;
                     var50.add(var51);
                  } else if(var36.equals("CELL")) {
                     ArrayList var53 = this.phoneMobile;
                     String var54 = var5.propValue;
                     var53.add(var54);
                  } else if(var36.equals("X-OTHER")) {
                     ArrayList var56 = this.phoneOther;
                     String var57 = var5.propValue;
                     var56.add(var57);
                  }

                  if(DEBUG) {
                     StringBuilder var40 = (new StringBuilder()).append("vCard phone(").append(var36).append(">");
                     String var41 = var5.propValue;
                     String var42 = var40.append(var41).toString();
                     ll.d("mailContact", var42);
                  }
               }
            } else if(var5.propName.equalsIgnoreCase("EMAIL") && var5.propValue != null) {
               var35 = var5.paraMap_TYPE.iterator();

               while(var35.hasNext()) {
                  String var59 = (String)var35.next();
                  if(var59.equalsIgnoreCase("INTERNET") || var59.equalsIgnoreCase("HOME") || var59.equalsIgnoreCase("WORK") || var59.equalsIgnoreCase("X-OTHER")) {
                     ArrayList var60 = this.emailAddress;
                     String var61 = var5.propValue.replaceAll(";", " ").trim();
                     var60.add(var61);
                     if(DEBUG) {
                        StringBuilder var63 = (new StringBuilder()).append("vCard email(").append(var59).append(">");
                        String var64 = var5.propValue;
                        String var65 = var63.append(var64).toString();
                        ll.d("mailContact", var65);
                     }
                  }
               }
            } else if(var5.propName.equalsIgnoreCase("ADR") && var5.propValue != null) {
               ArrayList var66 = var5.paraMap_TYPE;
               String var67 = this.listToString(var66);
               int var68 = this.getAddressTypeByName(var67);
               HashMap var69 = new HashMap();
               String var70 = var5.propValue.replaceAll(";", " ").trim();
               if(var70 == null) {
                  var70 = "";
               }

               var69.put("street", var70);
               this.postalAddrs[var68] = var69;
            } else if(DEBUG) {
               StringBuilder var72 = (new StringBuilder()).append("vCard unknow tag:");
               String var73 = var5.propName;
               StringBuilder var74 = var72.append(var73).append(",value=");
               String var75 = var5.propValue;
               String var76 = var74.append(var75).toString();
               ll.d("mailContact", var76);
            }
         }
      }

   }

   public static String[] split(String var0, String var1) {
      Vector var2 = new Vector(10);
      int var3 = var0.length();

      for(int var4 = 0; var4 < var3; ++var4) {
         char var5 = var0.charAt(var4);
         if(var1.indexOf(var5) != -1) {
            Integer var6 = new Integer(var4);
            var2.addElement(var6);
         }
      }

      int var7 = var2.size();
      String[] var8 = new String[var7 + 1];
      if(var7 == 0) {
         var8[0] = var0;
      } else {
         int var9 = ((Integer)var2.elementAt(0)).intValue();
         String var10 = var0.substring(0, var9);
         var8[0] = var10;

         int var15;
         byte var20;
         for(var20 = 1; var20 < var7; var15 = var20 + 1) {
            int var11 = var20 - 1;
            int var12 = ((Integer)var2.elementAt(var11)).intValue() + 1;
            int var13 = ((Integer)var2.elementAt(var20)).intValue();
            String var14 = var0.substring(var12, var13);
            var8[var20] = var14;
         }

         int var16 = var20 - 1;
         int var17 = ((Integer)var2.elementAt(var16)).intValue() + 1;
         int var18 = var0.length();
         String var19;
         if(var17 < var18) {
            var19 = var0.substring(var17);
         } else {
            var19 = "";
         }

         var8[var20] = var19;
      }

      return var8;
   }

   public String getName() {
      String var1;
      if(this.name == null) {
         var1 = "noname";
      } else {
         var1 = this.name;
      }

      return var1;
   }

   public void setId(String var1) {
      this._id = var1;
   }
}
