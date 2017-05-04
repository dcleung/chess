/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

// imports necessary libraries for Java swing
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
	public int gameMode; //radio buttons for the gamemode. 0 for standard, 1 for chess 960.
	public ArrayList<Board> boardList;
	public int moveNum;
	
	public void run() {
		final boolean isWhite;
		moveNum = 0;
		boardList = new ArrayList<Board>();
		
		// NOTE : recall that the 'final' keyword notes immutability
		// even for local variables.

		// Top-level frame in which game components live
		// Be sure to change "TOP LEVEL FRAME" to the name of your game
		final JFrame frame = new JFrame("Leung Chess");
		frame.setLocation(200, 0);

		// Status panel
		final JPanel status_panel = new JPanel();
		frame.add(status_panel, BorderLayout.SOUTH);
		final JLabel status = new JLabel("White to move");
		status_panel.add(status);
		
		//Radio Button Groups
		final ButtonGroup group = new ButtonGroup();
		final JRadioButton buttonClassic = new JRadioButton("Classic Chess");
		buttonClassic.setSelected(true);
		buttonClassic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameMode = 0;
			}
		});
		final JRadioButton button960 = new JRadioButton("Random Start");
		group.add(buttonClassic);
		group.add(button960);
		button960.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameMode = 1;
			}
		});
		


		//Instructions Button
		final JButton instructions = new JButton("Instructions");
		instructions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame,
					    "<html> Welcome to Leung Chess! <br><br>"
					    + "I implemented Chess.  The rules of chess are as follows: "
					    + "Knights move in L-shape, bishops diagonally,"
					    + "<br> rooks horizontally and vertically, queen a combination of bishops and rooks"
					    + "<br> kings move one square at a time, pawns can go two spaces on first move, one o.w."
					    + "<br> capture diagonally, and promote at the last rank.  Checkmate the other king to win."
					    + "<br>" 
					    + "<br> One difference is my chess doesn't allow en passant, but allows castling through check. " 
					    + "<br>" 
					    + "<br> Click a piece to select it and then click another " 
					    + "viable square to move it."
					    + "<br>" 
					    + "<br> You can change game mode from classic to random, which randomizes positions",
					    "Instructions",
					    JOptionPane.PLAIN_MESSAGE);
			}
		});

		//Custom button text
		Object[] options = {"Black",
		                    "White"};
		int n = JOptionPane.showOptionDialog(frame,
		    "What color do you want to play?",
		    "New Game",
		    JOptionPane.YES_NO_CANCEL_OPTION,
		    JOptionPane.QUESTION_MESSAGE,
		    null,
		    options,
		    null);
		if (n == 1)
			isWhite = true;
		else
			isWhite = false;
		
		/*String s = (String)JOptionPane.showInputDialog(
                frame,
                "What is your name?",
                "Welcome to Leung Chess!",
                JOptionPane.PLAIN_MESSAGE);
		
		// Player's Info Panel
		final JPanel player_info = new JPanel();
		frame.add(player_info, BorderLayout.WEST);
		final JLabel name = new JLabel("<html>Name: " + s + " <br>Rating: " + 2800 + "</html>");
		player_info.add(name);*/ //TODO: Ask for player information and get from document.
		
		// Move list Panel
		/*final JPanel move_list = new JPanel();
		frame.add(move_list, BorderLayout.EAST);
		final JLabel moves = new JLabel("The moves were...");
		move_list.add(moves);*/
		
		//Control Panel
		final JPanel control_panel = new JPanel();

		//Game list button
		//final JPanel game_list = new JPanel();
		//game_list.add(new JLabel("Game List"));
		
		//Toolbar
		final JPanel toolbar = new JPanel();
		toolbar.setLayout(new BoxLayout(toolbar, BoxLayout.Y_AXIS));
		toolbar.add(new JLabel("  Select Next Game Mode:  "));
		toolbar.add(buttonClassic);
		toolbar.add(button960);
		
		//toolbar.add(game_list);
		
		final JPanel gameOpt = new JPanel();
		gameOpt.add(control_panel, BorderLayout.NORTH);
		gameOpt.add(instructions, BorderLayout.SOUTH);
		
		frame.add(toolbar, BorderLayout.WEST);
		frame.add(gameOpt, BorderLayout.NORTH);
		
		// Main playing area
		final Board chessBoard = new Board(isWhite, status);
		chessBoard.setFocusable(true);
		frame.add(chessBoard, BorderLayout.CENTER);
		
		//Promotion Radio Button
		final ButtonGroup promoteButtons = new ButtonGroup();
		final JRadioButton knight = new JRadioButton("Knight");
		knight.setSelected(false);
		knight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chessBoard.promote = 0;
			}
		});
		final JRadioButton bishop = new JRadioButton("Bishop");
		bishop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chessBoard.promote = 1;
			}
		});
		final JRadioButton rook = new JRadioButton("Rook");
		rook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chessBoard.promote = 2;
			}
		});
		final JRadioButton queen = new JRadioButton("Queen");
		queen.setSelected(true);
		queen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chessBoard.promote = 3;
			}
		});
		promoteButtons.add(knight);
		promoteButtons.add(bishop);
		promoteButtons.add(rook);
		promoteButtons.add(queen);
		
		final JPanel promotionToolbar = new JPanel();
		promotionToolbar.setLayout(new BoxLayout(promotionToolbar, BoxLayout.Y_AXIS));
		promotionToolbar.add(new JLabel ("  Select Next Promotion Piece:  "));
		promotionToolbar.add(knight);
		promotionToolbar.add(bishop);
		promotionToolbar.add(rook);
		promotionToolbar.add(queen);
		frame.add(promotionToolbar, BorderLayout.EAST);

		// Note here that when we add an action listener to the reset
		// button, we define it as an anonymous inner class that is
		// an instance of ActionListener with its actionPerformed()
		// method overridden. When the button is pressed,
		// actionPerformed() will be called.
		final JButton reset = new JButton("New Game");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(gameMode);
				if (gameMode == 0)
					chessBoard.resetStd();
				if (gameMode == 1)
					chessBoard.resetRandomized();
			}
		});
		control_panel.add(reset);
		
		

		// Start game
		chessBoard.resetStd();	
		
		// Put the frame on the screen
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);


	}

	/*
	 * Main method run to start and run the game Initializes the GUI elements
	 * specified in Game and runs it IMPORTANT: Do NOT delete! You MUST include
	 * this in the final submission of your game.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Game());
	}
}
