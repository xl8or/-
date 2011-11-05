package org.apache.commons.httpclient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Hashtable;
import java.util.Locale;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.util.EncodingUtil;

public class URI implements Cloneable, Comparable, Serializable {

   protected static final BitSet IPv4address;
   protected static final BitSet IPv6address;
   protected static final BitSet IPv6reference;
   protected static final BitSet URI_reference;
   protected static final BitSet abs_path;
   protected static final BitSet absoluteURI;
   public static final BitSet allowed_IPv6reference;
   public static final BitSet allowed_abs_path;
   public static final BitSet allowed_authority;
   public static final BitSet allowed_fragment;
   public static final BitSet allowed_host;
   public static final BitSet allowed_opaque_part;
   public static final BitSet allowed_query;
   public static final BitSet allowed_reg_name;
   public static final BitSet allowed_rel_path;
   public static final BitSet allowed_userinfo;
   public static final BitSet allowed_within_authority;
   public static final BitSet allowed_within_path;
   public static final BitSet allowed_within_query;
   public static final BitSet allowed_within_userinfo;
   protected static final BitSet alpha;
   protected static final BitSet alphanum;
   protected static final BitSet authority;
   public static final BitSet control;
   protected static String defaultDocumentCharset = null;
   protected static String defaultDocumentCharsetByLocale = null;
   protected static String defaultDocumentCharsetByPlatform = null;
   protected static String defaultProtocolCharset = "UTF-8";
   public static final BitSet delims;
   protected static final BitSet digit;
   public static final BitSet disallowed_opaque_part;
   public static final BitSet disallowed_rel_path;
   protected static final BitSet domainlabel;
   protected static final BitSet escaped;
   protected static final BitSet fragment;
   protected static final BitSet hex;
   protected static final BitSet hier_part;
   protected static final BitSet host;
   protected static final BitSet hostname;
   protected static final BitSet hostport;
   protected static final BitSet mark;
   protected static final BitSet net_path;
   protected static final BitSet opaque_part;
   protected static final BitSet param;
   protected static final BitSet path;
   protected static final BitSet path_segments;
   protected static final BitSet pchar;
   protected static final BitSet percent;
   protected static final BitSet port;
   protected static final BitSet query;
   protected static final BitSet reg_name;
   protected static final BitSet rel_path;
   protected static final BitSet rel_segment;
   protected static final BitSet relativeURI;
   protected static final BitSet reserved;
   protected static final char[] rootPath;
   protected static final BitSet scheme;
   protected static final BitSet segment;
   static final long serialVersionUID = 604752400577948726L;
   protected static final BitSet server;
   public static final BitSet space;
   protected static final BitSet toplabel;
   protected static final BitSet unreserved;
   public static final BitSet unwise;
   protected static final BitSet uric;
   protected static final BitSet uric_no_slash;
   protected static final BitSet userinfo;
   public static final BitSet within_userinfo;
   protected char[] _authority;
   protected char[] _fragment;
   protected char[] _host;
   protected boolean _is_IPv4address;
   protected boolean _is_IPv6reference;
   protected boolean _is_abs_path;
   protected boolean _is_hier_part;
   protected boolean _is_hostname;
   protected boolean _is_net_path;
   protected boolean _is_opaque_part;
   protected boolean _is_reg_name;
   protected boolean _is_rel_path;
   protected boolean _is_server;
   protected char[] _opaque;
   protected char[] _path;
   protected int _port;
   protected char[] _query;
   protected char[] _scheme;
   protected char[] _uri;
   protected char[] _userinfo;
   protected int hash;
   protected String protocolCharset;


