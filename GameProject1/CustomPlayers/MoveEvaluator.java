package GameProject1.CustomPlayers;

import GameProject1.ProjectOneEngine.GameState;

public interface MoveEvaluator {
    public abstract float evaluateMove(GameState state);
}