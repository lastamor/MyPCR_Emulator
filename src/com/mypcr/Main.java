package com.mypcr;

import java.util.ArrayList;

import com.mypcr.emulator.Protocol;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<Protocol> list = new ArrayList<Protocol>();
		Protocol p1 = new Protocol("1", 20, 20);
		list.add(p1);
		
		System.out.println("==== Protocol List ====");
		System.out.println("Label"+"\t"+ "Temp" + "\t" + "Time");
		for (int i = 0; i < list.size(); i++) {
			Protocol p = list.get(i);
			System.out.println( p.getLabel() + "\t" +
			                    p.getTemp() + "\t"+ 
								p.getTime());	
		}
	}
}
