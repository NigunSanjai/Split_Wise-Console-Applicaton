import java.util.*;
class Expenses {
    static User us;

    String expenseName;
    int amt;
    String by;
    String date;
    int id;

    Expenses(String expenseName, int amt, String by, String date, int id) {
        this.expenseName = expenseName;
        this.amt = amt;
        this.by = by;
        this.date = date;
        this.id = id;
    }

    Expenses(){}

    static List<Expenses> eList = new ArrayList<>();
    static int ids = 100;

    public void expense(int pos) {
        System.out.println("~~~~~~~~~~ADD-EXPENSES~~~~~~~~~~\n");
        System.out.print("Enter Expense Name: ");
        String expenseName = Login.sc.nextLine();
        System.out.print("\nEnter Expense Amount: ");
        int amt = Login.sc.nextInt();
        Login.sc.nextLine();
        System.out.print("\nEnter Date of Expense in (dd/mm/yyyy): ");
        String date = Login.sc.nextLine();
        eList.add(new Expenses(expenseName, amt, User.uList.get(pos).name, date, ++ids));
        System.out.print("Press Enter to Continue...");
        Login.sc.nextLine();
    }

    public void viewExpense(int pos) {
        boolean flag = true;
        for (Expenses i : eList)
        {
            System.out.println("~~~~~~~~~~VIEW/REPAY-EXPENSES~~~~~~~~~~\n");
            if(i.by.equals(User.uList.get(pos).name) || User.uList.get(pos).payments.contains(i.id+""))
                continue;
            System.out.println("Expense Name: " + i.expenseName + "\nExpense Amount: " + i.amt + "\nAmount you to Pay: " + i.amt / User.uList.size() + "\nRequested by: " + i.by+"\nExpense Date: " + i.date + "\n----   ----   ----");
            flag = false;
            System.out.print("\nEnter (1) Pay / (0) Cancel: ");
            String choice = Login.sc.nextLine();
            if(choice.equals("0"))
            {
                continue;
            }
            User.uList.get(pos).payments += i.id + ":" + i.amt / User.uList.size() + ",";
            User.uList.get(pos).walletAmt -= i.amt / User.uList.size();
            for (User j : User.uList)
            {
                if (j.name.equals(i.by))
                {
                    j.walletAmt += i.amt / User.uList.size();
                    j.request.add(i.id + ":" + i.by + ":" + User.uList.get(pos).name + ":" + i.amt / User.uList.size());
                    break;
                }
            }
            System.out.println("\nPayment has been Completed");
            System.out.print("\nPress Enter to Continue...");
            Login.sc.nextLine();
        }
        System.out.println("~~~~~~~~~~VIEW/REPAY-EXPENSES~~~~~~~~~~\n");
        if(flag)
            System.out.print("\nNo User has been Request for Repay");
        else
            System.out.print("\nNo more Request for Repay");
        System.out.print("\nPress Enter to Continue...");
        Login.sc.nextLine();
    }

    public void transactions(int pos) {
        System.out.println("~~~~~~~~~~TRANSACTIONS~~~~~~~~~~\n");
        String transact[] = User.uList.get(pos).payments.split(",");
        boolean flag = true;
        for (String i : transact)
        {
            String arr[] = i.split(":");
            for (Expenses j : eList)
            {
                if(arr[0].equals(j.id + ""))
                {
                    flag = false;
                    System.out.println("Expense Name: " + j.expenseName + "\nAmount Payed: " + arr[1] + "\nRequested by: " + j.by+"\nExpense Date: " + j.date + "\n----   ----   ----");
                }
            }
        }
        if(flag)
        {
            System.out.print("\nNo Transaction has been done");
        }
        System.out.print("\nPress Enter to Continue...");
        Login.sc.nextLine();
    }

    public void requestResult(int pos) {
        System.out.println("~~~~~~~~~~REQUEST-RESULTS~~~~~~~~~~\n");
        boolean flag = true;
        for (User i : User.uList)
        {
            for (String j : i.request)
            {
                String arr[] = j.split(":");
                if(arr[1].equals(User.uList.get(pos).name))
                {
                    for (Expenses k : eList)
                    {
                        if(arr[0].equals(k.id+""))
                        {
                            System.out.println("Expense Name: " + k.expenseName + "\nAmount Payed: " + arr[3] + "\nPayed by: " + arr[2]+"\nExpense Date: " + k.date + "\n----   ----   ----");
                        }
                    }
                    flag = false;
                }
            }
        }
        if(flag)
        {
            System.out.print("\nNo Repayment has been done by any other Users");
        }
        System.out.print("\nPress Enter to Continue...");
        Login.sc.nextLine();
    }

}
class User {
    static User us;
    static Expenses ep;

    String name;
    String userName;
    String password;
    int walletAmt;
    String payments = "";
    List<String> request = new ArrayList<>();

    User (String name, String userName, String password) {
        this.name = name;
        this.userName =userName;
        this.password = password;
        this.walletAmt = 10000000;
    }

    User(){}

    static List<User> uList = new ArrayList<>();

