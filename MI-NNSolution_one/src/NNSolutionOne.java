import java.io.BufferedReader;
import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class NNSolutionOne {

	public static void main(String[] args) throws IOException {
		ArrayList<String> input = new ArrayList<>();
		
		
		//BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
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
		
		String[] architectureString = input.get(0).split(",");
		int[] architecture = new int[architectureString.length];
		
		for(int i=0; i<3; i++) {
			architecture[i] = Integer.parseInt(architectureString[i]);
			System.out.print(architecture[i]+" ");
		}
		System.out.print("\n");
		
		
		
		ArrayList<Neuron> neuronList = new ArrayList<>();
		
		for(int i=1; i<architecture.length; i++) {
			for(int j=0; j<architecture[i]; j++) {
				neuronList.add(new Neuron(architecture[i-1]));
				//neuronList.get(i+j-1).setWeights(fillRandom(architecture[i-1]));
			}
		}
		
		for(int i=0; i<neuronList.size(); i++) {
			if(i<neuronList.size()-1) {
				neuronList.get(i).print();
				System.out.print("\n");
			}
			else
				neuronList.get(i).print();
		}
		
		
	}
	
	public static double[] fillRandom(int size) {
		Random rand = new Random();
		double[] result = new double[size];
		
		for(int i = 0; i<size; i++)
			result[i] = rand.nextGaussian()*0.1;
		
		return result;
	}

}
