import java.awt.*;
import java.awt.geom.*;

class Bullet{
  double x; //current x position
  double y; //current y position
  double velocity; //speed of the ship at the time of shot
  double direction; //direction of the ship at the time of shot
  double bulletSpeed; //to be combined with velocity
  int radius; //radius of the circle of the shot
  Color color; //color of bullet
  
  Bullet(double x, double y, double velocity, double direction, double bulletSpeed, int radius, Color color){
    this.x = x;
    this.y = y;
    this.velocity = velocity;
    this.direction = direction;
    this.bulletSpeed = bulletSpeed;
    this.radius = radius;
    this.color = color;
  }
  
  void draw(Graphics g){
    g.setColor(this.color);
    g.fillOval((int)this.x - this.radius, //this.x + this.radius
               (int)this.y - this.radius,
               this.radius * 2,
               this.radius * 2);
    
    g.setColor(Color.BLACK);
    g.drawOval((int)this.x - this.radius,
               (int)this.y - this.radius,
               this.radius * 2,
               this.radius * 2);
  }
  
  void moveBullet(int frameSize){
    this.x += (Math.sin(this.direction * (Math.PI / 180)) * (this.velocity + this.bulletSpeed));
    this.y += (Math.cos(this.direction * (Math.PI / 180)) * (this.velocity + this.bulletSpeed));
    /*
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
    }*/
  }
}