    static {
        uList.add(new User("Dora", "dora001", "dora"));
        uList.add(new User("Bujji", "bujji002", "bujji"));
        uList.add(new User("Kulla Nari", "nari003", "kulla"));
        uList.add(new User("Back Pack", "back004", "pack"));
    }


    public void logged (int pos) {
        t:while (true)
        {
            System.out.printf("~~~~~~~~~~~~~~~WELCOME %s~~~~~~~~~~~~~~~\n\n" , uList.get(pos).name.toUpperCase());
            System.out.println("1. Add Expense   ");
            System.out.println("1. Add Expense   ");
            System.out.println("2. Add Partner     ");
            System.out.println("3. View/Repay Expense   ");
            System.out.println("4. Transactions   ");
            System.out.println("5. Change Password    ");
            System.out.println(" 6. Logout ");
            System.out.println("Enter the Choice: ");
            int choice = Login.sc.nextInt();
            Login.sc.nextLine();
            switch (choice)
            {
                case 1:
                    ep = new Expenses();
                    ep.expense(pos);
                    break;
                case 2:
                    us = new User();
                    us.partners("");
                    break;
                case 3:
                    ep = new Expenses();
                    ep.viewExpense(pos);
                    break;
                case 4:
                    ep = new Expenses();
                    ep.transactions(pos);
                    break;
                case 5:
                    us = new User();
                    us.changePassword(pos);
                    break;
                case 6:
                    System.out.println("---Logging Out---");
                    System.out.print("Press Enter to Continue...");
                    Login.sc.nextLine();
                    break t;
                default:
                    System.out.println("Invalid Input !!!");
                    System.out.print("Press Enter to Continue...");
                    Login.sc.nextLine();
            }
        }
    }

    public void partners(String name) {
        System.out.print("\033[H\033[2J");
        System.out.println("~~~~~~~~~~ADD-PARTNERS~~~~~~~~~~\n");
        System.out.print("Enter Partner's Name: "+name);
        if(name.equals(""))
            name = Login.sc.nextLine();
        else
            System.out.println();
        System.out.print("\nEnter Partner's Username: ");
        String userName = Login.sc.nextLine();
        for (User i : uList) {
            if(i.userName.equalsIgnoreCase(userName))
            {
                System.out.println("\nUsername Already Exists");
                System.out.print("Press Enter to Continue...");
                Login.sc.nextLine();
                partners(name);
                return;
            }
        }
        System.out.print("\nEnter Partner's Password: ");
        String password = Login.sc.nextLine();
        uList.add(new User(name, userName, password));
        System.out.println("\nPartner has been added Successfully");
        System.out.print("Press Enter to Continue...");
        Login.sc.nextLine();
    }

    public void changePassword(int pos) {
        System.out.print("\033[H\033[2J");
        System.out.println("~~~~~~~~~~CHANGE-PASSWORD~~~~~~~~~~\n");
        System.out.print("Enter New Password/Exist (0): ");
        String password = Login.sc.nextLine();
        if(password.equals("0"))
            return;
        if(password.equals(uList.get(pos).password))
        {
            System.out.println("\nNew Password mustn't be Old Password");
            System.out.print("Press Enter to Continue...");
            Login.sc.nextLine();
            changePassword(pos);
            return;
        }
        System.out.print("\nRetype Password: ");
        if(password.equals(Login.sc.nextLine()))
        {
            uList.get(pos).password = password;
            System.out.println("\nPassword Changed Successfully");
            System.out.print("Press Enter to Continue...");
            Login.sc.nextLine();
        }
        else
        {
            System.out.println("\nMismatch Password");
            System.out.print("Press Enter to Continue...");
            Login.sc.nextLine();
            changePassword(pos);
            return;
        }
    }

}
public class Login {
        static Login lo;
        static User us;

        static Scanner sc=new Scanner(System.in);
        public static void main(String[] args) {
            t:while (true)
            {
                System.out.println(" ==============WELCOME=================");
                System.out.println("1. User Login");
                System.out.println(" 2. Exit");
                System.out.println("Enter the Choice:");
                int choice = sc.nextInt();
                sc.nextLine();
                switch (choice)
                {
                    case 1:
                        lo = new Login();
                        lo.login();
                        break;
                    case 2:
                        System.out.println("!!! Thank You !!!");
                        break t;
                    default:
                        System.out.println("Invalid Input");
                        System.out.println("Press Enter to Continue...");
                        sc.nextLine();
                }
            }
        }

        public void login() {
            System.out.println("~~~~~~~~~~LOGIN~~~~~~~~~~\n");
            System.out.print("Enter Username: ");
            String userName = sc.nextLine();
            int pos = 0;
            for (User i : User.uList) {
                if(i.userName.equals(userName))
                {
                    System.out.print("\nEnter Password: ");
                    String password = sc.nextLine();
                    if(i.password.equals(password))
                    {
                        us = new User();
                        us.logged(pos);
                        return;
                    }
                    System.out.println("\nIncorrect Password !!!");
                    System.out.print("Press Enter to Continue...");
                    sc.nextLine();
                    return;
                }
                pos++;
            }
            System.out.println("\nUsername Doesn't Exists");
            System.out.print("Press Enter to Continue...");
            sc.nextLine();
        }

    }
