import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.List;
import java.util.LinkedList;

import javax.imageio.ImageIO;

public class Pawn extends Piece {
	public boolean isFirstMove;
	private boolean enPassant;
	public final boolean goingUp;
	private BufferedImage img;
	public static final String white_img_file = "white-pawn.png";
	public static final String black_img_file = "black-pawn.png";
	
	//initX varies from 0 to 7, initY varies from 0 to 7
	public Pawn(boolean isWhite, int initX, int initY) {
		super(isWhite, initX, initY);
		if (initY == 6)
			goingUp = true;
		else
			goingUp = false;
		isFirstMove = true;
		try {
			if (img == null) {
				if (isWhite)
					img = ImageIO.read(new File(white_img_file));
				else
					img = ImageIO.read(new File(black_img_file));
			}
		} catch (IOException e) {
			System.out.println("Internal Error:" + e.getMessage());
		}
	}
	
	public void setNotFirst() {
		isFirstMove = false;
	}
	
	public void enableEnPassant() {
		enPassant = true;
	}
	
	@Override
	public List<Coordinate> move(Piece[][] boardArray) {
		moves = new LinkedList<Coordinate>();
		if (this.goingUp == true) {
			if (isFirstMove)
				if (boardArray[getX()][getY() - 2] == null && boardArray[getX()][getY() - 1] == null)
					moves.add(new Coordinate(getX(), getY() - 2));
			if (boardArray[getX()][getY() - 1] == null)
				moves.add(new Coordinate (getX(), getY() - 1));
			
			if (getX() + 1 < 8 && getY() -1 > -1) {
				if (boardArray[getX() + 1][getY() - 1] != null && boardArray[getX() + 1][getY() - 1].getColor() != this.getColor())
					moves.add(new Coordinate (getX() + 1, getY() - 1));
			}
			if (getX() - 1 > -1 && getY() - 1 > -1) {
				if (boardArray[getX() - 1][getY() - 1] != null && boardArray[getX() - 1][getY() - 1].getColor() != this.getColor())
					moves.add(new Coordinate (getX() - 1, getY() - 1));
			}
		}
		
		if (this.goingUp == false) {
			if (isFirstMove)
				if (boardArray[getX()][getY() + 2] == null && boardArray[getX()][getY() + 1] == null)
					moves.add(new Coordinate(getX(), getY() + 2));
			if (boardArray[getX()][getY() + 1] == null)
				moves.add(new Coordinate (getX(), getY() + 1));
			
			if (getX() + 1 < 8 && getY() + 1 < 8) {
				if (boardArray[getX() + 1][getY() + 1] != null && boardArray[getX() + 1][getY() + 1].getColor() != this.getColor())
					moves.add(new Coordinate (getX() + 1, getY() + 1));
			}
			if (getX() - 1 > -1 && getY() + 1 < 8) {
				if (boardArray[getX() - 1][getY() + 1] != null && boardArray[getX() - 1][getY() + 1].getColor() != this.getColor())
					moves.add(new Coordinate (getX() - 1, getY() + 1));
			}
		}
		
		List<Coordinate> illegalMoves = new LinkedList<Coordinate>();
		for (Coordinate p : moves) {
			int xCoord = (int) p.getX();
			int yCoord = (int) p.getY();
			if (xCoord < 0 || yCoord < 0 || xCoord > 7 || yCoord > 7)
				illegalMoves.add(p);
			else {
				if (boardArray[xCoord][yCoord] != null && boardArray[xCoord][yCoord].getColor()
						== this.getColor())
					illegalMoves.add(p);
			}
		}
		
		for (Coordinate p : illegalMoves)
			moves.remove(p);
		
		return moves;
	}
		
	@Override
	public String toString() {
		return "P";
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(img, this.getX()*Board.SQUARE_LENGTH,
				this.getY()*Board.SQUARE_LENGTH, WIDTH, HEIGHT, null);
	}
}
