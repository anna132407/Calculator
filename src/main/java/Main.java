import java.math.BigDecimal;
import java.util.Scanner;
import java.util.Stack;

public class Main {

    public static void main(String[] args) {
        for(;;) {
            System.out.println("Введите пример");
            Scanner scanner = new Scanner(System.in);
            String item = scanner.nextLine();
            Stack stack = new Stack();
            Stack stack1 = new Stack();
            boolean key = false;
            if (!pi(item)) {
                System.out.println("Некорректное количество скобок, попробуйте ещё раз");
                return;
            }
            for (int i = 0; i < item.length(); i++) {

                char c = item.charAt(i);
                while (!isfu(c) && !isNumber(c)) {
                    ++i;
                    if (i < item.length())
                        c = item.charAt(i);
                    if (i == item.length())
                        break;
                }
                if (i == item.length())
                    break;
                StringBuilder SB = new StringBuilder("");
                if (c == '-' && key) {
                    ++i;
                    if (i < item.length())
                        c = item.charAt(i);
                    SB.append('-');
                }
                while (isNumber(c) || c == '.') {
                    key = false;
                    SB.append(c);
                    if (++i >= item.length()) break;
                    c = item.charAt(i);
                }
                if (!SB.toString().equals("")) {
                    stack.push(new BigDecimal(SB.toString()));

                }
                if (c == '^') {

                    if ((!stack1.empty()))
                        if (((char) stack1.peek() == '^')) {
                            stack.push(stack1.pop());
                        }
                }
                if (c == '*' || c == '/' || c == '+' || c == '-') {

                    if ((!stack1.empty()))
                        if (((char) stack1.peek() == '*') || ((char) stack1.peek() == '/') || ((char) stack1.peek() == '^')) {
                            stack.push(stack1.pop());
                        }
                }
                if (c == '+' || c == '-') {
                    if ((!stack1.empty()))
                        if (((char) stack1.peek() == '*') || ((char) stack1.peek() == '/') || ((char) stack1.peek() == '+') || ((char) stack1.peek() == '-') || ((char) stack1.peek() == '^')) {
                            stack.push(stack1.pop());
                        }
                }
                if (isfu(c)) {
                    stack1.push(c);
                    key = true;
                }
                if (c == ')') {
                    char cha = c;
                    while (cha != '(') {
                        cha = (char) stack1.pop();
                        if (cha != '(' && cha != ')')
                            stack.push(cha);
                    }
                }
            }
            while (!stack1.empty()) {
                stack.push(stack1.pop());
            }
            Stack stack2 = new Stack<>();

            while (!stack.empty()) {
                stack2.push(stack.pop());
            }
            while (!stack2.empty()) {
                Object c = stack2.pop();
                if (c.getClass() == BigDecimal.class) {
                    stack1.push((BigDecimal) c);
                    continue;
                } else {
                    if ((char) c == '*' && (stack1.size() != 1))
                        stack1.push(((BigDecimal) stack1.pop()).multiply((BigDecimal) stack1.pop()));
                    if ((char) c == '^' && (stack1.size() != 1)) {
                        BigDecimal a = (BigDecimal) stack1.pop();
                        if (a.intValue() >= 1)
                            stack1.push(((BigDecimal) stack1.pop()).pow(a.intValue()));
                        else {
                            stack1.push(new BigDecimal(Math.pow(((BigDecimal) (stack1.pop())).doubleValue(), a.doubleValue())));
                        }
                    }
                    if ((char) c == '/' && (stack1.size() != 1)) {
                        Double a = ((BigDecimal) stack1.pop()).doubleValue();
                        stack1.push(new BigDecimal(((Double) (((BigDecimal) stack1.pop()).doubleValue() / a)).toString()));
                    }
                    if ((char) c == '+' && (stack1.size() != 1))
                        stack1.push(((BigDecimal) stack1.pop()).add((BigDecimal) stack1.pop()));
                    if ((char) c == '-') {
                        BigDecimal a = (BigDecimal) stack1.pop();
                        if (!stack1.empty() && stack1.peek().getClass() != char.class) {
                            stack1.push(((BigDecimal) stack1.pop()).subtract(a));
                        } else
                            stack1.push((a).multiply(new BigDecimal(-1)));
                    }
                }
            }
            System.out.printf("%.6s \n", stack1.pop());
            System.out.println("Для завершения нажмите q. Для продолжения нажмите любую клавишу");
            String a = scanner.nextLine();
            char i = a.charAt(0);
            if (i == 'q') break;
        }
        }


        public static boolean isNumber ( char c)
        {
            if (c >= '0' && c <= '9')
                return true;
            else
                return false;
        }
        public static boolean isfu ( char c)
        {
            if (c == '+' || c == '-' || c == '*' || c == '/' || c == '(' || c == '^')
                return true;
            else return false;
        }

        public static boolean pi (String string)
        {
            Stack<Character> stack = new Stack<Character>();
            for (int i = 0; i < string.length(); i++) {

                if (string.charAt(i) == '(')
                    stack.push('(');
                if (string.charAt(i) == ')')
                    if (stack.empty() || stack.pop() != '(')
                        return false;
            }
            if (!stack.empty())
                return false;
            return true;
        }

    }




