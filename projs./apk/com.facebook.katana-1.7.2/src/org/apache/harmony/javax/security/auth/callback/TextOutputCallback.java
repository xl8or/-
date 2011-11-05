package org.apache.harmony.javax.security.auth.callback;

import java.io.Serializable;
import org.apache.harmony.javax.security.auth.callback.Callback;

public class TextOutputCallback implements Callback, Serializable {

   public static final int ERROR = 2;
   public static final int INFORMATION = 0;
   public static final int WARNING = 1;
   private static final long serialVersionUID = 1689502495511663102L;
   private String message;
   private int messageType;


   public TextOutputCallback(int var1, String var2) {
      if(var1 <= 2 && var1 >= 0) {
         if(var2 != null && var2.length() != 0) {
            this.messageType = var1;
            this.message = var2;
         } else {
            throw new IllegalArgumentException("auth.1F");
         }
      } else {
         throw new IllegalArgumentException("auth.16");
      }
   }

   public String getMessage() {
      return this.message;
   }

   public int getMessageType() {
      return this.messageType;
   }
}
