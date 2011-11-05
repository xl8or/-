package com.google.android.common.html.parser;

import com.google.android.common.base.CharMatcher;
import com.google.android.common.base.Preconditions;
import com.google.android.common.base.X;
import com.google.android.common.html.parser.HTML;
import com.google.android.common.html.parser.HTML4;
import com.google.android.common.html.parser.HtmlDocument;
import com.google.common.collect.ImmutableSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Logger;

public class HtmlTree {

   private static final boolean DEBUG;
   private static final HtmlTree.PlainTextConverterFactory DEFAULT_CONVERTER_FACTORY = new HtmlTree.1();
   private static final Logger logger = Logger.getLogger(HtmlTree.class.getName());
   private final Stack<Integer> begins;
   private HtmlTree.PlainTextConverterFactory converterFactory;
   private final Stack<Integer> ends;
   private String html;
   private final List<HtmlDocument.Node> nodes;
   private int parent;
   private String plainText;
   private Stack<Integer> stack;
   private int[] textPositions;


   HtmlTree() {
      ArrayList var1 = new ArrayList();
      this.nodes = var1;
      Stack var2 = new Stack();
      this.begins = var2;
      Stack var3 = new Stack();
      this.ends = var3;
      HtmlTree.PlainTextConverterFactory var4 = DEFAULT_CONVERTER_FACTORY;
      this.converterFactory = var4;
   }

   private void addNode(HtmlDocument.Node var1, int var2, int var3) {
      this.nodes.add(var1);
      Stack var5 = this.begins;
      Integer var6 = Integer.valueOf(var2);
      var5.add(var6);
      Stack var8 = this.ends;
      Integer var9 = Integer.valueOf(var3);
      var8.add(var9);
   }

   private boolean canBeginBlockAt(int var1) {
      boolean var2 = true;
      int var3 = this.textPositions[var1];
      int var4 = this.plainText.length();
      if(var3 == var4) {
         var3 += -1;
      }

      for(int var5 = var3; var5 > 0; var5 += -1) {
         char var6 = this.plainText.charAt(var5);
         if(var6 == 10) {
            break;
         }

         if(var5 < var3 && !Character.isWhitespace(var6)) {
            var2 = false;
            break;
         }
      }

      return var2;
   }

   private void convertToPlainText() {
      byte var1;
      if(this.plainText == null && this.textPositions == null) {
         var1 = 1;
      } else {
         var1 = 0;
      }

      X.assertTrue((boolean)var1);
      int var2 = this.nodes.size();
      int[] var3 = new int[var2 + 1];
      this.textPositions = var3;
      HtmlTree.PlainTextConverter var4 = this.converterFactory.createInstance();

      for(int var5 = 0; var5 < var2; ++var5) {
         int[] var6 = this.textPositions;
         int var7 = var4.getPlainTextLength();
         var6[var5] = var7;
         HtmlDocument.Node var8 = (HtmlDocument.Node)this.nodes.get(var5);
         int var9 = ((Integer)this.ends.get(var5)).intValue();
         var4.addNode(var8, var5, var9);
      }

      int[] var10 = this.textPositions;
      int var11 = var4.getPlainTextLength();
      var10[var2] = var11;
      String var12 = var4.getPlainText();
      this.plainText = var12;
   }

   private static final void debug(String var0) {
      logger.finest(var0);
   }

   private int getBlockEnd(int var1) {
      int var2 = Arrays.binarySearch(this.textPositions, var1);
      if(var2 >= 0) {
         while(true) {
            int var3 = var2 + 1;
            int var4 = this.textPositions.length;
            if(var3 >= var4) {
               break;
            }

            int[] var5 = this.textPositions;
            int var6 = var2 + 1;
            if(var5[var6] != var1) {
               break;
            }

            ++var2;
         }
      } else {
         var2 = -var2 + -2;
      }

      byte var8;
      label17: {
         if(var2 >= 0) {
            int var7 = this.nodes.size();
            if(var2 <= var7) {
               var8 = 1;
               break label17;
            }
         }

         var8 = 0;
      }

      X.assertTrue((boolean)var8);
      return var2;
   }

