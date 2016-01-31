package com.mypcr;

import java.util.ArrayList;

import com.mypcr.emulator.MyPCR;
import com.mypcr.emulator.Protocol;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<Protocol> list0 = new ArrayList<Protocol>();
		ArrayList<Protocol> list1 = new ArrayList<Protocol>();
		ArrayList<Protocol> list2 = new ArrayList<Protocol>();
		
		String pcr = "1	95	10\n" +
					 "2	60	30\n" + 
					 "3	72	30\n";
		
		String pcr1 =   "1	95	10\n" +
				 		"2	kk	30\n" + 
				 		"3	72	30\n";
	
		String pcr2 =   "1	95	10\n" +
						"2	60	30\n" + 
						"3	72\n";
		
		list0 = MyPCR.makeProtocolList(pcr);
		list1 = MyPCR.makeProtocolList(pcr1);
		list2 = MyPCR.makeProtocolList(pcr2);
		
		if (list0 == null)
			System.out.println("�߸��� PCR ������ ����Ϸ� �߽��ϴ�.");
		else
			MyPCR.printProtocolList2(list0);
		if (list1 == null)
			System.out.println("�߸��� PCR1 ������ ����Ϸ��߽��ϴ�.");
		else
			MyPCR.printProtocolList2(list1);
		if (list2 == null)
			System.out.println("�߸��� PCR2 ������ ����Ϸ� �߽��ϴ�.");
		


		
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
