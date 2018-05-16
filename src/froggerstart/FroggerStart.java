/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package froggerstart;

import java.io.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;
 
public class FroggerStart extends JFrame implements Runnable {
     boolean animateFirstTime = true;

    Image image;
    Graphics2D g;
      
    
    Frog frog;
    Road road;
    River river;
    int countd;
    int framerate;
    double timeCount;
    int numCarsRow3 = 3; 
    Car carRow3[] = new Car [numCarsRow3];
    Car carRow2[] = new Car [numCarsRow3];
    Car carRow1[] = new Car [numCarsRow3];
    Log logBot[] = new Log [2];
    Log logMid[] = new Log [1];
    Log logTop[] = new Log [2];
    boolean gameover;
    
    static FroggerStart frame1;

    public static void main(String[] args) {
        frame1 = new FroggerStart();
        frame1.setSize(Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setVisible(true);
    }

    public FroggerStart() {

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.BUTTON1 == e.getButton()) {
                    //left button
                }
                if (e.BUTTON3 == e.getButton()) {
                    //right button
                    reset();
                }
                repaint();
            }
        });

    addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseDragged(MouseEvent e) {
        repaint();
      }
    });

    addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseMoved(MouseEvent e) {
        repaint();
      }
    });

        addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
                if(gameover)
                    return;
                if (e.VK_RIGHT == e.getKeyCode())
                {
                    frog.moveRight();
                }
                if (e.VK_LEFT == e.getKeyCode())
                {
                    frog.moveLeft();

                }
                if (e.VK_UP == e.getKeyCode())
                {
                    frog.moveUp();
 
                }
                if (e.VK_DOWN == e.getKeyCode())
                {
                    frog.moveDown();
 
                }

                repaint();
            }
        });
        init();
        start();
    }




    Thread relaxer;
////////////////////////////////////////////////////////////////////////////
    public void init() {
        requestFocus();
    }
////////////////////////////////////////////////////////////////////////////
    public void destroy() {
    }
////////////////////////////////////////////////////////////////////////////
    public void paint(Graphics gOld) {
        if (image == null || Window.xsize != getSize().width || Window.ysize != getSize().height) {
            Window.xsize = getSize().width;
            Window.ysize = getSize().height;
            image = createImage(Window.xsize, Window.ysize);
            g = (Graphics2D) image.getGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
        }

//fill background
        g.setColor(Color.cyan);

        g.fillRect(0, 0, Window.xsize, Window.ysize);

        int x[] = {Window.getX(0), Window.getX(Window.getWidth2()), Window.getX(Window.getWidth2()), Window.getX(0), Window.getX(0)};
        int y[] = {Window.getY(0), Window.getY(0), Window.getY(Window.getHeight2()), Window.getY(Window.getHeight2()), Window.getY(0)};
//fill border
        g.setColor(Color.white);
        g.fillPolygon(x, y, 4);
// draw border
        g.setColor(Color.red);
        g.drawPolyline(x, y, 5);

        if (animateFirstTime) {
            gOld.drawImage(image, 0, 0, null);
            return;
        }
        
        river.draw(g);
        road.draw(g);
        
             for(int i=0;i<logBot.length;i++)
        {
            logBot[i].draw(g);
            logTop[i].draw(g); 
        }
             for(int i=0;i<logMid.length;i++)
        {
            logMid[i].draw(g);
        }
        for(int i=0;i<numCarsRow3;i++)
        {
            carRow3[i].draw(g);
            carRow2[i].draw(g);
            carRow1[i].draw(g);
            
        }
        
        frog.draw(g);
        

         if(gameover)
         {
                        g.setColor(Color.black);
                        g.setFont(new Font("Arial Black",Font.PLAIN,45));
                        g.drawString("Game Over", 100 ,300);
         }
            g.setColor(Color.black);
                 g.setFont(new Font("Arial Black",Font.PLAIN,15));
                 g.drawString("" + countd,200,50); 
                 
        gOld.drawImage(image, 0, 0, null);
    }
    
    
