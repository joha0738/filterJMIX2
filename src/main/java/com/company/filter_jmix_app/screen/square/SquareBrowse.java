package com.company.filter_jmix_app.screen.square;

import com.company.filter_jmix_app.SquareService;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.*;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.screen.*;
import com.company.filter_jmix_app.entity.Square;
import io.jmix.ui.screen.LookupComponent;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@UiController("Square.browse")
@UiDescriptor("square-browse.xml")
@LookupComponent("squaresTable")
public class SquareBrowse extends StandardLookup<Square> {
    @Autowired
    private SquareService squareService;
    @Autowired
    private TextField<Integer> cube9;
    @Autowired
    private TextField<Integer> cube8;
    @Autowired
    private TextField<Integer> cube7;
    @Autowired
    private TextField<Integer> cube6;
    @Autowired
    private TextField<Integer> cube5;
    @Autowired
    private TextField<Integer> cube4;
    @Autowired
    private TextField<Integer> cube3;
    @Autowired
    private TextField<Integer> cube2;
    @Autowired
    private CollectionContainer<Square> squaresDc;
    @Autowired
    private TextField<Integer> cube1;
    @Autowired
    private Notifications notifications;
    @Autowired
    private ValuesPicker squarePicker;
    @Autowired
    private TextField res1;
    @Autowired
    private TextField res2;
    @Autowired
    private TextField res3;
    @Autowired
    private TextField res4;
    @Autowired
    private TextField res5;
    @Autowired
    private TextField res6;
    @Autowired
    private TextField res7;
    @Autowired
    private TextField res8;
    @Autowired
    private TextField res9;
    @Autowired
    private TextField costField;
    @Autowired
    private FileStorageUploadField uploadFile;

    @Subscribe("saveBtn")
    public void onSaveBtnClick(Button.ClickEvent event) {
        try {
            Square square = squareService.saveSquare(cube1.getValue().toString(), cube2.getValue().toString(), cube3.getValue().toString(), cube4.getValue().toString(), cube5.getValue().toString(), cube6.getValue().toString(), cube7.getValue().toString(), cube8.getValue().toString(), cube9.getValue().toString());
            squaresDc.replaceItem(square);
        }catch (Exception e){
            notifications.create()
                    .withCaption("Запишите числа")
                    .show();
        }
    }

    @Subscribe("pickSquare")
    public void onPickSquareClick(Button.ClickEvent event) {
        List<String> aa = Arrays.stream(squarePicker.getValue().toString().substring(2, squarePicker.getValue().toString().length() - 1).split(" ")).collect(Collectors.toList());
        cube1.setValue(Integer.parseInt(aa.get(0)));
        cube2.setValue(Integer.parseInt(aa.get(1)));
        cube3.setValue(Integer.parseInt(aa.get(2)));
        cube4.setValue(Integer.parseInt(aa.get(3)));
        cube5.setValue(Integer.parseInt(aa.get(4)));
        cube6.setValue(Integer.parseInt(aa.get(5)));
        cube7.setValue(Integer.parseInt(aa.get(6)));
        cube8.setValue(Integer.parseInt(aa.get(7)));
        cube9.setValue(Integer.parseInt(aa.get(8)));
    }

    @Subscribe("computeSquare")
    public void onComputeSquareClick(Button.ClickEvent event) {
       try {
           List<String> result = squareService.computeSquare(cube1.getValue().toString(), cube2.getValue().toString(), cube3.getValue().toString(), cube4.getValue().toString(), cube5.getValue().toString(), cube6.getValue().toString(), cube7.getValue().toString(), cube8.getValue().toString(), cube9.getValue().toString());
           res1.setValue(result.get(1));
           res2.setValue(result.get(2));
           res3.setValue(result.get(3));
           res4.setValue(result.get(4));
           res5.setValue(result.get(5));
           res6.setValue(result.get(6));
           res7.setValue(result.get(7));
           res8.setValue(result.get(8));
           res9.setValue(result.get(9));
           costField.setValue("Cost: "+result.get(0));
       }catch (Exception e){
           notifications.create()
                   .withCaption("Запишите числа")
                   .show();
       }
    }

    @Subscribe("loadFile")
    public void onLoadFileClick(Button.ClickEvent event) {
        try{
            Square square = squareService.createSquare(cube1.getValue().toString(), cube2.getValue().toString(), cube3.getValue().toString(), cube4.getValue().toString(), cube5.getValue().toString(), cube6.getValue().toString(), cube7.getValue().toString(), cube8.getValue().toString(), cube9.getValue().toString());
            squareService.loadFile(square);
            notifications.create().withCaption("Файл Array.txt загружен на рабочий стол").show();
        } catch (Exception e){
            notifications.create().withCaption("Заполните квадрат числами").show();
        }
    }

    @Subscribe("uploadFile")
    public void onUploadFileFileUploadSucceed(SingleFileUploadField.FileUploadSucceedEvent event) {
        Square square = squareService.uploadFile(uploadFile.getFileId());
        squaresDc.replaceItem(square);
    }

}