   static {
      Locale var0 = Locale.getDefault();
      if(var0 != null) {
         defaultDocumentCharsetByLocale = URI.LocaleToCharsetMap.getCharset(var0);
         defaultDocumentCharset = defaultDocumentCharsetByLocale;
      }

      try {
         defaultDocumentCharsetByPlatform = System.getProperty("file.encoding");
      } catch (SecurityException var177) {
         ;
      }

      if(defaultDocumentCharset == null) {
         defaultDocumentCharset = defaultDocumentCharsetByPlatform;
      }

      char[] var1 = new char[]{'/'};
      rootPath = var1;
      percent = new BitSet(256);
      percent.set(37);
      digit = new BitSet(256);

      for(int var2 = 48; var2 <= 57; ++var2) {
         digit.set(var2);
      }

      alpha = new BitSet(256);

      for(int var3 = 97; var3 <= 122; ++var3) {
         alpha.set(var3);
      }

      for(int var4 = 65; var4 <= 90; ++var4) {
         alpha.set(var4);
      }

      alphanum = new BitSet(256);
      BitSet var5 = alphanum;
      BitSet var6 = alpha;
      var5.or(var6);
      BitSet var7 = alphanum;
      BitSet var8 = digit;
      var7.or(var8);
      hex = new BitSet(256);
      BitSet var9 = hex;
      BitSet var10 = digit;
      var9.or(var10);

      for(int var11 = 97; var11 <= 102; ++var11) {
         hex.set(var11);
      }

      for(int var12 = 65; var12 <= 70; ++var12) {
         hex.set(var12);
      }

      escaped = new BitSet(256);
      BitSet var13 = escaped;
      BitSet var14 = percent;
      var13.or(var14);
      BitSet var15 = escaped;
      BitSet var16 = hex;
      var15.or(var16);
      mark = new BitSet(256);
      mark.set(45);
      mark.set(95);
      mark.set(46);
      mark.set(33);
      mark.set(126);
      mark.set(42);
      mark.set(39);
      mark.set(40);
      mark.set(41);
      unreserved = new BitSet(256);
      BitSet var17 = unreserved;
      BitSet var18 = alphanum;
      var17.or(var18);
      BitSet var19 = unreserved;
      BitSet var20 = mark;
      var19.or(var20);
      reserved = new BitSet(256);
      reserved.set(59);
      reserved.set(47);
      reserved.set(63);
      reserved.set(58);
      reserved.set(64);
      reserved.set(38);
      reserved.set(61);
      reserved.set(43);
      reserved.set(36);
      reserved.set(44);
      uric = new BitSet(256);
      BitSet var21 = uric;
      BitSet var22 = reserved;
      var21.or(var22);
      BitSet var23 = uric;
      BitSet var24 = unreserved;
      var23.or(var24);
      BitSet var25 = uric;
      BitSet var26 = escaped;
      var25.or(var26);
      fragment = uric;
      query = uric;
      pchar = new BitSet(256);
      BitSet var27 = pchar;
      BitSet var28 = unreserved;
      var27.or(var28);
      BitSet var29 = pchar;
      BitSet var30 = escaped;
      var29.or(var30);
      pchar.set(58);
      pchar.set(64);
      pchar.set(38);
      pchar.set(61);
      pchar.set(43);
      pchar.set(36);
      pchar.set(44);
      param = pchar;
      segment = new BitSet(256);
      BitSet var31 = segment;
      BitSet var32 = pchar;
      var31.or(var32);
      segment.set(59);
      BitSet var33 = segment;
      BitSet var34 = param;
      var33.or(var34);
      path_segments = new BitSet(256);
      path_segments.set(47);
      BitSet var35 = path_segments;
      BitSet var36 = segment;
      var35.or(var36);
      abs_path = new BitSet(256);
      abs_path.set(47);
      BitSet var37 = abs_path;
      BitSet var38 = path_segments;
      var37.or(var38);
      uric_no_slash = new BitSet(256);
      BitSet var39 = uric_no_slash;
      BitSet var40 = unreserved;
      var39.or(var40);
      BitSet var41 = uric_no_slash;
      BitSet var42 = escaped;
      var41.or(var42);
      uric_no_slash.set(59);
      uric_no_slash.set(63);
      uric_no_slash.set(59);
      uric_no_slash.set(64);
      uric_no_slash.set(38);
      uric_no_slash.set(61);
      uric_no_slash.set(43);
      uric_no_slash.set(36);
      uric_no_slash.set(44);
      opaque_part = new BitSet(256);
      BitSet var43 = opaque_part;
      BitSet var44 = uric_no_slash;
      var43.or(var44);
      BitSet var45 = opaque_part;
      BitSet var46 = uric;
      var45.or(var46);
      path = new BitSet(256);
      BitSet var47 = path;
      BitSet var48 = abs_path;
      var47.or(var48);
      BitSet var49 = path;
      BitSet var50 = opaque_part;
      var49.or(var50);
      port = digit;
      IPv4address = new BitSet(256);
      BitSet var51 = IPv4address;
      BitSet var52 = digit;
      var51.or(var52);
      IPv4address.set(46);
      IPv6address = new BitSet(256);
      BitSet var53 = IPv6address;
      BitSet var54 = hex;
      var53.or(var54);
      IPv6address.set(58);
      BitSet var55 = IPv6address;
      BitSet var56 = IPv4address;
      var55.or(var56);
      IPv6reference = new BitSet(256);
      IPv6reference.set(91);
      BitSet var57 = IPv6reference;
      BitSet var58 = IPv6address;
      var57.or(var58);
      IPv6reference.set(93);
      toplabel = new BitSet(256);
      BitSet var59 = toplabel;
      BitSet var60 = alphanum;
      var59.or(var60);
      toplabel.set(45);
      domainlabel = toplabel;
      hostname = new BitSet(256);
      BitSet var61 = hostname;
      BitSet var62 = toplabel;
      var61.or(var62);
      hostname.set(46);
      host = new BitSet(256);
      BitSet var63 = host;
      BitSet var64 = hostname;
      var63.or(var64);
      BitSet var65 = host;
      BitSet var66 = IPv6reference;
      var65.or(var66);
      hostport = new BitSet(256);
      BitSet var67 = hostport;
      BitSet var68 = host;
      var67.or(var68);
      hostport.set(58);
      BitSet var69 = hostport;
      BitSet var70 = port;
      var69.or(var70);
      userinfo = new BitSet(256);
      BitSet var71 = userinfo;
      BitSet var72 = unreserved;
      var71.or(var72);
      BitSet var73 = userinfo;
      BitSet var74 = escaped;
      var73.or(var74);
      userinfo.set(59);
      userinfo.set(58);
      userinfo.set(38);
      userinfo.set(61);
      userinfo.set(43);
      userinfo.set(36);
      userinfo.set(44);
      within_userinfo = new BitSet(256);
      BitSet var75 = within_userinfo;
      BitSet var76 = userinfo;
      var75.or(var76);
      within_userinfo.clear(59);
      within_userinfo.clear(58);
      within_userinfo.clear(64);
      within_userinfo.clear(63);
      within_userinfo.clear(47);
      server = new BitSet(256);
      BitSet var77 = server;
      BitSet var78 = userinfo;
      var77.or(var78);
      server.set(64);
      BitSet var79 = server;
      BitSet var80 = hostport;
      var79.or(var80);
      reg_name = new BitSet(256);
      BitSet var81 = reg_name;
      BitSet var82 = unreserved;
      var81.or(var82);
      BitSet var83 = reg_name;
      BitSet var84 = escaped;
      var83.or(var84);
      reg_name.set(36);
      reg_name.set(44);
      reg_name.set(59);
      reg_name.set(58);
      reg_name.set(64);
      reg_name.set(38);
      reg_name.set(61);
      reg_name.set(43);
      authority = new BitSet(256);
      BitSet var85 = authority;
      BitSet var86 = server;
      var85.or(var86);
      BitSet var87 = authority;
      BitSet var88 = reg_name;
      var87.or(var88);
      scheme = new BitSet(256);
      BitSet var89 = scheme;
      BitSet var90 = alpha;
      var89.or(var90);
      BitSet var91 = scheme;
      BitSet var92 = digit;
      var91.or(var92);
      scheme.set(43);
      scheme.set(45);
      scheme.set(46);
      rel_segment = new BitSet(256);
      BitSet var93 = rel_segment;
      BitSet var94 = unreserved;
      var93.or(var94);
      BitSet var95 = rel_segment;
      BitSet var96 = escaped;
      var95.or(var96);
      rel_segment.set(59);
      rel_segment.set(64);
      rel_segment.set(38);
      rel_segment.set(61);
      rel_segment.set(43);
      rel_segment.set(36);
      rel_segment.set(44);
      rel_path = new BitSet(256);
      BitSet var97 = rel_path;
      BitSet var98 = rel_segment;
      var97.or(var98);
      BitSet var99 = rel_path;
      BitSet var100 = abs_path;
      var99.or(var100);
      net_path = new BitSet(256);
      net_path.set(47);
      BitSet var101 = net_path;
      BitSet var102 = authority;
      var101.or(var102);
      BitSet var103 = net_path;
      BitSet var104 = abs_path;
      var103.or(var104);
      hier_part = new BitSet(256);
      BitSet var105 = hier_part;
      BitSet var106 = net_path;
      var105.or(var106);
      BitSet var107 = hier_part;
      BitSet var108 = abs_path;
      var107.or(var108);
      BitSet var109 = hier_part;
      BitSet var110 = query;
      var109.or(var110);
      relativeURI = new BitSet(256);
      BitSet var111 = relativeURI;
      BitSet var112 = net_path;
      var111.or(var112);
      BitSet var113 = relativeURI;
      BitSet var114 = abs_path;
      var113.or(var114);
      BitSet var115 = relativeURI;
      BitSet var116 = rel_path;
      var115.or(var116);
      BitSet var117 = relativeURI;
      BitSet var118 = query;
      var117.or(var118);
      absoluteURI = new BitSet(256);
      BitSet var119 = absoluteURI;
      BitSet var120 = scheme;
      var119.or(var120);
      absoluteURI.set(58);
      BitSet var121 = absoluteURI;
      BitSet var122 = hier_part;
      var121.or(var122);
      BitSet var123 = absoluteURI;
      BitSet var124 = opaque_part;
      var123.or(var124);
      URI_reference = new BitSet(256);
      BitSet var125 = URI_reference;
      BitSet var126 = absoluteURI;
      var125.or(var126);
      BitSet var127 = URI_reference;
      BitSet var128 = relativeURI;
      var127.or(var128);
      URI_reference.set(35);
      BitSet var129 = URI_reference;
      BitSet var130 = fragment;
      var129.or(var130);
      control = new BitSet(256);

      for(int var131 = 0; var131 <= 31; ++var131) {
         control.set(var131);
      }

      control.set(127);
      space = new BitSet(256);
      space.set(32);
      delims = new BitSet(256);
      delims.set(60);
      delims.set(62);
      delims.set(35);
      delims.set(37);
      delims.set(34);
      unwise = new BitSet(256);
      unwise.set(123);
      unwise.set(125);
      unwise.set(124);
      unwise.set(92);
      unwise.set(94);
      unwise.set(91);
      unwise.set(93);
      unwise.set(96);
      disallowed_rel_path = new BitSet(256);
      BitSet var132 = disallowed_rel_path;
      BitSet var133 = uric;
      var132.or(var133);
      BitSet var134 = disallowed_rel_path;
      BitSet var135 = rel_path;
      var134.andNot(var135);
      disallowed_opaque_part = new BitSet(256);
      BitSet var136 = disallowed_opaque_part;
      BitSet var137 = uric;
      var136.or(var137);
      BitSet var138 = disallowed_opaque_part;
      BitSet var139 = opaque_part;
      var138.andNot(var139);
      allowed_authority = new BitSet(256);
      BitSet var140 = allowed_authority;
      BitSet var141 = authority;
      var140.or(var141);
      allowed_authority.clear(37);
      allowed_opaque_part = new BitSet(256);
      BitSet var142 = allowed_opaque_part;
      BitSet var143 = opaque_part;
      var142.or(var143);
      allowed_opaque_part.clear(37);
      allowed_reg_name = new BitSet(256);
      BitSet var144 = allowed_reg_name;
      BitSet var145 = reg_name;
      var144.or(var145);
      allowed_reg_name.clear(37);
      allowed_userinfo = new BitSet(256);
      BitSet var146 = allowed_userinfo;
      BitSet var147 = userinfo;
      var146.or(var147);
      allowed_userinfo.clear(37);
      allowed_within_userinfo = new BitSet(256);
      BitSet var148 = allowed_within_userinfo;
      BitSet var149 = within_userinfo;
      var148.or(var149);
      allowed_within_userinfo.clear(37);
      allowed_IPv6reference = new BitSet(256);
      BitSet var150 = allowed_IPv6reference;
      BitSet var151 = IPv6reference;
      var150.or(var151);
      allowed_IPv6reference.clear(91);
      allowed_IPv6reference.clear(93);
      allowed_host = new BitSet(256);
      BitSet var152 = allowed_host;
      BitSet var153 = hostname;
      var152.or(var153);
      BitSet var154 = allowed_host;
      BitSet var155 = allowed_IPv6reference;
      var154.or(var155);
      allowed_within_authority = new BitSet(256);
      BitSet var156 = allowed_within_authority;
      BitSet var157 = server;
      var156.or(var157);
      BitSet var158 = allowed_within_authority;
      BitSet var159 = reg_name;
      var158.or(var159);
      allowed_within_authority.clear(59);
      allowed_within_authority.clear(58);
      allowed_within_authority.clear(64);
      allowed_within_authority.clear(63);
      allowed_within_authority.clear(47);
      allowed_abs_path = new BitSet(256);
      BitSet var160 = allowed_abs_path;
      BitSet var161 = abs_path;
      var160.or(var161);
      BitSet var162 = allowed_abs_path;
      BitSet var163 = percent;
      var162.andNot(var163);
      allowed_abs_path.clear(43);
      allowed_rel_path = new BitSet(256);
      BitSet var164 = allowed_rel_path;
      BitSet var165 = rel_path;
      var164.or(var165);
      allowed_rel_path.clear(37);
      allowed_rel_path.clear(43);
      allowed_within_path = new BitSet(256);
      BitSet var166 = allowed_within_path;
      BitSet var167 = abs_path;
      var166.or(var167);
      allowed_within_path.clear(47);
      allowed_within_path.clear(59);
      allowed_within_path.clear(61);
      allowed_within_path.clear(63);
      allowed_query = new BitSet(256);
      BitSet var168 = allowed_query;
      BitSet var169 = uric;
      var168.or(var169);
      allowed_query.clear(37);
      allowed_within_query = new BitSet(256);
      BitSet var170 = allowed_within_query;
      BitSet var171 = allowed_query;
      var170.or(var171);
      BitSet var172 = allowed_within_query;
      BitSet var173 = reserved;
      var172.andNot(var173);
      allowed_fragment = new BitSet(256);
      BitSet var174 = allowed_fragment;
      BitSet var175 = uric;
      var174.or(var175);
      allowed_fragment.clear(37);
   }

   protected URI() {
      this.hash = 0;
      this._uri = null;
      this.protocolCharset = null;
      this._scheme = null;
      this._opaque = null;
      this._authority = null;
      this._userinfo = null;
      this._host = null;
      this._port = -1;
      this._path = null;
      this._query = null;
      this._fragment = null;
   }

   public URI(String var1) throws URIException {
      this.hash = 0;
      this._uri = null;
      this.protocolCharset = null;
      this._scheme = null;
      this._opaque = null;
      this._authority = null;
      this._userinfo = null;
      this._host = null;
      this._port = -1;
      this._path = null;
      this._query = null;
      this._fragment = null;
      this.parseUriReference(var1, (boolean)0);
   }

