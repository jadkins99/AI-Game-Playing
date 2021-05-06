package DecentPlayers;
  import java.util.*; 
import java.util.Random;
import ProjectThreeEngine.*;

public class WallAvoidingPlayer implements Player{
    int my_num;

    //This function is called when the game starts
    public void begin(GameState init_state, int play_num){
	my_num = play_num;
    }

    //You can return null to just keep going straight
    public DirType getMove(GameState state){	
	Random rand = new Random();
	DirType cur_dir = state.getSnake(my_num).getHead().getDir();
    int headX = state.getSnake(my_num).getHead().getX();
    int headY = state.getSnake(my_num).getHead().getY();
    System.out.println("X:"+headX);
    System.out.println("Y:"+headY);

	int r = rand.nextInt(2);

    ArrayList<DirType> str = new ArrayList<DirType>();
       str.add(DirType.North);
       str.add(DirType.West);
       str.add(DirType.East);
         str.add(DirType.South);
       

    Collections.shuffle(str);
    for (DirType dir: str){

        if(dir == DirType.North && state.getPiece(headX,headY-1) != null){
            continue;
        }
        if(dir == DirType.South && state.getPiece(headX,headY+1) != null){
            continue;
        }
        if(dir == DirType.East && state.getPiece(headX+1,headY) != null){
            continue;
        }
        if(dir == DirType.West && state.getPiece(headX-1,headY) != null){
            continue;
        }

        if(dir == DirType.North && headY == 0){
            continue;
        }
        if(dir == DirType.South && headY == state.max_y-1){
            continue;
        }
        if(dir == DirType.East && headX == state.max_x-1){
            continue;
        }
        if(dir == DirType.West && headX == 0){
            continue;
        }




        System.out.println(dir);
        return dir;
    }

	/*if( cur_dir == DirType.North || cur_dir == DirType.South ){
	    if( r == 0){
		return DirType.East;
	    }
	    if( r == 1){
		return DirType.West;
	    }
	    return null;
	}
	if( cur_dir == DirType.East || cur_dir == DirType.West ){
	    if( r == 0){
		return DirType.North;
	    }
	    if( r == 1){
		return DirType.South;
	    }
	    return null;
	}*/
	return null; 
    }

	

    public String getPlayName(){
	return "Wall Avoiding Player";
    }
}

 
