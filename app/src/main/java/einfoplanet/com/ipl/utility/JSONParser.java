package einfoplanet.com.ipl.utility;

import android.content.Context;
import android.database.Cursor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import einfoplanet.com.ipl.constants.Constants;
import einfoplanet.com.ipl.loacaldatabase.PlayerDBHandler;
import einfoplanet.com.ipl.loacaldatabase.TeamDBHandler;
import einfoplanet.com.ipl.model.PlayerModel;
import einfoplanet.com.ipl.model.TeamModel;

/**
 * 
 * 1)This is the utility class which we can use to get parsed json data
 * from the JSONArray.
 * 2)There are two methods parseTeamViewModelContent() and parseTeamPlayerViewModelContent()
 * to which we are passing the JSONArray as a parameter and it will return the
 * List<IPLTeamViewModel> and List<IPLTeamPlayerViewModel> respectively.
 */
public class JSONParser {
    static ArrayList<TeamModel> mTeamModelData;
    static ArrayList<PlayerModel> mPlayerModelData;

    public static ArrayList<TeamModel> parseTeamModelData(String content, Context context) {
        mTeamModelData = new ArrayList<>();
        final TeamDBHandler teamDBHandler = new TeamDBHandler(context);
        try {
            //--get the JSONArray from the content
            JSONArray jsonArray = new JSONArray(content);

            teamDBHandler.deleteDB();
            //--getting all the JSONObject from the JSONArray by passing JSONArray position.
            for (int i = 0; i < jsonArray.length(); i++) {
                TeamModel teamModel = new TeamModel();
                final JSONObject jsonObject = jsonArray.getJSONObject(i);
                //--setting the TeamModel data by using setTeamViewData()
                teamModel.setTeamViewData(jsonObject);

                teamDBHandler.insertTeamDataIntoDB(jsonObject.getString(Constants.KEY_TEAM_NAME),
                        jsonObject.getString(Constants.KEY_TEAM_OWNER),
                        jsonObject.getString(Constants.KEY_TEAM_CAPTAIN),
                        jsonObject.getString(Constants.KEY_TEAM_COACH),
                        jsonObject.getString(Constants.KEY_TEAM_HOME_VENUE),
                        jsonObject.getString(Constants.FOLDER_NAME));
                //--adding the teamData inside the List
                mTeamModelData.add(teamModel);
            }
            return mTeamModelData;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<PlayerModel> parsePlayerModelContent(String content, Context context) {
        ArrayList<PlayerModel> mPlayerModelData = new ArrayList<>();
        final PlayerDBHandler mPlayerHandler = new PlayerDBHandler(context);

        //---get jsonArray from content
        try {
            JSONArray jsonArray = new JSONArray(content);
            mPlayerHandler.deletePlayerDB();

            //--getting all the JSONObject from the JSONArray by passing JSONArray position.
            for (int i = 0; i < jsonArray.length(); i++) {

                PlayerModel mPlayerModel = new PlayerModel();

                final JSONObject jsonObject = jsonArray.getJSONObject(i);

                //--setting the TeamPlayerModel data by using setTeamViewData()
                mPlayerModel.setTeamPlayerData(jsonObject);

                //Method to convert image data into byte[] and stored it into the database
                mPlayerHandler.insertPlayerDataIntoDB(jsonObject.getString(Constants.KEY_PLAYER_NAME),
                        jsonObject.getString(Constants.KEY_PLAYER_ROLE),
                        jsonObject.getString(Constants.KEY_PLAYER_BATTING_STYLE),
                        jsonObject.getString(Constants.KEY_PLAYER_BOWLING_STYLE),
                        jsonObject.getString(Constants.KEY_PLAYER_NATIONALITY),
                        jsonObject.getString(Constants.KEY_PLAYER_DOB),
                        jsonObject.getString(Constants.FOLDER_NAME));

                mPlayerModelData.add(mPlayerModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mPlayerModelData;
    }

    public static ArrayList<PlayerModel> jsonParserForPlayerDataFromDB(JSONArray jsonArray) {
        mPlayerModelData = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                PlayerModel mPlayerModel = new PlayerModel();

                mPlayerModel.setTeamPlayerData(jsonObject);
                mPlayerModelData.add(mPlayerModel);
            }
            return mPlayerModelData;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    //--Method to Retrieving ArrayList<TeamModel> by passing the jsonArray
    public static ArrayList<TeamModel> jsonParserForTeamDataFromDB(JSONArray jsonArray) {
        mTeamModelData = new ArrayList<>();
        try {
            //--getting all the JSONObject from the JSONArray by passing JSONArray position.
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                TeamModel mTeamModel = new TeamModel();
                //--setting the TeamModel data by using setTeamViewData()
                mTeamModel.setTeamViewData(jsonObject);

                //--adding the teamData inside the List
                mTeamModelData.add(mTeamModel);
            }
            return mTeamModelData;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    //converting Cursor data into Json Array
    public static JSONArray convertToJsonArray(Cursor mCursor) {
        JSONArray resultSet = new JSONArray();
        mCursor.moveToFirst();
        while (!mCursor.isAfterLast()) {
            int totalColumn = mCursor.getColumnCount();
            JSONObject rowObject = new JSONObject();
            for (int i = 0; i < totalColumn; i++) {
                if (mCursor.getColumnName(i) != null) {
                    try {
                        rowObject.put(mCursor.getColumnName(i),
                                mCursor.getString(i));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            resultSet.put(rowObject);
            mCursor.moveToNext();
        }
        mCursor.close();
        return resultSet;
    }
}
