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
				  
	1. MyPCR�� ������ ���� �ϼ��� ���弼��.
		public ArrayList<Protocol> makeProtocolList(String pcr)
		���� �Լ��� ������ ������ pcr line ���� �о pcr list�� ����� ������ �̿��ؾ� �մϴ�.
		(���� �б� �κ� ����)
		�Ű� ���� pcr�� ���� pcr1, pcr2 �� ����ϸ� �˴ϴ�.
		
	2. (1) �� ������ ���� ��, ������ �߻��մϴ�.
		���� �߿� NumberFormatExeption �Ǵ� ArrayOutofIndexBound ������ �߻��� ���,
		"�߸��� PCR �����Դϴ�" ��� ��� �Ŀ� return null; �� ȣ���� �ּ���.
		
	3.	public void showProtocolList(ArrayList<Protocol> list);
		���� �Լ��� ������ ������ �������� ��� �κ��� �̿��ϸ� �˴ϴ�.
		�� �Ű������� ���� list�� null ���� üũ�ϰ�, null �� ���, "�߸�� PCR ���� ����Ϸ� �߽��ϴ�."
		��� ��� �Ŀ� return;�� ȣ�����ּ���.
 */ 
