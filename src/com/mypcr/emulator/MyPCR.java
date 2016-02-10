package com.mypcr.emulator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class MyPCR extends Thread {

	//firmware source Ȳ���Ͽ�
	//
	private double mTemp;
	private double mPreTemp, mTargetTemp;
	private int state;
	private int mElapsedTime;
	private int mSecondCount;
	private boolean isMonitor = false;
	
	private static final int STATE_READY = 0x00;
	private static final int STATE_RUN = 0x01;
	
	private static final double DEFAULT_TEMP = 25.1;
	

	
	public MyPCR(){
		mTemp = 25.1;
		mPreTemp = 25.1;
		mTargetTemp = 25.1;
		state = STATE_READY;
		mElapsedTime = 0;
		mSecondCount = 0;
	}
	
	public void run(){
		
		while(true){
			//
			
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if(state == STATE_RUN){
				if(mTemp <= mPreTemp){
					mTemp += 1.0;	
					}
				else{
					if(mTemp <= mTargetTemp)
						mTemp += 1.0;	
					else
						stopPCR();			
				}
				
				mElapsedTime++;	
				
		
			}else{
			
				mTemp = DEFAULT_TEMP;
				mPreTemp = DEFAULT_TEMP;
				mTargetTemp = DEFAULT_TEMP;
				mElapsedTime = 0;
				
			}		
		}
	}
	
	public ArrayList<Protocol> makeProtocolList(String pcr){
		if(pcr ==null)
			return null;
		
		ArrayList<Protocol> list = new ArrayList<Protocol>();	
		
		String[] pcrs = pcr.split("\n");
		
		
		for (int i = 0; i < pcrs.length; i++) {
			String temp = pcrs[i];
			String[] temp2 = temp.split("\t");
			
		try {
				Protocol p = new Protocol( temp2[0], Integer.parseInt(temp2[1]),  Integer.parseInt(temp2[2]));
				list.add(p);
			} catch (NumberFormatException e) {
				return null;
			} catch (ArrayIndexOutOfBoundsException e){
				return null;
			}
		}			
		
		return list;
	}
	
	public  void printProtocolList(ArrayList<Protocol> pcr){
		System.out.println("==== Protocol List ====");
		System.out.println("Label"+"\t"+ "Temp" + "\t" + "Time");
		for (int i = 0; i < pcr.size(); i++) {
			Protocol p = pcr.get(i);
			System.out.println( p.getLabel() + "\t"+
			                    p.getTemp()  + "\t"+ 
								p.getTime());	
		}
		
	}

	public  void printProtocolList2(ArrayList<Protocol> pcr){
		System.out.println("==== Protocol List ====");
		System.out.println("Label"+"\t"+ "Temp" + "\t" + "Time");
		for (int i = 0; i < pcr.size(); i++) {
			Protocol p = pcr.get(i);
			System.out.println( p.getLabel() + "\t"+
			                    p.getTemp()  + "\t"+ 
								p.getTime());	
		}
		
	}
	
	public String loadProtocolFromFile(String path){
		
		BufferedReader in = null;
		String line = null;
		String line2 = "";

		try {
			in = new BufferedReader(new FileReader(new File(path)));
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		

		try{
			
			while ((line = in.readLine()) != null) {
				
				line2 = line2 + line + "\n";
			}
			in.close();
		
			
		}catch(IOException e){
			e.printStackTrace();
			return null;
		}
		
		return line2;
	}
	
	private String getStateString(){
		
		if (state==STATE_READY)
			return "�غ���";
		else
			return "������";				
	}
	
	private String getElasedTime(){

		String str = "";
		int seconds = 0;
		int houre = 0;
		int minutes = 0;
		
		
		houre = mElapsedTime / 3600;
		seconds = mElapsedTime % 3600;
		minutes = seconds / 60;
		seconds = seconds % 60;
		

		
		if (houre != 0 ) {
			str = String.format("%dh %dm %ds",houre,minutes,seconds);
		}
		else if (minutes != 0) {
			str = String.format("%dm %ds",minutes,seconds);
		} else {
			str = String.format("%ds",seconds);
		}

		
		
		return str;
		
	}
	
	public void printStatus(){
		 Calendar time1 = Calendar.getInstance();
		System.out.println(String.format("����: %s,  �µ�: %3.1f, elapsedTime : %s", getStateString(),mTemp, getElasedTime()));
	}
	
	public void startPCR(){
		if(state == STATE_RUN)
			return;
		state = STATE_RUN;
		mPreTemp = 50;
		mTargetTemp = 95;
		System.out.println("PCR ����!");
		
	}
	
	public void stopPCR(){
		if(state == STATE_READY)
			return;
		state = STATE_READY;
		System.out.println("PCR ����!");
		
	}
	
	public void setMonitoring(boolean monitor){
		isMonitor = monitor;
	}
	public boolean isMonitoring(){
		return isMonitor;
	}

//	private
}


/**
1. startPCR(), stropPCR() ��, PCR ����!, PCR ����!
2. start ��, mPrevTemp = 95.0, mTargetTemp = 50.0 ���� ����,
	run() �Լ� ������ mPrevTemp ���� �µ� ���� ��Ű��,
	�� �Ŀ� mTargetTemp ���� �µ��� �����ϵ��� ����,
	mTargetTemp ���� �µ��� ������ ���, �ڵ����� Stop �ǵ��� ����
	
3. startPCR(), stopPCR() ��, ���� ���°� ���� ���� ��� �׳� �����ϵ��� ����,

	ex) start�� �ߴµ�, state�� �̹� RUN �̸� �׳� return  �ϵ��� ����,
	stop �� ����������....
	
4. private int mElapsedTime ���� �� , 0���� �ʱ�ȭ,
	run() �Լ�����, state �� RUN �� ���, 1sec ���� ������Ŵ,
	printstatus() �Լ����� String.format"����: %s,  �µ�: %3.1f", elapsedTime: %s", getStateString(),mTemp, getelapsedTime)); ���� ����
	������� ������ ����: ����: ������, �µ�: 78.1, elapsedTime: 1m 2s
	run() �Լ������� mTemp += 0.01; �� �����ϼ���.
	getElapsedTime() �Լ��� ������ ���� �����Ͽ� �����ϼ���.
	private String getElapsedTime()
	

**/
