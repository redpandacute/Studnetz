package hirtz.florian.matura.ksa.studnetz.interfaces;

import android.app.Activity;

/**
 * Created by ingli on 16.08.2018.
 */

public interface LoaderFragment {
    public boolean getReadyState();
    public void setReadyState(boolean readyState);
    public Activity getActivity();
}
