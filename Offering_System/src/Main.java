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
	static double[][] predicateR;
	
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
		// R feltöltése elemekkel
		for(int i=0; i<ratingCount; i++){
			R[inputRatings[i][0]][inputRatings[i][1]]=inputRatings[i][2];
		}
		
		// Kimeneti MX a prdikált értékeknek ahol a már értékelt filmek negatívak
		predicateR = new double[usersCount][movieCount];
		for(int i=0; i<usersCount; i++){
			for(int j=0; j<movieCount; j++){
				if(R[i][j] != 0)
					predicateR[i][j] = -R[i][j];
				else
					predicateR[i][j] = 0.0;
			}
		}
	
		// User avarage rating
		usersAvrRating = new double[usersCount];
		for(int i=0; i<usersCount; i++){
			int Vi = 0;
			// Számláló, hogy a felhasználó hány filmet értékelt
			int count=0; 
			for(int j=0; j<movieCount; j++){
				if(R[i][j] > 0){
					Vi += R[i][j];
					count++;
				}
			}
			usersAvrRating[i] = (double)Vi / (double)count;
		}
		
		// Predikált értékek kiszámítása minden user-film párosra
		for(int i=0; i<usersCount; i++){
			for(int j=0; j<movieCount; j++){
				if(predicateR[i][j]==0){
					// Ha még nincs érték, kiértékeljük 
					double sum = 0;
					for(int k=0; k<usersCount; k++){
						if(R[k][j] != 0){
							sum += PearsonCorr(i, k)*(R[k][j]-usersAvrRating[k]);
						}
					}
					//System.out.println(sum);
					predicateR[i][j]=usersAvrRating[i]+0.1*sum;
				}
			}
		}
		
		/*
		// Átlagolt felh. értékelések kiírsa
		for(int i=0; i<usersCount; i++){
			System.out.println(usersAvrRating[i]);
		}
		*/
		
		/*
		// R mátrix kiírása
		for(int i=0; i<usersCount; i++){
			for(int j=0; j<movieCount; j++){
				System.out.print(R[i][j] + "\t");
			}
			System.out.print("\n");
		}*/
		
		/*for(int i=0; i<ratingCount; i++){
			System.out.println(inputRatings[i][0] + "\t" + inputRatings[i][1] + "\t" + inputRatings[i][2]);
		}*/
		//System.out.println("--------------------------------------------");
		
		for(int i=0; i<usersCount; i++){
			for(int j=0; j<movieCount; j++){
				System.out.print(predicateR[i][j] + "\t");
			}
			System.out.print("\n");
		}
		
		br.close();
	}
	
	static double PearsonCorr(int a, int i){
		double szamlalo = 0; 
		double nevezo = 0;
		double x=0, y = 0;
		for(int j=0; j<movieCount; j++){
			if(R[a][j] > 0 && R[i][j] > 0){
				szamlalo += ((double)R[a][j]-usersAvrRating[a]) * ((double)R[i][j]-usersAvrRating[i]);
				x += Squared((double)R[a][j]-usersAvrRating[a]);
				y += Squared((double)R[i][j]-usersAvrRating[i]);
			}
		}
		if(x*y<=0.0)
			return 0.0;
		nevezo = Math.sqrt(x*y);
		return szamlalo/nevezo;
	}
	
	static double Squared(double a){
		return a*a;
	}

}
