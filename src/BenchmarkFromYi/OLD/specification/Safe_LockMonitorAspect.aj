package mop;import java.util.*;
import com.runtimeverification.rvmonitor.java.rt.RVMLogging;
import com.runtimeverification.rvmonitor.java.rt.RVMLogging.Level;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

import java.lang.ref.*;
import org.aspectj.lang.*;

aspect BaseAspect {
	pointcut notwithin() :
	!within(sun..*) &&
	!within(java..*) &&
	!within(javax..*) &&
	!within(com.sun..*) &&
	!within(org.dacapo.harness..*) &&
	!within(org.apache.commons..*) &&
	!within(org.apache.geronimo..*) &&
	!within(net.sf.cglib..*) &&
	!within(mop..*) &&
	!within(javamoprt..*) &&
	!within(rvmonitorrt..*) &&
	!within(com.runtimeverification..*);
}

public aspect Safe_LockMonitorAspect implements com.runtimeverification.rvmonitor.java.rt.RVMObject {
	public Safe_LockMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock Safe_Lock_MOPLock = new ReentrantLock();
	static Condition Safe_Lock_MOPLock_cond = Safe_Lock_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(com.runtimeverification.rvmonitor.java.rt.RVMObject+) && !adviceexecution() && BaseAspect.notwithin();
	pointcut Safe_Lock_begin() : (execution(* *.*(..)) && !within(Lock+)) && MOP_CommonPointCut();
	before () : Safe_Lock_begin() {
		Thread t = Thread.currentThread();
		Safe_LockRuntimeMonitor.beginEvent(t);
	}

	pointcut Safe_Lock_acquire(Lock l) : (call(* Lock.lock()) && target(l)) && MOP_CommonPointCut();
	after (Lock l) : Safe_Lock_acquire(l) {
		Thread t = Thread.currentThread();
		Safe_LockRuntimeMonitor.acquireEvent(l, t);
	}

	pointcut Safe_Lock_release(Lock l) : (call(* Lock.unlock()) && target(l)) && MOP_CommonPointCut();
	after (Lock l) : Safe_Lock_release(l) {
		Thread t = Thread.currentThread();
		Safe_LockRuntimeMonitor.releaseEvent(l, t);
	}

	pointcut Safe_Lock_end() : (execution(* *.*(..)) && !within(Lock+)) && MOP_CommonPointCut();
	after () : Safe_Lock_end() {
		Thread t = Thread.currentThread();
		Safe_LockRuntimeMonitor.endEvent(t);
	}

}
