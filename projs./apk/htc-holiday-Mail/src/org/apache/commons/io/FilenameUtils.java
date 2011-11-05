package org.apache.commons.io;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Stack;
import org.apache.commons.io.IOCase;

public class FilenameUtils {

   public static final char EXTENSION_SEPARATOR = '.';
   public static final String EXTENSION_SEPARATOR_STR = (new Character('.')).toString();
   private static final char OTHER_SEPARATOR = '\u0000';
   private static final char SYSTEM_SEPARATOR = File.separatorChar;
   private static final char UNIX_SEPARATOR = '/';
   private static final char WINDOWS_SEPARATOR = '\\';


   static {
      if(isSystemWindows()) {
         OTHER_SEPARATOR = 47;
      } else {
         OTHER_SEPARATOR = 92;
      }
   }

   public FilenameUtils() {}

   public static String concat(String var0, String var1) {
      String var2 = null;
      int var3 = getPrefixLength(var1);
      if(var3 >= 0) {
         if(var3 > 0) {
            var2 = normalize(var1);
         } else if(var0 != null) {
            int var4 = var0.length();
            if(var4 == 0) {
               var2 = normalize(var1);
            } else {
               int var5 = var4 - 1;
               if(isSeparator(var0.charAt(var5))) {
                  var2 = normalize(var0 + var1);
               } else {
                  var2 = normalize(var0 + '/' + var1);
               }
            }
         }
      }

      return var2;
   }

   private static String doGetFullPath(String var0, boolean var1) {
      String var2 = null;
      if(var0 != null) {
         int var3 = getPrefixLength(var0);
         if(var3 >= 0) {
            int var4 = var0.length();
            if(var3 >= var4) {
               if(var1) {
                  var2 = getPrefix(var0);
               } else {
                  var2 = var0;
               }
            } else {
               int var5 = indexOfLastSeparator(var0);
               if(var5 < 0) {
                  var2 = var0.substring(0, var3);
               } else {
                  byte var6;
                  if(var1) {
                     var6 = 1;
                  } else {
                     var6 = 0;
                  }

                  int var7 = var5 + var6;
                  var2 = var0.substring(0, var7);
               }
            }
         }
      }

      return var2;
   }

   private static String doGetPath(String var0, int var1) {
      String var2 = null;
      if(var0 != null) {
         int var3 = getPrefixLength(var0);
         if(var3 >= 0) {
            int var4 = indexOfLastSeparator(var0);
            int var5 = var0.length();
            if(var3 < var5 && var4 >= 0) {
               int var6 = var4 + var1;
               var2 = var0.substring(var3, var6);
            } else {
               var2 = "";
            }
         }
      }

      return var2;
   }

