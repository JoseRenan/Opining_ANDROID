package br.com.opining.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class FirebaseInstanceService extends Service {
    public FirebaseInstanceService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
