import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Packer {

	public static void main(String[] args) {
		
		ArrayList<Rectangle> rects = new ArrayList<>();
		
		// Container dimensions
		int contH, contW;
		
		contH=3;
		contW=5;
		
		// Load the container with 0-s
		int[][] container = new int[contH][contW];
		
		for(int i = 0; i<contH; i++) {
			for(int j=0; j<contW; j++)
				container[i][j] = 0;
		}
		
		// Init rectangles
		rects.add(new Rectangle(2, 3, 1));
		rects.add(new Rectangle(2, 1, 2));
		rects.add(new Rectangle(1, 3, 3));
		rects.add(new Rectangle(1, 4, 4));
		rects.add(new Rectangle(3, 3, 5));
		
		// Put them in 2 lists
		ArrayList<Rectangle> rects2 = new ArrayList<>();
		for(int i=0; i<rects.size(); i++) {
			rects2.add(rects.get(i));
		}
		
		// Sort the list by x and y
		Collections.sort(rects, new XComparator());
		Collections.sort(rects2, new YComparator());
		
		for (Rectangle r : rects2) {
			System.out.println(r.id);
		}
		
		// Print the results
		for(int i = 0; i<contH; i++) {
			for(int j=0; j<contW; j++) {
				if(j<contW - 1)
					System.out.print(container[i][j] + "\t");
				else
					System.out.print(container[i][j]);
			}
			System.out.print("\n");
		}
	}

	public static class Rectangle {
		public int x, y, id;
		
		public Rectangle(int _x, int _y, int _id) {
			x=_x;
			y=_y;
			id=_id;
		}
	}
	
	public static class XComparator implements Comparator<Rectangle>{
		
		@Override
		public int compare(Rectangle r1, Rectangle r2) {
			int res = Integer.compare(r2.x, r1.x);
			if(res == 0)
				res = Integer.compare(r2.y, r1.y);
			return res;
		}
	}
	
	public static class YComparator implements Comparator<Rectangle>{
		
		@Override
		public int compare(Rectangle r1, Rectangle r2) {
			int res = Integer.compare(r2.y, r1.y);
			if(res == 0)
				res = Integer.compare(r2.x, r1.x);
			return res;
		}
	}
}
