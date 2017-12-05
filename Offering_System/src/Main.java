import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("MI_HF03_TEST.txt")));
		String[] inputData = br.readLine().split("\t");
		int ratingCount = Integer.parseInt(inputData[0]);
		int usersCount = Integer.parseInt(inputData[1]);
		int movieCount = Integer.parseInt(inputData[2]);
			
		int[][] inputRatings = new int[ratingCount][3];
		
		for(int i=0; i<ratingCount; i++){
			String[] line = br.readLine().split("\t");
			inputRatings[i][0]= Integer.parseInt(line[0]);
			inputRatings[i][1]= Integer.parseInt(line[1]);
			inputRatings[i][2]= Integer.parseInt(line[2]);
		}
		
		for(int i=0; i<ratingCount; i++){
			System.out.println(inputRatings[i][0] + "\t" + inputRatings[i][1] + "\t" + inputRatings[i][2]);
		}
		
	}

}
