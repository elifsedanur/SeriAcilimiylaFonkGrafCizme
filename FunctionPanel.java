import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class FunctionPanel extends JPanel {
    int point;
    int[][] xPoints;
    int[][] yPoints;

    public FunctionPanel(int a) {
        super();
        point = a;
        setSize(500, 400);
        repaint();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        CoordinatePane(g);
        if(xPoints != null && yPoints != null){
            int indis = 0;
            g.setColor(Color.RED);
            while(indis < 10){
                for(int i = 0; i < xPoints[indis].length-1; i++){
                    //g.drawOval(xPoints[indis][i], yPoints[indis][i], 1, 1);
                    g.drawLine(xPoints[indis][i], yPoints[indis][i], xPoints[indis][i+1], yPoints[indis][i+1]);
                }
                g.setColor(Color.BLUE);
                indis++;
            }   
            
        }
       
   
        
    }
    private void CoordinatePane(Graphics g){
       // Draw x-axis
       g.drawLine(0,  getHeight()  / 2, getWidth(),  getHeight()  / 2);

       // Draw y-axis
       g.drawLine(getWidth() / 2, 0, getWidth() / 2,  getHeight() );

       // Optional: Label the axes
       g.drawString("X", getWidth() - 10, getHeight() / 2 - 5);
       g.drawString("Y", getWidth() / 2 + 5, 10);
      
       //Her bir birim x için 12 piksel aralık var
       //x in her 0.1 değeri 1 piksele denk düşer
       
       int xPix = getWidth()/2 -20;
       for(int i = 1; xPix > 0; i++){
            g.setColor(Color.BLACK);
            g.drawString(""+(-2)*i, xPix-5,  getHeight() / 2 - 5);
            g.setColor(Color.RED);
           g.drawOval(xPix, getHeight()/2, 1, 1);
           xPix -= 20;
       }   
     
       xPix = getWidth()/2 +20;
       for(int i = 1; xPix < getWidth(); i++){
           g.setColor(Color.BLACK);
           g.drawString(""+(2)*i, xPix-5,  getHeight() / 2 - 5);
           g.setColor(Color.RED);
           g.drawOval(xPix, getHeight()/2, 1, 1);
           xPix += 20;
       } 
       
       int yPix = getHeight() / 2 -20;
       for(int i = 1; yPix > 0; i++){
        g.setColor(Color.BLACK);
            g.drawString("" +2*i, getWidth() / 2 -15, yPix);
            g.setColor(Color.RED);
            g.drawOval(getWidth() / 2,yPix, 1, 1);
           yPix -=20;
       }   
       yPix = getHeight() / 2 +20;
       for(int i = 1; yPix <getHeight(); i++){
        //  System.out.println(yPix);
            g.setColor(Color.BLACK);
           g.drawString("-" +2*i, getWidth() / 2 + 5, yPix);
           g.setColor(Color.RED);
           g.drawOval(getWidth() / 2,yPix, 1, 1);
           yPix +=20;
       }   
    }
    public void drawFunction(int[][] x, int[][] y){
        xPoints = x;
        yPoints = y;
        repaint();

    }
       
    
}
