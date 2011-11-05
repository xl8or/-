package com.google.android.common.html.parser;

import com.google.android.common.html.parser.HTML;
import com.google.android.common.html.parser.HtmlWhitelist;
import com.google.common.collect.Maps;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class HTML4 {

   public static final HTML.Attribute ABBR_ATTRIBUTE;
   public static final HTML.Element ABBR_ELEMENT;
   public static final HTML.Attribute ACCEPT_ATTRIBUTE;
   public static final HTML.Attribute ACCEPT_CHARSET_ATTRIBUTE;
   public static final HTML.Attribute ACCESSKEY_ATTRIBUTE;
   public static final HTML.Element ACRONYM_ELEMENT;
   public static final HTML.Attribute ACTION_ATTRIBUTE;
   public static final HTML.Element ADDRESS_ELEMENT;
   public static final HTML.Attribute ALIGN_ATTRIBUTE;
   public static final HTML.Attribute ALINK_ATTRIBUTE;
   public static final HTML.Attribute ALT_ATTRIBUTE;
   public static final HTML.Element APPLET_ELEMENT;
   public static final HTML.Attribute ARCHIVE_ATTRIBUTE;
   public static final HTML.Element AREA_ELEMENT;
   public static final HTML.Attribute AXIS_ATTRIBUTE;
   public static final HTML.Element A_ELEMENT;
   public static final HTML.Attribute BACKGROUND_ATTRIBUTE;
   public static final HTML.Element BASEFONT_ELEMENT;
   public static final HTML.Element BASE_ELEMENT;
   public static final HTML.Element BDO_ELEMENT;
   public static final HTML.Attribute BGCOLOR_ATTRIBUTE;
   public static final HTML.Element BIG_ELEMENT;
   public static final HTML.Element BLOCKQUOTE_ELEMENT;
   public static final HTML.Element BODY_ELEMENT;
   public static final HTML.Attribute BORDER_ATTRIBUTE;
   public static final HTML.Element BR_ELEMENT;
   public static final HTML.Element BUTTON_ELEMENT;
   public static final HTML.Element B_ELEMENT;
   public static final HTML.Element CAPTION_ELEMENT;
   public static final HTML.Attribute CELLPADDING_ATTRIBUTE;
   public static final HTML.Attribute CELLSPACING_ATTRIBUTE;
   public static final HTML.Element CENTER_ELEMENT;
   public static final HTML.Attribute CHAROFF_ATTRIBUTE;
   public static final HTML.Attribute CHARSET_ATTRIBUTE;
   public static final HTML.Attribute CHAR_ATTRIBUTE;
   public static final HTML.Attribute CHECKED_ATTRIBUTE;
   public static final HTML.Attribute CITE_ATTRIBUTE;
   public static final HTML.Element CITE_ELEMENT;
   public static final HTML.Attribute CLASSID_ATTRIBUTE;
   public static final HTML.Attribute CLASS_ATTRIBUTE;
   public static final HTML.Attribute CLEAR_ATTRIBUTE;
   public static final HTML.Attribute CODEBASE_ATTRIBUTE;
   public static final HTML.Attribute CODETYPE_ATTRIBUTE;
   public static final HTML.Attribute CODE_ATTRIBUTE;
   public static final HTML.Element CODE_ELEMENT;
   public static final HTML.Element COLGROUP_ELEMENT;
   public static final HTML.Attribute COLOR_ATTRIBUTE;
   public static final HTML.Attribute COLSPAN_ATTRIBUTE;
   public static final HTML.Attribute COLS_ATTRIBUTE;
   public static final HTML.Element COL_ELEMENT;
   public static final HTML.Attribute COMPACT_ATTRIBUTE;
   public static final HTML.Attribute CONTENT_ATTRIBUTE;
   public static final HTML.Attribute COORDS_ATTRIBUTE;
   public static final HTML.Attribute DATA_ATTRIBUTE;
   public static final HTML.Attribute DATETIME_ATTRIBUTE;
   public static final HTML.Element DD_ELEMENT;
   public static final HTML.Attribute DECLARE_ATTRIBUTE;
   public static final HTML.Attribute DEFER_ATTRIBUTE;
   public static final HTML.Element DEL_ELEMENT;
   public static final HTML.Element DFN_ELEMENT;
   public static final HTML.Attribute DIR_ATTRIBUTE;
   public static final HTML.Element DIR_ELEMENT;
   public static final HTML.Attribute DISABLED_ATTRIBUTE;
   public static final HTML.Element DIV_ELEMENT;
   public static final HTML.Element DL_ELEMENT;
   public static final HTML.Element DT_ELEMENT;
   public static final HTML.Element EM_ELEMENT;
   public static final HTML.Attribute ENCTYPE_ATTRIBUTE;
   public static final HTML.Attribute FACE_ATTRIBUTE;
   public static final HTML.Element FIELDSET_ELEMENT;
   public static final HTML.Element FONT_ELEMENT;
   public static final HTML.Element FORM_ELEMENT;
   public static final HTML.Attribute FOR_ATTRIBUTE;
   public static final HTML.Attribute FRAMEBORDER_ATTRIBUTE;
   public static final HTML.Element FRAMESET_ELEMENT;
   public static final HTML.Attribute FRAME_ATTRIBUTE;
   public static final HTML.Element FRAME_ELEMENT;
   public static final HTML.Element H1_ELEMENT;
   public static final HTML.Element H2_ELEMENT;
   public static final HTML.Element H3_ELEMENT;
   public static final HTML.Element H4_ELEMENT;
   public static final HTML.Element H5_ELEMENT;
   public static final HTML.Element H6_ELEMENT;
   public static final HTML.Attribute HEADERS_ATTRIBUTE;
   public static final HTML.Element HEAD_ELEMENT;
   public static final HTML.Attribute HEIGHT_ATTRIBUTE;
   public static final HTML.Attribute HREFLANG_ATTRIBUTE;
   public static final HTML.Attribute HREF_ATTRIBUTE;
   public static final HTML.Element HR_ELEMENT;
   public static final HTML.Attribute HSPACE_ATTRIBUTE;
   public static final HTML.Element HTML_ELEMENT;
   public static final HTML.Attribute HTTP_EQUIV_ATTRIBUTE;
   public static final HTML.Attribute ID_ATTRIBUTE;
   public static final HTML.Element IFRAME_ELEMENT;
   public static final HTML.Element IMG_ELEMENT;
   public static final HTML.Element INPUT_ELEMENT;
   public static final HTML.Element INS_ELEMENT;
   public static final HTML.Element ISINDEX_ELEMENT;
   public static final HTML.Attribute ISMAP_ATTRIBUTE;
   public static final HTML.Element I_ELEMENT;
   public static final HTML.Element KBD_ELEMENT;
   public static final HTML.Attribute LABEL_ATTRIBUTE;
   public static final HTML.Element LABEL_ELEMENT;
   public static final HTML.Attribute LANGUAGE_ATTRIBUTE;
   public static final HTML.Attribute LANG_ATTRIBUTE;
   public static final HTML.Element LEGEND_ELEMENT;
   public static final HTML.Attribute LINK_ATTRIBUTE;
   public static final HTML.Element LINK_ELEMENT;
   public static final HTML.Element LI_ELEMENT;
   public static final HTML.Attribute LONGDESC_ATTRIBUTE;
   public static final HTML.Element MAP_ELEMENT;
   public static final HTML.Attribute MARGINHEIGHT_ATTRIBUTE;
   public static final HTML.Attribute MARGINWIDTH_ATTRIBUTE;
   public static final HTML.Attribute MAXLENGTH_ATTRIBUTE;
   public static final HTML.Attribute MEDIA_ATTRIBUTE;
   public static final HTML.Element MENU_ELEMENT;
   public static final HTML.Element META_ELEMENT;
   public static final HTML.Attribute METHOD_ATTRIBUTE;
   public static final HTML.Attribute MULTIPLE_ATTRIBUTE;
   public static final HTML.Attribute NAME_ATTRIBUTE;
   public static final HTML.Element NOFRAMES_ELEMENT;
   public static final HTML.Attribute NOHREF_ATTRIBUTE;
   public static final HTML.Attribute NORESIZE_ATTRIBUTE;
   public static final HTML.Element NOSCRIPT_ELEMENT;
   public static final HTML.Attribute NOSHADE_ATTRIBUTE;
   public static final HTML.Attribute NOWRAP_ATTRIBUTE;
   public static final HTML.Attribute OBJECT_ATTRIBUTE;
   public static final HTML.Element OBJECT_ELEMENT;
   public static final HTML.Element OL_ELEMENT;
   public static final HTML.Attribute ONBLUR_ATTRIBUTE;
   public static final HTML.Attribute ONCHANGE_ATTRIBUTE;
   public static final HTML.Attribute ONCLICK_ATTRIBUTE;
   public static final HTML.Attribute ONDBLCLICK_ATTRIBUTE;
   public static final HTML.Attribute ONFOCUS_ATTRIBUTE;
   public static final HTML.Attribute ONKEYDOWN_ATTRIBUTE;
   public static final HTML.Attribute ONKEYPRESS_ATTRIBUTE;
   public static final HTML.Attribute ONKEYUP_ATTRIBUTE;
   public static final HTML.Attribute ONLOAD_ATTRIBUTE;
   public static final HTML.Attribute ONMOUSEDOWN_ATTRIBUTE;
   public static final HTML.Attribute ONMOUSEMOVE_ATTRIBUTE;
   public static final HTML.Attribute ONMOUSEOUT_ATTRIBUTE;
   public static final HTML.Attribute ONMOUSEOVER_ATTRIBUTE;
   public static final HTML.Attribute ONMOUSEUP_ATTRIBUTE;
   public static final HTML.Attribute ONRESET_ATTRIBUTE;
   public static final HTML.Attribute ONSELECT_ATTRIBUTE;
   public static final HTML.Attribute ONSUBMIT_ATTRIBUTE;
   public static final HTML.Attribute ONUNLOAD_ATTRIBUTE;
   public static final HTML.Element OPTGROUP_ELEMENT;
   public static final HTML.Element OPTION_ELEMENT;
   public static final HTML.Element PARAM_ELEMENT;
   public static final HTML.Element PRE_ELEMENT;
   public static final HTML.Attribute PROFILE_ATTRIBUTE;
   public static final HTML.Attribute PROMPT_ATTRIBUTE;
   public static final HTML.Element P_ELEMENT;
   public static final HTML.Element Q_ELEMENT;
   public static final HTML.Attribute READONLY_ATTRIBUTE;
   public static final HTML.Attribute REL_ATTRIBUTE;
   public static final HTML.Attribute REV_ATTRIBUTE;
   public static final HTML.Attribute ROWSPAN_ATTRIBUTE;
   public static final HTML.Attribute ROWS_ATTRIBUTE;
   public static final HTML.Attribute RULES_ATTRIBUTE;
   public static final HTML.Element SAMP_ELEMENT;
   public static final HTML.Attribute SCHEME_ATTRIBUTE;
   public static final HTML.Attribute SCOPE_ATTRIBUTE;
   public static final HTML.Element SCRIPT_ELEMENT;
   public static final HTML.Attribute SCROLLING_ATTRIBUTE;
   public static final HTML.Attribute SELECTED_ATTRIBUTE;
   public static final HTML.Element SELECT_ELEMENT;
   public static final HTML.Attribute SHAPE_ATTRIBUTE;
   public static final HTML.Attribute SIZE_ATTRIBUTE;
   public static final HTML.Element SMALL_ELEMENT;
   public static final HTML.Attribute SPAN_ATTRIBUTE;
   public static final HTML.Element SPAN_ELEMENT;
   public static final HTML.Attribute SRC_ATTRIBUTE;
   public static final HTML.Attribute STANDBY_ATTRIBUTE;
   public static final HTML.Attribute START_ATTRIBUTE;
   public static final HTML.Element STRIKE_ELEMENT;
   public static final HTML.Element STRONG_ELEMENT;
   public static final HTML.Attribute STYLE_ATTRIBUTE;
   public static final HTML.Element STYLE_ELEMENT;
   public static final HTML.Element SUB_ELEMENT;
   public static final HTML.Attribute SUMMARY_ATTRIBUTE;
   public static final HTML.Element SUP_ELEMENT;
   public static final HTML.Element S_ELEMENT;
   public static final HTML.Attribute TABINDEX_ATTRIBUTE;
   public static final HTML.Element TABLE_ELEMENT;
   public static final HTML.Attribute TARGET_ATTRIBUTE;
   public static final HTML.Element TBODY_ELEMENT;
   public static final HTML.Element TD_ELEMENT;
   public static final HTML.Element TEXTAREA_ELEMENT;
   public static final HTML.Attribute TEXT_ATTRIBUTE;
   public static final HTML.Element TFOOT_ELEMENT;
   public static final HTML.Element THEAD_ELEMENT;
   public static final HTML.Element TH_ELEMENT;
   public static final HTML.Attribute TITLE_ATTRIBUTE;
   public static final HTML.Element TITLE_ELEMENT;
   public static final HTML.Element TR_ELEMENT;
   public static final HTML.Element TT_ELEMENT;
   public static final HTML.Attribute TYPE_ATTRIBUTE;
   public static final HTML.Element UL_ELEMENT;
   public static final HTML.Attribute USEMAP_ATTRIBUTE;
   public static final HTML.Element U_ELEMENT;
   public static final HTML.Attribute VALIGN_ATTRIBUTE;
   public static final HTML.Attribute VALUETYPE_ATTRIBUTE;
   public static final HTML.Attribute VALUE_ATTRIBUTE;
   public static final HTML.Element VAR_ELEMENT;
   public static final HTML.Attribute VERSION_ATTRIBUTE;
   public static final HTML.Attribute VLINK_ATTRIBUTE;
   public static final HTML.Attribute VSPACE_ATTRIBUTE;
   public static final HTML.Attribute WIDTH_ATTRIBUTE;
   private static final HashMap<String, HTML.Attribute> attributes = Maps.newHashMap();
   private static final HtmlWhitelist defaultWhitelist = new HTML4.1();
   private static final HashMap<String, HTML.Element> elements = Maps.newHashMap();


   static {
      HTML.Element.Flow var0 = HTML.Element.Flow.INLINE;
      A_ELEMENT = addElement("A", "", var0);
      HTML.Element.Flow var1 = HTML.Element.Flow.INLINE;
      ABBR_ELEMENT = addElement("ABBR", "", var1);
      HTML.Element.Flow var2 = HTML.Element.Flow.INLINE;
      ACRONYM_ELEMENT = addElement("ACRONYM", "", var2);
      HTML.Element.Flow var3 = HTML.Element.Flow.BLOCK;
      ADDRESS_ELEMENT = addElement("ADDRESS", "", var3);
      APPLET_ELEMENT = addElement("APPLET", "");
      AREA_ELEMENT = addElement("AREA", "E");
      HTML.Element.Flow var4 = HTML.Element.Flow.INLINE;
      B_ELEMENT = addElement("B", "", var4);
      BASE_ELEMENT = addElement("BASE", "E");
      BASEFONT_ELEMENT = addElement("BASEFONT", "E");
      HTML.Element.Flow var5 = HTML.Element.Flow.INLINE;
      BDO_ELEMENT = addElement("BDO", "", var5);
      HTML.Element.Flow var6 = HTML.Element.Flow.INLINE;
      BIG_ELEMENT = addElement("BIG", "", var6);
      HTML.Element.Flow var7 = HTML.Element.Flow.BLOCK;
      BLOCKQUOTE_ELEMENT = addElement("BLOCKQUOTE", "B", var7);
      BODY_ELEMENT = addElement("BODY", "O");
      HTML.Element.Flow var8 = HTML.Element.Flow.INLINE;
      BR_ELEMENT = addElement("BR", "EB", var8);
      HTML.Element.Flow var9 = HTML.Element.Flow.INLINE;
      BUTTON_ELEMENT = addElement("BUTTON", "", var9);
      HTML.Element.Flow var10 = HTML.Element.Flow.NONE;
      CAPTION_ELEMENT = addTableElement("CAPTION", "", var10);
      HTML.Element.Flow var11 = HTML.Element.Flow.BLOCK;
      CENTER_ELEMENT = addElement("CENTER", "B", var11);
      HTML.Element.Flow var12 = HTML.Element.Flow.INLINE;
      CITE_ELEMENT = addElement("CITE", "", var12);
      HTML.Element.Flow var13 = HTML.Element.Flow.INLINE;
      CODE_ELEMENT = addElement("CODE", "", var13);
      HTML.Element.Flow var14 = HTML.Element.Flow.NONE;
      COL_ELEMENT = addTableElement("COL", "E", var14);
      HTML.Element.Flow var15 = HTML.Element.Flow.NONE;
      COLGROUP_ELEMENT = addTableElement("COLGROUP", "O", var15);
      DD_ELEMENT = addElement("DD", "OB");
      DEL_ELEMENT = addElement("DEL", "");
      HTML.Element.Flow var16 = HTML.Element.Flow.INLINE;
      DFN_ELEMENT = addElement("DFN", "", var16);
      HTML.Element.Flow var17 = HTML.Element.Flow.BLOCK;
      DIR_ELEMENT = addElement("DIR", "B", var17);
      HTML.Element.Flow var18 = HTML.Element.Flow.BLOCK;
      DIV_ELEMENT = addElement("DIV", "B", var18);
      HTML.Element.Flow var19 = HTML.Element.Flow.BLOCK;
      DL_ELEMENT = addElement("DL", "B", var19);
      DT_ELEMENT = addElement("DT", "OB");
      HTML.Element.Flow var20 = HTML.Element.Flow.INLINE;
      EM_ELEMENT = addElement("EM", "", var20);
      HTML.Element.Flow var21 = HTML.Element.Flow.BLOCK;
      FIELDSET_ELEMENT = addElement("FIELDSET", "", var21);
      HTML.Element.Flow var22 = HTML.Element.Flow.INLINE;
      FONT_ELEMENT = addElement("FONT", "", var22);
      HTML.Element.Flow var23 = HTML.Element.Flow.BLOCK;
      FORM_ELEMENT = addElement("FORM", "B", var23);
      FRAME_ELEMENT = addElement("FRAME", "E");
      FRAMESET_ELEMENT = addElement("FRAMESET", "");
      HTML.Element.Flow var24 = HTML.Element.Flow.BLOCK;
      H1_ELEMENT = addElement("H1", "B", var24);
      HTML.Element.Flow var25 = HTML.Element.Flow.BLOCK;
      H2_ELEMENT = addElement("H2", "B", var25);
      HTML.Element.Flow var26 = HTML.Element.Flow.BLOCK;
      H3_ELEMENT = addElement("H3", "B", var26);
      HTML.Element.Flow var27 = HTML.Element.Flow.BLOCK;
      H4_ELEMENT = addElement("H4", "B", var27);
      HTML.Element.Flow var28 = HTML.Element.Flow.BLOCK;
      H5_ELEMENT = addElement("H5", "B", var28);
      HTML.Element.Flow var29 = HTML.Element.Flow.BLOCK;
      H6_ELEMENT = addElement("H6", "B", var29);
      HEAD_ELEMENT = addElement("HEAD", "OB");
      HTML.Element.Flow var30 = HTML.Element.Flow.BLOCK;
      HR_ELEMENT = addElement("HR", "EB", var30);
      HTML_ELEMENT = addElement("HTML", "OB");
      HTML.Element.Flow var31 = HTML.Element.Flow.INLINE;
      I_ELEMENT = addElement("I", "", var31);
      IFRAME_ELEMENT = addElement("IFRAME", "");
      HTML.Element.Flow var32 = HTML.Element.Flow.INLINE;
      IMG_ELEMENT = addElement("IMG", "E", var32);
      HTML.Element.Flow var33 = HTML.Element.Flow.INLINE;
      INPUT_ELEMENT = addElement("INPUT", "E", var33);
      INS_ELEMENT = addElement("INS", "");
      ISINDEX_ELEMENT = addElement("ISINDEX", "EB");
      HTML.Element.Flow var34 = HTML.Element.Flow.INLINE;
      KBD_ELEMENT = addElement("KBD", "", var34);
      HTML.Element.Flow var35 = HTML.Element.Flow.INLINE;
      LABEL_ELEMENT = addElement("LABEL", "", var35);
      LEGEND_ELEMENT = addElement("LEGEND", "");
      LI_ELEMENT = addElement("LI", "OB");
      LINK_ELEMENT = addElement("LINK", "E");
      HTML.Element.Flow var36 = HTML.Element.Flow.INLINE;
      MAP_ELEMENT = addElement("MAP", "", var36);
      HTML.Element.Flow var37 = HTML.Element.Flow.BLOCK;
      MENU_ELEMENT = addElement("MENU", "B", var37);
      META_ELEMENT = addElement("META", "E");
      NOFRAMES_ELEMENT = addElement("NOFRAMES", "B");
      HTML.Element.Flow var38 = HTML.Element.Flow.BLOCK;
      NOSCRIPT_ELEMENT = addElement("NOSCRIPT", "", var38);
      HTML.Element.Flow var39 = HTML.Element.Flow.INLINE;
      OBJECT_ELEMENT = addElement("OBJECT", "", var39);
      HTML.Element.Flow var40 = HTML.Element.Flow.BLOCK;
      OL_ELEMENT = addElement("OL", "B", var40);
      OPTGROUP_ELEMENT = addElement("OPTGROUP", "");
      OPTION_ELEMENT = addElement("OPTION", "O");
      HTML.Element.Flow var41 = HTML.Element.Flow.BLOCK;
      P_ELEMENT = addElement("P", "OB", var41);
      PARAM_ELEMENT = addElement("PARAM", "E");
      HTML.Element.Flow var42 = HTML.Element.Flow.BLOCK;
      PRE_ELEMENT = addElement("PRE", "B", var42);
      HTML.Element.Flow var43 = HTML.Element.Flow.INLINE;
      Q_ELEMENT = addElement("Q", "", var43);
      HTML.Element.Flow var44 = HTML.Element.Flow.INLINE;
      S_ELEMENT = addElement("S", "", var44);
      HTML.Element.Flow var45 = HTML.Element.Flow.INLINE;
      SAMP_ELEMENT = addElement("SAMP", "", var45);
      HTML.Element.Flow var46 = HTML.Element.Flow.INLINE;
      SCRIPT_ELEMENT = addElement("SCRIPT", "", var46);
      HTML.Element.Flow var47 = HTML.Element.Flow.INLINE;
      SELECT_ELEMENT = addElement("SELECT", "", var47);
      HTML.Element.Flow var48 = HTML.Element.Flow.INLINE;
      SMALL_ELEMENT = addElement("SMALL", "", var48);
      HTML.Element.Flow var49 = HTML.Element.Flow.INLINE;
      SPAN_ELEMENT = addElement("SPAN", "", var49);
      HTML.Element.Flow var50 = HTML.Element.Flow.INLINE;
      STRIKE_ELEMENT = addElement("STRIKE", "", var50);
      HTML.Element.Flow var51 = HTML.Element.Flow.INLINE;
      STRONG_ELEMENT = addElement("STRONG", "", var51);
      STYLE_ELEMENT = addElement("STYLE", "");
      HTML.Element.Flow var52 = HTML.Element.Flow.INLINE;
      SUB_ELEMENT = addElement("SUB", "", var52);
      HTML.Element.Flow var53 = HTML.Element.Flow.INLINE;
      SUP_ELEMENT = addElement("SUP", "", var53);
      HTML.Element.Flow var54 = HTML.Element.Flow.BLOCK;
      TABLE_ELEMENT = addTableElement("TABLE", "B", var54);
      HTML.Element.Flow var55 = HTML.Element.Flow.NONE;
      TBODY_ELEMENT = addTableElement("TBODY", "O", var55);
      HTML.Element.Flow var56 = HTML.Element.Flow.NONE;
      TD_ELEMENT = addTableElement("TD", "OB", var56);
      HTML.Element.Flow var57 = HTML.Element.Flow.INLINE;
      TEXTAREA_ELEMENT = addElement("TEXTAREA", "", var57);
      HTML.Element.Flow var58 = HTML.Element.Flow.NONE;
      TFOOT_ELEMENT = addTableElement("TFOOT", "O", var58);
      HTML.Element.Flow var59 = HTML.Element.Flow.NONE;
      TH_ELEMENT = addTableElement("TH", "OB", var59);
      HTML.Element.Flow var60 = HTML.Element.Flow.NONE;
      THEAD_ELEMENT = addTableElement("THEAD", "O", var60);
      TITLE_ELEMENT = addElement("TITLE", "B");
      HTML.Element.Flow var61 = HTML.Element.Flow.NONE;
      TR_ELEMENT = addTableElement("TR", "OB", var61);
      HTML.Element.Flow var62 = HTML.Element.Flow.INLINE;
      TT_ELEMENT = addElement("TT", "", var62);
      HTML.Element.Flow var63 = HTML.Element.Flow.INLINE;
      U_ELEMENT = addElement("U", "", var63);
      HTML.Element.Flow var64 = HTML.Element.Flow.BLOCK;
      UL_ELEMENT = addElement("UL", "B", var64);
      HTML.Element.Flow var65 = HTML.Element.Flow.INLINE;
      VAR_ELEMENT = addElement("VAR", "", var65);
      ABBR_ATTRIBUTE = addAttribute("ABBR");
      ACCEPT_ATTRIBUTE = addAttribute("ACCEPT");
      ACCEPT_CHARSET_ATTRIBUTE = addAttribute("ACCEPT-CHARSET");
      ACCESSKEY_ATTRIBUTE = addAttribute("ACCESSKEY");
      ACTION_ATTRIBUTE = addAttribute("ACTION", 1);
      String[] var66 = new String[]{"left", "center", "right", "justify", "char", "top", "bottom", "middle"};
      ALIGN_ATTRIBUTE = addAttribute("ALIGN", 3, var66);
      ALINK_ATTRIBUTE = addAttribute("ALINK");
      ALT_ATTRIBUTE = addAttribute("ALT");
      ARCHIVE_ATTRIBUTE = addAttribute("ARCHIVE", 1);
      AXIS_ATTRIBUTE = addAttribute("AXIS");
      BACKGROUND_ATTRIBUTE = addAttribute("BACKGROUND", 1);
      BGCOLOR_ATTRIBUTE = addAttribute("BGCOLOR");
      BORDER_ATTRIBUTE = addAttribute("BORDER");
      CELLPADDING_ATTRIBUTE = addAttribute("CELLPADDING");
      CELLSPACING_ATTRIBUTE = addAttribute("CELLSPACING");
      CHAR_ATTRIBUTE = addAttribute("CHAR");
      CHAROFF_ATTRIBUTE = addAttribute("CHAROFF");
      CHARSET_ATTRIBUTE = addAttribute("CHARSET");
      CHECKED_ATTRIBUTE = addAttribute("CHECKED", 4);
      CITE_ATTRIBUTE = addAttribute("CITE", 1);
      CLASS_ATTRIBUTE = addAttribute("CLASS");
      CLASSID_ATTRIBUTE = addAttribute("CLASSID", 1);
      String[] var67 = new String[]{"left", "all", "right", "none"};
      CLEAR_ATTRIBUTE = addAttribute("CLEAR", 3, var67);
      CODE_ATTRIBUTE = addAttribute("CODE");
      CODEBASE_ATTRIBUTE = addAttribute("CODEBASE", 1);
      CODETYPE_ATTRIBUTE = addAttribute("CODETYPE");
      COLOR_ATTRIBUTE = addAttribute("COLOR");
      COLS_ATTRIBUTE = addAttribute("COLS");
      COLSPAN_ATTRIBUTE = addAttribute("COLSPAN");
      COMPACT_ATTRIBUTE = addAttribute("COMPACT", 4);
      CONTENT_ATTRIBUTE = addAttribute("CONTENT");
      COORDS_ATTRIBUTE = addAttribute("COORDS");
      DATA_ATTRIBUTE = addAttribute("DATA", 1);
      DATETIME_ATTRIBUTE = addAttribute("DATETIME");
      DECLARE_ATTRIBUTE = addAttribute("DECLARE", 4);
      DEFER_ATTRIBUTE = addAttribute("DEFER", 4);
      String[] var68 = new String[]{"ltr", "rtl"};
      DIR_ATTRIBUTE = addAttribute("DIR", 3, var68);
      DISABLED_ATTRIBUTE = addAttribute("DISABLED", 4);
      ENCTYPE_ATTRIBUTE = addAttribute("ENCTYPE");
      FACE_ATTRIBUTE = addAttribute("FACE");
      FOR_ATTRIBUTE = addAttribute("FOR");
      FRAME_ATTRIBUTE = addAttribute("FRAME");
      String[] var69 = new String[]{"1", "0"};
      FRAMEBORDER_ATTRIBUTE = addAttribute("FRAMEBORDER", 3, var69);
      HEADERS_ATTRIBUTE = addAttribute("HEADERS");
      HEIGHT_ATTRIBUTE = addAttribute("HEIGHT");
      HREF_ATTRIBUTE = addAttribute("HREF", 1);
      HREFLANG_ATTRIBUTE = addAttribute("HREFLANG");
      HSPACE_ATTRIBUTE = addAttribute("HSPACE");
      HTTP_EQUIV_ATTRIBUTE = addAttribute("HTTP-EQUIV");
      ID_ATTRIBUTE = addAttribute("ID");
      ISMAP_ATTRIBUTE = addAttribute("ISMAP", 4);
      LABEL_ATTRIBUTE = addAttribute("LABEL");
      LANG_ATTRIBUTE = addAttribute("LANG");
      LANGUAGE_ATTRIBUTE = addAttribute("LANGUAGE");
      LINK_ATTRIBUTE = addAttribute("LINK");
      LONGDESC_ATTRIBUTE = addAttribute("LONGDESC", 1);
      MARGINHEIGHT_ATTRIBUTE = addAttribute("MARGINHEIGHT");
      MARGINWIDTH_ATTRIBUTE = addAttribute("MARGINWIDTH");
      MAXLENGTH_ATTRIBUTE = addAttribute("MAXLENGTH");
      MEDIA_ATTRIBUTE = addAttribute("MEDIA");
      String[] var70 = new String[]{"get", "post"};
      METHOD_ATTRIBUTE = addAttribute("METHOD", 3, var70);
      MULTIPLE_ATTRIBUTE = addAttribute("MULTIPLE", 4);
      NAME_ATTRIBUTE = addAttribute("NAME");
      NOHREF_ATTRIBUTE = addAttribute("NOHREF", 4);
      NORESIZE_ATTRIBUTE = addAttribute("NORESIZE", 4);
      NOSHADE_ATTRIBUTE = addAttribute("NOSHADE", 4);
      NOWRAP_ATTRIBUTE = addAttribute("NOWRAP", 4);
      OBJECT_ATTRIBUTE = addAttribute("OBJECT");
      ONBLUR_ATTRIBUTE = addAttribute("ONBLUR", 2);
      ONCHANGE_ATTRIBUTE = addAttribute("ONCHANGE", 2);
      ONCLICK_ATTRIBUTE = addAttribute("ONCLICK", 2);
      ONDBLCLICK_ATTRIBUTE = addAttribute("ONDBLCLICK", 2);
      ONFOCUS_ATTRIBUTE = addAttribute("ONFOCUS", 2);
      ONKEYDOWN_ATTRIBUTE = addAttribute("ONKEYDOWN", 2);
      ONKEYPRESS_ATTRIBUTE = addAttribute("ONKEYPRESS", 2);
      ONKEYUP_ATTRIBUTE = addAttribute("ONKEYUP", 2);
      ONLOAD_ATTRIBUTE = addAttribute("ONLOAD", 2);
      ONMOUSEDOWN_ATTRIBUTE = addAttribute("ONMOUSEDOWN", 2);
      ONMOUSEMOVE_ATTRIBUTE = addAttribute("ONMOUSEMOVE", 2);
      ONMOUSEOUT_ATTRIBUTE = addAttribute("ONMOUSEOUT", 2);
      ONMOUSEOVER_ATTRIBUTE = addAttribute("ONMOUSEOVER", 2);
      ONMOUSEUP_ATTRIBUTE = addAttribute("ONMOUSEUP", 2);
      ONRESET_ATTRIBUTE = addAttribute("ONRESET", 2);
      ONSELECT_ATTRIBUTE = addAttribute("ONSELECT", 2);
      ONSUBMIT_ATTRIBUTE = addAttribute("ONSUBMIT", 2);
      ONUNLOAD_ATTRIBUTE = addAttribute("ONUNLOAD", 2);
      PROFILE_ATTRIBUTE = addAttribute("PROFILE", 1);
      PROMPT_ATTRIBUTE = addAttribute("PROMPT");
      READONLY_ATTRIBUTE = addAttribute("READONLY", 4);
      REL_ATTRIBUTE = addAttribute("REL");
      REV_ATTRIBUTE = addAttribute("REV");
      ROWS_ATTRIBUTE = addAttribute("ROWS");
      ROWSPAN_ATTRIBUTE = addAttribute("ROWSPAN");
      RULES_ATTRIBUTE = addAttribute("RULES");
      SCHEME_ATTRIBUTE = addAttribute("SCHEME");
      SCOPE_ATTRIBUTE = addAttribute("SCOPE");
      String[] var71 = new String[]{"yes", "no", "auto"};
      SCROLLING_ATTRIBUTE = addAttribute("SCROLLING", 3, var71);
      SELECTED_ATTRIBUTE = addAttribute("SELECTED", 4);
      SHAPE_ATTRIBUTE = addAttribute("SHAPE");
      SIZE_ATTRIBUTE = addAttribute("SIZE");
      SPAN_ATTRIBUTE = addAttribute("SPAN");
      SRC_ATTRIBUTE = addAttribute("SRC", 1);
      STANDBY_ATTRIBUTE = addAttribute("STANDBY");
      START_ATTRIBUTE = addAttribute("START");
      STYLE_ATTRIBUTE = addAttribute("STYLE");
      SUMMARY_ATTRIBUTE = addAttribute("SUMMARY");
      TABINDEX_ATTRIBUTE = addAttribute("TABINDEX");
      TARGET_ATTRIBUTE = addAttribute("TARGET");
      TEXT_ATTRIBUTE = addAttribute("TEXT");
      TITLE_ATTRIBUTE = addAttribute("TITLE");
      TYPE_ATTRIBUTE = addAttribute("TYPE");
      USEMAP_ATTRIBUTE = addAttribute("USEMAP", 1);
      String[] var72 = new String[]{"top", "middle", "bottom", "baseline"};
      VALIGN_ATTRIBUTE = addAttribute("VALIGN", 3, var72);
      VALUE_ATTRIBUTE = addAttribute("VALUE");
      String[] var73 = new String[]{"data", "ref", "object"};
      VALUETYPE_ATTRIBUTE = addAttribute("VALUETYPE", 3, var73);
      VERSION_ATTRIBUTE = addAttribute("VERSION");
      VLINK_ATTRIBUTE = addAttribute("VLINK");
      VSPACE_ATTRIBUTE = addAttribute("VSPACE");
      WIDTH_ATTRIBUTE = addAttribute("WIDTH");
   }

   public HTML4() {}

   private static HTML.Attribute addAttribute(String var0) {
      return addAttribute(var0, 0);
   }

   private static HTML.Attribute addAttribute(String var0, int var1) {
      return addAttribute(var0, var1, (String[])null);
   }

   private static HTML.Attribute addAttribute(String var0, int var1, String[] var2) {
      String var3 = var0.toLowerCase();
      Set var4 = null;
      if(var2 != null) {
         HashSet var5 = new HashSet();
         String[] var6 = var2;
         int var7 = var2.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            String var9 = var6[var8].toLowerCase();
            var5.add(var9);
         }

         var4 = Collections.unmodifiableSet(var5);
      }

      HTML.Attribute var11 = new HTML.Attribute(var3, var1, var4);
      attributes.put(var3, var11);
      return var11;
   }

   private static HTML.Element addElement(String var0, String var1) {
      HTML.Element.Flow var2 = HTML.Element.Flow.NONE;
      return addElement(var0, var1, var2);
   }

   private static HTML.Element addElement(String var0, String var1, HTML.Element.Flow var2) {
      return addElement(var0, var1, var2, 0);
   }

   private static HTML.Element addElement(String var0, String var1, HTML.Element.Flow var2, int var3) {
      String var4 = var0.toLowerCase();
      byte var5 = 0;
      byte var6 = 0;
      byte var7 = 0;
      int var8 = 0;

      while(true) {
         int var9 = var1.length();
         if(var8 >= var9) {
            HTML.Element var13 = new HTML.Element(var4, var3, (boolean)var5, (boolean)var6, (boolean)var7, var2);
            elements.put(var4, var13);
            return var13;
         }

         switch(var1.charAt(var8)) {
         case 66:
            var7 = 1;
            break;
         case 69:
            var5 = 1;
            break;
         case 79:
            var6 = 1;
            break;
         default:
            throw new Error("Unknown element flag");
         }

         ++var8;
      }
   }

   private static HTML.Element addTableElement(String var0, String var1, HTML.Element.Flow var2) {
      return addElement(var0, var1, var2, 1);
   }

   public static Map<String, HTML.Attribute> getAllAttributes() {
      return Collections.unmodifiableMap(attributes);
   }

   public static Map<String, HTML.Element> getAllElements() {
      return Collections.unmodifiableMap(elements);
   }

   public static HtmlWhitelist getWhitelist() {
      return defaultWhitelist;
   }

   public static HTML.Attribute lookupAttribute(String var0) {
      HashMap var1 = attributes;
      String var2 = var0.toLowerCase();
      return (HTML.Attribute)var1.get(var2);
   }

   public static HTML.Element lookupElement(String var0) {
      HashMap var1 = elements;
      String var2 = var0.toLowerCase();
      return (HTML.Element)var1.get(var2);
   }

   static class 1 implements HtmlWhitelist {

      1() {}

      public HTML.Attribute lookupAttribute(String var1) {
         return HTML4.lookupAttribute(var1);
      }

      public HTML.Element lookupElement(String var1) {
         return HTML4.lookupElement(var1);
      }
   }
}
