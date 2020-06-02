/* *****************************************************************************
 *  Name:koro
 *  Date:2020.5.25
 *  Description:1.Union-Find
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // n*n grid
    private final int n;
    // 各个格子是否和顶虚拟点和底部虚拟点连接
    private boolean[] numArray;
    // 顶部和底部虚拟点连接
    private final WeightedQuickUnionUF quickUnionNum;
    // 只有顶部虚拟点连接
    private final WeightedQuickUnionUF quickUnionTop;

    //开放的格子数
    private int allOpenNum;


    // creates n-by-n grid,with all sites initially blocked
    public Percolation(int n) {
        // 如果n<0
        if (n <= 0) throw new IllegalArgumentException("illegal value of n!");
        // 改变对象中先定义的n
        this.n = n;
        // 创造一个长度为n*n+2的数组 2为虚拟的两个点
        numArray = new boolean[n * n + 2];


        for (int i = 0; i < numArray.length; i++) {
            numArray[i] = false;
        }
        numArray[0] = true;
        numArray[n * n + 1] = true;

        allOpenNum = 0;
        // 实例化
        quickUnionNum = new WeightedQuickUnionUF(n * n + 2);
        quickUnionTop = new WeightedQuickUnionUF(n * n + 1);


    }

    // opens the site(row,col) if it's not open already
    public void open(int row, int col) {
        // 验证
        validate(row, col);

        if (!isOpen(row, col)) {
            // 获取索引
            int currentIndex = currentIndex(row, col);
            // 开放此格
            numArray[currentIndex] = true;
            // 记录开放的格数
            allOpenNum += 1;


            // 是否有上格 有就连接
            if (row != 1) {
                int topIndex = currentIndex - n;
                // 查看它是否开放
                boolean isOpenReturnCode = isOpen(row - 1, col);
                if (isOpenReturnCode) {
                    // 连接
                    quickUnionNum.union(currentIndex, topIndex);
                    quickUnionTop.union(currentIndex, topIndex);


                }
                // 如果是第一行,就证明它可以和top相连接
            } else {
                quickUnionNum.union(currentIndex, 0);
                quickUnionTop.union(currentIndex, 0);
            }


            // 是否有下格
            if (row != n) {
                int bottomIndex = currentIndex + n;
                // 查看它是否开放
                boolean isOpenReturnCode = isOpen(row + 1, col);
                if (isOpenReturnCode) {
                    // 连接
                    quickUnionNum.union(currentIndex, bottomIndex);
                    quickUnionTop.union(currentIndex, bottomIndex);


                }
                // 如果是最后一行,就证明它可以和bot相连接
            } else {
                quickUnionNum.union(currentIndex, n * n + 1);
            }
            // 是否有左格
            if (col != 1) {
                int leftIndex = currentIndex - 1;
                // 查看它是否开放
                boolean isOpenReturnCode = isOpen(row, col - 1);
                if (isOpenReturnCode) {
                    // 连接
                    quickUnionNum.union(currentIndex, leftIndex);
                    quickUnionTop.union(currentIndex, leftIndex);


                }
            }
            // 是否有右格
            if (col != n) {
                int rightIndex = currentIndex + 1;
                // 查看它是否开放
                boolean isOpenReturnCode = isOpen(row, col + 1);
                if (isOpenReturnCode) {
                    // 连接
                    quickUnionNum.union(currentIndex, rightIndex);
                    quickUnionTop.union(currentIndex, rightIndex);


                }
            }


        }

    }


    // 获取当前索引
    private int currentIndex(int row, int col) {
        // 验证
        validate(row, col);
        return n * (row - 1) + col;
    }

    // 验证row col
    private void validate(int row, int col) {
        if (row < 1 || row > n) throw new IllegalArgumentException("illegal value of row!");
        if (col < 1 || col > n) throw new IllegalArgumentException("illegal value of col!");
    }

    // 查看此格是否开放
    public boolean isOpen(int row, int col) {
        // 验证
        validate(row, col);
        //获取索引
        int currentIndex = currentIndex(row, col);
        return numArray[currentIndex];
    }

    // 查看此格是否可被填满 注意backwash:如果已存在一条路让上下两个虚拟点相连的话,此时随意打开底部任意元素,由于打开底部元素后就会与底部虚拟点相连,此任意元素也会被判定为可被填满
    public boolean isFull(int row, int col) {
        // 验证
        validate(row, col);

        // 查看此格的根是否在topBum数组中是真
        return quickUnionTop.find(currentIndex(row, col)) == quickUnionTop.find(0);

    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return allOpenNum;
    }

    // does the system percolate?
    public boolean percolates() {
        // 查看系统是否可渗透
        return quickUnionNum.find(0) == quickUnionNum.find(n * n + 1);

    }


    public static void main(String[] args) {
        System.out.println("开始测试...");
        Percolation percolation = new Percolation(3);
//        boolean isOpenResult = percolation.isOpen(1, 1);
//        System.out.println(isOpenResult);
        percolation.open(1, 3);
        percolation.open(2, 3);
        percolation.open(3, 3);
        percolation.open(3, 1);
        percolation.open(2, 1);
//        percolation.open(1, 1);

//        System.out.println(percolation.isFull(3, 1));
//        System.out.println(percolation.isFull(2, 3));
        System.out.println(percolation.isFull(2, 1));


//        System.out.println("打开的总个数:" + percolation.numberOfOpenSites());
//        boolean open_result = percolation.isOpen(1, 1);
//        System.out.println(open_result);
//        boolean fullResul = percolation.isFull(3, 1);
//        System.out.println(fullResul);
//        // test
//        percolation.open(2, 2);
//        percolation.open(3, 1);
//        percolation.open(2, 1);
//        boolean result = percolation.percolates();
//        System.out.println(result);


    }
}

