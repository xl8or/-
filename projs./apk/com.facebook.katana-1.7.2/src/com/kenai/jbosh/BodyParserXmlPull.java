package com.kenai.jbosh;

import com.kenai.jbosh.BOSHException;
import com.kenai.jbosh.BodyParser;
import com.kenai.jbosh.BodyParserResults;
import java.lang.ref.SoftReference;
import java.util.logging.Logger;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

final class BodyParserXmlPull implements BodyParser {

   private static final Logger LOG = Logger.getLogger(BodyParserXmlPull.class.getName());
   private static final ThreadLocal<SoftReference<XmlPullParser>> XPP_PARSER = new BodyParserXmlPull.1();


   BodyParserXmlPull() {}

   private static XmlPullParser getXmlPullParser() {
      XmlPullParser var0 = (XmlPullParser)((SoftReference)XPP_PARSER.get()).get();
      if(var0 == null) {
         try {
            XmlPullParserFactory var1 = XmlPullParserFactory.newInstance();
            var1.setNamespaceAware((boolean)1);
            var1.setValidating((boolean)0);
            var0 = var1.newPullParser();
            SoftReference var2 = new SoftReference(var0);
            XPP_PARSER.set(var2);
         } catch (Exception var4) {
            throw new IllegalStateException("Could not create XmlPull parser", var4);
         }
      }

      return var0;
   }

   public BodyParserResults parse(String param1) throws BOSHException {
      // $FF: Couldn't be decompiled
   }

   static class 1 extends ThreadLocal<SoftReference<XmlPullParser>> {

      1() {}

      protected SoftReference<XmlPullParser> initialValue() {
         return new SoftReference((Object)null);
      }
   }
}
