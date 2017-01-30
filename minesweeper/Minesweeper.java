import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class Minesweeper implements World{
  static Cell[][] board = new Cell[16][30];
  int timer = 0;
  boolean lose = false;
  int flagCount = 99;
  
  public void draw(Graphics g){
    //draw timer
    g.setColor(Color.BLACK);
    g.fillRect(450, 20, 100, 40);
    g.setColor(Color.RED);
    g.setFont(new Font("Serif Bold", Font.BOLD, 35));
    g.drawString(Integer.toString(timer), 453, 52);
    
    //draw flagCount
    g.setColor(Color.BLACK);
    g.fillRect(50, 20, 100, 40);
    g.setColor(Color.RED);
    g.setFont(new Font("Serif Bold", Font.BOLD, 35));
    g.drawString(Integer.toString(flagCount), 53, 52);
    
    //draw a boundary
    g.setColor(Color.BLACK);
    g.drawRect(0, 80, 600, 320);
    
    //draw all the cells
    for(Cell[] row : board){
      for(Cell c : row){
        c.draw(g);
      }
    }
    
    
    g.setColor(Color.YELLOW);
    g.fillOval(280, 20, 40, 40);
    g.setColor(Color.BLACK);
    g.drawOval(280, 20, 40, 40);
    if(!lose){//if the game is not lost, alive face
      g.setColor(Color.BLACK);
      g.fillOval(291, 32, 5, 5);
      g.fillOval(304, 32, 5, 5);
      g.drawArc(291, 35, 20, 15, 210, 100);
    }
    //sunglasses win face as well
    else if(lose){//if the game is lost, dead face
      g.drawLine(291, 32, 295, 36);
      g.drawLine(295, 32, 291, 36);
      g.drawLine(304, 32, 308, 36);
      g.drawLine(308, 32, 304, 36);
      g.drawArc(291, 42, 20, 15, 35, 130);
    }
  }
  
  public void update(){
    if(!lose){
      timer++;
    }
    
    if(lose){
      for(Cell[] row : board){
        for(Cell c : row){
          if(c.number == -1){
            c.setVisible();
          }
        }
      }
    }
  }
  
  public void nullSetVisible(int row, int col){//something goes wrong here
    if(board[row][col].number == 0){
      board[row][col].setVisible();
      
      if((row > 0) && (col > 0)){
        boolean visible = board[row-1][col-1].visible;
        board[row-1][col-1].setVisible();
        if((board[row-1][col-1].number == 0) && !(visible)){ nullSetVisible((row-1), (col-1)); }
      }
      if(row > 0){
        boolean visible = board[row-1][col].visible;
        board[row-1][col].setVisible();
        if((board[row-1][col].number == 0) && !(visible)){ nullSetVisible((row-1), col); }
      }
      if((row > 0) && (col < 28)){
        boolean visible = board[row-1][col+1].visible;
        board[row-1][col+1].setVisible();
        if((board[row-1][col+1].number == 0) && !(visible)){ nullSetVisible((row-1), (col+1)); }
      }
      if(col > 0){
        boolean visible = board[row][col-1].visible;
        board[row][col-1].setVisible();
        if((board[row][col-1].number == 0) && !(visible)){ nullSetVisible(row, (col-1)); }
      }
      if(col < 28){
        boolean visible = board[row][col+1].visible;
        board[row][col+1].setVisible();
        if((board[row][col+1].number == 0) && !(visible)){ nullSetVisible(row, (col+1)); }
      }
      if((row < 15) && (col > 0)){
        boolean visible = board[row+1][col-1].visible;
        board[row+1][col-1].setVisible();
        if((board[row+1][col-1].number == 0) && !(visible)){ nullSetVisible((row+1), (col-1)); }
      }
      if(row < 15){
        boolean visible = board[row+1][col].visible;
        board[row+1][col].setVisible();
        if((board[row+1][col].number == 0) && !(visible)){ nullSetVisible((row+1), col); }
      }
      if((row < 15) && (col < 28)){
        boolean visible = board[row+1][col+1].visible;
        board[row+1][col+1].setVisible();
        if((board[row+1][col+1].number == 0) && !(visible)){ nullSetVisible((row+1), (col+1)); }
      }
    }
  }
  
  public void mousePressed(MouseEvent e){
    int x = e.getX();
    int y = e.getY();
    if(Math.sqrt((x - 300)*(x - 300) + (y - 40)*(y - 40)) <= 20){
      makeBoard();
      lose = false;
      timer = 0;
    }
    
    else if(((x < 600) && (x >= 0) && (y < 400) && (y >= 80)) && !lose){
      x = x / 20;
      y = (y - 80) / 20;
      
      if(SwingUtilities.isLeftMouseButton( e )){
        if(!(board[y][x].flagged)){
          board[y][x].setVisible();
          if(board[y][x].number == -1){//lose condition, click a mine(also turn that mine red
            this.lose = true;
            board[y][x].loseCell = true;
          }
          //if you click an empty cell
          if(board[y][x].number == 0){
            nullSetVisible(y, x);
          }
        }
      }
      else if(SwingUtilities.isRightMouseButton( e )){
        if(!(board[y][x].visible)){
          if(board[y][x].flagged){
            flagCount++;
          }
          else{
            flagCount--;
          }
          board[y][x].flag();
        }
      }
    }
  }
  
  public boolean hasEnded(){
    return false;
  }
  
  public static void main(String[] args){
    makeBoard();
    
    BigBang game = new BigBang(new Minesweeper());
    JFrame frame = new JFrame("MINESWEEPER");
    frame.getContentPane().add(game);
    frame.addMouseListener( game );
    frame.setVisible( true );
    frame.setSize(601, 401);//(600, 320)
    frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    game.start();
  }
  
  public static void makeBoard(){
    board = new Cell[16][30];
    
    ArrayList<Integer> randNums = new ArrayList<Integer>();
    while(randNums.size() < 99){//get some random mine positions
      int randNum = 1 + (int)(Math.random() * 479);
      if(!(randNums.contains(randNum))){
        randNums.add(randNum);
      }
    }
    for(int i : randNums){//put those mines in the board
      int row = i / 30;
      int col = i % 30;
      board[row][col] = new Cell(-1, (col * 20), ((row * 20) + 80));
    }
    for(int j = 0; j < board.length; j++){//fill the rest of the board
      for(int k = 0; k < board[j].length; k++){
        if(board[j][k] == null){//only make a new cell if there is no cell there
          int mineCount = 0;
          
          if((j > 0) && (k > 0)){
            if(board[j-1][k-1] != null){
              if(board[j-1][k-1].number == -1){ mineCount++; }
            }
          }
          if(j > 0){
            if(board[j-1][k] != null){
              if(board[j-1][k].number == -1){ mineCount++; }
            }
          }
          if((j > 0) && (k < 28)){
            if(board[j-1][k+1] != null){
              if(board[j-1][k+1].number == -1){ mineCount++; }
            }
          }
          if(k > 0){
            if(board[j][k-1] != null){
              if(board[j][k-1].number == -1){ mineCount++; }
            }
          }
          if(k < 28){
            if(board[j][k+1] != null){
              if(board[j][k+1].number == -1){ mineCount++; }
            }
          }
          if((j < 15) && (k > 0)){
            if(board[j+1][k-1] != null){
              if(board[j+1][k-1].number == -1){ mineCount++; }
            }
          }
          if(j < 15){
            if(board[j+1][k] != null){
              if(board[j+1][k].number == -1){ mineCount++; }
            }
          }
          if((j < 15) && (k < 28)){
            if(board[j+1][k+1] != null){
              if(board[j+1][k+1].number == -1){ mineCount++; }
            }
          }
          
          board[j][k] = new Cell(mineCount, (k * 20), ((j * 20) + 80));
        }
      }
    }
  }
}