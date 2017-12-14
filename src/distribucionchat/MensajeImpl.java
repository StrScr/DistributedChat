/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package distribucionchat;

/**
 *
 * @author Juan Leonardo
 */
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;



public class MensajeImpl extends UnicastRemoteObject implements Mensaje {               
    private Map<Integer,Mensaje> clienteObj = null;
    private ClienteGUI cGUI = null;
    private int lastid;
    
    public MensajeImpl() throws RemoteException {
        clienteObj = new HashMap<>();
        lastid = 0;
    }
    public MensajeImpl(ClienteGUI cGUI) throws RemoteException {
        clienteObj = new HashMap<>();
        this.cGUI = cGUI;
        lastid = 0;
    }
    
    @Override
    public void publicar(int idfrom, int idto, String mensaje) throws RemoteException {        
        System.out.println("Mensaje de " + idfrom + " a " + ((idto==0)?"todos":idto) + ": " + mensaje);
        Mensaje me = clienteObj.get(idfrom);
        if(idto==0){
            Iterator cliOI = clienteObj.values().iterator();
            Mensaje nxt;
            while (cliOI.hasNext()) {
                nxt = ((Mensaje)cliOI.next());
                if(nxt!=me){
                    nxt.publicarACliente(idfrom + " to Everyone: " + mensaje);
                }
            }
        }else{
            clienteObj.get(idto).publicarACliente(idfrom + " to You: " + mensaje);
        }
        me.publicarACliente("You to " + ((idto==0)?"Everyone":idto) + ": " + mensaje);
    }
    
    @Override
    public void publicarACliente(String mensaje) throws RemoteException {
        cGUI.actualizarTexto(mensaje);
    }
    
    @Override
    public int registrar(Mensaje mensajeObj) throws RemoteException {
        lastid++;
        clienteObj.put(lastid, mensajeObj);
        Iterator cliOI = clienteObj.values().iterator();
        while (cliOI.hasNext()) {
            ((Mensaje)cliOI.next()).setClients(lastid);
        }
        return lastid;
    }
    
    @Override
    public void setClients(int n) throws RemoteException{
        cGUI.updateClients(n);
    }
}