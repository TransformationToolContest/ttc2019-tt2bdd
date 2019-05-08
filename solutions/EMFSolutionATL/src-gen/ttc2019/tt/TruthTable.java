/**
 */
package ttc2019.tt;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Truth Table</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link ttc2019.tt.TruthTable#getName <em>Name</em>}</li>
 *   <li>{@link ttc2019.tt.TruthTable#getPorts <em>Ports</em>}</li>
 *   <li>{@link ttc2019.tt.TruthTable#getRows <em>Rows</em>}</li>
 * </ul>
 *
 * @see ttc2019.tt.TTPackage#getTruthTable()
 * @model
 * @generated
 */
public interface TruthTable extends EObject {
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
	 * @see ttc2019.tt.TTPackage#getTruthTable_Name()
	 * @model unique="false" required="true" ordered="false"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link ttc2019.tt.TruthTable#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Ports</b></em>' containment reference list.
	 * The list contents are of type {@link ttc2019.tt.Port}.
	 * It is bidirectional and its opposite is '{@link ttc2019.tt.Port#getOwner <em>Owner</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ports</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ports</em>' containment reference list.
	 * @see ttc2019.tt.TTPackage#getTruthTable_Ports()
	 * @see ttc2019.tt.Port#getOwner
	 * @model opposite="owner" containment="true" required="true" ordered="false"
	 * @generated
	 */
	EList<Port> getPorts();

	/**
	 * Returns the value of the '<em><b>Rows</b></em>' containment reference list.
	 * The list contents are of type {@link ttc2019.tt.Row}.
	 * It is bidirectional and its opposite is '{@link ttc2019.tt.Row#getOwner <em>Owner</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rows</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rows</em>' containment reference list.
	 * @see ttc2019.tt.TTPackage#getTruthTable_Rows()
	 * @see ttc2019.tt.Row#getOwner
	 * @model opposite="owner" containment="true" lower="2" ordered="false"
	 * @generated
	 */
	EList<Row> getRows();

} // TruthTable
