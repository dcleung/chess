import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.imageio.ImageIO;

public class King extends Piece {
	
	public boolean hasMoved;
	private BufferedImage img;
	public static final String white_img_file = "white-king.png";
	public static final String black_img_file = "black-king.png";
	
	public King(boolean isWhite, int initX, int initY) {
		super(isWhite, initX, initY);
		hasMoved = false;
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
	
	@Override
	public List<Coordinate> move(Piece[][] boardArray) {
		moves = new LinkedList<Coordinate>();
		moves.add(new Coordinate(getX(), getY() - 1));
		moves.add(new Coordinate (getX(), getY() + 1));
		moves.add(new Coordinate(getX() + 1, getY() - 1));
		moves.add(new Coordinate (getX() + 1, getY() + 1));
		moves.add(new Coordinate (getX() + 1, getY()));
		moves.add(new Coordinate(getX() - 1, getY() - 1));
		moves.add(new Coordinate (getX() - 1, getY() + 1));
		moves.add(new Coordinate (getX() - 1, getY()));
		
		//White Bottom Castling
		if (this.getColor() == true && !hasMoved && this.getY() == 7) {
			//Castling short
			if (boardArray[getX() + 1][getY()] == null && boardArray[getX() + 2][getY()] == null
					&& boardArray[getX() + 3][getY()] != null
					&& boardArray[getX() + 3][getY()].getClass() == Rook.class
					&& ((Rook) boardArray[getX() + 3][getY()]).hasMoved == false)
				moves.add(new Coordinate(getX() + 2, getY()));
			
			//Castling short
			if (boardArray[getX() - 1][getY()] == null && boardArray[getX() - 2][getY()] == null
					&& boardArray[getX() - 3][getY()] == null
					&& boardArray[getX() - 4][getY()] != null
					&& boardArray[getX() - 4][getY()].getClass() == Rook.class
					&& ((Rook) boardArray[getX() - 4][getY()]).hasMoved == false)
				moves.add(new Coordinate(getX() - 2 , getY()));
		}
		
		//White Top Castling
		if (this.getColor() == true && !hasMoved && this.getY() == 7) {
			//Castling long
			if (boardArray[getX() + 1][getY()] == null && boardArray[getX() + 2][getY()] == null
					&& boardArray[getX() + 3][getY()] == null
					&& boardArray[getX() + 4][getY()] != null
					&& boardArray[getX() + 4][getY()].getClass() == Rook.class
					&& ((Rook) boardArray[getX() + 4][getY()]).hasMoved == false)
				moves.add(new Coordinate(getX() + 2, getY()));
			
			//Castling short
			if (boardArray[getX() - 1][getY()] == null && boardArray[getX() - 2][getY()] == null
					&& boardArray[getX() - 3][getY()] != null
					&& boardArray[getX() - 3][getY()].getClass() == Rook.class
					&& ((Rook)boardArray[getX() - 3][getY()]).hasMoved == false)
				moves.add(new Coordinate(getX() - 2 , getY()));
		}
		
		//Black Bottom Castling
		if (this.getColor() == false && !hasMoved && this.getY() == 7) {
			//Castling short
			if (boardArray[getX() - 1][getY()] == null && boardArray[getX() - 2][getY()] == null
					&& boardArray[getX() - 3][getY()] != null
					&& boardArray[getX() - 3][getY()].getClass() == Rook.class
					&& ((Rook) boardArray[getX() - 3][getY()]).hasMoved == false)
				moves.add(new Coordinate(getX() - 2, getY()));
			
			//Castling short
			if (boardArray[getX() + 1][getY()] == null && boardArray[getX() + 2][getY()] == null
					&& boardArray[getX() + 3][getY()] == null
					&& boardArray[getX() + 4][getY()] != null
					&& boardArray[getX() + 4][getY()].getClass() == Rook.class
					&& ((Rook) boardArray[getX() + 4][getY()]).hasMoved == false)
				moves.add(new Coordinate(getX() + 2 , getY()));
		}
		
		//Black Top Castling
		if (this.getColor() == false && !hasMoved && this.getY() == 0) {
			//Castling short
			if (boardArray[getX() + 1][getY()] == null && boardArray[getX() + 2][getY()] == null
					&& boardArray[getX() + 3][getY()] != null
					&& boardArray[getX() + 3][getY()].getClass() == Rook.class
					&& ((Rook) boardArray[getX() + 3][getY()]).hasMoved == false)
				moves.add(new Coordinate(getX() + 2, getY()));
			
			//Castling long
			if (boardArray[getX() - 1][getY()] == null && boardArray[getX() - 2][getY()] == null
					&& boardArray[getX() - 3][getY()] == null
					&& boardArray[getX() - 4][getY()] != null
					&& boardArray[getX() - 4][getY()].getClass() == Rook.class
					&& ((Rook) boardArray[getX() - 4][getY()]).hasMoved == false)
				moves.add(new Coordinate(getX() - 2 , getY()));
		}
		
		/** Generate a list of illegal moves and remove them from the piece's move list**/
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
		/*for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				List<Coordinate> pieceSquares = null;
				if (boardArray[i][j] != null && boardArray[i][j].getColor() != this.getColor()
						&& boardArray[i][j].getClass() != King.class
						&& boardArray[i][j].move(boardArray) != null) {
					pieceSquares = boardArray[i][j].move(boardArray);
					if (boardArray[i][j].getClass() == Pawn.class && ((Pawn) boardArray[i][j]).goingUp) {
						pieceSquares.remove(new Coordinate(i, j - 1));
						pieceSquares.remove(new Coordinate(i, j - 2));
						pieceSquares.add(new Coordinate(i + 1, j - 1));
						pieceSquares.add(new Coordinate(i - 1, j - 1));
					}
					if (boardArray[i][j].getClass() == Pawn.class && !((Pawn) boardArray[i][j]).goingUp) {
						pieceSquares.remove(new Coordinate(i, j + 1));
						pieceSquares.remove(new Coordinate(i, j + 2));
						pieceSquares.add(new Coordinate(i + 1, j + 1));
						pieceSquares.add(new Coordinate(i - 1, j + 1));
					}
					illegalMoves.addAll(pieceSquares);
				}
			}
		}*/
		
		for (Coordinate p : illegalMoves)
			moves.remove(p);
		
		return moves;
	}
	
	
	@Override
	public String toString() {
		return "K";
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(img, this.getX()*Board.SQUARE_LENGTH,
				this.getY()*Board.SQUARE_LENGTH, WIDTH, HEIGHT, null);
	}
}

