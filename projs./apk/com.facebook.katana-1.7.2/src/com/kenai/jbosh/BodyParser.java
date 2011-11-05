package com.kenai.jbosh;

import com.kenai.jbosh.BOSHException;
import com.kenai.jbosh.BodyParserResults;

interface BodyParser {

   BodyParserResults parse(String var1) throws BOSHException;
}
