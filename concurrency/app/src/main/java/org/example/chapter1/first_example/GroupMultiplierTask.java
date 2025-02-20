package org.example.chapter1.first_example;

public class GroupMultiplierTask implements Runnable {
    private final double[][] result;
    private final double[][] first_matrix;
    private final double[][] second_matrix;
    private final int startIndex;
    private final int endIndex;

    public GroupMultiplierTask(double[][] result, double[][] firstMatrix, double[][] secondMatrix, int startIndex, int endIndex) {
        this.result = result;
        first_matrix = firstMatrix;
        second_matrix = secondMatrix;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    @Override
    public void run() {
        for (int i = startIndex; i < endIndex; i++) {
            for (int j = 0; j < second_matrix[0].length; j++) {
                result[i][j] = 0;
                for (int k = 0; k < first_matrix[i].length; k++) {
                    result[i][j] += first_matrix[i][k] * second_matrix[k][j];
                }
            }
        }
    }
}
