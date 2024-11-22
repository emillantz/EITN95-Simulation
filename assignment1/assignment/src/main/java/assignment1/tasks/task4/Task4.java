package assignment1.assignment.src.main.java.assignment1.tasks.task4;

import assignment1.assignment.src.main.java.assignment1.processinteraction.MainSimulation;
import assignment1.assignment.src.main.java.assignment1.processinteraction.Signal;
import assignment1.assignment.src.main.java.assignment1.processinteraction.SignalList;


public class Task4 extends MainSimulation{
    public void run() {
        System.out.println("Task 4");

		//Signallistan startas och actSignal deklareras. actSignal �r den senast utplockade signalen i huvudloopen nedan.
		// The signal list is started and actSignal is declaree. actSignal is the latest signal that has been fetched from the
		// signal list in the main loop below.

		Signal actSignal;
		new SignalList();

		//H�r nedan skapas de processinstanser som beh�vs och parametrar i dem ges v�rden.
		// Here process instances are created (two queues and one generator) and their parameters are given values.

		TaskQS Q1 = new TaskQS();
		Q1.sendTo = null;
		Q1.probability = 0.5;

		TaskGen Generator = new TaskGen();
		Generator.sendTo = Q1;

		//H�r nedan skickas de f�rsta signalerna f�r att simuleringen ska komma ig�ng.
		//To start the simulation the first signals are put in the signal list
		System.out.println(Generator.sendTo);
		SignalList.SendSignal(READY, Generator, time);
		//SignalList.SendSignal(MEASURE, Q1, time);


		// Detta �r simuleringsloopen:
		// This is the main loop

		while (Q1.arrival < 1000) {
			actSignal = SignalList.FetchSignal();
			time = actSignal.arrivalTime;
			actSignal.destination.TreatSignal(actSignal);
		}

		//Slutligen skrivs resultatet av simuleringen ut nedan:
		//Finally the result of the simulation is printed below:

		System.out.println(Q1.special);
		System.out.println(Q1.accumulatedSpecial);
		System.out.println(Q1.regular);
		System.out.println(Q1.accumulatedRegular);
		System.out.println("accumulated special customers " + Q1.accumulatedSpecial);
		System.out.println("accumulated reg customers " + Q1.accumulatedRegular);
		System.out.println("mean time reg " + 1.0 * Q1.regular /
				Q1.accumulatedRegular);
		System.out.println("mean time special " + 1.0 * Q1.special /
				Q1.accumulatedSpecial);

        System.out.println("-----------------------------------------------");
    }
}
