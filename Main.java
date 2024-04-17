import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame{
    JButton b1,b2;
    public Main(){
        this.setTitle("ABC Diagonostic Centre");
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(300,300,500,500);
        b1=new JButton("Sign Up");
        b2=new JButton("Login");
        b1.setBounds(200,146,100,55);
        b2.setBounds(200,220,100,55);
        this.add(b1);
        this.add(b2);
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
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}
