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
	private int mArrangeCount;
	private boolean isMonitor = false;
	private boolean mTempFalg = false;
	private boolean mTempTargetFalg = false;
	
	private static final int STATE_READY = 0x00;
	private static final int STATE_RUN = 0x01;
	
	private static final double DEFAULT_TEMP = 25.1;
	
	
	private static final double temps[] = { 95.5, 72.0, 85.0, 50.0, 4.0}; 

	
	
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
	
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if(state == STATE_RUN){
		
				if( mTargetTemp - mPreTemp< 0){
					mTemp -= 1.0;
					if(mTemp < mTargetTemp)
						mTempTargetFalg = true;
				}	
				else {
					mTemp += 1.0;
					if(mTemp > mTargetTemp)
						mTempTargetFalg = true;
				}
	
				if(mTempTargetFalg){
					mTempTargetFalg = false;
					mArrangeCount++;
					mPreTemp = mTargetTemp;
					if(temps.length <= mArrangeCount){
						stopPCR();
					}else{
						mTargetTemp = temps[mArrangeCount];
					}
				}
				/*
				if(mTemp > mTargetTemp && !mTempFalg )
				{
					mArrangeCount++;
					mPreTemp = mTargetTemp;
					if(temps.length < mArrangeCount){
						stopPCR();
					}else{
						mTargetTemp = temps[mArrangeCount];
					}

				}
				if(mTemp < mTargetTemp && mTempFalg ){
					mArrangeCount++;
					mPreTemp = mTargetTemp;
					if(temps.length <= mArrangeCount){
						stopPCR();
					}else{
						mTargetTemp = temps[mArrangeCount];
					}
				}
			*/
					
				
				// 1. 배열 읽어 오기
				// 2. 첫번째 배열 값을 mTargetTemp에 넣기
				// 3. mPreTemp - mTargetTemp 비교 해서 0보다 크면 1도식 증가&&mTempFlag =false 작으면 1도씩 감소 &&mTempFlag =true
				// 4. mTargetTemp 도달 후 다음 배열로 이동
				// 5. mPreTemp을 mTargetTemp에 넣고 mTargetTemp를 다음 온도에 넣는다.
			
				
						
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
		mPreTemp = DEFAULT_TEMP;
		mArrangeCount = 0;
		mTargetTemp = temps[mArrangeCount];
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


