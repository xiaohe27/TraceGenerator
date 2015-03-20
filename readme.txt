In the src folder, you can use command : 

sh benchmark.sh 15 3 1

 to run the benchmark 1 's program, with 15 iterations in total and one violation at the last iteration.

When the second argument is 0, then it will not insert violation at all; if it is positive number, 
will only generate one violation at the end (plz use some number greater than 1 if you want to have some violation).

In each benchmark directory, there is a folder "codei", where i is the number for its benchmark id. You can define good behaviors and bad behaviors in Good.java and Bad.java respectively.

You can define different patterns of behaviors: suppose you want to have three different good behaviors, then you can write the code inside Good.java's run1() to run3() respectively.

At runtime, in each iteration of the loop, if a good behavior is expected, then one method among run1, run2, run3 will be selected randomly.

In this design, objects have a short lifetime, and will be garbage collected soon.

