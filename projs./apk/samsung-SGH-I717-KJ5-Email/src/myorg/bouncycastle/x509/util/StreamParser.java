package myorg.bouncycastle.x509.util;

import java.util.Collection;
import myorg.bouncycastle.x509.util.StreamParsingException;

public interface StreamParser {

   Object read() throws StreamParsingException;

   Collection readAll() throws StreamParsingException;
}
