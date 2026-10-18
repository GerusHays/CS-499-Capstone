package com.gerushays.cs320.database;

import com.gerushays.cs320.exception.DuplicateIdException;
import com.gerushays.cs320.exception.RecordNotFoundException;
import com.gerushays.cs320.model.Contact;
import com.gerushays.cs320.repository.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SqliteContactRepository implements Repository<Contact> {
    private final DatabaseManager db;
    public SqliteContactRepository(DatabaseManager db) { this.db = db; db.initializeSchema(); }

    @Override public void add(Contact c) {
        String sql = "INSERT INTO contacts(contact_id, first_name, last_name, phone, address) VALUES(?,?,?,?,?)";
        try (Connection cn = db.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            bind(ps, c); ps.executeUpdate();
        } catch (SQLException e) { if (isDuplicateKey(e)) throw new DuplicateIdException(c.getId()); throw databaseError(e); }
    }
    @Override public void update(Contact c) {
        String sql = "UPDATE contacts SET first_name=?, last_name=?, phone=?, address=? WHERE contact_id=?";
        try (Connection cn = db.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1,c.getFirstName()); ps.setString(2,c.getLastName()); ps.setString(3,c.getPhone()); ps.setString(4,c.getAddress()); ps.setString(5,c.getId());
            if (ps.executeUpdate()==0) throw new RecordNotFoundException(c.getId());
        } catch (SQLException e) { throw databaseError(e); }
    }
    @Override public Optional<Contact> findById(String id) {
        validateId(id); String sql="SELECT contact_id,first_name,last_name,phone,address FROM contacts WHERE contact_id=?";
        try(Connection cn=db.getConnection(); PreparedStatement ps=cn.prepareStatement(sql)){ ps.setString(1,id); try(ResultSet rs=ps.executeQuery()){ return rs.next()?Optional.of(map(rs)):Optional.empty(); }} catch(SQLException e){throw databaseError(e);}
    }
    @Override public Contact requireById(String id){ return findById(id).orElseThrow(() -> new RecordNotFoundException(id)); }
    @Override public void deleteById(String id){ validateId(id); try(Connection cn=db.getConnection(); PreparedStatement ps=cn.prepareStatement("DELETE FROM contacts WHERE contact_id=?")){ps.setString(1,id); if(ps.executeUpdate()==0) throw new RecordNotFoundException(id);}catch(SQLException e){throw databaseError(e);} }
    @Override public List<Contact> findAll(){ List<Contact> out=new ArrayList<>(); try(Connection cn=db.getConnection(); PreparedStatement ps=cn.prepareStatement("SELECT contact_id,first_name,last_name,phone,address FROM contacts ORDER BY contact_id"); ResultSet rs=ps.executeQuery()){while(rs.next())out.add(map(rs)); return List.copyOf(out);}catch(SQLException e){throw databaseError(e);} }
    @Override public int size(){ try(Connection cn=db.getConnection(); Statement st=cn.createStatement(); ResultSet rs=st.executeQuery("SELECT COUNT(*) FROM contacts")){return rs.next()?rs.getInt(1):0;}catch(SQLException e){throw databaseError(e);} }
    private static void bind(PreparedStatement ps, Contact c)throws SQLException{ps.setString(1,c.getId());ps.setString(2,c.getFirstName());ps.setString(3,c.getLastName());ps.setString(4,c.getPhone());ps.setString(5,c.getAddress());}
    private static Contact map(ResultSet rs)throws SQLException{return new Contact(rs.getString("contact_id"),rs.getString("first_name"),rs.getString("last_name"),rs.getString("phone"),rs.getString("address"));}
    private static boolean isDuplicateKey(SQLException e){return e.getMessage()!=null && (e.getMessage().contains("UNIQUE constraint failed") || e.getMessage().contains("PRIMARY KEY constraint failed"));}
    private static IllegalStateException databaseError(SQLException e){return new IllegalStateException("Contact database operation failed.",e);}
    private static void validateId(String id){if(id==null||id.isBlank())throw new IllegalArgumentException("ID cannot be null or blank.");}
}
