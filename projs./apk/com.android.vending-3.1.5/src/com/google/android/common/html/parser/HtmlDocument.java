package com.google.android.common.html.parser;

import com.google.android.common.base.CharEscaper;
import com.google.android.common.base.CharEscapers;
import com.google.android.common.base.CharMatcher;
import com.google.android.common.base.StringUtil;
import com.google.android.common.base.X;
import com.google.android.common.html.parser.HTML;
import com.google.common.collect.Lists;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class HtmlDocument {

   private final List<HtmlDocument.Node> nodes;


   public HtmlDocument(List<HtmlDocument.Node> var1) {
      this.nodes = var1;
   }

   public static HtmlDocument.CDATA createCDATA(String var0) {
      return new HtmlDocument.CDATA(var0, (HtmlDocument.1)null);
   }

   public static HtmlDocument.EndTag createEndTag(HTML.Element var0) {
      return createEndTag(var0, (String)null);
   }

   public static HtmlDocument.EndTag createEndTag(HTML.Element var0, String var1) {
      return new HtmlDocument.EndTag(var0, var1, (HtmlDocument.1)null);
   }

   public static HtmlDocument.Text createEscapedText(String var0, String var1) {
      return new HtmlDocument.EscapedText(var0, var1, (HtmlDocument.1)null);
   }

   public static HtmlDocument.Comment createHtmlComment(String var0) {
      return new HtmlDocument.Comment(var0);
   }

   public static HtmlDocument.Tag createSelfTerminatingTag(HTML.Element var0, List<HtmlDocument.TagAttribute> var1) {
      return createSelfTerminatingTag(var0, var1, (String)null, (String)null);
   }

   public static HtmlDocument.Tag createSelfTerminatingTag(HTML.Element var0, List<HtmlDocument.TagAttribute> var1, String var2, String var3) {
      return new HtmlDocument.Tag(var0, var1, (boolean)1, var2, var3, (HtmlDocument.1)null);
   }

   public static HtmlDocument.Tag createTag(HTML.Element var0, List<HtmlDocument.TagAttribute> var1) {
      return createTag(var0, var1, (String)null, (String)null);
   }

   public static HtmlDocument.Tag createTag(HTML.Element var0, List<HtmlDocument.TagAttribute> var1, String var2, String var3) {
      return new HtmlDocument.Tag(var0, var1, (boolean)0, var2, var3, (HtmlDocument.1)null);
   }

   public static HtmlDocument.TagAttribute createTagAttribute(HTML.Attribute var0, String var1) {
      return createTagAttribute(var0, var1, (String)null);
   }

   public static HtmlDocument.TagAttribute createTagAttribute(HTML.Attribute var0, String var1, String var2) {
      byte var3;
      if(var0 != null) {
         var3 = 1;
      } else {
         var3 = 0;
      }

      X.assertTrue((boolean)var3);
      return new HtmlDocument.TagAttribute(var0, var1, var2, (HtmlDocument.1)null);
   }

   public static HtmlDocument.Text createText(String var0) {
      return createText(var0, (String)null);
   }

   public static HtmlDocument.Text createText(String var0, String var1) {
      return new HtmlDocument.UnescapedText(var0, var1, (HtmlDocument.1)null);
   }

   public void accept(HtmlDocument.Visitor var1) {
      var1.start();
      Iterator var2 = this.nodes.iterator();

      while(var2.hasNext()) {
         ((HtmlDocument.Node)var2.next()).accept(var1);
      }

      var1.finish();
   }

   public HtmlDocument filter(HtmlDocument.MultiplexFilter var1) {
      var1.start();
      ArrayList var2 = new ArrayList();
      Iterator var3 = this.nodes.iterator();

      while(var3.hasNext()) {
         HtmlDocument.Node var4 = (HtmlDocument.Node)var3.next();
         var1.filter(var4, var2);
      }

      var1.finish(var2);
      return new HtmlDocument(var2);
   }

   public List<HtmlDocument.Node> getNodes() {
      return this.nodes;
   }

   public String toHTML() {
      int var1 = this.nodes.size() * 10;
      StringBuilder var2 = new StringBuilder(var1);
      Iterator var3 = this.nodes.iterator();

      while(var3.hasNext()) {
         ((HtmlDocument.Node)var3.next()).toHTML(var2);
      }

      return var2.toString();
   }

   public String toOriginalHTML() {
      int var1 = this.nodes.size() * 10;
      StringBuilder var2 = new StringBuilder(var1);
      Iterator var3 = this.nodes.iterator();

      while(var3.hasNext()) {
         ((HtmlDocument.Node)var3.next()).toOriginalHTML(var2);
      }

      return var2.toString();
   }

   public String toString() {
      StringWriter var1 = new StringWriter();
      PrintWriter var2 = new PrintWriter(var1);
      HtmlDocument.DebugPrinter var3 = new HtmlDocument.DebugPrinter(var2);
      this.accept(var3);
      return var1.toString();
   }

   public String toXHTML() {
      int var1 = this.nodes.size() * 10;
      StringBuilder var2 = new StringBuilder(var1);
      Iterator var3 = this.nodes.iterator();

      while(var3.hasNext()) {
         ((HtmlDocument.Node)var3.next()).toXHTML(var2);
      }

      return var2.toString();
   }

   public static class CDATA extends HtmlDocument.UnescapedText {

      private CDATA(String var1) {
         super(var1, var1, (HtmlDocument.1)null);
      }

      // $FF: synthetic method
      CDATA(String var1, HtmlDocument.1 var2) {
         this(var1);
      }

      public void toHTML(StringBuilder var1) {
         String var2 = this.text;
         var1.append(var2);
      }

      public void toXHTML(StringBuilder var1) {
         StringBuilder var2 = var1.append("<![CDATA[");
         String var3 = this.text;
         StringBuilder var4 = var2.append(var3).append("]]>");
      }
   }

   public abstract static class Node {

      public Node() {}

      public abstract void accept(HtmlDocument.Visitor var1);

      public String toHTML() {
         StringBuilder var1 = new StringBuilder();
         this.toHTML(var1);
         return var1.toString();
      }

      public abstract void toHTML(StringBuilder var1);

      public String toOriginalHTML() {
         StringBuilder var1 = new StringBuilder();
         this.toOriginalHTML(var1);
         return var1.toString();
      }

      public abstract void toOriginalHTML(StringBuilder var1);

      public String toXHTML() {
         StringBuilder var1 = new StringBuilder();
         this.toXHTML(var1);
         return var1.toString();
      }

      public abstract void toXHTML(StringBuilder var1);
   }

   public interface Visitor {

      void finish();

      void start();

      void visitComment(HtmlDocument.Comment var1);

      void visitEndTag(HtmlDocument.EndTag var1);

      void visitTag(HtmlDocument.Tag var1);

      void visitText(HtmlDocument.Text var1);
   }

   // $FF: synthetic class
   static class 1 {
   }

   public interface Filter {

      void finish();

      void start();

      HtmlDocument.Comment visitComment(HtmlDocument.Comment var1);

      HtmlDocument.EndTag visitEndTag(HtmlDocument.EndTag var1);

      HtmlDocument.Tag visitTag(HtmlDocument.Tag var1);

      HtmlDocument.Text visitText(HtmlDocument.Text var1);
   }

   public static class Tag extends HtmlDocument.Node {

      private List<HtmlDocument.TagAttribute> attributes;
      private final HTML.Element element;
      private final boolean isSelfTerminating;
      private final String originalHtmlAfterAttributes;
      private final String originalHtmlBeforeAttributes;


      private Tag(HTML.Element var1, List<HtmlDocument.TagAttribute> var2, boolean var3, String var4, String var5) {
         byte var6;
         if(var1 != null) {
            var6 = 1;
         } else {
            var6 = 0;
         }

         X.assertTrue((boolean)var6);
         this.element = var1;
         this.attributes = var2;
         this.isSelfTerminating = var3;
         this.originalHtmlBeforeAttributes = var4;
         this.originalHtmlAfterAttributes = var5;
      }

      // $FF: synthetic method
      Tag(HTML.Element var1, List var2, boolean var3, String var4, String var5, HtmlDocument.1 var6) {
         this(var1, var2, var3, var4, var5);
      }

      private void serialize(StringBuilder var1, HtmlDocument.Tag.SerializeType var2) {
         HtmlDocument.Tag.SerializeType var3 = HtmlDocument.Tag.SerializeType.ORIGINAL_HTML;
         if(var2 == var3 && this.originalHtmlBeforeAttributes != null) {
            String var4 = this.originalHtmlBeforeAttributes;
            var1.append(var4);
         } else {
            StringBuilder var9 = var1.append('<');
            String var10 = this.element.getName();
            var1.append(var10);
         }

         if(this.attributes != null) {
            Iterator var6 = this.attributes.iterator();

            while(var6.hasNext()) {
               HtmlDocument.TagAttribute var7 = (HtmlDocument.TagAttribute)var6.next();
               HtmlDocument.Tag.SerializeType var8 = HtmlDocument.Tag.SerializeType.ORIGINAL_HTML;
               if(var2 == var8) {
                  var7.toOriginalHTML(var1);
               } else {
                  HtmlDocument.Tag.SerializeType var12 = HtmlDocument.Tag.SerializeType.HTML;
                  if(var2 == var12) {
                     var7.toHTML(var1);
                  } else {
                     var7.toXHTML(var1);
                  }
               }
            }
         }

         HtmlDocument.Tag.SerializeType var13 = HtmlDocument.Tag.SerializeType.ORIGINAL_HTML;
         if(var2 == var13 && this.originalHtmlAfterAttributes != null) {
            String var14 = this.originalHtmlAfterAttributes;
            var1.append(var14);
         } else {
            HtmlDocument.Tag.SerializeType var16 = HtmlDocument.Tag.SerializeType.XHTML;
            if(var2 == var16 && (this.isSelfTerminating || this.getElement().isEmpty())) {
               StringBuilder var17 = var1.append(" />");
            } else {
               StringBuilder var18 = var1.append('>');
            }
         }
      }

      public void accept(HtmlDocument.Visitor var1) {
         var1.visitTag(this);
      }

      public void addAttribute(HTML.Attribute var1, String var2) {
         byte var3;
         if(var1 != null) {
            var3 = 1;
         } else {
            var3 = 0;
         }

         X.assertTrue((boolean)var3);
         HtmlDocument.TagAttribute var4 = new HtmlDocument.TagAttribute(var1, var2, (String)null, (HtmlDocument.1)null);
         this.addAttribute(var4);
      }

      public void addAttribute(HtmlDocument.TagAttribute var1) {
         byte var2;
         if(var1 != null) {
            var2 = 1;
         } else {
            var2 = 0;
         }

         X.assertTrue((boolean)var2);
         if(this.attributes == null) {
            ArrayList var3 = new ArrayList();
            this.attributes = var3;
         }

         this.attributes.add(var1);
      }

      public HtmlDocument.TagAttribute getAttribute(HTML.Attribute var1) {
         HtmlDocument.TagAttribute var3;
         if(this.attributes != null) {
            Iterator var2 = this.attributes.iterator();

            while(var2.hasNext()) {
               var3 = (HtmlDocument.TagAttribute)var2.next();
               if(var3.getAttribute().equals(var1)) {
                  return var3;
               }
            }
         }

         var3 = null;
         return var3;
      }

      public List<HtmlDocument.TagAttribute> getAttributes() {
         return this.attributes;
      }

      public List<HtmlDocument.TagAttribute> getAttributes(HTML.Attribute var1) {
         ArrayList var2 = Lists.newArrayList();
         if(this.attributes != null) {
            Iterator var3 = this.attributes.iterator();

            while(var3.hasNext()) {
               HtmlDocument.TagAttribute var4 = (HtmlDocument.TagAttribute)var3.next();
               if(var4.getAttribute().equals(var1)) {
                  var2.add(var4);
               }
            }
         }

         return var2;
      }

      public HTML.Element getElement() {
         return this.element;
      }

      public String getName() {
         return this.element.getName();
      }

      public String getOriginalHtmlAfterAttributes() {
         return this.originalHtmlAfterAttributes;
      }

      public String getOriginalHtmlBeforeAttributes() {
         return this.originalHtmlBeforeAttributes;
      }

      public boolean isSelfTerminating() {
         return this.isSelfTerminating;
      }

      public void toHTML(StringBuilder var1) {
         HtmlDocument.Tag.SerializeType var2 = HtmlDocument.Tag.SerializeType.HTML;
         this.serialize(var1, var2);
      }

      public void toOriginalHTML(StringBuilder var1) {
         HtmlDocument.Tag.SerializeType var2 = HtmlDocument.Tag.SerializeType.ORIGINAL_HTML;
         this.serialize(var1, var2);
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         StringBuilder var2 = var1.append("Start Tag: ");
         String var3 = this.element.getName();
         var1.append(var3);
         if(this.attributes != null) {
            Iterator var5 = this.attributes.iterator();

            while(var5.hasNext()) {
               HtmlDocument.TagAttribute var6 = (HtmlDocument.TagAttribute)var5.next();
               StringBuilder var7 = var1.append(' ');
               String var8 = var6.toString();
               var1.append(var8);
            }
         }

         return var1.toString();
      }

      public void toXHTML(StringBuilder var1) {
         HtmlDocument.Tag.SerializeType var2 = HtmlDocument.Tag.SerializeType.XHTML;
         this.serialize(var1, var2);
      }

      private static enum SerializeType {

         // $FF: synthetic field
         private static final HtmlDocument.Tag.SerializeType[] $VALUES;
         HTML("HTML", 1),
         ORIGINAL_HTML("ORIGINAL_HTML", 0),
         XHTML("XHTML", 2);


         static {
            HtmlDocument.Tag.SerializeType[] var0 = new HtmlDocument.Tag.SerializeType[3];
            HtmlDocument.Tag.SerializeType var1 = ORIGINAL_HTML;
            var0[0] = var1;
            HtmlDocument.Tag.SerializeType var2 = HTML;
            var0[1] = var2;
            HtmlDocument.Tag.SerializeType var3 = XHTML;
            var0[2] = var3;
            $VALUES = var0;
         }

         private SerializeType(String var1, int var2) {}
      }
   }

   public interface MultiplexFilter {

      void filter(HtmlDocument.Node var1, List<HtmlDocument.Node> var2);

      void finish(List<HtmlDocument.Node> var1);

      void start();
   }

   public static class EndTag extends HtmlDocument.Node {

      private final HTML.Element element;
      private final String originalHtml;


      private EndTag(HTML.Element var1, String var2) {
         byte var3;
         if(var1 != null) {
            var3 = 1;
         } else {
            var3 = 0;
         }

         X.assertTrue((boolean)var3);
         this.element = var1;
         this.originalHtml = var2;
      }

      // $FF: synthetic method
      EndTag(HTML.Element var1, String var2, HtmlDocument.1 var3) {
         this(var1, var2);
      }

      public void accept(HtmlDocument.Visitor var1) {
         var1.visitEndTag(this);
      }

      public HTML.Element getElement() {
         return this.element;
      }

      public String getName() {
         return this.element.getName();
      }

      public void toHTML(StringBuilder var1) {
         StringBuilder var2 = var1.append("</");
         String var3 = this.element.getName();
         var1.append(var3);
         StringBuilder var5 = var1.append('>');
      }

      public void toOriginalHTML(StringBuilder var1) {
         if(this.originalHtml != null) {
            String var2 = this.originalHtml;
            var1.append(var2);
         } else {
            this.toHTML(var1);
         }
      }

      public String toString() {
         StringBuilder var1 = (new StringBuilder()).append("End Tag: ");
         String var2 = this.element.getName();
         return var1.append(var2).toString();
      }

      public void toXHTML(StringBuilder var1) {
         this.toHTML(var1);
      }
   }

   public static class Comment extends HtmlDocument.Node {

      private final String content;


      public Comment(String var1) {
         this.content = var1;
      }

      public void accept(HtmlDocument.Visitor var1) {
         var1.visitComment(this);
      }

      public String getContent() {
         return this.content;
      }

      public void toHTML(StringBuilder var1) {
         String var2 = this.content;
         var1.append(var2);
      }

      public void toOriginalHTML(StringBuilder var1) {
         String var2 = this.content;
         var1.append(var2);
      }

      public void toXHTML(StringBuilder var1) {
         String var2 = this.content;
         var1.append(var2);
      }
   }

   public abstract static class Text extends HtmlDocument.Node {

      private String html;
      private final String originalHtml;


      protected Text(String var1) {
         this.originalHtml = var1;
      }

      public void accept(HtmlDocument.Visitor var1) {
         var1.visitText(this);
      }

      public boolean equals(Object var1) {
         byte var2 = 1;
         if(var1 != this) {
            if(var1 instanceof HtmlDocument.Text) {
               HtmlDocument.Text var3 = (HtmlDocument.Text)var1;
               if(this.originalHtml == null) {
                  if(var3.originalHtml != null) {
                     var2 = 0;
                  }
               } else {
                  String var4 = this.originalHtml;
                  String var5 = var3.originalHtml;
                  var2 = var4.equals(var5);
               }
            } else {
               var2 = 0;
            }
         }

         return (boolean)var2;
      }

      public String getOriginalHTML() {
         return this.originalHtml;
      }

      public abstract String getText();

      public int hashCode() {
         int var1;
         if(this.originalHtml == null) {
            var1 = 0;
         } else {
            var1 = this.originalHtml.hashCode();
         }

         return var1;
      }

      public boolean isWhitespace() {
         String var1 = this.getText();
         int var2 = var1.length();
         int var3 = 0;

         boolean var4;
         while(true) {
            if(var3 >= var2) {
               var4 = true;
               break;
            }

            if(!Character.isWhitespace(var1.charAt(var3))) {
               var4 = false;
               break;
            }

            ++var3;
         }

         return var4;
      }

      public void toHTML(StringBuilder var1) {
         if(this.html == null) {
            CharEscaper var2 = CharEscapers.asciiHtmlEscaper();
            String var3 = this.getText();
            String var4 = var2.escape(var3);
            this.html = var4;
         }

         String var5 = this.html;
         var1.append(var5);
      }

      public void toOriginalHTML(StringBuilder var1) {
         if(this.originalHtml != null) {
            String var2 = this.originalHtml;
            var1.append(var2);
         } else {
            this.toHTML(var1);
         }
      }

      public String toString() {
         return this.getText();
      }

      public void toXHTML(StringBuilder var1) {
         this.toHTML(var1);
      }
   }

   public static class TagAttribute {

      private final HTML.Attribute attribute;
      private String originalHtml;
      private String value;


      private TagAttribute(HTML.Attribute var1, String var2, String var3) {
         byte var4;
         if(var1 != null) {
            var4 = 1;
         } else {
            var4 = 0;
         }

         X.assertTrue((boolean)var4);
         this.attribute = var1;
         this.value = var2;
         this.originalHtml = var3;
      }

      // $FF: synthetic method
      TagAttribute(HTML.Attribute var1, String var2, String var3, HtmlDocument.1 var4) {
         this(var1, var2, var3);
      }

      public HTML.Attribute getAttribute() {
         return this.attribute;
      }

      public String getName() {
         return this.attribute.getName();
      }

      public String getValue() {
         String var1;
         if(this.value != null) {
            var1 = this.value;
         } else {
            var1 = "";
         }

         return var1;
      }

      public boolean hasValue() {
         boolean var1;
         if(this.value != null) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public void setValue(String var1) {
         this.value = var1;
         this.originalHtml = null;
      }

      public String toHTML() {
         StringBuilder var1 = new StringBuilder();
         this.toHTML(var1);
         return var1.toString();
      }

      public void toHTML(StringBuilder var1) {
         StringBuilder var2 = var1.append(' ');
         String var3 = this.attribute.getName();
         var1.append(var3);
         if(this.value != null) {
            if(this.attribute.getType() != 4) {
               StringBuilder var5 = var1.append("=\"");
               CharEscaper var6 = CharEscapers.asciiHtmlEscaper();
               String var7 = this.value;
               String var8 = var6.escape(var7);
               var1.append(var8);
               StringBuilder var10 = var1.append("\"");
            }
         }
      }

      public String toOriginalHTML() {
         StringBuilder var1 = new StringBuilder();
         this.toOriginalHTML(var1);
         return var1.toString();
      }

      public void toOriginalHTML(StringBuilder var1) {
         if(this.originalHtml != null) {
            String var2 = this.originalHtml;
            var1.append(var2);
         } else {
            this.toHTML(var1);
         }
      }

      public String toString() {
         StringBuilder var1 = (new StringBuilder()).append("{");
         String var2 = this.attribute.getName();
         StringBuilder var3 = var1.append(var2).append("=");
         String var4 = this.value;
         return var3.append(var4).append("}").toString();
      }

      public String toXHTML() {
         StringBuilder var1 = new StringBuilder();
         this.toXHTML(var1);
         return var1.toString();
      }

      public void toXHTML(StringBuilder var1) {
         StringBuilder var2 = var1.append(' ');
         String var3 = this.attribute.getName();
         StringBuilder var4 = var1.append(var3).append("=\"");
         if(this.hasValue()) {
            CharEscaper var5 = CharEscapers.asciiHtmlEscaper();
            String var6 = this.value;
            String var7 = var5.escape(var6);
            var1.append(var7);
         } else {
            String var10 = this.attribute.getName();
            var1.append(var10);
         }

         StringBuilder var9 = var1.append("\"");
      }
   }

   public static class DebugPrinter implements HtmlDocument.Visitor {

      private final PrintWriter writer;


      public DebugPrinter(PrintWriter var1) {
         this.writer = var1;
      }

      private void writeCollapsed(String var1, String var2) {
         this.writer.print(var1);
         this.writer.print(": ");
         String var3 = var2.replace("\n", " ");
         String var4 = CharMatcher.LEGACY_WHITESPACE.trimAndCollapseFrom(var3, ' ');
         this.writer.print(var4);
      }

      public void finish() {}

      public void start() {}

      public void visitComment(HtmlDocument.Comment var1) {
         String var2 = var1.getContent();
         this.writeCollapsed("COMMENT", var2);
      }

      public void visitEndTag(HtmlDocument.EndTag var1) {
         PrintWriter var2 = this.writer;
         StringBuilder var3 = (new StringBuilder()).append("==</");
         String var4 = var1.getName();
         String var5 = var3.append(var4).append(">").toString();
         var2.println(var5);
      }

      public void visitTag(HtmlDocument.Tag var1) {
         PrintWriter var2 = this.writer;
         StringBuilder var3 = (new StringBuilder()).append("==<");
         String var4 = var1.getName();
         String var5 = var3.append(var4).append(">").toString();
         var2.print(var5);
         List var6 = var1.getAttributes();
         if(var6 != null) {
            ArrayList var7 = new ArrayList();
            Iterator var8 = var6.iterator();

            while(var8.hasNext()) {
               HtmlDocument.TagAttribute var9 = (HtmlDocument.TagAttribute)var8.next();
               StringBuilder var10 = (new StringBuilder()).append("[");
               String var11 = var9.getName();
               StringBuilder var12 = var10.append(var11).append(" : ");
               String var13 = var9.getValue();
               String var14 = var12.append(var13).append("]").toString();
               var7.add(var14);
            }

            String[] var16 = new String[var7.size()];
            String[] var17 = (String[])var7.toArray(var16);
            Arrays.sort(var17);
            int var18 = 0;

            while(true) {
               int var19 = var17.length;
               if(var18 >= var19) {
                  break;
               }

               PrintWriter var20 = this.writer;
               StringBuilder var21 = (new StringBuilder()).append(" ");
               String var22 = var17[var18];
               String var23 = var21.append(var22).toString();
               var20.print(var23);
               ++var18;
            }
         }

         this.writer.println();
      }

      public void visitText(HtmlDocument.Text var1) {
         String var2 = var1.getText();
         this.writeCollapsed("TEXT", var2);
      }
   }

   private static class UnescapedText extends HtmlDocument.Text {

      protected final String text;


      private UnescapedText(String var1, String var2) {
         super(var2);
         byte var3;
         if(var1 != null) {
            var3 = 1;
         } else {
            var3 = 0;
         }

         X.assertTrue((boolean)var3);
         this.text = var1;
      }

      // $FF: synthetic method
      UnescapedText(String var1, String var2, HtmlDocument.1 var3) {
         this(var1, var2);
      }

      public String getText() {
         return this.text;
      }
   }

   private static class EscapedText extends HtmlDocument.Text {

      private final String htmlText;
      private String text;


      private EscapedText(String var1, String var2) {
         super(var2);
         this.htmlText = var1;
      }

      // $FF: synthetic method
      EscapedText(String var1, String var2, HtmlDocument.1 var3) {
         this(var1, var2);
      }

      public String getText() {
         if(this.text == null) {
            String var1 = StringUtil.unescapeHTML(this.htmlText);
            this.text = var1;
         }

         return this.text;
      }
   }

   public static class VisitorWrapper implements HtmlDocument.Visitor {

      private final HtmlDocument.Visitor wrapped;


      protected VisitorWrapper(HtmlDocument.Visitor var1) {
         this.wrapped = var1;
      }

      public void finish() {
         this.wrapped.finish();
      }

      public void start() {
         this.wrapped.start();
      }

      public void visitComment(HtmlDocument.Comment var1) {
         this.wrapped.visitComment(var1);
      }

      public void visitEndTag(HtmlDocument.EndTag var1) {
         this.wrapped.visitEndTag(var1);
      }

      public void visitTag(HtmlDocument.Tag var1) {
         this.wrapped.visitTag(var1);
      }

      public void visitText(HtmlDocument.Text var1) {
         this.wrapped.visitText(var1);
      }
   }

   public static class Builder implements HtmlDocument.Visitor {

      private HtmlDocument doc;
      private final List<HtmlDocument.Node> nodes;
      private final boolean preserveComments;


      public Builder() {
         this((boolean)0);
      }

      public Builder(boolean var1) {
         ArrayList var2 = new ArrayList();
         this.nodes = var2;
         this.preserveComments = var1;
      }

      public void addNode(HtmlDocument.Node var1) {
         this.nodes.add(var1);
      }

      public void finish() {
         List var1 = this.nodes;
         HtmlDocument var2 = new HtmlDocument(var1);
         this.doc = var2;
      }

      public HtmlDocument getDocument() {
         return this.doc;
      }

      public void start() {}

      public void visitComment(HtmlDocument.Comment var1) {
         if(this.preserveComments) {
            this.addNode(var1);
         }
      }

      public void visitEndTag(HtmlDocument.EndTag var1) {
         this.addNode(var1);
      }

      public void visitTag(HtmlDocument.Tag var1) {
         this.addNode(var1);
      }

      public void visitText(HtmlDocument.Text var1) {
         this.addNode(var1);
      }
   }

   public static class MultiplexFilterAdapter implements HtmlDocument.MultiplexFilter {

      private final HtmlDocument.Filter filter;


      public MultiplexFilterAdapter(HtmlDocument.Filter var1) {
         this.filter = var1;
      }

      public void filter(HtmlDocument.Node var1, List<HtmlDocument.Node> var2) {
         if(var1 != null) {
            Object var5;
            if(var1 instanceof HtmlDocument.Tag) {
               HtmlDocument.Filter var3 = this.filter;
               HtmlDocument.Tag var4 = (HtmlDocument.Tag)var1;
               var5 = var3.visitTag(var4);
            } else if(var1 instanceof HtmlDocument.Text) {
               HtmlDocument.Filter var7 = this.filter;
               HtmlDocument.Text var8 = (HtmlDocument.Text)var1;
               var5 = var7.visitText(var8);
            } else if(var1 instanceof HtmlDocument.EndTag) {
               HtmlDocument.Filter var9 = this.filter;
               HtmlDocument.EndTag var10 = (HtmlDocument.EndTag)var1;
               var5 = var9.visitEndTag(var10);
            } else {
               if(!(var1 instanceof HtmlDocument.Comment)) {
                  StringBuilder var13 = (new StringBuilder()).append("unknown node type: ");
                  Class var14 = var1.getClass();
                  String var15 = var13.append(var14).toString();
                  throw new IllegalArgumentException(var15);
               }

               HtmlDocument.Filter var11 = this.filter;
               HtmlDocument.Comment var12 = (HtmlDocument.Comment)var1;
               var5 = var11.visitComment(var12);
            }

            if(var5 != null) {
               var2.add(var5);
            }
         }
      }

      public void finish(List<HtmlDocument.Node> var1) {
         this.filter.finish();
      }

      public void start() {
         this.filter.start();
      }
   }

   public abstract static class SimpleMultiplexFilter implements HtmlDocument.MultiplexFilter {

      public SimpleMultiplexFilter() {}

      public void filter(HtmlDocument.Node var1, List<HtmlDocument.Node> var2) {
         if(var1 != null) {
            if(var1 instanceof HtmlDocument.Tag) {
               HtmlDocument.Tag var3 = (HtmlDocument.Tag)var1;
               this.filterTag(var3, var2);
            } else if(var1 instanceof HtmlDocument.Text) {
               HtmlDocument.Text var4 = (HtmlDocument.Text)var1;
               this.filterText(var4, var2);
            } else if(var1 instanceof HtmlDocument.EndTag) {
               HtmlDocument.EndTag var5 = (HtmlDocument.EndTag)var1;
               this.filterEndTag(var5, var2);
            } else if(var1 instanceof HtmlDocument.Comment) {
               HtmlDocument.Comment var6 = (HtmlDocument.Comment)var1;
               this.filterComment(var6, var2);
            } else {
               StringBuilder var7 = (new StringBuilder()).append("unknown node type: ");
               Class var8 = var1.getClass();
               String var9 = var7.append(var8).toString();
               throw new IllegalArgumentException(var9);
            }
         }
      }

      public void filterComment(HtmlDocument.Comment var1, List<HtmlDocument.Node> var2) {}

      public abstract void filterEndTag(HtmlDocument.EndTag var1, List<HtmlDocument.Node> var2);

      public abstract void filterTag(HtmlDocument.Tag var1, List<HtmlDocument.Node> var2);

      public abstract void filterText(HtmlDocument.Text var1, List<HtmlDocument.Node> var2);
   }

   public static class MultiplexFilterChain implements HtmlDocument.MultiplexFilter {

      private final List<HtmlDocument.MultiplexFilter> filters;


      public MultiplexFilterChain(List<HtmlDocument.MultiplexFilter> var1) {
         ArrayList var2 = new ArrayList();
         this.filters = var2;
         this.filters.addAll(var1);
      }

      public void filter(HtmlDocument.Node var1, List<HtmlDocument.Node> var2) {
         ArrayList var3 = new ArrayList();
         var3.add(var1);

         ArrayList var7;
         for(Iterator var5 = this.filters.iterator(); var5.hasNext(); var3 = var7) {
            HtmlDocument.MultiplexFilter var6 = (HtmlDocument.MultiplexFilter)var5.next();
            if(var3.isEmpty()) {
               return;
            }

            var7 = new ArrayList();
            Iterator var8 = var3.iterator();

            while(var8.hasNext()) {
               HtmlDocument.Node var9 = (HtmlDocument.Node)var8.next();
               var6.filter(var9, var7);
            }
         }

         var2.addAll(var3);
      }

      public void finish(List<HtmlDocument.Node> var1) {
         ArrayList var2 = new ArrayList();

         ArrayList var5;
         for(Iterator var3 = this.filters.iterator(); var3.hasNext(); var2 = var5) {
            HtmlDocument.MultiplexFilter var4 = (HtmlDocument.MultiplexFilter)var3.next();
            var5 = new ArrayList();
            Iterator var6 = var2.iterator();

            while(var6.hasNext()) {
               HtmlDocument.Node var7 = (HtmlDocument.Node)var6.next();
               var4.filter(var7, var5);
            }

            var4.finish(var5);
         }

         var1.addAll(var2);
      }

      public void start() {
         Iterator var1 = this.filters.iterator();

         while(var1.hasNext()) {
            ((HtmlDocument.MultiplexFilter)var1.next()).start();
         }

      }
   }
}
