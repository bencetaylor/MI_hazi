import java.io.BufferedReader;
import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class NNSolutionOne {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Random rand = new Random();
		double result;
		
		for(int i = 0; i<10; i++) {
			result = rand.nextGaussian()*0.1;
			System.out.println(result);
		}
		
		ArrayList<String> input = new ArrayList<>();
		
		
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
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
		String[] var = input.get(0).split(",");
		int[] acrhitecture = new int[var.length];
		for(int i=0; i<3; i++) {
			acrhitecture[i] = Integer.parseInt(var[i]);
		}
		
		for(int i=0; i<acrhitecture.length; i++) {
			System.out.println(acrhitecture[i]);
		}
		
		
	}

}
