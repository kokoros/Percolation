import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/* ****
 * Name:koro
 * Date:2020.5.26
 * Description:1.Union-Find
 */

public class PercolationStats {

    private final int trials;
    private final double[] openNumList;
    // 平均值
    private double meanOpenNum;
    // 标准差
    private double standardDev;
    private final double confidenceNum;


    // perform independent trials on an n-by-n grid
    // trials是实验的次数 n是网格数
    public PercolationStats(int n, int trials) {
        // 如果n<0
        if (n <= 0) throw new IllegalArgumentException("illegal value of n!");

        // 如果trials<0
        if (trials <= 0) throw new IllegalArgumentException("illegal value of trials!");
        this.trials = trials;

        confidenceNum = 1.96;

        // 记录每次试验随机格的数量
        openNumList = new double[trials];
        // 做一次次试验
        for (int i = 0; i < trials; i++) {
            // 做一次实验
            Percolation percolation = new Percolation(n);
            int openNum = 0;
            while (true) {
                int randomRow = StdRandom.uniform(1, n + 1);
                int randomCol = StdRandom.uniform(1, n + 1);
                // System.out.println(randomRow + " " + randomCol);

                // 如果此格没有开
                if (!percolation.isOpen(randomRow, randomCol)) {
                    // 开一个随机格
                    percolation.open(randomRow, randomCol);
                    openNum += 1;
                    // 查看是否可渗透
                    if (percolation.percolates()) {
                        // System.out.println(openNum);
                        // 记录总共打开的随机格的数量占所有格子数量的分数
                        openNumList[i] = (double) openNum / (double) (n * n);
                        // System.out.println(openNumList);

                        // 实验结束
                        break;
                    }
                }


            }
        }


    }


    // 渗透阈值的样本均值
    public double mean() {
        // 求平均数
        meanOpenNum = StdStats.mean(openNumList);
        return meanOpenNum;
    }

    // 求渗透阈值的样本标准差
    public double stddev() {
        // 获取标准差
        standardDev = StdStats.stddev(openNumList);
        return standardDev;
    }

    // 95%置信区间的5%点
    public double confidenceLo() {
        // 如果标准差还没求取
        if (standardDev == 0.0) {
            this.stddev();
        }
        if (meanOpenNum == 0.0) {
            this.mean();
        }
        return meanOpenNum - confidenceNum * (standardDev / Math.sqrt(trials));
    }

    // 95%置信区间的95%点
    public double confidenceHi() {
        // 如果标准差还没求取
        if (standardDev == 0.0) {
            this.stddev();
        }
        if (meanOpenNum == 0.0) {
            this.mean();
        }
        return meanOpenNum + confidenceNum * (standardDev / Math.sqrt(trials));
    }

    // test 接收2个命令行参数
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
//        System.out.println(n);
//        System.out.println(trials);
        PercolationStats precolationstats = new PercolationStats(n, trials);
        // 获取样本均值
        double meanOpenNum = precolationstats.mean();
        System.out.println("mean=" + meanOpenNum);
        // 获取样本标准差
        double standardDev = precolationstats.stddev();
        System.out.println("stddev=" + standardDev);
        // 置信区间
        double lowConfidence = precolationstats.confidenceLo();
        double heightConfidence = precolationstats.confidenceHi();
        System.out.println("95% confidence interval = [ " + lowConfidence + ", " + heightConfidence + "]");

    }

}
