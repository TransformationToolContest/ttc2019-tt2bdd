
Model transformation case study using ATL :
Truth tables to Binary Decision Diagrams

Guillaume Savaton, ESEO
<guillaume (dot) savaton (at) eseo (dot) fr>

-----------------------------------------------------------
Summary
-----------------------------------------------------------

These files provide a case study for model transformation applied to digital logic circuits.
Engineers can use various formalisms to represent combinatorial or sequential logic.
The most widespread representations for combinatorial logic are truth tables and boolean equations.

In many EDA tools, combinatorial logic is represented in a canonical form using Binary Decision
Diagrams (BDD).
BDDs are manly used for model checking and logic optimization.

The purpose here is to use model transformation to transform a truth table into
a binary decision diagram.

-----------------------------------------------------------
Truth tables
-----------------------------------------------------------

The test files provided correspond to the following example.
Files TT/examples/Test.tt and Test.ttmodel contain representations of this truth table :

 A B C D  | S
----------|---
 0 0 - -  | 0
 0 1 0 0  | 1
 0 1 0 1  | 0
 0 1 1 -  | 0
 1 0 0 0  | 0
 1 0 1 0  | 1
 1 - - 1  | 0
 1 1 0 0  | 1
 1 1 1 0  | 0

A truth table (class TruthTable of the metamodel) has ports (class Port).
Ports are either
- input ports (class InputPort) such as A, B, C, D in the example ;
- or output ports (class OutputPort) such as S in the example.

A truth table is composed of rows (class Row of the metamodels) that can appear in any order.
A row provides a mapping between an input pattern (a detected set of values of input ports)
and an output pattern (values assigned to output ports).
A row is made of cells (class Cell) : each cell associates a boolean value to a given port.

Usually, boolean values are represented using symbols "0" (false) and "1" (true).
In file Test.tt, the symbols used are "F" (false) and "T" (true).

In a given row, the absence of a cell for a given input port means that the value of this port is
indifferent with respect to the result. In the example, this is the case for ports C and D in the first row.
However, in each row, all output ports must have a corresponding cell.

-----------------------------------------------------------
Binary decision diagrams
-----------------------------------------------------------

Here is a BDD representation for the example truth table :

                     (A)
                      |
          0 --------------------- 1
          |                       |
         (B)                     (D)
          |                       |
     0 ------- 1             0 -------- 1
     |         |             |          |
     |        (C)           (B)         |
     |         |             |          |
     |      0 --- 1     0---------1     |
     |      |     |     |         |     |
     |     (D)    |    (C)       (C)    |
     |      |     |     |         |     |
     |   0 --- 1  |  0 --- 1   0 --- 1  |
     |   |     |  |  |     |   |     |  |
S = [0] [1]   [0][0][0]   [1] [1]   [0][0]  

Usually, edges are distinguished by a line style :
- dashed lines represent 0 (or false) decisions
- continuouse lines represent 1 (or true) decisions.

In file Test.bdd, edges are labelled with a symbol : "F" (false) or "T" (true).

The evaluation process in a BDD is recursive and can be easily translated into an if-then-else
hierarchy :

if A=0 then
   if B=0 then
      S := 0;
   else
      if C=0 then
         if D=0 then
            S := 1;
         else
            S := 0;
         endif
      else
         S := 0;
      endif
   endif
else
   if D=0 then
      if B=0 then
         if C=0 then
            S := 0;
         else
            S := 1;
         endif
      else
         if C=0 then
            S := 1;
         else
            S := 0;
         endif
      endif
   else
      S := 0;
   endif
endif

In our metamodel, a binary decision diagram (class BDD of the metamodel) has input and output ports in
the same sense as truth tables.

A BDD is composed of a tree (class Tree).
A tree can be either a leaf node (class Leaf) or a subtree (class Subtree).
A subtree has a reference to an input port (see nodes labeled A, B, C and D on the example).
Upon the value of this port, two edges can be followed :
- one corresponding to a 0 (false) value (reference subtreeForZero in the metamodel)
- one corresponding to a 1 (true) value (reference subtreeForOne in the metamodel)

Leaf nodes represent values assigned to output ports.
In this example there is only one output port but the provided metamodel supports
multiple output ports if needed.
As a result, a leaf node is composed of one or several assignments (class Assignment).

-----------------------------------------------------------
File index
-----------------------------------------------------------

Folders :
   TT/      - All files related to truth tables (metamodel, models, diagrams)
   BDD/     - All files related to Binary Decision Diagrams (metamodel, models, diagrams)
   TT2BDD/  - The transformation from truth table to BDD

Files :

   TT/TT.km3                     - The Truth Table metamodel in text form (KM3 notation)
   TT/TT.ecore                   - The Truth Table metamodel in XMI format

   TT/diagrams/TT.png            - Graphical representation of the Truth Table metamodel

   TT/examples/Test.tt           - An example truth table in text form
   TT/examples/Test.ttmodel      - The same example in XMI format

   BDD/BDD.km3                   - The BDD metamodel in text form (KM3 notation)
   BDD/BDD.ecore                 - The BDD metamodel in XMI format
   
   BDD/diagrams/BDD.png          -
   BDD/diagrams/Tree.png         - Graphical representation of the BDD metamodel
   BDD/diagrams/Nodes.png        -
   
   BDD/examples/Test.bdd         - An example BDD in text form
   BDD/examples/Test.bddmodel    - The same example BDD in XMI format

   TT2BDD/TT2BDD.atl             - The transformation from Truth Table to BDD, in ATL

