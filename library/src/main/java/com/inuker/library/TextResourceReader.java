package com.inuker.library;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by liwentian on 17/5/30.
 */

public class TextResourceReader {

    public static String readTextFileFromResource(Context context, int resourceId) {
        StringBuilder sb = new StringBuilder();

        try {
            InputStream input = context.getResources().openRawResource(resourceId);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
//                sb.append('\n');
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.v("bush", String.format("read: %s", sb.toString()));
        return sb.toString();
    }
}
