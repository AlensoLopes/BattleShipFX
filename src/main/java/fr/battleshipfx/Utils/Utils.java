package fr.battleshipfx.Utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;

public class Utils {

    public static int[] processCoordinate(TextField textField){
        int a = textField.getText().toLowerCase().charAt(0) - 97;
        int b;
        try{
            b = Integer.parseInt(textField.getText().substring(1)) - 1;
        }catch (NumberFormatException e){
            return null;
        }
        return new int[]{a,b};
    }

    public static void showAnotherFXML(Object controller, String fxmlName, Stage stage, Object mainClass){
        FXMLLoader fxmlLoader = new FXMLLoader(mainClass.getClass().getResource(fxmlName));
        if(controller != null) fxmlLoader.setController(controller);
        try {
            stage.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getMacAdress(InetAddress address) throws SocketException {
        NetworkInterface networkInterface = NetworkInterface.getByInetAddress(address);
        byte[] mac = networkInterface.getHardwareAddress();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < mac.length; i++) {
            stringBuilder.append(String.format("%02X%s", mac[i], (i < mac.length -1) ? "-" : ""));
        }

        return stringBuilder.toString();
    }
}
