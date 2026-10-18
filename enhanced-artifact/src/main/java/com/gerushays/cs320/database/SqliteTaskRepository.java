package com.gerushays.cs320.database;

import com.gerushays.cs320.exception.DuplicateIdException;
import com.gerushays.cs320.exception.RecordNotFoundException;
import com.gerushays.cs320.model.Task;
import com.gerushays.cs320.repository.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SqliteTaskRepository implements Repository<Task> {
    private final DatabaseManager db;
    public SqliteTaskRepository(DatabaseManager db){this.db=db;db.initializeSchema();}
    @Override public void add(Task t){try(Connection cn=db.getConnection();PreparedStatement ps=cn.prepareStatement("INSERT INTO tasks(task_id,name,description) VALUES(?,?,?)")){ps.setString(1,t.getId());ps.setString(2,t.getName());ps.setString(3,t.getDescription());ps.executeUpdate();}catch(SQLException e){if(e.getMessage()!=null&&e.getMessage().toLowerCase().contains("constraint"))throw new DuplicateIdException(t.getId());throw err(e);}}
    @Override public void update(Task t){try(Connection cn=db.getConnection();PreparedStatement ps=cn.prepareStatement("UPDATE tasks SET name=?,description=? WHERE task_id=?")){ps.setString(1,t.getName());ps.setString(2,t.getDescription());ps.setString(3,t.getId());if(ps.executeUpdate()==0)throw new RecordNotFoundException(t.getId());}catch(SQLException e){throw err(e);}}
    @Override public Optional<Task> findById(String id){valid(id);try(Connection cn=db.getConnection();PreparedStatement ps=cn.prepareStatement("SELECT task_id,name,description FROM tasks WHERE task_id=?")){ps.setString(1,id);try(ResultSet rs=ps.executeQuery()){return rs.next()?Optional.of(new Task(rs.getString(1),rs.getString(2),rs.getString(3))):Optional.empty();}}catch(SQLException e){throw err(e);}}
    @Override public Task requireById(String id){return findById(id).orElseThrow(()->new RecordNotFoundException(id));}
    @Override public void deleteById(String id){valid(id);try(Connection cn=db.getConnection();PreparedStatement ps=cn.prepareStatement("DELETE FROM tasks WHERE task_id=?")){ps.setString(1,id);if(ps.executeUpdate()==0)throw new RecordNotFoundException(id);}catch(SQLException e){throw err(e);}}
    @Override public List<Task> findAll(){List<Task> out=new ArrayList<>();try(Connection cn=db.getConnection();Statement st=cn.createStatement();ResultSet rs=st.executeQuery("SELECT task_id,name,description FROM tasks ORDER BY task_id")){while(rs.next())out.add(new Task(rs.getString(1),rs.getString(2),rs.getString(3)));return List.copyOf(out);}catch(SQLException e){throw err(e);}}
    @Override public int size(){try(Connection cn=db.getConnection();Statement st=cn.createStatement();ResultSet rs=st.executeQuery("SELECT COUNT(*) FROM tasks")){return rs.next()?rs.getInt(1):0;}catch(SQLException e){throw err(e);}}
    private static void valid(String id){if(id==null||id.isBlank())throw new IllegalArgumentException("ID cannot be null or blank.");}
    private static IllegalStateException err(SQLException e){return new IllegalStateException("Task database operation failed.",e);}
}
