import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;
import java.util.TreeSet;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.*;

public class MyTests {
	
	/** Basic Moving Pieces Test */
	@Test
	public void testPawnFirstMove() {
		JLabel status = new JLabel("White to Move");
		Board myBoard = new Board(true, status);
		myBoard.resetStd();
		Piece[][] myArray = new Piece[8][8];
		myArray = myBoard.boardArray.clone();
		assertTrue(myArray[4][6] instanceof Pawn);
		assertTrue(myArray[4][6].move(myArray).size() == 2);
		assertTrue(myArray[4][6].move(myArray).contains(new Coordinate(4, 5)));
		assertTrue(myArray[4][6].move(myArray).contains(new Coordinate(4, 4)));
	}
	
	@Test
	public void testPawnSecondMove() {
		JLabel status = new JLabel("White to Move");
		Board myBoard = new Board(true, status);
		myBoard.resetStd();
		Piece[][] myArray = new Piece[8][8];
		myArray = myBoard.boardArray.clone();
		Pawn myPawn = (Pawn) myArray[4][6];
		myPawn.setNotFirst();
		assertTrue(myPawn.move(myArray).size() == 1);
		assertTrue(myPawn.move(myArray).contains(new Coordinate(4, 5)));
	}
	
	@Test
	public void testKnightMoves() {
		JLabel status = new JLabel("White to Move");
		Board myBoard = new Board(true, status);
		myBoard.resetStd();
		Piece[][] myArray = new Piece[8][8];
		myArray = myBoard.boardArray.clone();
		assertTrue(myArray[1][7] instanceof Knight);
		assertTrue(myArray[1][7].move(myArray).size() == 2);
		assertTrue(myArray[1][7].move(myArray).contains(new Coordinate(0, 5)));
		assertTrue(myArray[1][7].move(myArray).contains(new Coordinate(2, 5)));
	}
	
	@Test
	public void testBishopMove() {
		JLabel status = new JLabel("White to Move");
		Board myBoard = new Board(true, status);
		myBoard.resetStd();
		Piece[][] myArray = new Piece[8][8];
		myArray = myBoard.boardArray.clone();
		Bishop myBishop = (Bishop) myArray[2][7];
		myArray[3][6] = null; //removing the queen pawn
		assertTrue(myBishop.move(myArray).size() == 5);
		assertTrue(myBishop.move(myArray).contains(new Coordinate(3, 6)));
		assertTrue(myBishop.move(myArray).contains(new Coordinate(4, 5)));
		assertTrue(myBishop.move(myArray).contains(new Coordinate(5, 4)));
		assertTrue(myBishop.move(myArray).contains(new Coordinate(6, 3)));
		assertTrue(myBishop.move(myArray).contains(new Coordinate(7, 2)));
	}
	
	@Test
	public void testBishopNoMoves() {
		JLabel status = new JLabel("White to Move");
		Board myBoard = new Board(true, status);
		myBoard.resetStd();
		Piece[][] myArray = new Piece[8][8];
		myArray = myBoard.boardArray.clone();
		Bishop myBishop = (Bishop) myArray[2][7];
		assertTrue(myBishop.move(myArray).size() == 0);
	}
	
	@Test
	public void testRookMove() {
		Piece[][] myArray = new Piece[8][8];
		Rook myRook = new Rook(true, 0, 0);
		myArray[0][0] = myRook;
		assertTrue(myRook.move(myArray).size() == 14);
		for (int i = 1; i < 7; i++) {
			assertTrue(myRook.move(myArray).contains(new Coordinate(0, i)));
			assertTrue(myRook.move(myArray).contains(new Coordinate(i, 0)));
		}
	}
	
	@Test
	public void testRookNoMoves() {
		JLabel status = new JLabel("White to Move");
		Board myBoard = new Board(true, status);
		myBoard.resetStd();
		Piece[][] myArray = new Piece[8][8];
		myArray = myBoard.boardArray.clone();
		Rook myRook = (Rook) myArray[0][7];
		assertTrue(myRook.move(myArray).size() == 0);
	}
	
