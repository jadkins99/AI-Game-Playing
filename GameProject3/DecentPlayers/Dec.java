package DecentPlayers;

import ProjectThreeEngine.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Dec implements Player {

    int dec_num;
    boolean goodDir;
    DirType directions[] = DirType.values();
    GamePiece cur_piece;

    public void begin(GameState init_state, int play_num){
        dec_num = play_num;
    }

    public DirType getMove(GameState state){
        Snake dec = state.getSnake(dec_num);

    
        DirType cur_dir = state.getSnake(dec_num).head.getDir();
        int dec_x = state.getSnake(dec_num).head.getX();
        int dec_y = state.getSnake(dec_num).head.getY();

        goodDir = checkDir(state, cur_dir, dec_x, dec_y);
        
        for(DirType dirs: DirType.values()){
            goodDir = checkDir(state, dirs, dec_x, dec_y);
            if (goodDir = true){
                System.out.println(dirs);
                return dirs;
            }
            else if(goodDir = false){
                System.out.println("this sucks");
            } 
        }

        return null;
    }

    // checks if direction is safe
    private boolean checkDir (GameState state, DirType cur_dir, int x, int y){
        if (cur_dir == DirType.North){
            cur_piece = state.getPiece(x, y-1);
            if (y == 0 || y == 1){
                return false;
            }
            else {
                System.out.println("north: x = " + x + ", y =" + y);
                return false;
            }
        }

        else if (cur_dir == DirType.East){
            cur_piece = state.getPiece(x+1, y);
            if (cur_piece == null && x != 15){
                System.out.println("east: x = " + x + ", y =" + y);
                return true;
            }
            else {
                return false;
            }
        }

        else if (cur_dir == DirType.South){
            cur_piece = state.getPiece(x, y+1);
            if (cur_piece == null && y != 15){
                System.out.println("south: x = " + x + ", y =" + y);
                return true;
            }
            else {
                return false;
            }
        }

        else if (cur_dir == DirType.West){
            cur_piece = state.getPiece(x-1, y);
            if (cur_piece == null && x != 1){
                System.out.println("west: x = " + x + ", y =" + y);
                return true;
            }
            else {
                return false;
            }
        }

        return false;
    }

    public String getPlayName(){
        return "Dec";
    }
}
