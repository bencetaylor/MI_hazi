import java.io.BufferedReader;
import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class NNSolutionTwo {

	public static void main(String[] args) throws IOException {
		ArrayList<String> inputStr = new ArrayList<>();
		//Sulyok
		ArrayList<double[]> weightList = new ArrayList<>();
		//Bias ertekek
		ArrayList<Double> bias = new ArrayList<>();
		//Bemenetek szama
		int inputLayerNeurons;
		//Neuronok szama
		int neuronCount=0;
		
		
		ArrayList<Neuron[]> network = new ArrayList<>();
		ArrayList<double[]> outputs = new ArrayList<>();
		ArrayList<double[]> inputs = new ArrayList<>();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("input2.txt")));
		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try{
	   		while(true){
	   			String line = br.readLine();
	   			if(line == null)
	   				break;
	   			inputStr.add(line);
	   		}
	   		br.close();
		}
		catch(IOException e){
			System.out.println(e);
		}
		
		//Architektura init
		String[] architectureString = inputStr.get(0).split(",");
		int[] architecture = new int[architectureString.length];
		
		for(int i=0; i<architectureString.length; i++) {
			architecture[i] = Integer.parseInt(architectureString[i]);
			if(i>=1)
				neuronCount+=architecture[i];
		}
		
		//Bemeneti reteg elemeinek szama
		inputLayerNeurons = architecture[0];
		
		//1-tol megyunk a neuronok szama+a-ig mert az elso sor volt az architektura, innentol minden sorban egy neuron suly és bias ertekei lesznek
		for(int i=1; i<neuronCount+1; i++) {
			String[] weightStr = inputStr.get(i).split(",");
			double[] weight = new double[weightStr.length-1];
			for(int j=0; j<weightStr.length-1; j++) {
				weight[j]=Double.parseDouble(weightStr[j]);
			}
			weightList.add(weight);
			bias.add(Double.parseDouble(weightStr[weightStr.length-1]));	
		}
		//letrehozzuk a kimeneti ertekeket tarolo listat
		for(int i=0; i<architecture.length; i++){
			outputs.add(new double[architecture[i]]);
		}
		//Bemenetek szama
		int inputcount = Integer.parseInt(inputStr.get(neuronCount+1));
		//Bemenetek beolvasasa
		for(int i=neuronCount+2; i<neuronCount+2+inputcount; i++){
			String[] tmp = inputStr.get(i).split(",");
			double[] inputLine = new double[tmp.length];
			for(int j=0; j<tmp.length; j++){
				inputLine[j] = Double.parseDouble(tmp[j]);
			}
			inputs.add(inputLine);
		}
		
		
		// TODO for ciklussal minden szamitas vegen atadni a következo bemenetet
		/*for(i...){
		 * 	output.set(0, inputs.get(i);
		 * }
		 * */
		outputs.set(0, inputs.get(0));
		/*
		for(int i=0; i<outputs.size(); i++){
			System.out.println(outputs.get(i).length);
		}*/
		
//<-----Neuronok letrehozasa------------------------------------------------------------------------------------------------------------------>
		
		int weightListIndex=0;
		int biasListIndex=0;
		
		for(int i=1; i<architecture.length; i++) {
			Neuron[] layer = new Neuron[architecture[i]];
			for(int j=0; j<architecture[i]; j++) {
				layer[j]=new Neuron(architecture[i-1], weightList.get(weightListIndex++), bias.get(biasListIndex++));
				//weightListIndex++;
				//biasListIndex++;
			}
			network.add(layer);
		}
//<-----Kimenetek szamitasa es kiirasa------------------------------------------------------------------------------------------------------------------>

		for(int x=0; x<inputs.size(); x++){
			outputs.set(0, inputs.get(x));
			for(int i=0; i<network.size(); i++){
				double[] result = new double[network.get(i).length];
				for(int j=0; j<network.get(i).length; j++){
					result[j]=network.get(i)[j].calculateOutput(outputs.get(i));
				}
				outputs.set(i+1, result);
			}
			System.out.println(outputs.get(outputs.size()-1)[0]);
		}
		
		
//<-----Kiiratas------------------------------------------------------------------------------------------------------------------>
	/*
		for(int i=0; i<architecture.length; i++) {
			if(i<architecture.length-1)
				System.out.print(architecture[i]+",");
			else
				System.out.print(architecture[i]);
		}
		System.out.print("\n");
		
		printNetwork(network);
		*/
	}
	
	public static double[] fillRandom(int size) {
		Random rand = new Random();
		double[] result = new double[size];
		
		for(int i = 0; i<size; i++)
			result[i] = rand.nextGaussian()*0.1;
		
		return result;
	}
	
	public static void printNetwork(ArrayList<Neuron[]> network) {
		for(int i=0; i<network.size(); i++) {
			for (int j=0; j<network.get(i).length; j++){
				network.get(i)[j].print();
				System.out.print("\n");
			}
		}
	}

	public static void printWeight(double[] weight, double bias) {
		for(int i=0; i<weight.length; i++)
			System.out.print(weight[i]+",");
//		System.out.print(bias+"\n");
	}
	
}
