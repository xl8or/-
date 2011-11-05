package javax.mail.internet;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.mail.Address;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.MimeUtility;

public class InternetAddress extends Address implements Cloneable {

   private static final int LAX = 1;
   private static final int NONE = 0;
   private static final String RFC822 = "rfc822";
   private static final int STRICT = 2;
   private static final int STRICT_OR_LAX = 3;
   private static final String needsQuoting = "()<>@,;:\\\".[]";
   protected String address;
   protected String encodedPersonal;
   protected String personal;


   public InternetAddress() {}

   public InternetAddress(String var1) throws AddressException {
      this(var1, (boolean)0);
   }

   public InternetAddress(String var1, String var2) throws UnsupportedEncodingException {
      this(var1, var2, (String)null);
   }

   public InternetAddress(String var1, String var2, String var3) throws UnsupportedEncodingException {
      this.address = var1;
      this.setPersonal(var2, var3);
   }

   public InternetAddress(String var1, boolean var2) throws AddressException {
      InternetAddress[] var3 = parseHeader(var1, var2);
      if(var3.length != 1) {
         throw new AddressException("Illegal address", var1);
      } else {
         String var4 = var3[0].address;
         this.address = var4;
         String var5 = var3[0].personal;
         this.personal = var5;
         String var6 = var3[0].encodedPersonal;
         this.encodedPersonal = var6;
         if(var2) {
            validate(var1, (boolean)1, (boolean)1);
         }
      }
   }

   public static InternetAddress getLocalAddress(Session param0) {
      // $FF: Couldn't be decompiled
   }

   private static boolean isGroupAddress(String var0) {
      int var1 = var0.length();
      boolean var3;
      if(var1 > 0 && var0.indexOf(58) > 0) {
         int var2 = var1 - 1;
         if(var0.charAt(var2) == 59) {
            var3 = true;
            return var3;
         }
      }

      var3 = false;
      return var3;
   }

