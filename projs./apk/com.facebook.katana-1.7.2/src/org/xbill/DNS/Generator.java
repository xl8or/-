package org.xbill.DNS;

import java.io.IOException;
import java.util.ArrayList;
import org.xbill.DNS.DClass;
import org.xbill.DNS.Name;
import org.xbill.DNS.Options;
import org.xbill.DNS.Record;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Type;

public class Generator {

   private long current;
   public final int dclass;
   public long end;
   public final String namePattern;
   public final Name origin;
   public final String rdataPattern;
   public long start;
   public long step;
   public final long ttl;
   public final int type;


   public Generator(long var1, long var3, long var5, String var7, int var8, int var9, long var10, String var12, Name var13) {
      if(var1 >= 0L && var3 >= 0L && var1 <= var3 && var5 > 0L) {
         if(!supportedType(var8)) {
            throw new IllegalArgumentException("unsupported type");
         } else {
            DClass.check(var9);
            this.start = var1;
            this.end = var3;
            this.step = var5;
            this.namePattern = var7;
            this.type = var8;
            this.dclass = var9;
            this.ttl = var10;
            this.rdataPattern = var12;
            this.origin = var13;
            this.current = var1;
         }
      } else {
         throw new IllegalArgumentException("invalid range specification");
      }
   }

   private String substitute(String var1, long var2) throws IOException {
      byte[] var4 = var1.getBytes();
      StringBuffer var5 = new StringBuffer();
      byte var6 = 0;
      boolean var7 = false;
      int var8 = var6;

      while(true) {
         int var9 = var4.length;
         if(var8 >= var9) {
            return var5.toString();
         }

         char var10 = (char)(var4[var8] & 255);
         if(var7) {
            var5.append(var10);
            var7 = false;
         } else if(var10 == 92) {
            int var12 = var8 + 1;
            int var13 = var4.length;
            if(var12 == var13) {
               throw new TextParseException("invalid escape character");
            }

            var7 = true;
         } else if(var10 == 36) {
            label164: {
               boolean var14 = false;
               long var15 = 0L;
               long var17 = 0L;
               long var19 = 10L;
               boolean var21 = false;
               int var22 = var8 + 1;
               int var23 = var4.length;
               if(var22 < var23) {
                  int var24 = var8 + 1;
                  if(var4[var24] == 36) {
                     ++var8;
                     char var25 = (char)(var4[var8] & 255);
                     var5.append(var25);
                     break label164;
                  }
               }

               boolean var77;
               long var84;
               long var86;
               int var109;
               label150: {
                  int var27 = var8 + 1;
                  int var28 = var4.length;
                  if(var27 < var28) {
                     int var29 = var8 + 1;
                     if(var4[var29] == 123) {
                        int var38;
                        boolean var39;
                        char var37;
                        long var40;
                        label103: {
                           int var30 = var8 + 1;
                           int var31 = var30 + 1;
                           int var32 = var4.length;
                           if(var31 < var32) {
                              int var33 = var30 + 1;
                              if(var4[var33] == 45) {
                                 int var34 = var30 + 1;
                                 var37 = var10;
                                 var38 = var34;
                                 var39 = true;
                                 var40 = var15;
                                 break label103;
                              }
                           }

                           var37 = var10;
                           var38 = var30;
                           var39 = var14;
                           var40 = var15;
                        }

                        while(true) {
                           int var42 = var38 + 1;
                           int var43 = var4.length;
                           if(var42 < var43) {
                              int var44 = var38 + 1;
                              char var45 = (char)(var4[var44] & 255);
                              if(var45 != 44) {
                                 if(var45 != 125) {
                                    if(var45 >= 48 && var45 <= 57) {
                                       char var58 = (char)(var45 - 48);
                                       long var59 = var40 * 10L;
                                       long var61 = (long)var58;
                                       var40 = var59 + var61;
                                       var38 = var44;
                                       var37 = var58;
                                       continue;
                                    }

                                    throw new TextParseException("invalid offset");
                                 }

                                 var38 = var44;
                                 var37 = var45;
                              } else {
                                 var38 = var44;
                                 var37 = var45;
                              }
                           }

                           if(var39) {
                              var40 = -var40;
                           }

                           char var47;
                           int var50;
                           if(var37 == 44) {
                              var47 = var37;
                              var50 = var38;
                              var15 = var17;

                              while(true) {
                                 int var51 = var50 + 1;
                                 int var52 = var4.length;
                                 if(var51 >= var52) {
                                    break;
                                 }

                                 int var53 = var50 + 1;
                                 char var54 = (char)(var4[var53] & 255);
                                 if(var54 != 44) {
                                    if(var54 == 125) {
                                       var50 = var53;
                                       var47 = var54;
                                       break;
                                    }

                                    if(var54 >= 48 && var54 <= 57) {
                                       char var64 = (char)(var54 - 48);
                                       long var65 = var15 * 10L;
                                       long var67 = (long)var64;
                                       long var10000 = var65 + var67;
                                       var50 = var53;
                                       var47 = var64;
                                       continue;
                                    }

                                    throw new TextParseException("invalid width");
                                 }

                                 var50 = var53;
                                 var47 = var54;
                                 break;
                              }
                           } else {
                              var47 = var37;
                              var50 = var38;
                              var15 = var17;
                           }

                           int var76;
                           long var74;
                           if(var47 == 44) {
                              int var56 = var50 + 1;
                              int var57 = var4.length;
                              if(var56 == var57) {
                                 throw new TextParseException("invalid base");
                              }

                              int var72 = var50 + 1;
                              char var73 = (char)(var4[var72] & 255);
                              if(var73 == 111) {
                                 var74 = 8L;
                                 var76 = var72;
                                 var77 = var21;
                              } else if(var73 == 120) {
                                 var74 = 16L;
                                 var76 = var72;
                                 var77 = var21;
                              } else if(var73 == 88) {
                                 var74 = 16L;
                                 boolean var81 = true;
                                 var76 = var72;
                                 var77 = var81;
                              } else {
                                 if(var73 != 100) {
                                    throw new TextParseException("invalid base");
                                 }

                                 var74 = var19;
                                 var76 = var72;
                                 var77 = var21;
                              }
                           } else {
                              var77 = var21;
                              var76 = var50;
                              var74 = var19;
                           }

                           int var78 = var76 + 1;
                           int var79 = var4.length;
                           if(var78 != var79) {
                              int var80 = var76 + 1;
                              if(var4[var80] == 125) {
                                 var109 = var76 + 1;
                                 var84 = var40;
                                 var86 = var74;
                                 break label150;
                              }
                           }

                           throw new TextParseException("invalid modifiers");
                        }
                     }
                  }

                  var86 = var19;
                  var109 = var8;
                  var77 = var21;
                  long var107 = var15;
                  var15 = var17;
                  var84 = var107;
               }

               var17 = var84 + var2;
               if(var17 < 0L) {
                  throw new TextParseException("invalid offset expansion");
               }

               String var88;
               if(var86 == 8L) {
                  var88 = Long.toOctalString(var17);
               } else if(var86 == 16L) {
                  var88 = Long.toHexString(var17);
               } else {
                  var88 = Long.toString(var17);
               }

               String var89;
               if(var77) {
                  var89 = var88.toUpperCase();
               } else {
                  var89 = var88;
               }

               if(var15 != 0L) {
                  long var90 = (long)var89.length();
                  if(var15 > var90) {
                     int var92 = (int)var15;
                     int var93 = var89.length();
                     int var94 = var92 - var93;

                     while(true) {
                        int var95 = var94 + -1;
                        if(var94 <= 0) {
                           break;
                        }

                        StringBuffer var96 = var5.append('0');
                        var94 = var95;
                     }
                  }
               }

               var5.append(var89);
               var8 = var109;
            }
         } else {
            var5.append(var10);
         }

         ++var8;
      }
   }

