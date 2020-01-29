package testTheHW2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class homework {
	public final static String SELF = "self";
	public final static String OPPONENT = "opponent";
	public final static String FREE = "free";
	public final static int SIZE = 16;

	
	public static int numOfSelfInCamp;
	public static int numOfOpponInCamp;
	
	public static int[][] topLeftbase = new int[][] {{0,0},{0,1},{0,2},{0,3},{0,4},
		                                             {1,0},{1,1},{1,2},{1,3},{1,4},
		                                             {2,0},{2,1},{2,2},{2,3},
		                                             {3,0},{3,1},{3,2},
		                                             {4,0},{4,1}
	                                                };
	                                                
   public static int[][] outsidetopLeftbase = new int[][] {{0,5},{0,6},
                                                           {1,5},{1,6},
                                                           {2,4},{2,5},
                                                           {3,3},{3,4},
                                                           {4,2},{4,3}
                                                      };
                                                      
   public static int[][] outsidebottomRightbase = new int[][] {{15,10},{15,9},
                                                               {14,10},{14,9},
                                                               {13,11},{13,10},
                                                               {12,12},{12,11},
                                                               {11,13},{11,12}
                                                      };
	                                                
   public static int[][] bottomRightbase = new int[][] {{15,15},{15,14},{15,13},{15,12},{15,11},
	                                                    {14,15},{14,14},{14,13},{14,12},{14,11},
	                                                    {13,15},{13,14},{13,13},{13,12},
	                                                    {12,15},{12,14},{12,13},
	                                                    {11,15},{11,14}
	                                                 };
                                   
	public static String player;
	public static String oppo;
	public static int depth;
	
	
	public static Map<Integer,Cell> self;
	public static Map<Integer,Cell> oppon;
	
	public homework() {
		self =  new HashMap<Integer,Cell>();
		oppon = new HashMap<Integer,Cell>();
		numOfSelfInCamp = 0;
		numOfOpponInCamp = 0;
		depth = 0;
	}
	
	public static int getDepth() {
		return depth;
	}
	
	public static void setDepth(int dep) {
		depth = dep;
	}
	
	public static int getNumOfSelfInCamp() {
		return numOfSelfInCamp;
	}
	public static void setNumOfSelfInCamp(int val) {
		numOfSelfInCamp = val;
	}
	
	public static int getNumOfOpponInCamp() {
		return numOfOpponInCamp;
	}
	public static void setNumOfOpponInCamp(int val) {
		numOfOpponInCamp = val;
	}
	
    public static Map<Integer,Cell> getSelf(){
    	return self;
    }
    
    public static Map<Integer,Cell> getOpponent(){
    	return oppon;
    }
    
	public double centerCoordBlack(int x, int y) {
	     return (y - 7.5) - (7.5 - x);
    }
	
	public double centerCoordWhite(int x, int y) {
	     return (y - 7.5) + (x - 7.5);
   }
	
	
	
	
	public static void main(String[] args) {
		
		homework hk = new homework();
		
		oppon = new HashMap<Integer,Cell>();
		
		Map<Integer,Cell> self = homework.getSelf();
		Map<Integer,Cell> oppon = homework.getOpponent();;
		
		
		File file = new File("input.txt");
		String text, mode = null;
		player = "";
		oppo = "";
//		int n = 0, depth = 0;
		double time = 0;

		Cell[][] cells = new Cell[SIZE][SIZE]; 
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));

			if((text = br.readLine()) != null){
				mode = text;
			}
			if((text = br.readLine()) != null){
				player = text.substring(0,1);
				if (player.equals("W")){
					oppo = "B";
				} else {
					oppo = "W";
				}
			}
			if((text = br.readLine()) != null){
				time = Double.valueOf(text);
			}

			double value = 0;
			int count1 = 1;
			int count2 = 1;
			
			
			for ( int i = 0 ; i < SIZE; i++){
				if((text = br.readLine()) != null){
					for (int j = 0; j < text.length();j++){
						
						Cell cell = new Cell();
						cell.setRow(i);
						cell.setCol(j);
						cells[i][j] = cell;

 						if (text.charAt(j) == '.'){
							cells[i][j].setPlayer(FREE);
						} else if (player.equals(text.substring(j, j+1))){
							cells[i][j].setPlayer(SELF);
							self.put(count1,cell);
							count1++;
						} else {
							cells[i][j].setPlayer(OPPONENT);
							oppon.put(count2,cell);
							count2++;
						}
 						
 						if (text.substring(j, j+1).equals("W")){
 							value = value + hk.centerCoordWhite(i,j);
 						}else if(text.substring(j, j+1).equals("B")){
 							value = value + hk.centerCoordBlack(i,j);
 						}
 						
 						
					}
				}
			}
			
			
			List<int[]> listInit = new ArrayList<>();
			AdverserialSearch search = null;
			Move current = new Move();
			current.setValue(value);
			current.setCells(cells);
			current.setSelfMap(self);
			current.setOpponMap(oppon);
			current.setColor(player);
            current.setList(listInit);
			
			search = new GAME();
				current.setAlpha(Integer.MIN_VALUE);
				current.setBeta(Integer.MAX_VALUE);
			
			current.setValue(value);
			long startTime = System.nanoTime();
			Move bestMove = null;

			setDepth(3);

			double counter = 0.0;
			
			System.out.println("o " + player);
			if(player.equals("W")) {
				System.out.println("ppo ");
			    bestMove = search.adverserialSearch(current,depth,counter,time,false,true,"W");
			}else if(player.equals("B")){
				System.out.println("ppo ii ");
				System.out.print(" Here ");
				bestMove = search.adverserialSearch(current,depth,counter,time,true,true,"B");
			}
			
			
			long endTime = System.nanoTime();
			System.out.println("Took "+((double)(endTime - startTime)/1000000000) + " s" + ((double)(endTime - startTime)/1000000000)/60 + " m" ); 
			double newtime = (double)(endTime - startTime)/1000000000;

			cells = bestMove.getCells();
			
			if (bestMove != null){
				System.out.println((bestMove.getFrom().getRow())+" "+" " + (int)(bestMove.getFrom().getCol()) + " " +(int)(bestMove.getTo().getRow())+" "+" " + (int)(bestMove.getTo().getCol()) + " " + bestMove.getMoveType());
				List<int[]> list = bestMove.getList();
				File fil = new File("output.txt");
				FileWriter filewrt = new FileWriter(fil);
				
				System.out.println(list.size());
				int count = 0;
				
				for(int[] arr:list) {
					filewrt.write(bestMove.getMoveType() + " " + arr[1]+ "," + arr[0] + " " + arr[3] + "," + arr[2]);
					if(count != list.size()-1) filewrt.write("\n");
					count++;
				}
				filewrt.close();
				
			}
			br.close();
		}
			
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
