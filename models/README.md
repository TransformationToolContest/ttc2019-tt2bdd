Models folder
===

The original model is `Test.ttmodel`. For TTC2019 we have created a simple
generator which can produce tables of arbitrary size, with a specified number
of inputs and outputs. The outputs are randomly generated according to a
certain seed.

To run the generator, simply use:

    java -jar generator.jar nInputs nOutputs seed

`nInputs`, `nOutputs` and `seed` should be positive integers that specify the
number of input ports and output ports of the truth table, and the random seed
used for the truth table respectively.

The code for the generator is in the Eclipse `ttc2019.model.generator` project,
which includes a `.tests` fragment project with some simple unit tests.
