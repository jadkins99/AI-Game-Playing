package ProjectOneEngine;

public class BasicEvaluator implements MoveEvaluator {
    public float evaluateMove(GameState state){
        PlayerID curPlayer = state.getCurPlayer();
        float val = 0;

        if (curPlayer == PlayerID.TOP) {
            if (state.isGameOver()) {
                if (state.top_home > state.bot_home) val = Integer.MAX_VALUE;
                else if (state.top_home == state.bot_home) val = 0;
                else val = Integer.MIN_VALUE;
            }
            else {
                val = state.top_home - state.bot_home;
            }
        }
        else {
            if (state.isGameOver()) {
                if (state.bot_home > state.top_home) val = Integer.MAX_VALUE;
                else if (state.top_home == state.bot_home) val = 0;
                else val = Integer.MIN_VALUE;
            }
            else {
                val = state.bot_home - state.top_home;
            }
        }

        return (float)val;
    }
}