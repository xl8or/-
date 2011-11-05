package com.jgk.springrecipes.blackbelt.javaconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jgk.springrecipes.blackbelt.javaconfig.beans.SomeBeanToBeUsed;
import com.jgk.springrecipes.blackbelt.javaconfig.beans.YetSomeOtherBean;

@Configuration
public class MyConfig {
	
	@Bean
	public SomeBeanToBeUsed someBeanToBeUsed() {
		SomeBeanToBeUsed someBeanToBeUsed = new SomeBeanToBeUsed();
		someBeanToBeUsed.setYetSomeOtherBean(yetSomeOtherBean());
		return someBeanToBeUsed;
	}
	@Bean
	public YetSomeOtherBean yetSomeOtherBean() {
		return new YetSomeOtherBean();
	}
}
