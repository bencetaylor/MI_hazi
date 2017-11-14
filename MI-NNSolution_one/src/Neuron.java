import java.util.ArrayList;

public class Neuron {

	ArrayList<Double> inputWeight;
	double bias;
	double output;
	
	public Neuron() {
		bias = 0;
		output = 0;
		inputWeight = new ArrayList<>();
	}
	
	public void AddWeight(double weight) {
		inputWeight.add(weight);
	}
}
