package DefensivePlayers;

import java.util.ArrayList;

import GameProject1.ProjectOneEngine.GameState;
import GameProject1.ProjectOneEngine.MoveEvaluator;
import GameProject1.ProjectOneEngine.PlayerID;

class DefensiveCheck implements MoveEvaluator {

    // checks for rank for specified move
    // move = good when opponent can't make a capture
    public float evaluateMove(GameState state) {
        // state has a default rank of one
        float ranking = (float) 1.0;
        Integer bin_num;
        Integer bin;

        // two lists : one for number of stones in each bin and one for capturable bins
        ArrayList<Integer> bin_count = new ArrayList<Integer>();
        ArrayList<Integer> cap_bins = new ArrayList<Integer>();

        // arraylist for opponents bins
        ArrayList<Integer> opp_bins = new ArrayList<Integer>();

        PlayerID cur_player = state.getCurPlayer();
        if (cur_player == PlayerID.TOP){
            PlayerID opp_player = PlayerID.BOT;
        }
        else if (cur_player == PlayerID.BOT){
            PlayerID opp_player = PlayerID.TOP;
        }

        // get opponents side of the board / their bins, stores them in an array
        for (int i=0; i < 6; i ++){
            int opp_stones;
            opp_stones = state.getStones(opp_player, i);
            opp_bins.add(opp_stones);
        }
        // checks the nearest three spaces of the board, if its one or two stones then good moves are weighted to
        // first n second spaces, otherwise weight higher to "safer" moves

        // code to check bin amounts here

        // current players bins
        for (int i=0; i < 6; i ++){
            // gets number of stones from each bin
            int stones;
            stones = state.getStones(cur_player, i);
            bin_count.add(stones);
        }

        for(int i=0; i < bin_count.size() ; i++){
            bin_num = bin_count.get(i);
            if(bin_num < 3 && bin_num > 0){
                // save to list if bin has one or two stones -- "capturable"
                cap_bins.add(i);
            }
        }

        // if none of the bins can be captured then good state
        if (cap_bins.size() == 0){
            ranking = ranking + (float)3.0; // some rank thats like "yea okay idk what to do w this nothing can be captured"
        }
        else{
            // something something board is better if the first --> second --> third bins cant be captured afterwards
            // favor moves that mean [x] can't be captured
            for(int i=0; i < cap_bins.size(); i ++){
                // AAAAAAAAA
                bin_num = bin_count.get(cap_bins.get(i));
                if(bin_num < 3 && bin_num > 0){
                    ranking = ranking - (float)1.0;
                }
            }
        }

        // returns its ranking of that game state :
        // higher rank : no / few capturable bins
        // lower rank : bins are at risk of capture
        return ranking;
    }

}