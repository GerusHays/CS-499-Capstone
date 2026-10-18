package com.gerushays.cs320.database;

import com.gerushays.cs320.exception.DuplicateIdException;
import com.gerushays.cs320.exception.RecordNotFoundException;
import com.gerushays.cs320.model.Appointment;
import com.gerushays.cs320.repository.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class SqliteAppointmentRepository implements Repository<Appointment> {
    private final DatabaseManager db;
    public SqliteAppointmentRepository(DatabaseManager db){this.db=db;db.initializeSchema();}
    @Override public void add(Appointment a){try(Connection cn=db.getConnection();PreparedStatement ps=cn.prepareStatement("INSERT INTO appointments(appointment_id,appointment_date,description) VALUES(?,?,?)")){ps.setString(1,a.getId());ps.setLong(2,a.getAppointmentDate().getTime());ps.setString(3,a.getDescription());ps.executeUpdate();}catch(SQLException e){if(isDuplicateKey(e))throw new DuplicateIdException(a.getId());throw err(e);}}
    @Override public void update(Appointment a){try(Connection cn=db.getConnection();PreparedStatement ps=cn.prepareStatement("UPDATE appointments SET appointment_date=?,description=? WHERE appointment_id=?")){ps.setLong(1,a.getAppointmentDate().getTime());ps.setString(2,a.getDescription());ps.setString(3,a.getId());if(ps.executeUpdate()==0)throw new RecordNotFoundException(a.getId());}catch(SQLException e){throw err(e);}}
    @Override public Optional<Appointment> findById(String id){valid(id);try(Connection cn=db.getConnection();PreparedStatement ps=cn.prepareStatement("SELECT appointment_id,appointment_date,description FROM appointments WHERE appointment_id=?")){ps.setString(1,id);try(ResultSet rs=ps.executeQuery()){return rs.next()?Optional.of(map(rs)):Optional.empty();}}catch(SQLException e){throw err(e);}}
    @Override public Appointment requireById(String id){return findById(id).orElseThrow(()->new RecordNotFoundException(id));}
    @Override public void deleteById(String id){valid(id);try(Connection cn=db.getConnection();PreparedStatement ps=cn.prepareStatement("DELETE FROM appointments WHERE appointment_id=?")){ps.setString(1,id);if(ps.executeUpdate()==0)throw new RecordNotFoundException(id);}catch(SQLException e){throw err(e);}}
    @Override public List<Appointment> findAll(){List<Appointment> out=new ArrayList<>();try(Connection cn=db.getConnection();Statement st=cn.createStatement();ResultSet rs=st.executeQuery("SELECT appointment_id,appointment_date,description FROM appointments ORDER BY appointment_id")){while(rs.next())out.add(map(rs));return List.copyOf(out);}catch(SQLException e){throw err(e);}}
    @Override public int size(){try(Connection cn=db.getConnection();Statement st=cn.createStatement();ResultSet rs=st.executeQuery("SELECT COUNT(*) FROM appointments")){return rs.next()?rs.getInt(1):0;}catch(SQLException e){throw err(e);}}
    private static Appointment map(ResultSet rs)throws SQLException{return new Appointment(rs.getString(1),new Date(rs.getLong(2)),rs.getString(3));}
    private static boolean isDuplicateKey(SQLException e){return e.getMessage()!=null && (e.getMessage().contains("UNIQUE constraint failed") || e.getMessage().contains("PRIMARY KEY constraint failed"));}
    private static void valid(String id){if(id==null||id.isBlank())throw new IllegalArgumentException("ID cannot be null or blank.");}
    private static IllegalStateException err(SQLException e){return new IllegalStateException("Appointment database operation failed.",e);}
}
