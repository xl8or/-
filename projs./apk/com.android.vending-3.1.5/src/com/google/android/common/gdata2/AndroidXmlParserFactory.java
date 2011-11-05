package com.google.android.common.gdata2;

import android.util.Xml;
import com.google.android.common.SafeXmlSerializer;
import com.google.wireless.gdata2.parser.xml.XmlParserFactory;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

public class AndroidXmlParserFactory implements XmlParserFactory {

   public AndroidXmlParserFactory() {}

   public XmlPullParser createParser() throws XmlPullParserException {
      return Xml.newPullParser();
   }

   public XmlSerializer createSerializer() throws XmlPullParserException {
      XmlSerializer var1 = Xml.newSerializer();
      return new SafeXmlSerializer(var1);
   }
}
