package cir.lab.dao;

public class DAOFactory {
    public SubAirDataDAO subAirDataDAO() {
        return new SubAirDataDAO(connectionMaker());
    }


    private ConnectionMaker connectionMaker() {
        return new JDBCConnectionMaker();
    }
}
