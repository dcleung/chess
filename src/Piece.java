import java.awt.Graphics;
import java.awt.Point;
import java.util.LinkedList;
import java.util.List;
import java.util.List;

public abstract class Piece {
	public final int WIDTH = 80;
	public final int HEIGHT = 80;
	
	private int xPos;
	private int yPos;
	public final boolean isWhite;
	public List<Coordinate> moves;
	
	abstract List<Coordinate> move(Piece[][] boardArray);
	abstract void draw(Graphics g);

	public int getX() {
		return xPos;
	}
	
	public int getY(){
		return yPos;
	}
	
	public boolean getColor(){
		return isWhite;
	}
	
	public void setPos(int newX, int newY) {
		xPos = newX;
		yPos = newY;
	}
	
	public Piece(boolean initIsWhite, int initX, int initY) {
		isWhite = initIsWhite;
		xPos = initX;
		yPos = initY;
	}
	
	@Override
	public String toString() {
		return " ";
	}

}