	@Test
	public void testQueenMove() {
		Piece[][] myArray = new Piece[8][8];
		Queen myQueen = new Queen(true, 0, 0);
		myArray[0][0] = myQueen;
		assertTrue(myQueen.move(myArray).size() == 21);
		for (int i = 1; i < 7; i++) {
			assertTrue(myQueen.move(myArray).contains(new Coordinate(0, i)));
			assertTrue(myQueen.move(myArray).contains(new Coordinate(i, 0)));
			assertTrue(myQueen.move(myArray).contains(new Coordinate(i, i)));
		}
	}
	
	@Test
	public void testQueenNoMoves() {
		JLabel status = new JLabel("White to Move");
		Board myBoard = new Board(true, status);
		myBoard.resetStd();
		Piece[][] myArray = new Piece[8][8];
		myArray = myBoard.boardArray.clone();
		Queen myQueen = (Queen) myArray[3][7];
		assertTrue(myQueen.move(myArray).size() == 0);
	}
	
	@Test
	public void testKingMove() {
		Piece[][] myArray = new Piece[8][8];
		King myKing = new King(true, 4, 4);
		myArray[4][4] = myKing;
		assertTrue(myKing.move(myArray).size() == 8);
		assertTrue(myKing.move(myArray).contains(new Coordinate(3, 5)));
		assertTrue(myKing.move(myArray).contains(new Coordinate(5, 3)));
		assertTrue(myKing.move(myArray).contains(new Coordinate(4, 3)));
		assertTrue(myKing.move(myArray).contains(new Coordinate(3, 4)));
		assertTrue(myKing.move(myArray).contains(new Coordinate(3, 3)));
		assertTrue(myKing.move(myArray).contains(new Coordinate(4, 5)));
		assertTrue(myKing.move(myArray).contains(new Coordinate(5, 4)));
		assertTrue(myKing.move(myArray).contains(new Coordinate(5, 5)));
	}
	
	@Test
	public void testKingNoMoves() {
		JLabel status = new JLabel("White to Move");
		Board myBoard = new Board(true, status);
		myBoard.resetStd();
		Piece[][] myArray = new Piece[8][8];
		myArray = myBoard.boardArray.clone();
		King myKing = (King) myArray[4][7];
		assertTrue(myKing.move(myArray).size() == 0);
	}
	
	@Test
	public void testKingShortCastle() {
		JLabel status = new JLabel("White to Move");
		Board myBoard = new Board(true, status);
		myBoard.resetStd();
		Piece[][] myArray = new Piece[8][8];
		myArray = myBoard.boardArray.clone();
		King myKing = (King) myArray[4][7];
		myArray[5][7] = null;
		myArray[6][7] = null;
		assertTrue(myKing.move(myArray).size() == 2);
		assertTrue(myKing.move(myArray).contains(new Coordinate(5, 7)));
		assertTrue(myKing.move(myArray).contains(new Coordinate(6, 7)));
	}
	
	@Test
	public void testKingLongCastle() {
		JLabel status = new JLabel("White to Move");
		Board myBoard = new Board(true, status);
		myBoard.resetStd();
		Piece[][] myArray = new Piece[8][8];
		myArray = myBoard.boardArray.clone();
		King myKing = (King) myArray[4][7];
		myArray[3][7] = null;
		myArray[2][7] = null;
		myArray[1][7] = null;
		assertTrue(myKing.move(myArray).size() == 2);
		assertTrue(myKing.move(myArray).contains(new Coordinate(3, 7)));
		assertTrue(myKing.move(myArray).contains(new Coordinate(2, 7)));
	}
	
