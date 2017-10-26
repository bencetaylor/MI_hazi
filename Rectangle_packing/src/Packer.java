import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Packer {

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
		
		//Kiirjuk a teglalapok ID-ját
		for (Rectangle r : rects) {
			System.out.println(r.id);
		}
//<<----Logika--------------------------------------------------------------------->>//
		Methods m = new Methods();
		m.putRectToCont(rects.get(0), container, 0, 0);
		
		for(int i=0; i<rects.size(); i++){
			if(m.isFit(rects.get(1), container, contW, contH)){
				m.putRectToCont(rects.get(i), container, currCoordX, currCoordY);
			}
			else{
				System.out.println("Failed!");
				break;
			}
		}
		/*Boolean van = false;
		
		m.Backtrack(0, container, van);
		if(!van)
			System.out.println("Failed!");
		*/
		
//<<----Kiiratas------------------------------------------------------------------->>//
		// Print the results
		/*for(int i = 0; i<contH; i++) {
			for(int j=0; j<contH; j++) {
				if(j<contH - 1)
					System.out.print(container[j][i] + "\t");
				else
					System.out.print(container[j][i]);
			}
			if(i<contH-1)
				System.out.print("\n");
		}*/
		//printResult(container);
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
		System.out.println("\n------------------------------");
	}
	
	public static class Methods{
		
		public void putRectToCont(Rectangle tmp, int[][] container,int x, int y){
			for(int n=x; n<tmp.width+x; n++){
				for(int m=y; m<tmp.height+y; m++){
					System.out.println("n: " + n + " m: " + m);
					System.out.println("n felt: " + (x) + " m felt: " + (y));
					container[n][m] = tmp.id;
					printResult(container);
				}
			}
		}
		
		public boolean isFit(Rectangle r, int[][] container, int w, int h){
			int i=0;
			int j=0;
			boolean exit=false;
			while(j<h && !exit){
				if(container[i][j]==0){
					currCoordX=i;
					currCoordY=j;
					break;
				}
				while(i<w){
					if(container[i][j]==0){
						currCoordX=i;
						exit=true;
						break;
					}
					if(!exit)
						i++;
				}
				if(!exit)
					j++;
			}
			if(i+r.width>w)
				r.rotate();
			if(i+r.width>w)
				return false;
			if(j+r.height>h)
				return false;
			
			
			
			//putRectToCont(r, container, i, j);
			return true;
		}
		
		boolean Fk(int szint, Rectangle r, int[][] E){
			return isFit(r, E, contW, contH);
		}
		
		void Backtrack(int szint, int[][] E, Boolean van){
			int i = 0;
			while(!van && i < rects.size()){
				i++;
				if(Fk(szint, rects.get(i), E)){
					//e-be rakni
					if(szint == rects.size()){
						van = true;
					}else{
						Backtrack(szint + 1, E, van);
					}
				}
			}
		}
		
		/*void BackTrack(int szint,boolean[] E) {
            for (int i = 0; i <= 1; i++)
            {
                if (i == 0)
                    E[szint] = true;
                else
                    E[szint] = false;

                if (Fk(szint, E))
                {
                    if (szint == M.Length - 1)
                    {
                        if ((OraHossz(E) >= OraHossz(OPT)) && (OraAr(E) < legkisebbAr))
                        {
                            if(OraHossz(E) == 0)
                            {
                                throw new NincsElegHelyException();
                            }
                            for (int f = 0; f < E.Length; f++)
                            {
                                OPT[f] = E[f];
                                E[f] = false;
                            }
                            legkisebbAr = OraAr(OPT);
                            optimalisOrarend?.Invoke(OPT, legkisebbAr,M);
                        }
                    }
                    else
                    {
                        BackTrack(szint + 1, ref E, ref OPT);
                    }
                }
            }
        }
		/*
		public int[] whereFit(Rectangle r, int[][] container, int w, int h){
			int i=0;
			int j=0;
			while(j<h){
				if(container[i][j]==0)
					break;
				while(i<w){
					if(container[i][j]==0){
						break;
					}
					i++;
				}
				j++;
			}
			if(i+r.width>w)
				r.rotate();
			if(i+r.width>w)
				
			if(j+r.height>h)
				
			int[]
			
			return new int[] = {i, j};
			
		}
		*/
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
