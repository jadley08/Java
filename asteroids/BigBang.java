import javax.swing.*; 
import java.awt.event.*; 
import java.awt.*; 

class BigBang extends JComponent implements KeyListener, ActionListener { //MouseListener
  boolean running = false;
  Timer t;
  World world; 
  BigBang(int delay, World world) {
    this.world = world;
    t = new Timer(1, this);
  }
  public void sswitch() {
    if(running){
      this.stop();
    }
    else{
      this.start();
    }
  }
  public void start() {
    t.start();
    running = true;
  }
  public void stop() {
    t.stop();
    running = false;
  }
  BigBang(World world) {
    this(1000, world);  
  }
  public void paintComponent(Graphics g) {
    world.draw(g);
  }
  public void actionPerformed(ActionEvent e) {
    world.update();
    if(world.hasEnded()){
      t.stop();
      running = false;
    }
    this.repaint();
  }
  public void keyPressed(KeyEvent e) { 
    world.keyPressed(e); 
    this.repaint(); 
  } 
  public void keyTyped(KeyEvent e) { } 
  public void keyReleased(KeyEvent e) { } 
}