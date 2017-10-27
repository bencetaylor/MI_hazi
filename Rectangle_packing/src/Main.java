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
	 
	// Container dimensions
	static int contH, contW;
	
	static int currCoordX, currCoordY;
	
	public static void main(String[] args) throws IOException {
		// Lista a teglalapoknak
		rects = new ArrayList<>();
		
		// Lista a beolvasott adatoknak, ezeket Parse-oljuk es toltjuk be beloluk az adatokat
		ArrayList<String> input = new ArrayList<>();
		
		int itemCount;
		
		// Container
		int[][] container;
		
		currCoordX=0;
		currCoordY=0;
		
//<<----Beolvasas------------------------------------------------------->>//
		// FileInutStream-et atirni System.In-re!!!
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("test2.txt")));
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
		
		// Beallitjuk a kontener meretet
		String[] dimStr = new String[2];
		dimStr = input.get(0).split("\t");
		contW = Integer.parseInt(dimStr[0]);
		contH = Integer.parseInt(dimStr[1]);
		// Load the container with 0-s
		container = new int[contH][contW];
		for(int i = 0; i<contH; i++) {
			for(int j=0; j<contW; j++)
				container[i][j] = 0;
		}
		
		// Megnezzuk mennyi teglalap lesz
		itemCount = Integer.parseInt(input.get(1));
		
		// Beolvassuk a teglalapokat
		for(int i = 0; i < itemCount; i++){
			String[] rectData = input.get(i+2).split("\t");
			rects.add(new Rectangle(Integer.parseInt(rectData[0]), Integer.parseInt(rectData[1])));
		}
		
		// Sort the list
		Collections.sort(rects, new AreaComparator());
		/*
		//Kiirjuk a teglalapok ID-ját
		for (Rectangle r : rects) {
			r.print();
		}*/
//<<----Logika--------------------------------------------------------------------->>//
		
		boolean succes=false;
		for(int i=0; i<rects.size();i++){
			succes = isFit(rects.get(i), container, contW, contH);
			/*if(!succes){
				System.out.println("Failed");
				return;
			}*/
		}
		
		
//<<----Kiiratas------------------------------------------------------------------->>//
		// Print the results
		printResult(container);
	}

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
		for(int n=x; n<tmp.width+x; n++){
			for(int m=y; m<tmp.height+y; m++){
				container[n][m] = tmp.id;
			}
		}
	}
	
	public static boolean isFit(Rectangle r, int[][] container, int w, int h){
		boolean exit=false;
		
		for(int i = 0; i<h &&!exit; i++){
			for(int j = 0; j<w && !exit; j++){
				if(container[j][i]==0){
					currCoordX=j;
					currCoordY=i;
					exit=true;
				}
			}
		}
		if(currCoordX+r.width>w)
			r.rotate();
		if(currCoordX+r.width>w)
			return false;
		else if(currCoordY+r.height>h)
			return false;
		else{
			putRectToCont(r, container, currCoordX, currCoordY);
			return true;
		}
	}
	/*
	public static class Methods{
		
		public void putRectToCont(Rectangle tmp, int[][] container,int x, int y){
			for(int n=x; n<tmp.width+x; n++){
				for(int m=y; m<tmp.height+y; m++){
					container[n][m] = tmp.id;
				}
			}
		}
		
		public boolean isFit(Rectangle r, int[][] container, int w, int h){
			boolean exit=false;
			
			for(int i = 0; i<h &&!exit; i++){
				for(int j = 0; j<w && !exit; j++){
					if(container[j][i]==0){
						currCoordX=j;
						currCoordY=i;
						exit=true;
					}
				}
			}
			if(currCoordX+r.width>w)
				r.rotate();
			if(currCoordX+r.width>w)
				return false;
			else if(currCoordY+r.height>h)
				return false;
			else{
				putRectToCont(r, container, currCoordX, currCoordY);
				return true;
			}
		}
	}
	*/
	public static class Rectangle {
		public int width, height, id;
		//Auto-inkremens amibol az id-t generaljuk
		private static int counter=1;
		
		public Rectangle(int _width, int _height) {
			width=_width;
			height=_height;
			id = counter++;
		}
		
		public void rotate(){
			int tmp = width;
			width = height;
			height = tmp;
		}
		
		public void print(){
			System.out.println("id: "+id+" w: "+width+" h: "+height);
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
			return Integer.compare(r2.height*r2.width, r1.height*r1.width);
		}
	}
}
