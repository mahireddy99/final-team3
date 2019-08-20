# SCHEDULING APP
## BACKEND CODE:
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainPackage;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
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
                login.accumulate("STAFFID", staffid);

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
            String firstName, lastName, email, designation,phone;

            while (rs.next()) {
                staffId = rs.getInt("STAFFID");
                firstName = rs.getString("FIRSTNAME");
                lastName = rs.getString("LASTNAME");
                email = rs.getString("EMAIL");
                designation = rs.getString("DESIGNATION");
                phone = rs.getString("PHONE");

               singleStaff .accumulate("STAFFID", staffId);
                singleStaff .accumulate("FIRSTNAME", firstName);
                singleStaff .accumulate("LASTNAME", lastName);
                singleStaff .accumulate("EMAIL", email);
                singleStaff .accumulate("DESIGNATION", designation);
                singleStaff .accumulate("PHONE", phone);

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
           String firstName, lastName, email, designation,phone;
           
               while(rs.next()){
               singleStaff.clear();
                staffid=rs.getInt("STAFFID");
                firstName=rs.getString("FIRSTNAME");
                lastName=rs.getString("LASTNAME");
                email=rs.getString("EMAIL");
                designation=rs.getString("DESIGNATION");
                phone=rs.getString("PHONE");
                  
                singleStaff.accumulate("Status","ok");
                singleStaff.accumulate("TimeStamp",time);
               singleStaff.accumulate("message","exists");

                           
               singleStaff.accumulate("STAFFID",staffid);
               singleStaff.accumulate("FRISTNAME",firstName);
               singleStaff.accumulate("LASTNAME",lastName);
                singleStaff.accumulate("EMAIL",email);
                singleStaff.accumulate("DESIGNATION",designation);
                singleStaff.accumulate("PHONE",phone);
        
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
    public String getjson5(@PathParam("id") int APPOINTMENTID) {

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
            String sql = "SELECT * FROM appointments where appointmentid="+APPOINTMENTID;

            ResultSet rs = stm.executeQuery(sql);
           
             int appointmentid,starttime,endtime,roomno,staffstaffid,categorycategoryid;
            
            String appointmentdate;

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
               
              singleAppointment .accumulate("APPOINTMENTID", appointmentid );
              singleAppointment.accumulate("STARTTIME",starttime);
              singleAppointment.accumulate("ENDTIME",endtime);
              singleAppointment.accumulate("ROOMNO",roomno);
              singleAppointment.accumulate("STAFFSTAFFID",staffstaffid);
              singleAppointment.accumulate("CATEGORYCATEGORYID",categorycategoryid);
              singleAppointment.accumulate("APPOINTMENTDATE", appointmentdate);
              
               
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
                
                
               singleStaffAppointments .accumulate("STAFFID",staffId );
               singleStaffAppointments .accumulate("APPOINTMENTID", appointmentid );
               singleStaffAppointments.accumulate("STARTTIME",starttime);
               singleStaffAppointments.accumulate("ENDTIME",endtime);
               singleStaffAppointments.accumulate("ROOMNO",roomno);
               singleStaffAppointments.accumulate("STAFFSTAFFID",staffstaffid);
               singleStaffAppointments.accumulate("CATEGORYCATEGORYID",categorycategoryid);
               singleStaffAppointments .accumulate("FIRSTNAME", firstName);
               singleStaffAppointments .accumulate("LASTNAME", lastName);
               singleStaffAppointments .accumulate("EMAIL", email);
               singleStaffAppointments .accumulate("DESIGNATION", designation);
               singleStaffAppointments .accumulate("PHONE", phone);
                singleStaffAppointments.accumulate("APPOINTMENTDATE",appointmentdate);
               
               
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
            String categorytype;
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
    public String getjson11(@PathParam("id") int AVAILABILITYID) {

        long time = System.currentTimeMillis();
        JSONObject singleAvailable = new JSONObject();

       singleAvailable.accumulate("Status", "error");
        singleAvailable.accumulate("TimeStamp", time);
        singleAvailable.accumulate("messgae", "not exists");
        
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE","madteam3","anypw1");
            
            Statement stm = con.createStatement();
            String sql = "SELECT * FROM availabilites where availabilityid="+AVAILABILITYID;

            ResultSet rs = stm.executeQuery(sql);
            
            int availabilityid,starttime,endtime,staffstaffid;
            String availabileday;
            String availabiledate;

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

               singleAvailable.accumulate("AVAILABILITYID", availabilityid);
               singleAvailable.accumulate("STARTTIME",starttime);
               singleAvailable.accumulate("ENDTIME",endtime);
               
               singleAvailable.accumulate("AVAILABILEDATE",availabiledate);
               singleAvailable.accumulate("AVAILABILEDAY",availabileday);
               singleAvailable.accumulate("STAFFSTAFFID",staffstaffid);
               
               
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

                           
               singleStaff.accumulate("STAFFID",staffid);
               singleStaff.accumulate("AVAILABILITYID",availabilityid);
               singleStaff.accumulate("STARTTIME", starttime);
               singleStaff.accumulate("ENDTIME", endtime);
               singleStaff.accumulate("FRISTNAME",firstName);
               singleStaff.accumulate("LASTNAME",lastName);
                singleStaff.accumulate("EMAIL",email);
                singleStaff.accumulate("DESIGNATION",designation);
                singleStaff.accumulate("PHONE",phone);
                singleStaff.accumulate("AVAILABILEDAY",availableday);
                singleStaff.accumulate("AVAILABILEDATE",availabledate);
        
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
        
           String  firstName, lastName, email, designation,phone,appointmentdate,categorytype,availabileday,availabiledate;
           
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

                           
               singleStaff.accumulate("STAFFID",staffid);
               singleStaff.accumulate("FRISTNAME",firstName);
               singleStaff.accumulate("LASTNAME",lastName);
               singleStaff.accumulate("EMAIL",email);
               singleStaff.accumulate("DESIGNATION",designation);
               singleStaff.accumulate("PHONE",phone);
               singleStaff.accumulate("CATEGORYID", categoryid );
               singleStaff.accumulate("APPOINTMENTID", appointmentid );
               singleStaff.accumulate("STARTTIME",starttime);
               singleStaff.accumulate("ENDTIME",endtime);
               singleStaff.accumulate("ROOMNO",roomno);
               singleStaff.accumulate("STAFFSTAFFID",staffstaffid);
               singleStaff.accumulate("CATEGORYCATEGORYID",categorycategoryid);
               singleStaff.accumulate("APPOINTMENTDATE",appointmentdate);
               singleStaff.accumulate("CATEGORYTYPE", categorytype);
               singleStaff.accumulate("AVAILABILITYID", availabilityid);
               singleStaff.accumulate("AVAILABILEDATE",availabiledate);
               singleStaff.accumulate("AVAILABILEDAY",availabileday);
        
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
    @Path("updateappointment&{STARTTIME}&{ENDTIME}&{APPOINTMENTDATE}&{ROOMNO}&{STAFFSTAFFID}&{CATEGORYCATEGORYID}")
    @Produces("application/json")
    public String getjson15(@PathParam("STARTTIME") String STARTTIME, @PathParam("ENDTIME") String ENDTIME, @PathParam("APPOINTMENTDATE") String APPOINTMENTDATE, @PathParam("ROOMNO") String ROOMNO, @PathParam("STAFFSTAFFID") String STAFFSTAFFID, @PathParam("CATEGORYCATEGORYID") String CATEGORYCATEGORYID) {

        int num=0,num_of_records=0;
        long time = System.currentTimeMillis();
        JSONObject updateappointment = new JSONObject();
        

        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection con;

            con = DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE", "madteam3", "anypw1");

            Statement stm = con.createStatement();
            String countRecords = "SELECT APPOINTMENTID FROM (SELECT APPOINTMENTID FROM APPOINTMENTS ORDER BY APPOINTMENTID DESC) WHERE ROWNUM<=1";

            ResultSet rs = stm.executeQuery(countRecords);

            

            if (rs.next()) {
                    
                num_of_records=rs.getInt("APPOINTMENTID");
            
              num_of_records+=1;
            }
              String sql="UPDATE APPOINTMENTS SET (STARTTIME="+STARTTIME+",ENDTIME="+ENDTIME+","
                      + "APPOINTMENTDATE="+APPOINTMENTDATE+",STAFFSTAFFID="+STAFFSTAFFID+","
                      + "CATEGORYCATEGORYID="+CATEGORYCATEGORYID+") where APPOINTMENTID=APPOINTMENTID";
              
              
          
               num = stm.executeUpdate(sql);

            
            
            if (num== 1) {
                  
                updateappointment.accumulate("Status", "ok");
                updateappointment.accumulate("TimeStamp", time);
                updateappointment.accumulate("message", "Successfull");
               

            }
            else{
                updateappointment.accumulate("Status", "error");
                updateappointment.accumulate("TimeStamp", time);
                updateappointment.accumulate("message", "something goes wrong ");
            }
            
            
            stm.close();
            con.close();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MobileApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MobileApp.class.getName()).log(Level.SEVERE, null, ex);
           
        }

        return updateappointment.toString();
    }

}


    

   
    

