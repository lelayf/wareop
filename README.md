# wareop

**wareop** is a modular web application designed to fit all marketing automation and business intelligence needs of fast-growing, data-driven companies. It is implemented in <b>Clo<i>j</i>ure</b> and sits on top of any Hadoop cluster.     

Conceptually, the application is divided in 3 parts: *Topology*, *Analytics* and *Actions*.

### Topology : data management layer

Data sources definition, brute-force data profiling (thanks to Hadoop), data quality rules definition, data-to-metrics wiring.
Mostly based on the über-l33t cascalog framework, with midje testing.

### Analytics : data visualisation, reporting and mining

The idea behind analytics is quite simple : give you access to standard pre-packaged metrics while easing the process of mapping these to your actual data sets. Visualisation capabilities are based on the amazing [D3](https://github.com/mbostock/d3) library.

### Actions : data-driven marketing

This part of the application allows you to automate insight-based marketing actions  

## License

Copyright (C) 2011 François Le Lay

Distributed under the Eclipse Public License, the same as Clojure.

