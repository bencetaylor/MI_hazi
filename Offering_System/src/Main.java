import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	static int[][] inputRatings;
	static int[][] R;
	static int ratingCount;
	static int usersCount;
	static int movieCount;
	static double[] usersAvrRating;
	
	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("MI_HF03_TEST.txt")));
		String[] inputData = br.readLine().split("\t");
		ratingCount = Integer.parseInt(inputData[0]);
		usersCount = Integer.parseInt(inputData[1]);
		movieCount = Integer.parseInt(inputData[2]);
			
		inputRatings = new int[ratingCount][3];
		
		R = new int[usersCount][movieCount];
		
		for(int i=0; i<ratingCount; i++){
			String[] line = br.readLine().split("\t");
			inputRatings[i][0]= Integer.parseInt(line[0]);
			inputRatings[i][1]= Integer.parseInt(line[1]);
			inputRatings[i][2]= Integer.parseInt(line[2]);
		}
		// R felt�lt�se elemekkel
		for(int i=0; i<ratingCount; i++){
			R[inputRatings[i][0]][inputRatings[i][1]]=inputRatings[i][2];
		}
		
		usersAvrRating = new double[usersCount];
		for(int i=0; i<usersCount; i++){
			int Vi = 0;
			// Sz�ml�l�, hogy a felhaszn�l� h�ny filmet �rt�kelt
			int count=0; 
			for(int j=0; j<movieCount; j++){
				if(R[i][j] > 0){
					Vi += R[i][j];
					count++;
				}
			}
			usersAvrRating[i] = (double)Vi * (1.0 / (double)count);
		}
		
		/*
		// �tlagolt felh. �rt�kel�sek ki�rsa
		for(int i=0; i<usersCount; i++){
			System.out.println(usersAvrRating[i]);
		}
		*/
		
		/*
		// R m�trix ki�r�sa
		for(int i=0; i<usersCount; i++){
			for(int j=0; j<movieCount; j++){
				System.out.print(R[i][j] + "\t");
			}
			System.out.print("\n");
		}*/
		
		/*for(int i=0; i<ratingCount; i++){
			System.out.println(inputRatings[i][0] + "\t" + inputRatings[i][1] + "\t" + inputRatings[i][2]);
		}*/
		
		br.close();
	}
	
	static double PearsonCorr(int a, int i){
		
		double szamlalo = 0; 
		double nevezo = 0;
		double x=0, y = 0;
		for(int j=0; j<movieCount; j++){
			szamlalo += ((double)R[a][j]-usersAvrRating[a]) * ((double)R[i][j]-usersAvrRating[i]);
			
			x += Squared((double)R[a][j]-usersAvrRating[a]);
			y += Squared((double)R[i][j]-usersAvrRating[i]);
		}
		
		//TODO sqrt on x*y!!
		nevezo = x*y;
		
		return szamlalo/nevezo;
	}
	
	static double Squared(double a){
		return a*a;
	}

}
