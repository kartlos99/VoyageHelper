package diakonidze.kartlos.voiage.datebase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by k.diakonidze on 7/31/2015.
 */
public class DBhelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "VoiagerDB";
    private static final int DB_VERSION = 1;

    public DBhelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createDriverTable(db);
        createPassangerTable(db);
    }

    private void createPassangerTable(SQLiteDatabase db) {
        String query = "create table if not exists " +
                DBscheme.PASSANGER_TABLE_NAME + "(" +
                DBscheme.ID + " integer primary, " +
                DBscheme.USER_ID + " integer not null, " +
                DBscheme.PLACE_X + " text not null, " +
                DBscheme.PLACE_Y + " text, " +
                DBscheme.FREESPACE + " integer, " +
                DBscheme.PRICE + " integer, " +
                DBscheme.CONDICIONERI + " integer default -1, " +
                DBscheme.SIGAR + " integer default -1, " +
                DBscheme.SABARGULI + " integer default -1, " +
                DBscheme.CXOVELEBI + " integer default -1, " +
                DBscheme.ATHOME + " integer default -1, " +
                DBscheme.CITY_FROM + " text, " +
                DBscheme.CITY_TO + " text, " +
                DBscheme.DATE + " text, " +
                DBscheme.TIME + " text, " +
                DBscheme.COMMENT + " text, " +
                DBscheme.LOCATION + " integer, " +       //********* favritebshia tu chem gancxadebebshi
                DBscheme.NUMBER_MOBILE + " text, " +
                DBscheme.NAME + " text, " +
                DBscheme.SURMANE + " text);";
        db.execSQL(query);
    }

    private void createDriverTable(SQLiteDatabase db) {
        String query = "create table if not exists " +
                DBscheme.DRIVER_TABLE_NAME + "(" +
                DBscheme.ID + " integer primary, " +
                DBscheme.USER_ID + " integer not null, " +
                DBscheme.PLACE_X + " text, " +
                DBscheme.PLACE_Y + " text, " +
                DBscheme.FREESPACE + " integer, " +
                DBscheme.PRICE + " integer, " +
                DBscheme.CONDICIONERI + " integer default -1, " +
                DBscheme.SIGAR + " integer default -1, " +
                DBscheme.SABARGULI + " integer default -1, " +
                DBscheme.CXOVELEBI + " integer default -1, " +
                DBscheme.ATHOME + " integer default -1, " +
                DBscheme.MARKA + " integer, " +
                DBscheme.MODELI + " integer, " +
                DBscheme.COLOR + " integer, " +
                DBscheme.PHOTO_ST + " text, " +
                DBscheme.AGE_TO + " integer, " +
                DBscheme.GENDER_ST + " integer, " +
                DBscheme.CITY_FROM + " text, " +
                DBscheme.CITY_PATH + " text, " +
                DBscheme.CITY_TO + " text, " +
                DBscheme.DATE + " text, " +
                DBscheme.TIME + " text, " +
                DBscheme.COMMENT + " text, " +
                DBscheme.LOCATION + " integer, " +
                DBscheme.NUMBER_MOBILE + " text, " +
                DBscheme.NAME + " text, " +
                DBscheme.SURMANE + " text);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
