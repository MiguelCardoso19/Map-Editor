import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.keyboard.*;
import java.io.File;

public class Cursor {
    private Rectangle cursor;
    private Grid pos;
    private int x;
    private int y;
    private Rectangle[][] gridRectangles;
    private int currentColorIndex = 0;

    public Cursor(Grid pos, Rectangle[][] gridRectangles) {
        this.pos = pos;
        this.gridRectangles = gridRectangles;
        this.cursor = new Rectangle(10, 10, pos.getCellSize(), pos.getCellSize());
        this.cursor.setColor(Color.BLUE);
        this.cursor.fill();

        CursorKeyboardHandler cursorKeyboardHandler = new CursorKeyboardHandler(this);
    }

    public void moveUp() {
        if (this.cursor.getY() >= pos.getCellSize()) {
            this.cursor.translate(0, -pos.CELL_SIZE);
            y--;
        }
    }

    public void moveDown() {
        if (this.cursor.getY() <= pos.getHeight() * pos.getCellSize() - pos.getCellSize()) {
            this.cursor.translate(0, pos.CELL_SIZE);
            y++;
        }
    }

    public void moveLeft() {
        if (this.cursor.getX() >= pos.getCellSize()) {
            this.cursor.translate(-pos.CELL_SIZE, 0);
            x--;
        }
    }

    public void moveRight() {
        if (this.cursor.getX() <= pos.getWidth() * pos.getCellSize() - pos.getCellSize()) {
            this.cursor.translate(pos.CELL_SIZE, 0);
            x++;
        }
    }

    public void paintCell(int x, int y) {
        int cellX = x;
        int cellY = y;
        pos.getMaze()[cellY][cellX] = "1";

        gridRectangles[cellY][cellX].setColor(pos.getSelectedColor());
        gridRectangles[cellY][cellX].fill();
    }

    public void unpaintCell(int x, int y) {
        int cellX = x;
        int cellY = y;
        pos.getMaze()[cellY][cellX] = "0";

        gridRectangles[cellY][cellX].setColor(Color.WHITE);
        gridRectangles[cellY][cellX].fill();

        gridRectangles[cellY][cellX].setColor(Color.BLACK);
        gridRectangles[cellY][cellX].draw();
    }

    public Grid getPos() {
        return pos;
    }


    private static class CursorKeyboardHandler implements KeyboardHandler {
        private Cursor cursor;

        public CursorKeyboardHandler(Cursor cursor) {
            this.cursor = cursor;
            Keyboard keyboard = new Keyboard(this);

            KeyboardEvent pressedUp = new KeyboardEvent();
            pressedUp.setKey(KeyboardEvent.KEY_UP);
            pressedUp.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
            keyboard.addEventListener(pressedUp);

            KeyboardEvent pressedDown = new KeyboardEvent();
            pressedDown.setKey(KeyboardEvent.KEY_DOWN);
            pressedDown.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
            keyboard.addEventListener(pressedDown);

            KeyboardEvent pressedLeft = new KeyboardEvent();
            pressedLeft.setKey(KeyboardEvent.KEY_LEFT);
            pressedLeft.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
            keyboard.addEventListener(pressedLeft);

            KeyboardEvent pressedRight = new KeyboardEvent();
            pressedRight.setKey(KeyboardEvent.KEY_RIGHT);
            pressedRight.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
            keyboard.addEventListener(pressedRight);

            KeyboardEvent pressedS = new KeyboardEvent();
            pressedS.setKey(KeyboardEvent.KEY_S);
            pressedS.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
            keyboard.addEventListener(pressedS);

            KeyboardEvent pressedL = new KeyboardEvent();
            pressedL.setKey(KeyboardEvent.KEY_L);
            pressedL.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
            keyboard.addEventListener(pressedL);

            KeyboardEvent pressedD = new KeyboardEvent();
            pressedD.setKey(KeyboardEvent.KEY_D);
            pressedD.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
            keyboard.addEventListener(pressedD);

            for (int i = 1; i <= cursor.getPos().getColors().length; i++) {
                KeyboardEvent colorEvent = new KeyboardEvent();
                colorEvent.setKey(i + '0');
                colorEvent.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
                keyboard.addEventListener(colorEvent);
            }

            KeyboardEvent pressedSpace = new KeyboardEvent();
            pressedSpace.setKey(KeyboardEvent.KEY_SPACE);
            pressedSpace.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
            keyboard.addEventListener(pressedSpace);
        }

        @Override
        public void keyPressed(KeyboardEvent keyboardEvent) {
            if (keyboardEvent.getKey() >= '0' && keyboardEvent.getKey() <= '9') {
                int colorIndex = keyboardEvent.getKey() - '0' - 1;
                if (colorIndex >= 0 && colorIndex < cursor.getPos().getColors().length) {
                    cursor.currentColorIndex = colorIndex;
                    cursor.getPos().setSelectedColor(cursor.getPos().getColors()[colorIndex]);
                }
                return;
            }

            switch (keyboardEvent.getKey()) {
                case KeyboardEvent.KEY_LEFT:
                    cursor.moveLeft();
                    break;

                case KeyboardEvent.KEY_UP:
                    cursor.moveUp();
                    break;

                case KeyboardEvent.KEY_RIGHT:
                    cursor.moveRight();
                    break;

                case KeyboardEvent.KEY_DOWN:
                    cursor.moveDown();
                    break;

                case KeyboardEvent.KEY_D:
                    cursor.getPos().clearMaze();
                    break;

                case KeyboardEvent.KEY_SPACE:
                    if (cursor.getPos().getMaze()[cursor.y][cursor.x].equals("0")) {
                        cursor.paintCell(cursor.x, cursor.y);
                    } else {
                        cursor.unpaintCell(cursor.x, cursor.y);
                    }
                    break;

                case KeyboardEvent.KEY_S:
                    cursor.getPos().save("resources.colors.txt");
                    break;

                case KeyboardEvent.KEY_L:
                    File file = new File("resources.colors.txt");
                    if (file.exists() && !file.isDirectory()) {
                        cursor.getPos().load("resources.colors.txt");
                    }
                    break;
            }
        }

        @Override
        public void keyReleased(KeyboardEvent keyboardEvent) {
        }
    }
}