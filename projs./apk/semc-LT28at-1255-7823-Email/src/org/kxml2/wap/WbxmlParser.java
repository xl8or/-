package org.kxml2.wap;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Hashtable;
import java.util.Vector;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class WbxmlParser implements XmlPullParser {

   static final String HEX_DIGITS = "0123456789abcdef";
   private static final String ILLEGAL_TYPE = "Wrong event type";
   private static final String UNEXPECTED_EOF = "Unexpected EOF";
   public static final int WAP_EXTENSION = 64;
   private int ATTR_START_TABLE = 1;
   private int ATTR_VALUE_TABLE = 2;
   private int TAG_TABLE = 0;
   private String[] attrStartTable;
   private String[] attrValueTable;
   private int attributeCount;
   private String[] attributes;
   private Hashtable cacheStringTable = null;
   private boolean degenerated;
   private int depth;
   private String[] elementStack;
   private String encoding;
   private InputStream in;
   private boolean isWhitespace;
   private String name;
   private String namespace;
   private int nextId;
   private int[] nspCounts;
   private String[] nspStack;
   private String prefix;
   private boolean processNsp;
   private int publicIdentifierId;
   private byte[] stringTable;
   private Vector tables;
   private String[] tagTable;
   private String text;
   private int type;
   private int version;
   private int wapCode;
   private Object wapExtensionData;


   public WbxmlParser() {
      String[] var1 = new String[16];
      this.elementStack = var1;
      String[] var2 = new String[8];
      this.nspStack = var2;
      int[] var3 = new int[4];
      this.nspCounts = var3;
      String[] var4 = new String[16];
      this.attributes = var4;
      this.nextId = -1;
      Vector var5 = new Vector();
      this.tables = var5;
   }

   private final boolean adjustNsp() throws XmlPullParserException {
      boolean var1 = false;
      int var2 = 0;

      while(true) {
         int var3 = this.attributeCount << 2;
         if(var2 >= var3) {
            if(var1) {
               for(var2 = (this.attributeCount << 2) - 4; var2 >= 0; var2 += -4) {
                  String[] var30 = this.attributes;
                  int var31 = var2 + 2;
                  String var32 = var30[var31];
                  int var33 = var32.indexOf(58);
                  if(var33 == 0) {
                     String var34 = "illegal attribute name: " + var32 + " at " + this;
                     throw new RuntimeException(var34);
                  }

                  if(var33 != -1) {
                     String var35 = var32.substring(0, var33);
                     int var36 = var33 + 1;
                     var32 = var32.substring(var36);
                     String var37 = this.getNamespace(var35);
                     if(var37 == null) {
                        String var38 = "Undefined Prefix: " + var35 + " in " + this;
                        throw new RuntimeException(var38);
                     }

                     this.attributes[var2] = var37;
                     String[] var39 = this.attributes;
                     int var40 = var2 + 1;
                     var39[var40] = var35;
                     String[] var41 = this.attributes;
                     int var42 = var2 + 2;
                     var41[var42] = var32;

                     for(int var43 = (this.attributeCount << 2) - 4; var43 > var2; var43 += -4) {
                        String[] var44 = this.attributes;
                        int var45 = var43 + 2;
                        String var46 = var44[var45];
                        if(var32.equals(var46)) {
                           String var47 = this.attributes[var43];
                           if(var37.equals(var47)) {
                              String var48 = "Duplicate Attribute: {" + var37 + "}" + var32;
                              this.exception(var48);
                           }
                        }
                     }
                  }
               }
            }

            int var49 = this.name.indexOf(58);
            if(var49 == 0) {
               StringBuilder var50 = (new StringBuilder()).append("illegal tag name: ");
               String var51 = this.name;
               String var52 = var50.append(var51).toString();
               this.exception(var52);
            } else if(var49 != -1) {
               String var58 = this.name.substring(0, var49);
               this.prefix = var58;
               String var59 = this.name;
               int var60 = var49 + 1;
               String var61 = var59.substring(var60);
               this.name = var61;
            }

            String var53 = this.prefix;
            String var54 = this.getNamespace(var53);
            this.namespace = var54;
            if(this.namespace == null) {
               if(this.prefix != null) {
                  StringBuilder var55 = (new StringBuilder()).append("undefined prefix: ");
                  String var56 = this.prefix;
                  String var57 = var55.append(var56).toString();
                  this.exception(var57);
               }

               this.namespace = "";
            }

            return var1;
         }

         label79: {
            String[] var4 = this.attributes;
            int var5 = var2 + 2;
            String var6 = var4[var5];
            int var7 = var6.indexOf(58);
            String var8;
            if(var7 != -1) {
               var8 = var6.substring(0, var7);
               int var9 = var7 + 1;
               var6 = var6.substring(var9);
            } else {
               if(!var6.equals("xmlns")) {
                  break label79;
               }

               var8 = var6;
               var6 = null;
            }

            if(!var8.equals("xmlns")) {
               var1 = true;
            } else {
               int[] var10 = this.nspCounts;
               int var11 = this.depth;
               int var12 = var10[var11];
               int var13 = var12 + 1;
               var10[var11] = var13;
               int var14 = var12 << 1;
               String[] var15 = this.nspStack;
               int var16 = var14 + 2;
               String[] var17 = this.ensureCapacity(var15, var16);
               this.nspStack = var17;
               this.nspStack[var14] = var6;
               String[] var18 = this.nspStack;
               int var19 = var14 + 1;
               String[] var20 = this.attributes;
               int var21 = var2 + 3;
               String var22 = var20[var21];
               var18[var19] = var22;
               if(var6 != null) {
                  String[] var23 = this.attributes;
                  int var24 = var2 + 3;
                  if(var23[var24].equals("")) {
                     this.exception("illegal empty namespace");
                  }
               }

               String[] var25 = this.attributes;
               int var26 = var2 + 4;
               String[] var27 = this.attributes;
               int var28 = this.attributeCount - 1;
               this.attributeCount = var28;
               int var29 = (var28 << 2) - var2;
               System.arraycopy(var25, var26, var27, var2, var29);
               var2 += -4;
            }
         }

         var2 += 4;
      }
   }

   private final String[] ensureCapacity(String[] var1, int var2) {
      String[] var3;
      if(var1.length >= var2) {
         var3 = var1;
      } else {
         String[] var4 = new String[var2 + 16];
         int var5 = var1.length;
         System.arraycopy(var1, 0, var4, 0, var5);
         var3 = var4;
      }

      return var3;
   }

   private final void exception(String var1) throws XmlPullParserException {
      throw new XmlPullParserException(var1, this, (Throwable)null);
   }

   private final void nextImpl() throws IOException, XmlPullParserException {
      if(this.type == 3) {
         int var1 = this.depth - 1;
         this.depth = var1;
      }

      if(this.degenerated) {
         this.type = 3;
         this.degenerated = (boolean)0;
      } else {
         this.text = null;
         this.prefix = null;
         this.name = null;

         int var2;
         for(var2 = this.peekId(); var2 == 0; var2 = this.peekId()) {
            this.nextId = -1;
            int var3 = this.readByte();
            this.selectPage(var3, (boolean)1);
         }

         this.nextId = -1;
         switch(var2) {
         case -1:
            this.type = 1;
            return;
         case 1:
            int var4 = this.depth - 1 << 2;
            this.type = 3;
            String var5 = this.elementStack[var4];
            this.namespace = var5;
            String[] var6 = this.elementStack;
            int var7 = var4 + 1;
            String var8 = var6[var7];
            this.prefix = var8;
            String[] var9 = this.elementStack;
            int var10 = var4 + 2;
            String var11 = var9[var10];
            this.name = var11;
            return;
         case 2:
            this.type = 6;
            char var12 = (char)this.readInt();
            String var13 = "" + var12;
            this.text = var13;
            String var14 = "#" + var12;
            this.name = var14;
            return;
         case 3:
            this.type = 4;
            String var15 = this.readStrI();
            this.text = var15;
            return;
         case 64:
         case 65:
         case 66:
         case 128:
         case 129:
         case 130:
         case 192:
         case 193:
         case 194:
         case 195:
            this.type = 64;
            this.wapCode = var2;
            Object var16 = this.parseWapExtension(var2);
            this.wapExtensionData = var16;
            return;
         case 67:
            throw new RuntimeException("PI curr. not supp.");
         case 131:
            this.type = 4;
            String var17 = this.readStrT();
            this.text = var17;
            return;
         default:
            this.parseElement(var2);
         }
      }
   }

   private int peekId() throws IOException {
      if(this.nextId == -1) {
         int var1 = this.in.read();
         this.nextId = var1;
      }

      return this.nextId;
   }

   private void selectPage(int var1, boolean var2) throws XmlPullParserException {
      if(this.tables.size() != 0 || var1 != 0) {
         int var3 = var1 * 3;
         int var4 = this.tables.size();
         if(var3 > var4) {
            String var5 = "Code Page " + var1 + " undefined!";
            this.exception(var5);
         }

         if(var2) {
            Vector var6 = this.tables;
            int var7 = var1 * 3;
            int var8 = this.TAG_TABLE;
            int var9 = var7 + var8;
            String[] var10 = (String[])((String[])var6.elementAt(var9));
            this.tagTable = var10;
         } else {
            Vector var11 = this.tables;
            int var12 = var1 * 3;
            int var13 = this.ATTR_START_TABLE;
            int var14 = var12 + var13;
            String[] var15 = (String[])((String[])var11.elementAt(var14));
            this.attrStartTable = var15;
            Vector var16 = this.tables;
            int var17 = var1 * 3;
            int var18 = this.ATTR_VALUE_TABLE;
            int var19 = var17 + var18;
            String[] var20 = (String[])((String[])var16.elementAt(var19));
            this.attrValueTable = var20;
         }
      }
   }

   private final void setTable(int var1, int var2, String[] var3) {
      if(this.stringTable != null) {
         throw new RuntimeException("setXxxTable must be called before setInput!");
      } else {
         while(true) {
            int var4 = this.tables.size();
            int var5 = var1 * 3 + 3;
            if(var4 >= var5) {
               Vector var6 = this.tables;
               int var7 = var1 * 3 + var2;
               var6.setElementAt(var3, var7);
               return;
            }

            this.tables.addElement((Object)null);
         }
      }
   }

   public void defineEntityReplacementText(String var1, String var2) throws XmlPullParserException {}

   public int getAttributeCount() {
      return this.attributeCount;
   }

   public String getAttributeName(int var1) {
      int var2 = this.attributeCount;
      if(var1 >= var2) {
         throw new IndexOutOfBoundsException();
      } else {
         String[] var3 = this.attributes;
         int var4 = (var1 << 2) + 2;
         return var3[var4];
      }
   }

   public String getAttributeNamespace(int var1) {
      int var2 = this.attributeCount;
      if(var1 >= var2) {
         throw new IndexOutOfBoundsException();
      } else {
         String[] var3 = this.attributes;
         int var4 = var1 << 2;
         return var3[var4];
      }
   }

   public String getAttributePrefix(int var1) {
      int var2 = this.attributeCount;
      if(var1 >= var2) {
         throw new IndexOutOfBoundsException();
      } else {
         String[] var3 = this.attributes;
         int var4 = (var1 << 2) + 1;
         return var3[var4];
      }
   }

   public String getAttributeType(int var1) {
      return "CDATA";
   }

   public String getAttributeValue(int var1) {
      int var2 = this.attributeCount;
      if(var1 >= var2) {
         throw new IndexOutOfBoundsException();
      } else {
         String[] var3 = this.attributes;
         int var4 = (var1 << 2) + 3;
         return var3[var4];
      }
   }

   public String getAttributeValue(String var1, String var2) {
      int var3 = (this.attributeCount << 2) - 4;

      String var8;
      while(true) {
         if(var3 < 0) {
            var8 = null;
            break;
         }

         String[] var4 = this.attributes;
         int var5 = var3 + 2;
         if(var4[var5].equals(var2) && (var1 == null || this.attributes[var3].equals(var1))) {
            String[] var6 = this.attributes;
            int var7 = var3 + 3;
            var8 = var6[var7];
            break;
         }

         var3 += -4;
      }

      return var8;
   }

   public int getColumnNumber() {
      return -1;
   }

   public int getDepth() {
      return this.depth;
   }

   public int getEventType() throws XmlPullParserException {
      return this.type;
   }

   public boolean getFeature(String var1) {
      boolean var2;
      if("http://xmlpull.org/v1/doc/features.html#process-namespaces".equals(var1)) {
         var2 = this.processNsp;
      } else {
         var2 = false;
      }

      return var2;
   }

   public String getInputEncoding() {
      return this.encoding;
   }

   public int getLineNumber() {
      return -1;
   }

   public String getName() {
      return this.name;
   }

   public String getNamespace() {
      return this.namespace;
   }

   public String getNamespace(String var1) {
      String var2;
      if("xml".equals(var1)) {
         var2 = "http://www.w3.org/XML/1998/namespace";
      } else if("xmlns".equals(var1)) {
         var2 = "http://www.w3.org/2000/xmlns/";
      } else {
         int var3 = this.depth;
         int var4 = (this.getNamespaceCount(var3) << 1) - 2;

         while(true) {
            if(var4 < 0) {
               var2 = null;
               break;
            }

            if(var1 == null) {
               if(this.nspStack[var4] == false) {
                  String[] var5 = this.nspStack;
                  int var6 = var4 + 1;
                  var2 = var5[var6];
                  break;
               }
            } else {
               String var7 = this.nspStack[var4];
               if(var1.equals(var7)) {
                  String[] var8 = this.nspStack;
                  int var9 = var4 + 1;
                  var2 = var8[var9];
                  break;
               }
            }

            var4 += -2;
         }
      }

      return var2;
   }

   public int getNamespaceCount(int var1) {
      int var2 = this.depth;
      if(var1 > var2) {
         throw new IndexOutOfBoundsException();
      } else {
         return this.nspCounts[var1];
      }
   }

   public String getNamespacePrefix(int var1) {
      String[] var2 = this.nspStack;
      int var3 = var1 << 1;
      return var2[var3];
   }

   public String getNamespaceUri(int var1) {
      String[] var2 = this.nspStack;
      int var3 = (var1 << 1) + 1;
      return var2[var3];
   }

   public String getPositionDescription() {
      StringBuffer var1 = new StringBuffer;
      int var2 = this.type;
      int var3 = TYPES.length;
      String var6;
      if(var2 < var3) {
         String[] var4 = TYPES;
         int var5 = this.type;
         var6 = var4[var5];
      } else {
         var6 = "unknown";
      }

      var1.<init>(var6);
      StringBuffer var7 = var1.append(' ');
      if(this.type != 2 && this.type != 3) {
         if(this.type != 7) {
            if(this.type != 4) {
               String var43 = this.getText();
               var1.append(var43);
            } else if(this.isWhitespace) {
               StringBuffer var45 = var1.append("(whitespace)");
            } else {
               String var46 = this.getText();
               if(var46.length() > 16) {
                  StringBuilder var47 = new StringBuilder();
                  String var48 = var46.substring(0, 16);
                  var46 = var47.append(var48).append("...").toString();
               }

               var1.append(var46);
            }
         }
      } else {
         if(this.degenerated) {
            StringBuffer var8 = var1.append("(empty) ");
         }

         StringBuffer var9 = var1.append('<');
         if(this.type == 3) {
            StringBuffer var10 = var1.append('/');
         }

         if(this.prefix != null) {
            StringBuilder var11 = (new StringBuilder()).append("{");
            String var12 = this.namespace;
            StringBuilder var13 = var11.append(var12).append("}");
            String var14 = this.prefix;
            String var15 = var13.append(var14).append(":").toString();
            var1.append(var15);
         }

         String var17 = this.name;
         var1.append(var17);
         int var19 = this.attributeCount << 2;

         for(int var20 = 0; var20 < var19; var20 += 4) {
            StringBuffer var21 = var1.append(' ');
            String[] var22 = this.attributes;
            int var23 = var20 + 1;
            if(var22[var23] != false) {
               StringBuilder var24 = (new StringBuilder()).append("{");
               String var25 = this.attributes[var20];
               StringBuilder var26 = var24.append(var25).append("}");
               String[] var27 = this.attributes;
               int var28 = var20 + 1;
               String var29 = var27[var28];
               String var30 = var26.append(var29).append(":").toString();
               var1.append(var30);
            }

            StringBuilder var32 = new StringBuilder();
            String[] var33 = this.attributes;
            int var34 = var20 + 2;
            String var35 = var33[var34];
            StringBuilder var36 = var32.append(var35).append("=\'");
            String[] var37 = this.attributes;
            int var38 = var20 + 3;
            String var39 = var37[var38];
            String var40 = var36.append(var39).append("\'").toString();
            var1.append(var40);
         }

         StringBuffer var42 = var1.append('>');
      }

      return var1.toString();
   }

   public String getPrefix() {
      return this.prefix;
   }

   public Object getProperty(String var1) {
      return null;
   }

   public String getText() {
      return this.text;
   }

   public char[] getTextCharacters(int[] var1) {
      char[] var6;
      if(this.type >= 4) {
         var1[0] = 0;
         int var2 = this.text.length();
         var1[1] = var2;
         char[] var3 = new char[this.text.length()];
         String var4 = this.text;
         int var5 = this.text.length();
         var4.getChars(0, var5, var3, 0);
         var6 = var3;
      } else {
         var1[0] = -1;
         var1[1] = -1;
         var6 = null;
      }

      return var6;
   }

   public int getWapCode() {
      return this.wapCode;
   }

   public Object getWapExtensionData() {
      return this.wapExtensionData;
   }

   public boolean isAttributeDefault(int var1) {
      return false;
   }

   public boolean isEmptyElementTag() throws XmlPullParserException {
      if(this.type != 2) {
         this.exception("Wrong event type");
      }

      return this.degenerated;
   }

   public boolean isWhitespace() throws XmlPullParserException {
      if(this.type != 4 && this.type != 7 && this.type != 5) {
         this.exception("Wrong event type");
      }

      return this.isWhitespace;
   }

   public int next() throws XmlPullParserException, IOException {
      this.isWhitespace = (boolean)1;
      int var1 = 9999;

      label38:
      while(true) {
         String var2;
         do {
            var2 = this.text;
            this.nextImpl();
            if(this.type < var1) {
               var1 = this.type;
            }
         } while(var1 > 5);

         if(var1 < 4) {
            break;
         }

         if(var2 != null) {
            String var3;
            if(this.text == null) {
               var3 = var2;
            } else {
               StringBuilder var4 = (new StringBuilder()).append(var2);
               String var5 = this.text;
               var3 = var4.append(var5).toString();
            }

            this.text = var3;
         }

         switch(this.peekId()) {
         case 2:
         case 3:
         case 4:
         case 68:
         case 131:
         case 132:
         case 196:
            break;
         default:
            break label38;
         }
      }

      this.type = var1;
      if(this.type > 4) {
         this.type = 4;
      }

      return this.type;
   }

   public int nextTag() throws XmlPullParserException, IOException {
      int var1 = this.next();
      if(this.type == 4 && this.isWhitespace) {
         int var2 = this.next();
      }

      if(this.type != 3 && this.type != 2) {
         this.exception("unexpected type");
      }

      return this.type;
   }

   public String nextText() throws XmlPullParserException, IOException {
      if(this.type != 2) {
         this.exception("precondition: START_TAG");
      }

      int var1 = this.next();
      String var2;
      if(this.type == 4) {
         var2 = this.getText();
         int var3 = this.next();
      } else {
         var2 = "";
      }

      if(this.type != 3) {
         this.exception("END_TAG expected");
      }

      return var2;
   }

   public int nextToken() throws XmlPullParserException, IOException {
      this.isWhitespace = (boolean)1;
      this.nextImpl();
      return this.type;
   }

   void parseElement(int var1) throws IOException, XmlPullParserException {
      this.type = 2;
      String[] var2 = this.tagTable;
      int var3 = var1 & 63;
      String var4 = this.resolveId(var2, var3);
      this.name = var4;
      this.attributeCount = 0;
      if((var1 & 128) != 0) {
         this.readAttr();
      }

      byte var5;
      if((var1 & 64) == 0) {
         var5 = 1;
      } else {
         var5 = 0;
      }

      this.degenerated = (boolean)var5;
      int var6 = this.depth;
      int var7 = var6 + 1;
      this.depth = var7;
      int var8 = var6 << 2;
      String[] var9 = this.elementStack;
      int var10 = var8 + 4;
      String[] var11 = this.ensureCapacity(var9, var10);
      this.elementStack = var11;
      String[] var12 = this.elementStack;
      int var13 = var8 + 3;
      String var14 = this.name;
      var12[var13] = var14;
      int var15 = this.depth;
      int var16 = this.nspCounts.length;
      if(var15 >= var16) {
         int[] var17 = new int[this.depth + 4];
         int[] var18 = this.nspCounts;
         int var19 = this.nspCounts.length;
         System.arraycopy(var18, 0, var17, 0, var19);
         this.nspCounts = var17;
      }

      int[] var20 = this.nspCounts;
      int var21 = this.depth;
      int[] var22 = this.nspCounts;
      int var23 = this.depth - 1;
      int var24 = var22[var23];
      var20[var21] = var24;

      for(int var25 = this.attributeCount - 1; var25 > 0; var25 += -1) {
         for(int var26 = 0; var26 < var25; ++var26) {
            String var27 = this.getAttributeName(var25);
            String var28 = this.getAttributeName(var26);
            if(var27.equals(var28)) {
               StringBuilder var29 = (new StringBuilder()).append("Duplicate Attribute: ");
               String var30 = this.getAttributeName(var25);
               String var31 = var29.append(var30).toString();
               this.exception(var31);
            }
         }
      }

      if(this.processNsp) {
         boolean var32 = this.adjustNsp();
      } else {
         this.namespace = "";
      }

      String[] var33 = this.elementStack;
      String var34 = this.namespace;
      var33[var8] = var34;
      String[] var35 = this.elementStack;
      int var36 = var8 + 1;
      String var37 = this.prefix;
      var35[var36] = var37;
      String[] var38 = this.elementStack;
      int var39 = var8 + 2;
      String var40 = this.name;
      var38[var39] = var40;
   }

   public Object parseWapExtension(int var1) throws IOException, XmlPullParserException {
      Object var3;
      switch(var1) {
      case 64:
      case 65:
      case 66:
         var3 = this.readStrI();
         break;
      case 128:
      case 129:
      case 130:
         int var4 = this.readInt();
         var3 = new Integer(var4);
         break;
      case 192:
      case 193:
      case 194:
         var3 = null;
         break;
      case 195:
         int var5 = this.readInt();

         byte[] var6;
         int var9;
         for(var6 = new byte[var5]; var5 > 0; var5 -= var9) {
            InputStream var7 = this.in;
            int var8 = var6.length - var5;
            var9 = var7.read(var6, var8, var5);
         }

         var3 = var6;
         break;
      default:
         String var2 = "illegal id: " + var1;
         this.exception(var2);
         var3 = null;
      }

      return var3;
   }

   public void readAttr() throws IOException, XmlPullParserException {
      int var1 = this.readByte();

      int var35;
      for(int var2 = 0; var1 != 1; this.attributeCount = var35) {
         while(var1 == 0) {
            int var3 = this.readByte();
            this.selectPage(var3, (boolean)0);
            var1 = this.readByte();
         }

         String[] var4 = this.attrStartTable;
         String var5 = this.resolveId(var4, var1);
         int var6 = var5.indexOf(61);
         StringBuffer var7;
         if(var6 == -1) {
            var7 = new StringBuffer();
         } else {
            int var12 = var6 + 1;
            String var13 = var5.substring(var12);
            var7 = new StringBuffer(var13);
            var5 = var5.substring(0, var6);
         }

         int var11;
         for(var1 = this.readByte(); var1 > 128 || var1 == 0 || var1 == 2 || var1 == 3 || var1 == 131 || var1 >= 64 && var1 <= 66 || var1 >= 128 && var1 <= 130; var11 = this.readByte()) {
            switch(var1) {
            case 0:
               int var14 = this.readByte();
               this.selectPage(var14, (boolean)0);
               break;
            case 2:
               char var15 = (char)this.readInt();
               var7.append(var15);
               break;
            case 3:
               String var17 = this.readStrI();
               var7.append(var17);
               break;
            case 64:
            case 65:
            case 66:
            case 128:
            case 129:
            case 130:
            case 192:
            case 193:
            case 194:
            case 195:
               Object var19 = this.parseWapExtension(var1);
               String var20 = this.resolveWapExtension(var1, var19);
               var7.append(var20);
               break;
            case 131:
               String var22 = this.readStrT();
               var7.append(var22);
               break;
            default:
               String[] var8 = this.attrValueTable;
               String var9 = this.resolveId(var8, var1);
               var7.append(var9);
            }
         }

         String[] var24 = this.attributes;
         int var25 = var2 + 4;
         String[] var26 = this.ensureCapacity(var24, var25);
         this.attributes = var26;
         String[] var27 = this.attributes;
         int var28 = var2 + 1;
         var27[var2] = "";
         String[] var29 = this.attributes;
         int var30 = var28 + 1;
         var29[var28] = false;
         String[] var31 = this.attributes;
         int var32 = var30 + 1;
         var31[var30] = var5;
         String[] var33 = this.attributes;
         var2 = var32 + 1;
         String var34 = var7.toString();
         var33[var32] = var34;
         var35 = this.attributeCount + 1;
      }

   }

   int readByte() throws IOException {
      int var1 = this.in.read();
      if(var1 == -1) {
         throw new IOException("Unexpected EOF");
      } else {
         return var1;
      }
   }

   int readInt() throws IOException {
      int var1 = 0;

      int var2;
      do {
         var2 = this.readByte();
         int var3 = var1 << 7;
         int var4 = var2 & 127;
         var1 = var3 | var4;
      } while((var2 & 128) != 0);

      return var1;
   }

   String readStrI() throws IOException {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();

      while(true) {
         int var2 = this.in.read();
         if(var2 == 0) {
            this.isWhitespace = (boolean)1;
            byte[] var3 = var1.toByteArray();
            String var4 = this.encoding;
            String var5 = new String(var3, var4);
            var1.close();
            return var5;
         }

         if(var2 == -1) {
            throw new IOException("Unexpected EOF");
         }

         if(var2 > 32) {
            ;
         }

         var1.write(var2);
      }
   }

   String readStrT() throws IOException {
      int var1 = this.readInt();
      if(this.cacheStringTable == null) {
         Hashtable var2 = new Hashtable();
         this.cacheStringTable = var2;
      }

      Hashtable var3 = this.cacheStringTable;
      Integer var4 = new Integer(var1);
      String var5 = (String)var3.get(var4);
      if(var5 == null) {
         int var6 = var1;

         while(true) {
            int var7 = this.stringTable.length;
            if(var6 >= var7 || this.stringTable[var6] == 0) {
               byte[] var8 = this.stringTable;
               int var9 = var6 - var1;
               String var10 = this.encoding;
               var5 = new String(var8, var1, var9, var10);
               Hashtable var11 = this.cacheStringTable;
               Integer var12 = new Integer(var1);
               var11.put(var12, var5);
               break;
            }

            ++var6;
         }
      }

      return var5;
   }

   public void require(int var1, String var2, String var3) throws XmlPullParserException, IOException {
      int var4 = this.type;
      if(var1 == var4) {
         label27: {
            if(var2 != null) {
               String var5 = this.getNamespace();
               if(!var2.equals(var5)) {
                  break label27;
               }
            }

            if(var3 == null) {
               return;
            }

            String var6 = this.getName();
            if(var3.equals(var6)) {
               return;
            }
         }
      }

      StringBuilder var7 = (new StringBuilder()).append("expected: ");
      String var8;
      if(var1 == 64) {
         var8 = "WAP Ext.";
      } else {
         StringBuilder var10 = new StringBuilder();
         String var11 = TYPES[var1];
         var8 = var10.append(var11).append(" {").append(var2).append("}").append(var3).toString();
      }

      String var9 = var7.append(var8).toString();
      this.exception(var9);
   }

   String resolveId(String[] var1, int var2) throws IOException {
      int var3 = (var2 & 127) - 5;
      String var4;
      if(var3 == -1) {
         this.wapCode = -1;
         var4 = this.readStrT();
         return var4;
      } else {
         if(var3 >= 0 && var1 != null) {
            int var5 = var1.length;
            if(var3 < var5 && var1[var3] != false) {
               int var7 = var3 + 5;
               this.wapCode = var7;
               var4 = var1[var3];
               return var4;
            }
         }

         String var6 = "id " + var2 + " undef.";
         throw new IOException(var6);
      }
   }

   protected String resolveWapExtension(int var1, Object var2) {
      String var13;
      if(var2 instanceof byte[]) {
         StringBuffer var3 = new StringBuffer();
         byte[] var4 = (byte[])((byte[])var2);
         int var5 = 0;

         while(true) {
            int var6 = var4.length;
            if(var5 >= var6) {
               var13 = var3.toString();
               break;
            }

            int var7 = var4[var5] >> 4 & 15;
            char var8 = "0123456789abcdef".charAt(var7);
            var3.append(var8);
            int var10 = var4[var5] & 15;
            char var11 = "0123456789abcdef".charAt(var10);
            var3.append(var11);
            ++var5;
         }
      } else {
         var13 = "$(" + var2 + ")";
      }

      return var13;
   }

   public void setAttrStartTable(int var1, String[] var2) {
      int var3 = this.ATTR_START_TABLE;
      this.setTable(var1, var3, var2);
   }

   public void setAttrValueTable(int var1, String[] var2) {
      int var3 = this.ATTR_VALUE_TABLE;
      this.setTable(var1, var3, var2);
   }

   public void setFeature(String var1, boolean var2) throws XmlPullParserException {
      if("http://xmlpull.org/v1/doc/features.html#process-namespaces".equals(var1)) {
         this.processNsp = var2;
      } else {
         String var3 = "unsupported feature: " + var1;
         this.exception(var3);
      }
   }

   public void setInput(InputStream param1, String param2) throws XmlPullParserException {
      // $FF: Couldn't be decompiled
   }

   public void setInput(Reader var1) throws XmlPullParserException {
      this.exception("InputStream required");
   }

   public void setProperty(String var1, Object var2) throws XmlPullParserException {
      String var3 = "unsupported property: " + var1;
      throw new XmlPullParserException(var3);
   }

   public void setTagTable(int var1, String[] var2) {
      int var3 = this.TAG_TABLE;
      this.setTable(var1, var3, var2);
   }
}
