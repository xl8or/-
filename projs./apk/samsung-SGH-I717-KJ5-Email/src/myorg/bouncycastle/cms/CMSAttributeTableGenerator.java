package myorg.bouncycastle.cms;

import java.util.Map;
import myorg.bouncycastle.asn1.cms.AttributeTable;
import myorg.bouncycastle.cms.CMSAttributeTableGenerationException;

public interface CMSAttributeTableGenerator {

   String CONTENT_TYPE = "contentType";
   String DIGEST = "digest";
   String DIGEST_ALGORITHM_IDENTIFIER = "digestAlgID";
   String SIGNATURE = "encryptedDigest";


   AttributeTable getAttributes(Map var1) throws CMSAttributeTableGenerationException;
}
