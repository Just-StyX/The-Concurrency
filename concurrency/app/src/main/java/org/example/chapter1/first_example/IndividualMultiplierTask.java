package org.example.chapter1.first_example;

public class IndividualMultiplierTask implements Runnable {
    private final double[][] result;
    private final double[][] first_matrix;
    private final double[][] second_matrix;
    private final int row;
    private final int column;

    public IndividualMultiplierTask(double[][] result, double[][] firstMatrix, double[][] secondMatrix, int row, int column) {
        this.result = result;
        first_matrix = firstMatrix;
        second_matrix = secondMatrix;
        this.row = row;
        this.column = column;
    }

    @Override
    public void run() {
        result[row][column] = 0;
        for (int k = 0; k < first_matrix[row].length; k++) {
            result[row][column] += first_matrix[row][k] * second_matrix[k][column];
        }
    }
}
