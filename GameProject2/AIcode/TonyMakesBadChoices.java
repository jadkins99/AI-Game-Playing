package AIcode;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;

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

public class TonyMakesBadChoices implements Player {

    //Castles that Tony wants to win in order
    CastleID castle1;
    CastleID castle2;
    CastleID castle3;

    //Players in the game
    PlayerID Tony;
    PlayerID notTony;

    //Random generator
    Random rand;

    //Average strength of all monsters in a full deck
    float avgStr = 3.5f;

    //This function is called when the game starts
    public void begin(GameState init_state) {
        rand = new Random();
    }

    //This function is called when the player must select a monster to buy
    public BuyMonsterMove getBuyMonster(GameState state) {
        if (Tony == null) assignPlayers(state);
        if (castle1 == null) assignCastles(state);

        //TODO: Make Tony Smart
        List<Move> leg_moves = GameRules.getLegalMoves(state);

        int i = rand.nextInt(leg_moves.size());
        return (BuyMonsterMove) leg_moves.get(i);
    }

    //This function is called at the start of your opponent's turn
    public void startOppTurn(GameState state) {
    }

    //This function is called when your opponent tried to buy a monster
    //If you steal, you will get the chosen monster
    //... but hand your opponent the price in coins
    public RespondMove getRespond(GameState state, Monster mon, int price) {
        if (price <= mon.value && price <= state.getCoins(Tony))
            return new RespondMove(Tony, false, mon);
        else
            return new RespondMove(Tony, true, mon);
    }

    //This function is called when the opponent pays the price to steal
    // ... the monster chosen by the player
    public void stolenMonster(GameState state) {
    }

    //This function is called when the player successfully buys a monster
    //... and needs to place the monster at a castle
    public PlaceMonsterMove getPlace(GameState state, Monster mon) {
        if (Tony == null) assignPlayers(state);
        if (castle1 == null) assignCastles(state);
        /*
        if (mon == Monster.SLAYER) {
            if (state.getMonsters(castle1, notTony).contains(Monster.DRAGON))
                return new PlaceMonsterMove(Tony, castle1, mon);
            else
                return new PlaceMonsterMove(Tony, castle2, mon);
        }

        CastleID castleChoice = castle1;
        if (mon.value > avgStr) {
            //try to place at castle2
            castleChoice = findValidPlacement(castle2, castle1, castle3, state);
        } else {
            //try to place at castle1
            castleChoice = findValidPlacement(castle1, castle2, castle3, state);
        }
        */

        CastleID castleChoice = findValidPlacement(castle2, castle1, castle3, state);
        System.out.println(Tony + " is placing " + mon + " at " + castleChoice);

        return new PlaceMonsterMove(Tony, castleChoice, mon);
    }

    public String getPlayName() {
        return "Tony, Maker of Bad Choices";
    }

    private void assignPlayers(GameState state) {
        Tony = state.getCurPlayer();

        if (Tony == PlayerID.TOP)
            notTony = PlayerID.BOT;
        else
            notTony = PlayerID.TOP;
    }

    private void assignCastles(GameState state) {
        castle1 = state.getHidden(Tony);

        /*
        if (rand.nextInt(2) == 0) {
            if (castle1 == CastleID.CastleA) {
                castle2 = CastleID.CastleB;
                castle3 = CastleID.CastleC;
            } else if (castle1 == CastleID.CastleB) {
                castle2 = CastleID.CastleA;
                castle3 = CastleID.CastleC;
            } else {
                castle2 = CastleID.CastleA;
                castle3 = CastleID.CastleB;
            }
        } else {
            if (castle1 == CastleID.CastleA) {
                castle2 = CastleID.CastleC;
                castle3 = CastleID.CastleB;
            } else if (castle1 == CastleID.CastleB) {
                castle2 = CastleID.CastleC;
                castle3 = CastleID.CastleA;
            } else {
                castle2 = CastleID.CastleB;
                castle3 = CastleID.CastleA;
            }
        }
        */
        if (castle1 == CastleID.CastleA) {
            if (getCastleStrength(state, CastleID.CastleB, notTony) < getCastleStrength(state, CastleID.CastleC, notTony)) {
                castle2 = CastleID.CastleB;
                castle3 = CastleID.CastleC;
            } else {
                castle2 = CastleID.CastleC;
                castle3 = CastleID.CastleB;
            }
        } else if (castle1 == CastleID.CastleB) {
            if (getCastleStrength(state, CastleID.CastleA, notTony) < getCastleStrength(state, CastleID.CastleC, notTony)) {
                castle2 = CastleID.CastleA;
                castle3 = CastleID.CastleC;
            } else {
                castle2 = CastleID.CastleC;
                castle3 = CastleID.CastleA;
            }
        } else {
            if (getCastleStrength(state, CastleID.CastleA, notTony) < getCastleStrength(state, CastleID.CastleB, notTony)) {
                castle2 = CastleID.CastleA;
                castle3 = CastleID.CastleB;
            } else {
                castle2 = CastleID.CastleB;
                castle3 = CastleID.CastleA;
            }
        }
    }

    private int getCastleStrength(GameState state, CastleID castle, PlayerID player) {
        List<Monster> monsters = state.getMonsters(castle, player);

        int power = 0;
        for (Monster mon : monsters) {
            power += mon.value;
        }

        return power;
    }

    private CastleID findValidPlacement(CastleID first, CastleID second, CastleID third, GameState state) {
        System.out.println("Trying to place at " + first + ", dragon is at " + castle1);
        if (state.getMonsters(first, Tony).size() < 4 && state.getCastleWon(first) == null) {
            return first;
        }

        if (state.getMonsters(second, Tony).size() < 4 && state.getCastleWon(second) == null) {
            return second;
        }

        //assignCastles(state);
        //if (state.getMonsters(castle2, Tony).size() < 4 && state.getCastleWon(castle2) == null)
        //    return castle2;

        return third;
    }
}