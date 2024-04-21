import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Employee_Homepage extends JFrame implements ActionListener {
    private int emp_id;
    private JTable jTable;
    JLabel l1,l2,l3,l4,l5;
    JTextField t1,t2;
    JTextArea ta1;
    JButton b1,b2;
    public Employee_Homepage(int emp_id){
        this.emp_id=emp_id;
        this.setTitle("Pending Tests");
        this.setBounds(300, 300, 800, 800);
        this.setLayout(null);

        l4=new JLabel("Transaction_id");
        l5=new JLabel("Test_id");
        jTable = new JTable();
        jTable.setBounds(10, 40, 780, 360);
        l4.setBounds(10,20,100,20);
        l5.setBounds(400,20,100,20);
        this.add(l4);
        this.add(l5);
        this.add(jTable);

        l1=new JLabel("Transaction_id: ");
        l2=new JLabel("Test_id: ");
        l3=new JLabel("Test_Result: ");
        t1=new JTextField();
        t2=new JTextField();
        ta1=new JTextArea("Type The Result here....");
        b1=new JButton("Fetch Pending Tests");
        b2=new JButton("Submit Results");

        l1.setBounds(10,420,150,20);
        t1.setBounds(170,420,200,20);
        l2.setBounds(10,450,150,20);
        t2.setBounds(170,450,200,20);
        l3.setBounds(10,480,200,20);
        ta1.setBounds(10,500,700,200);
        b1.setBounds(150,720,200,50);
        b2.setBounds(400,720,200,50);

        this.add(l1);
        this.add(l2);
        this.add(l3);
        this.add(t1);
        this.add(t2);
        this.add(ta1);
        this.add(b1);
        this.add(b2);

        b1.addActionListener(this);
        b2.addActionListener(this);

        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String url="jdbc:mysql://localhost:3306/diagonostic_centre";
        String user="root";
        String password="MyRootUser";
        if(e.getSource()==b1){
            try (Connection con = DriverManager.getConnection(url, user, password)) {
                ResultSet rs = getPendingTests(con, "{call get_pending_results()}"); // Call separate method
                populateTable(rs); // Populate table if successful
            } catch (SQLException e1) {
                handleError(e1, "Fetching today's appointments");
            }
        }
        if(e.getSource()==b2){
            int transaction_id=Integer.parseInt(t1.getText());
            int test_id=Integer.parseInt(t2.getText());
            String res=ta1.getText();
            Database db=new Database();
            boolean f=db.addResults(transaction_id,test_id,emp_id,res);
            if(f){
                JOptionPane.showMessageDialog(null, "Test result updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                JOptionPane.showMessageDialog(null, "No rows updated. Check transaction_id and test_id.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private void populateTable(ResultSet rs) throws SQLException {
        if (rs != null) {
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            DefaultTableModel tableModel = new DefaultTableModel();
            for (int i = 1; i <= columnCount; i++) {
                tableModel.addColumn(rsmd.getColumnName(i));
            }

            while (rs.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    rowData[i - 1] = rs.getObject(i); // Get value based on data type
                }
                tableModel.addRow(rowData);
            }
            jTable.setModel(tableModel);
        } else {
            // Handle no data scenario (e.g., display a message)
            JOptionPane.showMessageDialog(this, "No Pending Tests found for today.", "Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    private void handleError(Exception e, String message) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error: " + message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    private ResultSet getPendingTests(Connection con, String procedureName) throws SQLException {
        CallableStatement cstmt = con.prepareCall(""+procedureName);
        cstmt.execute();
        return cstmt.getResultSet();
    }
}
