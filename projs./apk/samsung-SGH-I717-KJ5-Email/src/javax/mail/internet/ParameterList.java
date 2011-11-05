package javax.mail.internet;

import gnu.inet.util.GetSystemPropertyAction;
import java.io.UnsupportedEncodingException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import javax.mail.internet.HeaderTokenizer;
import javax.mail.internet.MimeUtility;
import javax.mail.internet.ParseException;

public class ParameterList {

   private LinkedHashMap list;


   public ParameterList() {
      LinkedHashMap var1 = new LinkedHashMap();
      this.list = var1;
   }

   public ParameterList(String var1) throws ParseException {
      LinkedHashMap var2 = new LinkedHashMap();
      this.list = var2;
      Object var3 = AccessController.doPrivileged(new GetSystemPropertyAction("mail.mime.decodeparameters"));
      boolean var4 = "true".equals(var3);
      LinkedHashMap var5 = new LinkedHashMap();
      HeaderTokenizer var6 = new HeaderTokenizer(var1, "()<>@,;:\\\"\t []/?=");
      int var7 = 0;

      while(true) {
         do {
            if(var7 == -1) {
               int var48 = this.list.size();
               String[] var49 = new String[var48];
               Object[] var50 = this.list.keySet().toArray(var49);

               for(int var51 = 0; var51 < var48; ++var51) {
                  LinkedHashMap var52 = this.list;
                  String var53 = var49[var51];
                  Object var64 = var52.get(var53);
                  if(var64 instanceof ArrayList) {
                     ArrayList var54 = (ArrayList)var64;
                     StringBuffer var55 = new StringBuffer();
                     Iterator var56 = var54.iterator();

                     while(var56.hasNext()) {
                        String var57 = (String)var56.next();
                        if(var57 != null) {
                           var55.append(var57);
                        }
                     }

                     String var59 = var49[var51];
                     String var60 = (String)var5.get(var59);
                     String var61 = var49[var51];
                     String var62 = var55.toString();
                     this.set(var61, var62, var60);
                  }
               }

               return;
            }

            var7 = var6.next().getType();
         } while(var7 == -1);

         if(var7 != 59) {
            String var8 = "expected \';\': " + var1;
            throw new ParseException(var8);
         }

         HeaderTokenizer.Token var9 = var6.next();
         if(var9.getType() != -1) {
            String var10 = "expected parameter name: " + var1;
            throw new ParseException(var10);
         }

         String var11 = var9.getValue().toLowerCase();
         if(var6.next().getType() != 61) {
            String var12 = "expected \'=\': " + var1;
            throw new ParseException(var12);
         }

         HeaderTokenizer.Token var13 = var6.next();
         int var14 = var13.getType();
         if(var14 != -1 && var14 != -1) {
            String var15 = "expected parameter value: " + var1;
            throw new ParseException(var15);
         }

         String var16 = var13.getValue();
         int var17 = var11.indexOf(42);
         if(var4 && var17 > 0) {
            label126: {
               int var18 = var11.length();
               int var19 = var18 - 1;
               String var23;
               if(var17 != var19) {
                  label129: {
                     int var20 = var18 - 3;
                     if(var17 == var20) {
                        int var21 = var17 + 1;
                        if(var11.charAt(var21) == 48) {
                           int var22 = var17 + 2;
                           if(var11.charAt(var22) == 42) {
                              break label129;
                           }
                        }
                     }

                     int var39 = var18 - 1;
                     if(var11.charAt(var39) == 42) {
                        --var18;
                     }

                     int var40 = var17 + 1;

                     int var41;
                     try {
                        var41 = Integer.parseInt(var11.substring(var40, var18));
                        if(var41 < 1) {
                           throw new NumberFormatException();
                        }
                     } catch (NumberFormatException var63) {
                        String var43 = "invalid section: " + var11;
                        throw new ParseException(var43);
                     }

                     String var44 = var11.substring(0, var17);
                     String var45 = (String)var5.get(var44);
                     ArrayList var46 = (ArrayList)this.list.get(var44);
                     if(var45 == null || var46 == null) {
                        String var47 = "no initial extended parameter for \'" + var44 + "\'";
                        throw new ParseException(var47);
                     }

                     if(var14 == -1) {
                        var23 = this.decode(var16, var45);
                     } else {
                        var23 = var16;
                     }

                     this.set(var46, var41, var23);
                     break label126;
                  }
               }

               var23 = var11.substring(0, var17);
               int var24 = var16.indexOf(39);
               if(var24 == -1) {
                  String var25 = "no charset specified: " + var16;
                  throw new ParseException(var25);
               }

               String var26 = MimeUtility.javaCharset(var16.substring(0, var24));
               var5.put(var23, var26);
               int var28 = var24 + 1;
               int var29 = var16.indexOf(39, var28);
               int var30 = var24;

               int var33;
               for(int var31 = var29; var31 != -1; var31 = var33) {
                  int var32 = var31 + 1;
                  var33 = var16.indexOf(39, var32);
                  var30 = var31;
               }

               int var34 = var30 + 1;
               String var35 = var16.substring(var34);
               String var36 = this.decode(var35, var26);
               ArrayList var37 = new ArrayList();
               this.set(var37, 0, var36);
               this.list.put(var23, var37);
            }

            var7 = var14;
         } else {
            this.set(var11, var16, (String)null);
            var7 = var14;
         }
      }
   }

