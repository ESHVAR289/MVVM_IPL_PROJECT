package einfoplanet.com.ipl.loacaldatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;

import einfoplanet.com.ipl.utility.JSONParser;

/**
 *
 */

public class PlayerDBHandler extends SQLiteOpenHelper {
    private JSONArray jsonArray;
    private Cursor mCursor;
    private SQLiteDatabase db;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "IPL_Database";
    private static final String DB_PLAYER_TABLE = "team_player_table";

    //--Table column names for Player table
    private static final String COLUMN_PLAYER_ID = "_id";
    private static final String COLUMN_PLAYER_NAME = "player_name";
    private static final String COLUMN_PLAYER_BATTING_STYLE = "player_batting_style";
    private static final String COLUMN_PLAYER_NATIONALITY = "player_nationality";
    private static final String COLUMN_PLAYER_BOWLING_STYLE = "player_bowling_style";
    private static final String COLUMN_PLAYER_DOB = "player_dob";
    private static final String COLUMN_PLAYER_ROLE = "player_role";
    private static final String COLUMN_FOLDER_NAME = "destination_folder_name";

    private static final String DB_TAG = "DBPlayerHandler";

    //--player table creation QUERY
    private static final String CREATE_TABLE_PLAYER_VIEW =
            "create table " + DB_PLAYER_TABLE +
                    "(" + COLUMN_PLAYER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                    + COLUMN_PLAYER_NAME + " VARCHAR(45) NOT NULL ,"
                    + COLUMN_PLAYER_ROLE + " VARCHAR(45) NOT NULL ,"
                    + COLUMN_PLAYER_BATTING_STYLE + " VARCHAR(15) NOT NULL ,"
                    + COLUMN_PLAYER_BOWLING_STYLE + " VARCHAR(15) NOT NULL ,"
                    + COLUMN_PLAYER_NATIONALITY + " VARCHAR(10) NOT NULL ,"
                    + COLUMN_PLAYER_DOB + " VARCHAR(15) NOT NULL ,"
                    + COLUMN_FOLDER_NAME + " VARCHAR(30) NOT NULL" +
                    ");";

    //--constructor to create table
    public PlayerDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE_PLAYER_VIEW);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DB_TAG, "Upgrading database from version" + oldVersion + " to " + newVersion +
                " which will destroy all the old data");
        db.execSQL("DROP TABLE IF EXISTS " + DB_PLAYER_TABLE);
        onCreate(db);
    }

    //--Open the database
    public PlayerDBHandler open() throws SQLException {
        db = getWritableDatabase();
        return this;
    }

    public void deletePlayerDB() {
        onUpgrade(db, DATABASE_VERSION, DATABASE_VERSION);
    }

    //--Team data insert into database
    public boolean insertPlayerDataIntoDB(String player_name, String player_role,
                                          String player_batting_style, String player_bowling_style,
                                          String player_nationality, String player_dob,
                                          String destination_folder_name) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PLAYER_NAME, player_name);
        contentValues.put(COLUMN_PLAYER_ROLE, player_role);
        contentValues.put(COLUMN_PLAYER_BATTING_STYLE, player_batting_style);
        contentValues.put(COLUMN_PLAYER_BOWLING_STYLE, player_bowling_style);
        contentValues.put(COLUMN_PLAYER_NATIONALITY, player_nationality);
        contentValues.put(COLUMN_PLAYER_DOB, player_dob);
        contentValues.put(COLUMN_FOLDER_NAME, destination_folder_name);

        long result = db.insert(DB_PLAYER_TABLE, null, contentValues);
        return result != -1;
    }

    //--Retrieve a team information
    private Cursor getPlayerData() {
        mCursor = db.query(DB_PLAYER_TABLE,
                new String[]{COLUMN_PLAYER_ID, COLUMN_PLAYER_NAME, COLUMN_PLAYER_ROLE,
                        COLUMN_PLAYER_BATTING_STYLE, COLUMN_PLAYER_BOWLING_STYLE, COLUMN_PLAYER_NATIONALITY,
                        COLUMN_PLAYER_DOB, COLUMN_FOLDER_NAME},
                null, null, null, null, null);

        return mCursor;
    }

    //--Retrieve a particular team information
    public Cursor getParticularTeamData(long teamId) throws SQLException {
        mCursor = db.query(true, DB_PLAYER_TABLE,
                new String[]{COLUMN_PLAYER_ID, COLUMN_PLAYER_NAME, COLUMN_PLAYER_ROLE,
                        COLUMN_PLAYER_BATTING_STYLE, COLUMN_PLAYER_BOWLING_STYLE, COLUMN_PLAYER_NATIONALITY,
                        COLUMN_PLAYER_DOB, COLUMN_FOLDER_NAME},
                COLUMN_PLAYER_ID + "=" + teamId, null, null, null, null, null
        );
        if (mCursor != null)
            mCursor.moveToFirst();
        return mCursor;
    }

    public boolean dataCheckFromDB() {
        try {
            mCursor = getPlayerData();
        } catch (Exception e) {
            return false;
        }
        return mCursor.getCount() > 0;
    }

    //collecting playerInfo from database
    public JSONArray getPlayerInfoDatabase() {
        open();
        mCursor = getPlayerData();
        jsonArray = JSONParser.convertToJsonArray(mCursor);

        return jsonArray;
    }

}


