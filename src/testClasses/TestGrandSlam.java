package testClasses;

import system.GrandSlam;

public class TestGrandSlam {

	public static void main(String[] args) {


		GrandSlam grandSlam = new GrandSlam();
		System.out.println("Team No: " + grandSlam.Team());
		grandSlam.Points(grandSlam.Team());

	}

}
