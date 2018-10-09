package hirtz.florian.matura.ksa.studnetz.util;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Florian Hirtz on 22.08.2018.
 */

public class MyReader {

    private Context mContext;

    public MyReader(Context mContext) {
        this.mContext = mContext;
    }


    /***************************************************************************************
     *    Title: Reading from a Text file in Android Studio Java
     *    Author: AndyRoid
     *    Year: 2015
     *    Availability: https://stackoverflow.com/a/30417883
     *
     ***************************************************************************************/
    public List<String> read(String path) {
        List<String> mLines = new ArrayList<>();

        AssetManager am = mContext.getAssets();

        try {
            InputStream is = am.open(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;

            while ((line = reader.readLine()) != null)
                mLines.add(line);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mLines;
    }
}
