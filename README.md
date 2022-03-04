# Arrow Fabric

### Projektarbeit Norman Nabhan

As part of the project work, a distributed memory based on Apache Arrow was implemented. Apache
Arrow itself is designed as a local storage. Based on gRPC, the functionality of Apache Arrow was
extended so that it can be used in a distributed manner.

## Installation

+ Clone this repo.

```bash
git clone
```

+ Build a fat jar of this project with

```bash
mvn clean package
```

Furthermore Redis is required.

## Usage

### Quickstart

First, the various components must be launched. A Fabric Dispatcher must be running and connected to
Redis. You can start as many Fabric Servers as you like.

#### Start the Fabric Dispatcher

```bash
java -cp ./arrowfabric/target/ArrowFabric-1.0-SNAPSHOT-jar-with-dependencies.jar dispatcher.FabricDispatcher --address=$(hostname) --redis-address=node59
```

In this case, the address to listen on is _automatically_ taken from the hostname environment
variable. But you can take any FQDN from the machine. The redis address is `node59`
If everything worked fine, you'll see a console output like

```bash
14:40:18.382 [main] INFO  o.i.allgood.consistent.hash.HashRing.<init> - Ring [hash_ring_6396] created: hasher [MURMUR_3], partitionRate [1000]
14:40:18.494 [main] INFO  dispatcher.DispatcherServiceImpl.<init> - Connection to REDIS Server successful!
14:40:19.074 [main] INFO  dispatcher.FabricDispatcher.startService - Dispatcher started, listening on node59:50052
```

#### Start a Fabric Server

```bash
java -cp arrowfabric/target/ArrowFabric-1.0-SNAPSHOT-jar-with-dependencies.jar server.FabricServer --address=$(hostname) --dispatcher-address=node59
```

In this case, the address to listen on is _automatically_ taken as well. The `dispatcher-address`
is `node59`. If everything worked fine, you'll see a console output like

```bash
14:40:22.785 [main] INFO  o.apache.arrow.memory.BaseAllocator.<clinit> - Debug mode disabled.
14:40:22.792 [main] INFO  o.a.a.m.DefaultAllocationManagerOption.getDefaultAllocationManagerFactory - allocation manager type not specified, using netty as the default type
14:40:22.795 [main] INFO  o.apache.arrow.memory.CheckAllocator.reportResult - Using DefaultAllocationManager at memory-netty/5.0.0/arrow-memory-netty-5.0.0.jar!/org/apache/arrow/memory/DefaultAllocationManagerFactory.class
14:40:23.809 [main] INFO  server.FabricServer.start - Server started, listening on node60:50051
14:40:24.671 [main] INFO  server.FabricServer.start - [registerServer] Code: OK
```

The last line is especially important here. The server reports that it was able to successfully
establish a connection to the dispatcher. On the other side, the dispatcher reports that the
connection has been successfully established

```bash
# Console output from dispatcher
[...]
14:40:24.574 [grpc-default-executor-0] INFO  o.i.allgood.consistent.hash.HashRing.addNode - Ring [hash_ring_6396]: node [node60:50051] added
14:40:24.612 [grpc-default-executor-0] INFO  dispatcher.DispatcherServiceImpl.registerServer - Server added. Servers now: [node60:50051]
[...]
```

From this point the system is ready to accept requests from clients 🎉 The next section demonstrates
the access via Java

#### Use the Fabric Client

Each access to the system is performed in a type of session. Therefore, it is necessary to configure
the fabric client properly. Now the usage will be explained step by step. At the end, all the steps are summarized.

```java
class FabricClientTest {
  void main(){
    string dispatcherAddress = "node59";
    int dispatcherPort = 50052;
    try (FabricClient client = new FabricClient(dispatcherAddress, dispatcherPort)) {
      // [ ]
    }
  }
}
```
Not much is happening here yet. A fabric client is initialized with the address of the dispatcher




## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would
like to change.

Please make sure to update tests as appropriate.

## License

[MIT](https://choosealicense.com/licenses/mit/)