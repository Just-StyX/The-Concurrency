package org.example.chapter1.first_example;

public class RowMultiplierTask implements Runnable {
    private final double[][] result;
    private final double[][] first_matrix;
    private final double[][] second_matrix;
    private final int row;

    public RowMultiplierTask(double[][] result, double[][] firstMatrix, double[][] secondMatrix, int row) {
        this.result = result;
        first_matrix = firstMatrix;
        second_matrix = secondMatrix;
        this.row = row;
    }

    @Override
    public void run() {
        for (int j = 0; j < second_matrix[0].length; j++) {
            result[row][j] = 0;
            for (int k = 0; k < first_matrix[0].length; k++) {
                result[row][j] += first_matrix[row][k] * second_matrix[k][j];
            }
        }
    }
}
