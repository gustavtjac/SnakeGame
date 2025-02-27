import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.random.*;

public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WITDH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WITDH * SCREEN_HEIGHT) / UNIT_SIZE;
    static final int DELAY = 75;
    final int[] x = new int[GAME_UNITS];
    final int[] y = new int[GAME_UNITS];
    int bodyParts = 6;
    int applesEaten = 0;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random rand = new Random();
GamePanel(){
rand = new Random();
this.setPreferredSize(new Dimension(SCREEN_WITDH, SCREEN_HEIGHT));
this.setBackground(Color.gray);
this.setFocusable(true);
this.addKeyListener(new MyKeyAdapter());
startGame();
}
public void startGame(){
newApple();
running = true;
timer = new Timer(DELAY, this);
timer.start();
}
public void paintComponent(Graphics g){
super.paintComponent(g);
draw(g);
}
public void draw(Graphics g){
if(running){
    for(int i = 0; i < SCREEN_HEIGHT/UNIT_SIZE; i++){
        g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
        g.drawLine(0,i*UNIT_SIZE,SCREEN_WITDH,i*UNIT_SIZE);
    }
    g.setColor(Color.RED);
    g.fillRect(appleX,appleY,UNIT_SIZE,UNIT_SIZE);

    for (int i = 0; i < bodyParts; i++){
        if(i==0){
            g.setColor(Color.GREEN);
            g.fillOval(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
        }else {
            g.setColor(Color.GREEN);
            g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
        }

    }
    g.setColor(Color.white);
    g.setFont(new Font("Ink Free",Font.BOLD,25));
    FontMetrics metrics = g.getFontMetrics(g.getFont());
    g.drawString("Score: " + applesEaten,SCREEN_WITDH/2,g.getFont().getSize());
}else {
    gameOver(g);
}
}
public void newApple(){
appleX = rand.nextInt((int)(SCREEN_WITDH/UNIT_SIZE))*UNIT_SIZE;
appleY = rand.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
}
public void move(){
for(int i = bodyParts; i >0; i--){
    x[i] = x[i-1];
    y[i] = y[i-1];
}
switch(direction){
    case 'U':
        y[0]=y[0]-UNIT_SIZE;
        break;
        case 'D':
            y[0]=y[0]+UNIT_SIZE;
            break;
            case 'L':
                x[0]=x[0]-UNIT_SIZE;
                break;
                case 'R':
                    x[0]=x[0]+UNIT_SIZE;
                    break;

}
}
public void checkApple(){
if((x[0]==appleX) && (y[0]==appleY)){
    bodyParts++;
    applesEaten++;
    newApple();
}
}
public void checkCollision(){
    //tjekker om snaken rammer sig selv
    for (int i = bodyParts; i > 0; i--){
        if((x[0]==x[i])&&(y[0]==y[i])){
            running = false;
        }
    }
//check if headtouches left border.
    if(x[0]<0){
        running = false;
    }
    //Right Border
    if(x[0]>SCREEN_WITDH){
        running = false;
    }
    if(y[0]<0){
        running = false;
    }
    if(y[0]>SCREEN_HEIGHT){
        running = false;
    }
    if(!running){
        timer.stop();
    }
}
public void gameOver(Graphics g){
g.setColor(Color.red);
g.setFont(new Font("Ink Free",Font.BOLD,75));
FontMetrics metrics = g.getFontMetrics(g.getFont());
g.drawString("GAME OVER" ,SCREEN_WITDH-metrics.stringWidth("GAME OVER")-75,SCREEN_HEIGHT/2);
}

    @Override
    public void actionPerformed(ActionEvent e) {
if(running){
    move();
    checkApple();
    checkCollision();
}
repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
    @Override
        public void keyPressed(KeyEvent e) {
switch(e.getKeyCode()){
    case KeyEvent.VK_LEFT:
        if (direction != 'R'){
            direction = 'L';}
        break;
    case KeyEvent.VK_RIGHT:
        if (direction != 'L'){
            direction = 'R';}
        break;
    case KeyEvent.VK_UP:
        if (direction != 'D'){
            direction = 'U';}
        break;
    case KeyEvent.VK_DOWN:
        if (direction != 'U'){
            direction = 'D';}
        break;
}
    }
    }
}
