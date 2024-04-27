package org.example.carrentalgui;

import java.io.*;
import java.util.*;

import static java.lang.System.exit;

public class Code {
    static class QueueList{
        String username;
        String make;
        String model;
        String year;
        String rent;
        QueueList next;
        String days;
        public QueueList(String make, String model, String year,String username,String rent,String days) {
            this.username=username;
            this.make = make;
            this.model = model;
            this.year = year;
            this.rent=rent;
            this.days=days;
        }
    }
    static class RentBST{
        String make;
        String model;
        String year;
        int rent;
        RentBST left;
        RentBST right;
        public RentBST(String make, String model, String year,int rent) {
            this.make = make;
            this.model = model;
            this.year = year;
            this.rent=rent;
        }
    }
    static RentBST root;
    static QueueList Enqueue,Dequeue;
    static class LockedOutException extends Exception{
        public LockedOutException(String error){
            super(error);
        }
    }
    public static String available="C:\\Users\\rayya\\Desktop\\AvailCars.txt";
    public static String users="C:\\Users\\rayya\\Desktop\\Users.txt";
    public static String temp="C:\\Users\\rayya\\Desktop\\temp.txt";
    public static String reservations="C:\\Users\\rayya\\Desktop\\Reservations.txt";
    static Scanner scanner = new Scanner(System.in);
    static int LocketOutCount=0;
    public static int Menu(){
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("Welcome To Car Rental System\n");
        System.out.println("Press (1) for User Panel");
        System.out.println("Press (2) for Admin Panel");
        System.out.println("Press (3) to Quit");
        System.out.println("Choose what to do:");
        return scanner.nextInt();
    }
    public static int UserPanel(){
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("Press (1) to Login");
        System.out.println("Press (2) to Register");
        System.out.println("Press (3) to Go back!\n");
        System.out.println("Choose what to do:");
        return scanner.nextInt();
    }
    public static String UserLogin(String name,String pass) throws LockedOutException{
        int flag=0;
        try {
            File f = new File(users);
            if (!f.exists()) {
                return "NoUsers";
            } else {
                BufferedReader br = new BufferedReader(new FileReader(f));
                String buffer;
                String[] arr;
                if ((buffer = br.readLine()) == null) {
                    return "NoUsers";
                } else {
                    arr = buffer.split(" ");
                    if (name.equals(arr[0]) && pass.equals(arr[1])) {
                    } else {
                        while ((buffer = br.readLine()) != null) {
                            arr = buffer.split(" ");
                            if (name.equals(arr[0]) && pass.equals(arr[1])) {
                                flag = 1;
                                break;
                            }
                        }
                        if (flag == 0) {
                            LocketOutCount++;
                            if(LocketOutCount==3){
                                return "Locked";
                            }
                            return "Invalid";
                        }
                    }
                }
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return "Login";
    }
    public static String UserRegistration(String name,String pass1,String pass2){
        int numCount = 0;
        int specialCount = 0;
        String buffer;
        for (; ; ) {
            try {
                File f = new File(users);
                if (!f.exists()) {
                    f.createNewFile();
                }
                BufferedReader br = new BufferedReader(new FileReader(f));
                while ((buffer = br.readLine()) != null) {
                    String[] arr = buffer.split(" ");
                    if (arr[0].equals(name)) {
                        return "exist";
                    }
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!pass1.equals(pass2)) {
                return "incorrect";
            }
            for (int i = 0; i < pass1.length(); i++) {
                if (Character.isDigit(pass1.charAt(i))) {
                    numCount++;
                }
            }
            for (int i = 0; i < pass1.length(); i++) {
                if (!Character.isDigit(pass1.charAt(i)) && !Character.isLetter(pass1.charAt(i)) && !Character.isWhitespace(pass1.charAt(i))) {
                    specialCount++;
                }
            }
            if (numCount < 3 && specialCount < 1) {
                return "both";
            }
            if (numCount < 3) {
                return "num";
            } else if (specialCount < 1) {
                return "char";
            } else {
                try {
                    File f = new File(users);
                    if (!f.exists()) {
                        f.createNewFile();
                    }
                    FileWriter fw = new FileWriter(f, true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write(name);
                    bw.write(" ");
                    bw.write(pass1);
                    bw.newLine();
                    bw.close();
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        return "done";
    }
    public static void RentACar(String[] data,String name,String input){
        int days = Integer.parseInt(input);
        String buffer = "%s %s %s %s".formatted(data[0],data[1],data[2],data[3]);
        String buffer2;
        int rent= Integer.parseInt(data[3]);
                int Total = rent*days;
                    try {
                        String filename = "C:\\Users\\rayya\\Desktop\\%s RentedCars.txt".formatted(name);
                        File f1 = new File(temp);
                        File f2 = new File(available);
                        File f3 = new File(filename);
                        f1.createNewFile();
                        if (!f3.exists()) {
                            f3.createNewFile();
                        }
                        BufferedReader br1=new BufferedReader(new FileReader(f2));
                        BufferedWriter bw = new BufferedWriter(new FileWriter(f1));
                        String line;
                        while((line=br1.readLine())!=null){
                            bw.write(line);
                            bw.newLine();
                        }
                        bw.close();
                        f2.delete();
                        f2.createNewFile();
                        BufferedReader br = new BufferedReader(new FileReader(f1));
                        BufferedWriter bw1 = new BufferedWriter(new FileWriter(f3,true));
                        bw=new BufferedWriter(new FileWriter(f2));
                        while ((buffer2 = br.readLine()) != null) {
                            if (buffer.equals(buffer2)) {
                            } else {
                                bw.write(buffer2);
                                bw.newLine();
                            }
                        }
                        bw1.write(buffer);
                        bw1.write(" ");
                        bw1.write(String.valueOf(days));
                        bw1.write(" ");
                        bw1.write(String.valueOf(Total));
                        bw1.newLine();
                        bw1.close();
                        br.close();
                        bw.close();
                        f1.delete();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
    }
    public static void ReturnACar(String data[],String name){
        String filename="C:\\Users\\rayya\\Desktop\\%s RentedCars.txt".formatted(name);
        String buffer = "%s %s %s %s %s %s".formatted(data[0],data[1],data[2],data[3],data[4],data[5]);
        String buffer2;
                try {
                    File f1 = new File(available);
                    File f2 = new File(temp);
                    File f3 = new File(filename);
                    BufferedReader br = new BufferedReader(new FileReader(f3));
                    BufferedWriter bufferedWriter=new BufferedWriter(new FileWriter(f2));
                    String line;
                    while((line=br.readLine())!=null){
                        bufferedWriter.write(line);
                        bufferedWriter.newLine();
                    }
                    bufferedWriter.close();
                    BufferedWriter bw1 = new BufferedWriter( new FileWriter(f1, true));
                    if (!f1.exists()) {
                        f1.createNewFile();
                    }
                    br=new BufferedReader(new FileReader(f2));
                    BufferedWriter bw2 = new BufferedWriter(new FileWriter(f3));
                    while ((buffer2 = br.readLine()) != null) {
                        if (buffer.equals(buffer2)) {
                        } else {
                            bw2.write(buffer2);
                            bw2.newLine();
                        }
                    }
                    String car="%s %s %s %s".formatted(data[0],data[1],data[2],data[3]);
                    bw1.write(car);
                    bw1.newLine();
                    bw1.close();
                    bw2.close();
                    br.close();
                    f2.delete();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                catch (NullPointerException e){
                    e.getMessage();
                }
    }
    public static boolean AdminLogin(String name, String pass){
            String admin="admin",password="pass";
            if(name.equals(admin) && pass.equals(password)){
                return true;
            }
            else{
                return false;
            }
    }
    public static void AddCar(String make,String model,String year,String rent){
        String buffer;
        try{
            File f=new File(available);
            if(!f.exists()){
                f.createNewFile();
            }
            FileWriter fw=new FileWriter(f,true);
            BufferedWriter bw=new BufferedWriter(fw);
            buffer="%s %s %s %s".formatted(make,model,year,rent);
            bw.write(buffer);
            bw.newLine();
            bw.close();
            fw.close();
            int intRent=Integer.parseInt(rent);
            RentBST node=new RentBST(make,model,year,intRent);
            root=InsertionInBST(node,root);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void DeleteCar(String[] data) throws ArrayIndexOutOfBoundsException{
        String buffer;
            try {
                File f1 = new File(available);
                File f2 = new File(temp);
                BufferedReader reader=new BufferedReader(new FileReader(f1));
                BufferedWriter writer=new BufferedWriter(new FileWriter(f2));
                String line;
                while((line=reader.readLine())!=null){
                    writer.write(line);
                    writer.newLine();
                }
                writer.close();
                reader.close();
                f1.delete();
                String[] split;
                BufferedReader br = new BufferedReader(new FileReader(f2));
                BufferedWriter bw = new BufferedWriter(new FileWriter(f1));
                while ((buffer = br.readLine()) != null) {
                    split = buffer.split(" ");
                    if (split[0].equalsIgnoreCase(data[0]) && split[1].equalsIgnoreCase(data[1]) && split[2].equalsIgnoreCase(data[2])&&split[3].equalsIgnoreCase(data[3])) {
                    } else {
                        bw.write(buffer);
                        bw.newLine();
                    }
                }
                bw.close();
                br.close();
                f2.delete();
                int intRent=Integer.parseInt(data[3]);
                RentBST node=new RentBST(data[0],data[1],data[2],intRent);
                root=deletionInBST(node,root);
            }catch (IOException e) {
                e.printStackTrace();
            }
    }
    public static void DisplayCars(){
        ArrayList<String> usernames=new ArrayList<>();
        try {
            String buffer;
            File f = new File(available);
            if (!f.exists()) {
                System.out.println("No Cars to display!");
            } else {
                BufferedReader br = new BufferedReader(new FileReader(f));
                if ((buffer = br.readLine()) == null) {
                    System.out.println("\nNo Available Cars for renting!\n");
                } else {
                    System.out.println("-------------------------------------------------------------------------");
                    System.out.println("Available cars:");
                    System.out.println(buffer);
                    while ((buffer = br.readLine()) != null) {
                        System.out.println(buffer);
                    }
                    System.out.println();
                    br.close();
                }
                File f1 = new File(users);
                if (!f1.exists()) {
                    System.out.println("No registered users!");
                } else {
                    BufferedReader br1 = new BufferedReader(new FileReader(f1));
                    while ((buffer = br1.readLine()) != null) {
                        String[] arr = buffer.split(" ");
                        usernames.add(arr[0]);
                    }
                    br1.close();
                    for (String username : usernames) {
                        String filename = "C:\\Users\\rayya\\Desktop\\%s RentedCars.txt".formatted(username);
                        File f2 = new File(filename);
                        if (!f2.exists()) {
                            continue;
                        }
                        BufferedReader br2 = new BufferedReader(new FileReader(f2));
                        if ((buffer = br2.readLine()) == null) {
                            System.out.printf("No rented cars of User:%s\n", username);
                        } else {
                            System.out.printf("Rented cars by User:%s:\n", username);
                            System.out.println(buffer);
                            while ((buffer = br2.readLine()) != null) {
                                System.out.println(buffer);
                            }
                            br2.close();
                        }
                    }
                }

            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void DisplayUsers(){
        String buffer;
        try {
            File f = new File(users);
            if (!f.exists()) {
                System.out.println("No registered users exist!");
            } else {
                BufferedReader br = new BufferedReader(new FileReader(f));
                if((buffer=br.readLine())==null){
                    System.out.println("No registered users exist!");
                }
                else{
                    System.out.println("-------------------------------------------------------------------------");
                    System.out.println("Registered users:\n");
                    System.out.println(buffer);
                    while((buffer=br.readLine())!=null){
                        System.out.println(buffer);
                    }
                }
                br.close();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void LinearSearch() {
        try {
            System.out.println("-------------------------------------------------------------------------");
            int flag=0;
            File f = new File(available);
            if (!f.exists()) {
                System.out.println("No cars available at the moment!");
            } else {
                System.out.println("Enter complete name of the car you want to search in format (Make Model): ");
                String car = scanner.nextLine();
                String[] carnames = car.split(" ");
                if (carnames.length != 2) {
                    System.out.println("Invalid name! Try Again! ");
                    LinearSearch();
                }
                else {
                    System.out.println("-------------------------------------------------------------------------");
                    System.out.println("Cars found:");
                    BufferedReader br=new BufferedReader(new FileReader(f));
                    String line;
                    while((line=br.readLine())!=null){
                        String[] splitWithHeadings =line.split(" ");
                        String[] makeWithHeading=splitWithHeadings[0].split(":");
                        String make=makeWithHeading[1];
                        String[] modelWithHeading=splitWithHeadings[1].split(":");
                        String model=modelWithHeading[1];
                        if(make.equalsIgnoreCase(carnames[0])&&model.equalsIgnoreCase(carnames[1])){
                            flag=1;
                            System.out.println(line);
                        }
                    }
                    if(flag==0){
                        System.out.println("None found!");
                    }
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void NameSortAscending(){
        try {
            File f = new File(available);
            if (!f.exists()) {
                System.out.println("No available cars");
            } else {
                BufferedReader br = new BufferedReader(new FileReader(f));
                String line;
                ArrayList<String> unsorted=new ArrayList<>();
                while((line=br.readLine())!=null){
                    if(unsorted.isEmpty()){
                        unsorted.add(line);
                        continue;
                    }
                    unsorted.add(line);
                    for (int i = unsorted.size()-1; i > 0 && unsorted.get(i).compareToIgnoreCase(unsorted.get(i-1))<0 ; i--) {
                        String temp=unsorted.get(i);
                        unsorted.set(i,unsorted.get(i-1));
                        unsorted.set(i-1,temp);
                    }
                }
                br.close();
                f.delete();
                File file=new File(available);
                if(!f.exists()){
                    f.createNewFile();
                }
                BufferedWriter bw=new BufferedWriter(new FileWriter(file));
                for (int i = 0; i < unsorted.size(); i++) {
                    bw.write(unsorted.get(i));
                    bw.newLine();
                }
                bw.close();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void NameSortDescending(){
        try {
            File f = new File(available);
            if (!f.exists()) {
                System.out.println("No available cars");
            } else {
                BufferedReader br = new BufferedReader(new FileReader(f));
                String line;
                ArrayList<String> unsorted=new ArrayList<>();
                while((line=br.readLine())!=null){
                    if(unsorted.isEmpty()){
                        unsorted.add(line);
                        continue;
                    }
                    unsorted.add(line);
                    for (int i = unsorted.size()-1; i > 0 && unsorted.get(i).compareToIgnoreCase(unsorted.get(i-1))>0 ; i--) {
                        String temp=unsorted.get(i);
                        unsorted.set(i,unsorted.get(i-1));
                        unsorted.set(i-1,temp);
                    }
                }
                br.close();
                f.delete();
                File file=new File(available);
                if(!file.exists()){
                    file.createNewFile();
                }
                BufferedWriter bw=new BufferedWriter(new FileWriter(file));
                for (int i = 0; i < unsorted.size(); i++) {
                    bw.write(unsorted.get(i));
                    bw.newLine();
                }
                bw.close();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void RentSortAscending(){
        try {
            File f = new File(available);
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            ArrayList<String> unsorted = new ArrayList<>();
            ArrayList<Integer> rent=new ArrayList<>();
            while ((line = br.readLine()) != null) {
                String[] splitwithHeadings=line.split(" ");
                int rentperday=Integer.parseInt(splitwithHeadings[3]);
                if (unsorted.isEmpty()) {
                    unsorted.add(line);
                    rent.add(rentperday);
                    continue;
                }
                unsorted.add(line);
                rent.add(rentperday);
                for (int i = rent.size()-1; i > 0 && rent.get(i)<rent.get(i-1) ; i--) {
                    String temp=unsorted.get(i);
                    unsorted.set(i,unsorted.get(i-1));
                    unsorted.set(i-1,temp);
                    int temprent=rent.get(i);
                    rent.set(i,rent.get(i-1));
                    rent.set(i-1,temprent);
                }
            }
            br.close();
            f.delete();
            File file=new File(available);
            if(!file.exists()){
                file.createNewFile();
            }
            BufferedWriter bw=new BufferedWriter(new FileWriter(file));
            for (int i = 0; i < unsorted.size(); i++) {
                bw.write(unsorted.get(i));
                bw.newLine();
            }
            bw.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void RentSortDescending(){
        try {
            File f = new File(available);
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            ArrayList<String> unsorted = new ArrayList<>();
            ArrayList<Integer> rent=new ArrayList<>();
            while ((line = br.readLine()) != null) {
                String[] splitwithHeadings=line.split(" ");
                int rentperday=Integer.parseInt(splitwithHeadings[3]);
                if (unsorted.isEmpty()) {
                    unsorted.add(line);
                    rent.add(rentperday);
                    continue;
                }
                unsorted.add(line);
                rent.add(rentperday);
                for (int i = rent.size()-1; i > 0 && rent.get(i)>rent.get(i-1) ; i--) {
                    String temp=unsorted.get(i);
                    unsorted.set(i,unsorted.get(i-1));
                    unsorted.set(i-1,temp);
                    int temprent=rent.get(i);
                    rent.set(i,rent.get(i-1));
                    rent.set(i-1,temprent);
                }
            }
            br.close();
            f.delete();
            File file=new File(available);
            if(!file.exists()){
                file.createNewFile();
            }
            BufferedWriter bw=new BufferedWriter(new FileWriter(file));
            for (int i = 0; i < unsorted.size(); i++) {
                bw.write(unsorted.get(i));
                bw.newLine();
            }
            bw.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void InsertInList(String line){
        String[] Splitted=line.split(" ");
        String username=Splitted[0];
        String make=Splitted[1];
        String model=Splitted[2];
        String year=Splitted[3];
        String rent=Splitted[4];
        String days=Splitted[5];
        QueueList node=new QueueList(make,model,year,username,rent,days);
        if(Enqueue==null){
            Enqueue=node;
            Dequeue=node;
        }
        else{
            Dequeue.next=node;
            Dequeue=node;
        }
    }
    public static void ReservationToList(){
        try{
            File f=new File(reservations);
            if(f.exists()){
                BufferedReader br=new BufferedReader(new FileReader(f));
                String line;
                while((line=br.readLine())!=null){
                    InsertInList(line);
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void BookingReservation(String name){
        System.out.println("-------------------------------------------------------------------------");
        System.out.print("Enter make of car:");
        String make=scanner.nextLine();
        System.out.print("Enter model of car:");
        String model=scanner.nextLine();
        System.out.print("Enter year of car:");
        String year=scanner.nextLine();
        System.out.print("Enter rent per day of car:");
        String rent=scanner.nextLine();
        System.out.print("Enter how many days you want to rent for:");
        String days=scanner.nextLine();
        QueueList node=new QueueList(make,model,year,name,rent,days);
        if(Enqueue==Dequeue){
            Enqueue=node;
            Dequeue=node;
        }
        else{
            Dequeue.next=node;
            Dequeue=node;
        }
        try{
            File f=new File(reservations);
            if(!f.exists()){
                f.createNewFile();
            }
            BufferedWriter bw=new BufferedWriter(new FileWriter(f,true));
            String buffer="%s %s %s %s %s %s".formatted(name,make,model,year,rent,days);
            bw.write(buffer);
            bw.newLine();
            bw.close();
            System.out.println("Reservation has been made successfully!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void PrintReservations(){
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("Reservations: ");
        QueueList current=Enqueue;
        if(current!=null) {
            while (current != null) {
                System.out.println(current.username + " " + current.make + " " + current.model + " " + current.year + " " + current.rent + " " + current.days);
                current = current.next;
            }
        }
        else{
            System.out.println("No reservations found!");
        }
    }
    public static void DequeueReservations(){
        try {
            File f1 = new File(available);
            File f2 = new File(reservations);
            if(!f1.exists()){
                f1.createNewFile();
            }
            if(!f2.exists()){
                f2.createNewFile();
            }
            BufferedReader br1 = new BufferedReader(new FileReader(f1));
            String buffer1;
            int flag = 0;
            ArrayList<String> availableCars = new ArrayList<>();
            QueueList current = Enqueue;
            QueueList prev = null;
            if (current != null) {
                while ((buffer1 = br1.readLine()) != null) {
                    current=Enqueue;
                    flag = 1;
                    if (current == null) {
                        availableCars.add(buffer1);
                        while ((buffer1 = br1.readLine()) != null) {
                            availableCars.add(buffer1);
                        }
                        break;
                    }
                    String[] AllWithHeadings = buffer1.split(" ");
                    String[] MakeWithHeading = AllWithHeadings[0].split(":");
                    String[] ModelWithHeading = AllWithHeadings[1].split(":");
                    String[] YearWithHeading = AllWithHeadings[2].split(":");
                    String[] RentWithHeading = AllWithHeadings[3].split(":");
                    String make = MakeWithHeading[1];
                    String model = ModelWithHeading[1];
                    String year = YearWithHeading[1];
                    String rent = RentWithHeading[1];
                    int check=0;
                    while (current != null) {
                        if ((current.make.equalsIgnoreCase(make) && current.model.equalsIgnoreCase(model)) && (current.year.equalsIgnoreCase(year) && current.rent.equalsIgnoreCase(rent))) {
                            check=1;
                            String filename = "C:\\Users\\rayya\\Desktop\\%s RentedCars.txt".formatted(current.username);
                            File f = new File(filename);
                            if (!f.exists()) {
                                f.createNewFile();
                            }
                            BufferedWriter bw = new BufferedWriter(new FileWriter(f, true));
                            int days = Integer.parseInt(current.days);
                            int Intrent = Integer.parseInt(current.rent);
                            int total = days * Intrent;
                            bw.write("Make:");
                            bw.write(current.make);
                            bw.write(" ");
                            bw.write("Model:");
                            bw.write(current.model);
                            bw.write(" ");
                            bw.write("Year:");
                            bw.write(current.year);
                            bw.write(" ");
                            bw.write("RentPerDay:");
                            bw.write(current.rent);
                            bw.write(" ");
                            bw.write("TotalDaysRented:");
                            bw.write(current.days);
                            bw.write(" ");
                            bw.write("TotalRent:");
                            bw.write(String.valueOf(total));
                            bw.write(" ");
                            bw.newLine();
                            bw.close();
                            System.out.println(current.make+" "+current.model+" "+current.year+" has been rented out to the reservation holder "+current.username+" successfully! ");
                            if (prev == null) {
                                Enqueue = Enqueue.next;
                                current.next = null;
                            } else if (current.next == null) {
                                prev.next = null;
                            } else {
                                prev.next = current.next;
                                current.next = null;
                            }
                            break;
                        } else {
                            prev = current;
                            current = current.next;
                        }
                    }
                    if(check==0){
                        availableCars.add(buffer1);
                    }
                    else{
                        int intRent=Integer.parseInt(rent);
                        RentBST node=new RentBST(make,model,year,intRent);
                        deletionInBST(node,root);
                    }
                }
                if (flag == 1) {
                    f1.delete();
                    BufferedWriter bw1 = new BufferedWriter(new FileWriter(f1));
                    for (int i = 0; i < availableCars.size(); i++) {
                        bw1.write(availableCars.get(i));
                        bw1.newLine();
                    }
                    bw1.close();
                    f2.delete();
                    BufferedWriter bw2=new BufferedWriter(new FileWriter(f2));
                    QueueList curr=Enqueue;
                    while(curr!=null){
                        bw2.write(curr.username);
                        bw2.write(" ");
                        bw2.write(curr.make);
                        bw2.write(" ");
                        bw2.write(curr.model);
                        bw2.write(" ");
                        bw2.write(curr.year);
                        bw2.write(" ");
                        bw2.write(curr.rent);
                        bw2.write(" ");
                        bw2.write(curr.days);
                        bw2.write(" ");
                        bw2.newLine();
                        curr=curr.next;
                    }
                    bw2.close();
                } else {
                    System.out.println("No available cars to rent out for reservations");
                }
            }
            else{
                System.out.println("No reservations found");
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void InsertInBST(String line){
        String[] AllWithHeadings = line.split(" ");
        String[] MakeWithHeading = AllWithHeadings[0].split(":");
        String[] ModelWithHeading = AllWithHeadings[1].split(":");
        String[] YearWithHeading = AllWithHeadings[2].split(":");
        String[] RentWithHeading = AllWithHeadings[3].split(":");
        String make = MakeWithHeading[1];
        String model = ModelWithHeading[1];
        String year = YearWithHeading[1];
        String rentString = RentWithHeading[1];
        int rent=Integer.parseInt(rentString);
        RentBST node=new RentBST(make,model,year,rent);
        if(root==null){
            root=node;
        }
        else{
            RentBST curr=root;
            RentBST prev=null;
            while(curr!=null){
                if(node.rent>=curr.rent){
                    prev=curr;
                    curr=curr.right;
                }
                else {
                    prev=curr;
                    curr=curr.left;
                }
            }
            if(node.rent>=prev.rent){
                prev.right=node;
            }
            else{
                prev.left=node;
            }
        }
    }
    public static void RentToBST(){
        try {
            File f = new File(available);
            if (f.exists()) {

                BufferedReader br = new BufferedReader(new FileReader(f));
                String line;
                while ((line = br.readLine()) != null) {
                    InsertInBST(line);
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public static RentBST InsertionInBST(RentBST node,RentBST root){
        if(root==null){
            return node;
        }
        else if (node.rent >= root.rent) {
            root.right=InsertionInBST(node,root.right);
        }
        else{
            root.left=InsertionInBST(node,root.left);
        }
        return root;
    }
    public static RentBST deletionInBST(RentBST node,RentBST root){
        if(root==null){
            return null;
        }
        if(node.rent>root.rent){
            root.right=deletionInBST(node,root.right);
        }
        else if(node.rent<root.rent){
            root.left=deletionInBST(node,root.left);
        }
        else {
            if(root.left==null&&root.right==null){
                return null;
            }
            else if(root.left!=null&&root.right!=null){
                RentBST succParent=root;
                RentBST succ=root.right;
                while (succ.left != null) {
                    succParent = succ;
                    succ = succ.left;
                }
                if (succParent != root) {
                    succParent.left = succ.right;
                }
                else {
                    succParent.right = succ.right;
                }
                root.model=succ.model;
                root.year=succ.year;
                root.make=succ.make;
                root.rent = succ.rent;
            }
            else{
                if (root.left == null) {
                    RentBST temp = root.right;
                    return temp;
                }
                else{
                    RentBST temp = root.left;
                    return temp;
                }
            }
        }
        return root;
    }
    static void preOrder(RentBST node) {
        if (node != null) {
            System.out.print(node.rent + " ");
            preOrder(node.left);
            preOrder(node.right);
        }
    }
    static int flags=0;
    public static void RentSearch(int rent,RentBST root){
        if(root==null){
            return;
        }
        if(rent>root.rent){
            RentSearch(rent,root.right);
        }
        else if(rent<root.rent){
            RentSearch(rent,root.left);
        }
        else{
            flags=1;
            System.out.println(root.make+" "+root.model+" "+root.year+" "+root.rent);
            RentSearch(rent,root.right);
        }
    }

//    public static void main(String[] args) throws LockedOutException {
//        ReservationToList();
//        RentToBST();
//        String name = null;
//        try {
//            for (;;) {
//                int flag = 0;
//                int MenuChoice = Menu();
//                if (MenuChoice == 1) {
//                    for (; ; ) {
//                        int UserChoice = UserPanel();
//                        if (UserChoice == 1) {
//                            name = UserLogin();
//                            if (!name.equals("False")) {
//                                break;
//                            }
//                        } else if (UserChoice == 2) {
//                            name = UserRegistration();
//                        } else if (UserChoice == 3) {
//                            flag = 1;
//                            break;
//                        } else {
//                            System.out.println("Incorrect input");
//                        }
//                    }
//                    if (flag == 1) {
//                        continue;
//                    }
//                    for (; ; ) {
//                        int choice;
//                        flag = 0;
//                        System.out.println("-------------------------------------------------------------------------");
//                        System.out.println("What would you like to do?\n 1)Rent a Car\n 2)Return a Car\n 3)Search for a car\n 4)Book Reservation\n 5)Logout\n");
//                        System.out.print("Choose: ");
//                        choice = scanner.nextInt();
//                        scanner.nextLine();
//                        switch (choice) {
//                            case 1 -> RentACar(name);
//                            case 2 -> ReturnACar(name);
//                            case 3 -> {
//                                System.out.println("-------------------------------------------------------------------------");
//                                System.out.print("Do you want to search by \n(1) Name of car \n(2) Rent per day\n(3) Sorting \n\nChoose: ");
//                                int ch=scanner.nextInt();
//                                scanner.nextLine();
//                                if(ch==1){
//                                    LinearSearch();
//                                }
//                                else if(ch==2){
//                                    System.out.println("-------------------------------------------------------------------------");
//                                    System.out.println("What is your desired rent to search? ");
//                                    int rent= scanner.nextInt();
//                                    scanner.nextLine();
//                                    System.out.println("-------------------------------------------------------------------------");
//                                    System.out.println("Cars with the searched rent: ");
//                                    RentSearch(rent,root);
//                                    if(flags==0){
//                                        System.out.println("No cars found!");
//                                    }
//                                    flags=0;
//                                }
//                                else if(ch==3) {
//                                    for (; ; ) {
//                                        System.out.println("-------------------------------------------------------------------------");
//                                        System.out.println("How do you want to sort the cars: ");
//                                        System.out.println("1) By Name in Ascending order");
//                                        System.out.println("2) By Name in Descending order");
//                                        System.out.println("3) By Rent in Ascending order");
//                                        System.out.println("4) By Rent in Descending order");
//                                        System.out.println("5) Go back");
//                                        System.out.print("\nChoose: ");
//                                        int c = scanner.nextInt();
//                                        scanner.nextLine();
//                                        if (c == 1) {
//                                            NameSortAscending();
//                                        } else if (c == 2) {
//                                            NameSortDescending();
//                                        } else if (c == 3) {
//                                            RentSortAscending();
//                                        } else if (c == 4) {
//                                            RentSortDescending();
//                                        }
//                                        else if(c==5){
//                                            break;
//                                        }
//                                        else {
//                                            System.out.println("Invalid choice!");
//                                            continue;
//                                        }
//                                        try{
//                                            File f=new File(available);
//                                            BufferedReader br=new BufferedReader(new FileReader(f));
//                                            String line;
//                                            System.out.println("-------------------------------------------------------------------------");
//                                            System.out.println("Available Cars:");
//                                            while((line= br.readLine())!=null){
//                                                System.out.println(line);
//                                            }
//                                        }
//                                        catch(IOException e){
//                                            e.printStackTrace();
//                                        }
//                                    }
//                                }
//                                else {
//                                    System.out.println("Invalid choice! ");
//                                }
//                            }
//                            case 4 -> BookingReservation(name);
//                            case 5 -> {
//                                System.out.println("Logged out of User!");
//                                flag = 1;
//                            }
//                            default -> {
//                                System.out.println("Wrong choice! Try again!\n");
//                                continue;
//                            }
//                        }
//                        if (flag == 1) {
//                            break;
//                        }
//                    }
//                } else if (MenuChoice == 2) {
//                    AdminLogin();
//                    do {
//                        System.out.println("-------------------------------------------------------------------------");
//                        System.out.println("What would you like to do?\n 1)Add a New Car\n 2)Delete an Available Car\n 3)Search for a car\n 4)Display all Records of Cars\n 5)View all registered users\n 6)View Reservations\n 7)Dequeue Reservations\n 8)Logout\n");
//                        System.out.print("Choose: ");
//                        int num = scanner.nextInt();
//                        scanner.nextLine();
//                        switch (num) {
//                            case 1 -> AddCar();
//                            case 2 -> DeleteCar();
//                            case 3 -> {
//                                System.out.println("-------------------------------------------------------------------------");
//                                System.out.print("Do you want to search by \n(1) Name of car \n(2) Rent per day\n(3) Sorting \n\nChoose: ");
//                                int ch=scanner.nextInt();
//                                scanner.nextLine();
//                                if(ch==1){
//                                    LinearSearch();
//                                }
//                                else if(ch==2){
//                                    System.out.println("-------------------------------------------------------------------------");
//                                    System.out.println("What is your desired rent to search? ");
//                                    int rent= scanner.nextInt();
//                                    scanner.nextLine();
//                                    System.out.println("-------------------------------------------------------------------------");
//                                    System.out.println("Cars with the searched rent: ");
//                                    RentSearch(rent,root);
//                                    if(flags==0){
//                                        System.out.println("No cars found!");
//                                    }
//                                    flags=0;
//                                }
//                                else if(ch==3) {
//                                    for (; ; ) {
//                                        System.out.println("-------------------------------------------------------------------------");
//                                        System.out.println("How do you want to sort the cars: ");
//                                        System.out.println("1) By Name in Ascending order");
//                                        System.out.println("2) By Name in Descending order");
//                                        System.out.println("3) By Rent in Ascending order");
//                                        System.out.println("4) By Rent in Descending order");
//                                        System.out.println("5) Go back");
//                                        System.out.print("\nChoose: ");
//                                        int c = scanner.nextInt();
//                                        scanner.nextLine();
//                                        if (c == 1) {
//                                            NameSortAscending();
//                                        } else if (c == 2) {
//                                            NameSortDescending();
//                                        } else if (c == 3) {
//                                            RentSortAscending();
//                                        } else if (c == 4) {
//                                            RentSortDescending();
//                                        }
//                                        else if(c==5){
//                                            break;
//                                        }
//                                        else {
//                                            System.out.println("Invalid choice!");
//                                            continue;
//                                        }
//                                        try{
//                                            File f=new File(available);
//                                            BufferedReader br=new BufferedReader(new FileReader(f));
//                                            String line;
//                                            System.out.println("-------------------------------------------------------------------------");
//                                            System.out.println("Available Cars:");
//                                            while((line= br.readLine())!=null){
//                                                System.out.println(line);
//                                            }
//                                        }
//                                        catch(IOException e){
//                                            e.printStackTrace();
//                                        }
//                                    }
//                                }
//                                else {
//                                    System.out.println("Invalid choice! ");
//                                }
//                            }
//                            case 4 -> DisplayCars();
//                            case 5 -> DisplayUsers();
//                            case 6 -> PrintReservations();
//                            case 7 -> DequeueReservations();
//                            case 8 -> {
//                                System.out.println("Logged out of Admin!");
//                                flag = 1;
//                            }
//                            default -> System.out.println("Wrong choice! Try again!\n");
//                        }
//                    } while (flag != 1);
//                }
//                else if (MenuChoice == 3) {
//                    System.out.println("-------------------------------------------------------------------------");
//                    System.out.println("Thank you! Goodbye!");
//                    exit(1);
//                } else
//                    System.out.println("Invalid Number. Please Try Again");
//            }
//        }
//        catch (InputMismatchException e){
//            e.printStackTrace();
//        }
//    }
}

