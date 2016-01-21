package com.mypcr.emulator;

public class Protocol {
	private String label;
	private int temp, time;

	public Protocol(String label, int temp, int time) {
		this.label = label;
		this.temp = temp;
		this.time = time;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getTemp() {
		return temp;
	}

	public void setTemp(int temp) {
		this.temp = temp;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
	
	
}
