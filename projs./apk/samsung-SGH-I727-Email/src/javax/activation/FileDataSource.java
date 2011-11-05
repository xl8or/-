package javax.activation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.activation.DataSource;
import javax.activation.FileTypeMap;

public class FileDataSource implements DataSource {

   private File file;
   private FileTypeMap typeMap;


   public FileDataSource(File var1) {
      this.file = var1;
   }

   public FileDataSource(String var1) {
      File var2 = new File(var1);
      this(var2);
   }

   public String getContentType() {
      String var3;
      if(this.typeMap == null) {
         FileTypeMap var1 = FileTypeMap.getDefaultFileTypeMap();
         File var2 = this.file;
         var3 = var1.getContentType(var2);
      } else {
         FileTypeMap var4 = this.typeMap;
         File var5 = this.file;
         var3 = var4.getContentType(var5);
      }

      return var3;
   }

   public File getFile() {
      return this.file;
   }

   public InputStream getInputStream() throws IOException {
      File var1 = this.file;
      return new FileInputStream(var1);
   }

   public String getName() {
      return this.file.getName();
   }

   public OutputStream getOutputStream() throws IOException {
      File var1 = this.file;
      return new FileOutputStream(var1);
   }

   public void setFileTypeMap(FileTypeMap var1) {
      this.typeMap = var1;
   }
}
