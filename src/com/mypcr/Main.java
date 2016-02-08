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
	
		final MyPCR pcr = new MyPCR();
		pcr.start();
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		
		while (true) {

			try {
				String cmd = in.readLine();

						
				if(cmd.equals("start")){
					pcr.startPCR();
				}
				else if(cmd.equals("stop")){
					pcr.stopPCR();
				}
					
				else if(cmd.equals("print")){
					pcr.printStatus();
				}
				else if(cmd.equals("monitor")){		
					pcr.setMonitoring(true);
					Thread t = new Thread(){
						public void run(){
							while(pcr.isMonitoring()){
								try {
									Thread.sleep(1000);
									pcr.printStatus();
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					};
					t.start();
					in.readLine();
					pcr.setMonitoring(false);
				}
				
			} catch (IOException e) {
				e.printStackTrace();

			}

		}

		
		
		/*
		String data = pcr.loadProtocolFromFile("protocol.txt");
		
		ArrayList<Protocol> list = p.makeProtocolList(data);
		
		p.printProtocolList(list);

		*/
	}
}
