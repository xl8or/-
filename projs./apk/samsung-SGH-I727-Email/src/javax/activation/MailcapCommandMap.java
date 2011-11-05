package javax.activation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.activation.CommandInfo;
import javax.activation.CommandMap;
import javax.activation.DataContentHandler;

public class MailcapCommandMap extends CommandMap {

   private static final int DEF = 4;
   private static final int FALLBACK = 1;
   private static final int HOME = 1;
   private static final int JAR = 3;
   private static final int NORMAL = 0;
   private static final int PROG = 0;
   private static final int SYS = 2;
   private static boolean debug = 0;
   private Map[][] mailcaps;


   static {
      try {
         debug = Boolean.valueOf(System.getProperty("javax.activation.debug")).booleanValue();
      } catch (SecurityException var1) {
         ;
      }
   }

   public MailcapCommandMap() {
      this.init((Reader)null);
   }

   public MailcapCommandMap(InputStream var1) {
      InputStreamReader var2 = new InputStreamReader(var1);
      this.init(var2);
   }

   public MailcapCommandMap(String var1) throws IOException {
      FileReader var2 = null;

      label21: {
         FileReader var3;
         try {
            var3 = new FileReader(var1);
         } catch (IOException var7) {
            break label21;
         }

         var2 = var3;
      }

      this.init(var2);
      if(var2 != null) {
         try {
            var2.close();
         } catch (IOException var6) {
            ;
         }
      }
   }

   private void addField(Map var1, String var2, String var3, String var4) {
      String var6;
      String var8;
      label34: {
         if(var3 == null) {
            int var5 = var4.indexOf(61);
            if(var5 != -1) {
               var6 = var4.substring(0, var5);
               int var7 = var5 + 1;
               var8 = var4.substring(var7);
               break label34;
            }
         }

         var8 = var4;
         var6 = var3;
      }

      if(var8.length() != 0) {
         if(var6 != null) {
            if(var6.length() != 0) {
               Map var9 = (Map)var1.get(var2);
               Object var10;
               if(var9 == null) {
                  var10 = new LinkedHashMap();
                  var1.put(var2, var10);
               } else {
                  var10 = var9;
               }

               List var15 = (List)((Map)var10).get(var6);
               Object var16;
               if(var15 == null) {
                  ArrayList var12 = new ArrayList();
                  ((Map)var10).put(var6, var12);
                  var16 = var12;
               } else {
                  var16 = var15;
               }

               ((List)var16).add(var8);
            }
         }
      }
   }

   private void addNativeCommands(List var1, Map var2, String var3) {
      Iterator var4 = var2.entrySet().iterator();

      while(var4.hasNext()) {
         Entry var23 = (Entry)var4.next();
         if(((String)var23.getKey()).equals(var3)) {
            Map var24 = (Map)var23.getValue();
            String var5 = (String)var24.get("view-command");
            if(var5 != null) {
               StringBuffer var6 = new StringBuffer();
               var6.append(var3);
               StringBuffer var8 = var6.append(';');
               StringBuffer var9 = var6.append(' ');
               var6.append(var5);
               Iterator var11 = var24.entrySet().iterator();

               while(var11.hasNext()) {
                  Entry var12 = (Entry)var11.next();
                  String var13 = (String)var12.getKey();
                  List var25 = (List)var12.getValue();
                  if(!"view-command".equals(var13)) {
                     Iterator var14 = var25.iterator();

                     while(var14.hasNext()) {
                        String var15 = (String)var14.next();
                        StringBuffer var16 = var6.append(';');
                        StringBuffer var17 = var6.append(' ');
                        var6.append(var13);
                        StringBuffer var19 = var6.append('=');
                        var6.append(var15);
                     }
                  }
               }

               if(var6.length() > 0) {
                  String var21 = var6.toString();
                  var1.add(var21);
               }
            }
         }
      }

   }

