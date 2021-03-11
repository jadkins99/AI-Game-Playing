package GameProject1.CustomPlayers;

import java.util.ArrayList;
import java.util.Collections;

import GameProject1.ProjectOneEngine.GameRules;
import GameProject1.ProjectOneEngine.GameState;
import GameProject1.ProjectOneEngine.Player;
import GameProject1.ProjectOneEngine.PlayerID;
import GameProject1.ProjectOneEngine.Move;

class SmartPlayer implements Player {

    public Move getMove(GameState state){

        boolean done = false;
        ArrayList<Integer> bin_count = new ArrayList<Integer>();

        // get current player and make an instance of Move
        PlayerID cur_player = state.getCurPlayer();
        Move smart_move = null;

        // code that'll make the move.
        // favor bins with more stones on your side
        while (! done){
            for (int i=0; i < 6; i ++){
                // gets number of stones from each bin
                int stones;
                stones = state.getStones(cur_player, i);
                bin_count.add(stones);
            }

            // finds largest bin and makes that move
            // add code that makes it check for bins with 2/3 stones before going for largest
            int small_bin = bin_count.indexOf(Collections.max(bin_count));
            smart_move = new Move(small_bin, cur_player);

            if (GameRules.makeMove(state, smart_move) != null){
                done = true;
            }
        }

        // returns the move to the game
        return smart_move;
    }

    public String getPlayName(){
        return "Allegedly Smart Player";
    }

}