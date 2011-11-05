package org.acra;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map.Entry;
import org.acra.ReportField;

public class CrashReportData extends EnumMap<ReportField, String> {

   private static final int CONTINUE = 3;
   private static final int IGNORE = 5;
   private static final int KEY_DONE = 4;
   private static final int NONE = 0;
   private static final String PROP_DTD_NAME = "http://java.sun.com/dtd/properties.dtd";
   private static final int SLASH = 1;
   private static final int UNICODE = 2;
   private static String lineSeparator = "\n";
   private static final long serialVersionUID = 4112578634029874840L;
   protected CrashReportData defaults;


   public CrashReportData() {
      super(ReportField.class);
   }

   public CrashReportData(CrashReportData var1) {
      super(ReportField.class);
      this.defaults = var1;
   }

   private void dumpString(StringBuilder var1, String var2, boolean var3) {
      int var4 = 0;
      if(!var3) {
         int var5 = var2.length();
         if(0 < var5 && var2.charAt(0) == 32) {
            StringBuilder var6 = var1.append("\\ ");
            var4 = 0 + 1;
         }
      }

      while(true) {
         int var7 = var2.length();
         if(var4 >= var7) {
            return;
         }

         char var8 = var2.charAt(var4);
         label49:
         switch(var8) {
         case 9:
            StringBuilder var11 = var1.append("\\t");
            break;
         case 10:
            StringBuilder var12 = var1.append("\\n");
            break;
         case 11:
         default:
            if("\\#!=:".indexOf(var8) >= 0 || var3 && var8 == 32) {
               StringBuilder var9 = var1.append('\\');
            }

            if(var8 >= 32 && var8 <= 126) {
               var1.append(var8);
               break;
            } else {
               String var15 = Integer.toHexString(var8);
               StringBuilder var16 = var1.append("\\u");
               int var17 = 0;

               while(true) {
                  int var18 = var15.length();
                  int var19 = 4 - var18;
                  if(var17 >= var19) {
                     var1.append(var15);
                     break label49;
                  }

                  StringBuilder var20 = var1.append("0");
                  ++var17;
               }
            }
         case 12:
            StringBuilder var13 = var1.append("\\f");
            break;
         case 13:
            StringBuilder var14 = var1.append("\\r");
         }

         ++var4;
      }
   }

   private boolean isEbcdic(BufferedInputStream var1) throws IOException {
      while(true) {
         byte var2 = (byte)var1.read();
         boolean var3;
         if(var2 != -1) {
            if(var2 != 35 && var2 != 10 && var2 != 61) {
               if(var2 != 21) {
                  continue;
               }

               var3 = true;
            } else {
               var3 = false;
            }
         } else {
            var3 = false;
         }

         return var3;
      }
   }

   private Enumeration<ReportField> keys() {
      return Collections.enumeration(this.keySet());
   }

   private String substitutePredefinedEntries(String var1) {
      return var1.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\'", "&apos;").replaceAll("\"", "&quot;");
   }

   public String getProperty(ReportField var1) {
      String var2 = (String)super.get(var1);
      if(var2 == null && this.defaults != null) {
         var2 = this.defaults.getProperty(var1);
      }

      return var2;
   }

   public String getProperty(ReportField var1, String var2) {
      String var3 = (String)super.get(var1);
      if(var3 == null && this.defaults != null) {
         var3 = this.defaults.getProperty(var1);
      }

      String var4;
      if(var3 == null) {
         var4 = var2;
      } else {
         var4 = var3;
      }

      return var4;
   }

