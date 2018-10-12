package channel;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.I_Result;

import java.util.concurrent.CountDownLatch;

@JCStressTest
@Outcome(id = "0", expect = Expect.ACCEPTABLE, desc = "OK")
@Outcome(id = "1", expect = Expect.FORBIDDEN, desc = "Message was lost")
@State
public class ChannelWithTimeoutStressTest {
    private BlockingChannel actor;

    public ChannelWithTimeoutStressTest() {
        actor = new BlockingChannel(1);
    }

    @Actor
    public void producer() {
        actor.send();
    }

    @Actor
    public void consumer(I_Result r) {
        r.r1 = actor.receive() ? 0 : 1;
        actor.close();
    }
}
