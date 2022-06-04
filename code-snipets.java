




/////////////////////// FILE INPUT OUPUT
/////////////////////// FILE INPUT OUPUT
/////////////////////// FILE INPUT OUPUT
/////////////////////// FILE INPUT OUPUT
/////////////////////// FILE INPUT OUPUT
/////////////////////// FILE INPUT OUPUT
/////////////////////// FILE INPUT OUPUT AND SQL


You can write data into a .csv file using the OpenCSV library and, you can communicate with MySQL database through a Java program using the mysql-java-connector.

Maven dependency
The following are the dependencies you need to include in your pom.xml file to write data to a .csv file from a database table.

<dependency>
   <groupId>com.opencsv</groupId>
   <artifactId>opencsv</artifactId>
   <version>4.4</version>
</dependency>
<dependency>
   <groupId>org.apache.commons</groupId>
   <artifactId>commons-lang3</artifactId>
   <version>3.9</version>
</dependency>
<dependency>
   <groupId>mysql</groupId>
   <artifactId>mysql-connector-java</artifactId>
   <version>5.1.6</version>
</dependency>
Writing data to a CSV file
The CSVWriter class of the com.opencsv package represents a simple CSV writer. While instantiating this class you need to pass a Writer object representing the file, to which you want to write the data, as a parameter to its constructor. It provides methods named writeAll() and writeNext() to write data to a .csv file

Using the writeNext() method
The writeNext() method of the CSVWriter class writes the next line to the .csv file

Example
Assume we have created a table with name empDetails and populated it using the following queries −

CREATE TABLE empDetails (ID INT, Name VARCHAR(255), Salary INT, start_date VARCHAR(255), Dept VARCHAR(255));
Insert INTO empDetails values (1, 'Krishna', 2548, '2012-01-01', 'IT');
Insert INTO empDetails values (2, 'Vishnu', 4522, '2013-02-26', 'Operations');
Insert INTO empDetails values (3, 'Raja', 3021, '2016-10-10', 'HR');
Insert INTO empDetails values (4, 'Raghav', 6988, '2012-01-01', 'IT');
The following Java program creates a csv file from the above-created table.

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import com.opencsv.CSVWriter;
public class DbToCSV {
   public static void main(String args[]) throws SQLException, IOException {
      //Getting the connection
      String url = "jdbc:mysql://localhost/mydb";
      Connection con = DriverManager.getConnection(url, "root", "password");
      System.out.println("Connection established......");
      //Creating the Statement
      Statement stmt = con.createStatement();
      //Query to retrieve records
      String query = "Select * from empDetails";
      //Executing the query
      stmt.executeQuery("use mydb");
      ResultSet rs = stmt.executeQuery(query);
      //Instantiating the CSVWriter classh
      CSVWriter writer = new CSVWriter(new FileWriter("D://output.csv"));
      ResultSetMetaData Mdata = rs.getMetaData();
      Mdata.getColumnName(1);
      //Writing data to a csv file
      String line1[] = {Mdata.getColumnName(1), Mdata.getColumnName(2), Mdata.getColumnName(3), Mdata.getColumnName(4), Mdata.getColumnName(5)};
      writer.writeNext(line1);
      String data[] = new String[5];
      while(rs.next()) {
         data[0] = new Integer(rs.getInt("ID")).toString();
         data[1] = rs.getString("Name");
         data[2] = new Integer(rs.getInt("Salary")).toString();
         data[3] = rs.getString("start_date");
         data[4] = rs.getString("Dept");
         writer.writeNext(data);
      }
      //Flushing data from writer to file
      writer.flush();
      System.out.println("Data entered");
   }
}
Output
Connection established......
Data entered
If you verify the generated .csv file you can observe its contents as follows −

"ID","Name","Salary","start_date","Dept"
"1","Krishna","2548","2012-01-01","IT"
"2","Vishnu","4522","2013-02-26","Operations"
"3","Raja","3021","2016-10-10","HR"
"4","Raghav","6988","2012-01-01","IT"



/////////////////////// FILE INPUT OUPUT

source : https://dzone.com/articles/how-to-read-a-big-csv-file-with-java-8-and-stream

private List<YourJavaItem> processInputFile(String inputFilePath) {

    List<YourJavaItem> inputList = new ArrayList<YourJavaItem>();

    try{

      File inputF = new File(inputFilePath);
      InputStream inputFS = new FileInputStream(inputF);
      BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));

      // skip the header of the csv
      inputList = br.lines().skip(1).map(mapToItem).collect(Collectors.toList());
      br.close();
    } catch (FileNotFoundException|IOException e) {
      ....
    }

    return inputList ;
}


