//make it easier!!! slow the asteroids down even more
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class Asteroids implements World{
  //                    x,y,direction,vel,color
  Ship ship = new Ship(500, 500, 180, 0, Color.BLACK); //ship construction
  int frameSize = 1000; //size of the frame fo game
  int shots; //count for the shots used in the game
  static int enemyRadius = 60; //size of the asteroids
  double enemySpeedVariant = .05; //lambda for speed of enemy
  int asteroidCount = 15; //total number of asteroids appearing in level
  int endGame = asteroidCount - 3; //tally of asteroids in level
  int respawnTicks = 2000;
  static int staticRespawnTicks = 2000;
  boolean respawn = true;
  boolean winBool = false;
  String endMessage = "";
  static BigBang game;
  static JFrame frame;
  int level = 1; //start level
  String winMessage = "";
  boolean startBool = false;
  double bulletVelocity = 1.613;
  int bulletRadius = 2;
  Color bulletColor = Color.RED;
  
  ArrayList<Ship> lives = new ArrayList<Ship>(Arrays.asList(new Ship(145, 15, 180, 0, Color.GREEN),
                                                            new Ship(165, 15, 180, 0, Color.GREEN),
                                                            new Ship(185, 15, 180, 0, Color.GREEN))); //these are extra lives
  
  ArrayList<Enemy> enemies = new ArrayList<Enemy>(Arrays.asList(new Enemy(250, 250, Math.random() + enemySpeedVariant, (int)(Math.random() * 360), enemyRadius, asteroidCount),
                                                                new Enemy(750, 250, Math.random() + enemySpeedVariant, (int)(Math.random() * 360), enemyRadius, asteroidCount - 1),
                                                                new Enemy(500, 750, Math.random() + enemySpeedVariant, (int)(Math.random() * 360), enemyRadius, asteroidCount - 2)));
  
  
  ArrayList<Bullet> bullets = new ArrayList<Bullet>();
  
  public void draw(Graphics g){
    //if game is over
    //if(this.hasEnded()){
    if(!game.running && startBool){
      g.setFont(new Font("Verdana", Font.PLAIN, 50));
      if(winBool){
        g.setColor(Color.GREEN);
        g.drawString("YOU WIN", 400, 400);
        g.setFont(new Font("Verdana", Font.PLAIN, 20));
        g.drawString(endMessage, 235, 500);
        if(level < 10){
          g.drawString(winMessage, 250, 550);
        }
        else{
          g.drawString(winMessage, 150, 550);
        }
      }
      else{
        g.setColor(Color.RED);
        g.drawString("YOU LOSE", 400, 400);
        g.setFont(new Font("Verdana", Font.PLAIN, 20));
        g.drawString(endMessage, 450, 500);
      }
    }

    //draw lives and level and label
    g.setColor(Color.GREEN);
    g.setFont(new Font("Verdana", Font.PLAIN, 22));
    g.drawString("Extra Lives:", 2, 20);
    for(Ship s : lives){
      s.draw(g);
    }
    g.drawString(("Level " + level), 900, 22);
    if(!(game.running)){
      g.drawString("ENTER to Start", 420, 22);
    }
    
    //draw ship
    if(respawn){
      respawnTicks--;
      if(respawnTicks <= 2000 && respawnTicks > 1750){
        ship.color = Color.BLACK;
      }
      else if(respawnTicks <= 1750 && respawnTicks > 1500){
        ship.color = Color.WHITE;
      }
      else if(respawnTicks <= 1500 && respawnTicks > 1250){
        ship.color = Color.BLACK;
      }
      else if(respawnTicks <= 1250 && respawnTicks > 1000){
        ship.color = Color.WHITE;
      }
      else if(respawnTicks <= 1000 && respawnTicks > 750){
        ship.color = Color.BLACK;
      }
      else if(respawnTicks <= 750 && respawnTicks > 500){
        ship.color = Color.WHITE;
      }
      else if(respawnTicks <= 500 && respawnTicks > 250){
        ship.color = Color.BLACK;
      }
      else if(respawnTicks <= 250 && respawnTicks > 0){
        ship.color = Color.WHITE;
      }
      else if(respawnTicks == 0){
        startBool = true;
        respawn = false;
        respawnTicks = staticRespawnTicks;
        ship.color = Color.BLACK;
      }
    }
    
    //draw ship
    ship.draw(g);
    
    //draw enemies
    for(Enemy e : enemies){
      e.draw(g);
    }
    
    //draw bullets
    for(Bullet b : bullets){
      b.draw(g);
    }
  }
  
  public void update(){
    //check shot detection
    int bulletRemove = -1;
    ArrayList<Enemy> newEnemies = new ArrayList<Enemy>();
    for(int i = 0; i < bullets.size(); i++){
      for(int j = 0; j < enemies.size(); j++){
        Bullet b = bullets.get(i);
        Enemy e = enemies.get(j);
        double distance = Math.sqrt(((b.x - e.x) * (b.x - e.x)) + ((b.y - e.y) * (b.y - e.y)));
        double radii = 0.0 + b.radius + e.radius;
        if((distance - radii) <= 0.0){
          double x = e.x;
          double y = e.y;
          int radius = e.radius;
          int radius2 = e.radius / 2;
          int tempNumber = e.number;
          enemies.remove(j); //remove enemy -- seems to work here
          bulletRemove = i;
          //bullets.remove(i); //remove bullet -- it gets stuck here
          
          //make more enemies depending on size -- this seems to work
          if(radius > (enemyRadius / 4)){
            int times = (int)(Math.random() * 3) + 2; //random [2..4]
            for(int k = 1; k <= times; k++){
              if(k == 1){
                newEnemies.add(new Enemy(x - (radius2 + 1), y - (radius2 + 1), Math.random() + enemySpeedVariant, (int)(Math.random() * 360), radius2, tempNumber));
              }
              else if(k == 2){
                newEnemies.add(new Enemy(x + (radius2 + 1), y + (radius2 + 1), Math.random() + enemySpeedVariant, (int)(Math.random() * 360), radius2, tempNumber));
              }
              else if(k == 3){
                newEnemies.add(new Enemy(x + (radius2 + 1), y - (radius2 + 1), Math.random() + enemySpeedVariant, (int)(Math.random() * 360), radius2, tempNumber));
              }
              else if(k == 4){
                newEnemies.add(new Enemy(x - (radius2 + 1), y + (radius2 + 1), Math.random() + enemySpeedVariant, (int)(Math.random() * 360), radius2, tempNumber));
              }
            }
          }
          else{
            if(!(Enemy.isInGame(enemies, tempNumber))){
              endGame += -1;
              System.out.println( "endGame: " + endGame ); //for testing
              if(endGame > 0){
                enemies.add(new Enemy(ship.x + 500, ship.y + 500, Math.random() + enemySpeedVariant, (int)(Math.random() * 360), enemyRadius, endGame));
              }
            }
          }
        }
      }
    }
    if(bulletRemove >= 0){
      bullets.remove(bulletRemove);
    }
    for(int i = newEnemies.size() - 1; i >= 0; i--){
      enemies.add(newEnemies.get(i));
      newEnemies.remove(i);
    }
    
    //move bullets - remove if beyond framesize
    for(int i = bullets.size() - 1; i>= 0; i--){
      Bullet b = bullets.get(i);
      if(b.x + bulletRadius > frameSize || b.y + bulletRadius > frameSize || b.x - bulletRadius < 0 || b.y - bulletRadius < 0){
        bullets.remove(i);
      }
      else{
        b.moveBullet(frameSize);
      }
    }
      
    //move enemies
    for(Enemy e : enemies){
      e.moveEnemy(frameSize);
    }
    
    //move ship
    ship.moveShip(frameSize);
  }
  
  public void keyPressed(KeyEvent e){
    if(e.getKeyCode() == 10){game.sswitch();}
    if(e.getKeyCode() == 39 && game.running){ship.rotateClockwise();} //right-arrow
    if(e.getKeyCode() == 37 && game.running){ship.rotateCounterClockwise();} //left-arrow
    if(e.getKeyCode() == 38 && game.running){ship.accelerate();} //up-arrow
    if(e.getKeyCode() == 40 && game.running){ship.decelerate();} //down-arrow
    if(e.getKeyCode() == 32 && game.running){
      bullets.add( new Bullet(ship.x, ship.y, ship.velocity, ship.direction, bulletVelocity, bulletRadius, bulletColor) );
      shots += 1;
    } //space-bar
  }
  
  public boolean hasEnded(){
    boolean bool = false;
    //check hit detection
    if(!respawn){
      for(Enemy e : enemies){
        double distance = Math.sqrt(((e.x - ship.x) * (e.x - ship.x)) + ((e.y - ship.y) * (e.y - ship.y)));
        double radii = e.radius + 7;
        if((distance - radii) <= 0){
          if(lives.size() == 0){
            bool = true;
            endMessage = "" + (endGame + 3) + " asteroids left.";
          }
          else{
            lives.remove(lives.size() - 1);
            ship = new Ship(500, 500, 180, 0, Color.BLACK);
            respawn = true;
          }
        }
      }
    }
    
    //check if all enemies killed
    if(enemies.size() == 0){
      //bool = true;
      winBool = true;
      endMessage = "You destroyed " + asteroidCount + " asteroids with " + shots + " shots and " + lives.size() + " extra lives.";
      if(level < 10){
        shots = 0;
        ship = new Ship(500, 500, 180, 0, Color.BLACK);
        int tempLevel = level + 1;
        winMessage = "Congrats! You beat level " + level + ". How about level " + tempLevel + "?";
        level++;
        bullets = new ArrayList<Bullet>();
        game.stop();
        game.repaint();
        enemySpeedVariant += .01;
        asteroidCount += 2;
        endGame = asteroidCount - 3;
        enemies.add(new Enemy(250, 250, Math.random() + enemySpeedVariant, (int)(Math.random() * 360), enemyRadius, asteroidCount));
        enemies.add(new Enemy(750, 250, Math.random() + enemySpeedVariant, (int)(Math.random() * 360), enemyRadius, asteroidCount - 1));
        enemies.add(new Enemy(500, 750, Math.random() + enemySpeedVariant, (int)(Math.random() * 360), enemyRadius, asteroidCount - 2));
        lives.add(new Ship((lives.get(lives.size() - 1)).x + 20, 15, 180, 0, Color.GREEN));
        respawn = true;
      }
      else{
        bool = true;
        winMessage = "WOW! You beat all 10 levels. Congrats, now go do something productive.";
        game.repaint();//needed?
      }
    }
    
    //return false; //for testing
    return bool; //for game
  }
  
  public static void main(String[] args){
    game = new BigBang(new Asteroids());
    frame = new JFrame("ASTEROIDS");
    frame.getContentPane().add( game );
    frame.addKeyListener( game );
    frame.setVisible( true );
    frame.setSize(1000, 1000);
    frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    frame.getContentPane().setBackground( Color.WHITE );
    //game.start();
  }
}
