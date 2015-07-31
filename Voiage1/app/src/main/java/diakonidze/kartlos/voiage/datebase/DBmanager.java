package diakonidze.kartlos.voiage.datebase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import diakonidze.kartlos.voiage.models.DriverStatement;
import diakonidze.kartlos.voiage.models.PassangerStatement;

/**
 * Created by k.diakonidze on 7/31/2015.
 */
public class DBmanager {

    private static DBhelper dbhelper;
    private static SQLiteDatabase db;

    public static void initialaize(Context context){
        if(dbhelper == null){
            dbhelper = new DBhelper(context);
        }
    }

    public static void openWritable(){
        db = dbhelper.getWritableDatabase();
    }

    public static void openReadable(){
        db = dbhelper.getReadableDatabase();
    }

    public static void close(){
        db.close();
    }

    public static long insertIntoDriver(DriverStatement driverStatement, int location){
        ContentValues values = new ContentValues();

        values.put(DBscheme.ID, driverStatement.getId());
        values.put(DBscheme.USER_ID, driverStatement.getUserID());
        values.put(DBscheme.PLACE_X, driverStatement.getPlaceX());
        values.put(DBscheme.PLACE_Y, driverStatement.getPlaceY());
        values.put(DBscheme.FREESPACE, driverStatement.getFreeSpace());
        values.put(DBscheme.PRICE, driverStatement.getPrice());
        values.put(DBscheme.CONDICIONERI, driverStatement.getKondencioneri());
        values.put(DBscheme.SIGAR, driverStatement.getSigareti());
        values.put(DBscheme.SABARGULI, driverStatement.getSabarguli());
        values.put(DBscheme.CXOVELEBI, driverStatement.getCxovelebi());
        values.put(DBscheme.ATHOME, driverStatement.getAtHome());
        values.put(DBscheme.MARKA, driverStatement.getMarka());
        values.put(DBscheme.MODELI, driverStatement.getModeli());
        values.put(DBscheme.COLOR, driverStatement.getColor());
        values.put(DBscheme.PHOTO_ST, driverStatement.getCarpicture());
        values.put(DBscheme.AGE_TO, driverStatement.getAgeTo());
        values.put(DBscheme.GENDER_ST, driverStatement.getGender());
        values.put(DBscheme.CITY_FROM, driverStatement.getCityFrom());
        values.put(DBscheme.CITY_PATH, driverStatement.getCityPath());
        values.put(DBscheme.CITY_TO, driverStatement.getCityTo());
        values.put(DBscheme.DATE, driverStatement.getDate());
        values.put(DBscheme.TIME, driverStatement.getTime());
        values.put(DBscheme.COMMENT, driverStatement.getComment());
        values.put(DBscheme.LOCATION, location);                            //********* favritebshia tu chem gancxadebebshi
        values.put(DBscheme.NUMBER_MOBILE, driverStatement.getNumber());
        values.put(DBscheme.NAME, driverStatement.getName());
        values.put(DBscheme.SURMANE, driverStatement.getSurname());

        return db.insert(DBscheme.DRIVER_TABLE_NAME, null, values);
    }

    public static long insertIntoPassanger(PassangerStatement passangerStatement, int location){
        ContentValues values = new ContentValues();

        values.put(DBscheme.ID, passangerStatement.getId());
        values.put(DBscheme.USER_ID, passangerStatement.getUserID());
        values.put(DBscheme.PLACE_X, passangerStatement.getPlaceX());
        values.put(DBscheme.PLACE_Y, passangerStatement.getPlaceY());
        values.put(DBscheme.FREESPACE, passangerStatement.getFreeSpace());
        values.put(DBscheme.PRICE, passangerStatement.getPrice());
        values.put(DBscheme.CONDICIONERI, passangerStatement.getKondencioneri());
        values.put(DBscheme.SIGAR, passangerStatement.getSigareti());
        values.put(DBscheme.SABARGULI, passangerStatement.getSabarguli());
        values.put(DBscheme.CXOVELEBI, passangerStatement.getCxovelebi());
        values.put(DBscheme.ATHOME, passangerStatement.getAtHome());
        values.put(DBscheme.CITY_FROM, passangerStatement.getCityFrom());
        values.put(DBscheme.CITY_TO, passangerStatement.getCityTo());
        values.put(DBscheme.DATE, passangerStatement.getDate());
        values.put(DBscheme.TIME, passangerStatement.getTime());
        values.put(DBscheme.COMMENT, passangerStatement.getComment());
        values.put(DBscheme.LOCATION, location);                            //********* favritebshia tu chem gancxadebebshi
        values.put(DBscheme.NUMBER_MOBILE, passangerStatement.getNumber());
        values.put(DBscheme.NAME, passangerStatement.getName());
        values.put(DBscheme.SURMANE, passangerStatement.getSurname());

        return db.insert(DBscheme.PASSANGER_TABLE_NAME, null, values);
    }