   private String decode(String var1, String var2) throws ParseException {
      int var3 = 0;
      char[] var4 = var1.toCharArray();
      int var5 = var4.length;
      byte[] var6 = new byte[var5];

      int var7;
      int var17;
      for(var7 = var3; var3 < var5; var17 = var3 + 1) {
         char var8 = var4[var3];
         if(var8 == 37) {
            if(var3 + 3 > var5) {
               String var9 = "malformed: " + var1;
               throw new ParseException(var9);
            }

            int var10 = var3 + 2;
            int var11 = Character.digit(var4[var10], 16);
            int var12 = var3 + 1;
            int var13 = Character.digit(var4[var12], 16) * 16;
            int var14 = var11 + var13;
            int var15 = var7 + 1;
            byte var16 = (byte)var14;
            var6[var7] = var16;
            var3 += 2;
            var7 = var15;
         } else {
            int var18 = var7 + 1;
            byte var19 = (byte)var8;
            var6[var7] = var19;
            var7 = var18;
         }
      }

      try {
         String var23 = new String(var6, 0, var7, var2);
         return var23;
      } catch (UnsupportedEncodingException var22) {
         String var21 = "Unsupported encoding: " + var2;
         throw new ParseException(var21);
      }
   }

   private void set(ArrayList var1, int var2, Object var3) {
      int var4 = var1.size();

      while(true) {
         int var5 = var4 - 1;
         if(var2 <= var5) {
            var1.set(var2, var3);
            return;
         }

         boolean var6 = var1.add((Object)null);
         ++var4;
      }
   }

   public String get(String var1) {
      LinkedHashMap var2 = this.list;
      String var3 = var1.toLowerCase().trim();
      String[] var4 = (String[])((String[])var2.get(var3));
      String var5;
      if(var4 != null) {
         var5 = var4[0];
      } else {
         var5 = null;
      }

      return var5;
   }

   public Enumeration getNames() {
      Iterator var1 = this.list.keySet().iterator();
      return new ParameterList.ParameterEnumeration(var1);
   }

   public void remove(String var1) {
      LinkedHashMap var2 = this.list;
      String var3 = var1.toLowerCase().trim();
      var2.remove(var3);
   }

   public void set(String var1, String var2) {
      this.set(var1, var2, (String)null);
   }

   public void set(String var1, String var2, String var3) {
      String[] var4 = new String[]{var2, var3};
      LinkedHashMap var5 = this.list;
      String var6 = var1.toLowerCase().trim();
      var5.put(var6, var4);
   }

   public int size() {
      return this.list.size();
   }

   public String toString() {
      return this.toString(0);
   }

   public String toString(int var1) {
      Object var2 = AccessController.doPrivileged(new GetSystemPropertyAction("mail.mime.encodeparameters"));
      boolean var3 = "true".equals(var2);
      StringBuffer var4 = new StringBuffer();
      Iterator var5 = this.list.entrySet().iterator();
      int var6 = var1;

      while(var5.hasNext()) {
         Entry var7 = (Entry)var5.next();
         String var8 = (String)var7.getKey();
         String[] var9 = (String[])((String[])var7.getValue());
         String var10 = var9[0];
         String var11 = var9[1];
         if(var3) {
            label21: {
               String var12;
               try {
                  var12 = MimeUtility.encodeText(var10, var11, "Q");
               } catch (UnsupportedEncodingException var22) {
                  break label21;
               }

               var10 = var12;
            }
         }

         String var13 = MimeUtility.quote(var10, "()<>@,;:\\\"\t []/?=");
         StringBuffer var14 = var4.append("; ");
         var6 += 2;
         int var15 = var8.length();
         int var16 = var13.length();
         if(var15 + var16 + 1 + var6 > 76) {
            StringBuffer var17 = var4.append("\r\n\t");
         }

         var4.append(var8);
         StringBuffer var19 = var4.append('=');
         var4.append(var13);
      }

      return var4.toString();
   }

   static class ParameterEnumeration implements Enumeration {

      Iterator source;


      ParameterEnumeration(Iterator var1) {
         this.source = var1;
      }

      public boolean hasMoreElements() {
         return this.source.hasNext();
      }

      public Object nextElement() {
         return this.source.next();
      }
   }
}
