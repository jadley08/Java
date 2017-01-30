import java.awt.*;
import java.awt.geom.*;
import java.lang.*;

class Ship{
  Color color;
  double velocity;
  double x;
  double radius1 = 15.0;
  double y;
  double radius2 = 17.0;
  double direction;
  double turnRate = 4.0;
  double accelerationRate = 0.1;
  
  Ship(double x, double y, double direction, double velocity, Color color){
    this.x = x;
    this.y = y;
    this.direction = direction;
    this.velocity = velocity;
    this.color = color;
  }
  
  double opposite1(double x){
    x = x - 155;
    if(x < 0){
      x = 360 + x;
    }
    return x;
  }
  
  double opposite2(double x){
    x = x - 205;
    if(x > 259){
      x = -1 + x;
    }
    return x;
  }
  
  void rotateClockwise(){
    this.direction += -1 * turnRate;
  }
  
  void rotateCounterClockwise(){
    this.direction += turnRate;
  }
  
  void accelerate(){
    this.velocity += accelerationRate;
  }
  
  void decelerate(){
    this.velocity += -1 * accelerationRate;
    
    if(this.velocity < 0){
      this.velocity = 0;
    }
  }
  
  void moveShip(int frameSize){
    this.x += (Math.sin(this.direction * (Math.PI / 180)) * this.velocity);
    this.y += (Math.cos(this.direction * (Math.PI / 180)) * this.velocity);
    
    if(this.x < 0){
      this.x += frameSize;
    }
    if(this.x > frameSize){
      this.x += (-1) * (frameSize);
    }
    if(this.y < 0){
      this.y += frameSize;
    }
    if(this.y > frameSize){
      this.y += (-1) * (frameSize);
    }
  }
  
  void draw(Graphics g){
    g.setColor(color);
    /*
    g.setColor(new Color((float)Math.random(),
                                              (float)Math.random(), 
                                              (float)Math.random()));*/
    
    
    g.drawPolygon(new int[] { (int)((radius1 * Math.sin(this.direction * (Math.PI / 180))) + this.x),
    (int)((radius2 * Math.sin(opposite1(this.direction) * (Math.PI / 180))) + this.x),
    (int)((radius2 * Math.sin(opposite2(this.direction) * (Math.PI / 180))) + this.x)},
                  new int[] {(int)((radius1 * Math.cos(this.direction * (Math.PI / 180))) + this.y),
    (int)((radius2 * Math.cos(opposite1(this.direction) * (Math.PI / 180))) + this.y),
    (int)((radius2 * Math.cos(opposite2(this.direction) * (Math.PI / 180))) + this.y)},
                  3);//also fillPolygon
  }
}