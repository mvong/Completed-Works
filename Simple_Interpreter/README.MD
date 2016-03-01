Mark Vong
Email: mvong@usc.edu
STUDENT ID: 8626056021

In order to compile facile //exe: $ make

Facile is comprised of 12 classes of statements(Let, Print, PrintAll, Add, Subtract, Multiply, Divide, GoTo, If, GoSub, Return, End) that all inherit from the Statement class.
Facile's dependencies are all of these classes including the Statement class. Each of these statements are executed via the virtual function 'execute' which is unique to each class. The Add, Subtract, Multipl, and Divide statements are operators that manipulate the given variables' values. The GoTo, GoSub, and Return statements deal with skipping lines throughout the program and either moving forward or returning to a specific line that was skipped. The Let statement intializes a variable with an integer value. The Print and PrintAll statements are responsible for printing a variable's integer value or all the variables' names and their values. The End statement terminates the program immediately.

The method of catching the error of dividing by 0 is implemented with an if/else statement that outputs the error message and terminates the program if the user provides such a statement.

For the print function, if the variable does not exist then it will print out 0.
For the printall function, the variable name and its value will be printed.



