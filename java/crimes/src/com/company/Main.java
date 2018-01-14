package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Main {

    public static void main(String[] args) {
        List<Crime> crimes = readCrimesFromCSV("/home/s0ck/Documents/enjoy/Crime_Data_from_2010_to_Present1.csv");
        List<String> districts = new ArrayList<>();
        List<String> weapons = new ArrayList<>();
        List<String> areas = new ArrayList<>();
        // let's get all districts and weapons
        for (Crime c: crimes){
            if(!c.getReporting_District().isEmpty()){
                districts.add(c.getReporting_District());
            }
            if(!c.getWeapon_used().isEmpty()){
                weapons.add(c.getWeapon_used());
            }
            if(!c.getArea_ID().isEmpty()){
                areas.add(c.getArea_ID());
            }
        }

        //Reduce duplicates
        districts = new ArrayList<>(new HashSet<>(districts));
        weapons = new ArrayList<>(new HashSet<>(weapons));
        areas = new ArrayList<>(new HashSet<>(areas));

        //printComittedCrimesDistrictWeapon(districts, weapons, crimes);
        printMostConflictingZones(areas, crimes);
        



    }
    private static void printComittedCrimesDistrictWeapon(List<String> districts, List<String> weapons, List<Crime> crimes){
        List<String> crime_codes;
        for (String district: districts){
            for(String weapon: weapons){
                crime_codes = new ArrayList<>();
                for (Crime c: crimes){
                    if(c.getReporting_District().trim().equals(district.trim()) && c.getWeapon_used().trim().equals(weapon.trim())){
                        crime_codes.add(c.getDR_Number());
                    }
                }
                if(!crime_codes.isEmpty()){
                    System.out.println("For district: "+district+" and weapon: "+weapon);
                    for(String code: crime_codes){
                        System.out.println(code);
                    }
                }

            }
        }

    }

    private static void printMostConflictingZones(List<String> areas, List<Crime> crimes){
        List<District_popularity> districts_popularity = new ArrayList<>();
        for (String area: areas){
            District_popularity district_popularity = new District_popularity(area);
            for (Crime c: crimes){
                if(c.getArea_ID().trim().equals(area.trim())){
                    Integer crimes_comitted = district_popularity.getPopularity();
                    crimes_comitted = crimes_comitted+1;
                    district_popularity.setPopularity(crimes_comitted);
                }
            }
            districts_popularity.add(district_popularity);
        }
        Collections.sort(districts_popularity);
        Collections.sort(districts_popularity, Collections.reverseOrder());
        Integer end = 3;
        if(districts_popularity.size()<3){
            end = districts_popularity.size();
        }
        for (District_popularity a: districts_popularity.subList(0,end)){
            System.out.println("Area: "+a.getArea()+ " with committed crimes: "+ a.getPopularity());
        }

    }

    private static List<Crime> readCrimesFromCSV(String fileName){
        List<Crime> crimes = new ArrayList<>();
        Path pathToFile = Paths.get(fileName);

        // create an instance of BufferedReader
        // using try with resource
        try(BufferedReader br = Files.newBufferedReader(pathToFile)){
            //read the first line from the text file
            String line = br.readLine();

            // loop until all lines are read
            while (line != null){
                //use string.split to load a string array with the values from
                // each line of
                // the file, using a comma as the delimiter
                String[] attributes = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");;

                // Check if its the header
                if(!tryParseInt(attributes[0])){
                    // read next line before looping
                    //if end of file reached, line would be null
                    line = br.readLine();
                }else{
                    // Check we have enough variables
                    if(attributes.length>=26){
                        Crime crime = createCrime(attributes);

                        // adding crime into ArrayList
                        crimes.add(crime);

                        // read next line before looping
                        //if end of file reached, line would be null
                        line = br.readLine();
                    }

                }
            }

        }catch (IOException ioe){
            ioe.printStackTrace();
        }

        return crimes;
    }

    private static boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static Crime createCrime(String[] metadata){
        return new Crime(metadata[0],metadata[1],metadata[2],metadata[3],metadata[4],metadata[5],metadata[6],metadata[7],metadata[8],metadata[9],metadata[10],metadata[11],metadata[12],metadata[13],metadata[14],metadata[15],metadata[16],metadata[17],metadata[18],metadata[19],metadata[20],metadata[21],metadata[22],metadata[23],metadata[24],metadata[25]);
    }

}

class Crime {
    private String DR_Number;
    private String Date_Reported;
    private String Date_occurred;
    private String Time_occurred;
    private String Area_ID;
    private String Area_Name;
    private String Reporting_District;
    private String Crime_code;
    private String Crime_code_description;
    private String M0_codes;
    private String Victim_age;
    private String Victim_sex;
    private String Visctim_descent;
    private String Premise_Code;
    private String Premise_Description;
    private String Weapon_used;
    private String Weapon_des;
    private String Status_code;
    private String Status_desc;
    private String Crime_code_1;
    private String Crime_code_2;
    private String Crime_code_3;
    private String Crime_code_4;
    private String Address;
    private String Cross_street;
    private String Location;


