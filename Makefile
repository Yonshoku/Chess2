all: compile run

compile: src/chessgui/Board.java src/chessgui/ChessGUI.java src/chessgui/ChessFrame.java src/pieces/Rook.java 
	javac -d bin src/pieces/*.java src/chessgui/*.java

run: src/chessgui/ChessGUI.java
	java -cp bin chessgui.ChessGUI