////////////////////////////////////////////////////////////////////////////
// needed for     implement runnable
    public void run() {
        while (true) {
            animate();
            repaint();
            double seconds = 0.04;    //time that 1 frame takes.
            int miliseconds = (int) (1000.0 * seconds);
            try {
                Thread.sleep(miliseconds);
            } catch (InterruptedException e) {
            }
        }
    }
/////////////////////////////////////////////////////////////////////////
    public void reset() {        

        frog = new Frog(8);
        road = new Road(120,100);
        river = new River(320,149);

                     for(int i=0;i<logBot.length;i++)
        {
            logBot[i] = new Log(2,river,3);
            logTop[i] = new Log(2,river,1);
        }
             for(int i=0;i<logMid.length;i++)
        {
            logMid[i]= new Log(-2,river,2);
        }
        gameover=false;
         for(int i=0;i<numCarsRow3;i++)
        {
            carRow2[i] = new Car(4,road,1);
            carRow1[i] = new Car(11,road,2);
            carRow3[i] = new Car(8,road,3);
        }
         timeCount= 30;
         countd = 13;
         framerate = 40;
      
    }
/////////////////////////////////////////////////////////////////////////
    public void animate() {

        if (animateFirstTime) {
            animateFirstTime = false;
            if (Window.xsize != getSize().width || Window.ysize != getSize().height) {
                Window.xsize = getSize().width;
                Window.ysize = getSize().height;
            }
            reset();
        }
         for(int i=0;i<numCarsRow3;i++)
        {
            if(carRow3[i].move(frog))
            {
                gameover = true;
            }
                
           if(carRow2[i].move(frog))
           {
               gameover = true;
           }
               
            if(carRow1[i].move(frog))
            {
                gameover=true;
            }
        }
//Frog is in the River         
         boolean frogOnLog = false;
        for(int i=0;i<logBot.length;i++)
        {
            if(logBot[i].move(frog))

                frogOnLog=true;
            if(logTop[i].move(frog))
                frogOnLog=true;
        }
        for(int i=0;i<logMid.length;i++)
        {        
            if(logMid[i].move(frog))
                frogOnLog=true;
        }
        boolean froginRiver = river.in(frog);
       if(countd == 0)
         countd=0;
        if(frog.gameOver() || countd == 0 || froginRiver && !frogOnLog)
            gameover=true;
        if(timeCount % framerate == framerate -1 )
            countd--;
        for(int i=0;i<carRow3.length;i++)
        {
        if(carRow3[i].collide(frog))
            gameover=true;
        if(carRow2[i].collide(frog))
            gameover=true;
        if(carRow1[i].collide(frog))
            gameover=true;
        }
        
        
        timeCount++;
    }

////////////////////////////////////////////////////////////////////////////
    public void start() {
        if (relaxer == null) {
            relaxer = new Thread(this);
            relaxer.start();
        }
    }
