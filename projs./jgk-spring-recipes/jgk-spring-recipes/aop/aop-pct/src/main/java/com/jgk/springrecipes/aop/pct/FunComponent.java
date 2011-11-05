package com.jgk.springrecipes.aop.pct;

import org.springframework.stereotype.Component;

@Component
public class FunComponent {

	public void saveNoParams() {
		System.out.println("I LOVE SAVING FIRST without params");
	}
	public void saveOneParam(String stuffToSave) {
		System.out.println("I LOVE SAVING with one params");
	}
	public void getKangaroo() {
		System.out.println("LOVE THOSE ROOs");
	}
}
