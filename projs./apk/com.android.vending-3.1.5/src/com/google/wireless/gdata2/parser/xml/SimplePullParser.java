package com.google.wireless.gdata2.parser.xml;

import android.util.Log;
import android.util.Xml;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class SimplePullParser {

   public static final String TEXT_TAG = "![CDATA[";
   private String mCurrentStartTag;
   private String mLogTag;
   private final XmlPullParser mParser;
   private Closeable source;


   public SimplePullParser(InputStream var1, String var2) throws SimplePullParser.ParseException, IOException {
      this.mLogTag = null;

      try {
         XmlPullParser var3 = Xml.newPullParser();
         var3.setInput(var1, var2);
         moveToStartDocument(var3);
         this.mParser = var3;
         this.mCurrentStartTag = null;
         this.source = var1;
      } catch (XmlPullParserException var5) {
         throw new SimplePullParser.ParseException(var5);
      }
   }

   public SimplePullParser(Reader var1) throws IOException, SimplePullParser.ParseException {
      this.mLogTag = null;

      try {
         XmlPullParser var2 = Xml.newPullParser();
         var2.setInput(var1);
         moveToStartDocument(var2);
         this.mParser = var2;
         this.mCurrentStartTag = null;
         this.source = var1;
      } catch (XmlPullParserException var4) {
         throw new SimplePullParser.ParseException(var4);
      }
   }

   public SimplePullParser(String var1) throws IOException, SimplePullParser.ParseException {
      StringReader var2 = new StringReader(var1);
      this((Reader)var2);
   }

   public SimplePullParser(XmlPullParser var1) {
      this.mLogTag = null;
      this.mParser = var1;
      this.mCurrentStartTag = null;
      this.source = null;
   }

   private static void moveToStartDocument(XmlPullParser var0) throws XmlPullParserException, IOException {
      if(var0.getEventType() != 0) {
         throw new XmlPullParserException("Not at start of response");
      }
   }

   public void close() {
      if(this.source != null) {
         try {
            this.source.close();
         } catch (IOException var2) {
            ;
         }
      }
   }

   public String getAttributeName(int var1) {
      return this.mParser.getAttributeName(var1);
   }

   public String getAttributeNamespace(int var1) {
      return this.mParser.getAttributeNamespace(var1);
   }

   public int getDepth() {
      return this.mParser.getDepth();
   }

   public int getIntAttribute(String var1, String var2) throws SimplePullParser.ParseException {
      String var3 = this.getStringAttribute(var1, var2);

      try {
         int var4 = Integer.parseInt(var3);
         return var4;
      } catch (NumberFormatException var7) {
         String var6 = "Cannot parse \'" + var3 + "\' as an integer";
         throw new SimplePullParser.ParseException(var6);
      }
   }

   public int getIntAttribute(String var1, String var2, int var3) throws SimplePullParser.ParseException {
      String var4 = this.mParser.getAttributeValue(var1, var2);
      if(var4 != null) {
         int var5;
         try {
            var5 = Integer.parseInt(var4);
         } catch (NumberFormatException var8) {
            String var7 = "Cannot parse \'" + var4 + "\' as an integer";
            throw new SimplePullParser.ParseException(var7);
         }

         var3 = var5;
      }

      return var3;
   }

   public long getLongAttribute(String var1, String var2) throws SimplePullParser.ParseException {
      String var3 = this.getStringAttribute(var1, var2);

      try {
         long var4 = Long.parseLong(var3);
         return var4;
      } catch (NumberFormatException var8) {
         String var7 = "Cannot parse \'" + var3 + "\' as a long";
         throw new SimplePullParser.ParseException(var7);
      }
   }

   public long getLongAttribute(String var1, String var2, long var3) throws SimplePullParser.ParseException {
      String var5 = this.mParser.getAttributeValue(var1, var2);
      if(var5 != null) {
         long var6;
         try {
            var6 = Long.parseLong(var5);
         } catch (NumberFormatException var10) {
            String var9 = "Cannot parse \'" + var5 + "\' as a long";
            throw new SimplePullParser.ParseException(var9);
         }

         var3 = var6;
      }

      return var3;
   }

   public String getStringAttribute(String var1, String var2) throws SimplePullParser.ParseException {
      String var3 = this.mParser.getAttributeValue(var1, var2);
      if(var3 == null) {
         StringBuilder var4 = (new StringBuilder()).append("missing \'").append(var2).append("\' attribute on \'");
         String var5 = this.mCurrentStartTag;
         String var6 = var4.append(var5).append("\' element").toString();
         throw new SimplePullParser.ParseException(var6);
      } else {
         return var3;
      }
   }

   public String getStringAttribute(String var1, String var2, String var3) {
      String var4 = this.mParser.getAttributeValue(var1, var2);
      if(var4 != null) {
         var3 = var4;
      }

      return var3;
   }

   public String nextTag(int var1) throws IOException, SimplePullParser.ParseException {
      return this.nextTagOrText(var1, (StringBuilder)null);
   }

   public String nextTagOrText(int var1, StringBuilder var2) throws IOException, SimplePullParser.ParseException {
      String var3 = null;

      while(true) {
         int var4;
         try {
            var4 = this.mParser.next();
         } catch (XmlPullParserException var37) {
            throw new SimplePullParser.ParseException(var37);
         }

         int var6 = this.mParser.getDepth();
         this.mCurrentStartTag = null;
         StringBuilder var9;
         if(var4 == 2) {
            int var7 = var1 + 1;
            if(var6 == var7) {
               String var8 = this.mParser.getName();
               this.mCurrentStartTag = var8;
               if(this.mLogTag != null && Log.isLoggable(this.mLogTag, 3)) {
                  var9 = new StringBuilder();

                  for(int var10 = 0; var10 < var6; ++var10) {
                     StringBuilder var11 = var9.append("  ");
                  }

                  StringBuilder var13 = var9.append("<");
                  String var14 = this.mParser.getName();
                  var13.append(var14);
                  int var16 = this.mParser.getAttributeCount();

                  for(int var38 = 0; var38 < var16; ++var38) {
                     StringBuilder var18 = var9.append(" ");
                     String var19 = this.mParser.getAttributeName(var38);
                     var9.append(var19);
                     StringBuilder var21 = var9.append("=\"");
                     String var22 = this.mParser.getAttributeValue(var38);
                     var9.append(var22);
                     StringBuilder var24 = var9.append("\"");
                  }

                  StringBuilder var25 = var9.append(">");
                  String var26 = this.mLogTag;
                  String var27 = var9.toString();
                  Log.d(var26, var27);
               }

               var3 = this.mParser.getName();
               break;
            }
         }

         if(var4 == 3 && var6 == var1) {
            if(this.mLogTag != null && Log.isLoggable(this.mLogTag, 3)) {
               var9 = new StringBuilder();

               int var30;
               for(byte var17 = 0; var17 < var6; var30 = var17 + 1) {
                  StringBuilder var29 = var9.append("  ");
               }

               StringBuilder var31 = var9.append("</>");
               String var32 = this.mLogTag;
               String var33 = var9.toString();
               Log.d(var32, var33);
            }
            break;
         }

         if(var4 == 1 && var1 == 0) {
            if(this.source != null) {
               this.source.close();
               this.source = null;
            }
            break;
         }

         if(var4 == 4 && var6 == var1 && var2 != null) {
            String var35 = this.mParser.getText();
            var2.append(var35);
            var3 = "![CDATA[";
            break;
         }
      }

      return var3;
   }

   public int numAttributes() {
      return this.mParser.getAttributeCount();
   }

   public void readRemainingText(int var1, StringBuilder var2) throws IOException, SimplePullParser.ParseException {
      while(this.nextTagOrText(var1, var2) != null) {
         ;
      }

   }

   public void setLogTag(String var1) {
      this.mLogTag = var1;
   }

   public static final class ParseException extends Exception {

      public ParseException(String var1) {
         super(var1);
      }

      public ParseException(String var1, Throwable var2) {
         super(var1, var2);
      }

      public ParseException(Throwable var1) {
         super(var1);
      }
   }
}