private Function<String, YourJavaItem> mapToItem = (line) -> {

  String[] p = line.split(COMMA);// a CSV has comma separated lines

  YourJavaItem item = new YourJavaItem();

  item.setItemNumber(p[0]);//<-- this is the first column in the csv file
  if (p[3] != null && p[3].trim().length() > 0) {
    item.setSomeProeprty(p[3]);
  }
  //more initialization goes here

  return item;
}


Some explanation about the above code might be needed:

 lines() : returns a stream object.

 skip(1) : skips the first line in the CSV file, which in this case is the header of the file.

 map(mapToItem) : calls the mapToItem  function for each line in the file.

 collect(Collectors.toList()) : creates a list containing all the items created by mapToItem  function.

Now, mapToItem  function looks like this:



Quelques explications sur le code ci-dessus pourraient être nécessaires:

  lines() : renvoie un objet flux.

  skip(1) : saute la première ligne du fichier CSV, qui dans ce cas est l'en-tête du fichier.

  map(mapToItem) : appelle la fonction mapToItem pour chaque ligne du fichier.

  collect(Collectors.toList()) : crée une liste contenant tous les éléments créés par la fonction mapToItem.

Maintenant, la fonction mapToItem ressemble à ceci this:


/////////////////////// FILE INPUT OUPUT

--------------  PERFORMANCE AND TUNING ----------------
source : https://www.oracle.com/technical-resources/articles/javase/perftuning.html
Règles de base pour accélérer les E/S
Pour commencer la discussion, voici quelques règles de base sur la façon d'accélérer les E/S :

Évitez d'accéder au disque.
Évitez d'accéder au système d'exploitation sous-jacent.
Évitez les appels de méthode.
Évitez de traiter les octets et les caractères individuellement.

Ces règles ne peuvent évidemment pas être appliquées de manière "globale", 
car si tel était le cas, aucune entrée/sortie ne serait jamais effectuée ! 



FileInputStream inputStream = null;
Scanner sc = null;
try {
    inputStream = new FileInputStream(path);
    sc = new Scanner(inputStream, "UTF-8");
    while (sc.hasNextLine()) {
        String line = sc.nextLine();
        // System.out.println(line);
    }
    // note that Scanner suppresses exceptions
    if (sc.ioException() != null) {
        throw sc.ioException();
    }
} finally {
    if (inputStream != null) {
        inputStream.close();
    }
    if (sc != null) {
        sc.close();
    }
}




///////////////////////

import java.text.*;

public class DecimalFormatDemo {

   static public void customFormat(String pattern, double value ) {
      DecimalFormat myFormatter = new DecimalFormat(pattern);
      String output = myFormatter.format(value);
      System.out.println(value + "  " + pattern + "  " + output);
   }

   static public void main(String[] args) {

      customFormat("###,###.###", 123456.789);
      customFormat("###.##", 123456.789);
      customFormat("000000.000", 123.78);
      customFormat("$###,###.###", 12345.67);  
   }
}




The output is:

123456.789  ###,###.###  123,456.789
123456.789  ###.##  123456.79
123.78  000000.000  000123.780
12345.67  $###,###.###  $12,345.67



///////////  TRI INCREMENTIEL 1

public class SortArrayDemo {
  public static void main(String[] args) {
    int[] arrNum = {45,12,78,34,67,8};
    
    int temp = 0;
    System.out.println("Sorted array elements:");
    for(int i=0;i<arrNum.length;i++) {
      for(int j=i+1;j<arrNum.length;j++) {
        if(arrNum[i] > arrNum[j]) {
          temp = arrNum[i];
          arrNum[i] = arrNum[j];
          arrNum[j] = temp;
        }
      
      }
      System.out.println(arrNum[i]);
    }
  }
}

///////////  TRI INCREMENTIEL 2

public static void reverse(int[] input) { 
   int last = input.length - 1; 
   int middle = input.length / 2; 
   for (int i = 0; i <= middle; i++) { 
      int temp = input[i]; input[i] = input[last - i]; 
      input[last - i] = temp; 
   } 
}

Read more: https://www.java67.com/2016/07/how-to-sort-array-in-descending-order-in-java.html#ixzz7U4g51bf8

///////////  TRI INCREMENTIEL 2

