import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.graphics.Text;
import java.io.*;

public class Grid {
    public static final int CELL_SIZE = 30;
    private int beginningX;
    private int beginningY;
    private int width;
    private int height;
    private String[][] maze = {
            {"0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
            {"0", "1", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
            {"0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
            {"0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
            {"0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
            {"0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
            {"0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
            {"0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
            {"0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
            {"0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
            {"0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
            {"0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
            {"0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
            {"0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
            {"0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
            {"0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
            {"0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
            {"0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"}
    };

    private Rectangle[][] gridRectangles;
    private Color[] colors = {
            Color.BLACK, Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW,
            Color.CYAN, Color.MAGENTA, Color.ORANGE, Color.PINK
    };
    private Color selectedColor = Color.BLACK;
    private int currentColorIndex = 0;

    public Grid(int beginningX, int beginningY) {
        this.width = maze[0].length;
        this.height = maze.length;
        this.beginningX = beginningX;
        this.beginningY = beginningY;

        this.gridRectangles = new Rectangle[height][width];
        createGridRectangles();
        displayColorPalette();
        displayInstructions();
    }

    public String[][] getMaze() {
        return maze;
    }

    public int getBeginningX() {
        return beginningX;
    }

    public int getBeginningY() {
        return beginningY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getCellSize() {
        return CELL_SIZE;
    }

    public String getCellValue(int row, int col) {
        return maze[row][col];
    }

    public Rectangle[][] getGridRectangles() {
        return gridRectangles;
    }

    private void createGridRectangles() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int x = beginningX + CELL_SIZE * j;
                int y = beginningY + CELL_SIZE * i;
                gridRectangles[i][j] = new Rectangle(x, y, CELL_SIZE, CELL_SIZE);
                gridRectangles[i][j].setColor(Color.BLACK);
                gridRectangles[i][j].draw();
            }
        }
    }

    public void clearMaze() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                gridRectangles[i][j].setColor(Color.BLACK);
                gridRectangles[i][j].draw();
            }
        }
    }

    public void displayColorPalette() {
        int paletteX = beginningX + width * CELL_SIZE + 10;
        int paletteY = beginningY;
        int paletteWidth = CELL_SIZE;
        int paletteHeight = CELL_SIZE * colors.length;

        for (int i = 0; i < colors.length; i++) {
            Rectangle colorRect = new Rectangle(paletteX, paletteY + i * paletteHeight / colors.length, paletteWidth, paletteHeight / colors.length);
            colorRect.setColor(colors[i]);
            colorRect.fill();

            Text keyText = new Text(paletteX + paletteWidth / 2, paletteY + i * paletteHeight / colors.length + paletteHeight / (2 * colors.length), Integer.toString(i + 1));
            keyText.setColor(Color.WHITE);
            keyText.grow(6, 6);
            keyText.draw();

            if (i == currentColorIndex) {
                colorRect.setColor(Color.BLACK);
                colorRect.draw();
            }
        }
    }

    public Color getSelectedColor() {
        return selectedColor;
    }

    public Color[] getColors() {
        return colors;
    }

    public void setSelectedColor(Color color) {
        this.selectedColor = color;
        for (int i = 0; i < colors.length; i++) {
            if (colors[i] == color) {
                currentColorIndex = i;
                break;
            }
        }
        displayColorPalette();
    }

    public void save(String filename) {
        try (DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(filename))) {
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    Color cellColor = gridRectangles[i][j].getColor();
                    int colorIndex = getColorIndex(cellColor);
                    outputStream.writeInt(colorIndex);
                    outputStream.writeBoolean(!maze[i][j].equals("0"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load(String filename) {
        try (DataInputStream inputStream = new DataInputStream(new FileInputStream(filename))) {
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    int colorIndex = inputStream.readInt();
                    if (colorIndex >= 0 && colorIndex < colors.length) {
                        gridRectangles[i][j].setColor(colors[colorIndex]);
                        boolean painted = inputStream.readBoolean();
                        if (painted) {
                            gridRectangles[i][j].fill();
                        } else {
                            gridRectangles[i][j].draw();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getColorIndex(Color color) {
        for (int i = 0; i < colors.length; i++) {
            if (colors[i] == color) {
                return i;
            }
        }
        return -1;
    }

    private void displayInstructions() {
        Text instructionsText = new Text(470, 300, "INSTRUCTIONS:");
        instructionsText.setColor(Color.BLACK);
        instructionsText.draw();

        Text selectColorsText = new Text(470, 340, " > Select color by pressing  a number key from 1 to 9");
        selectColorsText.setColor(Color.BLACK);
        selectColorsText.draw();

        Text movimentText = new Text(470, 360, " > Use the Arrows to move");
        movimentText.setColor(Color.BLACK);
        movimentText.draw();

        Text paintOrClearText = new Text(470, 380, " > Press 'SPACE' to paint or clear");
        paintOrClearText.setColor(Color.BLACK);
        paintOrClearText.draw();

        Text resetText = new Text(470, 400, " > Press 'D' to clear all");
        resetText.setColor(Color.BLACK);
        resetText.draw();

        Text saveText = new Text(470, 420, " > Press 'S' to save");
        saveText.setColor(Color.BLACK);
        saveText.draw();

        Text loadText = new Text(470, 440, " > Press 'L' to load");
        loadText.setColor(Color.BLACK);
        loadText.draw();
    }
}
