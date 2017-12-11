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
		
		// R felt�lt�se elemekkel
		for(int i=0; i<ratingCount; i++){
			R[inputRatings[i][0]][inputRatings[i][1]]=inputRatings[i][2];
		}
		
		// Kimeneti MX a predik�lt �rt�keknek ahol a m�r �rt�kelt filmek negat�vak
		for(int i=0; i<usersCount; i++){
			for(int j=0; j<movieCount; j++){
				if(R[i][j] != 0)
					predicateR[i][j] = -R[i][j];
				else
					predicateR[i][j] = 0.0;
			}
		}
	
		// �tlagos felhaszn�l�i �rt�kel�sek sz�m�t�sa
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
			usersAvrRating[i] = (double)Vi * (1.0/(double)count);
		}
		
		// Predik�lt �rt�kek kisz�m�t�sa minden user-film p�rosra
		for(int i=0; i<usersCount; i++){
			for(int j=0; j<movieCount; j++){
				if(predicateR[i][j]==0){
					// Ha m�g nincs �rt�k, ki�rt�kelj�k 
					double sum = 0;
					for(int k=0; k<usersCount; k++){
						if(R[k][j] != 0){
							sum += PearsonCorr(i, k)*(R[k][j]-usersAvrRating[k]);
						}
					}
					// K=0.1 normaliz�ci�s t�nyez�
					predicateR[i][j]=usersAvrRating[i]+0.1*sum;
				}
			}
		}
		
		// A 10 legjobb film kiv�laszt�sa felhaszn�l�nk�nt
		for(int i=0; i<usersCount; i++){
			for(int k=0; k<10; k++){
				// Ha megtal�ltuk a legjobbat �s elmentett�k annak index�t, lenull�zzuk az �rt�kel�st,
				// �gy legk�zelebb a k�vetkez� (m�sodik, harmadik, stb..) elemet kapjuk meg a keres�ssel.
				for(int j=0; j<movieCount; j++){
					if(predicateR[i][j] > usersTop10[i][k])
						usersTop10[i][k]=j;
				}
				predicateR[i][usersTop10[i][k]]=0.0;
			}
		}
		
		
		// Aj�nlott filmek ki�rat�sa felhaszn�l�nk�nt
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
	 * Felhaszn�l�i s�ly kisz�m�t�sa Pearson korrel�ci�val
	 * @param a - aktu�lis felhaszn�l�
	 * @param i - hasonl� felhaszn�l�
	 * @return - s�ly a hasonl� felhaszn�l�ra
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
