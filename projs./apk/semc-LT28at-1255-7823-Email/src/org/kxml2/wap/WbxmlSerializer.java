package org.kxml2.wap;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Hashtable;
import java.util.Vector;
import org.xmlpull.v1.XmlSerializer;

public class WbxmlSerializer implements XmlSerializer {

   private int attrPage;
   Hashtable attrStartTable;
   Hashtable attrValueTable;
   Vector attributes;
   ByteArrayOutputStream buf;
   int depth;
   private String encoding;
   private boolean headerSent;
   String name;
   String namespace;
   OutputStream out;
   String pending;
   Hashtable stringTable;
   ByteArrayOutputStream stringTableBuf;
   private int tagPage;
   Hashtable tagTable;


   public WbxmlSerializer() {
      Hashtable var1 = new Hashtable();
      this.stringTable = var1;
      ByteArrayOutputStream var2 = new ByteArrayOutputStream();
      this.buf = var2;
      ByteArrayOutputStream var3 = new ByteArrayOutputStream();
      this.stringTableBuf = var3;
      Vector var4 = new Vector();
      this.attributes = var4;
      Hashtable var5 = new Hashtable();
      this.attrStartTable = var5;
      Hashtable var6 = new Hashtable();
      this.attrValueTable = var6;
      Hashtable var7 = new Hashtable();
      this.tagTable = var7;
      this.headerSent = (boolean)0;
   }

   static void writeInt(OutputStream var0, int var1) throws IOException {
      byte[] var2 = new byte[5];
      int var3 = 0;

      while(true) {
         int var4 = var3 + 1;
         byte var5 = (byte)(var1 & 127);
         var2[var3] = var5;
         var1 >>= 7;
         if(var1 == 0) {
            int var6 = var4;

            while(var6 > 1) {
               var6 += -1;
               int var7 = var2[var6] | 128;
               var0.write(var7);
            }

            byte var8 = var2[0];
            var0.write(var8);
            return;
         }

         var3 = var4;
      }
   }

