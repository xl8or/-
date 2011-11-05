package myorg.bouncycastle.util;

import java.util.Collection;
import myorg.bouncycastle.util.StreamParsingException;

public interface StreamParser {

   Object read() throws StreamParsingException;

   Collection readAll() throws StreamParsingException;
}