   private Map getCommands(Map var1, String var2) {
      int var3 = var2.indexOf(47);
      String var4 = var2.substring(0, var3);
      String var5 = var4 + '/' + '*';
      Map var6 = (Map)var1.get(var2);
      Object var7 = (Map)var1.get(var5);
      if(var7 == null) {
         var7 = var6;
      } else if(var6 != null) {
         LinkedHashMap var8 = new LinkedHashMap();
         var8.putAll(var6);
         Iterator var9 = ((Map)var7).keySet().iterator();

         while(var9.hasNext()) {
            String var14 = (String)var9.next();
            List var10 = (List)((Map)var7).get(var14);
            List var11 = (List)var8.get(var14);
            if(var11 == null) {
               var8.put(var14, var10);
            } else {
               var11.addAll(var10);
            }
         }

         var7 = var8;
      }

      return (Map)var7;
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
      InputStreamReader var2 = null;
      InputStreamReader var3 = null;
      int[] var4 = new int[]{5, (int)var2};
      Map[][] var5 = (Map[][])Array.newInstance(Map.class, var4);
      this.mailcaps = var5;

      Object var6;
      int var10;
      for(var6 = var3; var6 < 5; ++var6) {
         for(byte var7 = 0; var7 < 2; var10 = var7 + 1) {
            Map[] var8 = this.mailcaps[var6];
            LinkedHashMap var9 = new LinkedHashMap();
            var8[var7] = var9;
         }
      }

      if(var1 != null) {
         if(debug) {
            System.out.println("MailcapCommandMap: load PROG");
         }

         var6 = null;

         try {
            this.parse((int)var6, var1);
         } catch (IOException var85) {
            ;
         }
      }

      if(debug) {
         System.out.println("MailcapCommandMap: load HOME");
      }

      try {
         String var11 = System.getProperty("user.home");
         if(var11 != null) {
            StringBuffer var12 = new StringBuffer(var11);
            char var13 = File.separatorChar;
            String var14 = var12.append(var13).append(".mailcap").toString();
            this.parseFile(1, var14);
         }
      } catch (SecurityException var84) {
         ;
      }

      if(debug) {
         System.out.println("MailcapCommandMap: load SYS");
      }

      byte var15 = 2;

      try {
         String var16 = System.getProperty("java.home");
         StringBuffer var17 = new StringBuffer(var16);
         char var18 = File.separatorChar;
         StringBuffer var19 = var17.append(var18).append("lib");
         char var20 = File.separatorChar;
         String var21 = var19.append(var20).append("mailcap").toString();
         this.parseFile(var15, var21);
      } catch (SecurityException var83) {
         ;
      }

      if(debug) {
         System.out.println("MailcapCommandMap: load JAR");
      }

      List var22 = this.getSystemResources("META-INF/mailcap");
      int var23 = var22.size();
      if(var23 > 0) {
         for(int var24 = 0; var24 < var23; ++var24) {
            var3 = null;
            URL var25 = (URL)var22.get(var24);
            boolean var79 = false;

            label553: {
               IOException var31;
               label554: {
                  try {
                     var79 = true;
                     if(debug) {
                        PrintStream var26 = System.out;
                        StringBuilder var27 = (new StringBuilder()).append("\t");
                        String var28 = var25.toString();
                        String var29 = var27.append(var28).toString();
                        var26.println(var29);
                     }

                     InputStream var30 = var25.openStream();
                     var2 = new InputStreamReader(var30);
                     var79 = false;
                  } catch (IOException var89) {
                     var31 = var89;
                     var79 = false;
                     break label554;
                  } finally {
                     if(var79) {
                        InputStreamReader var40 = var3;
                        if(var3 != null) {
                           try {
                              var40.close();
                           } catch (IOException var80) {
                              ;
                           }
                        }

                     }
                  }

                  byte var91 = 3;

                  try {
                     this.parse(var91, var2);
                     break label553;
                  } catch (IOException var87) {
                     var31 = var87;
                  } finally {
                     ;
                  }

                  var3 = var2;
               }

               try {
                  if(debug) {
                     PrintStream var32 = System.out;
                     StringBuilder var33 = new StringBuilder();
                     String var34 = var31.getClass().getName();
                     StringBuilder var35 = var33.append(var34).append(": ");
                     String var36 = var31.getMessage();
                     String var37 = var35.append(var36).toString();
                     var32.println(var37);
                  }
               } finally {
                  ;
               }

               if(var3 != null) {
                  try {
                     var3.close();
                  } catch (IOException var81) {
                     ;
                  }
               }
               continue;
            }

            if(var2 != null) {
               try {
                  var2.close();
               } catch (IOException var82) {
                  ;
               }
            }
         }
      } else {
         this.parseResource(3, "/META-INF/mailcap");
      }

      if(debug) {
         System.out.println("MailcapCommandMap: load DEF");
      }

      this.parseResource(4, "/META-INF/mailcap.default");
   }

   private static String nameOf(int var0) {
      String var1;
      switch(var0) {
      case 0:
         var1 = "PROG";
         break;
      case 1:
         var1 = "HOME";
         break;
      case 2:
         var1 = "SYS";
         break;
      case 3:
         var1 = "JAR";
         break;
      case 4:
         var1 = "DEF";
         break;
      default:
         var1 = "ERR";
      }

      return var1;
   }