	@Test
	public void testKingBothCastlesAvailable() {
		JLabel status = new JLabel("White to Move");
		Board myBoard = new Board(true, status);
		myBoard.resetStd();
		Piece[][] myArray = new Piece[8][8];
		myArray = myBoard.boardArray.clone();
		King myKing = (King) myArray[4][7];
		myArray[5][7] = null;
		myArray[6][7] = null;
		myArray[3][7] = null;
		myArray[2][7] = null;
		myArray[1][7] = null;
		assertTrue(myKing.move(myArray).size() == 4);
		assertTrue(myKing.move(myArray).contains(new Coordinate(5, 7)));
		assertTrue(myKing.move(myArray).contains(new Coordinate(6, 7)));
		assertTrue(myKing.move(myArray).contains(new Coordinate(3, 7)));
		assertTrue(myKing.move(myArray).contains(new Coordinate(2, 7)));
	}
	
	@Test
	public void testKingShortCastleAfterKingMoved() {
		JLabel status = new JLabel("White to Move");
		Board myBoard = new Board(true, status);
		myBoard.resetStd();
		Piece[][] myArray = new Piece[8][8];
		myArray = myBoard.boardArray.clone();
		King myKing = (King) myArray[4][7];
		myKing.hasMoved = true;
		myArray[5][7] = null;
		myArray[6][7] = null;
		assertTrue(myKing.move(myArray).size() == 1);
		assertTrue(myKing.move(myArray).contains(new Coordinate(5, 7)));
	}
	
	@Test
	public void testKingLongCastleAfterKingMoved() {
		JLabel status = new JLabel("White to Move");
		Board myBoard = new Board(true, status);
		myBoard.resetStd();
		Piece[][] myArray = new Piece[8][8];
		myArray = myBoard.boardArray.clone();
		King myKing = (King) myArray[4][7];
		myKing.hasMoved = true;
		myArray[3][7] = null;
		myArray[2][7] = null;
		myArray[1][7] = null;
		assertTrue(myKing.move(myArray).size() == 1);
		assertTrue(myKing.move(myArray).contains(new Coordinate(3, 7)));
	}
	
	@Test
	public void testKingShortCastleAfterRookMoved() {
		JLabel status = new JLabel("White to Move");
		Board myBoard = new Board(true, status);
		myBoard.resetStd();
		Piece[][] myArray = new Piece[8][8];
		myArray = myBoard.boardArray.clone();
		King myKing = (King) myArray[4][7];
		((Rook) myArray[7][7]).hasMoved = true;
		myArray[5][7] = null;
		myArray[6][7] = null;
		assertTrue(myKing.move(myArray).size() == 1);
		assertTrue(myKing.move(myArray).contains(new Coordinate(5, 7)));
	}

	@Test
	public void testKingLongCastleAfterRookMoved() {
		JLabel status = new JLabel("White to Move");
		Board myBoard = new Board(true, status);
		myBoard.resetStd();
		Piece[][] myArray = new Piece[8][8];
		myArray = myBoard.boardArray.clone();
		King myKing = (King) myArray[4][7];
		((Rook) myArray[0][7]).hasMoved = true;
		myArray[3][7] = null;
		myArray[2][7] = null;
		myArray[1][7] = null;
		assertTrue(myKing.move(myArray).size() == 1);
		assertTrue(myKing.move(myArray).contains(new Coordinate(3, 7)));
	}
	
	@Test
	public void testPawnBlocked() {
		JLabel status = new JLabel("White to Move");
		Board myBoard = new Board(true, status);
		myBoard.resetStd();
		Piece[][] myArray = new Piece[8][8];
		myArray = myBoard.boardArray.clone();
		assertTrue(myArray[4][6] instanceof Pawn);
		myArray[4][5] = new Pawn(false, 4, 5);
		assertTrue(myArray[4][6].move(myArray).size() == 0);
	}
	
