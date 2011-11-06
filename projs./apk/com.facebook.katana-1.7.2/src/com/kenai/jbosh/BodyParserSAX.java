// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BodyParserSAX.java

package com.kenai.jbosh;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

// Referenced classes of package com.kenai.jbosh:
//            BodyParser, BOSHException, BodyParserResults, AbstractBody, 
//            BodyQName

final class BodyParserSAX
    implements BodyParser
{
    private static final class Handler extends DefaultHandler
    {

        public void startElement(String s, String s1, String s2, Attributes attributes)
        {
            if(BodyParserSAX.LOG.isLoggable(Level.FINEST))
            {
                BodyParserSAX.LOG.finest((new StringBuilder()).append("Start element: ").append(s2).toString());
                BodyParserSAX.LOG.finest((new StringBuilder()).append("    URI: ").append(s).toString());
                BodyParserSAX.LOG.finest((new StringBuilder()).append("    local: ").append(s1).toString());
            }
            BodyQName bodyqname = AbstractBody.getBodyQName();
            if(!bodyqname.getNamespaceURI().equals(s) || !bodyqname.getLocalPart().equals(s1))
                throw new IllegalStateException((new StringBuilder()).append("Root element was not '").append(bodyqname.getLocalPart()).append("' in the '").append(bodyqname.getNamespaceURI()).append("' namespace.  (Was '").append(s1).append("' in '").append(s).append("')").toString());
            for(int i = 0; i < attributes.getLength(); i++)
            {
                String s3 = attributes.getURI(i);
                if(s3.length() == 0)
                    s3 = defaultNS;
                String s4 = attributes.getLocalName(i);
                String s5 = attributes.getValue(i);
                if(BodyParserSAX.LOG.isLoggable(Level.FINEST))
                    BodyParserSAX.LOG.finest((new StringBuilder()).append("    Attribute: {").append(s3).append("}").append(s4).append(" = '").append(s5).append("'").toString());
                BodyQName bodyqname1 = BodyQName.create(s3, s4);
                result.addBodyAttributeValue(bodyqname1, s5);
            }

            parser.reset();
        }

        public void startPrefixMapping(String s, String s1)
        {
            if(s.length() != 0) goto _L2; else goto _L1
_L1:
            if(BodyParserSAX.LOG.isLoggable(Level.FINEST))
                BodyParserSAX.LOG.finest((new StringBuilder()).append("Prefix mapping: <DEFAULT> => ").append(s1).toString());
            defaultNS = s1;
_L4:
            return;
_L2:
            if(BodyParserSAX.LOG.isLoggable(Level.FINEST))
                BodyParserSAX.LOG.info((new StringBuilder()).append("Prefix mapping: ").append(s).append(" => ").append(s1).toString());
            if(true) goto _L4; else goto _L3
_L3:
        }

        private String defaultNS;
        private final SAXParser parser;
        private final BodyParserResults result;

        private Handler(SAXParser saxparser, BodyParserResults bodyparserresults)
        {
            defaultNS = null;
            parser = saxparser;
            result = bodyparserresults;
        }

    }


    BodyParserSAX()
    {
    }

    private static SAXParser getSAXParser()
    {
        SAXParser saxparser = (SAXParser)((SoftReference)PARSER.get()).get();
        if(saxparser != null) goto _L2; else goto _L1
_L1:
        saxparser = SAX_FACTORY.newSAXParser();
        SoftReference softreference = new SoftReference(saxparser);
        PARSER.set(softreference);
_L3:
        return saxparser;
        Object obj;
        obj;
_L4:
        throw new IllegalStateException("Could not create SAX parser", ((Throwable) (obj)));
_L2:
        saxparser.reset();
          goto _L3
        obj;
          goto _L4
    }

    public BodyParserResults parse(String s)
        throws BOSHException
    {
        BodyParserResults bodyparserresults = new BodyParserResults();
        ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(s.getBytes());
        SAXParser saxparser = getSAXParser();
        saxparser.parse(bytearrayinputstream, new Handler(saxparser, bodyparserresults));
        return bodyparserresults;
        Object obj;
        obj;
_L2:
        throw new BOSHException((new StringBuilder()).append("Could not parse body:\n").append(s).toString(), ((Throwable) (obj)));
        obj;
        if(true) goto _L2; else goto _L1
_L1:
    }

    private static final Logger LOG = Logger.getLogger(com/kenai/jbosh/BodyParserSAX.getName());
    private static final ThreadLocal PARSER = new ThreadLocal() {

        protected volatile Object initialValue()
        {
            return initialValue();
        }

        protected SoftReference initialValue()
        {
            return new SoftReference(null);
        }

    }
;
    private static final SAXParserFactory SAX_FACTORY;

    static 
    {
        SAX_FACTORY = SAXParserFactory.newInstance();
        SAX_FACTORY.setNamespaceAware(true);
        SAX_FACTORY.setValidating(false);
    }

}
