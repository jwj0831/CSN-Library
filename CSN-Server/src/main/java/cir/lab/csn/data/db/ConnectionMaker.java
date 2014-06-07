package cir.lab.csn.data.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
/**
 * Created by nfm on 2014. 5. 20..
 */
public interface ConnectionMaker {
    public Connection makeConnection() throws ClassNotFoundException, SQLException, IOException;
}