////////////////////////////////////////////////////////////////////////////
    public void stop() {
        if (relaxer.isAlive()) {
            relaxer.stop();
        }
        relaxer = null;
    }

}
/////////////////////////////////////////////////////////
class Road {
    private int top;
    private int width;
    Road(int _top,int _width)
    {
        top = _top;
        width = _width;
    }
    public int getTop()
    {
        return (top);
    }
    public int getWidth()
    {
        return (width);
    }
    public void draw(Graphics2D g)
    {
        g.setColor(Color.black);
        g.fillRect(Window.getX(0),
        Window.getYNormal(top),
        Window.getWidth2(),
        width);
        
        g.setColor(Color.yellow);
        for (int xval=0;xval<Window.getWidth2();xval+=20)
        {
            g.fillRect(Window.getX(xval),
            Window.getYNormal (top -width/3),
            12,4);
        }
        for (int xval=0;xval<Window.getWidth2();xval+=20)
        {
            g.fillRect(Window.getX(xval),
            Window.getYNormal (top -width*2/3),
            12,4);
        }      
    }
}
//////////////////////////////////////////////////////////////
class Car {
    private int xpos;
    private int ypos;
    private int speed;
    private Road road;
    private int lane;
    Car(int _speed,Road _road,int _lane)
    {
        lane = _lane;
        speed = _speed;
        xpos = (int)(Math.random()*Window.getWidth2());
        road = _road;
        if (lane == 1)
            ypos = road.getTop() - (road.getWidth()*1/6);
        else if (lane == 2)
            ypos = road.getTop() - (road.getWidth()*3/6);
        else if (lane == 3)
            ypos = road.getTop() - (road.getWidth()*5/6);
    }
    public boolean move(Frog frog)
    {
        if (xpos-20 < frog.getXPos() && xpos+20 > frog.getXPos() && ypos-12 < frog.getYPos() && ypos+12 > frog.getYPos() )
            return (true);
        xpos += speed;
        if (xpos < -60 && speed < 0)
            xpos = Window.getWidth2()+70;
        else if (xpos > Window.getWidth2()+50 && speed > 0)
            xpos = -68;
        return (false);
    }
    public void draw(Graphics2D g)
    {
        g.setColor(Color.red);
        drawCar(g,Window.getX(xpos),
        Window.getYNormal(ypos),0,1,1);
    }   
    public void drawCar(Graphics2D g,int xpos,int ypos,double rot,double xscale,double yscale)
    {
        g.translate(xpos,ypos);
        g.rotate(rot  * Math.PI/180.0);
        g.scale( xscale , yscale );
        g.fillRect(-20,-12,40,24);
        g.scale( 1.0/xscale,1.0/yscale );
        g.rotate(-rot  * Math.PI/180.0);
        g.translate(-xpos,-ypos);
    }
    public boolean collide(Frog frog)
    {
        if(xpos-20 < frog.getLeft()&& xpos+20 > frog.getLeft() && ypos-12 < frog.getTop() && ypos+12 > frog.getTop() )
            return(true);
        if(xpos-20 < frog.getRight()&& xpos+20 > frog.getRight() && ypos-12 < frog.getTop() && ypos+12 > frog.getTop() )
            return(true);
        if(xpos-20 < frog.getRight()&& xpos+20 > frog.getRight() && ypos-12 < frog.getBot() && ypos+12 > frog.getBot() )
            return(true);
        if(xpos-20 < frog.getLeft()&& xpos+20 > frog.getLeft() && ypos-12 < frog.getBot() && ypos+12 > frog.getBot() )
            return(true);  
        return(false);
    }
}
/////////////////////////////////////////////////////////////////////////////////
class Frog {
    private int xpos;
    private int ypos;
    private int speed;
    Frog(int _speed)
    {
        speed = _speed;
        xpos = Window.getWidth2()/2;
        ypos = 10;
    }
    public void draw(Graphics2D g)
    {
        g.setColor(Color.green);
        drawFrog(g,Window.getX(xpos),
        Window.getYNormal(ypos),0,1,1);
    }
    public void drawFrog(Graphics2D g,int xpos,int ypos,double rot,double xscale,double yscale)
    {
        g.translate(xpos,ypos);
        g.rotate(rot  * Math.PI/180.0);
        g.scale( xscale , yscale );
        g.fillRect(-10,-10,20,20);
        g.scale( 1.0/xscale,1.0/yscale );
        g.rotate(-rot  * Math.PI/180.0);
        g.translate(-xpos,-ypos);
    }
    public boolean gameOver()
    {
        if(xpos > Window.getWidth2() || xpos < 0 || ypos < 0 || ypos > Window.getHeight2())
        
            return(true);
        else  
            return(false);
        
    }
    public void move(int speed)
    {
        xpos+=speed;
    }
    public int getXPos()
    {
        return (xpos);
    }
    public int getYPos()
    {
        return (ypos);
    }
        public int getRight()
    {
        return (xpos+10);
    }    
        public int getLeft()
    {
        return (xpos-10);
    }    
        public int getTop()
    {
        return (ypos-10);
    }   
        public int getBot()
    {
        return (ypos+10);
    }
    
