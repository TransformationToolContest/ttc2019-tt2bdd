/**
 */
package bddg;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Tree</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link bddg.Tree#getOwnerBDD <em>Owner BDD</em>}</li>
 *   <li>{@link bddg.Tree#getOwnerSubtreeForZero <em>Owner Subtree For Zero</em>}</li>
 *   <li>{@link bddg.Tree#getOwnerSubtreeForOne <em>Owner Subtree For One</em>}</li>
 * </ul>
 *
 * @see bddg.BddgPackage#getTree()
 * @model abstract="true"
 * @generated
 */
public interface Tree extends EObject {
	/**
	 * Returns the value of the '<em><b>Owner BDD</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link bddg.BDD#getTrees <em>Trees</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owner BDD</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owner BDD</em>' container reference.
	 * @see #setOwnerBDD(BDD)
	 * @see bddg.BddgPackage#getTree_OwnerBDD()
	 * @see bddg.BDD#getTrees
	 * @model opposite="trees" transient="false" ordered="false"
	 * @generated
	 */
	BDD getOwnerBDD();

	/**
	 * Sets the value of the '{@link bddg.Tree#getOwnerBDD <em>Owner BDD</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Owner BDD</em>' container reference.
	 * @see #getOwnerBDD()
	 * @generated
	 */
	void setOwnerBDD(BDD value);

	/**
	 * Returns the value of the '<em><b>Owner Subtree For Zero</b></em>' reference list.
	 * The list contents are of type {@link bddg.Subtree}.
	 * It is bidirectional and its opposite is '{@link bddg.Subtree#getTreeForZero <em>Tree For Zero</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owner Subtree For Zero</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owner Subtree For Zero</em>' reference list.
	 * @see bddg.BddgPackage#getTree_OwnerSubtreeForZero()
	 * @see bddg.Subtree#getTreeForZero
	 * @model opposite="treeForZero" ordered="false"
	 * @generated
	 */
	EList<Subtree> getOwnerSubtreeForZero();

	/**
	 * Returns the value of the '<em><b>Owner Subtree For One</b></em>' reference list.
	 * The list contents are of type {@link bddg.Subtree}.
	 * It is bidirectional and its opposite is '{@link bddg.Subtree#getTreeForOne <em>Tree For One</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owner Subtree For One</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owner Subtree For One</em>' reference list.
	 * @see bddg.BddgPackage#getTree_OwnerSubtreeForOne()
	 * @see bddg.Subtree#getTreeForOne
	 * @model opposite="treeForOne" ordered="false"
	 * @generated
	 */
	EList<Subtree> getOwnerSubtreeForOne();

} // Tree