int[] arr = new int[]{12,3,5,21,4,85,6,9,2,1};
      for (int i : arr) {
         System.out.print(i+" ");
      }
      for (int i = 0; i < arr.length; i++) {
         for (int j = 0; j < arr.length; j++) {
            if (arr[i] < arr[j]) {
               int temp = arr[i];
               arr[i] = arr[j];
               arr[j] = temp;
            }
         }
      }
      System.out.println("\nAfter Sorting...");
      for (int i : arr) {
         System.out.print(i+" ");
      }

///////////// TRI AVEC METHODE Collection.sort()

import java.util.Arrays;
import java.util.Collections;

public class Tri {

   public static void main(String[] args) {

   // initialiser le tableau
   int array[] = {8, 77, 15, 24, 46, 13};

   // créer un tableau qui contient des objets Integer
   Integer[] integerArray = new Integer[array.length];
   // afficher tous les entiers avant le tri
   // copier tous les valeurs dans un tableau de type Integer
   for (int i=0; i < array.length; i++) {
      System.out.println("nombre: " + array[i]);
   //instancier un nouveau Integer
   integerArray[i] = new Integer(array[i]);
   }
 
   // trier le tableau, puis l'inverser
   Arrays.sort(integerArray, Collections.reverseOrder());

   // lafficher tous les entiers après le tri
   System.out.println("Tableau trié\n");
   for (int entier : integerArray) {
      System.out.println("nombre: " + entier);
   }
   }
}

///////////// TRI AVEC METHODE Arrays.sort()

import java.util.Arrays;

public class ArraysTest {

   public static void main(String[] args) {

   // initialiser le tableau
   int array[] = {11, 34, 23, 62, 6, 41};

   // afficher tous les entiers avant le tri
   for (int entier : array) {
      System.out.println("nombre: " + entier);
   }

   // trier le tableau
   Arrays.sort(array);

   // lafficher tous les entiers après le tri
   System.out.println("Tableau trié\n");
   for (int entier : array) {
      System.out.println("nombre: " + entier);
   }
   }
}

///////////// TRI EN ENVIRONNEMENT THREADED

int[] arr = new int[]{12,3,5,21,4,85,6,9,2,1};
      for (int i : arr) {
         System.out.print(i+" ");
      }
      Arrays.parallelSort(arr);
      System.out.println("\nAfter Sorting...");
      for (int i : arr) {
         System.out.print(i+" ");
      }

///////////////// RECHERCHE DANS UN TABLEAU

public static int recherche(int maValeur, int[] tab, int depart ) {
   int monIndice=-1;
   for (int i=depart ; monIndice==-1 && i< tab.length ; i++) {
       if (maValeur==tab[i]) {
          monIndice = i;
       }
   }
   return monIndice;
}


///////////////// MEDIAN

static double median(int[] values) {
     // sort array
     Arrays.sort(values);
     double median;
     // get count of scores
     int totalElements = values.length;
     // check if total number of scores is even
     if (totalElements % 2 == 0) {
        int sumOfMiddleElements = values[totalElements / 2] +
                                  values[totalElements / 2 - 1];
        // calculate average of middle elements
        median = ((double) sumOfMiddleElements) / 2;
     } else {
        // get the middle element
        median = (double) values[values.length / 2];
  }
  return median;
 }

 ////////////////// MEAN

