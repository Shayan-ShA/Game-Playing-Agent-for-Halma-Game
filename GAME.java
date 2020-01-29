package testTheHW2;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


class SortbyValue implements Comparator<Move> 
{ 
    public int compare(Move a, Move b) 
    { 
        return (int)(a.getValue() - b.getValue()); 
    } 
}


public class GAME extends AdverserialSearch{
	
	public boolean beginingChecker = false;
	public int count = 0;
    public int[][] dirs = new int[][] {{-1,-1},{-1,0},{-1,1},{0,1},{1,1},{1,0},{1,-1},{0,-1}};
    
    
	@Override
	public Move adverserialSearch(Move current, int depth,double counter,double time, boolean maxPlayer,boolean player,String col) {
		long startTime1 = System.nanoTime();

//		System.out.println("checkpoint1 ");
		
		String next = "";
		if(col.equals("W")) {
			next = "B";
        }
		if(col.equals("B")){
        	next = "W";
        }
		count++;		
		Move bestMove = null;
		
		if (depth == 0){
			return current;
		} else {
			List<ArrayList<Move>> moves = generateMoves(current, maxPlayer,player,col);
			if(homework.getNumOfSelfInCamp() >= 14 ||  homework.getNumOfOpponInCamp() >=14) depth = 1;
			if(beginingChecker == true) depth = 1;
			if (moves != null){
				double bestSoFar;
				if (maxPlayer)
					bestSoFar = Integer.MIN_VALUE;
				else 
					bestSoFar = Integer.MAX_VALUE;
					
				for (int i = 0; i < moves.size();i++){
					for (int j = 0; j < moves.get(i).size();j++){
						if (current.getAlpha() < current.getBeta()){
							Move nextMove = moves.get(i).get(j);
							nextMove.setAlpha(current.getAlpha());
							nextMove.setBeta(current.getBeta());
							long endTime1 = System.nanoTime();		
							double timeDif1 = ((double)(endTime1 - startTime1)/1000000000);
							Move move = adverserialSearch(moves.get(i).get(j), depth -1,counter + timeDif1, time, !maxPlayer,!player,next);
							if (move != null){
								if (maxPlayer){
									if (move.getValue() > bestSoFar){
										bestMove  = moves.get(i).get(j);
										bestSoFar = move.getValue();
									}
									current.setAlpha(bestSoFar);
								} else {
									if (move.getValue() < bestSoFar){
										bestMove  = moves.get(i).get(j);
										bestSoFar = move.getValue();
									}
									current.setBeta(bestSoFar);
								}
							}
						} else {
						if(counter > time - 2 ) {
							bestMove.setValue(bestSoFar);
							return bestMove;
						}
					}
				}
			}
				bestMove.setValue(bestSoFar);
				return bestMove;
			}
		}
		return current;
	}

