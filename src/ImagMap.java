


import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class ImagMap {
    private final int DIM;
    private final int WIDTH;
    private final int HEIGHT;
    private Cell[][] grid;
    private List<Tile> tiles;
    private Stack<History> background;
    private Stack<Cell> clean;
    private boolean toRewind = false;
    private int[][] rewindSpot;
    private Display gUI;

    private List<Cell> collapsed;
    private List<Cell> notCollapsed;


    public ImagMap(Display gUI, List<Tile> tiles, int width, int height, int dim) {
        this.tiles = tiles;
        this.gUI = gUI;
        this.DIM = dim;
        this.WIDTH = width;
        this.HEIGHT = height;
        this.rewindSpot = new int[DIM][DIM];
        this.grid = new Cell[DIM][DIM];
        this.background = new Stack<>();
        this.clean = new Stack<>();
        this.collapsed = new ArrayList<>();
        this.notCollapsed = new ArrayList<>();
        this.startOver();

    }

    public Cell[][] createMap() {
        int FPS = 60;
        long workTime=1000/FPS;
        int framesAfterFinished = 0;
        long start = System.currentTimeMillis();
        long startFrame = System.currentTimeMillis();
        int frames = 0;
        while (true) {
            frames++;
            Graphics drawSurface = this.gUI.getGraphics();
            if (drawSurface == null) {
                continue;
            }
            draw(drawSurface);
            collapseTile();
            update();
            if (this.toRewind) {
                if (this.background.isEmpty()) {
                    System.out.println("Start Over");
                    startOver();
                } else {
                    rewind();
                    for (int i = 0; i < 5; i++) {
                        update();
                    }
                }
                this.toRewind = false;
            }

            if (this.notCollapsed.isEmpty()) {
                framesAfterFinished++;
            }
            if (framesAfterFinished > 60) {
                break;
            }

            this.gUI.showGraphics(drawSurface);
            long now = System.currentTimeMillis();
            long work=now-start;
           /* if(workTime>work) {
                try {
                    Thread.sleep(workTime - (now - start));
                } catch (InterruptedException e) {
                }
            }*/
            if (now - startFrame > 1000) {
                double finished = this.collapsed.size();
                double total = this.collapsed.size() + this.notCollapsed.size();
                this.gUI.updateTitle(frames);
                System.out.println("Finished percent: " + (finished * 100 / total) + " %");
                frames = 0;
                startFrame=System.currentTimeMillis();
            }


        }
        return this.grid;
    }

    private void draw(Graphics surface) {
        for (Cell c : this.collapsed) {
            int row = c.getR();
            int col = c.getC();
            int y = row * HEIGHT / DIM;
            int x = col * WIDTH / DIM;
            tiles.get(c.getOptions().get(0)).draw(surface, x, y);
        }
        while (!this.clean.isEmpty()) {
            Cell cell = this.clean.pop();
            int r = cell.getR();
            int c = cell.getC();
            int width = WIDTH / DIM;
            int height = HEIGHT / DIM;
            int y = r * height;
            int x = c * width;
            surface.setColor(Color.WHITE);
            surface.fillRect(x, y, width, height);
        }
    }

    private boolean collapseTile() {
        List<Cell> copyGrid = new ArrayList<>();
        for (Cell c : this.notCollapsed) {
            if (!c.getOptions().isEmpty()) {
                copyGrid.add(c);
            }
        }
        if (copyGrid.isEmpty()) {
            return true;
        }
        copyGrid.sort(new Comparator<Cell>() {
            @Override
            public int compare(Cell o1, Cell o2) {
                return o1.getOptions().size() - o2.getOptions().size();
            }
        });
        int miniSize = copyGrid.get(0).getOptions().size();
        int indexStop = copyGrid.size();
        for (int i = 1; i < copyGrid.size(); i++) {
            Cell c = copyGrid.get(i);
            if (c.getOptions().size() > miniSize) {
                indexStop = i;
                break;
            }
        }
        Random r = new Random();
        int index = r.nextInt(indexStop);
        Cell cellCollected = copyGrid.get(index);
        List<Integer> options = cellCollected.getOptions();
        if (options.isEmpty()) {
            //  startOver();
            return false;
        }
        List<Tile> tilesToSelect = new ArrayList<>();
        for (Integer option : options) {
            Tile tile = tiles.get(option);
            for (int i = 0; i < tile.getChance(); i++) {
                tilesToSelect.add(tile);
            }
        }
        int row = cellCollected.getR();
        int col = cellCollected.getC();
        this.background.push(new History(row, col, new ArrayList<>(options)));
        int optionChosen = r.nextInt(tilesToSelect.size());
        int tile = tiles.indexOf(tilesToSelect.get(optionChosen));
        options.clear();
        options.add(tile);
        cellCollected.setCollapsed(true);
        cellCollected.setOptions(options);
        updateN(cellCollected);
        this.notCollapsed.remove(cellCollected);
        this.collapsed.add(cellCollected);
        return true;
    }

    private void startOver() {
        this.notCollapsed.clear();
        this.collapsed.clear();
        this.background.clear();
        this.clean.clear();
        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                Cell c = new Cell(tiles.size(), i, j);
                this.grid[i][j] = c;
                this.rewindSpot[i][j] = 0;
                this.notCollapsed.add(c);
            }
        }
    }

    private void update() {
        int[] temp = new int[tiles.size()];
        Arrays.setAll(temp, k -> k);
        List<Cell> newGen = new ArrayList<>();
        for (Cell cell : this.notCollapsed) {
            int row = cell.getR();
            int col = cell.getC();
            List<Integer> optionsAll = Arrays.stream(temp).boxed().toList();
            //up
            Cell up = grid[(row - 1 + DIM) % DIM][col];
            List<Integer> validOptionsUP = new ArrayList<>();
            for (Integer option : up.getOptions()) {
                validOptionsUP.addAll(tiles.get(option).down);
            }
            optionsAll = checkValid(optionsAll, validOptionsUP);
            //down

            Cell down = grid[(row + 1 + DIM) % DIM][col];
            List<Integer> validOptionsDOWN = new ArrayList<>();
            for (Integer option : down.getOptions()) {
                validOptionsDOWN.addAll(tiles.get(option).up);
            }
            optionsAll = checkValid(optionsAll, validOptionsDOWN);

            //left
            Cell left = grid[row][(col - 1 + DIM) % DIM];
            List<Integer> validOptionsLEFT = new ArrayList<>();
            for (Integer option : left.getOptions()) {
                validOptionsLEFT.addAll(tiles.get(option).right);
            }
            optionsAll = checkValid(optionsAll, validOptionsLEFT);
            //right

            Cell right = grid[row][(col + 1 + DIM) % DIM];
            List<Integer> validOptionsRIGHT = new ArrayList<>();
            for (Integer option : right.getOptions()) {
                validOptionsRIGHT.addAll(tiles.get(option).left);
            }
            optionsAll = checkValid(optionsAll, validOptionsRIGHT);
            if (optionsAll.isEmpty() && !this.toRewind) {
                this.toRewind = true;

            }
            cell.setOptions(optionsAll);
            newGen.add(cell);
        }

        this.notCollapsed = newGen;

    }

    private void updateN(Cell cell) {
        int r = cell.getR();
        int c = cell.getC();
        Cell[] neighbours =
                new Cell[] {grid[(r - 1 + DIM) % DIM][c], grid[r][(c + 1 + DIM) % DIM], grid[(r + 1 + DIM) % DIM][c],
                        grid[r][(c - 1 + DIM) % DIM]};
        cell.updateNeighbours(neighbours, tiles);

    }

    private void rewind() {
        History last = this.background.pop();
        int r = last.getRow();
        int c = last.getCol();
        System.out.println("rewind");
        System.out.println("Row : " + r + " , Col : " + c);
        this.rewindSpot[r][c]++;
        Cell cell = this.grid[r][c];
        cell.setCollapsed(false);
        cell.setOptions(last.getOptions());
        this.collapsed.remove(cell);
        this.notCollapsed.add(cell);
        updateN(cell);
        if (this.rewindSpot[r][c] > this.tiles.size() / 2) {
            startOver();
        }
    }

    private List<Integer> checkValid(List<Integer> valid, List<Integer> options) {
        List<Integer> validOptions = new ArrayList<>();
        for (Integer option : valid) {
            if (options.contains(option)) {
                validOptions.add(option);
            }
        }
        return validOptions;
    }


}
