package com.binghe.ch03.dao;

import com.binghe.ch03.domain.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;

/**
 * 리팩토링하기 전
 * 가장 기본적인 try/catch/finally 예외처리를 한 UserDao
 */
public class UserDao {
    private final DataSource dataSource;

    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void add(User user) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = dataSource.getConnection();
            // SQL 실행 관심
            ps = conn.prepareStatement("insert into users(id, name, password) values(?, ?, ?)");
            ps.setString(1, user.getId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getPassword());

            ps.executeUpdate();
        } catch (SQLException e){
            throw e;
        } finally {
            // 리소스를 반환하는 관심
            if(ps != null){
                try{
                    ps.close();
                } catch (SQLException e){}
            }
            if(conn != null){
                try{
                    conn.close();
                } catch (SQLException e){}
            }
        }
    }

    public User get(String id) throws SQLException {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;

        try {
            conn = dataSource.getConnection();

            ps = conn.prepareStatement("select * from users where id =?");
            ps.setString(1, id);

            rs = ps.executeQuery();
            // id를 조건으로 한 쿼리의 결과가 있으면 User에 넣는다.
            if(rs.next()){
                user = new User();
                user.setId(rs.getString("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
            }
            // user에 넣어진 쿼리 결과가 없다면 (존재하지 않는 id라면) 에러 던지기
            if(user == null) throw new EmptyResultDataAccessException(1);
        } catch (SQLException e){
            throw e;
        } finally {
            // 리소스를 반환하는 관심
            if(rs != null){
                try{
                    rs.close();
                } catch (SQLException e){}
            }
            if(ps != null){
                try{
                    ps.close();
                } catch (SQLException e){}
            }
            if(conn != null){
                try{
                    conn.close();
                } catch (SQLException e){}
            }
        }

        return user;
    }

    public void deleteAll() throws SQLException {
        // DB 연결 관심
        Connection conn = null;

        // SQL 실행 관심
        PreparedStatement ps = null;

        try {
            conn = dataSource.getConnection();

            ps = conn.prepareStatement("delete from users");
            ps.executeUpdate();
        } catch (SQLException e){
            throw e;
        } finally {
            if(ps != null){
                try{
                    ps.close();
                } catch (SQLException e){}
            }
            if(conn != null){
                try{
                    conn.close();
                } catch (SQLException e){}
            }
        }
    }

    public int getCount() throws SQLException {
        // DB 연결 관심
        Connection conn = null;

        // SQL 관심
        PreparedStatement ps = null;

        // 결과
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();

            ps = conn.prepareStatement("select count(*) from users");

            rs = ps.executeQuery();
            rs.next();

            return rs.getInt(1);
        } catch (SQLException e){
            throw  e;
        } finally {
            // 리소스를 반환하는 관심
            if(rs != null){
                try{
                    rs.close();
                } catch (SQLException e){}
            }
            if(ps != null){
                try{
                    ps.close();
                } catch (SQLException e){}
            }
            if(conn != null){
                try{
                    conn.close();
                } catch (SQLException e){}
            }
        }
    }
}
