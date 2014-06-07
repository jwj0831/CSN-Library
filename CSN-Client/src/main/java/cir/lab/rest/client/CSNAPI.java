package cir.lab.rest.client;

/**
 * Created by nfm on 2014. 6. 5..
 */
public interface CSNAPI {

    /**
     *
     * @param json
     * @return
     */
    public String setCSNConfiguration(String json);

    /**
     *
     * @return
     */
    public String getCSNConfiguration();

    /**
     *
     * @return
     */
    public String deleteCSNConfiguration();

    /**
     * This method start the CSN system.
     * @param json
     * @return If successfully done, it returns 1, or -1.
     */
    public String startSystem(String json);

    /**
     * This method restart the CSN system.
     * @param json
     * @return If successfully done, it returns 1, or -1.
     */
    public String restartSystem(String json);

    /**
     * This method stop the CSN system.
     * @param json
     * @return If successfully done, it returns 1, or -1.
     */
    public String stopSystem(String json);
}
