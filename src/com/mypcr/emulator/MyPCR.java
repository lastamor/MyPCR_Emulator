package com.mypcr.emulator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class MyPCR extends Thread {

	//firmware source 황용하여
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
			return "준비중";
		else
			return "동작중";				
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
		System.out.println(String.format("상태: %s,  온도: %3.1f, elapsedTime : %s", getStateString(),mTemp, getElasedTime()));
	}
	
	public void startPCR(){
		if(state == STATE_RUN)
			return;
		state = STATE_RUN;
		mPreTemp = 50;
		mTargetTemp = 95;
		System.out.println("PCR 시작!");
		
	}
	
	public void stopPCR(){
		if(state == STATE_READY)
			return;
		state = STATE_READY;
		System.out.println("PCR 종료!");
		
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
1. startPCR(), stropPCR() 시, PCR 시작!, PCR 종료!
2. start 시, mPrevTemp = 95.0, mTargetTemp = 50.0 으로 변경,
	run() 함수 내에서 mPrevTemp 까지 온도 도달 시키고,
	그 후에 mTargetTemp 까지 온도가 도달하도록 구현,
	mTargetTemp 까지 온도가 도달할 경우, 자동으로 Stop 되도록 구현
	
3. startPCR(), stopPCR() 시, 현재 상태가 맞지 않은 경우 그냥 무시하도록 구현,

	ex) start를 했는데, state가 이미 RUN 이면 그냥 return  하도록 구현,
	stop 도 마찬가지로....
	
4. private int mElapsedTime 선언 후 , 0으로 초기화,
	run() 함수에서, state 가 RUN 일 경우, 1sec 마다 증가시킴,
	printstatus() 함수에서 String.format"상태: %s,  온도: %3.1f", elapsedTime: %s", getStateString(),mTemp, getelapsedTime)); 으로 구현
	결과물은 다음과 같음: 상태: 동작중, 온도: 78.1, elapsedTime: 1m 2s
	run() 함수에서의 mTemp += 0.01; 로 변경하세요.
	getElapsedTime() 함수는 다음과 같이 선언하여 구현하세요.
	private String getElapsedTime()
	

**/
