package com.a102590007.ntut.hw8;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.File;
import java.io.IOException;

/**
 * Created by Junkai on 2018/5/24.
 */

public class MediaPlayService extends Service {

    private MediaPlayer player = new MediaPlayer();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        player.stop();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Uri uriFile = Uri.fromFile(new File(Environment.getExternalStorageDirectory().getPath() + "/song.mp3"));
        player = MediaPlayer.create(this, uriFile);
        player.start();
        return super.onStartCommand(intent, flags, startId);
    }
}
