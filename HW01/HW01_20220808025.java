import java.util.ArrayList;
import java.util.List;

public class HW01_20220808025 {

    public static void main(String[] args) {

        }
    }





interface ITrafficControl {
    int[][] getCityGrid();

    int countMalfunctioningLights();

    String mostMalfunction();

    int countMalfunctioningNeighbors(int row, int col);

    List<int[]> crucialIntersections();

    String toString();
}

class TrafficControl implements ITrafficControl {
    private int[][] cityGrid;


    public TrafficControl(int m, int n) {
        cityGrid = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                cityGrid[i][j] = (int) (Math.random() * 2.5); // ????????
            }
        }
    }

    public TrafficControl(int[][] cityGrid) {
        this.cityGrid = cityGrid;
    }

    @Override
    public int[][] getCityGrid() {
        return cityGrid;
    }

    @Override
    public int countMalfunctioningLights() {
        int a = 0;
        for (int i = 0; i < cityGrid.length; i++) {
            for (int j = 0; j < cityGrid[i].length; j++) {
                if (cityGrid[i][j] == 1) {
                    a++;
                }
            }
        }
        return a;
    }

    @Override
    public String mostMalfunction() {
        int a = 0;
        int b = 0;
        int row=0;
        for (int i = 0; i < cityGrid.length; i++) {
            for (int j = 0; j < cityGrid[i].length; j++) {
                if (cityGrid[i][j] == 1) {
                    a++;
                }
            }
            if (a > b) {
                b = a;
                row = i;
            }
            a=0;
        }
        int c = 0;
        int d = 0;
        int col=0;
        for (int i = 0; i < cityGrid[0].length; i++) {
            for (int j = 0; j < cityGrid.length; j++) {
                if (cityGrid[j][i] == 1) {
                    c++;
                }
            }
            if (c>d) {
                d = c;
                col=i;
            }
            c=0;


        }
        if (b > d)
            return "Row: " + row;
        else return "Column: " + col;

    }

    public int countMalfunctioningNeighbors(int row, int col) {
        int malfunctioningNeighbors = 0;



        if (row > 0 && cityGrid[row - 1][col] == 1) {
            malfunctioningNeighbors++;
        }


        if (row < cityGrid.length - 1 && cityGrid[row + 1][col] == 1) {
            malfunctioningNeighbors++;
        }

        if (col > 0 && cityGrid[row][col - 1] == 1) {
            malfunctioningNeighbors++;
        }


        if (col < cityGrid[0].length - 1 && cityGrid[row][col + 1] == 1) {
            malfunctioningNeighbors++;
        }

        return malfunctioningNeighbors;
    }

    @Override
    public List<int[]> crucialIntersections() {
        List<int[]> criticalIntersections = new ArrayList<>();


        for (int i = 0; i < cityGrid.length; i++) {
            for (int j = 0; j < cityGrid[i].length; j++) {

                int malfunctioningNeighbors = countMalfunctioningNeighbors(i, j);

                if (malfunctioningNeighbors >= 2) {
                    criticalIntersections.add(new int[]{i, j});
                }
            }
        }

        return criticalIntersections;
    }


    public void printCrucialIntersections(List<int[]> intersections) {
        System.out.println("Kritik kesişim noktaları:");
        for (int[] point : intersections) {
            System.out.println("[" + point[0] + ", " + point[1] + "]");
        }
    }



    @Override
    public String toString() {
        String a = "";

        for (int i = 0; i < cityGrid.length; i++) {
            for (int j = 0; j < cityGrid[i].length; j++) {
                a += cityGrid[i][j];  // Önce elemanı ekle

                if (j < cityGrid[i].length - 1) {
                    a += " ";
                }
            }
            a += "\n";
        }

        return a.trim();
    }

}


