import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Interface {
    public static void main(String[] args) {
        ArrayList<User> list = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("欢迎来到学生管理系统主界面");
            System.out.println("1：登录");
            System.out.println("2：注册");
            System.out.println("3：忘记密码");
            System.out.println("4：退出");
            System.out.println("输入对应数字选择操作：");
            String choose = sc.next();
            switch (choose) {
                case "1" -> login(list);
                case "2" -> register(list);
                case "3" -> forgetPassword(list);
                case "4" -> {
                    System.out.println("退出");
                    System.exit(0);
                }
                default -> System.out.println("输入错误");

            }
        }
    }

    //忘记密码
    private static void forgetPassword(ArrayList<User> list) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入用户名：");
        String userName = sc.next();
        int index = getIndex(list, userName);
        if (!(index >= 0)) {//用户名不存在
            System.out.println("用户名不存在，请先注册！");
            return;
        }
        //键盘录入手机号码和身份证号码
        System.out.println("请输入身份证号：");
        String idCard = sc.next();
        System.out.println("请输入手机号码：");
        String phoneNumber = sc.next();
        int in = findUser(list, userName);
        User user = list.get(in);
        if (!(user.getIdCard().equals(idCard) && user.getPhoneNumber().equals(phoneNumber))) {
            System.out.println("身份证号或者手机号输入错误");
            return;
        }
        while (true) {
            System.out.println("请输入新密码：");
            String newPassword1 = sc.next();
            System.out.println("请再次输入新密码：");
            String newPassword2 = sc.next();
            if (newPassword1.equals(newPassword2)) {
                user.setPassword(newPassword1);
                System.out.println("修改密码成功");
                break;
            } else {
                System.out.println("两次密码输入不一致，请重新输入！");
            }
        }

    }

    //找到用户的用户名
    private static int findUser(ArrayList<User> list, String userName) {
        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);
            if (user.getUserName().equals(userName)) {
                return i;
            }
        }
        return -1;
    }

    //注册
    private static void register(ArrayList<User> list) {
        Scanner sc = new Scanner(System.in);
        //用户名
        String userName;
        while (true) {
            System.out.println("请输入用户名（提示：用户名长度在3~15之间，只能是字母加数字的组合，不能是纯数字）：");
            userName = sc.next();
            //先验证格式是否正确，再验证是否唯一。
            //因为在以后所有的数据，都存在数据库中，需要使用网络资源进行校验。
            //用户名长度在3~15之间，只能是字母加数字的组合，不能是纯数字。
            boolean flag = checkUserName(userName);

            if (!flag) {
                System.out.println("用户名格式错误！");
                continue;
            }
            //用户名格式满足条件
            //用户名唯一
            int index = getIndex(list, userName);
            if (index >= 0) {
                //用户名存在
                System.out.println("用户" + userName + "已存在");
            } else {
                //用户名不存在
                break;
            }
        }
        //密码
        String password;
        while (true) {
            System.out.println("请输入密码：");
            password = sc.next();
            System.out.println("请再次输入密码：");
            String againPassword = sc.next();
            if (!(password.equals(againPassword))) {
                System.out.println("两次密码不一致，请重新输入！");
            } else break;
        }
        //身份证号
        String idCard;
        while (true) {
            System.out.println("请输入18位身份证号（提示：长度为18，不能0开头，前十七位必须为数字，最后一位为数字或者X或者x）：");
            idCard = sc.next();
            boolean flag = checkIdCard(idCard);
            if (!flag) {
                System.out.println("身份证号码不合法");
            } else {
                //身份证号码满足要求
                break;
            }
        }
        //手机号码
        String phoneNumber;
        while (true) {
            System.out.println("请输入11位手机号码：");
            phoneNumber = sc.next();
            boolean flag = checkPhoneNumber(phoneNumber);
            if (!flag) {
                System.out.println("手机号码不正确！");
            } else {
                break;
            }
        }
        User user = new User(userName, password, idCard, phoneNumber);
        list.add(user);
        System.out.println("注册成功！");
/*        //遍历集合，方便查看是否注册成功。
        printlist(list);*/
    }

