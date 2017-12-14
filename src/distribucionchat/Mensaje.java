/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package distribucionchat;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Juan Leonardo
 */
public interface Mensaje extends Remote {    
    void publicar(int idfrom, int idto, String mensaje) throws RemoteException;
    void publicarACliente(String mensaje) throws RemoteException;
    int registrar(Mensaje mensajeObj) throws RemoteException;
    void setClients(int n) throws RemoteException;
}
