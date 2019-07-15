package ttc19

import java.util.Map
import org.apache.commons.lang3.tuple.Triple
import org.eclipse.xtend.lib.annotations.Accessors
import bddg.Tree

class AuxStruct {
	
	@Accessors
	val Map<Integer,Tree> nodeMap = newHashMap
	@Accessors
	val Map<Tree,Integer> nodeIndex = newHashMap
	@Accessors
	val Map<Triple<String,Integer,Integer>,Tree> treeMap = newHashMap
	
}