	public List<ArrayList<Move>> generateMoves(Move current, boolean maxPlayer,boolean player,String col){
		if (!isGameOver(current.getCells())){
			Map<Integer,Cell> iterator = isStillInCamp(current,player,col);

	        if(iterator.size() != 0) {
	        	///////////////////////
	        	
	        	if(beggining(current,maxPlayer,player,col)) {
	        		beginingChecker = true;
	        		if(first(current,maxPlayer,player,col)) {

	        			System.out.println(" checkpoint1 ");
	        			
	        			List<ArrayList<Move>> moves = new ArrayList<ArrayList<Move>>();		
	        			ArrayList<Move> move = new ArrayList<Move>();
	        			Cell[][] cells = current.getCells();
	        			
	        			Map<Integer,Cell> iteratoro;
	        			if(player) {
	        			   iteratoro = current.getSelfMap();  
	        			}else {
	        				iteratoro = current.getOpponMap();
	        			}
	        			int inde = 0;
	        			if(col.equals("W")) {
	        				for(Integer idx: iteratoro.keySet()) {
	            				
	            				Cell cell = iteratoro.get(idx);
	            	            int r = cell.getRow();
	            	            int c = cell.getCol();	
	            	            	if(r == 13 && c == 14) {
	            	            	    inde = idx;
	            	            	    cells[11][12].setPlayer(homework.SELF);
	            	            		iteratoro.put(inde,cells[11][12]);
	            	            		cells[13][14].setPlayer(homework.FREE);
	            	            		Move newMove = new Move();
	            	            		
	            	            		int[] arr = {13,14,11,12};
		           	            		List<int[]> list = new ArrayList<int[]>();
		           	            		list.add(arr);
		           	            		newMove.setList(list);
	            	            		
	            	            		newMove.setCells(cells);
	            	            		newMove.setValue(current.getValue());
	            	            		newMove.setFrom(cells[13][14]);
	            	            		newMove.setTo(cells[11][12]);
	            	            		newMove.setValue(getEvaluation(newMove,maxPlayer,player));
	            	            		newMove.setSelfMap(iteratoro);
	            	            		newMove.setOpponMap(current.getOpponMap());
	            	            		newMove.setMoveType("J");
	            	            	    move.add(newMove);
	            	            	}
	            			}
	        			}
	        			if(col.equals("B")) {
                            for(Integer idx: iteratoro.keySet()) {
	            				
	            				Cell cell = iteratoro.get(idx);
	            	            int r = cell.getRow();
	            	            int c = cell.getCol();	
	            	            	if(r == 2 && c == 1) {
	            	            	    inde = idx;
	            	            	    cells[4][3].setPlayer(homework.SELF);
	            	            		iteratoro.put(inde,cells[4][3]);
	            	            		cells[2][1].setPlayer(homework.FREE);
	            	            		Move newMove = new Move();
	            	            		
	            	            		int[] arr = {2,1,4,3};
		           	            		List<int[]> list = new ArrayList<int[]>();
		           	            		list.add(arr);
		           	            		newMove.setList(list);
	            	            		
	            	            		newMove.setCells(cells);
	            	            		newMove.setValue(current.getValue());
	            	            		newMove.setFrom(cells[2][1]);
	            	            		newMove.setTo(cells[4][3]);
	            	            		newMove.setValue(getEvaluation(newMove,maxPlayer,player));
	            	            		newMove.setSelfMap(iteratoro);
	            	            		newMove.setOpponMap(current.getOpponMap());
	            	            		newMove.setMoveType("J");
	            	            	    move.add(newMove);
	            	            	}
	            			}
	        			}	        			
	        			moves.add(move);
	        			if(move.size() != 0) return moves;
	        		}else if(second(current,maxPlayer,player,col)) {

	        			System.out.println(" checkpoint2 ");
	        			
	        			List<ArrayList<Move>> moves = new ArrayList<ArrayList<Move>>();		
	        			ArrayList<Move> move = new ArrayList<Move>();
	        			Cell[][] cells = current.getCells();
	        			
	        			Map<Integer,Cell> iteratoro;
	        			if(player) {
	        			   iteratoro = current.getSelfMap();  
	        			}else {
	        				iteratoro = current.getOpponMap();
	        			}
	        			int inde = 0;
	        			

	        			if(col.equals("W")) {
	        				for(Integer idx: iteratoro.keySet()) {
	            				
	            				Cell cell = iteratoro.get(idx);
	            	            int r = cell.getRow();
	            	            int c = cell.getCol();	
	            	            	if(r == 13 && c == 15) {
	            	            	    inde = idx;
	            	            	    cells[11][11].setPlayer(homework.SELF);
	            	            		iteratoro.put(inde,cells[11][11]);
	            	            		cells[13][15].setPlayer(homework.FREE);
	            	            		Move newMove = new Move();
	            	            		
	            	            		int[] arr1 = {13,15,11,13};
	            	            		int[] arr2 = {11,13,11,11};
		           	            		List<int[]> list = new ArrayList<int[]>();
		           	            		list.add(arr1);
		           	            		list.add(arr2);
		           	            		newMove.setList(list);
	            	            		
	            	            		newMove.setCells(cells);
	            	            		newMove.setValue(current.getValue());
	            	            		newMove.setFrom(cells[13][15]);
	            	            		newMove.setTo(cells[11][11]);
	            	            		newMove.setValue(getEvaluation(newMove,maxPlayer,player));
	            	            		newMove.setSelfMap(iteratoro);
	            	            		newMove.setOpponMap(current.getOpponMap());
	            	            		newMove.setMoveType("J");
	            	            	    move.add(newMove);
	            	            	}
	            			}
	        			}
	        			
	        			if(col.equals("B")) {
                            for(Integer idx: iteratoro.keySet()) {
	            				
	            				Cell cell = iteratoro.get(idx);
	            	            int r = cell.getRow();
	            	            int c = cell.getCol();	
	            	            	if(r == 2 && c == 0) {
	            	            	    System.out.println("In this loop ");
	            	            		inde = idx;
	            	            	    cells[4][4].setPlayer(homework.SELF);
	            	            		iteratoro.put(inde,cells[4][4]);
	            	            		cells[2][0].setPlayer(homework.FREE);
	            	            		Move newMove = new Move();
	            	            		
	            	            		int[] arr1 = {2,0,4,2};
	            	            		int[] arr2 = {4,2,4,4};
//		           	            		List<int[]> list = new ArrayList<int[]>();
	            	            		List<int[]> list = new ArrayList<int[]>();
	            	            		list.add(arr1);
	            	            		list.add(arr2);
		           	            		newMove.setList(list);
		           	            		
	            	            		newMove.setCells(cells);
	            	            		newMove.setValue(current.getValue());
	            	            		newMove.setFrom(cells[2][0]);
	            	            		newMove.setTo(cells[4][4]);
	            	            		newMove.setValue(getEvaluation(newMove,maxPlayer,player));
	            	            		newMove.setSelfMap(iteratoro);
	            	            		newMove.setOpponMap(current.getOpponMap());
	            	            		newMove.setMoveType("J");
	            	            	    move.add(newMove);
	            	            	}
	            			}
	        			}	        			
	        			moves.add(move);
	        			if(move.size() != 0) return moves;
	    				
	        		}else if(third(current,maxPlayer,player,col)) {
	        			
	        			System.out.println(" checkpoint3 ");
	        			
	        			List<ArrayList<Move>> moves = new ArrayList<ArrayList<Move>>();		
	        			ArrayList<Move> move = new ArrayList<Move>();
	        			Cell[][] cells = current.getCells();
	        			
	        			Map<Integer,Cell> iteratoro;
	        			if(player) {
	        			   iteratoro = current.getSelfMap();  
	        			}else {
	        				iteratoro = current.getOpponMap();
	        			}
	        			
	        			int inde;
	        			if(col.equals("W")) {
	        				for(Integer idx: iteratoro.keySet()) {
	            				
	            				Cell cell = iteratoro.get(idx);
	            	            int r = cell.getRow();
	            	            int c = cell.getCol();	
	            	            	if(r == 14 && c == 14) {
	            	            	   inde = idx;
	            	            	   cells[10][10].setPlayer(homework.SELF);
	           	            		   iteratoro.put(inde,cells[10][10]);
	           	            		   cells[14][14].setPlayer(homework.FREE);
	           	            		   Move newMove = new Move();
	           	            		   int[] arr1 = {14,14,12,12};
	           	            		   int[] arr2 = {12,12,10,10};
	           	            		   
	           	            		   List<int[]> list = new ArrayList<int[]>();
	           	            		   list.add(arr1);
	           	            		   list.add(arr2);

	           	            		   newMove.setList(list);
	           	            		   newMove.setCells(cells);
	           	            		   newMove.setValue(current.getValue());
	           	            		   newMove.setFrom(cells[14][14]);
	           	            		   newMove.setTo(cells[10][10]);
	           	            		   
	           	            		   newMove.setValue(getEvaluation(newMove,maxPlayer,player));
	           	            		   newMove.setSelfMap(iteratoro);
	           	            		   newMove.setOpponMap(current.getOpponMap());
	           	            		   newMove.setMoveType("J");
	           	            	       move.add(newMove);   
	            	            	}
	            			}
	        			}
	        			
	        			if(col.equals("B")) {
                            for(Integer idx: iteratoro.keySet()) {
	            				
	            				Cell cell = iteratoro.get(idx);
	            	            int r = cell.getRow();
	            	            int c = cell.getCol();	
	            	            	if(r == 1 && c == 1) {
	            	            	    inde = idx;
	            	            	    cells[5][5].setPlayer(homework.SELF);
	            	            		iteratoro.put(inde,cells[5][5]);
	            	            		cells[1][1].setPlayer(homework.FREE);
	            	            		Move newMove = new Move();
	            	            		
	            	            		int[] arr1 = {1,1,3,3};
	            	            		int[] arr2 = {3,3,5,5};

		           	            		List<int[]> list = new ArrayList<int[]>();
		           	            		list.add(arr1);
		           	            		list.add(arr2);

		           	            		newMove.setList(list);
	            	            		
		           	            		newMove.setCells(cells);
	            	            		newMove.setValue(current.getValue());
	            	            		newMove.setFrom(cells[1][1]);
	            	            		newMove.setTo(cells[5][5]);
	            	            		newMove.setValue(getEvaluation(newMove,maxPlayer,player));
	            	            		newMove.setSelfMap(iteratoro);
	            	            		newMove.setOpponMap(current.getOpponMap());
	            	            		newMove.setMoveType("J");
	            	            	    move.add(newMove);
	            	            	}
	            			}
	        			}	        			
	        			moves.add(move);
	    				if(move.size() != 0) return moves;
	        			
	        			
	        		}else if(fourth(current,maxPlayer,player,col)) {
	        			
	        			System.out.println(" checkpoint3 ");
	        			
	        			List<ArrayList<Move>> moves = new ArrayList<ArrayList<Move>>();		
	        			ArrayList<Move> move = new ArrayList<Move>();
	        			Cell[][] cells = current.getCells();
	        			
	        			Map<Integer,Cell> iteratoro;
	        			if(player) {
	        			   iteratoro = current.getSelfMap();  
	        			}else {
	        				iteratoro = current.getOpponMap();
	        			}
	        			
	        			int inde;
	        			if(col.equals("W")) {
	        				for(Integer idx: iteratoro.keySet()) {
	            				
	            				Cell cell = iteratoro.get(idx);
	            	            int r = cell.getRow();
	            	            int c = cell.getCol();	
	            	            	if(r == 14 && c == 13) {
	            	            	   inde = idx;
	            	            	   cells[10][9].setPlayer(homework.SELF);
	           	            		   iteratoro.put(inde,cells[10][9]);
	           	            		   cells[14][13].setPlayer(homework.FREE);
	           	            		   Move newMove = new Move();
	           	            		   int[] arr1 = {14,13,12,11};
	           	            		   int[] arr2 = {12,11,10,11};
	           	            		   int[] arr3 = {10,11,10,9};
	           	            		   List<int[]> list = new ArrayList<int[]>();
	           	            		   list.add(arr1);
	           	            		   list.add(arr2);
	           	            		   list.add(arr3);
	           	            		   newMove.setList(list);
	           	            		   newMove.setCells(cells);
	           	            		   newMove.setValue(current.getValue());
	           	            		   newMove.setFrom(cells[14][13]);
	           	            		   newMove.setTo(cells[10][9]);
	           	            		   
	           	            		   newMove.setValue(getEvaluation(newMove,maxPlayer,player));
	           	            		   newMove.setSelfMap(iteratoro);
	           	            		   newMove.setOpponMap(current.getOpponMap());
	           	            		   newMove.setMoveType("J");
	           	            	       move.add(newMove);   
	            	            	}
	            			}
	        			}
	        			
	        			if(col.equals("B")) {
                            for(Integer idx: iteratoro.keySet()) {
	            				
	            				Cell cell = iteratoro.get(idx);
	            	            int r = cell.getRow();
	            	            int c = cell.getCol();	
	            	            	if(r == 3 && c == 0) {
	            	            	    inde = idx;
	            	            	    cells[5][6].setPlayer(homework.SELF);
	            	            		iteratoro.put(inde,cells[5][6]);
	            	            		cells[3][0].setPlayer(homework.FREE);
	            	            		Move newMove = new Move();
	            	            		
	            	            		int[] arr1 = {3,0,5,2};
	            	            		int[] arr2 = {5,2,3,4};
	            	            		int[] arr3 = {3,4,5,4};
	            	            		int[] arr4 = {5,4,5,6};
	            	            		
		           	            		List<int[]> list = new ArrayList<int[]>();
		           	            		list.add(arr1);
		           	            		list.add(arr2);
		           	            		list.add(arr3);
		           	            		list.add(arr4);
		           	            		
		           	            		newMove.setList(list);
	            	            		
		           	            		newMove.setCells(cells);
	            	            		newMove.setValue(current.getValue());
	            	            		newMove.setFrom(cells[3][0]);
	            	            		newMove.setTo(cells[5][6]);
	            	            		newMove.setValue(getEvaluation(newMove,maxPlayer,player));
	            	            		newMove.setSelfMap(iteratoro);
	            	            		newMove.setOpponMap(current.getOpponMap());
	            	            		newMove.setMoveType("J");
	            	            	    move.add(newMove);
	            	            	}
	            			}
	        			}	        			
	        			moves.add(move);
	    				if(move.size() != 0) return moves;
	        			
	        			
	        		}else if(fifth(current,maxPlayer,player,col)) {
	        			
	        			System.out.println(" checkpoint3 ");
	        			
	        			List<ArrayList<Move>> moves = new ArrayList<ArrayList<Move>>();		
	        			ArrayList<Move> move = new ArrayList<Move>();
	        			Cell[][] cells = current.getCells();
	        			
	        			Map<Integer,Cell> iteratoro;
	        			if(player) {
	        			   iteratoro = current.getSelfMap();  
	        			}else {
	        				iteratoro = current.getOpponMap();
	        			}
	        			
	        			int inde;
	        			if(col.equals("W")) {
	        				for(Integer idx: iteratoro.keySet()) {
	            				
	            				Cell cell = iteratoro.get(idx);
	            	            int r = cell.getRow();
	            	            int c = cell.getCol();	
	            	            	if(r == 14 && c == 12) {
	            	            	   inde = idx;
	            	            	   cells[10][12].setPlayer(homework.SELF);
	           	            		   iteratoro.put(inde,cells[10][12]);
	           	            		   cells[14][12].setPlayer(homework.FREE);
	           	            		   Move newMove = new Move();
	           	            		   int[] arr1 = {14,12,12,12};
	           	            		   int[] arr2 = {12,12,10,12};
	           	            	
	           	            		   List<int[]> list = new ArrayList<int[]>();
	           	            		   list.add(arr1);
	           	            		   list.add(arr2);
	           	            		   
	           	            		   newMove.setList(list);
	           	            		   newMove.setCells(cells);
	           	            		   newMove.setValue(current.getValue());
	           	            		   newMove.setFrom(cells[14][12]);
	           	            		   newMove.setTo(cells[10][12]);
	           	            		   
	           	            		   newMove.setValue(getEvaluation(newMove,maxPlayer,player));
	           	            		   newMove.setSelfMap(iteratoro);
	           	            		   newMove.setOpponMap(current.getOpponMap());
	           	            		   newMove.setMoveType("J");
	           	            	       move.add(newMove);   
	            	            	}
	            			}
	        			}
	        			
	        			if(col.equals("B")) {
                            for(Integer idx: iteratoro.keySet()) {
	            				
	            				Cell cell = iteratoro.get(idx);
	            	            int r = cell.getRow();
	            	            int c = cell.getCol();	
	            	            	if(r == 1 && c == 3) {
	            	            	    inde = idx;
	            	            	    cells[5][3].setPlayer(homework.SELF);
	            	            		iteratoro.put(inde,cells[5][3]);
	            	            		cells[1][3].setPlayer(homework.FREE);
	            	            		Move newMove = new Move();
	            	            		
	            	            		int[] arr1 = {1,3,3,3};
	            	            		int[] arr2 = {3,3,5,3};

		           	            		List<int[]> list = new ArrayList<int[]>();
		           	            		list.add(arr1);
		           	            		list.add(arr2);

		           	            		newMove.setList(list);
	            	            		
		           	            		newMove.setCells(cells);
	            	            		newMove.setValue(current.getValue());
	            	            		newMove.setFrom(cells[1][3]);
	            	            		newMove.setTo(cells[5][3]);
	            	            		newMove.setValue(getEvaluation(newMove,maxPlayer,player));
	            	            		newMove.setSelfMap(iteratoro);
	            	            		newMove.setOpponMap(current.getOpponMap());
	            	            		newMove.setMoveType("J");
	            	            	    move.add(newMove);
	            	            	}
	            			}
	        			}	        			
	        			moves.add(move);
	    				if(move.size() != 0) return moves;
	        			
	        			
	        		}else if(sixth(current,maxPlayer,player,col)) {
	        			
	        			System.out.println(" checkpoint3 ");
	        			
	        			List<ArrayList<Move>> moves = new ArrayList<ArrayList<Move>>();		
	        			ArrayList<Move> move = new ArrayList<Move>();
	        			Cell[][] cells = current.getCells();
	        			
	        			Map<Integer,Cell> iteratoro;
	        			if(player) {
	        			   iteratoro = current.getSelfMap();  
	        			}else {
	        				iteratoro = current.getOpponMap();
	        			}
	        			
	        			int inde;
	        			if(col.equals("W")) {
	        				for(Integer idx: iteratoro.keySet()) {
	            				
	            				Cell cell = iteratoro.get(idx);
	            	            int r = cell.getRow();
	            	            int c = cell.getCol();	
	            	            	if(r == 15 && c == 15) {
	            	            	   inde = idx;
	            	            	   cells[9][9].setPlayer(homework.SELF);
	           	            		   iteratoro.put(inde,cells[9][9]);
	           	            		   cells[15][15].setPlayer(homework.FREE);
	           	            		   Move newMove = new Move();
	           	            		   int[] arr1 = {15,15,13,15};
	           	            		   int[] arr2 = {13,15,11,13};
	           	            		   int[] arr3 = {11,13,9,11};
	           	            		   int[] arr4 = {9,11,11,9};
	           	            		   int[] arr5 = {11,9,9,9};
	           	            		   List<int[]> list = new ArrayList<int[]>();
	           	            		   list.add(arr1);
	           	            		   list.add(arr2);
	           	            		   list.add(arr3);
	           	            		   list.add(arr4);
	           	            		   list.add(arr5);
	           	            		   newMove.setList(list);
	           	            		   newMove.setCells(cells);
	           	            		   newMove.setValue(current.getValue());
	           	            		   newMove.setFrom(cells[15][15]);
	           	            		   newMove.setTo(cells[9][9]);
	           	            		   
	           	            		   newMove.setValue(getEvaluation(newMove,maxPlayer,player));
	           	            		   newMove.setSelfMap(iteratoro);
	           	            		   newMove.setOpponMap(current.getOpponMap());
	           	            		   newMove.setMoveType("J");
	           	            	       move.add(newMove);   
	            	            	}
	            			}
	        			}
	        			
	        			if(col.equals("B")) {
                            for(Integer idx: iteratoro.keySet()) {
	            				
	            				Cell cell = iteratoro.get(idx);
	            	            int r = cell.getRow();
	            	            int c = cell.getCol();	
	            	            	if(r == 0 && c == 0) {
	            	            	    inde = idx;
	            	            	    cells[6][6].setPlayer(homework.SELF);
	            	            		iteratoro.put(inde,cells[6][6]);
	            	            		cells[0][0].setPlayer(homework.FREE);
	            	            		Move newMove = new Move();
	            	            		
	            	            		int[] arr1 = {0,0,2,0};
	            	            		int[] arr2 = {2,0,4,2};
	            	            		int[] arr3 = {4,2,6,4};
	            	            		int[] arr4 = {6,4,4,6};
	            	            		int[] arr5 = {4,6,6,6};
	            	            		
		           	            		List<int[]> list = new ArrayList<int[]>();
		           	            		list.add(arr1);
		           	            		list.add(arr2);
		           	            		list.add(arr3);
		           	            		list.add(arr4);
		           	            		list.add(arr5);
		           	            		
		           	            		newMove.setList(list);
	            	            		
		           	            		newMove.setCells(cells);
	            	            		newMove.setValue(current.getValue());
	            	            		newMove.setFrom(cells[0][0]);
	            	            		newMove.setTo(cells[6][6]);
	            	            		newMove.setValue(getEvaluation(newMove,maxPlayer,player));
	            	            		newMove.setSelfMap(iteratoro);
	            	            		newMove.setOpponMap(current.getOpponMap());
	            	            		newMove.setMoveType("J");
	            	            	    move.add(newMove);
	            	            	}
	            			}
	        			}	        			
	        			moves.add(move);
	    				if(move.size() != 0) return moves;
	        			
	        			
	        		}else if(seventh(current,maxPlayer,player,col)) {
	        			
	        			System.out.println(" checkpoint3 ");
	        			
	        			List<ArrayList<Move>> moves = new ArrayList<ArrayList<Move>>();		
	        			ArrayList<Move> move = new ArrayList<Move>();
	        			Cell[][] cells = current.getCells();
	        			
	        			Map<Integer,Cell> iteratoro;
	        			if(player) {
	        			   iteratoro = current.getSelfMap();  
	        			}else {
	        				iteratoro = current.getOpponMap();
	        			}
	        			
	        			int inde;
	        			if(col.equals("W")) {
	        				for(Integer idx: iteratoro.keySet()) {
	            				
	            				Cell cell = iteratoro.get(idx);
	            	            int r = cell.getRow();
	            	            int c = cell.getCol();	
	            	            	if(r == 15 && c == 13) {
	            	            	   inde = idx;
	            	            	   cells[9][11].setPlayer(homework.SELF);
	           	            		   iteratoro.put(inde,cells[9][11]);
	           	            		   cells[15][13].setPlayer(homework.FREE);
	           	            		   Move newMove = new Move();
	           	            		   int[] arr1 = {15,13,15,15};
	           	            		   int[] arr2 = {15,15,13,15};
	           	            		   int[] arr3 = {13,15,11,13};
	           	            		   int[] arr4 = {11,13,9,11};
	           	            		   
	           	            		   List<int[]> list = new ArrayList<int[]>();
	           	            		   list.add(arr1);
	           	            		   list.add(arr2);
	           	            		   list.add(arr3);
	           	            		   list.add(arr4);
	           	            		   
	           	            		   newMove.setList(list);
	           	            		   newMove.setCells(cells);
	           	            		   newMove.setValue(current.getValue());
	           	            		   newMove.setFrom(cells[15][13]);
	           	            		   newMove.setTo(cells[9][11]);
	           	            		   
	           	            		   newMove.setValue(getEvaluation(newMove,maxPlayer,player));
	           	            		   newMove.setSelfMap(iteratoro);
	           	            		   newMove.setOpponMap(current.getOpponMap());
	           	            		   newMove.setMoveType("J");
	           	            	       move.add(newMove);   
	            	            	}
	            			}
	        			}
	        			
	        			if(col.equals("B")) {
                            for(Integer idx: iteratoro.keySet()) {
	            				
	            				Cell cell = iteratoro.get(idx);
	            	            int r = cell.getRow();
	            	            int c = cell.getCol();	
	            	            	if(r == 0 && c == 2) {
	            	            	    inde = idx;
	            	            	    cells[6][4].setPlayer(homework.SELF);
	            	            		iteratoro.put(inde,cells[6][4]);
	            	            		cells[0][2].setPlayer(homework.FREE);
	            	            		Move newMove = new Move();
	            	            		
	            	            		int[] arr1 = {0,2,0,0};
	            	            		int[] arr2 = {0,0,2,0};
	            	            		int[] arr3 = {2,0,4,2};
	            	            		int[] arr4 = {4,2,6,4};
	            	            		
		           	            		List<int[]> list = new ArrayList<int[]>();
		           	            		list.add(arr1);
		           	            		list.add(arr2);
		           	            		list.add(arr3);
		           	            		list.add(arr4);
		           	            		
		           	            		newMove.setList(list);
	            	            		
		           	            		newMove.setCells(cells);
	            	            		newMove.setValue(current.getValue());
	            	            		newMove.setFrom(cells[0][2]);
	            	            		newMove.setTo(cells[6][4]);
	            	            		newMove.setValue(getEvaluation(newMove,maxPlayer,player));
	            	            		newMove.setSelfMap(iteratoro);
	            	            		newMove.setOpponMap(current.getOpponMap());
	            	            		newMove.setMoveType("J");
	            	            	    move.add(newMove);
	            	            	}
	            			}
	        			}	        			
	        			moves.add(move);
	    				if(move.size() != 0) return moves;
	        			
	        			
	        		}else if(eighth(current,maxPlayer,player,col)) {
	        			
	        			System.out.println(" checkpoint3 ");
	        			
	        			List<ArrayList<Move>> moves = new ArrayList<ArrayList<Move>>();		
	        			ArrayList<Move> move = new ArrayList<Move>();
	        			Cell[][] cells = current.getCells();
	        			
	        			Map<Integer,Cell> iteratoro;
	        			if(player) {
	        			   iteratoro = current.getSelfMap();  
	        			}else {
	        				iteratoro = current.getOpponMap();
	        			}
	        			
	        			int inde;
	        			if(col.equals("W")) {
	        				for(Integer idx: iteratoro.keySet()) {
	            				
	            				Cell cell = iteratoro.get(idx);
	            	            int r = cell.getRow();
	            	            int c = cell.getCol();	
	            	            	if(r == 13 && c == 12) {
	            	            	   inde = idx;
	            	            	   cells[12][11].setPlayer(homework.SELF);
	           	            		   iteratoro.put(inde,cells[12][11]);
	           	            		   cells[13][12].setPlayer(homework.FREE);
	           	            		   Move newMove = new Move();
	           	            		   int[] arr1 = {13,12,12,11};
	           	            		   
	           	            		   List<int[]> list = new ArrayList<int[]>();
	           	            		   list.add(arr1);
	           	            		   
	           	            		   newMove.setList(list);
	           	            		   newMove.setCells(cells);
	           	            		   newMove.setValue(current.getValue());
	           	            		   newMove.setFrom(cells[13][12]);
	           	            		   newMove.setTo(cells[12][11]);
	           	            		   
	           	            		   newMove.setValue(getEvaluation(newMove,maxPlayer,player));
	           	            		   newMove.setSelfMap(iteratoro);
	           	            		   newMove.setOpponMap(current.getOpponMap());
	           	            		   newMove.setMoveType("E");
	           	            	       move.add(newMove);   
	            	            	}
	            			}
	        			}
	        			
	        			if(col.equals("B")) {
                            for(Integer idx: iteratoro.keySet()) {
	            				
	            				Cell cell = iteratoro.get(idx);
	            	            int r = cell.getRow();
	            	            int c = cell.getCol();	
	            	            	if(r == 2 && c == 3) {
	            	            	    inde = idx;
	            	            	    cells[3][4].setPlayer(homework.SELF);
	            	            		iteratoro.put(inde,cells[3][4]);
	            	            		cells[2][3].setPlayer(homework.FREE);
	            	            		Move newMove = new Move();
	            	            		
	            	            		int[] arr1 = {2,3,3,4};

		           	            		List<int[]> list = new ArrayList<int[]>();
		           	            		list.add(arr1);

		           	            		newMove.setList(list);
	            	            		
		           	            		newMove.setCells(cells);
	            	            		newMove.setValue(current.getValue());
	            	            		newMove.setFrom(cells[2][3]);
	            	            		newMove.setTo(cells[3][4]);
	            	            		newMove.setValue(getEvaluation(newMove,maxPlayer,player));
	            	            		newMove.setSelfMap(iteratoro);
	            	            		newMove.setOpponMap(current.getOpponMap());
	            	            		newMove.setMoveType("E");
	            	            	    move.add(newMove);
	            	            	}
	            			}
	        			}	        			
	        			moves.add(move);
	    				if(move.size() != 0) return moves;	
	        		}else if(ninth(current,maxPlayer,player,col)) {
	        			
	        			System.out.println(" checkpoint3 ");
	        			
	        			List<ArrayList<Move>> moves = new ArrayList<ArrayList<Move>>();		
	        			ArrayList<Move> move = new ArrayList<Move>();
	        			Cell[][] cells = current.getCells();
	        			
	        			Map<Integer,Cell> iteratoro;
	        			if(player) {
	        			   iteratoro = current.getSelfMap();  
	        			}else {
	        				iteratoro = current.getOpponMap();
	        			}
	        			
	        			int inde;
	        			if(col.equals("W")) {
	        				for(Integer idx: iteratoro.keySet()) {
	            				
	            				Cell cell = iteratoro.get(idx);
	            	            int r = cell.getRow();
	            	            int c = cell.getCol();	
	            	            	if(r == 15 && c == 12) {
	            	            	   inde = idx;
	            	            	   cells[9][10].setPlayer(homework.SELF);
	           	            		   iteratoro.put(inde,cells[9][10]);
	           	            		   cells[15][12].setPlayer(homework.FREE);
	           	            		   Move newMove = new Move();
	           	            		   int[] arr1 = {15,12,15,10};
	           	            		   int[] arr2 = {15,10,13,12};
	           	            		   int[] arr3 = {13,12,11,10};
	           	            		   int[] arr4 = {11,10,9,10};
	           	            		   
	           	            		   List<int[]> list = new ArrayList<int[]>();
	           	            		   list.add(arr1);
	           	            		   list.add(arr2);
	           	            		   list.add(arr3);
	           	            		   list.add(arr4);
	           	            		   
	           	            		   newMove.setList(list);
	           	            		   newMove.setCells(cells);
	           	            		   newMove.setValue(current.getValue());
	           	            		   newMove.setFrom(cells[15][12]);
	           	            		   newMove.setTo(cells[9][10]);
	           	            		   
	           	            		   newMove.setValue(getEvaluation(newMove,maxPlayer,player));
	           	            		   newMove.setSelfMap(iteratoro);
	           	            		   newMove.setOpponMap(current.getOpponMap());
	           	            		   newMove.setMoveType("J");
	           	            	       move.add(newMove);   
	            	            	}
	            			}
	        			}
	        			
	        			if(col.equals("B")) {
                            for(Integer idx: iteratoro.keySet()) {
	            				
	            				Cell cell = iteratoro.get(idx);
	            	            int r = cell.getRow();
	            	            int c = cell.getCol();	
	            	            	if(r == 0 && c == 3) {
	            	            	    inde = idx;
	            	            	    cells[6][5].setPlayer(homework.SELF);
	            	            		iteratoro.put(inde,cells[6][5]);
	            	            		cells[0][3].setPlayer(homework.FREE);
	            	            		Move newMove = new Move();
	            	            		
	            	            		int[] arr1 = {0,3,0,5};
	            	            		int[] arr2 = {0,5,2,3};
	            	            		int[] arr3 = {2,3,4,5};
	            	            		int[] arr4 = {4,5,6,7};
	            	            		int[] arr5 = {6,7,6,5};
	            	            		
		           	            		List<int[]> list = new ArrayList<int[]>();
		           	            		list.add(arr1);
		           	            		list.add(arr2);
		           	            		list.add(arr3);
		           	            		list.add(arr4);
		           	            		list.add(arr5);
		           	            		newMove.setList(list);
	            	            		
		           	            		newMove.setCells(cells);
	            	            		newMove.setValue(current.getValue());
	            	            		newMove.setFrom(cells[0][3]);
	            	            		newMove.setTo(cells[6][5]);
	            	            		newMove.setValue(getEvaluation(newMove,maxPlayer,player));
	            	            		newMove.setSelfMap(iteratoro);
	            	            		newMove.setOpponMap(current.getOpponMap());
	            	            		newMove.setMoveType("J");
	            	            	    move.add(newMove);
	            	            	}
	            			}
	        			}	        			
	        			moves.add(move);
	    				if(move.size() != 0) return moves;
	        			
	        			
	        		}else if(tenth(current,maxPlayer,player,col)) {
	        			
	        			System.out.println(" checkpoint3 ");
	        			
	        			List<ArrayList<Move>> moves = new ArrayList<ArrayList<Move>>();		
	        			ArrayList<Move> move = new ArrayList<Move>();
	        			Cell[][] cells = current.getCells();
	        			
	        			Map<Integer,Cell> iteratoro;
	        			if(player) {
	        			   iteratoro = current.getSelfMap();  
	        			}else {
	        				iteratoro = current.getOpponMap();
	        			}
	        			
	        			int inde;
	        			if(col.equals("W")) {
	        				for(Integer idx: iteratoro.keySet()) {
	            				
	            				Cell cell = iteratoro.get(idx);
	            	            int r = cell.getRow();
	            	            int c = cell.getCol();	
	            	            	if(r == 12 && c == 15) {
	            	            	   inde = idx;
	            	            	   cells[8][9].setPlayer(homework.SELF);
	           	            		   iteratoro.put(inde,cells[8][9]);
	           	            		   cells[12][15].setPlayer(homework.FREE);
	           	            		   Move newMove = new Move();
	           	            		   int[] arr1 = {12,15,10,13};
	           	            		   int[] arr2 = {10,13,10,11};
	           	            		   int[] arr3 = {10,11,8,9};

	           	            		   List<int[]> list = new ArrayList<int[]>();
	           	            		   list.add(arr1);
	           	            		   list.add(arr2);
	           	            		   list.add(arr3);

	           	            		   newMove.setList(list);
	           	            		   newMove.setCells(cells);
	           	            		   newMove.setValue(current.getValue());
	           	            		   newMove.setFrom(cells[12][15]);
	           	            		   newMove.setTo(cells[8][9]);
	           	            		   
	           	            		   newMove.setValue(getEvaluation(newMove,maxPlayer,player));
	           	            		   newMove.setSelfMap(iteratoro);
	           	            		   newMove.setOpponMap(current.getOpponMap());
	           	            		   newMove.setMoveType("J");
	           	            	       move.add(newMove);   
	            	            	}
	            			}
	        			}
	        			
	        			if(col.equals("B")) {
                            for(Integer idx: iteratoro.keySet()) {
	            				
	            				Cell cell = iteratoro.get(idx);
	            	            int r = cell.getRow();
	            	            int c = cell.getCol();	
	            	            	if(r == 3 && c == 2) {
	            	            	    inde = idx;
	            	            	    cells[7][6].setPlayer(homework.SELF);
	            	            		iteratoro.put(inde,cells[7][6]);
	            	            		cells[3][2].setPlayer(homework.FREE);
	            	            		Move newMove = new Move();
	            	            		
	            	            		int[] arr1 = {3,2,5,4};
	            	            		int[] arr2 = {5,4,7,6};

		           	            		List<int[]> list = new ArrayList<int[]>();
		           	            		list.add(arr1);
		           	            		list.add(arr2);

		           	            		newMove.setList(list);
	            	            		
		           	            		newMove.setCells(cells);
	            	            		newMove.setValue(current.getValue());
	            	            		newMove.setFrom(cells[3][2]);
	            	            		newMove.setTo(cells[7][6]);
	            	            		newMove.setValue(getEvaluation(newMove,maxPlayer,player));
	            	            		newMove.setSelfMap(iteratoro);
	            	            		newMove.setOpponMap(current.getOpponMap());
	            	            		newMove.setMoveType("J");
	            	            	    move.add(newMove);
	            	            	}
	            			}
	        			}	        			
	        			moves.add(move);
	    				if(move.size() != 0) return moves;
	        			
	        			
	        		}else if(eleventh(current,maxPlayer,player,col)) {
	        			
	        			System.out.println(" checkpoint3 ");
	        			
	        			List<ArrayList<Move>> moves = new ArrayList<ArrayList<Move>>();		
	        			ArrayList<Move> move = new ArrayList<Move>();
	        			Cell[][] cells = current.getCells();
	        			
	        			Map<Integer,Cell> iteratoro;
	        			if(player) {
	        			   iteratoro = current.getSelfMap();  
	        			}else {
	        				iteratoro = current.getOpponMap();
	        			}
	        			
	        			int inde;
	        			if(col.equals("W")) {
	        				for(Integer idx: iteratoro.keySet()) {
	            				
	            				Cell cell = iteratoro.get(idx);
	            	            int r = cell.getRow();
	            	            int c = cell.getCol();	
	            	            	if(r == 11 && c == 14) {
	            	            	   inde = idx;
	            	            	   cells[7][10].setPlayer(homework.SELF);
	           	            		   iteratoro.put(inde,cells[7][10]);
	           	            		   cells[11][14].setPlayer(homework.FREE);
	           	            		   Move newMove = new Move();
	           	            		   int[] arr1 = {11,14,13,14};
	           	            		   int[] arr2 = {13,14,13,12};
	           	            		   int[] arr3 = {13,12,11,10};
	           	            		   int[] arr4 = {11,10,9,8};
	           	            		   int[] arr5 = {9,8,7,10};

	           	            		   List<int[]> list = new ArrayList<int[]>();
	           	            		   list.add(arr1);
	           	            		   list.add(arr2);
	           	            		   list.add(arr3);
	           	            		   list.add(arr4);
	           	            		   list.add(arr5);
	           	            		   
	           	            		   newMove.setList(list);
	           	            		   newMove.setCells(cells);
	           	            		   newMove.setValue(current.getValue());
	           	            		   newMove.setFrom(cells[11][14]);
	           	            		   newMove.setTo(cells[7][10]);
	           	            		   
	           	            		   newMove.setValue(getEvaluation(newMove,maxPlayer,player));
	           	            		   newMove.setSelfMap(iteratoro);
	           	            		   newMove.setOpponMap(current.getOpponMap());
	           	            		   newMove.setMoveType("J");
	           	            	       move.add(newMove);   
	            	            	}
	            			}
	        			}
	        			
	        			if(col.equals("B")) {
                            for(Integer idx: iteratoro.keySet()) {
	            				
	            				Cell cell = iteratoro.get(idx);
	            	            int r = cell.getRow();
	            	            int c = cell.getCol();	
	            	            	if(r == 0 && c == 1) {
	            	            	    inde = idx;
	            	            	    cells[8][5].setPlayer(homework.SELF);
	            	            		iteratoro.put(inde,cells[8][5]);
	            	            		cells[0][1].setPlayer(homework.FREE);
	            	            		Move newMove = new Move();
	            	            		
	            	            		int[] arr1 = {0,1,2,3};
	            	            		int[] arr2 = {2,3,4,5};
	            	            		int[] arr3 = {4,5,6,7};
	            	            		int[] arr4 = {6,7,8,5};
	            	            		
		           	            		List<int[]> list = new ArrayList<int[]>();
		           	            		list.add(arr1);
		           	            		list.add(arr2);
		           	            		list.add(arr3);
		           	            		list.add(arr4);
		           	            		
		           	            		newMove.setList(list);
	            	            		
		           	            		newMove.setCells(cells);
	            	            		newMove.setValue(current.getValue());
	            	            		newMove.setFrom(cells[0][1]);
	            	            		newMove.setTo(cells[8][5]);
	            	            		newMove.setValue(getEvaluation(newMove,maxPlayer,player));
	            	            		newMove.setSelfMap(iteratoro);
	            	            		newMove.setOpponMap(current.getOpponMap());
	            	            		newMove.setMoveType("J");
	            	            	    move.add(newMove);
	            	            	}
	            			}
	        			}	        			
	        			moves.add(move);
	    				if(move.size() != 0) return moves;
	        			
	        			
	        		}else if(twelveth(current,maxPlayer,player,col)) {
	        			
	        			System.out.println(" checkpoint3 ");
	        			
	        			List<ArrayList<Move>> moves = new ArrayList<ArrayList<Move>>();		
	        			ArrayList<Move> move = new ArrayList<Move>();
	        			Cell[][] cells = current.getCells();
	        			
	        			Map<Integer,Cell> iteratoro;
	        			if(player) {
	        			   iteratoro = current.getSelfMap();  
	        			}else {
	        				iteratoro = current.getOpponMap();
	        			}
	        			
	        			int inde;
	        			if(col.equals("W")) {
	        				for(Integer idx: iteratoro.keySet()) {
	            				
	            				Cell cell = iteratoro.get(idx);
	            	            int r = cell.getRow();
	            	            int c = cell.getCol();	
	            	            	if(r == 12 && c == 14) {
	            	            	   inde = idx;
	            	            	   cells[12][10].setPlayer(homework.SELF);
	           	            		   iteratoro.put(inde,cells[12][10]);
	           	            		   cells[12][14].setPlayer(homework.FREE);
	           	            		   Move newMove = new Move();
	           	            		   int[] arr1 = {12,14,12,12};
	           	            		   int[] arr2 = {12,12,12,10};

	           	            		   List<int[]> list = new ArrayList<int[]>();
	           	            		   list.add(arr1);
	           	            		   list.add(arr2);

	           	            		   newMove.setList(list);
	           	            		   newMove.setCells(cells);
	           	            		   newMove.setValue(current.getValue());
	           	            		   newMove.setFrom(cells[12][14]);
	           	            		   newMove.setTo(cells[12][10]);
	           	            		   
	           	            		   newMove.setValue(getEvaluation(newMove,maxPlayer,player));
	           	            		   newMove.setSelfMap(iteratoro);
	           	            		   newMove.setOpponMap(current.getOpponMap());
	           	            		   newMove.setMoveType("J");
	           	            	       move.add(newMove);   
	            	            	}
	            			}
	        			}
	        			
	        			if(col.equals("B")) {
                            for(Integer idx: iteratoro.keySet()) {
	            				
	            				Cell cell = iteratoro.get(idx);
	            	            int r = cell.getRow();
	            	            int c = cell.getCol();	
	            	            	if(r == 4 && c == 1) {
	            	            	    inde = idx;
	            	            	    cells[6][7].setPlayer(homework.SELF);
	            	            		iteratoro.put(inde,cells[6][7]);
	            	            		cells[4][1].setPlayer(homework.FREE);
	            	            		Move newMove = new Move();
	            	            		
	            	            		int[] arr1 = {4,1,2,1};
	            	            		int[] arr2 = {2,1,2,3};
	            	            		int[] arr3 = {2,3,4,5};
	            	            		int[] arr4 = {4,5,6,7};
	            	            		
		           	            		List<int[]> list = new ArrayList<int[]>();
		           	            		list.add(arr1);
		           	            		list.add(arr2);
		           	            		list.add(arr3);
		           	            		list.add(arr4);
		           	            		
		           	            		newMove.setList(list);
	            	            		
		           	            		newMove.setCells(cells);
	            	            		newMove.setValue(current.getValue());
	            	            		newMove.setFrom(cells[4][1]);
	            	            		newMove.setTo(cells[6][7]);
	            	            		newMove.setValue(getEvaluation(newMove,maxPlayer,player));
	            	            		newMove.setSelfMap(iteratoro);
	            	            		newMove.setOpponMap(current.getOpponMap());
	            	            		newMove.setMoveType("J");
	            	            	    move.add(newMove);
	            	            	}
	            			}
	        			}	        			
	        			moves.add(move);
	    				if(move.size() != 0) return moves;
	        			
	        			
	        		}
	        	
	        		
	        		
	        		
	        		
	        		
	        		
	        	}
                /////////
	        	
	        	///////////////////////
	        	
				ArrayList<Move> oneMove = specialOneMoveToQueueFirst(current,iterator,maxPlayer,player,col);
				ArrayList<Move> Jump = specialJumpToQueueFirst(current,iterator,maxPlayer,player,col);			
				List<ArrayList<Move>> moves = new ArrayList<ArrayList<Move>>();
				
                if(Jump.size() != 0) moves.add(Jump);
				if(oneMove.size() != 0) moves.add(oneMove);
				if(Jump.size() != 0 || oneMove.size() != 0) return moves;
				
				
				
				oneMove = specialOneMoveToQueue(current,iterator,maxPlayer,player,col);
				Jump = specialJumpToQueue(current,iterator,maxPlayer,player,col);			
				moves = new ArrayList<ArrayList<Move>>();
				
                if(Jump.size() != 0) moves.add(Jump);
				if(oneMove.size() != 0) moves.add(oneMove);
				if(Jump.size() != 0 || oneMove.size() != 0) return moves;
				/////////
				
				
					Map<Integer,Cell> it = isStilloutside(current,player,col);
				
					ArrayList<Move> oneMoveoutside = specialOneMoveToQueue(current,it,maxPlayer,player,col);
					ArrayList<Move> Jumpoutside = specialJumpToQueue(current,it,maxPlayer,player,col);			
					List<ArrayList<Move>> movesoutside = new ArrayList<ArrayList<Move>>();
					
	                if(Jumpoutside.size() != 0) movesoutside.add(Jumpoutside);
					if(oneMoveoutside.size() != 0) movesoutside.add(oneMoveoutside);
					
					if(Jumpoutside.size() != 0 || oneMoveoutside.size() != 0) return movesoutside;
			}

			ArrayList<Move> oneMove = oneMoveToQueue(current,maxPlayer,player,col);
			ArrayList<Move> Jump = jumpToQueue(current,maxPlayer,player,col);
			List<ArrayList<Move>> moves = new ArrayList<ArrayList<Move>>();
						
			moves.add(Jump);
			moves.add(oneMove);

			
			if(Jump.size() == 0 && oneMove.size() == 0) {
				if(player && homework.getNumOfSelfInCamp() < 14) {
					ArrayList<Move> oneMoveAgain = oneMoveToQueueagain(current,maxPlayer,player,col);
					List<ArrayList<Move>> movesagain = new ArrayList<ArrayList<Move>>();
					movesagain.add(oneMoveAgain);
					if(oneMoveAgain.size() !=0) return movesagain;
				}else if(!player && homework.getNumOfOpponInCamp() < 14) {
					ArrayList<Move> oneMoveAgain = oneMoveToQueueagain(current,maxPlayer,player,col);
					List<ArrayList<Move>> movesagain = new ArrayList<ArrayList<Move>>();
					
					movesagain.add(oneMoveAgain);
					if(oneMoveAgain.size() !=0) return movesagain;
				}
				
			}
			
			if(player && homework.getNumOfSelfInCamp() >= 14) {	
            	Set<Integer> remaining = new HashSet<Integer>();
            	Set<Cell> targets = new HashSet<Cell>();
            	Cell[][] cells = current.getCells();
        		Map<Integer,Cell> iterat;
        		if(player) {
        		   iterat = current.getSelfMap();  
        		}else {
        			iterat = current.getOpponMap();
        		}
        		
        		boolean check = false;
        		if(col.equals("B")) {
        			for(Integer idx: iterat.keySet()) {
        				check =false;
        				Cell cell = iterat.get(idx);
        	            int r = cell.getRow();
        	            int c = cell.getCol();
        	            for(int[] temp:homework.bottomRightbase) {	
        	            	if(r == temp[0] && c == temp[1]) {
        	            	    check = true;
        	            	}
        	            }
        	            if(!check) {
        	            	remaining.add(idx);
        	            }
        			}
        			
        			for(int[] temp:homework.bottomRightbase) {	
    	            	if(cells[temp[0]][temp[1]].getPlayer().equals("free")) {
    	            	    targets.add(cells[temp[0]][temp[1]]);
    	            	}
    	            }
        			
        		}
        		if(col.equals("W")) {
                	for(Integer idx: iterat.keySet()) {
                		check =false;
            			Cell cell = iterat.get(idx);
                        int r = cell.getRow();
                        int c = cell.getCol();
                        for(int[] temp:homework.topLeftbase) {
                        	if(r == temp[0] && c == temp[1]) {
        	            	    check = true;
        	            	}
        	            }
        	            if(!check) {
        	            	remaining.add(idx);
        	            }
                    }
                	for(int[] temp:homework.topLeftbase) {	
    	            	if(cells[temp[0]][temp[1]].getPlayer().equals("free")) {
    	            	    targets.add(cells[temp[0]][temp[1]]);
    	            	}
    	            }
                }            	
            	

//        		System.out.println(" check is " + targets.size() + " " + remaining.size());
        		
        		
            	ArrayList<Move> endOneMove = endOneMoveToQueue(current,iterat,targets,maxPlayer,player,col);			
				List<ArrayList<Move>> endMoves = new ArrayList<ArrayList<Move>>();

				
				
				
                endMoves.add(endOneMove);
                
				
				if(endOneMove.size() == 0) {
//				     endOneMove = EXendOneMoveToQueue(current,iterat,targets,maxPlayer,player,col);
					endOneMove = EXendOneMoveToQueue(current,iterat,remaining,targets,maxPlayer,player,col);
				     endMoves.add(endOneMove);
				}
				if(endOneMove.size() != 0) {
					System.out.println(" check is " + targets.size() + " " + remaining.size() + " " + endOneMove.size());
					return endMoves;
				}

			}
			
			if(!player && homework.getNumOfOpponInCamp() >= 14) {	

            	Set<Integer> remaining = new HashSet<Integer>();
            	Set<Cell> targets = new HashSet<Cell>();
            	Cell[][] cells = current.getCells();
        		Map<Integer,Cell> iterat;
        		if(player) {
        		   iterat = current.getSelfMap();  
        		}else {
        			iterat = current.getOpponMap();
        		}
        		boolean check = false;
        		if(col.equals("B")) {
        			for(Integer idx: iterat.keySet()) {
        				check =false;
        				Cell cell = iterat.get(idx);
        	            int r = cell.getRow();
        	            int c = cell.getCol();
        	            for(int[] temp:homework.bottomRightbase) {	
        	            	if(r == temp[0] && c == temp[1]) {
        	            	    check = true;
        	            	}
        	            }
        	            if(!check) {
        	            	
        	            	remaining.add(idx);
        	            }
        			}
        			
        			for(int[] temp:homework.bottomRightbase) {	
    	            	if(cells[temp[0]][temp[1]].getPlayer().equals("free")) {
    	            	    targets.add(cells[temp[0]][temp[1]]);
    	            	}
    	            }
        			
        		}
        		if(col.equals("W")) {
                	for(Integer idx: iterat.keySet()) {
                		check =false;
            			Cell cell = iterat.get(idx);
                        int r = cell.getRow();
                        int c = cell.getCol();
                        for(int[] temp:homework.topLeftbase) {
                        	if(r == temp[0] && c == temp[1]) {
        	            	    check = true;
        	            	}
        	            }
        	            if(!check) {
        	            	remaining.add(idx);
        	            }
                    }
                	for(int[] temp:homework.topLeftbase) {	
    	            	if(cells[temp[0]][temp[1]].getPlayer().equals("free")) {
    	            	    targets.add(cells[temp[0]][temp[1]]);
    	            	}
    	            }
                }            	

        		ArrayList<Move> endOneMove = endOneMoveToQueue(current,iterat,targets,maxPlayer,player,col);		
				List<ArrayList<Move>> endMoves = new ArrayList<ArrayList<Move>>();

                endMoves.add(endOneMove);
							
//				if(endOneMove.size() != 0) return endMoves;
                if(endOneMove.size() == 0) {
				     endOneMove = EXendOneMoveToQueue(current,iterat,remaining,targets,maxPlayer,player,col);
				     endMoves.add(endOneMove);
				}
                if(endOneMove.size() != 0) return endMoves;
			}			

			return moves;
		}

		return null;
	}
	
