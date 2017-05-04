import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.List;
import java.util.LinkedList;

import javax.imageio.ImageIO;

public class Knight extends Piece {
	
	private BufferedImage img;
	public static final String white_img_file = "white-knight.png";
	public static final String black_img_file = "black-knight.png";
	
	public Knight(boolean isWhite, int initX, int initY) {
		super(isWhite, initX, initY);
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

		moves.add(new Coordinate(getX() + 1, getY() + 2));
		moves.add(new Coordinate(getX() + 1, getY() - 2));
		moves.add(new Coordinate(getX() - 1, getY() + 2));
		moves.add(new Coordinate(getX() - 1, getY() - 2));
		moves.add(new Coordinate(getX() + 2, getY() + 1));
		moves.add(new Coordinate(getX() + 2, getY() - 1));
		moves.add(new Coordinate(getX() - 2, getY() + 1));
		moves.add(new Coordinate(getX() - 2, getY() - 1));
	
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
		return "N";
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(img, this.getX()*Board.SQUARE_LENGTH,
				this.getY()*Board.SQUARE_LENGTH, WIDTH, HEIGHT, null);
	}
}

