package myjava.awt.datatransfer;

import java.io.ByteArrayInputStream;
import java.io.CharArrayReader;
import java.io.Externalizable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import myjava.awt.datatransfer.MimeTypeProcessor;
import myjava.awt.datatransfer.Transferable;
import myjava.awt.datatransfer.UnsupportedFlavorException;
import org.apache.harmony.awt.datatransfer.DTK;
import org.apache.harmony.awt.internal.nls.Messages;

public class DataFlavor implements Externalizable, Cloneable {

   public static final DataFlavor javaFileListFlavor = new DataFlavor("application/x-java-file-list; class=java.util.List", "application/x-java-file-list");
   public static final String javaJVMLocalObjectMimeType = "application/x-java-jvm-local-objectref";
   public static final String javaRemoteObjectMimeType = "application/x-java-remote-object";
   public static final String javaSerializedObjectMimeType = "application/x-java-serialized-object";
   @Deprecated
   public static final DataFlavor plainTextFlavor = new DataFlavor("text/plain; charset=unicode; class=java.io.InputStream", "Plain Text");
   private static DataFlavor plainUnicodeFlavor;
   private static final long serialVersionUID = 8367026044764648243L;
   private static final String[] sortedTextFlavors;
   public static final DataFlavor stringFlavor = new DataFlavor("application/x-java-serialized-object; class=java.lang.String", "Unicode String");
   private String humanPresentableName;
   private MimeTypeProcessor.MimeType mimeInfo;
   private Class<?> representationClass;


   static {
      String[] var0 = new String[]{"text/sgml", "text/xml", "text/html", "text/rtf", "text/enriched", "text/richtext", "text/uri-list", "text/tab-separated-values", "text/t140", "text/rfc822-headers", "text/parityfec", "text/directory", "text/css", "text/calendar", "application/x-java-serialized-object", "text/plain"};
      sortedTextFlavors = var0;
      plainUnicodeFlavor = null;
   }

   public DataFlavor() {
      this.mimeInfo = null;
      this.humanPresentableName = null;
      this.representationClass = null;
   }

   public DataFlavor(Class<?> var1, String var2) {
      MimeTypeProcessor.MimeType var3 = new MimeTypeProcessor.MimeType("application", "x-java-serialized-object");
      this.mimeInfo = var3;
      if(var2 != null) {
         this.humanPresentableName = var2;
      } else {
         this.humanPresentableName = "application/x-java-serialized-object";
      }

      MimeTypeProcessor.MimeType var4 = this.mimeInfo;
      String var5 = var1.getName();
      var4.addParameter("class", var5);
      this.representationClass = var1;
   }

   public DataFlavor(String var1) throws ClassNotFoundException {
      this.init(var1, (String)null, (ClassLoader)null);
   }

   public DataFlavor(String var1, String var2) {
      try {
         this.init(var1, var2, (ClassLoader)null);
      } catch (ClassNotFoundException var6) {
         String var4 = this.mimeInfo.getParameter("class");
         String var5 = Messages.getString("awt.16C", (Object)var4);
         throw new IllegalArgumentException(var5, var6);
      }
   }

   public DataFlavor(String var1, String var2, ClassLoader var3) throws ClassNotFoundException {
      this.init(var1, var2, var3);
   }

   private static List<DataFlavor> fetchTextFlavors(List<DataFlavor> var0, String var1) {
      LinkedList var2 = new LinkedList();
      Iterator var3 = var0.iterator();

      while(var3.hasNext()) {
         DataFlavor var5 = (DataFlavor)var3.next();
         if(var5.isFlavorTextType()) {
            if(var5.mimeInfo.getFullType().equals(var1)) {
               if(!var2.contains(var5)) {
                  var2.add(var5);
               }

               var3.remove();
            }
         } else {
            var3.remove();
         }
      }

      LinkedList var4;
      if(var2.isEmpty()) {
         var4 = null;
      } else {
         var4 = var2;
      }

      return var4;
   }

