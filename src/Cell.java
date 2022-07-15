import java.util.ArrayList;
import java.util.List;

public class Cell {
    private List<Integer> options;
    private boolean collapsed;
    private int r;
    private int c;

    public Cell(int num, int r, int c) {
        this.r = r;
        this.c = c;
        this.options = new ArrayList<>();
        this.collapsed = false;
        for (int i = 0; i < num; i++) {
            this.options.add(i);
        }
    }

    public int getR() {
        return r;
    }

    public int getC() {
        return c;
    }

    public static Cell fromOptions(List<Integer> options, int r, int col) {
        Cell c = new Cell(0, r, col);
        c.setOptions(options);
        return c;
    }

    public List<Integer> getOptions() {
        return options;
    }

    public void setOptions(List<Integer> options) {
        this.options = options;
    }

    public void updateNeighbours(Cell[] neighbours, List<Tile> tiles) {
        for (int i = 0; i < neighbours.length; i++) {
            Cell c = neighbours[i];
            if(c.isCollapsed()){
                continue;
            }
            List<Integer> cOptions = c.getOptions();
            switch (i) {
                case 0:
                    for (Integer option : this.options) {
                        cOptions.addAll(tiles.get(option).up);
                    }
                    break;
                case 1:
                    for (Integer option : this.options) {
                        cOptions.addAll(tiles.get(option).right);
                    }
                    break;
                case 2:
                    for (Integer option : this.options) {
                        cOptions.addAll(tiles.get(option).down);
                    }
                    break;
                case 3:
                    for (Integer option : this.options) {
                        cOptions.addAll(tiles.get(option).left);
                    }
                    break;
            }
        }
    }

    public boolean isCollapsed() {
        return collapsed;
    }

    public void setCollapsed(boolean collapsed) {
        this.collapsed = collapsed;
    }
}
