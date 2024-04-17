import java.sql.*;
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
                if(rs.getString(1).equals(phone))
                    return true;
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
            pstmt.setDate(3,date);

            pstmt.executeUpdate();
            ResultSet rs=pstmt.getGeneratedKeys();
            int apt_id;
            if(rs.next()){
                apt_id=rs.getInt(1);
                return apt_id;
            }
            return 0;
        }
        catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }
}
