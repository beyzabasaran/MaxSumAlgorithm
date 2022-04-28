import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


public class FindMaxSum {

    public static final int MAXROWSIZE = 15; //max size of given pyramid example
    public static final int MAXCOLUMNSIZE = 15;

    public static int[][] numberArray = new int[MAXROWSIZE][MAXCOLUMNSIZE]; //2D matrix initialization

    public static int findMaxSum(String fileName, ArrayList<Location> path) {

        readFile(fileName, numberArray);

        if (isPrime(numberArray[0][0])) {
            return 0;
        } else {
            return findMaxSumRecursive(0, 0, MAXROWSIZE, path); //recursive func
        }
    }

    // x row index, y column index, size row size
    private static int findMaxSumRecursive(int x, int y, int size, ArrayList<Location> path) {

        int leftSum = 0;
        int rightSum = 0;

        ArrayList<Location> tempLeftList = new ArrayList<>();
        ArrayList<Location> tempRightList = new ArrayList<>();

        if (x >= size) { // if the size exceeded
            return 0;
        } else if (x == (size - 1)) {
            path.add(new Location(x, y, numberArray[x][y])); //Store x pos, y pos and the number to the list
            return numberArray[x][y];
        }

        if (!isPrime(numberArray[x + 1][y])) { //calculate right sum diagonal walk
            rightSum = findMaxSumRecursive(x + 1, y, size, tempRightList);
        }

        if (!isPrime(numberArray[x + 1][y + 1])) { //calculate left sum diagonal walk
            leftSum = findMaxSumRecursive(x + 1, y + 1, size, tempLeftList);
        }

        // maximum sum path will be the complete path
        if (isPrime(numberArray[x + 1][y + 1]) && isPrime(numberArray[x + 1][y]))
            return Integer.MIN_VALUE;

        // backtrace the path
        if (leftSum > rightSum) { // compare left sum and right sum results
            path.addAll(tempLeftList);
            path.add(new Location(x, y, numberArray[x][y]));
            return leftSum + numberArray[x][y];
        } else {
            path.addAll(tempRightList);
            path.add(new Location(x, y, numberArray[x][y]));
            return rightSum + numberArray[x][y];
        }
    }

    private static boolean isPrime(int number) { //Function for checking prime numbers
        if (number < 2) {
            return false;
        }
        for (int i = 2; i < number; ++i) { // Checks if it is a prime number, and return true or false
            if ((number % i) == 0) {
                return false;
            }
        }

        return true;
    }

    //  Reads number from the file and stores them into numberArray
    private static void readFile(String fileName, int[][] numberArray) {

        try {

            InputStream inputStream = new FileInputStream(fileName);
            Scanner scanner = new Scanner(inputStream); // read from file
            int index = 0;
            while (scanner.hasNextLine()) {  //if text has next line
                String line = scanner.nextLine();
                String[] numbers = line.split("\\s+");  //store numbers in an array
                for (int i = 0; i < numbers.length; ++i) {
                    numberArray[index][i] = Integer.parseInt(numbers[i]); //cast string to int
                }
                ++index; //prefix increment
            }
        } catch (FileNotFoundException e) {
            System.out.println("File can not be found!");
            java.lang.System.exit(1);
        } catch (IOException e) {
            System.out.println("Input or output problem occurred in the file!");
            java.lang.System.exit(1);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Check file row and column sizes!");
            java.lang.System.exit(1);
        }
    }

    /*Data structure to keep number's positions as x, y coordinates*/
    private static class Location {
        private int x; // x coordinate
        private int y; // y coordinate
        private int number; // number

        public Location(int x, int y, int number) {
            this.x = x;
            this.y = y;
            this.number = number;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getNumber() {
            return number;
        }
    }

    public static void FindPath(String fileName) {
        ArrayList<Location> path = new ArrayList<>(); //location class's object arraylist
        System.out.printf("\nMaximum Sum: \n\n%d", findMaxSum(fileName, path));

        System.out.println("\n\nMaximum Sum Path: \n");
        int pathLength = path.size() - 1;
        for (int i = pathLength; i >= 0; --i) {
            if (path.get(i).number != 0) {
                System.out.print(path.get(i).number + " -> ");

            }
        }

        System.out.print("Successfully Finished!!\n");

        System.out.println("\nPrinting the Maximum Sum Path: \n");
        for (int i = 0; i < getArraySize(fileName); ++i) {
            int index = path.size() - i - 1;
            for (int j = 0; j < getArraySize(fileName); ++j) {
                if (index >= 0 && index < path.size()) {
                    if (path.get(index).getX() == i && path.get(index).getY() == j) {
                        if (path.get(index).getNumber() != 0) {
                            System.out.printf("%d\t", path.get(index).getNumber());
                        } else {
                            System.out.printf(".\t");
                        }
                    } else {
                        System.out.printf(".\t");
                    }
                } else {
                    System.out.printf(".\t");
                }
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    //Specifying array size for printing path based on the given pyramid size dynamically
    public static int getArraySize(String fileName) {
        int size = 0;
        try {
            InputStream inputStream = new FileInputStream(fileName);
            Scanner scanner = new Scanner(inputStream); // read from file
            while (scanner.hasNextLine()) {  //if text has next line
                String line = scanner.nextLine();
                if (!line.equalsIgnoreCase("")) { //if line is not empty
                    size++; //posix increment
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File can not be found!");
            java.lang.System.exit(1);
        } catch (IOException e) {
            System.out.println("Input or output problem occurred in the file!");
            java.lang.System.exit(1);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Check file row and column sizes!");
            java.lang.System.exit(1);
        }
        return size;
    }


    public static void main(String args[]) {
        String fileName1 = "src/testFile1.txt";
        String fileName2 = "src/testFile2.txt";
        System.out.println("--Solution for First Pyramid--");
        FindPath(fileName1);
        System.out.println("--Solution for Second Pyramid--");
        FindPath(fileName2);
    }
}


