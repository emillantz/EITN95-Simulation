package assignment3.assignment.src.main.java.assignment3.tasks.task1;
// Denna klass definierar signallistan. Om man vill skicka mer information i signalen �n minimum, s� kan
// man skriva ytterligare variante av SendSignal som inneh�ller fler parametrar.

// This class defines the signal list. If one wants to send more information than here,
// one can add the extra information in the Signal class and write an extra sendSignal method 
// with more parameters. 

public class SignalList{
	private  static Signal list, last;

	SignalList(){
    	list = new Signal();
    	last = new Signal();
    	list.next = last;
	}

	public static void SendSignal(int type, Proc dest, double arrtime, Proc source){
 	Signal dummy, predummy;
 	Signal newSignal = new Signal();
 	newSignal.signalType = type;
 	newSignal.destination = dest;
 	newSignal.arrivalTime = arrtime;
 	predummy = list;
 	dummy = list.next;
	newSignal.source = source;
 	while ((dummy.arrivalTime < newSignal.arrivalTime) & (dummy != last)){
 		predummy = dummy;
 		dummy = dummy.next;
 	}
 	predummy.next = newSignal;
 	newSignal.next = dummy;
 }

	public static Signal FetchSignal(){
		Signal dummy;
		dummy = list.next;
		list.next = dummy.next;
		dummy.next = null;
		return dummy;
	}
}
