package CustomPlayers;

import GameProject1.ProjectOneEngine.GameState;
import GameProject1.ProjectOneEngine.PlayerID;

public class NewEvaluator implements MoveEvaluator {

    public float evaluateMove(GameState state){
        if (state.getCurPlayer() == PlayerID.TOP){
            System.out.println("I Think the value of this move is "+(state.top_home-state.bot_home));
            return state.top_home - state.bot_home;
        }
        else{
            System.out.println("I Think the value of this move is "+(state.bot_home-state.top_home));
            return state.bot_home - state.top_home;
        }
    }
}
