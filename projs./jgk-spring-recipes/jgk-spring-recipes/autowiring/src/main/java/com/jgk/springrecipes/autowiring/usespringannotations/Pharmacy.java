package com.jgk.springrecipes.autowiring.usespringannotations;

import org.springframework.beans.factory.annotation.Autowired;

public class Pharmacy {
	
	PharmacyProvider provider;

	@Override
	public String toString() {
		return "Pharmacy [provider=" + provider + "]";
	}

	public PharmacyProvider getProvider() {
		return provider;
	}

	public void setProvider(PharmacyProvider _provider) {
		System.out.println("SETTING PROVIDER " + _provider);
		this.provider = _provider;
	}
}
