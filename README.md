# SCHEDULING APP
## Aim
This application is used in educational management system. The main purpose is one staff member can create an appointment 
and the same staff member can send an invitation to other staff member for his/her created appointment.
## Features 
-make appointment
-update appointment
-view staff availabilities
-view & update profile of the particular staff
-appointment history
-accepted requests.
## Backend source code:

package MainPackage;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * REST Web Service
 *
 * @author chinni
 */
@Path("team3")
public class MobileApp {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of MobileApp
     */
    public MobileApp() {
    }

    /**
     * Retrieves representation of an instance of MainPackage.MobileApp
     * @return an instance of java.lang.String
     */
   @GET
    @Path("STAFF")
    @Produces("application/json")
    public String getjson() {

        long time = System.currentTimeMillis();
        JSONObject obj = new JSONObject();

        obj.accumulate("Status", "ok");
        obj.accumulate("TimeStamp", time);
        JSONArray mainJSON = new JSONArray();
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection con;
           
           
                con = DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE","madteam3","anypw1");
            
            
            Statement stm = con.createStatement();
            String sql = "SELECT * FROM MADTEAM3.STAFF";

            ResultSet rs = stm.executeQuery(sql);
            JSONObject singleStaff = new JSONObject();
            int staffId;
            String firstName, lastName, email, designation,phone,password;

            while (rs.next()) {
                staffId = rs.getInt("STAFFID");
                firstName = rs.getString("FIRSTNAME");
                lastName = rs.getString("LASTNAME");
                email = rs.getString("EMAIL");
                designation = rs.getString("DESIGNATION");
                phone = rs.getString("PHONE");
                password=rs.getString("PASSWORD");

               singleStaff .accumulate("STAFFID", staffId);
                singleStaff .accumulate("FIRSTNAME", firstName);
                singleStaff .accumulate("LASTNAME", lastName);
                singleStaff .accumulate("EMAIL", email);
                singleStaff .accumulate("DESIGNATION", designation);
                singleStaff .accumulate("PHONE", phone);
                singleStaff .accumulate("PASSWORD", password);

                

                mainJSON.add(singleStaff );
                singleStaff .clear();

            }
            obj.accumulate("List", mainJSON);
            rs.close();
            stm.close();
            con.close();

        } catch (ClassNotFoundException ex) { 
            Logger.getLogger(MobileApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MobileApp.class.getName()).log(Level.SEVERE, null, ex);
        }

        return obj.toString();
    }
