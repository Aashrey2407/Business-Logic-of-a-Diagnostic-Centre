import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class Database {
    String url="jdbc:mysql://localhost:3306/diagonostic_centre";
    String user="root";
    String password="MyRootUser";
    public int addPatient(String fn,String ln,String ph,String gen,int age,String address){
        try{
            Connection con=DriverManager.getConnection(url,user,password);
            String q="INSERT INTO patient(first_name,last_name,phone,sex,age,address) VALUES(?,?,?,?,?,?)";
            PreparedStatement pstmt=con.prepareStatement(q,Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1,fn);
            pstmt.setString(2,ln);
            pstmt.setString(3,ph);
            pstmt.setString(4,gen);
            pstmt.setInt(5,age);
            pstmt.setString(6,address);
            pstmt.executeUpdate();
            ResultSet id=pstmt.getGeneratedKeys();
            id.next();
            int res= id.getInt(1);
            con.close();
            return res;
        }
        catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public boolean patientExists(int id,String phone){
        try{
            Connection con= DriverManager.getConnection(url,user,password);
            // here run the query SELECT patient_id,phone FROM patient WHERE patient_id=id;
            String q="SELECT phone FROM patient WHERE patient_id=?";
            PreparedStatement pstmt=con.prepareStatement(q);
            pstmt.setInt(1,id);
            ResultSet rs=pstmt.executeQuery();
            if(rs.next()){
                return rs.getString(1).equals(phone);
            }
            return false;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public boolean employeeExists(int id,String phone){
        try{
            Connection con= DriverManager.getConnection(url,user,password);
            // here run the query SELECT patient_id,phone FROM patient WHERE patient_id=id;
            String q="SELECT phone FROM employee WHERE emp_id=?";
            PreparedStatement pstmt=con.prepareStatement(q);
            pstmt.setInt(1,id);
            ResultSet rs=pstmt.executeQuery();
            if(rs.next()){
                return rs.getString(1).equals(phone);
            }
            return false;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public int bookAppointment(int doc_id,int pat_id,Date date){
        try{
            Connection con=DriverManager.getConnection(url,user,password);
            String q="INSERT INTO appointments(doctor_id,patient_id,date) VALUES(?,?,?)";
            PreparedStatement pstmt=con.prepareStatement(q,Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1,doc_id);
            pstmt.setInt(2,pat_id);
            pstmt.setDate(3, (java.sql.Date) date);

            pstmt.executeUpdate();
            ResultSet rs=pstmt.getGeneratedKeys();
            int apt_id;
            if(rs.next()){
                apt_id=rs.getInt(1);
                con.close();
                return apt_id;
            }
            return 0;
        }
        catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }
    public int bookTest(ArrayList<Integer> tests,int patient_id,int doctor_id){
        try{
            Connection con=DriverManager.getConnection(url,user,password);
            String q="INSERT INTO test_booking(doctor_id,patient_id,test_date) VALUES(?,?,?)";
            PreparedStatement pstmt=con.prepareStatement(q,Statement.RETURN_GENERATED_KEYS);
            Date test_date=new Date();
            pstmt.setInt(1,doctor_id);
            pstmt.setInt(2,patient_id);
            pstmt.setDate(3,new java.sql.Date(test_date.getTime()));
            pstmt.executeUpdate();
            System.out.println("reached here 1");
            ResultSet rs=pstmt.getGeneratedKeys();
            int transaction_id=-1;
            if(rs.next()){
                transaction_id=rs.getInt(1);
                con.close();
            }
            else
                return -1;

            System.out.println("reached here 2");
            Connection con2=DriverManager.getConnection(url,user,password);
            String q2="INSERT INTO tests_done VALUES(?,?)";
            int i=tests.size()-1;
            while(i>=0){
                PreparedStatement pstmt2=con2.prepareStatement(q2);
                pstmt2.setInt(1,transaction_id);
                pstmt2.setInt(2, tests.get(i));
                pstmt2.executeUpdate();
                i--;
            }
            System.out.println("reached here 3");
            con2.close();
            return transaction_id;
        }
        catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }
    public ArrayList<Object> getTestDetails(int test_id){
        ArrayList<Object> a=new ArrayList<Object>();
        try{
            Connection con= DriverManager.getConnection(url,user,password);
            // here run the query SELECT patient_id,phone FROM patient WHERE patient_id=id;
            String q="SELECT test_name,price FROM test WHERE test_id=?";
            PreparedStatement pstmt=con.prepareStatement(q);
            pstmt.setInt(1,test_id);
            ResultSet rs=pstmt.executeQuery();
            String testName="";
            int testPrice=0;
            if(rs.next()){
                testName=rs.getString(1);
                testPrice=rs.getInt(2);
            }
            a.add(testName);
            a.add(testPrice);
            con.close();
            return a;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public ResultSet Appointments_today(){
        try{
            Connection con=DriverManager.getConnection(url,user,password);
            CallableStatement cstmt = con.prepareCall("{get_today_appointments}"); // Assuming no parameters
            cstmt.execute();

            ResultSet rs = cstmt.getResultSet();
            return rs;
        }
        catch(Exception e){
            return null;
        }
    }
    public boolean addResults(int transaction_id,int test_id,int employee_id,String res){

        try {
            Connection con= DriverManager.getConnection(url,user,password);
            String sql = "UPDATE test_results SET employee_id = ?, results = ?, result_date = ? WHERE transaction_id = ? AND test_id = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            Date test_date=new Date();
            // Set the values in the prepared statement
            statement.setInt(1, employee_id);
            statement.setString(2, res);
            statement.setDate(3, new java.sql.Date(test_date.getTime())); // Convert to SQL Date
            statement.setInt(4, transaction_id); // Replace with actual transaction ID
            statement.setInt(5, test_id); // Replace with actual test ID

            // Execute the update
            int rowsUpdated = statement.executeUpdate();

            // Handle the update result
            if (rowsUpdated > 0) {
                statement.close();
                con.close();
                return true;
            } else {
                return false;
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