	public boolean beggining(Move current, boolean maxPlayer,boolean player,String col){
		Cell[][] cells = current.getCells();
		
		if(player) {
			if(col.equals("W")) {
//				if(cells[14][15].getPlayer().equals("self") && 
//				   cells[15][14].getPlayer().equals("self")	&& 
//				   cells[13][15].getPlayer().equals("self")	&& 
//				   cells[12][15].getPlayer().equals("self")	&& 
//				   cells[15][12].getPlayer().equals("self")	&& 
//				   cells[11][15].getPlayer().equals("self")	&& 
//				   cells[15][11].getPlayer().equals("self")	&& 
//				   cells[14][14].getPlayer().equals("self")	&& 
//				   cells[13][14].getPlayer().equals("self")	&& 
//				   cells[14][13].getPlayer().equals("self")	&& 
//				   cells[12][14].getPlayer().equals("self")	&& 
//				   cells[14][12].getPlayer().equals("self")	&& 
//				   cells[11][14].getPlayer().equals("self")	&& 
//				   cells[14][11].getPlayer().equals("self")	&& 
//				   cells[13][12].getPlayer().equals("self")	&& 
//				   cells[12][13].getPlayer().equals("self") 
//				   	)
				        if(cells[15][14].getPlayer().equals("self")	&& 
						   cells[15][11].getPlayer().equals("self")	&& 
						   cells[14][15].getPlayer().equals("self")	&& 
						   cells[14][11].getPlayer().equals("self")	&& 
						   cells[13][13].getPlayer().equals("self")	&& 
						   cells[12][13].getPlayer().equals("self")	&& 
						   cells[11][15].getPlayer().equals("self") 
						   	) return true;				
			}
			if(col.equals("B")) {
//				        if( 
//						   cells[0][1].getPlayer().equals("self")	&& 
//						   cells[1][0].getPlayer().equals("self")	&& 
//						   cells[2][0].getPlayer().equals("self")	&& 
//						   cells[0][3].getPlayer().equals("self")	&& 
//						   cells[3][0].getPlayer().equals("self")	&& 
//						   cells[4][0].getPlayer().equals("self")	&& 
//						   cells[0][4].getPlayer().equals("self")	&& 
//						   cells[1][1].getPlayer().equals("self")	&& 
//						   cells[1][2].getPlayer().equals("self")	&& 
//						   cells[1][3].getPlayer().equals("self")	&& 
//						   cells[1][4].getPlayer().equals("self")	&& 
//						   cells[2][1].getPlayer().equals("self")	&& 
//						   cells[3][1].getPlayer().equals("self")	&& 
//						   cells[4][1].getPlayer().equals("self")   && 
//						   cells[2][3].getPlayer().equals("self")   && 
//						   cells[3][2].getPlayer().equals("self")	 
//						   	) return true;
				        if(cells[0][4].getPlayer().equals("self")	&& 
						   cells[1][0].getPlayer().equals("self")	&& 
						   cells[1][2].getPlayer().equals("self")	&& 
						   cells[1][4].getPlayer().equals("self")	&& 
						   cells[2][2].getPlayer().equals("self")	&& 
						   cells[3][1].getPlayer().equals("self")	&& 
						   cells[4][0].getPlayer().equals("self")	 
						   	) return true;
			}
		}
		return false;
	}
	
