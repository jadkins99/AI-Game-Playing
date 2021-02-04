import java.util.List;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.Font;
import javafx.scene.shape.ArcType;

public class GameDisplayGraphics{
    public static int SIZE_TALL = 500;
    public static int SIZE_WIDE = 700;
    public static int MARGIN = 20;
    public static double LINE_WIDE = 4;
    static int FONT_SIZE = 30;
    static GraphicsContext gc;


    static void displayState(Canvas can, GameState state){
	int x, y, num_stones;
	
	gc = can.getGraphicsContext2D();

	int BOX_WIDE = (SIZE_WIDE-2*MARGIN)/6;
	int BOX_TALL = (SIZE_TALL-2*MARGIN)/6;
	gc.setLineWidth(LINE_WIDE);
	
	gc.clearRect(0,0,SIZE_WIDE,SIZE_TALL);

	gc.setStroke(Color.BLACK);

	for(int i=0; i<=5; i++){
	    gc.strokeRect(MARGIN + i*BOX_WIDE, MARGIN + BOX_TALL, BOX_WIDE, 2*BOX_TALL);
	    gc.strokeRect(MARGIN + i*BOX_WIDE, MARGIN + 3*BOX_TALL, BOX_WIDE, 2*BOX_TALL);
	}

	gc.strokeRect(MARGIN,MARGIN, BOX_WIDE*6, BOX_TALL);
	gc.strokeRect(MARGIN,MARGIN + 5*BOX_TALL, BOX_WIDE*6, BOX_TALL);
       

	// NOTE: Top Player's bins are 5, 4, 3, 2, 1, 0
	// Bot Player's bins are 0, 1, 2, 3, 4, 5

	gc.setFont(Font.font("Helvetica", FONT_SIZE));
	gc.setLineWidth(2);
	
	for(int i=0; i<6; i++){
	    num_stones = state.getStones(PlayerID.TOP, 5-i);
	    x = MARGIN + i*BOX_WIDE + BOX_WIDE/2;
	    y = MARGIN + 2*BOX_TALL;
	    gc.strokeText( String.valueOf(num_stones), x, y);
	}

	for(int i=0; i<6; i++){
	    num_stones = state.getStones(PlayerID.BOT, i);
	    x = MARGIN + i*BOX_WIDE + BOX_WIDE/2;
	    y = MARGIN + 4*BOX_TALL;
	    gc.strokeText( String.valueOf(num_stones), x, y);
	}

	num_stones = state.getHome(PlayerID.TOP);
	x = MARGIN + 3*BOX_WIDE;
        y = MARGIN + BOX_TALL/2;	
	gc.strokeText(String.valueOf(num_stones), x, y);

	num_stones = state.getHome(PlayerID.BOT);
	x = MARGIN + 3*BOX_WIDE;
	y = MARGIN + 5*BOX_TALL + BOX_TALL/2;	
	gc.strokeText(String.valueOf(num_stones), x, y);

    }

    static void displayBinContents(int x, int y, int num_stones){
	y = y + FONT_SIZE/2;
	gc.strokeText(String.valueOf(num_stones), x, y);
    }
}