// Function for calculating mean
    public static double findMean(int a[], int n)
    {
        int sum = 0;
        for (int i = 0; i < n; i++)
            sum += a[i];
 
        return (double)sum / (double)n;
    }
 
    // Function for calculating median
    public static double findMedian(int a[], int n)
    {
        // First we sort the array
        Arrays.sort(a);
 
        // check for even case
        if (n % 2 != 0)
            return (double)a[n / 2];
 
        return (double)(a[(n - 1) / 2] + a[n / 2]) / 2.0;
    }
 




 ////////////////// toSTRING

    @Override
    public String toString() {
        return String.format("Correlation Distance(%s)", method);
    }







 ////////////////// STATISTICS



    
    /**
     * Returns the sum of each row.
     * @return the sum of each row.
     */
    public double[] rowSums() {
        double[] x = new double[m];

        for (int j = 0; j < n; j++) {
            for (int i = 0; i < m; i++) {
                x[i] += get(i, j);
            }
        }

        return x;
    }

    /**
     * Returns the mean of each row.
     * @return the mean of each row.
     */
    public double[] rowMeans() {
        double[] x = rowSums();

        for (int i = 0; i < m; i++) {
            x[i] /= n;
        }

        return x;
    }

    /**
     * Returns the standard deviations of each row.
     * @return the standard deviations of each row.
     */
    public double[] rowSds() {
        double[] x = new double[m];
        double[] x2 = new double[m];

        for (int j = 0; j < n; j++) {
            for (int i = 0; i < m; i++) {
                double a = get(i, j);
                x[i] += a;
                x2[i] += a * a;
            }
        }

        for (int i = 0; i < m; i++) {
            double mu = x[i] / n;
            x[i] = Math.sqrt(x2[i] / n - mu * mu);
        }

        return x;
    }

    /**
     * Returns the sum of each column.
     * @return the sum of each column.
     */
    public double[] colSums() {
        double[] x = new double[n];

        for (int j = 0; j < n; j++) {
            for (int i = 0; i < m; i++) {
                x[j] += get(i, j);
            }
        }

        return x;
    }

    /**
     * Returns the mean of each column.
     * @return the mean of each column.
     */
    public double[] colMeans() {
        double[] x = colSums();

        for (int j = 0; j < n; j++) {
            x[j] /= m;
        }

        return x;
    }

    /**
     * Returns the standard deviations of each column.
     * @return the standard deviations of each column.
     */
    public double[] colSds() {
        double[] x = new double[n];

        for (int j = 0; j < n; j++) {
            double mu = 0.0;
            double sumsq = 0.0;
            for (int i = 0; i < m; i++) {
                double a = get(i, j);
                mu += a;
                sumsq += a * a;
            }
            mu /= m;
            x[j] = Math.sqrt(sumsq / m - mu * mu);
        }

        return x;
    }

    /**
     * Standardizes the columns of matrix.
     * @return a new matrix with zero mean and unit variance for each column.
     */
    public Matrix standardize() {
        double[] center = colMeans();
        double[] scale = colSds();
        return scale(center, scale);
    }

    /**
     * Centers and scales the columns of matrix.
     * @param center column center. If null, no centering.
     * @param scale column scale. If null, no scaling.
     * @return a new matrix with zero mean and unit variance for each column.
     */
    public Matrix scale(double[] center, double[] scale) {
        if (center == null && scale == null) {
            throw new IllegalArgumentException("Both center and scale are null");
        }

        Matrix matrix = new Matrix(m, n);

        if (center == null) {
            for (int j = 0; j < n; j++) {
                for (int i = 0; i < m; i++) {
                    matrix.set(i, j, get(i, j) / scale[j]);
                }
            }
        } else if (scale == null) {
            for (int j = 0; j < n; j++) {
                for (int i = 0; i < m; i++) {
                    matrix.set(i, j, get(i, j) - center[j]);
                }
            }
        } else {
            for (int j = 0; j < n; j++) {
                for (int i = 0; i < m; i++) {
                    matrix.set(i, j, (get(i, j) - center[j]) / scale[j]);
                }
            }
        }

        return matrix;
    }






 ////////////////// MEAN



@Override
    public double p(double x) {
        double e = Math.exp(-(x - mu) / scale);
        return e / (scale * (1.0 + e) * (1.0 + e));
    }

    @Override
    public double logp(double x) {
        return Math.log(p(x));
    }

    @Override
    public double cdf(double x) {
        double e = Math.exp(-(x - mu) / scale);
        return 1.0 / (1.0 + e);
    }

    @Override
    public double quantile(double p) {
        if (p < 0.0 || p > 1.0) {
            throw new IllegalArgumentException();
        }

        return mu + scale * Math.log(p / (1.0 - p));
    }


 ////////////////// MEAN


 /**
     * Replaces NaN values with x.
     * @param x the value.
     * @return this object.
     */
    public Array2D replaceNaN(double x) {
        for (int i = 0; i < A.length; i++) {
            if (Double.isNaN(A[i])) {
                A[i] = x;
            }
        }

        return this;
    }




 ////////////////// MEAN



/**
 * Interface to impute missing values in the data.
 *
 * @author Haifeng
 */
public interface MissingValueImputation {
    /**
     * Impute missing values in the data.
     * @param data a data set with missing values (represented as Double.NaN).
     * On output, missing values are filled with estimated values.
     * @throws MissingValueImputationException when fails to impute the data.
     */
    void impute(double[][] data) throws MissingValueImputationException;

    /**
     * Impute the missing values with column averages.
     * @param data data with missing values.
     */
    static void imputeWithColumnAverage(double[][] data) {
        for (int j = 0; j < data[0].length; j++) {
            int n = 0;
            double sum = 0.0;

            for (double[] x : data) {
                if (!Double.isNaN(x[j])) {
                    n++;
                    sum += x[j];
                }
            }

            if (n == 0) {
                continue;
            }

            if (n < data.length) {
                double avg = sum / n;
                for (int i = 0; i < data.length; i++) {
                    if (Double.isNaN(data[i][j])) {
                        data[i][j] = avg;
                    }
                }
            }
        }
    }
}



 ////////////////// MEAN


