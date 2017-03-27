package com.romankaranchuk;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by roman on 24.3.17.
 */
public class Main {
    private static Shop shop;
    public static void main(String[] args){

        Scanner reader = new Scanner(System.in);
        shop = new Shop();

        shop.setGoods(getAvailableGoodFromFile());
        outputHelpInfo();

        while (true) {
            String inputLine = reader.nextLine();
            int inputInt = 0;
            try {
                inputInt = Integer.parseInt(inputLine);
            } catch (NumberFormatException e){
                System.out.println("Input correct number, please");
            }
            switch (inputInt) {
                case 1:
                    return;
                case 2:
                    outputAvailableGoods(shop);
                    break;
                case 3:
                    outputHelpInfo();
                    break;
                case 4:
                    outputRentUnits();
                    break;
                case 5:
                    rentUnits();
                    break;
                case 6:
                    searchGood();
                    break;
                default:
                    outputWarning();
                    break;
            }
        }

    }

    public static void outputWarning(){
        System.out.println("Input number from \"tap number\" or tap 3 for help, tap 1 for exit");
    }

    public static void outputHelpInfo(){
        Object[][] colsMenuTable = {{"---------------",
                                     "---------------"},
                                    {"tap number + Enter", "description\n"},
                                    {"1","for exit"},
                                    {"2","get current available goods info"},
                                    {"3","get help info for control"},
                                    {"4","get rented out units info"},
                                    {"5","rent units"},
                                    {"6","search of goods in the shop"},
                                    {"---------------",
                                     "---------------"}};
        System.out.println("Welcome in sport shop!");
        for (Object[] colMenuTable : colsMenuTable) {
            String output = String.format("%-20s%-20s", colMenuTable);
            System.out.println(output);
        }
    }

    public static Map<SportEquipment, Integer> getAvailableGoodFromFile(){
        Map<SportEquipment, Integer> goods = new HashMap<>();

        JSONParser parser = new JSONParser();
        Object o = new Object();
        try {
            o = parser.parse(new FileReader("/media/roman/Data/Java/SportShop/src/com/romankaranchuk/goods.json"));
        } catch (IOException | ParseException e){
            e.printStackTrace();
        }
        JSONObject jsonObject = (JSONObject) o;

        JSONArray jsonGoods = (JSONArray) jsonObject.get("sportEquipments");


        for (Object jsonGoodObject : jsonGoods) {
            JSONObject jsonGood = (JSONObject) jsonGoodObject;

            String title = (String) jsonGood.get("title");
            int price = (int) (long) jsonGood.get("price");
            int totalCount = (int) (long) jsonGood.get("total count");

            goods.put(new SportEquipment(title, price), totalCount);
        }
        return goods;
    }


    public static void outputAvailableGoods(Shop shop){
        System.out.println(shop);
    }

    public static ArrayList<SportEquipment> getRentUnitsFromFile(){
        ArrayList<SportEquipment> sportEquipments = new ArrayList<>();
        JSONParser parser = new JSONParser();
        Object o = new Object();
        try {
            o = parser.parse(new FileReader("/media/roman/Data/Java/tasks/src/tests/epam_task_test/rentUnits.json"));
        } catch (ParseException | IOException e){
            e.printStackTrace();
        }
        JSONObject jsonObject = (JSONObject) o;
        JSONArray jsonGoods = (JSONArray) jsonObject.get("rent units");

        for (Object jsonGoodObject : jsonGoods) {
            JSONObject jsonGood = (JSONObject) jsonGoodObject;

            String title = (String) jsonGood.get("title");
            int price = (int) (long) jsonGood.get("price");

            sportEquipments.add(new SportEquipment(title,price));
        }
        return sportEquipments;
    }


    public static void outputRentUnits(){
        RentUnit rentUnit = new RentUnit();
        rentUnit.setUnits(getRentUnitsFromFile());
        System.out.println(rentUnit);
    }

    public static void updateGoodsInFile(){
        Iterator it = shop.getGoods().entrySet().iterator();
        JSONArray jsonArray = new JSONArray();
        JSONObject equipObj = new JSONObject();
        while(it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            SportEquipment sportEquipment = (SportEquipment) pair.getKey();

            JSONObject obj = new JSONObject();
            obj.put("title", sportEquipment.getTitle());
            obj.put("price", sportEquipment.getPrice());
            obj.put("total count", pair.getValue());


            jsonArray.add(obj);
        }
        equipObj.put("sportEquipments", jsonArray);
        try (FileWriter file = new FileWriter("/media/roman/Data/Java/SportShop/src/com/romankaranchuk/goods.json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            file.write(gson.toJson(equipObj));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void searchGood(){
        System.out.println("Searching of good\nInput title of good:");
        Scanner reader = new Scanner(System.in);
        String titleGood = reader.nextLine();

        Map<SportEquipment, Integer> curGoods = shop.getGoods();
        Iterator it = curGoods.entrySet().iterator();

        boolean isFound = false;
        int countGood = 0, priceGood = 0;
        while(it.hasNext()){
            Map.Entry pair = (Map.Entry) it.next();
            SportEquipment sportEquipment = (SportEquipment) pair.getKey();
            if (sportEquipment.getTitle().equals(titleGood)){
                countGood = (int) pair.getValue();
                priceGood = sportEquipment.getPrice();
                isFound = true;
            }
        }
        if (isFound){
            System.out.println("Your good is available!\nCount is " +
                    countGood + " items\n" +
                    "Price is "+priceGood + " for one item");
        } else {
            System.out.println("Sorry, your good is absent.");
        }
    }

    public static void rentUnits(){
        Map<String, String> queriesLines = new HashMap<>();
        queriesLines.put("title","Input title of good:");
        queriesLines.put("count","Input count of goods:");
        Scanner reader = new Scanner(System.in);

        System.out.println(queriesLines.get("title"));
        String good = reader.nextLine();

        System.out.println(queriesLines.get("count"));
        int rentCount = 0;
        try {
            rentCount = Integer.parseInt(reader.nextLine());
        } catch (NumberFormatException e){
        }
        Map<SportEquipment, Integer> curGoods = shop.getGoods();
        Iterator it = curGoods.entrySet().iterator();


        boolean rentSuccess = false;
        while(it.hasNext()){
            Map.Entry pair = (Map.Entry) it.next();
            SportEquipment sportEquipment = (SportEquipment) pair.getKey();
            int count = (int)pair.getValue();
            if (sportEquipment.getTitle().equals(good) && count >= rentCount){
                curGoods.put(sportEquipment, count-rentCount);
                rentSuccess = true;
            }
        }

        if (rentSuccess){
            updateGoodsInFile();
            System.out.println("Rent is success completed!");
        } else {
            System.out.println("Sorry, rent is not completed, check your input data or availability of good!");
        }
    }
}
