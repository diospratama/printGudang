package net.vibrac.escpospi.bus;

import com.pi4j.io.serial.Serial;
import com.pi4j.io.serial.SerialFactory;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BusSerial implements BusConnexion {

    private final Serial printer = SerialFactory.createInstance();

    @Override
    public void open(){
        this.open(Serial.DEFAULT_COM_PORT, 9600);
    }

    @Override
    public void open(String address, int baudRate){
        try {
            printer.open(address, baudRate);
        } catch (IOException ex) {
            Logger.getLogger(BusSerial.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void write(byte[] command){
        for (byte b : command){
            try {
                printer.write(b);
            } catch (IllegalStateException ex) {
                Logger.getLogger(BusSerial.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(BusSerial.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void close(){
        try {
            printer.close();
        } catch (IllegalStateException ex) {
            Logger.getLogger(BusSerial.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BusSerial.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
