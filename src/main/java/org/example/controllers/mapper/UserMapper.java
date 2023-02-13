package org.example.controllers.mapper;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import org.example.models.User;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserMapper<T extends User> {
    public List<T> mapUsersFromCSV(InputStream inputStream) {
        CsvToBean csvToBean = new CsvToBean();
        csvToBean.setCsvReader(new CSVReader(new InputStreamReader(inputStream)));
        csvToBean.setMappingStrategy(getMappingStrategy());
        return csvToBean.parse();
    }

    private HeaderColumnNameTranslateMappingStrategy<User> getMappingStrategy() {
        Map<String, String> mapping = getUserMapping();
        HeaderColumnNameTranslateMappingStrategy<User> strategy = new HeaderColumnNameTranslateMappingStrategy<>();
        strategy.setType(User.class);
        strategy.setColumnMapping(mapping);
        return strategy;
    }

    private Map<String, String> getUserMapping() {
        Map<String, String> map = new
                HashMap<String, String>();
        map.put("password", "password");
        map.put("role", "role");
        map.put("firstName", "first name");
        map.put("secondName", "second name");
        map.put("birthDate", "birth date");
        map.put("phone", "phone");
        map.put("email", "email");
        return map;
    }
}
