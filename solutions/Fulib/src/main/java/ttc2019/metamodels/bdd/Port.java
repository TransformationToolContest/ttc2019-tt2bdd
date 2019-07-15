/**
 */
package ttc2019.metamodels.bdd;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Port</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link ttc2019.metamodels.bdd.Port#getName <em>Name</em>}</li>
 *   <li>{@link ttc2019.metamodels.bdd.Port#getOwner <em>Owner</em>}</li>
 * </ul>
 *
 * @see ttc2019.metamodels.bdd.BDDPackage#getPort()
 * @model abstract="true"
 * @generated
 */
public interface Port extends EObject {
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
	 * @see ttc2019.metamodels.bdd.BDDPackage#getPort_Name()
	 * @model unique="false" required="true" ordered="false"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link ttc2019.metamodels.bdd.Port#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Owner</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link ttc2019.metamodels.bdd.BDD#getPorts <em>Ports</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owner</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owner</em>' container reference.
	 * @see #setOwner(BDD)
	 * @see ttc2019.metamodels.bdd.BDDPackage#getPort_Owner()
	 * @see ttc2019.metamodels.bdd.BDD#getPorts
	 * @model opposite="ports" required="true" transient="false" ordered="false"
	 * @generated
	 */
	BDD getOwner();

	/**
	 * Sets the value of the '{@link ttc2019.metamodels.bdd.Port#getOwner <em>Owner</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Owner</em>' container reference.
	 * @see #getOwner()
	 * @generated
	 */
	void setOwner(BDD value);

} // Port
