package myApp.michal.crossNote.Converter;

import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ObjectConverter {

    private Logger logger = Logger.getAnonymousLogger();

    public Object stringToObject(String string){
        byte[] bytes = Base64.decode(string, 0);
        Object object = new Object();
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(bytes));
            object = objectInputStream.readObject();
        } catch (Exception e){
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
        return object;
    }

    public String objectToString(Object object){
        String encoded = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            objectOutputStream.close();
            encoded = Base64.encodeToString(byteArrayOutputStream.toByteArray(), 0);
        } catch (IOException e){
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
        return encoded;
    }
}

