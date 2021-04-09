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

        // take two get castle monster list
        // i have recoded this stupid part of the AI so many times now
        ArrayList<Monster> casA = new ArrayList<Monster>(state.getMonsters(CastleID.CastleA, player));
        ArrayList<Monster> casB = new ArrayList<Monster>(state.getMonsters(CastleID.CastleB, player));
        ArrayList<Monster> casC = new ArrayList<Monster>(state.getMonsters(CastleID.CastleC, player));
        int totalA = 0;
        int totalB = 0;
        int totalC = 0;

        CastleID cho_cas = CastleID.CastleB;

        if (casA.size() !=0 && casB.size() != 0 && casC.size() != 0){
            // get totals
            for(int i=0; i < casA.size(); i++){
                totalA += casA.get(i).value;
            }
            for(int i=0; i < casB.size(); i++){
                totalB += casB.get(i).value;
            }
            for(int i=0; i < casC.size(); i++){
                totalC += casC.get(i).value;
            }

            if ((int)casA.size() < 4){
                if (totalA < totalB && totalA < totalC){
                    cho_cas = CastleID.CastleA;
                    System.out.println("Add to Castle A !");
                }
            }

            else if ((int)casB.size() < 4){
                if (totalB < totalA && totalB < totalC){
                    cho_cas = CastleID.CastleB;
                    System.out.println("Add to Castle B !");
                }
            }

            else {
                if ((int)casC.size() < 4){
                    cho_cas = CastleID.CastleC;
                    System.out.println("Add to Castle C !");
                }
            }
        }
        else {
            if (casA.size() == 0){
                cho_cas = CastleID.CastleA;
                System.out.println("Add to Castle A ! NULL");
            }
            else if (casB.size() == 0) {
                cho_cas = CastleID.CastleB;
                System.out.println("Add to Castle B ! NULL");
            }
            else {
                cho_cas = CastleID.CastleC;
                System.out.println("Add to Castle C ! NULL");
            }
        }

        PlaceMonsterMove place_move = new PlaceMonsterMove(player, cho_cas, mon);
        return place_move;
    }

    // returns player name
    public String getPlayName(){
        return "Stupid AI";
    }
    
}
