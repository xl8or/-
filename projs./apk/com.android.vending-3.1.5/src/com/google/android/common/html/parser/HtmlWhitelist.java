package com.google.android.common.html.parser;

import com.google.android.common.html.parser.HTML;

public interface HtmlWhitelist {

   HTML.Attribute lookupAttribute(String var1);

   HTML.Element lookupElement(String var1);
}
