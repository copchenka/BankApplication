package ru.levelup.bank.repository.jdbc;

import lombok.RequiredArgsConstructor;
import ru.levelup.bank.domain.Organization;
import ru.levelup.bank.jdbc.JdbcConnectionManager;
import ru.levelup.bank.repository.OrganizationRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class JdbcOrganizationRepository implements OrganizationRepository {
    private final JdbcConnectionManager cm;

    @Override
    public List<Organization> all() {
        try (Connection conn = cm.openConnection()) {
            Statement stmt = conn.createStatement();
            List<Organization> allOrganization = new ArrayList<>();
            ResultSet rs = stmt.executeQuery("select * from organization");
            while (rs.next()) {
                allOrganization.add(map(rs));
            }
            return allOrganization;
        } catch (SQLException exc) {
            throw new RuntimeException(exc);
        }
    }

    @Override
    public void update(Organization organization) {
        try (Connection conn = cm.openConnection()) {
            PreparedStatement stmt = conn.prepareStatement("update organization set name = ?, vatin = ? where id = ?");
            stmt.setString(1, organization.getName());
            stmt.setString(2, organization.getVatin());
            stmt.setInt(3, organization.getId());
            stmt.executeUpdate();
        } catch (SQLException exc) {
            throw new RuntimeException(exc);
        }
    }

    @Override
    public Organization byVatin(String vatin) {
        try (Connection conn = cm.openConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select * from organization where vatin = ?");
            stmt.setString(1, vatin);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? map(rs) : null;
        } catch (SQLException exc) {
            throw new RuntimeException(exc);
        }
    }

    @Override
    public List<Organization> byName(String name) {
        try (Connection conn = cm.openConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select * from organization where name like ?");
            stmt.setString(1, "%" + name + "%");
            List<Organization> organizations = new ArrayList<>();
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Organization o = map(rs);
                organizations.add(o);
            }
            return organizations;
        } catch (SQLException exc) {
            throw new RuntimeException(exc);
        }
    }

    @Override
    public void assignCustomerToOrganization(Integer organizationId, Integer customerId) {
        try (Connection conn = cm.openConnection()) {
            PreparedStatement stmt = conn.prepareStatement("insert into customers_and_organizations values (?,?)");
            stmt.setInt(1, customerId);
            stmt.setInt(2, organizationId);
            stmt.executeUpdate();
        } catch (SQLException exc) {
            throw new RuntimeException(exc);
        }
    }

    private Organization map(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String vatin = rs.getString("vatin");
        return new Organization(id, name, vatin);
    }
}
