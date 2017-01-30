import java.awt.Graphics;
import java.awt.event.MouseEvent;

interface World{
  public void draw(Graphics g);
  public void update();
  public boolean hasEnded();
  public void mousePressed(MouseEvent e);
}