    public void moveRight()
    {
        xpos += speed;
    }
    public void moveLeft()
    {
        xpos -= speed;
    }
    public void moveUp()
    {
        ypos += speed;
    }
    public void moveDown()
    {
        ypos -= speed;
    }
}
///////////////////////////////////////////////////////////////////////////
class River {
    private int top;
    private int width;


           
    River(int _top,int _width)
    {
        top = _top;

        width = _width;
    }
    public int getTop()
    {
        return (top);
    }
    public int getWidth()
    {
        return (width);
    }
    public boolean in(Frog frog)
    {
       return(top > frog.getYPos() && frog.getYPos() > top-width);
          
    }
    public void draw(Graphics2D g)
    {
       
            g.setColor(Color.blue);
            g.fillRect(Window.getX(0), Window.getYNormal(top),Window.getWidth2(),width);

    }
 
}
class Log {
    private int xpos;
    private int ypos;
    private int speed;
    private River river;
    private int lane;
    Log(int _speed,River _river,int _lane)
    {
        lane = _lane;
        speed = _speed;
        xpos = (int)(Math.random()*Window.getWidth2());
        river = _river;
        if (lane == 1)
            ypos = river.getTop() - (river.getWidth()*1/6);
        else if (lane == 2)
            ypos = river.getTop() - (river.getWidth()*3/6);
        else if (lane == 3)
            ypos = river.getTop() - (river.getWidth()*5/6);
    }
    public boolean move(Frog frog)
    {
        boolean onLog=false;
        if (xpos+50 > frog.getXPos() && xpos-50 < frog.getXPos() && ypos+25 > frog.getYPos() && ypos-25 < frog.getYPos() )
        {
            frog.move(speed);
            onLog=true;
        }
        xpos += speed;
        if (xpos < -60 && speed < 0)
            xpos = Window.getWidth2()+78;
        else if (xpos > Window.getWidth2()+100 && speed > 0)
            xpos = -68;
        return (onLog);
    }
    public void draw(Graphics2D g)
    {
        g.setColor(Color.lightGray);
        drawLog(g,Window.getX(xpos),
        Window.getYNormal(ypos),0,1,1);
    }   
    public int getX()
    {
        return (xpos);
    }
    public int getY()
    {
        return (ypos);
    }    
    public void drawLog(Graphics2D g,int xpos,int ypos,double rot,double xscale,double yscale)
    {
        g.translate(xpos,ypos);
        g.rotate(rot  * Math.PI/180.0);
        g.scale( xscale , yscale );
        g.fillRect(-50,-25,100,50);
        g.scale( 1.0/xscale,1.0/yscale );
        g.rotate(-rot  * Math.PI/180.0);
        g.translate(-xpos,-ypos);
    }    
}


////////////////////////////////////////////////////////////////////
class Window
{
    static final int XBORDER = 0;
    static final int YBORDER = 20;
    static final int YTITLE = 30;
    static final int WINDOW_BORDER = 8;
    static final int WINDOW_WIDTH = 2*(WINDOW_BORDER + XBORDER) + 412;
    static final int WINDOW_HEIGHT = YTITLE + WINDOW_BORDER + 2 * YBORDER + 451;
    static int xsize = -1;
    static int ysize = -1;

    public static int getX(int x) {
        return (x + XBORDER + WINDOW_BORDER);
    }

    public static int getY(int y) {
        return (y + YBORDER + YTITLE );
    }

    public static int getYNormal(int y) {
        return (-y + YBORDER + YTITLE + getHeight2());
    }
    
    public static int getWidth2() {
        return (xsize - 2 * (XBORDER + WINDOW_BORDER));
    }

    public static int getHeight2() {
        return (ysize - 2 * YBORDER - WINDOW_BORDER - YTITLE);
    }    
}
