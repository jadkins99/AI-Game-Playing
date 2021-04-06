package AIcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ProjectTwoEngine.*;

// player that goes for points
public class PointsPlayer implements Player {

    boolean to_pass = false;
    RespondMove res_move; 
    Random rand;

    public void begin(GameState init_state){
        rand = new Random();
    }

    public BuyMonsterMove getBuyMonster(GameState state){ 
        PlayerID player = state.getCurPlayer();
        List<Monster> cur_public = state.getPublicMonsters();

        Monster cur_mon;
        Monster chose_mon = null;
        int coins = state.getCoins(player);
        int price = 0;

        for (int i = 0; i < cur_public.size(); i++){
            cur_mon = cur_public.get(i);
            if(cur_mon.value > 3){
                if (chose_mon != null || chose_mon.name != "Slayer" ){
                    if(chose_mon.value < cur_mon.value && coins > (int)cur_mon.value){
                        chose_mon = cur_mon;
                    }
                }
                else if (chose_mon == null){
                    chose_mon = cur_mon;
                }
            }
            else if (cur_mon.name == "Slayer" && coins > 3){
                chose_mon = cur_mon;
            }
            else {
                if (chose_mon == null){
                    chose_mon = cur_mon;
                }
            }
        }

        if (chose_mon.name == "Slayer"){
            price = 3;
        }
        else {
            price = chose_mon.value - 1;
        }
        
        BuyMonsterMove buy_move = new BuyMonsterMove(player, price, chose_mon);
        return buy_move;
    }

    // called to start the opponents turn
    public void startOppTurn(GameState state){ }

    // calls the response to the current players move
    public RespondMove getRespond(GameState state, Monster mon, int price){ 

        PlayerID player = state.getCurPlayer();
        if (player == PlayerID.TOP){
            player = PlayerID.BOT;
        }
        else {
            player = PlayerID.TOP;
        }

        to_pass = false;

        if (mon.value <= price){
            // buy it if the value is less than the price & player has enough coins
            if (state.getCoins(player) - price > 0){ 
                to_pass = false;
            }
            else if (state.getCoins(player) - price < 0){
                to_pass = true;
            }
        }
        else {
            to_pass = true;
        }
        
        res_move = new RespondMove(player, to_pass, mon);
        return res_move;
    }
    
    // called when monster is stolen by the other player
    public void stolenMonster(GameState state){ }

    // places monster at castle
    public PlaceMonsterMove getPlace(GameState state, Monster mon){ 
        PlayerID player = state.getCurPlayer(); 

        // add to the castle with the lowest overall value

        PlaceMonsterMove cast = new PlaceMonsterMove(player, CastleID.CastleA, mon);
        int pas_poin = 0;
        int cur_poin = 0;
        PlaceMonsterMove cho_cas = new PlaceMonsterMove(player, CastleID.CastleA, mon);

        List<Move> leg_moves = GameRules.getLegalMoves(state);
        
        for(int i=0; i < leg_moves.size()-1; i ++){
            cast = (PlaceMonsterMove)leg_moves.get(i);
            ArrayList<Monster> cur_cas = new ArrayList<Monster>(state.getMonsters(cast.getCastle(), player));
            
            // get total points of current castle
            for(int x=0; x < cur_cas.size()-1; x ++){
                cur_poin = cur_poin + cur_cas.get(x).value;
            }

            if (cur_poin < pas_poin && pas_poin != 0){
                cur_poin = pas_poin;
                cho_cas = cast;
            }
            
            cur_poin = 0;
        }

        PlaceMonsterMove place_move = new PlaceMonsterMove(player, cho_cas.getCastle(), mon);
        return place_move;
    }

    // returns player name
    public String getPlayName(){
        return "High Points :)";
    }
    
}
