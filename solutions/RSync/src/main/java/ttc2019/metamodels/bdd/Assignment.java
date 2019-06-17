/**
 */
package ttc2019.metamodels.bdd;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Assignment</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link ttc2019.metamodels.bdd.Assignment#isValue <em>Value</em>}</li>
 *   <li>{@link ttc2019.metamodels.bdd.Assignment#getPort <em>Port</em>}</li>
 *   <li>{@link ttc2019.metamodels.bdd.Assignment#getOwner <em>Owner</em>}</li>
 * </ul>
 *
 * @see ttc2019.metamodels.bdd.BDDPackage#getAssignment()
 * @model
 * @generated
 */
public interface Assignment extends EObject {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(boolean)
	 * @see ttc2019.metamodels.bdd.BDDPackage#getAssignment_Value()
	 * @model unique="false" required="true" ordered="false"
	 * @generated
	 */
	boolean isValue();

	/**
	 * Sets the value of the '{@link ttc2019.metamodels.bdd.Assignment#isValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #isValue()
	 * @generated
	 */
	void setValue(boolean value);

	/**
	 * Returns the value of the '<em><b>Port</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link ttc2019.metamodels.bdd.OutputPort#getAssignments <em>Assignments</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Port</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Port</em>' reference.
	 * @see #setPort(OutputPort)
	 * @see ttc2019.metamodels.bdd.BDDPackage#getAssignment_Port()
	 * @see ttc2019.metamodels.bdd.OutputPort#getAssignments
	 * @model opposite="assignments" required="true" ordered="false"
	 * @generated
	 */
	OutputPort getPort();

	/**
	 * Sets the value of the '{@link ttc2019.metamodels.bdd.Assignment#getPort <em>Port</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Port</em>' reference.
	 * @see #getPort()
	 * @generated
	 */
	void setPort(OutputPort value);

	/**
	 * Returns the value of the '<em><b>Owner</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link ttc2019.metamodels.bdd.Leaf#getAssignments <em>Assignments</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owner</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owner</em>' container reference.
	 * @see #setOwner(Leaf)
	 * @see ttc2019.metamodels.bdd.BDDPackage#getAssignment_Owner()
	 * @see ttc2019.metamodels.bdd.Leaf#getAssignments
	 * @model opposite="assignments" required="true" transient="false" ordered="false"
	 * @generated
	 */
	Leaf getOwner();

	/**
	 * Sets the value of the '{@link ttc2019.metamodels.bdd.Assignment#getOwner <em>Owner</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Owner</em>' container reference.
	 * @see #getOwner()
	 * @generated
	 */
	void setOwner(Leaf value);

} // Assignment
