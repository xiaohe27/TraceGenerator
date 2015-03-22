package mop;
import pgm.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.*;
import java.lang.ref.*;
import java.util.concurrent.atomic.AtomicInteger;
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
	pointcut Safe_Lock_acquire(ALock l, AThread t) : (call(* AThread.acquire(ALock)) && target(t) && args(l)) && MOP_CommonPointCut();
	after (ALock l, AThread t) : Safe_Lock_acquire(l, t) {
		Safe_LockRuntimeMonitor.acquireEvent(l, t);
		System.out.print("acquire, " + System.identityHashCode(l) + ", " + 
		System.identityHashCode(t) + "\r\n");
	}

	pointcut Safe_Lock_release(ALock l, AThread t) : (call(* AThread.release(ALock)) && target(t) && args(l)) && MOP_CommonPointCut();
	after (ALock l, AThread t) : Safe_Lock_release(l, t) {
		Safe_LockRuntimeMonitor.releaseEvent(l, t);
		System.out.print("release, " + System.identityHashCode(l) + ", " + 
		System.identityHashCode(t) + "\r\n");
	}

	pointcut Safe_Lock_begin(AThread t) : (call(* AThread.begin()) && target(t)) && MOP_CommonPointCut();
	after (AThread t) : Safe_Lock_begin(t) {
		Safe_LockRuntimeMonitor.beginEvent(t);
		System.out.print("begin, , " + 
		System.identityHashCode(t) + "\r\n");
	}

	pointcut Safe_Lock_end(AThread t) : (call(* AThread.end()) && target(t)) && MOP_CommonPointCut();
	after (AThread t) : Safe_Lock_end(t) {
		Safe_LockRuntimeMonitor.endEvent(t);
		System.out.print("end, , " + 
		System.identityHashCode(t) + "\r\n");
	}

}