@GET
   @Path("SingleStaff&{id}")
      @Produces("application/json")
    
    public String getjson1(@PathParam("id") int STAFFID) {
              
    long time =System.currentTimeMillis();
        JSONObject singleStaff=new JSONObject();
        singleStaff.accumulate("Status","Error");
        singleStaff.accumulate("TimeStamp",time);
        singleStaff.accumulate("message","doesnt exists");
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection con=DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE","madteam3", "anypw1");
            
           Statement stm=con.createStatement();
           String sql="select*from staff where staffid=" +STAFFID;
           
           ResultSet rs=stm.executeQuery(sql);
           int staffid;
           String firstName, lastName, email, designation,phone,password;
           
               while(rs.next()){
               singleStaff.clear();
                staffid=rs.getInt("STAFFID");
                firstName=rs.getString("FIRSTNAME");
                lastName=rs.getString("LASTNAME");
                email=rs.getString("EMAIL");
                designation=rs.getString("DESIGNATION");
                phone=rs.getString("PHONE");
                 password=rs.getString("PASSWORD");

                  
                singleStaff.accumulate("Status","ok");
                singleStaff.accumulate("TimeStamp",time);
               singleStaff.accumulate("message","exists");

                           
               singleStaff.accumulate("STAFFID",staffid);
               singleStaff.accumulate("FIRSTNAME",firstName);
               singleStaff.accumulate("LASTNAME",lastName);
                singleStaff.accumulate("EMAIL",email);
                singleStaff.accumulate("DESIGNATION",designation);
                singleStaff.accumulate("PHONE",phone);
                 singleStaff.accumulate("PASSWORD",password);
        
                rs.close();
                stm.close();
                con.close();
        }
       } catch (ClassNotFoundException ex) {
            Logger.getLogger(MobileApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MobileApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    
       return singleStaff.toString();
}


@GET
    @Path("category")
    @Produces("application/json")
    public String getjson2() {

        long time = System.currentTimeMillis();
        JSONObject obj = new JSONObject();

        obj.accumulate("Status", "ok");
        obj.accumulate("TimeStamp", time);
        JSONArray mainJSON = new JSONArray();
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection con;
           
                   con = DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE","madteam3","anypw1");
            
            Statement stm = con.createStatement();
            String sql = "SELECT * FROM category";

            ResultSet rs = stm.executeQuery(sql);
            JSONObject singleCategory = new JSONObject();
            int categoryid;
            String categorytype;

            while (rs.next()) {
                categoryid = rs.getInt("CATEGORYID");
                categorytype = rs.getString("CATEGORYTYPE");
                

              singleCategory .accumulate("CATEGORYID", categoryid );
               singleCategory.accumulate("CATEGORYTYPE",categorytype);
               
                mainJSON.add(singleCategory);
               singleCategory .clear();

            }
            obj.accumulate("categorylist", mainJSON);
            rs.close();
            stm.close();
            con.close();

        } catch (ClassNotFoundException ex) { 
            Logger.getLogger(MobileApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MobileApp.class.getName()).log(Level.SEVERE, null, ex);
        }

        return obj.toString();
    }




    @GET
    @Path("singleCategory&{id}")
    @Produces("application/json")
    public String getjson3(@PathParam("id") int CATEGORYID) {

        long time = System.currentTimeMillis();
         JSONObject singleCategory=new JSONObject();
         singleCategory.accumulate("Status","Error");
         singleCategory.accumulate("TimeStamp",time);
         singleCategory.accumulate("message","doesnt exists");

        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection con;
           
                con = DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE","madteam3","anypw1");
            
            Statement stm = con.createStatement();
            String sql = "SELECT * FROM category where categoryid="+CATEGORYID;

            ResultSet rs = stm.executeQuery(sql);
           
            int categoryid;
            String categorytype;

            while (rs.next()) {
                singleCategory.clear();
                categoryid = rs.getInt("CATEGORYID");
                categorytype = rs.getString("CATEGORYTYPE");
                
                singleCategory.accumulate("Status","ok");
                singleCategory.accumulate("TimeStamp",time);
                 singleCategory.accumulate("message","exists");

                

              singleCategory .accumulate("CATEGORYID", categoryid );
               singleCategory.accumulate("CATEGORYTYPE",categorytype);
               
            }
            rs.close();
            stm.close();
            con.close();

        } catch (ClassNotFoundException ex) { 
            Logger.getLogger(MobileApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MobileApp.class.getName()).log(Level.SEVERE, null, ex);
        }

        return singleCategory.toString();
    }

    
    @GET
    @Path("appointments")
    @Produces("application/json")
    public String getjson4() {

        long time = System.currentTimeMillis();
        JSONObject obj = new JSONObject();

        obj.accumulate("Status", "ok");
        obj.accumulate("TimeStamp", time);
        JSONArray mainJSON = new JSONArray();
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE","madteam3","anypw1");
            
            Statement stm = con.createStatement();
            String sql = "SELECT * FROM appointments";

            ResultSet rs = stm.executeQuery(sql);
            JSONObject singleAppointment = new JSONObject();
            int appointmentid,starttime,endtime,roomno,staffstaffid,categorycategoryid;
            String  appointmentdate;

            while (rs.next()) {
                appointmentid= rs.getInt("APPOINTMENTID");
                starttime=rs.getInt("STARTTIME");
                endtime=rs.getInt("ENDTIME");
                roomno=rs.getInt("ROOMNO");
                staffstaffid=rs.getInt("STAFFSTAFFID");
                categorycategoryid=rs.getInt("CATEGORYCATEGORYID");
     
                appointmentdate =rs.getString("APPOINTMENTDATE");
                
                

               singleAppointment .accumulate("APPOINTMENTID", appointmentid );
               singleAppointment.accumulate("STARTTIME",starttime);
               singleAppointment.accumulate("ENDTIME",endtime);
               singleAppointment.accumulate("ROOMNO",roomno);
               singleAppointment.accumulate("STAFFSTAFFID",staffstaffid);
               singleAppointment.accumulate("CATEGORYCATEGORYID",categorycategoryid);
            
               singleAppointment.accumulate("APPOINTMENTDATE", appointmentdate);
               
               
               
                mainJSON.add(singleAppointment);
                singleAppointment .clear();

            }
            obj.accumulate("appoinments list", mainJSON);
            rs.close();
            stm.close();
            con.close();

        } catch (ClassNotFoundException ex) { 
            Logger.getLogger(MobileApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MobileApp.class.getName()).log(Level.SEVERE, null, ex);
        }

        return obj.toString();
    }
   
     @GET
    @Path("singleAppointment&{id}")
    @Produces("application/json")
    public String getjson5(@PathParam("id") int STAFFSTAFFID) {

        long time = System.currentTimeMillis();
     
         JSONObject singleAppointment=new JSONObject();
        
         singleAppointment.accumulate("Status","Error");
         singleAppointment.accumulate("TimeStamp",time);
         singleAppointment.accumulate("message","doesnt exists");
          

        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection con;
           
                con = DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE","madteam3","anypw1");
            
            Statement stm = con.createStatement();
            String sql = "SELECT * FROM appointments where staffstaffid="+STAFFSTAFFID;

            ResultSet rs = stm.executeQuery(sql);
             
            
        
             int appointmentid,starttime,endtime,roomno,staffstaffid,categorycategoryid;
            
            String appointmentdate;
            JSONArray mainJSON=new JSONArray();
            JSONObject singleAppointment1=new JSONObject();

            while (rs.next()) {
               singleAppointment.clear(); 
                 
                
                appointmentid= rs.getInt("APPOINTMENTID");
                starttime=rs.getInt("STARTTIME");
                endtime=rs.getInt("ENDTIME");
                roomno=rs.getInt("ROOMNO");
                staffstaffid=rs.getInt("STAFFSTAFFID");
                categorycategoryid=rs.getInt("CATEGORYCATEGORYID");
                appointmentdate=rs.getString("APPOINTMENTDATE");
                
                 
               singleAppointment.accumulate("Status","ok");
               singleAppointment.accumulate("TimeStamp",time);
               singleAppointment.accumulate("message","exists");
            
              singleAppointment1 .accumulate("APPOINTMENTID", appointmentid );
              singleAppointment1.accumulate("STARTTIME",starttime);
              singleAppointment1.accumulate("ENDTIME",endtime);
              singleAppointment1.accumulate("ROOMNO",roomno);
              singleAppointment1.accumulate("STAFFSTAFFID",staffstaffid);
              singleAppointment1.accumulate("CATEGORYCATEGORYID",categorycategoryid);
              singleAppointment1.accumulate("APPOINTMENTDATE", appointmentdate);
              mainJSON.add(singleAppointment1);
              singleAppointment1.clear();
      
            singleAppointment.accumulate("list",mainJSON);
            }
            rs.close();
            stm.close();
            con.close();

        } catch (ClassNotFoundException ex) { 
            Logger.getLogger(MobileApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MobileApp.class.getName()).log(Level.SEVERE, null, ex);
        }

        return singleAppointment.toString();
       
    }

        
    @GET
    @Path("staffappointments")
    @Produces("application/json")
    public String getjson6() {

        long time = System.currentTimeMillis();
        JSONObject obj = new JSONObject();

        obj.accumulate("Status", "ok");
        obj.accumulate("TimeStamp", time);
        JSONArray mainJSON = new JSONArray();
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE","madteam3","anypw1");
            
            Statement stm = con.createStatement();
            String sql = "select * from staff join appointments on appointments.staffstaffid=staff.staffid";

            ResultSet rs = stm.executeQuery(sql);
            JSONObject singleAppointment = new JSONObject();
           
            String firstName, lastName, email, designation,phone;
            int   staffId,appointmentid,starttime,endtime,roomno,staffstaffid,categorycategoryid;
            

            while (rs.next()) {
               
                staffId=rs.getInt("STAFFID");
                appointmentid= rs.getInt("APPOINTMENTID");
                starttime=rs.getInt("STARTTIME");
                endtime=rs.getInt("ENDTIME");
                roomno=rs.getInt("ROOMNO");
                staffstaffid=rs.getInt("STAFFSTAFFID");
                categorycategoryid=rs.getInt("CATEGORYCATEGORYID");
                firstName = rs.getString("FIRSTNAME");
                lastName = rs.getString("LASTNAME");
                email = rs.getString("EMAIL");
                designation = rs.getString("DESIGNATION");
                phone = rs.getString("PHONE");
                
                
                singleAppointment .accumulate("STAFFID",staffId );
               singleAppointment .accumulate("APPOINTMENTID", appointmentid );
               singleAppointment.accumulate("STARTTIME",starttime);
               singleAppointment.accumulate("ENDTIME",endtime);
               singleAppointment.accumulate("ROOMNO",roomno);
               singleAppointment.accumulate("STAFFSTAFFID",staffstaffid);
               singleAppointment.accumulate("CATEGORYCATEGORYID",categorycategoryid);
                singleAppointment .accumulate("FIRSTNAME", firstName);
                singleAppointment .accumulate("LASTNAME", lastName);
                singleAppointment .accumulate("EMAIL", email);
                singleAppointment .accumulate("DESIGNATION", designation);
                singleAppointment .accumulate("PHONE", phone);
               
               
               
                mainJSON.add(singleAppointment);
                singleAppointment .clear();

            }
            obj.accumulate("List", mainJSON);
            rs.close();
            stm.close();
            con.close();

        } catch (ClassNotFoundException ex) { 
            Logger.getLogger(MobileApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MobileApp.class.getName()).log(Level.SEVERE, null, ex);
        }

        return obj.toString();
    
}
   @GET
    @Path("staffappointments&{id}")
    @Produces("application/json")
    public String getjson7(@PathParam("id") int STAFFID)
      {                 
        long time = System.currentTimeMillis();
        JSONObject singleStaffAppointments=new JSONObject();
        singleStaffAppointments.accumulate("Status","Error");
        singleStaffAppointments.accumulate("TimeStamp",time);
        singleStaffAppointments.accumulate("message","doesnt exists");
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE","madteam3","anypw1");
            
            Statement stm = con.createStatement();
            String sql = "select * from STAFF inner join APPOINTMENTS on APPOINTMENTS.STAFFSTAFFID=STAFF.STAFFID where staffid="+STAFFID;

            ResultSet rs = stm.executeQuery(sql);
            
           
            String firstName, lastName, email, designation,phone,appointmentdate;
            int   staffId,appointmentid,starttime,endtime,roomno,staffstaffid,categorycategoryid;
            
            JSONArray mainJSON1=new JSONArray();
            JSONObject singleStaffAppointments1=new JSONObject();
            while (rs.next()) {
                singleStaffAppointments.clear();
               
                staffId=rs.getInt("STAFFID");
                appointmentid= rs.getInt("APPOINTMENTID");
                starttime=rs.getInt("STARTTIME");
                endtime=rs.getInt("ENDTIME");
                roomno=rs.getInt("ROOMNO");
                staffstaffid=rs.getInt("STAFFSTAFFID");
                categorycategoryid=rs.getInt("CATEGORYCATEGORYID");
                firstName = rs.getString("FIRSTNAME");
                lastName = rs.getString("LASTNAME");
                email = rs.getString("EMAIL");
                designation = rs.getString("DESIGNATION");
                phone = rs.getString("PHONE");
                appointmentdate=rs.getString("APPOINTMENTDATE");
                
                singleStaffAppointments.accumulate("Status","ok");
                singleStaffAppointments.accumulate("TimeStamp",time);
                singleStaffAppointments.accumulate("message","exists");
                
                
               singleStaffAppointments1 .accumulate("STAFFID",staffId );
               singleStaffAppointments1 .accumulate("APPOINTMENTID", appointmentid );
               singleStaffAppointments1.accumulate("STARTTIME",starttime);
               singleStaffAppointments1.accumulate("ENDTIME",endtime);
               singleStaffAppointments1.accumulate("ROOMNO",roomno);
               singleStaffAppointments1.accumulate("STAFFSTAFFID",staffstaffid);
               singleStaffAppointments1.accumulate("CATEGORYCATEGORYID",categorycategoryid);
               singleStaffAppointments1 .accumulate("FIRSTNAME", firstName);
               singleStaffAppointments1 .accumulate("LASTNAME", lastName);
               singleStaffAppointments1 .accumulate("EMAIL", email);
               singleStaffAppointments1 .accumulate("DESIGNATION", designation);
               singleStaffAppointments1 .accumulate("PHONE", phone);
                singleStaffAppointments1.accumulate("APPOINTMENTDATE",appointmentdate);
                mainJSON1.add(singleStaffAppointments1);
             singleStaffAppointments1.clear();
      
            singleStaffAppointments.accumulate("list",mainJSON1);
            }
            rs.close();
            stm.close();
            con.close();

        } catch (ClassNotFoundException ex) { 
            Logger.getLogger(MobileApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MobileApp.class.getName()).log(Level.SEVERE, null, ex);
        }

        return singleStaffAppointments.toString();
    
    }
    

