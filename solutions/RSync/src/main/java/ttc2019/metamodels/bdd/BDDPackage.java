/**
 */
package ttc2019.metamodels.bdd;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see ttc2019.metamodels.bdd.BDDFactory
 * @model kind="package"
 * @generated
 */
public interface BDDPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "bdd";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "https://www.transformation-tool-contest.eu/2019/bdd";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "bdd";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	BDDPackage eINSTANCE = ttc2019.metamodels.bdd.impl.BDDPackageImpl.init();

	/**
	 * The meta object id for the '{@link ttc2019.metamodels.bdd.impl.BDDImpl <em>BDD</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see ttc2019.metamodels.bdd.impl.BDDImpl
	 * @see ttc2019.metamodels.bdd.impl.BDDPackageImpl#getBDD()
	 * @generated
	 */
	int BDD = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD__NAME = 0;

	/**
	 * The feature id for the '<em><b>Ports</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD__PORTS = 1;

	/**
	 * The feature id for the '<em><b>Tree</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD__TREE = 2;

	/**
	 * The number of structural features of the '<em>BDD</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>BDD</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link ttc2019.metamodels.bdd.impl.PortImpl <em>Port</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see ttc2019.metamodels.bdd.impl.PortImpl
	 * @see ttc2019.metamodels.bdd.impl.BDDPackageImpl#getPort()
	 * @generated
	 */
	int PORT = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT__NAME = 0;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT__OWNER = 1;

	/**
	 * The number of structural features of the '<em>Port</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Port</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link ttc2019.metamodels.bdd.impl.InputPortImpl <em>Input Port</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see ttc2019.metamodels.bdd.impl.InputPortImpl
	 * @see ttc2019.metamodels.bdd.impl.BDDPackageImpl#getInputPort()
	 * @generated
	 */
	int INPUT_PORT = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INPUT_PORT__NAME = PORT__NAME;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INPUT_PORT__OWNER = PORT__OWNER;

	/**
	 * The feature id for the '<em><b>Subtrees</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INPUT_PORT__SUBTREES = PORT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Input Port</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INPUT_PORT_FEATURE_COUNT = PORT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Input Port</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INPUT_PORT_OPERATION_COUNT = PORT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link ttc2019.metamodels.bdd.impl.OutputPortImpl <em>Output Port</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see ttc2019.metamodels.bdd.impl.OutputPortImpl
	 * @see ttc2019.metamodels.bdd.impl.BDDPackageImpl#getOutputPort()
	 * @generated
	 */
	int OUTPUT_PORT = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OUTPUT_PORT__NAME = PORT__NAME;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OUTPUT_PORT__OWNER = PORT__OWNER;

	/**
	 * The feature id for the '<em><b>Assignments</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OUTPUT_PORT__ASSIGNMENTS = PORT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Output Port</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OUTPUT_PORT_FEATURE_COUNT = PORT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Output Port</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OUTPUT_PORT_OPERATION_COUNT = PORT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link ttc2019.metamodels.bdd.impl.TreeImpl <em>Tree</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see ttc2019.metamodels.bdd.impl.TreeImpl
	 * @see ttc2019.metamodels.bdd.impl.BDDPackageImpl#getTree()
	 * @generated
	 */
	int TREE = 4;

	/**
	 * The feature id for the '<em><b>Owner BDD</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TREE__OWNER_BDD = 0;

	/**
	 * The feature id for the '<em><b>Owner Subtree For Zero</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TREE__OWNER_SUBTREE_FOR_ZERO = 1;

	/**
	 * The feature id for the '<em><b>Owner Subtree For One</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TREE__OWNER_SUBTREE_FOR_ONE = 2;

	/**
	 * The number of structural features of the '<em>Tree</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TREE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Tree</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TREE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link ttc2019.metamodels.bdd.impl.LeafImpl <em>Leaf</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see ttc2019.metamodels.bdd.impl.LeafImpl
	 * @see ttc2019.metamodels.bdd.impl.BDDPackageImpl#getLeaf()
	 * @generated
	 */
	int LEAF = 5;

	/**
	 * The feature id for the '<em><b>Owner BDD</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEAF__OWNER_BDD = TREE__OWNER_BDD;

	/**
	 * The feature id for the '<em><b>Owner Subtree For Zero</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEAF__OWNER_SUBTREE_FOR_ZERO = TREE__OWNER_SUBTREE_FOR_ZERO;

	/**
	 * The feature id for the '<em><b>Owner Subtree For One</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEAF__OWNER_SUBTREE_FOR_ONE = TREE__OWNER_SUBTREE_FOR_ONE;

	/**
	 * The feature id for the '<em><b>Assignments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEAF__ASSIGNMENTS = TREE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Leaf</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEAF_FEATURE_COUNT = TREE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Leaf</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEAF_OPERATION_COUNT = TREE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link ttc2019.metamodels.bdd.impl.AssignmentImpl <em>Assignment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see ttc2019.metamodels.bdd.impl.AssignmentImpl
	 * @see ttc2019.metamodels.bdd.impl.BDDPackageImpl#getAssignment()
	 * @generated
	 */
	int ASSIGNMENT = 6;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSIGNMENT__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Port</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSIGNMENT__PORT = 1;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSIGNMENT__OWNER = 2;

	/**
	 * The number of structural features of the '<em>Assignment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSIGNMENT_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Assignment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSIGNMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link ttc2019.metamodels.bdd.impl.SubtreeImpl <em>Subtree</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see ttc2019.metamodels.bdd.impl.SubtreeImpl
	 * @see ttc2019.metamodels.bdd.impl.BDDPackageImpl#getSubtree()
	 * @generated
	 */
	int SUBTREE = 7;

	/**
	 * The feature id for the '<em><b>Owner BDD</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBTREE__OWNER_BDD = TREE__OWNER_BDD;

	/**
	 * The feature id for the '<em><b>Owner Subtree For Zero</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBTREE__OWNER_SUBTREE_FOR_ZERO = TREE__OWNER_SUBTREE_FOR_ZERO;

	/**
	 * The feature id for the '<em><b>Owner Subtree For One</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBTREE__OWNER_SUBTREE_FOR_ONE = TREE__OWNER_SUBTREE_FOR_ONE;

	/**
	 * The feature id for the '<em><b>Port</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBTREE__PORT = TREE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Tree For Zero</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBTREE__TREE_FOR_ZERO = TREE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Tree For One</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBTREE__TREE_FOR_ONE = TREE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Subtree</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBTREE_FEATURE_COUNT = TREE_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Subtree</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBTREE_OPERATION_COUNT = TREE_OPERATION_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link ttc2019.metamodels.bdd.BDD <em>BDD</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>BDD</em>'.
	 * @see ttc2019.metamodels.bdd.BDD
	 * @generated
	 */
	EClass getBDD();

	/**
	 * Returns the meta object for the attribute '{@link ttc2019.metamodels.bdd.BDD#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see ttc2019.metamodels.bdd.BDD#getName()
	 * @see #getBDD()
	 * @generated
	 */
	EAttribute getBDD_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link ttc2019.metamodels.bdd.BDD#getPorts <em>Ports</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Ports</em>'.
	 * @see ttc2019.metamodels.bdd.BDD#getPorts()
	 * @see #getBDD()
	 * @generated
	 */
	EReference getBDD_Ports();

	/**
	 * Returns the meta object for the containment reference '{@link ttc2019.metamodels.bdd.BDD#getTree <em>Tree</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Tree</em>'.
	 * @see ttc2019.metamodels.bdd.BDD#getTree()
	 * @see #getBDD()
	 * @generated
	 */
	EReference getBDD_Tree();

	/**
	 * Returns the meta object for class '{@link ttc2019.metamodels.bdd.Port <em>Port</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Port</em>'.
	 * @see ttc2019.metamodels.bdd.Port
	 * @generated
	 */
	EClass getPort();

	/**
	 * Returns the meta object for the attribute '{@link ttc2019.metamodels.bdd.Port#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see ttc2019.metamodels.bdd.Port#getName()
	 * @see #getPort()
	 * @generated
	 */
	EAttribute getPort_Name();

	/**
	 * Returns the meta object for the container reference '{@link ttc2019.metamodels.bdd.Port#getOwner <em>Owner</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Owner</em>'.
	 * @see ttc2019.metamodels.bdd.Port#getOwner()
	 * @see #getPort()
	 * @generated
	 */
	EReference getPort_Owner();

	/**
	 * Returns the meta object for class '{@link ttc2019.metamodels.bdd.InputPort <em>Input Port</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Input Port</em>'.
	 * @see ttc2019.metamodels.bdd.InputPort
	 * @generated
	 */
	EClass getInputPort();

	/**
	 * Returns the meta object for the reference list '{@link ttc2019.metamodels.bdd.InputPort#getSubtrees <em>Subtrees</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Subtrees</em>'.
	 * @see ttc2019.metamodels.bdd.InputPort#getSubtrees()
	 * @see #getInputPort()
	 * @generated
	 */
	EReference getInputPort_Subtrees();

	/**
	 * Returns the meta object for class '{@link ttc2019.metamodels.bdd.OutputPort <em>Output Port</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Output Port</em>'.
	 * @see ttc2019.metamodels.bdd.OutputPort
	 * @generated
	 */
	EClass getOutputPort();

	/**
	 * Returns the meta object for the reference list '{@link ttc2019.metamodels.bdd.OutputPort#getAssignments <em>Assignments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Assignments</em>'.
	 * @see ttc2019.metamodels.bdd.OutputPort#getAssignments()
	 * @see #getOutputPort()
	 * @generated
	 */
	EReference getOutputPort_Assignments();

	/**
	 * Returns the meta object for class '{@link ttc2019.metamodels.bdd.Tree <em>Tree</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Tree</em>'.
	 * @see ttc2019.metamodels.bdd.Tree
	 * @generated
	 */
	EClass getTree();

	/**
	 * Returns the meta object for the container reference '{@link ttc2019.metamodels.bdd.Tree#getOwnerBDD <em>Owner BDD</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Owner BDD</em>'.
	 * @see ttc2019.metamodels.bdd.Tree#getOwnerBDD()
	 * @see #getTree()
	 * @generated
	 */
	EReference getTree_OwnerBDD();

	/**
	 * Returns the meta object for the container reference '{@link ttc2019.metamodels.bdd.Tree#getOwnerSubtreeForZero <em>Owner Subtree For Zero</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Owner Subtree For Zero</em>'.
	 * @see ttc2019.metamodels.bdd.Tree#getOwnerSubtreeForZero()
	 * @see #getTree()
	 * @generated
	 */
	EReference getTree_OwnerSubtreeForZero();

	/**
	 * Returns the meta object for the container reference '{@link ttc2019.metamodels.bdd.Tree#getOwnerSubtreeForOne <em>Owner Subtree For One</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Owner Subtree For One</em>'.
	 * @see ttc2019.metamodels.bdd.Tree#getOwnerSubtreeForOne()
	 * @see #getTree()
	 * @generated
	 */
	EReference getTree_OwnerSubtreeForOne();

	/**
	 * Returns the meta object for class '{@link ttc2019.metamodels.bdd.Leaf <em>Leaf</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Leaf</em>'.
	 * @see ttc2019.metamodels.bdd.Leaf
	 * @generated
	 */
	EClass getLeaf();

	/**
	 * Returns the meta object for the containment reference list '{@link ttc2019.metamodels.bdd.Leaf#getAssignments <em>Assignments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Assignments</em>'.
	 * @see ttc2019.metamodels.bdd.Leaf#getAssignments()
	 * @see #getLeaf()
	 * @generated
	 */
	EReference getLeaf_Assignments();

	/**
	 * Returns the meta object for class '{@link ttc2019.metamodels.bdd.Assignment <em>Assignment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Assignment</em>'.
	 * @see ttc2019.metamodels.bdd.Assignment
	 * @generated
	 */
	EClass getAssignment();

	/**
	 * Returns the meta object for the attribute '{@link ttc2019.metamodels.bdd.Assignment#isValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see ttc2019.metamodels.bdd.Assignment#isValue()
	 * @see #getAssignment()
	 * @generated
	 */
	EAttribute getAssignment_Value();

	/**
	 * Returns the meta object for the reference '{@link ttc2019.metamodels.bdd.Assignment#getPort <em>Port</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Port</em>'.
	 * @see ttc2019.metamodels.bdd.Assignment#getPort()
	 * @see #getAssignment()
	 * @generated
	 */
	EReference getAssignment_Port();

	/**
	 * Returns the meta object for the container reference '{@link ttc2019.metamodels.bdd.Assignment#getOwner <em>Owner</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Owner</em>'.
	 * @see ttc2019.metamodels.bdd.Assignment#getOwner()
	 * @see #getAssignment()
	 * @generated
	 */
	EReference getAssignment_Owner();

	/**
	 * Returns the meta object for class '{@link ttc2019.metamodels.bdd.Subtree <em>Subtree</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Subtree</em>'.
	 * @see ttc2019.metamodels.bdd.Subtree
	 * @generated
	 */
	EClass getSubtree();

	/**
	 * Returns the meta object for the reference '{@link ttc2019.metamodels.bdd.Subtree#getPort <em>Port</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Port</em>'.
	 * @see ttc2019.metamodels.bdd.Subtree#getPort()
	 * @see #getSubtree()
	 * @generated
	 */
	EReference getSubtree_Port();

	/**
	 * Returns the meta object for the containment reference '{@link ttc2019.metamodels.bdd.Subtree#getTreeForZero <em>Tree For Zero</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Tree For Zero</em>'.
	 * @see ttc2019.metamodels.bdd.Subtree#getTreeForZero()
	 * @see #getSubtree()
	 * @generated
	 */
	EReference getSubtree_TreeForZero();

	/**
	 * Returns the meta object for the containment reference '{@link ttc2019.metamodels.bdd.Subtree#getTreeForOne <em>Tree For One</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Tree For One</em>'.
	 * @see ttc2019.metamodels.bdd.Subtree#getTreeForOne()
	 * @see #getSubtree()
	 * @generated
	 */
	EReference getSubtree_TreeForOne();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	BDDFactory getBDDFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link ttc2019.metamodels.bdd.impl.BDDImpl <em>BDD</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see ttc2019.metamodels.bdd.impl.BDDImpl
		 * @see ttc2019.metamodels.bdd.impl.BDDPackageImpl#getBDD()
		 * @generated
		 */
		EClass BDD = eINSTANCE.getBDD();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BDD__NAME = eINSTANCE.getBDD_Name();

		/**
		 * The meta object literal for the '<em><b>Ports</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BDD__PORTS = eINSTANCE.getBDD_Ports();

		/**
		 * The meta object literal for the '<em><b>Tree</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BDD__TREE = eINSTANCE.getBDD_Tree();

		/**
		 * The meta object literal for the '{@link ttc2019.metamodels.bdd.impl.PortImpl <em>Port</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see ttc2019.metamodels.bdd.impl.PortImpl
		 * @see ttc2019.metamodels.bdd.impl.BDDPackageImpl#getPort()
		 * @generated
		 */
		EClass PORT = eINSTANCE.getPort();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PORT__NAME = eINSTANCE.getPort_Name();

		/**
		 * The meta object literal for the '<em><b>Owner</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PORT__OWNER = eINSTANCE.getPort_Owner();

		/**
		 * The meta object literal for the '{@link ttc2019.metamodels.bdd.impl.InputPortImpl <em>Input Port</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see ttc2019.metamodels.bdd.impl.InputPortImpl
		 * @see ttc2019.metamodels.bdd.impl.BDDPackageImpl#getInputPort()
		 * @generated
		 */
		EClass INPUT_PORT = eINSTANCE.getInputPort();

		/**
		 * The meta object literal for the '<em><b>Subtrees</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INPUT_PORT__SUBTREES = eINSTANCE.getInputPort_Subtrees();

		/**
		 * The meta object literal for the '{@link ttc2019.metamodels.bdd.impl.OutputPortImpl <em>Output Port</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see ttc2019.metamodels.bdd.impl.OutputPortImpl
		 * @see ttc2019.metamodels.bdd.impl.BDDPackageImpl#getOutputPort()
		 * @generated
		 */
		EClass OUTPUT_PORT = eINSTANCE.getOutputPort();

		/**
		 * The meta object literal for the '<em><b>Assignments</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OUTPUT_PORT__ASSIGNMENTS = eINSTANCE.getOutputPort_Assignments();

		/**
		 * The meta object literal for the '{@link ttc2019.metamodels.bdd.impl.TreeImpl <em>Tree</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see ttc2019.metamodels.bdd.impl.TreeImpl
		 * @see ttc2019.metamodels.bdd.impl.BDDPackageImpl#getTree()
		 * @generated
		 */
		EClass TREE = eINSTANCE.getTree();

		/**
		 * The meta object literal for the '<em><b>Owner BDD</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TREE__OWNER_BDD = eINSTANCE.getTree_OwnerBDD();

		/**
		 * The meta object literal for the '<em><b>Owner Subtree For Zero</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TREE__OWNER_SUBTREE_FOR_ZERO = eINSTANCE.getTree_OwnerSubtreeForZero();

		/**
		 * The meta object literal for the '<em><b>Owner Subtree For One</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TREE__OWNER_SUBTREE_FOR_ONE = eINSTANCE.getTree_OwnerSubtreeForOne();

		/**
		 * The meta object literal for the '{@link ttc2019.metamodels.bdd.impl.LeafImpl <em>Leaf</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see ttc2019.metamodels.bdd.impl.LeafImpl
		 * @see ttc2019.metamodels.bdd.impl.BDDPackageImpl#getLeaf()
		 * @generated
		 */
		EClass LEAF = eINSTANCE.getLeaf();

		/**
		 * The meta object literal for the '<em><b>Assignments</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LEAF__ASSIGNMENTS = eINSTANCE.getLeaf_Assignments();

		/**
		 * The meta object literal for the '{@link ttc2019.metamodels.bdd.impl.AssignmentImpl <em>Assignment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see ttc2019.metamodels.bdd.impl.AssignmentImpl
		 * @see ttc2019.metamodels.bdd.impl.BDDPackageImpl#getAssignment()
		 * @generated
		 */
		EClass ASSIGNMENT = eINSTANCE.getAssignment();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ASSIGNMENT__VALUE = eINSTANCE.getAssignment_Value();

		/**
		 * The meta object literal for the '<em><b>Port</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ASSIGNMENT__PORT = eINSTANCE.getAssignment_Port();

		/**
		 * The meta object literal for the '<em><b>Owner</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ASSIGNMENT__OWNER = eINSTANCE.getAssignment_Owner();

		/**
		 * The meta object literal for the '{@link ttc2019.metamodels.bdd.impl.SubtreeImpl <em>Subtree</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see ttc2019.metamodels.bdd.impl.SubtreeImpl
		 * @see ttc2019.metamodels.bdd.impl.BDDPackageImpl#getSubtree()
		 * @generated
		 */
		EClass SUBTREE = eINSTANCE.getSubtree();

		/**
		 * The meta object literal for the '<em><b>Port</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SUBTREE__PORT = eINSTANCE.getSubtree_Port();

		/**
		 * The meta object literal for the '<em><b>Tree For Zero</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SUBTREE__TREE_FOR_ZERO = eINSTANCE.getSubtree_TreeForZero();

		/**
		 * The meta object literal for the '<em><b>Tree For One</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SUBTREE__TREE_FOR_ONE = eINSTANCE.getSubtree_TreeForOne();

	}

} //BDDPackage
