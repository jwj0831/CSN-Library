package cir.lab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SubAirDataDAO {
    private ConnectionMaker connectionMaker;

    public SubAirDataDAO(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void add(SubAirData rawData) throws ClassNotFoundException, SQLException {
        Connection c = connectionMaker.makeConnection();

        PreparedStatement ps = c.prepareStatement("INSERT INTO t002_rawdata(snsr_id, val, inp_dt_str) VALUES(?, ?, ?)");
//        ps.setLong(1, rawData.getId());
        ps.setString(2, rawData.getId());
        ps.setDouble(3, Double.parseDouble( rawData.getVal() ) );
        ps.setString(4, rawData.getTime());

        ps.executeUpdate();

        ps.close();
        c.close();
    }

    public SubAirData get(long id) throws ClassNotFoundException, SQLException {
        Connection c = connectionMaker.makeConnection();

        PreparedStatement ps = c.prepareStatement("SELECT * FROM t002_rawdata WHERE id = ? AND snsr_id");
        ps.setLong(1, id);
        ResultSet rs = ps.executeQuery();

        rs.next();
        SubAirData subairData = new SubAirData();
        subairData.setId(rs.getString("snsr_id"));
        subairData.setVal( Double.toString( rs.getDouble("val") ) );
        subairData.setTime(rs.getString("inp_dt_str"));

        rs.close();
        ps.close();
        c.close();

        return subairData;
    }
}
