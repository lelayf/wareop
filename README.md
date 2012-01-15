# wareop

**wareop** is a modular platform designed to bridge marketing automation and business intelligence in a scalable, cost-effective way. It is essentially a data system comprised of a batch layer, a serving layer and a speed layer as described in @nathanmarz and @sritchie09's book "Big Data in Action" (Manning). The batch layer is based on any Hadoop cluster with cascalog as a data management facility. The serving layer should be based on a distributed, all-in-RAM ElephantDB key-value store, with local persitence yet to be defined (BerkeleyDB, H2, Krati...). The speed layer should implement a <b>Storm</b> topology, thus enabling real-time CRM and analytics. <b>Mahout</b> will be used to implement a recommender system and most features for which machine learning is necessary. Implementation should be primarily done in <b>Clo<i>j</i>ure</b>, <b>ClojureScript</b> and HTML/CSS/Javascript.     

Conceptually, the application is divided in 3 parts: *Topology*, *Analytics* and *Actions*.

### Topology : data management layer

Data sources definition, brute-force data profiling (thanks to Hadoop), data quality rules definition, data-to-metrics wiring.

### Analytics : data visualisation, reporting and mining

The idea behind analytics is quite simple : give you access to standard pre-packaged metrics while easing the process of mapping these to your actual data sets. Visualisation capabilities are based on the amazing [D3](https://github.com/mbostock/d3) library.

### Actions : a container for any kind of software that dynamically triggers actions based on data changes. 

- Emailing app : automate email marketing actions, content manage newsletters, etc.
- Texting app : automate text messages
- Physical computing app 
- Any API app 
- etc.

## License

Copyright (C) 2012 François Le Lay

Distributed under the Eclipse Public License, the same as Clojure.

