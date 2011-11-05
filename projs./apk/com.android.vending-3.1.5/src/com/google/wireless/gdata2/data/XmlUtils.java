package com.google.wireless.gdata2.data;

import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public final class XmlUtils {

   private XmlUtils() {}

   public static String extractChildText(XmlPullParser var0) throws XmlPullParserException, IOException {
      String var1;
      if(var0.next() != 4) {
         var1 = null;
      } else {
         var1 = var0.getText();
      }

      return var1;
   }

   public static String extractFirstChildTextIgnoreRest(XmlPullParser var0) throws XmlPullParserException, IOException {
      int var1 = var0.getDepth();

      for(int var2 = var0.next(); var2 != 1; var2 = var0.next()) {
         int var3 = var0.getDepth();
         if(var2 == 4) {
            if(true) {
               String var4 = var0.getText();
            }
         } else if(var2 == 3 && var3 == var1) {
            return null;
         }
      }

      String var5 = "End of document reached; never saw expected end tag at depth " + var1;
      throw new XmlPullParserException(var5);
   }

   public static boolean matchNameSpaceUri(XmlPullParser var0, String var1) {
      String var2 = var0.getNamespace();
      byte var3;
      if(var2 == null) {
         if(var1 == null) {
            var3 = 1;
         } else {
            var3 = 0;
         }
      } else {
         var3 = var2.equals(var1);
      }

      return (boolean)var3;
   }

   public static String nextDirectChildTag(XmlPullParser var0, int var1) throws XmlPullParserException, IOException {
      int var2 = var1 + 1;
      int var3 = var0.next();

      String var5;
      while(true) {
         if(var3 == 1) {
            String var6 = "End of document reached; never saw expected end tag at depth " + var1;
            throw new XmlPullParserException(var6);
         }

         int var4 = var0.getDepth();
         if(var3 == 2 && var4 == var2) {
            var5 = var0.getName();
            break;
         }

         if(var3 == 3 && var4 == var1) {
            var5 = null;
            break;
         }

         var3 = var0.next();
      }

      return var5;
   }
}