   public static boolean supportedType(int var0) {
      Type.check(var0);
      boolean var1;
      if(var0 != 12 && var0 != 5 && var0 != 39 && var0 != 1 && var0 != 28 && var0 != 2) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public Record[] expand() throws IOException {
      ArrayList var1 = new ArrayList();
      long var2 = this.start;

      while(true) {
         long var4 = this.end;
         if(var2 >= var4) {
            Record[] var23 = new Record[var1.size()];
            return (Record[])((Record[])var1.toArray(var23));
         }

         String var6 = this.namePattern;
         long var7 = this.current;
         String var9 = this.substitute(var6, var7);
         Name var10 = this.origin;
         Name var11 = Name.fromString(var9, var10);
         String var12 = this.rdataPattern;
         long var13 = this.current;
         String var15 = this.substitute(var12, var13);
         int var16 = this.type;
         int var17 = this.dclass;
         long var18 = this.ttl;
         Name var20 = this.origin;
         Record var21 = Record.fromString(var11, var16, var17, var18, var15, var20);
         var1.add(var21);
         var2 += this.step;
      }
   }

   public Record nextRecord() throws IOException {
      long var1 = this.current;
      long var3 = this.end;
      Record var5;
      if(var1 > var3) {
         var5 = null;
      } else {
         String var6 = this.namePattern;
         long var7 = this.current;
         String var9 = this.substitute(var6, var7);
         Name var10 = this.origin;
         Name var11 = Name.fromString(var9, var10);
         String var12 = this.rdataPattern;
         long var13 = this.current;
         String var15 = this.substitute(var12, var13);
         long var16 = this.current;
         long var18 = this.step;
         long var20 = var16 + var18;
         this.current = var20;
         int var22 = this.type;
         int var23 = this.dclass;
         long var24 = this.ttl;
         Name var26 = this.origin;
         var5 = Record.fromString(var11, var22, var23, var24, var15, var26);
      }

      return var5;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      StringBuffer var2 = var1.append("$GENERATE ");
      StringBuilder var3 = new StringBuilder();
      long var4 = this.start;
      StringBuilder var6 = var3.append(var4).append("-");
      long var7 = this.end;
      String var9 = var6.append(var7).toString();
      var1.append(var9);
      if(this.step > 1L) {
         StringBuilder var11 = (new StringBuilder()).append("/");
         long var12 = this.step;
         String var14 = var11.append(var12).toString();
         var1.append(var14);
      }

      StringBuffer var16 = var1.append(" ");
      StringBuilder var17 = new StringBuilder();
      String var18 = this.namePattern;
      String var19 = var17.append(var18).append(" ").toString();
      var1.append(var19);
      StringBuilder var21 = new StringBuilder();
      long var22 = this.ttl;
      String var24 = var21.append(var22).append(" ").toString();
      var1.append(var24);
      if(this.dclass != 1 || !Options.check("noPrintIN")) {
         StringBuilder var26 = new StringBuilder();
         String var27 = DClass.string(this.dclass);
         String var28 = var26.append(var27).append(" ").toString();
         var1.append(var28);
      }

      StringBuilder var30 = new StringBuilder();
      String var31 = Type.string(this.type);
      String var32 = var30.append(var31).append(" ").toString();
      var1.append(var32);
      StringBuilder var34 = new StringBuilder();
      String var35 = this.rdataPattern;
      String var36 = var34.append(var35).append(" ").toString();
      var1.append(var36);
      return var1.toString();
   }
}
