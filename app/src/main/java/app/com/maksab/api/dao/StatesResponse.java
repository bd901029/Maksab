package app.com.maksab.api.dao;

import java.util.ArrayList;

/**
 * Created by RWS 6 on 9/2/2017.
 */

public class StatesResponse {
    public String responseMessage = "";
    public String message = "";
    public ArrayList<stateList> stateList;
    public class stateList {
        public String state_id;
        public String state_name;
    }
}
