package org.apache.commons.httpclient;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.util.ParameterParser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HeaderElement extends NameValuePair {

   private static final Log LOG = LogFactory.getLog(HeaderElement.class);
   private NameValuePair[] parameters;


   public HeaderElement() {
      this((String)null, (String)null, (NameValuePair[])null);
   }

   public HeaderElement(String var1, String var2) {
      this(var1, var2, (NameValuePair[])null);
   }

   public HeaderElement(String var1, String var2, NameValuePair[] var3) {
      super(var1, var2);
      this.parameters = null;
      this.parameters = var3;
   }

   public HeaderElement(char[] var1) {
      int var2 = var1.length;
      this(var1, 0, var2);
   }

   public HeaderElement(char[] var1, int var2, int var3) {
      this();
      if(var1 != null) {
         List var4 = (new ParameterParser()).parse(var1, var2, var3, ';');
         if(var4.size() > 0) {
            NameValuePair var5 = (NameValuePair)var4.remove(0);
            String var6 = var5.getName();
            this.setName(var6);
            String var7 = var5.getValue();
            this.setValue(var7);
            if(var4.size() > 0) {
               NameValuePair[] var8 = new NameValuePair[var4.size()];
               NameValuePair[] var9 = (NameValuePair[])((NameValuePair[])var4.toArray(var8));
               this.parameters = var9;
            }
         }
      }
   }

   public static final HeaderElement[] parse(String var0) throws HttpException {
      LOG.trace("enter HeaderElement.parse(String)");
      HeaderElement[] var1;
      if(var0 == null) {
         var1 = new HeaderElement[0];
      } else {
         var1 = parseElements(var0.toCharArray());
      }

      return var1;
   }

   public static final HeaderElement[] parseElements(String var0) {
      LOG.trace("enter HeaderElement.parseElements(String)");
      HeaderElement[] var1;
      if(var0 == null) {
         var1 = new HeaderElement[0];
      } else {
         var1 = parseElements(var0.toCharArray());
      }

      return var1;
   }

   public static final HeaderElement[] parseElements(char[] var0) {
      LOG.trace("enter HeaderElement.parseElements(char[])");
      HeaderElement[] var1;
      if(var0 == null) {
         var1 = new HeaderElement[0];
      } else {
         ArrayList var2 = new ArrayList();
         int var3 = 0;
         byte var4 = 0;
         int var5 = var0.length;

         for(boolean var6 = false; var3 < var5; ++var3) {
            char var7 = var0[var3];
            if(var7 == 34) {
               if(!var6) {
                  var6 = true;
               } else {
                  var6 = false;
               }
            }

            HeaderElement var8 = null;
            if(!var6 && var7 == 44) {
               var8 = new HeaderElement(var0, var4, var3);
               int var9 = var3 + 1;
            } else {
               int var11 = var5 - 1;
               if(var3 == var11) {
                  var8 = new HeaderElement(var0, var4, var5);
               }
            }

            if(var8 != null && var8.getName() != null) {
               var2.add(var8);
            }
         }

         HeaderElement[] var12 = new HeaderElement[var2.size()];
         var1 = (HeaderElement[])((HeaderElement[])var2.toArray(var12));
      }

      return var1;
   }

   public NameValuePair getParameterByName(String var1) {
      LOG.trace("enter HeaderElement.getParameterByName(String)");
      if(var1 == null) {
         throw new IllegalArgumentException("Name may not be null");
      } else {
         NameValuePair var2 = null;
         NameValuePair[] var3 = this.getParameters();
         if(var3 != null) {
            int var4 = 0;

            while(true) {
               int var5 = var3.length;
               if(var4 >= var5) {
                  break;
               }

               NameValuePair var6 = var3[var4];
               if(var6.getName().equalsIgnoreCase(var1)) {
                  var2 = var6;
                  break;
               }

               ++var4;
            }
         }

         return var2;
      }
   }

   public NameValuePair[] getParameters() {
      return this.parameters;
   }
}
