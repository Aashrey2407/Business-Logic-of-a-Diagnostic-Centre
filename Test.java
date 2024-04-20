import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Test extends JFrame implements ActionListener {
    ArrayList<Integer> tests;
    int doctor_id;
    int patient_id;
    JLabel l,l1,l2,l3;
    JTextField t1,t2,t3;
    JButton b1,b2;
    public Test(int pat_id){
        doctor_id=0;
        tests=new ArrayList<>();
        this.patient_id=pat_id;
        this.setTitle("Test Booking");
        this.setBounds(300,300,500,500);
        this.setLayout(null);

        l=new JLabel("NOTE: you can book a maximum of 4 tests at a time");
        l.setBounds(20,4,400,20);
        this.add(l);

        l1=new JLabel("Doctor id:");
        l1.setBounds(20,40,100,20);
        t1=new JTextField();
        t1.setBounds(140,40,200,20);
        this.add(l1);
        this.add(t1);

        l2=new JLabel("Test Type: ");
        l2.setBounds(20,70,100,20);
        t2=new JTextField();
        t2.setBounds(140,70,100,20);
        this.add(l2);
        this.add(t2);

        b2=new JButton("Add Test");
        b2.setBounds(80,130,100,50);
        this.add(b2);
        b2.addActionListener(this);

        b1=new JButton("Book Tests");
        b1.setBounds(200,130,100,50);
        this.add(b1);
        b1.addActionListener(this);

        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==b1){
            if (tests.isEmpty()) {
                // Show error message for no tests added
                JFrame f = new JFrame();
                f.setTitle("Error");
                JLabel text = new JLabel("Please add at least one test");
                f.add(text);
                f.setVisible(true);
                return;  // Exit the method if no tests added
            }
            JFrame f=new JFrame();
            f.setLayout(null);
            f.setBounds(500,500,600,500);
            Database db=new Database();
            System.out.println("I reached here 1\n");
            int trans_id=db.bookTest(this.tests,this.patient_id,doctor_id);
            ArrayList<String> test_names=new ArrayList<>();
            ArrayList<Integer> test_prices=new ArrayList<>();
            int i=tests.size()-1;
            int total_price=0;
            while(i>=0){
                ArrayList<Object> a=db.getTestDetails(tests.get(i));
                test_names.add((String)a.get(0));
                test_prices.add((int)a.get(1));
                total_price+=(int)a.get(1);
                i--;
            }
            System.out.println("I reached here 2\n");
            if(trans_id!=-1){
                f.setTitle("Test Booked Successfully");
                JLabel text=new JLabel("Your Transaction id for the booking of test is :"+trans_id );
                text.setBounds(10,10,400,20);
                JTextArea ta=new JTextArea();
                ta.setBounds(5,90,590,300);
                i=tests.size()-1;
                while(i>=0){
                    ta.append("Test "+(tests.size()-i)+": "+test_names.get(i)+" , price: "+test_prices.get(i)+"\n");
                    i--;
                }
                JLabel l=new JLabel("Total Price: "+total_price);
                l.setBounds(250,400,100,20);
                f.add(l);
                f.add(text);
                f.add(ta);
                f.setVisible(true);
                this.hide();
            }
            else{
                f.setTitle("Test Booking Falied");
                JLabel text=new JLabel("Transaction has failed plz try again");
                f.add(text);
                f.setVisible(true);
                }
//            int i=tests.size()-1;
//            while(i>=0){
//                System.out.println((int)tests.get(i)+"\n");
//                i--;
//            }
        }
        else if(e.getSource()==b2){
            try{
                    if(this.doctor_id==0)
                        this.doctor_id=Integer.parseInt(t1.getText());
                    int test_id=Integer.parseInt(t2.getText());
                    tests.add(test_id);
                    this.t2.setText("");
                    this.t1.setEnabled(false);
            }
            catch(Exception e1){
                e1.printStackTrace();
                JFrame f=new JFrame();
                f.setBounds(500,500,500,100);
                f.setTitle("ERROR!!!");
                JLabel l=new JLabel("Please enter both the fields");
                f.add(l);
                f.setVisible(true);
            }
        }
    }
}