   public void list(PrintStream var1) {
      if(var1 == null) {
         throw new NullPointerException();
      } else {
         StringBuilder var2 = new StringBuilder(80);
         Enumeration var3 = this.keys();

         while(var3.hasMoreElements()) {
            ReportField var4 = (ReportField)var3.nextElement();
            var2.append(var4);
            StringBuilder var6 = var2.append('=');
            String var7 = (String)super.get(var4);

            for(CrashReportData var8 = this.defaults; var7 == null; var8 = var8.defaults) {
               var7 = (String)var8.get(var4);
            }

            if(var7.length() > 40) {
               String var9 = var7.substring(0, 37);
               var2.append(var9);
               StringBuilder var11 = var2.append("...");
            } else {
               var2.append(var7);
            }

            String var12 = var2.toString();
            var1.println(var12);
            var2.setLength(0);
         }

      }
   }

   public void list(PrintWriter var1) {
      if(var1 == null) {
         throw new NullPointerException();
      } else {
         StringBuilder var2 = new StringBuilder(80);
         Enumeration var3 = this.keys();

         while(var3.hasMoreElements()) {
            ReportField var4 = (ReportField)var3.nextElement();
            var2.append(var4);
            StringBuilder var6 = var2.append('=');
            String var7 = (String)super.get(var4);

            for(CrashReportData var8 = this.defaults; var7 == null; var8 = var8.defaults) {
               var7 = (String)var8.get(var4);
            }

            if(var7.length() > 40) {
               String var9 = var7.substring(0, 37);
               var2.append(var9);
               StringBuilder var11 = var2.append("...");
            } else {
               var2.append(var7);
            }

            String var12 = var2.toString();
            var1.println(var12);
            var2.setLength(0);
         }

      }
   }

   public void load(InputStream param1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public void load(Reader param1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   @Deprecated
   public void save(OutputStream var1, String var2) {
      try {
         this.store(var1, var2);
      } catch (IOException var4) {
         ;
      }
   }

   public Object setProperty(ReportField var1, String var2) {
      return this.put(var1, var2);
   }

   public void store(OutputStream var1, String var2) throws IOException {
      synchronized(this){}

      try {
         StringBuilder var3 = new StringBuilder(200);
         OutputStreamWriter var4 = new OutputStreamWriter(var1, "ISO8859_1");
         if(var2 != null) {
            var4.write("#");
            var4.write(var2);
            String var5 = lineSeparator;
            var4.write(var5);
         }

         Iterator var6 = this.entrySet().iterator();

         while(var6.hasNext()) {
            Entry var7 = (Entry)var6.next();
            String var8 = ((ReportField)var7.getKey()).toString();
            this.dumpString(var3, var8, (boolean)1);
            StringBuilder var9 = var3.append('=');
            String var10 = (String)var7.getValue();
            this.dumpString(var3, var10, (boolean)0);
            String var11 = lineSeparator;
            var3.append(var11);
            String var13 = var3.toString();
            var4.write(var13);
            var3.setLength(0);
         }

         var4.flush();
      } finally {
         ;
      }
   }

   public void store(Writer var1, String var2) throws IOException {
      synchronized(this){}

      try {
         StringBuilder var3 = new StringBuilder(200);
         if(var2 != null) {
            var1.write("#");
            var1.write(var2);
            String var4 = lineSeparator;
            var1.write(var4);
         }

         Iterator var5 = this.entrySet().iterator();

         while(var5.hasNext()) {
            Entry var6 = (Entry)var5.next();
            String var7 = ((ReportField)var6.getKey()).toString();
            this.dumpString(var3, var7, (boolean)1);
            StringBuilder var8 = var3.append('=');
            String var9 = (String)var6.getValue();
            this.dumpString(var3, var9, (boolean)0);
            String var10 = lineSeparator;
            var3.append(var10);
            String var12 = var3.toString();
            var1.write(var12);
            var3.setLength(0);
         }

         var1.flush();
      } finally {
         ;
      }
   }

   public void storeToXML(OutputStream var1, String var2) throws IOException {
      this.storeToXML(var1, var2, "UTF-8");
   }

   public void storeToXML(OutputStream param1, String param2, String param3) throws IOException {
      // $FF: Couldn't be decompiled
   }
}
