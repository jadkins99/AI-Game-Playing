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
	BOT_Player = new RandomPlayer();
	
        primary = primaryStage;
        primaryStage.setTitle("A Test");
        Group root = new Group();
        test_canvas = new Canvas(1200, 800);
 
        root.getChildren().add(test_canvas);
        Scene mainScene;
        mainScene = new Scene(root, 1200, 800, Color.BEIGE);
        primaryStage.setScene(mainScene);
        primaryStage.show();

	Timeline quickTimer = new Timeline(new KeyFrame(Duration.seconds(DELAY_TIME), new EventHandler<ActionEvent>() {
		
		public void handle(ActionEvent event) {
		    nextTurn();
		}
	    }));
	
	quickTimer.setCycleCount(Timeline.INDEFINITE);
	quickTimer.play();		
      
	state = new GameState();
	GameDisplayGraphics.displayState(test_canvas, state);

    }


    void nextTurn(){
	if (! GameRules.isGameOver(state)){
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
		    state = GameState.concede(cur_player);
		}
		else{
		    System.out.println("ILLEGAL MOVE BOTTOM CONCEDES!!");
		    state = GameState.concede(cur_player);
		}
	    }
	    
	    int bin_num = nextMove.getBin();
	    
	    System.out.println(bin_num + 1);
	    state = GameRules.makeMove(state, bin_num);
	    GameDisplayGraphics.displayState(test_canvas, state);
	}
    }
}

	
