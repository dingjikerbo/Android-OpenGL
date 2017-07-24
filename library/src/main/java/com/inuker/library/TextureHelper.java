package com.inuker.library;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import static android.opengl.GLES10.GL_LINEAR_MIPMAP_LINEAR;
import static android.opengl.GLES10.GL_LINEAR_MIPMAP_NEAREST;
import static android.opengl.GLES10.GL_TEXTURE_2D;
import static android.opengl.GLES10.GL_TEXTURE_MIN_FILTER;
import static android.opengl.GLES10.glBindTexture;
import static android.opengl.GLES10.glDeleteTextures;
import static android.opengl.GLES10.glGenTextures;
import static android.opengl.GLES20.GL_LINEAR;
import static android.opengl.GLES20.GL_TEXTURE_MAG_FILTER;
import static android.opengl.GLES20.glGenerateMipmap;
import static android.opengl.GLES20.glTexParameteri;
import static android.opengl.GLUtils.texImage2D;

/**
 * Created by liwentian on 17/6/10.
 */

public class TextureHelper {

    public static int loadTexture(Context context, int resourceId) {
        int[] textObjectIds = new int[1];
        glGenTextures(1, textObjectIds, 0);

        if (textObjectIds[0] == 0) {
            return 0;
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId, options);

        if (bitmap == null) {
            glDeleteTextures(1, textObjectIds, 0);
            return 0;
        }

        glBindTexture(GL_TEXTURE_2D, textObjectIds[0]);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        texImage2D(GL_TEXTURE_2D, 0, bitmap, 0);

        glGenerateMipmap(GL_TEXTURE_2D);

        bitmap.recycle();

        glBindTexture(GL_TEXTURE_2D, 0);

        return textObjectIds[0];
    }
}