   public URI(String var1, String var2) throws URIException {
      this.hash = 0;
      this._uri = null;
      this.protocolCharset = null;
      this._scheme = null;
      this._opaque = null;
      this._authority = null;
      this._userinfo = null;
      this._host = null;
      this._port = -1;
      this._path = null;
      this._query = null;
      this._fragment = null;
      this.protocolCharset = var2;
      this.parseUriReference(var1, (boolean)0);
   }

   public URI(String var1, String var2, String var3) throws URIException {
      this.hash = 0;
      this._uri = null;
      this.protocolCharset = null;
      this._scheme = null;
      this._opaque = null;
      this._authority = null;
      this._userinfo = null;
      this._host = null;
      this._port = -1;
      this._path = null;
      this._query = null;
      this._fragment = null;
      if(var1 == null) {
         throw new URIException(1, "scheme required");
      } else {
         char[] var4 = var1.toLowerCase().toCharArray();
         BitSet var5 = scheme;
         if(this.validate(var4, var5)) {
            this._scheme = var4;
            BitSet var6 = allowed_opaque_part;
            String var7 = this.getProtocolCharset();
            char[] var8 = encode(var2, var6, var7);
            this._opaque = var8;
            this._is_opaque_part = (boolean)1;
            char[] var9;
            if(var3 == null) {
               var9 = null;
            } else {
               var9 = var3.toCharArray();
            }

            this._fragment = var9;
            this.setURI();
         } else {
            throw new URIException(1, "incorrect scheme");
         }
      }
   }

   public URI(String var1, String var2, String var3, int var4) throws URIException {
      Object var10 = null;
      Object var11 = null;
      this(var1, var2, var3, var4, (String)null, (String)var10, (String)var11);
   }

   public URI(String var1, String var2, String var3, int var4, String var5) throws URIException {
      Object var12 = null;
      this(var1, var2, var3, var4, var5, (String)null, (String)var12);
   }

   public URI(String var1, String var2, String var3, int var4, String var5, String var6) throws URIException {
      this(var1, var2, var3, var4, var5, var6, (String)null);
   }

   public URI(String var1, String var2, String var3, int var4, String var5, String var6, String var7) throws URIException {
      String var8;
      if(var3 == null) {
         var8 = null;
      } else {
         StringBuilder var14 = new StringBuilder();
         String var15;
         if(var2 != null) {
            var15 = var2 + '@';
         } else {
            var15 = "";
         }

         StringBuilder var16 = var14.append(var15).append(var3);
         String var17;
         if(var4 != -1) {
            var17 = ":" + var4;
         } else {
            var17 = "";
         }

         var8 = var16.append(var17).toString();
      }

      this(var1, var8, var5, var6, var7);
   }

   public URI(String var1, String var2, String var3, String var4) throws URIException {
      this(var1, var2, var3, (String)null, var4);
   }

   public URI(String var1, String var2, String var3, String var4, String var5) throws URIException {
      this.hash = 0;
      this._uri = null;
      this.protocolCharset = null;
      this._scheme = null;
      this._opaque = null;
      this._authority = null;
      this._userinfo = null;
      this._host = null;
      this._port = -1;
      this._path = null;
      this._query = null;
      this._fragment = null;
      StringBuffer var6 = new StringBuffer();
      if(var1 != null) {
         var6.append(var1);
         StringBuffer var8 = var6.append(':');
      }

      if(var2 != null) {
         StringBuffer var9 = var6.append("//");
         var6.append(var2);
      }

      if(var3 != null) {
         if((var1 != null || var2 != null) && !var3.startsWith("/")) {
            throw new URIException(1, "abs_path requested");
         }

         var6.append(var3);
      }

      if(var4 != null) {
         StringBuffer var12 = var6.append('?');
         var6.append(var4);
      }

      if(var5 != null) {
         StringBuffer var14 = var6.append('#');
         var6.append(var5);
      }

      String var16 = var6.toString();
      this.parseUriReference(var16, (boolean)0);
   }

   public URI(String var1, boolean var2) throws URIException, NullPointerException {
      this.hash = 0;
      this._uri = null;
      this.protocolCharset = null;
      this._scheme = null;
      this._opaque = null;
      this._authority = null;
      this._userinfo = null;
      this._host = null;
      this._port = -1;
      this._path = null;
      this._query = null;
      this._fragment = null;
      this.parseUriReference(var1, var2);
   }

   public URI(String var1, boolean var2, String var3) throws URIException, NullPointerException {
      this.hash = 0;
      this._uri = null;
      this.protocolCharset = null;
      this._scheme = null;
      this._opaque = null;
      this._authority = null;
      this._userinfo = null;
      this._host = null;
      this._port = -1;
      this._path = null;
      this._query = null;
      this._fragment = null;
      this.protocolCharset = var3;
      this.parseUriReference(var1, var2);
   }

   public URI(URI var1, String var2) throws URIException {
      URI var3 = new URI(var2);
      this(var1, var3);
   }

   public URI(URI var1, String var2, boolean var3) throws URIException {
      URI var4 = new URI(var2, var3);
      this(var1, var4);
   }

   public URI(URI var1, URI var2) throws URIException {
      this.hash = 0;
      this._uri = null;
      this.protocolCharset = null;
      this._scheme = null;
      this._opaque = null;
      this._authority = null;
      this._userinfo = null;
      this._host = null;
      this._port = -1;
      this._path = null;
      this._query = null;
      this._fragment = null;
      if(var1._scheme == null) {
         throw new URIException(1, "base URI required");
      } else {
         if(var1._scheme != null) {
            char[] var3 = var1._scheme;
            this._scheme = var3;
            char[] var4 = var1._authority;
            this._authority = var4;
            boolean var5 = var1._is_net_path;
            this._is_net_path = var5;
         }

         if(!var1._is_opaque_part && !var2._is_opaque_part) {
            char[] var10 = var1._scheme;
            char[] var11 = var2._scheme;
            boolean var12 = Arrays.equals(var10, var11);
            if(var2._scheme != null && (!var12 || var2._authority != null)) {
               char[] var13 = var2._scheme;
               this._scheme = var13;
               boolean var14 = var2._is_net_path;
               this._is_net_path = var14;
               char[] var15 = var2._authority;
               this._authority = var15;
               if(var2._is_server) {
                  boolean var16 = var2._is_server;
                  this._is_server = var16;
                  char[] var17 = var2._userinfo;
                  this._userinfo = var17;
                  char[] var18 = var2._host;
                  this._host = var18;
                  int var19 = var2._port;
                  this._port = var19;
               } else if(var2._is_reg_name) {
                  boolean var38 = var2._is_reg_name;
                  this._is_reg_name = var38;
               }

               boolean var20 = var2._is_abs_path;
               this._is_abs_path = var20;
               boolean var21 = var2._is_rel_path;
               this._is_rel_path = var21;
               char[] var22 = var2._path;
               this._path = var22;
            } else if(var1._authority != null && var2._scheme == null) {
               boolean var39 = var1._is_net_path;
               this._is_net_path = var39;
               char[] var40 = var1._authority;
               this._authority = var40;
               if(var1._is_server) {
                  boolean var41 = var1._is_server;
                  this._is_server = var41;
                  char[] var42 = var1._userinfo;
                  this._userinfo = var42;
                  char[] var43 = var1._host;
                  this._host = var43;
                  int var44 = var1._port;
                  this._port = var44;
               } else if(var1._is_reg_name) {
                  boolean var45 = var1._is_reg_name;
                  this._is_reg_name = var45;
               }
            }

            if(var2._authority != null) {
               boolean var23 = var2._is_net_path;
               this._is_net_path = var23;
               char[] var24 = var2._authority;
               this._authority = var24;
               if(var2._is_server) {
                  boolean var25 = var2._is_server;
                  this._is_server = var25;
                  char[] var26 = var2._userinfo;
                  this._userinfo = var26;
                  char[] var27 = var2._host;
                  this._host = var27;
                  int var28 = var2._port;
                  this._port = var28;
               } else if(var2._is_reg_name) {
                  boolean var46 = var2._is_reg_name;
                  this._is_reg_name = var46;
               }

               boolean var29 = var2._is_abs_path;
               this._is_abs_path = var29;
               boolean var30 = var2._is_rel_path;
               this._is_rel_path = var30;
               char[] var31 = var2._path;
               this._path = var31;
            }

            if(var2._authority == null && (var2._scheme == null || var12)) {
               if((var2._path == null || var2._path.length == 0) && var2._query == null) {
                  char[] var32 = var1._path;
                  this._path = var32;
                  char[] var33 = var1._query;
                  this._query = var33;
               } else {
                  char[] var47 = var1._path;
                  char[] var48 = var2._path;
                  char[] var49 = this.resolvePath(var47, var48);
                  this._path = var49;
               }
            }

            if(var2._query != null) {
               char[] var34 = var2._query;
               this._query = var34;
            }

            if(var2._fragment != null) {
               char[] var35 = var2._fragment;
               this._fragment = var35;
            }

            this.setURI();
            char[] var36 = this._uri;
            String var37 = new String(var36);
            this.parseUriReference(var37, (boolean)1);
         } else {
            char[] var6 = var1._scheme;
            this._scheme = var6;
            byte var7;
            if(!var1._is_opaque_part && !var2._is_opaque_part) {
               var7 = 0;
            } else {
               var7 = 1;
            }

            this._is_opaque_part = (boolean)var7;
            char[] var8 = var2._opaque;
            this._opaque = var8;
            char[] var9 = var2._fragment;
            this._fragment = var9;
            this.setURI();
         }
      }
   }

