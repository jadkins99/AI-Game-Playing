package DecentPlayers;

import ProjectThreeEngine.*;

public class qlearning implements Player {

    // THIS DOESNT WORK YET
    // I'm trying to figure out how qlearning works and how best to implement it for this game

    private final double alpha = 0.1;
    private final double gamma = 0.9;

    private final int board_width = 15;
    private final int board_height = 15;
    private final int board_count = board_height * board_width;

    private final int reward = 100;
    private final int penalty = -10;

    public void begin(GameState init_state, int play_num){

    }

    public DirType getMove(GameState state) {
        return null;
    }

    public String getPlayName(){
        return "Qlearning Player";
    }

}