public class AverageImputation implements MissingValueImputation {
    /**
     * Constructor.
     */
    public AverageImputation() {

    }

    @Override
    public void impute(double[][] data) throws MissingValueImputationException {
        for (int i = 0; i < data.length; i++) {
            int n = 0;
            double sum = 0.0;

            for (double x : data[i]) {
                if (!Double.isNaN(x)) {
                    n++;
                    sum += x;
                }
            }

            if (n == 0) {
                throw new MissingValueImputationException("The whole row " + i + " is missing");
            }

            if (n < data[i].length) {
                double avg = sum / n;
                for (int j = 0; j < data[i].length; j++) {
                    if (Double.isNaN(data[i][j])) {
                        data[i][j] = avg;
                    }
                }
            }
        }
    }
}




 ////////////////// MEAN


public class KNNImputation implements MissingValueImputation {

    /**
     * The number of neighbors used for imputation.
     */
    private final int k;

    /**
     * Constructor.
     * @param k the number of neighbors used for imputation.
     */
    public KNNImputation(int k) {
        if (k < 1) {
            throw new IllegalArgumentException("Invalid number of nearest neighbors for imputation: " + k);
        }

        this.k = k;
    }

    @Override
    public void impute(double[][] data) throws MissingValueImputationException {
        int[] count = new int[data[0].length];
        for (int i = 0; i < data.length; i++) {
            int n = 0;
            for (int j = 0; j < data[i].length; j++) {
                if (Double.isNaN(data[i][j])) {
                    n++;
                    count[j]++;
                }
            }

            if (n == data[i].length) {
                throw new MissingValueImputationException("The whole row " + i + " is missing");
            }
        }

        for (int i = 0; i < data[0].length; i++) {
            if (count[i] == data.length) {
                throw new MissingValueImputationException("The whole column " + i + " is missing");
            }
        }

        double[] dist = new double[data.length];

        for (double[] x : data) {
            int missing = 0;
            for (double v : x) {
                if (Double.isNaN(v)) {
                    missing++;
                }
            }

            if (missing == 0)
                continue;

            for (int j = 0; j < data.length; j++) {
                double[] y = data[j];
                int n = 0;
                dist[j] = 0;
                for (int m = 0; m < x.length; m++) {
                    if (!Double.isNaN(x[m]) && !Double.isNaN(y[m])) {
                        n++;
                        double d = x[m] - y[m];
                        dist[j] += d * d;
                    }
                }

                if (n > (x.length - missing) / 2) {
                    dist[j] = x.length * dist[j] / n;
                } else {
                    dist[j] = Double.MAX_VALUE;
                }
            }

            double[][] dat = new double[data.length][];
            System.arraycopy(data, 0, dat, 0, data.length);

            QuickSort.sort(dist, dat);

            for (int j = 0; j < x.length; j++) {
                if (Double.isNaN(x[j])) {
                    x[j] = 0;
                    int n = 0;
                    for (int m = 0; n < k && m < dat.length; m++) {
                        if (!Double.isNaN(dat[m][j])) {
                            x[j] += dat[m][j];
                            n++;
                        }
                    }

                    x[j] /= n;
                }
            }
        }
    }
}




 ////////////////// MEAN


package jsat.clustering;

import java.util.*;
import jsat.DataSet;
import jsat.SimpleDataSet;
import jsat.classifiers.CategoricalData;
import jsat.classifiers.DataPoint;
import jsat.clustering.evaluation.IntraClusterSumEvaluation;
import jsat.clustering.evaluation.intra.SumOfSqrdPairwiseDistances;
import jsat.clustering.kmeans.HamerlyKMeans;
import jsat.clustering.kmeans.KMeans;
import jsat.linear.*;
import jsat.linear.distancemetrics.DistanceMetric;
import jsat.linear.distancemetrics.EuclideanDistance;
import jsat.math.OnLineStatistics;
import jsat.parameters.Parameter.ParameterHolder;
import jsat.parameters.Parameterized;
import jsat.utils.random.RandomUtil;

