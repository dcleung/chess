=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: dcleung
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2-D array. I used it to model the chess board’s state. It will be a Piece array, where Piece is
  the superclass of my subclasses, Bishop, Pawn, King, etc.  The array will be 8 by 8 to model the
  8 by 8 chess board.  Each position will either have a piece or be null (signifying that nothing is
  on that particular square).  To start, each piece will be in its respective starting position in
  the Array. The board will be updated whenever I move a piece legally. The 2-D array design
  decision is helpful because when I want to move a piece, I pass in the 2-D array and check if that
  piece can move or make any captures based on the current board state.

  2. Inheritance. I used an abstract class Piece with methods such as getColor() as well as
  abstract methods such as move().  I will have classes, Pawn, Knight, Bishop, Rook, Queen, and King
  extend the abstract class.  If I need to get the particular piece’s color, I call getColor() and
  it will use Piece.class’s method.  However, the move() method is different for different pieces
  because they have different restrictions, thus the move() method is implemented in the subclass.
  This design decision makes sense because I want my board to be represented as a Piece array.
  This array will be composed of different pieces so inheritance and subtyping here makes sense.
  Even though it would work, I don’t want all pieces to be of the Piece type and have a state in
  Piece represent a particular piece (e.g. int pieceType = 0; for pawn) because getting moves for a
  piece will require a complicated and long switch method depending on the piece. 
  This makes it hard to debug and to understand.

  3. Collections.  I used Collections when I generate "lists." Every time a piece is clicked, the
  piece's legal moves are gathered in a LinkedList and painted by repaint() so that they grow green.
  On the second click, I see if the end position is an element in the linked list.  Also in the
  randomize function, I used a set to store positions because no position should be repeated in a
  set.  If it was repeated, a new position for that piece is generated and then put on the board.

  4. JUnit Testing. I can test moves for particular pieces given a board state.  Testing is
  important in implementing chess because of all the edge cases including movement, captures, 
  castling, pins, and end state.  In my test cases, I will pass in a board that I construct myself
  and then verify the code-given moves against the actual legal moves (if there are any).
  Furthermore, I will design moving pieces so it updates the board state and I can test moving
  pieces with JUnit.  I will test that moving a piece updates the board and its Piece array.  I can
  test pins by seeing if a particular piece can move given a Piece array.  I can also set up
  stalemate and mate positions to verify that the game correctly identifies such positions.


=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
  
  Piece- the superclass that all the pieces extend.  It has methods to retrieve colors and generate
  possible moves given its position.  Pieces know where they are on the board, and this X and Y
  position is saved as a state.
  King/Queen/Rook/Bishop/Knight/Pawn- the subclasses of Pieces.  Each has a special method to
  generate moves based off of a passed in Piece array. There are a few pieces will special states.
  Pawn has a isFirstMove boolean that is used to determine if a pawn can jump 2 squares on a move.
  King and Rook have a hasMoved boolean that is used to determine if the king can castle.
  Board- the main implementation that paints all the pieces on location and has mouselisteners
  to find where the mouse has clicked.  Also translates that mouse click to a position in a Piece
  array and lights up the square.  It also checks move legality, that is a move cannot be made if 
  the king is in danger.  Checks checks and checkmates as well as stalemates, and special moves.
  Coordinate- a class with X and Y state with a compareTo function.
  Game- main method that starts the game, interacts with a few of the Board's states such as 
  promotion piece and the gamemode shift.
  MyTests- my test cases that assure that the game is running and working.


- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?
  Checkmate was difficult to implement due to my implementation.  I would check all moves for a side
  and see if they would "save" the king, that is prevent checkmate.  I had difficulty duplicating
  a board array so that I could make changes then check the new board array.  This is because arrays
  are by reference so reassignment did not work.  Instead I had to use .copy().  After I figured out
  to use .copy(), I had a few bugs regarding whose move it was, and I implemented a shift in moves
  after each move (moveUpdate()).  This took care of the bug and check/checkmates work smoothly.


- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?
  There is a good separation of functionality but there could be more as the legal moves method
  could belong in a Piece class rather than the actual Board class.  If given time, I would refactor
  the legal moves method by moving into the Piece class so that the pieces handle logic rather than
  have Board do everything.  Board also does castling and pawn double moves, which can be taken care
  by the actual Piece.  Private state encapsulation could be better as many states are left public,
  and are called directly by different classes.



========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.
  
  I used chess sprites as my images which can be found on Wikipedia:
  https://commons.wikimedia.org/wiki/Category:SVG_chess_pieces.
  
  I used the JUnit library to write my test cases.


