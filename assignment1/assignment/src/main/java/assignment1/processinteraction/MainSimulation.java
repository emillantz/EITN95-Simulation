package assignment1.assignment.src.main.java.assignment1.processinteraction;

import java.util.*;
import java.io.*;

//Denna klass arver Global sa att man kan anvanda time och signalnamnen utan punktnotation
//It inherits Proc so that we can use time and the signal names without dot notation


public class MainSimulation extends Global{

    public static void main(String[] args) throws IOException {

    	//Signallistan startas och actSignal deklareras. actSignal ar den senast utplockade signalen i huvudloopen nedan.
    	// The signal list is started and actSignal is declaree. actSignal is the latest signal that has been fetched from the 
    	// signal list in the main loop below.

    	Signal actSignal;
    	new SignalList();

    	//Har nedan skapas de processinstanser som behovs och parametrar i dem ges varden.
    	// Here process instances are created (two queues and one generator) and their parameters are given values. 

    	QS Q1 = new QS();
    	Q1.sendTo = null;

    	Gen Generator = new Gen();
    	Generator.lambda = 9; //Generator ska generera nio kunder per sekund  //Generator shall generate 9 customers per second
    	Generator.sendTo = Q1; //De genererade kunderna ska skickas till kosystemet QS  // The generated customers shall be sent to Q1

    	//Har nedan skickas de forsta signalerna for att simuleringen ska komma igang.
    	//To start the simulation the first signals are put in the signal list

    	SignalList.SendSignal(READY, Generator, time);
    	SignalList.SendSignal(MEASURE, Q1, time);


    	// Detta ar simuleringsloopen:
    	// This is the main loop

    	while (time < 100000){
    		actSignal = SignalList.FetchSignal();
    		time = actSignal.arrivalTime;
    		actSignal.destination.TreatSignal(actSignal);
    	}

    	//Slutligen skrivs resultatet av simuleringen ut nedan:
    	//Finally the result of the simulation is printed below:

    	System.out.println("Mean number of customers in queuing system: " + 1.0*Q1.accumulated/Q1.noMeasurements);

    }
}
