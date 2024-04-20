import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends JFrame implements ActionListener {
    private final int id;
    private final String phone;
    JButton b1,b2,b3,b4;
    JPanel p;
    public HomePage(int id,String phone){
        this.id=id;
        this.phone=phone;
        this.setTitle("ABC Diagonostics");
        this.setBounds(300,300,500,500);

        p=new JPanel();
        p.setBounds(20,20,460,460);
        p.setLayout(new GridLayout(4,1,10,10));
        b1=new JButton("Book An Appointment");
        p.add(b1);
        b2=new JButton("Book A Test");
        p.add(b2);
        b3=new JButton("View Result");
        p.add(b3);
        b4=new JButton("Your Transactions");
        p.add(b4);
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        this.add(p);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==b1){
            new Appointment(this.id);
        }
        if(e.getSource()==b2){
            new Test(this.id);
        }
    }
}
