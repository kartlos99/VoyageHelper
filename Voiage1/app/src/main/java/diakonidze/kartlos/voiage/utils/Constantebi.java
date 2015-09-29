package diakonidze.kartlos.voiage.utils;

import com.facebook.AccessToken;
import com.facebook.Profile;

import java.util.ArrayList;
import java.util.List;

import diakonidze.kartlos.voiage.models.CarBrend;
import diakonidze.kartlos.voiage.models.CarModel;
import diakonidze.kartlos.voiage.models.Cities;

/**
 * Created by kartlos on 7/28/2015.
 */
public class Constantebi {

    public static String MY_ID ;
    public static String MY_NAME = "";
    public static String MY_MOBILE = "";

    public final static String MY_OWN_STAT = "chemi";
    public final static String ALL_STAT = "yvela";
    public final static String FAVORIT_STAT = "rcheuli";
    public final static String STAT_TYPE_DRIVER = "driver";
    public final static String STAT_TYPE_PASSANGER = "passanger";
    public final static String REASON_ADD = "add";
    public final static String REASON_EDIT = "edit";
    public final static int MY_STATEMENT = 1;
    public final static int FAV_STATEMENT = 2;
    public final static int MAN_WIDTH = 32;
    public final static int MAN_HIEGHT = 52;


    public static ArrayList<CarBrend> brendList = new ArrayList<>();
    public static ArrayList<CarModel> modelList = new ArrayList<>();
    public static ArrayList<Cities> cityList = new ArrayList<>();

    public static List<Long> FAV_STAT_DRIVER = new ArrayList<>();
    public static List<Long> FAV_STAT_PASSANGER = new ArrayList<>();

    public static AccessToken accessToken;
    public static Profile profile;

}