	public boolean first(Move current, boolean maxPlayer,boolean player,String col) {
        Cell[][] cells = current.getCells();
		
		if(player) {
			if(col.equals("W")) {
//				if(cells[15][15].getPlayer().equals("self") && 
//				   cells[15][13].getPlayer().equals("self") &&
//				   cells[13][13].getPlayer().equals("self")
//				   	)
			     if(cells[15][15].getPlayer().equals("self") && 
					cells[15][13].getPlayer().equals("self") &&
					cells[15][12].getPlayer().equals("self") &&
					cells[14][14].getPlayer().equals("self") &&
					cells[14][13].getPlayer().equals("self") &&
					cells[14][12].getPlayer().equals("self") &&
					cells[13][15].getPlayer().equals("self") &&
					cells[13][14].getPlayer().equals("self") &&
					cells[13][12].getPlayer().equals("self") &&
					cells[12][15].getPlayer().equals("self") &&
					cells[12][14].getPlayer().equals("self") &&
					cells[11][14].getPlayer().equals("self")
					) return true;				
			}
			if(col.equals("B")) {
//				        if( 
//						   cells[0][0].getPlayer().equals("self")	&& 
//						   cells[0][2].getPlayer().equals("self")   &&
//						   cells[2][2].getPlayer().equals("self")	 
//						   	) return true;
				        if(cells[0][0].getPlayer().equals("self")	&& 
						   cells[0][1].getPlayer().equals("self")	&& 
						   cells[0][2].getPlayer().equals("self")	&& 
						   cells[0][3].getPlayer().equals("self")	&& 
						   cells[1][1].getPlayer().equals("self")	&& 
						   cells[1][3].getPlayer().equals("self")	&& 
						   cells[2][0].getPlayer().equals("self")	&& 
						   cells[2][1].getPlayer().equals("self")	&& 
						   cells[2][3].getPlayer().equals("self")	&& 
						   cells[3][0].getPlayer().equals("self")	&& 
						   cells[3][2].getPlayer().equals("self")	&& 
						   cells[4][1].getPlayer().equals("self")	 
						   	) return true;
			}
		}
		return false;
	}
	
	public boolean second(Move current, boolean maxPlayer,boolean player,String col) {
        Cell[][] cells = current.getCells();
		
		if(player) {
			if(col.equals("W")) {
//				if(cells[12][12].getPlayer().equals("self") && 
//				   cells[15][15].getPlayer().equals("self") &&
//				   cells[15][13].getPlayer().equals("self")
////				   cells[13][15].getPlayer().equals("self")
//				   	) 
				     if(cells[15][15].getPlayer().equals("self") && 
						cells[15][13].getPlayer().equals("self") &&
						cells[15][12].getPlayer().equals("self") &&
						cells[14][14].getPlayer().equals("self") &&
						cells[14][13].getPlayer().equals("self") &&
						cells[14][12].getPlayer().equals("self") &&
						cells[13][15].getPlayer().equals("self") &&
						cells[11][12].getPlayer().equals("self") &&
						cells[13][12].getPlayer().equals("self") &&
						cells[12][15].getPlayer().equals("self") &&
						cells[12][14].getPlayer().equals("self") &&
						cells[11][14].getPlayer().equals("self")
						) return true;				
//					return true;				
			}
			if(col.equals("B")) {
//				        if( 
//						   cells[0][0].getPlayer().equals("self")	&& 
//						   cells[3][3].getPlayer().equals("self")   &&
//						   cells[0][2].getPlayer().equals("self")
////						   cells[2][0].getPlayer().equals("self")	 
//						   	) return true;	
				        if(cells[0][0].getPlayer().equals("self")	&& 
						   cells[0][1].getPlayer().equals("self")	&& 
						   cells[0][2].getPlayer().equals("self")	&& 
						   cells[0][3].getPlayer().equals("self")	&& 
						   cells[1][1].getPlayer().equals("self")	&& 
						   cells[1][3].getPlayer().equals("self")	&& 
						   cells[2][0].getPlayer().equals("self")	&& 
						   cells[4][3].getPlayer().equals("self")	&& 
						   cells[2][3].getPlayer().equals("self")	&& 
						   cells[3][0].getPlayer().equals("self")	&& 
						   cells[3][2].getPlayer().equals("self")	&& 
						   cells[4][1].getPlayer().equals("self")	 
						   	) return true;
			}
		}
		return false;
	}
	
	public boolean third(Move current, boolean maxPlayer,boolean player,String col) {
        Cell[][] cells = current.getCells();
		
		if(player) {
			if(col.equals("W")) {
//				if(cells[12][12].getPlayer().equals("self") && 
//				   cells[11][13].getPlayer().equals("self") &&
//				   cells[15][15].getPlayer().equals("self")
//				   	) return true;
				if(cells[15][15].getPlayer().equals("self") && 
						cells[15][13].getPlayer().equals("self") &&
						cells[15][12].getPlayer().equals("self") &&
						cells[14][14].getPlayer().equals("self") &&
						cells[14][13].getPlayer().equals("self") &&
						cells[14][12].getPlayer().equals("self") &&
						cells[11][11].getPlayer().equals("self") &&
						cells[11][12].getPlayer().equals("self") &&
						cells[13][12].getPlayer().equals("self") &&
						cells[12][15].getPlayer().equals("self") &&
						cells[12][14].getPlayer().equals("self") &&
						cells[11][14].getPlayer().equals("self")
						) return true;
			}
			if(col.equals("B")) {
//				        if( 
//						   cells[4][2].getPlayer().equals("self")	&& 
//						   cells[3][3].getPlayer().equals("self")   &&
//						   cells[0][0].getPlayer().equals("self")	 
//						   	) return true;	
				        if(cells[0][0].getPlayer().equals("self")	&& 
						   cells[0][1].getPlayer().equals("self")	&& 
						   cells[0][2].getPlayer().equals("self")	&& 
						   cells[0][3].getPlayer().equals("self")	&& 
						   cells[1][1].getPlayer().equals("self")	&& 
						   cells[1][3].getPlayer().equals("self")	&& 
						   cells[4][4].getPlayer().equals("self")	&& 
						   cells[4][3].getPlayer().equals("self")	&& 
						   cells[2][3].getPlayer().equals("self")	&& 
						   cells[3][0].getPlayer().equals("self")	&& 
						   cells[3][2].getPlayer().equals("self")	&& 
						   cells[4][1].getPlayer().equals("self")	 
						   	) return true;
			}
		}
		return false;
	}
	
	public boolean fourth(Move current, boolean maxPlayer,boolean player,String col) {
        Cell[][] cells = current.getCells();
		
		if(player) {
			if(col.equals("W")) {
				if(cells[15][15].getPlayer().equals("self") && 
						cells[15][13].getPlayer().equals("self") &&
						cells[15][12].getPlayer().equals("self") &&
						cells[10][10].getPlayer().equals("self") &&
						cells[14][13].getPlayer().equals("self") &&
						cells[14][12].getPlayer().equals("self") &&
						cells[11][11].getPlayer().equals("self") &&
						cells[11][12].getPlayer().equals("self") &&
						cells[13][12].getPlayer().equals("self") &&
						cells[12][15].getPlayer().equals("self") &&
						cells[12][14].getPlayer().equals("self") &&
						cells[11][14].getPlayer().equals("self")
						) return true;
			}
			if(col.equals("B")) {
				   if(cells[0][0].getPlayer().equals("self")	&& 
						   cells[0][1].getPlayer().equals("self")	&& 
						   cells[0][2].getPlayer().equals("self")	&& 
						   cells[0][3].getPlayer().equals("self")	&& 
						   cells[5][5].getPlayer().equals("self")	&& 
						   cells[1][3].getPlayer().equals("self")	&& 
						   cells[4][4].getPlayer().equals("self")	&& 
						   cells[4][3].getPlayer().equals("self")	&& 
						   cells[2][3].getPlayer().equals("self")	&& 
						   cells[3][0].getPlayer().equals("self")	&& 
						   cells[3][2].getPlayer().equals("self")	&& 
						   cells[4][1].getPlayer().equals("self")	 
						   	) return true;				
			}
		}
		return false;
	}	
	
	public boolean fifth(Move current, boolean maxPlayer,boolean player,String col) {
        Cell[][] cells = current.getCells();
		
		if(player) {
			if(col.equals("W")) {
				if(cells[15][15].getPlayer().equals("self") && 
						cells[15][13].getPlayer().equals("self") &&
						cells[15][12].getPlayer().equals("self") &&
						cells[10][10].getPlayer().equals("self") &&
						cells[10][9].getPlayer().equals("self") &&
						cells[14][12].getPlayer().equals("self") &&
						cells[11][11].getPlayer().equals("self") &&
						cells[11][12].getPlayer().equals("self") &&
						cells[13][12].getPlayer().equals("self") &&
						cells[12][15].getPlayer().equals("self") &&
						cells[12][14].getPlayer().equals("self") &&
						cells[11][14].getPlayer().equals("self")
						) return true;
			}
			if(col.equals("B")) {
				if(cells[0][0].getPlayer().equals("self")	&& 
						   cells[0][1].getPlayer().equals("self")	&& 
						   cells[0][2].getPlayer().equals("self")	&& 
						   cells[0][3].getPlayer().equals("self")	&& 
						   cells[5][5].getPlayer().equals("self")	&& 
						   cells[1][3].getPlayer().equals("self")	&& 
						   cells[4][4].getPlayer().equals("self")	&& 
						   cells[4][3].getPlayer().equals("self")	&& 
						   cells[2][3].getPlayer().equals("self")	&& 
						   cells[5][6].getPlayer().equals("self")	&& 
						   cells[3][2].getPlayer().equals("self")	&& 
						   cells[4][1].getPlayer().equals("self")	 
						   	) return true;				
			}
		}
		return false;
	}
	
	public boolean sixth(Move current, boolean maxPlayer,boolean player,String col) {
        Cell[][] cells = current.getCells();
		
		if(player) {
			if(col.equals("W")) {
				if(cells[15][15].getPlayer().equals("self") && 
						cells[15][13].getPlayer().equals("self") &&
						cells[15][12].getPlayer().equals("self") &&
						cells[10][10].getPlayer().equals("self") &&
						cells[10][9].getPlayer().equals("self") &&
						cells[10][12].getPlayer().equals("self") &&
						cells[11][11].getPlayer().equals("self") &&
						cells[11][12].getPlayer().equals("self") &&
						cells[13][12].getPlayer().equals("self") &&
						cells[12][15].getPlayer().equals("self") &&
						cells[12][14].getPlayer().equals("self") &&
						cells[11][14].getPlayer().equals("self")
						) return true;
			}
			if(col.equals("B")) {
				if(cells[0][0].getPlayer().equals("self")	&& 
						   cells[0][1].getPlayer().equals("self")	&& 
						   cells[0][2].getPlayer().equals("self")	&& 
						   cells[0][3].getPlayer().equals("self")	&& 
						   cells[5][5].getPlayer().equals("self")	&& 
						   cells[5][3].getPlayer().equals("self")	&& 
						   cells[4][4].getPlayer().equals("self")	&& 
						   cells[4][3].getPlayer().equals("self")	&& 
						   cells[2][3].getPlayer().equals("self")	&& 
						   cells[5][6].getPlayer().equals("self")	&& 
						   cells[3][2].getPlayer().equals("self")	&& 
						   cells[4][1].getPlayer().equals("self")	 
						   	) return true;				
			}
		}
		return false;
	}
	
	public boolean seventh(Move current, boolean maxPlayer,boolean player,String col) {
        Cell[][] cells = current.getCells();
		
		if(player) {
			if(col.equals("W")) {
				if(cells[9][9].getPlayer().equals("self") && 
						cells[15][13].getPlayer().equals("self") &&
						cells[15][12].getPlayer().equals("self") &&
						cells[10][10].getPlayer().equals("self") &&
						cells[10][9].getPlayer().equals("self") &&
						cells[10][12].getPlayer().equals("self") &&
						cells[11][11].getPlayer().equals("self") &&
						cells[11][12].getPlayer().equals("self") &&
						cells[13][12].getPlayer().equals("self") &&
						cells[12][15].getPlayer().equals("self") &&
						cells[12][14].getPlayer().equals("self") &&
						cells[11][14].getPlayer().equals("self")
						) return true;
			}
			if(col.equals("B")) {
				if(cells[6][6].getPlayer().equals("self")	&& 
						   cells[0][1].getPlayer().equals("self")	&& 
						   cells[0][2].getPlayer().equals("self")	&& 
						   cells[0][3].getPlayer().equals("self")	&& 
						   cells[5][5].getPlayer().equals("self")	&& 
						   cells[5][3].getPlayer().equals("self")	&& 
						   cells[4][4].getPlayer().equals("self")	&& 
						   cells[4][3].getPlayer().equals("self")	&& 
						   cells[2][3].getPlayer().equals("self")	&& 
						   cells[5][6].getPlayer().equals("self")	&& 
						   cells[3][2].getPlayer().equals("self")	&& 
						   cells[4][1].getPlayer().equals("self")	 
						   	) return true;				
			}
		}
		return false;
	}
	
	
	public boolean eighth(Move current, boolean maxPlayer,boolean player,String col) {
        Cell[][] cells = current.getCells();
		
		if(player) {
			if(col.equals("W")) {
				if(cells[9][9].getPlayer().equals("self") && 
						cells[9][11].getPlayer().equals("self") &&
						cells[15][12].getPlayer().equals("self") &&
						cells[10][10].getPlayer().equals("self") &&
						cells[10][9].getPlayer().equals("self") &&
						cells[10][12].getPlayer().equals("self") &&
						cells[11][11].getPlayer().equals("self") &&
						cells[11][12].getPlayer().equals("self") &&
						cells[13][12].getPlayer().equals("self") &&
						cells[12][15].getPlayer().equals("self") &&
						cells[12][14].getPlayer().equals("self") &&
						cells[11][14].getPlayer().equals("self")
						) return true;
			}
			if(col.equals("B")) {
				if(cells[6][6].getPlayer().equals("self")	&& 
						   cells[0][1].getPlayer().equals("self")	&& 
						   cells[6][4].getPlayer().equals("self")	&& 
						   cells[0][3].getPlayer().equals("self")	&& 
						   cells[5][5].getPlayer().equals("self")	&& 
						   cells[5][3].getPlayer().equals("self")	&& 
						   cells[4][4].getPlayer().equals("self")	&& 
						   cells[4][3].getPlayer().equals("self")	&& 
						   cells[2][3].getPlayer().equals("self")	&& 
						   cells[5][6].getPlayer().equals("self")	&& 
						   cells[3][2].getPlayer().equals("self")	&& 
						   cells[4][1].getPlayer().equals("self")	 
						   	) return true;				
			}
		}
		return false;
	}
	
	public boolean ninth(Move current, boolean maxPlayer,boolean player,String col) {
        Cell[][] cells = current.getCells();
		
		if(player) {
			if(col.equals("W")) {
				if(cells[9][9].getPlayer().equals("self") && 
						cells[9][11].getPlayer().equals("self") &&
						cells[15][12].getPlayer().equals("self") &&
						cells[10][10].getPlayer().equals("self") &&
						cells[10][9].getPlayer().equals("self") &&
						cells[10][12].getPlayer().equals("self") &&
						cells[11][11].getPlayer().equals("self") &&
						cells[11][12].getPlayer().equals("self") &&
						cells[12][11].getPlayer().equals("self") &&
						cells[12][15].getPlayer().equals("self") &&
						cells[12][14].getPlayer().equals("self") &&
						cells[11][14].getPlayer().equals("self")
						) return true;
			}
			if(col.equals("B")) {
				if(cells[6][6].getPlayer().equals("self")	&& 
						   cells[0][1].getPlayer().equals("self")	&& 
						   cells[6][4].getPlayer().equals("self")	&& 
						   cells[0][3].getPlayer().equals("self")	&& 
						   cells[5][5].getPlayer().equals("self")	&& 
						   cells[5][3].getPlayer().equals("self")	&& 
						   cells[4][4].getPlayer().equals("self")	&& 
						   cells[4][3].getPlayer().equals("self")	&& 
						   cells[3][4].getPlayer().equals("self")	&& 
						   cells[5][6].getPlayer().equals("self")	&& 
						   cells[3][2].getPlayer().equals("self")	&& 
						   cells[4][1].getPlayer().equals("self")	 
						   	) return true;				
			}
		}
		return false;
	}
	
	public boolean tenth(Move current, boolean maxPlayer,boolean player,String col) {
        Cell[][] cells = current.getCells();
		
		if(player) {
			if(col.equals("W")) {
				if(cells[9][9].getPlayer().equals("self") && 
						cells[9][11].getPlayer().equals("self") &&
						cells[9][10].getPlayer().equals("self") &&
						cells[10][10].getPlayer().equals("self") &&
						cells[10][9].getPlayer().equals("self") &&
						cells[10][12].getPlayer().equals("self") &&
						cells[11][11].getPlayer().equals("self") &&
						cells[11][12].getPlayer().equals("self") &&
						cells[12][11].getPlayer().equals("self") &&
						cells[12][15].getPlayer().equals("self") &&
						cells[12][14].getPlayer().equals("self") &&
						cells[11][14].getPlayer().equals("self")
						) return true;
			}
			if(col.equals("B")) {
				if(cells[6][6].getPlayer().equals("self")	&& 
						   cells[0][1].getPlayer().equals("self")	&& 
						   cells[6][4].getPlayer().equals("self")	&& 
						   cells[6][5].getPlayer().equals("self")	&& 
						   cells[5][5].getPlayer().equals("self")	&& 
						   cells[5][3].getPlayer().equals("self")	&& 
						   cells[4][4].getPlayer().equals("self")	&& 
						   cells[4][3].getPlayer().equals("self")	&& 
						   cells[3][4].getPlayer().equals("self")	&& 
						   cells[5][6].getPlayer().equals("self")	&& 
						   cells[3][2].getPlayer().equals("self")	&& 
						   cells[4][1].getPlayer().equals("self")	 
						   	) return true;					
			}
		}
		return false;
	}
	
