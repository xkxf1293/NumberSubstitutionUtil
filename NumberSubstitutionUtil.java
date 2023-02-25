import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberSubstitutionUtil {
    public static int transferChineseNumber2ArabNumber(String chineseNumber) {
        String aval = "零一二三四五六七八九";
        String bval = "十百千万亿";
        int[] bnum = {10, 100, 1000, 10000, 100000000};
        int num = 0;
        char[] arr = chineseNumber.toCharArray();
        int len = arr.length;
        Stack<Integer> stack = new Stack<Integer>();
        for (int i = 0; i < len; i++) {
            char s = arr[i];
            //跳过零
            if(s == '零'){
                continue;
            }
            //用下标找到对应数字
            int index = bval.indexOf(s);
            //如果不在bval中，即当前字符为数字，直接入栈
            if(index == -1){
                stack.push(aval.indexOf(s));
            }else{ //当前字符为单位。
                int tempsum = 0;
                int val = bnum[index];
                //如果栈为空则直接入栈
                if(stack.isEmpty()){
                    stack.push(val);
                    continue;
                }
                //如果栈中有比val小的元素则出栈，累加，乘N，再入栈
                while(!stack.isEmpty() && stack.peek() < val){
                    tempsum += stack.pop();
                }
                //判断是否经过乘法处理
                if(tempsum == 0){
                    stack.push(val);
                }else{
                    stack.push(tempsum * val);
                }
            }
        }
        //计算最终的和
        while(!stack.isEmpty()){
            num += stack.pop();
        }
        return num;
    }

    public static String outputArabNumberString(String chineseNumberString){
        String reg = "[零一二三四五六七八九十百千万亿]+";
		
		System.out.println(chineseNumberString);
		
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(chineseNumberString);
        List<String> chineseNumbers = new ArrayList<>(16);
        List<Integer> arabNumbers = new ArrayList<>(16);
        boolean isNumberFirst = false;
        while(matcher.find()){
            chineseNumbers.add(matcher.group());
            // 转化成阿拉伯数字
            int arabNum = NumberSubstitutionUtil.transferChineseNumber2ArabNumber(matcher.group());
			
			System.out.println(arabNum);
			
            arabNumbers.add(arabNum);
        }
        String[] arrStr = chineseNumberString.split(reg);

        StringBuilder transferedNumber = new StringBuilder();
        // 数字的数量不大于文字的数量。
        // 如果结尾不是数字，则文字数量比数字数量多一（即使数字位于第一个也不会有影响）；如果结尾是数字，则数字文本数量相等。
        for(int i =0 ;i<arrStr.length;i++){
            // 先拼文字再拼数字，即使数字在第一个也会存在一个空的字符串（""），先拼也不会有影响
            transferedNumber.append(arrStr[i]);
			
			//System.out.println(i);
			//System.out.println(arrStr[i]);
			//System.out.println(arabNumbers.size());
			
            if (i < arabNumbers.size()) {
				//System.out.println("i="+i);
				//System.out.println("arabNumbers.size()="+arabNumbers.size());
				if (arabNumbers.get(i) < 10){
					//数值小于10的前缀加0两位显示
					transferedNumber.append("0"+arabNumbers.get(i));
				}else{
					transferedNumber.append(arabNumbers.get(i));
				}
				//System.out.println("transferedNumber="+transferedNumber);
            }
        }
        return transferedNumber.toString();
    }
	public static void main(String[] args) {
        // 测试代码
		System.out.println(outputArabNumberString(args[0]));
    }
}