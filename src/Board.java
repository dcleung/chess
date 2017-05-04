import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;

@SuppressWarnings("serial")
public class Board extends JPanel {
	public Piece[][] boardArray = new Piece[8][8];
	public ArrayList<Piece[][]> boardArrayList = new ArrayList<Piece[][]>();
	public int maxMoveNum;
	public int currMoveNum;
	public int promote = 3;
	
	public final static int SQUARE_LENGTH = 80;
	public Point clickedPt;
	public Point endPt;
	public Coordinate checkedSq;
	
	public boolean playing;
	public boolean isWhite;
	public Piece pieceSelected;
	public JLabel status;
	
	//true if white to move; false if black to move
	public boolean move;
	
	/** inCheck state is 0 if no one is in check. 1 if white is in check, -1 if black is in check**/
	public int inCheck = 0;
	
	public Board (boolean playerIsWhite, JLabel status) {
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.playing = true;
		this.status = status;
		this.isWhite = playerIsWhite;
		this.move = true;
		
		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int xBoard = (int) (e.getX()/SQUARE_LENGTH);
				int yBoard = (int) (e.getY()/SQUARE_LENGTH);
				System.out.println("(" + xBoard + "," + yBoard + ")");
				
				if (playing == true) {
					if (pieceSelected == null) {
						pieceSelected = boardArray[xBoard][yBoard];
						System.out.println(pieceSelected);
						if (pieceSelected == null)
							return;
						else if (pieceSelected.getColor() == true && getStatus().equals("White to move")
								|| pieceSelected.getColor() == false && getStatus().equals("Black to move")) {
							clickedPt = e.getPoint();
						}
						else {
							clickedPt = null;
							pieceSelected = null;
						}
					}
					else {
						endPt = e.getPoint();
						if ((int) (xBoard) == (int) (clickedPt.getX() / SQUARE_LENGTH) && 
								(int) (yBoard) == (int) (clickedPt.getY() / SQUARE_LENGTH)) {
							endPt = null;
							clickedPt = null;
							pieceSelected = null;
						}
		
						else if (legalMoves(pieceSelected).contains(new Coordinate(xBoard, yBoard))) {
							boardArray[pieceSelected.getX()][pieceSelected.getY()] = null;
							pieceSelected.setPos(xBoard, yBoard);
							boardArray[xBoard][yBoard] = pieceSelected;
							
							if (pieceSelected.getClass() == Pawn.class) {
								((Pawn) pieceSelected).setNotFirst();
								if (((Pawn) pieceSelected).getY() == 0 || ((Pawn) pieceSelected).getY() == 7) {
									//boardArray[xBoard][yBoard] = new Queen(pieceSelected.getColor(), xBoard, yBoard);
									switch (promote) {
										case 0: boardArray[xBoard][yBoard] = new Knight(pieceSelected.getColor(), xBoard, yBoard);
												break;
										case 1: boardArray[xBoard][yBoard] = new Bishop(pieceSelected.getColor(), xBoard, yBoard);
												break;
										case 2: boardArray[xBoard][yBoard] = new Rook(pieceSelected.getColor(), xBoard, yBoard);
												break;
										case 3: boardArray[xBoard][yBoard] = new Queen(pieceSelected.getColor(), xBoard, yBoard);
												break;
										default: boardArray[xBoard][yBoard] = new Queen(pieceSelected.getColor(), xBoard, yBoard);
												break;
										
									}
								}
							}	
							
							//White Castling
							if (pieceSelected.getClass() == King.class) {
								King king = (King) pieceSelected;
								if (king.getColor() == true && king.hasMoved == false) {
									//Bottom Kingside
									if (xBoard == 6 && yBoard == 7) {
										boardArray[7][7] = null;
										Rook shortRook = new Rook (true, 5, 7);
										shortRook.hasMoved = true;
										boardArray[5][7] = shortRook;
									}
									//Bottom Queenside
									if (xBoard == 2 && yBoard == 7) {
										boardArray[0][7] = null;
										Rook longRookBot = new Rook (true, 3, 7);
										longRookBot.hasMoved = true;
										boardArray[3][7] = longRookBot;
									}
									//Top Kingside
									if (xBoard == 1 && yBoard == 0) {
										boardArray[0][0] = null;
										Rook shortRook = new Rook (true, 2, 0);
										shortRook.hasMoved = true;
										boardArray[2][0] = shortRook;
									}
									//Top Queenside
									if (xBoard == 5 && yBoard == 0) {
										boardArray[7][0] = null;
										Rook longRook = new Rook (true, 4, 0);
										longRook.hasMoved = true;
										boardArray[4][0] = longRook;
									}
								}
							
								//Black Castling
								if (king.getColor() == false && king.hasMoved == false) {
									//Bottom Kingside
									if (xBoard == 1 && yBoard == 7) {
										boardArray[0][7] = null;
										Rook rightRook = new Rook (false, 2, 7);
										rightRook.hasMoved = true;
										boardArray[2][7] = rightRook;
									}
									//Bottom Queenside
									if (xBoard == 5 && yBoard == 7) {
										boardArray[7][7] = null;
										Rook leftRook = new Rook (false, 4, 7);
										leftRook.hasMoved = true;
										boardArray[4][7] = leftRook;
									}
									//Top Kingside
									if (xBoard == 6 && yBoard == 0) {
										boardArray[7][0] = null;
										Rook leftRook = new Rook (false, 5, 0);
										leftRook.hasMoved = true;
										boardArray[5][0] = leftRook;
									}
									//Top Queenside
									if (xBoard == 2 && yBoard == 0) {
										boardArray[0][0] = null;
										Rook leftRook = new Rook (false, 3, 0);
										leftRook.hasMoved = true;
										boardArray[3][0] = leftRook;
									}
								}
								
								king.hasMoved = true;					
							}
							
							if (pieceSelected.getClass() == Rook.class) {
								((Rook) pieceSelected).hasMoved = true;
							}
							//maxMoveNum++;
							//boardArrayList.add(boardArray);
							
							moveUpdate();	
							
							Coordinate kingSquare = null;
							for (int i = 0; i < 8; i++) {
								for (int j = 0; j < 8; j++) {
									if (boardArray[i][j] != null && boardArray[i][j].getColor() == move
											&& boardArray[i][j].getClass() == King.class) {
									kingSquare = new Coordinate(i, j);
									}
								}
							}
							
							if (kingSquare != null && checkForCheck(boardArray)) {
								checkedSq = kingSquare;
							}
							else {
								checkedSq = null;
							}
							
							if (checkForStalemate(boardArray) == true && checkedSq == null) {
								playing = false;
								updateStalemateStatus();
							}
							
							if (checkForMate(boardArray) == true && checkedSq != null) {
								playing = false;
								updateCheckStatus();
							}	

							clickedPt = null;
							endPt = null;
							pieceSelected = null;
						}
					}
				}
				repaint();
				
				//CONSOLE REPRESENTATION.
				for (int i = 0; i < 8; i++) {
					for (int j = 0; j < 8; j++) {
						if (boardArray[i][j] == null)
							System.out.print("[ ]");
						else
							System.out.print("[" + boardArray[i][j] + "]");
					}
					System.out.println("");
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		this.setFocusable(true);
		this.requestFocusInWindow();
		
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					currMoveNum--;
					System.out.println("LEFT");
					repaint();
				}
				else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					currMoveNum++;
					System.out.println("Right");
					repaint();
				}
				System.out.println("key");
			}
		});
	}
	
	public void updateStalemateStatus() {
		this.status.setText("Stalemate.");
	}

	public boolean checkForStalemate(Piece[][] someBoardArray) {
		List<Coordinate> allMoves = new LinkedList<Coordinate>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (someBoardArray[i][j] != null && someBoardArray[i][j].getColor() == move) {
					System.out.println(someBoardArray[i][j]);
					allMoves.addAll(legalMoves(someBoardArray[i][j].move(someBoardArray)));
				}
			}
		}
		int numPossMoves = allMoves.size();
		if (numPossMoves == 0)
			return true;
		return false;
	}

	public void updateCheckStatus() {
		this.status.setText("Checkmate!");
	}

	public List<Coordinate> squaresControlled(Piece[][] someBoardArray) {
		List<Coordinate> squaresControlled = new LinkedList<Coordinate>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (someBoardArray[i][j] != null && someBoardArray[i][j].getColor() != move
						//&& boardArray[i][j].getClass() != King.class
						&& someBoardArray[i][j].move(someBoardArray) != null) {
					List<Coordinate> pieceSquares = someBoardArray[i][j].move(someBoardArray);
					if (someBoardArray[i][j].getClass() == Pawn.class && ((Pawn) someBoardArray[i][j]).goingUp) {
						pieceSquares.remove(new Coordinate(i, j - 1));
						pieceSquares.remove(new Coordinate(i, j - 2));
						pieceSquares.add(new Coordinate(i + 1, j - 1));
						pieceSquares.add(new Coordinate(i - 1, j - 1));
					}
					if (someBoardArray[i][j].getClass() == Pawn.class && !((Pawn) someBoardArray[i][j]).goingUp) {
						pieceSquares.remove(new Coordinate(i, j + 1));
						pieceSquares.remove(new Coordinate(i, j + 2));
						pieceSquares.add(new Coordinate(i + 1, j + 1));
						pieceSquares.add(new Coordinate(i - 1, j + 1));
					}
					squaresControlled.addAll(pieceSquares);
				}
			}
		}
		return squaresControlled;
	}
	
	/*public List<Coordinate> checkValidity(List<Coordinate> pieceMoves) {
		List<Coordinate> newMoves = pieceMoves;
		List<Coordinate> illegalMoves = new LinkedList<Coordinate>();
		for (Coordinate c : pieceMoves) {
			Piece[][] testBoardArray = boardArray;
			testBoardArray[pieceSelected.getX()][pieceSelected.getY()] = null;
			testBoardArray[c.getX()][c.getY()] = pieceSelected;
			//move = !move;
			if (checkForCheck(testBoardArray) == true)
				illegalMoves.add(c);
		}
		for (Coordinate c : illegalMoves) {
			newMoves.remove(c);
		}
		return newMoves;
	}*/
	
	public boolean checkForCheck(Piece[][] someBoardArray) {
		System.out.println("checkForCheck");
		List<Coordinate> squaresControlled = new LinkedList<Coordinate>();
		Coordinate kingSquare = null;
		squaresControlled = squaresControlled(someBoardArray);
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (someBoardArray[i][j] != null && someBoardArray[i][j].getColor() == move
						&& someBoardArray[i][j].getClass() == King.class) {
				kingSquare = new Coordinate(i, j);
				}
			}
		}
		
		System.out.println("King is on " + kingSquare);
		
		if (kingSquare != null && squaresControlled.contains(kingSquare)) {
			System.out.println(move + " is in check!");
			return true;
		}
		else {
			System.out.println("no check");
			return false;
		}
	}
	
	public List<Coordinate> legalMoves(Piece somePiece) {
		List<Coordinate> lc = somePiece.move(boardArray);
		List<Coordinate> legalMoves = new LinkedList<Coordinate>();
		int origX = somePiece.getX();
		int origY = somePiece.getY();
		for (Coordinate c : lc) {
			somePiece.setPos(origX, origY);
			Piece[][] temp = new Piece[8][8];
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					temp[i][j] = boardArray[i][j];
				}
			}
			
			temp[somePiece.getX()][somePiece.getY()] = null;
			temp[c.getX()][c.getY()] = somePiece;
			
			if (!checkForCheck(temp)) {
				legalMoves.add(c);
			}

		}
		//pieceSelected.setPos(origX, origY);
		return legalMoves;
	}
	
	
	public List<Coordinate> legalMoves(List<Coordinate> lc) {
		List<Coordinate> legalMoves = new LinkedList<Coordinate>();
		int origX = pieceSelected.getX();
		int origY = pieceSelected.getY();
		for (Coordinate c : lc) {
			pieceSelected.setPos(origX, origY);
			Piece[][] temp = new Piece[8][8];
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					temp[i][j] = boardArray[i][j];
				}
			}
			
			temp[pieceSelected.getX()][pieceSelected.getY()] = null;
			//pieceSelected.setPos(c.getX(), c.getY());
			temp[c.getX()][c.getY()] = pieceSelected;
			
			if (!checkForCheck(temp)) {
				System.out.println(pieceSelected + " on " + pieceSelected.getX() + "," + pieceSelected.getY() + " going to " + c + " saves the king");
				legalMoves.add(c);
			}

		}
		//pieceSelected.setPos(origX, origY);
		return legalMoves;
	}
	
	public boolean checkForMate(Piece[][] someBoardArray) {
		List<Coordinate> allMoves = new LinkedList<Coordinate>();
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (someBoardArray[i][j] != null && someBoardArray[i][j].getColor() == move
						&& someBoardArray[i][j].move(someBoardArray) != null) {
					System.out.println(someBoardArray[i][j]);
					allMoves.addAll(legalMoves(someBoardArray[i][j]));
				}
			}
		}
		int numPossMoves = allMoves.size();
		
		System.out.println(move + " has this many " + numPossMoves + " moves.");
		System.out.println("the moves are: " + allMoves);
		if (numPossMoves == 0) {
			System.out.println("Checkmate!");
			return true;
		}
		System.out.println("no mate");
		return false;
	}

	/*public Piece getPiece(int i, int j) {
		return boardArray[i][j];
	}*/
	
	public void moveUpdate() {
		if (move == true) {
			move = false;
			this.status.setText("Black to move");
		}
		else {
			move = true;
			this.status.setText("White to move");
		}
	}
	
	public String getStatus() {
		return status.getText();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, SQUARE_LENGTH*8, SQUARE_LENGTH*8);
		Color black_squares = new Color(155, 180, 193);
		g.setColor(black_squares);
		for (int i = 0; i < 640; i+=160) {
			for (int j = 0; j < 640; j += 160)
			g.fillRect(80 + i, j, 80, 80);
		}
		for (int i = 0; i < SQUARE_LENGTH*8; i+= SQUARE_LENGTH*2) {
			for (int j = 80; j < 640; j += 160)
			g.fillRect(i, j, 80, 80);
		}
		Color white_squares = new Color(235, 238, 240);
		g.setColor(white_squares);
		for (int i = 0; i < SQUARE_LENGTH*8; i+= SQUARE_LENGTH*2) {
			for (int j = 80; j < 640; j += 160)
			g.fillRect(80 + i, j, 80, 80);
		}
		for (int i = 0; i < SQUARE_LENGTH*8; i+= SQUARE_LENGTH*2) {
			for (int j = 0; j < 640; j += 160)
			g.fillRect(i, j, 80, 80);
		}
		
		Color transgreen = new Color (0, 200, 0, 180);
		g.setColor(transgreen);
		if (pieceSelected != null) {
			int clickedX = (int) ((pieceSelected.getX()));
			int clickedY = (int) ((pieceSelected.getY()));
			g.fillRect(80*clickedX, 80*clickedY, 80, 80);
		}
		
		Color transred = new Color (255, 60, 20, 180);
		g.setColor(transred);
		if (checkedSq != null) {
			int clickedX = (int) ((checkedSq.getX()));
			int clickedY = (int) ((checkedSq.getY()));
			g.fillRect(80*clickedX, 80*clickedY, 80, 80);
		}
		
		g.setColor(transgreen);
		//Piece[][] currBoard = boardArrayList.get(currMoveNum);
		//iterate through the board and draw pieces in their position
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++){
				if (boardArray[i][j] != null)
					boardArray[i][j].draw(g);
			}
		}
		
		//get positions
		if (pieceSelected != null) {
			for (Coordinate p : legalMoves(pieceSelected)) {
				int pX = (int) p.getX();
				int pY = (int) p.getY();
				g.fillOval(80*pX + 30, 80*pY + 30, 20, 20);
				System.out.println("painted a green dot");
			}
		}
	}
	
	public void resetStd() {
		playing = true;
		clickedPt = null;
		checkedSq = null;
		endPt = null;
		pieceSelected = null;
		move = true;
		status.setText("White to move");
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				boardArray[i][j] = null; //clear the old board
			}
		}
		for (int i = 0; i < 8; i++) {
			boardArray[i][6] = new Pawn(isWhite, i, 6); //construct player's pawns
			boardArray[i][1] = new Pawn(!isWhite, i, 1); //construct opponent's pawns
		}
		//construct player's pieces
		boardArray[0][7] = new Rook(isWhite, 0, 7);
		boardArray[7][7] = new Rook(isWhite, 7, 7);
		boardArray[1][7] = new Knight(isWhite, 1, 7);
		boardArray[6][7] = new Knight(isWhite, 6, 7);
		boardArray[2][7] = new Bishop(isWhite, 2, 7);
		boardArray[5][7] = new Bishop(isWhite, 5, 7);
		
		//construct Kings and Queens
		if (isWhite == true) {
			boardArray[4][7] = new King(isWhite, 4, 7);
			boardArray[3][7] = new Queen(isWhite, 3, 7);
			boardArray[4][0] = new King(!isWhite, 4, 0);
			boardArray[3][0] = new Queen(!isWhite, 3, 0);
		}
		else {
			boardArray[4][7] = new Queen(isWhite, 4, 7);
			boardArray[3][7] = new King(isWhite, 3, 7);
			boardArray[4][0] = new Queen(!isWhite, 4, 0);
			boardArray[3][0] = new King(!isWhite, 3, 0);
		}
		
		//construct opponent's pieces
		boardArray[0][0] = new Rook(!isWhite, 0, 0);
		boardArray[7][0] = new Rook(!isWhite, 7, 0);
		boardArray[1][0] = new Knight(!isWhite, 1, 0);
		boardArray[6][0] = new Knight(!isWhite, 6, 0);
		boardArray[2][0] = new Bishop(!isWhite, 2, 0);
		boardArray[5][0] = new Bishop(!isWhite, 5, 0);
		
		boardArrayList.add(boardArray);
		maxMoveNum = 0;
		currMoveNum = 0;
		repaint();
	}
	
	public static int getRandom() {
		Random rand = new Random();
	    int randomNum = rand.nextInt(8);
	    return randomNum;
	}
	
	public void resetRandomized() {
		playing = true;
		clickedPt = null;
		checkedSq = null;
		endPt = null;
		move = true;
		pieceSelected = null;
		status.setText("White to move");
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				boardArray[i][j] = null; //clear the old board
			}
		}
		for (int i = 0; i < 8; i++) {
			boardArray[i][6] = new Pawn(isWhite, i, 6); //construct player's pawns
			boardArray[i][1] = new Pawn(!isWhite, i, 1); //construct opponent's pawns
		}
		
		//Randomize positions of main pieces by array.
		Set<Integer> seen = new TreeSet<Integer>();
		int a = getRandom();
		seen.add(a);
		boardArray[a][0] = new Rook(!isWhite, a, 0);
		boardArray[a][7] = new Rook(isWhite, a, 7);
		
		while (seen.contains(a)) {
			a = getRandom();
		}
		seen.add(a);
		boardArray[a][0] = new Rook(!isWhite, a, 0);
		boardArray[a][7] = new Rook(isWhite, a, 7);
		
		while (seen.contains(a)) {
			a = getRandom();
		}
		seen.add(a);
		boardArray[a][0] = new Knight(!isWhite, a, 0);
		boardArray[a][7] = new Knight(isWhite, a, 7);
		
		while (seen.contains(a)) {
			a = getRandom();
		}
		seen.add(a);
		boardArray[a][0] = new Knight(!isWhite, a, 0);
		boardArray[a][7] = new Knight(isWhite, a, 7);
		
		while (seen.contains(a)) {
			a = getRandom();
		}
		seen.add(a);
		boardArray[a][0] = new Bishop(!isWhite, a, 0);
		boardArray[a][7] = new Bishop(isWhite, a, 7);
		
		while (seen.contains(a)) {
			a = getRandom();
		}
		seen.add(a);
		boardArray[a][0] = new Bishop(!isWhite, a, 0);
		boardArray[a][7] = new Bishop(isWhite, a, 7);
		
		while (seen.contains(a)) {
			a = getRandom();
		}
		seen.add(a);
		boardArray[a][0] = new Queen(!isWhite, a, 0);
		boardArray[a][7] = new Queen(isWhite, a, 7);
		
		while (seen.contains(a)) {
			a = getRandom();
		}
		seen.add(a);
		King oppKing =  new King(!isWhite, a, 0);
		oppKing.hasMoved = true;
		boardArray[a][0] = oppKing;
		
		King myKing =  new King(isWhite, a, 7);
		myKing.hasMoved = true;
		boardArray[a][7] = myKing;

		boardArrayList.add(boardArray);
		maxMoveNum = 0;
		currMoveNum = 0;
		repaint();
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(SQUARE_LENGTH * 8, SQUARE_LENGTH * 8);
	}
}
