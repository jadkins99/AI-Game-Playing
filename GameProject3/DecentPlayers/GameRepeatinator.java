package DecentPlayers;

import ProjectThreeEngine.AIGameText;
import java.io.IOException;

public class GameRepeatinator {
    public static void main(String[] args) {
        runGames(20);
    }
    public static void runGames(int numGames) {
        for (int i = 0; i < numGames; i++) {
            try {
                AIGameText.run_game("oghmainfinium.txt");
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}