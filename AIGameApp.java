package ProjectOneEngine;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javafx.util.Duration;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.Scanner;

public class AIGameApp extends Application{
    Player TOP_Player;
    Player BOT_Player;
    
    Canvas test_canvas;
    Stage primary;
    GameState state;
    final double DELAY_TIME = 1.0;

    public void start(Stage primaryStage){
	//IMPORTANT : Change these lines to change who is playing!
	TOP_Player = new RandomPlayer();
	BOT_Player = null;  // null means "Human Player"
	
        primary = primaryStage;
        primaryStage.setTitle("A Test");
        Group root = new Group();
        test_canvas = new Canvas(1200, 800);
 
        root.getChildren().add(test_canvas);
        Scene mainScene;
        mainScene = new Scene(root, 1200, 800, Color.BEIGE);

	//This is Badly Coupled with the Graphics Code!! <sorry>
	// ... Really Ugly <sigh>
	//*********************************
	mainScene.setOnMouseClicked(new EventHandler<MouseEvent>() {
		public void handle(MouseEvent event) {
		    if((state.isTopTurn() && TOP_Player == null) ||
		       (!state.isTopTurn() && BOT_Player == null)){
			
			double x = event.getX();
			double y = event.getY();

			int  mar = GameDisplayGraphics.MARGIN;
			int sizeY = GameDisplayGraphics.SIZE_TALL;
			int sizeX = GameDisplayGraphics.SIZE_WIDE;

			double boxX = (sizeX-2*mar)/6;
			double boxY = (sizeY-2*mar)/6;

			int bin_num_bot = (int) ((x - mar)/boxX);
		    
			if (state.isTopTurn()){
			    if ( (y > mar + boxY) && (y < mar + 3*boxY) ){
				if((bin_num_bot >= 0) && (bin_num_bot < 6)){
				    humanTurn(5 - bin_num_bot);
				}
			    }
			}
			else if (!state.isTopTurn()){
			    if ( (y > mar + 3*boxY) && (y < mar + 5*boxY) ){
				if((bin_num_bot >= 0) && (bin_num_bot < 6)){
				    humanTurn(bin_num_bot);
				}
			    }
			}
		    }
		}
	    });
	//**************************************
		  
        primaryStage.setScene(mainScene);
        primaryStage.show();

	Timeline quickTimer = new Timeline(new KeyFrame(Duration.seconds(DELAY_TIME), new EventHandler<ActionEvent>() {
		
		public void handle(ActionEvent event) {
		    nextAITurn();
		}
	    }));
	
	quickTimer.setCycleCount(Timeline.INDEFINITE);
	quickTimer.play();		
      
	state = new GameState();
	GameDisplayGraphics.displayState(test_canvas, state);

    }


    void nextAITurn(){
	if (! GameRules.isGameOver(state)){
	    if(( state.isTopTurn() && TOP_Player != null) || ( !state.isTopTurn() && BOT_Player !=null)){
		PlayerID cur_player = state.getCurPlayer();
		GameState copy_state = new GameState(state);
		Move nextMove = null;
	    
		if (cur_player == PlayerID.TOP){
		    nextMove = TOP_Player.getMove(copy_state);
		}
		else{
		    nextMove = BOT_Player.getMove(copy_state);
		}
		
		if ((nextMove == null) || (GameRules.makeMove(state,nextMove) == null)){
		    if(cur_player == PlayerID.TOP){
			System.out.println("ILLEGAL MOVE TOP CONCEDES!!");
			state = GameState.concedeState(cur_player);
		    }
		    else{
			System.out.println("ILLEGAL MOVE BOTTOM CONCEDES!!");
			state = GameState.concedeState(cur_player);
		    }
		}
		
		int bin_num = nextMove.getBin();
		
		System.out.println(bin_num + 1);
		state = GameRules.makeMove(state, bin_num);
		GameDisplayGraphics.displayState(test_canvas, state);
	    }
	}
    }

    void humanTurn(int bin_num){
	if (! GameRules.isGameOver(state)){
	    PlayerID cur_player = state.getCurPlayer();

	    if ((GameRules.makeMove(state, bin_num) != null)){
		System.out.println(bin_num + 1);
		state = GameRules.makeMove(state, bin_num);
		GameDisplayGraphics.displayState(test_canvas, state);
	    }
	}
    }
	
}

	
