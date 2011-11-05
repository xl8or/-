package gnu.inet.smtp;

import gnu.inet.smtp.Parameter;
import java.util.ArrayList;
import java.util.List;

public final class ParameterList {

   private List parameters;


   public ParameterList() {
      ArrayList var1 = new ArrayList();
      this.parameters = var1;
   }

   public void add(Parameter var1) {
      List var2 = this.parameters;
      synchronized(var2) {
         this.parameters.add(var1);
      }
   }

   public Parameter get(int var1) {
      List var2 = this.parameters;
      synchronized(var2) {
         Parameter var3 = (Parameter)this.parameters.get(var1);
         return var3;
      }
   }

   public int size() {
      List var1 = this.parameters;
      synchronized(var1) {
         int var2 = this.parameters.size();
         return var2;
      }
   }

   public String toString() {
      List var1 = this.parameters;
      synchronized(var1) {
         int var2 = this.parameters.size();
         String var13;
         if(var2 == 0) {
            var13 = "";
         } else {
            StringBuffer var3 = new StringBuffer();
            Object var4 = this.parameters.get(0);
            var3.append(var4);

            for(int var6 = 1; var6 < var2; ++var6) {
               StringBuffer var7 = var3.append(' ');
               Object var8 = this.parameters.get(var6);
               var3.append(var8);
            }

            String var10 = var3.toString();
            var13 = var10;
         }

         return var13;
      }
   }
}
