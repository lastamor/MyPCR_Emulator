package com.mypcr;

public class Lottto {

	
	public int [] lotto_print()
	{
		int []a5 = new int[7];
		for (int i = 0; i < a5.length; i++) {
			int res = (int)(Math.random()*45)+1;
			
			a5[i] = res;
			
			for (int j = 0; j < i; j++) {
				if (res  == a5[j]) {
					System.out.println("숫자가 중복 되었습니다.다른 숫자를 입력하세요");
					i--;
					break;
				}
			}
			
			
		}
		return a5;
	}
	
}

