package gnu.mail.treeutil;

import java.util.EventObject;

public class StatusEvent extends EventObject {

   public static final int OPERATION_END = 2;
   public static final int OPERATION_START = 0;
   public static final int OPERATION_UPDATE = 1;
   public static final int UNKNOWN = 255;
   protected int maximum = -1;
   protected int minimum = -1;
   protected String operation;
   protected int type;
   protected int value = -1;


   public StatusEvent(Object var1, int var2, String var3) {
      super(var1);
      switch(var2) {
      case 0:
      case 1:
      case 2:
         this.type = var2;
         this.operation = var3;
         return;
      default:
         String var4 = "Illegal event type: " + var2;
         throw new IllegalArgumentException(var4);
      }
   }

   public StatusEvent(Object var1, int var2, String var3, int var4, int var5, int var6) {
      super(var1);
      switch(var2) {
      case 0:
      case 1:
      case 2:
         this.type = var2;
         this.operation = var3;
         this.minimum = var4;
         this.maximum = var5;
         this.value = var6;
         return;
      default:
         String var7 = "Illegal event type: " + var2;
         throw new IllegalArgumentException(var7);
      }
   }

   public int getMaximum() {
      return this.maximum;
   }

   public int getMinimum() {
      return this.minimum;
   }

   public String getOperation() {
      return this.operation;
   }

   public int getType() {
      return this.type;
   }

   public int getValue() {
      return this.value;
   }
}
