package ttc19

import java.util.ArrayList
import java.util.List
import java.util.Map
import org.apache.commons.lang3.tuple.ImmutableTriple
import org.eclipse.xtend.lib.annotations.Accessors
import bddg.BDD
import bddg.InputPort
import bddg.Leaf
import bddg.OutputPort
import bddg.Port
import bddg.Tree
import bddg.BddgFactory
import bddg.BddgPackage
import tt.Row
import tt.TTPackage
import tt.TruthTable
import yamtl.core.YAMTLModule
import yamtl.dsl.Rule

class YAMTLSolution extends YAMTLModule {
	val TT = TTPackage.eINSTANCE  
	val BDD = BddgPackage.eINSTANCE  

	@Accessors
	val Map<tt.OutputPort,List<List<Integer>>> dnfMap = newHashMap 

	new () {
		header().in('tt', TT).out('bdd', BDD)
		
		ruleStore( newArrayList(
						
			new Rule('TableToBDD')
				.in('tt', TT.truthTable).build()
				.out('bdd', BDD.BDD, [ val tt = 'tt'.fetch as TruthTable; val bdd = 'bdd'.fetch as BDD
					bdd.ports += tt.ports.fetch('bdd_p','InputPort') as List<InputPort>
					bdd.ports += tt.ports.fetch('bdd_p','OutputPort') as List<OutputPort>
					bdd.root += dnfMap.build(bdd)
				]).build()
			.build(),
			
			new Rule('InputPort')
				.in('tt_p', TT.inputPort).build()
				.out('bdd_p', BDD.inputPort, [ val tt_p = 'tt_p'.fetch as tt.InputPort; val bdd_p = 'bdd_p'.fetch as InputPort
					bdd_p.name = tt_p.name
				]).build()
			.build(),
			
			new Rule('OutputPort')
				.in('tt_p', TT.outputPort).build()
				.out('bdd_p', BDD.outputPort, [	val tt_p = 'tt_p'.fetch as tt.OutputPort; val bdd_p = 'bdd_p'.fetch as OutputPort
					bdd_p.name = tt_p.name
				]).build()
			.build(),
			
			new Rule('Row').priority(1).transient // input cells are assumed to appear before output cells
				.in('r', TT.row).build()
				.out('a', BDD.assignment, [ val r = 'r'.fetch as Row // this assignment will not be stored
					val portList = new ArrayList<tt.Port>(r.owner.ports)
					val oPortList = portList.filter[it instanceof tt.OutputPort].map[it as tt.OutputPort] 
					if (dnfMap.empty) // initialize list of boolean expressions, one per output port
						for (oPort: oPortList) dnfMap.put(oPort, newArrayList)
					val List<Integer> clause = newArrayList
					for (c: r.cells) // each row yields a dnf formula 
						if (c.port != portList.head) { // a port is not set in the row
							clause.add(2) // considered as a tautology: c.port or not(c.port)
						} else {
							portList.remove(c.port)
							if (c.port instanceof tt.InputPort) 
								clause.add(c.value.toInt) // toInt: false => 0, true => 1
							else // when the dnf is valid, it is added to the corresponding expression
								if (c.value) dnfMap.get(c.port).add(clause)
						}
				]).build()/* for out element */.build()/* for rule */
		))
	}
	
	// ------------------------------------------------
	// HELPERS FOR BUILDING ROBDDs
	// ------------------------------------------------
	def build(Map<tt.OutputPort,List<List<Integer>>> dnfMap, BDD bdd) {
		var List<Tree> rootTreeList = newArrayList
		val iPortList = bdd.ports.filter[it instanceof InputPort].map[it as InputPort].toList
		for (entry: dnfMap.entrySet) {
			val oPort = bdd.ports.findFirst[it.name==entry.key.name] as OutputPort
			rootTreeList.add(entry.value.build(iPortList, oPort, new AuxStruct))
		}
		rootTreeList
	}

	def build(List<List<Integer>> dnf, List<InputPort> iPortList, OutputPort oPort, AuxStruct aux) {
		val iPort = iPortList.head
		val portTail = iPortList.tail.toList
		// create successors
		var tree0 = dnf.clone().buildTree(iPort, portTail, oPort, 0, aux)
		var tree1 = dnf.clone().buildTree(iPort, portTail, oPort, 1, aux)
		// create tree by adding root
		var Tree tree
		val zeroIndex = aux.nodeIndex.get(tree0)
		val oneIndex = aux.nodeIndex.get(tree1)
		if (zeroIndex == oneIndex) { // avoid redundant tests
			tree = tree0 
		} else { // create tree if not yet indexed by port name, zero index and one index
			val triple = new ImmutableTriple(iPort.name, zeroIndex, oneIndex)
			tree = aux.treeMap.get(triple)
			if (tree === null) {
				tree = iPort.createSubtree(tree0, tree1, aux) // helper that creates a subtree with root x and two children
				aux.treeMap.put(triple,tree)
			} 
		}
		tree
	}

	def Tree buildTree(List<List<Integer>> dnf, Port iPort, List<InputPort> portTail, OutputPort oPort, Integer value, AuxStruct aux) {
		var Tree tree
		val reducedDNF = dnf.reduce(iPort, value) // applies substitution value/x and cancels invalid dnfs 
		if (!portTail.empty) { // there are still more variables to be reduced
			tree = reducedDNF.build(portTail, oPort, aux)
		} else { // no variables left
			if (reducedDNF.empty) // no dnfs left in the expression: the result is 0
				tree = oPort.createLeaf(0, aux) // helper that creates leaf node for oPort and indexes it at 0
			else // the result is 1
				tree = oPort.createLeaf(1, aux) // helper that creates leaf node for oPort and indexes it at 1
		}
		tree
	}
		
	def reduce(List<List<Integer>> dnf, Port x, Integer value) {
		val i = x.owner.ports.indexOf(x)
		// dnf expressions contain vbles, which are cancelled when they are set to their complement
		// e.g. [1/x]~x and [0/x]x 
		dnf.removeIf([r | r.get(i) != value])
		dnf.filter[r | r.get(i)==2].forEach[r | r.set(i, value.complement)]
		dnf
	}
	
	// ------------------------------------------------
	// UTILITY HELPERS
	// ------------------------------------------------
	def createSubtree(InputPort iPort, Tree tree0, Tree tree1, AuxStruct aux) {
		val subTree = BddgFactory.eINSTANCE.createSubtree
		subTree.port = iPort
		subTree.treeForZero = tree0
		subTree.treeForOne = tree1
		iPort.owner.trees.add(subTree)
		// index the subtree
		val i = aux.nodeMap.keySet.sort.last + 1
		aux.nodeMap.put(i, subTree)
		aux.nodeIndex.put(subTree, i)
		subTree
	}
	
	def createLeaf(OutputPort oPort, int value, AuxStruct aux) {
		var leaf = aux.nodeMap.get(value) as Leaf
		if (leaf === null) {
			leaf = BddgFactory.eINSTANCE.createLeaf
			val ass = BddgFactory.eINSTANCE.createAssignment
			ass.port = oPort
			ass.value = if (value==1) true else false
			leaf.assignments.add(ass)
			leaf.ownerBDD = oPort.owner
			// index the leaf
			aux.nodeMap.put(value,leaf) 
			aux.nodeIndex.put(leaf,value)
		}
		leaf
	}
	
	def Integer complement(Integer v) {
		if (v==1) 0 else 1
	}
	
	def Integer toInt(Boolean value) {
		if (value) 1 else 0
	}
	
	def clone(List<List<Integer>> dnf) {
		new ArrayList<List<Integer>>(dnf)
	}
}