    public static ArrayList<DriverStatement> getDriverList(int location){
        ArrayList<DriverStatement> statementsToReturn = new ArrayList<>();
        Cursor cursor = db.query(DBscheme.DRIVER_TABLE_NAME, null, DBscheme.LOCATION +" = "+ location, null, null, null, null);
        if(cursor.moveToFirst()){
            do {
                int user_id = cursor.getInt(cursor.getColumnIndex(DBscheme.USER_ID));
                int freeSpace = cursor.getInt(cursor.getColumnIndex(DBscheme.FREESPACE));
                int price = cursor.getInt(cursor.getColumnIndex(DBscheme.PRICE));
                String date = cursor.getString(cursor.getColumnIndex(DBscheme.DATE));
                String cityfrom = cursor.getString(cursor.getColumnIndex(DBscheme.CITY_FROM));
                String cityto = cursor.getString(cursor.getColumnIndex(DBscheme.CITY_TO));

                DriverStatement driverStatement = new DriverStatement(user_id, freeSpace, price, date, cityfrom, cityto);

                driverStatement.setId(cursor.getInt(cursor.getColumnIndex(DBscheme.ID)));
                driverStatement.setPlaceX(cursor.getInt(cursor.getColumnIndex(DBscheme.PLACE_X)));
                driverStatement.setPlaceY(cursor.getInt(cursor.getColumnIndex(DBscheme.PLACE_Y)));
                driverStatement.setKondencioneri(cursor.getInt(cursor.getColumnIndex(DBscheme.CONDICIONERI)));
                driverStatement.setSigareti(cursor.getInt(cursor.getColumnIndex(DBscheme.SIGAR)));
                driverStatement.setSabarguli(cursor.getInt(cursor.getColumnIndex(DBscheme.SABARGULI)));
                driverStatement.setCxovelebi(cursor.getInt(cursor.getColumnIndex(DBscheme.CXOVELEBI)));
                driverStatement.setAtHome(cursor.getInt(cursor.getColumnIndex(DBscheme.ATHOME)));
                driverStatement.setMarka(cursor.getInt(cursor.getColumnIndex(DBscheme.MARKA)));
                driverStatement.setModeli(cursor.getInt(cursor.getColumnIndex(DBscheme.MODELI)));
                driverStatement.setColor(cursor.getInt(cursor.getColumnIndex(DBscheme.COLOR)));
                driverStatement.setCarpicture(cursor.getString(cursor.getColumnIndex(DBscheme.PHOTO_ST)));
                driverStatement.setAgeTo(cursor.getInt(cursor.getColumnIndex(DBscheme.AGE_TO)));
                driverStatement.setGender(cursor.getInt(cursor.getColumnIndex(DBscheme.GENDER_ST)));
                driverStatement.setCityFrom(cursor.getString(cursor.getColumnIndex(DBscheme.CITY_FROM)));
                driverStatement.setCityPath(cursor.getString(cursor.getColumnIndex(DBscheme.CITY_PATH)));
                driverStatement.setCityTo(cursor.getString(cursor.getColumnIndex(DBscheme.CITY_TO)));
                driverStatement.setDate(cursor.getString(cursor.getColumnIndex(DBscheme.DATE)));
                driverStatement.setTime(cursor.getString(cursor.getColumnIndex(DBscheme.TIME)));
                driverStatement.setComment(cursor.getString(cursor.getColumnIndex(DBscheme.COMMENT)));

                driverStatement.setName(cursor.getString(cursor.getColumnIndex(DBscheme.NAME)));
                driverStatement.setSurname(cursor.getString(cursor.getColumnIndex(DBscheme.SURMANE)));
                driverStatement.setNumber(cursor.getString(cursor.getColumnIndex(DBscheme.NUMBER_MOBILE)));


                statementsToReturn.add(driverStatement);
            } while(cursor.moveToNext());
        }

        return statementsToReturn;
    }

}