	/** Basic Capturing Pieces Test */
	@Test
	public void testPawnCaptureOne() {
		JLabel status = new JLabel("White to Move");
		Board myBoard = new Board(true, status);
		myBoard.resetStd();
		Piece[][] myArray = new Piece[8][8];
		myArray = myBoard.boardArray.clone();
		assertTrue(myArray[4][6] instanceof Pawn);
		myArray[5][5] = new Pawn(false, 5, 5);
		assertTrue(myArray[4][6].move(myArray).size() == 3);
		assertTrue(myArray[4][6].move(myArray).contains(new Coordinate(5, 5)));
		assertTrue(myArray[4][6].move(myArray).contains(new Coordinate(4, 5)));
		assertTrue(myArray[4][6].move(myArray).contains(new Coordinate(4, 4)));
	}
	
	@Test
	public void testPawnCaptureTwo() {
		JLabel status = new JLabel("White to Move");
		Board myBoard = new Board(true, status);
		myBoard.resetStd();
		Piece[][] myArray = new Piece[8][8];
		myArray = myBoard.boardArray.clone();
		assertTrue(myArray[4][6] instanceof Pawn);
		myArray[5][5] = new Pawn(false, 5, 5);
		myArray[3][5] = new Pawn(false, 3, 5);
		assertTrue(myArray[4][6].move(myArray).size() == 4);
		assertTrue(myArray[4][6].move(myArray).contains(new Coordinate(3, 5)));
		assertTrue(myArray[4][6].move(myArray).contains(new Coordinate(5, 5)));
		assertTrue(myArray[4][6].move(myArray).contains(new Coordinate(4, 5)));
		assertTrue(myArray[4][6].move(myArray).contains(new Coordinate(4, 4)));
	}
	
	@Test
	public void testKnightCaptureOne() {
		JLabel status = new JLabel("White to Move");
		Board myBoard = new Board(true, status);
		myBoard.resetStd();
		Piece[][] myArray = new Piece[8][8];
		myArray = myBoard.boardArray.clone();
		myArray[0][5] = new Rook(false, 0, 5);
		assertTrue(myArray[1][7] instanceof Knight);
		assertTrue(myArray[1][7].move(myArray).size() == 2);
		assertTrue(myArray[1][7].move(myArray).contains(new Coordinate(0, 5)));
		assertTrue(myArray[1][7].move(myArray).contains(new Coordinate(2, 5)));
	}
	
	@Test
	public void testKnightCaptureTwo() {
		JLabel status = new JLabel("White to Move");
		Board myBoard = new Board(true, status);
		myBoard.resetStd();
		Piece[][] myArray = new Piece[8][8];
		myArray = myBoard.boardArray.clone();
		myArray[0][5] = new Rook(false, 0, 5);
		myArray[0][5] = new Rook(false, 2, 5);
		assertTrue(myArray[1][7] instanceof Knight);
		assertTrue(myArray[1][7].move(myArray).size() == 2);
		assertTrue(myArray[1][7].move(myArray).contains(new Coordinate(0, 5)));
		assertTrue(myArray[1][7].move(myArray).contains(new Coordinate(2, 5)));
	}
	
	@Test
	public void testBishopCapture() {
		JLabel status = new JLabel("White to Move");
		Board myBoard = new Board(true, status);
		myBoard.resetStd();
		Piece[][] myArray = new Piece[8][8];
		myArray = myBoard.boardArray.clone();
		myArray[3][6] = null; //removing queen pawn
		myArray[5][4] = new Rook(false, 4, 4);
		assertTrue(myArray[2][7] instanceof Bishop);
		assertTrue(myArray[2][7].move(myArray).size() == 3);
		assertTrue(myArray[2][7].move(myArray).contains(new Coordinate(5, 4)));
		assertFalse(myArray[2][7].move(myArray).contains(new Coordinate(6, 3)));
	}
	