   private int getBlockStart(int var1) {
      int var2 = Arrays.binarySearch(this.textPositions, var1);
      if(var2 >= 0) {
         while(var2 + -1 >= 0) {
            int[] var3 = this.textPositions;
            int var4 = var2 + -1;
            if(var3[var4] != var1) {
               break;
            }

            var2 += -1;
         }
      } else {
         var2 = -var2 + -1;
      }

      byte var6;
      label17: {
         if(var2 >= 0) {
            int var5 = this.nodes.size();
            if(var2 <= var5) {
               var6 = 1;
               break label17;
            }
         }

         var6 = 0;
      }

      X.assertTrue((boolean)var6);
      return var2;
   }

   void addEndTag(HtmlDocument.EndTag var1) {
      int var2 = this.nodes.size();
      int var3 = this.parent;
      this.addNode(var1, var3, var2);
      if(this.parent != -1) {
         Stack var4 = this.ends;
         int var5 = this.parent;
         Integer var6 = Integer.valueOf(var2);
         var4.set(var5, var6);
      }

      int var8 = ((Integer)this.stack.pop()).intValue();
      this.parent = var8;
   }

   void addSingularTag(HtmlDocument.Tag var1) {
      int var2 = this.nodes.size();
      this.addNode(var1, var2, var2);
   }

   void addStartTag(HtmlDocument.Tag var1) {
      int var2 = this.nodes.size();
      this.addNode(var1, var2, -1);
      Stack var3 = this.stack;
      Integer var4 = Integer.valueOf(this.parent);
      var3.add(var4);
      this.parent = var2;
   }

   void addText(HtmlDocument.Text var1) {
      int var2 = this.nodes.size();
      this.addNode(var1, var2, var2);
   }

   public ArrayList<HtmlTree.Block> createBlocks(int var1, int var2, int var3, int var4) {
      ArrayList var5 = new ArrayList();
      int var6 = Math.max(this.getBlockStart(var1), var3);
      int var7 = Math.min(this.getBlockEnd(var2), var4);
      int var8 = -1;
      int var9 = var6;

      while(var9 < var7) {
         int var10 = ((Integer)this.begins.get(var9)).intValue();
         int var11 = ((Integer)this.ends.get(var9)).intValue();
         if(var8 == -1) {
            if(var10 >= var9 && var11 <= var7 && this.canBeginBlockAt(var9)) {
               var8 = var9;
               var9 = var11 + 1;
            } else {
               ++var9;
            }
         } else if(var10 >= var8 && var11 < var7) {
            var9 = var11 + 1;
         } else {
            HtmlTree.Block var12 = new HtmlTree.Block();
            var12.start_node = var8;
            var12.end_node = var9;
            var5.add(var12);
            var8 = -1;
            ++var9;
         }
      }

      if(var8 != -1) {
         HtmlTree.Block var14 = new HtmlTree.Block();
         var14.start_node = var8;
         var14.end_node = var7;
         var5.add(var14);
      }

      return var5;
   }

   void finish() {
      byte var1 = 1;
      byte var2;
      if(this.stack.size() == 0) {
         var2 = 1;
      } else {
         var2 = 0;
      }

      X.assertTrue((boolean)var2);
      if(this.parent != -1) {
         var1 = 0;
      }

      X.assertTrue((boolean)var1);
   }

   public String getHtml() {
      return this.getHtml(-1);
   }

   public String getHtml(int var1) {
      if(this.html == null) {
         int var2 = this.nodes.size();
         String var3 = this.getHtml(0, var2, var1);
         this.html = var3;
      }

      return this.html;
   }

   public String getHtml(int var1, int var2) {
      return this.getHtml(var1, var2, -1);
   }

