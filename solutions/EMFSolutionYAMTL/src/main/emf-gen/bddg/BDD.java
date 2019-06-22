/**
 */
package bddg;

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
 *   <li>{@link bddg.BDD#getName <em>Name</em>}</li>
 *   <li>{@link bddg.BDD#getPorts <em>Ports</em>}</li>
 *   <li>{@link bddg.BDD#getRoot <em>Root</em>}</li>
 *   <li>{@link bddg.BDD#getTrees <em>Trees</em>}</li>
 * </ul>
 *
 * @see bddg.BddgPackage#getBDD()
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
	 * @see bddg.BddgPackage#getBDD_Name()
	 * @model unique="false" required="true" ordered="false"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link bddg.BDD#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Ports</b></em>' containment reference list.
	 * The list contents are of type {@link bddg.Port}.
	 * It is bidirectional and its opposite is '{@link bddg.Port#getOwner <em>Owner</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ports</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ports</em>' containment reference list.
	 * @see bddg.BddgPackage#getBDD_Ports()
	 * @see bddg.Port#getOwner
	 * @model opposite="owner" containment="true" required="true" ordered="false"
	 * @generated
	 */
	EList<Port> getPorts();

	/**
	 * Returns the value of the '<em><b>Root</b></em>' reference list.
	 * The list contents are of type {@link bddg.Tree}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Root</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Root</em>' reference list.
	 * @see bddg.BddgPackage#getBDD_Root()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	EList<Tree> getRoot();

	/**
	 * Returns the value of the '<em><b>Trees</b></em>' containment reference list.
	 * The list contents are of type {@link bddg.Tree}.
	 * It is bidirectional and its opposite is '{@link bddg.Tree#getOwnerBDD <em>Owner BDD</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Trees</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Trees</em>' containment reference list.
	 * @see bddg.BddgPackage#getBDD_Trees()
	 * @see bddg.Tree#getOwnerBDD
	 * @model opposite="ownerBDD" containment="true"
	 * @generated
	 */
	EList<Tree> getTrees();

} // BDD