/*    //遍历集合
    private static void printlist(ArrayList<User> list) {
        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);
            System.out.println("用户名：" + user.getUserName() + "密码：" + user.getPassword() + "身份证号：" + user.getUserID() + "手机号：" + user.getPhoneNumber()
            );
        }
    }*/

    //检查手机号码是否输入正确
    private static boolean checkPhoneNumber(String phoneNumber) {
        //长度为11，不能0开头，必须都是数字
        if (phoneNumber.length() != 11) {
            return false;
        }
        if (phoneNumber.startsWith("0")) {
            return false;
        }
        for (int i = 0; i < phoneNumber.length(); i++) {
            char c = phoneNumber.charAt(i);
            if (!(c >= '0' && c <= '9')) {
                return false;
            }
        }
        return true;
    }

    //检查身份证号是否合法
    //长度为18，不能0开头，前十七位必须为数字，最后一位为数字或者X或者x
    private static boolean checkIdCard(String idCard) {
        if (idCard.length() != 18) {
            return false;
        }
        //能不能以0开头
        boolean flag = idCard.startsWith("0");
        if (flag) {
            //如果0开头
            return false;
        }
        //前17位为数字
        for (int i = 0; i < idCard.length() - 1; i++) {
            char c = idCard.charAt(i);
            if (!(c >= '0' && c <= '9')) {
                return false;
            }
        }
        //最后一位是数字或者X或者x
        char end = idCard.charAt(idCard.length() - 1);
        if ((end == 'x') || (end == 'X') || (end >= '0' && end <= '9')) {
            return true;
        } else {
            return false;
        }

    }

    //遍历，检查用户名是否唯一
    private static int getIndex(ArrayList<User> list, String userName) {

        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);
            String u = user.getUserName();
            if (u.equals(userName)) {
                //用户名存在
                return i;
            }
        }
        return -1;
    }

    //检查用户名是否输入正确
    private static boolean checkUserName(String userName) {
        //用户名长度在3~15之间，只能是字母加数字的组合，不能是纯数字。
        //用户名长度在3~15之间
        int length = userName.length();
        if ((length < 3) || (length > 15)) {
            return false;
        }
        //只能是字母加数字的组合
        for (int i = 0; i < length; i++) {
            char c = userName.charAt(i);
            if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9'))) {
                return false;
            }
        }
        //不能是纯数字
        int count = 0;
        for (int i = 0; i < length; i++) {
            char c = userName.charAt(i);
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                count++;
                break;
            }
        }
        return count > 0;
    }

    //登录
    //验证要求:
    //用户名如果未注册，直接结束方法，并提示:用户名未注册，请先注册。
    //判断验证码是否正确，如不正确，重新输入
    //再判断用户名和密码是否正确，有3次机会
    private static void login(ArrayList<User> list) {
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < 3; i++) {
            System.out.println("请输入用户名：");
            String userName = sc.next();
            int index = getIndex(list, userName);
            if (!(index >= 0)) {
                System.out.println("用户未注册！请先注册");
                return;
            }
            System.out.println("请输入密码：");
            String password = sc.next();

            while (true) {
                String rightCode = getCode();
                System.out.println("验证码：" + rightCode);
                System.out.println("请输入验证码（不区分大小写）：");
                String code = sc.next();
                if (code.equalsIgnoreCase(rightCode)) {
                    break;
                } else {
                    System.out.println("验证码输入错误！");
                }
            }
            //验证用户名和密码
            //封装
            User userInfo = new User(userName, password, null, null);
            boolean flag = checkUserInfo(list, userInfo);
            if (flag) {
                System.out.println("登录成功！");
                //连接学生管理系统
                StudentTest studentTest = new StudentTest();
                studentTest.studentManagementSystem();
                break;
            } else {
                if (i == 2) {
                    System.out.println("账户被锁定!");
                    return;
                } else {
                    System.out.println("登录失败，用户名或密码错误！还剩" + (2 - i) + "次机会");
                }
            }
        }

    }

    //验证用户名和密码是否正确
    private static boolean checkUserInfo(ArrayList<User> list, User userInfo) {
        //遍历集合，判断用户名是否存在
        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);
            if (user.getUserName().equals(userInfo.getUserName()) && user.getPassword().equals(userInfo.getPassword())) {
                return true;
            }
        }
        return false;
    }

    //生成验证码
    private static String getCode() {
        //创建一个集合，添加所有的小写字母和大写字母
        ArrayList<Character> list = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            list.add((char) ('a' + i));
            list.add((char) ('A' + i));
        }
        //随机抽取4个字母
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int index = r.nextInt(list.size());
            char c = list.get(index);
            sb.append(c);
        }
        //添加数字
        int number = r.nextInt(10);
        sb.append(number);
        //生成验证码，数字出现在随机位置
        char[] chs = sb.toString().toCharArray();
        int randomIndex = r.nextInt(chs.length);
        char temp = chs[randomIndex];
        chs[randomIndex] = chs[chs.length - 1];
        chs[chs.length - 1] = temp;
        return new String(chs);
    }
}
