package gnu.activation.viewers;

import java.awt.Dimension;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.activation.CommandObject;
import javax.activation.DataHandler;

public class TextEditor extends TextArea implements CommandObject, ActionListener {

   private transient DataHandler dh;


   public TextEditor() {
      super("", 24, 80, 1);
   }

   public void actionPerformed(ActionEvent param1) {
      // $FF: Couldn't be decompiled
   }

   public Dimension getPreferredSize() {
      return this.getMinimumSize(24, 80);
   }

   public void setCommandContext(String var1, DataHandler var2) throws IOException {
      this.dh = var2;
      InputStream var3 = var2.getInputStream();
      ByteArrayOutputStream var4 = new ByteArrayOutputStream();
      byte[] var5 = new byte[4096];

      for(int var6 = var3.read(var5); var6 != -1; var6 = var3.read(var5)) {
         var4.write(var5, 0, var6);
      }

      var3.close();
      String var7 = var4.toString();
      this.setText(var7);
   }
}
