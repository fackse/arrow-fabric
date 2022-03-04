### sollipulli
```bash
ssh nabhan@134.99.70.210
```
####get node
```bash
qsub -I -l walltime=00:30:00,host=node62
```
##Redis
```bash
redis-stable/src/redis-server redis-stable/src/redis.conf
redis-stable/src/redis-cli -h node59 flushdb
```
### Dispatcher
```bash
java -cp arrowfabric/target/ArrowFabric-1.0-SNAPSHOT-jar-with-dependencies.jar dispatcher.FabricDispatcher --address=$(hostname) --redis-address=node59
```
###Server
```bash
java -cp arrowfabric/target/ArrowFabric-1.0-SNAPSHOT-jar-with-dependencies.jar server.FabricServer --address=$(hostname) --dispatcher-address=node59
```
### Client
```bash
java -cp arrowfabric/target/ArrowFabric-1.0-SNAPSHOT-jar-with-dependencies.jar client.FabricClient -a=node83 list-servers
```

###ycsb
```bash
export PATH=/home/nabhan/python27/python-2.7.10/bin:$PATH
export LD_LIBRARY_PATH=/home/nabhan/python27/python-2.7.10/lib:$LD_LIBRARY_PATH
cd ycsb
/home/nabhan/python27/python-2.7.10/bin/python2.7 ./bin/ycsb load redis -P ./redis/workloads/workloada -p arrowfabric.host=node59 -p arrowfabric.port=50052
```

