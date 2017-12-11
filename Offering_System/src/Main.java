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
	static int[][] usersTop10;
	
	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("MI_HF03_TEST.txt")));
		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] inputData = br.readLine().split("\t");
		ratingCount = Integer.parseInt(inputData[0]);
		usersCount = Integer.parseInt(inputData[1]);
		movieCount = Integer.parseInt(inputData[2]);
			
		inputRatings = new int[ratingCount][3];
		R = new int[usersCount][movieCount];
		predicateR = new double[usersCount][movieCount];
		usersAvrRating = new double[usersCount];
		usersTop10 = new int[usersCount][10];
		
		for(int i=0; i<ratingCount; i++){
			String[] line = br.readLine().split("\t");
			inputRatings[i][0]= Integer.parseInt(line[0]);
			inputRatings[i][1]= Integer.parseInt(line[1]);
			inputRatings[i][2]= Integer.parseInt(line[2]);
		}
		
		br.close();
		
		// R feltöltése elemekkel
		for(int i=0; i<ratingCount; i++){
			R[inputRatings[i][0]][inputRatings[i][1]]=inputRatings[i][2];
		}
		
		// Kimeneti MX a predikált értékeknek ahol a már értékelt filmek negatívak
		for(int i=0; i<usersCount; i++){
			for(int j=0; j<movieCount; j++){
				if(R[i][j] != 0)
					predicateR[i][j] = -R[i][j];
				else
					predicateR[i][j] = 0.0;
			}
		}
	
		// Átlagos felhasználói értékelések számítása
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
			usersAvrRating[i] = (double)Vi * (1.0/(double)count);
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
					// K=0.1 normalizációs tényezõ
					predicateR[i][j]=usersAvrRating[i]+0.1*sum;
				}
			}
		}
		
		// A 10 legjobb film kiválasztása felhasználónként
		for(int i=0; i<usersCount; i++){
			for(int k=0; k<10; k++){
				// Ha megtaláltuk a legjobbat és elmentettük annak indexét, lenullázzuk az értékelést,
				// így legközelebb a következõ (második, harmadik, stb..) elemet kapjuk meg a kereséssel.
				for(int j=0; j<movieCount; j++){
					if(predicateR[i][j] > usersTop10[i][k])
						usersTop10[i][k]=j;
				}
				predicateR[i][usersTop10[i][k]]=0.0;
			}
		}
		
		
		// Ajánlott filmek kiíratása felhasználónként
		for(int i=0; i<usersCount; i++){
			for(int j=0; j<10; j++){
				if(j<9)
					System.out.print(usersTop10[i][j] + "\t");
				else
					System.out.print(usersTop10[i][j]);
			}
			if(i < usersCount-1)
				System.out.print("\n");
		}
		
	}
	
	/**
	 * Felhasználói súly kiszámítása Pearson korrelációval
	 * @param a - aktuális felhasználó
	 * @param i - hasonló felhasználó
	 * @return - súly a hasonló felhasználóra
	 */
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
