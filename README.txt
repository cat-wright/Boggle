BOGGLE

//General Instructions:

HOW TO USE THE PROGRAM: Run UI_Control.java
			Relevent Files:
				Coordinate.java
				BoggleBlock.java
				Board.java
				Dictionary.java
				UI_Control.java

Boggle is a word game in which the player finds as many words as possible
in a given time limit on a board of tiles each with its own letter.

The game can be played on a 4x4 or 5x5 board, by selecting one of the opening
screens buttons.

Once a new game is started, the player has 3 minutes to find as many words as
they can.  Only words in the english dictionary may be used, and words must 
have at least 3 letters in them.  No pronouns or foreign words are accepted.
Words must be formed from distinct letters - no one letter may be used in a word twice.  

There is no penalty for words rejected or cleared.

Each accepted word will increase the score by two points less than the length
of the found word.

//Buttons:

Opening screen: 4 TILES: selects a 4x4 board and goes to the main game screen
				5 TILES: selects a 5x5 board and goes to the main game screen
				Music selection: changes the music to rap 
				** WARNING: EXPLICIT LYRICS**

Main Screen: Start Game: Generates a new game, can only be clicked if a game
			   is not in progress
			 End Game:  Ends the current game, blacking out the board and 
			   stopping the clock
			 Show All Words:  Only available if a game has ended.  Goes to 
			   screen displaying all words possible on the former board
			 Play Word: Trys to play current word selected.  If valid, the 
			   word is added to the players score.  
			 Clear:  Clears current board selection

Show All Words Screen: Hide List: Returns to main screen

// Board Play:
Once a game has started any letter on the board (pink or black) is valid to play.  When clicked, the selected letter will turn purple.  Any 
surrounding letters that are not purple are valid to play (diagonals included)
Once the player is satisfied with their word choice, Play Word button
will decide if the word is valid and give the player credit.

If a player has selected letters but is unhappy with their selection or
wants to change their selection, the clear button will erase their 
current selection.  

**WARNING: This font, while fantastic and retro, has very similar looking U's and V's.  

// Known Bugs:
















