package com.company.filter_jmix_app;

import com.company.filter_jmix_app.entity.Array;
import com.company.filter_jmix_app.entity.Square;
import io.jmix.core.DataManager;
import io.jmix.core.Metadata;
import io.jmix.ui.upload.TemporaryStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service("square_rest_service")
public class SquareService {

    @Autowired
    private Metadata metadata;
    @Autowired
    private DataManager dataManager;
    @Autowired
    private TemporaryStorage temporaryStorage;

    public Square createSquare(String cube1,String cube2,String cube3,String cube4,String cube5,String cube6,String cube7,String cube8,String cube9){
        check(cube1,cube2,cube3,cube4,cube5,cube6,cube7,cube8,cube9);
        String l =
                " " + cube1 + " " + cube2 + " " + cube3 +
                        " " + cube4 + " " + cube5 + " " + cube6 +
                        " " + cube7 + " " + cube8 + " " + cube9;
        Square square = metadata.create(Square.class);
        square.setCube(l);
        square.setType("Square");
        return square;
    }

    public Square saveSquare(String cube1,String cube2,String cube3,String cube4,String cube5,String cube6,String cube7,String cube8,String cube9){
        Square square = createSquare(cube1,cube2,cube3,cube4,cube5,cube6,cube7,cube8,cube9);
        dataManager.save(square);
        return square;
    }

    public List<String> computeSquare(String cube1,String cube2,String cube3,String cube4,String cube5,String cube6,String cube7,String cube8,String cube9){
        List<Integer> arr = new ArrayList<>();
        List<Integer> arr2 = new ArrayList<>();
            check(cube1,cube2,cube3,cube4,cube5,cube6,cube7,cube8,cube9);
            arr.add(Integer.parseInt(cube1));
            arr.add(Integer.parseInt(cube2));
            arr.add(Integer.parseInt(cube3));
            arr.add(Integer.parseInt(cube4));
            arr.add(Integer.parseInt(cube5));
            arr.add(Integer.parseInt(cube6));
            arr.add(Integer.parseInt(cube7));
            arr.add(Integer.parseInt(cube8));
            arr.add(Integer.parseInt(cube9));
            arr = arr.stream().sorted().toList();


            int avg = 0;
            for (int i = 0; i < 8; i++) {
                avg += arr.get(i + 1) - arr.get(i);
            }
            avg = avg / 8;

            arr2.add(arr.get(0));
            for (int i = 0; i < 8; i++) {
                arr2.add(arr2.get(i) + avg);
            }
            Iterator<Integer> it = arr2.stream().sorted().collect(Collectors.toList()).iterator();
            arr2.set(1, it.next());
            arr2.set(8, it.next());
            arr2.set(3, it.next());
            arr2.set(6, it.next());
            arr2.set(4, it.next());
            arr2.set(2, it.next());
            arr2.set(5, it.next());
            arr2.set(0, it.next());
            arr2.set(7, it.next());

            int cost = Math.abs(Integer.parseInt(cube1)-arr2.get(0))+
                    Math.abs(Integer.parseInt(cube2)-arr2.get(1))+
                    Math.abs(Integer.parseInt(cube3)-arr2.get(2))+
                    Math.abs(Integer.parseInt(cube4)-arr2.get(3))+
                    Math.abs(Integer.parseInt(cube5)-arr2.get(4))+
                    Math.abs(Integer.parseInt(cube6)-arr2.get(5))+
                    Math.abs(Integer.parseInt(cube7)-arr2.get(6))+
                    Math.abs(Integer.parseInt(cube8)-arr2.get(7))+
                    Math.abs(Integer.parseInt(cube9)-arr2.get(8));

            List<String> result = Arrays.asList(String.valueOf(cost),String.valueOf(arr2.get(0)),String.valueOf(arr2.get(1)),String.valueOf(arr2.get(2)),
                                                String.valueOf(arr2.get(3)),String.valueOf(arr2.get(4)),String.valueOf(arr2.get(5)),
                                                String.valueOf(arr2.get(6)),String.valueOf(arr2.get(7)),String.valueOf(arr2.get(8)));
           return result;
    }

    public void loadFile(Square square){
        String path = System.getProperty("user.home") + "\\Desktop\\";
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(path + "Square.txt"))) {
            Square s = square;
            output.writeObject(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Square uploadFile(UUID fileId){
        File file = temporaryStorage.getFile(fileId);
        if (file != null) {
            try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(file))) {
                Square square = (Square) input.readObject();
                dataManager.save(square);
                return square;
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

    public void check(String cube1,String cube2,String cube3,String cube4,String cube5,String cube6,String cube7,String cube8,String cube9){
        int a = Integer.parseInt(cube1);
        int a2 = Integer.parseInt(cube2);
        int a3 = Integer.parseInt(cube3);
        int a4 = Integer.parseInt(cube4);
        int a5 = Integer.parseInt(cube5);
        int a6 = Integer.parseInt(cube6);
        int a7 = Integer.parseInt(cube7);
        int a8 = Integer.parseInt(cube8);
        int a9 = Integer.parseInt(cube9);
    }
}