/**
 * This class implements a method for estimating the number of clusters in a 
 * data set called the Gap Statistic. It works by sampling new datasets from a 
 * uniform random space, and comparing the sum of squared pairwise distances 
 * between the sampled data and the real data. The number of samples has a 
 * significant impact on runtime, and is controlled via {@link #setSamples(int) 
 * }. <br>
 * The Gap method can be applied to any distance metric and any clustering 
 * algorithm. However, it is significantly faster for the 
 * {@link EuclideanDistance} and was developed with the {@link KMeans} 
 * algorithm. Thus that combination is the default when using the no argument
 * constructor. <br>
 * <br>
 * A slight deviation in the implementation from the original paper exists. The 
 * original paper specifies that the smallest {@code K} satisfying 
 * {@link #getGap() Gap}(K) &ge; Gap(K+1) - {@link #getElogWkStndDev() sd}(K+1)
 * what the value of {@code K} to use. Instead the condition used is the 
 * smallest {@code K} such that Gap(K) &ge; Gap(K+1)- sd(K+1) and Gap(K) &gt; 0.
 * <br>
 * In addition, if no value of {@code K} satisfies the condition, the largest
 * value of Gap(K) will be used. <br>
 * <br>
 * Note, by default this implementation uses a heuristic for the max value of 
 * {@code K} that is capped at 100 when using the 
 * {@link #cluster(jsat.DataSet) } type methods.<br>
 * Note: when called with the desired number of clusters, the result of the base
 * clustering algorithm be returned directly. <br>
 * <br>
 * See: Tibshirani, R., Walther, G.,&amp;Hastie, T. (2001). <i>Estimating the 
 * number of clusters in a data set via the gap statistic</i>. Journal of the 
 * Royal Statistical Society: Series B (Statistical Methodology), 63(2), 
 * 411–423. doi:10.1111/1467-9868.00293
 * 
 * @author Edward Raff
 */
public class GapStatistic extends KClustererBase implements Parameterized
{

    private static final long serialVersionUID = 8893929177942856618L;
    @ParameterHolder
    private KClusterer base;
    private int B;
    private DistanceMetric dm;
    private boolean PCSampling;
    
    private double[] ElogW;
    private double[] logW;
    private double[] gap;
    private double[] s_k;

    /**
     * Creates a new Gap clusterer using k-means as the base clustering algorithm
     */
    public GapStatistic()
    {
        this(new HamerlyKMeans());
    }

    /**
     * Creates a new Gap clusterer using the base clustering algorithm given. 
     * @param base the base clustering method to use for any individual number 
     * of clusters
     */
    public GapStatistic(KClusterer base)
    {
        this(base, false);
    }
            
    /**
     * Creates a new Gap clsuterer using the base clustering algorithm given. 
     * @param base the base clustering method to use for any individual number 
     * of clusters
     * @param PCSampling {@code true} if the Gap statistic should be computed 
     * from a PCA transformed space, or {@code false} to go with the uniform 
     * bounding hyper cube. 
     */
    public GapStatistic(KClusterer base, boolean PCSampling)
    {
        this(base, PCSampling, 10, new EuclideanDistance());
    }
    
    /**
     * Creates a new Gap clsuterer using the base clustering algorithm given. 
     * @param base the base clustering method to use for any individual number 
     * of clusters
     * @param PCSampling {@code true} if the Gap statistic should be computed 
     * from a PCA transformed space, or {@code false} to go with the uniform 
     * bounding hyper cube. 
     * @param B the number of datasets to sample
     * @param dm the distance metric to evaluate with
     */
    public GapStatistic(KClusterer base, boolean PCSampling, int B, DistanceMetric dm )
    {
        this.base = base;
        setSamples(B);
        setDistanceMetric(dm);
        setPCSampling(PCSampling);
    }

    /**
     * Copy constructor
     * @param toCopy the object to copy
     */
    public GapStatistic(GapStatistic toCopy)
    {
        this.base = toCopy.base.clone();
        this.B = toCopy.B;
        this.dm = toCopy.dm.clone();
        this.PCSampling = toCopy.PCSampling;
        if(toCopy.ElogW != null)
            this.ElogW = Arrays.copyOf(toCopy.ElogW, toCopy.ElogW.length);
        if(toCopy.logW != null)
            this.logW = Arrays.copyOf(toCopy.logW, toCopy.logW.length);
        if(toCopy.gap != null)
            this.gap = Arrays.copyOf(toCopy.gap, toCopy.gap.length);
        if(toCopy.s_k != null)
            this.s_k = Arrays.copyOf(toCopy.s_k, toCopy.s_k.length);
    }
    
    

    /**
     * Sets the distance metric to use when evaluating a clustering algorithm
     * @param dm the distance metric to use
     */
    public void setDistanceMetric(DistanceMetric dm)
    {
        this.dm = dm;
    }

    /**
     * 
     * @return the distance metric used for evaluation
     */
    public DistanceMetric getDistanceMetric()
    {
        return dm;
    }

