import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Result extends JFrame implements ActionListener {
    private int patient_id;
    private JTable jTable;

    String url="jdbc:mysql://localhost:3306/diagonostic_centre";
    String user="root";
    String password="MyRootUser";

    public Result(int patient_id){
        this.patient_id=patient_id;
        this.setTitle("Your Test Results");
        this.setBounds(300, 300, 800, 700);
        this.setLayout(null);

        jTable = new JTable();
        jTable.setBounds(10, 40, 780, 400);
        this.add(jTable);

        try (Connection con = DriverManager.getConnection(url, user, password)) {
            ResultSet rs = getAppointments(con, "{call get_test_data("+patient_id+")}"); // Call separate method
            populateTable(rs); // Populate table if successful
        } catch (SQLException e1) {
            handleError(e1, "Fetching today's appointments");
        }

        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }
    private ResultSet getAppointments(Connection con, String procedureName) throws SQLException {
        CallableStatement cstmt = con.prepareCall(""+procedureName);
        cstmt.execute();
        return cstmt.getResultSet();
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
            JOptionPane.showMessageDialog(this, "No appointments found for today.", "Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    private void handleError(Exception e, String message) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error: " + message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