	@Test
	public void testRookCapture() {
		JLabel status = new JLabel("White to Move");
		Board myBoard = new Board(true, status);
		myBoard.resetStd();
		Piece[][] myArray = new Piece[8][8];
		myArray = myBoard.boardArray.clone();
		myArray[0][6] = null; //removing a-pawn
		myArray[0][4] = new Rook(false, 0, 4);
		
		assertTrue(myArray[0][7] instanceof Rook);
		assertTrue(myArray[0][7].move(myArray).size() == 3);
		assertTrue(myArray[0][7].move(myArray).contains(new Coordinate(0, 4)));
		assertFalse(myArray[0][7].move(myArray).contains(new Coordinate(0, 3)));
	}
	
	@Test
	public void testQueenCapture() {
		Piece[][] myArray = new Piece[8][8];
		myArray[0][0] = new Queen(true, 0, 0);
		myArray[0][4] = new Rook(false, 0, 4);
		myArray[4][0] = new Rook(false, 4, 0);
		myArray[2][2] = new Rook(false, 2, 2);
		
		assertTrue(myArray[0][0] instanceof Queen);
		assertTrue(myArray[0][0].move(myArray).size() == 10);
		assertTrue(myArray[0][0].move(myArray).contains(new Coordinate(0, 4)));
		assertFalse(myArray[0][0].move(myArray).contains(new Coordinate(0, 5)));
		assertTrue(myArray[0][0].move(myArray).contains(new Coordinate(4, 0)));
		assertFalse(myArray[0][0].move(myArray).contains(new Coordinate(5, 0)));
		assertTrue(myArray[0][0].move(myArray).contains(new Coordinate(2, 2)));
		assertFalse(myArray[0][0].move(myArray).contains(new Coordinate(3, 3)));
	}
	
	@Test
	public void testKingCapture() {
		Piece[][] myArray = new Piece[8][8];
		myArray[0][0] = new King(true, 0, 0);
		myArray[0][4] = new Rook(false, 0, 1);
		myArray[4][0] = new Rook(false, 1, 0);
		myArray[2][2] = new Rook(false, 1, 1);
		
		assertTrue(myArray[0][0] instanceof King);
		assertTrue(myArray[0][0].move(myArray).size() == 3);
		assertTrue(myArray[0][0].move(myArray).contains(new Coordinate(0, 1)));
		assertTrue(myArray[0][0].move(myArray).contains(new Coordinate(1, 0)));
		assertTrue(myArray[0][0].move(myArray).contains(new Coordinate(1, 1)));
	}
	
	/** Pin Test */
	@Test
	public void testKnightPin() {
		JLabel status = new JLabel("White to Move");
		Board myBoard = new Board(true, status);
		//myBoard.resetStd();
		myBoard.boardArray[1][4] = new Bishop(false, 1, 4);
		myBoard.boardArray[2][5] = new Knight(true, 2, 5);
		myBoard.boardArray[3][6] = null;
		myBoard.boardArray[4][7] = new King(true, 4, 7);
		Piece[][] myArray = new Piece[8][8];
		myArray = myBoard.boardArray.clone();
		assertTrue(myArray[2][5] instanceof Knight);
		assertTrue(myBoard.legalMoves(myArray[2][5]).size() == 0);
	}
	
	@Test
	public void testQueenPin() {
		JLabel status = new JLabel("White to Move");
		Board myBoard = new Board(true, status);
		myBoard.boardArray[1][4] = new Bishop(false, 1, 4);
		myBoard.boardArray[2][5] = new Queen(true, 2, 5);
		myBoard.boardArray[3][6] = null;
		myBoard.boardArray[4][7] = new King(true, 4, 7);
		Piece[][] myArray = new Piece[8][8];
		myArray = myBoard.boardArray.clone();
		assertTrue(myArray[2][5] instanceof Queen);
		assertFalse(myBoard.legalMoves(myArray[2][5]).size() == 0);
	}
	
	@Test
	public void testRookPin() {
		JLabel status = new JLabel("White to Move");
		Board myBoard = new Board(true, status);
		myBoard.boardArray[1][4] = new Bishop(false, 1, 4);
		myBoard.boardArray[2][5] = new Rook(true, 2, 5);
		myBoard.boardArray[3][6] = null;
		myBoard.boardArray[4][7] = new King(true, 4, 7);
		Piece[][] myArray = new Piece[8][8];
		myArray = myBoard.boardArray.clone();
		assertTrue(myArray[2][5] instanceof Rook);
		assertTrue(myBoard.legalMoves(myArray[2][5]).size() == 0);
	}
	
