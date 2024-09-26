package com.intellingcamera.camera;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Camera extends JFrame {

    private JLabel cameraScreen;
    private JButton captureButton;
    private VideoCapture videoCapture;
    private Mat image;
    private boolean isClicked = false;

    public Camera() {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        setLayout(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Intento de camara inteligente :v");
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

    }

    public void startCamera() {

        videoCapture = new VideoCapture(0); // Asegúrate de que la cámara se inicialice correctamente
        image = new Mat();
        byte[] imageData;

        ImageIcon icon;
        while (true) {
            if (videoCapture.read(image)) {

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
