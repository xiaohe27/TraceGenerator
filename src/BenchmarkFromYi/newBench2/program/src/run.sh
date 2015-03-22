CLASSPATH="$CLASSPATH:."
javac pgm/*.java
rv-monitor -d spec/classes/mop spec/Safe_Lock.rvm
javac spec/classes/mop/*.java
rm spec/classes/mop/*.java
cd spec
cp -r ../pgm classes
javamopagent *.aj classes
rm -r classes/pgm
mv agent.jar ..
cd ..

//java -javaagent:agent.jar -d64 -Xms512m -Xmx4g pgm.SafeLock 1> trace.csv 2> err