@GET
    @Path("appointmentscategory")
    @Produces("application/json")
    public String getjson8() {

        long time = System.currentTimeMillis();
        JSONObject obj = new JSONObject();

        obj.accumulate("Status", "ok");
        obj.accumulate("TimeStamp", time);
        JSONArray mainJSON = new JSONArray();
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE","madteam3","anypw1");
            
            Statement stm = con.createStatement();
            String sql = "SELECT * FROM appointments join category on appointments.categorycategoryid=category.categoryid";

            ResultSet rs = stm.executeQuery(sql);
            JSONObject singleAppointmentCategory = new JSONObject();
            int categoryid,appointmentid,starttime,endtime,roomno,staffstaffid,categorycategoryid;
            String categorytype,appointmentdate;

            while (rs.next()) {
                categoryid=rs.getInt("CATEGORYID");
                appointmentid= rs.getInt("APPOINTMENTID");
                starttime=rs.getInt("STARTTIME");
                endtime=rs.getInt("ENDTIME");
                roomno=rs.getInt("ROOMNO");
                staffstaffid=rs.getInt("STAFFSTAFFID");
                categorycategoryid=rs.getInt("CATEGORYCATEGORYID");
                categorytype=rs.getString("CATEGORYTYPE");
                
                appointmentdate=rs.getString("APPOINTMENTDATE");
                
                
               singleAppointmentCategory .accumulate("CATEGORYID", categoryid );
               singleAppointmentCategory .accumulate("APPOINTMENTID", appointmentid );
               singleAppointmentCategory.accumulate("STARTTIME",starttime);
               singleAppointmentCategory.accumulate("ENDTIME",endtime);
               singleAppointmentCategory.accumulate("ROOMNO",roomno);
               singleAppointmentCategory.accumulate("STAFFSTAFFID",staffstaffid);
               singleAppointmentCategory.accumulate("CATEGORYCATEGORYID",categorycategoryid);
               singleAppointmentCategory.accumulate("CATEGORYTYPE", categorytype);
               
               
               
                mainJSON.add(singleAppointmentCategory);
                singleAppointmentCategory .clear();

            }
            obj.accumulate("List", mainJSON);
            rs.close();
            stm.close();
            con.close();

        } catch (ClassNotFoundException ex) { 
            Logger.getLogger(MobileApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MobileApp.class.getName()).log(Level.SEVERE, null, ex);
        }

        return obj.toString();
        
    }

    @GET
    @Path("appointmentscategory&{id}")
    @Produces("application/json")
    public String getjson9(@PathParam("id") int APPOINTMENTID) {

        long time = System.currentTimeMillis();
        JSONObject singleAppointmentCategory = new JSONObject();
        singleAppointmentCategory.accumulate("Status", "error");
        singleAppointmentCategory.accumulate("TimeStamp", time);
        singleAppointmentCategory.accumulate("message", "doesnt exists");
       
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection con= DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE","madteam3","anypw1");
            
            Statement stm = con.createStatement();
            String sql = "SELECT * FROM appointments join category on appointments.categorycategoryid=category.categoryid where appointmentid="+APPOINTMENTID;

            ResultSet rs = stm.executeQuery(sql);
            
            int categoryid,appointmentid,starttime,endtime,roomno,staffstaffid,categorycategoryid;
            String categorytype,appointmentday;
            String appointmentdate;

            while (rs.next()) {
                singleAppointmentCategory.clear();
                categoryid=rs.getInt("CATEGORYID");
                appointmentid= rs.getInt("APPOINTMENTID");
                starttime=rs.getInt("STARTTIME");
                endtime=rs.getInt("ENDTIME");
                roomno=rs.getInt("ROOMNO");
                staffstaffid=rs.getInt("STAFFSTAFFID");
                categorycategoryid=rs.getInt("CATEGORYCATEGORYID");
                categorytype=rs.getString("CATEGORYTYPE");
                appointmentday = rs.getString("APPOINTMENTDAY");
                appointmentdate=rs.getString("APPOINTMENTDATE");
                
                singleAppointmentCategory .accumulate("Status", "ok");
                singleAppointmentCategory .accumulate("TimeStamp", time);
                singleAppointmentCategory .accumulate("message", "exists");
        
               singleAppointmentCategory .accumulate("CATEGORYID", categoryid );
               singleAppointmentCategory .accumulate("APPOINTMENTID", appointmentid );
               singleAppointmentCategory.accumulate("STARTTIME",starttime);
               singleAppointmentCategory.accumulate("ENDTIME",endtime);
               singleAppointmentCategory.accumulate("ROOMNO",roomno);
               singleAppointmentCategory.accumulate("STAFFSTAFFID",staffstaffid);
               singleAppointmentCategory.accumulate("CATEGORYCATEGORYID",categorycategoryid);
               singleAppointmentCategory.accumulate("APPOINTMENTDAY",appointmentday);
               singleAppointmentCategory.accumulate("APPOINTMENTDATE",appointmentdate);
               singleAppointmentCategory.accumulate("CATEGORYTYPE", categorytype);
             }
           
            rs.close();
            stm.close();
            con.close();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MobileApp.class.getName()).log(Level.SEVERE, null, ex);
        }

        return singleAppointmentCategory.toString();
    }
    @GET
    @Path("availabilites")
    @Produces("application/json")
    public String getjson10() {

        long time = System.currentTimeMillis();
        JSONObject obj = new JSONObject();

        obj.accumulate("Status", "ok");
        obj.accumulate("TimeStamp", time);
        JSONArray mainJSON = new JSONArray();
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE","madteam3","anypw1");
            
            Statement stm = con.createStatement();
            String sql = " SELECT * FROM availabilites ";

            ResultSet rs = stm.executeQuery(sql);
            JSONObject singleAvailable = new JSONObject();
            int availabilityid,starttime,endtime,staffstaffid;
            String availabileday;
            String availabiledate;

            while (rs.next()) {
                availabilityid= rs.getInt("AVAILABILITYID");
                starttime=rs.getInt("STARTTIME");
                endtime=rs.getInt("ENDTIME");
                availabiledate=rs.getString("AVAILABILEDATE");
                availabileday = rs.getString("AVAILABILEDAY");
                staffstaffid=rs.getInt("STAFFSTAFFID");

               singleAvailable.accumulate("AVAILABILITYID", availabilityid);
               singleAvailable.accumulate("STARTTIME",starttime);
               singleAvailable.accumulate("ENDTIME",endtime);
               singleAvailable.accumulate("AVAILABILEDATE",availabiledate);
               singleAvailable.accumulate("AVAILABILEDAY",availabileday);
               singleAvailable.accumulate("STAFFSTAFFID",staffstaffid);
               
               
               
               
                mainJSON.add(singleAvailable);
                singleAvailable.clear();

            }
            obj.accumulate("availabledetails", mainJSON);
            rs.close();
            stm.close();
            con.close();

        } catch (ClassNotFoundException ex) { 
            Logger.getLogger(MobileApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MobileApp.class.getName()).log(Level.SEVERE, null, ex);
        }

        return obj.toString();
    }
   @GET
    @Path("singleavailabilites&{id}")
    @Produces("application/json")
    public String getjson11(@PathParam("id") int STAFFSTAFFID) {

        long time = System.currentTimeMillis();
        JSONObject singleAvailable = new JSONObject();

       singleAvailable.accumulate("Status", "error");
        singleAvailable.accumulate("TimeStamp", time);
        singleAvailable.accumulate("messgae", "not exists");
        
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE","madteam3","anypw1");
            
            Statement stm = con.createStatement();
            String sql = "SELECT * FROM availabilites where STAFFSTAFFID="+STAFFSTAFFID;

            ResultSet rs = stm.executeQuery(sql);
            
            int availabilityid,starttime,endtime,staffstaffid;
            String availabileday;
            String availabiledate;
             
            JSONObject singleAvailable1 = new JSONObject();
              JSONArray mainJSON3 = new JSONArray();
            
              while (rs.next()) {
                singleAvailable.clear();
                availabilityid= rs.getInt("AVAILABILITYID");
                starttime=rs.getInt("STARTTIME");
                endtime=rs.getInt("ENDTIME");
                
                availabiledate=rs.getString("AVAILABILEDATE");
                availabileday = rs.getString("AVAILABILEDAY");
                staffstaffid=rs.getInt("STAFFSTAFFID");
                
                singleAvailable.accumulate("Status", "ok");
                singleAvailable.accumulate("TimeStamp", time);
                singleAvailable.accumulate("messgae", "exists");

               singleAvailable1.accumulate("AVAILABILITYID", availabilityid);
               singleAvailable1.accumulate("STARTTIME",starttime);
               singleAvailable1.accumulate("ENDTIME",endtime);
               singleAvailable1.accumulate("AVAILABILEDATE",availabiledate);
               singleAvailable1.accumulate("AVAILABILEDAY",availabileday);
               singleAvailable1.accumulate("STAFFSTAFFID",staffstaffid);
               
               mainJSON3.add(singleAvailable1);
               singleAvailable1.clear();
               
               singleAvailable.accumulate("availabilities", mainJSON3);
               
               
            }
            rs.close();
            stm.close();
            con.close();

        } catch (ClassNotFoundException ex) { 
            Logger.getLogger(MobileApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MobileApp.class.getName()).log(Level.SEVERE, null, ex);
        }

        return singleAvailable.toString();
    }
   @GET
   @Path("SingleStaffAvailabile&{id}")
      @Produces("application/json")
    
    public String getjson12(@PathParam("id") int STAFFID) {
              
    long time =System.currentTimeMillis();
        JSONObject singleStaff=new JSONObject();
        singleStaff.accumulate("Status","Error");
        singleStaff.accumulate("TimeStamp",time);
        singleStaff.accumulate("message","doesnt exists");
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection con=DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE","madteam3", "anypw1");
            
           Statement stm=con.createStatement();
           String sql="select * from availabilites join staff on availabilites.staffstaffid=staff.staffid where staffid="+STAFFID;
           
           ResultSet rs=stm.executeQuery(sql);
           int staffid,availabilityid,starttime,endtime;
           String firstName, lastName, email, designation,phone,availableday;
           String availabledate;
           
           JSONObject singleStaff1=new JSONObject();
           JSONArray mainJSON4=new JSONArray();
               while(rs.next()){
                singleStaff.clear();
                staffid=rs.getInt("STAFFID");
                availabilityid=rs.getInt("AVAILABILITYID");
                starttime=rs.getInt("STARTTIME");
                endtime=rs.getInt("ENDTIME");
                firstName=rs.getString("FIRSTNAME");
                lastName=rs.getString("LASTNAME");
                email=rs.getString("EMAIL");
                designation=rs.getString("DESIGNATION");
                phone=rs.getString("PHONE");
                availableday=rs.getString("AVAILABILEDAY");
                availabledate=rs.getString("AVAILABILEDATE");
                  
                singleStaff.accumulate("Status","ok");
                singleStaff.accumulate("TimeStamp",time);
               singleStaff.accumulate("message","exists");

                           
               singleStaff1.accumulate("STAFFID",staffid);
               singleStaff1.accumulate("AVAILABILITYID",availabilityid);
               singleStaff1.accumulate("STARTTIME", starttime);
               singleStaff1.accumulate("ENDTIME", endtime);
               singleStaff1.accumulate("FRISTNAME",firstName);
               singleStaff1.accumulate("LASTNAME",lastName);
               singleStaff1.accumulate("EMAIL",email);
               singleStaff1.accumulate("DESIGNATION",designation);
               singleStaff1.accumulate("PHONE",phone);
               singleStaff1.accumulate("AVAILABILEDAY",availableday);
               singleStaff1.accumulate("AVAILABILEDATE",availabledate);
                
               mainJSON4.add(singleStaff1);
               singleStaff1.clear();
        
               singleStaff.accumulate("list",mainJSON4);
               }
                rs.close();
                stm.close();
                con.close();
        
       } catch (ClassNotFoundException ex) {
            Logger.getLogger(MobileApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MobileApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    
       return singleStaff.toString();
}
    @GET
   @Path("SingleStaffAppointmenttCategory&{id}")
      @Produces("application/json")
    
    public String getjson13(@PathParam("id") int STAFFID) {
              
    long time =System.currentTimeMillis();
        JSONObject singleStaff=new JSONObject();
        singleStaff.accumulate("Status","Error");
        singleStaff.accumulate("TimeStamp",time);
        singleStaff.accumulate("message","doesnt exists");
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection con=DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE","madteam3", "anypw1");
            
           Statement stm=con.createStatement();
           String sql="select * from staff join appointments on appointments.staffstaffid=staff.staffID join category on appointments.CATEGORYCATEGORYID=category.CATEGORYID join availabilites on availabilites.STAFFSTAFFID=STAFF.STAFFID WHERE STAFFID="+STAFFID;
           
           ResultSet rs=stm.executeQuery(sql);
           int staffid,appointmentid,starttime,endtime,roomno,staffstaffid,categoryid,categorycategoryid,availabilityid;
        
           String  firstName,lastName,email,designation,phone,appointmentdate,categorytype,availabileday,availabiledate;
           JSONObject singleStaff1=new JSONObject();
           JSONArray mainJSON2=new JSONArray();
               while(rs.next()){
               singleStaff.clear();
                staffid=rs.getInt("STAFFID");
                firstName=rs.getString("FIRSTNAME");
                lastName=rs.getString("LASTNAME");
                email=rs.getString("EMAIL");
                designation=rs.getString("DESIGNATION");
                phone=rs.getString("PHONE");
                categoryid=rs.getInt("CATEGORYID");
                appointmentid= rs.getInt("APPOINTMENTID");
                starttime=rs.getInt("STARTTIME");
                endtime=rs.getInt("ENDTIME");
                roomno=rs.getInt("ROOMNO");
                staffstaffid=rs.getInt("STAFFSTAFFID");
                categorycategoryid=rs.getInt("CATEGORYCATEGORYID");
                categorytype=rs.getString("CATEGORYTYPE");
               
                appointmentdate=rs.getString("APPOINTMENTDATE");
                availabilityid= rs.getInt("AVAILABILITYID"); 
                availabiledate=rs.getString("AVAILABILEDATE");
                availabileday = rs.getString("AVAILABILEDAY");
                  
               singleStaff.accumulate("Status","ok");
               singleStaff.accumulate("TimeStamp",time);
               singleStaff.accumulate("message","exists");

                           
               singleStaff1.accumulate("STAFFID",staffid);
               singleStaff1.accumulate("FRISTNAME",firstName);
               singleStaff1.accumulate("LASTNAME",lastName);
               singleStaff1.accumulate("EMAIL",email);
               singleStaff1.accumulate("DESIGNATION",designation);
               singleStaff1.accumulate("PHONE",phone);
               singleStaff1.accumulate("CATEGORYID", categoryid );
               singleStaff1.accumulate("APPOINTMENTID", appointmentid );
               singleStaff1.accumulate("STARTTIME",starttime);
               singleStaff1.accumulate("ENDTIME",endtime);
               singleStaff1.accumulate("ROOMNO",roomno);
               singleStaff1.accumulate("STAFFSTAFFID",staffstaffid);
               singleStaff1.accumulate("CATEGORYCATEGORYID",categorycategoryid);
               singleStaff1.accumulate("APPOINTMENTDATE",appointmentdate);
               singleStaff1.accumulate("CATEGORYTYPE", categorytype);
               singleStaff1.accumulate("AVAILABILITYID", availabilityid);
               singleStaff1.accumulate("AVAILABILEDATE",availabiledate);
               singleStaff1.accumulate("AVAILABILEDAY",availabileday);
               mainJSON2.add(singleStaff1);
               singleStaff1.clear();
      
            singleStaff.accumulate("list",mainJSON2);
               }
                rs.close();
                stm.close();
                con.close();
        
       } catch (ClassNotFoundException ex) {
            Logger.getLogger(MobileApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MobileApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    
       return singleStaff.toString();
    }
    
    @GET
    @Path("login&{staffid}&{password}")
    @Produces("application/json")
    public String getjson14(@PathParam("staffid") String STAFFID, @PathParam("password") String PASSWORD) {

        long time = System.currentTimeMillis();
        JSONObject login = new JSONObject();
        login.accumulate("Status", "Error");
        login.accumulate("TimeStamp", time);
        login.accumulate("message", "Invalid details");

        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection con;

            con = DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE", "madteam3", "anypw1");

            Statement stm = con.createStatement();
            String sql = "SELECT * FROM STAFF where STAFFID=" + "'" + STAFFID + "'" + "AND PASSWORD=" + "'" + PASSWORD + "'";

            ResultSet rs = stm.executeQuery(sql);

            int staffid;
            String firstName, lastName, email, designation, phone, password;

            if (rs.next() == true) {

                staffid = rs.getInt("STAFFID");

                firstName = rs.getString("FIRSTNAME");
                lastName = rs.getString("LASTNAME");
                email = rs.getString("EMAIL");
                designation = rs.getString("DESIGNATION");
                phone = rs.getString("PHONE");
                password = rs.getString("PASSWORD");
          
                login.accumulate("TYPE", "staff");

            } else {

                rs = stm.executeQuery(sql);
                rs.next();

               
                staffid = rs.getInt("STAFFID");
                
                login.accumulate("TYPE", "Staff");
            }
            if (staffid != 0) {
                   login.clear();
                login.accumulate("Status", "ok");
                login.accumulate("TimeStamp", time);
                login.accumulate("message", "Successfull");
               

            }
            
            rs.close();
            stm.close();
            con.close();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MobileApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MobileApp.class.getName()).log(Level.SEVERE, null, ex);
           
        }

        return login.toString();
    }
    
    
    @GET
    @Path("setappointment&{SENDERID}&{STARTTIME}&{ENDTIME}&{APPOINTMENTDATE}&{ROOMNO}&{STAFFSTAFFID}&{CATEGORYCATEGORYID}")
    @Produces("application/json")
    public String getjson15(@PathParam("SENDERID") String SENDERID, @PathParam("STARTTIME") String STARTTIME, @PathParam("ENDTIME") String ENDTIME,@PathParam("APPOINTMENTDATE") String APPOINTMENTDATE, @PathParam("ROOMNO") String ROOMNO, @PathParam("STAFFSTAFFID") String STAFFSTAFFID, @PathParam("CATEGORYCATEGORYID") String CATEGORYCATEGORYID) {

        int num = 0,num_of_records=0;
        long time = System.currentTimeMillis();
        JSONObject setappointment = new JSONObject();
        String sql = "";
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection con;

            con = DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE", "madteam3", "anypw1");
            Statement stmt = con.createStatement();
           
            String countRecords = "SELECT APPOINTMENTID FROM (SELECT APPOINTMENTID FROM APPOINTMENTS ORDER BY APPOINTMENTID DESC) WHERE ROWNUM<=1";
            
            
            ResultSet rs = stmt.executeQuery(countRecords);
            
            if (rs.next()){
                num_of_records = rs.getInt("APPOINTMENTID");
            }
            
            num_of_records+=1;
            sql = "INSERT INTO APPOINTMENTS (APPOINTMENTID,STARTTIME,ENDTIME,APPOINTMENTDATE,ROOMNO,STAFFSTAFFID,CATEGORYCATEGORYID,SENDER,APPOINTMENTSTATUS) VALUES(" +
                           num_of_records+","+STARTTIME+","+ENDTIME+", DATE '"+APPOINTMENTDATE+"',"+ROOMNO+","+STAFFSTAFFID+","+CATEGORYCATEGORYID+","+SENDERID+",'PENDING')";
            
            
            num = stmt.executeUpdate(sql);
            
           
                setappointment.accumulate("Status", "Ok");
                setappointment.accumulate("TimeStamp", time);
                setappointment.accumulate("message", "Successful");
         
            
            stmt.close();
            con.close();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MobileApp.class.getName()).log(Level.SEVERE, null, ex);
          
            setappointment.accumulate("Status", "Error");
        setappointment.accumulate("TimeStamp", time);
        setappointment.accumulate("message", "Invalid details");
            
        } catch (SQLException ex) {
            Logger.getLogger(MobileApp.class.getName()).log(Level.SEVERE, null, ex);
            setappointment.accumulate("STime", STARTTIME);
            setappointment.accumulate("ETIme",ENDTIME);
            setappointment.accumulate("Date", APPOINTMENTDATE);
            setappointment.accumulate("Room", ROOMNO);
            setappointment.accumulate("Category", CATEGORYCATEGORYID);
            setappointment.accumulate("SID", STAFFSTAFFID);
            setappointment.accumulate("Query", sql);
            
            
           
        }

        return setappointment.toString();
    } 

    
    @GET
    @Path("getlastrecordId")
    @Produces("application/json")
    public String getjson16() {

        int num_of_records = 0;
        long time = System.currentTimeMillis();
        JSONObject getlastrecordId = new JSONObject();

        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection con;

            con = DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE", "madteam3", "anypw1");
            Statement stmt = con.createStatement();
            
            String sql = "SELECT APPOINTMENTID FROM (SELECT APPOINTMENTID FROM APPOINTMENTS ORDER BY APPOINTMENTID DESC) WHERE ROWNUM<=1";
            
            
            ResultSet rs = stmt.executeQuery(sql);
            
            if (rs.next()){
                getlastrecordId.accumulate("Status", "Ok");
                getlastrecordId.accumulate("TimeStamp", time);
                num_of_records = rs.getInt("APPOINTMENTID");
                getlastrecordId.accumulate("message", String.valueOf(num_of_records));
            }
            else{
                getlastrecordId.accumulate("Status", "Error");
                getlastrecordId.accumulate("TimeStamp", time);
                getlastrecordId.accumulate("message", "Invalid details");
            }
            rs.close();
            stmt.close();
            con.close();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MobileApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MobileApp.class.getName()).log(Level.SEVERE, null, ex);
           
        }

        return getlastrecordId.toString();
    } 
    
    @GET
    @Path("setappointments&{STARTTIME}&{ENDTIME}&{APPOINTMENTDATE}&{ROOMNO}&{STAFFSTAFFID1}&{STAFFSTAFFID2}&{STAFFSTAFFID3}&{STAFFSTAFFID4}&{STAFFSTAFFID5}&{STAFFSTAFFID6}&{STAFFSTAFFID7}&{CATEGORYCATEGORYID}")
    @Produces("application/json")
    public String getjson17(@PathParam("STARTTIME") String STARTTIME, @PathParam("ENDTIME") String ENDTIME,@PathParam("APPOINTMENTDATE") String APPOINTMENTDATE, @PathParam("ROOMNO") String ROOMNO, @PathParam("STAFFSTAFFID1") String STAFFSTAFFID1, @PathParam("STAFFSTAFFID2") String STAFFSTAFFID2, @PathParam("STAFFSTAFFID3") String STAFFSTAFFID3, @PathParam("STAFFSTAFFID4") String STAFFSTAFFID4, @PathParam("STAFFSTAFFID5") String STAFFSTAFFID5, @PathParam("STAFFSTAFFID6") String STAFFSTAFFID6, @PathParam("STAFFSTAFFID7") String STAFFSTAFFID7, @PathParam("CATEGORYCATEGORYID") String CATEGORYCATEGORYID) {

        int num = 0,num_of_records=0;
        long time = System.currentTimeMillis();
        JSONObject setappointments = new JSONObject();       
        String staffID = "1";
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection con;

            con = DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE", "madteam3", "anypw1");
            Statement stmt = con.createStatement();
            for(int i=1;i<=7;i++){
                if(i==1){
                     staffID = STAFFSTAFFID1;
                }
                else if(i==2){
                     staffID = STAFFSTAFFID2;
                }
                else if(i==3){
                    staffID = STAFFSTAFFID3;
                }
                else if(i==4){
                     staffID = STAFFSTAFFID4;
                }
                else if(i==5){
                     staffID = STAFFSTAFFID5;
                }
                else if(i==6){
                     staffID = STAFFSTAFFID6;
                }
                else if(i==7){
                     staffID = STAFFSTAFFID7;
                }
                
                if (staffID.matches("[0-9]+") && staffID.length() == 7) {
                    String countRecords = "SELECT APPOINTMENTID FROM (SELECT APPOINTMENTID FROM APPOINTMENTS ORDER BY APPOINTMENTID DESC) WHERE ROWNUM<=1";
            
                    ResultSet rs = stmt.executeQuery(countRecords);
            
                    if (rs.next()){
                        num_of_records = rs.getInt("APPOINTMENTID");
                    }
            
                    num_of_records+=1;
                    String sql = "INSERT INTO APPOINTMENTS (APPOINTMENTID,STARTTIME,ENDTIME,APPOINTMENTDATE,ROOMNO,STAFFSTAFFID,CATEGORYCATEGORYID) VALUES(" +
                           num_of_records+","+STARTTIME+","+ENDTIME+", DATE '"+APPOINTMENTDATE+"',"+ROOMNO+","+staffID+","+CATEGORYCATEGORYID+")";
            
            
                    num = stmt.executeUpdate(sql);
                    num_of_records = 0;
                    if (num==1){
                        setappointments.accumulate("Status", "Ok");
                        setappointments.accumulate("TimeStamp", time);
                        setappointments.accumulate("message", "Successful");
                        setappointments.accumulate("Query", sql);
                        num=0;
                    }
                    else{
                        setappointments.accumulate("Status", "Error");
                        setappointments.accumulate("TimeStamp", time);
                        setappointments.accumulate("message", "Invalid details");
                    }
                    staffID = "1";
            
                }
                
               
            }
            stmt.close();
            con.close();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MobileApp.class.getName()).log(Level.SEVERE, null, ex);
            
        } catch (SQLException ex) {
            Logger.getLogger(MobileApp.class.getName()).log(Level.SEVERE, null, ex);
            
           
        }

        return setappointments.toString();
    }
    
    @GET
    @Path("forgetpassword&{staffid}&{password}")
    @Produces("application/json")
    public String getjson18(@PathParam("staffid") String staffid, @PathParam("password") String password) {

        
        long time = System.currentTimeMillis();
        JSONObject forgetpassword = new JSONObject();
        String sql = "";
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection con;

            con = DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE", "madteam3", "anypw1");
            Statement stmt = con.createStatement();
           
            String countRecords = "SELECT * FROM  STAFF WHERE STAFFID="+staffid;
            
            
            ResultSet rs = stmt.executeQuery(countRecords);
            
            if (rs.next()){
                sql = "UPDATE STAFF SET PASSWORD='"+password+"' WHERE STAFFID ="+staffid;
                stmt.executeUpdate(sql);
            
                
                forgetpassword.accumulate("Status", "Ok");
                forgetpassword.accumulate("TimeStamp", time);
                forgetpassword.accumulate("message", "Successful");
                
            }
            else{
                forgetpassword.accumulate("Status", "Ok");
                forgetpassword.accumulate("TimeStamp", time);
                forgetpassword.accumulate("message", "IdNotFound");
            }
            
            stmt.close();
            con.close();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MobileApp.class.getName()).log(Level.SEVERE, null, ex);
          
            forgetpassword.accumulate("Status", "Error");
            forgetpassword.accumulate("TimeStamp", time);
            forgetpassword.accumulate("message", "Invalid details");
            
        } catch (SQLException ex) {
            Logger.getLogger(MobileApp.class.getName()).log(Level.SEVERE, null, ex);
            forgetpassword.accumulate("Status", "Error");
            forgetpassword.accumulate("Query",sql);
            forgetpassword.accumulate("message", ex.getLocalizedMessage());
           
        }

        return forgetpassword.toString();
    } 
    
    @GET
    @Path("getappointments&{staffid}")
    @Produces("application/json")
    public String getjson19(@PathParam("staffid") String staffid) {

        
        long time = System.currentTimeMillis();
        JSONObject forgetpassword = new JSONObject();
        String sql = "",SenderName="",categoryType="";
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection con;

            con = DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE", "madteam3", "anypw1");
            Statement stmt = con.createStatement();
           
            sql = "SELECT * FROM  (SELECT * FROM APPOINTMENTS WHERE STAFFSTAFFID="+staffid+"  AND APPOINTMENTSTATUS='PENDING') WHERE ROWNUM<=3";
            
            
            ResultSet rs = stmt.executeQuery(sql);
            int num = -1;
                while (rs.next()){
                    num+=1;
                    int startTime = rs.getInt("STARTTIME");
                    int endTime = rs.getInt("ENDTIME");
                    String AppointmentDate = String.valueOf(rs.getDate("APPOINTMENTDATE"));
                    int roomNumber = rs.getInt("ROOMNO");
                    int Category = rs.getInt("CATEGORYCATEGORYID");
                    int StaffId = rs.getInt("STAFFSTAFFID");
                    int SenderId = rs.getInt("SENDER");
                    
                    Statement stmt1 = con.createStatement();
                    Statement stmt2 = con.createStatement();
                    String SenderNameQuery = "SELECT * FROM STAFF WHERE STAFFID="+SenderId;
                    ResultSet rs1 = stmt1.executeQuery(SenderNameQuery);
            
                    while(rs1.next()){
                        SenderName = rs1.getString("FIRSTNAME")+" "+rs1.getString("LASTNAME");
                    }
                    rs1.close();
                    stmt1.close();
                    
                    String CategoryType = "SELECT * FROM CATEGORY WHERE CATEGORYID="+Category;
                    ResultSet rs2 = stmt2.executeQuery(CategoryType);
        
            
                    while(rs2.next()){
                        categoryType = rs2.getString("CATEGORYTYPE");
                    }
                    rs2.close();
                    stmt2.close();
                    
                    JSONObject jObj = new JSONObject();
                    
                    jObj.put("Status", "Ok");
                    jObj.put("TimeStamp", time);
                     jObj.put("StaffId", StaffId);
                     jObj.put("startTime", startTime);
                     jObj.put("endTime", endTime);
                     jObj.put("AppointmentDate", AppointmentDate);
                     jObj.put("roomNumber", roomNumber);
                     jObj.put("Category", categoryType);
                     jObj.put("SenderId", SenderId);
                     jObj.put("Sender", SenderName);
                     jObj.put("message", "Successfull");
                     forgetpassword.put(num, jObj);
                }
            
            stmt.close();
            con.close();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MobileApp.class.getName()).log(Level.SEVERE, null, ex);
          
            forgetpassword.accumulate("Status", "Error");
            forgetpassword.accumulate("TimeStamp", time);
            forgetpassword.accumulate("message", "Invalid details");
            
        } catch (SQLException ex) {
            Logger.getLogger(MobileApp.class.getName()).log(Level.SEVERE, null, ex);
            forgetpassword.accumulate("Status", "Error");
            forgetpassword.accumulate("Query",sql);
            forgetpassword.accumulate("message", ex.getLocalizedMessage());
           
        }

        return forgetpassword.toString();
    } 
    
    
    @GET
    @Path("updateAppointmentStatus&{staffid}&{status}&{SenderId}")
    @Produces("application/json")
    public String getjson20(@PathParam("staffid") String staffid,@PathParam("status") String status,@PathParam("SenderId") String SenderId) {

        
        long time = System.currentTimeMillis();
        JSONObject updateAppointmentStatus = new JSONObject();
        String sql = "";
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection con;

            con = DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE", "madteam3", "anypw1");
            Statement stmt = con.createStatement();
           
            sql = "UPDATE APPOINTMENTS SET APPOINTMENTSTATUS='"+status+"' WHERE STAFFSTAFFID="+staffid+" AND SENDER="+SenderId;
            
            
            stmt.executeUpdate(sql);
            updateAppointmentStatus.accumulate("Status", "Ok");
            updateAppointmentStatus.accumulate("TimeStamp", time);
            updateAppointmentStatus.accumulate("message", "Successfull");
            
            stmt.close();
            con.close();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MobileApp.class.getName()).log(Level.SEVERE, null, ex);
          
            updateAppointmentStatus.accumulate("Status", "Error");
            updateAppointmentStatus.accumulate("TimeStamp", time);
            updateAppointmentStatus.accumulate("message", "Invalid details");
            
        } catch (SQLException ex) {
            Logger.getLogger(MobileApp.class.getName()).log(Level.SEVERE, null, ex);
            updateAppointmentStatus.accumulate("Status", "Error");
            updateAppointmentStatus.accumulate("Query",sql);
            updateAppointmentStatus.accumulate("message", ex.getLocalizedMessage());
           
        }

        return updateAppointmentStatus.toString();
    } 
    
    @GET
    @Path("currentappointmet&{senderId}&{appointmentdate}")
    @Produces("application/json")
    public String getjson21(@PathParam("senderId") String senderId,@PathParam("appointmentdate") String appointmentdate) {

        
        long time = System.currentTimeMillis();
        JSONObject currentappointmet = new JSONObject();
        String sql = "",StaffName="",categoryType="";
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection con;

            con = DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE", "madteam3", "anypw1");
            Statement stmt = con.createStatement();
          
           
            sql = "SELECT * FROM (SELECT * FROM APPOINTMENTS WHERE SENDER="+senderId+" AND APPOINTMENTSTATUS='PENDING' AND APPOINTMENTDATE>= DATE '"+appointmentdate+"') WHERE ROWNUM<=3";
            
            
            ResultSet rs = stmt.executeQuery(sql);
            
            int num = -1;
                while (rs.next()){
                    num+=1;
                    int aptId = rs.getInt("APPOINTMENTID");
                    int startTime = rs.getInt("STARTTIME");
                    int endTime = rs.getInt("ENDTIME");
                    String AppointmentDate = String.valueOf(rs.getDate("APPOINTMENTDATE"));
                    int roomNumber = rs.getInt("ROOMNO");
                    int Category = rs.getInt("CATEGORYCATEGORYID");
                    int StaffId = rs.getInt("STAFFSTAFFID");
                    int SenderId = rs.getInt("SENDER");
                    Statement stmt1 = con.createStatement();
                    Statement stmt2 = con.createStatement();
                    String StaffNameQuery = "SELECT * FROM STAFF WHERE STAFFID="+StaffId;
                    ResultSet rs1 = stmt1.executeQuery(StaffNameQuery);
            
                    while(rs1.next()){
                        StaffName = rs1.getString("FIRSTNAME")+" "+rs1.getString("LASTNAME");
                    }
                    rs1.close();
                    stmt1.close();
                    
                    String CategoryType = "SELECT * FROM CATEGORY WHERE CATEGORYID="+Category;
                    ResultSet rs2 = stmt2.executeQuery(CategoryType);
        
            
                    while(rs2.next()){
                        categoryType = rs2.getString("CATEGORYTYPE");
                    }
                    rs2.close();
                    stmt2.close();
                    
                    
                    JSONObject jObj = new JSONObject();
                    
                    jObj.put("Status", "Ok");
                    jObj.put("TimeStamp", time);
                     jObj.put("StaffId", StaffId);
                     jObj.put("aptId",aptId);
                     jObj.put("startTime", startTime);
                     jObj.put("endTime", endTime);
                     jObj.put("AppointmentDate", AppointmentDate);
                     jObj.put("roomNumber", roomNumber);
                     jObj.put("Category", categoryType);
                     jObj.put("StaffName", StaffName);
                     jObj.put("message", "Successfull");
                     currentappointmet.put(num, jObj);
                }
            rs.close();
            stmt.close();
            con.close();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MobileApp.class.getName()).log(Level.SEVERE, null, ex);
          
            currentappointmet.accumulate("Status", "Error");
            currentappointmet.accumulate("TimeStamp", time);
            currentappointmet.accumulate("message", "Invalid details");
            
        } catch (SQLException ex) {
            Logger.getLogger(MobileApp.class.getName()).log(Level.SEVERE, null, ex);
            currentappointmet.accumulate("Status", "Error");
            currentappointmet.accumulate("Query",sql);
            currentappointmet.accumulate("message", ex.getLocalizedMessage());
           
        }

        return currentappointmet.toString();
    } 
    
    @GET
    @Path("updateappointmentwithStaff&{aptId}&{aptDate}&{startTime}&{endTime}&{roomNum}")
    @Produces("application/json")
    public String getjson21(@PathParam("aptId") String aptId,@PathParam("aptDate") String aptDate,@PathParam("startTime") String startTime,@PathParam("endTime") String endTime,@PathParam("roomNum") String roomNum) {

        
        long time = System.currentTimeMillis();
        JSONObject updateappointmentwithStaff = new JSONObject();
        String sql = "";
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection con;

            con = DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE", "madteam3", "anypw1");
            Statement stmt = con.createStatement();
           
            sql = "UPDATE APPOINTMENTS SET STARTTIME="+startTime+", ENDTIME="+endTime+", APPOINTMENTDATE= DATE '"+aptDate+"', ROOMNO="+roomNum+", APPOINTMENTSTATUS='PENDING' WHERE APPOINTMENTID="+aptId;
            
            
            stmt.executeUpdate(sql);
            updateappointmentwithStaff.accumulate("Status", "Ok");
            updateappointmentwithStaff.accumulate("TimeStamp", time);
            updateappointmentwithStaff.accumulate("message", "Successfull");
            
            stmt.close();
            con.close();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MobileApp.class.getName()).log(Level.SEVERE, null, ex);
          
            updateappointmentwithStaff.accumulate("Status", "Error");
            updateappointmentwithStaff.accumulate("TimeStamp", time);
            updateappointmentwithStaff.accumulate("message", "Invalid details");
            
        } catch (SQLException ex) {
            Logger.getLogger(MobileApp.class.getName()).log(Level.SEVERE, null, ex);
            updateappointmentwithStaff.accumulate("Status", "Error");
            updateappointmentwithStaff.accumulate("Query",sql);
            updateappointmentwithStaff.accumulate("message", ex.getLocalizedMessage());
           
        }

        return updateappointmentwithStaff.toString();
    }
    
    @GET
    @Path("acceptedAppointments&{senderID}")
    @Produces("application/json")
    public String getjson22(@PathParam("senderID") String senderID) {

        
        long time = System.currentTimeMillis();
        JSONObject acceptedAppointments = new JSONObject();
        String sql = "",StaffName="",categoryType="";
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection con;

            con = DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE", "madteam3", "anypw1");
            Statement stmt = con.createStatement();
           
            sql = "SELECT * FROM  (SELECT * FROM APPOINTMENTS WHERE SENDER="+senderID+"  AND APPOINTMENTSTATUS='ACCEPTED') WHERE ROWNUM<=3";
            
            
            ResultSet rs = stmt.executeQuery(sql);
            int num = -1;
                while (rs.next()){
                    num+=1;
                    int startTime = rs.getInt("STARTTIME");
                    int endTime = rs.getInt("ENDTIME");
                    String AppointmentDate = String.valueOf(rs.getDate("APPOINTMENTDATE"));
                    int roomNumber = rs.getInt("ROOMNO");
                    int Category = rs.getInt("CATEGORYCATEGORYID");
                    int StaffId = rs.getInt("STAFFSTAFFID");
                    int SenderId = rs.getInt("SENDER");
                    
                    Statement stmt1 = con.createStatement();
                    Statement stmt2 = con.createStatement();
                    String StaffNameQuery = "SELECT * FROM STAFF WHERE STAFFID="+StaffId;
                    ResultSet rs1 = stmt1.executeQuery(StaffNameQuery);
            
                    while(rs1.next()){
                        StaffName = rs1.getString("FIRSTNAME")+" "+rs1.getString("LASTNAME");
                    }
                    rs1.close();
                    stmt1.close();
                    
                    String CategoryType = "SELECT * FROM CATEGORY WHERE CATEGORYID="+Category;
                    ResultSet rs2 = stmt2.executeQuery(CategoryType);
        
            
                    while(rs2.next()){
                        categoryType = rs2.getString("CATEGORYTYPE");
                    }
                    rs2.close();
                    stmt2.close();
                    
                    JSONObject jObj = new JSONObject();
                    
                    jObj.put("Status", "Ok");
                    jObj.put("TimeStamp", time);
                     jObj.put("StaffId", StaffId);
                     jObj.put("startTime", startTime);
                     jObj.put("endTime", endTime);
                     jObj.put("AppointmentDate", AppointmentDate);
                     jObj.put("roomNumber", roomNumber);
                     jObj.put("Category", categoryType);
                     jObj.put("SenderId", SenderId);
                     jObj.put("StaffName", StaffName);
                     jObj.put("message", "Successfull");
                     acceptedAppointments.put(num, jObj);
                }
            
            stmt.close();
            con.close();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MobileApp.class.getName()).log(Level.SEVERE, null, ex);
          
            acceptedAppointments.accumulate("Status", "Error");
            acceptedAppointments.accumulate("TimeStamp", time);
            acceptedAppointments.accumulate("message", "Invalid details");
            
        } catch (SQLException ex) {
            Logger.getLogger(MobileApp.class.getName()).log(Level.SEVERE, null, ex);
            acceptedAppointments.accumulate("Status", "Error");
            acceptedAppointments.accumulate("Query",sql);
            acceptedAppointments.accumulate("message", ex.getLocalizedMessage());
           
        }

        return acceptedAppointments.toString();
    }   
    
    @GET
    @Path("appointmenthistory&{senderId}&{appointmentdate}")
    @Produces("application/json")
    public String getjson23(@PathParam("senderId") String senderId,@PathParam("appointmentdate") String appointmentdate) {

        
        long time = System.currentTimeMillis();
        JSONObject appointmenthistory = new JSONObject();
        String sql = "",StaffName="",categoryType="";
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection con;

            con = DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE", "madteam3", "anypw1");
            Statement stmt = con.createStatement();
          
           
            sql = "SELECT * FROM APPOINTMENTS WHERE SENDER="+senderId+" AND APPOINTMENTDATE <= DATE '"+appointmentdate+"'";
            
            
            ResultSet rs = stmt.executeQuery(sql);
            
            int num = -1;
                while (rs.next()){
                    num+=1;
                    int aptId = rs.getInt("APPOINTMENTID");
                    int startTime = rs.getInt("STARTTIME");
                    int endTime = rs.getInt("ENDTIME");
                    String AppointmentDate = String.valueOf(rs.getDate("APPOINTMENTDATE"));
                    int roomNumber = rs.getInt("ROOMNO");
                    int Category = rs.getInt("CATEGORYCATEGORYID");
                    int StaffId = rs.getInt("STAFFSTAFFID");
                    int SenderId = rs.getInt("SENDER");
                    String AppointmentStatus = rs.getString("APPOINTMENTSTATUS");
                    Statement stmt1 = con.createStatement();
                    Statement stmt2 = con.createStatement();
                    String StaffNameQuery = "SELECT * FROM STAFF WHERE STAFFID="+StaffId;
                    ResultSet rs1 = stmt1.executeQuery(StaffNameQuery);
            
                    while(rs1.next()){
                        StaffName = rs1.getString("FIRSTNAME")+" "+rs1.getString("LASTNAME");
                    }
                    rs1.close();
                    stmt1.close();
                    
                    String CategoryType = "SELECT * FROM CATEGORY WHERE CATEGORYID="+Category;
                    ResultSet rs2 = stmt2.executeQuery(CategoryType);
        
            
                    while(rs2.next()){
                        categoryType = rs2.getString("CATEGORYTYPE");
                    }
                    rs2.close();
                    stmt2.close();
                    
                    
                    JSONObject jObj = new JSONObject();
                    
                    jObj.put("Status", "Ok");
                    jObj.put("TimeStamp", time);
                     jObj.put("StaffId", StaffId);
                     jObj.put("aptId",aptId);
                     jObj.put("startTime", startTime);
                     jObj.put("endTime", endTime);
                     jObj.put("AppointmentDate", AppointmentDate);
                     jObj.put("roomNumber", roomNumber);
                     jObj.put("Category", categoryType);
                     jObj.put("StaffName", StaffName);
                     jObj.put("aptStatus", AppointmentStatus);
                     jObj.put("message", "Successfull");
                     appointmenthistory.put(num, jObj);
                }
            rs.close();
            stmt.close();
            con.close();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MobileApp.class.getName()).log(Level.SEVERE, null, ex);
          
            appointmenthistory.accumulate("Status", "Error");
            appointmenthistory.accumulate("TimeStamp", time);
            appointmenthistory.accumulate("message", "Invalid details");
            
        } catch (SQLException ex) {
            Logger.getLogger(MobileApp.class.getName()).log(Level.SEVERE, null, ex);
            appointmenthistory.accumulate("Status", "Error");
            appointmenthistory.accumulate("Query",sql);
            appointmenthistory.accumulate("message", ex.getLocalizedMessage());
           
        }

        return appointmenthistory.toString();
    }  
    
    @GET
    @Path("setaAvilability&{SENDERID}&{STARTTIME}&{ENDTIME}&{AVAILABILEDATE}&{AVAILABILEDAY}")
    @Produces("application/json")
    public String getjson24(@PathParam("SENDERID") String SENDERID, @PathParam("STARTTIME") String STARTTIME, @PathParam("ENDTIME") String ENDTIME,@PathParam("AVAILABILEDATE") String AVAILABILEDATE, @PathParam("AVAILABILEDAY") String AVAILABILEDAY) {

        int num = 0,num_of_records=0;
        long time = System.currentTimeMillis();
        JSONObject setaAvilability = new JSONObject();
        String sql = "";
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection con;

            con = DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE", "madteam3", "anypw1");
            Statement stmt = con.createStatement();
           
            String countRecords = "SELECT AVAILABILITYID FROM (SELECT AVAILABILITYID FROM AVAILABILITES ORDER BY AVAILABILITYID DESC) WHERE ROWNUM<=1";
            
            
            ResultSet rs = stmt.executeQuery(countRecords);
            
            if (rs.next()){
                num_of_records = rs.getInt("AVAILABILITYID");
            }
            
            num_of_records+=1;
            sql = "INSERT INTO AVAILABILITES (AVAILABILITYID,STARTTIME,ENDTIME,AVAILABILEDATE,AVAILABILEDAY,STAFFSTAFFID) VALUES("+
                    num_of_records+","+STARTTIME+","+ENDTIME+",DATE '"+AVAILABILEDATE+"','"+AVAILABILEDAY+"',"+SENDERID+")";
            
            num = stmt.executeUpdate(sql);
            
           
                setaAvilability.accumulate("Status", "Ok");
                setaAvilability.accumulate("TimeStamp", time);
                setaAvilability.accumulate("message", "Successfull");
            
            stmt.close();
            con.close();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MobileApp.class.getName()).log(Level.SEVERE, null, ex);
          
            setaAvilability.accumulate("Status", "Error");
            setaAvilability.accumulate("TimeStamp", time);
            setaAvilability.accumulate("message", "Invalid details");
            
        } catch (SQLException ex) {
            Logger.getLogger(MobileApp.class.getName()).log(Level.SEVERE, null, ex);
            setaAvilability.accumulate("Status", "Error");
            setaAvilability.accumulate("TimeStamp", time);
            setaAvilability.accumulate("message", ex.getLocalizedMessage());
            setaAvilability.accumulate("Query", sql);
           
        }

        return setaAvilability.toString();
    } 

    @GET
    @Path("getavailability&{staffid}&{currentDate}")
    @Produces("application/json")
    public String getjson25(@PathParam("staffid") String staffid,@PathParam("currentDate") String currentDate) {
        
        long time = System.currentTimeMillis();
        JSONObject getavailability = new JSONObject();
        String sql = "",SenderName="",categoryType="";
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection con;

            con = DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE", "madteam3", "anypw1");
            Statement stmt = con.createStatement();
           
            sql = "SELECT * FROM  (SELECT * FROM AVAILABILITES WHERE STAFFSTAFFID!="+staffid+"  AND AVAILABILEDATE >= DATE '"+currentDate+"') WHERE ROWNUM<=7";
            
            ResultSet rs = stmt.executeQuery(sql);
            int num = -1;
                while (rs.next()){
                    num+=1;
                    int AvailabilityID = rs.getInt("AVAILABILITYID");
                    int startTime = rs.getInt("STARTTIME");
                    int endTime = rs.getInt("ENDTIME");
                    String ADate = String.valueOf(rs.getDate("AVAILABILEDATE"));
                    String ADay = rs.getString("AVAILABILEDAY");
                    int AvlbStaffID = rs.getInt("STAFFSTAFFID");
                    
                    Statement stmt1 = con.createStatement();
                    String SenderNameQuery = "SELECT * FROM STAFF WHERE STAFFID="+AvlbStaffID;
                    ResultSet rs1 = stmt1.executeQuery(SenderNameQuery);
                    while(rs1.next()){
                        SenderName = rs1.getString("FIRSTNAME")+" "+rs1.getString("LASTNAME");
                    }
                    rs1.close();
                    stmt1.close();
                    
                    JSONObject jObj = new JSONObject();
                    
                    jObj.put("Status", "Ok");
                    jObj.put("TimeStamp", time);
                     jObj.put("StaffId", AvlbStaffID);
                     jObj.put("startTime", startTime);
                     jObj.put("endTime", endTime);
                     jObj.put("Adate", ADate);
                     jObj.put("ADay", ADay);
                     jObj.put("StaffName", SenderName);
                     jObj.put("message", "Successfull");
                     getavailability.put(num, jObj);
                }
            
            stmt.close();
            con.close();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MobileApp.class.getName()).log(Level.SEVERE, null, ex);
          
            getavailability.accumulate("Status", "Error");
            getavailability.accumulate("TimeStamp", time);
            getavailability.accumulate("message", "Invalid details");
            
        } catch (SQLException ex) {
            Logger.getLogger(MobileApp.class.getName()).log(Level.SEVERE, null, ex);
            getavailability.accumulate("Status", "Error");
            getavailability.accumulate("Query",sql);
            getavailability.accumulate("message", ex.getLocalizedMessage());
           
        }

        return getavailability.toString();
    } 
    
    @GET
    @Path("updateprofile&{staffId}&{EMAIL}&{PHONE}")
    @Produces("application/json")
    public String getjson26(@PathParam("staffId") String staffId,@PathParam("EMAIL") String EMAIL,@PathParam("PHONE") String PHONE) {

        
        long time = System.currentTimeMillis();
        JSONObject updateprofile = new JSONObject();
        String sql = "";
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection con;

            con = DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE", "madteam3", "anypw1");
            Statement stmt = con.createStatement();
           
            sql = "UPDATE STAFF SET EMAIL='"+EMAIL+"', PHONE="+PHONE+" WHERE STAFFID="+staffId;
            
            
            stmt.executeUpdate(sql);
            updateprofile.accumulate("Status", "Ok");
            updateprofile.accumulate("TimeStamp", time);
            updateprofile.accumulate("message", "Successfull");
            
            stmt.close();
            con.close();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MobileApp.class.getName()).log(Level.SEVERE, null, ex);
          
            updateprofile.accumulate("Status", "Error");
            updateprofile.accumulate("TimeStamp", time);
            updateprofile.accumulate("message", "Invalid details");
            
        } catch (SQLException ex) {
            Logger.getLogger(MobileApp.class.getName()).log(Level.SEVERE, null, ex);
            updateprofile.accumulate("Status", "Error");
            updateprofile.accumulate("Query",sql);
            updateprofile.accumulate("message", ex.getLocalizedMessage());
           
        }

        return updateprofile.toString();
    }
    
}
    

   
    






              
