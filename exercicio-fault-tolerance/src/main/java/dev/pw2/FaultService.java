/**
 * PW2 by Rodrigo Prestes Machado
 *
 * PW2 is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 * work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
 */
package dev.pw2;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;

import java.util.concurrent.atomic.AtomicLong;

@Path("/fault")
public class FaultService {

    /* Contador */
    private AtomicLong counter = new AtomicLong(0);

    /*
     * Após 4 requests o CircuitBreaker vai ser ativado e, pelos próximos 5
     * segundos, o método fail() não vai
     * mais ser chamado e o contador vai permanecer congelado.
     */
    @GET
    @Path("/circuit-breaker")
    @Produces(MediaType.TEXT_PLAIN)
    @CircuitBreaker(requestVolumeThreshold = 4)
    @Fallback(fallbackMethod = "testCircuitBreakerFallback")
    public String testCircuitBreaker() {
        fail();
        return "Test";
    }

    private void fail() {
        counter.getAndIncrement();
        throw new RuntimeException("Service failed.");
    }

    public String testCircuitBreakerFallback() {
        return String.valueOf(counter.get());
    }

}
