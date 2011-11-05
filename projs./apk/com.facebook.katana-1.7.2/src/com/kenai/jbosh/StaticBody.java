package com.kenai.jbosh;

import com.kenai.jbosh.AbstractBody;
import com.kenai.jbosh.BOSHException;
import com.kenai.jbosh.BodyParser;
import com.kenai.jbosh.BodyParserXmlPull;
import com.kenai.jbosh.BodyQName;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;

final class StaticBody extends AbstractBody {

   private static final int BUFFER_SIZE = 1024;
   private static final BodyParser PARSER = new BodyParserXmlPull();
   private final Map<BodyQName, String> attrs;
   private final String raw;


   private StaticBody(Map<BodyQName, String> var1, String var2) {
      this.attrs = var1;
      this.raw = var2;
   }

   public static StaticBody fromStream(InputStream param0) throws BOSHException {
      // $FF: Couldn't be decompiled
   }

   public static StaticBody fromString(String var0) throws BOSHException {
      Map var1 = PARSER.parse(var0).getAttributes();
      return new StaticBody(var1, var0);
   }

   public Map<BodyQName, String> getAttributes() {
      return Collections.unmodifiableMap(this.attrs);
   }

   public String toXML() {
      return this.raw;
   }
}
