import java.io.BufferedReader;
import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class NNSolutionTwo {

	public static void main(String[] args) throws IOException {
		ArrayList<String> input = new ArrayList<>();
		//Sulyok
		ArrayList<double[]> weightList = new ArrayList<>();
		//
		ArrayList<Double> bias = new ArrayList<>();
		//Bemenetek szama
		int inputNumber;
		
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("input2.txt")));
		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try{
	   		while(true){
	   			String line = br.readLine();
	   			if(line == null)
	   				break;
	   			input.add(line);
	   		}
	   		br.close();
		}
		catch(IOException e){
			System.out.println(e);
		}
		
		//Architektura init
		String[] architectureString = input.get(0).split(",");
		int[] architecture = new int[architectureString.length];
		
		for(int i=0; i<architectureString.length; i++) {
			architecture[i] = Integer.parseInt(architectureString[i]);
			if(i<architectureString.length-1)
				System.out.print(architecture[i]+",");
			else
				System.out.print(architecture[i]);
		}
		System.out.print("\n");
		
		//Bemenetek szama
		inputNumber = architecture[0];
		
		for(int i=1; i< 5; i++) {
			String[] weightStr = input.get(i).split(",");
			double[] weight = new double[weightStr.length-1];
			for(int j=0; j<weightStr.length-1; j++) {
				weight[j]=Double.parseDouble(weightStr[j]);
			}
			weightList.add(weight);
			bias.add(Double.parseDouble(weightStr[weightStr.length-1]));
				
		}
		
		
//<-----Neuronok letrehozasa------------------------------------------------------------------------------------------------------------------>
		
		ArrayList<Neuron> neuronList = new ArrayList<>();
		/*
		for(int i=1; i<architecture.length; i++) {
			for(int j=0; j<architecture[i]; j++) {
				neuronList.add(new Neuron(architecture[i-1]));
			}
		}*/
		/*
		for(int i=0; i<neuronList.size(); i++) {
			if(i<neuronList.size()-1) {
				neuronList.get(i).print();
				System.out.print("\n");
			}
			else
				neuronList.get(i).print();
		}
		*/
		for(int i=0; i<weightList.size(); i++) {
			printWeight(weightList.get(i), bias.get(i));
		}
		
	}
	
	public static double[] fillRandom(int size) {
		Random rand = new Random();
		double[] result = new double[size];
		
		for(int i = 0; i<size; i++)
			result[i] = rand.nextGaussian()*0.1;
		
		return result;
	}

	public static void printWeight(double[] weight, double bias) {
		for(int i=0; i<weight.length; i++)
			System.out.print(weight[i]+",");
		System.out.print(bias+"\n");
	}
	
}
