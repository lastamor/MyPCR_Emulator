package com.mypcr.emulator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;

public class MyPCR extends Thread {

	//firmware source Ȳ���Ͽ�
	//
	private double mTemp;
	private double mPreTemp, mTargetTemp;
	private int mTime;
	private int state;
	private int mElapsedTime;
	private int mSecondCount;
	private int mArrangeCount;
	private boolean isMonitor = false;
	private boolean mTempFalg = false;
	private boolean mTempTargetFalg = false;
	private boolean mTimeTargetFalg = false;
	
	private ArrayList<Protocol> mProtocolList;
	private String mLabel ="";
	
	private int  mPriticilSize;
	
	private static final int STATE_READY = 0x00;
	private static final int STATE_RUN = 0x01;
	
	private static final double DEFAULT_TEMP = 25.1;
	
	
	private static final double temps[] = { 95.5, 72.0, 85.0, 50.0, 4.0}; 

	
	
	public MyPCR(){
		mTemp = 25.1;
		mPreTemp = 25.1;
		mTargetTemp = 25.1;
		mTime = 0;
		state = STATE_READY;
		mElapsedTime = 0;
		mSecondCount = 0;
		
		String data = loadProtocolFromFile("protocol.txt");
		mProtocolList = makeProtocolList(data);
		
		mPriticilSize = mProtocolList.size();
		
		//���� 1
		//private ArrayList<Protocol> mProtocolList; //��� ���� ����
		
		//���� ����Ʈ�� protocol.txt ���Ͽ��� ���� PCR ������ �ҷ��ͼ� �����ϱ�.
		//�� �۾���  public MyPCR() �����ڿ��� ����
		//��� ���� ����(���� �����ϰ� �ִ� ���������� ����Ͻÿ�)
		//Label :%s, TargetTemp : %3.1f, Reamin: %d ����: %s,  �µ�: %3.1f, elapsedTime : %s", getStateString(),mTemp, getElasedTime()));
		//����, PCR �� ���������� ���� ���, ���� ��� ���¸� ���� �ϼ���.
		
		
		//���� 2
		//microPCR_Firmware ���� branch �� pic18f4550_emul�� �����ϼ���
		//pwmValue ����ϴ� �κ� �����Ͽ� �µ� ���� �� ���ϱ�.
		
		//
		
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
					if(!mTempTargetFalg)
						mTemp -= 0.5;
					if(mTemp < mTargetTemp)
						mTempTargetFalg = true;
				}	
				else {
					if(!mTempTargetFalg)
						mTemp += 0.5;
					if(mTemp > mTargetTemp)
						mTempTargetFalg = true;	
				}
				
				if(mTempTargetFalg){
					if(mTime == 0){
						mSecondCount = 0;
						mTimeTargetFalg = true;
					}
					else{
						if(mSecondCount++ >= 2)
						{
							mSecondCount=0;
							mTime--;
						}
					}
				}
	
				if(mTimeTargetFalg && mTempTargetFalg){
					mTempTargetFalg = false;
					mTimeTargetFalg = false;
					mArrangeCount++;
					mPreTemp = mTargetTemp;
					if(mPriticilSize <= mArrangeCount){
						stopPCR();
						//continue;
					}else{
						String str= mProtocolList.get(mArrangeCount).getLabel();
						if(str.equals("GOTO"))
						{
							int target = mProtocolList.get(mArrangeCount).getTemp(); 
							int time = mProtocolList.get(mArrangeCount).getTime();
							mProtocolList.get(mArrangeCount).setTime(time-1);
							
							if(time-1 != 0){
								mArrangeCount = target-1;
							} else
							{
								mArrangeCount++;
							}
						} 
						System.out.println(mArrangeCount);
						mTargetTemp = mProtocolList.get(mArrangeCount).getTemp();
						mTime = mProtocolList.get(mArrangeCount).getTime();
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
					
				
				// 1. �迭 �о� ����
				// 2. ù��° �迭 ���� mTargetTemp�� �ֱ�
				// 3. mPreTemp - mTargetTemp �� �ؼ� 0���� ũ�� 1���� ����&&mTempFlag =false ������ 1���� ���� &&mTempFlag =true
				// 4. mTargetTemp ���� �� ���� �迭�� �̵�
				// 5. mPreTemp�� mTargetTemp�� �ְ� mTargetTemp�� ���� �µ��� �ִ´�.
			
				
						
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
		 if(state == STATE_RUN)
		 {
			 System.out.println(String.format("Label :%s, TargetTemp : %3.1f, Reamin: %d, ����: %s,  �µ�: %3.1f, "
			 		+ "elapsedTime : %s", mProtocolList.get(mArrangeCount).getLabel(), mTargetTemp, mTime,getStateString(), mTemp, getElasedTime()));
		 } else
			 System.out.println(String.format("����: %s,  �µ�: %3.1f, elapsedTime : %s", getStateString(), mTemp, getElasedTime()));

	}
	
	public void startPCR(){
		if(state == STATE_RUN)
			return;
		state = STATE_RUN;
		mPreTemp = DEFAULT_TEMP;
		mArrangeCount = 0;
		mTargetTemp = mProtocolList.get(mArrangeCount).getTemp();
		mTime = mProtocolList.get(mArrangeCount).getTime();
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


