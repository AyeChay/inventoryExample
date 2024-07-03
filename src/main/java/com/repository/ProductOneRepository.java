package com.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dto.ProductDTO;

public class ProductOneRepository {
	
	public int insertProduct(ProductDTO dto) {
        Connection con = ConnectionClass.getConnection();
        int result = 0;
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO productone (product_code, product_name, description, cat_id, deleted) values (?,?,?,?,?)");
            ps.setString(1, dto.getProductCode());
            ps.setString(2, dto.getProductName());
            ps.setString(3, dto.getDescription());
            ps.setInt(4, dto.getCategoryId());
            ps.setBoolean(5, false);

            result = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Insert Product: " + e.getMessage());
        }
        return result;
    }


	public List<ProductDTO> getAllProducts() {
	    Connection con = ConnectionClass.getConnection();
	    List<ProductDTO> lists = new ArrayList<>();
	    try {
	        PreparedStatement ps = con.prepareStatement(
	            "SELECT p.*, c.* FROM productone p INNER JOIN category c ON p.cat_id = c.cat_id where p.deleted= FALSE "
	        );
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            ProductDTO dto = new ProductDTO();
	            dto.setId(rs.getInt("id"));
	            dto.setProductCode(rs.getString("product_code"));
	            dto.setProductName(rs.getString("product_name"));
	            dto.setDescription(rs.getString("description"));
	            dto.setCategoryId(rs.getInt("cat_id"));
	            dto.setCategoryName(rs.getString("name"));
	            dto.setDeleted(rs.getBoolean("deleted"));
	            
	            lists.add(dto);
	        }
	    } catch (SQLException e) {
	        System.out.println("Get All Product: " + e.getMessage());
	    }
	    return lists;
	}



	
	public ProductDTO getProductById(int id) {
	    ProductDTO productDTO = null;
	    Connection con = ConnectionClass.getConnection();
	    try {
	        PreparedStatement ps = con.prepareStatement(
	            "SELECT p.*, c.* FROM productone p INNER JOIN category c ON p.cat_id = c.cat_id WHERE p.id = ? and p.deleted = FALSE"
	        );
	        ps.setInt(1, id);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            productDTO = new ProductDTO();
	            productDTO.setId(rs.getInt("id"));
	            productDTO.setProductCode(rs.getString("product_code"));
	            productDTO.setProductName(rs.getString("product_name"));
	            productDTO.setDescription(rs.getString("description"));
	            productDTO.setCategoryId(rs.getInt("cat_id"));
	            productDTO.setCategoryName(rs.getString("cat_id"));
	            productDTO.setDeleted(rs.getBoolean("deleted"));
	        }
	    } catch (SQLException e) {
	        System.out.println("Get Product By Id: " + e.getMessage());
	    }
	    return productDTO;
	}


    public int updateProduct(ProductDTO dto) {
        Connection con = ConnectionClass.getConnection();
        int result = 0;
        try {
            PreparedStatement ps = con.prepareStatement(
                "UPDATE productone SET product_code = ?, product_name = ?, description = ?, cat_id = ? WHERE id = ? "
            );
            ps.setString(1, dto.getProductCode());
            ps.setString(2, dto.getProductName());
            ps.setString(3, dto.getDescription());
            ps.setInt(4, dto.getCategoryId());
            
            ps.setInt(5, dto.getId());

            result = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Update Product: " + e.getMessage());
        }
        return result;
    }
    
    public int softDeleteProduct(int id) {
        Connection con = ConnectionClass.getConnection();
        int result = 0;
        try {
            PreparedStatement ps = con.prepareStatement("UPDATE productone SET deleted = TRUE WHERE id = ?");
            ps.setInt(1, id);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Soft Delete Product: " + e.getMessage());
        }
        return result;
    }
}
