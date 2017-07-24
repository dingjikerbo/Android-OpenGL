package com.inuker.chapter2;

import android.content.Context;
import android.opengl.GLSurfaceView;

import com.inuker.library.ShaderHelper;
import com.inuker.library.TextResourceReader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_LINES;
import static android.opengl.GLES20.GL_POINTS;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glLineWidth;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;
import static android.opengl.GLES20.glViewport;

/**
 * Created by liwentian on 17/7/24.
 */

public class MyRender implements GLSurfaceView.Renderer {

    private static final int POSITION_COMPONENT_COUNT = 2;

    private static final int BYTES_PER_FLOAT = 4;

    private Context mContext;

    private final FloatBuffer vertexData;

    private static final float[] tableVerticesWithTriangles = {
            -0.5f, -0.5f,
            0.5f, 0.5f,
            -0.5f, 0.5f,

            -0.5f, -0.5f,
            0.5f, -0.5f,
            0.5f, 0.5f,

            -0.5f, 0f,
            0.5f, 0f,

            0f, 0.25f,
            0f, -0.25f,
    };

    private int aPositionLocation;

    private int uColorLocation;

    public MyRender(Context context) {
        mContext = context;

        vertexData = ByteBuffer.allocateDirect(tableVerticesWithTriangles.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        vertexData.put(tableVerticesWithTriangles);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        String vertexShaderSrc = TextResourceReader.readTextFileFromResource(mContext, R.raw.vertex);
        int vertexShader = ShaderHelper.compileVertexShader(vertexShaderSrc);

        String fragmentShaderSrc = TextResourceReader.readTextFileFromResource(mContext, R.raw.fragment);
        int fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSrc);

        int program = ShaderHelper.linkProgram(vertexShader, fragmentShader);

        glUseProgram(program);

        aPositionLocation = glGetAttribLocation(program, "a_Position");
        uColorLocation = glGetUniformLocation(program, "u_Color");

        vertexData.position(0);

        glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT, false, 0, vertexData);
        glEnableVertexAttribArray(aPositionLocation);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        glClear(GL_COLOR_BUFFER_BIT);

        glClearColor(0f, 0f, 0f, 1f);

        glUniform4f(uColorLocation, 1f, 1f, 1f, 1f);
        glDrawArrays(GL_TRIANGLES, 0, 6);

        glUniform4f(uColorLocation, 0f, 0f, 0f, 0f);
        glLineWidth(5);
        glDrawArrays(GL_LINES, 6, 2);

        glUniform4f(uColorLocation, 0f, 0f, 0f, 0f);
        glDrawArrays(GL_POINTS, 8, 2);
    }
}
