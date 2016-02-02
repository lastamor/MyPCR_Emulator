package com.mypcr;

import java.awt.im.InputContext;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.mypcr.emulator.MyPCR;
import com.mypcr.emulator.Protocol;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
		//BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader in = null;
		ArrayList<Protocol> list = new ArrayList<Protocol>();		
		
		try {
			in = new BufferedReader(new FileReader(new File("procotol.txt")));
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	
		try{
		
			String line = null;
			String line2 = "";
			while ((line = in.readLine()) != null) {
				
				line2 = line2 + line + "\n";
			}
			in.close();
		

			list = MyPCR.makeProtocolList(line2);
		
			
			for (int i = 0; i < list.size(); i++) {
				Protocol protocol = list.get(i);
				
				if(protocol.getLabel().equals("GOTO")){
					
					int target = protocol.getTemp();
					int time = protocol.getTime();
					protocol.setTime(time-1);
					
					if(time-1 != 0){
						i= target-2;
					}
				}
				else{
					System.out.println( protocol.getLabel() + "\t"+
							protocol.getTemp()  + "\t"+ 
							protocol.getTime());	
				}
				
			}
		
			
			MyPCR p = new MyPCR();
			
			
			
		}catch(IOException e){
			e.printStackTrace();
		}
		


		
		/*		
		
		ArrayList<Protocol> list = new ArrayList<Protocol>();

		for (int i = 0; i < pcr_0.length; i++) {
			String temp = pcr_0[i];
			String[] temp2 = temp.split("\t");
			
		//	System.out.println( temp2[0] + "," + temp2[1] + "," +temp2[2]);
			Protocol p = new Protocol( temp2[0], Integer.parseInt(temp2[1]),  Integer.parseInt(temp2[2]));
			list.add(p);
		}		
		
		System.out.println("==== Protocol List ====");
		System.out.println("Label"+"\t"+ "Temp" + "\t" + "Time");
		for (int i = 0; i < list.size(); i++) {
			Protocol p = list.get(i);
			System.out.println( p.getLabel() + "\t"+
			                    p.getTemp()  + "\t"+ 
								p.getTime());	
		}
		
		*/
		
	}
}

/*
 * 
 
	String pcr1 = "1	95	10\n" +
				  "2	kk	30\n" + 
				  "3	72	30\n";
				  
 	String pcr1 = "1	95	10\n" +
				  "2	60	30\n" + 
				  "3	72\n";
				  
	1. MyPCR에 다음과 같은 하수를 만드세요.
		public ArrayList<Protocol> makeProtocolList(String pcr)
		위의 함수는 이전에 구현한 pcr line 값을 읽어서 pcr list를 만드는 예제를 이용해야 합니다.
		(파일 읽기 부분 구현)
		매개 변수 pcr은 위의 pcr1, pcr2 를 사용하면 됩니다.
		
	2. (1) 번 예제를 돌리 시, 에러가 발생합니다.
		에레 중에 NumberFormatExeption 또는 ArrayOutofIndexBound 에러가 발생할 경우,
		"잘못된 PCR 파일입니다" 라고 출력 후에 return null; 을 호출해 주세요.
		
	3.	public void showProtocolList(ArrayList<Protocol> list);
		위의 함수는 이전에 구현한 프로토콜 출력 부분을 이용하면 됩니다.
		단 매개변수로 받은 list가 null 인지 체크하고, null 일 경우, "잘목된 PCR 파일 출력하려 했습니다."
		라고 출력 후에 return;을 호출해주세요.
 */ 
