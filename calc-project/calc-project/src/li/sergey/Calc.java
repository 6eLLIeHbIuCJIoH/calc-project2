package li.sergey;

import java.io.*;
import java.util.*;


public class Calc {
    public static void main(String[] args) {
        BufferedReader sp = new BufferedReader(new InputStreamReader(System.in));
        String num;

        try {
            while (true) {
            System.out.println("Введите выражение Стандартные символы +,-,*,/ и скобки. Есть поддержка десятичных знаков.");
                num = sp.readLine();
                num = opn(num);
                calculate(num);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private static String opn(String num) throws Exception {
        StringBuilder sbStack = new StringBuilder(), sbOut = new StringBuilder();
        char pr, pTemp;

        for (int i = 0; i < num.length(); i++) {
            pr = num.charAt(i);
            if (isOp(pr)) {
                while (sbStack.length() > 0) {
                    pTemp = sbStack.substring(sbStack.length()-1).charAt(0);
                    if (isOp(pTemp) && (opPrior(pr) <= opPrior(pTemp))) {
                        sbOut.append(" ").append(pTemp).append(" ");
                        sbStack.setLength(sbStack.length()-1);
                    } else {
                        sbOut.append(" ");
                        break;
                    }
                }
                sbOut.append(" ");
                sbStack.append(pr);
            } else if ('(' == pr) {
                sbStack.append(pr);
            } else if (')' == pr) {
                pTemp = sbStack.substring(sbStack.length()-1).charAt(0);
                while ('(' != pTemp) {
                    if (sbStack.length() < 1) {
                        throw new Exception("Ошибка разбора скобок!");
                    }
                    sbOut.append(" ").append(pTemp);
                    sbStack.setLength(sbStack.length()-1);
                    pTemp = sbStack.substring(sbStack.length()-1).charAt(0);
                }
                sbStack.setLength(sbStack.length()-1);
            } else {
                sbOut.append(pr);
            }
        }

        while (sbStack.length() > 0) {
            sbOut.append(" ").append(sbStack.substring(sbStack.length()-1));
            sbStack.setLength(sbStack.length()-1);
        }

        return  sbOut.toString();
    }


    private static boolean isOp(char c) {
        return switch (c) {
            case '-', '+', '*', '/' -> true;
            default -> false;
        };
    }

    private static byte opPrior(char op) {
        switch (op) {
            case '*':
            case '/':
                return 2;
        }
        return 1;
    }


    private static void calculate(String num) throws Exception {
        double vrl1 = 0, vrl2 = 0;
        String sTmp;
        Deque<Double> stack = new ArrayDeque<>();
        StringTokenizer st = new StringTokenizer(num);
        while(st.hasMoreTokens()) {
            try {
                sTmp = st.nextToken().trim();
                if (1 == sTmp.length() && isOp(sTmp.charAt(0))) {
                    if (stack.size() < 2) {
                        throw new Exception("Неверное количество данных в стеке для операции!" + sTmp);
                    }
                    vrl2 = stack.pop();
                    vrl1 = stack.pop();
                    switch (sTmp.charAt(0)) {
                        case '+' -> vrl1 += vrl2;
                        case '-' -> vrl1 -= vrl2;
                        case '/' -> vrl1 /= vrl2;
                        case '*' -> vrl1 *= vrl2;
                        default -> throw new Exception("Недопустимая операция!" + sTmp);
                    }
                } else {
                    vrl1 = Double.parseDouble(sTmp);
                }

                stack.push(vrl1);
            } catch (Exception e) {
                throw new Exception("Введено незаконченное выражение!");
            }
        }

        if (stack.size() > 1) {
            throw new Exception("Количество операторов не соответствует количеству операндов!");
        }

        double varDbl = vrl1;
        System.out.println("Ответ: " + vrl1);
        double radians1 = Math.toRadians(varDbl);

        System.out.format("Косинус %.2f = %.2f°%n", //Косинус в градусах
                varDbl, Math.cos(radians1));
        System.out.format("Арккосинус %.2f = %.2f%n", //Косинус в радианах (арккосинус)
                varDbl, Math.acos(radians1));

        System.out.format("Синус %.2f = %.2f°%n", //Синус в градусах
                varDbl, Math.sin(radians1));
        System.out.format("Арксинус %.2f = %.2f%n", //Синус в радианах(арксинус)
                varDbl, Math.asin(radians1));

        System.out.format("Тангенс %.2f = %.2f°%n",//Тангенс в градусах
                varDbl, Math.tan(radians1));
        System.out.format("Арктангенс %.2f = %.2f%n",//Тангенс в радианах
                varDbl, Math.atan(radians1));

        System.out.format("Логарифм натуральный %.2f = %.2f%n",
                varDbl, Math.log(radians1));


        //return "";

    }

}


