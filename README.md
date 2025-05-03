# Aeron IPC Sample

A simple demonstration of Inter-Process Communication (IPC) using Aeron, a high-performance messaging system.

## Overview

This project demonstrates how to:
- Set up a Media Driver for Aeron IPC
- Create a Publisher to send messages
- Create a Subscriber to receive messages

## Prerequisites

- Java 17 or later
- Gradle 8.13 or later

## Running the Sample

The system consists of three components that need to be run in separate terminals:

1. Start the Media Driver:
```bash
./gradlew runMediaDriver
```

2. Start the Subscriber:
```bash
./gradlew run --args="subscriber"
```

3. Start the Publisher:
```bash
./gradlew run --args="publisher"
```

## How It Works

- The Media Driver handles all low-level communication
- The Publisher sends messages over IPC
- The Subscriber receives and displays messages
- All components use the same channel (`aeron:ipc`) and stream ID (1001)

## Project Structure

- `Publisher.java`: Sends messages using Aeron
- `Subscriber.java`: Receives and displays messages
- `OmsMain.java`: Main entry point that routes to Publisher or Subscriber

## Dependencies

- Aeron 1.40.0
- Agrona 1.16.0

## Notes

- The Media Driver must be running before starting the Publisher or Subscriber
- The Subscriber will continue running until manually stopped
- The Publisher sends a single message and exits 