   private String getCharset() {
      String var1;
      if(this.mimeInfo != null && !this.isCharsetRedundant()) {
         String var2 = this.mimeInfo.getParameter("charset");
         if(this.isCharsetRequired() && (var2 == null || var2.length() == 0)) {
            var1 = DTK.getDTK().getDefaultCharset();
         } else if(var2 == null) {
            var1 = "";
         } else {
            var1 = var2;
         }
      } else {
         var1 = "";
      }

      return var1;
   }

   private static List<DataFlavor> getFlavors(List<DataFlavor> var0, Class<?> var1) {
      LinkedList var2 = new LinkedList();
      Iterator var3 = var0.iterator();

      while(var3.hasNext()) {
         DataFlavor var4 = (DataFlavor)var3.next();
         if(var4.representationClass.equals(var1)) {
            var2.add(var4);
         }
      }

      List var6;
      if(var2.isEmpty()) {
         var6 = null;
      } else {
         var6 = var0;
      }

      return var6;
   }

   private static List<DataFlavor> getFlavors(List<DataFlavor> var0, String[] var1) {
      LinkedList var2 = new LinkedList();
      Iterator var3 = var0.iterator();

      while(var3.hasNext()) {
         DataFlavor var5 = (DataFlavor)var3.next();
         if(isCharsetSupported(var5.getCharset())) {
            int var6 = var1.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               Charset var8 = Charset.forName(var1[var7]);
               Charset var9 = Charset.forName(var5.getCharset());
               if(var8.equals(var9)) {
                  var2.add(var5);
               }
            }
         } else {
            var3.remove();
         }
      }

      List var4;
      if(var2.isEmpty()) {
         var4 = null;
      } else {
         var4 = var0;
      }

