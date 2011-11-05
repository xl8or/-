package com.jgk.springrecipes.jms;

import java.util.List;

public interface ClampettFamilyBatchProcessor {
	void processBatch(List<String> batch);
}
