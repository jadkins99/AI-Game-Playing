package ProjectOneEngine;
import java.util.List;
import java.util.ArrayList;

public class ThinkAheadPlayer implements Player {

    private class ValuedMove {
        public Move move;
        public float value;
        public ArrayList<ValuedMove> children;
        public ValuedMove parent;
        public boolean processed;

        public ValuedMove(Move move, float value) {
            this.move = move;
            this.value = value;
            this.children = new ArrayList<ValuedMove>();
            this.parent = null;
            this.processed = false;
        }

        public void addChild(ValuedMove child) {
            this.children.add(child);
            child.parent = this;
        }
    }

    private MoveEvaluator moveEvaluator;
    private int depth = 10;
    private PlayerID maxPlayer;
    private PlayerID minPlayer;

    public ThinkAheadPlayer(MoveEvaluator moveEvaluator) {
        this.moveEvaluator = moveEvaluator;
    }

    public Move getMove (GameState state) {
        if (maxPlayer == null) {
            maxPlayer = state.getCurPlayer();
            if (maxPlayer == PlayerID.TOP) minPlayer = PlayerID.BOT;
            else minPlayer = PlayerID.BOT;
        }
        return evaluateMoves(new ValuedMove(null, 0), state, 0, this.depth).move;
    }

    private ValuedMove evaluateMoves (ValuedMove curMove, GameState state, int n, int depth) {
        int curDepth = n + 1;                           // Which level of the evaluation "tree" we're on right now

        if (curDepth > depth) return curMove;

        for (int i = 0; i < 6; i++){
            Move m = new Move(i, state.getCurPlayer());
            GameState gs = checkGameState(state, m);
            if (gs != null) {
                System.out.println("New Move, depth: " + curDepth);
                ValuedMove vm = new ValuedMove(m, 0);
                curMove.addChild(vm);
                ValuedMove newMove = evaluateMoves(vm, gs, curDepth, depth);
                if (newMove == curMove) {
                    vm.value = moveEvaluator.evaluateMove(gs);
                }
                else {
                    vm.value = newMove.value;
                }
            }
        }

        if (curMove.children.isEmpty()) return curMove;

        return getBestMove(curMove.children, state.getCurPlayer());
    }

    private ValuedMove getBestMove (List<ValuedMove> moveList, PlayerID moveMaker) {
        ValuedMove bestMove = new ValuedMove(new Move(1, moveMaker), -(Float.MAX_VALUE));    // Stores the best move
        boolean myTurn = moveMaker == this.maxPlayer;
        if (myTurn) bestMove.value *= -1;

        // Finds the move with the greatest value
        for (ValuedMove vm : moveList) {
            if ((myTurn && vm.value > bestMove.value) || (!myTurn && vm.value < bestMove.value)) {
                bestMove = vm;
            }
        }

        return bestMove;
    }

    private GameState checkGameState (GameState state, Move move) {
        GameState tmpGameState = new GameState(state);
        return GameRules.makeMove(tmpGameState, move);
    }

    public String getPlayName() { return "Galaxy Brain Player v1"; }
}