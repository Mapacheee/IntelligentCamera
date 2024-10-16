package com.intelligentcamera.camera;

import com.intelligentcamera.recognition.FaceDetector;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Camera extends JFrame {

    private JLabel cameraScreen;
    private JButton captureButton;
    private VideoCapture videoCapture;
    private Mat image;
    private boolean isClicked = false;

    public Camera() {

        setLayout(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Intento de cámara inteligente :v");
        setSize(630, 580);

        cameraScreen = new JLabel();
        cameraScreen.setBounds(0, 0, 640, 480);
        add(cameraScreen);

        captureButton = new JButton("capturar");
        captureButton.setBounds(300, 480, 80, 40);
        add(captureButton);

        captureButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                isClicked = true;

            }
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {

                super.windowClosing(e);
                videoCapture.release();
                image.release();
                System.exit(0);

            }
        });

    }

    public void startCamera() {

        videoCapture = new VideoCapture(0);
        image = new Mat();
        byte[] imageData;

        ImageIcon icon;
        while (true) {

            if (videoCapture.read(image)) {

                FaceDetector.detectFaces(image);

                final MatOfByte buffer = new MatOfByte();
                Imgcodecs.imencode(".jpg", image, buffer);

                imageData = buffer.toArray();

                icon = new ImageIcon(imageData);
                cameraScreen.setIcon(icon);

                if (isClicked) {
                    String nameFile = JOptionPane.showInputDialog("ingresa el nombre de la imagen.");

                    if (nameFile == null || nameFile.trim().isEmpty()) {
                        nameFile = "poto";
                    }

                    Imgcodecs.imwrite("imagenes/" + nameFile + ".jpg", image);
                    isClicked = false;
                }
            }
            else {
                System.out.println("No se pudo capturar la imagen");
            }
        }
    }
}