	/** Outcome Test */
	@Test
	public void testStalemate() {
		JLabel status = new JLabel("Black to Move");
		Board myBoard = new Board(true, status);
		Piece[][] myArray = new Piece[8][8];
		King blackKing = new King(false, 0, 0);
		blackKing.hasMoved = true;
		myArray[0][0] = blackKing;
		King whiteKing = new King(true, 0, 2);
		whiteKing.hasMoved = true;
		myArray[0][2] = whiteKing;
		Pawn whitePawn = new Pawn(true, 0, 1);
		whitePawn.isFirstMove = false;
		myArray[0][1] = whitePawn;
		myBoard.move = false;
		myBoard.pieceSelected = blackKing;
		assertFalse(myBoard.checkForStalemate(myArray));
	}
	
	@Test
	public void testNotStalemate() {
		JLabel status = new JLabel("White to Move");
		Board myBoard = new Board(true, status);
		Piece[][] myArray = new Piece[8][8];
		King blackKing = new King(false, 0, 0);
		blackKing.hasMoved = true;
		myArray[0][0] = blackKing;
		King whiteKing = new King(true, 0, 2);
		whiteKing.hasMoved = true;
		myArray[0][2] = whiteKing;
		Pawn whitePawn = new Pawn(true, 0, 1);
		whitePawn.isFirstMove = false;
		myArray[0][1] = whitePawn;
		myBoard.move = false;
		myBoard.pieceSelected = whiteKing;
		assertFalse(myBoard.checkForStalemate(myArray));
	}
	
	@Test
	public void testCheckmate() {
		JLabel status = new JLabel("White to Move");
		Board myBoard = new Board(true, status);
		Piece[][] myArray = new Piece[8][8];
		King blackKing = new King(false, 0, 0);
		blackKing.hasMoved = true;
		myArray[0][0] = blackKing;
		King whiteKing = new King(true, 0, 2);
		whiteKing.hasMoved = true;
		myArray[0][2] = whiteKing;
		Queen whiteQueen = new Queen(true, 0, 1);
		myArray[0][1] = whiteQueen;
		myBoard.move = false;
		myBoard.pieceSelected = whiteQueen;
		assertTrue(myBoard.checkForCheck(myArray));
	}
	
	@Test
	public void testCheck() {
		JLabel status = new JLabel("White to Move");
		Board myBoard = new Board(true, status);
		Piece[][] myArray = new Piece[8][8];
		King blackKing = new King(false, 0, 0);
		blackKing.hasMoved = true;
		myArray[0][0] = blackKing;
		King whiteKing = new King(true, 0, 2);
		whiteKing.hasMoved = true;
		myArray[0][2] = whiteKing;
		Rook whiteRook = new Rook(true, 0, 1);
		myArray[0][1] = whiteRook;
		myBoard.move = false;
		myBoard.pieceSelected = whiteRook;
		assertTrue(myBoard.checkForCheck(myArray));
	}
	
	@Test
	public void testNoCheck() {
		JLabel status = new JLabel("White to Move");
		Board myBoard = new Board(true, status);
		Piece[][] myArray = new Piece[8][8];
		King blackKing = new King(false, 0, 0);
		blackKing.hasMoved = true;
		myArray[0][0] = blackKing;
		King whiteKing = new King(true, 0, 2);
		whiteKing.hasMoved = true;
		myArray[0][2] = whiteKing;
		Knight whiteKnight = new Knight(true, 0, 1);
		myArray[0][1] = whiteKnight;
		myBoard.move = false;
		myBoard.pieceSelected = whiteKnight;
		assertFalse(myBoard.checkForCheck(myArray));
	}
}
