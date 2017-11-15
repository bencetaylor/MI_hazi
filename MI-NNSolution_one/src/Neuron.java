import java.util.Random;

public class Neuron {

	double[] inputWeight;
	double bias;
	double output;
	int id;
	
	private int count = 0;
	
	public Neuron(int inputCount) {
		bias = 0;
		output = 0;
		inputWeight = new double[inputCount];
		
		Random rand = new Random();
		for(int i = 0; i<inputCount; i++)
			inputWeight[i] = rand.nextGaussian()*0.1;
		
		id=count++;
	}
	/*
	public void AddWeight(double weight) {
		inputWeight.add(weight);
	}*/
	
	public void setWeights(double[] weight) {
		if(weight.length != inputWeight.length)
			System.out.println("something went wrong: " + id);
		else
			inputWeight = weight;
	}
	
	public void print() {
		for(int i=0; i<inputWeight.length; i++)
			System.out.print(inputWeight[i]+",");
		System.out.print(bias);
	}
}
