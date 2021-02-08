package ProjectOneEngine;

class Move {
    int bin;
    PlayerID play;

    public int getBin(){
	return bin;
    }

    public PlayerID getPlayer(){
	return play;
    }

    public Move (int bin_num, PlayerID play_id){
	bin = bin_num;
	play = play_id;
    }
}
