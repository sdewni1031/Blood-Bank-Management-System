package client.ijse.rmi.bloodbank.serverconnector;

import common.ijse.rmi.bloodbank.controller.DonorController;
import common.ijse.rmi.bloodbank.controller.BloodSampleController;
import common.ijse.rmi.bloodbank.controller.PatientController;
import common.ijse.rmi.bloodbank.controller.GuardianController;
import common.ijse.rmi.bloodbank.controller.IssuebloodController;
import common.ijse.rmi.bloodbank.controller.RemoteFactory;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ServerConnector{
	private static ServerConnector serverConnector;
    private DonorController donorController;
    private BloodSampleController bloodSampleController;
    private PatientController patientController;
    private GuardianController guardianController;
    private IssuebloodController issuebloodController;
    private RemoteFactory remoteFactory;

	public ServerConnector() throws RemoteException, NotBoundException, MalformedURLException {
        remoteFactory = (RemoteFactory) Naming.lookup("rmi://localhost:5050/BloodBankServer");
    }
    public static ServerConnector getServerConnector() throws RemoteException, NotBoundException, MalformedURLException {
        if (serverConnector == null) {
            serverConnector = new ServerConnector();
        }
        return serverConnector;
    }
    public DonorController getDonorController() throws RemoteException {
        if (donorController == null) {
            donorController = remoteFactory.getDonorController();
        }
        return remoteFactory.getDonorController();
    }
    public BloodSampleController getBloodSampleController()throws RemoteException{
		if(bloodSampleController==null){
			bloodSampleController=remoteFactory.getBloodSampleController();
		}
		return remoteFactory.getBloodSampleController();
	}
	public PatientController getPatientController()throws RemoteException{
		if(patientController==null){
			patientController=remoteFactory.getPatientController();
		}
		return remoteFactory.getPatientController();
	}
	public GuardianController getGuardianController()throws RemoteException{
		if(guardianController==null){
			guardianController=remoteFactory.getGuardianController();
		}
		return remoteFactory.getGuardianController();
	}
	public IssuebloodController getIssuebloodController()throws RemoteException{
		if(issuebloodController==null){
			issuebloodController=remoteFactory.getIssuebloodController();
		}
		return remoteFactory.getIssuebloodController();
	}
}
