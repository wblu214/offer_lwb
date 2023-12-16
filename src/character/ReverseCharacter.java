package character;

import java.util.Scanner;
/*
    反转字符串
    输入：君不见黄河之水天上来
    输出：来上天水之河黄见不君
 */
public class ReverseCharacter {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();

        StringBuilder sb = reverse(str);
        System.out.println(sb);
    }
    static StringBuilder reverse(String arr){
            int n = arr.length();
            //用StringBuffer编辑字符串
            StringBuilder sb  =new StringBuilder(arr);
        for (int i = 0,j = n-1; i < n/2; i++,j--) {
            char  temp = arr.charAt(i);
            sb.setCharAt(i,sb.charAt(j));
            sb.setCharAt(j,temp);
        }
        return sb;
    }
}