   public String getHtml(int var1, int var2, int var3) {
      byte var5;
      label42: {
         if(var1 >= 0) {
            int var4 = this.nodes.size();
            if(var2 <= var4) {
               var5 = 1;
               break label42;
            }
         }

         var5 = 0;
      }

      X.assertTrue((boolean)var5);
      int var6 = (var2 - var1) * 10;
      StringBuilder var7 = new StringBuilder(var6);
      int var8 = 0;

      for(int var9 = var1; var9 < var2; ++var9) {
         HtmlDocument.Node var10 = (HtmlDocument.Node)this.nodes.get(var9);
         var10.toHTML(var7);
         if(var3 > 0 && (var10 instanceof HtmlDocument.Tag && ((HtmlDocument.Tag)var10).getElement().breaksFlow() || var10 instanceof HtmlDocument.EndTag && ((HtmlDocument.EndTag)var10).getElement().breaksFlow())) {
            int var11 = var8 + 1;
            int var12 = var7.substring(var11).lastIndexOf(10);
            if(var12 != -1) {
               var8 += var12;
            }

            if(var7.length() + -1 - var8 > var3) {
               StringBuilder var13 = var7.append('\n');
               int var14 = var7.length() + -1;
            }
         }
      }

      return var7.toString();
   }

   public ArrayList<String> getHtmlChunks(int var1, int var2, int var3) {
      byte var5;
      label63: {
         if(var1 >= 0) {
            int var4 = this.nodes.size();
            if(var2 <= var4) {
               var5 = 1;
               break label63;
            }
         }

         var5 = 0;
      }

      X.assertTrue((boolean)var5);
      ArrayList var6 = new ArrayList();
      int var7 = 0;
      boolean var8 = true;
      int var9 = var3 + 256;
      StringBuilder var10 = new StringBuilder(var9);

      for(int var11 = var1; var11 < var2; ++var11) {
         HtmlDocument.Node var12 = (HtmlDocument.Node)this.nodes.get(var11);
         var12.toHTML(var10);
         if(var12 instanceof HtmlDocument.Tag) {
            HTML.Element var13 = HTML4.TEXTAREA_ELEMENT;
            HTML.Element var14 = ((HtmlDocument.Tag)var12).getElement();
            if(var13.equals(var14)) {
               ++var7;
            }
         }

         if(var12 instanceof HtmlDocument.EndTag) {
            HTML.Element var15 = HTML4.TEXTAREA_ELEMENT;
            HTML.Element var16 = ((HtmlDocument.EndTag)var12).getElement();
            if(var15.equals(var16)) {
               if(var7 == 0) {
                  var8 = false;
               } else {
                  var7 += -1;
               }
            }
         }

         if(var7 == 0 && var10.length() >= var3) {
            String var17 = var10.toString();
            var6.add(var17);
            var10.setLength(0);
         }
      }

      if(var10.length() > 0) {
         String var19 = var10.toString();
         var6.add(var19);
      }

      if(!var8 || var7 != 0) {
         StringBuilder var21 = new StringBuilder("Returning unbalanced HTML:\n");
         String var22 = this.getHtml();
         var21.append(var22);
         StringBuilder var24 = var21.append("\nfromNode: ").append(var1);
         StringBuilder var25 = var21.append("\ntoNode: ").append(var2);
         StringBuilder var26 = var21.append("\nNum nodes_: ");
         int var27 = this.getNumNodes();
         var26.append(var27);

         StringBuilder var31;
         String var30;
         for(Iterator var29 = var6.iterator(); var29.hasNext(); var31 = var21.append("\nChunk:\n").append(var30)) {
            var30 = (String)var29.next();
         }

         Logger var32 = logger;
         String var33 = var21.toString();
         var32.severe(var33);
      }

      return var6;
   }

   public List<HtmlDocument.Node> getNodesList() {
      return Collections.unmodifiableList(this.nodes);
   }

   public int getNumNodes() {
      return this.nodes.size();
   }

   public String getPlainText() {
      if(this.plainText == null) {
         this.convertToPlainText();
      }

      return this.plainText;
   }

   public String getPlainText(int var1, int var2) {
      if(this.plainText == null) {
         this.convertToPlainText();
      }

      int var3 = this.textPositions[var1];
      int var4 = this.textPositions[var2];
      return this.plainText.substring(var3, var4);
   }

   public int getTextPosition(int var1) {
      return this.textPositions[var1];
   }

