<h1><img src="OASMR.png" width="100"> <a style="text-align: center"> OASMR</a> </h1> 

[![Hits-of-Code](https://hitsofcode.com/github/CCC-development-team/OASMR)](https://hitsofcode.com/view/github/CCC-development-team/OASMR)

### Supervisor

```bash
./gradlew supervisor [--args="<Port(default:40404)>"]
```
(On Windows launch gradlew.bat instead)
### Node
```bash
./gradlew node [--args="<supervisor address> <supervisor port> <node port>"]
```
Without parameters, the node will connect to localhost:40404 with node port 56780

### Application
```bash
./gradlew app
```
