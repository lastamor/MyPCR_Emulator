package com.mypcr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.mypcr.emulator.MyPCR;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int a1 = 25;
		int a2 = 26;
		int a3 = 27;
		int a4 = 29;
						
		Lottto lotto = new Lottto();
		
		
		int []a5 =  lotto.lotto_print();
		
		for (int i : a5)
			System.out.println(i);

		//System.out.println((int)(Math.random()*45)+1);

		
	}

		
		

}
