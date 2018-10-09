package hirtz.florian.matura.ksa.studnetz.interfaces;

import com.android.volley.Response;

/**
 * Created by ingli on 16.08.2018.
 */

public interface ProfilePicturesOnResponseListener extends Response.Listener<String> {
    public void setRange(int start, int amount);
}