      return var4;
   }

   private String getKeyInfo() {
      String var1 = String.valueOf(this.mimeInfo.getFullType());
      StringBuilder var2 = (new StringBuilder(var1)).append(";class=");
      String var3 = this.representationClass.getName();
      String var4 = var2.append(var3).toString();
      String var5;
      if(this.mimeInfo.getPrimaryType().equals("text") && !this.isUnicodeFlavor()) {
         String var6 = String.valueOf(var4);
         StringBuilder var7 = (new StringBuilder(var6)).append(";charset=");
         String var8 = this.getCharset().toLowerCase();
         var5 = var7.append(var8).toString();
      } else {
         var5 = var4;
      }

      return var5;
   }

   public static final DataFlavor getTextPlainUnicodeFlavor() {
      if(plainUnicodeFlavor == null) {
         StringBuilder var0 = new StringBuilder("text/plain; charset=");
         String var1 = DTK.getDTK().getDefaultCharset();
         String var2 = var0.append(var1).append("; class=java.io.InputStream").toString();
         plainUnicodeFlavor = new DataFlavor(var2, "Plain Text");
      }

      return plainUnicodeFlavor;
   }

   private void init(String var1, String var2, ClassLoader var3) throws ClassNotFoundException {
      try {
         MimeTypeProcessor.MimeType var4 = MimeTypeProcessor.parse(var1);
         this.mimeInfo = var4;
      } catch (IllegalArgumentException var13) {
         String var8 = Messages.getString("awt.16D", (Object)var1);
         throw new IllegalArgumentException(var8);
      }

      if(var2 != null) {
         this.humanPresentableName = var2;
      } else {
         String var9 = String.valueOf(this.mimeInfo.getPrimaryType());
         StringBuilder var10 = (new StringBuilder(var9)).append('/');
         String var11 = this.mimeInfo.getSubType();
         String var12 = var10.append(var11).toString();
         this.humanPresentableName = var12;
      }

      String var5 = this.mimeInfo.getParameter("class");
      if(var5 == null) {
         var5 = "java.io.InputStream";
         this.mimeInfo.addParameter("class", var5);
      }

      Class var6;
      if(var3 == null) {
         var6 = Class.forName(var5);
      } else {
         var6 = var3.loadClass(var5);
      }

      this.representationClass = var6;
   }

   private boolean isByteCodeFlavor() {
      boolean var1;
      if(this.representationClass != null && (this.representationClass.equals(InputStream.class) || this.representationClass.equals(ByteBuffer.class) || this.representationClass.equals(byte[].class))) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private boolean isCharsetRedundant() {
      String var1 = this.mimeInfo.getFullType();
      boolean var2;
      if(!var1.equals("text/rtf") && !var1.equals("text/tab-separated-values") && !var1.equals("text/t140") && !var1.equals("text/rfc822-headers") && !var1.equals("text/parityfec")) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   private boolean isCharsetRequired() {
      String var1 = this.mimeInfo.getFullType();
      boolean var2;
      if(!var1.equals("text/sgml") && !var1.equals("text/xml") && !var1.equals("text/html") && !var1.equals("text/enriched") && !var1.equals("text/richtext") && !var1.equals("text/uri-list") && !var1.equals("text/directory") && !var1.equals("text/css") && !var1.equals("text/calendar") && !var1.equals("application/x-java-serialized-object") && !var1.equals("text/plain")) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   private static boolean isCharsetSupported(String var0) {
      boolean var1;
      boolean var2;
      try {
         var1 = Charset.isSupported(var0);
      } catch (IllegalCharsetNameException var4) {
         var2 = false;
         return var2;
      }

      var2 = var1;
      return var2;
   }

   private boolean isUnicodeFlavor() {
      boolean var1;
      if(this.representationClass != null && (this.representationClass.equals(Reader.class) || this.representationClass.equals(String.class) || this.representationClass.equals(CharBuffer.class) || this.representationClass.equals(char[].class))) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private static List<DataFlavor> selectBestByAlphabet(List<DataFlavor> var0) {
      String[] var1 = new String[var0.size()];
      LinkedList var2 = new LinkedList();
      int var3 = 0;

      while(true) {
         int var4 = var1.length;
         if(var3 >= var4) {
            Comparator var5 = String.CASE_INSENSITIVE_ORDER;
            Arrays.sort(var1, var5);
            Iterator var6 = var0.iterator();

            while(var6.hasNext()) {
               DataFlavor var9 = (DataFlavor)var6.next();
               String var10 = var1[0];
               String var11 = var9.getCharset();
               if(var10.equalsIgnoreCase(var11)) {
                  var2.add(var9);
               }
            }

            LinkedList var7;
            if(var2.isEmpty()) {
               var7 = null;
            } else {
               var7 = var2;
            }

            return var7;
         }

         String var8 = ((DataFlavor)var0.get(var3)).getCharset();
         var1[var3] = var8;
         ++var3;
      }
   }

   private static DataFlavor selectBestByCharset(List<DataFlavor> var0) {
      String[] var1 = new String[]{"UTF-16", "UTF-8", "UTF-16BE", "UTF-16LE"};
      List var2 = getFlavors(var0, var1);
      if(var2 == null) {
         String[] var3 = new String[1];
         String var4 = DTK.getDTK().getDefaultCharset();
         var3[0] = var4;
         var2 = getFlavors(var0, var3);
         if(var2 == null) {
            String[] var5 = new String[]{"US-ASCII"};
            var2 = getFlavors(var0, var5);
            if(var2 == null) {
               var2 = selectBestByAlphabet(var0);
            }
         }
      }

      DataFlavor var6;
      if(var2 != null) {
         if(var2.size() == 1) {
            var6 = (DataFlavor)var2.get(0);
         } else {
            var6 = selectBestFlavorWOCharset(var2);
         }
      } else {
         var6 = null;
      }

      return var6;
   }

   private static DataFlavor selectBestFlavorWCharset(List<DataFlavor> var0) {
      List var1 = getFlavors(var0, Reader.class);
      DataFlavor var2;
      if(var1 != null) {
         var2 = (DataFlavor)var1.get(0);
      } else {
         var1 = getFlavors(var0, String.class);
         if(var1 != null) {
            var2 = (DataFlavor)var1.get(0);
         } else {
            var1 = getFlavors(var0, CharBuffer.class);
            if(var1 != null) {
               var2 = (DataFlavor)var1.get(0);
            } else {
               var1 = getFlavors(var0, char[].class);
               if(var1 != null) {
                  var2 = (DataFlavor)var1.get(0);
               } else {
                  var2 = selectBestByCharset(var0);
               }
            }
         }
      }

      return var2;
   }

   private static DataFlavor selectBestFlavorWOCharset(List<DataFlavor> var0) {
      List var1 = getFlavors(var0, InputStream.class);
      DataFlavor var2;
      if(var1 != null) {
         var2 = (DataFlavor)var1.get(0);
      } else {
         var1 = getFlavors(var0, ByteBuffer.class);
         if(var1 != null) {
            var2 = (DataFlavor)var1.get(0);
         } else {
            var1 = getFlavors(var0, byte[].class);
            if(var1 != null) {
               var2 = (DataFlavor)var1.get(0);
            } else {
               var2 = (DataFlavor)var0.get(0);
            }
         }
      }

      return var2;
   }

   public static final DataFlavor selectBestTextFlavor(DataFlavor[] var0) {
      DataFlavor var1;
      if(var0 == null) {
         var1 = null;
      } else {
         List var2 = Arrays.asList(var0);
         List var3 = sortTextFlavorsByType(new LinkedList(var2));
         if(var3.isEmpty()) {
            var1 = null;
         } else {
            List var4 = (List)var3.get(0);
            if(var4.size() == 1) {
               var1 = (DataFlavor)var4.get(0);
            } else if(((DataFlavor)var4.get(0)).getCharset().length() == 0) {
               var1 = selectBestFlavorWOCharset(var4);
            } else {
               var1 = selectBestFlavorWCharset(var4);
            }
         }
      }

      return var1;
   }

   private static List<List<DataFlavor>> sortTextFlavorsByType(List<DataFlavor> var0) {
      LinkedList var1 = new LinkedList();
      String[] var2 = sortedTextFlavors;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String var5 = var2[var4];
         List var6 = fetchTextFlavors(var0, var5);
         if(var6 != null) {
            var1.addLast(var6);
         }
      }

      if(!var0.isEmpty()) {
         var1.addLast(var0);
      }

      return var1;
   }

   protected static final Class<?> tryToLoadClass(String var0, ClassLoader var1) throws ClassNotFoundException {
      Class var2;
      Class var3;
      try {
         var2 = Class.forName(var0);
      } catch (ClassNotFoundException var10) {
         try {
            var2 = ClassLoader.getSystemClassLoader().loadClass(var0);
         } catch (ClassNotFoundException var9) {
            ClassLoader var6 = Thread.currentThread().getContextClassLoader();
            if(var6 != null) {
               label46: {
                  try {
                     var2 = var6.loadClass(var0);
                  } catch (ClassNotFoundException var8) {
                     break label46;
                  }

                  var3 = var2;
                  return var3;
               }
            }

            var3 = var1.loadClass(var0);
            return var3;
         }

         var3 = var2;
         return var3;
      }

      var3 = var2;
      return var3;
   }

   public Object clone() throws CloneNotSupportedException {
      DataFlavor var1 = new DataFlavor();
      String var2 = this.humanPresentableName;
      var1.humanPresentableName = var2;
      Class var3 = this.representationClass;
      var1.representationClass = var3;
      MimeTypeProcessor.MimeType var4;
      if(this.mimeInfo != null) {
         var4 = (MimeTypeProcessor.MimeType)this.mimeInfo.clone();
      } else {
         var4 = null;
      }

      var1.mimeInfo = var4;
      return var1;
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(var1 != null && var1 instanceof DataFlavor) {
         DataFlavor var3 = (DataFlavor)var1;
         var2 = this.equals(var3);
      } else {
         var2 = false;
      }

      return var2;
   }

   @Deprecated
   public boolean equals(String var1) {
      byte var2;
      if(var1 == null) {
         var2 = 0;
      } else {
         var2 = this.isMimeTypeEqual(var1);
      }

      return (boolean)var2;
   }

   public boolean equals(DataFlavor var1) {
      boolean var2;
      if(var1 == this) {
         var2 = true;
      } else if(var1 == null) {
         var2 = false;
      } else if(this.mimeInfo == null) {
         if(var1.mimeInfo == null) {
            var2 = true;
         } else {
            var2 = false;
         }
      } else {
         MimeTypeProcessor.MimeType var3 = this.mimeInfo;
         MimeTypeProcessor.MimeType var4 = var1.mimeInfo;
         if(var3.equals(var4)) {
            Class var5 = this.representationClass;
            Class var6 = var1.representationClass;
            if(var5.equals(var6)) {
               if(this.mimeInfo.getPrimaryType().equals("text") && !this.isUnicodeFlavor()) {
                  String var7 = this.getCharset();
                  String var8 = var1.getCharset();
                  if(isCharsetSupported(var7) && isCharsetSupported(var8)) {
                     Charset var9 = Charset.forName(var7);
                     Charset var10 = Charset.forName(var8);
                     var2 = var9.equals(var10);
                  } else {
                     var2 = var7.equalsIgnoreCase(var8);
                  }

                  return var2;
               } else {
                  var2 = true;
                  return var2;
               }
            }
         }

         var2 = false;
      }

      return var2;
   }

   public final Class<?> getDefaultRepresentationClass() {
      return InputStream.class;
   }

   public final String getDefaultRepresentationClassAsString() {
      return this.getDefaultRepresentationClass().getName();
   }

   public String getHumanPresentableName() {
      return this.humanPresentableName;
   }

   MimeTypeProcessor.MimeType getMimeInfo() {
      return this.mimeInfo;
   }

   public String getMimeType() {
      String var1;
      if(this.mimeInfo != null) {
         var1 = MimeTypeProcessor.assemble(this.mimeInfo);
      } else {
         var1 = null;
      }

      return var1;
   }

   public String getParameter(String var1) {
      String var2 = var1.toLowerCase();
      String var3;
      if(var2.equals("humanpresentablename")) {
         var3 = this.humanPresentableName;
      } else if(this.mimeInfo != null) {
         var3 = this.mimeInfo.getParameter(var2);
      } else {
         var3 = null;
      }

      return var3;
   }

   public String getPrimaryType() {
      String var1;
      if(this.mimeInfo != null) {
         var1 = this.mimeInfo.getPrimaryType();
      } else {
         var1 = null;
      }

      return var1;
   }

   public Reader getReaderForText(Transferable var1) throws UnsupportedFlavorException, IOException {
      Object var2 = var1.getTransferData(this);
      if(var2 == null) {
         String var3 = Messages.getString("awt.16E");
         throw new IllegalArgumentException(var3);
      } else {
         Object var5;
         if(var2 instanceof Reader) {
            Reader var4 = (Reader)var2;
            var4.reset();
            var5 = var4;
         } else if(var2 instanceof String) {
            String var6 = (String)var2;
            var5 = new StringReader(var6);
         } else if(var2 instanceof CharBuffer) {
            char[] var7 = ((CharBuffer)var2).array();
            var5 = new CharArrayReader(var7);
         } else if(var2 instanceof char[]) {
            char[] var8 = (char[])var2;
            var5 = new CharArrayReader(var8);
         } else {
            String var9 = this.getCharset();
            Object var10;
            if(var2 instanceof InputStream) {
               var10 = (InputStream)var2;
               ((InputStream)var10).reset();
            } else if(var2 instanceof ByteBuffer) {
               byte[] var11 = ((ByteBuffer)var2).array();
               var10 = new ByteArrayInputStream(var11);
            } else {
               if(!(var2 instanceof byte[])) {
                  String var13 = Messages.getString("awt.16F");
                  throw new IllegalArgumentException(var13);
               }

               byte[] var12 = (byte[])var2;
               var10 = new ByteArrayInputStream(var12);
            }

            if(var9.length() == 0) {
               var5 = new InputStreamReader((InputStream)var10);
            } else {
               var5 = new InputStreamReader((InputStream)var10, var9);
            }
         }

         return (Reader)var5;
      }
   }

   public Class<?> getRepresentationClass() {
      return this.representationClass;
   }

   public String getSubType() {
      String var1;
      if(this.mimeInfo != null) {
         var1 = this.mimeInfo.getSubType();
      } else {
         var1 = null;
      }

      return var1;
   }

   public int hashCode() {
      return this.getKeyInfo().hashCode();
   }

   public boolean isFlavorJavaFileListType() {
      Class var1 = this.representationClass;
      boolean var3;
      if(List.class.isAssignableFrom(var1)) {
         DataFlavor var2 = javaFileListFlavor;
         if(this.isMimeTypeEqual(var2)) {
            var3 = true;
            return var3;
         }
      }

      var3 = false;
      return var3;
   }

   public boolean isFlavorRemoteObjectType() {
      boolean var1;
      if(this.isMimeTypeEqual("application/x-java-remote-object") && this.isRepresentationClassRemote()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isFlavorSerializedObjectType() {
      boolean var1;
      if(this.isMimeTypeSerializedObject() && this.isRepresentationClassSerializable()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isFlavorTextType() {
      DataFlavor var1 = stringFlavor;
      boolean var3;
      if(!this.equals(var1)) {
         DataFlavor var2 = plainTextFlavor;
         if(!this.equals(var2)) {
            if(this.mimeInfo != null && !this.mimeInfo.getPrimaryType().equals("text")) {
               var3 = false;
               return var3;
            } else {
               String var4 = this.getCharset();
               if(this.isByteCodeFlavor()) {
                  if(var4.length() != 0) {
                     var3 = isCharsetSupported(var4);
                  } else {
                     var3 = true;
                  }

                  return var3;
               } else {
                  var3 = this.isUnicodeFlavor();
                  return var3;
               }
            }
         }
      }

      var3 = true;
      return var3;
   }

   public boolean isMimeTypeEqual(String var1) {
      boolean var4;
      boolean var5;
      try {
         MimeTypeProcessor.MimeType var2 = this.mimeInfo;
         MimeTypeProcessor.MimeType var3 = MimeTypeProcessor.parse(var1);
         var4 = var2.equals(var3);
      } catch (IllegalArgumentException var7) {
         var5 = false;
         return var5;
      }

      var5 = var4;
      return var5;
   }

   public final boolean isMimeTypeEqual(DataFlavor var1) {
      boolean var4;
      if(this.mimeInfo != null) {
         MimeTypeProcessor.MimeType var2 = this.mimeInfo;
         MimeTypeProcessor.MimeType var3 = var1.mimeInfo;
         var4 = var2.equals(var3);
      } else if(var1.mimeInfo == null) {
         var4 = true;
      } else {
         var4 = false;
      }

      return var4;
   }

   public boolean isMimeTypeSerializedObject() {
      return this.isMimeTypeEqual("application/x-java-serialized-object");
   }

   public boolean isRepresentationClassByteBuffer() {
      Class var1 = this.representationClass;
      return ByteBuffer.class.isAssignableFrom(var1);
   }

   public boolean isRepresentationClassCharBuffer() {
      Class var1 = this.representationClass;
      return CharBuffer.class.isAssignableFrom(var1);
   }

   public boolean isRepresentationClassInputStream() {
      Class var1 = this.representationClass;
      return InputStream.class.isAssignableFrom(var1);
   }

   public boolean isRepresentationClassReader() {
      Class var1 = this.representationClass;
      return Reader.class.isAssignableFrom(var1);
   }

   public boolean isRepresentationClassRemote() {
      return false;
   }

   public boolean isRepresentationClassSerializable() {
      Class var1 = this.representationClass;
      return Serializable.class.isAssignableFrom(var1);
   }

   public boolean match(DataFlavor var1) {
      return this.equals(var1);
   }

   @Deprecated
   protected String normalizeMimeType(String var1) {
      return var1;
   }

   @Deprecated
   protected String normalizeMimeTypeParameter(String var1, String var2) {
      return var2;
   }

   public void readExternal(ObjectInput param1) throws IOException, ClassNotFoundException {
      // $FF: Couldn't be decompiled
   }

   public void setHumanPresentableName(String var1) {
      this.humanPresentableName = var1;
   }

   public String toString() {
      String var1 = String.valueOf(this.getClass().getName());
      StringBuilder var2 = (new StringBuilder(var1)).append("[MimeType=(");
      String var3 = this.getMimeType();
      StringBuilder var4 = var2.append(var3).append(");humanPresentableName=");
      String var5 = this.humanPresentableName;
      return var4.append(var5).append("]").toString();
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      synchronized(this){}

      try {
         String var2 = this.humanPresentableName;
         var1.writeObject(var2);
         MimeTypeProcessor.MimeType var3 = this.mimeInfo;
         var1.writeObject(var3);
      } finally {
         ;
      }

   }
}
