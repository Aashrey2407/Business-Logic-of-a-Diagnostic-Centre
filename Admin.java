import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Admin extends JFrame implements ActionListener {

    private JLabel l1;
    private JTextField t1, t2;
    private JButton b1, b2, b3,b4,b5,b6,b7,b8,b9;
    private JTable jTable;

    public Admin() {
        this.setTitle("Admin Dashboard");
        this.setBounds(300, 300, 800, 700);
        this.setLayout(null);

        b1=new JButton("Appointments Done Today");
        b2=new JButton("Appointments This Week");
        b3=new JButton("Appointments This Month");
        b4=new JButton("Tests Done Today");
        b5=new JButton("Tests This Week");
        b6=new JButton("Tests This Month");
        b7=new JButton("Test Count Today");
        b8=new JButton("Test Count Week");
        b9=new JButton("Test Count Month");
        l1=new JLabel("Appointments Analysis");

        b1.setBounds(10,10,200,50);
        b2.setBounds(220,10,200,50);
        b3.setBounds(440,10,200,50);
        b4.setBounds(10,80,200,50);
        b5.setBounds(220,80,200,50);
        b6.setBounds(440,80,200,50);
        b7.setBounds(10,150,200,50);
        b8.setBounds(220,150,200,50);
        b9.setBounds(440,150,200,50);
        l1.setBounds(20,210,200,20);
        jTable = new JTable();
        jTable.setBounds(10, 235, 780, 400);
        this.add(jTable);
        this.add(b1);
        this.add(b2);
        this.add(b3);
        this.add(b4);
        this.add(b5);
        this.add(b6);
        this.add(b7);
        this.add(b8);
        this.add(b9);
        this.add(l1);
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);
        b6.addActionListener(this);
        b7.addActionListener(this);
        b8.addActionListener(this);
        b9.addActionListener(this);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        l1.setText("Appointments"); // Reset label text
        String url="jdbc:mysql://localhost:3306/diagonostic_centre";
        String user="root";
        String password="MyRootUser";

        if (e.getSource() == b1) {
            l1.setText("Appointments Done Today");
            try (Connection con = DriverManager.getConnection(url, user, password)) {
                ResultSet rs = getAppointments(con, "{call get_today_appointments()}"); // Call separate method
                populateTable(rs); // Populate table if successful
            } catch (SQLException e1) {
                handleError(e1, "Fetching today's appointments");
            }
        } else if (e.getSource() == b2) {
            l1.setText("Appointments This Week");
            try (Connection con = DriverManager.getConnection(url, user, password)) {
                ResultSet rs = getAppointments(con, "{call get_week_appointments()}"); // Call separate method
                populateTable(rs); // Populate table if successful
            } catch (SQLException e1) {
                handleError(e1, "Fetching today's appointments");
            }
            // Implement logic to fetch appointments for this week (similar to today)
        } else if (e.getSource() == b3) {
            l1.setText("Appointments This Month");
            try (Connection con = DriverManager.getConnection(url, user, password)) {
                ResultSet rs = getAppointments(con, "{call get_month_appointments()}"); // Call separate method
                populateTable(rs); // Populate table if successful
            } catch (SQLException e1) {
                handleError(e1, "Fetching today's appointments");
            }
            // Implement logic to fetch appointments for this month (similar to today)
        }else if (e.getSource() == b4) {
            l1.setText("Tests Done Today");
            try (Connection con = DriverManager.getConnection(url, user, password)) {
                ResultSet rs = getAppointments(con, "{call tests_done_today()}"); // Call separate method
                populateTable(rs); // Populate table if successful
            } catch (SQLException e1) {
                handleError(e1, "Fetching today's appointments");
            }
            // Implement logic to fetch appointments for this month (similar to today)
        }else if (e.getSource() == b5) {
            l1.setText("Tests This Week");
            try (Connection con = DriverManager.getConnection(url, user, password)) {
                ResultSet rs = getAppointments(con, "{call tests_done_week()}"); // Call separate method
                populateTable(rs); // Populate table if successful
            } catch (SQLException e1) {
                handleError(e1, "Fetching today's appointments");
            }
            // Implement logic to fetch appointments for this month (similar to today)
        }else if (e.getSource() == b6) {
            l1.setText("Tests This Month");
            try (Connection con = DriverManager.getConnection(url, user, password)) {
                ResultSet rs = getAppointments(con, "{call tests_done_month()}"); // Call separate method
                populateTable(rs); // Populate table if successful
            } catch (SQLException e1) {
                handleError(e1, "Fetching today's appointments");
            }
            // Implement logic to fetch appointments for this month (similar to today)
        }else if (e.getSource() == b7) {
            l1.setText("Tests By Count Today");
            try (Connection con = DriverManager.getConnection(url, user, password)) {
                ResultSet rs = getAppointments(con, "{call tests_by_count_today()}"); // Call separate method
                populateTable(rs); // Populate table if successful
            } catch (SQLException e1) {
                handleError(e1, "Fetching today's appointments");
            }
            // Implement logic to fetch appointments for this month (similar to today)
        }else if (e.getSource() == b8) {
            l1.setText("Tests By Count Week");
            try (Connection con = DriverManager.getConnection(url, user, password)) {
                ResultSet rs = getAppointments(con, "{call tests_by_count_month()}"); // Call separate method
                populateTable(rs); // Populate table if successful
            } catch (SQLException e1) {
                handleError(e1, "Fetching today's appointments");
            }
            // Implement logic to fetch appointments for this month (similar to today)
        }else if (e.getSource() == b9) {
            l1.setText("Tests By Count Month");
            try (Connection con = DriverManager.getConnection(url, user, password)) {
                ResultSet rs = getAppointments(con, "{call tests_by_count_month()}"); // Call separate method
                populateTable(rs); // Populate table if successful
            } catch (SQLException e1) {
                handleError(e1, "Fetching today's appointments");
            }
            // Implement logic to fetch appointments for this month (similar to today)
        }
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
