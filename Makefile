all: compile run

compile: 
	javac -d bin src/pieces/*.java src/chessgui/*.java 

run: src/chessgui/ChessFrame.java
	java -cp bin chessgui.ChessFrame  

