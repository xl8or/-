package javax.mail.internet;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import javax.mail.Header;
import javax.mail.MessagingException;

public class InternetHeaders {

   protected List headers;


   public InternetHeaders() {
      ArrayList var1 = new ArrayList(20);
      this.headers = var1;
      List var2 = this.headers;
      InternetHeaders.InternetHeader var3 = new InternetHeaders.InternetHeader("Return-Path", (String)null);
      var2.add(var3);
      List var5 = this.headers;
      InternetHeaders.InternetHeader var6 = new InternetHeaders.InternetHeader("Received", (String)null);
      var5.add(var6);
      List var8 = this.headers;
      InternetHeaders.InternetHeader var9 = new InternetHeaders.InternetHeader("Message-Id", (String)null);
      var8.add(var9);
      List var11 = this.headers;
      InternetHeaders.InternetHeader var12 = new InternetHeaders.InternetHeader("Resent-Date", (String)null);
      var11.add(var12);
      List var14 = this.headers;
      InternetHeaders.InternetHeader var15 = new InternetHeaders.InternetHeader("Date", (String)null);
      var14.add(var15);
      List var17 = this.headers;
      InternetHeaders.InternetHeader var18 = new InternetHeaders.InternetHeader("Resent-From", (String)null);
      var17.add(var18);
      List var20 = this.headers;
      InternetHeaders.InternetHeader var21 = new InternetHeaders.InternetHeader("From", (String)null);
      var20.add(var21);
      List var23 = this.headers;
      InternetHeaders.InternetHeader var24 = new InternetHeaders.InternetHeader("Reply-To", (String)null);
      var23.add(var24);
      List var26 = this.headers;
      InternetHeaders.InternetHeader var27 = new InternetHeaders.InternetHeader("To", (String)null);
      var26.add(var27);
      List var29 = this.headers;
      InternetHeaders.InternetHeader var30 = new InternetHeaders.InternetHeader("Subject", (String)null);
      var29.add(var30);
      List var32 = this.headers;
      InternetHeaders.InternetHeader var33 = new InternetHeaders.InternetHeader("Cc", (String)null);
      var32.add(var33);
      List var35 = this.headers;
      InternetHeaders.InternetHeader var36 = new InternetHeaders.InternetHeader("In-Reply-To", (String)null);
      var35.add(var36);
      List var38 = this.headers;
      InternetHeaders.InternetHeader var39 = new InternetHeaders.InternetHeader("Resent-Message-Id", (String)null);
      var38.add(var39);
      List var41 = this.headers;
      InternetHeaders.InternetHeader var42 = new InternetHeaders.InternetHeader("Errors-To", (String)null);
      var41.add(var42);
      List var44 = this.headers;
      InternetHeaders.InternetHeader var45 = new InternetHeaders.InternetHeader("Mime-Version", (String)null);
      var44.add(var45);
      List var47 = this.headers;
      InternetHeaders.InternetHeader var48 = new InternetHeaders.InternetHeader("Content-Type", (String)null);
      var47.add(var48);
      List var50 = this.headers;
      InternetHeaders.InternetHeader var51 = new InternetHeaders.InternetHeader("Content-Transfer-Encoding", (String)null);
      var50.add(var51);
      List var53 = this.headers;
      InternetHeaders.InternetHeader var54 = new InternetHeaders.InternetHeader("Content-MD5", (String)null);
      var53.add(var54);
      List var56 = this.headers;
      InternetHeaders.InternetHeader var57 = new InternetHeaders.InternetHeader("Content-Length", (String)null);
      var56.add(var57);
      List var59 = this.headers;
      InternetHeaders.InternetHeader var60 = new InternetHeaders.InternetHeader("Status", (String)null);
      var59.add(var60);
   }

   public InternetHeaders(InputStream var1) throws MessagingException {
      ArrayList var2 = new ArrayList(20);
      this.headers = var2;
      this.load(var1);
   }

   private static String trim(String var0) {
      int var1 = var0.length();
      String var4;
      if(var1 > 0) {
         int var2 = var1 - 1;
         if(var0.charAt(var2) == 13) {
            int var3 = var1 - 1;
            var4 = var0.substring(0, var3);
            return var4;
         }
      }

      var4 = var0;
      return var4;
   }