   private void writeStr(String param1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   private final void writeStrT(String var1, boolean var2) throws IOException {
      Integer var3 = (Integer)this.stringTable.get(var1);
      ByteArrayOutputStream var4 = this.buf;
      int var5;
      if(var3 == null) {
         var5 = this.addToStringTable(var1, var2);
      } else {
         var5 = var3.intValue();
      }

      writeInt(var4, var5);
   }

   public int addToStringTable(String var1, boolean var2) throws IOException {
      if(this.headerSent) {
         throw new IOException("stringtable sent");
      } else {
         int var3 = this.stringTableBuf.size();
         int var4 = var3;
         if(var1.charAt(0) >= 48 && var2) {
            var1 = ' ' + var1;
            var4 = var3 + 1;
         }

         Hashtable var5 = this.stringTable;
         Integer var6 = new Integer(var3);
         var5.put(var1, var6);
         if(var1.charAt(0) == 32) {
            Hashtable var8 = this.stringTable;
            String var9 = var1.substring(1);
            int var10 = var3 + 1;
            Integer var11 = new Integer(var10);
            var8.put(var9, var11);
         }

         int var13 = var1.lastIndexOf(32);
         if(var13 > 1) {
            Hashtable var14 = this.stringTable;
            String var15 = var1.substring(var13);
            int var16 = var3 + var13;
            Integer var17 = new Integer(var16);
            var14.put(var15, var17);
            Hashtable var19 = this.stringTable;
            int var20 = var13 + 1;
            String var21 = var1.substring(var20);
            int var22 = var3 + var13 + 1;
            Integer var23 = new Integer(var22);
            var19.put(var21, var23);
         }

         ByteArrayOutputStream var25 = this.stringTableBuf;
         this.writeStrI(var25, var1);
         this.stringTableBuf.flush();
         return var4;
      }
   }

   public XmlSerializer attribute(String var1, String var2, String var3) {
      this.attributes.addElement(var2);
      this.attributes.addElement(var3);
      return this;
   }

   public void cdsect(String var1) throws IOException {
      this.text(var1);
   }

   public void checkPending(boolean var1) throws IOException {
      if(this.pending != null) {
         int var2 = this.attributes.size();
         Hashtable var3 = this.tagTable;
         String var4 = this.pending;
         int[] var5 = (int[])((int[])var3.get(var4));
         if(var5 == null) {
            ByteArrayOutputStream var6 = this.buf;
            short var7;
            if(var2 == 0) {
               if(var1) {
                  var7 = 4;
               } else {
                  var7 = 68;
               }
            } else if(var1) {
               var7 = 132;
            } else {
               var7 = 196;
            }

            var6.write(var7);
            String var8 = this.pending;
            this.writeStrT(var8, (boolean)0);
         } else {
            int var19 = var5[0];
            int var20 = this.tagPage;
            if(var19 != var20) {
               int var21 = var5[0];
               this.tagPage = var21;
               this.buf.write(0);
               ByteArrayOutputStream var22 = this.buf;
               int var23 = this.tagPage;
               var22.write(var23);
            }

            ByteArrayOutputStream var24 = this.buf;
            int var39;
            if(var2 == 0) {
               if(var1) {
                  var39 = var5[1];
               } else {
                  var39 = var5[1] | 64;
               }
            } else if(var1) {
               var39 = var5[1] | 128;
            } else {
               var39 = var5[1] | 192;
            }

            var24.write(var39);
         }

         int var16;
         for(int var9 = 0; var9 < var2; var9 = var16 + 1) {
            Hashtable var10 = this.attrStartTable;
            Object var11 = this.attributes.elementAt(var9);
            int[] var12 = (int[])((int[])var10.get(var11));
            if(var12 == null) {
               this.buf.write(4);
               String var13 = (String)this.attributes.elementAt(var9);
               this.writeStrT(var13, (boolean)0);
            } else {
               int var25 = var12[0];
               int var26 = this.attrPage;
               if(var25 != var26) {
                  int var27 = var12[0];
                  this.attrPage = var27;
                  this.buf.write(0);
                  ByteArrayOutputStream var28 = this.buf;
                  int var29 = this.attrPage;
                  var28.write(var29);
               }

               ByteArrayOutputStream var30 = this.buf;
               int var31 = var12[1];
               var30.write(var31);
            }

            Hashtable var14 = this.attrValueTable;
            Vector var15 = this.attributes;
            var16 = var9 + 1;
            Object var17 = var15.elementAt(var16);
            var5 = (int[])((int[])var14.get(var17));
            if(var5 == null) {
               String var18 = (String)this.attributes.elementAt(var16);
               this.writeStr(var18);
            } else {
               int var32 = var5[0];
               int var33 = this.attrPage;
               if(var32 != var33) {
                  int var34 = var5[0];
                  this.attrPage = var34;
                  this.buf.write(0);
                  ByteArrayOutputStream var35 = this.buf;
                  int var36 = this.attrPage;
                  var35.write(var36);
               }

               ByteArrayOutputStream var37 = this.buf;
               int var38 = var5[1];
               var37.write(var38);
            }
         }

         if(var2 > 0) {
            this.buf.write(1);
         }

         this.pending = null;
         this.attributes.removeAllElements();
      }
   }

   public void comment(String var1) {}

   public void docdecl(String var1) {
      throw new RuntimeException("Cannot write docdecl for WBXML");
   }

   public void endDocument() throws IOException {
      this.flush();
   }

   public XmlSerializer endTag(String var1, String var2) throws IOException {
      if(this.pending != null) {
         this.checkPending((boolean)1);
      } else {
         this.buf.write(1);
      }

      int var3 = this.depth - 1;
      this.depth = var3;
      return this;
   }

   public void entityRef(String var1) {
      throw new RuntimeException("EntityReference not supported for WBXML");
   }

   public void flush() throws IOException {
      this.checkPending((boolean)0);
      if(!this.headerSent) {
         OutputStream var1 = this.out;
         int var2 = this.stringTableBuf.size();
         writeInt(var1, var2);
         OutputStream var3 = this.out;
         byte[] var4 = this.stringTableBuf.toByteArray();
         var3.write(var4);
         this.headerSent = (boolean)1;
      }

      OutputStream var5 = this.out;
      byte[] var6 = this.buf.toByteArray();
      var5.write(var6);
      this.buf.reset();
   }

   public int getDepth() {
      return this.depth;
   }

   public boolean getFeature(String var1) {
      return false;
   }

   public String getName() {
      return this.pending;
   }

   public String getNamespace() {
      return null;
   }

   public String getPrefix(String var1, boolean var2) {
      throw new RuntimeException("NYI");
   }

   public Object getProperty(String var1) {
      return null;
   }

   public void ignorableWhitespace(String var1) {}

   public void processingInstruction(String var1) {
      throw new RuntimeException("PI NYI");
   }

   public void setAttrStartTable(int var1, String[] var2) {
      int var3 = 0;

      while(true) {
         int var4 = var2.length;
         if(var3 >= var4) {
            return;
         }

         if(var2[var3] != false) {
            int[] var5 = new int[]{var1, 0};
            int var6 = var3 + 5;
            var5[1] = var6;
            Hashtable var7 = this.attrStartTable;
            String var8 = var2[var3];
            var7.put(var8, var5);
         }

         ++var3;
      }
   }

   public void setAttrValueTable(int var1, String[] var2) {
      int var3 = 0;

      while(true) {
         int var4 = var2.length;
         if(var3 >= var4) {
            return;
         }

         if(var2[var3] != false) {
            int[] var5 = new int[]{var1, 0};
            int var6 = var3 + 133;
            var5[1] = var6;
            Hashtable var7 = this.attrValueTable;
            String var8 = var2[var3];
            var7.put(var8, var5);
         }

         ++var3;
      }
   }

   public void setFeature(String var1, boolean var2) {
      String var3 = "unknown feature " + var1;
      throw new IllegalArgumentException(var3);
   }

   public void setOutput(OutputStream var1, String var2) throws IOException {
      String var3;
      if(var2 == null) {
         var3 = "UTF-8";
      } else {
         var3 = var2;
      }

      this.encoding = var3;
      this.out = var1;
      ByteArrayOutputStream var4 = new ByteArrayOutputStream();
      this.buf = var4;
      ByteArrayOutputStream var5 = new ByteArrayOutputStream();
      this.stringTableBuf = var5;
      this.headerSent = (boolean)0;
   }

   public void setOutput(Writer var1) {
      throw new RuntimeException("Wbxml requires an OutputStream!");
   }

   public void setPrefix(String var1, String var2) {
      throw new RuntimeException("NYI");
   }

   public void setProperty(String var1, Object var2) {
      String var3 = "unknown property " + var1;
      throw new IllegalArgumentException(var3);
   }

   public void setTagTable(int var1, String[] var2) {
      int var3 = 0;

      while(true) {
         int var4 = var2.length;
         if(var3 >= var4) {
            return;
         }

         if(var2[var3] != false) {
            int[] var5 = new int[]{var1, 0};
            int var6 = var3 + 5;
            var5[1] = var6;
            Hashtable var7 = this.tagTable;
            String var8 = var2[var3];
            var7.put(var8, var5);
         }

         ++var3;
      }
   }

   public void startDocument(String var1, Boolean var2) throws IOException {
      this.out.write(3);
      this.out.write(1);
      if(var1 != null) {
         this.encoding = var1;
      }

      if(this.encoding.toUpperCase().equals("UTF-8")) {
         this.out.write(106);
      } else if(this.encoding.toUpperCase().equals("ISO-8859-1")) {
         this.out.write(4);
      } else {
         throw new UnsupportedEncodingException(var1);
      }
   }

   public XmlSerializer startTag(String var1, String var2) throws IOException {
      if(var1 != null && !"".equals(var1)) {
         throw new RuntimeException("NSP NYI");
      } else {
         this.checkPending((boolean)0);
         this.pending = var2;
         int var3 = this.depth + 1;
         this.depth = var3;
         return this;
      }
   }

   public XmlSerializer text(String var1) throws IOException {
      this.checkPending((boolean)0);
      this.writeStr(var1);
      return this;
   }

   public XmlSerializer text(char[] var1, int var2, int var3) throws IOException {
      this.checkPending((boolean)0);
      String var4 = new String(var1, var2, var3);
      this.writeStr(var4);
      return this;
   }

   void writeStrI(OutputStream var1, String var2) throws IOException {
      String var3 = this.encoding;
      byte[] var4 = var2.getBytes(var3);
      var1.write(var4);
      var1.write(0);
   }

   public void writeWapExtension(int var1, Object var2) throws IOException {
      this.checkPending((boolean)0);
      this.buf.write(var1);
      switch(var1) {
      case 64:
      case 65:
      case 66:
         ByteArrayOutputStream var6 = this.buf;
         String var7 = (String)var2;
         this.writeStrI(var6, var7);
         return;
      case 128:
      case 129:
      case 130:
         String var8 = (String)var2;
         this.writeStrT(var8, (boolean)0);
         return;
      case 195:
         byte[] var3 = (byte[])((byte[])var2);
         ByteArrayOutputStream var4 = this.buf;
         int var5 = var3.length;
         writeInt(var4, var5);
         this.buf.write(var3);
      case 192:
      case 193:
      case 194:
         return;
      default:
         throw new IllegalArgumentException();
      }
   }
}
