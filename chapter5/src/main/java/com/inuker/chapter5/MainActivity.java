package com.inuker.chapter5;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.inuker.library.SurfaceActivity;

public class MainActivity extends SurfaceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public GLSurfaceView.Renderer getRender(Context context) {
        return new MyRender(this);
    }
}
