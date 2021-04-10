package AIcode;

import ProjectTwoEngine.BuyMonsterMove;
import ProjectTwoEngine.CastleID;
import ProjectTwoEngine.GameRules;
import ProjectTwoEngine.GameState;
import ProjectTwoEngine.Monster;
import ProjectTwoEngine.Move;
import ProjectTwoEngine.PlaceMonsterMove;
import ProjectTwoEngine.Player;
import ProjectTwoEngine.PlayerID;
import ProjectTwoEngine.RespondMove;

public class Frankenplayer implements Player{
    PointsPlayer pointsPlayer;
    TonyMakesBadChoices Tony;
    New_player newPlayer;

    //This function is called when the game starts
    public void begin(GameState init_state){
        pointsPlayer = new PointsPlayer();
        Tony = new TonyMakesBadChoices();
        newPlayer = new New_player();

        pointsPlayer.begin(init_state);
        Tony.begin(init_state);
    }

    //This function is called when the player must select a monster to buy
    public BuyMonsterMove getBuyMonster(GameState state){
        return newPlayer.getBuyMonster(state);
    }

    //This function is called at the start of your opponent's turn
    public void startOppTurn(GameState state){}

    //This function is called when your opponent tried to buy a monster
    //If you steal, you will get the chosen monster
    //... but hand your opponent the price in coins
    public RespondMove getRespond(GameState state, Monster mon, int price){
        return pointsPlayer.getRespond(state, mon, price);
    }

    //This function is called when the opponent pays the price to steal
    // ... the monster chosen by the player
    public void stolenMonster(GameState state){}

    //This function is called when the player successfully buys a monster
    //... and needs to place the monster at a castle
    public PlaceMonsterMove getPlace(GameState state, Monster mon){
        return Tony.getPlace(state, mon);
    }

    public String getPlayName(){
        return "Frankenbot Mark III";
    }
}