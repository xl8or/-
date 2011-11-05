package com.android.settings.flipfont;

import com.android.settings.flipfont.Typeface;
import com.android.settings.flipfont.TypefaceFile;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class TypefaceParser extends DefaultHandler {

   private static final String ATTR_NAME = "displayname";
   private static final String NODE_DROIDNAME = "droidname";
   private static final String NODE_FILE = "file";
   private static final String NODE_FILENAME = "filename";
   private static final String NODE_FONT = "font";
   private static final String NODE_MONOSPACE = "monospace";
   private static final String NODE_SANS = "sans";
   private static final String NODE_SERIF = "serif";
   private boolean in_droidname = 0;
   private boolean in_file = 0;
   private boolean in_filename = 0;
   private boolean in_font = 0;
   private boolean in_monospace = 0;
   private boolean in_sans = 0;
   private boolean in_serif = 0;
   private Typeface mFont = null;
   private TypefaceFile mFontFile = null;


   public TypefaceParser() {}

   public void characters(char[] var1, int var2, int var3) {
      if(this.in_filename) {
         TypefaceFile var4 = this.mFontFile;
         String var5 = new String(var1, var2, var3);
         var4.setFileName(var5);
      } else if(this.in_droidname) {
         TypefaceFile var6 = this.mFontFile;
         String var7 = new String(var1, var2, var3);
         var6.setDroidName(var7);
      }
   }

   public void endDocument() throws SAXException {}

   public void endElement(String var1, String var2, String var3) throws SAXException {
      if(var2.equals("font")) {
         this.in_font = (boolean)0;
      } else if(var2.equals("sans")) {
         this.in_sans = (boolean)0;
      } else if(var2.equals("serif")) {
         this.in_serif = (boolean)0;
      } else if(var2.equals("monospace")) {
         this.in_monospace = (boolean)0;
      } else if(var2.equals("file")) {
         this.in_file = (boolean)0;
         if(this.mFontFile != null) {
            if(this.in_sans) {
               List var4 = this.mFont.mSansFonts;
               TypefaceFile var5 = this.mFontFile;
               var4.add(var5);
            } else if(this.in_serif) {
               List var7 = this.mFont.mSerifFonts;
               TypefaceFile var8 = this.mFontFile;
               var7.add(var8);
            } else if(this.in_monospace) {
               List var10 = this.mFont.mMonospaceFonts;
               TypefaceFile var11 = this.mFontFile;
               var10.add(var11);
            }
         }
      } else if(var2.equals("filename")) {
         this.in_filename = (boolean)0;
      } else if(var2.equals("droidname")) {
         this.in_droidname = (boolean)0;
      }
   }

   public Typeface getParsedData() {
      return this.mFont;
   }

   public void startDocument() throws SAXException {
      Typeface var1 = new Typeface();
      this.mFont = var1;
   }

   public void startElement(String var1, String var2, String var3, Attributes var4) throws SAXException {
      if(var2.equals("font")) {
         this.in_font = (boolean)1;
         String var5 = var4.getValue("displayname");
         this.mFont.setName(var5);
      } else if(var2.equals("sans")) {
         this.in_sans = (boolean)1;
      } else if(var2.equals("serif")) {
         this.in_serif = (boolean)1;
      } else if(var2.equals("monospace")) {
         this.in_monospace = (boolean)1;
      } else if(var2.equals("file")) {
         this.in_file = (boolean)1;
         TypefaceFile var6 = new TypefaceFile();
         this.mFontFile = var6;
      } else if(var2.equals("filename")) {
         this.in_filename = (boolean)1;
      } else if(var2.equals("droidname")) {
         this.in_droidname = (boolean)1;
      }
   }
}
