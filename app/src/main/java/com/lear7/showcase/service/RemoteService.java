package com.lear7.showcase.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.lear7.showcase.IRemoteService;

import static android.os.Process.myPid;

public class RemoteService extends Service {
    public RemoteService() {
    }

    private final IRemoteService.Stub binder = new IRemoteService.Stub() {

        public int getPid(){
            return myPid();
        }
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean,
                               float aFloat, double aDouble, String aString) {
            // Does nothing
        }

        @Override
        public String getRemoteMessage(int type) throws RemoteException {
            return "This a Message from AndroidShowcase";
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
}
