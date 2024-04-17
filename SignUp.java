import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUp extends JFrame implements ActionListener{
    JLabel l1,l2,l3,l4,l5,l6;
    JTextField t1,t2,t3,t4,t5;
    JRadioButton b1,b2;
    ButtonGroup g;
    JButton b;
    public SignUp(){
        this.setTitle("Sign Up");
        this.setBounds(300,300,500,500);
        JPanel p=new JPanel();
        p.setLayout(new GridLayout(4,1));
        p.setBounds(10,10,480,400);
        JPanel p1=new JPanel();
        p1.setLayout(new GridLayout(3,2));
        l1=new JLabel("First Name:");
        t1=new JTextField();
        l2=new JLabel("Last Name:");
        t2=new JTextField();
        l3=new JLabel("Phone No.");
        t3=new JTextField();
        p1.add(l1);
        p1.add(t1);
        p1.add(l2);
        p1.add(t2);
        p1.add(l3);
        p1.add(t3);
        p.add(p1);

        JPanel p2=new JPanel();
        p2.setLayout(new GridLayout(1,3));
        g=new ButtonGroup();
        b1=new JRadioButton("Male");
        b2=new JRadioButton("Female");
        g.add(b1);
        g.add(b2);
        JLabel gender=new JLabel("Gender:");
        p2.add(gender);
        p2.add(b1);
        p2.add(b2);
        p.add(p2);

        JPanel p3=new JPanel();
        p3.setLayout(new GridLayout(2,2));
        l4=new JLabel("Age:");
        t4=new JTextField();
        l5=new JLabel("Address:");
        t5=new JTextField();
        p3.add(l4);
        p3.add(t4);
        p3.add(l5);
        p3.add(t5);
        p.add(p3);

        b=new JButton("Sign Up");
        //b.setBounds(500,500,100,40);
        b.addActionListener(this);
        p.add(b);

        this.add(p);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Disturbance@!!!!");
            String s="Male";
            if(b1.isSelected())
            s="Male" ;
            if(b2.isSelected())
                s="Female" ;
            if(e.getSource()==this.b){
                System.out.println("Got into the button");
                Database db=new Database();
                int x=db.addPatient(t1.getText(),t2.getText(),t3.getText(),s,Integer.parseInt(t4.getText()),t5.getText());
                if(x!=0)
                {
                    this.setVisible(false);
                    JFrame f=new JFrame();
                    f.setTitle("Account Created");
                    f.setBounds(500,500,200,100);
                    JPanel p4=new JPanel();
                    p4.setLayout(new FlowLayout());
                    JLabel l=new JLabel("Patient Id is "+x);
                    p4.add(l);
                    f.add(p4);
                    f.setVisible(true);
                }
                else{
                    JFrame f=new JFrame();
                    f.setTitle("ERROR!!!");
                    f.setBounds(500,500,200,100);
                    JPanel p4=new JPanel();
                    p4.setLayout(new FlowLayout());
                    JLabel l=new JLabel("Account Could'nt be created ");
                    p4.add(l);
                    f.add(p4);
                    f.setVisible(true);
                }
            }
    }
}
