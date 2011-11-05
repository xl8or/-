package javax.mail.internet;

import javax.mail.internet.ParseException;

public class HeaderTokenizer {

   private static final HeaderTokenizer.Token EOF = new HeaderTokenizer.Token(-1, (String)null);
   public static final String MIME = "()<>@,;:\\\"\t []/?=";
   public static final String RFC822 = "()<>@,;:\\\"\t .[]";
   private String delimiters;
   private String header;
   private int maxPos;
   private int next;
   private int peek;
   private int pos;
   private boolean skipComments;


   public HeaderTokenizer(String var1) {
      this(var1, "()<>@,;:\\\"\t .[]", (boolean)1);
   }

   public HeaderTokenizer(String var1, String var2) {
      this(var1, var2, (boolean)1);
   }

   public HeaderTokenizer(String var1, String var2, boolean var3) {
      this.pos = 0;
      this.next = 0;
      this.peek = 0;
      String var4;
      if(var1 == null) {
         var4 = "";
      } else {
         var4 = var1;
      }

      this.header = var4;
      this.delimiters = var2;
      this.skipComments = var3;
      this.peek = 0;
      this.next = 0;
      this.pos = 0;
      int var5 = var1.length();
      this.maxPos = var5;
   }

   private String filter(String var1, int var2, int var3) {
      StringBuffer var4 = new StringBuffer();
      int var5 = var2;
      boolean var6 = false;

      for(boolean var7 = false; var5 < var3; ++var5) {
         char var8 = var1.charAt(var5);
         if(var8 == 10 && var6) {
            var6 = false;
         } else if(!var7) {
            if(var8 == 92) {
               var6 = false;
               var7 = true;
            } else if(var8 == 13) {
               var6 = true;
            } else {
               var4.append(var8);
               var6 = false;
            }
         } else {
            var4.append(var8);
            var6 = false;
            var7 = false;
         }
      }

      return var4.toString();
   }

   private int skipWhitespace() {
      while(true) {
         int var1 = this.pos;
         int var2 = this.maxPos;
         int var6;
         if(var1 < var2) {
            String var3 = this.header;
            int var4 = this.pos;
            char var5 = var3.charAt(var4);
            if(var5 == 32 || var5 == 9 || var5 == 13 || var5 == 10) {
               int var7 = this.pos + 1;
               this.pos = var7;
               continue;
            }

            var6 = this.pos;
         } else {
            var6 = -1;
         }

         return var6;
      }
   }