    /**
     * By default the null distribution is sampled from the bounding hyper-cube 
     * of the dataset. The accuracy of the sampling can be made more accurate 
     * (and invariant) by sampling the null distribution based on the principal 
     * components of the dataset. This will also increase the runtime of the
     * algorithm. 
     * @param PCSampling {@code true} to sample from the projected data, {@code 
     * false} to do the default and sample from the bounding hyper-cube. 
     */
    public void setPCSampling(boolean PCSampling)
    {
        this.PCSampling = PCSampling;
    }

    /**
     * 
     * @return {@code true} to sample from the projected data, {@code 
     * false} to do the default and sample from the bounding hyper-cube.
     */
    public boolean isPCSampling()
    {
        return PCSampling;
    }
    
    /**
     * The Gap statistic is measured by sampling from a reference distribution 
     * and comparing with the given data set. This controls the number of sample
     * datasets to draw and evaluate. 
     * 
     * @param B the number of data sets to sample
     */
    public void setSamples(int B)
    {
        if(B <= 0)
            throw new IllegalArgumentException("sample size must be positive, not " + B);
        this.B = B;
    }

    /**
     * 
     * @return the number of data sets sampled 
     */
    public int getSamples()
    {
        return B;
    }

    /**
     * Returns the array of gap statistic values. Index {@code i} of the 
     * returned array indicates the gap score for using {@code i+1} clusters. A
     * value of {@link Double#NaN} if the score was not computed for that value 
     * of  {@code K}
     * @return the array of gap statistic values computed, or {@code null} if 
     * the algorithm hasn't been run yet. 
     */
    public double[] getGap()
    {
        return gap;
    }

    /**
     * Returns the array of empirical <i>log(W<sub>k</sub>)</i> scores computed 
     * from the data set last clustered. <br>
     * Index {@code i} of the returned array indicates the gap score for using
     * {@code i+1} clusters. A value of {@link Double#NaN} if the score was not 
     * computed for that value of {@code K}
     * @return the array of empirical scores from the last run, or {@code null}
     * if the algorithm hasn't been run yet
     */
    public double[] getLogW()
    {
        return logW;
    }

    /**
     * Returns the array of expected <i>E[log(W<sub>k</sub>)]</i> scores 
     * computed from sampling new data sets. <br>
     * Index {@code i} of the returned array indicates the gap score for using
     * {@code i+1} clusters. A value of {@link Double#NaN} if the score was not 
     * computed for that value of {@code K}
     * @return the array of sampled expected scores from the last run, or 
     * {@code null} if the algorithm hasn't been run yet
     */
    public double[] getElogW()
    {
        return ElogW;
    }

    /**
     * Returns the array of standard deviations from the samplings used to compute
     * {@link #getElogWkStndDev() }, multiplied by <i>sqrt(1+1/B)</i>. <br>
     * Index {@code i} of the returned array indicates the gap score for using
     * {@code i+1} clusters. A value of {@link Double#NaN} if the score was not 
     * computed for that value of {@code K}
     * @return the array of standard deviations from the last run, or 
     * {@code null} if the algorithm hasn't been run yet
     */
    public double[] getElogWkStndDev()
    {
        return s_k;
    }

    @Override
    public int[] cluster(DataSet dataSet, boolean parallel, int[] designations)
    {
        return cluster(dataSet, 1, (int) Math.min(Math.max(Math.sqrt(dataSet.size()), 10), 100), parallel, designations);
    }

    @Override
    public int[] cluster(DataSet dataSet, int clusters, boolean parallel, int[] designations)
    {
        return base.cluster(dataSet, clusters, parallel, designations);
    }

