package DataModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import javax.servlet.http.HttpSession;

import Entity.SessionUtils;
import Entity.User;

public class QueryData {

    public static boolean validate(String uname, String password) throws ClassNotFoundException {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = JavaConnectDB.ConnectDB();
            ps = con.prepareStatement("Select uname, password from user where uname = ? and password = ?");
            ps.setString(1, uname);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                //result found, means valid inputs
                User user = new User(rs.getInt("userID"), rs.getString("uname"), rs.getString("password"), rs.getString("hobby"),
                        rs.getString("study"), rs.getString("research"), rs.getString("record"), rs.getString("skill"),
                        rs.getString("dream"), rs.getString("introduce"));
                HttpSession session = SessionUtils.getSession();
                session.setAttribute("user", user);
                return true;
            }
        } catch (SQLException ex) {
            System.out.println("Login error -->" + ex.getMessage());
            return false;
        } finally {
            JavaConnectDB.close(con);
        }
        return false;
    }

    public static void updateUser(User user) throws SQLException, ClassNotFoundException {
        try (Connection connect = JavaConnectDB.ConnectDB()) {
            PreparedStatement pstm = connect.prepareStatement("update user set uname = ?, hobby=?, study = ?, research = ?, record = ?, skill = ?, dream = ?, introduce = ? where userID=?");
            pstm.setString(1, user.getUname());
            pstm.setString(2, user.getHobby());
            pstm.setString(3, user.getStudy());
            pstm.setString(4, user.getResearch());
            pstm.setString(5, user.getRecord());
            pstm.setString(6, user.getSkill());
            pstm.setString(7, user.getDream());
            pstm.setString(8, user.getIntroduce());
            pstm.setInt(9, user.getUserid());
            
            pstm.executeUpdate();
        }
    }
}
