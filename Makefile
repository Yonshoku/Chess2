all: compile run

compile: src/chessgui/Board.java src/chessgui/ChessFrame.java src/pieces/Rook.java 
	javac -d bin src/pieces/*.java src/chessgui/*.java

run: src/chessgui/ChessFrame.java
	java -cp bin chessgui.ChessFrame