	public boolean eleventh(Move current, boolean maxPlayer,boolean player,String col) {
        Cell[][] cells = current.getCells();
		
		if(player) {
			if(col.equals("W")) {
				if(cells[9][9].getPlayer().equals("self") && 
						cells[9][11].getPlayer().equals("self") &&
						cells[9][10].getPlayer().equals("self") &&
						cells[10][10].getPlayer().equals("self") &&
						cells[10][9].getPlayer().equals("self") &&
						cells[10][12].getPlayer().equals("self") &&
						cells[11][11].getPlayer().equals("self") &&
						cells[11][12].getPlayer().equals("self") &&
						cells[12][11].getPlayer().equals("self") &&
						cells[8][9].getPlayer().equals("self") &&
						cells[12][14].getPlayer().equals("self") &&
						cells[11][14].getPlayer().equals("self")
						) return true;
			}
			if(col.equals("B")) {
				if(cells[6][6].getPlayer().equals("self")	&& 
						   cells[0][1].getPlayer().equals("self")	&& 
						   cells[6][4].getPlayer().equals("self")	&& 
						   cells[6][5].getPlayer().equals("self")	&& 
						   cells[5][5].getPlayer().equals("self")	&& 
						   cells[5][3].getPlayer().equals("self")	&& 
						   cells[4][4].getPlayer().equals("self")	&& 
						   cells[4][3].getPlayer().equals("self")	&& 
						   cells[3][4].getPlayer().equals("self")	&& 
						   cells[5][6].getPlayer().equals("self")	&& 
						   cells[7][6].getPlayer().equals("self")	&& 
						   cells[4][1].getPlayer().equals("self")	 
						   	) return true;				
			}
		}
		return false;
	}
	
	public boolean twelveth(Move current, boolean maxPlayer,boolean player,String col) {
        Cell[][] cells = current.getCells();
		
		if(player) {
			if(col.equals("W")) {
				if(cells[9][9].getPlayer().equals("self") && 
						cells[9][11].getPlayer().equals("self") &&
						cells[9][10].getPlayer().equals("self") &&
						cells[10][10].getPlayer().equals("self") &&
						cells[10][9].getPlayer().equals("self") &&
						cells[10][12].getPlayer().equals("self") &&
						cells[11][11].getPlayer().equals("self") &&
						cells[11][12].getPlayer().equals("self") &&
						cells[12][11].getPlayer().equals("self") &&
						cells[8][9].getPlayer().equals("self") &&
						cells[12][14].getPlayer().equals("self") &&
						cells[7][10].getPlayer().equals("self")
						) return true;
			}
			if(col.equals("B")) {
				if(cells[6][6].getPlayer().equals("self")	&& 
						   cells[8][5].getPlayer().equals("self")	&& 
						   cells[6][4].getPlayer().equals("self")	&& 
						   cells[6][5].getPlayer().equals("self")	&& 
						   cells[5][5].getPlayer().equals("self")	&& 
						   cells[5][3].getPlayer().equals("self")	&& 
						   cells[4][4].getPlayer().equals("self")	&& 
						   cells[4][3].getPlayer().equals("self")	&& 
						   cells[3][4].getPlayer().equals("self")	&& 
						   cells[5][6].getPlayer().equals("self")	&& 
						   cells[7][6].getPlayer().equals("self")	&& 
						   cells[4][1].getPlayer().equals("self")	 
						   	) return true;				
			}
		}
		return false;
	}
	
	
	private Map<Integer,Cell> isStillInCamp(Move current,boolean player,String col) {
		Map<Integer,Cell> result = new HashMap<Integer,Cell>();
		Map<Integer,Cell> iterator;
		if(player) {
		   iterator = current.getSelfMap();  
		}else {
			iterator = current.getOpponMap();
		}
		
		if(col.equals("B")) {
			for(Integer idx: iterator.keySet()) {
				Cell cell = iterator.get(idx);
	            int r = cell.getRow();
	            int c = cell.getCol();
	            for(int[] temp:homework.topLeftbase) {
	            	if(r == temp[0] && c == temp[1]) {
	            	    result.put(idx, cell);
	            	}
	            }
			}

        }
		if(col.equals("W")){
        	for(Integer idx: iterator.keySet()) {
    			Cell cell = iterator.get(idx);
                int r = cell.getRow();
                int c = cell.getCol();
                for(int[] temp:homework.bottomRightbase) {
	            	if(r == temp[0] && c == temp[1]) {
	            	    result.put(idx, cell);
	            	}
	            }
            }
        }		
		return result;
	}
	
	
	
	private Map<Integer,Cell>isStilloutside(Move current,boolean player,String col) {
		Map<Integer,Cell> result = new HashMap<Integer,Cell>();
		Map<Integer,Cell> iterator;
		if(player) {
		   iterator = current.getSelfMap();  
		}else {
			iterator = current.getOpponMap();
		}
		
		if(col.equals("B")) {
			for(Integer idx: iterator.keySet()) {
				Cell cell = iterator.get(idx);
	            int r = cell.getRow();
	            int c = cell.getCol();
	            for(int[] temp:homework.outsidetopLeftbase) {
	            	if(r == temp[0] && c == temp[1]) {
	            	    result.put(idx, cell);
	            	}
	            }
			}

        }
		if(col.equals("W")) {
        	for(Integer idx: iterator.keySet()) {
    			Cell cell = iterator.get(idx);
                int r = cell.getRow();
                int c = cell.getCol();
                for(int[] temp:homework.outsidebottomRightbase) {
	            	if(r == temp[0] && c == temp[1]) {
	            	    result.put(idx, cell);
	            	}
	            }
            }
        }		
		return result;
	}
	
	
	private ArrayList<Move> specialOneMoveToQueue(Move current,Map<Integer,Cell> iter, boolean maxPlayer,boolean player,String col) {
		
		ArrayList<Move> moves = new ArrayList<Move>();
		Cell[][] cells = current.getCells();
		
		Map<Integer,Cell> iterator;
		if(player) {
		   iterator = current.getSelfMap();  
		}else {
			iterator = current.getOpponMap();
		}

		for(Integer idx: iter.keySet()) {
			Cell cell = iterator.get(idx);
			
            for(int[] dir:dirs) {
            	int r = cell.getRow() + dir[0];
                int c = cell.getCol() + dir[1];
                
              if(r >= 0 && r<cells.length && c >= 0 && c < cells[0].length) {
            	if(player && cells[r][c].getPlayer().equals("free")) {
            		cells[r][c].setPlayer(homework.SELF);
            		iterator.put(idx,cells[r][c]);
            		cells[cell.getRow()][cell.getCol()].setPlayer(homework.FREE);
            		Move newMove = new Move();
            		
            		int[] arr = {cell.getRow(),cell.getCol(),r,c};
            		List<int[]> list = new ArrayList<int[]>();
            		list.add(arr);
            		newMove.setList(list);
            		
            		newMove.setCells(cells);
            		newMove.setValue(current.getValue());
            		newMove.setFrom(cells[cell.getRow()][cell.getCol()]);
            		newMove.setTo(cells[r][c]);
            		newMove.setValue(getEvaluation(newMove,maxPlayer,player));
            		newMove.setSelfMap(iterator);
            		newMove.setOpponMap(current.getOpponMap());
            		newMove.setMoveType("E");
                    double diff = newMove.getValue() - current.getValue();
            		
            		if(col.equals("W") && diff < 0) {
            			moves.add(newMove);
                    }else if(col.equals("B") && diff > 0){
                    	moves.add(newMove);
                    }
            		
            		
            		cells[r][c].setPlayer(homework.FREE);
            		cells[cell.getRow()][cell.getCol()].setPlayer(homework.SELF);
            		iterator.put(idx,cell);
            	}
            	else if(!player && cells[r][c].getPlayer().equals("free")) {
            		cells[r][c].setPlayer(homework.OPPONENT);
            		iterator.put(idx,cells[r][c]);          		
            		cells[cell.getRow()][cell.getCol()].setPlayer(homework.FREE);
            		Move newMove = new Move();
            		
            		int[] arr = {cell.getRow(),cell.getCol(),r,c};
            		List<int[]> list = new ArrayList<int[]>();
            		list.add(arr);
            		newMove.setList(list);
            		
            		newMove.setValue(current.getValue());
            		newMove.setCells(cells);
            		newMove.setFrom(cells[cell.getRow()][cell.getCol()]);
            		newMove.setTo(cells[r][c]);
            		newMove.setValue(getEvaluation(newMove,maxPlayer,player));
            		newMove.setOpponMap(iterator);
            		newMove.setSelfMap(current.getSelfMap());
            		newMove.setMoveType("E");
                    double diff = newMove.getValue() - current.getValue();
            		
            		if(col.equals("W") && diff < 0) {
            			moves.add(newMove);
                    }else if(col.equals("B") && diff > 0){
                    	moves.add(newMove);
                    }
            		
            		cells[r][c].setPlayer(homework.FREE);
            		cells[cell.getRow()][cell.getCol()].setPlayer(homework.OPPONENT);
            		iterator.put(idx,cell);
            	}
              }
            }
		}
		return moves;
	}
	//
    public ArrayList<Move> specialJumpToQueue(Move current,Map<Integer,Cell> iter,boolean maxPlayer,boolean player,String col){
		
		ArrayList<Move> moves = new ArrayList<Move>();
		Cell[][] cells = current.getCells();		
		Map<Integer,Cell> iterator;
		if(player) {
		   iterator = current.getSelfMap();  
		}else {
			iterator = current.getOpponMap();
		}
		
		for(Integer idx: iter.keySet()) {
			Cell cell = iterator.get(idx);
			
            for(int[] dir:dirs) {
            	int r = cell.getRow() + dir[0];
                int c = cell.getCol() + dir[1];
                Set<Integer> set = new HashSet<Integer>();
              if(r >= 0 && r<cells.length && c >= 0 && c < cells[0].length) {
            	if(player && !cells[r][c].getPlayer().equals("free")) {
            		if(r+dir[0] >= 0 && r+dir[0]<cells.length && c+dir[1] >= 0 && c+dir[1] < cells[0].length && cells[r+dir[0]][c+dir[1]].getPlayer().equals("free")) {
            		    cells[r+dir[0]][c+dir[1]].setPlayer(homework.SELF);
            		    iterator.put(idx,cells[r+dir[0]][c+dir[1]]);
            		    set.add(cell.getRow()* homework.SIZE + cell.getCol());
            		    cells[cell.getRow()][cell.getCol()].setPlayer(homework.FREE);
                		Move newMove = new Move();
                		
                		int[] arr = {cell.getRow(),cell.getCol(),r+dir[0],c+dir[1]};
                		List<int[]> list = new ArrayList<>();
                		list.addAll(current.getList());
                		list.add(arr);
                		newMove.setList(list);                		
                		
                		newMove.setCells(cells);
                		newMove.setValue(current.getValue());
                		newMove.setFrom(cells[cell.getRow()][cell.getCol()]);
                		newMove.setTo(cells[r+dir[0]][c+dir[1]]);
                		newMove.setValue(getEvaluation(newMove,maxPlayer,player));
                	    newMove.setSelfMap(iterator);
                		newMove.setOpponMap(current.getOpponMap());
                		
                		keepSelfJumpingToQueue(moves,newMove,set,idx,maxPlayer,player,col);
                		newMove.setMoveType("J");

                		double diff = newMove.getValue() - current.getValue();
                		
                		if(col.equals("W") && diff < 0) {
                			moves.add(newMove);
                        }else if(col.equals("B") && diff > 0){
                        	moves.add(newMove);
                        }
                		
                		
                		
                		cells[r+dir[0]][c+dir[1]].setPlayer(homework.FREE);
                		cells[cell.getRow()][cell.getCol()].setPlayer(homework.SELF);
                		iterator.put(idx,cell);
            	    }
            	}
            	else if(!player && !cells[r][c].getPlayer().equals("free")) {
            		if(r+dir[0] >= 0 && r+dir[0]<cells.length && c+dir[1] >= 0 && c+dir[1] < cells[0].length && cells[r+dir[0]][c+dir[1]].getPlayer().equals("free")) {
            			cells[r+dir[0]][c+dir[1]].setPlayer(homework.OPPONENT);
            			iterator.put(idx,cells[r+dir[0]][c+dir[1]]);
            			set.add(cell.getRow()* homework.SIZE + cell.getCol());
                		cells[cell.getRow()][cell.getCol()].setPlayer(homework.FREE);
                		Move newMove = new Move();
                		
                		int[] arr = {cell.getRow(),cell.getCol(),r+dir[0],c+dir[1]};
                		List<int[]> list = new ArrayList<>();
                		list.addAll(current.getList());
                		list.add(arr);
                		newMove.setList(list);
		
                		newMove.setValue(current.getValue());
                		newMove.setCells(cells);
                		newMove.setFrom(cells[cell.getRow()][cell.getCol()]);
                		newMove.setTo(cells[r+dir[0]][c+dir[1]]);
                		newMove.setValue(getEvaluation(newMove,maxPlayer,player));
                		newMove.setOpponMap(iterator);
                		newMove.setSelfMap(current.getSelfMap());
                        keepOpponJumpingToQueue(moves,newMove,set,idx,maxPlayer,player,col);
                		newMove.setMoveType("J");
                		double diff = newMove.getValue() - current.getValue();
                		
                		if(col.equals("W") && diff < 0) {
                			moves.add(newMove);
                        }else if(col.equals("B") && diff > 0){
                        	moves.add(newMove);
                        }
                		cells[r+dir[0]][c+dir[1]].setPlayer(homework.FREE);
                		cells[cell.getRow()][cell.getCol()].setPlayer(homework.OPPONENT);
                		iterator.put(idx,cell);
            	    }
            	}            	
              }
            }
		}
		
		return moves;
	}
	//

    private ArrayList<Move> specialOneMoveToQueueFirst(Move current,Map<Integer,Cell> iter, boolean maxPlayer,boolean player,String col) {
		
		ArrayList<Move> moves = new ArrayList<Move>();
		Cell[][] cells = current.getCells();
		boolean checker = true;
		
		
		Map<Integer,Cell> iterator;
		if(player) {
		   iterator = current.getSelfMap();  
		}else {
			iterator = current.getOpponMap();
		}

		for(Integer idx: iter.keySet()) {
			Cell cell = iterator.get(idx);
			
            for(int[] dir:dirs) {
            	int r = cell.getRow() + dir[0];
                int c = cell.getCol() + dir[1];
                
              if(r >= 0 && r<cells.length && c >= 0 && c < cells[0].length) {
            	if(player && cells[r][c].getPlayer().equals("free")) {
            		checker = true;
            		cells[r][c].setPlayer(homework.SELF);
            		iterator.put(idx,cells[r][c]);
            		cells[cell.getRow()][cell.getCol()].setPlayer(homework.FREE);
            		Move newMove = new Move();
            		
            		int[] arr = {cell.getRow(),cell.getCol(),r,c};
            		List<int[]> list = new ArrayList<int[]>();
            		list.add(arr);
            		newMove.setList(list);
            		
            		newMove.setCells(cells);
            		newMove.setValue(current.getValue());
            		newMove.setFrom(cells[cell.getRow()][cell.getCol()]);
            		newMove.setTo(cells[r][c]);
            		newMove.setValue(getEvaluation(newMove,maxPlayer,player));
            		newMove.setSelfMap(iterator);
            		newMove.setOpponMap(current.getOpponMap());
            		newMove.setMoveType("E");
                    double diff = newMove.getValue() - current.getValue();
            		
            		if(col.equals("W") && diff < 0) {
            			for(int[] temp:homework.bottomRightbase) {
                        	if(r == temp[0] && c == temp[1]) {
                        	    checker = false;
                        	}
                        }
            			if(checker) moves.add(newMove);
                    }else if(col.equals("B") && diff > 0){
                        for(int[] temp:homework.topLeftbase) {
                    	    if(r == temp[0] && c == temp[1]) {
                    	        checker = false;
                    	    }
                        }
                    	if(checker) moves.add(newMove);
                    }
            		
            		cells[r][c].setPlayer(homework.FREE);
            		cells[cell.getRow()][cell.getCol()].setPlayer(homework.SELF);
            		iterator.put(idx,cell);
            	}
            	else if(!player && cells[r][c].getPlayer().equals("free")) {
            		checker = true;
            		cells[r][c].setPlayer(homework.OPPONENT);
            		iterator.put(idx,cells[r][c]);          		
            		cells[cell.getRow()][cell.getCol()].setPlayer(homework.FREE);
            		Move newMove = new Move();
            		
            		int[] arr = {cell.getRow(),cell.getCol(),r,c};
            		List<int[]> list = new ArrayList<int[]>();
            		list.add(arr);
            		newMove.setList(list);

            		newMove.setValue(current.getValue());
            		newMove.setCells(cells);
            		newMove.setFrom(cells[cell.getRow()][cell.getCol()]);
            		newMove.setTo(cells[r][c]);
            		newMove.setValue(getEvaluation(newMove,maxPlayer,player));
            		newMove.setOpponMap(iterator);
            		newMove.setSelfMap(current.getSelfMap());
            		newMove.setMoveType("E");
                    double diff = newMove.getValue() - current.getValue();
            		
                    if(col.equals("W") && diff < 0) {
            			for(int[] temp:homework.bottomRightbase) {
                        	if(r == temp[0] && c == temp[1]) {
                        	    checker = false;
                        	}
                        }
            			if(checker) moves.add(newMove);
                    }else if(col.equals("B") && diff > 0){
                        for(int[] temp:homework.topLeftbase) {
                    	    if(r == temp[0] && c == temp[1]) {
                    	        checker = false;
                    	    }
                        }
                    	if(checker) moves.add(newMove);
                    }
            		
            		cells[r][c].setPlayer(homework.FREE);
            		cells[cell.getRow()][cell.getCol()].setPlayer(homework.OPPONENT);
            		iterator.put(idx,cell);
            	}
              }
            }
		}
		return moves;
	}
	
