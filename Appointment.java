import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
public class Appointment extends JFrame implements ActionListener {
    int patient_id;
    JLabel l1,l2;
    JTextField t1,t2;
    JButton b1;
    public Appointment(int pat_id){
        this.patient_id=pat_id;
        this.setTitle("Appointment Booking");
        this.setBounds(300,300,500,500);
        this.setLayout(null);

        l1=new JLabel("Doctor id:");
        l1.setBounds(20,20,100,20);
        t1=new JTextField();
        t1.setBounds(140,20,200,20);
        this.add(l1);
        this.add(t1);

        l2=new JLabel("Date: ");
        l2.setBounds(20,50,100,20);
        t2=new JTextField();
        t2.setBounds(140,50,100,20);
        this.add(l2);
        this.add(t2);

        b1=new JButton("Book Appointment");
        b1.setBounds(200,100,100,50);
        this.add(b1);
        b1.addActionListener(this);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Database db=new Database();
        int x=db.bookAppointment(Integer.parseInt(t1.getText()),patient_id,Date.valueOf(t2.getText()));
        JFrame f=new JFrame();
        f.setBounds(500,500,250,100);
        if(x!=0){
            f.setTitle("Appointment Booked");
            JLabel l=new JLabel("Appointment number: "+x);
            l.setBounds(50,50,100,20);
            f.add(l);
            f.setVisible(true);
        }
        else{
            f.setTitle("Booking Failed");
            JLabel l=new JLabel("Appointment could not be done");
            l.setBounds(50,50,100,20);
            f.add(l);
            f.setVisible(true);
        }
    }
}
