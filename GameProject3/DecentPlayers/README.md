# Project Three

## Dec.java
AI which avoids walls and the other snake, and collects food when it comes across it.

## WallAvoidingPlayer.java
WallAvoidingPlayer is a rule based player that chooses random actions that
satisfy the conditions of avoiding walls, and all non-null
squares. 


## QPlayer.java
 QPlayer uses tabular q learning to play snake game.
 The state space is defined based off the 4 squares around the snakes head and
 the number of food pieces that the snake has ate.
 Each square can have 4 states: empty, food, snakepiece or wall.
data.txt is a file that contains the snakes current q matrix for persistant
storage across multiple games.


