package gnu.activation.viewers;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.activation.CommandObject;
import javax.activation.DataHandler;

public class ImageViewer extends Component implements CommandObject {

   private Image image;


   public ImageViewer() {}

   public Dimension getPreferredSize() {
      Dimension var1 = new Dimension(0, 0);
      if(this.image != null) {
         int var2 = this.image.getWidth(this);
         var1.width = var2;
         int var3 = this.image.getHeight(this);
         var1.height = var3;
      }

      return var1;
   }

   public boolean imageUpdate(Image var1, int var2, int var3, int var4, int var5, int var6) {
      boolean var7;
      if((var2 & 32) != 0) {
         this.image = var1;
         this.invalidate();
         this.repaint();
         var7 = false;
      } else if((var2 & 64) == 0) {
         var7 = true;
      } else {
         var7 = false;
      }

      return var7;
   }

   public void paint(Graphics var1) {
      if(this.image != null) {
         int var2 = this.image.getWidth(this);
         int var3 = this.image.getHeight(this);
         Dimension var4 = new Dimension(var2, var3);
         if(var4.width > -1) {
            if(var4.height > -1) {
               Dimension var5 = this.getSize();
               Image var6 = this.image;
               int var7 = var5.width;
               int var8 = var5.height;
               int var9 = var4.width;
               int var10 = var4.height;
               byte var12 = 0;
               byte var13 = 0;
               byte var14 = 0;
               var1.drawImage(var6, 0, var12, var7, var8, var13, var14, var9, var10, this);
            }
         }
      }
   }

   public void setCommandContext(String var1, DataHandler var2) throws IOException {
      InputStream var3 = var2.getInputStream();
      ByteArrayOutputStream var4 = new ByteArrayOutputStream();
      byte[] var5 = new byte[4096];

      for(int var6 = var3.read(var5); var6 != -1; var6 = var3.read(var5)) {
         var4.write(var5, 0, var6);
      }

      var3.close();
      Toolkit var7 = this.getToolkit();
      byte[] var8 = var4.toByteArray();
      Image var9 = var7.createImage(var8);

      try {
         MediaTracker var10 = new MediaTracker(this);
         var10.addImage(var9, 0);
         var10.waitForID(0);
      } catch (InterruptedException var13) {
         ;
      }

      var7.prepareImage(var9, -1, -1, this);
   }
}
