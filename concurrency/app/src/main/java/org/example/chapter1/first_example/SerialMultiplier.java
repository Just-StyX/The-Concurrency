package org.example.chapter1.first_example;

public class SerialMultiplier {
    public static void multiply(double[][] first_matrix, double[][] second_matrix, double[][] result) {
        int first_row_length = first_matrix.length;
        int first_column_length = first_matrix[0].length;
        int second_column_length = second_matrix[0].length;

        for (int i = 0; i < first_row_length; i++) {
            for (int j = 0; j < second_column_length; j++) {
                result[i][j] = 0;
                for (int k = 0; k < first_column_length; k++) {
                    result[i][j] += first_matrix[i][k] * second_matrix[k][j];
                }
            }
        }
    }
}
