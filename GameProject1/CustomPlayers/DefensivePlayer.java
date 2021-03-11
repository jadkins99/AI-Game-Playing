package GameProject1.CustomPlayers;

import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;

import GameProject1.ProjectOneEngine.GameRules;
import GameProject1.ProjectOneEngine.GameState;
import GameProject1.ProjectOneEngine.Player;
import GameProject1.ProjectOneEngine.PlayerID;
import GameProject1.ProjectOneEngine.Move;

class DefensivePlayer implements Player {

    public Move getMove(GameState state) {

        Integer bin_num;
        Integer bin;

        Random rand = new Random();
        boolean done = false;

        // two lists : one for number of stones in each bin and one for capturable bins
        ArrayList<Integer> bin_count = new ArrayList<Integer>();
        ArrayList<Integer> cap_bins = new ArrayList<Integer>();

        // get current player and make an instance of Move
        PlayerID cur_player = state.getCurPlayer();
        Move def_move = null;
        int def_mo = 0;

        while (!done) {

            // current players bins
            for (int i = 0; i < 6; i++) {
                // gets number of stones from each bin
                int stones;
                stones = state.getStones(cur_player, i);
                bin_count.add(stones);
            }

            for (int i = 0; i < bin_count.size(); i++) {
                bin_num = bin_count.get(i);
                if (bin_num < 3 && bin_num > 0) {
                    // save to list if bin has one or two stones
                    cap_bins.add(i);
                }
            }

            // AAAAAAAA
            // this is the worst AI actually i hate everything about this
            // this is still in progress aaaa
            if (bin_count.get(0) > 0){
                // if first bin has stones, move that one
                def_mo = 0;
            }
            else if (bin_count.get(5) > 2){
                def_mo = 5;
            }
            else {
                if (cap_bins.size() == 0){
                    // if no bins have capturable stones, pick the largest bin to move
                    int large_bin = bin_count.indexOf(Collections.max(bin_count));
                    def_mo = large_bin;
                }
                else {
                    // else get the capturable bin closest to the enemy players move
                    def_mo = cap_bins.get(0);
                }
            }

            // sets move as Move
            def_move = new Move(def_mo, cur_player);

            if (GameRules.makeMove(state, def_move) != null) {
                done = true;
            }
        }

        // returns the move to the game
        return def_move;
    }

    public String getPlayName() {
        return "Defensive Player";
    }

}