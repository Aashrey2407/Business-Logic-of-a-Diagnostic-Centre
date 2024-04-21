import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame{
    JButton b1,b2,b3,b4;
    public Main(){
        this.setTitle("ABC Diagonostic Centre");
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(300,300,500,500);
        b1=new JButton("Sign Up");
        b2=new JButton("Login");
        b3=new JButton("Admin Dashboard");
        b4=new JButton("Employee Login");
        b1.setBounds(200,126,100,55);
        b2.setBounds(200,200,100,55);
        b3.setBounds(157,274,200,55);
        b4.setBounds(160,348,200,55);
        this.add(b1);
        this.add(b2);
        this.add(b3);
        this.add(b4);
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SignUp();
            }
        });
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login();
            }
        });
        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Admin();
            }
        });
        b4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Employee();
            }
        });
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}
