import java.util.Random;

public class Neuron {

	double[] inputWeight;
	double bias;
	double output;
	int id;
	
	private int count = 0;
	
	public Neuron(int inputCount, double[] _weights, double _bias) {
		bias = _bias;
		output = 0;
		inputWeight = _weights;
		id=count++;
	}
	
	public void setWeights(double[] weight) {
		if(weight.length != inputWeight.length)
			System.out.println("something went wrong: " + id);
		else
			inputWeight = weight;
	}
	
	public double calculateOutput(double[] input) {
		if(input.length != inputWeight.length) {
			System.out.println("Error: input number & weights number not equal!");
		}
		double res = 0;
		for(int i=0; i<input.length; i++) {
			res += input[i]*inputWeight[i];
		}

		output = res+bias;
		
		return output;
		/*
		if(output<=0)
			return 0;
		else
			return output;
	*/
	}
	
	public void print() {
		for(int i=0; i<inputWeight.length; i++)
			System.out.print(inputWeight[i]+",");
		System.out.print(bias);
	}
}