   public URI(char[] var1) throws URIException, NullPointerException {
      this.hash = 0;
      this._uri = null;
      this.protocolCharset = null;
      this._scheme = null;
      this._opaque = null;
      this._authority = null;
      this._userinfo = null;
      this._host = null;
      this._port = -1;
      this._path = null;
      this._query = null;
      this._fragment = null;
      String var2 = new String(var1);
      this.parseUriReference(var2, (boolean)1);
   }

   public URI(char[] var1, String var2) throws URIException, NullPointerException {
      this.hash = 0;
      this._uri = null;
      this.protocolCharset = null;
      this._scheme = null;
      this._opaque = null;
      this._authority = null;
      this._userinfo = null;
      this._host = null;
      this._port = -1;
      this._path = null;
      this._query = null;
      this._fragment = null;
      this.protocolCharset = var2;
      String var3 = new String(var1);
      this.parseUriReference(var3, (boolean)1);
   }

   protected static String decode(String var0, String var1) throws URIException {
      if(var0 == null) {
         throw new IllegalArgumentException("Component array of chars may not be null");
      } else {
         byte[] var2;
         try {
            var2 = URLCodec.decodeUrl(EncodingUtil.getAsciiBytes(var0));
         } catch (DecoderException var4) {
            String var3 = var4.getMessage();
            throw new URIException(var3);
         }

         return EncodingUtil.getString(var2, var1);
      }
   }

   protected static String decode(char[] var0, String var1) throws URIException {
      if(var0 == null) {
         throw new IllegalArgumentException("Component array of chars may not be null");
      } else {
         return decode(new String(var0), var1);
      }
   }

   protected static char[] encode(String var0, BitSet var1, String var2) throws URIException {
      if(var0 == null) {
         throw new IllegalArgumentException("Original string may not be null");
      } else if(var1 == null) {
         throw new IllegalArgumentException("Allowed bitset may not be null");
      } else {
         byte[] var3 = EncodingUtil.getBytes(var0, var2);
         return EncodingUtil.getAsciiString(URLCodec.encodeUrl(var1, var3)).toCharArray();
      }
   }

   public static String getDefaultDocumentCharset() {
      return defaultDocumentCharset;
   }

   public static String getDefaultDocumentCharsetByLocale() {
      return defaultDocumentCharsetByLocale;
   }

   public static String getDefaultDocumentCharsetByPlatform() {
      return defaultDocumentCharsetByPlatform;
   }

   public static String getDefaultProtocolCharset() {
      return defaultProtocolCharset;
   }

   private void readObject(ObjectInputStream var1) throws ClassNotFoundException, IOException {
      var1.defaultReadObject();
   }

   public static void setDefaultDocumentCharset(String var0) throws URI.DefaultCharsetChanged {
      defaultDocumentCharset = var0;
      throw new URI.DefaultCharsetChanged(2, "the default document charset changed");
   }

   public static void setDefaultProtocolCharset(String var0) throws URI.DefaultCharsetChanged {
      defaultProtocolCharset = var0;
      throw new URI.DefaultCharsetChanged(1, "the default protocol charset changed");
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      var1.defaultWriteObject();
   }

   public Object clone() throws CloneNotSupportedException {
      synchronized(this){}

      URI var1;
      try {
         var1 = (URI)super.clone();
         char[] var2 = this._uri;
         var1._uri = var2;
         char[] var3 = this._scheme;
         var1._scheme = var3;
         char[] var4 = this._opaque;
         var1._opaque = var4;
         char[] var5 = this._authority;
         var1._authority = var5;
         char[] var6 = this._userinfo;
         var1._userinfo = var6;
         char[] var7 = this._host;
         var1._host = var7;
         int var8 = this._port;
         var1._port = var8;
         char[] var9 = this._path;
         var1._path = var9;
         char[] var10 = this._query;
         var1._query = var10;
         char[] var11 = this._fragment;
         var1._fragment = var11;
         String var12 = this.protocolCharset;
         var1.protocolCharset = var12;
         boolean var13 = this._is_hier_part;
         var1._is_hier_part = var13;
         boolean var14 = this._is_opaque_part;
         var1._is_opaque_part = var14;
         boolean var15 = this._is_net_path;
         var1._is_net_path = var15;
         boolean var16 = this._is_abs_path;
         var1._is_abs_path = var16;
         boolean var17 = this._is_rel_path;
         var1._is_rel_path = var17;
         boolean var18 = this._is_reg_name;
         var1._is_reg_name = var18;
         boolean var19 = this._is_server;
         var1._is_server = var19;
         boolean var20 = this._is_hostname;
         var1._is_hostname = var20;
         boolean var21 = this._is_IPv4address;
         var1._is_IPv4address = var21;
         boolean var22 = this._is_IPv6reference;
         var1._is_IPv6reference = var22;
      } finally {
         ;
      }

      return var1;
   }

   public int compareTo(Object var1) throws ClassCastException {
      URI var2 = (URI)var1;
      char[] var3 = this._authority;
      char[] var4 = var2.getRawAuthority();
      int var5;
      if(!this.equals(var3, var4)) {
         var5 = -1;
      } else {
         String var6 = this.toString();
         String var7 = var2.toString();
         var5 = var6.compareTo(var7);
      }

      return var5;
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(var1 == this) {
         var2 = true;
      } else if(!(var1 instanceof URI)) {
         var2 = false;
      } else {
         URI var3 = (URI)var1;
         char[] var4 = this._scheme;
         char[] var5 = var3._scheme;
         if(!this.equals(var4, var5)) {
            var2 = false;
         } else {
            char[] var6 = this._opaque;
            char[] var7 = var3._opaque;
            if(!this.equals(var6, var7)) {
               var2 = false;
            } else {
               char[] var8 = this._authority;
               char[] var9 = var3._authority;
               if(!this.equals(var8, var9)) {
                  var2 = false;
               } else {
                  char[] var10 = this._path;
                  char[] var11 = var3._path;
                  if(!this.equals(var10, var11)) {
                     var2 = false;
                  } else {
                     char[] var12 = this._query;
                     char[] var13 = var3._query;
                     if(!this.equals(var12, var13)) {
                        var2 = false;
                     } else {
                        char[] var14 = this._fragment;
                        char[] var15 = var3._fragment;
                        if(!this.equals(var14, var15)) {
                           var2 = false;
                        } else {
                           var2 = true;
                        }
                     }
                  }
               }
            }
         }
      }

      return var2;
   }

   protected boolean equals(char[] var1, char[] var2) {
      boolean var3;
      if(var1 == null && var2 == null) {
         var3 = true;
      } else if(var1 != null && var2 != null) {
         int var4 = var1.length;
         int var5 = var2.length;
         if(var4 != var5) {
            var3 = false;
         } else {
            int var6 = 0;

            while(true) {
               int var7 = var1.length;
               if(var6 >= var7) {
                  var3 = true;
                  break;
               }

               char var8 = var1[var6];
               char var9 = var2[var6];
               if(var8 != var9) {
                  var3 = false;
                  break;
               }

               ++var6;
            }
         }
      } else {
         var3 = false;
      }

      return var3;
   }

   public String getAboveHierPath() throws URIException {
      char[] var1 = this.getRawAboveHierPath();
      String var2;
      if(var1 == null) {
         var2 = null;
      } else {
         String var3 = this.getProtocolCharset();
         var2 = decode(var1, var3);
      }

      return var2;
   }

   public String getAuthority() throws URIException {
      String var1;
      if(this._authority == null) {
         var1 = null;
      } else {
         char[] var2 = this._authority;
         String var3 = this.getProtocolCharset();
         var1 = decode(var2, var3);
      }

      return var1;
   }

   public String getCurrentHierPath() throws URIException {
      char[] var1 = this.getRawCurrentHierPath();
      String var2;
      if(var1 == null) {
         var2 = null;
      } else {
         String var3 = this.getProtocolCharset();
         var2 = decode(var1, var3);
      }

      return var2;
   }

   public String getEscapedAboveHierPath() throws URIException {
      char[] var1 = this.getRawAboveHierPath();
      String var2;
      if(var1 == null) {
         var2 = null;
      } else {
         var2 = new String(var1);
      }

      return var2;
   }

   public String getEscapedAuthority() {
      String var1;
      if(this._authority == null) {
         var1 = null;
      } else {
         char[] var2 = this._authority;
         var1 = new String(var2);
      }

      return var1;
   }

   public String getEscapedCurrentHierPath() throws URIException {
      char[] var1 = this.getRawCurrentHierPath();
      String var2;
      if(var1 == null) {
         var2 = null;
      } else {
         var2 = new String(var1);
      }

      return var2;
   }

   public String getEscapedFragment() {
      String var1;
      if(this._fragment == null) {
         var1 = null;
      } else {
         char[] var2 = this._fragment;
         var1 = new String(var2);
      }

      return var1;
   }

   public String getEscapedName() {
      char[] var1 = this.getRawName();
      String var2;
      if(var1 == null) {
         var2 = null;
      } else {
         var2 = new String(var1);
      }

      return var2;
   }

   public String getEscapedPath() {
      char[] var1 = this.getRawPath();
      String var2;
      if(var1 == null) {
         var2 = null;
      } else {
         var2 = new String(var1);
      }

      return var2;
   }

   public String getEscapedPathQuery() {
      char[] var1 = this.getRawPathQuery();
      String var2;
      if(var1 == null) {
         var2 = null;
      } else {
         var2 = new String(var1);
      }

      return var2;
   }

   public String getEscapedQuery() {
      String var1;
      if(this._query == null) {
         var1 = null;
      } else {
         char[] var2 = this._query;
         var1 = new String(var2);
      }

      return var1;
   }

