package util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JFormattedTextField.AbstractFormatter;
 
/**
 * 
 * @author Simone
 * Formato data per il datePicker
 */
public class FormatoData extends AbstractFormatter {
 
    private String formatoData = "dd-MM-yyyy";
    private SimpleDateFormat formattazioneData = new SimpleDateFormat(formatoData);
     
    @Override
    public Object stringToValue(String text) throws ParseException {
        return formattazioneData.parseObject(text);
    }
 
    @Override
    public String valueToString(Object value) throws ParseException {
        if (value != null) {
            Calendar cal = (Calendar) value;
            return formattazioneData.format(cal.getTime());
        }
         
        return "";
    }
 
}