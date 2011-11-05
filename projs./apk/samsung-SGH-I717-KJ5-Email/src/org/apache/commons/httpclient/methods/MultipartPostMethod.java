package org.apache.commons.httpclient.methods;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.httpclient.HttpConnection;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.methods.ExpectContinueMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MultipartPostMethod extends ExpectContinueMethod {

   private static final Log LOG = LogFactory.getLog(MultipartPostMethod.class);
   public static final String MULTIPART_FORM_CONTENT_TYPE = "multipart/form-data";
   private final List parameters;


   public MultipartPostMethod() {
      ArrayList var1 = new ArrayList();
      this.parameters = var1;
   }

   public MultipartPostMethod(String var1) {
      super(var1);
      ArrayList var2 = new ArrayList();
      this.parameters = var2;
   }

   protected void addContentLengthRequestHeader(HttpState var1, HttpConnection var2) throws IOException, HttpException {
      LOG.trace("enter EntityEnclosingMethod.addContentLengthRequestHeader(HttpState, HttpConnection)");
      if(this.getRequestHeader("Content-Length") == null) {
         String var3 = String.valueOf(this.getRequestContentLength());
         this.addRequestHeader("Content-Length", var3);
      }

      this.removeRequestHeader("Transfer-Encoding");
   }

   protected void addContentTypeRequestHeader(HttpState var1, HttpConnection var2) throws IOException, HttpException {
      LOG.trace("enter EntityEnclosingMethod.addContentTypeRequestHeader(HttpState, HttpConnection)");
      if(!this.parameters.isEmpty()) {
         StringBuffer var3 = new StringBuffer("multipart/form-data");
         if(Part.getBoundary() != null) {
            StringBuffer var4 = var3.append("; boundary=");
            String var5 = Part.getBoundary();
            var3.append(var5);
         }

         String var7 = var3.toString();
         this.setRequestHeader("Content-Type", var7);
      }
   }

   public void addParameter(String var1, File var2) throws FileNotFoundException {
      LOG.trace("enter MultipartPostMethod.addParameter(String parameterName, File parameterFile)");
      FilePart var3 = new FilePart(var1, var2);
      this.parameters.add(var3);
   }

   public void addParameter(String var1, String var2) {
      LOG.trace("enter addParameter(String parameterName, String parameterValue)");
      StringPart var3 = new StringPart(var1, var2);
      this.parameters.add(var3);
   }

   public void addParameter(String var1, String var2, File var3) throws FileNotFoundException {
      LOG.trace("enter MultipartPostMethod.addParameter(String parameterName, String fileName, File parameterFile)");
      FilePart var4 = new FilePart(var1, var2, var3);
      this.parameters.add(var4);
   }

   public void addPart(Part var1) {
      LOG.trace("enter addPart(Part part)");
      this.parameters.add(var1);
   }

   protected void addRequestHeaders(HttpState var1, HttpConnection var2) throws IOException, HttpException {
      LOG.trace("enter MultipartPostMethod.addRequestHeaders(HttpState state, HttpConnection conn)");
      super.addRequestHeaders(var1, var2);
      this.addContentLengthRequestHeader(var1, var2);
      this.addContentTypeRequestHeader(var1, var2);
   }

   public String getName() {
      return "POST";
   }

   public Part[] getParts() {
      List var1 = this.parameters;
      Part[] var2 = new Part[this.parameters.size()];
      return (Part[])((Part[])var1.toArray(var2));
   }

   protected long getRequestContentLength() throws IOException {
      LOG.trace("enter MultipartPostMethod.getRequestContentLength()");
      return Part.getLengthOfParts(this.getParts());
   }

   protected boolean hasRequestContent() {
      return true;
   }

   public void recycle() {
      LOG.trace("enter MultipartPostMethod.recycle()");
      super.recycle();
      this.parameters.clear();
   }

   protected boolean writeRequestBody(HttpState var1, HttpConnection var2) throws IOException, HttpException {
      LOG.trace("enter MultipartPostMethod.writeRequestBody(HttpState state, HttpConnection conn)");
      OutputStream var3 = var2.getRequestOutputStream();
      Part[] var4 = this.getParts();
      Part.sendParts(var3, var4);
      return true;
   }
}
