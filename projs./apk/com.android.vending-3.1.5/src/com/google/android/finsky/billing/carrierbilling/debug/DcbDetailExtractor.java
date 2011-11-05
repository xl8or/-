package com.google.android.finsky.billing.carrierbilling.debug;

import com.google.android.finsky.billing.carrierbilling.debug.DcbDetail;
import java.util.Collection;

public interface DcbDetailExtractor {

   Collection<DcbDetail> getDetails();
}