   public int getTreeHeight() {
      int var1 = 0;
      int var2 = 0;
      int var3 = 0;

      while(true) {
         int var4 = this.nodes.size();
         if(var3 >= var4) {
            return var2;
         }

         HtmlDocument.Node var5 = (HtmlDocument.Node)this.nodes.get(var3);
         if(var5 instanceof HtmlDocument.Tag) {
            ++var1;
            if(var1 > var2) {
               var2 = var1;
            }

            if(((HtmlDocument.Tag)var5).getElement().isEmpty()) {
               int var6 = var1 + -1;
            }
         } else if(var5 instanceof HtmlDocument.EndTag) {
            int var7 = var1 + -1;
         }

         ++var3;
      }
   }

   public void setPlainTextConverterFactory(HtmlTree.PlainTextConverterFactory var1) {
      if(var1 == null) {
         throw new NullPointerException("factory must not be null");
      } else {
         this.converterFactory = var1;
      }
   }

   void start() {
      Stack var1 = new Stack();
      this.stack = var1;
      this.parent = -1;
   }

   public interface PlainTextConverterFactory {

      HtmlTree.PlainTextConverter createInstance();
   }

   static class 1 implements HtmlTree.PlainTextConverterFactory {

      1() {}

      public HtmlTree.PlainTextConverter createInstance() {
         return new HtmlTree.DefaultPlainTextConverter();
      }
   }

   public static class Block {

      public int end_node;
      public int start_node;


      public Block() {}
   }

   // $FF: synthetic class
   static class 2 {

      // $FF: synthetic field
      static final int[] $SwitchMap$com$google$android$common$html$parser$HtmlTree$PlainTextPrinter$Separator = new int[HtmlTree.PlainTextPrinter.Separator.values().length];


      static {
         try {
            int[] var0 = $SwitchMap$com$google$android$common$html$parser$HtmlTree$PlainTextPrinter$Separator;
            int var1 = HtmlTree.PlainTextPrinter.Separator.Space.ordinal();
            var0[var1] = 1;
         } catch (NoSuchFieldError var11) {
            ;
         }

         try {
            int[] var2 = $SwitchMap$com$google$android$common$html$parser$HtmlTree$PlainTextPrinter$Separator;
            int var3 = HtmlTree.PlainTextPrinter.Separator.LineBreak.ordinal();
            var2[var3] = 2;
         } catch (NoSuchFieldError var10) {
            ;
         }

         try {
            int[] var4 = $SwitchMap$com$google$android$common$html$parser$HtmlTree$PlainTextPrinter$Separator;
            int var5 = HtmlTree.PlainTextPrinter.Separator.BlankLine.ordinal();
            var4[var5] = 3;
         } catch (NoSuchFieldError var9) {
            ;
         }
      }
   }

   public static class DefaultPlainTextConverter implements HtmlTree.PlainTextConverter {

      private static final Set<HTML.Element> BLANK_LINE_ELEMENTS;
      private int preDepth;
      private final HtmlTree.PlainTextPrinter printer;


      static {
         HTML.Element var0 = HTML4.P_ELEMENT;
         HTML.Element var1 = HTML4.BLOCKQUOTE_ELEMENT;
         HTML.Element var2 = HTML4.PRE_ELEMENT;
         BLANK_LINE_ELEMENTS = ImmutableSet.of(var0, var1, var2);
      }

      public DefaultPlainTextConverter() {
         HtmlTree.PlainTextPrinter var1 = new HtmlTree.PlainTextPrinter();
         this.printer = var1;
         this.preDepth = 0;
      }

