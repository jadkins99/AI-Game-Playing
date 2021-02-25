package ProjectOneEngine;
import java.util.Random;
import java.util.ArrayList;

public class GreedyStrategy implements MoveEvaluator{
    
    public float evaluateMove(GameState state){
    // get a representation of current board
    int[] game_board = get_board(state);
    // best_move is variable for position of current move with highest possible
    // capture
    // best_score is number of stones captured from best_move
	Random rand = new Random();
	boolean done = false;
    Move move = null;
    
    float reward;

	while ( ! done ){
    int best_move = 0;
    int best_score = 0;
    int current_score;
    PlayerID cur_player = state.getCurPlayer();
   
    // loop through possible moves (bins 1-5)
    for (int i = 0; i < 6; i++){
        // check the number of stones in ith bin
        int ith_num_stones = game_board[i];
        current_score = 0;
        // check each bin that ith bin's stones would touch and see if capture 
        // if so how many stones captured
        while (ith_num_stones > 0){
            // in this case its a capture
            // increment the current_score
            if (((i+ith_num_stones)%12 > 5) && (game_board[(i+ith_num_stones)%12] == 1||game_board[(i+ith_num_stones)%12] == 2)){
                System.out.println("checking bin "+i);
                /*try{
                Thread.sleep(1000);
                }
                catch(InterruptedException e){

                }*/
                current_score +=game_board[(i+ith_num_stones)%12]; 


                
                                }
                
                ith_num_stones--;
            }

        if (current_score > best_score){
                            best_move = i;
                            best_score = current_score;

        }
    }

    int bin;
    if (best_score > 0){
        bin = best_move;
        reward = (float)best_score; 
       }

    
    else{
        bin = rand.nextInt(6);
        reward = 0;
       }
    
    move = new Move(bin, cur_player); 
    if (GameRules.makeMove(state,move) != null){
            done = true;
        

    }
	    }
	return reward;
    }

    public String getPlayName(){

        return "A not very forward thinking greedy player.";
    }

    // function for returning an array that putting the opponents positions in
    // 0-5 & players positions in 6-11
    private int [] get_board(GameState state){
            PlayerID cur_player = state.getCurPlayer();
            PlayerID other_player;
            if (cur_player == PlayerID.BOT){
                other_player = PlayerID.TOP;
            }
            else{
                other_player = PlayerID.BOT;
            }
            //PlayerID other_player  = PlayerID.TOP;
            /*if (cur_player == PlayerID.TOP){
                other_player = Player_ID.BOT;
            }
            else{
                
                other_player = PlayerID.TOP;
            }*/
            int [] game_board = new int[122];
           for(int i = 0; i < 6; i++){
                game_board[i] = state.getStones(cur_player,i);
            
           }
            for(int i = 0; i < 6; i++){
                game_board[i+6] = state.getStones(other_player,i);
            
           }

        return game_board; 
    }
}
	
