package andy.com.actor;

import javax.xml.datatype.Duration;

public class Pi {

	
	public static void main(String[] args) {
		// TODO 自动生成的方法存根

	}

}
class Calculate {
}
 
class Work {
  private final int start;
  private final int nrOfElements;
 
  public Work(int start, int nrOfElements) {
    this.start = start;
    this.nrOfElements = nrOfElements;
  }
 
  public int getStart() {
    return start;
  }
 
  public int getNrOfElements() {
    return nrOfElements;
  }
}
 
class Result {
  private final double value;
 
  public Result(double value) {
    this.value = value;
  }
 
  public double getValue() {
    return value;
  }
}
 
class PiApproximation {
  private final double pi;
  private final Duration duration;
 
  public PiApproximation(double pi, Duration duration) {
    this.pi = pi;
    this.duration = duration;
  }
 
  public double getPi() {
    return pi;
  }
 
  public Duration getDuration() {
    return duration;
  }
}
//
//
//public class Worker extends UntypedActor {
//	 
//	  // calculatePiFor ...
//	 
//	  public void onReceive(Object message) {
//	    if (message instanceof Work) {
//	      Work work = (Work) message;
//	      double result = calculatePiFor(work.getStart(), work.getNrOfElements());
//	      getSender().tell(new Result(result), getSelf());
//	    } else {
//	      unhandled(message);
//	    }
//	  }
//	  
//	  private double calculatePiFor(int start, int nrOfElements) {
//		  double acc = 0.0;
//		  for (int i = start * nrOfElements; i <= ((start + 1) * nrOfElements - 1); i++) {
//		    acc += 4.0 * (1 - (i % 2) * 2) / (2 * i + 1);
//		  }
//		  return acc;
//		}
//	}
