package org.apache.james.mime4j.field;

import org.apache.james.mime4j.field.AddressListField;
import org.apache.james.mime4j.field.ContentTransferEncodingField;
import org.apache.james.mime4j.field.ContentTypeField;
import org.apache.james.mime4j.field.DateTimeField;
import org.apache.james.mime4j.field.DelegatingFieldParser;
import org.apache.james.mime4j.field.MailboxField;
import org.apache.james.mime4j.field.MailboxListField;

public class DefaultFieldParser extends DelegatingFieldParser {

   public DefaultFieldParser() {
      ContentTransferEncodingField.Parser var1 = new ContentTransferEncodingField.Parser();
      this.setFieldParser("Content-Transfer-Encoding", var1);
      ContentTypeField.Parser var2 = new ContentTypeField.Parser();
      this.setFieldParser("Content-Type", var2);
      DateTimeField.Parser var3 = new DateTimeField.Parser();
      this.setFieldParser("Date", var3);
      this.setFieldParser("Resent-Date", var3);
      MailboxListField.Parser var4 = new MailboxListField.Parser();
      this.setFieldParser("From", var4);
      this.setFieldParser("Resent-From", var4);
      MailboxField.Parser var5 = new MailboxField.Parser();
      this.setFieldParser("Sender", var5);
      this.setFieldParser("Resent-Sender", var5);
      AddressListField.Parser var6 = new AddressListField.Parser();
      this.setFieldParser("To", var6);
      this.setFieldParser("Resent-To", var6);
      this.setFieldParser("Cc", var6);
      this.setFieldParser("Resent-Cc", var6);
      this.setFieldParser("Bcc", var6);
      this.setFieldParser("Resent-Bcc", var6);
      this.setFieldParser("Reply-To", var6);
   }
}
