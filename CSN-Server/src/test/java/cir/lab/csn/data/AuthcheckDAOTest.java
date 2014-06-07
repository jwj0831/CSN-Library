package cir.lab.csn.data;

import cir.lab.csn.data.dao.AuthcheckDAO;
import cir.lab.csn.data.db.CSNDAOFactory;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by nfm on 2014. 6. 2..
 */
public class AuthcheckDAOTest {
    Logger logger = LoggerFactory.getLogger(SensorMetadataDAOTest.class);
    AuthcheckDAO dao = new CSNDAOFactory().csnAuthKeyDAO();

    @Before
    public void setUp() {

    }

    @Test
    public void testAddKey() {

    }

    @Test
    public void testCheckHasKey() {

    }

    @After
    public void tearDown() {

    }
}
