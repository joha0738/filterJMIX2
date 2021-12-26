package com.company.filter_jmix_app.screen.array;

import com.company.filter_jmix_app.ArrayService;
import io.jmix.ui.Notifications;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.*;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.screen.*;
import com.company.filter_jmix_app.entity.Array;
import io.jmix.ui.screen.LookupComponent;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

@UiController("Array_.browse")
@UiDescriptor("array-browse.xml")
@LookupComponent("arraysTable")
public class ArrayBrowse extends StandardLookup<Array> {
    @Autowired
    private ArrayService arrayService;
    @Autowired
    private ValuesPicker arrayFirst;
    @Autowired
    private ValuesPicker arraySecond;
    @Autowired
    private CollectionContainer<Array> arraysDc;
    @Autowired
    private TextField resultField;
    @Autowired
    private FileStorageUploadField uploadFile;
    @Autowired
    private Notifications notifications;

    @Subscribe("arraysTable.save")
    public void onArraysTableSave(Action.ActionPerformedEvent event) {
        Array array = arrayService.saveArr(arrayFirst.getValue().toString());
        arraysDc.replaceItem(array);
    }

    @Subscribe("computeBtn")
    public void onComputeBtnClick(Button.ClickEvent event) {
      String result = arrayService.computeArr(arrayFirst.getValue().toString().substring(1,arrayFirst.getValue().toString().length()-1)
                                             ,arraySecond.getValue().toString().substring(1,arraySecond.getValue().toString().length()-1));
      resultField.setValue(result);
    }

    @Subscribe("loadFile")
    public void onLoadFileClick(Button.ClickEvent event) {
        try {
            arrayService.loadFile(arrayFirst.getValue().toString());
            notifications.create().withCaption("Файл Array.txt загружен на рабочий стол").show();
        } catch (NullPointerException e){
            notifications.create().withCaption("Заполните Array 1").show();
        }
    }

    @Subscribe("uploadFile")
    public void onUploadFileFileUploadSucceed(SingleFileUploadField.FileUploadSucceedEvent event) {
        Array array = arrayService.uploadFile(uploadFile.getFileId());
        arraysDc.replaceItem(array);
    }
}