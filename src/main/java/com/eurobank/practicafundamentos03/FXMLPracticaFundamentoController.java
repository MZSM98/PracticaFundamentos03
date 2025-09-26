package com.eurobank.practicafundamentos03;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.IntegerStringConverter;

public class FXMLPracticaFundamentoController implements Initializable {
    
    private static final int MIN_ANTIGUEDAD = 0;
    private static final int MAX_ANTIGUEDAD = 100;
    private static final double MIN_IMPORTE = 100.0;
    private static final double MAX_IMPORTE = 1000000.0;
    private static final double UMBRAL_IMPORTE = 100000.0;
    
    @FXML
    Button botonCalcular;
    
    @FXML
    TextField textDescuento;
    
    @FXML
    TextField textImporte;
    
    @FXML
    Spinner<Integer> spinnerAntiguedad;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        textImporte.setTextFormatter(new TextFormatter<>(change -> {
            String text = change.getControlNewText();
            if (text.matches("\\d*\\.?\\d*")) {
                return change;
            }
            return null;
        }));
        
        spinnerAntiguedad.getEditor().setTextFormatter(new TextFormatter<>(
            new IntegerStringConverter(), 0, change -> {
                String text = change.getControlNewText();
                if (text.matches("\\d*")) {
                    return change;
                }
                return null;
            }));
    }
    
    @FXML
    private void calcularDescuento() {
        try {
            String importeText = textImporte.getText().trim();
            if (importeText.isEmpty()) {
                mostrarError("Debe ingresar un importe");
                return;
            }
            
            double importe = Double.parseDouble(importeText);
            Integer antiguedadValue = spinnerAntiguedad.getValue();
            
            if (antiguedadValue == null) {
                mostrarError("Debe ingresar una antigüedad válida");
                return;
            }
            
            int antiguedad = antiguedadValue;
            
            if (antiguedad < MIN_ANTIGUEDAD || antiguedad > MAX_ANTIGUEDAD) {
                mostrarError("La antigüedad debe estar entre " + MIN_ANTIGUEDAD + " y " + MAX_ANTIGUEDAD + " años");
                return;
            }
            
            if (importe < MIN_IMPORTE || importe > MAX_IMPORTE) {
                mostrarError("El importe debe estar entre $" + (int)MIN_IMPORTE + " y $" + (int)MAX_IMPORTE);
                return;
            }
            
            double porcentajeDescuento = determinarDescuento(antiguedad, importe);
            
            if (porcentajeDescuento == 0) {
                textDescuento.setText("No aplica descuento");
            } else {
                textDescuento.setText(porcentajeDescuento + "%");
            }
            
        } catch (NumberFormatException e) {
            mostrarError("El importe debe ser un número válido");
        }
    }
    
    private double determinarDescuento(int antiguedad, double importe) {
        if (antiguedad < 1) {
            return 0.0;
        }
        
        if (antiguedad >= 1 && antiguedad <= 5) {
            return importe < UMBRAL_IMPORTE ? 5.0 : 10.0;
        }
        
        if (antiguedad >= 6 && antiguedad <= 10) {
            return importe < UMBRAL_IMPORTE ? 8.0 : 12.0;
        }
        
        if (antiguedad > 10) {
            return 15.0;
        }
        
        return 0.0;
    }
    
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}