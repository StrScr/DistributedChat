/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package distribucionchat;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

/**
 *
 * @author Juan Leonardo
 */
public class Cliente {    
    public String name;
    private Mensaje mensaje = null; 
    private ClienteGUI cGUI = null;
    private int myid;
    
    Cliente() {}
    Cliente(ClienteGUI cGUI) {
        this.cGUI = cGUI;
    }
    
    public void iniciar(String username) {
        try {
            Registry myRegistry = LocateRegistry.getRegistry("127.0.0.1", 1099);                         
            mensaje = (Mensaje) myRegistry.lookup("miMensaje");                        
            if (this.cGUI == null){
                myid = mensaje.registrar(new MensajeImpl(username));
            }else{
                myid = mensaje.registrar(new MensajeImpl(username, this.cGUI));
            }
            System.out.println("My id is " + myid);
        } catch (RemoteException | NotBoundException ex) {
            System.out.println("");
        }
    }
    public void enviarMensaje(String mensaje, int toid) {
        try {
            this.mensaje.publicar(myid, toid ,mensaje);
        } catch (RemoteException ex) {
            System.out.println("");
        }
    }
     
    private void prueba(){        
            Scanner scn = new Scanner(System.in);
            String texto = "";
            while (true) {
                System.out.print("Ingrese texto: ");                 
                texto = scn.nextLine();            
                try {
                    mensaje.publicar(myid,0,texto);
                } catch (RemoteException ex) {
                    System.out.println("");
                }
            }                           
    }  
        
    public static void main(String[] args) throws RemoteException, NotBoundException {        
        Cliente cliente = new Cliente();
        cliente.iniciar("Default");
        cliente.prueba();
    }
    
}   

