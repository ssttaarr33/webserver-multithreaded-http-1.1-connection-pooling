# webserver-multithreaded-http-1.1-connection-pooling
Low level implementation of a java web server.
It supports connection pooling, using java.util.concurrent.Executor
It can serve static files (angular frontend).
Implements Get & Post methods for the scenario - home automation.
Handles keep-alive.

The main thread awaits and accepts connections which are scheduled to be processed by the executor. If keep-alive is enabled the thread keeps waiting
for requests for a certain time.
