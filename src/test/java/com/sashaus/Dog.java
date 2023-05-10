package com.sashaus;

import java.util.List;

public class Dog {
    public String name;
    public String breed;
    public Integer age;
    public Integer ageInHumanYears;
    public List<String> characterValues;
    public ForeignObjects foreignObjectsInDogsStomach;

    public static class ForeignObjects {
        public Integer piecesOfWallpaper;
        public Integer smallToy;
        public Boolean dogFood;
        public Boolean trashFromTheStreet;
    }
}
