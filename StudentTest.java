import java.util.ArrayList;
import java.util.Scanner;

public class StudentTest {
    public static void studentManagementSystem() {
        ArrayList<Student> list = new ArrayList<>();
        loop:
        while (true) {
            System.out.println("欢迎来到学生管理系统");
            System.out.println("1：添加学生");
            System.out.println("2：删除学生");
            System.out.println("3：修改学生");
            System.out.println("4；查询学生");
            System.out.println("5：退出");
            System.out.println("输入对应数字开始使用：");
            Scanner sc = new Scanner(System.in);
            String choose = sc.next();
            switch (choose) {
                case "1" -> addStudent(list);
                case "2" -> deleteStudent(list);
                case "3" -> updateStudent(list);
                case "4" -> queryStudent(list);
                case "5" -> {
                    System.out.println("退出");
                    break loop;
                    //System.exit(0);//停止虚拟机运行
                }

                default -> System.out.println("输入错误");
            }
        }

    }

    //添加学生
    public static void addStudent(ArrayList<Student> list) {
        Student s = new Student();
        Scanner sc = new Scanner(System.in);

        String id;
        while (true) {
            System.out.println("输入学生ID：");
            id = sc.next();
            //调用getIndex方法，返回索引
            int index = getIndex(list, id);
            if (index >= 0) {
                //索引>=0,表示ID已经存在
                System.out.println("学生ID重复，请重新输入!");

            } else {
                //ID不存在，可以进行添加
                s.setId(id);
                break;
            }

        }

        System.out.println("输入学生姓名：");
        String name = sc.next();
        s.setName(name);
        System.out.println("输入学生年龄：");
        int age = sc.nextInt();
        s.setAge(age);
        System.out.println("输入学生地址：");
        String address = sc.next();
        s.setAddress(address);
        list.add(s);
        System.out.println("学生信息录入成功!");
    }

    //判断id是否存在，获取索引以便复用。
    public static int getIndex(ArrayList<Student> list, String id) {
        //循环遍历得到ID再判断
        for (int i = 0; i < list.size(); i++) {
            Student stu = list.get(i);
            String sid = stu.getId();
            if (sid.equals(id)) {
                //ID存在，返回集合的索引，表示第几个学生对象里面的ID重复了
                return i;
            }
        }
        //循环结束，ID不存在
        return -1;
    }

    //删除学生
    public static void deleteStudent(ArrayList<Student> list) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("输入要删除的学生ID：");
            String id = sc.next();
            int index = getIndex(list, id);
            if (index >= 0) {
                list.remove(index);
                System.out.println("ID为" + id + "的学生信息删除成功！");
                break;
            } else {
                System.out.println("ID不存在，删除失败！请重新输入");
            }
        }
    }

    //修改学生
    public static void updateStudent(ArrayList<Student> list) {
        Scanner sc = new Scanner(System.in);
        String id = null;
        int index = 0;
        while (true) {
            System.out.println("输入要修改的学生ID：");
            id = sc.next();
            index = getIndex(list, id);
            if (index == -1) {
                System.out.println("没有这个学生ID，请重新输入!");
            } else {
                //获取要修改的学生对象
                Student stu = list.get(index);
                //修改
                System.out.println("输入修改后的学生姓名");
                String newName = sc.next();
                stu.setName(newName);
                System.out.println("输入修改后的学生年龄");
                int newAge = sc.nextInt();
                stu.setAge(newAge);
                System.out.println("输入修改后的学生地址");
                String newAddress = sc.next();
                stu.setAddress(newAddress);
                System.out.println("ID为" + id + "的学生信息修改成功");
                break;
            }
        }
    }

    //查询学生
    public static void queryStudent(ArrayList<Student> list) {
        if (list.size() == 0) {
            System.out.println("当前没有学生信息，请先添加。");
            return;
        }

        System.out.println("id\t\t姓名\t年龄\t家庭住址");//表头，为了排版美观，建议姓名和地址输入中文汉字，年龄和ID输入数字
        for (int i = 0; i < list.size(); i++) {
            Student stu = list.get(i);
            System.out.println(stu.getId() + "\t\t" + stu.getName() + "\t" + stu.getAge() + "\t\t" + stu.getAddress());
        }
    }
}