      public void addNode(HtmlDocument.Node var1, int var2, int var3) {
         if(var1 instanceof HtmlDocument.Text) {
            String var4 = ((HtmlDocument.Text)var1).getText();
            if(this.preDepth > 0) {
               this.printer.appendPreText(var4);
            } else {
               this.printer.appendNormalText(var4);
            }
         } else {
            HTML.Element var5;
            if(var1 instanceof HtmlDocument.Tag) {
               var5 = ((HtmlDocument.Tag)var1).getElement();
               if(BLANK_LINE_ELEMENTS.contains(var5)) {
                  HtmlTree.PlainTextPrinter var6 = this.printer;
                  HtmlTree.PlainTextPrinter.Separator var7 = HtmlTree.PlainTextPrinter.Separator.BlankLine;
                  var6.setSeparator(var7);
               } else if(HTML4.BR_ELEMENT.equals(var5)) {
                  this.printer.appendForcedLineBreak();
               } else if(var5.breaksFlow()) {
                  HtmlTree.PlainTextPrinter var8 = this.printer;
                  HtmlTree.PlainTextPrinter.Separator var9 = HtmlTree.PlainTextPrinter.Separator.LineBreak;
                  var8.setSeparator(var9);
                  if(HTML4.HR_ELEMENT.equals(var5)) {
                     this.printer.appendNormalText("________________________________");
                     HtmlTree.PlainTextPrinter var10 = this.printer;
                     HtmlTree.PlainTextPrinter.Separator var11 = HtmlTree.PlainTextPrinter.Separator.LineBreak;
                     var10.setSeparator(var11);
                  }
               }

               if(HTML4.BLOCKQUOTE_ELEMENT.equals(var5)) {
                  this.printer.incQuoteDepth();
               } else if(HTML4.PRE_ELEMENT.equals(var5)) {
                  int var12 = this.preDepth + 1;
                  this.preDepth = var12;
               }
            } else if(var1 instanceof HtmlDocument.EndTag) {
               var5 = ((HtmlDocument.EndTag)var1).getElement();
               if(BLANK_LINE_ELEMENTS.contains(var5)) {
                  HtmlTree.PlainTextPrinter var13 = this.printer;
                  HtmlTree.PlainTextPrinter.Separator var14 = HtmlTree.PlainTextPrinter.Separator.BlankLine;
                  var13.setSeparator(var14);
               } else if(var5.breaksFlow()) {
                  HtmlTree.PlainTextPrinter var15 = this.printer;
                  HtmlTree.PlainTextPrinter.Separator var16 = HtmlTree.PlainTextPrinter.Separator.LineBreak;
                  var15.setSeparator(var16);
               }

               if(HTML4.BLOCKQUOTE_ELEMENT.equals(var5)) {
                  this.printer.decQuoteDepth();
               } else if(HTML4.PRE_ELEMENT.equals(var5)) {
                  int var17 = this.preDepth + -1;
                  this.preDepth = var17;
               }
            }
         }
      }

      public final String getPlainText() {
         return this.printer.getText();
      }

      public final int getPlainTextLength() {
         return this.printer.getTextLength();
      }
   }

   public interface PlainTextConverter {

      void addNode(HtmlDocument.Node var1, int var2, int var3);

      String getPlainText();

      int getPlainTextLength();
   }

   static final class PlainTextPrinter {

      private static final String HTML_SPACE_EQUIVALENTS = " \n\r\t\f";
      private int endingNewLines;
      private int quoteDepth;
      private final StringBuilder sb;
      private HtmlTree.PlainTextPrinter.Separator separator;


      PlainTextPrinter() {
         StringBuilder var1 = new StringBuilder();
         this.sb = var1;
         this.quoteDepth = 0;
         this.endingNewLines = 2;
         HtmlTree.PlainTextPrinter.Separator var2 = HtmlTree.PlainTextPrinter.Separator.None;
         this.separator = var2;
      }

      private void appendNewLine() {
         this.maybeAddQuoteMarks((boolean)0);
         StringBuilder var1 = this.sb.append('\n');
         int var2 = this.endingNewLines + 1;
         this.endingNewLines = var2;
      }

      private void appendTextDirect(String var1) {
         if(var1.length() != 0) {
            byte var2;
            if(var1.indexOf(10) < 0) {
               var2 = 1;
            } else {
               var2 = 0;
            }

            Preconditions.checkArgument((boolean)var2, "text must not contain newlines.");
            this.flushSeparator();
            this.maybeAddQuoteMarks((boolean)1);
            this.sb.append(var1);
            this.endingNewLines = 0;
         }
      }