   private static String doNormalize(String var0, boolean var1) {
      if(var0 == null) {
         var0 = null;
      } else {
         int var2 = var0.length();
         if(var2 != 0) {
            int var3 = getPrefixLength(var0);
            if(var3 < 0) {
               var0 = null;
            } else {
               char[] var4 = new char[var2 + 2];
               int var5 = var0.length();
               var0.getChars(0, var5, var4, 0);
               int var6 = 0;

               while(true) {
                  int var7 = var4.length;
                  if(var6 >= var7) {
                     boolean var11 = true;
                     int var12 = var2 - 1;
                     char var13 = var4[var12];
                     char var14 = SYSTEM_SEPARATOR;
                     int var15;
                     boolean var68;
                     if(var13 != var14) {
                        var15 = var2 + 1;
                        char var16 = SYSTEM_SEPARATOR;
                        var4[var2] = var16;
                        var68 = false;
                     } else {
                        var15 = var2;
                        var68 = var11;
                     }

                     for(int var17 = var3 + 1; var17 < var15; ++var17) {
                        char var18 = var4[var17];
                        char var19 = SYSTEM_SEPARATOR;
                        if(var18 == var19) {
                           int var20 = var17 - 1;
                           char var21 = var4[var20];
                           char var22 = SYSTEM_SEPARATOR;
                           if(var21 == var22) {
                              int var23 = var17 - 1;
                              int var24 = var15 - var17;
                              System.arraycopy(var4, var17, var4, var23, var24);
                              int var25 = var15 + -1;
                              var17 += -1;
                           }
                        }
                     }

                     for(int var26 = var3 + 1; var26 < var15; ++var26) {
                        char var27 = var4[var26];
                        char var28 = SYSTEM_SEPARATOR;
                        if(var27 == var28) {
                           int var29 = var26 - 1;
                           if(var4[var29] == 46) {
                              int var30 = var3 + 1;
                              if(var26 != var30) {
                                 int var31 = var26 - 2;
                                 char var32 = var4[var31];
                                 char var33 = SYSTEM_SEPARATOR;
                                 if(var32 != var33) {
                                    continue;
                                 }
                              }

                              int var34 = var15 - 1;
                              if(var26 == var34) {
                                 ;
                              }

                              int var35 = var26 + 1;
                              int var36 = var26 - 1;
                              int var37 = var15 - var26;
                              System.arraycopy(var4, var35, var4, var36, var37);
                              int var38 = var15 + -2;
                              var26 += -1;
                           }
                        }
                     }

                     int var39 = var3 + 2;

                     int var40;
                     int var60;
                     label100:
                     for(var40 = var15; var39 < var40; var60 = var39 + 1) {
                        char var41 = var4[var39];
                        char var42 = SYSTEM_SEPARATOR;
                        if(var41 == var42) {
                           int var43 = var39 - 1;
                           if(var4[var43] == 46) {
                              int var44 = var39 - 2;
                              if(var4[var44] == 46) {
                                 int var45 = var3 + 2;
                                 if(var39 != var45) {
                                    int var46 = var39 - 3;
                                    char var47 = var4[var46];
                                    char var48 = SYSTEM_SEPARATOR;
                                    if(var47 != var48) {
                                       continue;
                                    }
                                 }

                                 int var49 = var3 + 2;
                                 if(var39 == var49) {
                                    var0 = null;
                                    return var0;
                                 }

                                 int var50 = var40 - 1;
                                 boolean var51;
                                 if(var39 == var50) {
                                    var51 = true;
                                 } else {
                                    var51 = var68;
                                 }

                                 for(int var52 = var39 - 4; var52 >= var3; var52 += -1) {
                                    char var53 = var4[var52];
                                    char var54 = SYSTEM_SEPARATOR;
                                    if(var53 == var54) {
                                       int var55 = var39 + 1;
                                       int var56 = var52 + 1;
                                       int var57 = var40 - var39;
                                       System.arraycopy(var4, var55, var4, var56, var57);
                                       int var58 = var39 - var52;
                                       var40 -= var58;
                                       var39 = var52 + 1;
                                       var68 = var51;
                                       continue label100;
                                    }
                                 }

                                 int var62 = var39 + 1;
                                 int var63 = var40 - var39;
                                 System.arraycopy(var4, var62, var4, var3, var63);
                                 int var64 = var39 + 1 - var3;
                                 int var65 = var40 - var64;
                                 var39 = var3 + 1;
                                 var68 = var51;
                              }
                           }
                        }
                     }

                     if(var40 <= 0) {
                        var0 = "";
                     } else if(var40 <= var3) {
                        var0 = new String(var4, 0, var40);
                     } else if(var68 && var1) {
                        var0 = new String(var4, 0, var40);
                     } else {
                        int var67 = var40 - 1;
                        var0 = new String(var4, 0, var67);
                     }
                     break;
                  }

                  char var8 = var4[var6];
                  char var9 = OTHER_SEPARATOR;
                  if(var8 == var9) {
                     char var10 = SYSTEM_SEPARATOR;
                     var4[var6] = var10;
                  }

                  ++var6;
               }
            }
         }
      }

      return var0;
   }

   public static boolean equals(String var0, String var1) {
      IOCase var2 = IOCase.SENSITIVE;
      return equals(var0, var1, (boolean)0, var2);
   }

