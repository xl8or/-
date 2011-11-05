package org.apache.commons.httpclient;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.httpclient.Header;

public class HeaderGroup {

   private List headers;


   public HeaderGroup() {
      ArrayList var1 = new ArrayList();
      this.headers = var1;
   }

   public void addHeader(Header var1) {
      this.headers.add(var1);
   }

   public void clear() {
      this.headers.clear();
   }

   public boolean containsHeader(String var1) {
      Iterator var2 = this.headers.iterator();

      boolean var3;
      while(true) {
         if(var2.hasNext()) {
            if(!((Header)var2.next()).getName().equalsIgnoreCase(var1)) {
               continue;
            }

            var3 = true;
            break;
         }

         var3 = false;
         break;
      }

      return var3;
   }

   public Header[] getAllHeaders() {
      List var1 = this.headers;
      Header[] var2 = new Header[this.headers.size()];
      return (Header[])((Header[])var1.toArray(var2));
   }

   public Header getCondensedHeader(String var1) {
      Header[] var2 = this.getHeaders(var1);
      Header var3;
      if(var2.length == 0) {
         var3 = null;
      } else if(var2.length == 1) {
         String var4 = var2[0].getName();
         String var5 = var2[0].getValue();
         var3 = new Header(var4, var5);
      } else {
         String var6 = var2[0].getValue();
         StringBuffer var7 = new StringBuffer(var6);
         int var8 = 1;

         while(true) {
            int var9 = var2.length;
            if(var8 >= var9) {
               String var13 = var1.toLowerCase();
               String var14 = var7.toString();
               var3 = new Header(var13, var14);
               break;
            }

            StringBuffer var10 = var7.append(", ");
            String var11 = var2[var8].getValue();
            var7.append(var11);
            ++var8;
         }
      }

      return var3;
   }

   public Header getFirstHeader(String var1) {
      Iterator var2 = this.headers.iterator();

      Header var4;
      while(true) {
         if(var2.hasNext()) {
            Header var3 = (Header)var2.next();
            if(!var3.getName().equalsIgnoreCase(var1)) {
               continue;
            }

            var4 = var3;
            break;
         }

         var4 = null;
         break;
      }

      return var4;
   }

   public Header[] getHeaders(String var1) {
      ArrayList var2 = new ArrayList();
      Iterator var3 = this.headers.iterator();

      while(var3.hasNext()) {
         Header var4 = (Header)var3.next();
         if(var4.getName().equalsIgnoreCase(var1)) {
            var2.add(var4);
         }
      }

      Header[] var6 = new Header[var2.size()];
      return (Header[])((Header[])var2.toArray(var6));
   }

   public Iterator getIterator() {
      return this.headers.iterator();
   }

   public Header getLastHeader(String var1) {
      int var2 = this.headers.size() - 1;

      Header var4;
      while(true) {
         if(var2 < 0) {
            var4 = null;
            break;
         }

         Header var3 = (Header)this.headers.get(var2);
         if(var3.getName().equalsIgnoreCase(var1)) {
            var4 = var3;
            break;
         }

         var2 += -1;
      }

      return var4;
   }

   public void removeHeader(Header var1) {
      this.headers.remove(var1);
   }

   public void setHeaders(Header[] var1) {
      this.clear();
      int var2 = 0;

      while(true) {
         int var3 = var1.length;
         if(var2 >= var3) {
            return;
         }

         Header var4 = var1[var2];
         this.addHeader(var4);
         ++var2;
      }
   }
}