   private static boolean isSimpleAddress(String var0) {
      boolean var1;
      if(var0.indexOf(34) == -1 && var0.indexOf(40) == -1 && var0.indexOf(41) == -1 && var0.indexOf(44) == -1 && var0.indexOf(58) == -1 && var0.indexOf(59) == -1 && var0.indexOf(60) == -1 && var0.indexOf(62) == -1 && var0.indexOf(91) == -1 && var0.indexOf(92) == -1 && var0.indexOf(93) == -1) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static InternetAddress[] parse(String var0) throws AddressException {
      return parse(var0, (boolean)1);
   }

   private static InternetAddress[] parse(String var0, int var1) throws AddressException {
      int var2 = var0.length();
      ArrayList var3 = new ArrayList();
      byte var4 = 0;
      int var5 = var4;
      byte var6 = -1;
      boolean var7 = false;
      int var8 = var6;
      byte var9 = -1;
      int var10 = -1;
      int var11 = var9;
      byte var12 = 0;
      int var13 = -1;

      byte var14;
      for(var14 = var12; var5 < var2; ++var5) {
         int var17 = var0.charAt(var5);
         boolean var67;
         switch(var17) {
         case 9:
         case 10:
         case 13:
         case 32:
            continue;
         case 34:
            var7 = true;
            if(var10 == -1) {
               var10 = var5;
            }

            int var65 = var5 + 1;
            boolean var66 = false;
            var17 = var65;

            int var70;
            for(var67 = var66; var17 < var2 && !var67; var70 = var17 + 1) {
               switch(var0.charAt(var17)) {
               case 34:
                  var67 = true;
                  int var71 = var17 + -1;
                  break;
               case 92:
                  int var72 = var17 + 1;
               }
            }

            if(var17 >= var2) {
               AddressException var73 = new AddressException;
               String var75 = "Unmatched \'\"\'";
               var73.<init>(var75, var0, var17);
               throw var73;
            }
            break;
         case 40:
            var7 = true;
            if(var10 >= 0 && var11 == -1) {
               var11 = var5;
            }

            if(var8 == -1) {
               var8 = var5 + 1;
            }

            int var49 = var5 + 1;
            byte var50 = 1;
            int var51 = var49;

            int var52;
            for(var52 = var50; var51 < var2 && var52 > 0; ++var51) {
               switch(var0.charAt(var51)) {
               case 40:
                  ++var52;
                  break;
               case 41:
                  var52 += -1;
                  break;
               case 92:
                  ++var51;
               }
            }

            if(var52 > 0) {
               AddressException var55 = new AddressException;
               String var57 = "Unmatched \'(\'";
               var55.<init>(var57, var0, var51);
               throw var55;
            }

            var5 = var51 + -1;
            if(var13 == -1) {
               var13 = var5;
            }
            continue;
         case 41:
            AddressException var60 = new AddressException;
            String var62 = "Unmatched \')\'";
            var60.<init>(var62, var0, var5);
            throw var60;
         case 44:
            if(var10 == -1) {
               var7 = false;
               byte var91 = -1;
               var14 = 0;
               var11 = var91;
            } else if(true) {
               if(var11 == -1) {
                  var11 = var5;
               }

               String var95 = var0.substring(var10, var11).trim();
               if(!var7 && (var1 | 3) == 0) {
                  StringTokenizer var157 = new StringTokenizer(var95);

                  while(var157.hasMoreTokens()) {
                     String var103 = var157.nextToken();
                     validate(var103, (boolean)0, (boolean)0);
                     InternetAddress var104 = new InternetAddress();
                     var104.setAddress(var103);
                     var3.add(var104);
                  }
               } else {
                  if((var1 & 2) != 0 || (var1 & 1) == 0) {
                     validate(var95, (boolean)var14, (boolean)0);
                  }

                  InternetAddress var96 = new InternetAddress();
                  var96.setAddress(var95);
                  if(var8 >= 0) {
                     String var100 = unquote(var0.substring(var8, var13).trim());
                     var96.encodedPersonal = var100;
                     var13 = -1;
                     var8 = var13;
                  }

                  var3.add(var96);
               }

               byte var102 = -1;
               var7 = false;
               var11 = -1;
               var14 = 0;
               var10 = var102;
            }
            continue;
         case 58:
            var7 = true;
            if(false) {
               AddressException var106 = new AddressException;
               String var108 = "Cannot have nested group";
               var106.<init>(var108, var0, var5);
               throw var106;
            }
            continue;
         case 59:
            if(true) {
               AddressException var111 = new AddressException;
               String var113 = "Unexpected \';\'";
               var111.<init>(var113, var0, var5);
               throw var111;
            }

            int var116 = var5 + 1;
            String var120 = var0.substring(var10, var116).trim();
            InternetAddress var121 = new InternetAddress();
            var121.setAddress(var120);
            var3.add(var121);
            boolean var123 = false;
            var11 = -1;
            byte var124 = 0;
            var10 = -1;
            var14 = var124;
            continue;
         case 60:
            boolean var18 = true;
            if(var14 != 0) {
               AddressException var19 = new AddressException;
               String var21 = "Too many route-addr";
               var19.<init>(var21, var0, var5);
               throw var19;
            }

            if(true) {
               if(var10 >= 0) {
                  var13 = var5;
               }

               int var24 = var5 + 1;
               int var25 = var10;
               var10 = var24;
               var8 = var25;
            }

            int var26 = var5 + 1;
            boolean var27 = false;
            boolean var28 = false;
            int var29 = var26;

            boolean var30;
            for(var30 = var28; var29 < var2 && !var30; ++var29) {
               switch(var0.charAt(var29)) {
               case 34:
                  if(!var27) {
                     var27 = true;
                  } else {
                     var27 = false;
                  }
                  break;
               case 62:
                  if(!var27) {
                     var30 = true;
                     var29 += -1;
                  }
                  break;
               case 92:
                  ++var29;
               }
            }

            if(!var30 && var29 >= var2) {
               if(var27) {
                  AddressException var33 = new AddressException;
                  String var35 = "Unmatched \'\"\'";
                  var33.<init>(var35, var0, var29);
                  throw var33;
               }

               AddressException var38 = new AddressException;
               String var40 = "Unmatched \'<\'";
               var38.<init>(var40, var0, var29);
               throw var38;
            }

            var7 = var18;
            var11 = var29;
            var14 = 1;
            var5 = var29;
            continue;
         case 62:
            AddressException var44 = new AddressException;
            String var46 = "Unmatched \'>\'";
            var44.<init>(var46, var0, var5);
            throw var44;
         case 91:
            int var78 = var5 + 1;
            boolean var79 = false;
            int var80 = var78;

            int var83;
            for(var67 = var79; var80 < var2 && !var67; var83 = var80 + 1) {
               switch(var0.charAt(var80)) {
               case 92:
                  int var85 = var80 + 1;
                  break;
               case 93:
                  int var84 = var80 + -1;
               }
            }

            if(var80 >= var2) {
               AddressException var86 = new AddressException;
               String var88 = "Unmatched \'[\'";
               var86.<init>(var88, var0, var80);
               throw var86;
            }
            break;
         default:
            if(var10 == -1) {
               var10 = var5;
            }
            continue;
         }

         var5 = var17;
      }

      if(var10 > -1) {
         if(var11 != -1) {
            var5 = var11;
         }

         String var156 = var0.substring(var10, var5).trim();
         if(!var7 && (var1 | 3) == 0) {
            StringTokenizer var154 = new StringTokenizer(var156);

            while(var154.hasMoreTokens()) {
               String var146 = var154.nextToken();
               byte var148 = 0;
               byte var149 = 0;
               validate(var146, (boolean)var148, (boolean)var149);
               InternetAddress var150 = new InternetAddress();
               var150.setAddress(var146);
               var3.add(var150);
            }
         } else {
            if((var1 & 2) != 0 || (var1 & 1) == 0) {
               byte var130 = 0;
               validate(var156, (boolean)var14, (boolean)var130);
            }

            InternetAddress var155 = new InternetAddress();
            var155.setAddress(var156);
            if(var8 >= 0) {
               String var136 = unquote(var0.substring(var8, var13).trim());
               var155.encodedPersonal = var136;
            }

            boolean var139 = var3.add(var155);
         }
      }

      InternetAddress[] var140 = new InternetAddress[var3.size()];
      Object[] var143 = var3.toArray(var140);
      return var140;
   }

   public static InternetAddress[] parse(String var0, boolean var1) throws AddressException {
      byte var2;
      if(var1) {
         var2 = 2;
      } else {
         var2 = 0;
      }

      return parse(var0, var2);
   }

   public static InternetAddress[] parseHeader(String var0, boolean var1) throws AddressException {
      byte var2;
      if(var1) {
         var2 = 3;
      } else {
         var2 = 1;
      }

      return parse(var0, var2);
   }

   private static String quote(String var0) {
      int var1 = var0.length();
      int var2 = 0;
      boolean var3 = false;

      String var16;
      while(true) {
         if(var2 >= var1) {
            if(var3) {
               int var11 = var1 + 2;
               StringBuffer var12 = new StringBuffer(var11);
               StringBuffer var13 = var12.append('\"');
               var12.append(var0);
               StringBuffer var15 = var12.append('\"');
               var16 = var12.toString();
            } else {
               var16 = var0;
            }
            break;
         }

         char var4 = var0.charAt(var2);
         if(var4 == 34 || var4 == 92) {
            int var5 = var1 + 3;
            StringBuffer var17 = new StringBuffer(var5);
            StringBuffer var6 = var17.append('\"');

            int var9;
            for(byte var18 = 0; var18 < var1; var9 = var18 + 1) {
               var4 = var0.charAt(var18);
               if(var4 == 34 || var4 == 92) {
                  StringBuffer var7 = var17.append('\\');
               }

               var17.append(var4);
            }

            StringBuffer var10 = var17.append('\"');
            var16 = var17.toString();
            break;
         }

         if(var4 < 32 && var4 != 13 && var4 != 10 && var4 != 9 || var4 >= 127 || "()<>@,;:\\\".[]".indexOf(var4) > -1) {
            var3 = true;
         }

         ++var2;
      }

      return var16;
   }

   public static String toString(Address[] var0) {
      return toString(var0, 0);
   }

   public static String toString(Address[] var0, int var1) {
      String var2;
      if(var0 != null && var0.length != 0) {
         String var3 = "\r\n";
         StringBuffer var4 = new StringBuffer();
         int var5 = 0;
         int var6 = var1;

         while(true) {
            int var7 = var0.length;
            if(var5 >= var7) {
               var2 = var4.toString();
               break;
            }

            if(var5 != 0) {
               StringBuffer var8 = var4.append(", ");
               var6 += 2;
            }

            String var9 = var0[var5].toString();
            int var10 = var9.length();
            int var11 = var9.indexOf(var3);
            if(var11 < 0) {
               var11 = var9.length();
            }

            int var12 = var9.lastIndexOf(var3);
            if(var11 + var6 > 76) {
               StringBuffer var13 = var4.append("\r\n\t");
               var6 = 8;
            }

            var4.append(var9);
            if(var12 > -1) {
               var6 += var10;
            } else {
               var6 = var10 - var12 - 2;
            }

            ++var5;
         }
      } else {
         var2 = null;
      }

      return var2;
   }

   private static String unquote(String var0) {
      StringBuffer var1 = null;
      byte var2 = 0;
      int var3 = var0.length();
      String var7;
      if(var3 > 2 && var0.charAt(0) == 34) {
         int var4 = var3 - 1;
         if(var0.charAt(var4) == 34) {
            int var5 = var3 - 1;
            String var6 = var0.substring(1, var5);
            if(var6.indexOf(92) > -1) {
               int var17 = var3 + -2;
               var1 = new StringBuffer(var17);

               int var12;
               for(int var8 = 0; var8 < var17; var8 = var12 + 1) {
                  char var13;
                  label24: {
                     char var16 = var6.charAt(var8);
                     if(var16 == 92) {
                        int var9 = var17 - 1;
                        if(var8 < var9) {
                           int var10 = var8 + 1;
                           char var11 = var6.charAt(var10);
                           var12 = var10;
                           var13 = var11;
                           break label24;
                        }
                     }

                     var12 = var8;
                     var13 = var16;
                  }

                  var1.append(var13);
               }

               var7 = var1.toString();
            } else {
               var7 = var6;
            }

            return var7;
         }
      }

      var7 = var0;
      return var7;
   }

   private static void validate(String var0, boolean var1, boolean var2) throws AddressException {
      int var5;
      if(var2 && !var1) {
         var5 = 0;
      } else {
         int var3 = var0.indexOf(44, 0);
         int var4;
         if(var3 < 0) {
            var3 = var0.indexOf(58, 0);
            var4 = 0;
         } else {
            var4 = 0;
         }

         while(var3 > -1) {
            if(var0.charAt(var4) != 64) {
               throw new AddressException("Illegal route-addr", var0);
            }

            if(var0.charAt(var3) != 58) {
               var3 = var0.indexOf(44, var4);
               if(var3 < 0) {
                  var3 = var0.indexOf(58, var4);
               }
            } else {
               var4 = var3 + 1;
               var3 = -1;
            }
         }

         var5 = var4;
      }

      Object var6 = null;
      int var7 = var0.indexOf(64, var5);
      String var12;
      String var13;
      if(var7 > -1) {
         if(var7 == var5) {
            throw new AddressException("Missing local name", var0);
         }

         int var8 = var0.length() - 1;
         if(var7 == var8) {
            throw new AddressException("Missing domain", var0);
         }

         String var9 = var0.substring(var5, var7);
         int var10 = var7 + 1;
         String var11 = var0.substring(var10);
         var12 = var9;
         var13 = var11;
      } else {
         if(var2) {
            throw new AddressException("Missing final @domain", var0);
         }

         var13 = (String)var6;
         var12 = var0;
      }

      String var14 = "\t\n\r ";
      byte var15 = 4;

      for(int var16 = 0; var16 < var15; ++var16) {
         char var17 = var14.charAt(var16);
         if(var0.indexOf(var17) > -1) {
            throw new AddressException("Illegal whitespace", var0);
         }
      }

      String var18 = "\"(),:;<>@[\\]";
      byte var19 = 12;

      for(int var20 = 0; var20 < var19; ++var20) {
         char var21 = var18.charAt(var20);
         if(var12.indexOf(var21) > -1) {
            throw new AddressException("Illegal local name", var0);
         }
      }

      if(var13 != null) {
         for(int var23 = 0; var23 < var19; ++var23) {
            char var22 = var18.charAt(var23);
            if(var13.indexOf(var22) > -1) {
               throw new AddressException("Illegal domain", var0);
            }
         }

      }
   }

   public Object clone() {
      InternetAddress var1 = new InternetAddress();
      String var2 = this.address;
      var1.address = var2;
      String var3 = this.personal;
      var1.personal = var3;
      String var4 = this.encodedPersonal;
      var1.encodedPersonal = var4;
      return var1;
   }

   public boolean equals(Object var1) {
      boolean var3;
      if(var1 instanceof InternetAddress) {
         String var2 = ((InternetAddress)var1).getAddress();
         if(this != var1 && (this.address == null || !this.address.equalsIgnoreCase(var2))) {
            var3 = false;
         } else {
            var3 = true;
         }
      } else {
         var3 = false;
      }

      return var3;
   }

   public String getAddress() {
      return this.address;
   }

   public InternetAddress[] getGroup(boolean var1) throws AddressException {
      int var2 = this.address.indexOf(58);
      int var3 = this.address.length() - 1;
      InternetAddress[] var6;
      if(var2 != -1 && this.address.charAt(var3) != 59) {
         String var4 = this.address;
         int var5 = var2 + 1;
         var6 = parseHeader(var4.substring(var5, var3), var1);
      } else {
         var6 = null;
      }

      return var6;
   }

   public String getPersonal() {
      String var1;
      if(this.personal != null) {
         var1 = this.personal;
      } else if(this.encodedPersonal != null) {
         try {
            String var2 = MimeUtility.decodeText(this.encodedPersonal);
            this.personal = var2;
            var1 = this.personal;
         } catch (Exception var4) {
            var1 = this.encodedPersonal;
         }
      } else {
         var1 = null;
      }

      return var1;
   }

   public String getType() {
      return "rfc822";
   }

   public int hashCode() {
      int var1;
      if(this.address == null) {
         var1 = 0;
      } else {
         var1 = this.address.hashCode();
      }

      return var1;
   }

   public boolean isGroup() {
      boolean var1;
      if(this.address.indexOf(58) == -1) {
         var1 = false;
      } else {
         int var2 = this.address.length() - 1;
         if(this.address.charAt(var2) == 59) {
            var1 = true;
         } else {
            var1 = false;
         }
      }

      return var1;
   }

   public void setAddress(String var1) {
      this.address = var1;
   }

   public void setPersonal(String var1) throws UnsupportedEncodingException {
      this.setPersonal(var1, (String)null);
   }

   public void setPersonal(String var1, String var2) throws UnsupportedEncodingException {
      this.personal = var1;
      if(var1 != null) {
         if(var2 == null) {
            String var3 = MimeUtility.encodeWord(var1);
            this.encodedPersonal = var3;
         } else {
            String var4 = MimeUtility.encodeWord(var1, var2, (String)null);
            this.encodedPersonal = var4;
         }
      } else {
         this.encodedPersonal = null;
      }
   }

   public String toString() {
      if(this.encodedPersonal == null && this.personal != null) {
         try {
            String var1 = MimeUtility.encodeWord(this.personal);
            this.encodedPersonal = var1;
         } catch (UnsupportedEncodingException var17) {
            ;
         }
      }

      StringBuffer var2 = new StringBuffer();
      if(this.encodedPersonal != null) {
         String var3 = quote(this.encodedPersonal);
         var2.append(var3);
         StringBuffer var5 = var2.append(' ');
         StringBuffer var6 = var2.append('<');
         String var7 = this.address;
         var2.append(var7);
         StringBuffer var9 = var2.append('>');
      } else if(!isGroupAddress(this.address) && !isSimpleAddress(this.address)) {
         StringBuffer var12 = var2.append('<');
         String var13 = this.address;
         var2.append(var13);
         StringBuffer var15 = var2.append('>');
      } else {
         String var10 = this.address;
         var2.append(var10);
      }

      return var2.toString();
   }

   public String toUnicodeString() {
      StringBuffer var1 = new StringBuffer();
      if(this.getPersonal() != null) {
         String var2 = quote(this.personal);
         var1.append(var2);
         StringBuffer var4 = var1.append(' ');
         StringBuffer var5 = var1.append('<');
         String var6 = this.address;
         var1.append(var6);
         StringBuffer var8 = var1.append('>');
      } else if(!isGroupAddress(this.address) && !isSimpleAddress(this.address)) {
         StringBuffer var11 = var1.append('<');
         String var12 = this.address;
         var1.append(var12);
         StringBuffer var14 = var1.append('>');
      } else {
         String var9 = this.address;
         var1.append(var9);
      }

      return var1.toString();
   }

   public void validate() throws AddressException {
      validate(this.address, (boolean)1, (boolean)1);
   }
}
