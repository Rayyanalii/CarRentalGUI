
package org.example.carrentalgui;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Code {
    static class LockedOutException extends Exception {
        public LockedOutException(String error) {
            super(error);
        }
    }

    public static String available = "C:\\Users\\rayya\\Desktop\\AvailCars.txt";
    public static String users = "C:\\Users\\rayya\\Desktop\\Users.txt";
    public static String temp = "C:\\Users\\rayya\\Desktop\\temp.txt";
    public static String reservations = "C:\\Users\\rayya\\Desktop\\Reservations.txt";
    static Scanner scanner = new Scanner(System.in);
    static int LocketOutCount = 0;

    public static int Menu() {
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("Welcome To Car Rental System\n");
        System.out.println("Press (1) for User Panel");
        System.out.println("Press (2) for Admin Panel");
        System.out.println("Press (3) to Quit");
        System.out.println("Choose what to do:");
        return scanner.nextInt();
    }
    public static String UserLogin(String name, String pass) {
        int flag = 0;
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
                            if (LocketOutCount == 3) {
                                return "Locked";
                            }
                            return "Invalid";
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Login";
    }

    public static String UserRegistration(String name, String pass1, String pass2) {
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

    public static void RentACar(String[] data, String name, String input) {
        int days = Integer.parseInt(input);
        String buffer = "%s %s %s %s".formatted(data[0], data[1], data[2], data[3]);
        String buffer2;
        int rent = Integer.parseInt(data[3]);
        int Total = rent * days;
        try {
            String filename = "C:\\Users\\rayya\\Desktop\\%s RentedCars.txt".formatted(name);
            File f1 = new File(temp);
            File f2 = new File(available);
            File f3 = new File(filename);
            f1.createNewFile();
            if (!f3.exists()) {
                f3.createNewFile();
            }
            BufferedReader br1 = new BufferedReader(new FileReader(f2));
            BufferedWriter bw = new BufferedWriter(new FileWriter(f1));
            String line;
            while ((line = br1.readLine()) != null) {
                bw.write(line);
                bw.newLine();
            }
            bw.close();
            f2.delete();
            f2.createNewFile();
            BufferedReader br = new BufferedReader(new FileReader(f1));
            BufferedWriter bw1 = new BufferedWriter(new FileWriter(f3, true));
            bw = new BufferedWriter(new FileWriter(f2));
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

    public static void ReturnACar(String data[], String name) {
        String filename = "C:\\Users\\rayya\\Desktop\\%s RentedCars.txt".formatted(name);
        String buffer = "%s %s %s %s %s %s".formatted(data[0], data[1], data[2], data[3], data[4], data[5]);
        String buffer2;
        try {
            File f1 = new File(available);
            File f2 = new File(temp);
            File f3 = new File(filename);
            BufferedReader br = new BufferedReader(new FileReader(f3));
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(f2));
            String line;
            while ((line = br.readLine()) != null) {
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
            BufferedWriter bw1 = new BufferedWriter(new FileWriter(f1, true));
            if (!f1.exists()) {
                f1.createNewFile();
            }
            br = new BufferedReader(new FileReader(f2));
            BufferedWriter bw2 = new BufferedWriter(new FileWriter(f3));
            while ((buffer2 = br.readLine()) != null) {
                if (buffer.equals(buffer2)) {
                } else {
                    bw2.write(buffer2);
                    bw2.newLine();
                }
            }
            String car = "%s %s %s %s".formatted(data[0], data[1], data[2], data[3]);
            bw1.write(car);
            bw1.newLine();
            bw1.close();
            bw2.close();
            br.close();
            f2.delete();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.getMessage();
        }
    }

    public static boolean AdminLogin(String name, String pass) {
        String admin = "admin", password = "pass";
        if (name.equals(admin) && pass.equals(password)) {
            return true;
        } else {
            return false;
        }
    }

    public static void AddCar(String make, String model, String year, String rent) {
        String buffer;
        try {
            File f = new File(available);
            if (!f.exists()) {
                f.createNewFile();
            }
            FileWriter fw = new FileWriter(f, true);
            BufferedWriter bw = new BufferedWriter(fw);
            buffer = "%s %s %s %s".formatted(make, model, year, rent);
            bw.write(buffer);
            bw.newLine();
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void DeleteCar(String[] data, File file) throws ArrayIndexOutOfBoundsException {
        String buffer;
        try {
            File f2 = new File(temp);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new FileWriter(f2));
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
            writer.close();
            reader.close();
            file.delete();
            String[] split;
            BufferedReader br = new BufferedReader(new FileReader(f2));
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            if (file.equals(new File(available))) {
                while ((buffer = br.readLine()) != null) {
                    split = buffer.split(" ");
                    if (split[0].equalsIgnoreCase(data[0]) && split[1].equalsIgnoreCase(data[1]) && split[2].equalsIgnoreCase(data[2]) && split[3].equalsIgnoreCase(data[3])) {
                    } else {
                        bw.write(buffer);
                        bw.newLine();
                    }
                }
                bw.close();
                br.close();
                f2.delete();
            } else if (file.equals(new File(reservations))) {
                while ((buffer = br.readLine()) != null) {
                    split = buffer.split(" ");
                    if (split[0].equalsIgnoreCase(data[0]) && split[1].equalsIgnoreCase(data[1]) && split[2].equalsIgnoreCase(data[2]) && split[3].equalsIgnoreCase(data[3]) && split[4].equalsIgnoreCase(data[4])) {
                    } else {
                        bw.write(buffer);
                        bw.newLine();
                    }
                }
                bw.close();
                br.close();
                f2.delete();
                File file1 = new File(available);
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file1, true));
                buffer = "%s %s %s %s".formatted(data[1], data[2], data[3], data[4]);
                bufferedWriter.write(buffer);
                bufferedWriter.newLine();
                bufferedWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void NameSortAscending(File file) {
        try {
            if (!file.exists()) {
            } else {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                ArrayList<String> unsorted = new ArrayList<>();
                while ((line = br.readLine()) != null) {
                    if (unsorted.isEmpty()) {
                        unsorted.add(line);
                        continue;
                    }
                    unsorted.add(line);
                    for (int i = unsorted.size() - 1; i > 0 && unsorted.get(i).compareToIgnoreCase(unsorted.get(i - 1)) < 0; i--) {
                        String temp = unsorted.get(i);
                        unsorted.set(i, unsorted.get(i - 1));
                        unsorted.set(i - 1, temp);
                    }
                }
                br.close();
                file.delete();
                if (!file.exists()) {
                    file.createNewFile();
                }
                BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                for (int i = 0; i < unsorted.size(); i++) {
                    bw.write(unsorted.get(i));
                    bw.newLine();
                }
                bw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void NameSortDescending(File file) {
        try {
            if (!file.exists()) {
            } else {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                ArrayList<String> unsorted = new ArrayList<>();
                while ((line = br.readLine()) != null) {
                    if (unsorted.isEmpty()) {
                        unsorted.add(line);
                        continue;
                    }
                    unsorted.add(line);
                    for (int i = unsorted.size() - 1; i > 0 && unsorted.get(i).compareToIgnoreCase(unsorted.get(i - 1)) > 0; i--) {
                        String temp = unsorted.get(i);
                        unsorted.set(i, unsorted.get(i - 1));
                        unsorted.set(i - 1, temp);
                    }
                }
                br.close();
                file.delete();
                if (!file.exists()) {
                    file.createNewFile();
                }
                BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                for (int i = 0; i < unsorted.size(); i++) {
                    bw.write(unsorted.get(i));
                    bw.newLine();
                }
                bw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void RentSortAscending(File file) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            ArrayList<String> unsorted = new ArrayList<>();
            ArrayList<Integer> rent = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                String[] splitwithHeadings = line.split(" ");
                int rentperday = Integer.parseInt(splitwithHeadings[3]);
                if (unsorted.isEmpty()) {
                    unsorted.add(line);
                    rent.add(rentperday);
                    continue;
                }
                unsorted.add(line);
                rent.add(rentperday);
                for (int i = rent.size() - 1; i > 0 && rent.get(i) < rent.get(i - 1); i--) {
                    String temp = unsorted.get(i);
                    unsorted.set(i, unsorted.get(i - 1));
                    unsorted.set(i - 1, temp);
                    int temprent = rent.get(i);
                    rent.set(i, rent.get(i - 1));
                    rent.set(i - 1, temprent);
                }
            }
            br.close();
            file.delete();
            ;
            if (!file.exists()) {
                file.createNewFile();
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            for (int i = 0; i < unsorted.size(); i++) {
                bw.write(unsorted.get(i));
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void RentSortDescending(File file) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            ArrayList<String> unsorted = new ArrayList<>();
            ArrayList<Integer> rent = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                String[] splitwithHeadings = line.split(" ");
                int rentperday = Integer.parseInt(splitwithHeadings[3]);
                if (unsorted.isEmpty()) {
                    unsorted.add(line);
                    rent.add(rentperday);
                    continue;
                }
                unsorted.add(line);
                rent.add(rentperday);
                for (int i = rent.size() - 1; i > 0 && rent.get(i) > rent.get(i - 1); i--) {
                    String temp = unsorted.get(i);
                    unsorted.set(i, unsorted.get(i - 1));
                    unsorted.set(i - 1, temp);
                    int temprent = rent.get(i);
                    rent.set(i, rent.get(i - 1));
                    rent.set(i - 1, temprent);
                }
            }
            br.close();
            file.delete();
            if (!file.exists()) {
                file.createNewFile();
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            for (int i = 0; i < unsorted.size(); i++) {
                bw.write(unsorted.get(i));
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void BookingReservation(String[] data, String name, String days) {
        int intdays = Integer.parseInt(days);
        int intrent = Integer.parseInt(data[3]);
        int total = intdays * intrent;
        String stringtotal = String.valueOf(total);
        try {
            File avail = new File(available);
            File tem = new File(temp);
            File f = new File(reservations);
            if (!f.exists()) {
                f.createNewFile();
            }
            String b;
            String car = "%s %s %s %s".formatted(data[0], data[1], data[2], data[3]);
            BufferedReader availreader = new BufferedReader(new FileReader(avail));
            BufferedWriter tempwriter = new BufferedWriter(new FileWriter(tem));
            while ((b = availreader.readLine()) != null) {
                if (b.equalsIgnoreCase(car)) {
                } else {
                    tempwriter.write(b);
                    tempwriter.newLine();
                }
            }
            availreader.close();
            tempwriter.close();
            BufferedWriter availwriter = new BufferedWriter(new FileWriter(avail));
            BufferedReader tempreader = new BufferedReader(new FileReader(tem));
            while ((b = tempreader.readLine()) != null) {
                availwriter.write(b);
                availwriter.newLine();
            }
            tempreader.close();
            availwriter.close();
            tem.delete();
            BufferedWriter bw = new BufferedWriter(new FileWriter(f, true));
            String buffer = "%s %s %s %s %s %s %s".formatted(name, data[0], data[1], data[2], data[3], days, stringtotal);
            bw.write(buffer);
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
