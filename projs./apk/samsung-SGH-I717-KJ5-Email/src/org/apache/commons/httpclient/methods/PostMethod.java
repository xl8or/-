package org.apache.commons.httpclient.methods;

import java.util.Iterator;
import java.util.Vector;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.EntityEnclosingMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.util.EncodingUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PostMethod extends EntityEnclosingMethod {

   public static final String FORM_URL_ENCODED_CONTENT_TYPE = "application/x-www-form-urlencoded";
   private static final Log LOG = LogFactory.getLog(PostMethod.class);
   private Vector params;


   public PostMethod() {
      Vector var1 = new Vector();
      this.params = var1;
   }

   public PostMethod(String var1) {
      super(var1);
      Vector var2 = new Vector();
      this.params = var2;
   }

   public void addParameter(String var1, String var2) throws IllegalArgumentException {
      LOG.trace("enter PostMethod.addParameter(String, String)");
      if(var1 != null && var2 != null) {
         super.clearRequestBody();
         Vector var3 = this.params;
         NameValuePair var4 = new NameValuePair(var1, var2);
         var3.add(var4);
      } else {
         throw new IllegalArgumentException("Arguments to addParameter(String, String) cannot be null");
      }
   }

   public void addParameter(NameValuePair var1) throws IllegalArgumentException {
      LOG.trace("enter PostMethod.addParameter(NameValuePair)");
      if(var1 == null) {
         throw new IllegalArgumentException("NameValuePair may not be null");
      } else {
         String var2 = var1.getName();
         String var3 = var1.getValue();
         this.addParameter(var2, var3);
      }
   }

   public void addParameters(NameValuePair[] var1) {
      LOG.trace("enter PostMethod.addParameters(NameValuePair[])");
      if(var1 == null) {
         LOG.warn("Attempt to addParameters(null) ignored");
      } else {
         super.clearRequestBody();
         int var2 = 0;

         while(true) {
            int var3 = var1.length;
            if(var2 >= var3) {
               return;
            }

            Vector var4 = this.params;
            NameValuePair var5 = var1[var2];
            var4.add(var5);
            ++var2;
         }
      }
   }

   protected void clearRequestBody() {
      LOG.trace("enter PostMethod.clearRequestBody()");
      this.params.clear();
      super.clearRequestBody();
   }

   protected RequestEntity generateRequestEntity() {
      Object var4;
      if(!this.params.isEmpty()) {
         NameValuePair[] var1 = this.getParameters();
         String var2 = this.getRequestCharSet();
         byte[] var3 = EncodingUtil.getAsciiBytes(EncodingUtil.formUrlEncode(var1, var2));
         var4 = new ByteArrayRequestEntity(var3, "application/x-www-form-urlencoded");
      } else {
         var4 = super.generateRequestEntity();
      }

      return (RequestEntity)var4;
   }

   public String getName() {
      return "POST";
   }

   public NameValuePair getParameter(String var1) {
      LOG.trace("enter PostMethod.getParameter(String)");
      NameValuePair var2;
      if(var1 == null) {
         var2 = null;
      } else {
         Iterator var3 = this.params.iterator();

         while(true) {
            if(var3.hasNext()) {
               NameValuePair var4 = (NameValuePair)var3.next();
               String var5 = var4.getName();
               if(!var1.equals(var5)) {
                  continue;
               }

               var2 = var4;
               break;
            }

            var2 = null;
            break;
         }
      }

      return var2;
   }

   public NameValuePair[] getParameters() {
      LOG.trace("enter PostMethod.getParameters()");
      int var1 = this.params.size();
      Object[] var2 = this.params.toArray();
      NameValuePair[] var3 = new NameValuePair[var1];

      for(int var4 = 0; var4 < var1; ++var4) {
         NameValuePair var5 = (NameValuePair)var2[var4];
         var3[var4] = var5;
      }

      return var3;
   }

   protected boolean hasRequestContent() {
      LOG.trace("enter PostMethod.hasRequestContent()");
      byte var1;
      if(!this.params.isEmpty()) {
         var1 = 1;
      } else {
         var1 = super.hasRequestContent();
      }

      return (boolean)var1;
   }

   public boolean removeParameter(String var1) throws IllegalArgumentException {
      LOG.trace("enter PostMethod.removeParameter(String)");
      if(var1 == null) {
         throw new IllegalArgumentException("Argument passed to removeParameter(String) cannot be null");
      } else {
         boolean var2 = false;
         Iterator var3 = this.params.iterator();

         while(var3.hasNext()) {
            String var4 = ((NameValuePair)var3.next()).getName();
            if(var1.equals(var4)) {
               var3.remove();
               var2 = true;
            }
         }

         return var2;
      }
   }

   public boolean removeParameter(String var1, String var2) throws IllegalArgumentException {
      LOG.trace("enter PostMethod.removeParameter(String, String)");
      if(var1 == null) {
         throw new IllegalArgumentException("Parameter name may not be null");
      } else if(var2 == null) {
         throw new IllegalArgumentException("Parameter value may not be null");
      } else {
         Iterator var3 = this.params.iterator();

         boolean var7;
         while(true) {
            if(var3.hasNext()) {
               NameValuePair var4 = (NameValuePair)var3.next();
               String var5 = var4.getName();
               if(!var1.equals(var5)) {
                  continue;
               }

               String var6 = var4.getValue();
               if(!var2.equals(var6)) {
                  continue;
               }

               var3.remove();
               var7 = true;
               break;
            }

            var7 = false;
            break;
         }

         return var7;
      }
   }

   public void setParameter(String var1, String var2) {
      LOG.trace("enter PostMethod.setParameter(String, String)");
      this.removeParameter(var1);
      this.addParameter(var1, var2);
   }

   public void setRequestBody(NameValuePair[] var1) throws IllegalArgumentException {
      LOG.trace("enter PostMethod.setRequestBody(NameValuePair[])");
      if(var1 == null) {
         throw new IllegalArgumentException("Array of parameters may not be null");
      } else {
         this.clearRequestBody();
         this.addParameters(var1);
      }
   }
}
