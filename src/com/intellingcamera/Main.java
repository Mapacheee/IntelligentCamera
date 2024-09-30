package com.intellingcamera;

import com.intellingcamera.camera.Camera;
import org.opencv.core.Core;

import java.awt.*;

class Main {
    public static void main(String[] args) {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        EventQueue.invokeLater(new Runnable() {

            public void run() {

                Camera camera = new Camera();
                camera.setVisible(true);

                new Thread(new Runnable() {

                    public void run() {

                        camera.startCamera();

                    }
                }).start();
            }
        });
    }
}