   public String getEscapedURI() {
      String var1;
      if(this._uri == null) {
         var1 = null;
      } else {
         char[] var2 = this._uri;
         var1 = new String(var2);
      }

      return var1;
   }

   public String getEscapedURIReference() {
      char[] var1 = this.getRawURIReference();
      String var2;
      if(var1 == null) {
         var2 = null;
      } else {
         var2 = new String(var1);
      }

      return var2;
   }

   public String getEscapedUserinfo() {
      String var1;
      if(this._userinfo == null) {
         var1 = null;
      } else {
         char[] var2 = this._userinfo;
         var1 = new String(var2);
      }

      return var1;
   }

   public String getFragment() throws URIException {
      String var1;
      if(this._fragment == null) {
         var1 = null;
      } else {
         char[] var2 = this._fragment;
         String var3 = this.getProtocolCharset();
         var1 = decode(var2, var3);
      }

      return var1;
   }

   public String getHost() throws URIException {
      String var3;
      if(this._host != null) {
         char[] var1 = this._host;
         String var2 = this.getProtocolCharset();
         var3 = decode(var1, var2);
      } else {
         var3 = null;
      }

      return var3;
   }

   public String getName() throws URIException {
      String var1;
      if(this.getRawName() == null) {
         var1 = null;
      } else {
         char[] var2 = this.getRawName();
         String var3 = this.getProtocolCharset();
         var1 = decode(var2, var3);
      }

      return var1;
   }

   public String getPath() throws URIException {
      char[] var1 = this.getRawPath();
      String var2;
      if(var1 == null) {
         var2 = null;
      } else {
         String var3 = this.getProtocolCharset();
         var2 = decode(var1, var3);
      }

      return var2;
   }

   public String getPathQuery() throws URIException {
      char[] var1 = this.getRawPathQuery();
      String var2;
      if(var1 == null) {
         var2 = null;
      } else {
         String var3 = this.getProtocolCharset();
         var2 = decode(var1, var3);
      }

      return var2;
   }

   public int getPort() {
      return this._port;
   }

   public String getProtocolCharset() {
      String var1;
      if(this.protocolCharset != null) {
         var1 = this.protocolCharset;
      } else {
         var1 = defaultProtocolCharset;
      }

      return var1;
   }

   public String getQuery() throws URIException {
      String var1;
      if(this._query == null) {
         var1 = null;
      } else {
         char[] var2 = this._query;
         String var3 = this.getProtocolCharset();
         var1 = decode(var2, var3);
      }

      return var1;
   }

   public char[] getRawAboveHierPath() throws URIException {
      char[] var1 = this.getRawCurrentHierPath();
      char[] var2;
      if(var1 == null) {
         var2 = null;
      } else {
         var2 = this.getRawCurrentHierPath(var1);
      }

      return var2;
   }

   public char[] getRawAuthority() {
      return this._authority;
   }

   public char[] getRawCurrentHierPath() throws URIException {
      char[] var1;
      if(this._path == null) {
         var1 = null;
      } else {
         char[] var2 = this._path;
         var1 = this.getRawCurrentHierPath(var2);
      }

      return var1;
   }

   protected char[] getRawCurrentHierPath(char[] var1) throws URIException {
      if(this._is_opaque_part) {
         throw new URIException(1, "no hierarchy level");
      } else if(var1 == null) {
         throw new URIException(1, "empty path");
      } else {
         String var2 = new String(var1);
         int var3 = var2.indexOf(47);
         int var4 = var2.lastIndexOf(47);
         char[] var5;
         if(var4 == 0) {
            var5 = rootPath;
         } else if(var3 != var4 && var4 != -1) {
            var5 = var2.substring(0, var4).toCharArray();
         } else {
            var5 = var1;
         }

         return var5;
      }
   }

   public char[] getRawFragment() {
      return this._fragment;
   }

   public char[] getRawHost() {
      return this._host;
   }

   public char[] getRawName() {
      char[] var1;
      if(this._path == null) {
         var1 = null;
      } else {
         int var2 = 0;

         for(int var3 = this._path.length - 1; var3 >= 0; var3 += -1) {
            if(this._path[var3] == 47) {
               var2 = var3 + 1;
               break;
            }
         }

         int var4 = this._path.length - var2;
         char[] var5 = new char[var4];
         System.arraycopy(this._path, var2, var5, 0, var4);
         var1 = var5;
      }

      return var1;
   }

   public char[] getRawPath() {
      char[] var1;
      if(this._is_opaque_part) {
         var1 = this._opaque;
      } else {
         var1 = this._path;
      }

      return var1;
   }

   public char[] getRawPathQuery() {
      char[] var1;
      if(this._path == null && this._query == null) {
         var1 = null;
      } else {
         StringBuffer var2 = new StringBuffer();
         if(this._path != null) {
            char[] var3 = this._path;
            var2.append(var3);
         }

         if(this._query != null) {
            StringBuffer var5 = var2.append('?');
            char[] var6 = this._query;
            var2.append(var6);
         }

         var1 = var2.toString().toCharArray();
      }

      return var1;
   }

   public char[] getRawQuery() {
      return this._query;
   }

   public char[] getRawScheme() {
      return this._scheme;
   }

   public char[] getRawURI() {
      return this._uri;
   }

   public char[] getRawURIReference() {
      char[] var1;
      if(this._fragment == null) {
         var1 = this._uri;
      } else if(this._uri == null) {
         var1 = this._fragment;
      } else {
         StringBuilder var2 = new StringBuilder();
         char[] var3 = this._uri;
         String var4 = new String(var3);
         StringBuilder var5 = var2.append(var4).append("#");
         char[] var6 = this._fragment;
         String var7 = new String(var6);
         var1 = var5.append(var7).toString().toCharArray();
      }

      return var1;
   }

   public char[] getRawUserinfo() {
      return this._userinfo;
   }

   public String getScheme() {
      String var1;
      if(this._scheme == null) {
         var1 = null;
      } else {
         char[] var2 = this._scheme;
         var1 = new String(var2);
      }

      return var1;
   }

   public String getURI() throws URIException {
      String var1;
      if(this._uri == null) {
         var1 = null;
      } else {
         char[] var2 = this._uri;
         String var3 = this.getProtocolCharset();
         var1 = decode(var2, var3);
      }

      return var1;
   }

   public String getURIReference() throws URIException {
      char[] var1 = this.getRawURIReference();
      String var2;
      if(var1 == null) {
         var2 = null;
      } else {
         String var3 = this.getProtocolCharset();
         var2 = decode(var1, var3);
      }

      return var2;
   }

   public String getUserinfo() throws URIException {
      String var1;
      if(this._userinfo == null) {
         var1 = null;
      } else {
         char[] var2 = this._userinfo;
         String var3 = this.getProtocolCharset();
         var1 = decode(var2, var3);
      }

      return var1;
   }