    public ArrayList<Move> specialJumpToQueueFirst(Move current,Map<Integer,Cell> iter,boolean maxPlayer,boolean player,String col){
		
		ArrayList<Move> moves = new ArrayList<Move>();
		Cell[][] cells = current.getCells();		
		Map<Integer,Cell> iterator;
		boolean checker = true;
		if(player) {
		   iterator = current.getSelfMap();  
		}else {
			iterator = current.getOpponMap();
		}
		
		for(Integer idx: iter.keySet()) {
			Cell cell = iterator.get(idx);
			
            for(int[] dir:dirs) {
            	int r = cell.getRow() + dir[0];
                int c = cell.getCol() + dir[1];
                Set<Integer> set = new HashSet<Integer>();
              if(r >= 0 && r<cells.length && c >= 0 && c < cells[0].length) {
            	if(player && !cells[r][c].getPlayer().equals("free")) {
            		if(r+dir[0] >= 0 && r+dir[0]<cells.length && c+dir[1] >= 0 && c+dir[1] < cells[0].length && cells[r+dir[0]][c+dir[1]].getPlayer().equals("free")) {
            		    checker = true;
            			cells[r+dir[0]][c+dir[1]].setPlayer(homework.SELF);
            		    iterator.put(idx,cells[r+dir[0]][c+dir[1]]);
            		    set.add(cell.getRow()* homework.SIZE + cell.getCol());
            		    cells[cell.getRow()][cell.getCol()].setPlayer(homework.FREE);
                		Move newMove = new Move();
                		
                		int[] arr = {cell.getRow(),cell.getCol(),r+dir[0],c+dir[1]};
                		List<int[]> list = new ArrayList<>();
                		list.addAll(current.getList());
                		list.add(arr);
                		newMove.setList(list);
                		
                		newMove.setCells(cells);
                		newMove.setValue(current.getValue());
                		newMove.setFrom(cells[cell.getRow()][cell.getCol()]);
                		newMove.setTo(cells[r+dir[0]][c+dir[1]]);
                		newMove.setValue(getEvaluation(newMove,maxPlayer,player));
                	    newMove.setSelfMap(iterator);
                		newMove.setOpponMap(current.getOpponMap());
                		
                		
                		newMove.setMoveType("J");
            
                		
                		
                        double diff = newMove.getValue() - current.getValue();
                		
                		if(col.equals("W")) {
                			for(int[] temp:homework.bottomRightbase) {
                				if(r+dir[0] == temp[0] && c+dir[1] == temp[1]) {
                            	    checker = false;
                            	}
                            }
                			if(checker && diff < 0) {
                				keepSelfJumpingToQueueFirst(moves,newMove,set,idx,maxPlayer,player,col);
                				moves.add(newMove);
                			}
                        }else if(col.equals("B")){
                            for(int[] temp:homework.topLeftbase) {
                        	    if(r+dir[0] == temp[0] && c+dir[1] == temp[1]) {
                        	        checker = false;
                        	    }
                            }
                        	if(checker && diff > 0) {
                        		keepSelfJumpingToQueueFirst(moves,newMove,set,idx,maxPlayer,player,col);
                        		moves.add(newMove);
                        	}
                        }
                		
//                		moves.add(newMove);
                		cells[r+dir[0]][c+dir[1]].setPlayer(homework.FREE);
                		cells[cell.getRow()][cell.getCol()].setPlayer(homework.SELF);
                		iterator.put(idx,cell);
            	    }
            	}
            	else if(!player && !cells[r][c].getPlayer().equals("free")) {
            		if(r+dir[0] >= 0 && r+dir[0]<cells.length && c+dir[1] >= 0 && c+dir[1] < cells[0].length && cells[r+dir[0]][c+dir[1]].getPlayer().equals("free")) {
            			checker = true;
            			cells[r+dir[0]][c+dir[1]].setPlayer(homework.OPPONENT);
            			iterator.put(idx,cells[r+dir[0]][c+dir[1]]);
            			set.add(cell.getRow()* homework.SIZE + cell.getCol());
                		cells[cell.getRow()][cell.getCol()].setPlayer(homework.FREE);
                		Move newMove = new Move();
                		
                		int[] arr = {cell.getRow(),cell.getCol(),r+dir[0],c+dir[1]};
                		List<int[]> list = new ArrayList<>();
                		list.addAll(current.getList());
                		list.add(arr);
                		newMove.setList(list);
                		
                		newMove.setValue(current.getValue());
                		newMove.setCells(cells);
                		newMove.setFrom(cells[cell.getRow()][cell.getCol()]);
                		newMove.setTo(cells[r+dir[0]][c+dir[1]]);
                		newMove.setValue(getEvaluation(newMove,maxPlayer,player));
                		newMove.setOpponMap(iterator);
                		newMove.setSelfMap(current.getSelfMap());
                        
                		newMove.setMoveType("J");
//                		moves.add(newMove);
                		double diff = newMove.getValue() - current.getValue();
                		
                		if(col.equals("W")) {
                			for(int[] temp:homework.bottomRightbase) {
                            	if(r+dir[0] == temp[0] && c+dir[1] == temp[1]) {
                            	    checker = false;
                            	}
                            }
                			if(checker && diff < 0) {
                				keepOpponJumpingToQueueFirst(moves,newMove,set,idx,maxPlayer,player,col);
                				moves.add(newMove);
                			}
                        }else if(col.equals("B")){
                            for(int[] temp:homework.topLeftbase) {
                            	if(r+dir[0] == temp[0] && c+dir[1] == temp[1]) {
                        	        checker = false;
                        	    }
                            }
                        	if(checker && diff > 0) {
                        		keepOpponJumpingToQueueFirst(moves,newMove,set,idx,maxPlayer,player,col);
                        		moves.add(newMove);
                        	}
                        }
                		cells[r+dir[0]][c+dir[1]].setPlayer(homework.FREE);
                		cells[cell.getRow()][cell.getCol()].setPlayer(homework.OPPONENT);
                		iterator.put(idx,cell);
            	    }
            	}
            	
              }
            }
		}
		
		return moves;
	}
	
    public ArrayList<Move> endOneMoveToQueue(Move current,Map<Integer,Cell> iter,Set<Cell> targets,boolean maxPlayer,boolean player,String col){
    	
		ArrayList<Move> moves = new ArrayList<Move>();
		Cell[][] cells = current.getCells();		
		Map<Integer,Cell> iterator;
		if(player) {
		   iterator = current.getSelfMap();  
		}else {
			iterator = current.getOpponMap();
		}
		
		
		
		
		Cell target = null;
		int theIndex = 0;
		for(Cell tar:targets) {
		    for(Integer idx: iter.keySet()) {
			
			theIndex = idx;
			target = tar;

			Cell theCell = iterator.get(theIndex);
			int distanceEve = Integer.MAX_VALUE;
			int distance = Integer.MAX_VALUE;
			int toGoRow = -17;
			int toGoCol = -17; 
            for(int[] dir:dirs) {
            	int r = theCell.getRow() + dir[0];
                int c = theCell.getCol() + dir[1];
              if(r >= 0 && r<cells.length && c >= 0 && c < cells[0].length) {
            	
            		if(cells[r][c].getPlayer().equals("free")) {
            		  if(Math.abs(target.getRow() - r)+ Math.abs(target.getCol() - c) < distance) {
    					            			  
            			  toGoRow = r;
    					toGoCol = c;

    					distance = Math.abs(target.getRow() - r)+ Math.abs(target.getCol() - c);
    					
    				  }
            		}
            		if(distance != distanceEve) {
                		if(player) {
                		    cells[toGoRow][toGoCol].setPlayer(homework.SELF);
                		    iterator.put(theIndex,cells[toGoRow][toGoCol]);
                		    cells[theCell.getRow()][theCell.getCol()].setPlayer(homework.FREE);
                    		Move newMove = new Move();
                    		
                    		int[] arr = {theCell.getRow(),theCell.getCol(),toGoRow,toGoCol};
                    		List<int[]> list = new ArrayList<int[]>();
                    		list.add(arr);
                    		newMove.setList(list);

                    		
                    		
                    		newMove.setCells(cells);
                    		newMove.setValue(current.getValue());
                    		newMove.setFrom(cells[theCell.getRow()][theCell.getCol()]);
                    		newMove.setTo(cells[toGoRow][toGoCol]);
                    		newMove.setValue(getEvaluation(newMove,maxPlayer,player));
                    	    newMove.setSelfMap(iterator);
                    		newMove.setOpponMap(current.getOpponMap());

                    		newMove.setMoveType("E");
                    		
                    		
                    		double diff = newMove.getValue() - current.getValue();
                    		
                    		if(col.equals("W") && diff < 0) {
                    			moves.add(newMove);
                            }else if(col.equals("B") && diff > 0){
                            	moves.add(newMove);
                            }
                    		
                    		cells[toGoRow][toGoCol].setPlayer(homework.FREE);
                    		cells[theCell.getRow()][theCell.getCol()].setPlayer(homework.SELF);
                    		iterator.put(theIndex,theCell);
                	    
                	     }
                	else if(!player) {

                			cells[toGoRow][toGoCol].setPlayer(homework.OPPONENT);
                		    iterator.put(theIndex,cells[toGoRow][toGoCol]);
                		    cells[theCell.getRow()][theCell.getCol()].setPlayer(homework.FREE);
                    		Move newMove = new Move();
                    		
                    		int[] arr = {theCell.getRow(),theCell.getCol(),toGoRow,toGoCol};
                    		List<int[]> list = new ArrayList<int[]>();
                    		list.add(arr);
                    		newMove.setList(list);
                    		
                    		newMove.setCells(cells);
                    		newMove.setValue(current.getValue());
                    		newMove.setFrom(cells[theCell.getRow()][theCell.getCol()]);
                    		newMove.setTo(cells[toGoRow][toGoCol]);
                    		newMove.setValue(getEvaluation(newMove,maxPlayer,player));
                    	    newMove.setSelfMap(iterator);
                    		newMove.setOpponMap(current.getOpponMap());

                    		newMove.setMoveType("E");
                    		
                    		double diff = newMove.getValue() - current.getValue();
                    		
                    		if(col.equals("W") && diff < 0) {
                    			moves.add(newMove);
                            }else if(col.equals("B") && diff > 0){
                            	moves.add(newMove);
                            }
                    		
                    		cells[toGoRow][toGoCol].setPlayer(homework.FREE);
                    		cells[theCell.getRow()][theCell.getCol()].setPlayer(homework.OPPONENT);
                    		iterator.put(theIndex,theCell);
                	   
                	    
                	}
            		}
            		
            		
            		
            		
            		distanceEve = distance;
              }
            }
             

           
            
            
            
            Cell theCell1 = iterator.get(theIndex);
			int distanceEve1 = Integer.MAX_VALUE;
			int distance1 = Integer.MAX_VALUE;
			int tGoRow = -17;
			int tGoCol = -17; 
            for(int[] dir:dirs) {
            	int r = theCell1.getRow() + dir[0];
                int c = theCell1.getCol() + dir[1];
              if(r >= 0 && r<cells.length && c >= 0 && c < cells[0].length) {
            	
            	if(!cells[r][c].getPlayer().equals("free")) {
            		if(r+dir[0] >= 0 && r+dir[0]<cells.length && c+dir[1] >= 0 && c+dir[1] < cells[0].length && cells[r+dir[0]][c+dir[1]].getPlayer().equals("free")) {
            		  if(Math.abs(target.getRow() - r - dir[0])+ Math.abs(target.getCol() - c - dir[1]) < distance1) {
    					
            			  
            			  tGoRow = r + dir[0];
    					tGoCol = c + dir[1];
	
    					distance1 = Math.abs(target.getRow() - r - dir[0])+ Math.abs(target.getCol() - c - dir[1]);
    					
    				  }
            		}
            	}
            		if(distance1 != distanceEve1) {   
                          		if(player) {
                        		    cells[tGoRow][tGoCol].setPlayer(homework.SELF);
                        		    iterator.put(theIndex,cells[tGoRow][tGoCol]);
                        		    cells[theCell1.getRow()][theCell1.getCol()].setPlayer(homework.FREE);
                            		Move newMove = new Move();
                            		
                            		int[] arr = {theCell1.getRow(),theCell1.getCol(),tGoRow,tGoCol};
                            		List<int[]> list = new ArrayList<>();
                            		list.addAll(current.getList());
                            		list.add(arr);
                            		newMove.setList(list);

                            		
                            		newMove.setCells(cells);
                            		newMove.setValue(current.getValue());
                            		newMove.setFrom(cells[theCell1.getRow()][theCell1.getCol()]);
                            		newMove.setTo(cells[tGoRow][tGoCol]);
                            		newMove.setValue(getEvaluation(newMove,maxPlayer,player));
                            	    newMove.setSelfMap(iterator);
                            		newMove.setOpponMap(current.getOpponMap());

                            		
                            		
                            		endKeepJumpingToQueue(moves,current,theIndex,target,maxPlayer,player,col,distance1);
                            		
                            		newMove.setMoveType("J");
                            		
                            		
                            		double diff = newMove.getValue() - current.getValue();
                            		
                            		if(col.equals("W") && diff < 0) {
                            			moves.add(newMove);
                                    }else if(col.equals("B") && diff > 0){
                                    	moves.add(newMove);
                                    }
                            		
                            		cells[tGoRow][tGoCol].setPlayer(homework.FREE);
                            		cells[theCell1.getRow()][theCell1.getCol()].setPlayer(homework.SELF);
                            		iterator.put(theIndex,theCell1);
                        	    
                        	     }
                        	else if(!player) {	
                        			cells[tGoRow][tGoCol].setPlayer(homework.OPPONENT);
                        		    iterator.put(theIndex,cells[tGoRow][tGoCol]);
                        		    cells[theCell1.getRow()][theCell1.getCol()].setPlayer(homework.FREE);
                            		Move newMove = new Move();
                            		
                            		int[] arr = {theCell1.getRow(),theCell1.getCol(),tGoRow,tGoCol};
                            		List<int[]> list = new ArrayList<>();
                            		list.addAll(current.getList());
                            		list.add(arr);
                            		newMove.setList(list);
                            		
                            		
                            		newMove.setCells(cells);
                            		newMove.setValue(current.getValue());
                            		newMove.setFrom(cells[theCell1.getRow()][theCell1.getCol()]);
                            		newMove.setTo(cells[tGoRow][tGoCol]);
                            		newMove.setValue(getEvaluation(newMove,maxPlayer,player));
                            	    newMove.setSelfMap(iterator);
                            		newMove.setOpponMap(current.getOpponMap());

                            		endKeepJumpingToQueue(moves,current,theIndex,target,maxPlayer,player,col,distance1);
                            		
                            		newMove.setMoveType("J");
                            		
                            		double diff = newMove.getValue() - current.getValue();
                            		
                            		if(col.equals("W") && diff < 0) {
                            			moves.add(newMove);
                                    }else if(col.equals("B") && diff > 0){
                                    	moves.add(newMove);
                                    }
                            		
                            		cells[tGoRow][tGoCol].setPlayer(homework.FREE);
                            		cells[theCell1.getRow()][theCell1.getCol()].setPlayer(homework.OPPONENT);
                            		iterator.put(theIndex,theCell1);
                        	   
                        	    
                        	} 
          
            				  }
            		distanceEve1 = distance1;
                    		}
                      }
		    }
		}
 		
		return moves;		
	}
            
