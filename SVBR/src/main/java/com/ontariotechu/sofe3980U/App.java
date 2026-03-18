package com.ontariotechu.sofe3980U;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class App {

    public static void evaluateModel(String filePath) throws IOException, CsvException {
        CSVReader reader = new CSVReader(new FileReader(filePath));
        List<String[]> rows = reader.readAll();
        reader.close();

        int n = rows.size() - 1; // skip header
        int[] trueVals = new int[n];
        double[] predVals = new double[n];

        for (int i = 0; i < n; i++) {
            trueVals[i]  = Integer.parseInt(rows.get(i + 1)[0].trim());
            predVals[i]  = Double.parseDouble(rows.get(i + 1)[1].trim());
        }

        // BCE
        double bce = 0;
        for (int i = 0; i < n; i++) {
            if (trueVals[i] == 1)
                bce += Math.log(predVals[i]);
            else
                bce += Math.log(1 - predVals[i]);
        }
        bce = -bce / n;

        // Confusion matrix at threshold 0.5
        int TP = 0, FP = 0, TN = 0, FN = 0;
        for (int i = 0; i < n; i++) {
            int predBinary = predVals[i] >= 0.5 ? 1 : 0;
            if (trueVals[i] == 1 && predBinary == 1) TP++;
            else if (trueVals[i] == 0 && predBinary == 1) FP++;
            else if (trueVals[i] == 0 && predBinary == 0) TN++;
            else FN++;
        }

        double accuracy  = (double)(TP + TN) / n;
        double precision = (double) TP / (TP + FP);
        double recall    = (double) TP / (TP + FN);
        double f1        = 2 * precision * recall / (precision + recall);

        // AUC-ROC
        int nPos = 0, nNeg = 0;
        for (int i = 0; i < n; i++) {
            if (trueVals[i] == 1) nPos++;
            else nNeg++;
        }

        double[] xArr = new double[101];
        double[] yArr = new double[101];
        for (int t = 0; t <= 100; t++) {
            double th = t / 100.0;
            int tp = 0, fp = 0;
            for (int i = 0; i < n; i++) {
                if (trueVals[i] == 1 && predVals[i] >= th) tp++;
                if (trueVals[i] == 0 && predVals[i] >= th) fp++;
            }
            yArr[t] = (double) tp / nPos;
            xArr[t] = (double) fp / nNeg;
        }

        double auc = 0;
        for (int i = 1; i <= 100; i++) {
            auc += (yArr[i - 1] + yArr[i]) * Math.abs(xArr[i - 1] - xArr[i]) / 2;
        }

        // Print results
        String modelName = filePath.substring(filePath.lastIndexOf("/") + 1);
        if (modelName.contains("\\"))
            modelName = filePath.substring(filePath.lastIndexOf("\\") + 1);

        System.out.println("for " + modelName);
        System.out.println("\tBCE =" + (float) bce);
        System.out.println("\tConfusion matrix");
        System.out.println("\t\t\t\ty=1\t y=0");
        System.out.println("\t\ty^=1\t" + TP + "\t" + FP);
        System.out.println("\t\ty^=0\t" + FN + "\t" + TN);
        System.out.println("\tAccuracy ="  + (float) accuracy);
        System.out.println("\tPrecision =" + (float) precision);
        System.out.println("\tRecall ="    + (float) recall);
        System.out.println("\tf1 score ="  + (float) f1);
        System.out.println("\tauc roc ="   + (float) auc);
    }

    public static void main(String[] args) throws IOException, CsvException {
        String[] models = {"model_1.csv", "model_2.csv", "model_3.csv"};

        double[] bceVals      = new double[3];
        double[] accVals      = new double[3];
        double[] precVals     = new double[3];
        double[] recallVals   = new double[3];
        double[] f1Vals       = new double[3];
        double[] aucVals      = new double[3];

        for (int m = 0; m < models.length; m++) {
            CSVReader reader = new CSVReader(new FileReader(models[m]));
            List<String[]> rows = reader.readAll();
            reader.close();

            int n = rows.size() - 1;
            int[] trueVals   = new int[n];
            double[] predVals = new double[n];

            for (int i = 0; i < n; i++) {
                trueVals[i]  = Integer.parseInt(rows.get(i + 1)[0].trim());
                predVals[i]  = Double.parseDouble(rows.get(i + 1)[1].trim());
            }

            double bce = 0;
            for (int i = 0; i < n; i++) {
                if (trueVals[i] == 1) bce += Math.log(predVals[i]);
                else bce += Math.log(1 - predVals[i]);
            }
            bce = -bce / n;

            int TP = 0, FP = 0, TN = 0, FN = 0;
            for (int i = 0; i < n; i++) {
                int predBinary = predVals[i] >= 0.5 ? 1 : 0;
                if (trueVals[i] == 1 && predBinary == 1) TP++;
                else if (trueVals[i] == 0 && predBinary == 1) FP++;
                else if (trueVals[i] == 0 && predBinary == 0) TN++;
                else FN++;
            }

            double accuracy  = (double)(TP + TN) / n;
            double precision = (double) TP / (TP + FP);
            double recall    = (double) TP / (TP + FN);
            double f1        = 2 * precision * recall / (precision + recall);

            int nPos = 0, nNeg = 0;
            for (int i = 0; i < n; i++) {
                if (trueVals[i] == 1) nPos++;
                else nNeg++;
            }

            double[] xArr = new double[101];
            double[] yArr = new double[101];
            for (int t = 0; t <= 100; t++) {
                double th = t / 100.0;
                int tp = 0, fp = 0;
                for (int i = 0; i < n; i++) {
                    if (trueVals[i] == 1 && predVals[i] >= th) tp++;
                    if (trueVals[i] == 0 && predVals[i] >= th) fp++;
                }
                yArr[t] = (double) tp / nPos;
                xArr[t] = (double) fp / nNeg;
            }

            double auc = 0;
            for (int i = 1; i <= 100; i++) {
                auc += (yArr[i - 1] + yArr[i]) * Math.abs(xArr[i - 1] - xArr[i]) / 2;
            }

            bceVals[m]    = bce;
            accVals[m]    = accuracy;
            precVals[m]   = precision;
            recallVals[m] = recall;
            f1Vals[m]     = f1;
            aucVals[m]    = auc;

            System.out.println("for " + models[m]);
            System.out.println("\tBCE =" + (float) bce);
            System.out.println("\tConfusion matrix");
            System.out.println("\t\t\t\ty=1\t y=0");
            System.out.println("\t\ty^=1\t" + TP + "\t" + FP);
            System.out.println("\t\ty^=0\t" + FN + "\t" + TN);
            System.out.println("\tAccuracy ="  + (float) accuracy);
            System.out.println("\tPrecision =" + (float) precision);
            System.out.println("\tRecall ="    + (float) recall);
            System.out.println("\tf1 score ="  + (float) f1);
            System.out.println("\tauc roc ="   + (float) auc);
        }

        System.out.println("According to BCE, The best model is "      + models[argMin(bceVals)]);
        System.out.println("According to Accuracy, The best model is " + models[argMax(accVals)]);
        System.out.println("According to Precision, The best model is "+ models[argMax(precVals)]);
        System.out.println("According to Recall, The best model is "   + models[argMax(recallVals)]);
        System.out.println("According to F1 score, The best model is " + models[argMax(f1Vals)]);
        System.out.println("According to AUC ROC, The best model is "  + models[argMax(aucVals)]);
    }

    static int argMin(double[] arr) {
        int idx = 0;
        for (int i = 1; i < arr.length; i++)
            if (arr[i] < arr[idx]) idx = i;
        return idx;
    }

    static int argMax(double[] arr) {
        int idx = 0;
        for (int i = 1; i < arr.length; i++)
            if (arr[i] > arr[idx]) idx = i;
        return idx;
    }
}