   private void parse(int var1, Reader var2) throws IOException {
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

   private void parseEntry(int var1, String var2) {
      char[] var3 = var2.toCharArray();
      int var4 = var3.length;
      boolean var5 = false;
      StringBuffer var6 = new StringBuffer();
      ArrayList var7 = new ArrayList();
      byte var8 = 0;
      boolean var9 = false;

      int var20;
      for(int var10 = var8; var10 < var4; var10 = var20) {
         char var11 = var3[var10];
         int var14;
         char var15;
         if(var11 == 92) {
            int var12 = var10 + 1;
            char var13 = var3[var12];
            var14 = var12;
            var15 = var13;
         } else {
            var14 = var10;
            var15 = var11;
         }

         boolean var19;
         boolean var18;
         if(var15 == 59 && !var9) {
            String var16 = var6.toString().trim();
            if("x-java-fallback-entry".equals(var16)) {
               var5 = true;
            }

            var7.add(var16);
            var6.setLength(0);
            var18 = var5;
            var19 = var9;
         } else {
            if(var15 == 34) {
               if(!var9) {
                  var9 = true;
               } else {
                  var9 = false;
               }
            }

            var6.append(var15);
            var18 = var5;
            var19 = var9;
         }

         var20 = var14 + 1;
         var9 = var19;
         var5 = var18;
      }

      String var22 = var6.toString().trim();
      boolean var23;
      if("x-java-fallback-entry".equals(var22)) {
         var23 = true;
      } else {
         var23 = var5;
      }

      var7.add(var22);
      int var25 = var7.size();
      if(var25 < 2) {
         if(debug) {
            PrintStream var26 = System.err;
            String var27 = "Invalid mailcap entry: " + var2;
            var26.println(var27);
         }
      } else {
         Map var33;
         if(var23) {
            var33 = this.mailcaps[var1][1];
         } else {
            var33 = this.mailcaps[var1][0];
         }

         String var32 = (String)var7.get(0);
         String var28 = (String)var7.get(1);
         this.addField(var33, var32, "view-command", var28);

         for(int var29 = 2; var29 < var25; ++var29) {
            String var30 = (String)var7.get(var29);
            this.addField(var33, var32, (String)null, var30);
         }

      }
   }

   private void parseFile(int param1, String param2) {
      // $FF: Couldn't be decompiled
   }

   private void parseResource(int param1, String param2) {
      // $FF: Couldn't be decompiled
   }

   public void addMailcap(String param1) {
      // $FF: Couldn't be decompiled
   }

   public DataContentHandler createDataContentHandler(String param1) {
      // $FF: Couldn't be decompiled
   }

   public CommandInfo[] getAllCommands(String var1) {
      synchronized(this){}

      try {
         ArrayList var2 = new ArrayList();

         for(int var3 = 0; var3 < 2; ++var3) {
            for(int var4 = 0; var4 < 5; ++var4) {
               Map var5 = this.mailcaps[var4][var3];
               Map var6 = this.getCommands(var5, var1);
               if(var6 != null) {
                  Iterator var7 = var6.entrySet().iterator();

                  while(var7.hasNext()) {
                     Entry var8 = (Entry)var7.next();
                     String var9 = (String)var8.getKey();
                     List var20 = (List)var8.getValue();
                     int var10 = var20.size();

                     for(int var11 = 0; var11 < var10; ++var11) {
                        String var12 = (String)var20.get(var11);
                        CommandInfo var13 = new CommandInfo(var9, var12);
                        var2.add(var13);
                     }
                  }
               }
            }
         }

         CommandInfo[] var19 = new CommandInfo[var2.size()];
         var2.toArray(var19);
         return var19;
      } finally {
         ;
      }
   }

   public CommandInfo getCommand(String var1, String var2) {
      synchronized(this){}
      int var3 = 0;

      CommandInfo var15;
      while(var3 < 2) {
         int var4 = 0;

         while(true) {
            if(var4 < 5) {
               boolean var12 = false;

               CommandInfo var14;
               label78: {
                  try {
                     var12 = true;
                     Map var5 = this.mailcaps[var4][var3];
                     Map var6 = this.getCommands(var5, var1);
                     if(var6 != null) {
                        List var7 = (List)var6.get(var2);
                        if(var7 == null) {
                           String var8 = "x-java-" + var2;
                           var7 = (List)var6.get(var8);
                        }

                        if(var7 != null) {
                           String var9 = (String)var7.get(0);
                           var14 = new CommandInfo(var2, var9);
                           var12 = false;
                           break label78;
                        }

                        var12 = false;
                     } else {
                        var12 = false;
                     }
                  } finally {
                     if(var12) {
                        ;
                     }
                  }

                  ++var4;
                  continue;
               }

               var15 = var14;
               return var15;
            }

            ++var3;
            break;
         }
      }

      var15 = null;
      return var15;
   }

   public String[] getNativeCommands(String var1) {
      ArrayList var2 = new ArrayList();

      for(int var3 = 0; var3 < 2; ++var3) {
         for(int var4 = 0; var4 < 5; ++var4) {
            Map var5 = this.mailcaps[var4][var3];
            this.addNativeCommands(var2, var5, var1);
         }
      }

      String[] var6 = new String[var2.size()];
      var2.toArray(var6);
      return var6;
   }

   public CommandInfo[] getPreferredCommands(String param1) {
      // $FF: Couldn't be decompiled
   }
}
