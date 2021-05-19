package DecentPlayers;
import java.util.Random;
import java.util.HashMap;
import ProjectThreeEngine.*;
import java.lang.Math;
import java.util.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;


public class QPlayer implements Player{
    int my_num;

    int state_rows_num;

    float reward;
    
    HashMap<Integer,DirType> int_to_dir = new HashMap<Integer,DirType>();

    
    int num_actions;

     QMatrix q_matrix;

    //This function is called when the game starts
    public void begin(GameState init_state, int play_num){


    int_to_dir.put(0,DirType.North);
    int_to_dir.put(1,DirType.South);
    int_to_dir.put(2,DirType.East);
    int_to_dir.put(3,DirType.West);




	my_num = play_num;
    state_rows_num = (int)(3*Math.pow(4,0) + 3*Math.pow(4,1) + 3*Math.pow(4,2) + 3*Math.pow(4,3) + 6*Math.pow(4,4)); // 4 states per neighbor square, 6 for possible numbers of peices eaten 0-5

    num_actions = 4;


    float alpha = (float)0.05;
    float gamma = (float)0.1;
    String file_path = "/home/jadkins/AI-Game-Playing/GameProject3/DecentPlayers/data";
    q_matrix = new QMatrix(alpha,gamma,state_rows_num,num_actions,file_path);



    }

    //You can return null to just keep going straight
    public DirType getMove(GameState state){	
	Random rand = new Random();

    //System.out.println("X:"+headX);
    //System.out.println("Y:"+headY);


    int myState = getState(state);
    int action;
  

    if(explore((float)0.2)) {
        action = rand.nextInt(4);
        
    }


    else{

        action = getArrayMaxIndex(q_matrix.matrix[myState]);

    }


    // check if game ends when we make this move
    // if so we probably died, so give big negative reward
    // else we probably survived so give small positive reward
    List<Move> moves = new ArrayList<Move>();

    moves.add(new Move(my_num,int_to_dir.get(action)));

    GameState newState = GameRules.makeMoves(state, moves);

    if(newState.isGameOver()) reward = -50;
    
    else reward = 5;


    q_matrix.updateMatrix(reward,myState,action);


    return int_to_dir.get(action);



    


    }


    private int getState(GameState state){

        int headX = state.getSnake(my_num).getHead().getX();
        int headY = state.getSnake(my_num).getHead().getY();

        int left_neighbor = -1;
        int right_neighbor = -1;
        int top_neighbor = -1;
        int bottom_neighbor = -1;
        int my_pieces_eaten = -1;
      

        GamePiece piece;

       // figure out left neighbor
        piece  = state.getPiece(headX-1,headY);


        if(piece == null) left_neighbor = 0;

        else if (piece instanceof FoodPiece) left_neighbor = 1;

        else if(piece instanceof SnakePiece) left_neighbor = 2;

        if (headX - 1 < 0) left_neighbor = 3;

        // figure out right neighbor
        piece  = state.getPiece(headX+1,headY);


        if(piece == null) right_neighbor = 0;

        else if (piece instanceof FoodPiece)  right_neighbor = 1;

        else if(piece instanceof SnakePiece) right_neighbor = 2;

        if (headX+1 >= state.max_x) right_neighbor = 3;


        // figure out bottom neighbor

        piece  = state.getPiece(headX,headY+1);


        if(piece == null) bottom_neighbor = 0;

        else if (piece instanceof FoodPiece) bottom_neighbor = 1;

        else if(piece instanceof SnakePiece) bottom_neighbor = 2;

        if (headY+1 >= state.max_y) bottom_neighbor = 3;



        // figure out top neighbor
        piece  = state.getPiece(headX,headY-1);


        if(piece == null) top_neighbor = 0;

        else if (piece instanceof FoodPiece) top_neighbor = 1;

        else if(piece instanceof SnakePiece) top_neighbor = 2;

        if (headY-1 < 0) top_neighbor = 3;


        // figure out my pieces eaten 

        Snake mySnake = state.getSnake(my_num);

        // snake length minus initial starting length
        my_pieces_eaten = Math.max(mySnake.getBody().size() - 4,0);




        
        int my_state = (int)(left_neighbor*Math.pow(4,0) + right_neighbor*Math.pow(4,1) + bottom_neighbor*Math.pow(4,2) + top_neighbor*Math.pow(4,3) + my_pieces_eaten*Math.pow(4,4));


        System.out.println("left_neighbor"+ new Integer(left_neighbor).toString());
        System.out.println("right_neighbor"+ new Integer(right_neighbor).toString());
        System.out.println("bottom_neighbor"+ new Integer(bottom_neighbor).toString());
        System.out.println("top_neighbor"+ new Integer(top_neighbor).toString());
        System.out.println("myState"+ new Integer(my_state).toString());
        System.out.println("my_pieces_eaten"+ new Integer(my_pieces_eaten).toString());
        

        return my_state;

    }


    private int getArrayMaxIndex(float[] arr){

        float max = arr[0];
        int index=  0;

        for(int i = 0; i < arr.length; i++){
            if(max < arr[i]){
                max = arr[i];
                index = i;
            }
        }
        return index;


    }


    private boolean explore(float p){

        Random rand = new Random();

        if (rand.nextFloat() < p) return true;

        else return false;

        // returns whether or not the agent should explore or exploit

    }



	

    public String getPlayName(){
	return "Q Player";
    }
}

 class QMatrix{

    float alpha;
    float gamma;

    int num_game_state_rows;
    int num_actions;
    String fileName;
    float[][] matrix;


    public QMatrix(float alpha, float gamma, int num_game_state_rows, int num_actions,String fileName){

        this.alpha = alpha;
        this.gamma = gamma;
        this.fileName = fileName;
        this.matrix = new float[num_game_state_rows][num_actions];

      
        System.out.println(Files.exists(Paths.get(fileName+".txt")));
        
       if(Files.exists(Paths.get(fileName))) { 
        System.out.println("getting from file!");
        this.matrix = Array_As_File.from_file(this.fileName,num_game_state_rows,num_actions);
   
            }



    }


    void updateMatrix(float reward, int state, int action){
        float newQ = (1-this.alpha)*this.matrix[state][action] + this.alpha*(reward + this.gamma*this.matrix[state][action]);

        this.matrix[state][action] = newQ;

        System.out.println(this.matrix[0].length);
        
        Array_As_File.to_file(this.matrix,this.fileName);

    }







}

 
