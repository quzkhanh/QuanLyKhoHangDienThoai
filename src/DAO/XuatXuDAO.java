/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.ThuocTinhSanPham.XuatXuDTO;
import config.JDBCUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 84907
 */
public class XuatXuDAO implements DAOinterface<XuatXuDTO>{
     public static XuatXuDAO getInstance() {
        return new XuatXuDAO();
    }
    @Override
    public int insert(XuatXuDTO xuatxu) {
        int generatedId = -1;
        Connection conn = null;
        try {
            conn = JDBCUtil.getConnection();
            String sql = "INSERT INTO xuatxu (tenxuatxu) VALUES (?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, xuatxu.getTenxuatxu());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    generatedId = rs.getInt(1);
                    xuatxu.setMaxuatxu(generatedId); // Gán lại id cho DTO
                }
            }
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try { JDBCUtil.closeConnection(conn); } catch (Exception ex) { }
            }
        }
        return generatedId;
    }

    @Override
    public int update(XuatXuDTO t) {
        int result = 0;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "UPDATE `xuatxu` SET `tenxuatxu`=? WHERE `maxuatxu`=?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, t.getTenxuatxu());
            pst.setInt(2, t.getMaxuatxu());
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(XuatXuDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int delete(String t) {
        int result = 0;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "UPDATE `xuatxu` SET `trangthai` = 0 WHERE maxuatxu = ?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1,t);
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(XuatXuDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public ArrayList<XuatXuDTO> selectAll() {
        ArrayList<XuatXuDTO> result = new ArrayList<>();
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT * FROM xuatxu WHERE trangthai = 1";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while (rs.next()) {
                int mahdh = rs.getInt("maxuatxu");
                String tenxuatxu = rs.getString("tenxuatxu");
                XuatXuDTO ms = new XuatXuDTO(mahdh, tenxuatxu);
                result.add(ms);
            }
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
        }
        return result;
    }

    @Override
    public XuatXuDTO selectById(String t) {
        XuatXuDTO result = null;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT * FROM xuatxu WHERE maxuatxu=?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, t);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while (rs.next()) {
                int mahdh = rs.getInt("maxuatxu");
                String tenxuatxu = rs.getString("tenxuatxu");
                result = new XuatXuDTO(mahdh, tenxuatxu);
            }
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
        }
        return result;
    }

    @Override
    public int getAutoIncrement() {
        int result = -1;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT `AUTO_INCREMENT` FROM  INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'quanlikhohang' AND   TABLE_NAME   = 'xuatxu'";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs2 = pst.executeQuery(sql);
            if (!rs2.isBeforeFirst()) {
                System.out.println("No data");
            } else {
                while (rs2.next()) {
                    result = rs2.getInt("AUTO_INCREMENT");

                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(XuatXuDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
