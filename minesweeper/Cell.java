import java.awt.*;


class Cell{
  int number;//0 is an empty cell, [1..9] is valid, -1 is a mine
  int size = 20;
  int x;
  int y;
  Color color;
  boolean visible;//has it been clicked yet?
  boolean flagged;//flag on the square, cannot be clicked
  boolean loseCell = false;//this is only ever true if it was the cell that lose the game, that way you can turn it red
  
  Cell(int number, int x, int y){
    this.number = number;
    this.x = x;
    this.y = y;
    this.visible = false;
    this.flagged = false;
    
    
    if(number == -1){
      this.color = Color.BLACK;
    }
    else if(number == 1){//light blue
      this.color = new Color(0,0,182,155);
    }
    else if(number == 2){//dirty green
      this.color = Color.GREEN;
    }
    else if(number == 3){//orange tinted red
      this.color = Color.RED;
    }
    else if(number == 4){//purple tinted dark blue
      this.color = Color.BLUE;
    }
    else if(number == 5){//red tinted brown
      this.color = new Color(156, 93, 82);
    }
    else if(number == 6){//flat teal
      this.color = Color.CYAN;
    }
    else if(number == 7){//black
      color = color.BLACK;
    }
    else if(number == 8){//dark gray
      color = color.DARK_GRAY;
    }
  }
  void draw(Graphics g){
    //draw the square cells
    if(!(this.visible)){
      g.setColor(Color.LIGHT_GRAY);
      
    }
    else{
      g.setColor(new Color(220, 220, 220, 230));
    }
    g.fillRect(x, y, size, size);
    g.setColor(Color.DARK_GRAY);
    g.drawRect(x, y, size, size);

    if(this.visible && (this.number != 0) && (this.number != -1)){//display the number
      g.setColor(this.color);
      g.setFont(new Font("Serif Bold", Font.BOLD, 15));
      g.drawString(Integer.toString(number), (x + 6), (y + 16));
    }
    
    if(this.flagged && !(this.visible)){//draw a flag
      g.setColor(Color.BLACK);//draw a post
      g.fillRect((x + 4), (y + 13), 14, 3);
      g.fillRect((x + 10), (y + 3), 2, 12);
      
      g.setColor(Color.RED);//draw a triangle
      int[] xPoints = {(x + 10), (x + 10), (x + 4)};
      int[] yPoints = {(y + 3), (y + 11), (y + 7)};
      int nPoints = 3;
      g.fillPolygon(xPoints, yPoints, nPoints);
    }
    
    if(this.visible && (this.number == -1)){//draw a mine, aka a polygon and a circle
      g.setColor(this.color);
      if(this.loseCell){ g.setColor(Color.RED); }
      g.fillOval((x + 5), (y + 5), 10, 10);
      g.fillRect((x + 9), (y + 3), 2, 14);
      g.fillRect((x + 3), (y + 9), 14, 2);
    }
  }
  
  public void flag(){
    this.flagged = !this.flagged;
  }
  
  public void setVisible(){
    this.visible = true;
  }
}