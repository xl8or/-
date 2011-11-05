package gnu.inet.gopher;

import gnu.inet.gopher.DirectoryEntry;
import gnu.inet.util.LineInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ProtocolException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public final class DirectoryListing implements Iterator {

   private static final String DOT = ".";
   private DirectoryEntry current;
   private boolean doneRead = 0;
   private LineInputStream in;


   DirectoryListing(InputStream var1) {
      LineInputStream var2 = new LineInputStream(var1);
      this.in = var2;
   }

   void fetch() throws IOException {
      if(!this.doneRead) {
         String var1 = this.in.readLine();
         byte var2 = ".".equals(var1);
         if(var2 != 0) {
            this.current = null;
         } else {
            switch(var1.charAt(0)) {
            case 43:
            case 48:
            case 49:
            case 50:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 73:
            case 84:
            case 103:
               break;
            case 51:
               boolean var6 = true;
               break;
            default:
               var2 = 51;
            }

            byte var3 = 1;
            int var4 = var1.indexOf(9, var3);
            if(var4 == -1) {
               String var5 = "Invalid directory entry: " + var1;
               throw new ProtocolException(var5);
            } else {
               String var7 = var1.substring(var3, var4);
               int var8 = var4 + 1;
               int var9 = var1.indexOf(9, var8);
               if(var9 == -1) {
                  String var10 = "Invalid directory entry: " + var1;
                  throw new ProtocolException(var10);
               } else {
                  String var11 = var1.substring(var8, var9);
                  int var12 = var9 + 1;
                  int var13 = var1.indexOf(9, var12);
                  if(var13 == -1) {
                     String var14 = "Invalid directory entry: " + var1;
                     throw new ProtocolException(var14);
                  } else {
                     String var15 = var1.substring(var12, var13);
                     int var16 = var13 + 1;
                     int var17 = Integer.parseInt(var1.substring(var16));
                     DirectoryEntry var18 = new DirectoryEntry(var2, var7, var11, var15, var17);
                     this.current = var18;
                  }
               }
            }
         }
      }
   }

   public boolean hasNext() {
      boolean var1;
      try {
         this.fetch();
      } catch (IOException var3) {
         var1 = false;
         return var1;
      }

      if(this.current != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public Object next() {
      try {
         DirectoryEntry var1 = this.nextEntry();
         return var1;
      } catch (IOException var6) {
         StringBuilder var3 = (new StringBuilder()).append("I/O error: ");
         String var4 = var6.getMessage();
         String var5 = var3.append(var4).toString();
         throw new NoSuchElementException(var5);
      }
   }

   public DirectoryEntry nextEntry() throws IOException {
      this.fetch();
      if(this.current == null) {
         throw new NoSuchElementException();
      } else {
         this.doneRead = (boolean)0;
         return this.current;
      }
   }

   public void remove() {
      throw new UnsupportedOperationException();
   }
}
