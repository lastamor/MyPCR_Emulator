package com.mypcr;

import java.util.ArrayList;

import com.mypcr.emulator.Protocol;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String pcr = "1	95	10\n" +
					 "2	60	30\n" + 
					 "3	72	30\n";
		String[] pcrs = pcr.split("\n");
		ArrayList<Protocol> list = new ArrayList<Protocol>();

		for (int i = 0; i < pcrs.length; i++) {
			String temp = pcrs[i];
			String[] temp2 = temp.split("\t");
			
		//	System.out.println( temp2[0] + "," + temp2[1] + "," +temp2[2]);
			Protocol p1 = new Protocol( temp2[0], Integer.parseInt(temp2[1]),  Integer.parseInt(temp2[2]));
			list.add(p1);
			
		}		

		
		System.out.println("==== Protocol List ====");
		System.out.println("Label"+"\t"+ "Temp" + "\t" + "Time");
		for (int i = 0; i < list.size(); i++) {
			Protocol p = list.get(i);
			System.out.println( p.getLabel() + "\t"+
			                    p.getTemp()  + "\t"+ 
								p.getTime());	
		}
	}
}
