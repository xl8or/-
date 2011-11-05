package org.kxml2.wap.wml;

import org.kxml2.wap.WbxmlParser;
import org.kxml2.wap.WbxmlSerializer;

public abstract class Wml {

   public static final String[] ATTR_START_TABLE;
   public static final String[] ATTR_VALUE_TABLE;
   public static final String[] TAG_TABLE;


   static {
      String[] var0 = new String[]{false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, "a", "td", "tr", "table", "p", "postfield", "anchor", "access", "b", "big", "br", "card", "do", "em", "fieldset", "go", "head", "i", "img", "input", "meta", "noop", "prev", "onevent", "optgroup", "option", "refresh", "select", "small", "strong", false, "template", "timer", "u", "setvar", "wml"};
      TAG_TABLE = var0;
      String[] var1 = new String[]{"accept-charset", "align=bottom", "align=center", "align=left", "align=middle", "align=right", "align=top", "alt", "content", false, "domain", "emptyok=false", "emptyok=true", "format", "height", "hspace", "ivalue", "iname", false, "label", "localsrc", "maxlength", "method=get", "method=post", "mode=nowrap", "mode=wrap", "multiple=false", "multiple=true", "name", "newcontext=false", "newcontext=true", "onpick", "onenterbackward", "onenterforward", "ontimer", "optimal=false", "optimal=true", "path", false, false, false, "scheme", "sendreferer=false", "sendreferer=true", "size", "src", "ordered=true", "ordered=false", "tabindex", "title", "type", "type=accept", "type=delete", "type=help", "type=password", "type=onpick", "type=onenterbackward", "type=onenterforward", "type=ontimer", false, false, false, false, false, "type=options", "type=prev", "type=reset", "type=text", "type=vnd.", "href", "href=http://", "href=https://", "value", "vspace", "width", "xml:lang", false, "align", "columns", "class", "id", "forua=false", "forua=true", "src=http://", "src=https://", "http-equiv", "http-equiv=Content-Type", "content=application/vnd.wap.wmlc;charset=", "http-equiv=Expires", false, false};
      ATTR_START_TABLE = var1;
      String[] var2 = new String[]{".com/", ".edu/", ".net/", ".org/", "accept", "bottom", "clear", "delete", "help", "http://", "http://www.", "https://", "https://www.", false, "middle", "nowrap", "onpick", "onenterbackward", "onenterforward", "ontimer", "options", "password", "reset", false, "text", "top", "unknown", "wrap", "www."};
      ATTR_VALUE_TABLE = var2;
   }

   public Wml() {}

   public static WbxmlParser createParser() {
      WbxmlParser var0 = new WbxmlParser();
      String[] var1 = TAG_TABLE;
      var0.setTagTable(0, var1);
      String[] var2 = ATTR_START_TABLE;
      var0.setAttrStartTable(0, var2);
      String[] var3 = ATTR_VALUE_TABLE;
      var0.setAttrValueTable(0, var3);
      return var0;
   }

   public static WbxmlSerializer createSerializer() {
      WbxmlSerializer var0 = new WbxmlSerializer();
      String[] var1 = TAG_TABLE;
      var0.setTagTable(0, var1);
      String[] var2 = ATTR_START_TABLE;
      var0.setAttrStartTable(0, var2);
      String[] var3 = ATTR_VALUE_TABLE;
      var0.setAttrValueTable(0, var3);
      return var0;
   }
}
