package diakonidze.kartlos.voiage.backServises;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import diakonidze.kartlos.voiage.MainActivity;
import diakonidze.kartlos.voiage.datebase.DBmanager;
import diakonidze.kartlos.voiage.utils.Constantebi;

/**
 * Created by k.diakonidze on 8/7/2015.
 */
public class SilentDBwrite extends AsyncTask<Context, String, String> {

    public SilentDBwrite() {
    }

    @Override
    protected String doInBackground(Context... params) {
        Context context = params[0];
//        DBmanager.initialaize(MainActivity.this);
        DBmanager.openWritable();
        for (int i = 0; i < Constantebi.brendList.size(); i++) {
            DBmanager.insertToMarka(Constantebi.brendList.get(i));
        }
        for (int i = 0; i < Constantebi.modelList.size(); i++) {
            DBmanager.insertToModel(Constantebi.modelList.get(i));
        }
        for (int i = 0; i < Constantebi.cityList.size(); i++) {
            DBmanager.insertCity(Constantebi.cityList.get(i));
        }
        DBmanager.close();
        return "OK";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
//        Toast.makeText(t, "morcha chawera", Toast.LENGTH_LONG).show();
    }
}
