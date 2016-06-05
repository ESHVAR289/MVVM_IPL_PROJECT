package einfoplanet.com.ipl.loacaldatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 *  TeamDBHandler class is used to Handle the database operation of such as inserting the
 *  values into the database retrieving the particular data of the team from the database
 *  Also complete set of information from the database.
 */
public class TeamDBHandler extends SQLiteOpenHelper {
    private Cursor mCursor;
    private SQLiteDatabase db;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "IPL_Database";
    private static final String DB_TABLE_NAME = "team_view_table";

    //--Table column names
    private static final String COLUMN_TEAM_ID = "_id";
    private static final String COLUMN_TEAM_NAME = "team_name";
    private static final String COLUMN_TEAM_OWNER = "team_owner";
    private static final String COLUMN_TEAM_CAPTAIN = "team_captain";
    private static final String COLUMN_TEAM_COACH = "team_coach";
    private static final String COLUMN_TEAM_HOME_VENUE = "team_home_venue";
    private static final String COLUMN_FOLDER_NAME = "destination_folder_name";

    private static final String DB_TAG = "DBHandler";

    //--Query for table creation
    private static final String CREATE_TABLE_TEAM_VIEW =
            "create table " + DB_TABLE_NAME +
                    "(" + COLUMN_TEAM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                    + COLUMN_TEAM_NAME + " VARCHAR(45) NOT NULL ,"
                    + COLUMN_TEAM_OWNER + " VARCHAR(45) NOT NULL ,"
                    + COLUMN_TEAM_CAPTAIN + " VARCHAR(15) NOT NULL ,"
                    + COLUMN_TEAM_COACH + " VARCHAR(15) NOT NULL ,"
                    + COLUMN_TEAM_HOME_VENUE + " VARCHAR(45) NOT NULL ,"
                    + COLUMN_FOLDER_NAME + " VARCHAR(20) NOT NULL "
                    + ");";

    //--Constructor to create table
    public TeamDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE_TEAM_VIEW);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DB_TAG, "Upgrading database from version" + oldVersion + " to " + newVersion +
                " which will destroy all the old data");
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_NAME);
        onCreate(db);
    }

    //--Open the database
    public TeamDBHandler open() throws SQLException {
        db = getWritableDatabase();
        return this;
    }

    //--Team data insert into database
    public boolean insertTeamDataIntoDB(String team_name, String team_owner,
                                        String team_captain, String team_coach,
                                        String team_home_venue, String folder_name) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TEAM_NAME, team_name);
        contentValues.put(COLUMN_TEAM_OWNER, team_owner);
        contentValues.put(COLUMN_TEAM_CAPTAIN, team_captain);
        contentValues.put(COLUMN_TEAM_COACH, team_coach);
        contentValues.put(COLUMN_TEAM_HOME_VENUE, team_home_venue);
        contentValues.put(COLUMN_FOLDER_NAME, folder_name);


        long result = db.insert(DB_TABLE_NAME, null, contentValues);
        return result != -1;
    }

    //--Retrieve a team information
    private Cursor getTeamData() {
        mCursor = db.query(DB_TABLE_NAME,
                new String[]{COLUMN_TEAM_ID, COLUMN_TEAM_NAME, COLUMN_TEAM_OWNER,
                        COLUMN_TEAM_CAPTAIN, COLUMN_TEAM_COACH, COLUMN_TEAM_HOME_VENUE,
                        COLUMN_FOLDER_NAME},
                null, null, null, null, null);

        return mCursor;
    }

    //--deleting all the information from the database
    public void deleteDB() {
        onUpgrade(db, DATABASE_VERSION, DATABASE_VERSION);
    }

    //--Retrieve a particular team information
    public Cursor getParticularTeamData(long teamId) throws SQLException {
        mCursor = db.query(true, DB_TABLE_NAME,
                new String[]{COLUMN_TEAM_ID, COLUMN_TEAM_NAME, COLUMN_TEAM_OWNER,
                        COLUMN_TEAM_CAPTAIN, COLUMN_TEAM_COACH, COLUMN_TEAM_HOME_VENUE,
                        COLUMN_FOLDER_NAME},
                COLUMN_TEAM_ID + "=" + teamId, null, null, null, null, null
        );
        if (mCursor != null)
            mCursor.moveToFirst();
        return mCursor;
    }

    public boolean dataCheckFromDB() {
        try {
            mCursor = getTeamData();
        } catch (Exception e) {
            return false;
        }
        if (mCursor.getCount() > 0) {
            mCursor.close();
            return true;
        } else {
            mCursor.close();
            return false;
        }
    }

    //collecting teamInfo from database
    public JSONArray getTeamInfoDatabase() {
        open();
        Cursor teamInfoData = getTeamData();
        JSONArray jsonArray = convertToJsonArray(teamInfoData);
        teamInfoData.close();
        return jsonArray;
    }

    //converting Cursor data into Json Array
    private JSONArray convertToJsonArray(Cursor cursor) {
        JSONArray resultSet = new JSONArray();
        cursor.moveToFirst();
        while (!mCursor.isAfterLast()) {
            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();
            for (int i = 0; i < totalColumn; i++) {
                if (cursor.getColumnName(i) != null) {
                    try {
                        rowObject.put(cursor.getColumnName(i),
                                cursor.getString(i));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            resultSet.put(rowObject);
            cursor.moveToNext();
        }
        cursor.close();
        return resultSet;
    }

}
