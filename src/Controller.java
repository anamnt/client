import DTOobjects.Ad;
import DTOobjects.Book;
import DTOobjects.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.Scanner;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by anamnt on 23/01/2017.
 * Dele af kode herfra: http://www.mkyong.com/java/how-to-send-http-request-getpost-in-java/
 */
public class Controller {

    Gson gson = new Gson();
    private String cookie; //cookie gemmes
    private String usersPassword;
    public Controller(){

    }


    public void login() throws Exception{


        final String USER_AGENT = "Mozilla/5.0";

        Scanner scanner = new Scanner(System.in);


        System.out.println("Indtast brugernavn");
        String username = scanner.nextLine();
        System.out.println("Indtast password");
        String password = scanner.nextLine();

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        String userString = gson.toJson(user);





        String url = "http://localhost:8000/login";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty( "Content-Type", "application/json" );
        con.setRequestProperty("Accept", "application/json");

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(userString);
        wr.flush();
        wr.close();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        User userFromServer = gson.fromJson(response.toString(),User.class);
        usersPassword = password;
        for (int i = 0;; i++) {
            String headerName = con.getHeaderFieldKey(i);
            String headerValue = con.getHeaderField(i);

            if (headerName == null && headerValue == null) {
                break;
            }
            if ("Set-Cookie".equalsIgnoreCase(headerName)) {
                String[] fields = headerValue.split(";\\s*");
                cookie = fields[0].toString();
            }
        }

        if (userFromServer.getType() == 0){
            userMenu(userFromServer);
        }else{
            adminMenu();
        }
    }

    public void userMenu(User user){
        Scanner scanner = new Scanner(System.in);
        boolean stop = false;
        while (!stop){
            System.out.println("\n Velkommen til menuen - tast dit valg" +
                    "\n1: Opdatere oplysninger" +
                    "\n2: Vis bøger til salg og lav en reservation" +
                    "\n3: Opret en annonce" +
                    "\n4 Log ud");
            try{
                int choice = scanner.nextInt();

                switch (choice) {

                    case 1: updateUser(user);
                        break;

                    case 2: makeReservation();
                        break;

                    case 3: createAd();
                        break;

                    case 4: logout();
                        stop = true;
                        break;

                    default: System.out.println("Prøv igen");
                        break;
                }
            }catch (Exception exception){
                System.out.println("Ukendt værdi indtastet, prøv igen");
            }
        }
    }

    public void makeReservation() throws Exception {
        final String USER_AGENT = "Mozilla/5.0";
        String url = "http://localhost:8000/reservead";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty( "Content-Type", "application/json" );
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Cookie", cookie);

        System.out.println("Bøger til salg");

        System.out.printf("%-7s %-55s %-20s %-20s\n", "Nr", "Title", "ISBN", "Pris");
        int i = 0;
        for (Ad ad: getAds()){
            System.out.printf("%-7d %-55s %-20d %-20d\n", i++, ad.getBookTitle() ,  ad.getIsbn(), ad.getPrice());
        }

        System.out.println("Hvilken bog vil du reservere? (Vælge et nummer)");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        String adString = gson.toJson(getAds().get(choice));

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(adString);
        wr.flush();
        wr.close();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println(response.toString());

    }

    public void adminMenu(){
        Scanner scanner = new Scanner(System.in);
        boolean stop = false;
        while (!stop){
            System.out.println("\n Velkommen til Adminmenuen - hvad vil du?" +
                    "\n1: Hent bøger" +
                    "\n2: Slet bog" +
                    "\n3: Opret bog" +
                    "\n4: Opret bruger" +
                    "\n5: Opret annonce" +
                    "\n6: Log ud");
            try{
                int choice = scanner.nextInt();

                switch (choice) {

                    case 1: showBooks();
                        break;

                    case 2: deleteBook();
                        break;

                    case 3: createBook();
                        break;

                    case 4: createUser();
                        break;

                    case 5: createAd();
                        break;

                    case 6: logout();
                        stop = true;
                        break;

                    default: System.out.println("Prøv igen");
                        break;
                }
            }catch (Exception exception){
                System.out.println("Ukendt værdi indtastet, prøv igen");
            }
        }
    }

