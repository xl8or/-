package com.jgk.springrecipes.autowiring.usespringannotations;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

/**
 * Shows @Qualifier and @Resource equivalent
 * Notice: you can mix usage of @Qualifier and @Resource - though you might question why?
 * @author jkroub
 *
 */
public class FirstDatabankPharmacy extends Pharmacy {
	PharmacyProvider lexProvider;
	
	@Value("FREDY")
	String someInfo;
	
	@Value("#{systemProperties['FUZZY']}")
	String fuzzyInfo;
	
	@Override
	@Autowired
	//@Qualifier("fdbPharmacyProvider")
	@Resource(name="fdbPharmacyProvider")
	public void setProvider(PharmacyProvider provider) {
		System.out.println("provider===="+provider);
		super.setProvider(provider);
	}

	@Autowired
	@Qualifier("lexicomPharmacyProvider")
	//@Resource(name="lexicomPharmacyProvider")
	public void setLexProvider(PharmacyProvider provider) {
		System.out.println("lexic provider===="+provider);
		this.lexProvider=provider;
	}

	public PharmacyProvider getLexProvider() {
		return lexProvider;
	}

	@Override
	public String toString() {
		return "FirstDatabankPharmacy [fuzzyInfo="+fuzzyInfo+", someInfo="+someInfo+", lexProvider=" + lexProvider + "]" + super.toString();
	}
	
	

	
}
