package ProjectOneEngine;

public class ThinkAheadPlayer implements Player {

    private class ValuedMove {
        public Move move;
        public float value;
        public ArrayList<ValuedMove> children;
        public ValuedMove parent;

        public ValuedMove(Move move, float value) {
            this.move = move;
            this.value = value;
            this.children = new ArrayList<ValuedMove>();
            this.parent = null
        }

        public void addChild(ValuedMove child) {
            this.children.add(child);
            child.parent = this;
        }
    }

    private MoveEvaluator moveEvaluator;
    private int depth = 1;
    private PlayerID player;

    public ThinkAheadPlayer(MoveEvaluator moveEvaluator) {
        this.moveEvaluator = moveEvaluator;
    }

    public Move getMove (GameState state) {
        if (player == null) {
            player = state.getCurPlayer();
        }
        return evaluateMoves(state, depth=this.depth).move;
    }

    private ValuedMove evaluateMoves (ValuedMove curMove, GameState state, int n = 0) {
        int curDepth = n + 1;                           // Which level of the evaluation "tree" we're on right now

        if (curDepth > depth) return null;

        // Get the possible moves, assign values to them, and store them in a list
        for (int i = 0; i < 6; i++){
            Move m = new Move(i, state.getCurPlayer());
            GameState gs = checkGameState(state, m);
            if (gs != null) {
                ValuedMove vm = new ValuedMove(m, 0);
                curMove.addChild(vm);
                ValuedMove newMove = evaluateMoves(vm, gs, curDepth);
                if (newMove == null) {
                    vm.value = moveEvaluator.evaluateMove(gs);
                }
                else {
                    vm.value = newMove.value
                }
            }
        }

        if (curMove.children.isEmpty()) return null;

        return getBestMove(curMove.children, state.getCurPlayer());
    }

    private ValuedMove getBestMove (List<ValuedMove> moveList, PlayerID moveMaker) {
        ValuedMove bestMove = new ValuedMove(null, -(Float.MAX_VALUE));    // Stores the best move
        boolean myTurn = moveMaker == this.player;
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

    public String getPlayName() { return "Galaxy Brain Player v1" }
}