    public void createAd() throws Exception {


        final String USER_AGENT = "Mozilla/5.0";
        String url = "http://localhost:8000/createad";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty( "Content-Type", "application/json" );
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Cookie", cookie);

        int i = 0;

        System.out.printf("%-7s %-55s %-55s\n", "Nr", "Title", "ISBN");
        for (Book book: getBooks()){
            System.out.printf("%-7s %-55s %-55d\n", i++,  book.getTitle() ,  book.getISBN());
        }

        Ad ad = new Ad();

        try {
            System.out.println("Hvilken bog vil du sælge? (Vælge et nummer)");
            Scanner scanBook = new Scanner(System.in);
            int choice = scanBook.nextInt();
            long isbn = getBooks().get(choice).getISBN();
            ad.setIsbn(isbn);

            System.out.println("Stand");
            Scanner scanRating = new Scanner(System.in);
            int rating = scanRating.nextInt();
            ad.setRating(rating);

            System.out.println("Kommentar");
            Scanner scanComment = new Scanner(System.in);
            String comment = scanComment.nextLine();
            ad.setComment(comment);

            System.out.println("Pris");
            Scanner scanPrice = new Scanner(System.in);
            int price = scanPrice.nextInt();
            ad.setPrice(price);

        }catch (Exception exception){
            System.out.println("Ukendt værdi indtastet, prøv igen");
        }

        String adString = gson.toJson(ad);

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(adString);
        wr.flush();
        wr.close();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println(response.toString());
    }


    public void updateUser(User user) throws Exception{
        final String USER_AGENT = "Mozilla/5.0";
        String url = "http://localhost:8000/updateuser";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty( "Content-Type", "application/json" );
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Cookie", cookie);

        boolean stop = false;
        boolean sendPassword = true;
        while (!stop){
            System.out.println("\n Hvad ønsker du at ændre ved brugeren?" +
                    "\n1: Brugernavn" +
                    "\n2: Adgangskode" +
                    "\n3: Telefonnummer" +
                    "\n4: Adressen" +
                    "\n5: E-mail" +
                    "\n6: Mobilepay" +
                    "\n7: Kontanter" +
                    "\n8: Overførelse" +
                    "\n9: Slet profil" +
                    "\n10: Tilbage til hovedmenuen");
            try{
                Scanner scanner = new Scanner(System.in);
                int choice = scanner.nextInt();

                switch (choice) {

                    case 1: System.out.println("Indtast værdi");
                        Scanner scanUsername = new Scanner(System.in);
                    String username = scanUsername.nextLine();
                    user.setUsername(username);
                        break;

                    case 2: System.out.println("Indtast værdi");
                        Scanner scanPassword = new Scanner(System.in);
                        String password = scanPassword.nextLine();
                        user.setPassword(password);
                        sendPassword=false;
                        break;

                    case 3: System.out.println("Indtast værdi");
                        Scanner scanPhone = new Scanner(System.in);
                        int phone = scanPhone.nextInt();

                        user.setPhonenumber(phone);
                        break;

                    case 4: System.out.println("Indtast værdi");
                        Scanner scanAddress = new Scanner(System.in);
                        String address = scanAddress.nextLine();
                        user.setAddress(address);
                        break;

                    case 5: System.out.println("Indtast værdi");
                        Scanner scanEmail = new Scanner(System.in);
                        String email = scanEmail.nextLine();
                        user.setEmail(email);
                        break;

                    case 6: System.out.println("Indtast værdi");
                        Scanner scanMobilePay = new Scanner(System.in);
                        int mobilePay = scanMobilePay.nextInt();
                        user.setMobilepay(mobilePay);
                        break;

                    case 7: System.out.println("Indtast værdi");
                        Scanner scanCash = new Scanner(System.in);
                        int cash = scanCash.nextInt();
                        user.setCash(cash);
                        break;

                    case 8: System.out.println("Indtast værdi");
                        Scanner scanTransfer = new Scanner(System.in);
                        int transfer = scanTransfer.nextInt();
                        user.setTransfer(transfer);
                        break;

                    case 9: System.out.println("Bruger slettet");
                        stop = true;
                        break;

                    case 10: stop = true;
                    break;

                    default: System.out.println("Prøv igen");
                        break;
                }
            }catch (Exception exception){
                System.out.println("Ukendt værdi indtastet, prøv igen");
            }
        }

        if(sendPassword == true){
            user.setPassword(usersPassword);
        }
        String userString = gson.toJson(user);

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(userString);
        wr.flush();
        wr.close();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println(response.toString());

    }

    public void booksOnSale() throws Exception{

        System.out.printf("%-55s %-55s\n", "Title", "ISBN");
        for (Ad ad: getAds()){
            System.out.printf("%-55s %-55d\n", ad.getBookTitle() ,  ad.getIsbn());
        }
    }

