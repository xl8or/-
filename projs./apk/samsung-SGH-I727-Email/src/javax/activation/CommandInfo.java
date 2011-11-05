package javax.activation;

import java.beans.Beans;
import java.io.Externalizable;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import javax.activation.CommandObject;
import javax.activation.DataHandler;

public class CommandInfo {

   private String className;
   private String verb;


   public CommandInfo(String var1, String var2) {
      this.verb = var1;
      this.className = var2;
   }

   public String getCommandClass() {
      return this.className;
   }

   public String getCommandName() {
      return this.verb;
   }

   public Object getCommandObject(DataHandler var1, ClassLoader var2) throws IOException, ClassNotFoundException {
      String var3 = this.className;
      Object var4 = Beans.instantiate(var2, var3);
      if(var4 != null) {
         if(var4 instanceof CommandObject) {
            CommandObject var5 = (CommandObject)var4;
            String var6 = this.verb;
            var5.setCommandContext(var6, var1);
         } else if(var1 != null && var4 instanceof Externalizable) {
            InputStream var7 = var1.getInputStream();
            if(var7 != null) {
               Externalizable var8 = (Externalizable)var4;
               ObjectInputStream var9 = new ObjectInputStream(var7);
               var8.readExternal(var9);
            }
         }
      }

      return var4;
   }
}