    public ArrayList<Move> EXendOneMoveToQueue(Move current,Map<Integer,Cell> iter,Set<Integer> remain,Set<Cell> targets,boolean maxPlayer,boolean player,String col){
	
	ArrayList<Move> moves = new ArrayList<Move>();
	Cell[][] cells = current.getCells();		
	Map<Integer,Cell> iterator;
	if(player) {
	   iterator = current.getSelfMap();  
	}else {
		iterator = current.getOpponMap();
	}
	
	
	System.out.println( " HELP   ");
	
	Cell target = null;
	int theIndex = 0;
	
	Iterator<Cell> it = targets.iterator();
       target = it.next();
	
	
	
//	for(Cell tar:targets) {
//	    for(Integer idx: iter.keySet()) {
       for(Integer idx: remain) {
    	   
		theIndex = idx;
//		target = tar;

		Cell theCell = iterator.get(theIndex);
		int distanceEve = Integer.MAX_VALUE;
		int distance = Integer.MAX_VALUE;
		int toGoRow = -17;
		int toGoCol = -17; 
        for(int[] dir:dirs) {
        	int r = theCell.getRow() + dir[0];
            int c = theCell.getCol() + dir[1];
          if(r >= 0 && r<cells.length && c >= 0 && c < cells[0].length) {
        	
        		if(cells[r][c].getPlayer().equals("free")) {
        		  if(Math.abs(target.getRow() - r)+ Math.abs(target.getCol() - c) < distance) {
					            			  
        			  toGoRow = r;
					toGoCol = c;

					distance = Math.abs(target.getRow() - r)+ Math.abs(target.getCol() - c);
					
				  }
        		}
        		if(distance != distanceEve) {
            		if(player) {
            		    cells[toGoRow][toGoCol].setPlayer(homework.SELF);
            		    iterator.put(theIndex,cells[toGoRow][toGoCol]);
            		    cells[theCell.getRow()][theCell.getCol()].setPlayer(homework.FREE);
                		Move newMove = new Move();
                		
                		int[] arr = {theCell.getRow(),theCell.getCol(),toGoRow,toGoCol};
                		List<int[]> list = new ArrayList<int[]>();
                		list.add(arr);
                		newMove.setList(list);
                		
                		newMove.setCells(cells);
                		newMove.setValue(current.getValue());
                		newMove.setFrom(cells[theCell.getRow()][theCell.getCol()]);
                		newMove.setTo(cells[toGoRow][toGoCol]);
                		newMove.setValue(getEvaluation(newMove,maxPlayer,player));
                	    newMove.setSelfMap(iterator);
                		newMove.setOpponMap(current.getOpponMap());

                		newMove.setMoveType("E");
                		
                		
                		double diff = newMove.getValue() - current.getValue();
                		
//                		if(col.equals("W") && diff <= 0) {
//                			moves.add(newMove);
//                        }else if(col.equals("B") && diff >= 0){
                        	moves.add(newMove);
//                        }
                		
                		cells[toGoRow][toGoCol].setPlayer(homework.FREE);
                		cells[theCell.getRow()][theCell.getCol()].setPlayer(homework.SELF);
                		iterator.put(theIndex,theCell);
            	    
            	     }
            	else if(!player) {

            			cells[toGoRow][toGoCol].setPlayer(homework.OPPONENT);
            		    iterator.put(theIndex,cells[toGoRow][toGoCol]);
            		    cells[theCell.getRow()][theCell.getCol()].setPlayer(homework.FREE);
                		Move newMove = new Move();
                		
                		int[] arr = {theCell.getRow(),theCell.getCol(),toGoRow,toGoCol};
                		List<int[]> list = new ArrayList<int[]>();
                		list.add(arr);
                		newMove.setList(list);
                		
                		newMove.setCells(cells);
                		newMove.setValue(current.getValue());
                		newMove.setFrom(cells[theCell.getRow()][theCell.getCol()]);
                		newMove.setTo(cells[toGoRow][toGoCol]);
                		newMove.setValue(getEvaluation(newMove,maxPlayer,player));
                	    newMove.setSelfMap(iterator);
                		newMove.setOpponMap(current.getOpponMap());

                		newMove.setMoveType("E");
                		
                		double diff = newMove.getValue() - current.getValue();
                		
//                		if(col.equals("W") && diff <= 0) {
//                			moves.add(newMove);
//                        }else if(col.equals("B") && diff >= 0){
                        	moves.add(newMove);
//                        }
                		
                		cells[toGoRow][toGoCol].setPlayer(homework.FREE);
                		cells[theCell.getRow()][theCell.getCol()].setPlayer(homework.OPPONENT);
                		iterator.put(theIndex,theCell);
            	   
            	    
            	}
        		}
        		
        		
        		
        		
        		distanceEve = distance;
          }
        }
         

       
        
        
        
        Cell theCell1 = iterator.get(theIndex);
		int distanceEve1 = Integer.MAX_VALUE;
		int distance1 = Integer.MAX_VALUE;
		int tGoRow = -17;
		int tGoCol = -17; 
        for(int[] dir:dirs) {
        	int r = theCell1.getRow() + dir[0];
            int c = theCell1.getCol() + dir[1];
          if(r >= 0 && r<cells.length && c >= 0 && c < cells[0].length) {
        	
        	if(!cells[r][c].getPlayer().equals("free")) {
        		if(r+dir[0] >= 0 && r+dir[0]<cells.length && c+dir[1] >= 0 && c+dir[1] < cells[0].length && cells[r+dir[0]][c+dir[1]].getPlayer().equals("free")) {
        		  if(Math.abs(target.getRow() - r - dir[0])+ Math.abs(target.getCol() - c - dir[1]) < distance1) {
					
        			  
        			  tGoRow = r + dir[0];
					tGoCol = c + dir[1];

					distance1 = Math.abs(target.getRow() - r - dir[0])+ Math.abs(target.getCol() - c - dir[1]);
					
				  }
        		}
        	}
        		if(distance1 != distanceEve1) {   
                      		if(player) {
                    		    cells[tGoRow][tGoCol].setPlayer(homework.SELF);
                    		    iterator.put(theIndex,cells[tGoRow][tGoCol]);
                    		    cells[theCell1.getRow()][theCell1.getCol()].setPlayer(homework.FREE);
                        		Move newMove = new Move();
                        		
                        		int[] arr = {theCell1.getRow(),theCell1.getCol(),tGoRow,tGoCol};
                        		List<int[]> list = new ArrayList<>();
                        		list.addAll(current.getList());
                        		list.add(arr);
                        		newMove.setList(list);
                        		
                        		newMove.setCells(cells);
                        		newMove.setValue(current.getValue());
                        		newMove.setFrom(cells[theCell1.getRow()][theCell1.getCol()]);
                        		newMove.setTo(cells[tGoRow][tGoCol]);
                        		newMove.setValue(getEvaluation(newMove,maxPlayer,player));
                        	    newMove.setSelfMap(iterator);
                        		newMove.setOpponMap(current.getOpponMap());

                        		
                        		
                        		endKeepJumpingToQueue(moves,current,theIndex,target,maxPlayer,player,col,distance1);
                        		
                        		newMove.setMoveType("J");
                        		
                        		
                        		double diff = newMove.getValue() - current.getValue();
                        		
//                        		if(col.equals("W") && diff < 0) {
//                        			moves.add(newMove);
//                                }else if(col.equals("B") && diff > 0){
                                	moves.add(newMove);
//                                }
                        		
                        		cells[tGoRow][tGoCol].setPlayer(homework.FREE);
                        		cells[theCell1.getRow()][theCell1.getCol()].setPlayer(homework.SELF);
                        		iterator.put(theIndex,theCell1);
                    	    
                    	     }
                    	else if(!player) {	
                    			cells[tGoRow][tGoCol].setPlayer(homework.OPPONENT);
                    		    iterator.put(theIndex,cells[tGoRow][tGoCol]);
                    		    cells[theCell1.getRow()][theCell1.getCol()].setPlayer(homework.FREE);
                        		Move newMove = new Move();
                        		
                        		int[] arr = {theCell1.getRow(),theCell1.getCol(),tGoRow,tGoCol};
                        		List<int[]> list = new ArrayList<>();
                        		list.addAll(current.getList());
                        		list.add(arr);
                        		newMove.setList(list);
                        		
                        		newMove.setCells(cells);
                        		newMove.setValue(current.getValue());
                        		newMove.setFrom(cells[theCell1.getRow()][theCell1.getCol()]);
                        		newMove.setTo(cells[tGoRow][tGoCol]);
                        		newMove.setValue(getEvaluation(newMove,maxPlayer,player));
                        	    newMove.setSelfMap(iterator);
                        		newMove.setOpponMap(current.getOpponMap());

                        		endKeepJumpingToQueue(moves,current,theIndex,target,maxPlayer,player,col,distance1);
                        		
                        		newMove.setMoveType("J");
                        		
                        		double diff = newMove.getValue() - current.getValue();
                        		
//                        		if(col.equals("W") && diff <= 0) {
//                        			moves.add(newMove);
//                                }else if(col.equals("B") && diff >= 0){
                                	moves.add(newMove);
//                                }
                        		
                        		cells[tGoRow][tGoCol].setPlayer(homework.FREE);
                        		cells[theCell1.getRow()][theCell1.getCol()].setPlayer(homework.OPPONENT);
                        		iterator.put(theIndex,theCell1);
                    	   
                    	    
                    	} 
      
        				  }
        		distanceEve1 = distance1;
                		}
                  }
	    }
//	}
		
	return moves;		
}

	public void endKeepJumpingToQueue(ArrayList<Move> moves,Move current,int idx,Cell target,boolean maxPlayer,boolean player,String col,int distance){
	
		Cell[][] cells = current.getCells();		
		Map<Integer,Cell> iterator;
		if(player) {
		   iterator = current.getSelfMap();  
		}else {
			iterator = current.getOpponMap();
		}

		int theIndex = idx;		
		Cell theCell1 = iterator.get(theIndex);
		int distanceEve1 = distance;
		int distance1 = distance;
		int tGoRow = -17;
		int tGoCol = -17; 
        for(int[] dir:dirs) {
        	int r = theCell1.getRow() + dir[0];
            int c = theCell1.getCol() + dir[1];
          if(r >= 0 && r<cells.length && c >= 0 && c < cells[0].length) {
        	
        	if(!cells[r][c].getPlayer().equals("free")) {
        		if(r+dir[0] >= 0 && r+dir[0]<cells.length && c+dir[1] >= 0 && c+dir[1] < cells[0].length && cells[r+dir[0]][c+dir[1]].getPlayer().equals("free")) {
                   if(Math.abs(target.getRow() - r - dir[0])+ Math.abs(target.getCol() - c - dir[1]) < distance1) {
					
        			  
        			  tGoRow = r + dir[0];
					tGoCol = c + dir[1];
					
					distance1 = Math.abs(target.getRow() - r - dir[0])+ Math.abs(target.getCol() - c - dir[1]);
					
				  }
        		}
        	}
        		if(distance1 != distanceEve1) {   
                      		if(player) {
                    		    cells[tGoRow][tGoCol].setPlayer(homework.SELF);
                    		    iterator.put(theIndex,cells[tGoRow][tGoCol]);
                    		    cells[theCell1.getRow()][theCell1.getCol()].setPlayer(homework.FREE);
                        		Move newMove = new Move();
                        		
                        		int[] arr = {theCell1.getRow(),theCell1.getCol(),tGoRow,tGoCol};
                        		List<int[]> list = new ArrayList<>();
                        		list.addAll(current.getList());
                        		list.add(arr);
                        		newMove.setList(list);
                        		
                        		newMove.setCells(cells);
                        		newMove.setValue(current.getValue());
                        		newMove.setFrom(cells[theCell1.getRow()][theCell1.getCol()]);
                        		newMove.setTo(cells[tGoRow][tGoCol]);
                        		newMove.setValue(getEvaluation(newMove,maxPlayer,player));
                        	    newMove.setSelfMap(iterator);
                        		newMove.setOpponMap(current.getOpponMap());

                        		
                        		
                        		endKeepJumpingToQueue(moves,newMove,theIndex,target,maxPlayer,player,col,distance1);
                        		
                        		newMove.setMoveType("J");


                        		double start = 0.0;
                        		double end = 0.0;
                        		if(col.equals("W")) {
                        			start = centerCoordWhite(theCell1.getRow(),theCell1.getCol());
                                    end = centerCoordWhite(tGoRow,tGoCol);
                                }else if(col.equals("B")){
                                	start = centerCoordBlack(theCell1.getRow(),theCell1.getCol());
                                    end = centerCoordBlack(tGoRow,tGoCol);
                                }
                        		
                        		cells[tGoRow][tGoCol].setPlayer(homework.FREE);
                        		cells[theCell1.getRow()][theCell1.getCol()].setPlayer(homework.SELF);
                        		iterator.put(theIndex,theCell1);
                    	    
                    	     }
                    	else if(!player) {	
                    			cells[tGoRow][tGoCol].setPlayer(homework.OPPONENT);
                    		    iterator.put(theIndex,cells[tGoRow][tGoCol]);
                    		    cells[theCell1.getRow()][theCell1.getCol()].setPlayer(homework.FREE);
                        		Move newMove = new Move();
                        		
                        		int[] arr = {theCell1.getRow(),theCell1.getCol(),tGoRow,tGoCol};
                        		List<int[]> list = new ArrayList<>();
                        		list.addAll(current.getList());
                        		list.add(arr);
                        		newMove.setList(list);
                        		
                        		newMove.setCells(cells);
                        		newMove.setValue(current.getValue());
                        		newMove.setFrom(cells[theCell1.getRow()][theCell1.getCol()]);
                        		newMove.setTo(cells[tGoRow][tGoCol]);
                        		newMove.setValue(getEvaluation(newMove,maxPlayer,player));
                        	    newMove.setSelfMap(iterator);
                        		newMove.setOpponMap(current.getOpponMap());

                        		endKeepJumpingToQueue(moves,newMove,theIndex,target,maxPlayer,player,col,distance1);
                        		
                        		newMove.setMoveType("J");
                        		
                        		
                        		double start = 0.0;
                        		double end = 0.0;
                        		if(col.equals("W")) {
                        			start = centerCoordWhite(theCell1.getRow(),theCell1.getCol());
                                    end = centerCoordWhite(tGoRow,tGoCol);
                                }else if(col.equals("B")){
                                	start = centerCoordBlack(theCell1.getRow(),theCell1.getCol());
                                    end = centerCoordBlack(tGoRow,tGoCol);
                                }
                        		
                        		double diff = end - start;
                        		
                        		if(col.equals("W") && diff < 0) {
                        			moves.add(newMove);
                                }else if(col.equals("B") && diff > 0){
                                	moves.add(newMove);
                                }
                        		
                        		
                        		cells[tGoRow][tGoCol].setPlayer(homework.FREE);
                        		cells[theCell1.getRow()][theCell1.getCol()].setPlayer(homework.OPPONENT);
                        		iterator.put(theIndex,theCell1);
                    	   
                    	    
                    	} 
      
        				  }
        		distanceEve1 = distance1;
                		}
		
        }
		
		
		
		
	}
	
	public double centerCoordBlack(double x, double y) {
	     return (y - 7.5) - (7.5 - x);
  }
	
	public double centerCoordWhite(double x, double y) {
	     return (y - 7.5) + (x - 7.5);
 }
	
    private ArrayList<Move> oneMoveToQueue(Move current, boolean maxPlayer,boolean player,String col) {
		
		ArrayList<Move> moves = new ArrayList<Move>();
		Cell[][] cells = current.getCells();

		Map<Integer,Cell> iterator;
		if(player) {
		   iterator = current.getSelfMap();  
		}else {
			iterator = current.getOpponMap();
		}

		for(Integer idx: iterator.keySet()) {
			Cell cell = iterator.get(idx);
			
            for(int[] dir:dirs) {
            	int r = cell.getRow() + dir[0];
                int c = cell.getCol() + dir[1];
                
              if(r >= 0 && r<cells.length && c >= 0 && c < cells[0].length) {
            	if(player && cells[r][c].getPlayer().equals("free")) {
            		cells[r][c].setPlayer(homework.SELF);
            		iterator.put(idx,cells[r][c]);
            		cells[cell.getRow()][cell.getCol()].setPlayer(homework.FREE);
            		Move newMove = new Move();
            		
            		int[] arr = {cell.getRow(),cell.getCol(),r,c};
            		List<int[]> list = new ArrayList<int[]>();
            		list.add(arr);
            		newMove.setList(list);

            		
            		newMove.setCells(cells);
            		newMove.setValue(current.getValue());
            		newMove.setFrom(cells[cell.getRow()][cell.getCol()]);
            		newMove.setTo(cells[r][c]);
            		newMove.setValue(getEvaluation(newMove,maxPlayer,player));
            		newMove.setSelfMap(iterator);
            		newMove.setOpponMap(current.getOpponMap());
            		newMove.setMoveType("E");
            		
            		double diff = newMove.getValue() - current.getValue();
            		
            		if(col.equals("W") && diff < 0) {
            			moves.add(newMove);
                    }else if(col.equals("B") && diff > 0){
                    	moves.add(newMove);
                    }

            		cells[r][c].setPlayer(homework.FREE);
            		cells[cell.getRow()][cell.getCol()].setPlayer(homework.SELF);
            		iterator.put(idx,cell);
            	}
            	else if(!player && cells[r][c].getPlayer().equals("free")) {
            		cells[r][c].setPlayer(homework.OPPONENT);
            		iterator.put(idx,cells[r][c]);
            		cells[cell.getRow()][cell.getCol()].setPlayer(homework.FREE);
            		Move newMove = new Move();
            		
            		int[] arr = {cell.getRow(),cell.getCol(),r,c};
            		List<int[]> list = new ArrayList<int[]>();
            		list.add(arr);
            		newMove.setList(list);

            		
            		newMove.setValue(current.getValue());
            		newMove.setCells(cells);
            		newMove.setFrom(cells[cell.getRow()][cell.getCol()]);
            		newMove.setTo(cells[r][c]);
            		newMove.setValue(getEvaluation(newMove,maxPlayer,player));
            		newMove.setOpponMap(iterator);
            		newMove.setSelfMap(current.getSelfMap());
            		newMove.setMoveType("E");
            		
                    double diff = newMove.getValue() - current.getValue();
            		
            		if(col.equals("W") && diff < 0) {
            			moves.add(newMove);
                    }else if(col.equals("B") && diff > 0){
                    	moves.add(newMove);
                    }
            		
            		cells[r][c].setPlayer(homework.FREE);
            		cells[cell.getRow()][cell.getCol()].setPlayer(homework.OPPONENT);
            		iterator.put(idx,cell);
            	}
              }
            }
		}
		
		return moves;
	}
	
	
    private ArrayList<Move> oneMoveToQueueagain(Move current, boolean maxPlayer,boolean player,String col) {
		
		ArrayList<Move> moves = new ArrayList<Move>();
		Cell[][] cells = current.getCells();

		Map<Integer,Cell> iterator;
		if(player) {
		   iterator = current.getSelfMap();  
		}else {
			iterator = current.getOpponMap();
		}

		for(Integer idx: iterator.keySet()) {
			Cell cell = iterator.get(idx);
			
            for(int[] dir:dirs) {
            	int r = cell.getRow() + dir[0];
                int c = cell.getCol() + dir[1];
                
              if(r >= 0 && r<cells.length && c >= 0 && c < cells[0].length) {
            	if(player && cells[r][c].getPlayer().equals("free")) {
            		cells[r][c].setPlayer(homework.SELF);
            		iterator.put(idx,cells[r][c]);
            		cells[cell.getRow()][cell.getCol()].setPlayer(homework.FREE);
            		Move newMove = new Move();
            		newMove.setCells(cells);
            		
            		int[] arr = {cell.getRow(),cell.getCol(),r,c};
            		List<int[]> list = new ArrayList<int[]>();
            		list.add(arr);
            		newMove.setList(list);

            		
            		newMove.setValue(current.getValue());
            		
            		
            		newMove.setFrom(cells[cell.getRow()][cell.getCol()]);
            		newMove.setTo(cells[r][c]);
            		newMove.setValue(getEvaluation(newMove,maxPlayer,player));
            		newMove.setSelfMap(iterator);
            		newMove.setOpponMap(current.getOpponMap());
            		newMove.setMoveType("E");
            		
            		double diff = newMove.getValue() - current.getValue();
            		
            		if(col.equals("W") && diff <= 0) {
            			moves.add(newMove);
                    }else if(col.equals("B") && diff >= 0){
                    	moves.add(newMove);
                    }

            		cells[r][c].setPlayer(homework.FREE);
            		cells[cell.getRow()][cell.getCol()].setPlayer(homework.SELF);
            		iterator.put(idx,cell);
            	}
            	else if(!player && cells[r][c].getPlayer().equals("free")) {
            		cells[r][c].setPlayer(homework.OPPONENT);
            		iterator.put(idx,cells[r][c]);
            		cells[cell.getRow()][cell.getCol()].setPlayer(homework.FREE);
            		Move newMove = new Move();
            		
            		int[] arr = {cell.getRow(),cell.getCol(),r,c};
            		List<int[]> list = new ArrayList<int[]>();
            		list.add(arr);
            		newMove.setList(list);

            		
            		newMove.setValue(current.getValue());
            		newMove.setCells(cells);
            		newMove.setFrom(cells[cell.getRow()][cell.getCol()]);
            		newMove.setTo(cells[r][c]);
            		newMove.setValue(getEvaluation(newMove,maxPlayer,player));
            		newMove.setOpponMap(iterator);
            		newMove.setSelfMap(current.getSelfMap());
            		newMove.setMoveType("E");
            		
                    double diff = newMove.getValue() - current.getValue();
            		
            		if(col.equals("W") && diff <= 0) {
            			moves.add(newMove);
                    }else if(col.equals("B") && diff >= 0){
                    	moves.add(newMove);
                    }
            		
            		cells[r][c].setPlayer(homework.FREE);
            		cells[cell.getRow()][cell.getCol()].setPlayer(homework.OPPONENT);
            		iterator.put(idx,cell);
            	}
              }
            }
		}
		
		return moves;
	}
	
