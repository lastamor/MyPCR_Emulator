package com.mypcr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;

import com.mypcr.emulator.MyPCR;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Lottto lotto = new Lottto();	
		
		int []a0 =  lotto.lotto_generation();
		
		int []a1 = lotto.lotto_generation();
		
		int []a2 = lotto.lotto_generation();
		
		int []a3 = lotto.lotto_generation();

		quickSort(a0, 0, a0.length-1);
		quickSort(a1, 0, a0.length-1);
		quickSort(a2, 0, a0.length-1);
		quickSort(a3, 0, a0.length-1);
		
		System.out.println("** ´ç Ã· ¹ø È£ **");
		lotto.lotto_print(a0);
		
		System.out.println("1st LOTTO Number");
		lotto.lotto_print(a1);
		lotto.number_comparison(a0, a1);
	
		System.out.println("2st LOTTO Number");
		lotto.lotto_print(a2);
		lotto.number_comparison(a0, a2);
		
		System.out.println("3st LOTTO Number");
		lotto.lotto_print(a3);
		lotto.number_comparison(a0, a3);
		
	}
	
	public static void quickSort(int arr[], int left, int right) {
	      int index = partition(arr, left, right);
	      if (left < index - 1)
	            quickSort(arr, left, index - 1);
	      if (index < right)
	            quickSort(arr, index, right);
	}
	
	static int partition(int arr[], int left, int right)
	{
	      int i = left, j = right;
	      int tmp;
	      int pivot = arr[(left + right) / 2];
	     
	      while (i <= j) {
	            while (arr[i] < pivot)
	                  i++;
	            while (arr[j] > pivot)
	                  j--;
	            if (i <= j) {
	                  tmp = arr[i];
	                  arr[i] = arr[j];
	                  arr[j] = tmp;
	                  i++;
	                  j--;
	            }
	      };
	     
	      return i;
	}

}
