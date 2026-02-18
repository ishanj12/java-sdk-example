# java-sdk-example

A minimal HTTP server using the [ngrok Java SDK](https://github.com/ngrok/ngrok-java) (`com.ngrok:ngrok-java`).

## Clone and Run

```sh
git clone git@github.com:ngrok/java-sdk-example.git
cd java-sdk-example
NGROK_AUTHTOKEN=<token> mvn compile exec:java
```

## Add to Existing Code

1. Add the dependency to your `pom.xml`:

   ```xml
   <dependency>
       <groupId>com.ngrok</groupId>
       <artifactId>ngrok-java</artifactId>
       <version>1.0.0</version>
   </dependency>
   <dependency>
       <groupId>com.ngrok</groupId>
       <artifactId>ngrok-java-native</artifactId>
       <version>1.0.0</version>
       <classifier>${os.detected.classifier}</classifier>
       <scope>runtime</scope>
   </dependency>
   ```

2. Add the following to your app:

   ```java
   import com.ngrok.Session;
   import java.net.URL;

   Forwarder.Endpoint forwardToApp() throws Exception {
       var session = Session.withAuthtokenFromEnv().connect();
       var forwarder = session.httpEndpoint().forward(new URL("http://localhost:8080"));
       System.out.println("Ingress established at: " + forwarder.getUrl());
       return forwarder;
   }
   ```

## License

MIT
