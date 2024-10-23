package com.intelligentcamera.recognition;

import org.opencv.core.*;
import org.opencv.dnn.Dnn;
import org.opencv.dnn.Net;
import org.opencv.imgproc.Imgproc;

public class PoseDetector {

    private Net poseNet;
    private int inWidth = 368;
    private int inHeight = 368;
    private double threshold = 0.1;


    private int[][] pairs = {

            {1, 2}, {1, 5}, {2, 3}, {3, 4}, {5, 6}, {6, 7},
            {1, 8}, {8, 9}, {9, 10}, {1, 11}, {11, 12}, {12, 13},
            {1, 0}, {0, 14}, {14, 16}, {0, 15}, {15, 17}

    };

    public PoseDetector(String modelPath, String configPath) {

        poseNet = Dnn.readNetFromCaffe(modelPath, configPath);

    }

    public void detectAndDrawSkeleton(Mat frame) {

        Mat inputBlob = Dnn.blobFromImage(frame, 1.0 / 255.0, new Size(inWidth, inHeight), new Scalar(0, 0, 0), false, false);
        poseNet.setInput(inputBlob);
        Mat output = poseNet.forward();

        int frameWidth = frame.width();
        int frameHeight = frame.height();

        int nPoints = 18;
        Point[] points = new Point[nPoints];

        int heatmapHeight = output.size(2);
        int heatmapWidth = output.size(3);

        for (int i = 0; i < nPoints; i++) {

            Mat heatMap = output.row(0).col(i);
            Core.MinMaxLocResult mm = Core.minMaxLoc(heatMap);

            Point point = new Point(mm.maxLoc.x * frameWidth / heatmapWidth, mm.maxLoc.y * frameHeight / heatmapHeight);

            if (mm.maxVal > threshold) {

                points[i] = point;
                Imgproc.circle(frame, point, 5, new Scalar(0, 255, 0), -1);

            }
            else {

                points[i] = null;

            }
        }

        for (int[] pair : pairs) {
            Point partA = points[pair[0]];
            Point partB = points[pair[1]];

            if (partA != null && partB != null) {

                Imgproc.line(frame, partA, partB, new Scalar(255, 0, 0), 3);

            }
        }
    }
}