   public static boolean equals(String var0, String var1, boolean var2, IOCase var3) {
      boolean var4;
      if(var0 != null && var1 != null) {
         if(var2) {
            var0 = normalize(var0);
            var1 = normalize(var1);
            if(var0 == null || var1 == null) {
               throw new NullPointerException("Error normalizing one or both of the file names");
            }
         }

         if(var3 == null) {
            var3 = IOCase.SENSITIVE;
         }

         var4 = var3.checkEquals(var0, var1);
      } else if(var0 == var1) {
         var4 = true;
      } else {
         var4 = false;
      }

      return var4;
   }

   public static boolean equalsNormalized(String var0, String var1) {
      IOCase var2 = IOCase.SENSITIVE;
      return equals(var0, var1, (boolean)1, var2);
   }

   public static boolean equalsNormalizedOnSystem(String var0, String var1) {
      IOCase var2 = IOCase.SYSTEM;
      return equals(var0, var1, (boolean)1, var2);
   }

   public static boolean equalsOnSystem(String var0, String var1) {
      IOCase var2 = IOCase.SYSTEM;
      return equals(var0, var1, (boolean)0, var2);
   }

   public static String getBaseName(String var0) {
      return removeExtension(getName(var0));
   }

   public static String getExtension(String var0) {
      String var1;
      if(var0 == null) {
         var1 = null;
      } else {
         int var2 = indexOfExtension(var0);
         if(var2 == -1) {
            var1 = "";
         } else {
            int var3 = var2 + 1;
            var1 = var0.substring(var3);
         }
      }

      return var1;
   }

   public static String getFullPath(String var0) {
      return doGetFullPath(var0, (boolean)1);
   }

   public static String getFullPathNoEndSeparator(String var0) {
      return doGetFullPath(var0, (boolean)0);
   }

   public static String getName(String var0) {
      String var1;
      if(var0 == null) {
         var1 = null;
      } else {
         int var2 = indexOfLastSeparator(var0) + 1;
         var1 = var0.substring(var2);
      }

      return var1;
   }

   public static String getPath(String var0) {
      return doGetPath(var0, 1);
   }

   public static String getPathNoEndSeparator(String var0) {
      return doGetPath(var0, 0);
   }

   public static String getPrefix(String var0) {
      String var1 = null;
      if(var0 != null) {
         int var2 = getPrefixLength(var0);
         if(var2 >= 0) {
            int var3 = var0.length();
            if(var2 > var3) {
               var1 = var0 + '/';
            } else {
               var1 = var0.substring(0, var2);
            }
         }
      }

      return var1;
   }

   public static int getPrefixLength(String var0) {
      int var1;
      if(var0 == null) {
         var1 = -1;
      } else {
         int var2 = var0.length();
         if(var2 == 0) {
            var1 = 0;
         } else {
            char var3 = var0.charAt(0);
            if(var3 == 58) {
               var1 = -1;
            } else if(var2 == 1) {
               if(var3 == 126) {
                  var1 = 2;
               } else if(isSeparator(var3)) {
                  var1 = 1;
               } else {
                  var1 = 0;
               }
            } else {
               int var4;
               int var5;
               if(var3 == 126) {
                  var4 = var0.indexOf(47, 1);
                  var5 = var0.indexOf(92, 1);
                  if(var4 == -1 && var5 == -1) {
                     var1 = var2 + 1;
                  } else {
                     if(var4 == -1) {
                        var4 = var5;
                     }

                     if(var5 == -1) {
                        var5 = var4;
                     }

                     var1 = Math.min(var4, var5) + 1;
                  }
               } else {
                  char var6 = var0.charAt(1);
                  if(var6 == 58) {
                     var3 = Character.toUpperCase(var3);
                     if(var3 >= 65 && var3 <= 90) {
                        if(var2 != 2 && isSeparator(var0.charAt(2))) {
                           var1 = 3;
                        } else {
                           var1 = 2;
                        }
                     } else {
                        var1 = -1;
                     }
                  } else if(isSeparator(var3) && isSeparator(var6)) {
                     var4 = var0.indexOf(47, 2);
                     var5 = var0.indexOf(92, 2);
                     if((var4 != -1 || var5 != -1) && var4 != 2 && var5 != 2) {
                        if(var4 == -1) {
                           var4 = var5;
                        }

                        if(var5 == -1) {
                           var5 = var4;
                        }

                        var1 = Math.min(var4, var5) + 1;
                     } else {
                        var1 = -1;
                     }
                  } else if(isSeparator(var3)) {
                     var1 = 1;
                  } else {
                     var1 = 0;
                  }
               }
            }
         }
      }

      return var1;
   }

