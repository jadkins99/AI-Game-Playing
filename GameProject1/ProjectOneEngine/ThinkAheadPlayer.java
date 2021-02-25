package ProjectOneEngine;

public class ThinkAheadPlayer implements Player {

    private class ValuedMove {
        public Move move;
        public float value;

        public ValuedMove(Move move, float value) {
            this.move = move;
            this.value = value;
        }
    }

    private MoveEvaluator moveEvaluator;
    private int depth = 1;

    public ThinkAheadPlayer(MoveEvaluator moveEvaluator) {
        this.moveEvaluator = moveEvaluator;
    }

    public Move getMove (GameState state) {
        return getBestMove(evaluateMoves(state, depth=this.depth))
    }

    private List<ValuedMove> evaluateMoves (GameState state, int n = 0) {
        int curDepth = n + 1;                           // Which level of the evaluation "tree" we're on right now

        if (curDepth > depth) return null;

        // Get the possible moves, assign values to them, and store them in a list
        ArrayList<ValuedMove> moveList = new ArrayList<ValuedMove>();

        for (int i = 0; i < 6; i++){
            Move m = new Move(i, state.getCurPlayer());
            GameState gs = checkGameState(state, m);
            if (gs != null) {
                moveList.add(new ValuedMove(m, moveEvaluator.evaluateMove(gs)));
            }
        }

        return moveList;
    }

    private Move getBestMove (List<ValuedMove> moveList) {
        Move bestMove = null;                           // Stores the best move
        float bestMoveVal = Integer.MIN_VALUE;          // Stores the value of the best move for comparison

        // Finds the move with the greatest value
        for (ValuedMove vm : moveList) {
            if (vm.value > bestMoveVal) {
                bestMove = vm.move;
                bestMoveVal = vm.value;
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