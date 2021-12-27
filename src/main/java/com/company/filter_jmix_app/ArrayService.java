package com.company.filter_jmix_app;

import com.company.filter_jmix_app.entity.Array;
import io.jmix.core.DataManager;
import io.jmix.core.Metadata;
import io.jmix.ui.upload.TemporaryStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service("array_rest_service")
public class ArrayService {
    @Autowired
    private Metadata metadata;
    @Autowired
    private DataManager dataManager;
    @Autowired
    private TemporaryStorage temporaryStorage;

    public Array createArr(String arrayFirst){
        String line = arrayFirst;
        Array array = metadata.create(Array.class);
        array.setString(line.substring(1, line.length() - 1));
        array.setType("Array");
        return array;
    }

    public Array saveArr(String arrayFirst) {
        Array array = createArr(arrayFirst);
        dataManager.save(array);
        return array;
    }

    public String computeArr(List<String> arrayFirst, List<String> arraySecond) {
        List<String> result = new ArrayList<>();

//        String firsta = arrayFirst;
//        List<String> o = Arrays.stream(firsta.split(", ")).toList();
        List<String> o = arrayFirst;
//        String seconda = arraySecond;
//        List<String> d = Arrays.stream(seconda.split(", ")).toList();
        List<String> d = arraySecond;
        for (String f : o) {
            for (String a : d) {
                if (a.contains(f))
                    result.add(f);
            }
        }
        String resultString = result.stream().distinct().sorted().collect(Collectors.joining(", "));
        return resultString;
    }

    public void loadFile(String array) {
        String path = System.getProperty("user.home") + "\\Desktop\\";
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(path + "Array.txt"))) {
            Array s = createArr(array);
            output.writeObject(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Array uploadFile(UUID fileId) {
        File file = temporaryStorage.getFile(fileId);
        if (file != null) {
            try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(file))) {
                Array array = (Array) input.readObject();
                dataManager.save(array);
                return array;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
