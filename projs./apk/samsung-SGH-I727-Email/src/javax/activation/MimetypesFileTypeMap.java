package javax.activation;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.activation.FileTypeMap;

public class MimetypesFileTypeMap extends FileTypeMap {

   private static final int DEF = 4;
   private static final String DEFAULT_MIME_TYPE = "application/octet-stream";
   private static final int HOME = 1;
   private static final int JAR = 3;
   private static final int PROG = 0;
   private static final int SYS = 2;
   private static boolean debug = 0;
   private Map[] mimetypes;


   static {
      try {
         debug = Boolean.valueOf(System.getProperty("javax.activation.debug")).booleanValue();
      } catch (SecurityException var1) {
         ;
      }
   }

   public MimetypesFileTypeMap() {
      this.init((Reader)null);
   }

   public MimetypesFileTypeMap(InputStream var1) {
      InputStreamReader var2 = new InputStreamReader(var1);
      this.init(var2);
   }

   public MimetypesFileTypeMap(String param1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   private List getSystemResources(String var1) {
      ArrayList var2 = new ArrayList();

      try {
         Enumeration var3 = ClassLoader.getSystemResources(var1);

         while(var3.hasMoreElements()) {
            Object var4 = var3.nextElement();
            var2.add(var4);
         }
      } catch (IOException var7) {
         ;
      }

      return var2;
   }

   private void init(Reader var1) {
      Object var2 = null;
      int var3 = 0;
      Map[] var4 = new Map[5];
      this.mimetypes = var4;
      int var5 = var3;

      while(true) {
         int var6 = this.mimetypes.length;
         if(var5 >= var6) {
            if(var1 != null) {
               if(debug) {
                  System.out.println("MimetypesFileTypeMap: load PROG");
               }

               try {
                  Map var9 = this.mimetypes[0];
                  this.parse(var9, var1);
               } catch (IOException var66) {
                  ;
               }
            }

            if(debug) {
               System.out.println("MimetypesFileTypeMap: load HOME");
            }

            try {
               String var10 = System.getProperty("user.home");
               if(var10 != null) {
                  Map var11 = this.mimetypes[1];
                  StringBuffer var12 = new StringBuffer(var10);
                  char var13 = File.separatorChar;
                  String var14 = var12.append(var13).append(".mime.types").toString();
                  this.parseFile(var11, var14);
               }
            } catch (SecurityException var65) {
               ;
            }

            if(debug) {
               System.out.println("MimetypesFileTypeMap: load SYS");
            }

            try {
               Map var15 = this.mimetypes[2];
               String var16 = System.getProperty("java.home");
               StringBuffer var17 = new StringBuffer(var16);
               char var18 = File.separatorChar;
               StringBuffer var19 = var17.append(var18).append("lib");
               char var20 = File.separatorChar;
               String var21 = var19.append(var20).append("mime.types").toString();
               this.parseFile(var15, var21);
            } catch (SecurityException var64) {
               ;
            }

            if(debug) {
               System.out.println("MimetypesFileTypeMap: load JAR");
            }

            List var22 = this.getSystemResources("META-INF/mime.types");
            int var23 = var22.size();
            if(var23 > 0) {
               for(; var3 < var23; ++var3) {
                  var2 = null;
                  URL var24 = (URL)var22.get(var3);
                  boolean var60 = false;

                  InputStreamReader var26;
                  label312: {
                     InputStreamReader var71;
                     label334: {
                        label310: {
                           try {
                              var60 = true;
                              InputStream var25 = var24.openStream();
                              var26 = new InputStreamReader(var25);
                              var60 = false;
                              break label310;
                           } catch (IOException var69) {
                              var60 = false;
                           } finally {
                              if(var60) {
                                 Object var31 = var2;
                                 if(var2 != null) {
                                    try {
                                       ((Reader)var31).close();
                                    } catch (IOException var61) {
                                       ;
                                    }
                                 }

                              }
                           }

                           var71 = (InputStreamReader)var2;
                           break label334;
                        }

                        try {
                           Map var27 = this.mimetypes[3];
                           this.parse(var27, var26);
                           break label312;
                        } catch (IOException var67) {
                           ;
                        } finally {
                           ;
                        }

                        var71 = var26;
                     }

                     if(var71 != null) {
                        try {
                           var71.close();
                        } catch (IOException var62) {
                           ;
                        }
                     }
                     continue;
                  }

                  if(var26 != null) {
                     try {
                        var26.close();
                     } catch (IOException var63) {
                        ;
                     }
                  }
               }
            } else {
               Map var32 = this.mimetypes[3];
               this.parseResource(var32, "/META-INF/mime.types");
            }

            if(debug) {
               System.out.println("MimetypesFileTypeMap: load DEF");
            }

            Map var33 = this.mimetypes[4];
            this.parseResource(var33, "/META-INF/mimetypes.default");
            return;
         }

         Map[] var7 = this.mimetypes;
         HashMap var8 = new HashMap();
         var7[var5] = var8;
         ++var5;
      }
   }

   private void parse(Map var1, Reader var2) throws IOException {
      BufferedReader var3 = new BufferedReader(var2);
      String var4 = var3.readLine();

      String var9;
      for(StringBuffer var5 = null; var4 != null; var4 = var9) {
         StringBuffer var8;
         label31: {
            String var6 = var4.trim();
            int var7 = var6.length();
            if(var7 != 0) {
               if(var6.charAt(0) == 35) {
                  var8 = var5;
                  break label31;
               }

               int var10 = var7 - 1;
               if(var6.charAt(var10) == 92) {
                  if(var5 == null) {
                     var5 = new StringBuffer();
                  }

                  int var11 = var7 - 1;
                  String var12 = var6.substring(0, var11);
                  var5.append(var12);
                  var8 = var5;
                  break label31;
               }

               if(var5 != null) {
                  var5.append(var6);
                  String var15 = var5.toString();
                  this.parseEntry(var1, var15);
                  var8 = null;
                  break label31;
               }

               this.parseEntry(var1, var6);
            }

            var8 = var5;
         }

         var9 = var3.readLine();
         var5 = var8;
      }

   }

   private void parseEntry(Map var1, String var2) {
      char[] var3 = var2.toCharArray();
      int var4 = var3.length;
      StringBuffer var5 = new StringBuffer();
      String var6 = null;

      for(int var7 = 0; var7 < var4; ++var7) {
         char var8 = var3[var7];
         if(Character.isWhitespace(var8)) {
            if(var6 == null) {
               var6 = var5.toString();
            } else if(var5.length() > 0) {
               String var9 = var5.toString();
               var1.put(var9, var6);
            }

            var5.setLength(0);
         } else {
            var5.append(var8);
         }
      }

      if(var5.length() > 0) {
         String var12 = var5.toString();
         var1.put(var12, var6);
      }
   }

   private void parseFile(Map param1, String param2) {
      // $FF: Couldn't be decompiled
   }

   private void parseResource(Map var1, String var2) {
      InputStreamReader var3 = null;

      label171: {
         InputStreamReader var5;
         label175: {
            label176: {
               try {
                  try {
                     InputStream var4 = this.getClass().getResourceAsStream(var2);
                     if(var4 == null) {
                        break label171;
                     }

                     var5 = new InputStreamReader(var4);
                  } catch (IOException var33) {
                     break label176;
                  }
               } catch (Throwable var34) {
                  Object var9 = null;
                  if(var9 != null) {
                     try {
                        ((Reader)var9).close();
                     } catch (IOException var28) {
                        ;
                     }
                  }

                  throw var34;
               }

               try {
                  this.parse(var1, var5);
                  break label175;
               } catch (IOException var31) {
                  ;
               } finally {
                  ;
               }

               var3 = var5;
            }

            if(var3 == null) {
               return;
            }

            try {
               var3.close();
               return;
            } catch (IOException var29) {
               return;
            }
         }

         var3 = var5;
      }

      if(var3 != null) {
         try {
            var3.close();
         } catch (IOException var30) {
            ;
         }
      }
   }

   public void addMimeTypes(String var1) {
      synchronized(this){}

      try {
         if(debug) {
            System.out.println("MimetypesFileTypeMap: add to PROG");
         }

         try {
            Map var2 = this.mimetypes[0];
            StringReader var3 = new StringReader(var1);
            this.parse(var2, var3);
         } catch (IOException var8) {
            ;
         }
      } finally {
         ;
      }

   }

   public String getContentType(File var1) {
      String var2 = var1.getName();
      return this.getContentType(var2);
   }

   public String getContentType(String param1) {
      // $FF: Couldn't be decompiled
   }
}
