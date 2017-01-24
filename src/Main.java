
import DTOobjects.User;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {

        Controller controller = new Controller();

        System.out.println("Velkommen");


        Scanner scanner = new Scanner(System.in);
        boolean stop = false;
        while (!stop){
            System.out.println("\n1: Opret bruger" +
                    "\n2: Login");
            try{
                int choice = scanner.nextInt();

                switch (choice) {

                    case 1: controller.createUser();
                        break;

                    case 2: controller.login();
                        break;

                    default: System.out.println("Prøv igen");
                        break;
                }
            }catch (Exception exception){
                System.out.println("Ukendt værdi indtastet, prøv igen");
            }
        }



    }

}
