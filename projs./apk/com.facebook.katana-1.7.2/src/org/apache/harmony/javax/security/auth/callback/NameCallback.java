package org.apache.harmony.javax.security.auth.callback;

import java.io.Serializable;
import org.apache.harmony.javax.security.auth.callback.Callback;

public class NameCallback implements Callback, Serializable {

   private static final long serialVersionUID = 3770938795909392253L;
   private String defaultName;
   private String inputName;
   private String prompt;


   public NameCallback(String var1) {
      this.setPrompt(var1);
   }

   public NameCallback(String var1, String var2) {
      this.setPrompt(var1);
      this.setDefaultName(var2);
   }

   private void setDefaultName(String var1) {
      if(var1 != null && var1.length() != 0) {
         this.defaultName = var1;
      } else {
         throw new IllegalArgumentException("auth.1E");
      }
   }

   private void setPrompt(String var1) {
      if(var1 != null && var1.length() != 0) {
         this.prompt = var1;
      } else {
         throw new IllegalArgumentException("auth.14");
      }
   }

   public String getDefaultName() {
      return this.defaultName;
   }

   public String getName() {
      return this.inputName;
   }

   public String getPrompt() {
      return this.prompt;
   }

   public void setName(String var1) {
      this.inputName = var1;
   }
}
