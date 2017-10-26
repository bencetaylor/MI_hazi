import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Packer {

	public static void main(String[] args) throws IOException {
		// Lista a teglalapoknak
		ArrayList<Rectangle> rects = new ArrayList<>();
		
		// Lista a beolvasott adatoknak, ezeket Parse-oljuk es toltjuk be beloluk az adatokat
		ArrayList<String> input = new ArrayList<>();
		
		int itemCount;
		
		// Container dimensions
		int contH, contW;
		
		// Container
		int[][] container;
		
//<<----Beolvasas------------------------------------------------------->>//
		// FileInutStream-et atirni System.In-re!!!
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("in.txt")));
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
		
		// Sort the list by x
		Collections.sort(rects, new XComparator());
		
		//Kiirjuk a teglalapok ID-j�t
		for (Rectangle r : rects) {
			System.out.println(r.id);
		}
//<<----Logika--------------------------------------------------------------------->>//
		Methods m = new Methods();
		Rectangle r = rects.get(0);
		m.putRectToCont(r, container, 0, 0);
		/*
		for(int i=0; i<contW; i++){
			for(int j=0; j<contH; j++){
				if(container[i][j]==0){
					
				}
			}
		}
		
		*/
//<<----Kiiratas------------------------------------------------------------------->>//
		// Print the results
		for(int i = 0; i<contH; i++) {
			for(int j=0; j<contW; j++) {
				if(j<contW - 1)
					System.out.print(container[i][j] + "\t");
				else
					System.out.print(container[i][j]);
			}
			if(i<contH-1)
				System.out.print("\n");
		}
	}

	public static class Methods{
		public void putRectToCont(Rectangle tmp, int[][] container,int x, int y){
			for(int i=x; i<tmp.width+x; i++){
				for(int j=y; j<tmp.height+y; j++){
					container[i][j] = tmp.id;
				}
			}
		}
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
		
		public void rotate(){
			int tmp = width;
			width = height;
			height = tmp;
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