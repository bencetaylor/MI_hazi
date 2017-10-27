import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Main {

	static ArrayList<Rectangle> rects;
	 
	// Tarolo meretei
	static int contH, contW;
	// Koordinatak a beilleszteshez
	static int currCoordX, currCoordY;
	
	public static void main(String[] args) throws IOException {
		// Lista a teglalapoknak
		rects = new ArrayList<>();
		
		// Lista a beolvasott adatoknak, ezeket Parse-oljuk es toltjuk be beloluk az adatokat
		ArrayList<String> input = new ArrayList<>();
		
		int itemCount;
		
		// Tarolo
		int[][] container;
		
		// Beallitjuk a kezdo poziciot a taroloban
		currCoordX=0;
		currCoordY=0;
		
		/**
		 * Beolvasas
		 */
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("test7.txt")));
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
		
		// Beallitjuk a tarolo meretet
		String[] dimStr = new String[2];
		dimStr = input.get(0).split("\t");
		contW = Integer.parseInt(dimStr[0]);
		contH = Integer.parseInt(dimStr[1]);
		// Majd letrehozzuk a tarolot
		container = new int[contW][contH];
		
		// Megnezzuk mennyi teglalap lesz
		itemCount = Integer.parseInt(input.get(1));
		
		// Beolvassuk a teglalapokat
		for(int i = 0; i < itemCount; i++){
			String[] rectData = input.get(i+2).split("\t");
			// Elforgatjuk a teglalapot, hogy mindig a hosszabb oldal legyen az X
			if(Integer.parseInt(rectData[0]) < Integer.parseInt(rectData[1]))
				rects.add(new Rectangle(Integer.parseInt(rectData[1]), Integer.parseInt(rectData[0])));
			else
				rects.add(new Rectangle(Integer.parseInt(rectData[0]), Integer.parseInt(rectData[1])));
		}
		
		// Rendezzuk a listat eloszor szelesseg, ha az egyenlo akkor magassag szerint
		Collections.sort(rects, new XComparator());
		
		/**
		 * Logika
		 */
		boolean succes=false;
		// Vegigmegyunk a teglalapokon es megprobaljuk elhelyezni oket a taroloban
		for(int i=0; i<rects.size();i++){
			// Ha sikerul, megyunk tovabb
			succes = isFit(rects.get(i), container, contW, contH);
			// Ha nem, elforgatjuk es ujra probaljuk
			if(!succes){
				rects.get(i).rotate();
				succes = isFit(rects.get(i), container, contW, contH);
			}
			// Ha igy sem, akkor kilepunk
			if(!succes){
				break;
			}
		}
		// Ha az elozo ciklusban nem sikerult beilleszteni mindent, elforgatjuk az elso elemet es ezek utan ujra probalkozunk
		if(!succes){
			rects.get(0).rotate();
			//Collections.sort(rects, new XComparator());
			container = new int[contW][contH];
			
			for(int i=0; i<rects.size();i++){
				succes = isFit(rects.get(i), container, contW, contH);
				if(!succes){
					rects.get(i).rotate();
					succes = isFit(rects.get(i), container, contW, contH);
				}
				// Ha igy sem sikerult, hibat jelzunk, majd kilepunk
				if(!succes){
					System.out.println("Failed: " + rects.get(i).id);
					rects.get(i).print();
					return;
				}
			}
		}
		/**
		 * Kiiratas
		 */
		printResult(container);
	}

	/**
	 * Segedfuggvenyek
	 */

	//Kiirja a kapott tarolo elemeit
	public static void printResult(int[][] container){
		for(int i = 0; i<contH; i++) {
			for(int j=0; j<contH; j++) {
				if(j<contH - 1)
					System.out.print(container[j][i] + "\t");
				else
					System.out.print(container[j][i]);
			}
			if(i<contH-1)
				System.out.print("\n");
		}
	}
	
	public static void putRectToCont(Rectangle tmp, int[][] container,int x, int y){
		// Berakjuk a kapott teglalapot a taroloba a megadott koordinatakra
		for(int n=x; n<tmp.width+x; n++){
			for(int m=y; m<tmp.height+y; m++){
				container[n][m] = tmp.id;
			}
		}
	}
	
	public static boolean isFit(Rectangle r, int[][] container, int w, int h){
		
		boolean exit=false;
		// Vegigmegyunk a tarolon es megnezzuk hogy hova tudnank berakni a teglalapot. Ha talalunk helyet kilepunk a ciklusbol es belerakjuk.
		for(int i = 0; i<h && !exit; i++){
			for(int j = 0; j<w && !exit; j++){
				if(container[j][i]==0){
					currCoordX=j;
					currCoordY=i;
					exit=checkCoord(r, currCoordX, currCoordY, container);
				}
			}
		}
		if(exit){
			putRectToCont(r, container, currCoordX, currCoordY);
			return true;
		}
		else
			return false;
	}
	
	public static boolean checkCoord(Rectangle r, int X, int Y, int[][] container){
		// Ha a teglalap kilogna a tarolobol, nem tesszuk bele
		if((X+r.width) > contW || (Y+r.height) > contH){
			return false;
		}
		// Megnezzuk van e mar valami azon a helyen ahova a teglalapot tenni szeretnenk
		for(int i = Y; i<Y+r.height; i++){
			for(int j=X; j< X+r.width; j++)
				if(container[j][i] != 0)
					return false;
		}
		// Ha nincs, mehet! :)
		return true;
	}
	
	public static class Rectangle {
		public int width, height, id;
		//Auto-inkremens amibol az id-t generaljuk
		private static int counter=1;
		
		public Rectangle(int _width, int _height) {
			width=_width;
			height=_height;
			id = counter++;
		}
		//Elforgatja a teglalapot (megcsereli a ket oldalt)
		public void rotate(){
			int tmp = width;
			width = height;
			height = tmp;
		}
		
		public void print(){
			System.out.println("id: "+id+"\tw: "+width+"\th: "+height);
		}
	}	
	
	
	public static class XComparator implements Comparator<Rectangle>{
		
		@Override
		public int compare(Rectangle r1, Rectangle r2) {
			int res = Integer.compare(r2.width, r1.width);
			if(res == 0)
				res = Integer.compare(r2.height, r1.height);
			return res;
		}
	}
	
	public static class YComparator implements Comparator<Rectangle>{
		
		@Override
		public int compare(Rectangle r1, Rectangle r2) {
			int res = Integer.compare(r2.height, r1.height);
			if(res == 0)
				res = Integer.compare(r2.width, r1.width);
			return res;
		}
	}
	
	public static class AreaComparator implements Comparator<Rectangle>{
		
		@Override
		public int compare(Rectangle r1, Rectangle r2) {
			int res = Integer.compare(r2.width, r1.width);
			if(res==0){
				res = Integer.compare(r2.height*r2.width, r1.height*r1.width);
			}
			return res;
		}
	}
}
