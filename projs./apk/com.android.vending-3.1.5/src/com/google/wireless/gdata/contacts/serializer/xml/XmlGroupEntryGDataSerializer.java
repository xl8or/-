package com.google.wireless.gdata.contacts.serializer.xml;

import com.google.wireless.gdata.contacts.data.GroupEntry;
import com.google.wireless.gdata.data.StringUtils;
import com.google.wireless.gdata.parser.ParseException;
import com.google.wireless.gdata.parser.xml.XmlParserFactory;
import com.google.wireless.gdata.serializer.xml.XmlEntryGDataSerializer;
import java.io.IOException;
import org.xmlpull.v1.XmlSerializer;

public class XmlGroupEntryGDataSerializer extends XmlEntryGDataSerializer {

   public XmlGroupEntryGDataSerializer(XmlParserFactory var1, GroupEntry var2) {
      super(var1, var2);
   }

   private void serializeSystemGroup(GroupEntry var1, XmlSerializer var2) throws IOException {
      String var3 = var1.getSystemGroup();
      if(!StringUtils.isEmpty(var3)) {
         XmlSerializer var4 = var2.startTag((String)null, "systemGroup");
         var2.attribute((String)null, "id", var3);
         XmlSerializer var6 = var2.endTag((String)null, "systemGroup");
      }
   }

   protected void declareExtraEntryNamespaces(XmlSerializer var1) throws IOException {
      super.declareExtraEntryNamespaces(var1);
      var1.setPrefix("gContact", "http://schemas.google.com/contact/2008");
   }

   protected GroupEntry getGroupEntry() {
      return (GroupEntry)this.getEntry();
   }

   protected void serializeExtraEntryContents(XmlSerializer var1, int var2) throws ParseException, IOException {
      GroupEntry var3 = this.getGroupEntry();
      var3.validate();
      this.serializeSystemGroup(var3, var1);
   }
}