   public void addHeader(String param1, String param2) {
      // $FF: Couldn't be decompiled
   }

   public void addHeaderLine(String param1) {
      // $FF: Couldn't be decompiled
   }

   public Enumeration getAllHeaderLines() {
      Iterator var1 = this.headers.iterator();
      return new InternetHeaders.HeaderEnumeration(var1, (String[])null, (boolean)1, (boolean)0);
   }

   public Enumeration getAllHeaders() {
      Iterator var1 = this.headers.iterator();
      return new InternetHeaders.HeaderEnumeration(var1, (String[])null, (boolean)0, (boolean)0);
   }

   public String getHeader(String var1, String var2) {
      String[] var3 = this.getHeader(var1);
      String var11;
      if(var3 == null) {
         var11 = null;
      } else if(var2 != null && var3.length != 1) {
         StringBuffer var4 = new StringBuffer();
         byte var5 = 0;

         while(true) {
            int var6 = var3.length;
            if(var5 >= var6) {
               var11 = var4.toString();
               break;
            }

            if(var5 > 0) {
               var4.append(var2);
            }

            String var8 = var3[var5];
            var4.append(var8);
            int var10 = var5 + 1;
         }
      } else {
         var11 = var3[0];
      }

      return var11;
   }

   public String[] getHeader(String var1) {
      int var2 = this.headers.size();
      ArrayList var3 = new ArrayList(var2);
      Iterator var4 = this.headers.iterator();

      while(var4.hasNext()) {
         InternetHeaders.InternetHeader var10 = (InternetHeaders.InternetHeader)var4.next();
         if(var10.nameEquals(var1) && var10.line != null) {
            String var5 = var10.getValue();
            var3.add(var5);
         }
      }

      int var7 = var3.size();
      String[] var11;
      if(var7 == 0) {
         var11 = null;
      } else {
         String[] var8 = new String[var7];
         var3.toArray(var8);
         var11 = var8;
      }

      return var11;
   }

   public Enumeration getMatchingHeaderLines(String[] var1) {
      Iterator var2 = this.headers.iterator();
      return new InternetHeaders.HeaderEnumeration(var2, var1, (boolean)1, (boolean)1);
   }

   public Enumeration getMatchingHeaders(String[] var1) {
      Iterator var2 = this.headers.iterator();
      return new InternetHeaders.HeaderEnumeration(var2, var1, (boolean)0, (boolean)1);
   }

   public Enumeration getNonMatchingHeaderLines(String[] var1) {
      Iterator var2 = this.headers.iterator();
      return new InternetHeaders.HeaderEnumeration(var2, var1, (boolean)1, (boolean)0);
   }

   public Enumeration getNonMatchingHeaders(String[] var1) {
      Iterator var2 = this.headers.iterator();
      return new InternetHeaders.HeaderEnumeration(var2, var1, (boolean)0, (boolean)0);
   }

   public void load(InputStream param1) throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public void removeHeader(String var1) {
      List var2 = this.headers;
      synchronized(var2) {
         int var3 = this.headers.size();

         for(int var4 = 0; var4 < var3; ++var4) {
            InternetHeaders.InternetHeader var5 = (InternetHeaders.InternetHeader)this.headers.get(var4);
            if(var5.nameEquals(var1)) {
               var5.line = null;
            }
         }

      }
   }

   public void setHeader(String var1, String var2) {
      int var3 = 0;
      boolean var4 = true;

      while(true) {
         int var5 = this.headers.size();
         if(var3 >= var5) {
            if(!var4) {
               return;
            }

            this.addHeader(var1, var2);
            return;
         }

         InternetHeaders.InternetHeader var6 = (InternetHeaders.InternetHeader)this.headers.get(var3);
         int var7;
         boolean var8;
         if(var6.nameEquals(var1)) {
            if(var4) {
               var6.setValue(var2);
               var7 = var3;
               var8 = false;
            } else {
               this.headers.remove(var3);
               var7 = var3 + -1;
               var8 = var4;
            }
         } else {
            var7 = var3;
            var8 = var4;
         }

         int var9 = var7 + 1;
         var4 = var8;
         var3 = var9;
      }
   }

