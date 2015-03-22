package mop;
import java.util.*;
import com.runtimeverification.rvmonitor.java.rt.RVMLogging;
import com.runtimeverification.rvmonitor.java.rt.RVMLogging.Level;
import code4.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
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

public aspect EqualityCheckMonitorAspect implements com.runtimeverification.rvmonitor.java.rt.RVMObject {
	public EqualityCheckMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock EqualityCheck_MOPLock = new ReentrantLock();
	static Condition EqualityCheck_MOPLock_cond = EqualityCheck_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(com.runtimeverification.rvmonitor.java.rt.RVMObject+) && !adviceexecution() && BaseAspect.notwithin();
	pointcut EqualityCheck_done(MyObj o) : (execution(* done(..)) && target(o)) && MOP_CommonPointCut();
	after (MyObj o) : EqualityCheck_done(o) {
		EqualityCheckRuntimeMonitor.doneEvent(o);
		System.out.print("done, " + System.identityHashCode(o) + "\r\n");
	}

	pointcut EqualityCheck_a(MyObj o) : (call(* MyObj.A()) && target(o)) && MOP_CommonPointCut();
	after (MyObj o) : EqualityCheck_a(o) {
		EqualityCheckRuntimeMonitor.aEvent(o);
		System.out.print("a, " + System.identityHashCode(o) + "\r\n");
	}

	pointcut EqualityCheck_b(MyObj o) : (call(* MyObj.B()) && target(o)) && MOP_CommonPointCut();
	after (MyObj o) : EqualityCheck_b(o) {
		EqualityCheckRuntimeMonitor.bEvent(o);
		System.out.print("b, " + System.identityHashCode(o) + "\r\n");
	}

	pointcut EqualityCheck_c(MyObj o) : (call(* MyObj.C()) && target(o)) && MOP_CommonPointCut();
	after (MyObj o) : EqualityCheck_c(o) {
		EqualityCheckRuntimeMonitor.cEvent(o);
		System.out.print("c, " + System.identityHashCode(o) + "\r\n");
	}

}