    @Override
    public int[] cluster(DataSet dataSet, int lowK, int highK, boolean parallel, int[] designations)
    {
        final int D = dataSet.getNumNumericalVars();
        final int N = dataSet.size();
        
        if(designations == null || designations.length < N)
            designations = new int[N];
        //TODO we dont need all values in [1, lowK-1)  in order to get the gap statistic for [lowK, highK]. So lets not do that extra work. 
        
        logW = new double[highK-1];
        ElogW = new double[highK-1];
        gap = new double[highK-1];
        s_k = new double[highK-1];
        
        IntraClusterSumEvaluation ssd = new IntraClusterSumEvaluation(new SumOfSqrdPairwiseDistances(dm));
        
        //Step 1: Cluster the observed data
        Arrays.fill(designations, 0);
        logW[0] = Math.log(ssd.evaluate(designations, dataSet));//base case
        for(int k = 2; k < highK; k++)
        {
            designations = base.cluster(dataSet, k, parallel, designations);
            logW[k-1] = Math.log(ssd.evaluate(designations, dataSet));
        }
        //Step 2: 
        //use online statistics and run through all K for each B, so that we minimize the memory use
        OnLineStatistics[] expected = new OnLineStatistics[highK-1];
        for(int i = 0; i < expected.length; i++)
            expected[i] = new OnLineStatistics();
        
        //dataset object we will reuse
        SimpleDataSet Xp = new SimpleDataSet(D, new CategoricalData[0]);
        for(int i = 0; i < N; i++)
            Xp.add(new DataPoint(new DenseVector(D)));
        
        Random rand = RandomUtil.getRandom();
        
        //info needed for sampling
        //min/max for each row/col to smaple uniformly from
        double[] min = new double[D];
        double[] max = new double[D];
        Arrays.fill(min, Double.POSITIVE_INFINITY);
        Arrays.fill(max, Double.NEGATIVE_INFINITY);
        final Matrix V_T;//the V^T from [U, D, V] of SVD decomposation
        if(PCSampling)
        {
            SingularValueDecomposition svd = new SingularValueDecomposition(dataSet.getDataMatrix());
            //X' = X V , from generation strategy (b)
            Matrix tmp = dataSet.getDataMatrixView().multiply(svd.getV());
            
            for(int i = 0; i < tmp.rows(); i++)
                for(int j = 0; j < tmp.cols(); j++)
                {
                    min[j] = Math.min(tmp.get(i, j), min[j]);
                    max[j] = Math.max(tmp.get(i, j), max[j]);
                }
            V_T = svd.getV().transpose();
        }
        else
        {
            V_T = null;
            OnLineStatistics[] columnStats = dataSet.getOnlineColumnStats(false);
            for(int i = 0; i < D; i++)
            {
                min[i] = columnStats[i].getMin();
                max[i] = columnStats[i].getMax();
            }
        }
        
        //generate B reference datasets
        for(int b = 0; b < B; b++)
        {
            for (int i = 0; i < N; i++)//sample
            {
                Vec xp = Xp.getDataPoint(i).getNumericalValues();
                for (int j = 0; j < D; j++)
                    xp.set(j, (max[j] - min[j]) * rand.nextDouble() + min[j]);
            }
            
            if(PCSampling)//project if wanted
            {
                //Finally we back-transform via Z = Z' V^T to give reference data Z
                //TODO batch as a matrix matrix op would be faster, but use more memory
                Vec tmp = new DenseVector(D);
                for (int i = 0; i < N; i++)
                {
                    Vec xp = Xp.getDataPoint(i).getNumericalValues();
                    tmp.zeroOut();
                    xp.multiply(V_T, tmp);
                    tmp.copyTo(xp);
                }
            }
            
            //cluster each one
            Arrays.fill(designations, 0);
            expected[0].add(Math.log(ssd.evaluate(designations, Xp)));//base case
            for(int k = 2; k < highK; k++)
            {
                designations = base.cluster(Xp, k, parallel, designations);
                expected[k-1].add(Math.log(ssd.evaluate(designations, Xp)));
            }
        }
        
        //go through and copmute gap
        int k_first = -1;
        int biggestGap = 0;//used as a fall back incase the original condition can't be satisfied in the specified range
        for (int i = 0; i < gap.length; i++)
        {
            gap[i] = (ElogW[i] = expected[i].getMean()) - logW[i];
            s_k[i] = expected[i].getStandardDeviation() * Math.sqrt(1 + 1.0 / B);
            //check original condition first
            int k = i + 1;
            if (i > 0 && lowK <= k && k <= highK)
                if (k_first == -1 && gap[i - 1] >= gap[i] - s_k[i] && gap[i-1] > 0)
                    k_first = k - 1;
            //check backup
            if(gap[i] > biggestGap && lowK <= k && k <= highK)
                biggestGap = i;
        }

        if(k_first == -1)//never satisfied our conditions?
            k_first = biggestGap+1;//Maybe we should go back and pick the best gap k we can find?
        if(k_first == 1)//easy case
        {
            Arrays.fill(designations, 0);
            return designations;
        }
        
        return base.cluster(dataSet, k_first, parallel, designations);
    }

    @Override
    public GapStatistic clone()
    {
        return new GapStatistic(this);
    }
}




 ////////////////// MEAN






 ////////////////// MEAN






 ////////////////// MEAN






 ////////////////// MEAN






 ////////////////// MEAN






 ////////////////// MEAN






 ////////////////// MEAN




























































