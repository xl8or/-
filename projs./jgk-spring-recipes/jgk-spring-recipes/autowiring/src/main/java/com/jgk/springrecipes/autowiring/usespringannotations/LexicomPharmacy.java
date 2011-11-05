package com.jgk.springrecipes.autowiring.usespringannotations;

import org.springframework.beans.factory.annotation.Qualifier;

public class LexicomPharmacy extends Pharmacy {

	@Override
	@Qualifier("lexicomPharmacyProvider")
	public void setProvider(PharmacyProvider provider) {
		super.setProvider(provider);
	}

	
}
