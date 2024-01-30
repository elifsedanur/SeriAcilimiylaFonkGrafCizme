import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FunctionGraph extends JFrame implements ActionListener{
    private JTextField func;
    private JLabel funclabel;
    private JTextField point;
    private JLabel pointlabel;
    private JButton draw;
    private JButton up;
    private JButton down;
    //private JCheckBox toogle;
    private JPanel panel;
    private JPanel panel2;
    private JPanel panel3;
    String exp;
    Exp function;
    int x0;
    Double[][] xPoints = new Double[10][500];
    int[][] xPix = new int[10][500];
    Double[][] yPoints = new Double[10][500];
    int[][] yPix = new int[10][500];
    boolean first = true;
    int seri = 0;

    
    public FunctionGraph() {
        super("Fonksiyon Grafiği Çizme");
       
        panel2 = new JPanel();
        panel2.setLayout(new GridLayout(2,2));
        
        funclabel = new JLabel("Grafiği çizilecek fonksiyon");
        pointlabel = new JLabel("Seriye Açilacaği nokta");
        panel2.add(funclabel);
        panel2.add(pointlabel);
        func = new JTextField(20);
        point = new JTextField(10);
        panel2.add(func);
        panel2.add(point);
        add(panel2,BorderLayout.NORTH);
       
       
        panel3 = new JPanel();
        panel3.setLayout(new GridLayout(1,3));
        draw = new JButton("Çiz");
        up = new JButton("Yukari");
        down = new JButton("Asaği");
        
        panel3.add(draw);
        panel3.add(up);
        panel3.add(down);
        draw.addActionListener(this);
        up.addActionListener(this);
        down.addActionListener(this);
        add(panel3,BorderLayout.SOUTH);
       
        panel = new FunctionPanel(0);
        add(panel,BorderLayout.CENTER);

        
    }
    public static void main(String[] args) {
        FunctionGraph graph = new FunctionGraph();
        graph.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        graph.setSize(500, 600);
        graph.setVisible(true);
    }

   
    @Override
    public void actionPerformed(ActionEvent e)
    {
       if(e.getSource() == draw){
        try{
            exp = func.getText();
            x0 = Integer.parseInt( point.getText()); 
            InputStream stream = new ByteArrayInputStream(exp.getBytes());

        function = new FuncParser(stream).Start();
        }catch(Exception ex){
            System.out.println("Syntax error: " + ex.getMessage());
        }
        functionPoints(function);
        Donusum();
        ((FunctionPanel)panel).drawFunction(xPix, yPix);
         }
         else if(e.getSource() == up){
            seriAcilim();
            Donusum();
            ((FunctionPanel)panel).drawFunction(xPix, yPix);
         }
         else if(e.getSource() == down){
            geriButton();
           // Donusum();
            ((FunctionPanel)panel).drawFunction(xPix, yPix);
         } 
    }
    public void functionPoints(Exp e){
        //x=2 icin degerlendirme
      //double a = ((Double)new EvalVisitor(2).visit(e)).doubleValue();
      int indis = 0;
      for(Double i = -25.0; i <= 25.0; i+=0.1){
        xPoints [seri][indis] = i;
        yPoints [seri][indis] = ((Double)new EvalVisitor(i).visit(e)).doubleValue();
        //System.out.println("Result: " + yPoints[indis]);
        
        indis++;
      }
     
    }
    private void Donusum(){
        for(int i = 0; i < xPoints[seri].length; i++)
        {
            xPix[seri][i] = (int)(panel.getWidth()/2 + xPoints[seri][i]/0.1);
            yPix[seri][i] = (int)(panel.getHeight()/2 - yPoints[seri][i]/0.1);
            //System.out.println("(" + xPix[i]+","+yPix[i]+")");
        }
    }
    private void seriAcilim(){
        //f(x) = f(x0) + (x-x0)*f'(x0) + (x-x0)^2*f''(x0)/2!+....

        Double x = ((Double)new EvalVisitor(x0).visit(function)).doubleValue();
        int fact = 1;
        int temp = seri-1;
        seri++;
        if(first){
            int indis = 0;
            for(Double i = -25.0; i <= 25.0; i+=0.1){
                xPoints[seri][indis] = i;
                yPoints[seri][indis] = x;
                indis++;
            }
            first = !first;
            return;
        }
        while(temp > 1){
            fact *= temp;
            temp--;
        }
        function = (Exp)new DeriveVisitor("x").visit(function);
        int post = seri -1;
        Double fturevx0 =((Double)new EvalVisitor(x0).visit(function)).doubleValue();
        for(int i = 0; i < xPoints[post].length; i++){
            xPoints[seri][i] = xPoints[post][i];
            yPoints[seri][i] = yPoints[post][i] + (Math.pow((xPoints[post][i]-x0),seri-1)*fturevx0)/fact;
            System.out.println("(" + xPoints[seri][i]+","+yPoints[seri][i]+")");
        }

    }
    private void geriButton(){
        for(int i = 0; i < xPoints[seri].length; i++){
            xPix[seri][i] = 0;
            yPix[seri][i] = 0;
        }
    }
    
}
