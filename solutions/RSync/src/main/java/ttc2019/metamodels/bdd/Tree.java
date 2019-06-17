/**
 */
package ttc2019.metamodels.bdd;

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
 *   <li>{@link ttc2019.metamodels.bdd.Tree#getOwnerBDD <em>Owner BDD</em>}</li>
 *   <li>{@link ttc2019.metamodels.bdd.Tree#getOwnerSubtreeForZero <em>Owner Subtree For Zero</em>}</li>
 *   <li>{@link ttc2019.metamodels.bdd.Tree#getOwnerSubtreeForOne <em>Owner Subtree For One</em>}</li>
 * </ul>
 *
 * @see ttc2019.metamodels.bdd.BDDPackage#getTree()
 * @model abstract="true"
 * @generated
 */
public interface Tree extends EObject {
	/**
	 * Returns the value of the '<em><b>Owner BDD</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link ttc2019.metamodels.bdd.BDD#getTree <em>Tree</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owner BDD</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owner BDD</em>' container reference.
	 * @see #setOwnerBDD(BDD)
	 * @see ttc2019.metamodels.bdd.BDDPackage#getTree_OwnerBDD()
	 * @see ttc2019.metamodels.bdd.BDD#getTree
	 * @model opposite="tree" transient="false" ordered="false"
	 * @generated
	 */
	BDD getOwnerBDD();

	/**
	 * Sets the value of the '{@link ttc2019.metamodels.bdd.Tree#getOwnerBDD <em>Owner BDD</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Owner BDD</em>' container reference.
	 * @see #getOwnerBDD()
	 * @generated
	 */
	void setOwnerBDD(BDD value);

	/**
	 * Returns the value of the '<em><b>Owner Subtree For Zero</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link ttc2019.metamodels.bdd.Subtree#getTreeForZero <em>Tree For Zero</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owner Subtree For Zero</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owner Subtree For Zero</em>' container reference.
	 * @see #setOwnerSubtreeForZero(Subtree)
	 * @see ttc2019.metamodels.bdd.BDDPackage#getTree_OwnerSubtreeForZero()
	 * @see ttc2019.metamodels.bdd.Subtree#getTreeForZero
	 * @model opposite="treeForZero" transient="false" ordered="false"
	 * @generated
	 */
	Subtree getOwnerSubtreeForZero();

	/**
	 * Sets the value of the '{@link ttc2019.metamodels.bdd.Tree#getOwnerSubtreeForZero <em>Owner Subtree For Zero</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Owner Subtree For Zero</em>' container reference.
	 * @see #getOwnerSubtreeForZero()
	 * @generated
	 */
	void setOwnerSubtreeForZero(Subtree value);

	/**
	 * Returns the value of the '<em><b>Owner Subtree For One</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link ttc2019.metamodels.bdd.Subtree#getTreeForOne <em>Tree For One</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owner Subtree For One</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owner Subtree For One</em>' container reference.
	 * @see #setOwnerSubtreeForOne(Subtree)
	 * @see ttc2019.metamodels.bdd.BDDPackage#getTree_OwnerSubtreeForOne()
	 * @see ttc2019.metamodels.bdd.Subtree#getTreeForOne
	 * @model opposite="treeForOne" transient="false" ordered="false"
	 * @generated
	 */
	Subtree getOwnerSubtreeForOne();

	/**
	 * Sets the value of the '{@link ttc2019.metamodels.bdd.Tree#getOwnerSubtreeForOne <em>Owner Subtree For One</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Owner Subtree For One</em>' container reference.
	 * @see #getOwnerSubtreeForOne()
	 * @generated
	 */
	void setOwnerSubtreeForOne(Subtree value);

} // Tree
