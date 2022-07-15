import java.util.List;

public class History {
    private int row;
    private int col;
    private List<Integer> options;

    public History(int row, int col, List<Integer> options) {
        this.row = row;
        this.col = col;
        this.options = options;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public List<Integer> getOptions() {
        return options;
    }
}
