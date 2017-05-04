import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.imageio.ImageIO;

public class Bishop extends Piece {
	
	private BufferedImage img;
	public static final String white_img_file = "white-bishop.png";
	public static final String black_img_file = "black-bishop.png";
	
	public Bishop(boolean isWhite, int initX, int initY) {
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
		
		int i = this.getX();
		int j = this.getY();
		while(i < 7 && j < 7 && boardArray[i+1][j+1] == null) {
			i++;
			j++;
			moves.add(new Coordinate(i, j));
		}
		if (i < 7 && j < 7 && boardArray[i+1][j+1].getColor() != this.getColor())
			moves.add(new Coordinate(i+1, j+1));
		
		i = this.getX();
		j = this.getY();
		while(i < 7 && j > 0 && boardArray[i+1][j-1] == null) {
			i++;
			j--;
			moves.add(new Coordinate(i, j));
		}
		if (i < 7 && j > 0 && boardArray[i+1][j-1].getColor() != this.getColor())
			moves.add(new Coordinate(i+1, j-1));
		
		i = this.getX();
		j = this.getY();
		while(i > 0 && j > 0 && boardArray[i-1][j-1] == null) {
			i--;
			j--;
			moves.add(new Coordinate(i, j));
		}
		if (i > 0 && j > 0 && boardArray[i-1][j-1].getColor() != this.getColor())
			moves.add(new Coordinate(i-1, j-1));
		
		i = this.getX();
		j = this.getY();
		while(i > 0 && j < 7 && boardArray[i-1][j+1] == null) {
			i--;
			j++;
			moves.add(new Coordinate(i, j));
		}
		if (i > 0 && j < 7 && boardArray[i-1][j+1].getColor() != this.getColor())
			moves.add(new Coordinate(i-1, j+1));
		
		return moves;
	}
	
	@Override
	public String toString() {
		return "B";
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(img, this.getX()*Board.SQUARE_LENGTH,
				this.getY()*Board.SQUARE_LENGTH, WIDTH, HEIGHT, null);
	}
}

