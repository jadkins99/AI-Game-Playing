package AIcode; 
import java.util.List;
import java.util.Iterator;
import java.util.Random;
import ProjectTwoEngine.*;

public class New_player implements Player{

    PlayerID my_id;
    PlayerID opp_id;
    Random rand = new Random();


     public New_player() {
        this.my_id = null;
        this.opp_id = null;
    }

    public void begin(GameState init_state){
    }
    
    public BuyMonsterMove getBuyMonster(GameState state){

        this.my_id = state.getCurPlayer();

        if (this.my_id == PlayerID.TOP) {
            this.opp_id = PlayerID.BOT;
    }
        else {
            this.opp_id = PlayerID.TOP;
        }

        int my_coins = state.getCoins(this.my_id);
        int opp_coins = state.getCoins(this.opp_id);



        int max_value = 0;
        Monster my_mon = null;
        for (Monster mon : state.getPublicMonsters()){
            if(mon.value > max_value){
                my_mon = mon;
                max_value = mon.value;
            }
        }

        int price;
        if (my_coins > opp_coins){
            
            if(my_mon.value > 3){
                price = opp_coins + 1;
            }

            else{
                price = Math.min(my_coins,my_mon.value);
            }
        }
        else{
            price = Math.min(my_coins,my_mon.value);

        }

        return new BuyMonsterMove(this.my_id,price,my_mon);

    }

    public void startOppTurn(GameState state){
        System.out.println("The opponent's turn has started.");
    }

    public RespondMove getRespond(GameState state, Monster mon, int price){

        this.opp_id = state.getCurPlayer();

        if (this.opp_id == PlayerID.TOP) {
            this.my_id = PlayerID.BOT;
    }
        else {
            this.my_id = PlayerID.TOP;
        }

        int my_coins = state.getCoins(this.my_id);
        int opp_coins = state.getCoins(this.opp_id);


        if(my_coins > price && mon.value > 2){
            return new RespondMove(this.my_id,false,mon);
        }

        else{
            return new RespondMove(this.my_id,true,mon);
        }
    }
 

    

    public void stolenMonster(GameState state){
        System.out.println("The opponent stole my monster!");
    }

    private int addCastleMonsters(List<Monster> monsters){
        int sum = 0;
        Iterator<Monster> itr = monsters.iterator();
        while(itr.hasNext()){
            sum += itr.next().value;
        }
        return sum;
    }
    
    private int getNumCastles(GameState state){
        int num = 3;
        if(state.getCastleWon(CastleID.CastleA) != null){
            num -= 1;

        }
    if(state.getCastleWon(CastleID.CastleB) != null){
                num -= 1;

        }
    if(state.getCastleWon(CastleID.CastleC) != null){
                num -= 1;

            }
    System.out.println("Num castles active "+num);
    return num;
        }

    private int evaluateCastle(GameState state, CastleID castle){

        List<Monster> my_monsters;
        List<Monster> opp_monsters;

        int mySum;
        int oppSum;
        if(state.getCastleWon(castle) == this.my_id){
            return 1;
        }
        else if (state.getCastleWon(castle) == this.opp_id)
        {
            return -1;
        }

        else{
            my_monsters = state.getMonsters(castle,this.my_id);
            opp_monsters = state.getMonsters(castle,this.opp_id);

            mySum = addCastleMonsters(my_monsters);
            oppSum = addCastleMonsters(opp_monsters);
            System.out.println(mySum);

            if(state.getHidden(this.my_id) == castle){

                mySum += 6;

            }

             
            int buffer = 0;
            
            if (this.getNumCastles(state) == 3){
                buffer = 2;
            }

             if (this.getNumCastles(state) == 2){
                            buffer = 3;
                        }

             if (this.getNumCastles(state) == 1){
                            buffer = 6;
                        }



            if(mySum > oppSum + buffer){
                return 1;
            }

            else{
                return -1;
            }
        }
    }

    private int evaluateState(GameState state){
        return this.evaluateCastle(state,CastleID.CastleA) + this.evaluateCastle(state,CastleID.CastleB) + this.evaluateCastle(state,CastleID.CastleC);


    }


    public boolean hasDragonSlayer(GameState state, CastleID castleid){
        
        List<Monster> opp_monsters = state.getMonsters(castleid,this.opp_id);

        for(Monster monster: opp_monsters){
            if (monster.name == "Slayer"){
            return true;
        }
        

    }

        return false;
    }
    public PlaceMonsterMove getPlace(GameState state, Monster mon)
    {
        state.setDeck(DeckFactory.createDeck());
        List<Move> moves= GameRules.getLegalMoves(state);

        int best_val = -10;

        PlaceMonsterMove best_move= null;

        for(Move move : moves){
            int val = this.evaluateState(GameRules.makeMove(state,move));

            if(val > best_val){
                best_move = (PlaceMonsterMove)move;
                best_val = val;
            }
        }

        if (this.hasDragonSlayer(state, best_move.getCastle()) && best_move.getMonster().name == "Dragon"){
            int i = rand.nextInt(moves.size());
            if (best_move == (PlaceMonsterMove) moves.get(i)){
                return (PlaceMonsterMove) moves.get((i+1)%moves.size());
            }
            else{
                return (PlaceMonsterMove) moves.get(i);
            }
        }


        else if(best_val > -10){

            return best_move;
        }

        else{
            int i = rand.nextInt(moves.size());
            return (PlaceMonsterMove) moves.get(i);
        }
    }

    public String getPlayName(){
        return "New Player";
    }
}