   public boolean hasAuthority() {
      boolean var1;
      if(this._authority == null && !this._is_net_path) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public boolean hasFragment() {
      boolean var1;
      if(this._fragment != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean hasQuery() {
      boolean var1;
      if(this._query != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean hasUserinfo() {
      boolean var1;
      if(this._userinfo != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public int hashCode() {
      if(this.hash == 0) {
         char[] var1 = this._uri;
         int var2;
         int var3;
         if(var1 != null) {
            var2 = 0;

            for(var3 = var1.length; var2 < var3; ++var2) {
               int var4 = this.hash * 31;
               char var5 = var1[var2];
               int var6 = var4 + var5;
               this.hash = var6;
            }
         }

         char[] var7 = this._fragment;
         if(var7 != null) {
            var2 = 0;

            for(var3 = var7.length; var2 < var3; ++var2) {
               int var8 = this.hash * 31;
               char var9 = var7[var2];
               int var10 = var8 + var9;
               this.hash = var10;
            }
         }
      }

      return this.hash;
   }

   protected int indexFirstOf(String var1, String var2) {
      return this.indexFirstOf(var1, var2, -1);
   }

   protected int indexFirstOf(String var1, String var2, int var3) {
      int var4;
      if(var1 != null && var1.length() != 0) {
         if(var2 != null && var2.length() != 0) {
            if(var3 < 0) {
               var3 = 0;
            } else {
               int var11 = var1.length();
               if(var3 > var11) {
                  var4 = -1;
                  return var4;
               }
            }

            int var5 = var1.length();
            char[] var6 = var2.toCharArray();
            int var7 = 0;

            while(true) {
               int var8 = var6.length;
               if(var7 >= var8) {
                  int var12 = var1.length();
                  if(var5 == var12) {
                     var4 = -1;
                  } else {
                     var4 = var5;
                  }
                  break;
               }

               char var9 = var6[var7];
               int var10 = var1.indexOf(var9, var3);
               if(var10 >= 0 && var10 < var5) {
                  var5 = var10;
               }

               ++var7;
            }
         } else {
            var4 = -1;
         }
      } else {
         var4 = -1;
      }

      return var4;
   }

   protected int indexFirstOf(char[] var1, char var2) {
      return this.indexFirstOf(var1, var2, 0);
   }

   protected int indexFirstOf(char[] var1, char var2, int var3) {
      int var4;
      if(var1 != null && var1.length != 0) {
         if(var3 < 0) {
            var3 = 0;
         } else {
            int var7 = var1.length;
            if(var3 > var7) {
               var4 = -1;
               return var4;
            }
         }

         int var5 = var3;

         while(true) {
            int var6 = var1.length;
            if(var5 >= var6) {
               var4 = -1;
               break;
            }

            if(var1[var5] == var2) {
               var4 = var5;
               break;
            }

            ++var5;
         }
      } else {
         var4 = -1;
      }

      return var4;
   }

   public boolean isAbsPath() {
      return this._is_abs_path;
   }

   public boolean isAbsoluteURI() {
      boolean var1;
      if(this._scheme != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isHierPart() {
      return this._is_hier_part;
   }

   public boolean isHostname() {
      return this._is_hostname;
   }

   public boolean isIPv4address() {
      return this._is_IPv4address;
   }

   public boolean isIPv6reference() {
      return this._is_IPv6reference;
   }

   public boolean isNetPath() {
      boolean var1;
      if(!this._is_net_path && this._authority == null) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public boolean isOpaquePart() {
      return this._is_opaque_part;
   }

   public boolean isRegName() {
      return this._is_reg_name;
   }

   public boolean isRelPath() {
      return this._is_rel_path;
   }

   public boolean isRelativeURI() {
      boolean var1;
      if(this._scheme == null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isServer() {
      return this._is_server;
   }

   public void normalize() throws URIException {
      if(this.isAbsPath()) {
         char[] var1 = this._path;
         char[] var2 = this.normalize(var1);
         this._path = var2;
         this.setURI();
      }
   }

   protected char[] normalize(char[] var1) throws URIException {
      char[] var2;
      if(var1 == null) {
         var2 = null;
         return var2;
      } else {
         String var3 = new String(var1);
         if(var3.startsWith("./")) {
            var3 = var3.substring(1);
         } else if(var3.startsWith("../")) {
            var3 = var3.substring(2);
         } else if(var3.startsWith("..")) {
            var3 = var3.substring(2);
         }

         StringBuffer var4 = new StringBuffer();

         while(true) {
            int var5 = var3.indexOf("/./");
            if(var5 == -1) {
               if(var3.endsWith("/.")) {
                  int var12 = var3.length() - 1;
                  var3 = var3.substring(0, var12);
               }

               int var13 = 0;

               while(true) {
                  var5 = var3.indexOf("/../", var13);
                  if(var5 == -1) {
                     if(var3.endsWith("/..")) {
                        int var22 = var3.length() - 4;
                        int var23 = var3.lastIndexOf(47, var22);
                        if(var23 >= 0) {
                           int var24 = var23 + 1;
                           var3 = var3.substring(0, var24);
                        }
                     }

                     while(true) {
                        var5 = var3.indexOf("/../");
                        if(var5 == -1) {
                           break;
                        }

                        int var25 = var5 - 1;
                        if(var3.lastIndexOf(47, var25) >= 0) {
                           break;
                        }

                        int var27 = var5 + 3;
                        var3 = var3.substring(var27);
                     }

                     if(var3.endsWith("/..")) {
                        int var26 = var3.length() - 4;
                        if(var3.lastIndexOf(47, var26) < 0) {
                           var3 = "/";
                        }
                     }

                     var2 = var3.toCharArray();
                     return var2;
                  }

                  int var14 = var5 - 1;
                  int var15 = var3.lastIndexOf(47, var14);
                  if(var15 >= 0) {
                     String var16 = var3.substring(0, var15);
                     StringBuffer var17 = var4.append(var16);
                     int var18 = var5 + 3;
                     String var19 = var3.substring(var18);
                     var3 = var17.append(var19).toString();
                     int var20 = var4.length();
                     var4.delete(0, var20);
                  } else {
                     var13 = var5 + 3;
                  }
               }
            }

            String var6 = var3.substring(0, var5);
            StringBuffer var7 = var4.append(var6);
            int var8 = var5 + 2;
            String var9 = var3.substring(var8);
            var3 = var7.append(var9).toString();
            int var10 = var4.length();
            var4.delete(0, var10);
         }
      }
   }

   protected void parseAuthority(String var1, boolean var2) throws URIException {
      this._is_IPv6reference = (boolean)0;
      this._is_IPv4address = (boolean)0;
      this._is_hostname = (boolean)0;
      this._is_server = (boolean)0;
      this._is_reg_name = (boolean)0;
      String var3 = this.getProtocolCharset();
      boolean var4 = true;
      int var5 = 0;
      int var6 = var1.indexOf(64);
      if(var6 != -1) {
         char[] var7;
         if(var2) {
            var7 = var1.substring(0, var6).toCharArray();
         } else {
            String var9 = var1.substring(0, var6);
            BitSet var10 = allowed_userinfo;
            var7 = encode(var9, var10, var3);
         }

         this._userinfo = var7;
         var5 = var6 + 1;
      }

      int var11;
      if(var1.indexOf(91, var5) >= var5) {
         int var8 = var1.indexOf(93, var5);
         if(var8 == -1) {
            throw new URIException(1, "IPv6reference");
         }

         var11 = var8 + 1;
         char[] var12;
         if(var2) {
            var12 = var1.substring(var5, var11).toCharArray();
         } else {
            String var16 = var1.substring(var5, var11);
            BitSet var17 = allowed_IPv6reference;
            var12 = encode(var16, var17, var3);
         }

         this._host = var12;
         this._is_IPv6reference = (boolean)1;
      } else {
         var11 = var1.indexOf(58, var5);
         if(var11 == -1) {
            int var18 = var1.length();
            var4 = false;
         }

         char[] var19 = var1.substring(var5, var11).toCharArray();
         this._host = var19;
         char[] var20 = this._host;
         BitSet var21 = IPv4address;
         if(this.validate(var20, var21)) {
            this._is_IPv4address = (boolean)1;
         } else {
            char[] var22 = this._host;
            BitSet var23 = hostname;
            if(this.validate(var22, var23)) {
               this._is_hostname = (boolean)1;
            } else {
               this._is_reg_name = (boolean)1;
            }
         }
      }

      if(this._is_reg_name) {
         this._is_IPv6reference = (boolean)0;
         this._is_IPv4address = (boolean)0;
         this._is_hostname = (boolean)0;
         this._is_server = (boolean)0;
         if(var2) {
            char[] var13 = var1.toCharArray();
            this._authority = var13;
            char[] var14 = this._authority;
            BitSet var15 = reg_name;
            if(!this.validate(var14, var15)) {
               throw new URIException("Invalid authority");
            }
         } else {
            BitSet var24 = allowed_reg_name;
            char[] var25 = encode(var1, var24, var3);
            this._authority = var25;
         }
      } else {
         if(var1.length() - 1 > var11 && var4 && var1.charAt(var11) == 58) {
            var5 = var11 + 1;

            try {
               int var26 = Integer.parseInt(var1.substring(var5));
               this._port = var26;
            } catch (NumberFormatException var38) {
               throw new URIException(1, "invalid port number");
            }
         }

         StringBuffer var27 = new StringBuffer();
         if(this._userinfo != null) {
            char[] var28 = this._userinfo;
            var27.append(var28);
            StringBuffer var30 = var27.append('@');
         }

         if(this._host != null) {
            char[] var31 = this._host;
            var27.append(var31);
            if(this._port != -1) {
               StringBuffer var33 = var27.append(':');
               int var34 = this._port;
               var27.append(var34);
            }
         }

         char[] var36 = var27.toString().toCharArray();
         this._authority = var36;
         this._is_server = (boolean)1;
      }
   }

   protected void parseUriReference(String var1, boolean var2) throws URIException {
      if(var1 == null) {
         throw new URIException("URI-Reference required");
      } else {
         String var3 = var1.trim();
         int var4 = var3.length();
         if(var4 > 0) {
            char[] var5 = new char[1];
            byte var7 = 0;
            char var8 = var3.charAt(var7);
            var5[0] = var8;
            BitSet var9 = delims;
            if(this.validate(var5, var9)) {
               byte var14 = 2;
               if(var4 >= var14) {
                  char[] var15 = new char[1];
                  int var16 = var4 - 1;
                  char var19 = var3.charAt(var16);
                  var15[0] = var19;
                  BitSet var20 = delims;
                  if(this.validate(var15, var20)) {
                     int var24 = var4 - 1;
                     var3 = var3.substring(1, var24);
                     var4 -= 2;
                  }
               }
            }
         }

         int var25 = 0;
         boolean var26 = false;
         int var27 = var3.indexOf(58);
         int var28 = var3.indexOf(47);
         if(var27 <= 0 && !var3.startsWith("//") || var28 >= 0 && var28 < var27) {
            var26 = true;
         }

         String var29;
         if(var26) {
            var29 = "/?#";
         } else {
            var29 = ":/?#";
         }

         int var34 = this.indexFirstOf(var3, var29, var25);
         char var36 = '\uffff';
         if(var34 == var36) {
            var34 = 0;
         }

         if(var34 > 0 && var34 < var4) {
            char var39 = var3.charAt(var34);
            byte var40 = 58;
            if(var39 == var40) {
               byte var42 = 0;
               char[] var44 = var3.substring(var42, var34).toLowerCase().toCharArray();
               BitSet var45 = scheme;
               if(!this.validate(var44, var45)) {
                  throw new URIException("incorrect scheme");
               }

               this._scheme = var44;
               ++var34;
               var25 = var34;
            }
         }

         byte var50 = 0;
         this._is_hier_part = (boolean)var50;
         byte var51 = 0;
         this._is_rel_path = (boolean)var51;
         byte var52 = 0;
         this._is_abs_path = (boolean)var52;
         byte var53 = 0;
         this._is_net_path = (boolean)var53;
         int var69;
         if(var34 >= 0 && var34 < var4) {
            char var56 = var3.charAt(var34);
            byte var57 = 47;
            if(var56 == var57) {
               byte var58 = 1;
               this._is_hier_part = (boolean)var58;
               int var59 = var34 + 2;
               if(var59 < var4) {
                  int var61 = var34 + 1;
                  char var62 = var3.charAt(var61);
                  byte var63 = 47;
                  if(var62 == var63 && !var26) {
                     int var64 = var34 + 2;
                     String var67 = "/?#";
                     var69 = this.indexFirstOf(var3, var67, var64);
                     char var71 = '\uffff';
                     if(var69 == var71) {
                        int var72 = var34 + 2;
                        if(var3.substring(var72).length() == 0) {
                           var69 = var34 + 2;
                        } else {
                           var69 = var3.length();
                        }
                     }

                     int var73 = var34 + 2;
                     String var77 = var3.substring(var73, var69);
                     this.parseAuthority(var77, var2);
                     var34 = var69;
                     var25 = var69;
                     byte var81 = 1;
                     this._is_net_path = (boolean)var81;
                  }
               }

               if(var25 == var34) {
                  byte var82 = 1;
                  this._is_abs_path = (boolean)var82;
               }
            }
         }

         if(var25 < var4) {
            String var85 = "?#";
            var69 = this.indexFirstOf(var3, var85, var25);
            char var88 = '\uffff';
            if(var69 == var88) {
               int var89 = var3.length();
            }

            if(!this._is_abs_path) {
               label139: {
                  label165: {
                     if(!var2) {
                        String var93 = var3.substring(var25, var69);
                        BitSet var94 = disallowed_rel_path;
                        if(this.prevalidate(var93, var94)) {
                           break label165;
                        }
                     }

                     if(var2) {
                        char[] var101 = var3.substring(var25, var69).toCharArray();
                        BitSet var102 = rel_path;
                        if(this.validate(var101, var102)) {
                           break label165;
                        }
                     }

                     label166: {
                        if(!var2) {
                           String var138 = var3.substring(var25, var69);
                           BitSet var139 = disallowed_opaque_part;
                           if(this.prevalidate(var138, var139)) {
                              break label166;
                           }
                        }

                        if(var2) {
                           char[] var146 = var3.substring(var25, var69).toCharArray();
                           BitSet var147 = opaque_part;
                           if(this.validate(var146, var147)) {
                              break label166;
                           }
                        }

                        Object var152 = null;
                        this._path = (char[])var152;
                        break label139;
                     }

                     byte var151 = 1;
                     this._is_opaque_part = (boolean)var151;
                     break label139;
                  }

                  byte var106 = 1;
                  this._is_rel_path = (boolean)var106;
               }
            }

            String var110 = var3.substring(var25, var69);
            if(var2) {
               char[] var111 = var110.toCharArray();
               this.setRawPath(var111);
            } else {
               this.setPath(var110);
            }

            var34 = var69;
         }

         String var114 = this.getProtocolCharset();
         if(var34 >= 0) {
            int var115 = var34 + 1;
            if(var115 < var4) {
               char var119 = var3.charAt(var34);
               byte var120 = 63;
               if(var119 == var120) {
                  int var121 = var34 + 1;
                  var69 = var3.indexOf(35, var121);
                  char var123 = '\uffff';
                  if(var69 == var123) {
                     int var124 = var3.length();
                  }

                  if(var2) {
                     int var125 = var34 + 1;
                     char[] var129 = var3.substring(var125, var69).toCharArray();
                     this._query = var129;
                     char[] var130 = this._query;
                     BitSet var131 = uric;
                     if(!this.validate(var130, var131)) {
                        throw new URIException("Invalid query");
                     }
                  } else {
                     int var155 = var34 + 1;
                     String var159 = var3.substring(var155, var69);
                     BitSet var160 = allowed_query;
                     char[] var164 = encode(var159, var160, var114);
                     this._query = var164;
                  }

                  var34 = var69;
               }
            }
         }

         if(var34 >= 0) {
            int var165 = var34 + 1;
            if(var165 <= var4) {
               char var169 = var3.charAt(var34);
               byte var170 = 35;
               if(var169 == var170) {
                  int var171 = var34 + 1;
                  if(var171 == var4) {
                     char[] var173 = "".toCharArray();
                     this._fragment = var173;
                  } else {
                     char[] var175;
                     if(var2) {
                        int var174 = var34 + 1;
                        var175 = var3.substring(var174).toCharArray();
                     } else {
                        int var177 = var34 + 1;
                        String var178 = var3.substring(var177);
                        BitSet var179 = allowed_fragment;
                        var175 = encode(var178, var179, var114);
                     }

                     this._fragment = var175;
                  }
               }
            }
         }

         this.setURI();
      }
   }

   protected boolean prevalidate(String var1, BitSet var2) {
      boolean var3;
      if(var1 == null) {
         var3 = false;
      } else {
         char[] var4 = var1.toCharArray();
         int var5 = 0;

         while(true) {
            int var6 = var4.length;
            if(var5 >= var6) {
               var3 = true;
               break;
            }

            char var7 = var4[var5];
            if(var2.get(var7)) {
               var3 = false;
               break;
            }

            ++var5;
         }
      }

      return var3;
   }

   protected char[] removeFragmentIdentifier(char[] var1) {
      char[] var2;
      if(var1 == null) {
         var2 = null;
      } else {
         int var3 = (new String(var1)).indexOf(35);
         if(var3 != -1) {
            var1 = (new String(var1)).substring(0, var3).toCharArray();
         }

         var2 = var1;
      }

      return var2;
   }

   protected char[] resolvePath(char[] var1, char[] var2) throws URIException {
      String var3;
      if(var1 == null) {
         var3 = "";
      } else {
         var3 = new String(var1);
      }

      char[] var4;
      if(var2 != null && var2.length != 0) {
         if(var2[0] == 47) {
            var4 = this.normalize(var2);
         } else {
            int var5 = var3.lastIndexOf(47);
            if(var5 != -1) {
               int var6 = var5 + 1;
               char[] var7 = var3.substring(0, var6).toCharArray();
            }

            int var8 = var3.length();
            int var9 = var2.length;
            int var10 = var8 + var9;
            StringBuffer var11 = new StringBuffer(var10);
            String var13;
            if(var5 != -1) {
               int var12 = var5 + 1;
               var13 = var3.substring(0, var12);
            } else {
               var13 = "/";
            }

            var11.append(var13);
            var11.append(var2);
            char[] var16 = var11.toString().toCharArray();
            var4 = this.normalize(var16);
         }
      } else {
         var4 = this.normalize(var1);
      }

      return var4;
   }

   public void setEscapedAuthority(String var1) throws URIException {
      this.parseAuthority(var1, (boolean)1);
      this.setURI();
   }

   public void setEscapedFragment(String var1) throws URIException {
      if(var1 == null) {
         this._fragment = null;
         this.hash = 0;
      } else {
         char[] var2 = var1.toCharArray();
         this.setRawFragment(var2);
      }
   }

   public void setEscapedPath(String var1) throws URIException {
      if(var1 == null) {
         this._opaque = null;
         this._path = null;
         this.setURI();
      } else {
         char[] var2 = var1.toCharArray();
         this.setRawPath(var2);
      }
   }

   public void setEscapedQuery(String var1) throws URIException {
      if(var1 == null) {
         this._query = null;
         this.setURI();
      } else {
         char[] var2 = var1.toCharArray();
         this.setRawQuery(var2);
      }
   }

   public void setFragment(String var1) throws URIException {
      if(var1 != null && var1.length() != 0) {
         BitSet var3 = allowed_fragment;
         String var4 = this.getProtocolCharset();
         char[] var5 = encode(var1, var3, var4);
         this._fragment = var5;
         this.hash = 0;
      } else {
         char[] var2;
         if(var1 == null) {
            var2 = null;
         } else {
            var2 = var1.toCharArray();
         }

         this._fragment = var2;
         this.hash = 0;
      }
   }

   public void setPath(String var1) throws URIException {
      if(var1 != null && var1.length() != 0) {
         String var3 = this.getProtocolCharset();
         if(!this._is_net_path && !this._is_abs_path) {
            if(this._is_rel_path) {
               int var6 = var1.length();
               StringBuffer var7 = new StringBuffer(var6);
               int var8 = var1.indexOf(47);
               if(var8 == 0) {
                  throw new URIException(1, "incorrect relative path");
               }

               if(var8 > 0) {
                  String var9 = var1.substring(0, var8);
                  BitSet var10 = allowed_rel_path;
                  char[] var11 = encode(var9, var10, var3);
                  var7.append(var11);
                  String var13 = var1.substring(var8);
                  BitSet var14 = allowed_abs_path;
                  char[] var15 = encode(var13, var14, var3);
                  var7.append(var15);
               } else {
                  BitSet var18 = allowed_rel_path;
                  char[] var19 = encode(var1, var18, var3);
                  var7.append(var19);
               }

               char[] var17 = var7.toString().toCharArray();
               this._path = var17;
            } else {
               if(!this._is_opaque_part) {
                  throw new URIException(1, "incorrect path");
               }

               StringBuffer var21 = new StringBuffer();
               String var22 = var1.substring(0, 1);
               BitSet var23 = uric_no_slash;
               char[] var24 = encode(var22, var23, var3);
               var21.insert(0, var24);
               String var26 = var1.substring(1);
               BitSet var27 = uric;
               char[] var28 = encode(var26, var27, var3);
               var21.insert(1, var28);
               char[] var30 = var21.toString().toCharArray();
               this._opaque = var30;
            }
         } else {
            BitSet var4 = allowed_abs_path;
            char[] var5 = encode(var1, var4, var3);
            this._path = var5;
         }

         this.setURI();
      } else {
         char[] var2;
         if(var1 == null) {
            var2 = null;
         } else {
            var2 = var1.toCharArray();
         }

         this._opaque = var2;
         this._path = var2;
         this.setURI();
      }
   }

   public void setQuery(String var1) throws URIException {
      if(var1 != null && var1.length() != 0) {
         BitSet var3 = allowed_query;
         String var4 = this.getProtocolCharset();
         char[] var5 = encode(var1, var3, var4);
         this.setRawQuery(var5);
      } else {
         char[] var2;
         if(var1 == null) {
            var2 = null;
         } else {
            var2 = var1.toCharArray();
         }

         this._query = var2;
         this.setURI();
      }
   }

   public void setRawAuthority(char[] var1) throws URIException, NullPointerException {
      String var2 = new String(var1);
      this.parseAuthority(var2, (boolean)1);
      this.setURI();
   }

   public void setRawFragment(char[] var1) throws URIException {
      if(var1 != null && var1.length != 0) {
         BitSet var2 = fragment;
         if(!this.validate(var1, var2)) {
            throw new URIException(3, "escaped fragment not valid");
         } else {
            this._fragment = var1;
            this.hash = 0;
         }
      } else {
         this._fragment = var1;
         this.hash = 0;
      }
   }

   public void setRawPath(char[] var1) throws URIException {
      if(var1 != null && var1.length != 0) {
         var1 = this.removeFragmentIdentifier(var1);
         if(!this._is_net_path && !this._is_abs_path) {
            if(this._is_rel_path) {
               int var3 = this.indexFirstOf(var1, '/');
               if(var3 == 0) {
                  throw new URIException(1, "incorrect path");
               }

               if(var3 > 0) {
                  int var4 = var3 - 1;
                  BitSet var5 = rel_segment;
                  if(!this.validate(var1, 0, var4, var5)) {
                     BitSet var6 = abs_path;
                     if(!this.validate(var1, var3, -1, var6)) {
                        throw new URIException(3, "escaped relative path not valid");
                     }
                  }
               }

               if(var3 < 0) {
                  BitSet var7 = rel_segment;
                  if(!this.validate(var1, 0, -1, var7)) {
                     throw new URIException(3, "escaped relative path not valid");
                  }
               }

               this._path = var1;
            } else {
               if(!this._is_opaque_part) {
                  throw new URIException(1, "incorrect path");
               }

               BitSet var8 = uric_no_slash;
               char var9 = var1[0];
               if(!var8.get(var9)) {
                  BitSet var10 = uric;
                  if(!this.validate(var1, 1, -1, var10)) {
                     throw new URIException(3, "escaped opaque part not valid");
                  }
               }

               this._opaque = var1;
            }
         } else {
            if(var1[0] != 47) {
               throw new URIException(1, "not absolute path");
            }

            BitSet var2 = abs_path;
            if(!this.validate(var1, var2)) {
               throw new URIException(3, "escaped absolute path not valid");
            }

            this._path = var1;
         }

         this.setURI();
      } else {
         this._opaque = var1;
         this._path = var1;
         this.setURI();
      }
   }

   public void setRawQuery(char[] var1) throws URIException {
      if(var1 != null && var1.length != 0) {
         var1 = this.removeFragmentIdentifier(var1);
         BitSet var2 = query;
         if(!this.validate(var1, var2)) {
            throw new URIException(3, "escaped query not valid");
         } else {
            this._query = var1;
            this.setURI();
         }
      } else {
         this._query = var1;
         this.setURI();
      }
   }

   protected void setURI() {
      StringBuffer var1 = new StringBuffer();
      if(this._scheme != null) {
         char[] var2 = this._scheme;
         var1.append(var2);
         StringBuffer var4 = var1.append(':');
      }

      if(this._is_net_path) {
         StringBuffer var5 = var1.append("//");
         if(this._authority != null) {
            char[] var6 = this._authority;
            var1.append(var6);
         }
      }

      if(this._opaque != null && this._is_opaque_part) {
         char[] var8 = this._opaque;
         var1.append(var8);
      } else if(this._path != null && this._path.length != 0) {
         char[] var14 = this._path;
         var1.append(var14);
      }

      if(this._query != null) {
         StringBuffer var10 = var1.append('?');
         char[] var11 = this._query;
         var1.append(var11);
      }

      char[] var13 = var1.toString().toCharArray();
      this._uri = var13;
      this.hash = 0;
   }

   public String toString() {
      return this.getEscapedURI();
   }

   protected boolean validate(char[] var1, int var2, int var3, BitSet var4) {
      if(var3 == -1) {
         var3 = var1.length - 1;
      }

      int var5 = var2;

      boolean var7;
      while(true) {
         if(var5 > var3) {
            var7 = true;
            break;
         }

         char var6 = var1[var5];
         if(!var4.get(var6)) {
            var7 = false;
            break;
         }

         ++var5;
      }

      return var7;
   }

   protected boolean validate(char[] var1, BitSet var2) {
      return this.validate(var1, 0, -1, var2);
   }

   public static class LocaleToCharsetMap {

      private static final Hashtable LOCALE_TO_CHARSET_MAP = new Hashtable();


      static {
         Object var0 = LOCALE_TO_CHARSET_MAP.put("ar", "ISO-8859-6");
         Object var1 = LOCALE_TO_CHARSET_MAP.put("be", "ISO-8859-5");
         Object var2 = LOCALE_TO_CHARSET_MAP.put("bg", "ISO-8859-5");
         Object var3 = LOCALE_TO_CHARSET_MAP.put("ca", "ISO-8859-1");
         Object var4 = LOCALE_TO_CHARSET_MAP.put("cs", "ISO-8859-2");
         Object var5 = LOCALE_TO_CHARSET_MAP.put("da", "ISO-8859-1");
         Object var6 = LOCALE_TO_CHARSET_MAP.put("de", "ISO-8859-1");
         Object var7 = LOCALE_TO_CHARSET_MAP.put("el", "ISO-8859-7");
         Object var8 = LOCALE_TO_CHARSET_MAP.put("en", "ISO-8859-1");
         Object var9 = LOCALE_TO_CHARSET_MAP.put("es", "ISO-8859-1");
         Object var10 = LOCALE_TO_CHARSET_MAP.put("et", "ISO-8859-1");
         Object var11 = LOCALE_TO_CHARSET_MAP.put("fi", "ISO-8859-1");
         Object var12 = LOCALE_TO_CHARSET_MAP.put("fr", "ISO-8859-1");
         Object var13 = LOCALE_TO_CHARSET_MAP.put("hr", "ISO-8859-2");
         Object var14 = LOCALE_TO_CHARSET_MAP.put("hu", "ISO-8859-2");
         Object var15 = LOCALE_TO_CHARSET_MAP.put("is", "ISO-8859-1");
         Object var16 = LOCALE_TO_CHARSET_MAP.put("it", "ISO-8859-1");
         Object var17 = LOCALE_TO_CHARSET_MAP.put("iw", "ISO-8859-8");
         Object var18 = LOCALE_TO_CHARSET_MAP.put("ja", "Shift_JIS");
         Object var19 = LOCALE_TO_CHARSET_MAP.put("ko", "EUC-KR");
         Object var20 = LOCALE_TO_CHARSET_MAP.put("lt", "ISO-8859-2");
         Object var21 = LOCALE_TO_CHARSET_MAP.put("lv", "ISO-8859-2");
         Object var22 = LOCALE_TO_CHARSET_MAP.put("mk", "ISO-8859-5");
         Object var23 = LOCALE_TO_CHARSET_MAP.put("nl", "ISO-8859-1");
         Object var24 = LOCALE_TO_CHARSET_MAP.put("no", "ISO-8859-1");
         Object var25 = LOCALE_TO_CHARSET_MAP.put("pl", "ISO-8859-2");
         Object var26 = LOCALE_TO_CHARSET_MAP.put("pt", "ISO-8859-1");
         Object var27 = LOCALE_TO_CHARSET_MAP.put("ro", "ISO-8859-2");
         Object var28 = LOCALE_TO_CHARSET_MAP.put("ru", "ISO-8859-5");
         Object var29 = LOCALE_TO_CHARSET_MAP.put("sh", "ISO-8859-5");
         Object var30 = LOCALE_TO_CHARSET_MAP.put("sk", "ISO-8859-2");
         Object var31 = LOCALE_TO_CHARSET_MAP.put("sl", "ISO-8859-2");
         Object var32 = LOCALE_TO_CHARSET_MAP.put("sq", "ISO-8859-2");
         Object var33 = LOCALE_TO_CHARSET_MAP.put("sr", "ISO-8859-5");
         Object var34 = LOCALE_TO_CHARSET_MAP.put("sv", "ISO-8859-1");
         Object var35 = LOCALE_TO_CHARSET_MAP.put("tr", "ISO-8859-9");
         Object var36 = LOCALE_TO_CHARSET_MAP.put("uk", "ISO-8859-5");
         Object var37 = LOCALE_TO_CHARSET_MAP.put("zh", "GB2312");
         Object var38 = LOCALE_TO_CHARSET_MAP.put("zh_TW", "Big5");
      }

      public LocaleToCharsetMap() {}

      public static String getCharset(Locale var0) {
         Hashtable var1 = LOCALE_TO_CHARSET_MAP;
         String var2 = var0.toString();
         String var3 = (String)var1.get(var2);
         String var4;
         if(var3 != null) {
            var4 = var3;
         } else {
            Hashtable var5 = LOCALE_TO_CHARSET_MAP;
            String var6 = var0.getLanguage();
            var4 = (String)var5.get(var6);
         }

         return var4;
      }
   }

   public static class DefaultCharsetChanged extends RuntimeException {

      public static final int DOCUMENT_CHARSET = 2;
      public static final int PROTOCOL_CHARSET = 1;
      public static final int UNKNOWN;
      private String reason;
      private int reasonCode;


      public DefaultCharsetChanged(int var1, String var2) {
         super(var2);
         this.reason = var2;
         this.reasonCode = var1;
      }

      public String getReason() {
         return this.reason;
      }

      public int getReasonCode() {
         return this.reasonCode;
      }
   }
}
