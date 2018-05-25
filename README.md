# mapr-streamprinter

A translation of the sample MapR stream consumer that accepts the stream and the poll interval as parameters.

## Making the jar

Launch `sbt assembly`

## Launching

Run:
```sh
$ maprlogin password
$ java -classpath 'mapr-streamprinter-assembly-0.1.jar:/opt/mapr/lib/*' com.github.simonedeponti.maprstreamprinter.MaprStreamPrinter /path/to/stream:topic 1
```