   public static int indexOfExtension(String var0) {
      int var1;
      if(var0 == null) {
         var1 = -1;
      } else {
         int var2 = var0.lastIndexOf(46);
         if(indexOfLastSeparator(var0) > var2) {
            var1 = -1;
         } else {
            var1 = var2;
         }
      }

      return var1;
   }

   public static int indexOfLastSeparator(String var0) {
      int var1;
      if(var0 == null) {
         var1 = -1;
      } else {
         int var2 = var0.lastIndexOf(47);
         int var3 = var0.lastIndexOf(92);
         var1 = Math.max(var2, var3);
      }

      return var1;
   }

   public static boolean isExtension(String var0, String var1) {
      boolean var2;
      if(var0 == null) {
         var2 = false;
      } else if(var1 != null && var1.length() != 0) {
         var2 = getExtension(var0).equals(var1);
      } else if(indexOfExtension(var0) == -1) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public static boolean isExtension(String var0, Collection var1) {
      boolean var2;
      if(var0 == null) {
         var2 = false;
      } else if(var1 != null && !var1.isEmpty()) {
         String var3 = getExtension(var0);
         Iterator var4 = var1.iterator();

         while(true) {
            if(var4.hasNext()) {
               Object var5 = var4.next();
               if(!var3.equals(var5)) {
                  continue;
               }

               var2 = true;
               break;
            }

            var2 = false;
            break;
         }
      } else if(indexOfExtension(var0) == -1) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public static boolean isExtension(String var0, String[] var1) {
      boolean var2;
      if(var0 == null) {
         var2 = false;
      } else if(var1 != null && var1.length != 0) {
         String var3 = getExtension(var0);
         int var4 = 0;

         while(true) {
            int var5 = var1.length;
            if(var4 >= var5) {
               var2 = false;
               break;
            }

            String var6 = var1[var4];
            if(var3.equals(var6)) {
               var2 = true;
               break;
            }

            ++var4;
         }
      } else if(indexOfExtension(var0) == -1) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   private static boolean isSeparator(char var0) {
      boolean var1;
      if(var0 != 47 && var0 != 92) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   static boolean isSystemWindows() {
      boolean var0;
      if(SYSTEM_SEPARATOR == 92) {
         var0 = true;
      } else {
         var0 = false;
      }

      return var0;
   }

   public static String normalize(String var0) {
      return doNormalize(var0, (boolean)1);
   }

   public static String normalizeNoEndSeparator(String var0) {
      return doNormalize(var0, (boolean)0);
   }

   public static String removeExtension(String var0) {
      String var1;
      if(var0 == null) {
         var1 = null;
      } else {
         int var2 = indexOfExtension(var0);
         if(var2 == -1) {
            var1 = var0;
         } else {
            var1 = var0.substring(0, var2);
         }
      }

      return var1;
   }

   public static String separatorsToSystem(String var0) {
      String var1;
      if(var0 == null) {
         var1 = null;
      } else if(isSystemWindows()) {
         var1 = separatorsToWindows(var0);
      } else {
         var1 = separatorsToUnix(var0);
      }

      return var1;
   }

   public static String separatorsToUnix(String var0) {
      String var1;
      if(var0 != null && var0.indexOf(92) != -1) {
         var1 = var0.replace('\\', '/');
      } else {
         var1 = var0;
      }

      return var1;
   }

   public static String separatorsToWindows(String var0) {
      String var1;
      if(var0 != null && var0.indexOf(47) != -1) {
         var1 = var0.replace('/', '\\');
      } else {
         var1 = var0;
      }

      return var1;
   }

   static String[] splitOnTokens(String var0) {
      String[] var1;
      if(var0.indexOf("?") == -1 && var0.indexOf("*") == -1) {
         var1 = new String[]{var0};
      } else {
         char[] var2 = var0.toCharArray();
         ArrayList var3 = new ArrayList();
         StringBuffer var4 = new StringBuffer();
         int var5 = 0;

         while(true) {
            int var6 = var2.length;
            if(var5 >= var6) {
               if(var4.length() != 0) {
                  String var14 = var4.toString();
                  var3.add(var14);
               }

               String[] var16 = new String[var3.size()];
               var1 = (String[])((String[])var3.toArray(var16));
               break;
            }

            if(var2[var5] != 63 && var2[var5] != 42) {
               char var12 = var2[var5];
               var4.append(var12);
            } else {
               if(var4.length() != 0) {
                  String var7 = var4.toString();
                  var3.add(var7);
                  var4.setLength(0);
               }

               if(var2[var5] == 63) {
                  boolean var9 = var3.add("?");
               } else {
                  label35: {
                     if(var3.size() != 0) {
                        if(var5 <= 0) {
                           break label35;
                        }

                        int var10 = var3.size() - 1;
                        if(var3.get(var10).equals("*")) {
                           break label35;
                        }
                     }

                     boolean var11 = var3.add("*");
                  }
               }
            }

            ++var5;
         }
      }

      return var1;
   }

   public static boolean wildcardMatch(String var0, String var1) {
      IOCase var2 = IOCase.SENSITIVE;
      return wildcardMatch(var0, var1, var2);
   }

   public static boolean wildcardMatch(String var0, String var1, IOCase var2) {
      boolean var3;
      if(var0 == null && var1 == null) {
         var3 = true;
      } else if(var0 != null && var1 != null) {
         if(var2 == null) {
            var2 = IOCase.SENSITIVE;
         }

         var0 = var2.convertCase(var0);
         String[] var4 = splitOnTokens(var2.convertCase(var1));
         boolean var5 = false;
         int var6 = 0;
         int var7 = 0;
         Stack var8 = new Stack();

         while(true) {
            if(var8.size() > 0) {
               int[] var9 = (int[])((int[])var8.pop());
               var7 = var9[0];
               var6 = var9[1];
               var5 = true;
            }

            while(true) {
               int var10 = var4.length;
               if(var7 >= var10) {
                  break;
               }

               if(var4[var7].equals("?")) {
                  ++var6;
                  var5 = false;
               } else if(var4[var7].equals("*")) {
                  var5 = true;
                  int var11 = var4.length - 1;
                  if(var7 == var11) {
                     var6 = var0.length();
                  }
               } else {
                  if(var5) {
                     String var12 = var4[var7];
                     var6 = var0.indexOf(var12, var6);
                     if(var6 == -1) {
                        break;
                     }

                     String var15 = var4[var7];
                     int var16 = var6 + 1;
                     int var17 = var0.indexOf(var15, var16);
                     if(var17 >= 0) {
                        int[] var18 = new int[]{var7, var17};
                        var8.push(var18);
                     }
                  } else {
                     String var21 = var4[var7];
                     if(!var0.startsWith(var21, var6)) {
                        break;
                     }
                  }

                  int var20 = var4[var7].length();
                  var6 += var20;
                  var5 = false;
               }

               ++var7;
            }

            int var13 = var4.length;
            if(var7 == var13) {
               int var14 = var0.length();
               if(var6 == var14) {
                  var3 = true;
                  break;
               }
            }

            if(var8.size() <= 0) {
               var3 = false;
               break;
            }
         }
      } else {
         var3 = false;
      }

      return var3;
   }

   public static boolean wildcardMatchOnSystem(String var0, String var1) {
      IOCase var2 = IOCase.SYSTEM;
      return wildcardMatch(var0, var1, var2);
   }
}
