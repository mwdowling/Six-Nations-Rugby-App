# Six-Nations-Rugby-App
A Java/MSAccess system to establish the fixtures, input and display match scores, and display round results and a league table for the Six Nations Rugby Tournament.

Originally a group project for the MSc in Software Development in Queen's University of Belfast,
I have designed this system without reference to any previous efforts. 

See Requirements specification in repository. The MS Access database holds a Results Table, a League Table, a Fixture Asssignment Table, and Query Tables for the League Table and the 5 Rounds of the tournament. 
 
The Java system includes Database objects (Connection, Table Updates, and Table Display), Fixture Assignment objects (Randomizer, Assigner of Team Names to Tables),  Results objects (to input data from a match or a round of three matches), and an Opposing Team hierarchy of objects with an abstract team for shared attributes and behaviours and concrete TeamA and TeamB objects for opposing team behaviours objects (to update points and find teams).

The Java system has a separate package of Test objects for each system object. 

A blank database "template" is provided.  A partially complete database "2018" is provided.  A sample tab delimited round result file is provided.  

Congratulations to Ireland in 2018!!

TODO:   
Add guards and validations on inputs.
Improve exception handling, inheritance of exceptions.

23 March 2018


