/**
 */
package bddg;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Subtree</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link bddg.Subtree#getPort <em>Port</em>}</li>
 *   <li>{@link bddg.Subtree#getTreeForZero <em>Tree For Zero</em>}</li>
 *   <li>{@link bddg.Subtree#getTreeForOne <em>Tree For One</em>}</li>
 * </ul>
 *
 * @see bddg.BddgPackage#getSubtree()
 * @model
 * @generated
 */
public interface Subtree extends Tree {
	/**
	 * Returns the value of the '<em><b>Port</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link bddg.InputPort#getSubtrees <em>Subtrees</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Port</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Port</em>' reference.
	 * @see #setPort(InputPort)
	 * @see bddg.BddgPackage#getSubtree_Port()
	 * @see bddg.InputPort#getSubtrees
	 * @model opposite="subtrees" required="true" ordered="false"
	 * @generated
	 */
	InputPort getPort();

	/**
	 * Sets the value of the '{@link bddg.Subtree#getPort <em>Port</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Port</em>' reference.
	 * @see #getPort()
	 * @generated
	 */
	void setPort(InputPort value);

	/**
	 * Returns the value of the '<em><b>Tree For Zero</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link bddg.Tree#getOwnerSubtreeForZero <em>Owner Subtree For Zero</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tree For Zero</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tree For Zero</em>' reference.
	 * @see #setTreeForZero(Tree)
	 * @see bddg.BddgPackage#getSubtree_TreeForZero()
	 * @see bddg.Tree#getOwnerSubtreeForZero
	 * @model opposite="ownerSubtreeForZero" required="true" ordered="false"
	 * @generated
	 */
	Tree getTreeForZero();

	/**
	 * Sets the value of the '{@link bddg.Subtree#getTreeForZero <em>Tree For Zero</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tree For Zero</em>' reference.
	 * @see #getTreeForZero()
	 * @generated
	 */
	void setTreeForZero(Tree value);

	/**
	 * Returns the value of the '<em><b>Tree For One</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link bddg.Tree#getOwnerSubtreeForOne <em>Owner Subtree For One</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tree For One</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tree For One</em>' reference.
	 * @see #setTreeForOne(Tree)
	 * @see bddg.BddgPackage#getSubtree_TreeForOne()
	 * @see bddg.Tree#getOwnerSubtreeForOne
	 * @model opposite="ownerSubtreeForOne" required="true" ordered="false"
	 * @generated
	 */
	Tree getTreeForOne();

	/**
	 * Sets the value of the '{@link bddg.Subtree#getTreeForOne <em>Tree For One</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tree For One</em>' reference.
	 * @see #getTreeForOne()
	 * @generated
	 */
	void setTreeForOne(Tree value);

} // Subtree
