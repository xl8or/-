package myorg.bouncycastle;

import java.io.PrintStream;

public class LICENSE {

   public static String licenseText;


   static {
      StringBuilder var0 = (new StringBuilder()).append("Copyright (c) 2000-2008 The Legion Of The Bouncy Castle (http://www.bouncycastle.org) ");
      String var1 = System.getProperty("line.separator");
      StringBuilder var2 = var0.append(var1);
      String var3 = System.getProperty("line.separator");
      StringBuilder var4 = var2.append(var3).append("Permission is hereby granted, free of charge, to any person obtaining a copy of this software ");
      String var5 = System.getProperty("line.separator");
      StringBuilder var6 = var4.append(var5).append("and associated documentation files (the \"Software\"), to deal in the Software without restriction, ");
      String var7 = System.getProperty("line.separator");
      StringBuilder var8 = var6.append(var7).append("including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, ");
      String var9 = System.getProperty("line.separator");
      StringBuilder var10 = var8.append(var9).append("and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,");
      String var11 = System.getProperty("line.separator");
      StringBuilder var12 = var10.append(var11).append("subject to the following conditions:");
      String var13 = System.getProperty("line.separator");
      StringBuilder var14 = var12.append(var13);
      String var15 = System.getProperty("line.separator");
      StringBuilder var16 = var14.append(var15).append("The above copyright notice and this permission notice shall be included in all copies or substantial");
      String var17 = System.getProperty("line.separator");
      StringBuilder var18 = var16.append(var17).append("portions of the Software.");
      String var19 = System.getProperty("line.separator");
      StringBuilder var20 = var18.append(var19);
      String var21 = System.getProperty("line.separator");
      StringBuilder var22 = var20.append(var21).append("THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,");
      String var23 = System.getProperty("line.separator");
      StringBuilder var24 = var22.append(var23).append("INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR");
      String var25 = System.getProperty("line.separator");
      StringBuilder var26 = var24.append(var25).append("PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE");
      String var27 = System.getProperty("line.separator");
      StringBuilder var28 = var26.append(var27).append("LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR");
      String var29 = System.getProperty("line.separator");
      StringBuilder var30 = var28.append(var29).append("OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER");
      String var31 = System.getProperty("line.separator");
      licenseText = var30.append(var31).append("DEALINGS IN THE SOFTWARE.").toString();
   }

   public LICENSE() {}

   public static void main(String[] var0) {
      PrintStream var1 = System.out;
      String var2 = licenseText;
      var1.println(var2);
   }
}
