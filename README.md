# Six-Nations-Rugby-App
A Java/MSAccess system to establish the fixtures, input and display match scores, and display round results and a league table for the Six Nations Rugby Tournament.

Originally a group project for the MSc in Software Development in Queen's University of Belfast,
I have designed this system without reference to any previous efforts. 

See Requirements specification in repository. 

Progress on requirements:

1.	DONE Fixture-setting objects.
2.	DONE GUI for user entry of scores.
3.	DONE object for bulk upload of round results. 
4.	DONE league table. 
6.	DONE league table display in descending order of points.
7.	DONE persistent data.
8.	DONE search facility for results and league table.   

The MS Access database holds a Results Table, a League Table, a Fixture Asssignment Table, and Query Tables for the league table and the 5 rounds. 
 
The Java system includes Database objects (Connection, Table Updates, and Table Display), Fixture Assignment objects (Randomizer, Writer of teams to Tables),  Results objects (for a match or a round of three matches), and Opposing Team objects (Update points, finder)

The Java system includes a separate package of Test objects for each system object. 

Nearly finished!  

TODO:   

Test GUI inputs and outputs. 
Add guards and validations on inputs.
Improve exception handling.  

22 March 2018