   protected static class InternetHeader extends Header {

      protected String line;
      protected String name;


      public InternetHeader(String var1) {
         super((String)null, (String)null);
         int var2 = var1.indexOf(58);
         String var3;
         if(var2 < 0) {
            var3 = var1.trim();
         } else {
            var3 = var1.substring(0, var2).trim();
         }

         this.name = var3;
         this.line = var1;
      }

      public InternetHeader(String var1, String var2) {
         super((String)null, (String)null);
         this.name = var1;
         if(var2 != null) {
            StringBuffer var3 = new StringBuffer();
            var3.append(var1);
            StringBuffer var5 = var3.append(':');
            StringBuffer var6 = var3.append(' ');
            var3.append(var2);
            String var8 = var3.toString();
            this.line = var8;
         }
      }

      public String getName() {
         return this.name;
      }

      public String getValue() {
         int var1 = this.line.indexOf(58);
         String var5;
         if(var1 < 0) {
            var5 = this.line;
         } else {
            int var2 = this.line.length();

            int var3;
            for(var3 = var1 + 1; var3 < var2; ++var3) {
               char var4 = this.line.charAt(var3);
               if(var4 != 32 && var4 != 9 && var4 != 13 && var4 != 10) {
                  break;
               }
            }

            var5 = this.line.substring(var3);
         }

         return var5;
      }

      boolean nameEquals(String var1) {
         return this.name.equalsIgnoreCase(var1);
      }

      void setValue(String var1) {
         StringBuffer var2 = new StringBuffer();
         String var3 = this.name;
         var2.append(var3);
         StringBuffer var5 = var2.append(':');
         StringBuffer var6 = var2.append(' ');
         var2.append(var1);
         String var8 = var2.toString();
         this.line = var8;
      }
   }

   static class HeaderEnumeration implements Iterator, Enumeration {

      private boolean matching;
      private String[] names;
      private InternetHeaders.InternetHeader nextHeader;
      private Iterator source;
      private boolean stringForm;


      HeaderEnumeration(Iterator var1, String[] var2, boolean var3, boolean var4) {
         this.source = var1;
         this.names = var2;
         this.stringForm = var3;
         this.matching = var4;
      }

      private InternetHeaders.InternetHeader getNext() {
         label34:
         while(true) {
            InternetHeaders.InternetHeader var1;
            if(this.source.hasNext()) {
               var1 = (InternetHeaders.InternetHeader)this.source.next();
               if(var1.line == null) {
                  continue;
               }

               if(this.names == null) {
                  if(this.matching) {
                     var1 = null;
                  }
               } else {
                  int var2 = 0;

                  while(true) {
                     int var3 = this.names.length;
                     if(var2 < var3) {
                        String var4 = this.names[var2];
                        if(!var1.nameEquals(var4)) {
                           ++var2;
                           continue;
                        }

                        if(!this.matching) {
                           var1 = this.getNext();
                        }
                        break;
                     }

                     if(this.matching) {
                        continue label34;
                     }
                     break;
                  }
               }
            } else {
               var1 = null;
            }

            return var1;
         }
      }

      public boolean hasMoreElements() {
         return this.hasNext();
      }

      public boolean hasNext() {
         if(this.nextHeader == null) {
            InternetHeaders.InternetHeader var1 = this.getNext();
            this.nextHeader = var1;
         }

         boolean var2;
         if(this.nextHeader != null) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      public Object next() {
         if(this.nextHeader == null) {
            InternetHeaders.InternetHeader var1 = this.getNext();
            this.nextHeader = var1;
         }

         if(this.nextHeader == null) {
            throw new NoSuchElementException();
         } else {
            Object var2 = this.nextHeader;
            this.nextHeader = null;
            if(this.stringForm) {
               var2 = ((InternetHeaders.InternetHeader)var2).line;
            }

            return var2;
         }
      }

      public Object nextElement() {
         return this.next();
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }
   }
}
