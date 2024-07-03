package com.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dto.CategoryDTO;
import com.dto.LocationDTO;

public class LocationRepository {
	public int insertLocation(LocationDTO dto) {
        Connection con = ConnectionClass.getConnection();
        int result = 0;
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO location (lname, address, deleted) values (?,?,?)");
            ps.setString(1, dto.getName());
            ps.setString(2, dto.getAddress());
            ps.setBoolean(3, false);

            result = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Insert Location: " + e.getMessage());
        }
        return result;
    }

    public List<LocationDTO> getAllLocations() {
        Connection con = ConnectionClass.getConnection();
        List<LocationDTO> lists = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM location WHERE deleted = FALSE");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LocationDTO dto = new LocationDTO();
                dto.setId(rs.getInt("location_id"));
                dto.setName(rs.getString("lname"));
                dto.setAddress(rs.getString("address"));
                dto.setDeleted(rs.getBoolean("deleted"));
                lists.add(dto);
            }
        } catch (SQLException e) {
            System.out.println("Get All Locations: " + e.getMessage());
        }
        return lists;
    }
    
    public List<LocationDTO> searchLocationsByNameAndAddress(String locationName, String locationAddress) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<LocationDTO> locations = new ArrayList<>();

        try {
            con = ConnectionClass.getConnection();
            String sql = "SELECT location_id, lname, address FROM location WHERE "
                       + "(lname LIKE ? OR ? IS NULL) "
                       + "AND (address LIKE ? OR ? IS NULL)";
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + locationName + "%");
            ps.setString(2, locationName);
            ps.setString(3, "%" + locationAddress + "%");
            ps.setString(4, locationAddress);

            rs = ps.executeQuery();

            while (rs.next()) {
                LocationDTO location = new LocationDTO();
                location.setId(rs.getInt("location_id"));
                location.setName(rs.getString("lname"));
                location.setAddress(rs.getString("address"));

                locations.add(location);
            }
        } catch (SQLException e) {
            System.out.println("Search Locations: " + e.getMessage());
        }

        return locations;
    }
    
    public boolean checkWarehousename(String name) {
        Connection con = ConnectionClass.getConnection();
        boolean status = false;
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM location WHERE lname=?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                status = true;
            }
        } catch (SQLException e) {
            System.out.println("Already warehousename error: " + e.getMessage());
        }
        return status;
    }

    public LocationDTO getLocationById(int id) {
        LocationDTO locationDTO = null;
        Connection con = ConnectionClass.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM location WHERE location_id=? AND deleted = FALSE");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                locationDTO = new LocationDTO();
                locationDTO.setId(rs.getInt("location_id"));
                locationDTO.setName(rs.getString("lname"));
                locationDTO.setAddress(rs.getString("address"));
                locationDTO.setDeleted(rs.getBoolean("deleted"));
            }
        } catch (SQLException e) {
            System.out.println("Get Location By Id: " + e.getMessage());
        }
        return locationDTO;
    }

    public int updateLocation(LocationDTO dto) {
        Connection con = ConnectionClass.getConnection();
        int result = 0;
        try {
            PreparedStatement ps = con.prepareStatement("UPDATE location SET lname = ?, address = ? WHERE location_id = ?");
            ps.setString(1, dto.getName());
            ps.setString(2, dto.getAddress());
            ps.setInt(3, dto.getId());

            result = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Update Location: " + e.getMessage());
        }
        return result;
    }

    public int softDeleteLocation(int id) {
        Connection con = ConnectionClass.getConnection();
        int result = 0;
        try {
            PreparedStatement ps = con.prepareStatement("UPDATE location SET deleted = TRUE WHERE location_id = ?");
            ps.setInt(1, id);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Soft Delete Location: " + e.getMessage());
        }
        return result;
    }
}
