# Parallelization with Apache Ignite

Example showing how to parallelize function evaluations across multiple computers using Apache Ignite.

## Usage

To run these examples on the local machine, simply run either of the Java examples.  Ignite will automatically
distribute the work across all available CPUs.

To distribute the work across multiple machines, you must setup an Ignite config file specifying the topology and
start one or more nodes following the guidance at https://ignite.apache.org/docs/latest/starting-nodes.  

## Troubleshooting

### Inaccessible Field

This error occurs because Apache Ignite accesses internal Java APIs, which are not accessible on newer versions of
Java.

```
Exception in thread "main" java.lang.ExceptionInInitializerError
...
Caused by: java.lang.reflect.InaccessibleObjectException: Unable to make field ... accessible
```

To fix this, add the following options when launching the JVM:

```
--add-opens=java.base/jdk.internal.access=ALL-UNNAMED
--add-opens=java.base/jdk.internal.misc=ALL-UNNAMED
--add-opens=java.base/sun.nio.ch=ALL-UNNAMED
--add-opens=java.base/sun.util.calendar=ALL-UNNAMED
--add-opens=java.management/com.sun.jmx.mbeanserver=ALL-UNNAMED
--add-opens=jdk.internal.jvmstat/sun.jvmstat.monitor=ALL-UNNAMED
--add-opens=java.base/sun.reflect.generics.reflectiveObjects=ALL-UNNAMED
--add-opens=jdk.management/com.sun.management.internal=ALL-UNNAMED
--add-opens=java.base/java.io=ALL-UNNAMED
--add-opens=java.base/java.nio=ALL-UNNAMED
--add-opens=java.base/java.net=ALL-UNNAMED
--add-opens=java.base/java.util=ALL-UNNAMED
--add-opens=java.base/java.util.concurrent=ALL-UNNAMED
--add-opens=java.base/java.util.concurrent.locks=ALL-UNNAMED
--add-opens=java.base/java.util.concurrent.atomic=ALL-UNNAMED
--add-opens=java.base/java.lang=ALL-UNNAMED
--add-opens=java.base/java.lang.invoke=ALL-UNNAMED
--add-opens=java.base/java.math=ALL-UNNAMED
--add-opens=java.sql/java.sql=ALL-UNNAMED
--add-opens=java.base/java.lang.reflect=ALL-UNNAMED
--add-opens=java.base/java.time=ALL-UNNAMED
--add-opens=java.base/java.text=ALL-UNNAMED
--add-opens=java.management/sun.management=ALL-UNNAMED
--add-opens java.desktop/java.awt.font=ALL-UNNAMED
```

More details can be found at https://ignite.apache.org/docs/latest/setup#running-ignite-with-java-11-or-later.

### Failed to create page store work directory

This error can occur when the file path is too long for the file system:

```
Exception in thread "main" class org.apache.ignite.IgniteException: Failed to start processor: GridProcessorAdapter []
...
Caused by: class org.apache.ignite.IgniteCheckedException: Failed to create page store work directory: <path>
```

To fix this, we can override the generated id for the node by adding this setting when launching the JVM:

```
-DIGNITE_OVERRIDE_CONSISTENT_ID=node00
```

## License

Copyright 2009-2025 David Hadka and other contributors.  All rights reserved.

The MOEA Framework is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or (at your
option) any later version.

The MOEA Framework is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
License for more details.

You should have received a copy of the GNU Lesser General Public License
along with the MOEA Framework.  If not, see <http://www.gnu.org/licenses/>.
