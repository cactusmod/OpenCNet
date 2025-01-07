# Open CNet
A basic customizable open-source version of the Cactus Backend's core

> [!NOTE]
> This project is still WIP. More features will be added as required, I'm maintaining this as a side-project.
> If you are implementing something you think is a good general addition, please open a pull-request :)

## Introduction
Let's create a basic service without any actual configuration.
```java
CNetServer server = new CNetServerBuilder(5000, ProtocolImpl.empty()).build();
```
This will create an instance of a new `CNetServer`. However, it won't really be useable since it doesn't know about any packets to process.

## Protocol & Packets
There are two types of packets: Packets which are sent from the client to the server (C2S, `IPacketIn`), and packets which are sent from the server to the client (S2C, `IPacketOut`).
We'll go ahead and create a packet which contains a number, and log that number to the console.
```java
public class NumberPacketIn implements IPacketIn {

    private int number;

    @Override
    public void read(ByteBuf buf) {
        // Here we receive a raw ByteBuf, from which we can read information contained in this packet.
        this.number = buf.readInt();
    }

    @Override
    public void handle(AbstractClientConnection connection) {
        // Here we can safely process the packet
        System.out.println("Number received: " + number);
    }
    
}
```

Now, let's create another packet which sends a simple number back to the client.
```java
public class NumberPacketOut implements IPacketOut {

    private final int number;

    public NumberPacketOut(int number) {
        this.number = number;
    }

    @Override
    public void write(ByteBuf buf) {
        // Write the number to the ByteBuf
        buf.writeInt(this.number);
    }

}
```

Now that we have our packets we want to use, we have to let the server know they exist and how to process them by creating a new `ProtocolImpl`. For that, we can use the `ProtocolBuilder`.
Each packet is identified by a unique ID. This ID can occur once on both sides, i.e. once for an incoming packet and once for an outgoing packet.
```java
ProtocolImpl protocol = new ProtocolBuilder()
    .addIn(0, NumberPacketIn.class)
    .addOut(0, NumberPacketOut.class)
    .build;
```

## Connection class
By default, the `CNetServerBuilder` uses the default implementation of a `ClientConnection`. It does not handle events like connects, disconnects or exception and is also unable to process packets passed back to its handler. If we want to use those features, we have to create our own implementation of a `ClientConnection`.
```java
public class CustomClientConnection extends AbstractClientConnection {

    @Override
    public void connected(ChannelHandlerContext ctx) {
        System.out.println("Client connected: " + ctx.channel().remoteAddress());
    }

    @Override
    public void disconnected(ChannelHandlerContext ctx) {
      System.out.println("Client disconnected: " + ctx.channel().remoteAddress());
    }

    @Override
    public void exception(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println("There was an error!");
        cause.printStackTrace();
    }

    @Override
    public void handle(IPacketIn packet) {
        // Custom logic for handling packets if there's no custom method implemented for that packet type
    }

    @Override
    public boolean shouldHandle(IPacketIn packet) {
        // Gets called before handle() is called on an incoming packet and can be used for restrictions.
        return true;
    }

}
```

## Integration
With that, we now have a working protocol to handle your packets and our own client connection implementation. Let's integrate that into our `CNetServerBuilder`, along with some other options.
```java
CNetServer server = new CNetServerBuilder(5000, protocol) // The port we want to start the server on and our protocol
    .useConnection(CustomClientConnection::new)
    .timeout(20L) // Specifies the read-timeout
    .withChannelConfig(config -> {
        // Allows fine-tuning of channel options, if required
        config.setOption(ChannelOption.SO_KEEPALIVE, true);
    })
    .build();
```

We can now start our server
```java
server.start();
```
