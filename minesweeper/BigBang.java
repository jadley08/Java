import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BigBang extends JComponent implements ActionListener, MouseListener{
  Timer timer;
  World world;
  BigBang(World world){
    this.world = world;
    timer = new Timer(1000, this);//1 second timer(1000 milliseconds)
  }
  public void start(){
    timer.start();
  }
  public void paintComponent(Graphics g){
    world.draw(g);
  }
  public void actionPerformed(ActionEvent e){
    world.update();
    if(world.hasEnded()){
      timer.stop();
    }
    this.repaint();
  }
  public void mouseEntered(MouseEvent e) { }
  public void mouseExited(MouseEvent e) { }
  public void mousePressed(MouseEvent e) { 
    this.world.mousePressed(e); 
    this.repaint(); 
  }
  public void mouseReleased(MouseEvent u) { }
  public void mouseClicked(MouseEvent e) { }
}