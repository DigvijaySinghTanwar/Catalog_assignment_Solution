import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.DecompositionSolver;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ShamirSecretSharing {

    // Method to decode the value from a given base
    private static int decodeValue(String base, String value) {
        return Integer.parseInt(value, Integer.parseInt(base));
    }

    // Method to parse the JSON input and decode the roots
    private static List<int[]> parseRoots(String jsonInput) {
        try {
            JSONObject jsonObject = new JSONObject(jsonInput);
            JSONObject keys = jsonObject.getJSONObject("keys");
            int n = keys.getInt("n");
            int k = keys.getInt("k");

            if (n < k) {
                throw new IllegalArgumentException("Number of roots (n) must be greater than or equal to the minimum required (k).");
            }

            List<int[]> roots = new ArrayList<>();
            for (String key : jsonObject.keySet()) {
                if (!key.equals("keys")) {
                    JSONObject rootObject = jsonObject.getJSONObject(key);
                    String base = rootObject.getString("base");
                    String value = rootObject.getString("value");
                    int x = Integer.parseInt(key);
                    int y = decodeValue(base, value);
                    roots.add(new int[]{x, y});
                }
            }

            return roots;
        } catch (JSONException e) {
            throw new IllegalArgumentException("Invalid JSON format: " + e.getMessage());
        }
    }

    // Method to find the constant term c using polynomial interpolation
    private static double findConstantTerm(List<int[]> roots, int k) {
        try {
            // Create matrix A and vector B for Ax = B
            RealMatrix A = MatrixUtils.createRealMatrix(k, k);
            double[] B = new double[k];

            // Fill in the matrix A and vector B
            for (int i = 0; i < k; i++) {
                int x = roots.get(i)[0];
                for (int j = 0; j < k; j++) {
                    A.setEntry(i, j, Math.pow(x, j));
                }
                B[i] = roots.get(i)[1];
            }

            // Solve for the coefficients using LU decomposition
            LUDecomposition decomposition = new LUDecomposition(A);
            DecompositionSolver solver = decomposition.getSolver();
            double[] coefficients = solver.solve(MatrixUtils.createRealVector(B)).toArray();

            // The constant term is the first coefficient
            return coefficients[0];
        } catch (Exception e) {
            throw new IllegalArgumentException("Error solving matrix: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        String jsonInput = "";

        // Check if a file path was provided
        if (args.length > 0) {
            String filePath = args[0];
            try {
                jsonInput = new String(Files.readAllBytes(Paths.get(filePath)));
            } catch (IOException e) {
                System.err.println("Error reading file: " + e.getMessage());
                System.exit(1);
            }
        } else {
            // Read JSON input from command line
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter JSON input:");
            jsonInput = scanner.useDelimiter("\\A").next();
            scanner.close();
        }

        List<int[]> roots = parseRoots(jsonInput);
        JSONObject jsonObject = new JSONObject(jsonInput);
        int k = jsonObject.getJSONObject("keys").getInt("k");

        // Get only the first k roots
        List<int[]> selectedRoots = roots.subList(0, k);

        double c = findConstantTerm(selectedRoots, k);
        System.out.printf("The constant term (c) is: %.2f%n", c);
    }
}
