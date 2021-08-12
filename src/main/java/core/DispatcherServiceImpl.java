package core;

import io.grpc.stub.StreamObserver;
import org.example.ArrowFabric.DispatcherServiceGrpc;
import org.example.ArrowFabric.GetTicket;
import org.example.ArrowFabric.Ticket;

import java.util.logging.Logger;

public class DispatcherServiceImpl extends DispatcherServiceGrpc.DispatcherServiceImplBase {

    private static final Logger logger = Logger.getLogger(DispatcherServiceImpl.class.getName());
    /**
     * Tickets are used to distribute writes across the servers
     * @param request
     * @param responseObserver
     */
    @Override
    public void getTicket(GetTicket request, StreamObserver<Ticket> responseObserver) {
        String type = request.getType();
        String name = request.getName();
        /*
        Later, at this point, the dispatcher will decide which server should be used to save the vector
        This includes:
        + Is there already an server with the vector named *name*
        ++ Is the vector sealed? (writes allowed or not)
        + If not: Decide which server suits "best" (in means of free memory, maybe Geolocation & performance)
        */
        // atm hard coded
        Ticket reply = Ticket.newBuilder().setServer("localhost:50051").setTransactionId("1").build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
