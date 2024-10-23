package com.intelligentcamera.recognition;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class MovementDetector {

    private Mat previousFrame;
    private Mat diffFrame;
    private Mat grayFrame;

    public MovementDetector() {

        previousFrame = new Mat();
        diffFrame = new Mat();
        grayFrame = new Mat();



    }

    public void detectMovement(Mat currentFrame) {

        Imgproc.cvtColor(currentFrame, grayFrame, Imgproc.COLOR_BGR2GRAY);
        Imgproc.GaussianBlur(grayFrame, grayFrame, new Size(21, 21), 0);

        if (previousFrame.empty()) {

            grayFrame.copyTo(previousFrame);
            return;

        }

        Core.absdiff(previousFrame, grayFrame, diffFrame);
        Imgproc.threshold(diffFrame, diffFrame, 25, 255, Imgproc.THRESH_BINARY);

        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(diffFrame, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        Scalar color = new Scalar(0, 255, 0);

        for (MatOfPoint contour : contours) {

            Rect boundingRect = Imgproc.boundingRect(contour);
            Imgproc.rectangle(currentFrame, boundingRect.tl(), boundingRect.br(), color, 2);

        }

        grayFrame.copyTo(previousFrame);
    }
}