    public ArrayList<Move> jumpToQueue(Move current,boolean maxPlayer,boolean player,String col){
		
		ArrayList<Move> moves = new ArrayList<Move>();
		Cell[][] cells = current.getCells();		
		Map<Integer,Cell> iterator;
		if(player) {
		   iterator = current.getSelfMap();  
		}else {
			iterator = current.getOpponMap();
		}
		
		for(Integer idx: iterator.keySet()) {
			Cell cell = iterator.get(idx);
			
            for(int[] dir:dirs) {
            	int r = cell.getRow() + dir[0];
                int c = cell.getCol() + dir[1];
                Set<Integer> set = new HashSet<Integer>();
              if(r >= 0 && r<cells.length && c >= 0 && c < cells[0].length) {
            	if(player && !cells[r][c].getPlayer().equals("free")) {
            		if(r+dir[0] >= 0 && r+dir[0]<cells.length && c+dir[1] >= 0 && c+dir[1] < cells[0].length && cells[r+dir[0]][c+dir[1]].getPlayer().equals("free")) {
            		    cells[r+dir[0]][c+dir[1]].setPlayer(homework.SELF);
            		    iterator.put(idx,cells[r+dir[0]][c+dir[1]]);
            		    set.add(cell.getRow()* homework.SIZE + cell.getCol());
            		    
            		    cells[cell.getRow()][cell.getCol()].setPlayer(homework.FREE);
                		Move newMove = new Move();
                		
                		int[] arr = {cell.getRow(),cell.getCol(),r+dir[0],c+dir[1]};
                		List<int[]> list = new ArrayList<>();
                		list.addAll(current.getList());
                		list.add(arr);
                		newMove.setList(list);

                		
                		newMove.setCells(cells);
                		newMove.setValue(current.getValue());
                		newMove.setFrom(cells[cell.getRow()][cell.getCol()]);
                		newMove.setTo(cells[r+dir[0]][c+dir[1]]);
                		newMove.setValue(getEvaluation(newMove,maxPlayer,player));
                	    newMove.setSelfMap(iterator);
                		newMove.setOpponMap(current.getOpponMap());
                		
                		keepSelfJumpingToQueue(moves,newMove,set,idx,maxPlayer,player,col);
                		newMove.setMoveType("J");

                		double diff = newMove.getValue() - current.getValue();
                		
                		if(col.equals("W") && diff < 0) {
                			moves.add(newMove);
                        }else if(col.equals("B") && diff > 0){
                        	moves.add(newMove);
                        }
                		
            		    
                		cells[r+dir[0]][c+dir[1]].setPlayer(homework.FREE);
                		cells[cell.getRow()][cell.getCol()].setPlayer(homework.SELF);
                		iterator.put(idx,cell);
            	    }
            	}
            	else if(!player && !cells[r][c].getPlayer().equals("free")) {
            		if(r+dir[0] >= 0 && r+dir[0]<cells.length && c+dir[1] >= 0 && c+dir[1] < cells[0].length && cells[r+dir[0]][c+dir[1]].getPlayer().equals("free")) {
            			cells[r+dir[0]][c+dir[1]].setPlayer(homework.OPPONENT);
            			iterator.put(idx,cells[r+dir[0]][c+dir[1]]);
            			set.add(cell.getRow()* homework.SIZE + cell.getCol());
                		
                		
                		
                		cells[cell.getRow()][cell.getCol()].setPlayer(homework.FREE);
                		Move newMove = new Move();

                		int[] arr = {cell.getRow(),cell.getCol(),r+dir[0],c+dir[1]};
                		List<int[]> list = new ArrayList<>();
                		list.addAll(current.getList());
                		list.add(arr);
                		newMove.setList(list);

                		
                		newMove.setValue(current.getValue());
                		newMove.setCells(cells);
                		newMove.setFrom(cells[cell.getRow()][cell.getCol()]);
                		newMove.setTo(cells[r+dir[0]][c+dir[1]]);
                		newMove.setValue(getEvaluation(newMove,maxPlayer,player));
                		newMove.setOpponMap(iterator);
                		newMove.setSelfMap(current.getSelfMap());
                		
                		keepOpponJumpingToQueue(moves,newMove,set,idx,maxPlayer,player,col);
                				
                		newMove.setMoveType("J");

                		double diff = newMove.getValue() - current.getValue();
                		
                		if(col.equals("W") && diff < 0) {
                			moves.add(newMove);
                        }else if(col.equals("B") && diff > 0){
                        	moves.add(newMove);
                        }
                		
                		cells[r+dir[0]][c+dir[1]].setPlayer(homework.FREE);
                		cells[cell.getRow()][cell.getCol()].setPlayer(homework.OPPONENT);
                		iterator.put(idx,cell);
            	    }
            	}
            	
              }
            }
		}
		
		return moves;
	}
	
	
    public void keepSelfJumpingToQueue(ArrayList<Move> moves,Move current,Set<Integer> set,int idx,boolean maxPlayer,boolean player,String col){
		Cell[][] cells = current.getCells();
		Map<Integer,Cell> iterator;
		iterator = current.getSelfMap();  
		Cell cell = iterator.get(idx);
			
            for(int[] dir:dirs) {
            	int r = cell.getRow() + dir[0];
                int c = cell.getCol() + dir[1]; 
                
              if(set.contains((r+dir[0]) * homework.SIZE + c+dir[1])) continue;
              
              if(r >= 0 && r<cells.length && c >= 0 && c < cells[0].length) {
            	
            	if(!cells[r][c].getPlayer().equals("free")) {
            		if(r+dir[0] >= 0 && r+dir[0]<cells.length && c+dir[1] >= 0 && c+dir[1] < cells[0].length && cells[r+dir[0]][c+dir[1]].getPlayer().equals("free")) {
            			cells[r+dir[0]][c+dir[1]].setPlayer(homework.SELF);

            			iterator.put(idx,cells[r+dir[0]][c+dir[1]]);
            		    set.add(cell.getRow()* homework.SIZE + cell.getCol());
            		    cells[cell.getRow()][cell.getCol()].setPlayer(homework.FREE);
                		Move newMove = new Move();
                		
                		int[] arr = {cell.getRow(),cell.getCol(),r+dir[0],c+dir[1]};
                		List<int[]> list = new ArrayList<>();
                		list.addAll(current.getList());
                		list.add(arr);
                		newMove.setList(list);
                		
                		
                		newMove.setCells(cells);
                		newMove.setValue(current.getValue());
                		newMove.setFrom(current.getFrom());
                		newMove.setTo(cells[r+dir[0]][c+dir[1]]);
                		newMove.setValue(getEvaluation(newMove,maxPlayer,player));
                	    newMove.setSelfMap(iterator);
                		newMove.setOpponMap(current.getOpponMap());
                		keepSelfJumpingToQueue(moves,newMove,set,idx,maxPlayer,player,col);
                		newMove.setMoveType("J");
                		double diffFromCur = newMove.getValue() - current.getValue();
//                		
//                		if(col.equals("W") && diff < 0) {
//                			moves.add(newMove);
//                        }else if(col.equals("B") && diff > 0){
//                        	moves.add(newMove);
//                        }
                		double start = 0.0;
                		double end = 0.0;
                		if(col.equals("W")) {
                			start = centerCoordWhite(cell.getRow(),cell.getCol());
                            end = centerCoordWhite(r+dir[0],c+dir[1]);
                        }else if(col.equals("B")){
                        	start = centerCoordBlack(cell.getRow(),cell.getCol());
                            end = centerCoordBlack(r+dir[0],c+dir[1]);
                        }
                		
                		double diff = end - start;
                		
                		if(col.equals("W") && diff < 0 && diffFromCur<0) {
                			moves.add(newMove);
                        }else if(col.equals("B") && diff > 0 && diffFromCur>0){
                        	moves.add(newMove);
                        }
                		
                		
                		cells[r+dir[0]][c+dir[1]].setPlayer(homework.FREE);
                		cells[cell.getRow()][cell.getCol()].setPlayer(homework.SELF);
                		iterator.put(idx,cell);
                		set.remove(cell.getRow()* homework.SIZE + cell.getCol());
                		
            	    }
            	}
              }
            }
	}
	
    
    public void keepOpponJumpingToQueue(ArrayList<Move> moves,Move current,Set<Integer> set,int idx,boolean maxPlayer,boolean player,String col){
		Cell[][] cells = current.getCells();
		
		Map<Integer,Cell> iterator;
		iterator = current.getOpponMap();  
		Cell cell = iterator.get(idx);
			
            for(int[] dir:dirs) {
            	int r = cell.getRow() + dir[0];
                int c = cell.getCol() + dir[1]; 
              if(set.contains((r+dir[0]) * homework.SIZE + c+dir[1])) continue;
              if(r >= 0 && r<cells.length && c >= 0 && c < cells[0].length) {
            	
            	if(!cells[r][c].getPlayer().equals("free")) {
            		if(r+dir[0] >= 0 && r+dir[0]<cells.length && c+dir[1] >= 0 && c+dir[1] < cells[0].length && cells[r+dir[0]][c+dir[1]].getPlayer().equals("free")) {
            			cells[r+dir[0]][c+dir[1]].setPlayer(homework.OPPONENT);
            			iterator.put(idx,cells[r+dir[0]][c+dir[1]]);
            			set.add(cell.getRow()* homework.SIZE + cell.getCol());
            		    cells[cell.getRow()][cell.getCol()].setPlayer(homework.FREE);
                		Move newMove = new Move();
                		
                		int[] arr = {cell.getRow(),cell.getCol(),r+dir[0],c+dir[1]};
                		List<int[]> list = new ArrayList<>();
                		list.addAll(current.getList());
                		list.add(arr);
                		newMove.setList(list);
                		
                		newMove.setCells(cells);
                		newMove.setValue(current.getValue());
                		newMove.setFrom(current.getFrom());
                		newMove.setTo(cells[r+dir[0]][c+dir[1]]);
                		newMove.setValue(getEvaluation(newMove,maxPlayer,player));	
                		newMove.setSelfMap(current.getSelfMap());
                		newMove.setOpponMap(iterator);
                		keepOpponJumpingToQueue(moves,newMove,set,idx,maxPlayer,player,col);
                		newMove.setMoveType("J");
                		double diffFromCur = newMove.getValue() - current.getValue();
//                		
//                		if(col.equals("W") && diff < 0) {
//                			moves.add(newMove);
//                        }else if(col.equals("B") && diff > 0){
//                        	moves.add(newMove);
//                        }
                		
                		double start = 0.0;
                		double end = 0.0;
                		if(col.equals("W")) {
                			start = centerCoordWhite(cell.getRow(),cell.getCol());
                            end = centerCoordWhite(r+dir[0],c+dir[1]);
                        }else if(col.equals("B")){
                        	start = centerCoordBlack(cell.getRow(),cell.getCol());
                            end = centerCoordBlack(r+dir[0],c+dir[1]);
                        }
                		
                		double diff = end - start;
                		
                		if(col.equals("W") && diff < 0 && diffFromCur<0) {
                			moves.add(newMove);
                        }else if(col.equals("B") && diff > 0 && diffFromCur>0){
                        	moves.add(newMove);
                        }
                		
                		
                		cells[r+dir[0]][c+dir[1]].setPlayer(homework.FREE);
                		cells[cell.getRow()][cell.getCol()].setPlayer(homework.OPPONENT);
                		iterator.put(idx,cell);
                		set.remove(cell.getRow()* homework.SIZE + cell.getCol());
            	    }
            	}

              }
            }
	}
    
	
    public void keepSelfJumpingToQueueFirst(ArrayList<Move> moves,Move current,Set<Integer> set,int idx,boolean maxPlayer,boolean player,String col){
		Cell[][] cells = current.getCells();
		Map<Integer,Cell> iterator;
		iterator = current.getSelfMap();  
		Cell cell = iterator.get(idx);
			
            for(int[] dir:dirs) {
            	int r = cell.getRow() + dir[0];
                int c = cell.getCol() + dir[1]; 
                
              if(set.contains((r+dir[0]) * homework.SIZE + c+dir[1])) continue;
              
              if(r >= 0 && r<cells.length && c >= 0 && c < cells[0].length) {
            	
            	if(!cells[r][c].getPlayer().equals("free")) {
            		if(r+dir[0] >= 0 && r+dir[0]<cells.length && c+dir[1] >= 0 && c+dir[1] < cells[0].length && cells[r+dir[0]][c+dir[1]].getPlayer().equals("free")) {
            			boolean checker = true;
            			cells[r+dir[0]][c+dir[1]].setPlayer(homework.SELF);

            			iterator.put(idx,cells[r+dir[0]][c+dir[1]]);
            		    set.add(cell.getRow()* homework.SIZE + cell.getCol());
            		    cells[cell.getRow()][cell.getCol()].setPlayer(homework.FREE);
                		Move newMove = new Move();
                		
                		int[] arr = {cell.getRow(),cell.getCol(),r+dir[0],c+dir[1]};
                		List<int[]> list = new ArrayList<>();
                		list.addAll(current.getList());
                		list.add(arr);
                		newMove.setList(list);
                		
                		
                		newMove.setCells(cells);
                		newMove.setValue(current.getValue());
                		newMove.setFrom(current.getFrom());
                		newMove.setTo(cells[r+dir[0]][c+dir[1]]);
                		newMove.setValue(getEvaluation(newMove,maxPlayer,player));
                	    newMove.setSelfMap(iterator);
                		newMove.setOpponMap(current.getOpponMap());
//                		keepSelfJumpingToQueueFirst(moves,newMove,set,idx,maxPlayer,player,col);
                		newMove.setMoveType("J");
//                		double diff = newMove.getValue() - current.getValue();
//                		
//                		if(col.equals("W") && diff < 0) {
//                			moves.add(newMove);
//                        }else if(col.equals("B") && diff > 0){
//                        	moves.add(newMove);
//                        }
                		double start = 0.0;
                		double end = 0.0;
                		if(col.equals("W")) {
                			start = centerCoordWhite(cell.getRow(),cell.getCol());
                            end = centerCoordWhite(r+dir[0],c+dir[1]);
                        }else if(col.equals("B")){
                        	start = centerCoordBlack(cell.getRow(),cell.getCol());
                            end = centerCoordBlack(r+dir[0],c+dir[1]);
                        }
                		double diff = end - start;
                		
//                		if(col.equals("W") && diff < 0) {
//                			moves.add(newMove);
//                        }else if(col.equals("B") && diff > 0){
//                        	moves.add(newMove);
//                        }
                		
                		///
                		if(col.equals("W")) {
                			for(int[] temp:homework.bottomRightbase) {
                	        	if(r == temp[0] && c == temp[1]) {
                	        	    checker = false;
                	        	}
                	        }
                			if(diff < 0) {
                				keepSelfJumpingToQueueFirst(moves,newMove,set,idx,maxPlayer,player,col);
                				if(checker) moves.add(newMove);
                			}
                	    }else if(col.equals("B")){
                	        for(int[] temp:homework.topLeftbase) {
                	    	    if(r == temp[0] && c == temp[1]) {
                	    	        checker = false;
                	    	    }
                	        }
                	    	if(diff > 0) {
                				keepSelfJumpingToQueueFirst(moves,newMove,set,idx,maxPlayer,player,col);
                				if(checker) moves.add(newMove);
                			}
                	    }
                		///
                		
                		cells[r+dir[0]][c+dir[1]].setPlayer(homework.FREE);
                		cells[cell.getRow()][cell.getCol()].setPlayer(homework.SELF);
                		iterator.put(idx,cell);
                		set.remove(cell.getRow()* homework.SIZE + cell.getCol());
                		
            	    }
            	}
              }
            }
	}
	    
    public void keepOpponJumpingToQueueFirst(ArrayList<Move> moves,Move current,Set<Integer> set,int idx,boolean maxPlayer,boolean player,String col){
		Cell[][] cells = current.getCells();
		
		Map<Integer,Cell> iterator;
		iterator = current.getOpponMap();  
		Cell cell = iterator.get(idx);
			
            for(int[] dir:dirs) {
            	int r = cell.getRow() + dir[0];
                int c = cell.getCol() + dir[1]; 
              if(set.contains((r+dir[0]) * homework.SIZE + c+dir[1])) continue;
              if(r >= 0 && r<cells.length && c >= 0 && c < cells[0].length) {
            	
            	if(!cells[r][c].getPlayer().equals("free")) {
            		if(r+dir[0] >= 0 && r+dir[0]<cells.length && c+dir[1] >= 0 && c+dir[1] < cells[0].length && cells[r+dir[0]][c+dir[1]].getPlayer().equals("free")) {
            			boolean checker = true;
            			cells[r+dir[0]][c+dir[1]].setPlayer(homework.OPPONENT);
            			iterator.put(idx,cells[r+dir[0]][c+dir[1]]);
            			set.add(cell.getRow()* homework.SIZE + cell.getCol());
            		    cells[cell.getRow()][cell.getCol()].setPlayer(homework.FREE);
                		Move newMove = new Move();
                		
                		int[] arr = {cell.getRow(),cell.getCol(),r+dir[0],c+dir[1]};
                		List<int[]> list = new ArrayList<>();
                		list.addAll(current.getList());
                		list.add(arr);
                		newMove.setList(list);
                		
                		newMove.setCells(cells);
                		newMove.setValue(current.getValue());
                		newMove.setFrom(current.getFrom());
                		newMove.setTo(cells[r+dir[0]][c+dir[1]]);
                		newMove.setValue(getEvaluation(newMove,maxPlayer,player));	
                		newMove.setSelfMap(current.getSelfMap());
                		newMove.setOpponMap(iterator);
                		
                		newMove.setMoveType("J");
//                		double diff = newMove.getValue() - current.getValue();
//                		
//                		if(col.equals("W") && diff < 0) {
//                			moves.add(newMove);
//                        }else if(col.equals("B") && diff > 0){
//                        	moves.add(newMove);
//                        }
                		
                		double start = 0.0;
                		double end = 0.0;
                		if(col.equals("W")) {
                			start = centerCoordWhite(cell.getRow(),cell.getCol());
                            end = centerCoordWhite(r+dir[0],c+dir[1]);
                        }else if(col.equals("B")){
                        	start = centerCoordBlack(cell.getRow(),cell.getCol());
                            end = centerCoordBlack(r+dir[0],c+dir[1]);
                        }
                		
                		double diff = end - start;
                		
                		if(col.equals("W")) {
                			for(int[] temp:homework.bottomRightbase) {
                	        	if(r == temp[0] && c == temp[1]) {
                	        	    checker = false;
                	        	}
                	        }

                			if(diff < 0) {
                				keepOpponJumpingToQueueFirst(moves,newMove,set,idx,maxPlayer,player,col);
                				if(checker) moves.add(newMove);
                			}
                			
                			
                			
                	    }else if(col.equals("B")){
                	        for(int[] temp:homework.topLeftbase) {
                	    	    if(r == temp[0] && c == temp[1]) {
                	    	        checker = false;
                	    	    }
                	        }
                	    	if(diff > 0) {
                				keepOpponJumpingToQueueFirst(moves,newMove,set,idx,maxPlayer,player,col);
                				if(checker) moves.add(newMove);
                			}
                	    	
                	    	
                	    }
                		///
                		
                		
                		cells[r+dir[0]][c+dir[1]].setPlayer(homework.FREE);
                		cells[cell.getRow()][cell.getCol()].setPlayer(homework.OPPONENT);
                		iterator.put(idx,cell);
                		set.remove(cell.getRow()* homework.SIZE + cell.getCol());
            	    }
            	}

              }
            }	
	
    
    
    }
}