    public void createUser() throws Exception{

        final String USER_AGENT = "Mozilla/5.0";
        String url = "http://localhost:8000/createuser";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty( "Content-Type", "application/json" );
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Cookie", cookie);

        User user = new User();

        try{

                System.out.println("Indtast brugernavn");
                    Scanner scanUsername = new Scanner(System.in);
                    String username = scanUsername.nextLine();
                    user.setUsername(username);

                System.out.println("Indtast adgangskode");
                    Scanner scanPassword = new Scanner(System.in);
                    String password = scanPassword.nextLine();
                    user.setPassword(password);

                System.out.println("Indtast telefonnummer");
                    Scanner scanPhone = new Scanner(System.in);
                    int phone = scanPhone.nextInt();

                    user.setPhonenumber(phone);

                System.out.println("Indtast adresse");
                    Scanner scanAddress = new Scanner(System.in);
                    String address = scanAddress.nextLine();
                    user.setAddress(address);

                System.out.println("Indtast e-mail");
                    Scanner scanEmail = new Scanner(System.in);
                    String email = scanEmail.nextLine();
                    user.setEmail(email);

                System.out.println("MobilePay (1: Ja eller 0: Nej)");
                    Scanner scanMobilePay = new Scanner(System.in);
                    int mobilePay = scanMobilePay.nextInt();
                    user.setMobilepay(mobilePay);

                System.out.println("Kontanter (1: Ja eller 0: Nej)");
                    Scanner scanCash = new Scanner(System.in);
                    int cash = scanCash.nextInt();
                    user.setCash(cash);

                System.out.println("Overførelse (1: Ja eller 0: Nej)");
                    Scanner scanTransfer = new Scanner(System.in);
                    int transfer = scanTransfer.nextInt();
                    user.setTransfer(transfer);

        }catch (Exception exception){
            System.out.println("Ukendt værdi indtastet, prøv igen");
        }

        String userString = gson.toJson(user);

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(userString);
        wr.flush();
        wr.close();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println(response.toString());
    }

    public void createBook() throws Exception{

        final String USER_AGENT = "Mozilla/5.0";
        String url = "http://localhost:8000/createbook";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty( "Content-Type", "application/json" );
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Cookie", cookie);

        Book book = new Book();

        try {
            System.out.println("ISBN");
            Scanner scanDouble = new Scanner(System.in);
            long isbn = scanDouble.nextLong();
            book.setISBN(isbn);

            System.out.println("Title");
            Scanner scanTitle = new Scanner(System.in);
            String title = scanTitle.nextLine();
            book.setTitle(title);

            System.out.println("Udgave");
            Scanner scanEdition = new Scanner(System.in);
            String edition = scanEdition.nextLine();
            book.setEdition(edition);

            System.out.println("Forfatter");
            Scanner scanAuthor = new Scanner(System.in);
            String author = scanAuthor.nextLine();
            book.setAuthor(author);

        }catch (Exception exception){
            System.out.println("Ukendt værdi indtastet, prøv igen");
        }

        String bookString = gson.toJson(book);

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(bookString);
        wr.flush();
        wr.close();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println(response.toString());


    }

    public ArrayList<Book> getBooks() throws Exception{

        final String USER_AGENT = "Mozilla/5.0";
        String url = "http://localhost:8000/getbooks";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("GET");
        con.setRequestProperty( "Content-Type", "application/json" );
        con.setRequestProperty("Accept", "application/json");
        //con.setRequestProperty("Cookie", cookie);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        ArrayList<Book> books = gson.fromJson(response.toString(), new TypeToken<ArrayList<Book>>(){
        }.getType());

        return books;

    }

    public ArrayList<Ad> getAds() throws Exception{

        final String USER_AGENT = "Mozilla/5.0";
        String url = "http://localhost:8000/getads";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("GET");
        con.setRequestProperty( "Content-Type", "application/json" );
        con.setRequestProperty("Accept", "application/json");
        //con.setRequestProperty("Cookie", cookie);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        ArrayList<Ad> ads = gson.fromJson(response.toString(), new TypeToken<ArrayList<Ad>>(){
        }.getType());

        return ads;

    }


    public void showBooks() throws Exception{

        System.out.printf("%-55s %-55s\n", "Title", "ISBN");
        for (Book book: getBooks()){
            System.out.printf("%-55s %-55d\n", book.getTitle() ,  book.getISBN());
        }

    }



    public void deleteBook() throws Exception{
        final String USER_AGENT = "Mozilla/5.0";
        String url = "http://localhost:8000/deletebook";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty( "Content-Type", "application/json" );
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Cookie", cookie);

        System.out.printf("%-7s %-55s %-55s\n", "Nr", "Title", "ISBN");
        int i = 0;
        for (Book book: getBooks()){
            System.out.printf("%-7d %-55s %-55d\n", i++, book.getTitle() ,  book.getISBN());
        }

        System.out.println("Hvilken bog vil du slette? (Vælge et nummer)");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        String bookString = gson.toJson(getBooks().get(choice));

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(bookString);
        wr.flush();
        wr.close();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println(response.toString());
    }

    public void logout() throws Exception{

        final String USER_AGENT = "Mozilla/5.0";
        String url = "http://localhost:8000/logout";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty( "Content-Type", "application/json" );
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Cookie", cookie);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println(response.toString());

    }
}
