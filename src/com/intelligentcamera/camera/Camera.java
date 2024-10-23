package com.intelligentcamera.camera;

import com.intelligentcamera.recognition.FaceDetector;
import com.intelligentcamera.recognition.MovementDetector;
import com.intelligentcamera.recognition.PoseDetector;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Camera extends JFrame {

    private JLabel cameraScreen;
    private VideoCapture videoCapture;
    private Mat image;
    private MovementDetector movementDetector;
    private PoseDetector poseDetector;

    public Camera() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Cámara inteligente con detección de movimiento y esqueleto");
        setSize(650, 520);

        cameraScreen = new JLabel();
        cameraScreen.setBounds(0, 0, 1280, 720);
        add(cameraScreen);

        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {

                super.windowClosing(e);
                videoCapture.release();
                image.release();
                System.exit(0);
            }
        });

        movementDetector = new MovementDetector();

    }

    public void startCamera() {

        videoCapture = new VideoCapture(0);
        image = new Mat();
        byte[] imageData;

        ImageIcon icon;
        while (true) {

            if (videoCapture.read(image)) {

                movementDetector.detectMovement(image);
                //poseDetector.detectAndDrawSkeleton(image);
                FaceDetector.detectFaces(image);

                final MatOfByte buffer = new MatOfByte();
                Imgcodecs.imencode(".jpg", image, buffer);

                imageData = buffer.toArray();
                icon = new ImageIcon(imageData);
                cameraScreen.setIcon(icon);

            }
        }
    }
}