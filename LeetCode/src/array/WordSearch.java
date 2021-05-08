package array;

/**
 * https://leetcode-cn.com/problems/word-search/
 */
public class WordSearch {
    /**
     * 回溯算法，其实类似N皇后的问题，都是此路不通则返回上一状态并继续尝试。
     * @param board 数组
     * @param word 目标值
     * @return 是否存在
     */
    public static boolean exist(char[][] board, String word) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (backTrace(board, word,0, i, j)){//从二维表格的每一个格子出发
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean backTrace(char[][] board, String word, int wordIndex, int x, int y){
        if (board[x][y] != word.charAt(wordIndex)){//当前位的字母不相同，此路不通，直接返回false
            return false;
        }
        if (word.length() - 1 == wordIndex){//最后一个字母也相同,返回成功
            return true;
        }
        char tmp = board[x][y]; //记录当前字符
        board[x][y] = 0; //避免重复使用
        wordIndex++;
        if (x > 0 && backTrace(board, word, wordIndex, x - 1, y)//往上走
        || y > 0 && backTrace(board, word, wordIndex, x, y - 1)//往左走
        || x < board.length - 1 && backTrace(board, word, wordIndex, x + 1, y)//往下走
        || y < board[0].length - 1 && backTrace(board, word, wordIndex, x, y+1)){//往右走
            return  true; // 其中一条能走通，就算成功
        }
        board[x][y] = tmp;//都不通，返回上一状态
        return false;
    }

    public static void main(String[] args) {
        char[][] board = new char[][]{
                {'A','B'}
        };
        System.out.println(exist(board, "AB"));
    }
}
