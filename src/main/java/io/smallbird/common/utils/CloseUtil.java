package io.smallbird.common.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * <p>@Des:  </p>
 * <p>@Author: xupj </p>
 * <p>@Date: 2019/3/25 15:34 </p>
 **/
public class CloseUtil {

    public static void close(Closeable... closeables){
        for (Closeable closeable : closeables){
            if (closeable != null){
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
