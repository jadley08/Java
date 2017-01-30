import java.awt.*;
import java.awt.geom.*;
import java.lang.*;
import java.util.*;

class Enemy{
  double x; //current x position
  double y; //current y position
  double velocity; //speed of the enemy
  double direction; //direction the enemy is heading
  int radius; //radius of enemy
  int number;
  
  Enemy(double x, double y, double velocity, double direction, int radius, int number){
    this.x = x;
    this.y = y;
    this.velocity = velocity;
    this.direction = direction;
    this.radius = radius;
    this.number = number;
  }
  
  boolean equals(Enemy that){
    
    boolean bool = false;
    if(this.x == that.x && this.y == that.y && this.velocity == that.velocity
         && this.direction == that.direction && this.radius == that.direction){
      bool = true;
    }
    return bool;
  }
  
  void draw(Graphics g){//to make it look more like an asteroid, can use same method as ship
    /*
    g.setColor(Color.LIGHT_GRAY);
    g.fillOval((int)this.x - this.radius, //this.x + this.radius
               (int)this.y - this.radius,
               this.radius * 2,
               this.radius * 2);
    g.setColor(Color.BLACK);
    g.drawOval((int)this.x - this.radius,
               (int)this.y - this.radius,
               this.radius * 2,
               this.radius * 2);*/
    ///*
    int[] xCoords = new int[16];
    int[] yCoords = new int[16];
    double theta = 0;
    for(int i = 0; i <= 15; i++){
      if(i == 0){ theta = 0;}
      if(i == 1){ theta = Math.PI / 6; }
      if(i == 2){ theta = Math.PI / 4; }
      if(i == 3){ theta = Math.PI / 3; }
      if(i == 4){ theta = Math.PI / 2; }
      if(i == 5){ theta = (2 * Math.PI) / 3; }
      if(i == 6){ theta = (3 * Math.PI) / 4; }
      if(i == 7){ theta = (5 * Math.PI) / 6; }
      if(i == 8){ theta = Math.PI; }
      if(i == 9){ theta = (7 * Math.PI) / 6; }
      if(i == 10){ theta = (5 * Math.PI) / 4;}
      if(i == 11){ theta = (4 * Math.PI) / 3; }
      if(i == 12){ theta = (3 * Math.PI) / 2; }
      if(i == 13){ theta = (5 * Math.PI) / 3; }
      if(i == 14){ theta = (7 * Math.PI) / 4; }
      if(i == 15){ theta = (11 * Math.PI) / 6; }
      
      if(this.radius == 60){
      xCoords[i] = (int)((this.radius * Math.cos(theta)) + (int)(Math.random()*9) - 4 + this.x);
      }
      else if(this.radius == 30){
        xCoords[i] = (int)((this.radius * Math.cos(theta)) + (int)(Math.random()*7) - 3 + this.x);
      }
      else if(this.radius == 15){
        xCoords[i] = (int)((this.radius * Math.cos(theta)) + (int)(Math.random()*5) - 2 + this.x);
      }
    }
    for(int i = 0; i <= 15; i++){
      if(i == 0){ theta = 0;}
      if(i == 1){ theta = Math.PI / 6; }
      if(i == 2){ theta = Math.PI / 4; }
      if(i == 3){ theta = Math.PI / 3; }
      if(i == 4){ theta = Math.PI / 2; }
      if(i == 5){ theta = (2 * Math.PI) / 3; }
      if(i == 6){ theta = (3 * Math.PI) / 4; }
      if(i == 7){ theta = (5 * Math.PI) / 6; }
      if(i == 8){ theta = Math.PI; }
      if(i == 9){ theta = (7 * Math.PI) / 6; }
      if(i == 10){ theta = (5 * Math.PI) / 4;}
      if(i == 11){ theta = (4 * Math.PI) / 3; }
      if(i == 12){ theta = (3 * Math.PI) / 2; }
      if(i == 13){ theta = (5 * Math.PI) / 3; }
      if(i == 14){ theta = (7 * Math.PI) / 4; }
      if(i == 15){ theta = (11 * Math.PI) / 6; }
      
      if(this.radius == 60){
        yCoords[i] = (int)((this.radius * Math.sin(theta)) + (int)(Math.random()*9) - 4 + this.y);
      }
      else if(this.radius == 30){
        yCoords[i] = (int)((this.radius * Math.sin(theta)) + (int)(Math.random()*7) - 3 + this.y);
      }
      else if(this.radius == 15){
        yCoords[i] = (int)((this.radius * Math.sin(theta)) + (int)(Math.random()*5) - 2 + this.y);
      }
    }
    
    //g.setColor(Color.LIGHT_GRAY);
    g.setColor(new Color(156, 93, 82));
    /*
    g.setColor(new Color((float)Math.random(),
                                              (float)Math.random(), 
                                              (float)Math.random())); //for the joke*/
    g.fillPolygon(xCoords, yCoords, 16);//*/
  }
  
  void moveEnemy(int frameSize){
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
  
  static boolean isInGame(ArrayList<Enemy> arr, int num){
    boolean bool = false;
    for(Enemy e : arr){
      if(e.number == num){
        bool = true;
      }
    }
    return bool;
  }
}