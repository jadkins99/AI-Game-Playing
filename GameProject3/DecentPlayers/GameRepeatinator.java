package DecentPlayers;

import ProjectThreeEngine.AIGameText;
import ProjectThreeEngine.AIGameApp;
import java.io.IOException;

public class GameRepeatinator {
    public static void main(String[] args) {
        runGames(2000000);
    }
    public static void runGames(int numGames) {
        for (int i = 0; i < numGames; i++) {
            try {
                //AIGameApp();
                AIGameText.run_game("game.txt");
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}