   private HeaderTokenizer.Token token() throws ParseException {
      int var1 = this.pos;
      int var2 = this.maxPos;
      HeaderTokenizer.Token var3;
      if(var1 >= var2) {
         var3 = EOF;
      } else if(this.skipWhitespace() == -1) {
         var3 = EOF;
      } else {
         String var4 = this.header;
         int var5 = this.pos;
         char var6 = var4.charAt(var5);
         boolean var7 = false;
         char var8 = var6;

         while(true) {
            if(var8 != 40) {
               if(var8 == 34) {
                  int var28 = this.pos + 1;
                  this.pos = var28;

                  while(true) {
                     int var29 = this.pos;
                     int var30 = this.maxPos;
                     if(var29 >= var30) {
                        throw new ParseException("Illegal quoted string");
                     }

                     String var31 = this.header;
                     int var32 = this.pos;
                     char var33 = var31.charAt(var32);
                     if(var33 == 92) {
                        int var34 = this.pos + 1;
                        this.pos = var34;
                        boolean var35 = true;
                     } else if(var33 == 13) {
                        boolean var37 = true;
                     } else if(var33 == 34) {
                        int var38 = this.pos + 1;
                        this.pos = var38;
                        String var41;
                        if(var7) {
                           String var39 = this.header;
                           int var40 = this.pos - 1;
                           var41 = this.filter(var39, var28, var40);
                        } else {
                           String var42 = this.header;
                           int var43 = this.pos - 1;
                           var41 = var42.substring(var28, var43);
                        }

                        var3 = new HeaderTokenizer.Token(-1, var41);
                        return var3;
                     }

                     int var36 = this.pos + 1;
                     this.pos = var36;
                  }
               } else {
                  if(var8 >= 32 && var8 < 127 && this.delimiters.indexOf(var8) < 0) {
                     int var47 = this.pos;

                     while(true) {
                        int var48 = this.pos;
                        int var49 = this.maxPos;
                        if(var48 >= var49) {
                           break;
                        }

                        String var50 = this.header;
                        int var51 = this.pos;
                        char var52 = var50.charAt(var51);
                        if(var52 < 32 || var52 >= 127 || var52 == 40 || var52 == 32 || var52 == 34 || this.delimiters.indexOf(var52) >= 0) {
                           break;
                        }

                        int var56 = this.pos + 1;
                        this.pos = var56;
                     }

                     String var53 = this.header;
                     int var54 = this.pos;
                     String var55 = var53.substring(var47, var54);
                     var3 = new HeaderTokenizer.Token(-1, var55);
                  } else {
                     int var44 = this.pos + 1;
                     this.pos = var44;
                     char[] var45 = new char[]{var8};
                     String var46 = new String(var45);
                     var3 = new HeaderTokenizer.Token(var8, var46);
                  }
                  break;
               }
            }

            int var9 = this.pos + 1;
            this.pos = var9;

            int var11;
            int var19;
            for(var11 = 1; var11 > 0; this.pos = var19) {
               int var12 = this.pos;
               int var13 = this.maxPos;
               if(var12 >= var13) {
                  break;
               }

               String var14 = this.header;
               int var15 = this.pos;
               char var16 = var14.charAt(var15);
               if(var16 == 92) {
                  int var17 = this.pos + 1;
                  this.pos = var17;
                  boolean var18 = true;
               } else if(var16 == 13) {
                  boolean var20 = true;
               } else if(var16 == 40) {
                  ++var11;
               } else if(var16 == 41) {
                  var11 += -1;
               }

               var19 = this.pos + 1;
            }

            if(var11 != 0) {
               throw new ParseException("Illegal comment");
            }

            if(!this.skipComments) {
               String var23;
               if(var7) {
                  String var21 = this.header;
                  int var22 = this.pos - 1;
                  var23 = this.filter(var21, var9, var22);
               } else {
                  String var24 = this.header;
                  int var25 = this.pos - 1;
                  var23 = var24.substring(var9, var25);
               }

               var3 = new HeaderTokenizer.Token(-1, var23);
               break;
            }

            if(this.skipWhitespace() == -1) {
               var3 = EOF;
               break;
            }

            String var26 = this.header;
            int var27 = this.pos;
            var8 = var26.charAt(var27);
            var7 = var7;
         }
      }

      return var3;
   }

   public String getRemainder() {
      String var1 = this.header;
      int var2 = this.next;
      return var1.substring(var2);
   }

   public HeaderTokenizer.Token next() throws ParseException {
      int var1 = this.next;
      this.pos = var1;
      HeaderTokenizer.Token var2 = this.token();
      int var3 = this.pos;
      this.next = var3;
      int var4 = this.next;
      this.peek = var4;
      return var2;
   }

   public HeaderTokenizer.Token peek() throws ParseException {
      int var1 = this.peek;
      this.pos = var1;
      HeaderTokenizer.Token var2 = this.token();
      int var3 = this.pos;
      this.peek = var3;
      return var2;
   }

   public static class Token {

      public static final int ATOM = 255;
      public static final int COMMENT = 253;
      public static final int EOF = 252;
      public static final int QUOTEDSTRING = 254;
      private int type;
      private String value;


      public Token(int var1, String var2) {
         this.type = var1;
         this.value = var2;
      }

      public int getType() {
         return this.type;
      }

      public String getValue() {
         return this.value;
      }
   }
}
