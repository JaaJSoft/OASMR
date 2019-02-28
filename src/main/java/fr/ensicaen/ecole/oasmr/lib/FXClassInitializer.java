/*
 *  Copyright (c) 2019. CCC-Development-Team
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package fr.ensicaen.ecole.oasmr.lib;

import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.*;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class FXClassInitializer {
    private final Stage stage;
    private final Class klazz;
    private final List<JFXTextField> textFields = new ArrayList<>();

    public FXClassInitializer(Stage stage, Class klazz) {
        this.stage = stage;
        this.klazz = klazz;
    }

    public void initFromClass(ObjectCreatedHandler handler) {
        List<Parameter[]> parameters = ParamsFromClass.getParamsFromClass(klazz);

        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Label(klazz.getSimpleName()));
        content.setBody(generateInputFromParameters(parameters.get(0)));

        JFXAlert alert = new JFXAlert(stage);
        alert.setOverlayClose(true);
        alert.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
        alert.setContent(content);
        alert.initModality(Modality.APPLICATION_MODAL);

        JFXButton create = new JFXButton("Ok");
        JFXButton close = new JFXButton("Close");
        close.setOnAction(actionEvent -> {
            alert.close();
        });
        create.setOnAction(actionEvent -> {
            alert.close();
            Object[] inputs = textFields.stream().map(TextInputControl::getText).toArray();
            try {
                Object o = klazz.getConstructors()[0].newInstance(inputs);
                handler.objectCreatedHandler(o);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });
        content.setActions(create, close);
        alert.show();
    }


    private Node generateInputFromParameters(Parameter[] parameters) {
        VBox vBox = new VBox();
        for (Parameter p : parameters) {
            HBox hBox = new HBox();
            String name = p.getName();
            JFXTextField textField = (JFXTextField) getNodeFrom(p.getType());
            textFields.add(textField);
            hBox.getChildren().add(new Label(name + " : "));
            hBox.getChildren().add(textField);
            vBox.getChildren().add(hBox);
        }
        return vBox;
    }

    private Node getNodeFrom(Class klazz) {
        JFXTextField textFieldString = new JFXTextField();// TODO handle other type than string & validator
        return textFieldString;
    }
}
