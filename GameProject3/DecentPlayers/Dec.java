package DecentPlayers;

import ProjectThreeEngine.*;

import java.util.*; 
import java.util.Random;


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

    
        DirType cur_dir = dec.getHead().getDir();
        int dec_x = dec.getHead().getX();
        int dec_y = dec.getHead().getY();

        goodDir = checkDir(state, cur_dir, dec_x, dec_y);
        
        ArrayList<DirType> all_dirs = new ArrayList<DirType>();
            all_dirs.add(DirType.North);
            all_dirs.add(DirType.West);
            all_dirs.add(DirType.East);
            all_dirs.add(DirType.South);
    
        Collections.shuffle(all_dirs);

        for(DirType dirs: all_dirs){
            goodDir = checkDir(state, dirs, dec_x, dec_y);
            if (goodDir == true){
                System.out.println(dirs);

                return dirs;
            }
            else if(goodDir == false){
                System.out.println("Not safe");
            } 
        }

        return null;
    }

    // checks if direction is safe
    private boolean checkDir (GameState state, DirType cur_dir, int x, int y){
        if (cur_dir == DirType.North){
            cur_piece = state.getPiece(x, y-1);
            if (cur_piece == null && y != 0){
                return true;
            }
            else {
                if (state.isFood(x, y-1) == true){
                    return true;
                }
                else {
                    return false;
                }
            }
        }

        else if (cur_dir == DirType.East){
            cur_piece = state.getPiece(x+1, y);
            if (cur_piece == null && x != state.max_x-1){
                return true;
            }
            else {
                if (state.isFood(x+1, y) == true){
                    return true;
                }
                else {
                    return false;
                }
            }
        }

        else if (cur_dir == DirType.South){
            cur_piece = state.getPiece(x, y+1);
            if (cur_piece == null && y != state.max_y-1){
                return true;
            }
            else {
                if (state.isFood(x, y+1) == true){
                    return true;
                }
                else {
                    return false;
                }
            }
        }

        else if (cur_dir == DirType.West){
            cur_piece = state.getPiece(x-1, y);
            if (cur_piece == null && x != 0){
                return true;
            }
            else {
                if (state.isFood(x-1, y) == true){
                    return true;
                }
                else {
                    return false;
                }
            }
        }

        return false;
    }

    public String getPlayName(){
        return "Dec";
    }
}
