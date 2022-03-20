package com.example.medicalreminder.local_data.room_database;

import androidx.room.TypeConverter;

import com.example.medicalreminder.pojo.DoseTime;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class RoomConverter implements Serializable {


    @TypeConverter
    public static Map<String,List<DoseTime>> fromString(String value) {
        Type mapType = new TypeToken<Map<String, String>>() {
        }.getType();
        return new Gson().fromJson(value, mapType);
    }

    @TypeConverter
    public static String fromStringMap(Map<String,List<DoseTime>> map) {
        Gson gson = new Gson();
        return gson.toJson(map);
    }

    /*static Gson gson = new Gson();

    @androidx.room.TypeConverter
    public Map<String,List<DoseTime>> stringToObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<Map<String,List<DoseTime>>>() {
        }.getType();

        return gson.fromJson(data, listType);
    }

    @androidx.room.TypeConverter
    public  String objectListToString(List<MedicineDatabase> data) {
        if (data == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String,List<DoseTime>>>() {
        }.getType();
        String json = gson.toJson(data, type);
        return json;
    }

    @androidx.room.TypeConverter
    public  MedicineDatabase stringToObject(String data) {
        if (data == null) {
            return null;
        }

        Type listType = new TypeToken<MedicineDatabase>() {
        }.getType();

        return gson.fromJson(data, listType);
    }

    @androidx.room.TypeConverter
    public  String objectToString(MedicineDatabase data) {
        if (data == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<MedicineDatabase>() {
        }.getType();
        String json = gson.toJson(data, type);
        return json;
    }
*/
}

