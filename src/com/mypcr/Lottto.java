package com.mypcr;

public class Lottto {

	//print
	public int [] lotto_generation()
	{
		int []a = new int[7];
		
		for (int i = 0; i < a.length; i++) {
			int res = (int)(Math.random()*45)+1;
			
			a[i] = res;
			
			for (int j = 0; j < i; j++) {
				if (res  == a[j]) {
					//System.out.println("숫자가 중복 되었습니다.다른 숫자를 입력하세요");
					i--;
					break;
				}
			}
		}
		return a;
	}
	
	
	public void number_comparison( int []a1, int []a2)
	{
		int res=0;
		
		for (int i = 0; i < a1.length; i++) {
			for (int j = 0; j < a1.length; j++) {
				if(a1[i] == a2[j]){
					res++;
					break;
				}
			}
			
		}
		System.out.print("등수: ");
		if (res != 0) {
			String str = new String(8-res + "등 (" + res +"개 맞품)");
			System.out.println(str);
		} else {
			System.out.println("- 꽝 -");
		}
		
	}
	
	public void lotto_print (int []a0) {

		for (int i=0; i<a0.length; i++)
		{
			if (i == a0.length -1 ) {
				System.out.println(a0[i]);
			} else {
				System.out.print(a0[i] + ", ");
			}
		}
	}
	

	
}

