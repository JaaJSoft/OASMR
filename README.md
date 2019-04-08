<h1><img src="OASMR.png" width="100"> <a style="text-align: center"> OASMR</a> </h1> 


### Supervisor

```bash
./gradlew supervisor [--args="<Port(default:40404)>"]
```
(In windows launch gradlew.bat instead)
### Node
```bash
./gradlew node [--args="<supervisor address> <supervisor port> <node port>"]
```
Without parameters, the node will connect to localhost:40404 with node port 56780

### Application
```bash
./gradlew app
```