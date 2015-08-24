package diakonidze.kartlos.voiage.datebase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by k.diakonidze on 7/31/2015.
 */
public class DBhelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "VoiagerDB1";
    private static final int DB_VERSION = 10;

    public DBhelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createDriverTable(db);
        createPassangerTable(db);
        createMarkaTable(db);
        createModelTable(db);
        createCityTable(db);
    }

    private void createCityTable(SQLiteDatabase db) {
        String query = "create table if not exists " +
                DBscheme.CITY_TABLE_NAME + "(" +
                DBscheme.ID + " integer primary key autoincrement, " +
                DBscheme.CITY_ID + " integer not null, " +
                DBscheme.CITY_PHOTO + " text, " +
                DBscheme.NAME_EN + " text, " +
                DBscheme.NAME + " text);";
        db.execSQL(query);
    }

    private void createModelTable(SQLiteDatabase db) {
        String query = "create table if not exists " +
                DBscheme.MODEL_TABLE_NAME + "(" +
                DBscheme.ID + " integer primary key autoincrement, " +
                DBscheme.MARKA_ID + " integer not null, " +
                DBscheme.MODEL_ID + " integer not null, " +
                DBscheme.NAME + " text);";
        db.execSQL(query);
    }

    private void createMarkaTable(SQLiteDatabase db) {
        String query = "create table if not exists " +
                DBscheme.MARKA_TABLE_NAME + "(" +
                DBscheme.ID + " integer primary key autoincrement, " +
                DBscheme.MARKA_ID + " integer not null, " +
                DBscheme.NAME + " text);";
        db.execSQL(query);
    }

    private void createPassangerTable(SQLiteDatabase db) {
        String query = "create table if not exists " +
                DBscheme.PASSANGER_TABLE_NAME + "(" +
                DBscheme.ID + " integer primary key autoincrement, " +
                DBscheme.USER_ID + " integer not null, " +
                DBscheme.S_ID + " integer not null, " +
                DBscheme.PLACE_X + " text not null, " +
                DBscheme.PLACE_Y + " text, " +
                DBscheme.FREESPACE + " integer, " +
                DBscheme.PRICE + " integer, " +
                DBscheme.CONDICIONERI + " integer default 2, " +
                DBscheme.SIGAR + " integer default 2, " +
                DBscheme.SABARGULI + " integer default 2, " +
                DBscheme.CXOVELEBI + " integer default 2, " +
                DBscheme.ATHOME + " integer default 2, " +
                DBscheme.CITY_FROM + " text, " +
                DBscheme.CITY_TO + " text, " +
                DBscheme.DATE + " text, " +
                DBscheme.TIME + " text, " +
                DBscheme.COMMENT + " text, " +
                DBscheme.MANU_LOCATION + " integer, " +       //********* favritebshia tu chem gancxadebebshi
                DBscheme.NUMBER_MOBILE + " text, " +
                DBscheme.NAME + " text, " +
                DBscheme.SURMANE + " text);";
        db.execSQL(query);
    }

    private void createDriverTable(SQLiteDatabase db) {
        String query = "create table if not exists " +
                DBscheme.DRIVER_TABLE_NAME + "(" +
                DBscheme.ID + " integer primary key autoincrement, " +
                DBscheme.USER_ID + " integer not null, " +
                DBscheme.S_ID + " integer not null, " +
                DBscheme.PLACE_X + " text, " +
                DBscheme.PLACE_Y + " text, " +
                DBscheme.FREESPACE + " integer, " +
                DBscheme.PRICE + " integer, " +
                DBscheme.CONDICIONERI + " integer default 2, " +
                DBscheme.SIGAR + " integer default 2, " +
                DBscheme.SABARGULI + " integer default 2, " +
                DBscheme.CXOVELEBI + " integer default 2, " +
                DBscheme.ATHOME + " integer default 2, " +
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
                DBscheme.MANU_LOCATION + " integer, " +
                DBscheme.NUMBER_MOBILE + " text, " +
                DBscheme.NAME + " text, " +
                DBscheme.SURMANE + " text);";
        db.execSQL(query);
    }

    private void createUserTable(SQLiteDatabase db) {
        String query = "create table if not exists " +
                DBscheme.USER_TABLE_NAME + "(" +
                DBscheme.ID + " integer primary key autoincrement, " +
                DBscheme.MARKA_ID + " integer not null, " +
                DBscheme.MODEL_ID + " integer not null, " +
                DBscheme.NAME + " text);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        createMarkaTable(db);
//        createModelTable(db);
//        createCityTable(db);
int i =1;
    }
}
