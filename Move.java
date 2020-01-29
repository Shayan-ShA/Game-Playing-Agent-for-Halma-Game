package testTheHW2;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Move implements Comparator<Move>{
	private Cell[][] cells;
	private double value;
	private String moveType;
	private Cell to;
	private Cell from;
	private double alpha;
	private double beta;
	private List<int[]> list;
	
	private Map<Integer,Cell> self;
	private Map<Integer,Cell> opponent;
	private String type;
	private String col;
	
	public double getAlpha() {
		return alpha;
	}
	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}
	public double getBeta() {
		return beta;
	}
	public void setBeta(double bestSoFar) {
		this.beta = bestSoFar;
	}
	public Cell[][] getCells() {
		return cells;
	}
	public void setCells(Cell[][] cells) {
		this.cells = cells;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	
	
	public int compare(Move a, Move b) {
		return (int) (a.getValue() - b.getValue());
	}
	
	
	public String getMoveType() {
		return moveType;
	}
	public void setMoveType(String moveType) {
		this.moveType = moveType;
	}
	
	public Map<Integer,Cell> getSelfMap(){
		return this.self;
	}
	
	public void setSelfMap(Map<Integer,Cell> self2) {
		this.self = self2;
	}
	
	
	public List<int[]> getList(){
		return this.list;
	}
	
	public void setList(List<int[]> li) {
		this.list = li;
	}
	
	
	
	
	public Map<Integer,Cell> getOpponMap(){
		return this.opponent;
	}
	
	public void setOpponMap(Map<Integer,Cell> map) {
		this.opponent = map;
	}
	
	public Cell getFrom() {
		return from;
	}
	public void setFrom(Cell from) {
		this.from = from;
	}
	
	public Cell getTo() {
		return to;
	}
	public void setTo(Cell to) {
		this.to = to;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getColor() {
		return col;
	}
	
	public void setColor(String col) {
		this.col = col;
	}
	
}