      private void flushSeparator() {
         int[] var1 = HtmlTree.2.$SwitchMap$com$google$android$common$html$parser$HtmlTree$PlainTextPrinter$Separator;
         int var2 = this.separator.ordinal();
         label21:
         switch(var1[var2]) {
         case 1:
            if(this.endingNewLines == 0) {
               StringBuilder var4 = this.sb.append(" ");
            }
            break;
         case 2:
            while(true) {
               if(this.endingNewLines >= 1) {
                  break label21;
               }

               this.appendNewLine();
            }
         case 3:
            while(this.endingNewLines < 2) {
               this.appendNewLine();
            }
         }

         HtmlTree.PlainTextPrinter.Separator var3 = HtmlTree.PlainTextPrinter.Separator.None;
         this.separator = var3;
      }

      private static boolean isHtmlWhiteSpace(char var0) {
         boolean var1;
         if(" \n\r\t\f".indexOf(var0) >= 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      private void maybeAddQuoteMarks(boolean var1) {
         if(this.endingNewLines > 0) {
            if(this.quoteDepth > 0) {
               int var2 = 0;

               while(true) {
                  int var3 = this.quoteDepth;
                  if(var2 >= var3) {
                     if(!var1) {
                        return;
                     } else {
                        StringBuilder var5 = this.sb.append(' ');
                        return;
                     }
                  }

                  StringBuilder var4 = this.sb.append('>');
                  ++var2;
               }
            }
         }
      }

      final void appendForcedLineBreak() {
         this.flushSeparator();
         this.appendNewLine();
      }

      final void appendNormalText(String var1) {
         if(var1.length() != 0) {
            boolean var2 = isHtmlWhiteSpace(var1.charAt(0));
            int var3 = var1.length() + -1;
            boolean var4 = isHtmlWhiteSpace(var1.charAt(var3));
            String var5 = CharMatcher.anyOf(" \n\r\t\f").trimFrom(var1);
            var1 = CharMatcher.anyOf(" \n\r\t\f").collapseFrom(var5, ' ');
            if(var2) {
               HtmlTree.PlainTextPrinter.Separator var6 = HtmlTree.PlainTextPrinter.Separator.Space;
               this.setSeparator(var6);
            }

            this.appendTextDirect(var1);
            if(var4) {
               HtmlTree.PlainTextPrinter.Separator var7 = HtmlTree.PlainTextPrinter.Separator.Space;
               this.setSeparator(var7);
            }
         }
      }

      final void appendPreText(String var1) {
         String[] var2 = var1.split("[\\r\\n]", -1);
         String var3 = var2[0];
         this.appendTextDirect(var3);
         int var4 = 1;

         while(true) {
            int var5 = var2.length;
            if(var4 >= var5) {
               return;
            }

            this.appendNewLine();
            String var6 = var2[var4];
            this.appendTextDirect(var6);
            ++var4;
         }
      }

      final void decQuoteDepth() {
         int var1 = this.quoteDepth + -1;
         int var2 = Math.max(0, var1);
         this.quoteDepth = var2;
      }

      final String getText() {
         return this.sb.toString();
      }

      final int getTextLength() {
         return this.sb.length();
      }

      final void incQuoteDepth() {
         int var1 = this.quoteDepth + 1;
         this.quoteDepth = var1;
      }

      final void setSeparator(HtmlTree.PlainTextPrinter.Separator var1) {
         int var2 = var1.ordinal();
         int var3 = this.separator.ordinal();
         if(var2 > var3) {
            this.separator = var1;
         }
      }

      static enum Separator {

         // $FF: synthetic field
         private static final HtmlTree.PlainTextPrinter.Separator[] $VALUES;
         BlankLine("BlankLine", 3),
         LineBreak("LineBreak", 2),
         None("None", 0),
         Space("Space", 1);


         static {
            HtmlTree.PlainTextPrinter.Separator[] var0 = new HtmlTree.PlainTextPrinter.Separator[4];
            HtmlTree.PlainTextPrinter.Separator var1 = None;
            var0[0] = var1;
            HtmlTree.PlainTextPrinter.Separator var2 = Space;
            var0[1] = var2;
            HtmlTree.PlainTextPrinter.Separator var3 = LineBreak;
            var0[2] = var3;
            HtmlTree.PlainTextPrinter.Separator var4 = BlankLine;
            var0[3] = var4;
            $VALUES = var0;
         }

         private Separator(String var1, int var2) {}
      }
   }
}
