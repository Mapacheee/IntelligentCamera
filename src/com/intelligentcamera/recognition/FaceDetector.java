package com.intelligentcamera.recognition;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;

public class FaceDetector {

    public static void detectFaces(Mat image) {

        MatOfRect faces = new MatOfRect();
        Mat hierarchy = new Mat();

        Imgproc.cvtColor(image, hierarchy, Imgproc.COLOR_BGR2GRAY);
        Imgproc.equalizeHist(hierarchy, faces);

        int height = hierarchy.height();
        int faceSize = 0;

        if (Math.round(height * 0.2f) > 0) {

            faceSize = Math.round(height * 0.2f);

        }

        CascadeClassifier faceCascade = new CascadeClassifier();

        try {

            faceCascade.load("src/com/intelligentcamera/resources/haarcascade_frontalface_alt2.xml");

        }
        catch (Exception e) {

            System.out.println("Error al cargar el pre entreno :,v");
            e.printStackTrace();

        }

        faceCascade.detectMultiScale(hierarchy, faces, 1.1, 2
                                    ,0| Objdetect.CASCADE_SCALE_IMAGE,
                                    new Size(faceSize, faceSize), new Size());

        Rect[] faceArray = faces.toArray();

        for (int i = 0; i < faceArray.length; i++) {

            Imgproc.rectangle(image, faceArray[i].tl(), faceArray[i].br(), new Scalar(255, 0, 0));

        }
    }
}
