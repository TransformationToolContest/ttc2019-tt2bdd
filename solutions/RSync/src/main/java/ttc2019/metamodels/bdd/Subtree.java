/**
 */
package ttc2019.metamodels.bdd;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Subtree</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link ttc2019.metamodels.bdd.Subtree#getPort <em>Port</em>}</li>
 *   <li>{@link ttc2019.metamodels.bdd.Subtree#getTreeForZero <em>Tree For Zero</em>}</li>
 *   <li>{@link ttc2019.metamodels.bdd.Subtree#getTreeForOne <em>Tree For One</em>}</li>
 * </ul>
 *
 * @see ttc2019.metamodels.bdd.BDDPackage#getSubtree()
 * @model
 * @generated
 */
public interface Subtree extends Tree {
	/**
	 * Returns the value of the '<em><b>Port</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link ttc2019.metamodels.bdd.InputPort#getSubtrees <em>Subtrees</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Port</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Port</em>' reference.
	 * @see #setPort(InputPort)
	 * @see ttc2019.metamodels.bdd.BDDPackage#getSubtree_Port()
	 * @see ttc2019.metamodels.bdd.InputPort#getSubtrees
	 * @model opposite="subtrees" required="true" ordered="false"
	 * @generated
	 */
	InputPort getPort();

	/**
	 * Sets the value of the '{@link ttc2019.metamodels.bdd.Subtree#getPort <em>Port</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Port</em>' reference.
	 * @see #getPort()
	 * @generated
	 */
	void setPort(InputPort value);

	/**
	 * Returns the value of the '<em><b>Tree For Zero</b></em>' containment reference.
	 * It is bidirectional and its opposite is '{@link ttc2019.metamodels.bdd.Tree#getOwnerSubtreeForZero <em>Owner Subtree For Zero</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tree For Zero</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tree For Zero</em>' containment reference.
	 * @see #setTreeForZero(Tree)
	 * @see ttc2019.metamodels.bdd.BDDPackage#getSubtree_TreeForZero()
	 * @see ttc2019.metamodels.bdd.Tree#getOwnerSubtreeForZero
	 * @model opposite="ownerSubtreeForZero" containment="true" required="true" ordered="false"
	 * @generated
	 */
	Tree getTreeForZero();

	/**
	 * Sets the value of the '{@link ttc2019.metamodels.bdd.Subtree#getTreeForZero <em>Tree For Zero</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tree For Zero</em>' containment reference.
	 * @see #getTreeForZero()
	 * @generated
	 */
	void setTreeForZero(Tree value);

	/**
	 * Returns the value of the '<em><b>Tree For One</b></em>' containment reference.
	 * It is bidirectional and its opposite is '{@link ttc2019.metamodels.bdd.Tree#getOwnerSubtreeForOne <em>Owner Subtree For One</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tree For One</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tree For One</em>' containment reference.
	 * @see #setTreeForOne(Tree)
	 * @see ttc2019.metamodels.bdd.BDDPackage#getSubtree_TreeForOne()
	 * @see ttc2019.metamodels.bdd.Tree#getOwnerSubtreeForOne
	 * @model opposite="ownerSubtreeForOne" containment="true" required="true" ordered="false"
	 * @generated
	 */
	Tree getTreeForOne();

	/**
	 * Sets the value of the '{@link ttc2019.metamodels.bdd.Subtree#getTreeForOne <em>Tree For One</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tree For One</em>' containment reference.
	 * @see #getTreeForOne()
	 * @generated
	 */
	void setTreeForOne(Tree value);

} // Subtree