    Crime(String DR_Number, String date_Reported, String date_occurred, String time_occurred, String area_ID, String area_Name, String reporting_District, String crime_code, String crime_code_description, String m0_codes, String victim_age, String victim_sex, String visctim_descent, String premise_Code, String premise_Description, String weapon_used, String weapon_des, String status_code, String status_desc, String crime_code_1, String crime_code_2, String crime_code_3, String crime_code_4, String address, String cross_street, String location) {
        this.DR_Number = DR_Number;
        this.Date_Reported = date_Reported;
        this.Date_occurred = date_occurred;
        this.Time_occurred = time_occurred;
        this.Area_ID = area_ID;
        this.Area_Name = area_Name;
        this.Reporting_District = reporting_District;
        this.Crime_code = crime_code;
        this.Crime_code_description = crime_code_description;
        this.M0_codes = m0_codes;
        this.Victim_age = victim_age;
        this.Victim_sex = victim_sex;
        this.Visctim_descent = visctim_descent;
        this.Premise_Code = premise_Code;
        this.Premise_Description = premise_Description;
        this.Weapon_used = weapon_used;
        this.Weapon_des = weapon_des;
        this.Status_code = status_code;
        this.Status_desc = status_desc;
        this.Crime_code_1 = crime_code_1;
        this.Crime_code_2 = crime_code_2;
        this.Crime_code_3 = crime_code_3;
        this.Crime_code_4 = crime_code_4;
        this.Address = address;
        this.Cross_street = cross_street;
        this.Location = location;
    }

    public String getDR_Number() {
        return DR_Number;
    }

    public String getDate_Reported() {
        return Date_Reported;
    }

    public String getDate_occurred() {
        return Date_occurred;
    }

    public String getTime_occurred() {
        return Time_occurred;
    }

    public String getArea_ID() {
        return Area_ID;
    }

    public String getArea_Name() {
        return Area_Name;
    }

    public String getReporting_District() {
        return Reporting_District;
    }

    public String getCrime_code() {
        return Crime_code;
    }

    public String getCrime_code_description() {
        return Crime_code_description;
    }

    public String getM0_codes() {
        return M0_codes;
    }

    public String getVictim_age() {
        return Victim_age;
    }

    public String getVictim_sex() {
        return Victim_sex;
    }

    public String getVisctim_descent() {
        return Visctim_descent;
    }

    public String getPremise_Code() {
        return Premise_Code;
    }

    public String getPremise_Description() {
        return Premise_Description;
    }

    public String getWeapon_used() {
        return Weapon_used;
    }

    public String getWeapon_des() {
        return Weapon_des;
    }

    public String getStatus_code() {
        return Status_code;
    }

    public String getStatus_desc() {
        return Status_desc;
    }

    public String getCrime_code_1() {
        return Crime_code_1;
    }

    public String getCrime_code_2() {
        return Crime_code_2;
    }

    public String getCrime_code_3() {
        return Crime_code_3;
    }

    public String getCrime_code_4() {
        return Crime_code_4;
    }

    public String getAddress() {
        return Address;
    }

    public String getCross_street() {
        return Cross_street;
    }

    public String getLocation() {
        return Location;
    }

    public void setDR_Number(String DR_Number) {
        this.DR_Number = DR_Number;
    }

    public void setDate_Reported(String date_Reported) {
        Date_Reported = date_Reported;
    }

    public void setDate_occurred(String date_occurred) {
        Date_occurred = date_occurred;
    }

    public void setTime_occurred(String time_occurred) {
        Time_occurred = time_occurred;
    }

    public void setArea_ID(String area_ID) {
        Area_ID = area_ID;
    }

    public void setArea_Name(String area_Name) {
        Area_Name = area_Name;
    }

    public void setReporting_District(String reporting_District) {
        Reporting_District = reporting_District;
    }

    public void setCrime_code(String crime_code) {
        Crime_code = crime_code;
    }

    public void setCrime_code_description(String crime_code_description) {
        Crime_code_description = crime_code_description;
    }

    public void setM0_codes(String m0_codes) {
        M0_codes = m0_codes;
    }

    public void setVictim_age(String victim_age) {
        Victim_age = victim_age;
    }

    public void setVictim_sex(String victim_sex) {
        Victim_sex = victim_sex;
    }

    public void setVisctim_descent(String visctim_descent) {
        Visctim_descent = visctim_descent;
    }

    public void setPremise_Code(String premise_Code) {
        Premise_Code = premise_Code;
    }

    public void setPremise_Description(String premise_Description) {
        Premise_Description = premise_Description;
    }

    public void setWeapon_used(String weapon_used) {
        Weapon_used = weapon_used;
    }

    public void setWeapon_des(String weapon_des) {
        Weapon_des = weapon_des;
    }

    public void setStatus_code(String status_code) {
        Status_code = status_code;
    }

    public void setStatus_desc(String status_desc) {
        Status_desc = status_desc;
    }

    public void setCrime_code_1(String crime_code_1) {
        Crime_code_1 = crime_code_1;
    }

    public void setCrime_code_2(String crime_code_2) {
        Crime_code_2 = crime_code_2;
    }

    public void setCrime_code_3(String crime_code_3) {
        Crime_code_3 = crime_code_3;
    }

    public void setCrime_code_4(String crime_code_4) {
        Crime_code_4 = crime_code_4;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public void setCross_street(String cross_street) {
        Cross_street = cross_street;
    }

    public void setLocation(String location) {
        Location = location;
    }


}

class District_popularity implements Comparable<District_popularity>{
    String area;
    Integer popularity;

    public District_popularity(String area) {
        this.area = area;
        this.popularity = 0;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String crime) {
        this.area = crime;
    }

    public Integer getPopularity() {
        return popularity;
    }

    public void setPopularity(Integer popularity) {
        this.popularity = popularity;
    }

    @Override
    public int compareTo(District_popularity district_popularity) {
        return popularity.compareTo(district_popularity.popularity);
    }
}