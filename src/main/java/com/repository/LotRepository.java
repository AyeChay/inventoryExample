package com.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dto.LotDTO;

public class LotRepository {
	public int insertLot(LotDTO dto) {
		String newlotId = generateLotId();
		dto.setLotNo(newlotId);
        Connection con = ConnectionClass.getConnection();
        int result = 0;
        try {
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO lotone (p_id, cat_id, lot_no, qunatity, uom, date, expired_date, price, location_id, deleted) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
            );
            ps.setInt(1, dto.getP_id());
            ps.setInt(2, dto.getCategoryId());
            ps.setString(3, dto.getLotNo());
            ps.setDouble(4, dto.getQuantity());
            ps.setString(5, dto.getUom());
            ps.setDate(6, new java.sql.Date(dto.getDate().getTime()));
            ps.setDate(7, new java.sql.Date(dto.getExpiredDate().getTime()));
            ps.setDouble(8, dto.getPrice());
            ps.setInt(9, dto.getLocationId());
            ps.setBoolean(10, false);

            result = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Insert Lot: " + e.getMessage());
        }
        return result;
    }
	public String generateLotId() {
	    String newLotId = null;
	    Connection con = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    
	    try {
	        con = ConnectionClass.getConnection();
	        ps = con.prepareStatement("SELECT lot_no FROM lotone ORDER BY id DESC LIMIT 1");
	        rs = ps.executeQuery();
	        
	        if (rs.next()) {
	            String lastlotId = rs.getString("lot_no");
	            if (lastlotId != null && lastlotId.startsWith("LOT")) {
	                int num = Integer.parseInt(lastlotId.substring(3)) + 1;
	                newLotId = String.format("LOT%03d", num);
	            } else {
	            	newLotId = "LOT001"; // Initial user ID if no records are found
	            }
	        } else {
	        	newLotId = "LOT001"; // Initial user ID if table is empty
	        }
	    } catch (SQLException e) {
	        System.out.println("lot No generation error: " + e.getMessage());
	    } finally {
	        
	        if (rs != null) {
	            try {
	                rs.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        if (ps != null) {
	            try {
	                ps.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        if (con != null) {
	            try {
	                con.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	    
	    return newLotId;
	}

    public List<LotDTO> getAllLots() {
        Connection con = ConnectionClass.getConnection();
        List<LotDTO> lists = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement(
                "SELECT l.*, p.*, c.*, loc.* FROM lotone l " +
                "INNER JOIN productone p ON l.p_id = p.id " +
                "INNER JOIN category c ON l.cat_id = c.cat_id " +
                "INNER JOIN location loc ON l.location_id = loc.location_id " +
                "WHERE l.deleted = FALSE"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LotDTO dto = new LotDTO();
                dto.setId(rs.getInt("id"));
                dto.setP_id(rs.getInt("p_id"));
                dto.setProductCode(rs.getString("product_code"));
                dto.setProductName(rs.getString("product_name"));
                dto.setProductdescription(rs.getString("description"));
                dto.setCategoryId(rs.getInt("cat_id"));
                dto.setCategoryName(rs.getString("name"));
                dto.setLotNo(rs.getString("lot_no"));
                dto.setQuantity(rs.getDouble("qunatity"));
                dto.setUom(rs.getString("uom"));
                dto.setDate(rs.getDate("date"));
                dto.setExpiredDate(rs.getDate("expired_date"));
                dto.setPrice(rs.getDouble("price"));
                dto.setLocationId(rs.getInt("location_id"));
                dto.setLocationName(rs.getString("lname"));
                dto.setDeleted(rs.getBoolean("deleted"));
                
                lists.add(dto);
            }
        } catch (SQLException e) {
            System.out.println("Get All Lots: " + e.getMessage());
        }
        return lists;
    }

    public LotDTO getLotById(int id) {
        LotDTO lotDTO = null;
        Connection con = ConnectionClass.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(
                "SELECT l.*, p.*, c.*, loc.* FROM lotone l " +
                "INNER JOIN productone p ON l.p_id = p.id " +
                "INNER JOIN category c ON l.cat_id = c.cat_id " +
                "INNER JOIN location loc ON l.location_id = loc.location_id " +
                "WHERE l.id = ? AND l.deleted = FALSE"
            );
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                lotDTO = new LotDTO();
                lotDTO.setId(rs.getInt("id"));
                lotDTO.setP_id(rs.getInt("p_id"));
                lotDTO.setProductCode(rs.getString("product_code"));
                lotDTO.setProductName(rs.getString("product_name"));
                lotDTO.setProductdescription(rs.getString("description"));
                lotDTO.setCategoryId(rs.getInt("cat_id"));
                lotDTO.setCategoryName(rs.getString("name"));
                lotDTO.setLotNo(rs.getString("lot_no"));
                lotDTO.setQuantity(rs.getDouble("qunatity"));
                lotDTO.setUom(rs.getString("uom"));
                lotDTO.setDate(rs.getDate("date"));
                lotDTO.setExpiredDate(rs.getDate("expired_date"));
                lotDTO.setPrice(rs.getDouble("price"));
                lotDTO.setLocationId(rs.getInt("location_id"));
                lotDTO.setLocationName(rs.getString("lname"));
                lotDTO.setDeleted(rs.getBoolean("deleted"));
            }
        } catch (SQLException e) {
            System.out.println("Get Lot By Id: " + e.getMessage());
        }
        return lotDTO;
    }

    public int updateLot(LotDTO dto) {
        Connection con = ConnectionClass.getConnection();
        int result = 0;
        try {
            PreparedStatement ps = con.prepareStatement(
                "UPDATE lotone SET p_id = ?, cat_id = ?, qunatity = ?, uom = ?, date = ?, expired_date = ?, price = ?, location_id = ? " +
                "WHERE id = ?"
            );
            ps.setInt(1, dto.getP_id());
            
            ps.setInt(2, dto.getCategoryId());
            ps.setDouble(3, dto.getQuantity());
            ps.setString(4, dto.getUom());
            ps.setDate(5, new java.sql.Date(dto.getDate().getTime()));
            ps.setDate(6, new java.sql.Date(dto.getExpiredDate().getTime()));
            ps.setDouble(7, dto.getPrice());
            ps.setInt(8, dto.getLocationId());
            ps.setInt(9, dto.getId());

            result = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Update Lot: " + e.getMessage());
        }
        return result;
    }

    public int softDeleteLot(int id) {
        Connection con = ConnectionClass.getConnection();
        int result = 0;
        try {
            PreparedStatement ps = con.prepareStatement("UPDATE lotone SET deleted = TRUE WHERE id = ?");
            ps.setInt(1, id);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Soft Delete Lot: " + e.getMessage());
        }
        return result;
    }
}
