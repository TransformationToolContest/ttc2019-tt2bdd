/**
 */
package ttc2019.metamodels.bdd;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>BDD</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link ttc2019.metamodels.bdd.BDD#getName <em>Name</em>}</li>
 *   <li>{@link ttc2019.metamodels.bdd.BDD#getPorts <em>Ports</em>}</li>
 *   <li>{@link ttc2019.metamodels.bdd.BDD#getTree <em>Tree</em>}</li>
 * </ul>
 *
 * @see ttc2019.metamodels.bdd.BDDPackage#getBDD()
 * @model
 * @generated
 */
public interface BDD extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see ttc2019.metamodels.bdd.BDDPackage#getBDD_Name()
	 * @model unique="false" required="true" ordered="false"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link ttc2019.metamodels.bdd.BDD#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Ports</b></em>' containment reference list.
	 * The list contents are of type {@link ttc2019.metamodels.bdd.Port}.
	 * It is bidirectional and its opposite is '{@link ttc2019.metamodels.bdd.Port#getOwner <em>Owner</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ports</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ports</em>' containment reference list.
	 * @see ttc2019.metamodels.bdd.BDDPackage#getBDD_Ports()
	 * @see ttc2019.metamodels.bdd.Port#getOwner
	 * @model opposite="owner" containment="true" required="true" ordered="false"
	 * @generated
	 */
	EList<Port> getPorts();

	/**
	 * Returns the value of the '<em><b>Tree</b></em>' containment reference.
	 * It is bidirectional and its opposite is '{@link ttc2019.metamodels.bdd.Tree#getOwnerBDD <em>Owner BDD</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tree</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tree</em>' containment reference.
	 * @see #setTree(Tree)
	 * @see ttc2019.metamodels.bdd.BDDPackage#getBDD_Tree()
	 * @see ttc2019.metamodels.bdd.Tree#getOwnerBDD
	 * @model opposite="ownerBDD" containment="true" required="true" ordered="false"
	 * @generated
	 */
	Tree getTree();

	/**
	 * Sets the value of the '{@link ttc2019.metamodels.bdd.BDD#getTree <em>Tree</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tree</em>' containment reference.
	 * @see #getTree()
	 * @generated
	 */
	void setTree(Tree value);

} // BDD
