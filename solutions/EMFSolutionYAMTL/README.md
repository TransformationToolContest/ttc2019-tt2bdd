# EMFSolutionYAMTL: YAMTL Solution to TTC19 TT2BDD Case

Solution for [TTC'19 TT2BDD case](https://github.com/TransformationToolContest/ttc2019-tt2bdd):

* [Transformation](./src/main/java/YAMTLSolution.xtend)
* [Transformation configuration](./src/main/java/Solution.xtend)
* [Auxiliary data structures](./src/main/java/AuxStruct.xtend)
* [Driver](./src/main/java/Driver.xtend)

The target graph BDD metamodel `BBDv2.ecore` has been adapted and renamed to [BBDv3.ecore](./src/main/resources/metamodels/BDDv3.ecore) with the following changes:

* the upper bound of the reference `BDD::root` is `-1`
* the `nsUri` of the root `EPackage` is `https://www.transformation-tool-contest.eu/2019/bdd/forest`

The configuration in the `.ini` file assumes that Java `11.0.2` is installed at `/usr/libexec/java_home` using the command:

`/usr/libexec/java_home -v 11.0.2 --exec java -Xms4g -Xmx12g -jar ./EMFSolutionYAMTL-all-0.0.4-SNAPSHOT.jar`

Update as appropriate, e.g., `java -Xms4g -Xmx12g -jar ./EMFSolutionYAMTL-all-0.0.4-SNAPSHOT.jar` to execute the jar file with the default JRE.