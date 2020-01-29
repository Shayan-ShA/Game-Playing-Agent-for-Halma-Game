package testTheHW2;

public abstract class AdverserialSearch {

	public AdverserialSearch() {
		
	}
	
	public abstract Move adverserialSearch(Move current,int depth,double counter,double time, boolean maxPlayer,boolean player,String Col);
	
	public double centerCoordBlack(double x, double y) {
	     return (y - 7.5) - (7.5 - x);
   }
	
	public double centerCoordWhite(double x, double y) {
	     return (y - 7.5) + (x - 7.5);
  }
	
	public double getEvaluation(Move move, boolean maxPlayer,boolean player){
		double value = move.getValue();
		double start = 0.0;
		double end = 0.0;
		double extra = 0.0;
		double ret = 0.0;
		Cell from = move.getFrom();
        Cell to = move.getTo();
        
        String evalPawn;
        
        if(player) {
        	evalPawn = homework.player;
        }else {
        	evalPawn = homework.oppo;
        }
        
        if(evalPawn.equals("B")) {
        	start = centerCoordBlack(from.getRow(),from.getCol());
            end = centerCoordBlack(to.getRow(),to.getCol());
            if(to.getCol() - to.getRow() > 8 || to.getCol() - to.getRow() < -8) {
            	extra = 3;
            }
            return value + end - start - extra;
        }else {
        	start = centerCoordWhite(from.getRow(),from.getCol());
            end = centerCoordWhite(to.getRow(),to.getCol());
            if(to.getCol() - to.getRow() > 8 || to.getCol() - to.getRow() < -8) {
            	extra = 3;
            }
            return value + end - start + extra;
        }
	}
	
	
	
	public boolean isGameOver(Cell[][] cells){

		boolean check1 = true;
		boolean check2 = true;
		int count1 = 0;
		int count2 = 0;
		
		if(homework.player.equals("B")) {
			
		    for(int[] base1: homework.bottomRightbase) {
			    if (!cells[base1[0]][base1[1]].getPlayer().equals(homework.SELF)){
				    check1 = false;
		        }else count1++;
		    }
		    for(int[] base2: homework.topLeftbase) {
			    if (!cells[base2[0]][base2[1]].getPlayer().equals(homework.OPPONENT)){
				    check2 = false;
		        }else count2++;
		    }
		}
		if(homework.player.equals("W")){
			for(int[] base1: homework.bottomRightbase) {
			    if (!cells[base1[0]][base1[1]].getPlayer().equals(homework.OPPONENT)){
				    check2 = false;
		        }else count2++;
		    }
		    for(int[] base2: homework.topLeftbase) {
			    if (!cells[base2[0]][base2[1]].getPlayer().equals(homework.SELF)){
				    check1 = false;
		        }else count1++;
		    }
		}
		
		homework.setNumOfSelfInCamp(count1);
		homework.setNumOfOpponInCamp(count2);
		if(check1 || check2) System.out.println(" GAME OVER" );
		return check1 || check2;
	
    }
}