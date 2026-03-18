package com.ontariotechu.sofe3980U;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class App {

    public static void main(String[] args) throws IOException, CsvException {
        CSVReader reader = new CSVReader(new FileReader("model.csv"));
        List<String[]> rows = reader.readAll();
        reader.close();

        int n = rows.size() - 1;
        int m = 5;

        int[] trueVals = new int[n];
        double[][] predVals = new double[n][m];

        for (int i = 0; i < n; i++) {
            trueVals[i] = Integer.parseInt(rows.get(i + 1)[0].trim());
            for (int j = 0; j < m; j++) {
                predVals[i][j] = Double.parseDouble(rows.get(i + 1)[j + 1].trim());
            }
        }

        // Cross Entropy
        double ce = 0;
        for (int i = 0; i < n; i++) {
            int trueClass = trueVals[i] - 1;
            ce += Math.log(predVals[i][trueClass]);
        }
        ce = -ce / n;

        // Confusion matrix
        int[][] confMatrix = new int[m][m];
        for (int i = 0; i < n; i++) {
            int trueClass = trueVals[i] - 1;
            int predClass = 0;
            double maxProb = predVals[i][0];
            for (int j = 1; j < m; j++) {
                if (predVals[i][j] > maxProb) {
                    maxProb = predVals[i][j];
                    predClass = j;
                }
            }
            confMatrix[predClass][trueClass]++;
        }

        System.out.println("CE =" + (float) ce);
        System.out.println("Confusion matrix");
        System.out.println("\t\t\ty=1\t y=2\t y=3\t y=4\t y=5");
        for (int i = 0; i < m; i++) {
            System.out.print("\ty^=" + (i + 1) + "\t");
            for (int j = 0; j < m; j++) {
                System.out.print(confMatrix[i][j] + "\t");
            }
            System.out.println();
        }
    }
}