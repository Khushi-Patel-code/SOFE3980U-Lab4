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

        // skip header row
        double mse = 0, mae = 0, mare = 0;
        double epsilon = 1e-10;
        int n = 0;

        for (int i = 1; i < rows.size(); i++) {
            double actual    = Double.parseDouble(rows.get(i)[0]);
            double predicted = Double.parseDouble(rows.get(i)[1]);
            double diff = actual - predicted;

            mse  += diff * diff;
            mae  += Math.abs(diff);
            mare += Math.abs(diff) / (Math.abs(actual) + epsilon);
            n++;
        }

        mse  /= n;
        mae  /= n;
        mare  = (mare / n);

        System.out.println("for " + filePath.substring(filePath.lastIndexOf("/") + 1));
        System.out.println("\tMSE ="  + (float) mse);
        System.out.println("\tMAE ="  + (float) mae);
        System.out.println("\tMARE =" + (float) mare);
    }

    public static void main(String[] args) throws IOException, CsvException {
        String[] models = {"model_1.csv", "model_2.csv", "model_3.csv"};

        double[] mseVals  = new double[3];
        double[] maeVals  = new double[3];
        double[] mareVals = new double[3];
        double epsilon = 1e-10;

        for (int m = 0; m < models.length; m++) {
            CSVReader reader = new CSVReader(new FileReader(models[m]));
            List<String[]> rows = reader.readAll();
            reader.close();

            double mse = 0, mae = 0, mare = 0;
            int n = 0;

            for (int i = 1; i < rows.size(); i++) {
                double actual    = Double.parseDouble(rows.get(i)[0]);
                double predicted = Double.parseDouble(rows.get(i)[1]);
                double diff = actual - predicted;

                mse  += diff * diff;
                mae  += Math.abs(diff);
                mare += Math.abs(diff) / (Math.abs(actual) + epsilon);
                n++;
            }

            mse  /= n;
            mae  /= n;
            mare  = (mare / n);

            mseVals[m]  = mse;
            maeVals[m]  = mae;
            mareVals[m] = mare;

            System.out.println("for " + models[m]);
            System.out.println("\tMSE ="  + (float) mse);
            System.out.println("\tMAE ="  + (float) mae);
            System.out.println("\tMARE =" + (float) mare);
        }

        // find best model per metric
        System.out.println("According to MSE, The best model is "  + models[argMin(mseVals)]);
        System.out.println("According to MAE, The best model is "  + models[argMin(maeVals)]);
        System.out.println("According to MARE, The best model is " + models[argMin(mareVals)]);
    }

    static int argMin(double[] arr) {
        int idx = 0;
        for (int i = 1; i < arr.length; i++)
            if (arr[i] < arr[idx]) idx = i;
        return idx;
    }
}