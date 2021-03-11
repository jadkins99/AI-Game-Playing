# Custom Players Package
DefensivePlayer.java : An AI class which doesn't have look-ahead. It prioritizes moves that prevent its pieces from being captured within the current game state, without regard for whether or not its a good move in the long-run.

DefensiveCheck.java : An AI class for the ThinkAheadPlayer. It takes in a gamestate and looks for whether or not pieces can be captured in that gamestate. Good gamestates are ones that cannot be captured.

SmartPlayer.java : A basic AI class without lookahead. It doesn't have a good grasp on good moves, but its better than random selection! 

KillerRobot.java : A greedy player that does not look ahead. If a capture is
available, it will pick the move with the most opponent pieces captured. Else,
it will pick randomly.

GreedyStrategy.java : A greedy state evaluator for use in the ThinkAheadPlayer. 

NewEvaluator.java : A state evaluator for use in the ThinkAheadPlayer that
returns the number of pieces captured by the player minus the number of pieces
captured by the opponent.
