package org.apache.harmony.javax.security.auth.callback;

import java.io.Serializable;
import org.apache.harmony.javax.security.auth.callback.Callback;

public class ConfirmationCallback implements Callback, Serializable {

   public static final int CANCEL = 2;
   public static final int ERROR = 2;
   public static final int INFORMATION = 0;
   public static final int NO = 1;
   public static final int OK = 3;
   public static final int OK_CANCEL_OPTION = 2;
   public static final int UNSPECIFIED_OPTION = 255;
   public static final int WARNING = 1;
   public static final int YES = 0;
   public static final int YES_NO_CANCEL_OPTION = 1;
   public static final int YES_NO_OPTION = 0;
   private static final long serialVersionUID = -9095656433782481624L;
   private int defaultOption;
   private int messageType;
   private int optionType = -1;
   private String[] options;
   private String prompt;
   private int selection;


   public ConfirmationCallback(int var1, int var2, int var3) {
      if(var1 <= 2 && var1 >= 0) {
         switch(var2) {
         case 0:
            if(var3 != 0 && var3 != 1) {
               throw new IllegalArgumentException("auth.17");
            }
            break;
         case 1:
            if(var3 != 0 && var3 != 1 && var3 != 2) {
               throw new IllegalArgumentException("auth.17");
            }
            break;
         case 2:
            if(var3 != 3 && var3 != 2) {
               throw new IllegalArgumentException("auth.17");
            }
            break;
         default:
            throw new IllegalArgumentException("auth.18");
         }

         this.messageType = var1;
         this.optionType = var2;
         this.defaultOption = var3;
      } else {
         throw new IllegalArgumentException("auth.16");
      }
   }

   public ConfirmationCallback(int var1, String[] var2, int var3) {
      if(var1 <= 2 && var1 >= 0) {
         if(var2 != null && var2.length != 0) {
            int var4 = 0;

            while(true) {
               int var5 = var2.length;
               if(var4 >= var5) {
                  if(var3 >= 0) {
                     int var6 = var2.length;
                     if(var3 < var6) {
                        this.options = var2;
                        this.defaultOption = var3;
                        this.messageType = var1;
                        return;
                     }
                  }

                  throw new IllegalArgumentException("auth.17");
               }

               if(var2[var4] == false || var2[var4].length() == 0) {
                  throw new IllegalArgumentException("auth.1A");
               }

               ++var4;
            }
         } else {
            throw new IllegalArgumentException("auth.1A");
         }
      } else {
         throw new IllegalArgumentException("auth.16");
      }
   }

   public ConfirmationCallback(String var1, int var2, int var3, int var4) {
      if(var1 != null && var1.length() != 0) {
         if(var2 <= 2 && var2 >= 0) {
            switch(var3) {
            case 0:
               if(var4 != 0 && var4 != 1) {
                  throw new IllegalArgumentException("auth.17");
               }
               break;
            case 1:
               if(var4 != 0 && var4 != 1 && var4 != 2) {
                  throw new IllegalArgumentException("auth.17");
               }
               break;
            case 2:
               if(var4 != 3 && var4 != 2) {
                  throw new IllegalArgumentException("auth.17");
               }
               break;
            default:
               throw new IllegalArgumentException("auth.18");
            }

            this.prompt = var1;
            this.messageType = var2;
            this.optionType = var3;
            this.defaultOption = var4;
         } else {
            throw new IllegalArgumentException("auth.16");
         }
      } else {
         throw new IllegalArgumentException("auth.14");
      }
   }

   public ConfirmationCallback(String var1, int var2, String[] var3, int var4) {
      if(var1 != null && var1.length() != 0) {
         if(var2 <= 2 && var2 >= 0) {
            if(var3 != null && var3.length != 0) {
               int var5 = 0;

               while(true) {
                  int var6 = var3.length;
                  if(var5 >= var6) {
                     if(var4 >= 0) {
                        int var7 = var3.length;
                        if(var4 < var7) {
                           this.options = var3;
                           this.defaultOption = var4;
                           this.messageType = var2;
                           this.prompt = var1;
                           return;
                        }
                     }

                     throw new IllegalArgumentException("auth.17");
                  }

                  if(var3[var5] == false || var3[var5].length() == 0) {
                     throw new IllegalArgumentException("auth.1A");
                  }

                  ++var5;
               }
            } else {
               throw new IllegalArgumentException("auth.1A");
            }
         } else {
            throw new IllegalArgumentException("auth.16");
         }
      } else {
         throw new IllegalArgumentException("auth.14");
      }
   }

   public int getDefaultOption() {
      return this.defaultOption;
   }

   public int getMessageType() {
      return this.messageType;
   }

   public int getOptionType() {
      return this.optionType;
   }

   public String[] getOptions() {
      return this.options;
   }

   public String getPrompt() {
      return this.prompt;
   }

   public int getSelectedIndex() {
      return this.selection;
   }

   public void setSelectedIndex(int var1) {
      if(this.options != null) {
         if(var1 >= 0) {
            int var2 = this.options.length;
            if(var1 <= var2) {
               this.selection = var1;
               return;
            }
         }

         throw new ArrayIndexOutOfBoundsException("auth.1B");
      } else {
         switch(this.optionType) {
         case 0:
            if(var1 != 0 && var1 != 1) {
               throw new IllegalArgumentException("auth.19");
            }
            break;
         case 1:
            if(var1 != 0 && var1 != 1 && var1 != 2) {
               throw new IllegalArgumentException("auth.19");
            }
            break;
         case 2:
            if(var1 != 3 && var1 != 2) {
               throw new IllegalArgumentException("auth.19");
            }
         }

         this.selection = var1;
      }
   }
}
