package org.jivesoftware.smack;

import java.io.PrintStream;
import java.io.PrintWriter;
import org.jivesoftware.smack.packet.StreamError;
import org.jivesoftware.smack.packet.XMPPError;

public class XMPPException extends Exception {

   private XMPPError error = null;
   private StreamError streamError = null;
   private Throwable wrappedThrowable = null;


   public XMPPException() {}

   public XMPPException(String var1) {
      super(var1);
   }

   public XMPPException(String var1, Throwable var2) {
      super(var1);
      this.wrappedThrowable = var2;
   }

   public XMPPException(String var1, XMPPError var2) {
      super(var1);
      this.error = var2;
   }

   public XMPPException(String var1, XMPPError var2, Throwable var3) {
      super(var1);
      this.error = var2;
      this.wrappedThrowable = var3;
   }

   public XMPPException(Throwable var1) {
      this.wrappedThrowable = var1;
   }

   public XMPPException(StreamError var1) {
      this.streamError = var1;
   }

   public XMPPException(XMPPError var1) {
      this.error = var1;
   }

   public String getMessage() {
      String var1 = super.getMessage();
      if(var1 == null && this.error != null) {
         var1 = this.error.toString();
      } else if(var1 == null && this.streamError != null) {
         var1 = this.streamError.toString();
      }

      return var1;
   }

   public StreamError getStreamError() {
      return this.streamError;
   }

   public Throwable getWrappedThrowable() {
      return this.wrappedThrowable;
   }

   public XMPPError getXMPPError() {
      return this.error;
   }

   public void printStackTrace() {
      PrintStream var1 = System.err;
      this.printStackTrace(var1);
   }

   public void printStackTrace(PrintStream var1) {
      super.printStackTrace(var1);
      if(this.wrappedThrowable != null) {
         var1.println("Nested Exception: ");
         this.wrappedThrowable.printStackTrace(var1);
      }
   }

   public void printStackTrace(PrintWriter var1) {
      super.printStackTrace(var1);
      if(this.wrappedThrowable != null) {
         var1.println("Nested Exception: ");
         this.wrappedThrowable.printStackTrace(var1);
      }
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      String var2 = super.getMessage();
      if(var2 != null) {
         StringBuilder var3 = var1.append(var2).append(": ");
      }

      if(this.error != null) {
         XMPPError var4 = this.error;
         var1.append(var4);
      }

      if(this.streamError != null) {
         StreamError var6 = this.streamError;
         var1.append(var6);
      }

      if(this.wrappedThrowable != null) {
         StringBuilder var8 = var1.append("\n  -- caused by: ");
         Throwable var9 = this.wrappedThrowable;
         var8.append(var9);
      }

      return var1.toString();
   }
}
