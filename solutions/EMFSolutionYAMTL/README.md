# EMFSolutionYAMTL: YAMTL Solution to TTC19 TT2BDD Case

Solution for [TTC'19 TT2BDD case](https://github.com/TransformationToolContest/ttc2019-tt2bdd):


solutions/EMFSolutionYAMTL/src/main/java/YAMTLSolution.xtend

* [Transformation](./src/main/java/ttc19/YAMTLSolution.xtend)
* [Transformation configuration](./src/main/java/ttc19/Solution.xtend)
* [Auxiliary data structures](./src/main/java/ttc19/AuxStruct.xtend)
* [Driver](./src/main/java/ttc19/Driver.xtend)

The configuration in the `.ini` file assumes that Java `11.0.2` is installed at `/usr/libexec/java_home` using the command:

`/usr/libexec/java_home -v 11.0.2 --exec java -Xms4g -Xmx12g -jar ./EMFSolutionYAMTL-all-0.0.4-SNAPSHOT.jar`

Update as appropriate, e.g., `java -Xms4g -Xmx12g -jar ./EMFSolutionYAMTL-all-0.0.4-SNAPSHOT.jar` to execute the jar file with the default JRE.

## Adaptations of the original resources

The `EMFSolutionYAMTL` produces a BDD model with a ROBDD for each boolean expression that is represented in the source truth table, that is, one per output port. To account for this fact, the following adaptations of the original resources have had to me applied:

### Graph BDD Metamodel

The target graph BDD metamodel `BBDv2.ecore` has been adapted and renamed to [BBDv3.ecore](./src/main/resources/metamodels/BDDv3.ecore) with the following changes:

* the upper bound of the reference `BDD::root` is `-1`
* the `nsUri` of the root `EPackage` is `https://www.transformation-tool-contest.eu/2019/bdd/forest`

### Graph Validator

The graph validator has been generalized in order to validate each of the produced ROBDD. The main change is the loop at [line 42](https://github.com/arturboronat/ttc2019-tt2bdd/blob/ee50b6e223d07775fe78a3205c213d45ae4d6c95/solutions/EMFSolutionYAMTL/src/main/java/validator/GraphValidator.java#L42) in `src/main/java/validator/GraphValidator.java`.