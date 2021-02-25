package ProjectOneEngine;

import java.util.ArrayList;

class DefensiveCheck implements MoveEvalulator {

    // checks for rank for specified move
    // move = good when opponent can't make a capture
    public float evaluateMove(GameState state) {
        float ranking;
        Integer bin_num;

        // two lists : one for number of stones in each bin and one for capturable bins
        ArrayList<Integer> bin_count = new ArrayList<Integer>();
        ArrayList<Integer> cap_bins = new ArrayList<Integer>();

        PlayerID cur_player = state.getCurPlayer();

        for (int i=0; i < 6; i ++){
            // gets number of stones from each bin
            int stones;
            stones = state.getStones(cur_player, i);
            bin_count.add(stones);
        }

        for(int i=0; i < bin_count.size() ; i++){
            bin_num = bin_count.get(i);
            if(i < 3 && i > 0){
                // save to list if bin has one or two stones
                cap_bins.add(i);
            }
        }

        // something something move is better if the first --> second --> third bins cant be captured afterwards
        // i'll add this check soon

        ranking = 0.0; // all moves suck for NOW lmao
        return ranking;
    }

}