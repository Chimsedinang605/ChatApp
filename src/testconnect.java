/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 *
 * @author ADMIN
 */
public class testconnect {
     private static final String URL = "jdbc:mysql://localhost:3306/messenger_sk"; // Thay đổi tên cơ sở dữ liệu nếu cần
    private static final String USER = "root"; // Thay đổi tên người dùng nếu cần
    private static final String PASSWORD = "Nguyenhong24@"; // Thay đổi mật khẩu tương ứng

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Tạo kết nối
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Kết nối thành công đến cơ sở dữ liệu!");

            // Tạo statement
            stmt = conn.createStatement();

            // Thực thi truy vấn
            String sql = "SELECT * FROM users";
            rs = stmt.executeQuery(sql);

            // Xử lý kết quả
            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("USERNAME");
                String email = rs.getString("EMAIL");

                // In kết quả
                System.out.println("ID: " + id + ", Name: " + name + ", Email: " + email );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng kết nối và các tài nguyên
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
